package com.syntax.visualizer.plugin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Executor {
    public static String Exec(String[] args) {
        InputStream in = null;
        String answer = null;

        try {
            Process child = Runtime.getRuntime().exec(args);
            in = child.getInputStream();
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(in));

            answer = inputReader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return answer;
    }
}
