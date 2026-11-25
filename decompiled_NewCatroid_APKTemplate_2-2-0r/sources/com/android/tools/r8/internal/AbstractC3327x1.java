package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.x1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3327x1 implements Cloneable, InterfaceC2998sZ {
    public boolean a(C0702m c0702m, AbstractC3327x1 abstractC3327x1) {
        return equals(b(c0702m, abstractC3327x1));
    }

    public abstract AbstractC3327x1 b(C0702m c0702m, AbstractC3327x1 abstractC3327x1);

    @Override // com.android.tools.r8.internal.InterfaceC2998sZ
    public final boolean c() {
        return true;
    }

    @Override // 
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public AbstractC3327x1 clone() {
        return a();
    }

    public abstract boolean equals(Object obj);
}
