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
package net.daboross.tools.mavenbukkitcreator;

import java.nio.charset.Charset;
import java.util.Scanner;
import javax.xml.bind.DatatypeConverter;

public class Base64Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("input>");
        String input1 = scanner.nextLine();
        String data = DatatypeConverter.printBase64Binary(input1.getBytes(Charset.forName("UTF-8")));
        System.out.println("data = " + data);
        System.out.println("input data>");
        String input2 = scanner.nextLine();
        String decoded = new String(DatatypeConverter.parseBase64Binary(input2), Charset.forName("UTF-8"));
        System.out.println("Decoded = " + decoded);
    }
}
