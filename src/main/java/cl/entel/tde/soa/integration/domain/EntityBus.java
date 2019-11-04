package cl.entel.tde.soa.integration.domain;


import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class EntityBus {

    @Id
    private String id;

    private String name;

    private String path;

    private String type;

    private String resource;

    private List<Reference> references;

    private List<CustomProperty> properties;

    private Uri uri;

    //@Transient
    private Target target;

    public EntityBus() {
        this.references = new ArrayList<>();
        this.properties = new ArrayList<>();
    }

    public EntityBus(String name, String path, String type) {
        this.name = name;
        this.path = path;
        this.type = type;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public List<CustomProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<CustomProperty> properties) {
        this.properties = properties;
    }

    public void addProperty(CustomProperty customProperty){
        this.properties.add(customProperty);
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

}
