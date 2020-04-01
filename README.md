##JavaMelody for Spring Boot Example

This example project demonstrates how to integrate JavaMelody in a Spring Boot web application using our starter package.

There are only two essential things to add to any basic Spring Boot project:

Adding the dependency net.bull.javamelody:javamelody-spring-boot-starter to the POM of the application. See pom.xml.

[source,xml]
----
<dependency>
	<groupId>net.bull.javamelody</groupId>
	<artifactId>javamelody-spring-boot-starter</artifactId>
	<version>1.82.0</version>
</dependency>
----

Adding custom configuration for JavaMelody to the application.yml or application.properties. See src/main/resources/application.yml.


Spring beans having an annotation @Controller, @RestController, @Service, @Async, @FeignClient, and RestTemplate or ElasticsearchOperations defined as bean, and methods having an annotation @Async, @Scheduled or @Schedules are automatically monitored. Otherwise, if you want to monitor method calls on some Spring beans, you can add @MonitoredWithSpring on those classes or methods.
Configuration

If you want, you can configure other settings by using configuration properties prefixed with javamelody in your application.yml or application.properties.
authentication parameters shoul added in following "javamelody.init-parameters.authorized-users"

Example for application.properties:
[source,java]
----
javamelody.enabled=true
javamelody.excluded-datasources=secretSource,topSecretSource
javamelody.spring-monitoring-enabled=true
javamelody.init-parameters.log=true
javamelody.init-parameters.url-exclude-pattern=(/webjars/.*|/css/.*|/images/.*|/fonts/.*|/js/.*)
javamelody.init-parameters.authorized-users=ADMIN77fae508:77fae508-1d23-4864-8aae-dfa4bd060fec;ADMINb6683814:b6683814-74c0-46e9-8dc2-6f976cb3c2ce
javamelody.init-parameters.monitoring-path=monitoring
----

Then you can run your spring-boot application and open http://localhost:8080/monitoring to browse the monitoring reports.
by entering following credentials 
username : ADMIN77fae508
password :77fae508-1d23-4864-8aae-dfa4bd060fec

## Security

To secure the access to the /monitoring URL, you may want to use the parameters allowed-addr-pattern to restrict access using a regexp for IP address or authorized-users for http basic auth as shown above in Configuration.

Or you may want to use Spring security. For that:
add the starter dependency in pom.xml:

[source,xml]
----

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
	</dependency>

----

   configure Spring security with .antMatchers("/monitoring").hasRole("ADMIN") (and http basic auth and in memory user's storage for example):

[source,java]
----
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and().authorizeRequests().antMatchers("/monitoring").hasRole("ADMIN").anyRequest().permitAll();
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withDefaultPasswordEncoder().username("USER3c0b47cc").password("3c0b47cc-13b1-448d-92a9-4a8bb65edc1a")
        .roles("USER").build();
    UserDetails admin = User.withDefaultPasswordEncoder().username("ADMIN16d26298").password("16d26298-3e81-435b-8e58-d49d4d798964")
        .roles("ADMIN").build();
    return new InMemoryUserDetailsManager(user, admin);
  }
}
----

#Configuration in case of management port

(Since 1.76)

Perhaps you already use a management http port (e.g. 8081) different from the application http port (8080). In that case, you may want to use the management port also to display the monitoring reports, instead of using the default application http port to display the reports. For that:

    If not already done, configure the actuator and the management http port, in pom.xml:
	
[source,xml]
----
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
---


[source,java]
----
and in application.yml (or in application.properties), for example on 8081:

management.server.port: 8081

    Enable the "monitoring" management endpoint and expose it over http, in application.yml (or in application.properties) :

management.endpoints.web.exposure.include: info,health,monitoring

javamelody:
  management-endpoint-monitoring-enabled: true

----

    http://localhost:8080/monitoring is now forbidden access
    http://localhost:8081/actuator/monitoring is now available

    Secure the access to the monitoring endpoint: See doc or use the authorized-users or allowed-addr-pattern javamelody init-parameters in application.yml/application.properties.

Or integration without the Spring Boot Starter

    Add javamelody-core dependency in your pom.xml.
    Then, copy this example of JavaMelodyConfiguration class in your application.
    If your application has auto-proxy issues, such as The bean could not be injected as ..., comment the defaultAdvisorAutoProxyCreator() method in your JavaMelodyConfiguration class and add the following dependency in your pom.xml:

[source,xml]
----
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>

----

### Reference Documentation
For further reference, please consider the following sections:
* [Documentation of JavaMelody] (https://github.com/javamelody/javamelody/wiki/SpringBootStarter)
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/maven-plugin/)

