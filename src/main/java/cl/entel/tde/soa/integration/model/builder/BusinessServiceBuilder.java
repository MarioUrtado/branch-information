package cl.entel.tde.soa.integration.model.builder;

import cl.entel.tde.soa.integration.model.esb.BusinessService;
import cl.entel.tde.soa.integration.model.esb.OWSMPolicy;
import cl.entel.tde.soa.integration.model.esb.Uri;
import cl.entel.tde.soa.integration.xml.xpath.XPathExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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

    public BusinessService build(Map<String, String> parameters, String filePath){
        try{
            Document document = xPathExecutor.getBuilder().parse(filePath);
            String aux = parameters.get("application") + "/";
            String name = filePath.split(aux)[1];
            String uri = (String)xPathExecutor.execute(document, "/businessServiceEntry/endpointConfig/URI/value", XPathConstants.STRING);
            String transport = (String)xPathExecutor.execute(document, "/businessServiceEntry/endpointConfig/provider-id", XPathConstants.STRING);

            String dispatchPolicy = (String)xPathExecutor.execute(document, "/businessServiceEntry/endpointConfig/provider-specific/dispatch-policy", XPathConstants.STRING);

            Uri url = new Uri(uri);

            List<OWSMPolicy> policies = this.getPolicySet(document);

            BusinessService resource = new BusinessService(filePath, name, url, transport, dispatchPolicy, policies);

            return resource;
        } catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }

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
}
