package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3090tt extends AbstractC3069tZ {
    final /* synthetic */ InterfaceC3010sj b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3090tt(Iterator it, InterfaceC3010sj interfaceC3010sj) {
        super(it);
        this.b = interfaceC3010sj;
    }

    @Override // com.android.tools.r8.internal.AbstractC3069tZ
    final Object a(Object obj) {
        return this.b.apply(obj);
    }
}
