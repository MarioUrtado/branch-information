package cl.entel.tde.soa.integration.repository;

import cl.entel.tde.soa.integration.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceImplRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Service> find(String name, String operation, String code, String version){
        Query query = new Query();
        this.addCriteria(query, "name", name);
        this.addCriteria(query, "operation", operation);
        this.addCriteria(query, "code", code);
        this.addCriteria(query, "version", version);
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "dateTimeStamp")));
        return mongoTemplate.find(query, Service.class);
    }

    private void addCriteria(Query query, String field, String value){
        if (value == null){ return; }
        if (value.contains("*")){
            query.addCriteria(Criteria.where(field).regex(value.replace("*", "")));
        }else {
            query.addCriteria(Criteria.where(field).is(value));
        }
    }

}
