package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1800cv extends com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n implements InterfaceC1147Ky {
    private final ArrayList c = new ArrayList();

    public final void a(InterfaceC2653ny interfaceC2653ny) {
        InterfaceC1173Ly interfaceC1173Ly = (InterfaceC1173Ly) interfaceC2653ny;
        if (!(interfaceC1173Ly instanceof com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n nVar = (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n) interfaceC1173Ly;
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            nVar.a((C3321wx) it.next());
        }
        nVar.a();
    }

    public final ArrayList b() {
        return this.c;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n
    public final void a(C3321wx c3321wx) {
        AbstractC0936Ds.c(c3321wx, "annotation");
        this.c.add(c3321wx);
    }
}
