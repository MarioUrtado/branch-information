package cl.entel.tde.soa.integration.domain;

import org.springframework.data.annotation.Id;

public class Branch {

    @Id
    private String id;

    private Service service;

    public Branch() {
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
