package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1145Kw extends AbstractC1171Lw {
    private final C1027Gw a;

    public C1145Kw(C1027Gw c1027Gw) {
        this.a = c1027Gw;
    }

    @Override // com.android.tools.r8.internal.AbstractC1171Lw
    public final C1027Gw d() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1145Kw.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C1145Kw) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "Member{access=" + this.a + "}";
    }
}
