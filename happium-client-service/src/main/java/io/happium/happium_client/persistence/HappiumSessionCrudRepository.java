package io.happium.happium_client.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface HappiumSessionCrudRepository extends CrudRepository<HappiumSession, Long>{

}
