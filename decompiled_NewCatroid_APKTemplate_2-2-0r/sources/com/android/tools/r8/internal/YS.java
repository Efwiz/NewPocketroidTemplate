package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0657g;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.C3626g0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3970u0;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.HashSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class YS implements ZS {
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final C0722p1 b;
    private final com.android.tools.r8.utils.A1 c;
    private final com.android.tools.r8.shaking.L2 d;
    private final C3626g0 e;

    public YS(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = c0702m.Y();
        this.d = c0702m.Y().D0();
        this.e = new C3626g0(c0702m);
    }

    private boolean b(com.android.tools.r8.graph.r5 r5Var) {
        for (C0729q2 c0729q2 : r5Var.a()) {
            com.android.tools.r8.graph.E accessFlags = c0729q2.getAccessFlags();
            if (!accessFlags.m() && !accessFlags.j() && !this.a.v().a(c0729q2).d((com.android.tools.r8.shaking.K0) this.c)) {
                return true;
            }
            for (com.android.tools.r8.graph.U0 u0 : c0729q2.L1()) {
                if (!u0.D0() || !u0.w0().u1()) {
                    AbstractC0657g accessFlags2 = u0.getAccessFlags();
                    if (!accessFlags2.m() && !accessFlags2.j() && !this.a.v().a(u0, c0729q2).d((com.android.tools.r8.shaking.K0) this.c)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final String a(com.android.tools.r8.graph.r5 r5Var, HashSet hashSet) {
        String strG = C3970u0.g(this.d.p());
        com.android.tools.r8.utils.A1.n nVarO = this.d.o();
        if (!this.a.Y().k1()) {
            if (strG.isEmpty() || b(r5Var)) {
                return r5Var.b();
            }
            return strG + "/" + r5Var.b();
        }
        if (nVarO.c()) {
            return strG;
        }
        if (nVarO.b()) {
            if (r5Var.b().isEmpty() || b(r5Var)) {
                return r5Var.b();
            }
            strG = "";
        } else {
            if (!f && !nVarO.a()) {
                throw new AssertionError();
            }
            if (!strG.isEmpty()) {
                strG = strG + "/";
            }
        }
        return this.e.a(new NN$$ExternalSyntheticLambda0(hashSet), strG);
    }

    public final boolean a(com.android.tools.r8.graph.r5 r5Var) {
        String strG = C3970u0.g(this.d.p());
        com.android.tools.r8.utils.A1.n nVarO = this.d.o();
        if (nVarO.c()) {
            return r5Var.b().equals(strG);
        }
        if (nVarO.b()) {
            return r5Var.b().isEmpty() || b(r5Var);
        }
        if (f || nVarO.a()) {
            return false;
        }
        throw new AssertionError();
    }

    public final C0767v2 a(C0729q2 c0729q2, C0729q2 c0729q3, String str, C3011sk c3011sk) {
        C0767v2 c0767v2B = c0729q2.getType().b(str, this.b);
        if (c0729q3 != null) {
            String strK0 = c0729q2.getType().K0();
            String strK1 = c0729q3.getType().K0();
            if (strK0.startsWith(strK1 + "$")) {
                c0767v2B = c0767v2B.c(((C0767v2) c3011sk.get(c0729q3.getType())).K0() + strK0.substring(strK1.length()), this.b);
            } else if (!f && !this.c.R0) {
                throw new AssertionError("Unexpected name for inner class: " + c0729q2.getType().r0() + " (outer class: " + c0729q3.getType().r0() + Constant.AFTER_QUTO);
            }
        }
        int i = 1;
        C0767v2 c0767v2A = c0767v2B;
        while (true) {
            if (!c3011sk.g().containsKey(c0767v2A) && !((C3733i) this.a.f()).p(c0767v2A) && !((C3733i) this.a.f()).q().a(c0767v2A)) {
                return c0767v2A;
            }
            c0767v2A = c0767v2B.a(new StringBuilder().append(i).toString(), this.b);
            i++;
        }
    }
}
