package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.LineIterator;

/* JADX INFO: loaded from: classes3.dex */
public class LineIteratorImpl implements LineIterator {
    private int myLineIndex = 0;
    private final LineSet myLineSet;

    LineIteratorImpl(LineSet lineSet) {
        this.myLineSet = lineSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.LineIterator
    public int getStart() {
        return this.myLineSet.getLineStart(this.myLineIndex);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.LineIterator
    public int getEnd() {
        return this.myLineSet.getLineEnd(this.myLineIndex);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.LineIterator
    public int getSeparatorLength() {
        return this.myLineSet.getSeparatorLength(this.myLineIndex);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.LineIterator
    public void advance() {
        this.myLineIndex++;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.LineIterator
    public boolean atEnd() {
        return this.myLineIndex >= this.myLineSet.getLineCount() || this.myLineIndex < 0;
    }
}
