package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3538zx extends AbstractC1059Hx {
    private final boolean a;

    public C3538zx(boolean z) {
        super(0);
        this.a = z;
    }

    public final Boolean a() {
        return Boolean.valueOf(this.a);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C3538zx) && this.a == ((C3538zx) obj).a;
    }

    public final int hashCode() {
        boolean z = this.a;
        if (z) {
            return 1;
        }
        return z ? 1 : 0;
    }

    public final String toString() {
        return "BooleanValue(value=" + this.a + ')';
    }
}
