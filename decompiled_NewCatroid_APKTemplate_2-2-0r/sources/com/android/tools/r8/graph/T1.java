package com.android.tools.r8.graph;

import java.util.IdentityHashMap;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class T1 {
    static final /* synthetic */ boolean h = true;
    private final C0737r2 a;
    private final C0737r2 b;
    private final C0737r2 c;
    public final Set d;
    private final Set e;
    public final Set f;
    final /* synthetic */ C0722p1 g;

    public T1(C0722p1 c0722p1) {
        this.g = c0722p1;
        this.a = c0722p1.b(c0722p1.Z1, c0722p1.c2);
        this.b = c0722p1.b(c0722p1.D1, c0722p1.c2);
        this.c = c0722p1.b(c0722p1.v1, c0722p1.c2);
        this.d = a("compareAndExchange", "compareAndExchangeAcquire", "compareAndExchangeRelease", c0722p1.m1, "getAcquire", "getAndAdd", "getAndAddAcquire", "getAndAddRelease", "getAndBitwiseAnd", "getAndBitwiseAndAcquire", "getAndBitwiseAndRelease", "getAndBitwiseOr", "getAndBitwiseOrAcquire", "getAndBitwiseOrRelease", "getAndBitwiseXor", "getAndBitwiseXorAcquire", "getAndBitwiseXorRelease", "getAndSet", "getAndSetAcquire", "getAndSetRelease", "getOpaque", "getVolatile");
        this.e = a(c0722p1.n1, "setOpaque", c0722p1.s1, c0722p1.r1);
        this.f = a(c0722p1.o1, c0722p1.p1, "weakCompareAndSetAcquire", "weakCompareAndSetPlain", "weakCompareAndSetRelease");
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0062  */
    public final C0684j2 a(C0684j2 c0684j2) {
        C0684j2 c0684j2A;
        C0767v2 c0767v2 = c0684j2.e;
        C0722p1 c0722p1 = this.g;
        C0767v2 c0767v3 = c0722p1.C2;
        if (c0767v2 == c0767v3) {
            C0759u2 c0759u2 = c0684j2.f;
            if (c0759u2 == c0722p1.C0 || c0759u2 == c0722p1.D0) {
                c0684j2A = c0722p1.a(c0767v3, this.a, c0759u2);
            } else {
                c0684j2A = null;
            }
        } else if (c0767v2 != c0722p1.B2) {
            c0684j2A = null;
        } else if (this.d.contains(c0684j2.f)) {
            C0722p1 c0722p2 = this.g;
            c0684j2A = c0722p2.a(c0722p2.B2, this.a, c0684j2.f);
        } else if (this.e.contains(c0684j2.f)) {
            C0722p1 c0722p3 = this.g;
            c0684j2A = c0722p3.a(c0722p3.B2, this.b, c0684j2.f);
        } else if (this.f.contains(c0684j2.f)) {
            C0722p1 c0722p4 = this.g;
            c0684j2A = c0722p4.a(c0722p4.B2, this.c, c0684j2.f);
        } else {
            c0684j2A = null;
        }
        if (!h) {
            if ((c0684j2A != null) != b(c0684j2)) {
                throw new AssertionError();
            }
        }
        return c0684j2A;
    }

    public final boolean b(C0684j2 c0684j2) {
        C0767v2 c0767v2 = c0684j2.e;
        C0722p1 c0722p1 = this.g;
        if (c0767v2 == c0722p1.C2) {
            C0759u2 c0759u2 = c0684j2.f;
            return c0759u2 == c0722p1.C0 || c0759u2 == c0722p1.D0;
        }
        if (c0767v2 == c0722p1.B2) {
            return this.d.contains(c0684j2.f) || this.e.contains(c0684j2.f) || this.f.contains(c0684j2.f);
        }
        return false;
    }

    private Set a(Object... objArr) {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        for (Object obj : objArr) {
            C0759u2 c0759u2C = obj instanceof String ? this.g.c((String) obj) : (C0759u2) obj;
            identityHashMap.put(c0759u2C, c0759u2C);
        }
        return identityHashMap.keySet();
    }
}
