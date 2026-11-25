package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2491lr extends C1021Gq implements InterfaceC2415kr {
    protected C2491lr() {
    }

    public InterfaceC2415kr a(int i, int i2) {
        return AbstractC2568mr.a;
    }

    public InterfaceC2415kr b(int i) {
        return AbstractC2568mr.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC3391xr comparator() {
        return null;
    }

    @Override // java.util.SortedMap
    public final /* bridge */ /* synthetic */ Comparator comparator() {
        return null;
    }

    public int d() {
        throw new NoSuchElementException();
    }

    public int a() {
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedMap
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public InterfaceC2415kr headMap(Integer num) {
        return c(num.intValue());
    }

    @Override // com.android.tools.r8.internal.C1021Gq, com.android.tools.r8.internal.InterfaceC0992Fq
    public InterfaceC2842qK c() {
        return AbstractC2983sK.a;
    }

    @Override // com.android.tools.r8.internal.C1021Gq, java.util.Map
    public InterfaceC2842qK entrySet() {
        return AbstractC2983sK.a;
    }

    @Override // java.util.SortedMap
    public Integer firstKey() {
        return Integer.valueOf(a());
    }

    @Override // java.util.SortedMap
    public Integer lastKey() {
        return Integer.valueOf(d());
    }

    @Override // java.util.SortedMap
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public InterfaceC2415kr tailMap(Integer num) {
        return b(num.intValue());
    }

    public InterfaceC2415kr c(int i) {
        return AbstractC2568mr.a;
    }

    @Override // com.android.tools.r8.internal.C1021Gq, java.util.Map
    public InterfaceC1357Sr keySet() {
        return AbstractC1432Ur.a;
    }

    @Override // java.util.SortedMap
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public InterfaceC2415kr subMap(Integer num, Integer num2) {
        return a(num.intValue(), num2.intValue());
    }
}
