package cl.entel.tde.soa.integration.model.builder;

import cl.entel.tde.soa.integration.domain.*;
import cl.entel.tde.soa.integration.model.esb.OWSMPolicy;
import cl.entel.tde.soa.integration.xml.xpath.XPathExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ProxyServiceBuilder {

    @Autowired
    private XPathExecutor xPathExecutor;

    Logger logger = LoggerFactory.getLogger(ProxyServiceBuilder.class);

    public List<String> findReference(Document document){
        String resource = (String)xPathExecutor.execute(document, "/proxyServiceEntry/coreEntry/invoke/@ref", XPathConstants.STRING);
        String type = (String)xPathExecutor.execute(document, "/proxyServiceEntry/coreEntry/invoke/@type", XPathConstants.STRING);
        String reference = resource + "." + this.refactorType(type);

        List<String> references = new ArrayList<>();
        references.add(reference);
        return references;
    }

    public List<OWSMPolicy> getPolicySet(Document document) {
        List<OWSMPolicy> policies = new ArrayList<>();
        NodeList nodes = (NodeList) xPathExecutor.execute(document, "/businessServiceEntry/coreEntry/ws-policy/owsm-policy-metadata/wsm-assembly/policySet/PolicyReference", XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element node = (Element) nodes.item(i);
            String uri = node.getAttribute("URI");
            OWSMPolicy policy = new OWSMPolicy();
            policy.setUri(uri);
            NodeList overrideNodes = node.getElementsByTagName("OverrideProperty");
            Map<String, String> overrideValues = new HashMap<>();
            for (int j = 0; j < overrideNodes.getLength(); j++) {
                Element refNode = (Element) overrideNodes.item(j);
                overrideValues.put(refNode.getAttribute("csf-key"), refNode.getAttribute("eusb-sap-pi-csf-key"));
            }
            policy.setOverrideValues(overrideValues);
            policies.add(policy);
        }
        return policies;
    }

    public EntityBus buildEntity(Map<String, String> parameters, String filePath){
        EntityBus resource = new EntityBus();
        try{
            Document document = xPathExecutor.getBuilder().parse(filePath);
            String aux = parameters.get("application") + "/";
            String name = filePath.split(aux)[1];
            resource.setName(name);
            resource.setPath(filePath);
            resource.setType(ResourceBusType.PROXY_SERVICE);
            String uri = (String)xPathExecutor.execute(document, "/proxyServiceEntry/endpointConfig/URI/value", XPathConstants.STRING);

            String transport = (String)xPathExecutor.execute(document, "/proxyServiceEntry/endpointConfig/provider-id", XPathConstants.STRING);
            resource.addProperty(new CustomProperty(transport, new CustomType("transport","transport")));
            String dispatchPolicy = (String)xPathExecutor.execute(document, "/proxyServiceEntry/endpointConfig/provider-specific/dispatch-policy", XPathConstants.STRING);
            resource.addProperty(new CustomProperty(dispatchPolicy, new CustomType("dispatchPolicy","dispatchPolicy")));
            List<OWSMPolicy> policies = this.getPolicySet(document);
            List<Reference> references = this.findReference(document).stream().map(x-> new Reference(x)).collect(Collectors.toList());
            resource.setReferences(references);
            Uri url = new Uri(uri, transport, resource.getType());
            resource.setUri(url);

        } catch (Exception e ){
            logger.error(e.getMessage() +";"+filePath);
            resource=null;
        }
        return resource;
    }

    public String refactorType(String type){
        if(type.toUpperCase().contains("PROXYREF")){
            return "proxy";
        }
        if(type.toUpperCase().contains("BUSINESSSERVICEREF")){
            return "bix";
        }
        if(type.toUpperCase().contains("PIPELINEREF")){
            return "pipeline";
        }
        return type;
    }
}

