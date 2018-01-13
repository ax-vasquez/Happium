package io.happium.appium_client_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.happium.appium_client_service.persistence.SupportedDesiredCapability;
import io.happium.appium_client_service.persistence.SupportedDesiredCapabilityCrudRepository;
import io.happium.appium_client_service.util.CapabilityParser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service class for Supported Desired Capabilities
 *
 * <p>
 *     This service is used both internally and externally,
 *     meaning that this service is used on application
 *     startup to populated the supported capability data table.
 *     Externally, this service is available to allow enabling
 *     further capabilities not included in Happium.
 */
@Service
public class SupportedDesiredCapabilityService {

    private enum CAPABILITY_ATTRIBUTE {
        CAP_NAME("capability_name"),
        CAP_PLATFORM("supported_platform"),
        CAP_IS_REQUIRED("required_capability"),
        CAP_DESCRIPTION("description"),
        CAP_ACC_VALUES("accepted_values"),
        CAP_TIPS("usage_tips"),
        CAP_ALT_CAPS("alternate_capabilities"),
        CAP_DEP_CAPS("dependent_capabilities");

        CAPABILITY_ATTRIBUTE(String attributeLabel) {
        }
    }

    private enum ALT_CAP_CATEGORY {
        ALT_CAP_IOS("ios"),
        ALT_CAP_ANDROID("android"),
        ALT_CAP_GLOBAL("global");

        ALT_CAP_CATEGORY(String category) {
        }
    }

    /**
     * Enables interaction with the supported capability data table
     */
    @Getter @Setter private SupportedDesiredCapabilityCrudRepository supportedDesiredCapabilityCrudRepository;

    /**
     * Autowired constructor that initializes the CRUDRepository and loads in the
     * built-in SupportedDesiredCapability configuration JSON file
     *
     * @param supportedDesiredCapabilityCrudRepository          Repository to enable interacting with the SupportedCapability table
     */
    @Autowired
    public SupportedDesiredCapabilityService( SupportedDesiredCapabilityCrudRepository supportedDesiredCapabilityCrudRepository ) {

        this.supportedDesiredCapabilityCrudRepository = supportedDesiredCapabilityCrudRepository;

    }

    /**
     * Utility method to retrieve all stored SupportedCapability objects
     *
     * @return          List of all SupportedCapability objects
     */
    public List<SupportedDesiredCapability> getSupportedCapabilities() {

        return (List<SupportedDesiredCapability>) supportedDesiredCapabilityCrudRepository.findAll();

    }

