package cl.entel.tde.soa.integration.model.esb;

import cl.entel.tde.soa.integration.domain.Adapter;
import cl.entel.tde.soa.integration.domain.ResourceBusType;

import java.util.List;

public class ProxyService extends ResourceBusGeneric implements ResourceBus {

    private Uri uri;

    private String transport;

    private String dispatchPolicy;

    private List<OWSMPolicy> policies;

    public ProxyService() {
        super();
    }

    public ProxyService(String path, String name, Uri uri, String transport, String dispatchPolicy, List<OWSMPolicy> policies) {
        super(path, name);
        this.uri = uri;
        this.transport = transport;
        this.dispatchPolicy = dispatchPolicy;
        this.policies = policies;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getType() {
        return ResourceBusType.PROXY_SERVICE;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    public void setType(String type){

    }
    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setAdapter(Adapter adapter){

    }

    public String getDispatchPolicy() {
        return dispatchPolicy;
    }

    public void setDispatchPolicy(String dispatchPolicy) {
        this.dispatchPolicy = dispatchPolicy;
    }

    public List<OWSMPolicy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<OWSMPolicy> policies) {
        this.policies = policies;
    }
}
