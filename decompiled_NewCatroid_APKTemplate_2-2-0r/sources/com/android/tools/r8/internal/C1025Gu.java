package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1025Gu extends com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k implements InterfaceC3322wy {
    private final ArrayList c = new ArrayList(0);
    private String d;

    public final void a(InterfaceC2653ny interfaceC2653ny) {
        InterfaceC3399xy interfaceC3399xy = (InterfaceC3399xy) interfaceC2653ny;
        if (!(interfaceC3399xy instanceof com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        for (C3539zy c3539zy : this.c) {
            AbstractC0914Cy abstractC0914CyA = ((com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k) interfaceC3399xy).a(c3539zy.d(), c3539zy.e(), c3539zy.i(), c3539zy.f());
            if (abstractC0914CyA != null) {
                c3539zy.a(abstractC0914CyA);
            }
        }
        String str = this.d;
        if (str != null) {
            ((com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k) interfaceC3399xy).a(str);
        }
        ((com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k) interfaceC3399xy).b();
    }

    public final void b(String str) {
        this.d = str;
    }

    public final ArrayList c() {
        return this.c;
    }

    public final String d() {
        return this.d;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.c
    public final AbstractC0914Cy a(int i, int i2, int i3, String str) {
        AbstractC0936Ds.c(str, "name");
        C3539zy c3539zy = new C3539zy(i, i2, i3, str);
        this.c.add(c3539zy);
        return c3539zy;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.c
    public final void a(String str) {
        this.d = str;
    }
}
