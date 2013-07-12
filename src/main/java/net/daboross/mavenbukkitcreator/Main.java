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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import static java.lang.System.out;

/**
 *
 * @author daboross
 */
public class Main {

    private static BufferedReader in;

    private static void init() {
        out.println("Welcome to Maven Bukkit Project Creator");
        Reader r = new InputStreamReader(System.in);
        in = new BufferedReader(r);
    }

    private static String ask(String question) throws IOException {
        out.printf(question + "\n> ");
        return in.readLine();
    }

    private static boolean askBoolean(String question) throws IOException {
        out.printf(question + "\n([Y]es/[N]o)> ");
        while (true) {
            String line = in.readLine();
            if (line.equalsIgnoreCase("y")) {
                return true;
            } else if (line.equalsIgnoreCase("n")) {
                return false;
            } else {
                out.printf("'" + line + "' isn't a valid answer.\n([Y]es/[N]o)> ");
            }
        }
    }

    public static void main(String[] args) {
        try {
            init();
            makeProject();
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
        } catch (InterruptedException ie) {
            ie.printStackTrace(System.err);
        }
    }

    private static void makeProject() throws IOException, InterruptedException {
        String name, desc, gitOriginName;
        while (true) {
            name = ask("What should the project be called?");
            desc = ask("What should the project's description be?");
            gitOriginName = ask("What should the remote github repository be named?");
            if (askBoolean("Project will be called '" + name + "'.\n"
                    + "Project will be described as '" + desc + "'.\n"
                    + "Remote git repository will be named '" + gitOriginName + "'.\n"
                    + "Is this OK?")) {
                break;
            }
        }
        new ProjectCreator(name, desc, gitOriginName).create();
    }
}
