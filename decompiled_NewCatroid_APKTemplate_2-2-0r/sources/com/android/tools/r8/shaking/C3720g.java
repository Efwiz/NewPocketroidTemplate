package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.OU;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.g, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3720g {
    private final Set a = OU.c();
    private final L.a b;

    C3720g(L.a aVar) {
        this.b = aVar;
    }

    public final boolean a(C0633d0 c0633d0) {
        return this.a.contains(c0633d0);
    }

    public final void b(C0633d0 c0633d0) {
        this.a.add(c0633d0);
    }

    public final C3728h a(C0702m c0702m, Set set) {
        return new C3728h(c0702m, this.a, this.b, set);
    }
}
