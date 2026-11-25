package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2556mg implements NV {
    private final C0767v2 a;
    private final Map b;

    public C2556mg(C0767v2 c0767v2, IdentityHashMap identityHashMap) {
        this.a = c0767v2;
        this.b = identityHashMap;
    }

    public final Map a() {
        return this.b;
    }

    public final C0767v2 b() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2556mg)) {
            return false;
        }
        C2556mg c2556mg = (C2556mg) obj;
        return this.a == c2556mg.a && this.b.equals(c2556mg.b);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    @Override // com.android.tools.r8.internal.NV
    public final Object[] a(NF nf) {
        return nf.a(this);
    }
}
