package com.android.tools.r8.internal;

import java.util.LinkedHashMap;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2404kg implements NV {
    private final C2630ne a;
    private final C2630ne b;
    private final C2630ne c;
    private final LinkedHashMap d;

    public C2404kg(C2630ne c2630ne, C2630ne c2630ne2, C2630ne c2630ne3, LinkedHashMap linkedHashMap) {
        this.a = c2630ne;
        this.b = c2630ne2;
        this.c = c2630ne3;
        this.d = linkedHashMap;
    }

    public final LinkedHashMap a() {
        return this.d;
    }

    public final C2630ne b() {
        return this.b;
    }

    public final C2630ne c() {
        return this.c;
    }

    public final C2630ne d() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2404kg)) {
            return false;
        }
        C2404kg c2404kg = (C2404kg) obj;
        return Objects.equals(this.a, c2404kg.a) && Objects.equals(this.b, c2404kg.b) && Objects.equals(this.c, c2404kg.c) && Objects.equals(this.d, c2404kg.d);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b, this.c, this.d);
    }

    @Override // com.android.tools.r8.internal.NV
    public final Object[] a(NF nf) {
        return nf.a(this);
    }
}
