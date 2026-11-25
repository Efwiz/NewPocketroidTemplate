package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C1549Za;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class J3 {
    static final /* synthetic */ boolean e = true;
    private final int a;
    private final C0767v2 b;
    private final C0767v2 c;
    private final C0759u2 d;

    public J3(int i, C0767v2 c0767v2, C0767v2 c0767v3, C0759u2 c0759u2) {
        if (!e && c0767v2 == null) {
            throw new AssertionError();
        }
        this.a = i;
        this.b = c0767v2;
        this.c = c0767v3;
        this.d = c0759u2;
    }

    public static J3 a(C0767v2 c0767v2, C0767v2 c0767v3) {
        return new J3(0, c0767v2, c0767v3, C0722p1.T5);
    }

    public C0767v2 b() {
        return this.b;
    }

    public C0759u2 c() {
        return this.d;
    }

    public C0767v2 d() {
        return this.c;
    }

    public final boolean e() {
        return this.d == null;
    }

    public final boolean f() {
        return this.d != null;
    }

    public final String toString() {
        String hexString = Integer.toHexString(this.a);
        String strB1 = this.b.b1();
        C0767v2 c0767v2 = this.c;
        String strB2 = c0767v2 == null ? "null" : c0767v2.b1();
        C0759u2 c0759u2 = this.d;
        return "[access : " + hexString + ", inner: " + strB1 + ", outer: " + strB2 + ", innerName: " + (c0759u2 == null ? "(anonymous)" : c0759u2.toString()) + "]";
    }

    public final void a(Consumer consumer) {
        C0767v2 c0767v2 = this.b;
        if (c0767v2 != null) {
            consumer.accept(c0767v2);
        }
        C0767v2 c0767v3 = this.c;
        if (c0767v3 != null) {
            consumer.accept(c0767v3);
        }
    }

    public int a() {
        return this.a;
    }

    public final void a(C1549Za c1549Za, AbstractC3649o0 abstractC3649o0, com.android.tools.r8.utils.A1 a1) {
        String strD = abstractC3649o0.d(this.b);
        C0767v2 c0767v2 = this.c;
        c1549Za.a(this.a, strD, c0767v2 == null ? null : abstractC3649o0.d(c0767v2), this.d != null ? abstractC3649o0.a(this, a1).toString() : null);
    }

    public final void a(C0702m c0702m, com.android.tools.r8.dex.l0 l0Var) {
        C0767v2 c0767v2 = this.b;
        c0767v2.getClass();
        if (l0Var.a(c0767v2)) {
            C0759u2 c0759u2C = c0702m.z().c(c0767v2);
            c0759u2C.getClass();
            l0Var.a(c0759u2C);
        }
        C0767v2 c0767v3 = this.c;
        if (c0767v3 != null && l0Var.a(c0767v3)) {
            C0759u2 c0759u2C2 = c0702m.z().c(c0767v3);
            c0759u2C2.getClass();
            l0Var.a(c0759u2C2);
        }
        C0759u2 c0759u2 = this.d;
        if (c0759u2 != null) {
            l0Var.a(c0759u2);
        }
    }
}
