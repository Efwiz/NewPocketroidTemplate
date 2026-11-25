package com.android.tools.r8.internal;

import com.android.tools.r8.errors.ConstantDynamicDesugarDiagnostic;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.position.MethodPosition;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1151Lc implements C7 {
    private final C0702m a;
    private final ConcurrentHashMap b = new ConcurrentHashMap();

    public C1151Lc(C0702m c0702m) {
        this.a = c0702m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var, String str) {
        this.a.Y().e.error(new ConstantDynamicDesugarDiagnostic(o5Var.getOrigin(), MethodPosition.create(o5Var), str));
    }

    private C3224ve b(final com.android.tools.r8.graph.o5 o5Var, final String str) {
        return C3224ve.a().a(new InterfaceC3147ue() { // from class: com.android.tools.r8.internal.Lc$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.internal.InterfaceC3147ue
            public final void a() {
                this.f$0.a(o5Var, str);
            }
        }).a();
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, final B7 b7) {
        b7.getClass();
        if (!(b7 instanceof Y6)) {
            return C3224ve.c();
        }
        Y6 y6D = b7.d();
        if (!y6D.U().isEmpty()) {
            return b(o5Var, "Unsupported dynamic constant (has arguments to bootstrap method)");
        }
        if (!y6D.T().d.g()) {
            return b(o5Var, "Unsupported dynamic constant (not invoke static)");
        }
        C0722p1 c0722p1A = this.a.a();
        C0684j2 c0684j2U0 = y6D.T().u0();
        C0767v2 c0767v2B0 = c0684j2U0.B0();
        if (c0767v2B0 == c0722p1A.m5) {
            return b(o5Var, "Unsupported dynamic constant (runtime provided bootstrap method)");
        }
        if (c0767v2B0 != o5Var.t()) {
            return b(o5Var, "Unsupported dynamic constant (different owner)");
        }
        if (c0684j2U0.H0().e != c0722p1A.E1 && c0684j2U0.H0().e != c0722p1A.Z1) {
            return b(o5Var, "Unsupported dynamic constant (unsupported constant type)");
        }
        if (c0684j2U0.H0().u0().size() != 3) {
            return b(o5Var, "Unsupported dynamic constant (unsupported signature)");
        }
        if (c0684j2U0.H0().u0().j(0) != c0722p1A.o5) {
            return b(o5Var, "Unsupported dynamic constant (unexpected type of first argument to bootstrap method");
        }
        if (c0684j2U0.H0().u0().j(1) != c0722p1A.X1) {
            return b(o5Var, "Unsupported dynamic constant (unexpected type of second argument to bootstrap method");
        }
        if (c0684j2U0.H0().u0().j(2) != c0722p1A.m2) {
            return b(o5Var, "Unsupported dynamic constant (unexpected type of third argument to bootstrap method");
        }
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.Lc$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(b7, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    private C1096Jc b(Y6 y6, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, InterfaceC1124Kc interfaceC1124Kc) {
        Map map = (Map) this.b.computeIfAbsent(o5Var.t(), new Function() { // from class: com.android.tools.r8.internal.Lc$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1151Lc.a((C0767v2) obj);
            }
        });
        C1096Jc c1096Jc = (C1096Jc) map.get(y6.V());
        if (c1096Jc == null) {
            synchronized (map) {
                c1096Jc = (C1096Jc) map.get(y6.V());
                if (c1096Jc == null) {
                    C1096Jc c1096JcA = a(y6, o5Var, c1230Ob, interfaceC1124Kc);
                    map.put(y6.V(), c1096JcA);
                    c1096Jc = c1096JcA;
                }
            }
        }
        return c1096Jc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(B7 b7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        Y6 y6D = b7.d();
        return b(y6D, o5Var, c1230Ob, g7).a(y6D, g7, c1230Ob);
    }

    @Override // com.android.tools.r8.internal.C7
    public final void a(com.android.tools.r8.graph.o5 o5Var, G7 g7) {
        Iterator<B7> it = o5Var.e().a1().K().I0().iterator();
        while (it.hasNext()) {
            a(o5Var, it.next()).d();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(C0767v2 c0767v2) {
        return new HashMap();
    }

    private C1096Jc a(final Y6 y6, final com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, InterfaceC1124Kc interfaceC1124Kc) {
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        C0729q2 c0729q2A = this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Lc$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.y;
            }
        }, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.Lc$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(n, o5Var, y6, (com.android.tools.r8.synthesis.P) obj);
            }
        });
        C1096Jc c1096Jc = (C1096Jc) n.a();
        c1096Jc.a(c0729q2A);
        interfaceC1124Kc.a(c1096Jc, o5Var);
        return c1096Jc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.utils.N n, com.android.tools.r8.graph.o5 o5Var, Y6 y6, com.android.tools.r8.synthesis.P p) {
        n.a(new C1096Jc(p, this.a, o5Var, y6));
    }
}
