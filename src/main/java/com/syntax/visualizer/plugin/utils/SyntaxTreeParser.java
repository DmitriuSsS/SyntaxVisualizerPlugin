package com.syntax.visualizer.plugin.utils;

import com.google.gson.Gson;
import com.intellij.openapi.editor.Document;

import java.nio.file.Paths;

public class SyntaxTreeParser {
    private static final Gson parser = new Gson();

    public SyntaxTree GetSyntaxTreeFromJson(String json) {
        return parser.fromJson(json, SyntaxTree.class);
    }

    public SyntaxTree GetSyntaxTreeFromCode(String code) {
        final String json = Executor.Exec(GetCommand(code));
        return GetSyntaxTreeFromJson(json);
    }

    public SyntaxTree GetSyntaxTreeFromDocument(Document doc) {
        return GetSyntaxTreeFromCode(doc.getText());
    }

    private String[] GetCommand(String code) {
        String pathToProj = Paths.get(System.getProperty("user.dir"), "SyntaxTreeBuilder").toString();
        return new String[] {"dotnet", "run", "--no-build", "--project", pathToProj, "--", "-c", code };
    }
}
