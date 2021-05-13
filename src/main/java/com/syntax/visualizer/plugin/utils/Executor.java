package com.syntax.visualizer.plugin.utils;

import com.syntax.visualizer.plugin.globals.ProjectLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Executor {
    public static String Exec(String[] args) {
        InputStream in = null;
        InputStream err = null;
        String answer = "";
        String error = "";

        try {
            Process child = Runtime.getRuntime().exec(args);
            in = child.getInputStream();
            err = child.getErrorStream();
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(in));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(err));

            answer = inputReader.lines().collect(Collectors.joining());
            error = errorReader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            ProjectLogger.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    ProjectLogger.LOGGER.log(Level.SEVERE, "Failed to close data stream", e);
                }
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                    ProjectLogger.LOGGER.log(Level.SEVERE, "Failed to close error stream", e);
                }
            }
        }

        if (!error.isEmpty()) {
            ProjectLogger.LOGGER.log(Level.SEVERE, error);
        }

        return answer;
    }
}
