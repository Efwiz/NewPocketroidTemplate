package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1482Wp extends C3016sp implements InterfaceC1456Vp {
    protected C1482Wp() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp a(int i, int i2) {
        return AbstractC1511Xp.a;
    }

    @Override // com.android.tools.r8.internal.C3016sp, com.android.tools.r8.internal.InterfaceC2947rp
    public final InterfaceC2529mK b() {
        return AbstractC2983sK.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp c(int i) {
        return AbstractC1511Xp.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp, java.util.SortedMap
    public final InterfaceC3391xr comparator() {
        return null;
    }

    @Override // java.util.SortedMap
    public final /* bridge */ /* synthetic */ Comparator comparator() {
        return null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final int d() {
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.C3016sp
    /* JADX INFO: renamed from: e */
    public final InterfaceC2529mK entrySet() {
        return AbstractC2983sK.a;
    }

    @Override // com.android.tools.r8.internal.C3016sp, java.util.Map, java.util.SortedMap
    public final Set entrySet() {
        return AbstractC2983sK.a;
    }

    @Override // java.util.SortedMap
    public final Object firstKey() {
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedMap
    public final SortedMap headMap(Object obj) {
        ((Integer) obj).intValue();
        return AbstractC1511Xp.a;
    }

    @Override // com.android.tools.r8.internal.C3016sp, java.util.Map, java.util.SortedMap
    public final InterfaceC1277Pr keySet() {
        return AbstractC1432Ur.a;
    }

    @Override // java.util.SortedMap
    public final Object lastKey() {
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedMap
    public final SortedMap subMap(Object obj, Object obj2) {
        ((Integer) obj).intValue();
        ((Integer) obj2).intValue();
        return AbstractC1511Xp.a;
    }

    @Override // java.util.SortedMap
    public final SortedMap tailMap(Object obj) {
        ((Integer) obj).intValue();
        return AbstractC1511Xp.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final int a() {
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.C3016sp, com.android.tools.r8.internal.InterfaceC2947rp
    public final InterfaceC2842qK b() {
        return AbstractC2983sK.a;
    }

    @Override // com.android.tools.r8.internal.C3016sp, java.util.Map, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        return AbstractC1432Ur.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp b(int i) {
        return AbstractC1511Xp.a;
    }

    @Override // com.android.tools.r8.internal.C3016sp, java.util.Map, java.util.SortedMap
    public final Set keySet() {
        return AbstractC1432Ur.a;
    }
}
