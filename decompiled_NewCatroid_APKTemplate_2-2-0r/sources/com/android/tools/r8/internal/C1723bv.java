package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1723bv extends com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m implements InterfaceC1060Hy {
    private boolean c;
    private final ArrayList d = new ArrayList();

    public final void a(InterfaceC2653ny interfaceC2653ny) {
        InterfaceC1090Iy interfaceC1090Iy = (InterfaceC1090Iy) interfaceC2653ny;
        if (!(interfaceC1090Iy instanceof com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m mVar = (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m) interfaceC1090Iy;
        mVar.a(this.c);
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            mVar.a((C3321wx) it.next());
        }
        mVar.a();
    }

    public final void b(boolean z) {
        this.c = z;
    }

    public final boolean c() {
        return this.c;
    }

    public final ArrayList b() {
        return this.d;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m
    public final void a(boolean z) {
        this.c = z;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m
    public final void a(C3321wx c3321wx) {
        AbstractC0936Ds.c(c3321wx, "annotation");
        this.d.add(c3321wx);
    }
}
