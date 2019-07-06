package cl.entel.tde.soa.integration.model.esb;

import cl.entel.tde.soa.integration.domain.Adapter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=BusinessService.class, name="BUSINESS_SERVICE"),
        @JsonSubTypes.Type(value=Pipeline.class, name="PIPELINE"),
        @JsonSubTypes.Type(value=ProxyService.class, name="PROXY_SERVICE")
})
public interface ResourceBus {

    public String getName();

    public String getType();

    public String getPath();




}
