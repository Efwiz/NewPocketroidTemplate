package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3762m1;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2913rL {
    static final /* synthetic */ boolean a = true;

    public static boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        C3762m1 c3762m1A = c0702m.a(o5Var);
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        if (c3762m1A.j(a1Y) && c0702m.a(o5Var).n(a1Y)) {
            return o5Var.e().D1().a();
        }
        return false;
    }

    public static boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, int i) {
        if (!a && !a(c0702m, o5Var)) {
            throw new AssertionError();
        }
        if (i != 0) {
            return true;
        }
        if (o5Var.e().C1()) {
            return false;
        }
        if (o5Var.e().B1()) {
            return !o5Var.getAccessFlags().c0() && c0702m.a(o5Var).i(c0702m.Y());
        }
        return true;
    }
}
