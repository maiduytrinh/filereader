# Rest API for Base

## Quick run
Build development environment
> mvn clean install 

Build testing environment
> mvn clean install -Ptest

Build development environment
> mvn clean install -Pprod

Run
> java -jar target/file-reader-rest-api.jar

By default the server starts on port: 4444, alternatively you can select a different port:
> java -jar target/file-reader-rest-api.jar -p 5555 

Test
> curl http://localhost:4444/api/echo

Rest API documentation 
> curl http://localhost:4444/api/


See all available on: [http://localhost:4444/](http://localhost:4444/)

Run debug mode

java -agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n -jar target/file-reader-rest-api.jar

Run JMX mode:

java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=128.199.248.69 -jar file-reader-rest-api.jar

nohup java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=128.199.248.69 -XX:MaxDirectMemorySize=128M -Xmx256M -jar file-reader-rest-api.jar &

nohup java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9991 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=128.199.248.69 -XX:MaxDirectMemorySize=128M -Xmx256M -jar file-reader-rest-api.jar -p 5555 &

Production :
nohup java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9992 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=128.199.248.69 -XX:MaxDirectMemorySize=128M -Xmx256M -jar file-reader-rest-api.jar -p 8080 &

Sonar

mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=cb23691577585883407d64869d4a5500ec96079a
>>>>>>> 3d65b1b (Create project java file reader rest api)
