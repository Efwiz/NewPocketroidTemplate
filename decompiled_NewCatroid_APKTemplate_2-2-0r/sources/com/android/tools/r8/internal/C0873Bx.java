package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Bx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0873Bx extends AbstractC1059Hx {
    private final char a;

    public C0873Bx(char c) {
        super(0);
        this.a = c;
    }

    public final Character a() {
        return Character.valueOf(this.a);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C0873Bx) && this.a == ((C0873Bx) obj).a;
    }

    public final int hashCode() {
        return Character.hashCode(this.a);
    }

    public final String toString() {
        return "CharValue(value=" + this.a + ')';
    }
}
