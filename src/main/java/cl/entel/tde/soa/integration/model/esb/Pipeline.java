package cl.entel.tde.soa.integration.model.esb;

import cl.entel.tde.soa.integration.domain.Adapter;
import cl.entel.tde.soa.integration.domain.ResourceBusType;

import java.util.ArrayList;
import java.util.List;

public class Pipeline extends ResourceBusGeneric implements ResourceBus {


    private Adapter adapter = null;

    private List<String> references;

    public Pipeline() {
    }

    public Pipeline(String path, String name) {
        super(path, name);
        this.references = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return ResourceBusType.PIPELINE;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setAdapter(Adapter adapter){
        this.adapter = adapter;
    }

    public Adapter getAdapter(){
        return this.adapter;
    }

    public List<String> getReferences() {
        return references;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }
}
