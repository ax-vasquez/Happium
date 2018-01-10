package io.happium.appium_client_service.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * Entity representation of a Desired Capability
 */
@Entity
@Transactional
public class SupportedDesiredCapability {

    @Transient
    @Getter @Setter private static String IOS_PLATFORM = "iOS";

    @Transient
    @Getter @Setter private static String ANDROID_PLATFORM = "Android";

    /**
     * Name of the desired capability - must be unique
     */
    @Id
    @Column(nullable = false)
    @Getter @Setter private String name;        // Name must be unique

    /**
     * Appium-provided description of the DesiredCapability
     */
    @Column(nullable = false, name = "appium_description")
    @Getter @Setter private String appiumDescription;

    /**
     * Can be list_option, string, or boolean - more may be added later. This
     * cannot be nullable as all capabilities expect some form of input
     */
    @Column(nullable = false, name = "accepted_input_value_type")
    @Getter @Setter private String acceptedInputValueType;

    /**
     * Indicates whether Appium requires the presence of this capability
     * in the current test scenario - requirements are decided for you dynamically
     * by HappiumService
     */
    @Column(nullable = false)
    @Getter @Setter private boolean isRequiredCapability;

    /**
     * Indicates whether this capability is iOS/Android only
     */
    @Column(nullable = false, name = "is_platform_dependent")
    @Getter @Setter private boolean isPlatformDependent;

    /**
     * Either iOS or Android (or null, if global capability)
     */
    @Column(name = "platform_name")
    @Getter @Setter private String platformName;

    /**
     * If this capability accepts values from a predetermined
     * list of options, then this variable stores all supported
     * option values
     */
    @Column(name = "list_option_values")
    @Getter @Setter private List<String> listOptionValues;

    /**
     * Happium-provided tips that aid in creating DesiredCapabilities
     * objects
     */
    @Column(name = "happium_tips")
    @Getter @Setter private List<String> happiumTips;

}
