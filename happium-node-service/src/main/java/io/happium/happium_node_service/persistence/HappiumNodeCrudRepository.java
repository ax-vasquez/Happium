package io.happium.happium_node_service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface HappiumNodeCrudRepository extends CrudRepository<HappiumNode, Long> {
}
