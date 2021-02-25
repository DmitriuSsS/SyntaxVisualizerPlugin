package com.syntax.visualizer.plugin;

import com.intellij.ide.BrowserUtil;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginInstaller;
import com.intellij.ide.plugins.PluginStateListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.syntax.visualizer.plugin.utils.FileWorker;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;


public class Uninstall implements StartupActivity {

    @Override
    public void runActivity(@NotNull Project project) {
        PluginInstaller.addStateListener(new PluginStateListener() {
            @Override
            public void install(@NotNull IdeaPluginDescriptor descriptor) {
            }

            @Override
            public void uninstall(@NotNull IdeaPluginDescriptor descriptor) {
                if (!FileWorker.DeleteFile(Paths.get(System.getProperty("user.dir"), "SyntaxTreeBuilder").toFile())){
                    throw new RuntimeException();
                }
            }
        });
    }
}