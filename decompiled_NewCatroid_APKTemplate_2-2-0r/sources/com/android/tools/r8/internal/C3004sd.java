package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3004sd implements NV {
    static final /* synthetic */ boolean c = true;
    private final C0684j2 a;
    private final C0684j2 b;

    public C3004sd(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.a = c0684j2;
        this.b = c0684j3;
        boolean z = c;
        if (!z && c0684j2.I0() != c0684j3.a(0, true)) {
            throw new AssertionError();
        }
        if (!z && c0684j3.I0() != c0684j2.a(0, true)) {
            throw new AssertionError();
        }
    }

    public final C0684j2 a() {
        return this.b;
    }

    public final C0684j2 b() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C3004sd)) {
            return false;
        }
        C3004sd c3004sd = (C3004sd) obj;
        return this.a == c3004sd.a && this.b == c3004sd.b;
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    @Override // com.android.tools.r8.internal.NV
    public final Object[] a(NF nf) {
        return nf.a(this);
    }
}
