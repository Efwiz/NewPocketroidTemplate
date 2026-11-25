package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Z implements TC, XC {
    protected Z() {
    }

    @Override // com.android.tools.r8.internal.TC
    public abstract long e();

    @Override // java.util.Iterator
    public final Object next() {
        return Long.valueOf(c());
    }

    @Override // com.android.tools.r8.internal.YJ
    public final Object previous() {
        return Long.valueOf(e());
    }
}
