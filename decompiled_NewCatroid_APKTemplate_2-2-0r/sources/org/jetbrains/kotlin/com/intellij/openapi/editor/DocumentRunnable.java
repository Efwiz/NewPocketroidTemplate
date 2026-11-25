package org.jetbrains.kotlin.com.intellij.openapi.editor;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DocumentRunnable implements Runnable {
    private final Document myDocument;
    private final Project myProject;

    public DocumentRunnable(Document document, Project project) {
        this.myDocument = document;
        this.myProject = project;
    }

    public Document getDocument() {
        return this.myDocument;
    }

    public Project getProject() {
        return this.myProject;
    }
}
