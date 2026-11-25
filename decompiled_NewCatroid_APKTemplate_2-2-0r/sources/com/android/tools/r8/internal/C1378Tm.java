package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Tm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1378Tm {
    static final /* synthetic */ boolean d = true;
    final C0702m a;
    final C3012sl b;
    final C1678bF c;

    public C1378Tm(C0702m c0702m, C3012sl c3012sl, C1678bF c1678bF) {
        this.a = c0702m;
        this.b = c3012sl;
        this.c = c1678bF;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set) {
        final ArrayDeque arrayDeque = new ArrayDeque(set);
        while (!arrayDeque.isEmpty()) {
            C1352Sm c1352Sm = (C1352Sm) arrayDeque.removeLast();
            if (!C1352Sm.g && !c1352Sm.f) {
                throw new AssertionError();
            }
            c1352Sm.f = false;
            b(new Consumer() { // from class: com.android.tools.r8.internal.Tm$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1378Tm.a(arrayDeque, (C1352Sm) obj);
                }
            }, c1352Sm);
        }
    }

    public final void b(ExecutorService executorService) {
        C3879c3.a(new C1326Rm(this, ((C3733i) this.a.f()).d()).a(), new Consumer() { // from class: com.android.tools.r8.internal.Tm$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((Set) obj);
            }
        }, executorService);
        a(executorService);
    }

    private void b(final Consumer consumer, C1352Sm c1352Sm) {
        AbstractC3122uL abstractC3122uLA = c1352Sm.a.a(c1352Sm.b);
        if (abstractC3122uLA.c()) {
            return;
        }
        ArrayList<C1352Sm> arrayList = new ArrayList();
        for (final C1352Sm c1352Sm2 : c1352Sm.e) {
            C0702m c0702m = this.a;
            AbstractC3425yH abstractC3425yHB = abstractC3122uLA.b();
            InterfaceC3880d interfaceC3880d = new InterfaceC3880d() { // from class: com.android.tools.r8.internal.Tm$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.utils.InterfaceC3880d
                public final void a() {
                    consumer.accept(c1352Sm2);
                }
            };
            AbstractC3122uL abstractC3122uLA2 = c1352Sm2.a.a(c1352Sm2.b);
            AbstractC3122uL abstractC3122uLA3 = abstractC3122uLA2.a(c0702m, abstractC3425yHB, c1352Sm2.c, FW.a(), interfaceC3880d);
            if (abstractC3122uLA3 != abstractC3122uLA2) {
                c1352Sm2.a.a(c1352Sm2.b, abstractC3122uLA3);
                interfaceC3880d.a();
            }
            abstractC3122uLA3.getClass();
            if (abstractC3122uLA3 instanceof C2582n10) {
                arrayList.add(c1352Sm2);
            }
        }
        for (C1352Sm c1352Sm3 : arrayList) {
            Iterator it = c1352Sm3.d.iterator();
            while (it.hasNext()) {
                ((C1352Sm) it.next()).e.remove(c1352Sm3);
            }
            c1352Sm3.d.clear();
        }
    }

    private void a(ExecutorService executorService) {
        C3879c3.a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.internal.Tm$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.Tm$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        AbstractC1777cc abstractC1777ccE = this.c.b(o5Var).e();
        if (abstractC1777ccE == null) {
            return;
        }
        if (!d && !(abstractC1777ccE instanceof C1853dc)) {
            throw new AssertionError();
        }
        C1853dc c1853dcB = abstractC1777ccE.b();
        if (c1853dcB.l()) {
            this.c.b(o5Var, C3480z5.k());
        } else if (c1853dcB.m()) {
            this.c.b(o5Var, C2508m10.k());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Deque deque, C1352Sm c1352Sm) {
        if (c1352Sm.f || c1352Sm.e.isEmpty()) {
            return;
        }
        deque.add(c1352Sm);
        if (!C1352Sm.g && c1352Sm.f) {
            throw new AssertionError();
        }
        c1352Sm.f = true;
    }
}
