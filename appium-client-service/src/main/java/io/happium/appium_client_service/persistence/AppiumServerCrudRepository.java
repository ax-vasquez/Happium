package io.happium.appium_client_service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AppiumServerCrudRepository extends CrudRepository<AppiumServerEntity, Long> {

    void delete(AppiumServerEntity entity);

    @Override
    <S extends AppiumServerEntity> S save(S entity);

    List<AppiumServerEntity> findAll();

}
