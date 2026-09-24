chcp 65001 | Out-Null
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
javac -encoding UTF-8 -d out src/main/java/ru/nsu/vkuznetsov/task_1_1_2/domain/*.java src/main/java/ru/nsu/vkuznetsov/task_1_1_2/game/*.java src/main/java/ru/nsu/vkuznetsov/task_1_1_2/ui/*.java src/main/java/ru/nsu/vkuznetsov/task_1_1_2/Main.java
java -cp out ru.nsu.vkuznetsov.task_1_1_2.Main