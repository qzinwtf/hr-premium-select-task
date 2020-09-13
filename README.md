how to build

mvn clean compile assembly:single

how to run

**1. Run server (News Analyzer)**

java -cp target/hr-premium-select-test-task-1.0-SNAPSHOT-jar-with-dependencies.jar ru.nkuzin.server.ServerApp -port [News analyzer's port]

**2. Run client (Mock news feed)**

java -cp target/hr-premium-select-test-task-1.0-SNAPSHOT-jar-with-dependencies.jar ru.nkuzin.client.ClientApp -host [News analyzer's host] -port [News analyzer's port] -freq [Frequency of news generation (how many news will be generated in a SECOND), maximum is 1000]

default values are:

host=127.0.0.1
port=7071
freq=10