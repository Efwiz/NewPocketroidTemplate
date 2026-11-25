package org.jetbrains.kotlin.com.intellij.openapi.roots.impl;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public interface PushedFilePropertiesRetriever {
    List<String> dumpSortedPushedProperties(VirtualFile virtualFile);

    static PushedFilePropertiesRetriever getInstance() {
        return (PushedFilePropertiesRetriever) ApplicationManager.getApplication().getService(PushedFilePropertiesRetriever.class);
    }
}
