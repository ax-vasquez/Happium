package io.happium.happium_client.service;

import io.happium.happium_client.persistence.HappiumSessionCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HappiumClientService {

    private HappiumSessionCrudRepository happiumSessionCrudRepository;

    @Autowired
    public HappiumClientService( HappiumSessionCrudRepository happiumSessionCrudRepository ) {

        this.happiumSessionCrudRepository = happiumSessionCrudRepository;

    }


}
