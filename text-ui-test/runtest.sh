#!/usr/bin/env bash

# change to script directory
cd "${0%/*}"

cd ..
./gradlew clean shadowJar

cd text-ui-test

java  -jar $(find ../build/libs/ -mindepth 1 -print -quit) < input.txt > ACTUAL.TXT

# Normalize the output by removing extra whitespaces and newlines at the end of each line
sed 's/[[:space:]]*$//' ACTUAL.TXT > ACTUAL-NORMALIZED.TXT
sed 's/[[:space:]]*$//' EXPECTED.TXT > EXPECTED-NORMALIZED.TXT

# Compare the normalized outputs
diff EXPECTED-NORMALIZED.TXT ACTUAL-NORMALIZED.TXT
if [ $? -eq 0 ]
then
    echo "Test passed!"
    exit 0
else
    echo "Test failed!"
    exit 1
fi
