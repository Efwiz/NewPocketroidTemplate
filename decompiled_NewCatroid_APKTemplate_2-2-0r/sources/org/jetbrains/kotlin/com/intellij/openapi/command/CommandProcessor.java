package org.jetbrains.kotlin.com.intellij.openapi.command;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CommandProcessor {
    public abstract void executeCommand(Project project, Runnable runnable, String str, Object obj);

    public abstract Runnable getCurrentCommand();

    public abstract boolean isUndoTransparentActionInProgress();

    public static CommandProcessor getInstance() {
        return (CommandProcessor) ApplicationManager.getApplication().getService(CommandProcessor.class);
    }
}
