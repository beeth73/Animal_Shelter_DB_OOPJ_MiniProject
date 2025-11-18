# Compile and run script for Animal Adoption JavaFX app
# Usage: Open PowerShell in project root (one level above the folder 'animal-adoption-javafx') and run:
#   .\run.ps1

$projectDir = Join-Path $PWD "animal-adoption-javafx"
$srcDir = Join-Path $projectDir "src"
$binDir = Join-Path $projectDir "bin"
$javafxLib = Join-Path $projectDir "openjfx-25.0.1_windows-x64_bin-sdk\javafx-sdk-25.0.1\lib"
<#
  Auto-detect any mysql connector jar in the project's lib folder.
  Looks for files like mysql-connector-java-*.jar or mysql-connector-java.jar
#>
$libDir = Join-Path $projectDir "lib"
$mysqlJar = $null
if (Test-Path $libDir) {
    $found = Get-ChildItem -Path $libDir -Filter "mysql-connector*.jar" -File -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($found) { $mysqlJar = $found.FullName }
}

if (-not $mysqlJar) {
    Write-Host "Warning: MySQL connector JAR not found in $libDir" -ForegroundColor Yellow
    Write-Host "Download MySQL Connector/J and place the JAR in '$libDir' (example file: mysql-connector-java-8.0.34.jar)" -ForegroundColor Yellow
}

# Create bin directory if missing
if (-not (Test-Path $binDir)) { New-Item -ItemType Directory -Path $binDir | Out-Null }

# Compile
Write-Host "Compiling sources..."
$files = Get-ChildItem -Path $srcDir -Filter *.java | ForEach-Object { $_.FullName }
javac -d $binDir --module-path $javafxLib --add-modules javafx.controls,javafx.fxml $files
if ($LASTEXITCODE -ne 0) { Write-Host "Compilation failed." -ForegroundColor Red; exit $LASTEXITCODE }

# Run
Write-Host "Running application..."
$cp = "$binDir"
if ($mysqlJar) { $cp = "$binDir;$mysqlJar" }

Write-Host "Launching with classpath: $cp"
java --enable-native-access=javafx.graphics --module-path $javafxLib --add-modules javafx.controls,javafx.fxml -cp $cp Main

# End
