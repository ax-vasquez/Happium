package io.happium.happium_client_service.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Core Entity of the Happium application
 *
 * <p>
 *     This is the model representation of a Happium
 *     Session. HappiumSession objects are serialized
 *     to support unique session IDs - This is the
 *     main entity you will want to have access to as
 *     it stores ALL information about any given HappiumSession.
 *
 * <p>
 *     It is important to note that even if an invalid
 *     JSON Payload is sent to the Happium service, a new
 *     HappiumSession will still be instantiated. This is to
 *     enable tracking Session Failures (and more importantly,
 *     WHY the session failed).
 *
 * <p>
 *     Once a Payload is received by the core application,
 *     a new HappiumSession is immediately instantiated with
 *     that payload. The HappiumSessionService then validates
 *     the payload. If it's unable to validate the payload, then
 *     the HappiumSession ceases further operation (but the session
 *     and it's invalid configuration are still persisted to the
 *     table for debugging purposes)
 */
@Entity
@NoArgsConstructor
public class HappiumSession implements Serializable {

    /**
     * Serialized ID of Happium session
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", nullable = false)
    @Getter @Setter private Long sessionId;

    /**
     * JSON object containing DesiredCapabilities object as well
     * as any custom content for custom implementations
     */
    @Column(name = "payload_string", columnDefinition = "text")
    @Getter @Setter private String payloadString;       // Convert this to a JsonNode later

    /**
     * Stores record of the URL the hosting server was started on
     */
    @Column(name = "server_url", columnDefinition = "text")
    @Getter @Setter private String serverUrl;

    @Column(name = "total_tests", columnDefinition = "int")
    @Getter @Setter private int totalTestCount;

    @Column(name = "passed_count", columnDefinition = "int")
    @Getter @Setter private int passedTestCount;

    /**
     * Number of tests failed in the given test suite
     */
    @Column(name = "failed_count", columnDefinition = "int")
    @Getter @Setter private int failedTestCount;

    /**
     * Number of tests ignored in the given test suite
     */
    @Column(name = "ignored_count", columnDefinition = "int")
    @Getter @Setter private int ignoredTestCount;

}
