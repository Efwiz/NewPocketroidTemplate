package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.e3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3712e3 extends C3737i3 {
    private static final Origin w = new C3694b3(Origin.root());
    private static final Origin x = new C3700c3(Origin.root());
    public static final /* synthetic */ int y = 0;
    private final Set t;
    final C3732h3 u;
    private ConcurrentHashMap v;

    private C3712e3(Origin origin, Position position, String str, List list, C3774o2 c3774o2, C3774o2 c3774o3, boolean z, I2 i2, AbstractC3833y2 abstractC3833y2, List list2, G3 g3, boolean z2, List list3, C3732h3 c3732h3, Set set) {
        super(origin, position, str, list, c3774o2, c3774o3, z, i2, abstractC3833y2, list2, g3, z2, list3, EnumC3758l3.CONDITIONAL, C3742j3.a().a());
        this.v = new ConcurrentHashMap();
        this.u = c3732h3;
        this.t = set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3775o3 b(C0722p1 c0722p1, C3775o3 c3775o3) {
        return c3775o3.a(c0722p1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3775o3 c(C0722p1 c0722p1, C3775o3 c3775o3) {
        return c3775o3.a(c0722p1);
    }

    @Override // com.android.tools.r8.shaking.C3737i3, com.android.tools.r8.shaking.S2
    final String F() {
        return PsiKeyword.IF;
    }

    public final Set I() {
        Set setKeySet = this.v.keySet();
        this.v = null;
        return setKeySet;
    }

    public final Set J() {
        return this.t;
    }

    public final C3732h3 K() {
        return this.u;
    }

    public final void a(com.android.tools.r8.graph.Y0 y0) {
        ConcurrentHashMap concurrentHashMap = this.v;
        if (concurrentHashMap != null) {
            concurrentHashMap.put(y0, y0);
        }
    }

    @Override // com.android.tools.r8.shaking.C3737i3, com.android.tools.r8.shaking.S2, com.android.tools.r8.shaking.B2
    public final boolean equals(Object obj) {
        if ((obj instanceof C3712e3) && this.u.equals(((C3712e3) obj).u)) {
            return super.equals(obj);
        }
        return false;
    }

    @Override // com.android.tools.r8.shaking.C3737i3, com.android.tools.r8.shaking.S2, com.android.tools.r8.shaking.B2
    public final int hashCode() {
        return this.u.hashCode() + (super.hashCode() * 3);
    }

    @Override // com.android.tools.r8.shaking.S2
    public final C3712e3 s() {
        return this;
    }

    @Override // com.android.tools.r8.shaking.S2
    protected final Iterable x() {
        return AbstractC0984Fi.a(super.x(), this.u.x());
    }

    protected final Y1 b(final C0722p1 c0722p1) {
        List<C3775o3> list = j() != null ? (List) j().stream().filter(new Predicate() { // from class: com.android.tools.r8.shaking.e3$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3712e3.b((C3775o3) obj);
            }
        }).map(new Function() { // from class: com.android.tools.r8.shaking.e3$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3712e3.c(c0722p1, (C3775o3) obj);
            }
        }).collect(Collectors.toList()) : null;
        X1 x1G = Y1.G();
        x1G.a = x;
        X1 x1 = (X1) ((X1) x1G.e()).a(G3.a(c0722p1, b()));
        x1.f = a();
        X1 x2 = (X1) x1.e();
        x2.g = k();
        X1 x3 = (X1) x2.e();
        x3.i = d();
        X1 x4 = (X1) x3.e();
        x4.h = e();
        X1 x5 = (X1) ((X1) ((X1) x4.e()).a(c().a(c0722p1))).b(G3.a(c0722p1, f()));
        x5.l = g() != null ? g().a(c0722p1) : null;
        X1 x6 = (X1) x5.e();
        x6.m = h();
        return ((X1) ((X1) x6.e()).c(list)).f();
    }

    protected final C3712e3 a(final C0722p1 c0722p1, Set set) {
        return new C3712e3(l(), m(), n(), G3.a(c0722p1, b()), a(), k(), e(), d(), c().a(c0722p1), G3.a(c0722p1, f()), g() == null ? null : g().a(c0722p1), h(), j() != null ? (List) j().stream().map(new Function() { // from class: com.android.tools.r8.shaking.e3$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3712e3.a(c0722p1, (C3775o3) obj);
            }
        }).collect(Collectors.toList()) : null, this.u.a(c0722p1), set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3775o3 a(C0722p1 c0722p1, C3775o3 c3775o3) {
        return c3775o3.a(c0722p1);
    }

    protected final C3792s a(C0722p1 c0722p1) {
        return new C3792s(w, Position.UNKNOWN, null, G3.a(c0722p1, b()), a(), k(), e(), d(), c().a(c0722p1), G3.a(c0722p1, f()), g() == null ? null : g().a(c0722p1), h(), AbstractC1639am.i(), r.NEVER);
    }

    protected final R0 a(final C0722p1 c0722p1, Q0 q0) {
        if (j() == null || j().isEmpty()) {
            return null;
        }
        return new R0(w, Position.UNKNOWN, null, G3.a(c0722p1, b()), a(), k(), e(), d(), c().a(c0722p1), G3.a(c0722p1, f()), g() != null ? g().a(c0722p1) : null, h(), (List) j().stream().filter(new Predicate() { // from class: com.android.tools.r8.shaking.e3$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3712e3.a((C3775o3) obj);
            }
        }).map(new Function() { // from class: com.android.tools.r8.shaking.e3$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3712e3.b(c0722p1, (C3775o3) obj);
            }
        }).collect(Collectors.toList()), q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C3775o3 c3775o3) {
        return c3775o3.i().b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3775o3 c3775o3) {
        return c3775o3.i().b();
    }

    @Override // com.android.tools.r8.shaking.S2, com.android.tools.r8.shaking.B2
    protected final StringBuilder a(StringBuilder sb) {
        super.a(sb);
        sb.append('\n');
        return this.u.a(sb);
    }
}
