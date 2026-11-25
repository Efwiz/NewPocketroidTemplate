package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.g, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2038g extends LK {
    static final C2038g a = new C2038g();

    private C2038g() {
    }

    @Override // com.android.tools.r8.internal.LK
    public final Object a(Object obj) {
        int i = AbstractC3123uM.a;
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException("use Optional.orNull() instead of Optional.or(null)");
    }

    public final boolean equals(Object obj) {
        return obj == this;
    }

    public final int hashCode() {
        return 2040732332;
    }

    public final String toString() {
        return "Optional.absent()";
    }
}
