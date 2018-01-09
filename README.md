# Happium
Appium Automation Spring Microservice platform

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
  1. Spring Framework
     - [Dependency Injection](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-introduction)
     - [Annotation Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-configuration-classes.html)
     - [Autowiring](http://www.baeldung.com/spring-autowire)
  2. Spring Boot
     - `spring-boot-starter-web`
     - `spring-boot-starter-thymeleaf`
     - `spring-boot-starter-jpa`
  3. Spring Cloud
     - `spring-cloud-netflix-eureka-server` (only need the `eureka-server` dependency since Happium uses the Spring cloud `DiscoveryClient`)
  4. [ADB](https://developer.android.com/studio/command-line/adb.html)
     - Don't worry about referring to the `Jadb` documentation - there really isn't much to go off of there, so I will provide documentation in the wiki for this later - just know basic ADB functionality
  5. Familiarity with Appium's [`DesiredCapabilities`](https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/caps.md)

## Pre-Requisites
Happium relies on several pre-requisites in order to work:
  1. *Java 8 JDK*
  2. *Android SDK*
  3. *Hibernate-Supported Database Storage Option*
  4. *`node.js` Installed*
  5. *Appium Installed (via NPM - requires `node.js`)*
  
As of writing this, only Windows has been tested for support. You can find more information on how to configure a Windows system for Happium here: https://github.com/ax-vasquez/Happium/wiki/Windows-Setup. Technically, there is code in place that should support operation on a MacOS machine, but since this has not been tested, I am not advertising this functionality yet.

## Mobile Operating System Support
   Happium has code to support both iOS and Android Appium session, but as of now, only Android is currently supported. Happium's primary
strength is enabling rapid-execution of Appium test suites by leveraging concurrent operation. There are a couple of degrees of complexity
to doing this.

   First, an Appium server *is only capable of hosting a single device at a time*; in other words, it is impossible to host multiple Appium
sessions on a single Appium server. Due to this limitation, runninh multiple Appium sessions can only be accomplished via concurrency. 
(Remember, this project uses the Spring Framework, so don't introduce thread management strategies that are not handled by Spring, 
otherwise you run the risk of introducing unmanaged threads).

   The second degree of complexity is the fact that, for iOS devices, *only one physical (or emulated) device can be hosted on a single
HOST MACHINE at once*. This makes concurrent iOS Appium tests impossible without some way to manage concurrent Virtual Machine instances of
MacOS installations - something a bit beyond my scope at the moment. As such, *only Android is currently supported by Happium*.
