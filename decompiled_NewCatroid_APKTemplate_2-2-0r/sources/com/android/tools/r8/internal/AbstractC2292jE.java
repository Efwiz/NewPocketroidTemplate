package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0739r4;
import com.android.tools.r8.graph.C0762u4;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2292jE {
    public static C2141hE a(C0702m c0702m, ExecutorService executorService) {
        com.android.tools.r8.graph.T2 t2;
        C0762u4 c0762u4A;
        if (((C0680j) c0702m.f()).i() && c0702m.Y().n1.G) {
            C3733i c3733iO = ((C0680j) c0702m.f()).o();
            t2 = c3733iO.v();
            c0762u4A = c3733iO.x();
        } else {
            t2 = new com.android.tools.r8.graph.T2(new ConcurrentHashMap());
            C0739r4 c0739r4A = C0762u4.a();
            a(c0702m, t2, c0739r4A, executorService);
            c0762u4A = c0739r4A.a();
        }
        return a(c0702m, t2, c0762u4A);
    }

    public static C2141hE a(C0702m c0702m, com.android.tools.r8.graph.T2 t2, C0762u4 c0762u4) {
        final C2057gE c2057gEA = C2141hE.a(c0702m);
        t2.a(new Consumer() { // from class: com.android.tools.r8.internal.jE$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2057gEA.a((com.android.tools.r8.graph.R2) obj);
            }
        });
        c0762u4.a(new Consumer() { // from class: com.android.tools.r8.internal.jE$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2057gEA.a((C0684j2) obj);
            }
        });
        return c2057gEA.a();
    }

    private static void a(final C0702m c0702m, final com.android.tools.r8.graph.T2 t2, final C0739r4 c0739r4, ExecutorService executorService) {
        final Set setA = OU.a();
        final Set setA2 = OU.a();
        C0680j c0680j = (C0680j) c0702m.f();
        Objects.requireNonNull(c0680j);
        C3879c3.a(new hV$$ExternalSyntheticLambda4(c0680j), new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.jE$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                AbstractC2292jE.a(c0702m, t2, c0739r4, setA, setA2, (com.android.tools.r8.graph.o5) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, com.android.tools.r8.graph.T2 t2, C0739r4 c0739r4, Set set, Set set2, com.android.tools.r8.graph.o5 o5Var) {
        o5Var.a(new C2216iE(c0702m, t2, c0739r4, set, set2, o5Var));
    }
}
