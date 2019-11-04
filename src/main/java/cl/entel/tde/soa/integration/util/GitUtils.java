package cl.entel.tde.soa.integration.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.lib.Ref;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class GitUtils {

    private static Git git = null;

    public static synchronized Git getInstance () throws Exception {
        if (git == null){
            git = Git.open(new File("d:/Projects/Repository/dev_arq20/.git"));
        }
        return git;
    }

    public synchronized void  checkout(String branch) throws Exception{
        Ref ref = GitUtils.getInstance().checkout().setName("origin/"+branch).call();
    }
}
