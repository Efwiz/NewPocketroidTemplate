package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.u3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3809u3 extends AbstractC3815v3 {
    private final String c;

    C3809u3(String str) {
        super();
        this.c = str;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3815v3
    public final boolean a(String str) {
        return this.c.equals(str);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C3809u3) && this.c.equals(((C3809u3) obj).c);
    }

    public final int hashCode() {
        return this.c.hashCode();
    }

    public final String toString() {
        return this.c;
    }
}
