package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.t4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3036t4 implements Iterator {
    private InterfaceC2488lo a;
    private AbstractC2412ko b = a();
    final /* synthetic */ AbstractC2412ko c;

    C3036t4(C3332x4 c3332x4, AbstractC2412ko abstractC2412ko) {
        this.c = abstractC2412ko;
        this.a = c3332x4.R();
    }

    private AbstractC2412ko a() {
        AbstractC2412ko next;
        if (!this.a.hasNext() || (next = this.a.next()) == this.c) {
            return null;
        }
        return next;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b != null;
    }

    @Override // java.util.Iterator
    public final Object next() {
        AbstractC2412ko abstractC2412ko = this.b;
        if (abstractC2412ko == null) {
            throw new NoSuchElementException();
        }
        this.b = a();
        return abstractC2412ko;
    }
}
