package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.EnumC1266Ph;
import java.nio.ShortBuffer;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.z1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0538z1 extends K {
    static final /* synthetic */ boolean g = true;
    private final int e;
    private final C0767v2 f;

    public C0538z1(int i, C0767v2 c0767v2) {
        if (!g && !c0767v2.O0()) {
            throw new AssertionError();
        }
        this.e = i;
        this.f = c0767v2;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "initclass";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return ((this.f.hashCode() << 8) | this.e) ^ C0538z1.class.hashCode();
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int r() {
        return 256;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "InitClass";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        throw new C3405y10();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.z1$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((C0538z1) obj).e;
            }
        }).c(new Function() { // from class: com.android.tools.r8.dex.code.z1$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0538z1) obj).f;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new z1$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.c(this.e, this.f);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + this.e + ", " + p2.a(this.f));
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        c0702m.N().a(AbstractC2964s2.a(c0702m.E(), this.f)).a(c0702m, l);
    }

    private static int a(com.android.tools.r8.graph.Y0 y0) {
        EnumC1266Ph enumC1266PhA = EnumC1266Ph.a(y0.h);
        switch (enumC1266PhA.ordinal()) {
            case 0:
                return 98;
            case 1:
                return 99;
            case 2:
                return 100;
            case 3:
                return 101;
            case 4:
                return 102;
            case 5:
            case 6:
                return 96;
            case 7:
            case 8:
                return 97;
            default:
                throw new C3405y10("Unexpected type: " + enumC1266PhA);
        }
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.b(this.f);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        C0767v2 c0767v2 = this.f;
        abstractC1476Wj.getClass();
        com.android.tools.r8.graph.Y0 y0A = c0645e5.a(abstractC1476Wj.a(AbstractC1476Wj.e(), c0767v2));
        B1.a(this.e, shortBuffer, a(y0A));
        B1.a(y0A, shortBuffer, c0645e5);
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (C0538z1) b1, new z1$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        return a(RegisterSpec.PREFIX + this.e + ", " + this.f.q0());
    }
}
