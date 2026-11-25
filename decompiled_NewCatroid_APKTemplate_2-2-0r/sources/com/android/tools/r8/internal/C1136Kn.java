package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Collections;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1136Kn implements InterfaceC0989Fn {
    private final C1229Oa a;
    private final AbstractC3472z00 b;

    C1136Kn(C1229Oa c1229Oa, AbstractC3472z00 abstractC3472z00) {
        this.a = c1229Oa;
        this.b = abstractC3472z00;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    public final InterfaceC0989Fn a(com.android.tools.r8.graph.proto.c cVar) {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    public final boolean a() {
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (C1136Kn.class != obj.getClass()) {
            return false;
        }
        C1136Kn c1136Kn = (C1136Kn) obj;
        return Objects.equals(this.a, c1136Kn.a) && Objects.equals(this.b, c1136Kn.b);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    public final String toString() {
        return "InstanceFieldTypeInitializationInfo";
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    /* JADX INFO: renamed from: a */
    public final InterfaceC0989Fn b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        C3226vg c3226vgE0 = c0702m.e0();
        C1229Oa c1229Oa = this.a;
        if (c1229Oa != null && c3226vgE0.b(c1229Oa.R())) {
            return C2351k10.b();
        }
        if (this.b.w() && c3226vgE0.b(this.b.b().R())) {
            return C2351k10.b();
        }
        C1229Oa c1229Oa2 = this.a;
        C1229Oa c1229OaB = c1229Oa2 != null ? c1229Oa2.a(c0702m, abstractC1476Wj, abstractC1476Wj2, Collections.emptySet()).b() : null;
        AbstractC3472z00 abstractC3472z00 = this.b;
        abstractC3472z00.getClass();
        return new C1136Kn(c1229OaB, abstractC3472z00.a(c0702m, abstractC1476Wj, abstractC1476Wj2, Collections.emptySet()));
    }
}
