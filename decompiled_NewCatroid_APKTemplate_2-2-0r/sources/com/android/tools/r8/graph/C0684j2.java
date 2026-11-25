package com.android.tools.r8.graph;

import androidx.exifinterface.media.ExifInterface;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.graph.j2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0684j2 extends AbstractC0642e2<W0, C0684j2> {
    public final C0737r2 h;

    C0684j2(C0767v2 c0767v2, C0737r2 c0737r2, C0759u2 c0759u2, boolean z) {
        super(c0759u2, c0767v2);
        this.h = c0737r2;
        if (z || c0759u2.z0()) {
            return;
        }
        throw new C1367Tb("Method name '" + c0759u2 + "' in class '" + c0767v2.r0() + "' cannot be represented in dex format.");
    }

    @Override // com.android.tools.r8.graph.AbstractC0642e2
    public Iterable<C0767v2> D0() {
        C0737r2 c0737r2 = this.h;
        return AbstractC0984Fi.a(Collections.singleton(c0737r2.e), c0737r2.f);
    }

    public MethodReference E0() {
        ArrayList arrayList = new ArrayList();
        for (C0767v2 c0767v2 : this.h.f.a) {
            arrayList.add(Reference.typeFromDescriptor(c0767v2.b1()));
        }
        String strB1 = this.h.e.b1();
        return Reference.method(Reference.classFromDescriptor(this.e.b1()), this.f.toString(), arrayList, strB1.equals(ExifInterface.GPS_MEASUREMENT_INTERRUPTED) ? null : Reference.typeFromDescriptor(strB1));
    }

    public int F0() {
        return this.h.f.size();
    }

    public C0800x2 G0() {
        return this.h.f;
    }

    public C0737r2 H0() {
        return this.h;
    }

    public C0767v2 I0() {
        return this.h.e;
    }

    public final AbstractC0717o2 J0() {
        return new C0705m2(this);
    }

    public String K0() {
        return this.e + "." + this.f;
    }

    public String L0() {
        return a(false, true);
    }

    public final String M0() {
        return a(true, false);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (C0684j2) xVar);
    }

    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public final W0 c(AbstractC0727q0 abstractC0727q0) {
        if (abstractC0727q0 != null) {
            return (W0) abstractC0727q0.a((AbstractC0642e2) this);
        }
        return null;
    }

    public C0767v2 k(int i) {
        return this.h.k(i);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.j2$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0684j2.a(zVar);
            }
        };
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public String q0() {
        return this.e.q0() + "->" + this.f + this.h.q0();
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public String r0() {
        return a(true, true);
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final int s0() {
        return (this.f.hashCode() * 31) + (this.h.hashCode() * 29) + (this.e.hashCode() * 7);
    }

    public String toString() {
        return r0();
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final C0684j2 v0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final boolean y0() {
        return true;
    }

    final boolean b(C0702m c0702m, com.android.tools.r8.dex.L l) {
        if (!l.a(this)) {
            return false;
        }
        C0767v2 c0767v2 = this.e;
        c0767v2.getClass();
        if (l.a(c0767v2)) {
            C0759u2 c0759u2C = c0702m.z().c(c0767v2);
            c0759u2C.getClass();
            l.a(c0759u2C);
        }
        this.h.a(c0702m, l);
        return true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final boolean c(Object obj) {
        if (!(obj instanceof C0684j2)) {
            return false;
        }
        C0684j2 c0684j2 = (C0684j2) obj;
        return this.e.equals(c0684j2.e) && this.f.equals(c0684j2.f) && this.h.equals(c0684j2.h);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.j2$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0684j2) obj).B0();
            }
        }).c(new j2$$ExternalSyntheticLambda2()).c(new Function() { // from class: com.android.tools.r8.graph.j2$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0684j2) obj).h;
            }
        });
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final int a(AbstractC0744s2 abstractC0744s2) {
        if (abstractC0744s2.y0()) {
            return compareTo(abstractC0744s2.v0());
        }
        int iA = B0().compareTo(abstractC0744s2.C());
        if (iA != 0) {
            return iA;
        }
        return 1;
    }

    public final boolean b(C0684j2 c0684j2) {
        if (c0684j2 != this) {
            C0737r2 c0737r2H0 = c0684j2.H0();
            C0759u2 c0759u2C0 = c0684j2.C0();
            if (this.h != c0737r2H0 || this.f != c0759u2C0) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this.e);
        oVar.a(this.f);
        D0().forEach(new Y0$$ExternalSyntheticLambda1(oVar));
    }

    public final boolean b(C0722p1 c0722p1) {
        c0722p1.getClass();
        return this.f == c0722p1.Y0;
    }

    public final boolean b(C0702m c0702m) {
        if (!b(c0702m.a())) {
            C0722p1 c0722p1A = c0702m.a();
            C0759u2 c0759u2C0 = C0();
            C0759u2 c0759u2 = c0722p1A.a1;
            c0759u2C0.getClass();
            if (!c0759u2C0.a(c0759u2.e) || c0702m.b(this) == null || !c0702m.E().a((AbstractC1476Wj) null, this).b(c0722p1A)) {
                C0722p1 c0722p1A2 = c0702m.a();
                C0759u2 c0759u2C1 = C0();
                C0759u2 c0759u3 = c0722p1A2.b1;
                c0759u2C1.getClass();
                if (!c0759u2C1.a(c0759u3.e)) {
                    return false;
                }
            }
        }
        return true;
    }

    public final C0767v2 a(int i, boolean z) {
        if (z) {
            return k(i);
        }
        if (i == 0) {
            return B0();
        }
        return k(i - 1);
    }

    public final int a(boolean z) {
        return com.android.tools.r8.utils.M.a(!z) + F0();
    }

    @Override // com.android.tools.r8.graph.AbstractC0642e2
    public final Object a(Function function, Function function2) {
        return function2.apply(this);
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final Object a(Function function, Function function2, Function function3) {
        return function3.apply(this);
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final void a(Consumer consumer, Consumer consumer2, Consumer consumer3) {
        consumer3.accept(this);
    }

    public final o5 a(C0729q2 c0729q2) {
        if (c0729q2 != null) {
            return c0729q2.f(this);
        }
        return null;
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        if (b(c0702m, l)) {
            C0759u2 c0759u2A = c0702m.z().a(this);
            c0759u2A.getClass();
            l.a(c0759u2A);
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0642e2
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final AbstractC0748t0 a(AbstractC0727q0 abstractC0727q0) {
        W0 w0B;
        if (abstractC0727q0 == null || (w0B = abstractC0727q0.k.b(this)) == null) {
            return null;
        }
        return AbstractC0748t0.a(abstractC0727q0, w0B);
    }

    @Override // com.android.tools.r8.graph.I3
    public final int a(C0645e5 c0645e5) {
        return c0645e5.a(this);
    }

    public boolean a(W0 w0) {
        return b(w0.getReference());
    }

    private String a(boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder();
        if (z2) {
            sb.append(I0().r0()).append(" ");
        }
        if (z) {
            sb.append(this.e.r0()).append(".");
        }
        sb.append(this.f).append("(");
        for (int i = 0; i < F0(); i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(this.h.f.a[i].r0());
        }
        return sb.append(Constant.AFTER_QUTO).toString();
    }

    @Override // com.android.tools.r8.graph.AbstractC0642e2
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0684j2 b(C0767v2 c0767v2, C0722p1 c0722p1) {
        c0767v2.getClass();
        return c0722p1.a(c0767v2, this.h, this.f);
    }

    public final C0684j2 a(String str, C0722p1 c0722p1) {
        return c0722p1.a(this.e, this.h, c0722p1.c(str));
    }

    public final C0684j2 a(C0722p1 c0722p1, C0759u2 c0759u2) {
        return c0722p1.a(this.e, this.h, c0759u2);
    }

    public final C0684j2 a(C0737r2 c0737r2, C0722p1 c0722p1) {
        return c0722p1.a(this.e, c0737r2, this.f);
    }
}
