package io.happium.appium_client_service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object that interfaces with the AppiumServerEntity
 * table
 *
 * <p>
 *     AppiumServerEntities are stored in a common database that
 *     is accessible to all services within the Happium application
 *     (meaning all services, not just the Appium Client Service).
 *     The integrity of this database is extremely important as the
 *     application relies on the states of the individual server entities
 *     in order to interact with them.
 */
@Repository
@Transactional
public interface AppiumServerCrudRepository extends CrudRepository<AppiumServerEntity, Long> {

    void delete(AppiumServerEntity entity);

    List<AppiumServerEntity> findAll();

    @Override
    <S extends AppiumServerEntity> S save(S entity);

}
