package org.jetbrains.kotlin.com.intellij.util.text;

/* JADX INFO: loaded from: classes3.dex */
public interface CharSequenceBackedByArray extends CharSequence {
    void getChars(char[] cArr, int i);

    char[] getChars();
}
