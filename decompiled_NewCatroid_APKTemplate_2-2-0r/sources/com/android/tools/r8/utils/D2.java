package com.android.tools.r8.utils;

import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC2251ih;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class D2 extends AbstractC2251ih {
    private static final D2 a = new D2();

    private D2() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final boolean a(Object obj, Object obj2) {
        return ((o5) obj).e() == ((o5) obj2).e();
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final int a(Object obj) {
        return ((o5) obj).getReference().hashCode();
    }

    public static D2 a() {
        return a;
    }
}
