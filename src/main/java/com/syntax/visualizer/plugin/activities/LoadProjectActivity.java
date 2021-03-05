package com.syntax.visualizer.plugin.activities;

import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.progress.ProgressIndicator;
import com.syntax.visualizer.plugin.globals.LocalFiles;
import com.syntax.visualizer.plugin.utils.FileWorker;
import org.jetbrains.annotations.NotNull;

public class LoadProjectActivity extends PreloadingActivity {
    @Override
    public void preload(@NotNull ProgressIndicator indicator) {
        if (!(FileWorker.DeleteIfExist(LocalFiles.BackendFolder) &&
                FileWorker.UnZip(getClass().getResourceAsStream("/SyntaxTreeBuilder.zip"), LocalFiles.BackendFolder))) {
            throw new RuntimeException("Failed to load project");
        }
    }
}
