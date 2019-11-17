package cl.entel.tde.soa.integration;

import cl.entel.tde.soa.integration.model.builder.AdapterReader;
import cl.entel.tde.soa.integration.model.builder.ModelBuilder;
import cl.entel.tde.soa.integration.service.ServiceService;
import cl.entel.tde.soa.integration.util.GitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SOAProjectReader {


    @Autowired
    private ModelBuilder modelBuilder;

    @Autowired
    private AdapterReader adapterReader;

    @Value("${workspace.dir}")
    private String workspaceDir;

    @Autowired
    private GitUtils gitUtils;

    @Autowired
    private ServiceService serviceService;
}
