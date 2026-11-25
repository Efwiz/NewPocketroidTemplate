package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ef, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1939ef {
    static final /* synthetic */ boolean d = true;
    private final AbstractC2412ko a;
    private int b = -1;
    private int c = -1;

    public AbstractC1939ef(AbstractC2412ko abstractC2412ko) {
        if (!d && abstractC2412ko == null) {
            throw new AssertionError();
        }
        this.a = abstractC2412ko;
    }

    public abstract int a(C2322jf c2322jf);

    public void a(int i) {
        if (!d && i < 0) {
            throw new AssertionError();
        }
        this.c = i;
    }

    public abstract void a(C2322jf c2322jf, ArrayList arrayList);

    public abstract boolean a(AbstractC1939ef abstractC1939ef, C2322jf c2322jf);

    public void b(int i) {
        if (!d && i < 0) {
            throw new AssertionError();
        }
    }

    public int c() {
        if (d || this.b >= 0) {
            return this.b;
        }
        throw new AssertionError(this);
    }

    public abstract int d();

    public abstract int e();

    public abstract int f();

    public int b() {
        if (d || this.c >= 0) {
            return this.c;
        }
        throw new AssertionError();
    }

    public AbstractC2412ko a() {
        return this.a;
    }

    public void c(int i) {
        if (!d && i < 0) {
            throw new AssertionError();
        }
        this.b = i;
    }
}
