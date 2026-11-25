package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3970u0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3323wz implements InterfaceC3680a0 {
    public C0767v2 a(AbstractC1476Wj abstractC1476Wj) {
        return null;
    }

    abstract boolean a(C0942Dy c0942Dy, C0702m c0702m);

    /* JADX WARN: Multi-variable type inference failed */
    public static AbstractC3323wz a(AbstractC1438Ux abstractC1438Ux, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        rzIA rzia = null;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        Object[] objArr3 = 0;
        Object[] objArr4 = 0;
        if (abstractC1438Ux instanceof C1391Tx) {
            String strA = ((C1391Tx) abstractC1438Ux).a();
            boolean zStartsWith = strA.startsWith(".");
            String strM = C3970u0.m(zStartsWith ? strA.substring(1) : strA);
            return C3970u0.z(strM) ? new C2957rz(C2973sA.b(strM, c0722p1, strM), zStartsWith) : new C3173uz(strA);
        }
        if (abstractC1438Ux instanceof AbstractC1438Ux.a) {
            return new C3026sz(((AbstractC1438Ux.a) abstractC1438Ux).a());
        }
        if (abstractC1438Ux instanceof AbstractC1438Ux.b) {
            return new C3096tz(((AbstractC1438Ux.b) abstractC1438Ux).a());
        }
        o2.warning(C1339Rz.a(abstractC1438Ux.toString()));
        return new C3248vz(abstractC1438Ux.toString());
    }
}
