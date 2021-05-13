package com.syntax.visualizer.plugin.ui;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.syntax.visualizer.plugin.globals.ProjectLogger;

import javax.swing.*;
import java.util.logging.Level;

public class MainPanel {
    private SyntaxTreeUI tree;
    private JPanel content;
    private JButton refreshButton;

    private JScrollPane scroll;
    private JPanel updateLay;
    private JPanel hintLay;
    private JPanel errorLay;

    private enum Lay {
        Tree,
        Update,
        Hint,
        Error
    }

    public MainPanel(Project project) {
        viewLay(Lay.Hint);
        refreshButton.addActionListener(e -> {
            Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
            if (editor == null) {
                viewLay(Lay.Hint);
                return;
            }

            Document document = editor.getDocument();
            FileDocumentManager.getInstance().saveDocument(document);

            viewLay(Lay.Update);

            new Thread(() -> {
                try {
                    if (tree.refresh(document))
                        viewLay(Lay.Tree);
                    else
                        viewLay(Lay.Hint);
                } catch (Exception exc) {
                    ProjectLogger.LOGGER.log(Level.SEVERE, "Failed to update document tree", exc);
                    viewLay(Lay.Error);
                }
            }).start();
        });
    }

    private void viewLay(Lay lay) {
        scroll.setVisible(lay == Lay.Tree);
        updateLay.setVisible(lay == Lay.Update);
        hintLay.setVisible(lay == Lay.Hint);
        errorLay.setVisible(lay == Lay.Error);
        refreshButton.setEnabled(lay != Lay.Update);
    }

    public JPanel getContent() {
        return content;
    }
}
