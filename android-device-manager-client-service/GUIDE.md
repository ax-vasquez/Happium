# Before Reading
This guide only demonstrates ONE use case, so I feel it's important to note that this is NOT the only use case. Happium uses PostgreSQL
as the supporting Database, but it is NOT limited to this solution. Since this service uses `spring-boot-starter-data-jpa` to interact
with the database, you can alter the configuration of the `DataSource` bean in the `DatabaseConfiguration` class to use a different 
database solution. This is the only time I will mention this since it's mostly custom work and is beyond the scope of this documentation.

Happium should support any database solution that's supported by the Hibernate ORM. (Though you may need to implement dialect-specific
code if there are issues).

## How To Use This Service
In order to use `android-device-manager-client-service` effectively, you should be familiar with the following:
   1. PostgreSQL installation and setup
   2. ADB installation and operation (including some "gotchas" to watch out for)

## PostgreSQL
Happium's services are configured by default to utilize a local PostgreSQL database installation. However, simply installing PostgreSQL is
not enough to get this service working. You must also add the appropriate user/group to your PostgreSQL installation and configure the
`DatabaseConfiguration` class accordingly.

(NOTE!!! if you choose to create the default "happium" user as shown in this guide, then you DO NOT need to alter the configuration of
your `DatabaseConfiguration` class)

### Installation
PostgreSQL is easy enough to install, but if you are totally new to using a database solution, it's a good idea to follow the steps
below:
   1. Head over to the [PostgreSQL main site](https://www.postgresql.org/)
   2. Download the latest version (Happium uses version `10.1`)
   3. During installation, you should be able to choose which PostgreSQL products to install - The main thing you'll want is PgAdmin4,
   which is a GUI editor for your PostgreSQL installation
   4. After (or during) installation, you should be prompted to create a sudo (root) user password - make this something secure
      - This IS NOT the user you should use with `happium` (best practice is to create authorized users/groups per unique database instance
      within PostgreSQL)
      
### Setup
   1. Once installation is complete, launch PgAdmin4 - THIS TAKES SOME TIME TO START UP, SO BE PATIENT
   2. Expand the `Servers` node in the right-column of the UI
      - You will be prompted to enter the sudo user's password to connect
   3. Once connected, right-click on `Login/Group Roles` and select `Create` > `Login/ Group Role...`
   4. In the "Login Role" popup, set the name as `happium` then navigate to the `Privileges` tab
   5. Make sure the following privileges are ENABLED (should be set to "Yes"):
       - `Can login`
       - `Create databases`
       - `Inherit rights from the parent roles`
 
That's it for PostgreSQL! So long as you have the `happium` user (with password set to `happium`), Happium will be able to 
interact with your local PostgreSQL installation without any change to the code.

# `Jadb`
The `android-device-manager-client-service` uses `Jadb` to maintain accurate record of the connected devices. You can read more on how
to get your host environment set up for `Jadb` in the Happium Wiki article, [How to Jadb](https://github.com/ax-vasquez/Happium/wiki/How-To-Jadb).

## In Action
Happium implements the `se.vidstige.jadb.DeviceDetectionListener` to create the `AndroidConnectionListenerService`. This class' main
responsibility is to fire the `onDetect()` method each time an Android device is either:
   1. Connected
   2. Disconnected
   3. Underwent a Connection State change (e.g. from MTP to "charge only" on newer devices when connected to a PC)

If any of the above happens while `android-device-manager-client-service` is in operation, the `onDetect()` method fires to retrieve
the list of currently-connected devices. This method first deletes ALL entries from the target data table before re-populating the
table with the list of devices that are still connected to the host machine (e.g. the ones that are returned by `onDetect()`). 

Though it may seem inefficient to drop all entries on each connection event, this is necessary since some devices trigger multiple
connection events when connected to a PC. This is more common on newer devices that use USB-C type connections. For example, the 
Samsung Galaxy Note 5 only triggers `onDetect()` once when connected, but the Samsung Galaxy S8 triggers it twice each time it's connected.
Stranger still is that the S8 ONLY triggers `onDetect()` once when changing the connection type. On connection and disconnection, `onDetect()`
fires twice for the S8. As you can see, this is only two devices from the same manufacturer, yet they still have different impacts
on Happium. As such, it proved to be impractical to maintain the table's accuracy without dropping all contents at the beginning of each
`onDetect()` event.
