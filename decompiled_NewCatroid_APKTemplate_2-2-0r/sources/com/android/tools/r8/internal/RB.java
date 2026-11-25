package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class RB<EV> implements Iterable<VB> {
    private final AbstractC2366kC a;
    private final C3155ul b;
    private final AbstractC0594a1[] c;
    private final PB[] d;
    private final int e;
    private final byte[] f;
    private final int g;
    private final QB h;
    private final OB i;

    RB(C3155ul c3155ul, AbstractC0594a1[] abstractC0594a1Arr, PB[] pbArr, int i, byte[] bArr, int i2, QB qb, OB ob, AbstractC2366kC abstractC2366kC) {
        this.b = c3155ul;
        this.c = abstractC0594a1Arr;
        this.d = pbArr;
        this.e = i;
        this.f = bArr;
        this.g = i2;
        this.h = qb;
        this.i = ob;
        this.a = abstractC2366kC;
    }

    public static <V, EV> NB<V, EV> a(C0684j2 c0684j2, TB<V, EV> tb, C0722p1 c0722p1) {
        return new NB<>(c0684j2, tb, c0722p1);
    }

    public final OB b() {
        return this.i;
    }

    public final int c() {
        return this.g;
    }

    public final C3155ul d() {
        return this.b;
    }

    public final Object e(int i, int i2) {
        return this.a.a().a(i, i2);
    }

    public final AbstractC2366kC f() {
        return this.a;
    }

    public final QB g() {
        return this.h;
    }

    @Override // java.lang.Iterable
    /* JADX INFO: renamed from: i, reason: merged with bridge method [inline-methods] */
    public WB iterator() {
        return new WB(new Q5(this.f));
    }

    public final AbstractC0594a1 j(int i) {
        return this.c[i];
    }

    public final int[] k(int i) {
        OB ob = this.i;
        if (ob == null) {
            return null;
        }
        return (int[]) ob.b.get(i);
    }

    public final String toString() {
        return new C1595aC(this).i();
    }

    public final int a() {
        return this.e;
    }

    public final com.android.tools.r8.graph.X c(Object obj) {
        OB ob = this.i;
        if (ob == null) {
            return null;
        }
        return (com.android.tools.r8.graph.X) ob.a.get(obj);
    }

    public final PB[] e() {
        return this.d;
    }
}
