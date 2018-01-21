package io.happium.happium_node_service.controller;

import io.happium.happium_node_service.service.HappiumHubService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * Controller for HappiumHub
 *
 * <p>
 *     There is currently no need to provide a GUI for this controller.
 *     Its only purpose at the moment is to wire up the underlying
 *     HappiumHub service so it can be started.
 * <p>
 *     NOTE: This also means that Happium is currently only capable
 *     of using the defaults provided in the project - this will
 *     change soon.
 *
 */
@Controller
public class HappiumHubController {

    @Getter @Setter private HappiumHubService happiumHubService;

    /**
     * Initializes this controller with the HappiumHubService bean
     *
     * @param happiumHubService
     */
    @Autowired
    public HappiumHubController( HappiumHubService happiumHubService ) {

        this.happiumHubService = happiumHubService;

    }

}
