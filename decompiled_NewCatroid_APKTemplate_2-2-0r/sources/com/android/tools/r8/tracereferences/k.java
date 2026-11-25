package com.android.tools.r8.tracereferences;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.CM;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class k {
    private final C0702m a;
    private final DiagnosticsHandler b;
    private final Predicate c;

    public k(C0702m c0702m, DiagnosticsHandler diagnosticsHandler, Predicate predicate) {
        this.a = c0702m;
        this.b = diagnosticsHandler;
        this.c = predicate;
    }

    public final void a(TraceReferencesConsumer traceReferencesConsumer) {
        final j jVar = new j(this.a, traceReferencesConsumer, this.b, this.c);
        for (C0729q2 c0729q2 : ((C0680j) this.a.f()).d()) {
            DefinitionContext definitionContextA = com.android.tools.r8.diagnostic.internal.d.a(c0729q2);
            C0767v2 c0767v2 = c0729q2.f;
            if (c0767v2 != null) {
                jVar.a(c0729q2, c0767v2, definitionContextA);
            }
            Iterator<C0767v2> it = c0729q2.f1().iterator();
            while (it.hasNext()) {
                jVar.a(c0729q2, it.next(), definitionContextA);
            }
            c0729q2.e(new Consumer() { // from class: com.android.tools.r8.tracereferences.k$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    k.a(jVar, (m5) obj);
                }
            }, CM.b());
            c0729q2.l(new Consumer() { // from class: com.android.tools.r8.tracereferences.k$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    k.a(jVar, (o5) obj);
                }
            });
        }
        traceReferencesConsumer.finished(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(j jVar, m5 m5Var) {
        jVar.getClass();
        jVar.b(m5Var.getType(), com.android.tools.r8.diagnostic.internal.d.a(m5Var));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(j jVar, o5 o5Var) {
        jVar.a(o5Var);
        o5Var.a(new i(jVar, o5Var));
    }
}
