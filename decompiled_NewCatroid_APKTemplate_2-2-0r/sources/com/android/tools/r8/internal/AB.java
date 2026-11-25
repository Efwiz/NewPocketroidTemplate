package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class AB extends AbstractC3250w0 {
    private AB() {
        super(new LinkedHashMap());
    }

    public static AB a(InterfaceC1600aG interfaceC1600aG) {
        AB ab = new AB(interfaceC1600aG instanceof InterfaceC1600aG ? interfaceC1600aG.I().size() : 11);
        AbstractC2734ot.a((Iterable) interfaceC1600aG, (Collection) ab);
        return ab;
    }

    public static AB f() {
        return new AB();
    }

    @Override // com.android.tools.r8.internal.H0, java.util.AbstractCollection, java.util.Collection
    public final boolean isEmpty() {
        return entrySet().isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new C3175v0(this);
    }

    private AB(int i) {
        super(RD.c(i));
    }
}
