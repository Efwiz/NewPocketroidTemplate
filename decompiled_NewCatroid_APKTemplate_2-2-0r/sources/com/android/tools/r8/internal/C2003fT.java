package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.HashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2003fT {
    static final /* synthetic */ boolean d = true;
    protected final C3011sk a = C3011sk.l();
    protected final Q4 b = new Q4();
    protected final Q4 c = new Q4();

    public final C2079gT a(C0702m c0702m, HashMap map) {
        if (d || !this.a.isEmpty()) {
            return new C2079gT(c0702m, this.b, this.c, this.a, map);
        }
        throw new AssertionError();
    }
}
