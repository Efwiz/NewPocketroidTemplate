package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0725p4;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class FA extends AA {
    final com.android.tools.r8.graph.o5 f;

    FA(boolean z, com.android.tools.r8.graph.o5 o5Var) {
        super(o5Var.getReference(), EnumC1953et.e, z);
        this.f = o5Var;
    }

    @Override // com.android.tools.r8.internal.GA
    final com.android.tools.r8.graph.o5 a(InterfaceC1011Gi interfaceC1011Gi, Consumer consumer) {
        C0725p4 accessFlags = this.f.getAccessFlags();
        accessFlags.I();
        if (!this.f.a().isInterface()) {
            return null;
        }
        accessFlags.C();
        return null;
    }
}
