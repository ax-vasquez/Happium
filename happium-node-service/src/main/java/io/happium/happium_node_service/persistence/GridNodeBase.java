package io.happium.happium_node_service.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Base Node class
 *
 * <p>
 *     This class is extended into both the Hub and
 *     Node classes. This class contains the shared
 *     properties that both Node and Hub classes, and should
 *     be used as the core grid node for this service
 *     instance
 */
@NoArgsConstructor
public class GridNodeBase implements Serializable {

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> in seconds: number of seconds a browser session is allowed to hang while
     *     a WebDriver command is running (example: driver.get(url)). If
     *     the timeout is reached while a WebDriver command is still
     *     processing, the session will quit. Minimum value is 60. An
     *     unspecified, zero, or negative value means wait indefinitely.
     * <p>
     *     Default: 5000
     */
    @Column(name = "browser_timeout", columnDefinition = "int")
    @Getter @Setter private int browserTimeout;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> in ms : specifies how often the hub will poll running proxies
     *     for timed-out (i.e. hung) threads. Must also specify "timeout" option
     * <p>
     *     Default: 5000
     */
    @Column(name = "clean_up_cycle", columnDefinition = "int")
    @Getter @Setter private int cleanUpCycle;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Boolean> : enables LogLevel.FINE.
     * <p>
     *     Default: false
     */
    @Column(name = "debug_enabled", columnDefinition = "boolean")
    @Getter @Setter private boolean debugEnabled;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Boolean>: Whether or not to use the experimental
     *     passthrough mode. Defaults to true.
     * <p>
     *     Default: true
     */
    @Column(name = "enable_pass_through", columnDefinition = "boolean")
    @Getter @Setter private boolean passThroughEnabled;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> IP or hostname : usually determined automatically.
     *     Most commonly useful in exotic network configurations (e.g.
     *     network with VPN)
     */
    @Column(name = "host_address", columnDefinition = "text")
    @Getter @Setter private String hostAddress;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> : max number of threads for Jetty. An unspecified,
     *     zero, or negative value means the Jetty default value (200)
     *     will be used.
     */
    @Column(name = "jetty_max_threads", columnDefinition = "int")
    @Getter @Setter private int jettyMaxThreads;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> filename : the filename to use for logging.
     *     If omitted, will log to STDOUT
     */
    @Column(name = "log_file_name", columnDefinition = "text")
    @Getter @Setter private String logFileName;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> max number of tests that can run at the
     *     same time on the node, irrespective of the browser used
     */
    @Column(name = "max_session", columnDefinition = "int")
    @Getter @Setter private int maxSessions;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> : the port number the server will use.
     * <p>
     *     Default: 4444
     */
    @Column(name = "port", columnDefinition = "int")
    @Getter @Setter private int port;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> options are [hub], [node], or [standalone].
     * <p>
     *     Default: hub
     */
    @Column(name = "node_role", columnDefinition = "text")
    @Getter @Setter private String role;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> in seconds : Specifies the timeout before the server
     *     automatically kills a session that hasn't had any activity in
     *     the last X seconds. The test slot will then be released for
     *     another test to use. This is typically used to take care of
     *     client crashes. For grid hub/node roles, cleanUpCycle must also
     *     be set.
     * <p>
     *     Default: true
     */
    @Column(name = "timeout", columnDefinition = "int")
    @Getter @Setter private int timeout;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> : list of extra servlets the grid (hub or node)
     *     will make available.
     * <p>
     *     Default: []
     */
    @Column(name = "with_servlets", columnDefinition = "text[]")
    @Getter @Setter private String[] withServlets;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> : list of default (hub or node) servlets to
     *     disable. Advanced use cases only. Not all default
     *     servlets can be disabled.
     * <p>
     *     Default: []
     */
    @Column(name = "without_servlets", columnDefinition = "text[]")
    @Getter @Setter private String[] withoutServlets;

}
