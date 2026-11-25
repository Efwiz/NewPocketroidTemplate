package com.android.tools.r8.internal;

import java.util.IdentityHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.f6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1973f6 {
    static final /* synthetic */ boolean c = true;
    private final C2133h6 a;
    private final IdentityHashMap b = new IdentityHashMap();

    C1973f6(C2133h6 c2133h6) {
        this.a = c2133h6;
    }

    public final void a(AbstractC2412ko abstractC2412ko, Object obj) {
        C1890e6 c1890e6A = this.a.a(abstractC2412ko);
        if (c1890e6A != null) {
            this.b.put(obj, c1890e6A);
        }
    }

    public final C2049g6 a() {
        if (this.b.isEmpty()) {
            return C2049g6.b;
        }
        return new C2049g6(this.b);
    }

    public final void a(AbstractC2412ko abstractC2412ko) {
        if (!c && this.a.a(abstractC2412ko) != null) {
            throw new AssertionError();
        }
    }
}
