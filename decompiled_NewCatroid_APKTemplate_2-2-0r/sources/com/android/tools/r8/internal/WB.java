package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class WB implements Iterator<VB>, VB {
    static final /* synthetic */ boolean f = true;
    private final Q5 a;
    private int b = 0;
    private int c = 0;
    private int d = -1;
    private int e = -1;

    public WB(Q5 q5) {
        this.a = q5;
    }

    @Override // com.android.tools.r8.internal.VB
    public void a(UB ub) {
        ub.a(this);
    }

    @Override // com.android.tools.r8.internal.VB
    public final int b() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.VB
    public final int d() {
        return this.c - this.b;
    }

    @Override // com.android.tools.r8.internal.VB
    public final int g() {
        return m();
    }

    @Override // com.android.tools.r8.internal.VB
    public final long h() {
        if (!f && !k()) {
            throw new AssertionError();
        }
        this.b += 8;
        Q5 q5 = this.a;
        int i = C1589a6.b;
        return (((long) (q5.f() & 255)) << 56) | (((long) (q5.f() & 255)) << 48) | (((long) (q5.f() & 255)) << 40) | (((long) (q5.f() & 255)) << 32) | (((long) (q5.f() & 255)) << 24) | (((long) (q5.f() & 255)) << 16) | (((long) (q5.f() & 255)) << 8) | ((long) (q5.f() & 255));
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (k()) {
            int iD = d();
            this.b += iD;
            this.a.a(iD);
        }
        return this.a.hasNext();
    }

    @Override // com.android.tools.r8.internal.VB
    public final boolean k() {
        return this.b < this.c;
    }

    @Override // com.android.tools.r8.internal.VB
    public final int l() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.VB
    public final int m() {
        if (!f && !k()) {
            throw new AssertionError();
        }
        this.b += 4;
        Q5 q5 = this.a;
        int i = C1589a6.b;
        return (q5.f() & 255) | ((q5.f() & 255) << 24) | ((q5.f() & 255) << 16) | ((q5.f() & 255) << 8);
    }

    @Override // com.android.tools.r8.internal.VB
    public final int n() {
        return m();
    }

    @Override // java.util.Iterator
    /* JADX INFO: renamed from: p, reason: merged with bridge method [inline-methods] */
    public VB next() {
        if (k()) {
            int iD = d();
            this.b += iD;
            this.a.a(iD);
        }
        this.d++;
        this.b++;
        byte bF = this.a.f();
        int i = C1589a6.b;
        int i2 = bF & 255;
        this.e = i2;
        if (!XB.a && i2 < 1) {
            throw new AssertionError();
        }
        if (i2 <= 15 || i2 == 177 || i2 == 209 || i2 == 211) {
            this.c = this.b;
        } else {
            this.b++;
            this.c = this.b + (this.a.f() & 255);
        }
        return this;
    }

    @Override // com.android.tools.r8.internal.VB
    public final int a() {
        return m();
    }
}
