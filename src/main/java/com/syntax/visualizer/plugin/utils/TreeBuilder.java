package com.syntax.visualizer.plugin.utils;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeBuilder {
    public static DefaultMutableTreeNode GetRootNode(SyntaxTree tree) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(tree);
        for (SyntaxTree node : tree.Children) {
            root.add(GetRootNode(node));
        }

        return root;
    }
}
