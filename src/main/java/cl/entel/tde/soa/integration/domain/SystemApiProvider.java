package cl.entel.tde.soa.integration.domain;

public class SystemApiProvider {

    private String code;

    private String name;

    private String description;

    public SystemApiProvider() {
    }


    public SystemApiProvider(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code != null){
            this.code = code;
        } else {
            this.code="";
        }
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
