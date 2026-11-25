package org.jetbrains.kotlin.com.intellij.ui;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public interface CoreAwareIconManager {
    Icon getIcon(VirtualFile virtualFile, int i, Project project);

    Runnable wakeUpNeo(Object obj);
}
