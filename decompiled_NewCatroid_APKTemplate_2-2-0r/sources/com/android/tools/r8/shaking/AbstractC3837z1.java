package com.android.tools.r8.shaking;

import com.android.tools.r8.experimental.graphinfo.GraphNode;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.C1349Sj;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.z1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3837z1 {
    static AbstractC3837z1 a(com.android.tools.r8.graph.O0 o0) {
        return new C3767n1(o0);
    }

    static C3778p1 b(o5 o5Var) {
        return new C3778p1(o5Var.e());
    }

    public static AbstractC3837z1 c(o5 o5Var) {
        return new C3784q1(o5Var.a(), o5Var.e());
    }

    public static AbstractC3837z1 d(o5 o5Var) {
        return new C3788r1(o5Var.e());
    }

    public static AbstractC3837z1 e(o5 o5Var) {
        return new C3794s1(o5Var.e());
    }

    public static AbstractC3837z1 f(o5 o5Var) {
        return new C3800t1(o5Var.e());
    }

    public static C3824x1 g(o5 o5Var) {
        return new C3824x1(o5Var.e());
    }

    public static AbstractC3837z1 h(o5 o5Var) {
        return new C3831y1(o5Var.e());
    }

    public abstract GraphNode a(M0 m0);

    public abstract C1349Sj.a a();

    public static AbstractC3837z1 a(C0767v2 c0767v2) {
        return new C3806u1(c0767v2);
    }

    public static AbstractC3837z1 a(o5 o5Var) {
        return new C3812v1(o5Var.e());
    }

    public static AbstractC3837z1 a(C0633d0 c0633d0, InterfaceC0694k5 interfaceC0694k5) {
        return new C3818w1(c0633d0, interfaceC0694k5);
    }
}
