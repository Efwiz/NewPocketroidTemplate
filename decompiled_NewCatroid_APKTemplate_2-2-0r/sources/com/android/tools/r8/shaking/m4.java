package com.android.tools.r8.shaking;

import java.util.ArrayList;
import java.util.Comparator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class m4 {
    final ArrayList a;
    final ArrayList b;

    private m4() {
        this.a = new ArrayList();
        this.b = new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(com.android.tools.r8.graph.T0 t0, com.android.tools.r8.graph.T0 t1) {
        return t0.getReference().compareTo(t1.getReference());
    }

    final void a() {
        this.a.sort(new Comparator() { // from class: com.android.tools.r8.shaking.m4$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return m4.a((com.android.tools.r8.graph.T0) obj, (com.android.tools.r8.graph.T0) obj2);
            }
        });
        this.b.sort(new Comparator() { // from class: com.android.tools.r8.shaking.m4$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return m4.a((com.android.tools.r8.graph.W0) obj, (com.android.tools.r8.graph.W0) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.W0 w1) {
        return w0.getReference().compareTo(w1.getReference());
    }
}
