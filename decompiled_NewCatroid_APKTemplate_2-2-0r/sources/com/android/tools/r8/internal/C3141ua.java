package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ua, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3141ua extends AbstractC2525mG {
    private static final C3141ua c = new C3141ua(EnumC3070ta.d, 0);
    private static final C3141ua d = new C3141ua(EnumC3070ta.b, 0);
    private static final C3141ua e = new C3141ua(EnumC3070ta.a, 0);
    private static final C3141ua f = new C3141ua(EnumC3070ta.e, 0);
    private static final C3141ua g = new C3141ua(EnumC3070ta.c, 0);
    private final int a;
    private final EnumC3070ta b;

    private C3141ua(EnumC3070ta enumC3070ta, int i) {
        this.b = enumC3070ta;
        this.a = i;
    }

    public static C3141ua g() {
        return e;
    }

    @Override // com.android.tools.r8.internal.AbstractC2525mG
    public final C3141ua a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2525mG
    public final boolean e() {
        EnumC3070ta enumC3070ta = this.b;
        enumC3070ta.getClass();
        return enumC3070ta != EnumC3070ta.a;
    }

    public final boolean equals(Object obj) {
        if (C3141ua.class != obj.getClass()) {
            return false;
        }
        C3141ua c3141ua = (C3141ua) obj;
        return this.a == c3141ua.a && this.b == c3141ua.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC2525mG
    public final boolean f() {
        EnumC3070ta enumC3070ta = this.b;
        enumC3070ta.getClass();
        return enumC3070ta == EnumC3070ta.e;
    }

    public final int hashCode() {
        return (this.b.ordinal() * 31) + this.a;
    }

    public static C3141ua a(EnumC3070ta enumC3070ta, int i) {
        return i > 0 ? new C3141ua(enumC3070ta, i) : a(enumC3070ta);
    }

    public static C3141ua a(EnumC3070ta enumC3070ta) {
        int iOrdinal = enumC3070ta.ordinal();
        if (iOrdinal == 0) {
            return e;
        }
        if (iOrdinal == 1) {
            return d;
        }
        if (iOrdinal == 2) {
            return g;
        }
        if (iOrdinal == 3) {
            return c;
        }
        if (iOrdinal != 4) {
            throw new C3405y10("Unexpected ClassNameMapping: " + enumC3070ta);
        }
        return f;
    }

    public final C0759u2 a(C0767v2 c0767v2, C0702m c0702m, AbstractC3649o0 abstractC3649o0) {
        return this.b.a(abstractC3649o0.c(c0767v2).toString(), c0702m.c(c0767v2), c0702m.a(), this.a);
    }
}
