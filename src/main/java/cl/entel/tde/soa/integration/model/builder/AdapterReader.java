package cl.entel.tde.soa.integration.model.builder;

import cl.entel.tde.soa.integration.domain.Target;
import cl.entel.tde.soa.integration.xml.xpath.XPathExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathConstants;

@Component
public class AdapterReader {

    @Autowired
    private XPathExecutor xPathExecutor;

    public Target read(String filePath){
        Target target = new Target(null,null,null,null);
        try {
            Document document = xPathExecutor.getBuilder().parse(filePath);
            target = this.read(document);
        } catch (Exception e ){

        }
        return target;
    }

    public Target read(Document document){
        String tarProvider = (String)xPathExecutor.execute(document, "//pipeline[@type='request']/stage/actions/assign[@varName='TargetProvider']/expr/xqueryText/text()", XPathConstants.STRING);
        String tarApi = (String)xPathExecutor.execute(document, "//pipeline[@type='request']/stage/actions/assign[@varName='TargetApi' or @varName='TargetAPI']/expr/xqueryText/text()", XPathConstants.STRING);
        String tarOperation = (String)xPathExecutor.execute(document, "//pipeline[@type='request']/stage/actions/assign[@varName='TargetOperation']/expr/xqueryText/text()", XPathConstants.STRING);
        String tarVersion = (String)xPathExecutor.execute(document, "//pipeline[@type='request']/stage/actions/assign[@varName='TargetVersion']/expr/xqueryText/text()", XPathConstants.STRING);

        Target target = new Target(tarProvider.replace("'", ""),tarApi.replace("'", ""),tarOperation.replace("'", ""),tarVersion.replace("'", ""));
        return target;
    }
}
