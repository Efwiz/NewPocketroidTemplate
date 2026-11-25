package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class QE {
    public static CN a(C0702m c0702m, com.android.tools.r8.graph.H3 h3, Collection collection, final Predicate predicate) {
        final CN cnB = CN.b();
        final CN cnB2 = CN.b();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            final C0729q2 c0729q2 = (C0729q2) it.next();
            c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.QE$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    QE.a(predicate, c0729q2, cnB2, cnB, (com.android.tools.r8.graph.o5) obj);
                }
            });
        }
        if (!cnB2.isEmpty()) {
            new OE(c0702m, h3, cnB, cnB2).a(collection);
        }
        PE pe = new PE(c0702m, h3, cnB, cnB2);
        pe.a(collection);
        return pe.g;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, C0729q2 c0729q2, CN cn, CN cn2, com.android.tools.r8.graph.o5 o5Var) {
        if (predicate.test(o5Var)) {
            if (c0729q2.isInterface()) {
                cn.add(o5Var);
            } else {
                cn2.add(o5Var);
            }
        }
    }
}
