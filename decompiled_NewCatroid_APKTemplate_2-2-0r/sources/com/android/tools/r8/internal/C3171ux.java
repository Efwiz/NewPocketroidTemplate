package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ux, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3171ux extends AbstractC3246vx {
    static final /* synthetic */ boolean b = true;
    private final String a;

    private C3171ux(String str) {
        if (!b && str == null) {
            throw new AssertionError();
        }
        this.a = str;
    }

    @Override // com.android.tools.r8.internal.AbstractC3246vx
    public final C3171ux b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3246vx
    public final boolean c() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC3246vx
    public final boolean d() {
        return true;
    }

    public final String e() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C3171ux.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C3171ux) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a;
    }
}
