# Getting Started

Integration for Spring Boot 2

See the example project for a demonstration.

For Spring Boot 1, see the integration in Spring Boot without the spring-boot-starter, because the spring-boot-starter is no longer compatible with Spring Boot 1.

To integrate JavaMelody in a Spring Boot 2 app, simply add the following dependency to your Maven pom.xml:

<dependency>
	<groupId>net.bull.javamelody</groupId>
	<artifactId>javamelody-spring-boot-starter</artifactId>
	<version>1.82.0</version>
</dependency>

Then you can run your spring-boot application and open http://localhost:8080/monitoring to browse the monitoring reports.

Optionally, add the iText 2.1.7 dependency in your pom.xml if you want to use PDF exports.

Spring beans having an annotation @Controller, @RestController, @Service, @Async, @FeignClient, and RestTemplate or ElasticsearchOperations defined as bean, and methods having an annotation @Async, @Scheduled or @Schedules are automatically monitored. Otherwise, if you want to monitor method calls on some Spring beans, you can add @MonitoredWithSpring on those classes or methods.
Configuration

If you want, you can configure other settings by using configuration properties prefixed with javamelody in your application.yml or application.properties.

Example for application.yml:

javamelody:
  # Enable JavaMelody auto-configuration (optional, default: true)
  enabled: true
  # Data source names to exclude from monitoring (optional, comma-separated)
  excluded-datasources: secretSource,topSecretSource
  # Enable monitoring of Spring services and controllers (optional, default: true)
  spring-monitoring-enabled: true
  # Initialization parameters for JavaMelody (optional)
  # See: https://github.com/javamelody/javamelody/wiki/UserGuide#6-optional-parameters
  init-parameters:
    # log http requests:
    log: true
    # to exclude images, css, fonts and js urls from the monitoring:
    #url-exclude-pattern: (/webjars/.*|/css/.*|/images/.*|/fonts/.*|/js/.*)
    # to aggregate digits in http requests:
    #http-transform-pattern: \d+
    # to add basic auth:
    #authorized-users: admin:pwd
    # to change the default storage directory:
    #storage-directory: /tmp/javamelody
    # to change the default "/monitoring" path:
    #monitoring-path: /admin/performance

Example for application.properties:

# Enable JavaMelody auto-configuration (optional, default: true)
javamelody.enabled=true
# Data source names to exclude from monitoring (optional, comma-separated)
javamelody.excluded-datasources=secretSource,topSecretSource
# Enable monitoring of Spring services and controllers (optional, default: true)
javamelody.spring-monitoring-enabled=true
# Initialization parameters for JavaMelody (optional)
# See: https://github.com/javamelody/javamelody/wiki/UserGuide#6-optional-parameters
#    log http requests:
javamelody.init-parameters.log=true
#    to exclude images, css, fonts and js urls from the monitoring:
# javamelody.init-parameters.url-exclude-pattern=(/webjars/.*|/css/.*|/images/.*|/fonts/.*|/js/.*)
#    to aggregate digits in http requests:
# javamelody.init-parameters.http-transform-pattern: \d+
#    to add basic auth:
# javamelody.init-parameters.authorized-users=admin:pwd
#    to change the default storage directory:
# javamelody.init-parameters.storage-directory=/tmp/javamelody
#    to change the default "/monitoring" path:
# javamelody.init-parameters.monitoring-path=/admin/performance

Security

To secure the access to the /monitoring URL, you may want to use the parameters allowed-addr-pattern to restrict access using a regexp for IP address or authorized-users for http basic auth as shown above in Configuration.

Or you may want to use Spring security. For that:

    add the starter dependency in pom.xml:

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
	</dependency>

    configure Spring security with .antMatchers("/monitoring").hasRole("ADMIN") (and http basic auth and in memory user's storage for example):

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
    UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password")
        .roles("USER").build();
    UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("password")
        .roles("ADMIN").build();
    return new InMemoryUserDetailsManager(user, admin);
  }
}

Configuration in case of management port

(Since 1.76)

Perhaps you already use a management http port (e.g. 8081) different from the application http port (8080). In that case, you may want to use the management port also to display the monitoring reports, instead of using the default application http port to display the reports. For that:

    If not already done, configure the actuator and the management http port, in pom.xml:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

and in application.yml (or in application.properties), for example on 8081:

management.server.port: 8081

    Enable the "monitoring" management endpoint and expose it over http, in application.yml (or in application.properties) :

management.endpoints.web.exposure.include: info,health,monitoring

javamelody:
  management-endpoint-monitoring-enabled: true

    Start the application and see that:

    http://local

    host:8080/monitoring is now forbidden access
    http://localhost:8081/actuator/monitoring is now available

    Secure the access to the monitoring endpoint: See doc or use the authorized-users or allowed-addr-pattern javamelody init-parameters in application.yml/application.properties.

Or integration without the Spring Boot Starter

    Add javamelody-core dependency in your pom.xml.
    Then, copy this example of JavaMelodyConfiguration class in your application.
    If your application has auto-proxy issues, such as The bean could not be injected as ..., comment the defaultAdvisorAutoProxyCreator() method in your JavaMelodyConfiguration class and add the following dependency in your pom.xml:

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>



### Reference Documentation
For further reference, please consider the following sections:
* [Documentation of JavaMelody] (https://github.com/javamelody/javamelody/wiki/SpringBootStarter)
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/maven-plugin/)

