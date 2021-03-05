package com.syntax.visualizer.plugin.utils;

import com.google.gson.Gson;
import com.syntax.visualizer.plugin.globals.LocalFiles;

public class SyntaxTreeParser {
    private static final Gson parser = new Gson();

    public SyntaxTree getSyntaxTreeFromJson(String json) {
        return parser.fromJson(json, SyntaxTree.class);
    }

    public SyntaxTree getSyntaxTreeFromCode(String code) {
        final String json = Executor.Exec(getCommand(code));
        return getSyntaxTreeFromJson(json);
    }

    public SyntaxTree getSyntaxTreeFromDocument(String pathToDoc) {
        return getSyntaxTreeFromCode(pathToDoc);
    }

    private String[] getCommand(String pathToCode) {
        return new String[] {"dotnet", LocalFiles.ASTBuilderDll.getAbsolutePath(), "-f", pathToCode };
    }
}
