package io.happium.appium_client_service.persistence;

import io.happium.appium_client_service.appium.AppiumServer;
import io.happium.appium_client_service.appium.AppiumSession;
import lombok.Getter;
import lombok.Setter;

import javax.json.JsonObject;
import javax.persistence.*;
import java.io.Serializable;

@Entity
public class HappiumSession implements Serializable {

    /**
     * Serialized ID of Happium session
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long sessionId;

    /**
     * JSON object containing DesiredCapabilities object as well
     * as any custom content for custom implementations
     */
    @Getter @Setter private JsonObject payloadObject;

    /**
     * Appium session used to execute any specified test suites (requires
     * custom implementation)
     */
    @Transient
    @Getter @Setter private AppiumSession appiumSession;

    /**
     * Appium server used to host the Appium session
     */
    @Transient
    @Getter @Setter private AppiumServer appiumServer;

}
