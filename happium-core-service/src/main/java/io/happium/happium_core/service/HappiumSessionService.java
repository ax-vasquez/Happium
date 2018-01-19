package io.happium.happium_core.service;

import io.happium.happium_core.persistence.HappiumSession;
import io.happium.happium_core.persistence.HappiumSessionCrudRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HappiumSessionService {

    @Autowired
    @Getter @Setter private HappiumSessionCrudRepository happiumSessionCrudRepository;

    public List<HappiumSession> getAllHappiumSessions() {

        return (List<HappiumSession>) happiumSessionCrudRepository.findAll();

    }

}
