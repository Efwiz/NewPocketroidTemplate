package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class NG {
    static final /* synthetic */ boolean i = true;
    final C2688oE a;
    final Set b;
    private final Set c = OU.c();
    private final CN d = CN.b();
    private final ArrayDeque e = new ArrayDeque();
    private final IdentityHashMap f = new IdentityHashMap();
    private Collection g;
    final /* synthetic */ OG h;

    NG(OG og, C2688oE c2688oE) {
        this.h = og;
        this.a = c2688oE;
        this.b = com.android.tools.r8.utils.T2.a((Iterable) c2688oE);
    }

    final void a() {
        this.e.clear();
    }

    final void b(com.android.tools.r8.graph.o5 o5Var) {
        boolean zAdd = this.d.add(o5Var);
        if (!i && !zAdd) {
            throw new AssertionError();
        }
        this.e.add(o5Var);
    }

    final void c() {
        while (!this.e.isEmpty()) {
            com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) this.e.removeLast();
            o5Var.a(new MG(this, o5Var));
        }
        this.c.clear();
        this.d.clear();
    }

    final boolean a(com.android.tools.r8.graph.o5 o5Var) {
        if (!this.d.add(o5Var)) {
            return false;
        }
        this.e.addLast(o5Var);
        return true;
    }

    final boolean a(C0729q2 c0729q2) {
        return ((Set) this.f.getOrDefault(c0729q2, Collections.emptySet())).contains(c0729q2);
    }

    final void b(C0729q2 c0729q2) {
        if (!i && !this.b.contains(c0729q2)) {
            throw new AssertionError();
        }
        ((Set) this.f.computeIfAbsent(c0729q2, com.android.tools.r8.utils.Z1.a(new NG$$ExternalSyntheticLambda0()))).addAll(this.g);
    }

    final boolean a(C0729q2 c0729q2, C0729q2 c0729q3) {
        return ((Set) this.f.getOrDefault(c0729q2, Collections.emptySet())).contains(c0729q3);
    }

    final void b() {
        for (C0729q2 c0729q2 : this.g) {
            ((Set) this.f.computeIfAbsent(c0729q2, com.android.tools.r8.utils.Z1.a(new NG$$ExternalSyntheticLambda0()))).add(c0729q2);
        }
    }

    final void a(Collection collection) {
        boolean z = i;
        if (!z) {
            if (!z && !this.c.isEmpty()) {
                throw new AssertionError();
            }
            if (!z && !this.d.isEmpty()) {
                throw new AssertionError();
            }
        }
        if (!z && !z && !this.e.isEmpty()) {
            throw new AssertionError();
        }
        this.g = collection;
    }
}
