package cl.entel.tde.soa.integration.domain;


import org.springframework.data.annotation.Id;

public class Reference {

    @Id
    private String id;

    private String name;

    public Reference() {
    }

    public Reference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
