package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3346xH extends KK {
    static final /* synthetic */ boolean c = true;
    private final Set b;

    public C3346xH(Set<C0767v2> set) {
        this.b = set;
    }

    @Override // com.android.tools.r8.internal.KK
    public final boolean a(AbstractC0727q0 abstractC0727q0) {
        if (c || abstractC0727q0.isInterface()) {
            return !this.b.contains(abstractC0727q0.getType());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.KK
    public final KK a(AbstractC1476Wj abstractC1476Wj) {
        Set setB = com.android.tools.r8.utils.T2.b(this.b.size());
        for (C0767v2 c0767v2 : this.b) {
            abstractC1476Wj.getClass();
            setB.add(abstractC1476Wj.a(AbstractC1476Wj.e(), c0767v2));
        }
        return new C3346xH(setB);
    }

    @Override // com.android.tools.r8.internal.KK
    public final KK a(com.android.tools.r8.graph.u5 u5Var) {
        if (!u5Var.h()) {
            return this;
        }
        Set setB = com.android.tools.r8.utils.T2.b(this.b.size());
        for (C0767v2 c0767v2 : this.b) {
            if (!u5Var.a(c0767v2)) {
                setB.add(c0767v2);
            }
        }
        return new C3346xH(setB);
    }
}
