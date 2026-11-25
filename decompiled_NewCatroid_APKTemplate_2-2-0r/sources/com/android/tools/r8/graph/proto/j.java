package com.android.tools.r8.graph.proto;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC3292wV;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class j extends h {
    j(AbstractC3292wV abstractC3292wV, C0767v2 c0767v2) {
        super(abstractC3292wV, c0767v2);
    }

    @Override // com.android.tools.r8.graph.proto.h, com.android.tools.r8.graph.proto.b
    public final b a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        AbstractC3292wV abstractC3292wVB = h() ? f().b(c0702m, abstractC1476Wj, abstractC1476Wj2) : null;
        C0767v2 c0767v2A = abstractC1476Wj.a(abstractC1476Wj2, g());
        return (abstractC3292wVB == f() && c0767v2A == g()) ? this : new j(abstractC3292wVB, c0767v2A);
    }

    @Override // com.android.tools.r8.graph.proto.h
    /* JADX INFO: renamed from: b */
    public final h a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        AbstractC3292wV abstractC3292wVB = h() ? f().b(c0702m, abstractC1476Wj, abstractC1476Wj2) : null;
        C0767v2 c0767v2A = abstractC1476Wj.a(abstractC1476Wj2, g());
        return (abstractC3292wVB == f() && c0767v2A == g()) ? this : new j(abstractC3292wVB, c0767v2A);
    }

    @Override // com.android.tools.r8.graph.proto.h
    public final boolean equals(Object obj) {
        if (obj == null || j.class != obj.getClass()) {
            return false;
        }
        j jVar = (j) obj;
        return g() == jVar.g() && Objects.equals(f(), jVar.f());
    }

    @Override // com.android.tools.r8.graph.proto.h
    public final int hashCode() {
        return Objects.hash(f(), g());
    }
}
