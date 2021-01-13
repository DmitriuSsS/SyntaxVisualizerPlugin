package com.syntax.visualizer.plugin.utils;

import com.google.gson.Gson;
import com.intellij.openapi.editor.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SyntaxTreeParser {
    private enum Mode {
        File,
        Code
    }

    private static final Gson parser = new Gson();

    public SyntaxTree GetSyntaxTreeFromJson(String json) {
        return parser.fromJson(json, SyntaxTree.class);
    }

    public SyntaxTree GetSyntaxTreeFromFile(String pathToFile) {
        final String json = ExecFunction(GetCommand(pathToFile.replace('/', '\\'), Mode.File));
        return GetSyntaxTreeFromJson(json);
    }

    public SyntaxTree GetSyntaxTreeFromCode(String code) {
        final String json = ExecFunction(GetCommand(code, Mode.Code));
        return GetSyntaxTreeFromJson(json);
    }

    public SyntaxTree GetSyntaxTreeFromDocument(Document doc) {
        return GetSyntaxTreeFromCode(doc.getText());
    }

    private String[] GetCommand(String arg, Mode mode) {
        String secondWord = mode == Mode.File ? "-f" : "-c";
        // TODO: подумать над тем как не забывать компилировать и переносить C# проект
        String pathToExe = "C:\\Users\\Dmitriy\\Desktop\\SyntaxVisualizerPlugin\\backend\\SyntaxTreeBuilder\\bin\\Debug\\netcoreapp3.1\\SyntaxTreeBuilder.exe";
        return new String[] {pathToExe, secondWord, arg };
    }

    private String ExecFunction(String[] cmdArgs) {
        InputStream in = null;
        try {
            Process child = Runtime.getRuntime().exec(cmdArgs);
            in = child.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
                result.append(line);

            return result.toString();
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

        return null;
    }
}
