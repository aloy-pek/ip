#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
    rm ACTUAL.TXT
fi

# compile the code into the bin folder, terminates if error occurred
JAVA_FILES=$(find ../src/main/java/kuro -type f -name "*.java" ! -path "*/gui/*")
if ! javac -Xlint:none -d ../bin $JAVA_FILES
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ../bin kuro.chatbot.Kuro < input.txt > ACTUAL.TXT

# convert to UNIX format
cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT

# normalize slashes for cross-platform compatibility
sed -i.bak 's|\\|/|g' ACTUAL.TXT EXPECTED-UNIX.TXT
rm -f ACTUAL.TXT.bak EXPECTED-UNIX.TXT.bak

# compare the output to the expected output
diff ACTUAL.TXT EXPECTED-UNIX.TXT
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
