package io.happium.appium_client_service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.json.JsonObject;

@Repository
@Transactional
public interface PayloadCrudRepository extends CrudRepository<Payload, Long> {

    @Override
    <S extends Payload> S save(S entity);
}
