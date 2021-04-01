# Spielbeschreibung

Fakultät 07 für Informatik und Mathematik, Hochschule München
Vorlesung Softwareentwicklung 2 (R. Schiedermeier)

Arbeitsgruppe:

- Christoph Schwarz
- Georg Lang
- Enno Scholz

## Spieldetails

### Spielaufbau
- 8x7 Matrix für Spielsteine
- platzierbare Spielsteine für zwei Spieler in verschiedenen Farben
- 8x1 Matrix für Menü (neu starten, Spiel beenden, 2 Joker pro Spieler)

### Spielablauf
- Auswahl von Spielerzahl (1-2)
  - bei einem Spieler leuchtet linke Hälfte der Matrix in Spielerfarbe
  - bei zwei Spielern leuchtet beide Hälften in zwei verschiedenen Spielerfarben
- Spieler 1 beginnt
- Abwechselndes platzieren von Steinen in Spalten (fallen nach unten)
- Spieler kann zwei Joker pro Spiel einsetzen statt Stein zu platzieren
- Spielende
  - tritt bei einer Sequenz von 4 Steinen (horizontal, vertikal, diagonal) ein oder wenn das Feld voll ist
  - wenn Spieler gewinnt leuchten alle Felder in Farbe des Spielers
  - wenn das Feld voll ist (ohne Gewinner) leuchtet das Feld in den beiden Spielerfarben

#### Joker
- jeweils ein mal einsetzbar (wird ausgegraut)
  - Löschen
    - einzelnen Stein löschen
    - Reihe/Spalte löschen
  - Bombe (nur auf leeres Feld platzierbar)
    - löscht alle Steine im Radius von 2

### Eingabemöglichkeiten
- Auswahl von Spielerzahl + Spielstart
  - Auswahl mit Joystick (links, rechts) -> 1/2 Spieler
  - Starten mit Joystick (mitte)
- Stein platzieren
  - Indikatorsäule (in Farbe des Spielers) in Spalte zeigt an wo man Stein platzieren würde
  - verschieben von Indikator mit Joystick (links, rechts)
  - platzieren mit Joystick (mitte)
- Menü (Joker einsetzten, Zurücksetzen und Abbrechen des Spiels)
  - Menü aktivieren mit Joystick (oben)
  - navigieren mit Joystick (links, rechts)
  - aktivieren mit Joystick (mitte)
  - zurück zu Stein platzieren mit Joystick (unten)
- bei Spielende Joystick (mitte) drücken um neu zu starten

## Bauteile

- `edu.hm.se2.connect_four.csgles` -- Kanonisches Package
  - `datastore` -- Bietet eine unveränderliche Sicht auf die Daten.
    - `datastore.mutable`  -- Bietet eine veränderliche Sicht auf die Daten.
  - `logic` -- Beinhaltet Logik zur Verwaltung der Daten des Models.
  - `control` -- Bildet den Controller des Spiels, verwaltet das Model und die View des Spiels
  - `view` -- Verschiedene Ansichten auf den Spielstand.

### Datenbasis

- `datastore` -- erlaubt lesenden Zugriff auf alle Daten.
  - `datastore.Game` -- das Spiel.
    - `Board` Game.getBoard()
    - `Player` Game.getActivePlayer() -- wer dran ist.
    - `Player` Game.getWinner()
    - `bool` Game.getIsStarted()
    - `int` Game.getPlayerCount()
  - `datastore.Board` -- das Spielfeld.
    - `List<Field>` Board.getFields() -- alle belegten Felder.
    - `Field` Board.getField()
    - `List<Field>` Board.getHighlight() -- Felder mit Indikator.
  - `datastore.Field` -- einzelnes Feld.
    - `int` Field.getCoordinateX()
    - `int` Field.getCoordinateY()
    - `Player` Field.owner() -- wer das Feld belegt.
  - `datastore.Player` -- Repräsentiert einen Spieler.
    - `PlayerID` Player.getID()
    - `boolean` Player.getUsedBombJoker()
    - `boolean` Player.getUsedDeleteJoker()
- `datastore.mutable` -- erlaubt schreibenden Zugriff auf alle Daten.
  - `datastore.mutable.FullGame` -- das Spiel.
    - `Board` FullGame.getBoard()
    - `Player` FullGame.getActivePlayer() -- wer dran ist.
    - `Player` Game.getWinner()
    - `bool` Game.getIsStarted()
    - `int` Game.getPlayerCount()
    - `void` FullGame.setActivePlayer()
    - `void` FullGame.setWinner()
    - `boolean` FullGame.setIsStarted()
    - `int` FullGame.setPlayerCount()
  - `datastore.mutable.FullBoard` -- das Spielfeld.
    - `List<Field>` FullBoard.getFields() -- alle belegten Felder.
    - `void` FullBoard.toggleStone() -- place new stone or delete Stone
    - `Field` FullBoard.getField()
    - `List<Field>` FullBoard.setHighlight() -- Felder mit Indikator.
  - `datastore.mutable.FullPlayer` -- Repräsentiert einen Spieler.
    - `PlayerID` FullPlayer.getID()
    - `boolean` FullPlayer.getUsedBombJoker()
    - `void` FullPlayer.useBombJoker()
    - `boolean` FullPlayer.getUsedDeleteJoker()
    - `void` FullPlayer.useDeleteJoker()

