package com.syntax.visualizer.plugin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.syntax.visualizer.plugin.utils.FileWorker;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;

public class MainPanelFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        if (!init()) {
            throw new RuntimeException("Не удалось выкачать проект");
        }

        MainPanel mainPanel = new MainPanel(project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(mainPanel.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }

    private boolean init() {
        //TODO: подумать над папкой с C# проектом + подумать как не оставлять мусор
        File folderForBackend = new File(System.getProperty("user.dir"));
        File zipFile = Paths.get(folderForBackend.getAbsolutePath(), "SyntaxTreeBuilder.zip").toFile();
        File backendFolder = Paths.get(folderForBackend.getAbsolutePath(), "SyntaxTreeBuilder").toFile();

        return (!zipFile.exists() || zipFile.delete()) &&
                (!backendFolder.exists() || FileWorker.DeleteFile(backendFolder)) &&
                FileWorker.WriteFileFromStream(getClass().getResourceAsStream("/SyntaxTreeBuilder.zip"), zipFile) &&
                FileWorker.UnZip(zipFile, backendFolder) &&
                zipFile.delete();
    }
}
