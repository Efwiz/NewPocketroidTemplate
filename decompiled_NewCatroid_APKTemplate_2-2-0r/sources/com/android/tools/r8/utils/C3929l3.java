package com.android.tools.r8.utils;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.utils.l3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3929l3 extends AbstractC3934m3 {
    final /* synthetic */ Iterator a;

    C3929l3(Iterator it) {
        this.a = it;
    }

    @Override // com.android.tools.r8.utils.AbstractC3934m3
    public final boolean a() {
        return this.a.hasNext();
    }

    @Override // com.android.tools.r8.utils.AbstractC3934m3
    public final Object b() {
        return this.a.next();
    }
}
