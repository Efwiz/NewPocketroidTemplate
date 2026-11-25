package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class WQ {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final RQ b;
    private final VQ c = new VQ(this);

    public WQ(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.Y().E0().b();
    }

    private boolean a(com.android.tools.r8.graph.o5 o5Var, EnumC1459Vs enumC1459Vs, C0684j2 c0684j2) {
        if (enumC1459Vs == EnumC1459Vs.e) {
            return false;
        }
        if (enumC1459Vs == EnumC1459Vs.a) {
            return o5Var.e().C1() && this.a.Y().w() && ((C3733i) this.a.f()).b(o5Var.t(), c0684j2.B0());
        }
        boolean z = d;
        if (!z && o5Var.getAccessFlags().j()) {
            throw new AssertionError();
        }
        if (!z && o5Var.e().C1()) {
            throw new AssertionError();
        }
        if (enumC1459Vs == EnumC1459Vs.d) {
            return true;
        }
        if (enumC1459Vs == EnumC1459Vs.c) {
            return ((C3733i) this.a.f()).b(o5Var.t(), c0684j2.e);
        }
        if (z) {
            return false;
        }
        throw new AssertionError("Unexpected invoke-kind for visibility bridge: " + enumC1459Vs);
    }

    public final void a(C2141hE c2141hE, ExecutorService executorService) {
        if (!d && c2141hE != null && c2141hE != this.a.E()) {
            throw new AssertionError();
        }
        PQ pq = new PQ();
        ConcurrentHashMap concurrentHashMapA = a(pq, executorService);
        if (concurrentHashMapA.isEmpty()) {
            return;
        }
        a(concurrentHashMapA, executorService);
        if (!pq.a()) {
            C0702m c0702m = this.a;
            c0702m.b((AbstractC1476Wj) pq.a(c0702m));
        }
        if (c2141hE != null) {
            Iterator it = concurrentHashMapA.values().iterator();
            while (it.hasNext()) {
                for (com.android.tools.r8.graph.o5 o5Var : (CN) it.next()) {
                    c2141hE.a(o5Var.getReference(), ((C3733i) this.a.f()).a(o5Var.a(), o5Var.getReference()).c().getReference());
                }
            }
        }
    }

    private ConcurrentHashMap a(final PQ pq, ExecutorService executorService) {
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        C3879c3.a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.internal.WQ$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(pq, concurrentHashMap, (C0729q2) obj);
            }
        }, executorService);
        return concurrentHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final PQ pq, Map map, C0729q2 c0729q2) {
        final CN cnB = CN.b();
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.WQ$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(cnB, pq, (com.android.tools.r8.graph.o5) obj);
            }
        });
        if (cnB.isEmpty()) {
            return;
        }
        map.put(c0729q2, cnB);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:87:0x01dc  */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00bb, code lost:
    
        if (r0.f1().isEmpty() != false) goto L52;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void a(com.android.tools.r8.internal.CN r6, com.android.tools.r8.internal.PQ r7, com.android.tools.r8.graph.o5 r8) {
        /*
            Method dump skipped, instruction units count: 526
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.WQ.a(com.android.tools.r8.internal.CN, com.android.tools.r8.internal.PQ, com.android.tools.r8.graph.o5):void");
    }

    private void a(ConcurrentHashMap concurrentHashMap, ExecutorService executorService) {
        final com.android.tools.r8.graph.t5 t5VarA = com.android.tools.r8.graph.u5.a().a(this.a.e());
        concurrentHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.WQ$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                WQ.a(t5VarA, (C0729q2) obj, (CN) obj2);
            }
        });
        this.a.a(t5VarA.a(), executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final com.android.tools.r8.graph.t5 t5Var, C0729q2 c0729q2, CN cn) {
        c0729q2.Y().a((Set) cn.a());
        cn.forEach(new Consumer() { // from class: com.android.tools.r8.internal.WQ$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WQ.a(t5Var, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.t5 t5Var, com.android.tools.r8.graph.o5 o5Var) {
        t5Var.a(o5Var.getReference());
    }
}
