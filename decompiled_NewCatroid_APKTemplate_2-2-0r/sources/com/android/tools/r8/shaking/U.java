package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2964s2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class U extends T {
    public U(C0729q2 c0729q2) {
        this(c0729q2.getType());
    }

    @Override // com.android.tools.r8.shaking.Y
    public final Y a(AbstractC1476Wj abstractC1476Wj) {
        C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, ((T) this).a);
        return c0767v2A.R0() ? W.a : new U(c0767v2A);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof U) && ((T) this).a == ((T) ((U) obj)).a;
    }

    public final int hashCode() {
        return (((T) this).a.hashCode() << 1) | 1;
    }

    private U(C0767v2 c0767v2) {
        super(c0767v2);
    }
}
