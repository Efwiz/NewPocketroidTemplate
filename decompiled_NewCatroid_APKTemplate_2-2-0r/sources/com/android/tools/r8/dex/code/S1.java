package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.internal.KE;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.nio.ShortBuffer;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S1 extends M {
    static final /* synthetic */ boolean m = true;
    public final byte e;
    public final byte f;
    public final byte g;
    public final byte h;
    public final byte i;
    public final byte j;
    public C0684j2 k;
    public C0737r2 l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    S1(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(interfaceC0409a);
        C0684j2[] c0684j2ArrD = c0656f5.d();
        C0737r2[] c0737r2ArrE = c0656f5.e();
        this.j = (byte) (i & 15);
        this.e = (byte) ((i >> 4) & 15);
        this.k = c0684j2ArrD[B1.a(interfaceC0409a)];
        short sD = B1.d(interfaceC0409a);
        this.h = (byte) (sD & 15);
        this.i = (byte) ((sD >> 4) & 15);
        short sD2 = B1.d(interfaceC0409a);
        this.f = (byte) (sD2 & 15);
        this.g = (byte) ((sD2 >> 4) & 15);
        this.l = c0737r2ArrE[B1.a(interfaceC0409a)];
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "invoke-polymorphic";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return ((((((((this.l.hashCode() << 28) | (this.k.hashCode() << 24)) | (this.e << 20)) | (this.f << 16)) | (this.g << 12)) | (this.h << 8)) | (this.i << 4)) | this.j) ^ S1.class.hashCode();
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final C0684j2 s() {
        return this.k;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "InvokePolymorphic";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 250;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.android.tools.r8.dex.code.B1
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new S1$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.a(EnumC1953et.j, this.k, this.l, this.e, new int[]{this.f, this.g, this.h, this.i, this.j});
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.android.tools.r8.dex.code.B1
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (S1) b1, new S1$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        StringBuilder sb = new StringBuilder();
        a(sb, " ");
        sb.append(" ");
        sb.append(p2.b(this.k));
        sb.append(", ");
        sb.append(p2.a(this.l));
        return b(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.S1$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((S1) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.S1$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((S1) obj).f;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.S1$$ExternalSyntheticLambda3
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((S1) obj).g;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.S1$$ExternalSyntheticLambda4
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((S1) obj).h;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.S1$$ExternalSyntheticLambda5
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((S1) obj).i;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.S1$$ExternalSyntheticLambda6
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((S1) obj).j;
            }
        }).c(new Function() { // from class: com.android.tools.r8.dex.code.S1$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((S1) obj).k;
            }
        }).c(new Function() { // from class: com.android.tools.r8.dex.code.S1$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((S1) obj).l;
            }
        });
    }

    public S1(int i, C0684j2 c0684j2, C0737r2 c0737r2, int i2, int i3, int i4, int i5, int i6) {
        boolean z = m;
        if (!z && (i < 0 || i > 15)) {
            throw new AssertionError();
        }
        if (!z && (i2 < 0 || i2 > 15)) {
            throw new AssertionError();
        }
        if (!z && (i3 < 0 || i3 > 15)) {
            throw new AssertionError();
        }
        if (!z && (i4 < 0 || i4 > 15)) {
            throw new AssertionError();
        }
        if (!z && (i5 < 0 || i5 > 15)) {
            throw new AssertionError();
        }
        if (!z && (i6 < 0 || i6 > 15)) {
            throw new AssertionError();
        }
        this.e = (byte) i;
        this.k = c0684j2;
        this.l = c0737r2;
        this.f = (byte) i2;
        this.g = (byte) i3;
        this.h = (byte) i4;
        this.i = (byte) i5;
        this.j = (byte) i6;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        C0684j2 c0684j2 = this.k;
        C0684j2 reference = o5Var.getReference();
        EnumC1953et enumC1953et = EnumC1953et.j;
        KE keA = abstractC1476WjE.a(c0684j2, reference, enumC1953et, (AbstractC1476Wj) null);
        if (!m && keA.f() != enumC1953et) {
            throw new AssertionError();
        }
        ((C0684j2) keA.b()).a(c0702m, l);
        c2363kB.a(this.l).a(c0702m, l);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        boolean z = m;
        if (!z && !c2363kB.a().A4.b(this.k)) {
            throw new AssertionError();
        }
        if (!z) {
            C0684j2 c0684j2 = this.k;
            if (c0684j2 != abstractC1476Wj.a(c0684j2, o5Var.getReference(), EnumC1953et.j, (AbstractC1476Wj) null).b()) {
                throw new AssertionError();
            }
        }
        C0737r2 c0737r2A = c2363kB.a(this.l);
        B1.a(this.e, this.j, shortBuffer, 250);
        B1.a(this.k, shortBuffer, c0645e5);
        shortBuffer.put(B1.d(B1.e(this.i, this.h), B1.e(this.g, this.f)));
        B1.a(c0737r2A, shortBuffer, c0645e5);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        StringBuilder sb = new StringBuilder();
        a(sb, ", ");
        sb.append(", ");
        sb.append(this.k.q0());
        sb.append(", ");
        C0737r2 c0737r2 = this.l;
        c0737r2.getClass();
        sb.append(c0737r2.a(AbstractC3649o0.a()));
        return a(sb.toString());
    }

    private void a(StringBuilder sb, String str) {
        sb.append("{ ");
        int[] iArr = {this.f, this.g, this.h, this.i, this.j};
        for (int i = 0; i < this.e; i++) {
            if (i != 0) {
                sb.append(str);
            }
            sb.append(RegisterSpec.PREFIX).append(iArr[i]);
        }
        sb.append(" }");
    }
}
