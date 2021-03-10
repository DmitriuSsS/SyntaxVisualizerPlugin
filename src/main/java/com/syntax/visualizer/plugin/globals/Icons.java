package com.syntax.visualizer.plugin.globals;

import com.intellij.openapi.util.IconLoader;
import javax.swing.*;

public interface Icons {
    Icon Node = IconLoader.getIcon("/icons/node.svg", Icons.class);
    Icon Token = IconLoader.getIcon("/icons/token.svg", Icons.class);
    Icon Value = IconLoader.getIcon("/icons/value.svg", Icons.class);
    Icon Trivia = IconLoader.getIcon("/icons/trivia.svg", Icons.class);

    Icon ToolWindowIcon = IconLoader.getIcon("/icons/toolWindowIcon.svg", Icons.class);
}
