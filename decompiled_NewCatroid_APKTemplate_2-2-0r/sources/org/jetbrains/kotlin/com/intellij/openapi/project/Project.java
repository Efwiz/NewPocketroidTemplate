package org.jetbrains.kotlin.com.intellij.openapi.project;

import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.AreaInstance;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public interface Project extends ComponentManager, AreaInstance {
    public static final String DIRECTORY_STORE_FOLDER = ".idea";

    @Deprecated
    VirtualFile getBaseDir();

    String getBasePath();

    String getLocationHash();

    String getName();

    default String getPresentableUrl() {
        return null;
    }

    VirtualFile getProjectFile();

    String getProjectFilePath();

    VirtualFile getWorkspaceFile();

    default boolean isDefault() {
        return false;
    }

    boolean isInitialized();

    boolean isOpen();

    void save();
}
