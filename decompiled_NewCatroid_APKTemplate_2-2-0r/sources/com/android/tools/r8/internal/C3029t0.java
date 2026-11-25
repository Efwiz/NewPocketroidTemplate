package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.t0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3029t0 extends AbstractC1679bG {
    final /* synthetic */ Map.Entry a;
    final /* synthetic */ C3098u0 b;

    C3029t0(C3098u0 c3098u0, Map.Entry entry) {
        this.b = c3098u0;
        this.a = entry;
    }

    @Override // com.android.tools.r8.internal.ZF
    public final int a() {
        C2549md c2549md;
        C2549md c2549md2 = (C2549md) this.a.getValue();
        if ((c2549md2 == null || c2549md2.b() == 0) && (c2549md = (C2549md) this.b.c.c.get(this.a.getKey())) != null) {
            return c2549md.b();
        }
        if (c2549md2 == null) {
            return 0;
        }
        return c2549md2.b();
    }

    @Override // com.android.tools.r8.internal.ZF
    public final Object b() {
        return this.a.getKey();
    }
}
