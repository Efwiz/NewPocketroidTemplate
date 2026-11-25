package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2882qu extends com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f {
    final /* synthetic */ C3285wO c;
    final /* synthetic */ C3317wu d;
    final /* synthetic */ Q20 e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2882qu(C3285wO c3285wO, C3317wu c3317wu, Q20 q20) {
        super(0);
        this.c = c3285wO;
        this.d = c3317wu;
        this.e = q20;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f
    public final void a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        if (jVar != null) {
            this.c.a(AbstractC1543Yu.b, (Serializable) C3317wu.a(this.d, jVar, this.e));
        }
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f
    public final void a(String str) {
        AbstractC0936Ds.c(str, "internalName");
        this.c.a(AbstractC1543Yu.c, Integer.valueOf(this.e.a(str)));
    }
}
