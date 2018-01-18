# Happium
Happium is a self-contained test automation framework. It's designed to provie a quick and easy test automation solution to developers and/or companies who previously had no automation in their workflow.

Since Happium is a standalone service, it does not care about the exact implementation of your application/webapp. Happium is desined to run *along side* your production application(s) to eliminate any need for direct integration of Happium into your platform. However, should you prefer to implement your own custom integration with Happium, simply use the same endpoints that are used to populate the default Happium Dashboard fields.

## Assumed Knowledge (Click Links to Learn More)
In order to make the most use out of this documentation, I have provided a list of concepts you should be familiar with. These are the
concepts employed in Happium (as well as concepts you should know when it comes to configuring your environment for Happium):

### Setup
When setting up Happium, it's assumed that you know how to do the following:
   1. Set System PATH variables ( [Windows](https://www.computerhope.com/issues/ch000549.htm) | [Unix](https://stackoverflow.com/questions/14637979/how-to-permanently-set-path-on-linux-unix) )
   2. Know how to configure Android Devices for use with ADB ( [Enable USB Debugging on Android Device](https://developer.android.com/studio/command-line/adb.html#Enabling) )
   3. Know how to use [Appium Doctor](https://github.com/appium/appium-doctor) - Makes setting up Appium installation much easier

### Usage
To get the most out of Happium, you should be familiar with the concepts employed in it:
  1. [Spring Framework](https://spring.io/)
     - [Dependency Injection](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-introduction)
     - [Annotation Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-configuration-classes.html)
     - [Autowiring](http://www.baeldung.com/spring-autowire)
  2. [Spring Boot](https://github.com/spring-projects/spring-boot)
     - `spring-boot-starter-web`
     - `spring-boot-starter-thymeleaf`
     - `spring-boot-starter-jpa`
  3. [Spring Cloud](https://github.com/spring-cloud/spring-cloud-commons)
     - `spring-cloud-netflix-eureka-server` (you don't need the `eureka-client` dependency since Happium uses the Spring cloud [`DiscoveryClient`](http://static.javadoc.io/org.springframework.cloud/spring-cloud-commons-parent/1.1.4.RELEASE/org/springframework/cloud/client/discovery/DiscoveryClient.html))
  4. [ADB](https://developer.android.com/studio/command-line/adb.html)
     - Don't worry about referring to the [`Jadb`](https://github.com/vidstige/jadb) documentation - there really isn't much to go off of there, so I will provide documentation in the wiki for this later - just know basic ADB functionality
  5. Familiarity with Appium's [`DesiredCapabilities`](https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/caps.md)

## Environment Setup
In order to use Happium, you must have the following requirements:
1. Java 8 JDK
   - User Variable: Add `JAVA_HOME` variable and set value to `\Java\jdk1.8.xxxxx`
   - System PATH Variable: Edit PATH and add `..\Java\jdk1.8.xxxxx\bin\`
2. Android SDK
   - User Variable: Add `ANDROID_HOME` and set value to `..\Android\SDK`
   - System PATH Variable: Edit PATH and add `..\Android\SDK\tools\`
   - System PATH Variable: Edit PATH and add `..\Android\SDK\platform-tools\`
   - NOTE: `adb` must be running on the system(s) that monitor for connected Android devices (otherwise, you will get an Exception)
3. Hibernate-Supported Database Option
   - Any database option that Hibernate supports will work, but only PostgreSQL support is coded into Happium in the base implementation
4. `node.js` installed
   - Appium and Selenium servers are `node.js` servers, so this is a must
   - Also provides you with `npm` which is the ideal way to install `appium` and `appium-doctor`
5. Appium installed (via `npm`)
   - Best practice is to install globally

## Architecture
Happium Architecture Map:

![HappiumArchitectureMap](https://image.ibb.co/b12bgm/Architecture.png)

### Architecture Breakdown
1. Happium Client Service
   - Represents the "user's" endpoint (e.g. Happium Dashboard)
   - You can point the endpoints used in this service to set up your own custom implementation
   - End user uses this service to create new Happium Payloads which are used to direct the Selenium/Appium test passes
2. Core Netflix Eureka Service Registry
   - This is how you add/remove service nodes to the core Happium application
   - Adding a new service is as easy as setting up a new Spring `DiscoveryClient` in the submodule you are trying to add and point it to the core Eureka Registry Service
3. Selenium Grid array
   - Think of this as an "array of arrays" - each nested array has its own set of registered server nodes
        - A server node takes instructions and then executes the test pass before generating and returning a result
        - Results should be stored in local database and queried by separate service
   - Technically, since all server nodes are based on the `DriverService` class, each node can support ANY platform
      - HOWEVER, there are driver-specific platform limitations to keep in mind - for example, iOS sessions CANNOT be run on any system other than MacOS
4. Supported Desired Capabilities Client Service
   - Reference service to provide easy access to the supported `DesiredCapability` list
   - You can still explicitly-set capabilities not specifically defined in the Supported Capabilities service
   - Can be interacted with to add/remove capability support, as well as add supplemental information on how to use a given capability
5. Android Device Manager Client Service
   - Monitors a host machine for connected Android devices
   - Maintains record of connected devices by updating the Connected Android Device table for each `onConnect` event (includes connecting, disconnecting and changing the connection type)
