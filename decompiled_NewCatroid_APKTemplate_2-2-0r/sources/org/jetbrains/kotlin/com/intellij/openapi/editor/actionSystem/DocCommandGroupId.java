package org.jetbrains.kotlin.com.intellij.openapi.editor.actionSystem;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;

/* JADX INFO: loaded from: classes3.dex */
public final class DocCommandGroupId {
    private final Document myDocument;
    private final Object myGroupId;

    public static DocCommandGroupId noneGroupId(Document document) {
        return new DocCommandGroupId(document, new Object());
    }

    private DocCommandGroupId(Document document, Object obj) {
        this.myDocument = document;
        this.myGroupId = obj;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DocCommandGroupId docCommandGroupId = (DocCommandGroupId) obj;
        return this.myDocument.equals(docCommandGroupId.myDocument) && this.myGroupId.equals(docCommandGroupId.myGroupId);
    }

    public int hashCode() {
        return (this.myDocument.hashCode() * 31) + this.myGroupId.hashCode();
    }
}
