package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0912Cw extends AbstractC0940Dw {
    static final /* synthetic */ boolean b = true;
    private final C0846Aw a;

    private C0912Cw(C0846Aw c0846Aw) {
        if (!b && c0846Aw == null) {
            throw new AssertionError();
        }
        this.a = c0846Aw;
    }

    @Override // com.android.tools.r8.internal.AbstractC0940Dw
    public final C0846Aw b() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C0912Cw.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C0912Cw) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a.toString();
    }
}
