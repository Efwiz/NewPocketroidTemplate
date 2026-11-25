package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.OU;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class K2 {
    private final Set a;

    private K2(Set set) {
        this.a = set;
    }

    public static J2 a() {
        return new J2();
    }

    public final boolean a(C0729q2 c0729q2) {
        return this.a.contains(c0729q2.getType());
    }

    public final K2 a(AbstractC1476Wj abstractC1476Wj) {
        Set setC = OU.c();
        for (C0767v2 c0767v2 : this.a) {
            abstractC1476Wj.getClass();
            setC.add(abstractC1476Wj.a(AbstractC1476Wj.e(), c0767v2));
        }
        return new K2(setC);
    }

    public final K2 a(u5 u5Var) {
        Set setC = OU.c();
        for (C0767v2 c0767v2 : this.a) {
            if (!u5Var.a(c0767v2)) {
                setC.add(c0767v2);
            }
        }
        return new K2(setC);
    }
}
