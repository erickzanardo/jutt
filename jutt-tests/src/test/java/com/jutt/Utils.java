package com.jutt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    public static String readFile(String file) {
        BufferedReader br = null;
        StringBuilder ret = new StringBuilder();

        try {
            String line;
            br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                ret.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return ret.toString();
    }
}
