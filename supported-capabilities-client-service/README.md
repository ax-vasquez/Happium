# Before Reading
Appium has a LOT of `DesiredCapabilities`, many of which are inherited from Selenium. Fortunately, it doesn't take many capabilities to 
set up a basic Appium session, but it's not so clear exactly which capabilities should be used. In effort to save future testers from
having to navigate the vague documentation on how each capability works, I've included this simple service within Happium to serve as an
internal reference for only the `DesiredCapabilities` you care about. This service is not meant to be a mirror of information that's already
available in the Appium documentation, but rather as a means to restrict your visibility to only the capabilities you need to use. Additionally,
I will be adding facets to this service that will enable setting the possible accepted values for a given capability as well as a list of
tips per capability that help the user better understand how to use the given capability.

# Overview
Happium includes some pre-configured `DesiredCapability` settings to get you started. These capabilities are the most-commonly used, general
purpose capabilities:
  1. `platformName`
  2. `deviceName`
  3. `app`
  4. `udid`
  5. `appActivity` (Android)
  6. `appPackage` (Android)
  7. `autoGrantPermissions` (Android)
  8. `bundleId` (iOS)
  9. `autoAcceptAlerts` (iOS)

The basic details for all DesiredCapabilities can be found at `http://localhost:8001/supported_capabilities` in the form of a RESTful 
endpoint that exposes a JSON list of all current `SupportedDesiredCapability` objects.
