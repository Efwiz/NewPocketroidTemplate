package org.jetbrains.kotlin.com.intellij.openapi.editor.ex;

/* JADX INFO: loaded from: classes3.dex */
public interface LineIterator {
    void advance();

    boolean atEnd();

    int getEnd();

    int getSeparatorLength();

    int getStart();
}
