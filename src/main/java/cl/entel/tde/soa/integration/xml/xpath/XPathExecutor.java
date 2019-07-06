package cl.entel.tde.soa.integration.xml.xpath;


import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;

@Component
public class XPathExecutor {

    private DocumentBuilder builder = null;

    private XPath xpath = null;

    public XPathExecutor(){
    }

    @PostConstruct
    public void init(){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            this.builder = factory.newDocumentBuilder();
            XPathFactory xPathfactory = XPathFactory.newInstance();
            xpath = xPathfactory.newXPath();
        } catch (Exception e ){

        }
    }

    public Object execute(Document document, String xPathExpression, QName qname){
        Object value = null;
        try {
            XPathExpression expr = this.xpath.compile(xPathExpression);
            value = expr.evaluate(document,qname);
        } catch (XPathExpressionException e){
            e.printStackTrace();
        }
        return value;
    }

    public DocumentBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(DocumentBuilder builder) {
        this.builder = builder;
    }

    public XPath getXpath() {
        return xpath;
    }

    public void setXpath(XPath xpath) {
        this.xpath = xpath;
    }
}
