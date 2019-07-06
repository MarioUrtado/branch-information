package cl.entel.tde.soa.integration.model.builder;

import cl.entel.tde.soa.integration.domain.Adapter;
import cl.entel.tde.soa.integration.domain.Target;
import cl.entel.tde.soa.integration.model.esb.Pipeline;
import cl.entel.tde.soa.integration.model.esb.ResourceRef;
import cl.entel.tde.soa.integration.xml.xpath.XPathExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PipelineBuilder {

    @Autowired
    private XPathExecutor xPathExecutor;

    public Pipeline build(Map<String, String> parameters, String filePath){
        Pipeline pipeline = null;
        try{
            Document document = xPathExecutor.getBuilder().parse(filePath);
            String aux = parameters.get("application") + "/";
            String name = filePath.split(aux)[1];
            pipeline = new Pipeline(filePath, name);
            String provider = ((String)xPathExecutor.execute(document, "/pipelineEntry/router/pipeline/stage/actions/assign[@varName = 'TargetProvider']/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
            String api = ((String)xPathExecutor.execute(document, "/pipelineEntry/router/pipeline/stage/actions/assign[@varName = 'TargetApi' or @varName = 'TargetAPI']/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
            String operation = ((String)xPathExecutor.execute(document, "/pipelineEntry/router/pipeline/stage/actions/assign[@varName = 'TargetOperation']/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
            String version = ((String)xPathExecutor.execute(document, "/pipelineEntry/router/pipeline/stage/actions/assign[@varName = 'TargetVersion']/expr/xqueryText", XPathConstants.STRING)).replace("'", "");
            Target target = new Target(provider, api, operation, version);
            List<String> references = this.findReferences(document);
            pipeline.setReferences(references);
            if (target.isValid()){
                pipeline.setAdapter(new Adapter(target));
            }
        } catch (Exception e ){
            e.printStackTrace();
        }
        return pipeline;
    }

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
}
