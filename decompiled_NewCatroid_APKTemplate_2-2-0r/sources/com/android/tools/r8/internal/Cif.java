package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.if, reason: invalid class name */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Cif implements Comparable {
    public final C3038t6 a;
    public int b;
    public int c;

    public Cif(C3038t6 c3038t6, int i, int i2) {
        this.a = c3038t6;
        this.b = i;
        this.c = i2;
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final int compareTo(Cif cif) {
        return Integer.compare(this.b, cif.b);
    }
}
