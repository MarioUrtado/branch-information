package cl.entel.tde.soa.integration.domain;

public class SystemApiOperationProvider {

    private String name;

    private String version;

    private String description;

    public SystemApiOperationProvider() {
    }

    public SystemApiOperationProvider(String name, String version, String description) {
        this.name = name;
        this.version = version;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null){
            this.name = name;
        } else {
            this.name="";
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        if (version != null){
            this.version = version;
        } else {
            this.version="";
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null){
            this.description = description;
        } else {
            this.description="";
        }
    }
}
