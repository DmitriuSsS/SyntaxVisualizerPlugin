package com.syntax.visualizer.plugin.ui;

import com.syntax.visualizer.plugin.utils.SyntaxTree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class SyntaxTreeCellRenderer extends DefaultTreeCellRenderer {
    private final Icon nodeIcon;
    private final Icon tokenIcon;
    private final Icon valueIcon;
    private final Icon triviaIcon;

    public SyntaxTreeCellRenderer() {
        super();

        nodeIcon = new ImageIcon(getClass().getResource("/icons/node.png"));
        tokenIcon = new ImageIcon(getClass().getResource("/icons/token.png"));
        valueIcon = new ImageIcon(getClass().getResource("/icons/value.png"));
        triviaIcon = new ImageIcon(getClass().getResource("/icons/trivia.png"));

        setBackgroundNonSelectionColor(new Color(0x0, true));
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
            boolean expanded, boolean leaf, int row, boolean hasFocus) {

        SyntaxTree node =  (SyntaxTree) ((DefaultMutableTreeNode) value).getUserObject();

        switch (node.Type) {
            case "Node":
                setIcon(nodeIcon);
                setText(node.Kind);
                break;

            case "Token":
                setIcon(tokenIcon);
                if (leaf) {
                    setText(String.format("%s: \"%s\"", node.Kind, node.Value));
                } else {
                    setText(node.Kind);
                }
                break;

            case "Value":
                setIcon(valueIcon);
                setText(node.Value);
                break;

            case "Trivia":
                setIcon(triviaIcon);
                setText(String.format("%s: \"%s\"", node.Kind, node.Value));
                break;

            default:
                setText(String.format("%s: %s", node.Type, node.Kind));
        }

        return this;
    }
}
