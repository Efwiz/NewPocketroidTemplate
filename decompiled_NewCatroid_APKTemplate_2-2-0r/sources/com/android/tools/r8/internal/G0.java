package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class G0 extends MU {
    final /* synthetic */ H0 a;

    G0(H0 h0) {
        this.a = h0;
    }

    InterfaceC1600aG a() {
        return this.a;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        a().clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (obj instanceof ZF) {
            ZF zf = (ZF) obj;
            if (zf.a() > 0 && a().b(zf.b()) == zf.a()) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return this.a.e();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (obj instanceof ZF) {
            ZF zf = (ZF) obj;
            Object objB = zf.b();
            int iA = zf.a();
            if (iA != 0) {
                return a().a(iA, objB);
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.c();
    }
}
