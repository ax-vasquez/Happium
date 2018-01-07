package io.happium.android_device_manager_client_service.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class LocatorStrategyService {

    /**
     * Supported locator strategies to use
     * in relative XPath locators
     */
    public enum LOCATORS {
        TEXT("@text"),
        RESOURCE_ID("@resource-id");

        LOCATORS(String s) {
        }
    }

    /**
     * Convenience prefixes to use when locating
     * an element by ID
     */
    public enum ID_PREFIX {
        ANDROID("android:id/")
        // Add more ID prefixes as-needed
        ;

        ID_PREFIX(String s) {
        }
    }

    /**
     * Generates the relative Xpath locator strategy value
     *
     * @param targetElementPackage        e.g. com.android.Button
     * @param locator                     One of the strategies in the LOCATORS enum (edit this as needed)
     * @param value                       Value of the locator strategy identifier
     * @return                            Complete relative XPath
     */
    public String generateRelativeXpath ( String targetElementPackage, LOCATORS locator , String value ) {

        String completeRelativeXpath = "//";

        switch ( locator ) {

            case TEXT:
                completeRelativeXpath += (targetElementPackage + "[" + LOCATORS.TEXT + "=" + value + "]");
                break;
            case RESOURCE_ID:
                completeRelativeXpath += (targetElementPackage + "[" + LOCATORS.RESOURCE_ID + "=" + value + "]");
                break;

        }

        return completeRelativeXpath;

    }

    /**
     * Generates a valid locator when locating an element by its ID
     *
     * @param prefix            Any of the supported prefixes in ID_PREFIX enum
     * @param value             Valid element target ID
     * @return                  Complete ID locator
     */
    public String generateIdLocator( ID_PREFIX prefix, String value ) {

        return prefix + value;

    }

}
