package cl.entel.tde.soa.integration.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Composite {

    @Id
    private String id;

    private String name;

    private String path;

    private String type;

    private String resource;

    private String revision;

    private List<Reference> references;

    private List<CustomProperty> properties;
}
