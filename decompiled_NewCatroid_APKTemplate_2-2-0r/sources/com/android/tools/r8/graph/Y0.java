package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.Reference;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class Y0 extends AbstractC0642e2<T0, Y0> {
    public final C0767v2 h;

    Y0(C0767v2 c0767v2, C0767v2 c0767v3, C0759u2 c0759u2, boolean z) {
        super(c0759u2, c0767v2);
        this.h = c0767v3;
        if (z || c0759u2.y0()) {
            return;
        }
        throw new C1367Tb("Field name '" + c0759u2.toString() + "' cannot be represented in dex format.");
    }

    @Override // com.android.tools.r8.graph.AbstractC0642e2
    public final Iterable D0() {
        return Collections.singleton(this.h);
    }

    public FieldReference E0() {
        return Reference.field(Reference.classFromDescriptor(this.e.b1()), this.f.toString(), Reference.typeFromDescriptor(this.h.b1()));
    }

    public final String F0() {
        return this.e + "." + this.f;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (Y0) xVar);
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final boolean c(Object obj) {
        if (!(obj instanceof Y0)) {
            return false;
        }
        Y0 y0 = (Y0) obj;
        return this.e.equals(y0.e) && this.h.equals(y0.h) && this.f.equals(y0.f);
    }

    public C0767v2 getType() {
        return this.h;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.Y0$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                Y0.a(zVar);
            }
        };
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final String q0() {
        return this.e.q0() + "->" + this.f + ":" + this.h.q0();
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public String r0() {
        return this.h.r0() + " " + this.e.r0() + "." + this.f.r0();
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final int s0() {
        return (this.f.hashCode() * 31) + (this.h.hashCode() * 7) + this.e.hashCode();
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final Y0 t0() {
        return this;
    }

    public final String toString() {
        return "Field " + this.h + " " + this.e + "." + this.f;
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final boolean x0() {
        return true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0642e2
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final AbstractC0735r0 a(AbstractC0727q0 abstractC0727q0) {
        T0 t0A;
        if (abstractC0727q0 == null || (t0A = abstractC0727q0.j.a(this)) == null) {
            return null;
        }
        return AbstractC0735r0.a(abstractC0727q0, t0A);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.Y0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Y0) obj).B0();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.Y0$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Y0) obj).C0();
            }
        }).c(new Y0$$ExternalSyntheticLambda4());
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final int a(AbstractC0744s2 abstractC0744s2) {
        if (abstractC0744s2.x0()) {
            return compareTo(abstractC0744s2.t0());
        }
        if (abstractC0744s2.y0()) {
            int iCompareTo = B0().compareTo(abstractC0744s2.C());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
            return -1;
        }
        int iCompareTo2 = B0().compareTo(abstractC0744s2.w0());
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        return 1;
    }

    public final Y0 c(C0722p1 c0722p1, C0767v2 c0767v2) {
        return c0722p1.a(this.e, c0767v2, this.f);
    }

    public final T0 c(AbstractC0727q0 abstractC0727q0) {
        if (abstractC0727q0 != null) {
            return abstractC0727q0.j.a(this);
        }
        return null;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this.e);
        oVar.a(this.f);
        Collections.singleton(this.h).forEach(new Y0$$ExternalSyntheticLambda1(oVar));
    }

    public final m5 a(C0729q2 c0729q2) {
        if (c0729q2 != null) {
            return c0729q2.d(this);
        }
        return null;
    }

    @Override // com.android.tools.r8.graph.AbstractC0642e2
    public final Object a(Function function, Function function2) {
        return function.apply(this);
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final Object a(Function function, Function function2, Function function3) {
        return function2.apply(this);
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final void a(Consumer consumer, Consumer consumer2, Consumer consumer3) {
        consumer2.accept(this);
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        if (l.a(this)) {
            C0767v2 c0767v2 = this.e;
            c0767v2.getClass();
            if (l.a(c0767v2)) {
                C0759u2 c0759u2C = c0702m.z().c(c0767v2);
                c0759u2C.getClass();
                l.a(c0759u2C);
            }
            C0767v2 c0767v3 = this.h;
            c0767v3.getClass();
            if (l.a(c0767v3)) {
                C0759u2 c0759u2C2 = c0702m.z().c(c0767v3);
                c0759u2C2.getClass();
                l.a(c0759u2C2);
            }
            C0759u2 c0759u2A = c0702m.z().a(this);
            c0759u2A.getClass();
            l.a(c0759u2A);
        }
    }

    @Override // com.android.tools.r8.graph.I3
    public final int a(C0645e5 c0645e5) {
        return c0645e5.a(this);
    }

    @Override // com.android.tools.r8.graph.AbstractC0642e2
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Y0 b(C0767v2 c0767v2, C0722p1 c0722p1) {
        return c0722p1.a(c0767v2, this.h, this.f);
    }
}
