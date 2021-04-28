package com.syntax.visualizer.plugin.activities;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginInstaller;
import com.intellij.ide.plugins.PluginStateListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.syntax.visualizer.plugin.globals.LocalFiles;
import org.jetbrains.annotations.NotNull;

import static com.syntax.visualizer.plugin.utils.FileWorker.DeleteIfExist;


public class UninstallActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        PluginInstaller.addStateListener(new PluginStateListener() {
            @Override
            public void install(@NotNull IdeaPluginDescriptor descriptor) { }

            @Override
            public void uninstall(@NotNull IdeaPluginDescriptor descriptor) {
                if (!DeleteIfExist(LocalFiles.BackendFolder)){
                    throw new RuntimeException("Failed to delete extra unpacked resources");
                }
            }
        });
    }
}