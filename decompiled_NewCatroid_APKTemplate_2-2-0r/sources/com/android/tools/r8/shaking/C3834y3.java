package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.y3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3834y3 {
    private boolean a;
    private final C1507Xl b;

    private C3834y3() {
        this.a = true;
        this.b = AbstractC1639am.g();
    }

    public final C3834y3 a(E3 e3) {
        this.b.a(e3);
        return this;
    }

    public final C3834y3 b() {
        this.a = false;
        return this;
    }

    public final void c() {
        this.a = true;
    }

    final C3840z3 a() {
        return new C3840z3(this.b.a(), this.a);
    }
}
