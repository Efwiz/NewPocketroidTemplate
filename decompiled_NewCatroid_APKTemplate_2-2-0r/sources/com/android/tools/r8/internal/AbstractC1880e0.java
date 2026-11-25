package com.android.tools.r8.internal;

import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.e0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1880e0 extends Z implements ListIterator {
    protected AbstractC1880e0() {
    }

    public abstract void a(long j);

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        a(((Long) obj).longValue());
    }

    public abstract void b(long j);

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        b(((Long) obj).longValue());
    }
}
