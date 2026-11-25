package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class L3 {
    static final /* synthetic */ boolean b = true;
    private final C1207Nf a;

    public L3(C1207Nf c1207Nf) {
        if (!b && c1207Nf == null) {
            throw new AssertionError();
        }
        this.a = c1207Nf;
    }

    public final C1207Nf a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (obj != null && L3.class == obj.getClass()) {
            return this.a.equals(((L3) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final void a(S10 s10, C0702m c0702m) {
        boolean z = b;
        if (!z && this.a.h()) {
            throw new AssertionError();
        }
        if (!z && this.a.m()) {
            throw new AssertionError();
        }
        if (!z && !this.a.o().b(s10.w(), (C0702m<?>) c0702m)) {
            throw new AssertionError();
        }
    }
}
