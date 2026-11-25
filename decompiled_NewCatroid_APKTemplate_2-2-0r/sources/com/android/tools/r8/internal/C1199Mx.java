package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1199Mx extends AbstractC1059Hx {
    private final int a;

    public /* synthetic */ C1199Mx(int i, int i2) {
        this(i);
    }

    public final int a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C1199Mx) && this.a == ((C1199Mx) obj).a;
    }

    public final int hashCode() {
        return T00.c(this.a);
    }

    public final String toString() {
        return "UIntValue(value=" + ((Object) String.valueOf(((long) this.a) & 4294967295L)) + ')';
    }

    private C1199Mx(int i) {
        super(0);
        this.a = i;
    }
}
