package com.syntax.visualizer.plugin.ui;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.syntax.visualizer.plugin.utils.SyntaxTreeParser;
import com.syntax.visualizer.plugin.utils.SyntaxTree;
import com.syntax.visualizer.plugin.utils.TreeBuilder;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Objects;

public class SyntaxTreeUI extends JTree {
    private Document codeDocument;
    private final SyntaxTreeParser treeParser;

    public SyntaxTreeUI() {
        setModel(new DefaultTreeModel(null));
        setCellRenderer(new SyntaxTreeCellRenderer());
        treeParser = new SyntaxTreeParser();
    }

    public void Build() {
        new Thread(() -> {
            if (codeDocument == null)
                return;

            SyntaxTree newTree = treeParser.GetSyntaxTreeFromDocument(codeDocument);
            DefaultMutableTreeNode root = TreeBuilder.GetRootNode(newTree);

            setModel(new DefaultTreeModel(root));
        }).start();
    }

    public void LinkDocument(Document newDocument) {
        new Thread(() -> {
            if (newDocument == null || newDocument == codeDocument)
                return;

            VirtualFile file = FileDocumentManager.getInstance().getFile(newDocument);
            if (file == null)
                return;

            if (!Objects.equals(file.getExtension(), "cs"))
                return;

            codeDocument = newDocument;
            Build();
        }).start();
    }
}
