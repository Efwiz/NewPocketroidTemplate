package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0999Fx extends AbstractC1059Hx {
    private final int a;

    public C0999Fx(int i) {
        super(0);
        this.a = i;
    }

    public final Integer a() {
        return Integer.valueOf(this.a);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C0999Fx) && this.a == ((C0999Fx) obj).a;
    }

    public final int hashCode() {
        return Integer.hashCode(this.a);
    }

    public final String toString() {
        return "IntValue(value=" + this.a + ')';
    }
}
