package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.in, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2257in {
    static final /* synthetic */ boolean b = true;
    private final ConcurrentHashMap a = new ConcurrentHashMap();

    public final void a(C0767v2 c0767v2, com.android.tools.r8.graph.Y0 y0) {
        if (!b && y0.e != c0767v2) {
            throw new AssertionError();
        }
        this.a.put(c0767v2, y0);
    }

    public final C2482li a() {
        return new C2482li(this.a);
    }
}
