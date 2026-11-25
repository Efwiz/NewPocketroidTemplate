package com.android.tools.r8.internal;

import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3597a;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2031fu extends AbstractC3597a implements InterfaceC1310Qx {
    private final ArrayList c = new ArrayList(0);
    private String d;
    private String e;
    private int f;

    public final void a(InterfaceC2653ny interfaceC2653ny) {
        InterfaceC1337Rx interfaceC1337Rx = (InterfaceC1337Rx) interfaceC2653ny;
        if (!(interfaceC1337Rx instanceof AbstractC3597a)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        for (C3539zy c3539zy : this.c) {
            AbstractC0914Cy abstractC0914CyA = ((AbstractC3597a) interfaceC1337Rx).a(c3539zy.d(), c3539zy.e(), c3539zy.i(), c3539zy.f());
            if (abstractC0914CyA != null) {
                c3539zy.a(abstractC0914CyA);
            }
        }
        String str = this.d;
        if (str != null) {
            ((AbstractC3597a) interfaceC1337Rx).a(str);
        }
        String str2 = this.e;
        if (str2 != null) {
            ((AbstractC3597a) interfaceC1337Rx).b(str2);
        }
        Integer numValueOf = Integer.valueOf(this.f);
        if (numValueOf.intValue() == 0) {
            numValueOf = null;
        }
        if (numValueOf != null) {
            ((AbstractC3597a) interfaceC1337Rx).a(numValueOf.intValue());
        }
        ((AbstractC3597a) interfaceC1337Rx).b();
    }

    public final void b(int i) {
        this.f = i;
    }

    public final String c() {
        return this.e;
    }

    public final void d(String str) {
        this.d = str;
    }

    public final ArrayList e() {
        return this.c;
    }

    public final String f() {
        return this.d;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3597a
    public final void b(String str) {
        AbstractC0936Ds.c(str, "internalName");
        this.e = str;
    }

    public final void c(String str) {
        this.e = str;
    }

    public final int d() {
        return this.f;
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

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3597a
    public final void a(int i) {
        this.f = i;
    }
}
