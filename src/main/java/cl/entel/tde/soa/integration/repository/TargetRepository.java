package cl.entel.tde.soa.integration.repository;

import cl.entel.tde.soa.integration.domain.Target;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TargetRepository extends MongoRepository<Target, Long> {

    //@Query("select t from Target t where t.targetProvider = ?1 and t.targetApi = ?2 and t.targetOperation = ?3 and t.targetVersion = ?4")
    //public Target find(String system, String api, String operation, String version);

}
