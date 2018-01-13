package io.happium.appium_client_service.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity representation of a Desired Capability
 */
@Entity
@NoArgsConstructor
public class SupportedDesiredCapability {

    /**
     * Name of the desired capability - must be unique
     */
    @Id
    @Column(name = "name", columnDefinition = "text", nullable = false)
    @Getter @Setter private String name;        // Name must be unique

    /**
     * iOS, Android, or global
     */
    @Column(name = "supported_platform", columnDefinition = "text", nullable = false)
    @Getter @Setter String supportedPlatform;

    /**
     * Indicates if this capability is typically required
     */
    @Column(name = "is_required", columnDefinition = "boolean",nullable = false)
    @Getter @Setter boolean isRequired;

    /**
     * Stores a description of this capability
     */
    @Column(name = "description", columnDefinition = "text", nullable = false)
    @Getter @Setter String description;

    /**
     * Indicates what type of values this capability accepts
     */
    @Column(name = "accepted_value_type", columnDefinition = "text")
    @Getter @Setter String acceptedValueType;

    /**
     * Only required if the acceptedValueType is "list_option"
     */
    @Column(name = "accepted_values_list", columnDefinition = "text[]")
    @Getter @Setter String[] acceptedValuesList;

    /**
     * List of tips on how this capability should be used. This
     * can be empty
     */
    @Column(name = "usage_tips", columnDefinition = "text[]")
    @Getter @Setter String[] usageTips;

    /**
     * List of Android capabilities that can be used instead of this capability (if any)
     */
    @Column(name = "alt_android_capabilities", columnDefinition = "text[]")
    @Getter @Setter String[] altAndroidCapabilities;

    /**
     * List of iOS capabilities that can be used instead of this capability (if any)
     */
    @Column(name = "alt_ios_capabilities", columnDefinition = "text[]")
    @Getter @Setter String[] altIOSCapabilities;

    /**
     * List of Global capabilities that can be used instead of this capability (if any)
     */
    @Column(name = "alt_global_capabilities", columnDefinition = "text[]")
    @Getter @Setter String[] altGlobalCapabilities;

    /**
     * List of capabilities that this capability requires in order to work
     */
    @Column(name = "dependent_capabilities", columnDefinition = "text[]")
    @Getter @Setter String[] dependentCapabilities;

    /**
     * Convenient method to print out the device properties
     *
     * @return          Formatted string representing device properties
     */
    @Override
    public String toString() {
        return String.format(
                "SupportedDesiredCapability[name='%s', supported_platform='%s', required_capability='%s'," +
                        "description='%s', accepted_value_type='%s', accepted_values='%s', usage_tips='%s'" +
                        "alt_android_capabilities='%s', alt_ios_capabilities='%s', alt_global_capabilities='%s'" +
                        "dependent_capabilities='%s'",
                name, supportedPlatform, isRequired, description,
                acceptedValueType, acceptedValuesList, usageTips,
                altAndroidCapabilities, altIOSCapabilities, altGlobalCapabilities,
                dependentCapabilities
        );
    }


}
