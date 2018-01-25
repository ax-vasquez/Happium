package io.happium.happium_client.persistence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HappiumSession implements Serializable {

    /**
     * Auto-incremented ID for this session
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", nullable = false)
    @Getter @Setter private Long id;

    /**
     * The absolute local, OR REMOTE http URL to an .ipa
     * or .apk file, or a .zip file containing one of these
     */
    @Column(name = "app_path", nullable = false)
    @Getter @Setter private String appPath;

    /**
     * Android or iOS
     */
    @Column(name = "platform_name", nullable = false)
    @Getter @Setter private String platformName;

    /**
     * The kind of device or emulator to use - for iOS, valid
     * values are any of the devices returned by instruments
     * with `instruments -s devices`
     */
    @Column(name = "device_name", nullable = false)
    @Getter @Setter private String deviceName;

    /**
     * UDID of the device to use for testing - for iOS, this
     * is the actual UDID of the device, which can be found in
     * iTunes with the device connected. For Android, this is
     * the value for the device property, ro.boot.serialno
     */
    @Column(name = "udid", nullable = false)
    @Getter @Setter private String udid;

    public HappiumSession( String appPath, String platformName, String deviceName, String udid ) {

        this.appPath = appPath;
        this.platformName = platformName;
        this.deviceName = deviceName;
        this.udid = udid;

    }

}
