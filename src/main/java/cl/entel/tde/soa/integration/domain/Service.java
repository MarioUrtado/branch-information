package cl.entel.tde.soa.integration.domain;

public class Service {

    protected String code;

    protected String name;

    protected String description;

    protected String version;

    public Service() {
    }

    public Service(String code, String name, String description, String version) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
