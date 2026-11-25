package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1693bR extends AbstractC2350k1 implements InterfaceC1613aR, Cloneable {
    protected C1693bR() {
    }

    @Override // com.android.tools.r8.internal.XQ
    public final /* bridge */ /* synthetic */ boolean b(Object obj) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2350k1, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final /* bridge */ /* synthetic */ void clear() {
    }

    public final Object clone() {
        return AbstractC1842dR.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final /* bridge */ /* synthetic */ boolean containsKey(Object obj) {
        return false;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return false;
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return AbstractC2771pK.a;
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1613aR
    public final InterfaceC2529mK h() {
        return AbstractC2771pK.a;
    }

    @Override // java.util.Map
    public final int hashCode() {
        return 0;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1613aR, java.util.Map
    public final InterfaceC3211vS keySet() {
        return AbstractC3436yS.a;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final /* bridge */ /* synthetic */ int size() {
        return 0;
    }

    public final String toString() {
        return "{}";
    }

    @Override // java.util.Map
    public final Collection values() {
        return AbstractC2968s5.a;
    }

    @Override // java.util.Map
    public final Set keySet() {
        return AbstractC3436yS.a;
    }
}
