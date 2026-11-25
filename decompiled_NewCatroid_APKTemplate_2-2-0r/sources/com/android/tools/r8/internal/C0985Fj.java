package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0985Fj implements InterfaceC1710bi {
    final int a;
    final L20 b;
    final boolean c;
    final boolean d = false;

    C0985Fj(int i, L20 l20, boolean z) {
        this.a = i;
        this.b = l20;
        this.c = z;
    }

    public final L20 a() {
        return this.b;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return this.a - ((C0985Fj) obj).a;
    }
}
