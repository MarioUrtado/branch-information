package cl.entel.tde.soa.integration.model.esb;

import cl.entel.tde.soa.integration.domain.Adapter;
import cl.entel.tde.soa.integration.domain.ResourceBusType;

import java.util.List;

public class BusinessService extends ResourceBusGeneric implements ResourceBus {

    private Uri uri;

    private String transport;

    private String dispatchPolicy;

    private List<OWSMPolicy> policies;

    public BusinessService() {
        super();

    }
    public BusinessService(String path, String name, Uri uri, String transport, String dispatchPolicy, List<OWSMPolicy> policies) {
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
        return ResourceBusType.BUSINESS_SERVICE;
    }
    public void setType(String type){

    }
    @Override
    public String getPath() {
        return this.path;
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
