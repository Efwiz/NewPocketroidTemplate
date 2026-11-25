package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.startup.diagnostic.MissingStartupProfileItemsDiagnostic;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2783pW {
    private final C0702m a;
    private final AbstractC3135uW b;

    private C2783pW(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.G() ? c0702m.C() : AbstractC3135uW.a(c0702m.Y(), new Function() { // from class: com.android.tools.r8.internal.pW$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2783pW.a((Origin) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ MissingStartupProfileItemsDiagnostic.a a(Origin origin) {
        return MissingStartupProfileItemsDiagnostic.a.c();
    }

    public static void a(C0702m c0702m) throws Exception {
        if (c0702m.Y().G0().e()) {
            C2783pW c2783pW = new C2783pW(c0702m);
            Set setA = c2783pW.a();
            Iterator<C0729q2> it = c0702m.f().d().iterator();
            while (it.hasNext()) {
                c2783pW.a(it.next(), setA);
            }
        }
    }

    private void a(C0729q2 c0729q2, final Set set) {
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.pW$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.internal.pW$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2783pW.a(set, (com.android.tools.r8.graph.W0) obj);
            }
        });
        if (set.contains(c0729q2.getType())) {
            return;
        }
        if (c0729q2.p1()) {
            a(c0729q2.X1());
        } else {
            c0729q2.a(com.android.tools.r8.graph.W0.Y1().a(C0725p4.b(4104, true)).a(com.android.tools.r8.graph.F5.H0()).a(this.a.a().a(c0729q2.getType())).a());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, com.android.tools.r8.graph.W0 w0) {
        return !set.contains(w0.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        o5Var.e().a(com.android.tools.r8.graph.F5.H0(), AbstractC1052Hq.a);
    }

    private Set a() throws Exception {
        final Set setC = OU.c();
        this.b.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.pW$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                C2783pW.a(setC, (CW) obj);
            }
        });
        return setC;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, CW cw) {
        set.add(cw.c());
    }
}
