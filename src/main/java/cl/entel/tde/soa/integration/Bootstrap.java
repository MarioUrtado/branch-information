package cl.entel.tde.soa.integration;

import cl.entel.tde.soa.integration.domain.Adapter;
import cl.entel.tde.soa.integration.domain.ServiceContext;
import cl.entel.tde.soa.integration.domain.Target;
import cl.entel.tde.soa.integration.model.builder.AdapterReader;
import cl.entel.tde.soa.integration.model.builder.ModelBuilder;
import cl.entel.tde.soa.integration.model.esb.ResourceBus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Bootstrap {

    @Autowired
    private ModelBuilder modelBuilder;

    @Autowired
    private AdapterReader adapterReader;

    @Value("${osb.project}")
    private String projectPath;

    @Value("${git.remote.uri}")
    private String gitRemoteUri;

    @Value("${git.workspace.dir}")
    private String gitWorkspaceDir;

    @Value("${git.branch.name}")
    private String gitBranchName;

    @Autowired
    private ServiceContext serviceContext;

    Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    @PostConstruct
    public void run(){
        try {
            Map<String, String> parameters = new HashMap<>();

            File workspace = new File(gitWorkspaceDir+ File.separator );
            File workspaceGit = new File(gitWorkspaceDir+ File.separator  +File.separator + ".git");
            workspace.delete();
            workspace.mkdir();

            parameters.put("workspace", workspace.getPath().replace("\\", "/"));

            String serviceName = gitBranchName.split("-")[2];
            String serviceVersion = gitBranchName.split("-")[3];

            String applicationPath = workspace.getPath() + File.separator + "01.FUENTES/ES_"+serviceName+"_"+serviceVersion+"/01.OSB/01.WORKSPACE/ENTEL_ServiceBusApp";
            parameters.put("application", applicationPath.replace("\\", "/"));
            this.projectPath = workspace.getPath() + File.separator + "01.FUENTES/ES_"+serviceName+"_"+serviceVersion+"/01.OSB/01.WORKSPACE/ENTEL_ServiceBusApp/ES_"+serviceName+"_"+serviceVersion;
            parameters.put("project", this.projectPath);

            List<File> files = Files.walk(Paths.get(projectPath))
                    .filter(Files::isRegularFile)
                    .map(x -> x.toFile())
                    .filter(x-> x.isFile())
                    .filter(x-> x.getName().toLowerCase().endsWith(".proxy") || x.getName().toLowerCase().endsWith(".bix") || x.getName().toLowerCase().endsWith(".pipeline") )
                    .collect(Collectors.toList());

            List<ResourceBus> resources = files.stream().map(x->modelBuilder.build(parameters, x.getPath().replace("\\", "/"))).collect(Collectors.toList());

            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writeValueAsString(resources));

        }catch (Exception e ){
            e.printStackTrace();
        }

    }

}
