package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C1875dx extends AbstractC2035fx {
    static final /* synthetic */ boolean b = true;
    private final String a;

    private C1875dx(String str) {
        if (!b && str == null) {
            throw new AssertionError();
        }
        this.a = str;
    }

    @Override // com.android.tools.r8.internal.AbstractC2035fx
    public final String b() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC2035fx
    public boolean c() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2035fx
    public final boolean d() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2035fx
    public boolean e() {
        return this.a.equals("");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C1875dx) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return this.a;
    }
}
