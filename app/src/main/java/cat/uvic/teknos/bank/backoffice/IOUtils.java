package cat.uvic.teknos.bank.backoffice;

import cat.uvic.teknos.bank.backoffice.exceptions.BackOfficeException;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    static String readLine (BufferedReader in){
        String command;
        try {
            command = in.readLine();

        } catch (IOException e) {
            throw new BackOfficeException("Error while reading the menu option", e);
        }
        return command;

    }
}
