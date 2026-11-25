package org.jetbrains.kotlin.org.codehaus.stax2.typed;

/* JADX INFO: loaded from: classes4.dex */
public abstract class TypedValueDecoder {
    public abstract void decode(char[] cArr, int i, int i2) throws IllegalArgumentException;

    public abstract void handleEmptyValue() throws IllegalArgumentException;
}
