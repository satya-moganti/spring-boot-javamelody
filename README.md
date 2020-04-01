# JavaMelody for Spring Boot Example
This example project demonstrates how to integrate JavaMelody in a Spring Boot web application using our starter package.

There are only two essential things to add to any basic Spring Boot project:

Adding the dependency net.bull.javamelody:javamelody-spring-boot-starter to the POM of the application. See pom.xml.

```xml
<dependency>
	<groupId>net.bull.javamelody</groupId>
	<artifactId>javamelody-spring-boot-starter</artifactId>
	<version>1.82.0</version>
</dependency>

