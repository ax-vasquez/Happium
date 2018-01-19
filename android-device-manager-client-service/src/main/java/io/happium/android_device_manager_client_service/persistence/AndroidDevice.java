package io.happium.android_device_manager_client_service.persistence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Map;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AndroidDevice {

    @Id
    @Getter @Setter private String serial;      // Device associated with multiple Appium sessions

    /**
     * Android or iOS
     */
    @Getter @Setter private String platform;

    /**
     * Device model
     */
    @Getter @Setter private String model;

    /**
     * Indicates whether device is in use by a HappiumServer instance
     */
    @Getter @Setter private boolean inUse;

    /**
     * Constructor
     *
     * <p>
     *     Takes map of device properties which are then used
     *     to initialize all integral device properties.
     *
     * @param deviceProperties      Map of properties from Android adb shell getprop command
     */
    @Autowired
    public AndroidDevice( Map<String, String> deviceProperties ) {

        _initializeImperativeProperties( deviceProperties );

    }

    /**
     * Processes the deviceProperties to obtain the model and
     * serialno - This method also sets the platform to "Android"
     *
     * This must be called prior to querying the device's attributes
     */
    private void _initializeImperativeProperties( Map<String, String> deviceProperties ) {

        platform = "Android";

        for ( Map.Entry<String, String> entry : deviceProperties.entrySet() ) {

            String keyName = entry.getKey();

            if ( keyName.contains( "ro.product.model" ) ) {

                model = entry.getValue();

            }

            if ( keyName.contains("ro.boot.serialno") ) {

                serial = entry.getValue();

            }

        }

    }

    /**
     * Convenient method to print out the device properties
     *
     * @return          Formatted string representing device properties
     */
    @Override
    public String toString() {
        return String.format(
                "Device[serial='%s', platform='%s', model='%s']",
                serial, platform, model
        );
    }

}

