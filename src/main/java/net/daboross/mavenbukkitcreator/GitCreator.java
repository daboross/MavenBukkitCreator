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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daboross
 */
public class GitCreator {

    private final File projectDir;
    private final String originName;
    private final String githubName;
    private final String projectDesc;

    public GitCreator(File projectDir, String githubName, String projectDesc) {
        this.projectDir = projectDir;
        this.originName = "git@github.com:daboross/" + githubName + ".git";
        this.githubName = githubName;
        this.projectDesc = projectDesc;

    }

    public void run(boolean alreadyExists) throws IOException, InterruptedException {

        Runtime r = Runtime.getRuntime();
        String token = getToken();
        if (token != null) {
            Process create = r.exec(new String[]{
                "curl", "-i", "-H", "Authorization: token " + getToken(), "-d",
                "{\"name\": \"" + githubName + "\",\"description\": \"" + projectDesc + "\",\"auto_init\": true}",
                "https://api.github.com/user/repos"}, null, projectDir);
            create.waitFor();
            writeToConsole(create.getErrorStream());
            writeToConsole(create.getInputStream());
        }
        if (!alreadyExists) {
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
        if (token != null) {
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

    private String getToken() {
        if (githubName.length() == 0) {
            return null;
        }
        File tokenFile = new File(new File(new File(System.getProperty("user.home")), "Private"), "github-token");
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new FileReader(tokenFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GitCreator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        BufferedReader br = new BufferedReader(inputStreamReader);
        String token;
        try {
            token = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(GitCreator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        try {
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(GitCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            inputStreamReader.close();
        } catch (IOException ex) {
            Logger.getLogger(GitCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return token;
    }
}
