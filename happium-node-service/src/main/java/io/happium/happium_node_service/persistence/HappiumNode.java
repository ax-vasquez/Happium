package io.happium.happium_node_service.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class HappiumNode extends GridNodeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "happium_node_id", columnDefinition = "int")
    @Getter @Setter private Long happiumNodeId;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> : comma separated Capability values.
     * <p>
     *     Default:
     *     [
     *        Capabilities {
     *            browserName: chrome,
     *            maxInstances: 5,
     *            seleniumProtocol: WebDriver
     *        },
     *        Capabilities {
     *        browserName: firefox,
     *        marionette: true,
     *        maxInstances: 5,
     *        seleniumProtocol: WebDriver
     *        },
     *        Capabilities {
     *        browserName: internet explorer,
     *        maxInstances: 1,
     *        platform: WINDOWS,
     *        seleniumProtocol: WebDriver
     *        },
     *        Capabilities {
     *        browserName: safari,
     *        maxInstances: 1,
     *        platform: MAC,
     *        seleniumProtocol: WebDriver,
     *        technologyPreview: false
     *        }
     *     ]
     */
    @Column(name = "capabilities_string", columnDefinition = "text")
    @Getter @Setter private String capabilitiesString;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> : node is marked as "down" if the node
     *     hasn't responded after the number of checks specified
     *     in [downPollingLimit].
     * <p>
     *     Default: 2
     */
    @Column(name = "down_polling_limit", columnDefinition = "int")
    @Getter @Setter private int downPollingLimit;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> : the url that will be used to post the registration
     *     request. This option takes precedence over -hubHost and
     *     -hubPort options.
     */
    @Column(name = "hub_url", columnDefinition = "text")
    @Getter @Setter private String hubUrl;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> IP or hostname : the host address of the hub we're
     *     attempting to register with. If -hub is specified the -hubHost
     *     is determined from it.
     */
    @Column(name = "hub_host", columnDefinition = "text")
    @Getter @Setter private String hubHost;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> : the port of the hub we're attempting to register
     *     with. If -hub is specified the -hubPort is determined from it.
     */
    @Column(name = "hub_port", columnDefinition = "int")
    @Getter @Setter private int hubPort;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> : optional unique identifier for the node. Defaults
     *     to the url of the remoteHost, when not specified.
     */
    @Column(name = "node_identifier", columnDefinition = "text")
    @Getter @Setter private String uniqueNodeIdentifier;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> in ms : specifies how often the hub will poll
     *     to see if the node is still responding.
     * <p>
     *     Default: 5000
     */
    @Column(name = "node_polling", columnDefinition = "int")
    @Getter @Setter private int nodePolling;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> in ms : connection/socket timeout, used
     *     for node "nodePolling" check.
     * <p>
     *     Default: 5000
     */
    @Column(name = "node_status_check_timeout", columnDefinition = "int")
    @Getter @Setter private int nodeStatusCheckTimeout;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> : the class used to represent the node proxy.
     * <p>
     *     Default: org.openqa.grid.selenium.proxy.DefaultRemoteProxy
     */
    @Column(name = "proxy", columnDefinition = "text")
    @Getter @Setter private String proxy;

    /**
     * From Selenium Help:
     *
     * <p>
     *     if specified, node will attempt to re-register itself
     *     automatically with its known grid hub if the hub becomes
     *     unavailable.
     * <p>
     *     Default: true
     */
    @Column(name = "register", columnDefinition = "boolean")
    @Getter @Setter private boolean register;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> in ms : specifies how often the node will try to
     *     register itself again. Allows administrator to restart the
     *     hub without restarting (or risk orphaning) registered nodes.
     *     Must be specified with the "-register" option.
     * <p>
     *     Default: 5000
     */
    @Column(name = "register_cycle", columnDefinition = "int")
    @Getter @Setter private int registerCycle;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <String> URL: Address to report to the hub. Used
     *     to override default (http://<host>:<port>).
     */
    @Column(name = "remote_host_url", columnDefinition = "text")
    @Getter @Setter private String remoteHostUrl;

    /**
     * From Selenium Help:
     *
     * <p>
     *     <Integer> in ms : if the node remains down for more
     *     than [unregisterIfStillDownAfter] ms, it will stop
     *     attempting to re-register from the hub.
     */
    @Column(name = "unregister_if_still_down_after", columnDefinition = "int")
    @Getter @Setter private int unregisterIfStillDownAfter;

}
