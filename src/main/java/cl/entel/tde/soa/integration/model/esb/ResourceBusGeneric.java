package cl.entel.tde.soa.integration.model.esb;

import java.util.ArrayList;
import java.util.List;

public class ResourceBusGeneric {

    protected String path;
    protected String name;

    public ResourceBusGeneric() {
    }

    public ResourceBusGeneric(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
