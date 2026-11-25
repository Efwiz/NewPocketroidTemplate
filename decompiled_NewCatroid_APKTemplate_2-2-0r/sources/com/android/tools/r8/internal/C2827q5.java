package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.q5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2827q5 extends AbstractC1972f5 implements Set, Serializable, Cloneable {
    protected C2827q5() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h
    public final boolean c(boolean z) {
        return super.c(z);
    }

    public final Object clone() {
        return AbstractC2968s5.a;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        return (obj instanceof Set) && ((Set) obj).isEmpty();
    }
}
