package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Yz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1548Yz extends com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l {
    static final /* synthetic */ boolean d = true;
    final /* synthetic */ C1574Zz c;

    C1548Yz(C1574Zz c1574Zz) {
        this.c = c1574Zz;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l
    public final void a(int i, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e eVar, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar2) {
        boolean z = d;
        if (!z && this.c.b.a != null) {
            throw new AssertionError(this.c.b.a.a());
        }
        C1593aA c1593aA = this.c.b;
        c1593aA.a = eVar;
        if (!z && c1593aA.b != null) {
            throw new AssertionError(this.c.b.b.a());
        }
        c1593aA.b = jVar;
        if (!z && c1593aA.c != null) {
            throw new AssertionError(this.c.b.c.a());
        }
        c1593aA.c = jVar2;
    }
}
