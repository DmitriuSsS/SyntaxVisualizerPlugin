package com.syntax.visualizer.plugin.ui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

import javax.swing.*;

public class MainPanel {
    private SyntaxTreeUI tree;
    private JPanel content;
    private JButton refreshButton;
    private JScrollPane scroll;
    private JPanel updateLay;

    public MainPanel(Project project) {
        refreshButton.addActionListener(e -> {
            Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
            if (editor == null)
                return;

            refreshButton.setEnabled(false);
            updateLay.setVisible(true);
            scroll.setVisible(false);

            new Thread(() -> {
                tree.Refresh(editor.getDocument());

                scroll.setVisible(true);
                updateLay.setVisible(false);
                refreshButton.setEnabled(true);
            }).start();
        });
    }

    public JPanel getContent() {
        return content;
    }
}
