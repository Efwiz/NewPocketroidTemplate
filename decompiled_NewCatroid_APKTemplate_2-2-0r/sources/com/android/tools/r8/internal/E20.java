package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class E20 extends XM {
    private static final E20 d = new E20();

    E20() {
    }

    static E20 R() {
        return d;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public boolean M() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public int O() {
        return 2;
    }

    @Override // com.android.tools.r8.internal.XM
    public String P() {
        throw new C3405y10("Unexpected attempt to get descriptor of " + this);
    }

    @Override // com.android.tools.r8.internal.XM
    public String Q() {
        throw new C3405y10("Unexpected attempt to get type name of " + this);
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public int hashCode() {
        return System.identityHashCode(d);
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public String toString() {
        return "WIDE";
    }
}
