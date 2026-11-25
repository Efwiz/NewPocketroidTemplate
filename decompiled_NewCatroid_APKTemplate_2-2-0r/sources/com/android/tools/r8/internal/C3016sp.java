package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3016sp extends AbstractC3470z implements InterfaceC2947rp, Cloneable {
    protected C3016sp() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2730op
    public final /* bridge */ /* synthetic */ boolean a(int i) {
        return false;
    }

    public InterfaceC2529mK b() {
        return AbstractC2771pK.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC3470z, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final /* bridge */ /* synthetic */ void clear() {
    }

    public final Object clone() {
        return AbstractC3086tp.a;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return false;
    }

    @Override // java.util.Map, java.util.SortedMap
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public InterfaceC2529mK entrySet() {
        return b();
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2730op
    public final /* bridge */ /* synthetic */ Object get(int i) {
        return null;
    }

    @Override // java.util.Map
    public final int hashCode() {
        return 0;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return true;
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

    @Override // com.android.tools.r8.internal.InterfaceC2947rp, java.util.Map, java.util.SortedMap
    public final ZJ values() {
        return AbstractC2771pK.a;
    }

    @Override // java.util.Map, java.util.SortedMap
    public InterfaceC1277Pr keySet() {
        return AbstractC1331Rr.a;
    }

    @Override // java.util.Map
    public final Collection values() {
        return AbstractC2771pK.a;
    }
}
