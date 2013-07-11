/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.mavenbukkitcreator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author daboross
 */
public class GitInit {

    private final File projectDir;
    private final String originName;
    private final String githubName;
    private final String projectDesc;

    public GitInit(File projectDir, String githubName, String projectDesc) {
        this.projectDir = projectDir;
        this.originName = "git@github.com:daboross/" + githubName + ".git";
        this.githubName = githubName;
        this.projectDesc = projectDesc;

    }

    public void run() throws IOException, InterruptedException {
        if (githubName != null && originName.length() > 0) {
            Runtime r = Runtime.getRuntime();
            Process create = r.exec(new String[]{
                "curl", "-i", "-H", "Authorization: token 7e8cc546746f425fb60341097c01eb2c5c62742f", "-d",
                "{\"name\": \"" + githubName + "\",\"description\": \"" + projectDesc + "\",\"auto_init\": true}",
                "https://api.github.com/user/repos"}, null, projectDir);
            create.waitFor();
            writeToConsole(create.getErrorStream());
            writeToConsole(create.getInputStream());
            Process init = r.exec(new String[]{"git", "init"}, null, projectDir);
            init.waitFor();
            writeToConsole(init.getErrorStream());
            writeToConsole(init.getInputStream());
            Process add = r.exec(new String[]{"git", "add", "."}, null, projectDir);
            add.waitFor();
            writeToConsole(add.getErrorStream());
            writeToConsole(add.getInputStream());
            Process commit = r.exec(new String[]{"git", "commit", "-a", "-m", "Initial Commit"}, null, projectDir);
            commit.waitFor();
            writeToConsole(commit.getErrorStream());
            writeToConsole(commit.getInputStream());
            Process remote = r.exec(new String[]{"git", "remote", "add", "origin", originName}, null, projectDir);
            remote.waitFor();
            writeToConsole(remote.getErrorStream());
            writeToConsole(remote.getInputStream());
            Process pull = r.exec(new String[]{"git", "pull", "origin", "master"}, null, projectDir);
            pull.waitFor();
            writeToConsole(pull.getErrorStream());
            writeToConsole(pull.getInputStream());
            Process push = r.exec(new String[]{"git", "push", "--set-upstream", "origin", "master"}, null, projectDir);
            push.waitFor();
            writeToConsole(push.getErrorStream());
            writeToConsole(push.getInputStream());
        }
    }

    private static void writeToConsole(InputStream is) throws IOException {
        int i;
        while ((i = is.read()) != -1) {
            System.out.write(i);
        }
    }
}
