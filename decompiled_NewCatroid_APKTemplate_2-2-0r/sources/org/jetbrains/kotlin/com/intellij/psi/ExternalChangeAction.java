package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.DocumentRunnable;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public interface ExternalChangeAction extends IgnorePsiEventsMarker, Runnable {

    public static abstract class ExternalDocumentChange extends DocumentRunnable implements ExternalChangeAction {
        protected ExternalDocumentChange(Document document, Project project) {
            super(document, project);
        }
    }
}
