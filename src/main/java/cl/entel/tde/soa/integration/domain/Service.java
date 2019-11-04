package cl.entel.tde.soa.integration.domain;


import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Service {

    @Id
    private String id;

    private String code;

    private String name;

    private String operation;

    private String description;

    private String version;

    private String branch;

    private String index;

    private String branchIssueKey;

    private List<Target> targets;

    private List<EntityBus> entities;

    public Service() {
        this.targets = new ArrayList<>();this.entities = new ArrayList<>();
    }

    public Service(String code, String name, String description, String version) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.version = version;
        this.targets = new ArrayList<>();this.entities = new ArrayList<>();
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

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public void addTarget(Target adapter){
        this.targets.add(adapter);
    }

    public List<EntityBus> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityBus> entities) {
        this.entities = entities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean contain(Target target){
        return this.getTargets().stream().anyMatch( t -> t.getTargetProvider().equals(target.getTargetProvider()) && t.getTargetApi().equals(target.getTargetApi())&& t.getTargetOperation().equals(target.getTargetOperation()) && t.getTargetVersion().equals(target.getTargetVersion()));
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getBranchIssueKey() {
        return branchIssueKey;
    }

    public void setBranchIssueKey(String branchIssueKey) {
        this.branchIssueKey = branchIssueKey;
    }
}
