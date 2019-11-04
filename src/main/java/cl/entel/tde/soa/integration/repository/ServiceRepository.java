package cl.entel.tde.soa.integration.repository;

import cl.entel.tde.soa.integration.domain.Service;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends MongoRepository<Service, String> {

    public Service findByNameAndVersion(String name, String version);

}
