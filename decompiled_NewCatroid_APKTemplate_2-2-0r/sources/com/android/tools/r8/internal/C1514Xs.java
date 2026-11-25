package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0753u;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xs, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1514Xs implements Comparable {
    private final com.android.tools.r8.graph.o5 a;
    private final com.android.tools.r8.graph.o5 b;
    private final C0753u c;

    C1514Xs(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, C0753u c0753u) {
        this.a = o5Var;
        this.b = o5Var2;
        this.c = c0753u;
    }

    public final com.android.tools.r8.graph.o5 a() {
        return this.a;
    }

    public final com.android.tools.r8.graph.o5 b() {
        return this.b;
    }

    public final C0753u c() {
        return this.c;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return this.a.getReference().compareTo(((C1514Xs) obj).a.getReference());
    }
}
