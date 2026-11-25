package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ox, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1253Ox extends AbstractC1059Hx {
    private final short a;

    public /* synthetic */ C1253Ox(short s, int i) {
        this(s);
    }

    public final short a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C1253Ox) && this.a == ((C1253Ox) obj).a;
    }

    public final int hashCode() {
        return W00.a(this.a);
    }

    public final String toString() {
        return "UShortValue(value=" + ((Object) String.valueOf(this.a & 65535)) + ')';
    }

    private C1253Ox(short s) {
        super(0);
        this.a = s;
    }
}
