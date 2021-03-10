package com.syntax.visualizer.plugin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.syntax.visualizer.plugin.globals.Icons;
import org.jetbrains.annotations.NotNull;

public class MainPanelFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        MainPanel mainPanel = new MainPanel(project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(mainPanel.getContent(), "", false);
        toolWindow.setIcon(Icons.ToolWindowIcon);
        toolWindow.getContentManager().addContent(content);
    }
}
