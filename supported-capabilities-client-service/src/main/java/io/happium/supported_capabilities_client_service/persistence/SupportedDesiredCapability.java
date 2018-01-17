package io.happium.supported_capabilities_client_service.persistence;

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
    @Getter @Setter private String supportedPlatform;

    /**
     * Indicates if this capability is typically required
     */
    @Column(name = "is_required", columnDefinition = "boolean",nullable = false)
    @Getter @Setter private boolean isRequired;

    /**
     * Stores a description of this capability
     */
    @Column(name = "description", columnDefinition = "text", nullable = false)
    @Getter @Setter private String description;

    /**
     * Indicates what type of values this capability accepts
     */
    @Column(name = "accepted_value_type", columnDefinition = "text", nullable = false)
    @Getter @Setter private String acceptedValueType;

    /**
     * Convenient method to print out the device properties
     *
     * @return          Formatted string representing device properties
     */
    @Override
    public String toString() {
        return String.format(
                "SupportedDesiredCapability[name='%s', supported_platform='%s', required_capability='%s'," +
                        "description='%s', accepted_value_type='%s'",
                name, supportedPlatform, isRequired, description, acceptedValueType
        );
    }

}
