package com.android.tools.r8.naming;

/* JADX INFO: renamed from: com.android.tools.r8.naming.f0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3624f0 implements I {
    private int a = 0;
    private int b = 1;

    C3624f0() {
    }

    @Override // com.android.tools.r8.naming.I
    public final int a() {
        int i = this.b;
        this.b = i + 1;
        return i;
    }

    @Override // com.android.tools.r8.naming.I
    public final int b() {
        return this.a;
    }

    @Override // com.android.tools.r8.naming.I
    public final int c() {
        int i = this.a;
        this.a = i + 1;
        return i;
    }
}
