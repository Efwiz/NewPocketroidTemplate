package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ax, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0847Ax extends AbstractC1059Hx {
    private final byte a;

    public C0847Ax(byte b) {
        super(0);
        this.a = b;
    }

    public final Byte a() {
        return Byte.valueOf(this.a);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C0847Ax) && this.a == ((C0847Ax) obj).a;
    }

    public final int hashCode() {
        return Byte.hashCode(this.a);
    }

    public final String toString() {
        return "ByteValue(value=" + ((int) this.a) + ')';
    }
}
