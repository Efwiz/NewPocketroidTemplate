package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.va, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3220va extends AbstractC1256Pa {
    public List c;
    public List d;
    public List e;
    public List f;
    public List g;
    public ArrayList h;
    public List i;
    public List j;
    public List k;
    public ArrayList l;
    public ArrayList m;

    public C3220va() {
        this(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str) {
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str, String str2) {
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str, String str2, String str3) {
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void b(String str) {
        this.i = P10.a(this.i, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void c(String str) {
        this.j = P10.a(this.j, str);
    }

    public C3220va(int i) {
        super(589824);
        new ArrayList();
        this.h = new ArrayList();
        this.l = new ArrayList();
        this.m = new ArrayList();
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final CQ b(String str, String str2, String str3) {
        BQ bq = new BQ();
        this.k = P10.a(this.k, bq);
        return bq;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, int i2, String str, String str2, String str3, String[] strArr) {
        P10.a(strArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC3116uF a(int i, String str, String str2) {
        return new C2837qF();
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2509m2 a(String str, boolean z) {
        C2432l2 c2432l2 = new C2432l2(str);
        if (z) {
            this.c = P10.a(this.c, c2432l2);
        } else {
            this.d = P10.a(this.d, c2432l2);
        }
        return c2432l2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        C3251w00 c3251w00 = new C3251w00(i, c00, str);
        if (z) {
            this.e = P10.a(this.e, c3251w00);
        } else {
            this.f = P10.a(this.f, c3251w00);
        }
        return c3251w00;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(T3 t3) {
        this.g = P10.a(this.g, t3);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, String str, String str2, String str3) {
        this.h.add(new C3527zn());
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2252ii a(int i, String str, String str2, String str3, Object obj) {
        C1422Uh c1422Uh = new C1422Uh(obj);
        this.l.add(c1422Uh);
        return c1422Uh;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC1899eF a(int i, String str, String str2, String str3, String[] strArr) {
        ME me = new ME(589824, i, str, str2, strArr);
        this.m.add(me);
        return me;
    }
}
