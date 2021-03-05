package com.syntax.visualizer.plugin.ui;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
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

            Document document = editor.getDocument();
            FileDocumentManager.getInstance().saveDocument(document);

            setTreeVisible(false);

            new Thread(() -> {
                tree.refresh(document);
                setTreeVisible(true);
            }).start();
        });
    }

    private void setTreeVisible(boolean enable) {
        scroll.setVisible(enable);
        updateLay.setVisible(!enable);
        refreshButton.setEnabled(enable);
    }

    public JPanel getContent() {
        return content;
    }
}
