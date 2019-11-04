package cl.entel.tde.soa.integration.model.util.uri;

import cl.entel.tde.soa.integration.domain.Uri;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CompareUri {

    private Map<String, TransportCompare> mapCompare;

    public CompareUri() {
        this.mapCompare = new HashMap<>();
        this.mapCompare.put("sb", new SBCompare());
        this.mapCompare.put("http", new HTTPCompare());
        this.mapCompare.put("jms", new JMSCompare());

    }

    public CompareUri(Map<String, TransportCompare> mapCompare) {
        this.mapCompare = mapCompare;
    }

    public boolean equals(String transport, Uri source, Uri destination){
        return this.mapCompare.get(transport).equals(source, destination);
    }

}
