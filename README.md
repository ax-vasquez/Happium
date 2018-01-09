# Happium
Appium Automation Spring Microservice platform

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
