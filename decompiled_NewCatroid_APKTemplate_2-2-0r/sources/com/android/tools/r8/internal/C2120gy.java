package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2120gy extends AbstractC2191hy {
    private Integer c;
    private C1464Vx d;
    private C0942Dy e;
    private int b = AbstractC3452yi.a(new C3079ti[0]);
    private final ArrayList f = new ArrayList(0);
    private final ArrayList g = new ArrayList(0);

    public final void a(Integer num) {
        this.c = num;
    }

    public final void b(int i) {
        this.b = i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2191hy
    public final AbstractC2191hy c() {
        return (AbstractC2191hy) AbstractC2909rH.a(new C2120gy(), this.g);
    }

    public final ArrayList d() {
        return this.f;
    }

    public final C1464Vx e() {
        return this.d;
    }

    public final int f() {
        return this.b;
    }

    public final ArrayList g() {
        return this.g;
    }

    public final Integer h() {
        return this.c;
    }

    public final C0942Dy i() {
        return this.e;
    }

    public final void a(C1464Vx c1464Vx) {
        this.d = c1464Vx;
    }

    public final void a(C0942Dy c0942Dy) {
        this.e = c0942Dy;
    }

    @Override // com.android.tools.r8.internal.AbstractC2191hy
    public final void a(int i, Integer num) {
        this.b = i;
        this.c = num;
    }

    @Override // com.android.tools.r8.internal.AbstractC2191hy
    public final void a(Object obj) {
        this.d = new C1464Vx(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2191hy
    public final AbstractC1254Oy a(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        this.e = c0942Dy;
        return c0942Dy;
    }

    @Override // com.android.tools.r8.internal.AbstractC2191hy
    public final AbstractC2191hy a() {
        return (AbstractC2191hy) AbstractC2909rH.a(new C2120gy(), this.f);
    }

    public final void a(AbstractC2191hy abstractC2191hy) {
        AbstractC1254Oy abstractC1254OyA;
        abstractC2191hy.a(this.b, this.c);
        C1464Vx c1464Vx = this.d;
        if (c1464Vx != null) {
            abstractC2191hy.a(c1464Vx.a());
        }
        C0942Dy c0942Dy = this.e;
        if (c0942Dy != null && (abstractC1254OyA = abstractC2191hy.a(c0942Dy.e())) != null) {
            c0942Dy.a(abstractC1254OyA);
        }
        for (C2120gy c2120gy : this.f) {
            AbstractC2191hy abstractC2191hyA = abstractC2191hy.a();
            if (abstractC2191hyA != null) {
                c2120gy.a(abstractC2191hyA);
            }
        }
        for (C2120gy c2120gy2 : this.g) {
            AbstractC2191hy abstractC2191hyC = abstractC2191hy.c();
            if (abstractC2191hyC != null) {
                c2120gy2.a(abstractC2191hyC);
            }
        }
        abstractC2191hy.b();
    }
}
