package io.happium.happium_hub_service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface HappiumHubCrudRepository extends CrudRepository< HappiumHub, Long > {
}
