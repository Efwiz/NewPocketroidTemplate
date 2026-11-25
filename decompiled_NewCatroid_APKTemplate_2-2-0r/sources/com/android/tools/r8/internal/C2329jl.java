package com.android.tools.r8.internal;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2329jl {
    static final /* synthetic */ boolean d = true;
    public final LinkedHashSet a;
    public final Set b;
    public final Deque c;

    public C2329jl(LinkedHashSet linkedHashSet, Set set, ArrayDeque arrayDeque) {
        if (!d && !linkedHashSet.containsAll(set)) {
            throw new AssertionError();
        }
        this.a = linkedHashSet;
        this.b = set;
        this.c = arrayDeque;
    }

    public final boolean a() {
        return this.a.isEmpty() && this.b.isEmpty();
    }

    public final boolean equals(Object obj) {
        C2329jl c2329jl = (C2329jl) obj;
        return this.a.equals(c2329jl.a) && this.b.equals(c2329jl.b);
    }

    public final int hashCode() {
        throw new C3405y10();
    }
}