### Logik

- `logic` -- Beinhaltet Logik zur Verwaltung der Daten des Models.
  - `logic.GameManager`
    - `List<Move>` getMoves()
    - `void` executeMove()
  - `logic.Move`-- Enum mit allen möglichen Inputs (right, left, up, down, confirm)

### Control

- `control` -- Bildet den Controller des Spiels, verwaltet das Model und die View des Spiels.
  - `control.Joystick`
    - `void` run()

### View

- `view` -- Verschiedene Ansichten auf den Spielstand.
  - `view.LEDMatrix`
    - `void` updateCursor()
    - `void` updateMatrix() -- Updated das ganze Feld (Hauptfeld und Joker)
    - `void` updateWinner()
    - `void` updatePlayerSelect()


## Use Cases

### LED Colors
- G = green (Player 1)
- B = blue (Player 2)
- C = cyan (Lösch-Joker)
- O = orange (Bombe-Joker)
- R = red (Spiel beenden)
- Y = yellow (Spiel neu starten)
- Kleinbuchstabe = Farbe in abgeschwächter Farbintensität (Highlight)
- "\<Buchstabe\>-" = ausgegraute Farbe (Joker verwendet)

### Use Case 'initialize'

Zuerst muss die Spielerzahl ausgewählt werden.
Hierzu werden die Hälften der LED-Matrix des SenseHAT's in der Spielerfarbe oder in schwarz eingefärbt.
```
  0 1 2 3 4 5 6 7 
 +===============+
0|G G G G        |
1|G G G G        |
2|G G G G        |
3|G G G G        |
4|G G G G        |
5|G G G G        |
6|G G G G        |
7|G G G G        |
 +===============+
```
*LED Matrix mit momentan einem Spieler.*

```
  0 1 2 3 4 5 6 7 
 +===============+
0|G G G G B B B B|
1|G G G G B B B B|
2|G G G G B B B B|
3|G G G G B B B B|
4|G G G G B B B B|
5|G G G G B B B B|
6|G G G G B B B B|
7|G G G G B B B B|
 +===============+
```
*LED Matrix mit momentan zwei Spielern.*

- Joystick Links: Verringere Spieleranzahl um eins, mindestens ein Spieler.
- Joystick Rechts: Erhöhe Spieleranzahl um eins, maximal zwei Spieler.
- Joystick Mitte: Bestätige Eingabe, starte Spiel.

### Use Case 'Stein platzieren'

Ein Highlight in Spielerfarbe zeigt an in welcher Spalte der Stein platziert wird.
```
  0 1 2 3 4 5 6 7 
 +===============+
0|O C   R Y   C O|
1|          g    |
2|          g    |
3|          g    |
4|          g    |
5|      B   g    |
6|      B G g    |
7|      G B G    |
 +===============+
```
*Spieler 1 (grün) ist momentan an der Reihe*

- Joystick Links: Highlight nach links verschieben.
- Joystick Rechts: Highlight nach rechts verschieben.
- Joystick Mitte: Spaltenauswahl bestätigen.

### Use Case 'Menü bedienen'

Ein Highlight in der Menüzeile (erste Zeile) zeigt an welcher Menüpunkt ausgewählt wird. Das Highlight ist in Spielerfarbe oder in einer abgeschwächten Farbintensität des ausgewählten Menüpunkts.
```
  0 1 2 3 4 5 6 7 
 +===============+
0|O c   R Y   C O|
1|               |
2|               |
3|               |
4|               |
5|      B        |
6|      B G      |
7|      G B G    |
 +===============+
```
*Lösch-Joker von Spieler 1 momentan ausgewählt*

- Joystick Links: Highlight nach links verschieben.
- Joystick Rechts: Highlight nach rechts verschieben.
- Joystick Mitte: Spaltenauswahl bestätigen.

### Use Case 'Joker verbraucht'

Verbrauchte Joker werden in einer ausgegrauten Farbe dargestellt.
```
  0 1 2 3 4 5 6 7 
 +===============+
0|O C-  r Y   C O|
1|               |
2|               |
3|               |
4|               |
5|      B        |
6|      B G      |
7|      G B G    |
 +===============+
```
*Lösch-Joker von Spieler 1 verbraucht, Spiel beenden ausgewählt*

