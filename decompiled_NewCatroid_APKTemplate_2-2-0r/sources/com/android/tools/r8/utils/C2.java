package com.android.tools.r8.utils;

import com.android.tools.r8.graph.m5;
import com.android.tools.r8.internal.AbstractC2251ih;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C2 extends AbstractC2251ih {
    private static final C2 a = new C2();

    private C2() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final boolean a(Object obj, Object obj2) {
        return ((m5) obj).e() == ((m5) obj2).e();
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final int a(Object obj) {
        return ((m5) obj).getReference().hashCode();
    }

    public static C2 a() {
        return a;
    }
}
