# -------- BEGIN CONFIGURATION --------

# Geben Sie Hier den Pfad zu ihrem Quellcode Verzeichnis an.
# Beispiel: = "C:\Users\xyz\demoProject\src"
$sourceDir = "..\src"

# Geben Sie Hier den Pfad zu ihrem Test-Quellcode Verzeichnis an.
# Beispiel: = "C:\Users\xyz\demoProject\test"
$testDir = "..\test"

# Geben Sie Hier den Pfad zu ihren Testklassen (.class Endung) an.
# Beispiel: = "C:\Users\xyz\demoProject\out\test\demoProject"
$testClasses = "..\out\test"

# Geben Sie Hier den Pfad zu ihren Produktionsklassen (.class Endung) an.
# Beispiel: = "C:\Users\xyz\demoProject\out\production\demoProject"
$classes = "..\out\production"

# Geben Sie Hier den FQCN ihren Testklassen an
# Beispiel: = "edu.hm.HelloWorldTest"
$TEST_FQCN = "hm.se2.connect_four.csgles.AllConnectFourTests"

# Geben Sie Hier den FQCN ihren Produktionsklassen an
# Beispiel: = "edu.hm.HelloWorld"
#$TARGET_FQCN = "edu.hm.se2.connect_four.csgles."

# -------- END CONFIGURATION --------

$scriptpath = $MyInvocation.MyCommand.Path
$scriptdir = Split-Path $scriptpath

cd $scriptdir
java --version
java --enable-preview -jar .\metrics.jar $sourceDir > .\reports\komplexitaet.txt

cd $sourceDir
java -jar "$scriptdir\checkstyle-8.41.1-all.jar" -c "$scriptdir\checkstyle-2021-04-07.xml" -o $scriptdir\reports\checkstyle.txt .\

cd $scriptdir
.\pmd-bin-6.33.0\pmd-bin-6.33.0\bin\pmd.bat -d $sourceDir -f text -R .\pmd-2021-04-07.xml > .\reports\pmd.txt

$jacocoClassPath = "$testClasses;$classes;.\junit-platform-console-standalone-1.5.2.jar"
java -Xint -javaagent:.\jacoco-0.8.7\jacocoagent.jar=append=true,destfile=.\reports\jacoco.exec -cp $jacocoClassPath org.junit.runner.JUnitCore $TEST_FQCN
java -jar .\jacoco-0.8.7\jacococli.jar report .\reports\jacoco.exec --sourcefiles $sourceDir --classfiles $classes --html .\reports\jacoco\

java -cp "$testClasses;$classes;.\pitest-1.6.4\*;.\junit-platform-console-standalone-1.5.2.jar" org.pitest.mutationtest.commandline.MutationCoverageReport `
        --reportDir .\reports\pitest `
        --timestampedReports=false `
        --targetClasses $TARGET_FQCN `
        --sourceDirs $sourceDir `
        --mutators DEFAULTS,STRONGER `
        --targetTests $TEST_FQCN