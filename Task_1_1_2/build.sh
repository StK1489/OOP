#!/bin/bash

echo "=== Task_1_1_1 ==="

rm -rf build docs
mkdir -p build/classes docs

javac -d build/classes src/main/java/ru/nsu/vkuznetsov/Task_1_1_1/*.java || exit 1

jar cfe build/Task_1_1_1.jar ru.nsu.vkuznetsov.Task_1_1_1.Main -C build/classes .

javadoc -d docs -sourcepath src/main/java -subpackages ru.nsu.vkuznetsov

java -jar build/Task_1_1_1.jar

echo "Done"