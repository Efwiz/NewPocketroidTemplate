package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.e6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1890e6 {
    private final Set a;
    private final boolean b;

    private C1890e6(Set set, boolean z) {
        this.a = set;
        this.b = z;
    }

    public static C1817d6 a() {
        return new C1817d6();
    }

    public final Set b() {
        return this.a;
    }

    public final boolean c() {
        return this.a != null;
    }

    public final boolean d() {
        return this.b;
    }
}
