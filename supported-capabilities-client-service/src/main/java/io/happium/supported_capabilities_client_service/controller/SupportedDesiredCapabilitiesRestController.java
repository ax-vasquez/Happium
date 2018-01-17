package io.happium.supported_capabilities_client_service.controller;

import io.happium.supported_capabilities_client_service.persistence.SupportedDesiredCapability;
import io.happium.supported_capabilities_client_service.service.SupportedDesiredCapabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SupportedDesiredCapabilitiesRestController {

    /**
     * Enables interacting with the supporting CRUDRepository
     */
    private SupportedDesiredCapabilityService supportedDesiredCapabilityService;

    @Autowired
    public SupportedDesiredCapabilitiesRestController( SupportedDesiredCapabilityService supportedDesiredCapabilityService ) {

        this.supportedDesiredCapabilityService = supportedDesiredCapabilityService;

    }

    /**
     * RESTful endpoint to access the stored supported capabilities
     *
     * @return
     */
    @RequestMapping(value = "/supported_capabilities", method = RequestMethod.GET, produces = "application/json")
    public List<SupportedDesiredCapability> getAllSupportedCapabilities() {

        return supportedDesiredCapabilityService.getAllSupportedCapabilities();

    }

}
