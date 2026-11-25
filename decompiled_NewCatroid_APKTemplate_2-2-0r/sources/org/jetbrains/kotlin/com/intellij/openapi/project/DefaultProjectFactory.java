package org.jetbrains.kotlin.com.intellij.openapi.project;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DefaultProjectFactory {
    public abstract Project getDefaultProject();

    public static DefaultProjectFactory getInstance() {
        return (DefaultProjectFactory) ApplicationManager.getApplication().getService(DefaultProjectFactory.class);
    }
}
