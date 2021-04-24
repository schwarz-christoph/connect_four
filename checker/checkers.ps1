# -------- BEGIN CONFIGURATION --------

# Geben Sie Hier den Pfad zu ihrem Quellcode Verzeichnis an.
# Beispiel: = "C:\Users\xyz\demoProject\src"
$sourceDir = "C:\Users\Chris\OneDrive\Hochschule\2.Semester\Softwareentwicklung\Connect_Four\src"

# Geben Sie Hier den Pfad zu ihrem Test-Quellcode Verzeichnis an.
# Beispiel: = "C:\Users\xyz\demoProject\test"
$testDir = "C:\Users\Chris\OneDrive\Hochschule\2.Semester\Softwareentwicklung\Connect_Four\test"

# Geben Sie Hier den Pfad zu ihren Testklassen ( Endung) an.
# Beispiel: = "C:\Users\xyz\demoProject\out\test\demoProject"
$testClasses = "C:\Users\Chris\OneDrive\Hochschule\2.Semester\Softwareentwicklung\Connect_Four\out\test\Connect_Four"

# Geben Sie Hier den Pfad zu ihren Produktionsklassen ( Endung) an.
# Beispiel: = "C:\Users\xyz\demoProject\out\production\demoProject"
$classes = "C:\Users\Chris\OneDrive\Hochschule\2.Semester\Softwareentwicklung\Connect_Four\out\production\Connect_Four"

# Geben Sie Hier den FQCN ihren Testklasse an. Am besten hier eine TestSuit benutzen dann funktioniert auch alles.
# Beispiel: = "edu.hm.HelloWorldTest"
$TEST_FQCN = "edu.hm.se2.connect_four.csgles.AllConnectFourTests"

# Geben Sie hier das Patern um alle Tests auszuschließen beim Mutation Testing.
# Beispiel: = "hm.edu.**.*Test" würde alle Klassen die "Test" am Ende haben ausschließen.
$TEST_LIST_FQCN = "edu.hm.se2.connect_four.csgles.**.*Test"

# Geben Sie Hier den FQCN aller Produktionsklassen an mit Matching
# Beispiel: = "edu.hm.datastore.*" würde alle Klassen des DataStore im Mutation Testing einbeziehen.
$TARGET_FQCN = "edu.hm.se2.connect_four.csgles.*"

# -------- END CONFIGURATION --------

$scriptpath = $MyInvocation.MyCommand.Path
$scriptdir = Split-Path $scriptpath

cd $scriptdir
echo "`n************************************************************`n"
echo "Java Version:`n"
java --version
java --enable-preview -jar .\metrics.jar $sourceDir > .\reports\komplexitaet.log

cd $sourceDir
echo "`n************************************************************`n"
echo "Checkstyle:`n"
java -jar "$scriptdir\checkstyle-8.41.1-all.jar" -c "$scriptdir\checkstyle-2021-04-07.xml" -o $scriptdir\reports\checkstyle.log .\

cd $scriptdir
echo "`n************************************************************`n"
echo "PMD:`n"
.\pmd-bin-6.33.0\pmd-bin-6.33.0\bin\pmd.bat -showsuppressed -d $sourceDir -f text -R .\pmd-2021-04-07.xml > .\reports\pmd.log
.\pmd-bin-6.33.0\pmd-bin-6.33.0\bin\pmd.bat -showsuppressed -d $sourceDir -f html -R .\pmd-2021-04-07.xml > .\reports\pmd.html

echo "`n************************************************************`n"
echo "Junit:`n"
java -cp "$testClasses;$classes;.\junit-platform-console-standalone-1.5.2.jar" org.junit.runner.JUnitCore $TEST_FQCN > .\reports\junit.log

echo "`n************************************************************`n"
echo "Jacoco:`n"
$jacocoClassPath = "$testClasses;$classes;.\junit-platform-console-standalone-1.5.2.jar"
java -Xint -javaagent:.\jacoco-0.8.7\jacocoagent.jar=append=true,destfile=.\reports\jacoco.exec -cp $jacocoClassPath org.junit.runner.JUnitCore $TEST_FQCN
java -jar .\jacoco-0.8.7\jacococli.jar report .\reports\jacoco.exec --sourcefiles $sourceDir --classfiles $classes --html .\reports\jacoco\

echo "`n************************************************************`n"
echo "PITest:`n"
java -cp "$testClasses;$classes;.\pitest-1.6.4\*;.\junit-platform-console-standalone-1.5.2.jar" org.pitest.mutationtest.commandline.MutationCoverageReport `
        --reportDir .\reports\pitest `
        --timestampedReports=false `
        --targetClasses $TARGET_FQCN `
		--excludedClasses $TEST_LIST_FQCN `
        --sourceDirs $sourceDir `
        --mutators DEFAULTS,STRONGER `
        --targetTests $TEST_FQCN