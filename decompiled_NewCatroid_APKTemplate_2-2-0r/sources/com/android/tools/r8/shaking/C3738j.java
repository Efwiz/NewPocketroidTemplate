package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0606a5;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.internal.OU;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.j, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3738j {
    private final Set a = OU.a();
    private final Set b = OU.a();

    C3738j() {
    }

    public final boolean a() {
        return this.a.isEmpty();
    }

    public final void a(C0729q2 c0729q2) {
        this.a.add(c0729q2);
    }

    public final void a(com.android.tools.r8.graph.Y0 y0) {
        this.b.add(y0);
    }

    public final void a(final C3733i c3733i) {
        Set set = this.a;
        Objects.requireNonNull(c3733i);
        set.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.j$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c3733i.a((AbstractC0727q0) obj);
            }
        });
        c3733i.c(new Consumer() { // from class: com.android.tools.r8.shaking.j$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0606a5) obj);
            }
        });
        final com.android.tools.r8.graph.T2 t2Y = c3733i.y();
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.j$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3738j.a(t2Y, (com.android.tools.r8.graph.Y0) obj);
            }
        });
        this.a.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final C0606a5 c0606a5) {
        Set set = this.a;
        Objects.requireNonNull(c0606a5);
        set.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.j$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0606a5.g((C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.T2 t2, com.android.tools.r8.graph.Y0 y0) {
        com.android.tools.r8.graph.U2 u2B = t2.b(y0);
        if (u2B != null) {
            u2B.b();
        }
    }
}
