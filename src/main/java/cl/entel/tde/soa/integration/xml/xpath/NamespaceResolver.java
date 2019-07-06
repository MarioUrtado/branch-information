package cl.entel.tde.soa.integration.xml.xpath;


import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NamespaceResolver implements NamespaceContext{
    //Store the source document to search the namespaces
    private Map<String, String> maps;

    private Map<String, String> mapsInv;

    public NamespaceResolver() {
        this.maps = new HashMap<String, String>();
        this.mapsInv = new HashMap<String, String>();
        maps.put("con","http://www.bea.com/wli/sb/pipeline/config");
        maps.put("con6","http://www.bea.com/wli/sb/stages/transform/config");
        maps.put("con2","http://www.bea.com/wli/sb/stages/config");


        mapsInv.put("http://www.bea.com/wli/sb/pipeline/config", "con");
        mapsInv.put("http://www.bea.com/wli/sb/stages/transform/config", "con6");
        mapsInv.put("http://www.bea.com/wli/sb/stages/config", "con2");
    }

    //The lookup for the namespace uris is delegated to the stored document.
    public String getNamespaceURI(String prefix) {
        String var = this.maps.get(prefix);
        return var;
    }

    public String getPrefix(String namespaceURI) {
        String var = mapsInv.get(namespaceURI);
        return  var;
    }

    @SuppressWarnings("rawtypes")
    public Iterator getPrefixes(String namespaceURI) {
        return this.maps.keySet().iterator();
    }
}