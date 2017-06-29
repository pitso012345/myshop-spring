Description

A CRUD RESTful web service using Spring 4 specifically Spring Boot, Spring Data and Spring MVC.
It also uses Spring Test for integration testing.


Build an executable JAR

Although it is possible to package this service as a traditional WAR file for deployment to an external application server, the simpler approach demonstrated below creates a standalone application. You package everything in a single, executable JAR file by embedding the Tomcat servlet container as the HTTP runtime, instead of deploying to an external instance. I also used embedded H2 database instead of an external database.

Build the executable JAR file like this:
> mvn package

After running the Maven build, you'll find the artifact in the target folder.
Now you can run it from the command line:
> java -jar target/myshop-spring-0.0.1-SNAPSHOT.jar

After only a few seconds, the application should start up and be ready to go.


Test the service

You can test it from the command line with curl. See https://curl.haxx.se.

To post a JSON data:
(Linux)
> curl -i -H "Content-Type: application/json" -d '{"id":null,"name":"iphone 7","description":"apple","price":20.0,"version":1}' http://localhost:8080/products
(Windows)
> curl -i -H "Content-Type: application/json" -d "{\"id\":null,\"name\":\"iphone 7\",\"description\":\"apple\",\"price\":20.0,\"version\":1}" http://localhost:8080/products

Then get the posted data:
> curl -i -H "Accept: application/json" http://localhost:8080/products/1
