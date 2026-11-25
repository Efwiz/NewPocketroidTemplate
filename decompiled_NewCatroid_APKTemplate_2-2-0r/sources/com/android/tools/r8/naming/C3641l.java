package com.android.tools.r8.naming;

import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.naming.l, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3641l {
    private final String a;
    private final String b;

    public C3641l(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C3641l)) {
            return false;
        }
        C3641l c3641l = (C3641l) obj;
        return this.a.equals(c3641l.a) && this.b.equals(c3641l.b);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }
}
