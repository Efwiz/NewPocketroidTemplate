package com.android.tools.r8.utils;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0644e4;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC3477z20;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.g, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3896g {
    static final /* synthetic */ boolean a = true;

    public static boolean a(o5 o5Var, o5 o5Var2, A1 a1, AbstractC3477z20 abstractC3477z20) {
        if (!a1.a().d() || o5Var.t() == o5Var2.t()) {
            return true;
        }
        com.android.tools.r8.androidapi.h hVarY0 = o5Var.e().Y0();
        if (hVarY0.H()) {
            abstractC3477z20.a();
            return false;
        }
        com.android.tools.r8.androidapi.h hVarY1 = o5Var2.e().Y0();
        if (o5Var.e().Y0().c(hVarY1)) {
            return true;
        }
        abstractC3477z20.a(hVarY0, hVarY1);
        return false;
    }

    private static AbstractC0727q0 b(final C0680j c0680j, AbstractC0727q0 abstractC0727q0, final AbstractC0642e2 abstractC0642e2) {
        return abstractC0727q0.f0() ? abstractC0727q0 : (AbstractC0727q0) O3.h(abstractC0727q0).a(new BiFunction() { // from class: com.android.tools.r8.utils.g$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC3896g.a(abstractC0642e2, c0680j, (AbstractC0727q0) obj, (O3) obj2);
            }
        }).b().i();
    }

    public static com.android.tools.r8.androidapi.h a(C0729q2 c0729q2, com.android.tools.r8.androidapi.h hVar) {
        for (com.android.tools.r8.graph.W0 w0 : c0729q2.M1()) {
            if (w0.q1()) {
                com.android.tools.r8.androidapi.h hVarY0 = w0.Y0();
                if (hVarY0.J()) {
                    return com.android.tools.r8.androidapi.h.w();
                }
                hVar = hVar.e(hVarY0);
            }
            if (hVar.H()) {
                break;
            }
        }
        return hVar;
    }

    public static boolean b(C0729q2 c0729q2, com.android.tools.r8.androidapi.h hVar) {
        String strL1;
        int iIndexOf;
        if (!a && !hVar.y()) {
            throw new AssertionError();
        }
        if (!c0729q2.getType().D0().b("Landroidx/") || (iIndexOf = (strL1 = c0729q2.l1()).indexOf("Api")) < 0) {
            return false;
        }
        int i = iIndexOf + 3;
        int iIndexOf2 = strL1.indexOf("Impl");
        if (iIndexOf2 < 0 || iIndexOf2 < i || iIndexOf2 - i != 2) {
            return false;
        }
        String strSubstring = strL1.substring(i, iIndexOf2);
        char[] cArr = Y2.a;
        for (int i2 = 0; i2 < strSubstring.length(); i2++) {
            if (!Character.isDigit(strSubstring.charAt(i2))) {
                return false;
            }
        }
        int i3 = Integer.parseInt(strSubstring);
        return i3 >= 10 && i3 <= EnumC3891f.L.d() && hVar.b0().a().d() <= i3;
    }

    public static boolean a(C0644e4 c0644e4, C0684j2 c0684j2, com.android.tools.r8.androidapi.b bVar, A1 a1) {
        if (!bVar.a()) {
            if (a || !a1.a().a) {
                return false;
            }
            throw new AssertionError();
        }
        if (!a && !a1.a().a) {
            throw new AssertionError();
        }
        com.android.tools.r8.androidapi.h hVarA = bVar.a((AbstractC0744s2) c0644e4.getReference(), (com.android.tools.r8.androidapi.h) com.android.tools.r8.androidapi.h.f());
        if (hVarA.H()) {
            return false;
        }
        com.android.tools.r8.androidapi.h hVarA2 = bVar.a((AbstractC0744s2) c0684j2, (com.android.tools.r8.androidapi.h) com.android.tools.r8.androidapi.h.f());
        if (hVarA2.H()) {
            return false;
        }
        return hVarA2.e(hVarA).a(a1.B0()).d();
    }

    public static boolean a(C0635d2 c0635d2, C0702m c0702m) {
        com.android.tools.r8.androidapi.b bVarD = c0702m.d();
        A1 a1Y = c0702m.Y();
        C0722p1 c0722p1A = c0702m.a();
        if (!a1Y.a().a) {
            return c0722p1A.P5.contains(c0635d2.C());
        }
        return bVarD.a(c0635d2.getReference(), com.android.tools.r8.androidapi.h.f()).a(a1Y.B0()).d();
    }

    public static boolean a(C0702m c0702m, C0767v2 c0767v2, C0767v2 c0767v3) {
        boolean z = a;
        if (!z && !c0767v2.X0()) {
            throw new AssertionError();
        }
        if (!z && !c0767v3.X0()) {
            throw new AssertionError();
        }
        if (!z && c0767v2 == c0767v3) {
            throw new AssertionError();
        }
        C0722p1 c0722p1A = c0702m.a();
        C0767v2 c0767v2B = c0767v2.b(c0722p1A);
        if (c0767v2B.W0()) {
            return true;
        }
        if (!z && !c0767v2B.O0()) {
            throw new AssertionError();
        }
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2B);
        if (abstractC0727q0C == null) {
            return false;
        }
        if (!abstractC0727q0C.f0()) {
            return true;
        }
        if (!c0702m.Y().a().a) {
            return false;
        }
        C0635d2 c0635d2D0 = abstractC0727q0C.d0();
        if (a(c0635d2D0, c0702m)) {
            return true;
        }
        C0767v2 c0767v2B2 = c0767v3.b(c0722p1A);
        if (!z && !c0767v2B2.O0()) {
            throw new AssertionError();
        }
        AbstractC0727q0 abstractC0727q0C2 = c0702m.c(c0767v2B2);
        int i = C0635d2.u;
        C0635d2 c0635d2D1 = abstractC0727q0C2 != null ? abstractC0727q0C2.d0() : null;
        if (c0635d2D1 != null) {
            if (!z && !c0702m.Y().a().a) {
                throw new AssertionError();
            }
            if (!z && a(c0635d2D0, c0702m)) {
                throw new AssertionError("Clients should first check if the definition is present on all apis since the min api");
            }
            com.android.tools.r8.androidapi.b bVarD = c0702m.d();
            com.android.tools.r8.androidapi.h hVarA = bVarD.a(c0635d2D0.getReference(), com.android.tools.r8.androidapi.h.f());
            if (hVarA.H() ? false : hVarA.d(bVarD.a(c0635d2D1.getReference(), com.android.tools.r8.androidapi.h.f())).d()) {
                return true;
            }
        }
        return false;
    }

    public static C3979v2 a(C0702m c0702m, C0680j c0680j, AbstractC0727q0 abstractC0727q0, AbstractC0642e2 abstractC0642e2) {
        com.android.tools.r8.androidapi.b bVarD = c0702m.d();
        if (abstractC0727q0.f0()) {
            return C3979v2.a(abstractC0727q0, bVarD.a((AbstractC0744s2) abstractC0642e2, (com.android.tools.r8.androidapi.h) com.android.tools.r8.androidapi.h.f()));
        }
        AbstractC0727q0 abstractC0727q0B = b(c0680j, abstractC0727q0, abstractC0642e2);
        AbstractC0727q0 abstractC0727q1 = null;
        if (abstractC0727q0B == null) {
            return C3979v2.a(null, com.android.tools.r8.androidapi.h.f());
        }
        if (!abstractC0727q0B.f0()) {
            return C3979v2.a(abstractC0727q0B, c0702m.o());
        }
        com.android.tools.r8.androidapi.h hVarA = bVarD.a((AbstractC0744s2) abstractC0642e2.b(c0702m.a(), abstractC0727q0B.getType()), (com.android.tools.r8.androidapi.h) com.android.tools.r8.androidapi.h.f());
        if (hVarA.y()) {
            return C3979v2.a(abstractC0727q0B, hVarA);
        }
        Set<AbstractC0727q0> setA = a(c0680j, abstractC0727q0, abstractC0642e2);
        if (setA.size() == 1) {
            AbstractC0727q0 abstractC0727q2 = (AbstractC0727q0) setA.iterator().next();
            if (!abstractC0727q2.f0()) {
                return C3979v2.a(abstractC0727q2, c0702m.o());
            }
        }
        com.android.tools.r8.androidapi.h hVarF = com.android.tools.r8.androidapi.h.f();
        for (AbstractC0727q0 abstractC0727q3 : setA) {
            if (!a && !abstractC0727q3.f0()) {
                throw new AssertionError();
            }
            com.android.tools.r8.androidapi.h hVarA2 = bVarD.a((AbstractC0744s2) abstractC0642e2.b(c0702m.a(), abstractC0727q0B.getType()), (com.android.tools.r8.androidapi.h) com.android.tools.r8.androidapi.h.f());
            if (hVarF.a(hVarA2)) {
                abstractC0727q1 = abstractC0727q3;
                hVarF = hVarA2;
            }
        }
        return C3979v2.a(abstractC0727q1, hVarF);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ D3 a(AbstractC0642e2 abstractC0642e2, C0680j c0680j, AbstractC0727q0 abstractC0727q0, O3 o3) {
        if (abstractC0727q0.f0()) {
            return D3.a(abstractC0727q0);
        }
        if (abstractC0727q0.a(abstractC0642e2) != null) {
            return D3.a(abstractC0727q0);
        }
        if (abstractC0727q0.n1() != null) {
            com.android.tools.r8.graph.O oF = c0680j.f(abstractC0727q0.n1());
            Objects.requireNonNull(o3);
            oF.b(new g$$ExternalSyntheticLambda2(o3));
        }
        return D3.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0680j c0680j, O3 o3, C0767v2 c0767v2) {
        com.android.tools.r8.graph.O oF = c0680j.f(c0767v2);
        Objects.requireNonNull(o3);
        oF.b(new g$$ExternalSyntheticLambda2(o3));
    }

    private static Set a(final C0680j c0680j, AbstractC0727q0 abstractC0727q0, AbstractC0642e2 abstractC0642e2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        final O3 o3H = O3.h(abstractC0727q0);
        while (o3H.c()) {
            AbstractC0727q0 abstractC0727q1 = (AbstractC0727q0) o3H.f();
            if (abstractC0727q1.f0()) {
                if (abstractC0727q1.isInterface()) {
                    linkedHashSet.add(abstractC0727q1);
                }
            } else {
                if (abstractC0727q1.a(abstractC0642e2) != null) {
                    return Collections.singleton(abstractC0727q1);
                }
                abstractC0727q1.d(new Consumer() { // from class: com.android.tools.r8.utils.g$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AbstractC3896g.a(c0680j, o3H, (C0767v2) obj);
                    }
                });
            }
        }
        return linkedHashSet;
    }
}
