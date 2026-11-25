package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.EnumC0608b0;
import com.android.tools.r8.graph.InterfaceC0694k5;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.e, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3708e {
    private final InterfaceC0694k5 a;
    private final C0633d0 b;
    private final EnumC0608b0 c;

    C3708e(InterfaceC0694k5 interfaceC0694k5, C0633d0 c0633d0, EnumC0608b0 enumC0608b0) {
        this.a = interfaceC0694k5;
        this.b = c0633d0;
        this.c = enumC0608b0;
    }

    public final InterfaceC0694k5 a() {
        return this.a;
    }

    public final EnumC0608b0 b() {
        return this.c;
    }

    public final C0633d0 c() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C3708e)) {
            return false;
        }
        C3708e c3708e = (C3708e) obj;
        return this.a == c3708e.a && this.b == c3708e.b && this.c == c3708e.c;
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b, this.c);
    }
}
