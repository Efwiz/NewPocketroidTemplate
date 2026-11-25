package com.android.tools.r8.ir.regalloc;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class e implements Comparable<e> {
    public static final e c = new e(0, Integer.MAX_VALUE);
    public int a;
    public int b;

    public e(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    @Override // java.lang.Comparable
    public final int compareTo(e eVar) {
        e eVar2 = eVar;
        int i = this.a;
        int i2 = eVar2.a;
        return i != i2 ? i - i2 : this.b - eVar2.b;
    }

    public final String toString() {
        return "[" + this.a + ", " + this.b + "[";
    }
}
