package cl.entel.tde.soa.integration.domain;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceContext {

    private List<Adapter> adapters;

    private Service service;

    public ServiceContext() {
        this.adapters = new ArrayList<>();
    }

    public List<Adapter> getAdapters() {
        return adapters;
    }

    public void setAdapters(List<Adapter> adapters) {
        this.adapters = adapters;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void addAdapter(Adapter adapter){
        this.adapters.add(adapter);
    }
}
