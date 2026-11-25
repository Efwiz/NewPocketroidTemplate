package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3397xw extends AbstractC3466yw {
    private final AbstractC3024sx a;
    static final /* synthetic */ boolean c = true;
    private static final C3397xw b = new C3397xw(AbstractC3024sx.a());

    private C3397xw(AbstractC3024sx abstractC3024sx) {
        this.a = abstractC3024sx;
    }

    @Override // com.android.tools.r8.internal.AbstractC3466yw
    public final AbstractC3024sx b() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C3397xw.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C3397xw) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a.toString();
    }
}
