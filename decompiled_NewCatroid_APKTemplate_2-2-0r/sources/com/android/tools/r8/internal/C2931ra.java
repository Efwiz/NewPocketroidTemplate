package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ra, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2931ra {
    static final /* synthetic */ boolean d = true;
    private Set a = OU.c();
    private ArrayList b = new ArrayList();
    private ArrayList c = new ArrayList();

    public final void a(com.android.tools.r8.graph.W0 w0) {
        this.c.add(w0);
        boolean zAdd = this.a.add(w0.getReference());
        if (!d && !zAdd) {
            throw new AssertionError();
        }
    }

    public final void b(com.android.tools.r8.graph.W0 w0) {
        this.b.add(w0);
        boolean zAdd = this.a.add(w0.getReference());
        if (!d && !zAdd) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C0729q2 c0729q2, com.android.tools.r8.graph.W0 w0) {
        return w0.J0() == c0729q2.d;
    }

    public final boolean a(C0684j2 c0684j2) {
        return !this.a.contains(c0684j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0729q2 c0729q2, com.android.tools.r8.graph.W0 w0) {
        return w0.J0() == c0729q2.d;
    }

    public final void a(final C0729q2 c0729q2) {
        boolean z = d;
        if (!z && !this.b.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.ra$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2931ra.a(c0729q2, (com.android.tools.r8.graph.W0) obj);
            }
        })) {
            throw new AssertionError();
        }
        if (!z && !this.b.stream().allMatch(new O2$$ExternalSyntheticLambda4())) {
            throw new AssertionError();
        }
        if (!z && !this.c.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.ra$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2931ra.b(c0729q2, (com.android.tools.r8.graph.W0) obj);
            }
        })) {
            throw new AssertionError();
        }
        if (!z && !this.c.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.ra$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((com.android.tools.r8.graph.W0) obj).Q0();
            }
        })) {
            throw new AssertionError();
        }
        c0729q2.a(this.b);
        c0729q2.d(this.c);
    }
}
