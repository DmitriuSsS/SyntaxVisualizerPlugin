package com.syntax.visualizer.plugin.ui;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.syntax.visualizer.plugin.utils.SyntaxTreeParser;
import com.syntax.visualizer.plugin.utils.SyntaxTree;
import com.syntax.visualizer.plugin.utils.TreeBuilder;
import org.jetbrains.annotations.Nullable;

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

    public void Refresh(Document newDocument) {
        new Thread(() -> {
            if (newDocument == null)
                return;

            VirtualFile file = FileDocumentManager.getInstance().getFile(newDocument);
            if (file == null || !Objects.equals(file.getExtension(), "cs"))
                return;

            codeDocument = newDocument;

            SyntaxTree newTree = treeParser.GetSyntaxTreeFromDocument(codeDocument);
            DefaultMutableTreeNode root = TreeBuilder.GetRootNode(newTree);

            setModel(new DefaultTreeModel(root));
        }).start();
    }
}
