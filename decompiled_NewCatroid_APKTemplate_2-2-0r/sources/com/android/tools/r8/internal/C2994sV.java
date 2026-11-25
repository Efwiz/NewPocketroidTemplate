package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2994sV extends XM {
    private static final C2994sV d = new C2994sV();

    C2994sV() {
    }

    static C2994sV R() {
        return d;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public boolean K() {
        return true;
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
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public int hashCode() {
        return System.identityHashCode(d);
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public String toString() {
        return "SINGLE";
    }
}
