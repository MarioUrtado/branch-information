package cl.entel.tde.soa.integration.domain;


import org.springframework.data.annotation.Id;

public class Target {

    @Id
    private String id;

    private String targetProvider;

    private String targetApi;

    private String targetOperation;

    private String targetVersion;

    private String targetName;

    private boolean valid;

    public Target() {
    }

    public Target(String targetProvider, String targetApi, String targetOperation, String targetVersion) {
        this.targetProvider = targetProvider;
        this.targetApi = targetApi;
        this.targetOperation = targetOperation;
        this.targetVersion = targetVersion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetProvider() {
        return targetProvider;
    }

    public void setTargetProvider(String targetProvider) {
        this.targetProvider = targetProvider;
    }

    public String getTargetApi() {
        return targetApi;
    }

    public void setTargetApi(String targetApi) {
        this.targetApi = targetApi;
    }

    public String getTargetOperation() {
        return targetOperation;
    }

    public void setTargetOperation(String targetOperation) {
        this.targetOperation = targetOperation;
    }

    public String getTargetVersion() {
        return targetVersion;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }

    public boolean isValid(){
        this.valid = !getTargetProvider().equals("") && !getTargetApi().equals("") && !getTargetOperation().equals("") && !getTargetVersion().equals("");
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getTargetName(){
        this.targetName =  getTargetProvider()+"_"+getTargetApi()+"_"+getTargetOperation()+"_"+getTargetVersion();
        return this.targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
}
