package io.happium.appium_client_service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object that interfaces with the AppiumSessionEntity
 * data table.
 */
@Repository
@Transactional
public interface AppiumSessionCrudRepository extends CrudRepository<AppiumSessionEntity,Long> {

    void delete(AppiumSessionEntity appiumSessionEntity);

    List<AppiumSessionEntity> findAll();

    @Override
    <S extends AppiumSessionEntity> S save(S entity);

}
