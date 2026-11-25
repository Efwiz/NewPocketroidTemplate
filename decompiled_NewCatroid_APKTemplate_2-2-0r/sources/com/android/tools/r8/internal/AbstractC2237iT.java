package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3826x2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2237iT {
    public static boolean a(C0729q2 c0729q2, com.android.tools.r8.utils.A1 a1) {
        String strH0 = c0729q2.getType().H0();
        if (strH0.isEmpty()) {
            return true;
        }
        C3826x2 c3826x2J = a1.D0().j();
        if (c3826x2J.c()) {
            return false;
        }
        if (c3826x2J.a(a1.j0().e("L" + strH0 + ";"))) {
            return true;
        }
        return !a1.W0() && c3826x2J.a(c0729q2.getType());
    }
}
