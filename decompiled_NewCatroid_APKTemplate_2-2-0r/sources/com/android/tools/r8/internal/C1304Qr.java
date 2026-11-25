package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1304Qr extends AbstractC3165ur implements InterfaceC1277Pr, Serializable, Cloneable {
    protected C1304Qr() {
    }

    public Object clone() {
        return AbstractC1331Rr.a;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        return (obj instanceof Set) && ((Set) obj).isEmpty();
    }

    @Override // com.android.tools.r8.internal.J
    public final boolean j(int i) {
        return super.j(i);
    }
}
