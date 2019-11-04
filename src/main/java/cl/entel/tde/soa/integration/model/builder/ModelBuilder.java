package cl.entel.tde.soa.integration.model.builder;

import cl.entel.tde.soa.integration.domain.EntityBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ModelBuilder {

    public static String projectPath = "";

    @Autowired
    ProxyServiceBuilder proxyServiceBuilder;

    @Autowired
    BusinessServiceBuilder businessServiceBuilder;

    @Autowired
    PipelineBuilder pipelineBuilder;

    public ModelBuilder(){
    }

    public EntityBus buildEntity(Map<String,String> parameters, String filePath){
        EntityBus resource = null;
        if (filePath.toLowerCase().endsWith(".proxy")){
            resource = this.proxyServiceBuilder.buildEntity(parameters,  filePath);
        }
        if (filePath.toLowerCase().endsWith(".pipeline")){
            resource = this.pipelineBuilder.buildEntity( parameters, filePath);
        }
        if (filePath.toLowerCase().endsWith(".bix")){
            resource =  this.businessServiceBuilder.buildEntity(parameters,  filePath);
        }
        return resource;
    }

}
