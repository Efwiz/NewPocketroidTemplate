package org.jetbrains.kotlin.com.intellij.util;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;

/* JADX INFO: loaded from: classes3.dex */
public interface CharTable {
    public static final Key<CharTable> CHAR_TABLE_KEY = new Key<>("Char table");

    CharSequence intern(CharSequence charSequence);

    CharSequence intern(CharSequence charSequence, int i, int i2);
}
