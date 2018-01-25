package io.happium.happium_client.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Data Access Object to interface with the AndroidDevice
 * table. Handles CReate, Update, Delete operations (CRUD)
 */
@Repository
@Transactional
public interface AndroidDeviceCrudRepository extends CrudRepository<AndroidDevice, String>{

}

