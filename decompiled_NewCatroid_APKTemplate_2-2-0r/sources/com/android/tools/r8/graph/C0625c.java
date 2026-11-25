package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C3405y10;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.c, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0625c extends AbstractC0632d {
    public static C0625c a = new C0625c();

    private C0625c() {
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    final AbstractC0632d a(Q0 q0, AbstractC1476Wj abstractC1476Wj) {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    public final AbstractC0632d a(AbstractC0632d abstractC0632d) {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    final void a(Y0 y0) {
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    final boolean a(W0 w0) {
        return true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    final boolean a(Predicate predicate) {
        return true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    final boolean b(Predicate predicate) {
        return false;
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    public final boolean c() {
        return false;
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    final int b() {
        throw new C3405y10("Should never be querying the number of access contexts when they are unknown");
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    final void a(Consumer consumer) {
        throw new C3405y10("Should never be iterating the access contexts when they are unknown");
    }
}
