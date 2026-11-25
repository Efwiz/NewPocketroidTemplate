package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.beust.jcommander.Parameters;
import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class S2 extends B2 {
    static final /* synthetic */ boolean q = true;
    private boolean o;
    private boolean p;

    S2(Origin origin, Position position, String str, List list, C3774o2 c3774o2, C3774o2 c3774o3, boolean z, I2 i2, AbstractC3833y2 abstractC3833y2, List list2, G3 g3, boolean z2, List list3) {
        super(origin, position, str, list, c3774o2, c3774o3, z, i2, abstractC3833y2, list2, g3, z2, list3);
        this.o = false;
        this.p = false;
    }

    public boolean A() {
        return this instanceof C3732h3;
    }

    public boolean B() {
        return this instanceof S3;
    }

    public final boolean C() {
        return this.o;
    }

    public final void D() {
        this.o = true;
    }

    String E() {
        return null;
    }

    abstract String F();

    final Iterable a(C0702m c0702m, B5 b5, Collection collection) {
        List<C0767v2> listA = c().a();
        if (listA != null) {
            return C0729q2.a((Collection) listA, (com.android.tools.r8.graph.Q0) new R2(this, c0702m));
        }
        if (o()) {
            G3 g3G = g();
            g3G.getClass();
            if (g3G instanceof G3.b) {
                C0767v2 c0767v2C = g().c();
                if (c0702m.g0() == null || !c0702m.g0().d(c0767v2C)) {
                    return C0729q2.a((Collection) b5.j(c0767v2C), (com.android.tools.r8.graph.Q0) c0702m);
                }
                AbstractC0727q0 abstractC0727q0C = c0702m.c(c0702m.g0().c(c0767v2C));
                if (q || (abstractC0727q0C != null && abstractC0727q0C.e0())) {
                    return AbstractC0984Fi.a(AbstractC1639am.c(abstractC0727q0C.a0()), C0729q2.a((Collection) b5.j(c0767v2C), (com.android.tools.r8.graph.Q0) c0702m));
                }
                throw new AssertionError();
            }
        }
        return collection;
    }

    @Override // com.android.tools.r8.shaking.B2
    public boolean equals(Object obj) {
        if (!(obj instanceof S2)) {
            return false;
        }
        S2 s2 = (S2) obj;
        if (this.o == s2.o && Objects.equals(F(), s2.F()) && Objects.equals(E(), s2.E())) {
            return super.equals(s2);
        }
        return false;
    }

    @Override // com.android.tools.r8.shaking.B2
    public int hashCode() {
        int iHashCode = (F().hashCode() * 9) + (this.o ? 1 : 0);
        String strE = E();
        return (iHashCode * 3) + (strE != null ? strE.hashCode() : 0) + super.hashCode();
    }

    public boolean p() {
        return this instanceof C3816w;
    }

    public C3792s q() {
        return null;
    }

    public C3820w2 r() {
        return null;
    }

    public C3712e3 s() {
        return null;
    }

    public C3732h3 t() {
        return null;
    }

    public Q3 u() {
        return null;
    }

    public S3 v() {
        return null;
    }

    public final void w() {
        this.p = true;
    }

    protected Iterable<O3> x() {
        Iterable a2$$ExternalSyntheticLambda4;
        List<C3775o3> listJ = j();
        Iterable[] iterableArr = new Iterable[5];
        iterableArr[0] = G3.a(b());
        iterableArr[1] = AbstractC3833y2.a(c());
        iterableArr[2] = G3.a(f());
        iterableArr[3] = G3.a(g());
        if (listJ != null) {
            Stream streamFlatMap = listJ.stream().map(new Function() { // from class: com.android.tools.r8.shaking.S2$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((C3775o3) obj).k();
                }
            }).flatMap(new Function() { // from class: com.android.tools.r8.shaking.S2$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return S2.a((Iterable) obj);
                }
            });
            Objects.requireNonNull(streamFlatMap);
            a2$$ExternalSyntheticLambda4 = new A2$$ExternalSyntheticLambda4(streamFlatMap);
        } else {
            a2$$ExternalSyntheticLambda4 = new Iterable() { // from class: com.android.tools.r8.shaking.S2$$ExternalSyntheticLambda2
                @Override // java.lang.Iterable
                public final Iterator iterator() {
                    return Collections.emptyIterator();
                }
            };
        }
        iterableArr[4] = a2$$ExternalSyntheticLambda4;
        return AbstractC0984Fi.a(iterableArr);
    }

    public boolean y() {
        return this instanceof C3792s;
    }

    public boolean z() {
        return this instanceof C3820w2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Stream a(Iterable iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    @Override // com.android.tools.r8.shaking.B2
    protected StringBuilder a(StringBuilder sb) {
        sb.append(Parameters.DEFAULT_OPTION_PREFIXES);
        sb.append(F());
        com.android.tools.r8.utils.Y2.a(sb, ",", E());
        sb.append(StringUtil.SPACE);
        super.a(sb);
        return sb;
    }
}
