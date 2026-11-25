package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1118Jx extends AbstractC1059Hx {
    private final short a;

    public C1118Jx(short s) {
        super(0);
        this.a = s;
    }

    public final Short a() {
        return Short.valueOf(this.a);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C1118Jx) && this.a == ((C1118Jx) obj).a;
    }

    public final int hashCode() {
        return Short.hashCode(this.a);
    }

    public final String toString() {
        return "ShortValue(value=" + ((int) this.a) + ')';
    }
}
