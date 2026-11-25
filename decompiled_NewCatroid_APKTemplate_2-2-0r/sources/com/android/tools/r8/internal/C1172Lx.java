package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1172Lx extends AbstractC1059Hx {
    private final byte a;

    public /* synthetic */ C1172Lx(byte b, int i) {
        this(b);
    }

    public final byte a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C1172Lx) && this.a == ((C1172Lx) obj).a;
    }

    public final int hashCode() {
        return S00.a(this.a);
    }

    public final String toString() {
        return "UByteValue(value=" + ((Object) String.valueOf(this.a & 255)) + ')';
    }

    private C1172Lx(byte b) {
        super(0);
        this.a = b;
    }
}
