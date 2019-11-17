package cl.entel.tde.soa.integration;

import cl.entel.tde.soa.integration.domain.EntityBus;
import cl.entel.tde.soa.integration.domain.Service;
import cl.entel.tde.soa.integration.domain.Target;
import cl.entel.tde.soa.integration.model.builder.AdapterReader;
import cl.entel.tde.soa.integration.model.builder.ModelBuilder;
import cl.entel.tde.soa.integration.service.ServiceService;
import cl.entel.tde.soa.integration.util.GitUtils;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.lib.Ref;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OSBProjectReader {

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

    Logger logger = LoggerFactory.getLogger(OSBProjectReader.class);

    public Map<String, String> readBranch(String branchName){
        Map<String, String> parameters = new HashMap<>();
        String[] strs = branchName.split("-");
        String ticket = strs[0] + "-" + strs[1];
        String service = strs[2];
        String version = strs[3];
        String application = workspaceDir + "/" + "01.FUENTES" + "/" + "ES_" + service + "_" + version + "/" + "01.OSB" + "/" + "01.WORKSPACE" + "/" + "ENTEL_ServiceBusApp" ;
        String projectPath = application + "/" + "ES_" + service + "_" + version;
        parameters.put("project", projectPath);
        parameters.put("application", application);
        parameters.put("version", version);
        parameters.put("service", service);
        parameters.put("branchIssueKey", ticket);
        parameters.put("index", strs[1]);
        return parameters;
    }

    public void runEntity(){
        try{
            List<Ref> branchs = this.getRefsWithBranchs();
            for (Ref ref: branchs) {
                System.out.println(ref.getName());
                try {
                    String branch = ref.getName().replace("refs/remotes/origin/", "");
                    logger.info("Current Branch: " + branch);
                    gitUtils.checkout(branch);
                    Map<String, String> parameters = this.readBranch(branch);
                    String projectPath = parameters.get("project");
                    List<File> files = Files.walk(Paths.get(projectPath))
                            .filter(Files::isRegularFile)
                            .map(x -> x.toFile())
                            .filter(x -> x.isFile())
                            .filter(x -> x.getName().toLowerCase().endsWith(".proxy") || x.getName().toLowerCase().endsWith(".bix") || x.getName().toLowerCase().endsWith(".pipeline"))
                            .collect(Collectors.toList());
                    List<EntityBus> resources = files.stream().map(x -> modelBuilder.buildEntity(parameters, x.getPath().replace("\\", "/"))).filter(x -> x != null).collect(Collectors.toList());

                    Service service = new Service();
                    service.setEntities(resources);
                    service.setVersion(parameters.get("version"));
                    service.setName(parameters.get("service"));
                    service.setOperation(parameters.get("service"));
                    service.setBranchIssueKey(parameters.get("branchIssueKey"));
                    service.setBranch(branch);
                    service.setEntities(resources);
                    List<Target> targets = resources.stream().map(x -> x.getTarget()).filter(x -> x != null).collect(Collectors.toList());
                    service.setTargets(targets);
                    serviceService.save(service);
                } catch (Exception e) {
                    logger.error(e.getMessage() + "|" + ref.getName());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Ref> getRefsWithBranchs(){
        List<Ref> branchs = new ArrayList<>();
        try {
            branchs = GitUtils.getInstance().branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
            branchs = branchs.stream().filter( x -> x.getName().startsWith("refs/remotes/origin/")).collect(Collectors.toList());
        } catch (Exception e ){
            e.printStackTrace();
        }
        return branchs;
    }

}
