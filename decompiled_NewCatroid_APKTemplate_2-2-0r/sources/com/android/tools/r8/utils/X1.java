package com.android.tools.r8.utils;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.position.TextPosition;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class X1 {
    public static C0767v2 a(String str, C0722p1 c0722p1) {
        if (!str.endsWith(".class")) {
            throw new C1367Tb("Illegal main-dex-list entry '" + str + "'.");
        }
        String strA = AbstractC0402a.a(str, 6, 0);
        if (strA.contains(".")) {
            throw new C1367Tb("Illegal main-dex-list entry '" + str + "'.");
        }
        return c0722p1.e("L" + strA + ";");
    }

    public static Set<C0767v2> a(com.android.tools.r8.d0 d0Var, C0722p1 c0722p1) {
        try {
            String strA = d0Var.a();
            Set<C0767v2> setC = OU.c();
            int i = 0;
            int i2 = 0;
            while (i < strA.length()) {
                i2++;
                int iIndexOf = strA.indexOf(10, i);
                if (iIndexOf == -1) {
                    iIndexOf = strA.length();
                }
                String strJ = Y2.j(strA.substring(i, iIndexOf));
                if (!strJ.isEmpty()) {
                    try {
                        setC.add(a(strJ, c0722p1));
                    } catch (C1367Tb e) {
                        throw new C1367Tb(e.getMessage(), e, d0Var.getOrigin(), new TextPosition(i, i2, -1));
                    }
                }
                i = iIndexOf + 1;
            }
            return setC;
        } catch (ResourceException e2) {
            throw new C1367Tb("Failed to parse main-dex resource", e2, d0Var.getOrigin(), Position.UNKNOWN);
        }
    }
}
