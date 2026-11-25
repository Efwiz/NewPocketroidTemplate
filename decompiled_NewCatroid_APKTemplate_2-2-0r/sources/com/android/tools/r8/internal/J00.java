package com.android.tools.r8.internal;

import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class J00 {
    final InterfaceC2729oo a;
    final S10 b;

    J00(InterfaceC2729oo interfaceC2729oo, S10 s10) {
        this.a = interfaceC2729oo;
        this.b = s10;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || J00.class != obj.getClass()) {
            return false;
        }
        J00 j00 = (J00) obj;
        return this.a == j00.a && this.b == j00.b;
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }
}
