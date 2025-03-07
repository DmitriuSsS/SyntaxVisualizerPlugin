package com.syntax.visualizer.plugin.ui;

import com.syntax.visualizer.plugin.globals.Icons;
import com.syntax.visualizer.plugin.utils.SyntaxTree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class SyntaxTreeCellRenderer extends DefaultTreeCellRenderer {
    public SyntaxTreeCellRenderer() {
        super();
        setBackgroundNonSelectionColor(new Color(0x0, true));
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
            boolean expanded, boolean leaf, int row, boolean hasFocus) {

        SyntaxTree node =  (SyntaxTree) ((DefaultMutableTreeNode) value).getUserObject();

        switch (node.Type) {
            case "Node":
                setIcon(Icons.Node);
                setText(node.Kind);
                break;

            case "Token":
                setIcon(Icons.Token);
                if (leaf) {
                    setText(String.format("%s: \"%s\"", node.Kind, node.Value));
                } else {
                    setText(node.Kind);
                }
                break;

            case "Value":
                setIcon(Icons.Value);
                setText(node.Value);
                break;

            case "Trivia":
                setIcon(Icons.Trivia);
                setText(String.format("%s: \"%s\"", node.Kind, GetScreenTriviaValue(node.Value)));
                break;

            default:
                setText(String.format("%s: %s", node.Type, node.Kind));
        }

        return this;
    }

    private String GetScreenTriviaValue(String triviaValue) {
        if (triviaValue.equals("\r\n")) {
            return "\\r\\n";
        } else if (triviaValue.equals("\n")) {
            return "\\n";
        } else if (triviaValue.trim().isEmpty()) {
            return triviaValue.length() == 1 ? "<space>" : String.format("<space:%d>", triviaValue.length());
        }

        return triviaValue;
    }
}
