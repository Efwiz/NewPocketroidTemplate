package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class A3 {
    private final C1507Xl a;

    private A3() {
        this.a = AbstractC1639am.g();
    }

    public final A3 a() {
        return a("META-INF/services/*", false);
    }

    public final E3 b() {
        AbstractC1639am abstractC1639amA = this.a.a();
        return abstractC1639amA.size() > 0 ? new D3(abstractC1639amA) : new B3();
    }

    public final A3 a(String str, boolean z) {
        this.a.a(new C3(str, z));
        return this;
    }
}
