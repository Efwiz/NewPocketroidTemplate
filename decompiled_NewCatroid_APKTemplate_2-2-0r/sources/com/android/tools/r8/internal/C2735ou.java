package com.android.tools.r8.internal;

import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3597a;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ou, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2735ou extends AbstractC3597a {
    final /* synthetic */ C1610aO c;
    final /* synthetic */ Q20 d;

    C2735ou(C1610aO c1610aO, Q20 q20) {
        this.c = c1610aO;
        this.d = q20;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.c
    public final AbstractC0914Cy a(int i, int i2, int i3, String str) {
        AbstractC0936Ds.c(str, "name");
        return O30.a(i, i2, i3, new C2648nu(this.c), this.d, str);
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3597a
    public final void b(String str) {
        AbstractC0936Ds.c(str, "internalName");
        this.c.a(AbstractC1543Yu.k, Integer.valueOf(this.d.a(str)));
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.c
    public final void a(String str) {
        if (AbstractC0936Ds.a((Object) str, (Object) "main")) {
            return;
        }
        this.c.a(AbstractC1543Yu.i, Integer.valueOf(this.d.a(str)));
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3597a
    public final void a(int i) {
        if (i != 0) {
            this.c.a(AbstractC1543Yu.l, Integer.valueOf(i));
        }
    }
}