    /**
     * Main initializer helper method that drives the instantiation of all capabilities
     * specified in resources/supported_desired_capabilities.json
     */
    public void initializeSupportedCapabilityTable() {

        try {
            String supportedCapabilityJsonString = _generateFileString( "src/main/resources/supported_desired_capabilities.json" );

            CapabilityParser capParser = new CapabilityParser();
            List<Object> nestedObjects = capParser.parseList( supportedCapabilityJsonString );

            for ( Object nestedObject : nestedObjects ) {

                JsonNode objectAsJsonNode = (JsonNode) nestedObject;    // Cast to JsonNode
                SupportedDesiredCapability builtInSupportedCap = _generateSupportedCapabilityFromJsonNode( objectAsJsonNode );
                supportedDesiredCapabilityCrudRepository.save( builtInSupportedCap );

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Helper method to load a file as a string
     *
     * <p>
     *     This is used to convert the built in supported
     *     capabilities configuration file from a JSON
     *     object to a string representation of the JSON.
     *     This is required since the built in JSON-parsing
     *     mechanisms take strings as argument.
     *
     * @param fileLocation          Location of file to convert
     * @return                      String representation of file contents
     * @throws IOException          If file is not found, or if unable to read from file
     */
    private String _generateFileString( String fileLocation ) throws IOException {

        InputStream fileInput = new FileInputStream( fileLocation );
        BufferedReader reader = new BufferedReader( new InputStreamReader( fileInput ) );

        String line = reader.readLine();
        StringBuilder builder = new StringBuilder();

        while( line != null ) {

            builder.append( line ).append("\n");
            line = reader.readLine();

        }

        return builder.toString();

    }

    /**
     * Takes a JsonNode containing a complete JSON configuration object
     * that is used to create a new SupportedDesiredCapability
     *
     * @param sourceObject              Source JsonNode to initialize new capability with
     * @return                          Newly-created SupportedDesiredCapability object
     */
    private SupportedDesiredCapability _generateSupportedCapabilityFromJsonNode( JsonNode sourceObject ) {

        String name = sourceObject.get( CAPABILITY_ATTRIBUTE.CAP_NAME
                .toString() ).textValue();
        String supportedPlatform = sourceObject.get( CAPABILITY_ATTRIBUTE.CAP_PLATFORM
                .toString() ).textValue();
        boolean isRequired = sourceObject.get( CAPABILITY_ATTRIBUTE.CAP_IS_REQUIRED
                .toString() ).booleanValue();
        String description = sourceObject.get( CAPABILITY_ATTRIBUTE.CAP_DESCRIPTION
                .toString() ).textValue();

        SupportedDesiredCapability newCapability = new SupportedDesiredCapability();

        newCapability = _configureAcceptedValueTypeBlock( sourceObject, newCapability );

        JsonNode usageTipsObject = sourceObject.get( CAPABILITY_ATTRIBUTE.CAP_TIPS
                .toString() );

        if (!usageTipsObject.isNull()) {
            newCapability.setUsageTips( _generateArrayFromJsonNode( usageTipsObject ) );
        }


        JsonNode alternateCapsBaseObject = sourceObject.get( CAPABILITY_ATTRIBUTE.CAP_ALT_CAPS
                .toString());

        JsonNode iosAltCaps = alternateCapsBaseObject.get("ios");
        newCapability.setAltIOSCapabilities( _generateArrayFromJsonNode( iosAltCaps ) );

        JsonNode androidAltCaps = alternateCapsBaseObject.get("android");
        newCapability.setAltIOSCapabilities( _generateArrayFromJsonNode( androidAltCaps ) );

        JsonNode globalAltCaps = alternateCapsBaseObject.get("global");
        newCapability.setAltIOSCapabilities( _generateArrayFromJsonNode( globalAltCaps ) );

        JsonNode dependentCapabilities = sourceObject.get("dependent_capabilities");
        newCapability.setDependentCapabilities( _generateArrayFromJsonNode( dependentCapabilities ) );

        return newCapability;

    }

    private SupportedDesiredCapability _configureTargetAttribute(
            JsonNode sourceObject, SupportedDesiredCapability newCapability, CAPABILITY_ATTRIBUTE targetAttribute ) {

        JsonNode targetAttributeObject = sourceObject.get( targetAttribute.toString() );

        switch ( targetAttribute ) {

            case CAP_NAME:

                newCapability.setName( targetAttributeObject.textValue() );
                break;

            case CAP_PLATFORM:

                newCapability.setSupportedPlatform( targetAttributeObject.textValue() );
                break;

            case CAP_IS_REQUIRED:

                newCapability.setRequired( targetAttributeObject.booleanValue() );
                break;

            case CAP_DESCRIPTION:

                newCapability.setDescription( targetAttributeObject.textValue() );
                break;

            case CAP_ACC_VALUES:

                newCapability = _configureAcceptedValueTypeBlock( sourceObject, newCapability );
                break;

            case CAP_TIPS:

                String [] tipArray = _generateArrayFromJsonNode( targetAttributeObject );
                newCapability.setUsageTips( tipArray );
                break;

            case CAP_ALT_CAPS:

                newCapability = _configureAlternativeCapsBlock( targetAttributeObject, newCapability );
                break;

            case CAP_DEP_CAPS:

                String [] dependentCapsArray = _generateArrayFromJsonNode( targetAttributeObject );
                newCapability.setDependentCapabilities( dependentCapsArray );

                break;

        }

        return newCapability;

    }

    /**
     * Helper method to configure the type of values this Desired Capability should take
     *
     * @param sourceObject              source Accepted Values JSON Node
     * @param newCapability             Capability to configure accepted values for
     * @return                          Modified version of passed-in capability with configured accepted values
     */
    private SupportedDesiredCapability _configureAcceptedValueTypeBlock( JsonNode sourceObject, SupportedDesiredCapability newCapability ) {

        JsonNode acceptedValuesObject = sourceObject.get("accepted_values");
        String acceptedValueType = acceptedValuesObject.get("type").textValue();
        newCapability.setAcceptedValueType( acceptedValueType );

        if ( acceptedValueType.equals("list_option") ) {

            JsonNode validOptions = acceptedValuesObject.get("list_options");
            newCapability.setAcceptedValuesList( _generateArrayFromJsonNode( validOptions ) );

        }

        return newCapability;

    }

    /**
     * Helper method to configure the possible alternate capabilities that
     * can be used instead of this capability. There are three arrays nested
     * inside this object, one for each capability platform (e.g. "ios", "android"
     * and "global")
     *
     * @param sourceObject                  AcceptedValue JsonNode block
     * @param newCapability                 Capability to configure
     * @return                              Capability with all alternate capabilities initialized
     */
    private SupportedDesiredCapability _configureAlternativeCapsBlock( JsonNode sourceObject, SupportedDesiredCapability newCapability ) {

        final String [] ALT_CAPS_CATEGORIES = {"ios", "android", "global"};

        for ( String category : ALT_CAPS_CATEGORIES ) {     // Iterate possible alt-caps categories

            JsonNode categoryListObject = sourceObject.get( category );
            String [] altCapListForCategory = _generateArrayFromJsonNode( categoryListObject );

            if ( category.equals( ALT_CAP_CATEGORY.ALT_CAP_IOS.toString() ) ) {

                newCapability.setAltIOSCapabilities( altCapListForCategory );

            }

            if ( category.equals( ALT_CAP_CATEGORY.ALT_CAP_ANDROID.toString() ) ) {

                newCapability.setAltAndroidCapabilities( altCapListForCategory );

            }

            if ( category.equals( ALT_CAP_CATEGORY.ALT_CAP_GLOBAL.toString() ) ) {

                newCapability.setAltGlobalCapabilities( altCapListForCategory );

            }

        }

        return newCapability;

    }

    /**
     * Converts a given JsonNode into a String Array
     *
     * @param arrayObject           JsonNode to convert
     * @return                      String array representation of JsonNode
     */
    private String [] _generateArrayFromJsonNode ( JsonNode arrayObject ) {

        Iterator<JsonNode> iterator = arrayObject.elements();
        List<String> options = new ArrayList<>();

        while ( iterator.hasNext() ) {

            String option = String.valueOf( iterator.next() );
            options.add( option );

        }

        return (String[]) options.toArray();

    }

}
