package com.syntax.visualizer.plugin.icons;

import com.intellij.openapi.util.IconLoader;
import javax.swing.*;

public interface TreeIcons {
    Icon Node = IconLoader.getIcon("/icons/node.svg", TreeIcons.class);
    Icon Token = IconLoader.getIcon("/icons/token.svg", TreeIcons.class);
    Icon Value = IconLoader.getIcon("/icons/value.svg", TreeIcons.class);
    Icon Trivia = IconLoader.getIcon("/icons/trivia.svg", TreeIcons.class);
}
