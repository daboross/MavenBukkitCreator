/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

    public void run(boolean alreadyExists) throws IOException, InterruptedException {
        if (githubName != null && originName.length() > 0) {
            Runtime r = Runtime.getRuntime();
            Process create = r.exec(new String[]{
                "curl", "-i", "-H", "Authorization: token 7e8cc546746f425fb60341097c01eb2c5c62742f", "-d",
                "{\"name\": \"" + githubName + "\",\"description\": \"" + projectDesc + "\",\"auto_init\": true}",
                "https://api.github.com/user/repos"}, null, projectDir);
            create.waitFor();
            if (!alreadyExists) {
                writeToConsole(create.getErrorStream());
                writeToConsole(create.getInputStream());
                Process init = r.exec(new String[]{"git", "init"}, null, projectDir);
                init.waitFor();
                writeToConsole(init.getErrorStream());
                writeToConsole(init.getInputStream());
                Process add = r.exec(new String[]{"git", "add", "-A"}, null, projectDir);
                add.waitFor();
                writeToConsole(add.getErrorStream());
                writeToConsole(add.getInputStream());
                Process commit = r.exec(new String[]{"git", "commit", "-a", "-m", "Initial Commit"}, null, projectDir);
                commit.waitFor();
                writeToConsole(commit.getErrorStream());
                writeToConsole(commit.getInputStream());
            }
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
