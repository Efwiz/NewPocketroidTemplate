package com.android.tools.r8.ir.regalloc;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class d implements Comparable<d> {
    private final int a;
    private final int b;

    public d(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public final int a() {
        return this.b;
    }

    public final int b() {
        return this.a;
    }

    public final boolean c() {
        return this.b < 65535;
    }

    @Override // java.lang.Comparable
    public final int compareTo(d dVar) {
        d dVar2 = dVar;
        int i = dVar2.a;
        int i2 = this.a;
        return i != i2 ? i2 - i : this.b - dVar2.b;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return dVar.a == this.a && dVar.b == this.b;
    }

    public final int hashCode() {
        return (this.b * 7) + this.a;
    }
}
