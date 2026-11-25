package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.J5;
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
public final class T1 extends M {
    static final /* synthetic */ boolean i = true;
    public final short e;
    public final char f;
    public C0684j2 g;
    public C0737r2 h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    T1(int i2, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(interfaceC0409a);
        C0684j2[] c0684j2ArrD = c0656f5.d();
        C0737r2[] c0737r2ArrE = c0656f5.e();
        this.e = (short) i2;
        this.g = c0684j2ArrD[B1.a(interfaceC0409a)];
        this.f = B1.a(interfaceC0409a);
        this.h = c0737r2ArrE[B1.a(interfaceC0409a)];
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "invoke-polymorphic/range";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return ((((this.f << 24) | (this.h.hashCode() << 12)) | (this.g.hashCode() << 4)) | this.e) ^ T1.class.hashCode();
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final C0684j2 s() {
        return this.g;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "InvokePolymorphicRange";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 251;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.android.tools.r8.dex.code.B1
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new T1$$ExternalSyntheticLambda4());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.T1$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((T1) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.T1$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((T1) obj).f;
            }
        }).c(new Function() { // from class: com.android.tools.r8.dex.code.T1$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((T1) obj).g;
            }
        }).c(new Function() { // from class: com.android.tools.r8.dex.code.T1$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((T1) obj).h;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.android.tools.r8.dex.code.B1
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (T1) b1, new T1$$ExternalSyntheticLambda4());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.a(this.g);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        StringBuilder sb = new StringBuilder("{ v");
        char c = this.f;
        sb.append((int) c);
        if (this.e != 1) {
            sb.append(" .. v").append((c + this.e) - 1);
        }
        sb.append(" } ");
        sb.append(p2.b(this.g));
        sb.append(p2.a(this.h));
        return b(sb.toString());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.a(EnumC1953et.j, this.g, this.h, this.e, this.f);
    }

    public T1(int i2, int i3, C0684j2 c0684j2, C0737r2 c0737r2) {
        boolean z = i;
        if (!z && (i2 < 0 || i2 > 65535)) {
            throw new AssertionError();
        }
        if (!z && (i3 < 0 || i3 > 255)) {
            throw new AssertionError();
        }
        this.f = (char) i2;
        this.e = (short) i3;
        this.g = c0684j2;
        this.h = c0737r2;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        boolean z = i;
        if (!z && !c2363kB.a().A4.b(this.g)) {
            throw new AssertionError();
        }
        if (!z) {
            C0684j2 c0684j2 = this.g;
            if (c0684j2 != abstractC1476Wj.a(c0684j2, o5Var.getReference(), EnumC1953et.j, (AbstractC1476Wj) null).b()) {
                throw new AssertionError();
            }
        }
        C0737r2 c0737r2A = c2363kB.a(this.h);
        B1.a(this.e, shortBuffer, 251);
        B1.a(this.g, shortBuffer, c0645e5);
        shortBuffer.put((short) this.f);
        B1.a(c0737r2A, shortBuffer, c0645e5);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        StringBuilder sb = new StringBuilder("{ v");
        char c = this.f;
        sb.append((int) c);
        if (this.e != 1) {
            sb.append(" .. v").append((c + this.e) - 1);
        }
        sb.append(" }, ");
        sb.append(this.g.q0());
        sb.append(", ");
        C0737r2 c0737r2 = this.h;
        c0737r2.getClass();
        sb.append(c0737r2.a(AbstractC3649o0.a()));
        return a(sb.toString());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        C0684j2 c0684j2 = this.g;
        C0684j2 reference = o5Var.getReference();
        EnumC1953et enumC1953et = EnumC1953et.j;
        KE keA = abstractC1476WjE.a(c0684j2, reference, enumC1953et, (AbstractC1476Wj) null);
        if (!i && keA.f() != enumC1953et) {
            throw new AssertionError();
        }
        ((C0684j2) keA.b()).a(c0702m, l);
        c2363kB.a(this.h).a(c0702m, l);
    }
}
