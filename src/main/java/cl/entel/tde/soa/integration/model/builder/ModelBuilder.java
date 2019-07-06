package cl.entel.tde.soa.integration.model.builder;

import cl.entel.tde.soa.integration.model.esb.BusinessService;
import cl.entel.tde.soa.integration.model.esb.Pipeline;
import cl.entel.tde.soa.integration.model.esb.ProxyService;
import cl.entel.tde.soa.integration.model.esb.ResourceBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
        //this.businessServiceBuilder = new BusinessServiceBuilder();
        //this.pipelineBuilder = new PipelineBuilder();
        //this.proxyServiceBuilder = new ProxyServiceBuilder();
    }

    public ResourceBus build(Map<String,String> parameters, String filePath){
        ResourceBus resource = null;
        if (filePath.endsWith(".proxy")){
            resource = this.proxyServiceBuilder.build(parameters,  filePath);
            ((ProxyService) resource).setPath(filePath);
        }
        if (filePath.endsWith(".pipeline")){
            resource = this.pipelineBuilder.build( parameters, filePath);
            ((Pipeline) resource).setPath(filePath);
        }
        if (filePath.endsWith(".bix")){
            resource =  this.businessServiceBuilder.build(parameters,  filePath);
            ((BusinessService) resource).setPath(filePath);
        }
        if (resource == null){
            System.out.println(filePath);
        }
        return resource;
    }

}
