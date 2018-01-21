package io.happium.happium_node_service.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openqa.grid.web.Hub;

import javax.persistence.*;

/**
 * Represents a HappiumHub object
 *
 * <p>
 *     HappiumHub is a wrapper class that contains an instance
 *     of the `selenium-server` Hub class. This is not THE central
 *     element of Happium, but it is one of the most important
 *     elements. Each instance of the `happium-hub-service` contains
 *     an embedded Jetty ServletDispatcher to which selenium server
 *     NODES may register with.
 * <p>
 *     It's important to keep in mind that this service is a SELF
 *     CONTAINED service, meaning that executing the jar built by
 *     the `happium-hub-service` module will only start ONE instance
 *     of the service on the host machine.
 */
@Entity
@NoArgsConstructor
public class HappiumHub extends GridNodeBase {

    /**
     * Auto-generate ID for this HappiumHub instance
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "happium_hub_id", nullable = false)
    @Getter @Setter private Long id;

    /**
     * Indicates if this HappiumHub is currently running
     */
    @Column(name = "is_running", columnDefinition = "boolean")
    @Getter @Setter private boolean isRunning;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> class name : a class implementing the CapabilityMatcher interface.
     *     Specifies the logic the hub will follow to define whether a request can be
     *     assigned to a node. For example, if you want to have the matching process
     *     use regular expressions instead of exact match when specifying browser version.
     *     ALL nodes of a grid ecosystem would then use the same capabilityMatcher,
     *     as defined here.
     * <p>
     *     Default: org.openqa.grid.internal.utils.DefaultCapabilityMatcher@5fcfe4b2
     */
    @Column(name = "capability_matcher", columnDefinition = "text")
    @Getter @Setter private String capabilityMatcher;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> in ms : The time after which a new test waiting for a node to
     *     become available will time out. When that happens, the test will throw an
     *     exception before attempting to start a browser. An unspecified, zero, or
     *     negative value means wait indefinitely.
     * <p>
     *     Default: -1
     */
    @Column(name = "new_session_wait_timeout", columnDefinition = "int")
    @Getter @Setter private int newSessionWaitTimeout;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> class name : a class implementing the Prioritizer
     *     interface. Specify a custom Prioritizer if you want to sort
     *     the order in which new session requests are processed when
     *     there is a queue. Default to null ( no priority = FIFO)
     */
    @Column(name = "prioritizer", columnDefinition = "text")
    @Getter @Setter private String prioritizer;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> class name : a class implementing the GridRegistry
     *     interface. Specifies the registry the hub will use.
     * <p>
     *     Default: org.openqa.grid.internal.DefaultGridRegistry
     */
    @Column(name = "registry", columnDefinition = "text")
    @Getter @Setter private String registry;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Boolean> true or false : If true, the hub will reject all test requests
     *     if no compatible proxy is currently registered. If set to false, the
     *     request will queue until a node supporting the capability is registered
     *     with the grid.
     * <p>
     *     Default: true
     */
    @Column(name = "throw_on_capability_not_present", columnDefinition = "boolean")
    @Getter @Setter private boolean throwOnCapabilityNotPresent;

    /**
     * Underlying Hub object that supports this HappiumHub. This is
     * not persisted to the database
     */
    @Transient
    @Getter @Setter private Hub hub;

}
