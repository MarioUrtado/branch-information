package cl.entel.tde.soa.integration.service;

import cl.entel.tde.soa.integration.domain.Service;
import cl.entel.tde.soa.integration.domain.Target;
import cl.entel.tde.soa.integration.repository.ServiceImplRepository;
import cl.entel.tde.soa.integration.repository.ServiceRepository;
import cl.entel.tde.soa.integration.repository.TargetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    Logger logger = LoggerFactory.getLogger(ServiceService.class);

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private ServiceImplRepository serviceImplRepository;

    public ServiceService(){

    }

    public synchronized void save(Service service){
        try{
            /*
            Service preService = serviceRepository.findByNameAndVersion(service.getName(), service.getVersion());
            if (preService == null){
                preService = new Service();
                preService.setName(service.getName());
                preService.setVersion(service.getVersion());
            }
            preService = serviceRepository.save(preService);
            */
            serviceRepository.save(service);
        } catch (Exception e ){
            logger.error( e.getMessage()+";"+service.getName());

        }

    }

    public synchronized void saveTargets(Service service){
        for (Target target:service.getTargets()) {
            try{
                this.saveTarget(target);
            }catch (Exception e ){
                logger.error(e.getMessage()+";"+target.getTargetName());
            }
        }
    }

    public synchronized void saveTarget(Target target) {
        Target preexist = null; // targetRepository.find(target.getTargetProvider(), target.getTargetApi(), target.getTargetOperation(), target.getTargetVersion());
        if(preexist == null){
            Target newTarget = new Target();
            newTarget.setTargetApi(target.getTargetApi());
            newTarget.setTargetProvider(target.getTargetProvider());
            newTarget.setTargetOperation(target.getTargetOperation());
            newTarget.setTargetVersion(target.getTargetVersion());
            newTarget.setTargetName(target.getTargetName());
            newTarget.setValid(target.isValid());
            targetRepository.save(newTarget);

        }
    }
    public List<Service> find(String name, String operation,  String code, String version){
        return this.serviceImplRepository.find(name, operation, code, version);
    }

}
