package com.syntax.visualizer.plugin.ui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

import javax.swing.*;

public class MainPanel {
    private SyntaxTreeUI tree;
    private JButton linkButton;
    private JPanel content;
    private JButton buildButton;

    public MainPanel(Project project) {
        linkButton.addActionListener(e -> {
            Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
            if (editor == null)
                return;

            tree.LinkDocument(editor.getDocument());
        });

        buildButton.addActionListener(e -> tree.Build());
    }

    public JPanel getContent() {
        return content;
    }
}
