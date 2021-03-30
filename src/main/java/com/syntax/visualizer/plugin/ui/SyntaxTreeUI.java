package com.syntax.visualizer.plugin.ui;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.syntax.visualizer.plugin.utils.SyntaxTree;
import com.syntax.visualizer.plugin.utils.SyntaxTreeParser;
import com.syntax.visualizer.plugin.utils.TreeBuilder;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Objects;

public class SyntaxTreeUI extends JTree {
    private final SyntaxTreeParser treeParser;

    public SyntaxTreeUI() {
        dropTree();
        setCellRenderer(new SyntaxTreeCellRenderer());
        treeParser = new SyntaxTreeParser();
    }

    public boolean refresh(Document document) {
        if (document == null) {
            dropTree();
            return false;
        }

        VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        if (file == null || !Objects.equals(file.getExtension(), "cs")) {
            dropTree();
            return false;
        }

        SyntaxTree newTree = treeParser.getSyntaxTreeFromDocument(file.getPath());
        DefaultMutableTreeNode root = TreeBuilder.GetRootNode(newTree);

        setModel(new DefaultTreeModel(root));
        return true;
    }

    private void dropTree() {
        setModel(new DefaultTreeModel(null));
    }
}