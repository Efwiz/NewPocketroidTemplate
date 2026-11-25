package org.jetbrains.kotlin.com.intellij.injected.editor;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: loaded from: classes3.dex */
public interface DocumentWindow extends Document {
    Document getDelegate();

    Segment[] getHostRanges();

    int hostToInjected(int i);

    TextRange injectedToHost(TextRange textRange);

    boolean isValid();
}
