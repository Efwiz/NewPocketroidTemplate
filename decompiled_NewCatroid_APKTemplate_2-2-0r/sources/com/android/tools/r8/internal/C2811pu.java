package com.android.tools.r8.internal;

import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3598b;
import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2811pu extends AbstractC3598b {
    final /* synthetic */ C1921eO b;
    final /* synthetic */ C3317wu c;
    final /* synthetic */ Q20 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2811pu(C1921eO c1921eO, C3317wu c3317wu, Q20 q20) {
        super(0);
        this.b = c1921eO;
        this.c = c3317wu;
        this.d = q20;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3598b
    public final void a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        if (jVar != null) {
            this.b.a(AbstractC1543Yu.a, (Serializable) C3317wu.a(this.c, jVar, this.d));
        }
    }
}
