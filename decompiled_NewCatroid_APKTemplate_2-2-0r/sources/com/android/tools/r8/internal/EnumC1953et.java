package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.EnumC0691k2;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.et, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EnumC1953et {
    public static final EnumC1953et c = new EnumC1953et(0, 112, 118, "DIRECT");
    public static final EnumC1953et d = new EnumC1953et(1, 114, 120, Namer.CLASS_KIND_INTERFACE);
    public static final EnumC1953et e = new EnumC1953et(2, 113, 119, "STATIC");
    public static final EnumC1953et f = new EnumC1953et(3, 111, 117, "SUPER");
    public static final EnumC1953et g = new EnumC1953et(4, 110, 116, "VIRTUAL");
    public static final EnumC1953et h = new EnumC1953et(5, 35, -1, "NEW_ARRAY");
    public static final EnumC1953et i = new EnumC1953et(6, -1, -1, "MULTI_NEW_ARRAY");
    public static final EnumC1953et j = new EnumC1953et(8, 250, 251, "POLYMORPHIC");
    static final /* synthetic */ boolean k = true;
    private final int a;
    private final int b;

    private EnumC1953et(int i2, int i3, int i4, String str) {
        super(str, i2);
        this.a = i3;
        this.b = i4;
    }

    public static EnumC1953et a(int i2, C0684j2 c0684j2, AbstractC0748t0 abstractC0748t0, C0702m c0702m, AbstractC1476Wj abstractC1476Wj) {
        switch (i2) {
            case 182:
                return (!c0702m.a().A4.b(c0684j2) || c0702m.Y().D1()) ? g : j;
            case 183:
                return a(c0684j2, abstractC0748t0, c0702m, abstractC1476Wj);
            case 184:
                return e;
            case 185:
                return d;
            default:
                throw new C3405y10("unknown CfInvoke opcode " + i2);
        }
    }

    public final int b() {
        if (k || this.a >= 0) {
            return this.a;
        }
        throw new AssertionError();
    }

    public final int c() {
        if (k || this.b >= 0) {
            return this.b;
        }
        throw new AssertionError();
    }

    public final boolean d() {
        return this == e;
    }

    public final boolean e() {
        return this == f;
    }

    public final boolean f() {
        return this == g;
    }

    public static EnumC1953et a(C0684j2 c0684j2, AbstractC0748t0 abstractC0748t0, C0702m c0702m, AbstractC1476Wj abstractC1476Wj) {
        AbstractC0727q0 abstractC0727q0C;
        if (c0684j2.b(c0702m.a())) {
            return c;
        }
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        C0684j2 c0684j2A = abstractC1476WjE.a(abstractC1476Wj, abstractC0748t0.getReference());
        if (c0684j2.B0() != c0684j2A.B0()) {
            if (c0702m.Y().Y0() && c0702m.Y().V() && abstractC0748t0.a().D1() && (abstractC0727q0C = c0702m.c(c0684j2.B0())) != null && abstractC0727q0C.D1()) {
                AbstractC0727q0 abstractC0727q0A = abstractC0748t0.a();
                if (abstractC0727q0C.D1() && abstractC0727q0A.D1() && abstractC0727q0C.g1() == abstractC0727q0A.g1()) {
                    return c;
                }
            }
            return f;
        }
        C0684j2 reference = abstractC0748t0.getReference();
        EnumC1953et enumC1953et = c;
        KE keA = abstractC1476WjE.a(c0684j2, reference, enumC1953et, (AbstractC1476Wj) null);
        if (keA.f().d() || keA.f().f()) {
            return enumC1953et;
        }
        com.android.tools.r8.graph.W0 w0C = abstractC0748t0.a().c((C0684j2) keA.b());
        if (w0C == null) {
            return f;
        }
        if (abstractC1476WjE.a(abstractC1476Wj, w0C.getReference()).B0() != c0684j2A.B0()) {
            return f;
        }
        if (abstractC0748t0.a().isInterface() || (c0702m.L() && c0702m.g0().e(c0684j2A.B0()))) {
            if (w0C.R0()) {
                return f;
            }
        } else if (!k && !w0C.O0() && !keA.f().f()) {
            throw new AssertionError();
        }
        return enumC1953et;
    }

    public final int a() {
        int iOrdinal = ordinal();
        if (iOrdinal == 8) {
            return 182;
        }
        if (iOrdinal == 0) {
            return 183;
        }
        if (iOrdinal == 1) {
            return 185;
        }
        if (iOrdinal == 2) {
            return 184;
        }
        if (iOrdinal == 3) {
            return 183;
        }
        if (iOrdinal == 4) {
            return 182;
        }
        throw new C3405y10();
    }

    public final EnumC0691k2 a(C0684j2 c0684j2) {
        int iOrdinal = ordinal();
        if (iOrdinal == 0) {
            if (c0684j2.f.toString().equals("<init>")) {
                return EnumC0691k2.h;
            }
            return EnumC0691k2.i;
        }
        if (iOrdinal == 1) {
            return EnumC0691k2.j;
        }
        if (iOrdinal == 2) {
            return EnumC0691k2.f;
        }
        if (iOrdinal == 3) {
            return EnumC0691k2.k;
        }
        if (iOrdinal != 4) {
            throw new C3405y10("Conversion to method handle with unexpected invoke type: " + this);
        }
        return EnumC0691k2.g;
    }
}
