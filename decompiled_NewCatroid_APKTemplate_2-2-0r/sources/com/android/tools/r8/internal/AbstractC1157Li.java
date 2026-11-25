package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Li, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1157Li extends AbstractC1183Mi implements Iterator {
    protected AbstractC1157Li() {
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return ((C3073tc) this).b.hasNext();
    }

    @Override // java.util.Iterator
    public Object next() {
        return ((C3073tc) this).b.next();
    }
}
