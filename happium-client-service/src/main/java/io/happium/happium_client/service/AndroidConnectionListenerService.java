package io.happium.happium_client.service;

import io.happium.happium_client.persistence.AndroidDevice;
import io.happium.happium_client.persistence.AndroidDeviceCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.vidstige.jadb.DeviceDetectionListener;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;
import se.vidstige.jadb.managers.PropertyManager;

import java.io.IOException;
import java.util.List;

@Service
public class AndroidConnectionListenerService implements DeviceDetectionListener {

    private AndroidDeviceCrudRepository androidDeviceCrudRepository;

    @Autowired
    public AndroidConnectionListenerService( AndroidDeviceCrudRepository androidDeviceCrudRepository ) {

        this.androidDeviceCrudRepository = androidDeviceCrudRepository;

    }

    /**
     * Android Device connection listener responsible for
     * maintaining the accuracy of the Android Device table
     *
     * <p>
     *     On each CONNECTION and DISCONNECTION event, this
     *     method is called to refresh the table of connected
     *     devices.
     *
     * @param connectedJadbDevices          List of JadbDevices passed in when onDetect is triggered
     */
    @Override
    public void onDetect(List<JadbDevice> connectedJadbDevices) {

        androidDeviceCrudRepository.deleteAll();

        for (JadbDevice jadbDevice: connectedJadbDevices) {

            AndroidDevice androidDevice;

            try {
                androidDevice = new AndroidDevice( new PropertyManager( jadbDevice ).getprop() );
                androidDeviceCrudRepository.save( androidDevice );
            } catch (IOException | JadbException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onException(Exception e) {

    }

}


