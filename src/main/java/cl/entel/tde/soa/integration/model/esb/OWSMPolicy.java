package cl.entel.tde.soa.integration.model.esb;

import java.util.Map;

public class OWSMPolicy {

    private String uri;

    private Map<String, String> overrideValues;

    public OWSMPolicy() {
    }

    public OWSMPolicy(String uri, Map<String, String> overrideValues) {
        this.uri = uri;
        this.overrideValues = overrideValues;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getOverrideValues() {
        return overrideValues;
    }

    public void setOverrideValues(Map<String, String> overrideValues) {
        this.overrideValues = overrideValues;
    }
}
