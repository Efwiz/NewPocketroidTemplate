package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ej, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0958Ej extends AbstractC1045Hj {
    private final C1785ci a;

    protected AbstractC0958Ej() {
        this.a = C1785ci.g();
    }

    public final boolean b(C1012Gj c1012Gj) {
        if (c1012Gj.a == b()) {
            return this.a.b(c1012Gj.d);
        }
        throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
    }

    protected final boolean g() {
        return this.a.d();
    }

    protected final int h() {
        return this.a.c();
    }

    protected final void i() {
        this.a.f();
    }

    protected final C0927Dj j() {
        return new C0927Dj(this, 0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1045Hj
    protected final boolean a(C2396kb c2396kb, C2547mb c2547mb, C2867qh c2867qh, int i) {
        return AbstractC1045Hj.a(this.a, b(), c2396kb, c2547mb, c2867qh, i);
    }

    protected AbstractC0958Ej(AbstractC0899Cj abstractC0899Cj) {
        this.a = AbstractC0899Cj.a(abstractC0899Cj);
    }

    public final Object a(C1012Gj c1012Gj) {
        if (c1012Gj.a == b()) {
            Object objA = this.a.a(c1012Gj.d);
            if (objA == null) {
                return c1012Gj.b;
            }
            C0985Fj c0985Fj = c1012Gj.d;
            if (c0985Fj.c) {
                if (c0985Fj.b.a() != M20.i) {
                    return objA;
                }
                ArrayList arrayList = new ArrayList();
                Iterator it = ((List) objA).iterator();
                while (it.hasNext()) {
                    arrayList.add(c1012Gj.a(it.next()));
                }
                return arrayList;
            }
            return c1012Gj.a(objA);
        }
        throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
    }
}
