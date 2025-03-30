@echo off
setlocal enableextensions
pushd %~dp0

cd ..
call gradlew clean shadowJar

cd build\libs
for /f "tokens=*" %%a in (
    'dir /b *.jar'
) do (
    set jarloc=%%a
)

java -jar %jarloc% < ..\..\text-ui-test\input.txt > ..\..\text-ui-test\ACTUAL.TXT

cd ..\..\text-ui-test

:: Normalize the ACTUAL.TXT and EXPECTED.TXT to remove trailing spaces and newlines
powershell -Command "Get-Content ACTUAL.TXT | ForEach-Object {$_ -replace '\s+$',''} | Set-Content ACTUAL-NORMALIZED.TXT"
powershell -Command "Get-Content EXPECTED.TXT | ForEach-Object {$_ -replace '\s+$',''} | Set-Content EXPECTED-NORMALIZED.TXT"

:: Compare the normalized files
FC ACTUAL-NORMALIZED.TXT EXPECTED-NORMALIZED.TXT >NUL && ECHO Test passed! || ECHO Test failed!

popd