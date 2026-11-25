package com.android.tools.r8.internal;

import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2420kv extends AbstractC2496lv {
    static final /* synthetic */ boolean b = true;
    private final C2424kx a;

    private C2420kv(C2424kx c2424kx) {
        if (!b && c2424kx == null) {
            throw new AssertionError();
        }
        this.a = c2424kx;
    }

    @Override // com.android.tools.r8.internal.AbstractC2496lv
    public final C2424kx b() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC2496lv
    public final List c() {
        return Collections.emptyList();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C2420kv.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C2420kv) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a.toString();
    }
}
