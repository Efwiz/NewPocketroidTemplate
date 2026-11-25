package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.IdentityHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Tj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1375Tj {
    protected final M4 a = M4.d();
    protected final M4 b = M4.d();
    protected final IdentityHashMap c = new IdentityHashMap();

    protected AbstractC1375Tj() {
    }

    public final void a(C0684j2 c0684j2, C0684j2 c0684j3) {
        if (c0684j2 == c0684j3) {
            return;
        }
        this.b.a(c0684j2, c0684j3);
    }
}
