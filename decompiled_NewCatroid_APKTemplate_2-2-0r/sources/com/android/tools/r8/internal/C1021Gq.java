package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1021Gq extends E implements InterfaceC0992Fq, Cloneable {
    protected C1021Gq() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3087tq
    public final /* bridge */ /* synthetic */ boolean a(int i) {
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0992Fq
    public InterfaceC2529mK c() {
        return AbstractC2771pK.a;
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final /* bridge */ /* synthetic */ void clear() {
    }

    public final Object clone() {
        return AbstractC1052Hq.a;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return false;
    }

    @Override // java.util.Map
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public InterfaceC2529mK entrySet() {
        return c();
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3087tq
    public /* bridge */ /* synthetic */ Object get(int i) {
        return null;
    }

    @Override // java.util.Map
    public final int hashCode() {
        return 0;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return true;
    }

    @Override // java.util.Map
    public void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public /* bridge */ /* synthetic */ int size() {
        return 0;
    }

    public final String toString() {
        return "{}";
    }

    @Override // com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC2703oS values() {
        return AbstractC3436yS.a;
    }

    @Override // java.util.Map
    public InterfaceC1277Pr keySet() {
        return AbstractC1331Rr.a;
    }

    @Override // java.util.Map
    public final Collection values() {
        return AbstractC3436yS.a;
    }
}
