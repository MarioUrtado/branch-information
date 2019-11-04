package cl.entel.tde.soa.integration.domain;


import org.springframework.data.annotation.Id;

public class CustomProperty {

    @Id
    private String id;

    private String value;

    private CustomType type;

    public CustomProperty() {
    }

    public CustomProperty(String value, CustomType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CustomType getType() {
        return type;
    }

    public void setType(CustomType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
