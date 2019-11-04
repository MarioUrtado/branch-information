package cl.entel.tde.soa.integration.model.builder;

import cl.entel.tde.soa.integration.domain.*;
import cl.entel.tde.soa.integration.xml.xpath.XPathExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PipelineBuilder {

    @Autowired
    private XPathExecutor xPathExecutor;

    Logger logger = LoggerFactory.getLogger(PipelineBuilder.class);

    public List<String> findReferences(Document document){
        List<String> references = new ArrayList<>();
        NodeList services = (NodeList)xPathExecutor.execute(document, "//actions/wsCallout/service", XPathConstants.NODESET);
        NodeList routes = (NodeList)xPathExecutor.execute(document, "//route/service", XPathConstants.NODESET);
        for (int i = 0; i < services.getLength(); i++) {
            references.add( ((Element)services.item(i)).getAttribute("ref") + "." + this.refactorType(((Element)services.item(i)).getAttribute("xsi:type")));
        }
        for (int i = 0; i < routes.getLength(); i++) {
            references.add ( ((Element)routes.item(i)).getAttribute("ref")+"."+ this.refactorType(((Element)routes.item(i)).getAttribute("xsi:type") ) );
        }
        return references;
    }

    public String refactorType(String type){
        if(type.contains("ProxyRef")){
            return "proxy";
        }
        if(type.contains("BusinessServiceRef")){
            return "bix";
        }
        if(type.contains("PipelineRef")){
            return "pipeline";
        }
        return type;
    }

    public Target searchTarget(Document document){
        Node template = (Node)this.xPathExecutor.execute(document, "/pipelineEntry/template", XPathConstants.NODE);
        Target target = null;
        String provider = null;
        String api = null;
        String operation = null;
        String version = null;
        if (template == null){
            provider = ((String)this.xPathExecutor.execute(document, "/pipelineEntry/router/pipeline/stage/actions/assign[@varName = 'TargetProvider']/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
            api = ((String)this.xPathExecutor.execute(document, "/pipelineEntry/router/pipeline/stage/actions/assign[@varName = 'TargetApi' or @varName = 'TargetAPI']/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
            operation = ((String)this.xPathExecutor.execute(document, "/pipelineEntry/router/pipeline/stage/actions/assign[@varName = 'TargetOperation']/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
            version = ((String)this.xPathExecutor.execute(document, "/pipelineEntry/router/pipeline/stage/actions/assign[@varName = 'TargetVersion']/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
        } else {
            provider = ((String)this.xPathExecutor.execute(document, "/pipelineEntry/router/template-overrides/action-override[@id = '_ActionId-N53edf2ce.N7c27188c.0.15ea9cac095.N612a']/assign/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
            api = ((String)this.xPathExecutor.execute(document, "/pipelineEntry/router/template-overrides/action-override[@id = '_ActionId-N53edf2ce.N7c27188c.0.15ea9cac095.N6121' ]/assign/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
            operation = ((String)this.xPathExecutor.execute(document, "/pipelineEntry/router/template-overrides/action-override[@id = '_ActionId-N53edf2ce.N7c27188c.0.15ea9cac095.N6124']/assign/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
            version = ((String)this.xPathExecutor.execute(document, "/pipelineEntry/router/template-overrides/action-override[@id = '_ActionId-N53edf2ce.N7c27188c.0.15ea9cac095.N6127']/assign/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
        }
        target = new Target(provider, api, operation, version);
        return target;
    }



    public EntityBus buildEntity(Map<String, String> parameters, String filePath){
        EntityBus resource = new EntityBus();
        try{
            Document document = this.xPathExecutor.getBuilder().parse(filePath);
            String aux = parameters.get("application") + "/";
            String name = filePath.split(aux)[1];
            resource.setName(name);
            resource.setPath(filePath);
            resource.setType(ResourceBusType.PIPELINE);
            Target target = this.searchTarget(document);
            if (target.isValid()){
                resource.setTarget(target);
            }
            List<Reference> references = this.findReferences(document).stream().map(x-> new Reference(x)).collect(Collectors.toList());
            resource.setReferences(references);
        } catch (Exception e ){
            logger.error(e.getMessage() +";"+filePath);
            resource = null;
        }
        return resource;
    }








}
