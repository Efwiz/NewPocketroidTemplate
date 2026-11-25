package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2371kH extends AbstractC2309jV {
    private final C0702m b;
    private final Set c;

    public C2371kH(C0702m c0702m) {
        this.b = c0702m;
        this.c = c0702m.j().b();
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        return (this.b.j().a().contains(c0729q2.getType()) || this.c.contains(c0729q2.getType())) ? false : true;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoServiceLoaders";
    }
}
