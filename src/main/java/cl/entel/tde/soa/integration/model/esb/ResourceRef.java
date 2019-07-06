package cl.entel.tde.soa.integration.model.esb;

public class ResourceRef {

    private String ref;

    private String type;

    private String operation;

    private String referenceResource;

    public ResourceRef() {
    }

    public ResourceRef(String ref, String type, String operation){
        setRef(ref);
        setType(type);
        this.operation = operation;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if(type.contains(":")){
            this.type = type.split(":")[1];
        } else {
            this.type = type;
        }
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setReferenceResource(String referenceResource ){
        this.referenceResource = referenceResource;
    }
}
