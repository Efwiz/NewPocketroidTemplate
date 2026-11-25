package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.W8;

/* JADX INFO: renamed from: com.android.tools.r8.graph.x, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0795x {
    public static C0771w a(o5 o5Var, W8 w8, String str, C0702m c0702m) {
        StringBuilder sbAppend = new StringBuilder("Invalid try catch range for ").append(com.android.tools.r8.utils.Y2.a(", ", w8.c, new C0796x$$ExternalSyntheticLambda0())).append(": ").append(str).append(".");
        if (c0702m.q()) {
            sbAppend.append(" In later version of R8, the method may be assumed not reachable.");
        }
        return new C0771w(o5Var.getOrigin(), c0702m.E().a((AbstractC1476Wj) null, o5Var.getReference()), sbAppend.toString());
    }

    public static C0771w a(o5 o5Var, int i, B7 b7, String str, C0702m c0702m) {
        StringBuilder sbAppend = new StringBuilder("Invalid stack map table at instruction ").append(i).append(": ").append(b7).append(", error: ").append(str).append(".");
        if (c0702m.q()) {
            sbAppend.append(" In later version of R8, the method may be assumed not reachable.");
        }
        return new C0771w(o5Var.getOrigin(), c0702m.E().a((AbstractC1476Wj) null, o5Var.getReference()), sbAppend.toString());
    }
}
