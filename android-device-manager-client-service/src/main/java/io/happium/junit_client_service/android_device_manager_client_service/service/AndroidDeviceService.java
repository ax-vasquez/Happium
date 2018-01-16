package io.happium.junit_client_service.android_device_manager_client_service.service;

import io.happium.android_device_manager_client_service.persistence.AndroidDevice;
import io.happium.android_device_manager_client_service.persistence.AndroidDeviceCrudRepository;
import io.happium.junit_client_service.android_device_manager_client_service.persistence.AndroidDevice;
import io.happium.junit_client_service.android_device_manager_client_service.persistence.AndroidDeviceCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.vidstige.jadb.DeviceWatcher;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbException;

import java.io.IOException;
import java.util.List;

/**
 * Android Device Service Class
 *
 * <p>
 *     This class is responsible for maintaining
 *     the accuracy of the AndroidDevice database.
 *     It responds to new device connection events
 *     and will update the table so that it only
 *     ever contains device that are actually connected
 *     to the host machine.
 */
@Service
public class AndroidDeviceService {

    private AndroidConnectionListenerService androidConnectionListenerService;
    private AndroidDeviceCrudRepository androidDeviceCrudRepository;
    private DeviceWatcher deviceWatcher;
    private JadbConnection jadbConnection;

    @Autowired
    public AndroidDeviceService( AndroidConnectionListenerService listenerService, AndroidDeviceCrudRepository crudRepository ) throws IOException, JadbException {

        this.androidConnectionListenerService = listenerService;
        this.androidDeviceCrudRepository = crudRepository;

        this.jadbConnection = new JadbConnection();
        this.deviceWatcher = jadbConnection.createDeviceWatcher( androidConnectionListenerService );
    }

    @Async("threadPoolTaskExecutor")
    public void runWatcher() {

        deviceWatcher.run();

    }

    public List<AndroidDevice> getAllDevices() {

        return (List<AndroidDevice>) androidDeviceCrudRepository.findAll();

    }

}
