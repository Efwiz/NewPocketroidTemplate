package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2984sL {
    private boolean a;
    private boolean b;
    private boolean c;

    public final AbstractC3053tL a() {
        if (e()) {
            return T1.a;
        }
        return (this.a || this.b || this.c) ? new HH(this.b) : L1.a;
    }

    final C2984sL b() {
        this.a = true;
        return this;
    }

    final void c() {
        this.b = true;
    }

    final void d() {
        this.c = true;
    }

    final boolean e() {
        return this.a && this.b && this.c;
    }
}
