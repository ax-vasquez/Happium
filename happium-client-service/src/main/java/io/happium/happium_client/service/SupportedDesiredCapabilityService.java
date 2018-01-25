package io.happium.happium_client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.happium.happium_client.persistence.SupportedDesiredCapability;
import io.happium.happium_client.persistence.SupportedDesiredCapabilityCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
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

    private static Logger logger = LoggerFactory.getLogger( SupportedDesiredCapabilityService.class );

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

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Enables interaction with the supported capability data table
     */
    private SupportedDesiredCapabilityCrudRepository supportedDesiredCapabilityCrudRepository;

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
     * Initialization method to load the supported capabilities file
     * and add them to a local database instance
     */
    @PostConstruct
    public void initTable() {

        logger.debug("Retrieving supported Desired Capabilities base configuration file");

        ObjectMapper mapper = new ObjectMapper();

        try {

            Resource capsFileResource = applicationContext.getResource("classpath:supported_desired_capabilities.json");

            // Read in file as List of DesiredCapability objects (Jackson databind)
            List<SupportedDesiredCapability> supportedDesiredCapabilitiesList
                    = mapper.readValue( capsFileResource.getFile(), new TypeReference< List<SupportedDesiredCapability>>() {} );
            logger.debug("Saving supported capabilities to local storage");
            supportedDesiredCapabilityCrudRepository.save( supportedDesiredCapabilitiesList );
            logger.debug("Capabilities saved.");

        } catch (IOException e) {
            logger.error("Supported Capabilities file could not be loaded");
            e.printStackTrace();
        }


    }

    /**
     * Utility method to retrieve all of the SupportedCapabilities
     *
     * @return
     */
    public List<SupportedDesiredCapability> getAllSupportedCapabilities() {
        return (List<SupportedDesiredCapability>) supportedDesiredCapabilityCrudRepository.findAll();
    }

}
