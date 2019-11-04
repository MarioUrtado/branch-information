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

@Component
public class BusinessServiceBuilder {

    @Autowired
    private XPathExecutor xPathExecutor;

    Logger logger = LoggerFactory.getLogger(BusinessServiceBuilder.class);

    public List<OWSMPolicy> getPolicySet(Document document){
        List<OWSMPolicy> policies = new ArrayList<>();
        NodeList nodes = (NodeList)xPathExecutor.execute(document, "/businessServiceEntry/coreEntry/ws-policy/owsm-policy-metadata/wsm-assembly/policySet/PolicyReference", XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element node = (Element)nodes.item(i);
            String uri = node.getAttribute("URI");
            OWSMPolicy policy = new OWSMPolicy();
            policy.setUri(uri);
            NodeList overrideNodes = (NodeList) xPathExecutor.execute(node.getOwnerDocument(), "/PolicyReference/OverrideProperty", XPathConstants.NODESET);
            Map<String, String> overrideValues = new HashMap<>();
            for (int j = 0; j < overrideNodes.getLength(); j++) {
                Element refNode = (Element)overrideNodes.item(j);
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
            resource.setType(ResourceBusType.BUSINESS_SERVICE);
            String uri = (String)xPathExecutor.execute(document, "/businessServiceEntry/endpointConfig/URI/value", XPathConstants.STRING);
            String transport = (String)xPathExecutor.execute(document, "/businessServiceEntry/endpointConfig/provider-id", XPathConstants.STRING);
            resource.addProperty(new CustomProperty(transport, new CustomType("transport","transport")));
            String dispatchPolicy = (String)xPathExecutor.execute(document, "/businessServiceEntry/endpointConfig/provider-specific/dispatch-policy", XPathConstants.STRING);
            resource.addProperty(new CustomProperty(dispatchPolicy, new CustomType("dispatchPolicy","dispatchPolicy")));
            String httpConnectionTimeout = (String)xPathExecutor.execute(document, "/businessServiceEntry/endpointConfig/provider-specific/outbound-properties/connection-timeout", XPathConstants.STRING);
            resource.addProperty(new CustomProperty(httpConnectionTimeout, new CustomType("httpConnectionTimeout","httpConnectionTimeout")));
            String httpReadTimeout = (String)xPathExecutor.execute(document, "/businessServiceEntry/endpointConfig/provider-specific/outbound-properties/timeout", XPathConstants.STRING);
            resource.addProperty(new CustomProperty(httpReadTimeout, new CustomType("httpReadTimeout","httpReadTimeout")));
            String chuncked = (String)xPathExecutor.execute(document, "/businessServiceEntry/endpointConfig/provider-specific/outbound-properties/chunked-streaming-mode", XPathConstants.STRING);
            resource.addProperty(new CustomProperty(chuncked, new CustomType("chuncked","chuncked")));
            Uri url = new Uri(uri, transport, resource.getType());
            resource.setUri(url);
            List<OWSMPolicy> policies = this.getPolicySet(document);
        } catch (Exception e ){
            logger.error(e.getMessage() +";"+filePath);
            resource = null;
        }
        return resource;
    }
}
