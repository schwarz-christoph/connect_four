# (C) R. Schiedermeier, 2021

import socket
import sys
import threading
import time
# sense_emu = Emulator
# sense_hat = Hardware
try:
    f = open("/proc/device-tree/hat/product", "r")
    from sense_hat import SenseHat, ACTION_PRESSED
except IOError:
    from sense_emu import SenseHat, ACTION_PRESSED


sense = SenseHat()
sense.clear()

# Image of LED array: List of Tupels, R G B
leds = []
for _ in range(0, 64):
    leds += [[0, 0, 0]]
# Controls server accept loop
goon = True
# Expected command lengths
command_lengths = {'q': 1,
                   'x': 1,
                   'e': 1,
                   'w': 1,
                   'c': 1,
                   'p': 1 + 2 + 6,
                   'g': 1 + 6 * 8 * 8
                   }
# Last seen joystick direction, x = none
last_event = 'x'


def record_event(event):
    global last_event
    if event.action == ACTION_PRESSED:
        last_event = event.direction


# Register callbacks for all joystick events
sense.stick.direction_up = record_event
sense.stick.direction_down = record_event
sense.stick.direction_left = record_event
sense.stick.direction_right = record_event
sense.stick.direction_middle = record_event


def loop(connection, client_address):
    try:
        while True:
            data = connection.recv(6 * 64 + 8)
            result = process(data.strip(), connection)
            if not result:
                break
    finally:
        connection.close()


def process(string, connection):
    global leds
    global last_event
    global goon
    result = True
    print >> sys.stderr, 'received "%s"' % string
    response = '0'
    if len(string) == 0:
        response = '1'
    elif command_lengths[string[0]] != len(string):
        response = '2'
    elif string[0] == 'p':
        x = ord(string[1]) - ord('0')
        y = ord(string[2]) - ord('0')
        red = int(string[3:5], 16)
        green = int(string[5:7], 16)
        blue = int(string[7:9], 16)
        leds[8 * y + x] = [red, green, blue]
        sense.set_pixels(leds)
    elif string[0] == 'g':
        for i in range(0, 64):
            offset = 1 + i * 6
            red = int(string[offset:offset + 2], 16)
            green = int(string[offset + 2:offset + 4], 16)
            blue = int(string[offset + 4:offset + 6], 16)
            leds[i] = [red, green, blue]
        sense.set_pixels(leds)
    elif string[0] == 'c':
        for i in range(0, 64):
            leds[i] = [0, 0, 0]
        sense.set_pixels(leds)
    elif string[0] == 'e':
        response = '0' + str(last_event)[0:1]
        last_event = 'x'
    elif string[0] == 'w':
        while last_event == 'x':
            time.sleep(0.02)
        response = '0' + str(last_event)[0:1]
        last_event = 'x'
    elif string[0] == 'q':
        result = False
    elif string[0] == 'x':
        goon = False
        result = False
        clientsock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        clientsock.connect(("localhost", 29144))
    else:
        response = '3'
    connection.sendall(response + '\n')
    return result


# Open server socket and wait for connections
# Start thread for each connection, then wait for next
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server_address = ('', 29144)  # empty string = any network interface
print >> sys.stderr, 'starting up on %s port %s' % server_address
sock.bind(server_address)
sock.listen(1)
while goon:
    # Wait for a connection
    print >> sys.stderr, 'waiting for a connection'
    connection, client_address = sock.accept()
    print >> sys.stderr, 'connection from', client_address
    if goon:
        processor = threading.Thread(target=loop, args=(connection, client_address,))
        processor.start()
