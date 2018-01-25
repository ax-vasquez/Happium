package io.happium.happium_client.controller;

import io.happium.android_device_manager_client_service.persistence.AndroidDevice;
import io.happium.android_device_manager_client_service.service.AndroidDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AndroidDeviceRestController {

    private AndroidDeviceService androidDeviceService;

    @Autowired
    public AndroidDeviceRestController( AndroidDeviceService androidDeviceService ) {

        this.androidDeviceService = androidDeviceService;
        androidDeviceService.runWatcher();

    }

    @RequestMapping(value = "/android_devices", method = RequestMethod.GET, produces = "application/json")
    public List<AndroidDevice> getAllDevices() {

        return androidDeviceService.getAllDevices();

    }


}
