package io.happium.appium_client_service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Data Access Object that interfaces with the AppiumSessionEntity
 * data table.
 */
@Repository
@Transactional
public interface AppiumSessionCrudRepository extends CrudRepository<AppiumSessionEntity,Long> {
}