- Joystick Links: Verringere Spieleranzahl um eins, mindestens ein Spieler.
- Joystick Rechts: Erhöhe Spieleranzahl um eins, maximal zwei Spieler.
- Joystick Mitte: Bestätige Eingabe, starte Spiel.

### Use Case 'Lösch-Joker verwenden'

Der Lösch-Joker erlaubt es einem Spieler eine Spalte, Zeile oder einzelnes Feld auszuwählen wo dann alle vorhanden Steine gelöscht werden. Es können Steine durch das Löschen nach unten "fallen".
```
  0 1 2 3 4 5 6 7 
 +===============+
0|O c   R Y   C O|
1|               |
2|               |
3|               |
4|               |
5|      B        |
6|W W W b g W W W|
7|      G B G    |
 +===============+
```
*Spieler der Joker eingesetzt hat, wählt gerade vorletzte Zeile zum löschen aus.*

```
  0 1 2 3 4 5 6 7 
 +===============+
0|O c   R Y   C O|
1|      W        |
2|      W        |
3|      W        |
4|      W        |
5|      b        |
6|      b G      |
7|      g B G    |
 +===============+
```
*Spieler der Joker eingesetzt hat, wählt gerade vierte Spalte zum löschen aus.*

```
  0 1 2 3 4 5 6 7 
 +===============+
0|O c   R Y   C O|
1|               |
2|               |
3|               |
4|               |
5|      B        |
6|      b G      |
7|      G B G    |
 +===============+
```
*Spieler der Joker eingesetzt hat, wählt gerade das Feld an den Koordinaten (3,6) zum löschen aus.*

- Joystick Links: Highlight nach links verschieben, beim navigieren außerhalb des Spielfeldes wird das einzelne Feld zu einer Zeile.
- Joystick Rechts: Highlight nach rechts verschieben, beim navigieren außerhalb des Spielfeldes wird das einzelne Feld zu einer Zeile.
- Joystick Oben: Highlight nach oben verschieben, beim navigieren außerhalb des Spielfeldes wird das einzelne Feld zu einer Spalte.
- Joystick Unten: Highlight nach unten verschieben, beim navigieren außerhalb des Spielfeldes wird das einzelne Feld zu einer Spalte.
- Joystick Mitte: Bestätige Eingabe, Auswahl löschen.

### Use Case 'Bombe-Joker verwenden'

Der Bombe-Joker erlaubt es einem Spieler einen Bereich mit dem Radius 2 auszuwählen wo dann alle vorhanden Steine gelöscht werden. Es können Steine durch das Löschen nach unten "fallen". Das Feld der Bombe muss leer sein.
```
  0 1 2 3 4 5 6 7 
 +===============+
0|o C   R Y   C O|
1|               |
2|               |
3|        W      |
4|      W W W    |
5|    W b O W W  |
6|      b g W    |
7|      G b G    |
 +===============+
```
*Spieler der Joker eingesetzt hat, platziert gerade eine Bombe an der Koordinate (4,5).*

- Joystick Links: Bombe nach links verschieben.
- Joystick Rechts: Bombe nach rechts verschieben.
- Joystick Oben: Bombe nach oben verschieben.
- Joystick Unten: Bombe nach unten verschieben.
- Joystick Mitte: Bestätige Eingabe, Auswahl löschen.

### Use Case 'Spielende'

Nachdem ein Spieler 4 Steine in einer Sequenz gelegt hat und somit gewonnnen hat, wird die Spielerfarbe auf allen LEDs der Matrix angezeigt.
```
  0 1 2 3 4 5 6 7 
 +===============+
0|G G G G G G G G|
1|G G G G G G G G|
2|G G G G G G G G|
3|G G G G G G G G|
4|G G G G G G G G|
5|G G G G G G G G|
6|G G G G G G G G|
7|G G G G G G G G|
 +===============+
```
*Spieler 1 hat gewonnen.*

- Joystick Mitte: Spiel neu starten.

### Use Case 'Spiel unentschieden'

Wenn die ganze Matrix voll ist ohne das ein Spieler eine Sequenz von 4 Steinen legen konnte, endet das Spiel in unentschieden. Die LEDs der Matrix werden abwechselnd in den Spielerfarben angezeigt.
```
  0 1 2 3 4 5 6 7 
 +===============+
0|G B G B G B G B|
0|B G B G B G B G|
0|G B G B G B G B|
0|B G B G B G B G|
0|G B G B G B G B|
0|B G B G B G B G|
0|G B G B G B G B|
0|B G B G B G B G|
 +===============+
```
*Spieler 1 hat gewonnen.*

- Joystick Mitte: Spiel neu starten.