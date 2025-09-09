@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
set SOURCES=
for /R ..\src\main\java\kuro %%f in (*.java) do (
    echo %%f | findstr /i "\\gui\\" >nul
    if errorlevel 1 (
        set SOURCES=!SOURCES! %%f
    )
)

REM compile all collected source files into ..\bin
if defined SOURCES (
    javac -d ..\bin !SOURCES!
)

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin kuro.chatbot.Kuro < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
