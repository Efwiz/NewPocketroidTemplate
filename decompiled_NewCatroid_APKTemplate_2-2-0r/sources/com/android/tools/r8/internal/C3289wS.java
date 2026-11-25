package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wS, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3289wS extends AbstractC2779pS implements InterfaceC3211vS, Serializable, Cloneable {
    protected C3289wS() {
    }

    public final Object clone() {
        return AbstractC3436yS.a;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        return (obj instanceof Set) && ((Set) obj).isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }
}
