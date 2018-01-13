//package io.happium.appium_client_service.persistence;
//
//import io.happium.appium_client_service.appium.AppiumServer;
//import io.happium.appium_client_service.appium.AppiumSession;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.json.JsonObject;
//import javax.persistence.*;
//import java.io.Serializable;

///**
// * Core Entity of the Happium application
// *
// * <p>
// *     This is the model representation of a Happium
// *     Session. HappiumSession objects are serialized
// *     to support unique session IDs - This is the
// *     main entity you will want to have access to as
// *     it stores ALL information about any given HappiumSession.
// *
// * <p>
// *     It is important to note that even if an invalid
// *     JSON Payload is sent to the Happium service, a new
// *     HappiumSession will still be instantiated. This is to
// *     enable tracking Session Failures (and more importantly,
// *     WHY the session failed).
// *
// * <p>
// *     Once a Payload is received by the core application,
// *     a new HappiumSession is immediately instantiated with
// *     that payload. The HappiumSessionService then validates
// *     the payload. If it's unable to validate the payload, then
// *     the HappiumSession ceases further operation (but the session
// *     and it's invalid configuration are still persisted to the
// *     table for debugging purposes)
// */
//@Entity
//public class HappiumSession implements Serializable {
//
//    /**
//     * Serialized ID of Happium session
//     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Getter @Setter private Long sessionId;
//
//    /**
//     * JSON object containing DesiredCapabilities object as well
//     * as any custom content for custom implementations
//     */
//    @Getter @Setter private JsonObject payloadObject;
//
//    /**
//     * Stores record of the URL the hosting server was started on
//     */
//    @Getter @Setter private String serverUrl;
//
//    /**
//     * Appium session used to execute any specified test suites (requires
//     * custom implementation)
//     */
//    @Transient
//    @Getter @Setter private AppiumSession appiumSession;
//
//    /**
//     * Appium server used to host the Appium session
//     */
//    @Transient
//    @Getter @Setter private AppiumServer appiumServer;
//
//}
