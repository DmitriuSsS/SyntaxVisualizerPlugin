package com.syntax.visualizer.plugin.globals;

import java.io.File;
import java.nio.file.Paths;

public class LocalFiles {
    private static final File FolderForBackend = new File(new File(getPathToJar()).getParent());
    public static final File BackendFolder = Paths.get(FolderForBackend.getAbsolutePath(), "SyntaxTreeBuilder").toFile();
    public static final File ASTBuilderDll = Paths.get(BackendFolder.getAbsolutePath(), "SyntaxTreeBuilder.dll").toFile();

    private static String getPathToJar() {
        String path = LocalFiles.class.getResource(".").getPath();
        if (path.startsWith("jar:")) {
            path = path.substring(4);
        }
        if (path.startsWith("file:")) {
            path = path.substring(5);
        }
        int index;
        if ((index = path.indexOf("!/")) != -1) {
            path = path.substring(0, index);
        }

        return path;
    }
}
