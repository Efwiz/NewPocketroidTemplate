package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3454yk extends AbstractC3250w0 {
    private C3454yk() {
        super(new HashMap());
    }

    public static C3454yk a(Iterable iterable) {
        C3454yk c3454yk = new C3454yk(iterable instanceof InterfaceC1600aG ? ((InterfaceC1600aG) iterable).I().size() : 11);
        AbstractC2734ot.a(iterable, (Collection) c3454yk);
        return c3454yk;
    }

    public static C3454yk f() {
        return new C3454yk();
    }

    @Override // com.android.tools.r8.internal.H0, java.util.AbstractCollection, java.util.Collection
    public final boolean isEmpty() {
        return entrySet().isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new C3175v0(this);
    }

    private C3454yk(int i) {
        super(RD.b(i));
    }
}
