package org.eclipse.jdt.internal.compiler.codegen;

/* JADX INFO: loaded from: classes14.dex */
public class CachedIndexEntry {
    public int index;
    public char[] signature;

    public CachedIndexEntry(char[] cArr, int i) {
        this.signature = cArr;
        this.index = i;
    }
}
