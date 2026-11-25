package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.z5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3480z5 extends ZE implements InterfaceC1935ec, InterfaceC2319jc {
    private static final C3480z5 a = new C3480z5();

    private C3480z5() {
    }

    public static C3480z5 k() {
        return a;
    }

    @Override // com.android.tools.r8.internal.YE
    public final YE a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.ZE, com.android.tools.r8.internal.YE
    public final InterfaceC1935ec g() {
        return this;
    }

    @Override // com.android.tools.r8.internal.ZE, com.android.tools.r8.internal.YE
    public final InterfaceC2319jc i() {
        return this;
    }

    @Override // com.android.tools.r8.internal.YE
    public final YE a(C0702m c0702m, AbstractC0717o2 abstractC0717o2, YE ye, FW fw) {
        return ye.a();
    }

    @Override // com.android.tools.r8.internal.YE
    public final YE a(C0702m c0702m, AbstractC0717o2 abstractC0717o2, FW fw, Function function) {
        return ((YE) function.apply(this)).a();
    }
}
