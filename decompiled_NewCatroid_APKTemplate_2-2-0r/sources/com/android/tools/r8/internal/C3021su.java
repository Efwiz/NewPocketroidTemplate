package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.su, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3021su extends com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k {
    final /* synthetic */ Q20 c;
    final /* synthetic */ BO d;

    C3021su(Q20 q20, BO bo) {
        this.c = q20;
        this.d = bo;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.c
    public final AbstractC0914Cy a(int i, int i2, int i3, String str) {
        AbstractC0936Ds.c(str, "name");
        return O30.a(i, i2, i3, new C2952ru(this.d), this.c, str);
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.c
    public final void a(String str) {
        if (AbstractC0936Ds.a((Object) str, (Object) "main")) {
            return;
        }
        this.d.a(AbstractC1543Yu.m, Integer.valueOf(this.c.a(str)));
    }
}
