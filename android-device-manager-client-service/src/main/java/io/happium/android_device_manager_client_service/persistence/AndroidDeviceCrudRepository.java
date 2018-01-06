package io.happium.android_device_manager_client_service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AndroidDeviceCrudRepository extends CrudRepository<AndroidDevice, Long>{

}

