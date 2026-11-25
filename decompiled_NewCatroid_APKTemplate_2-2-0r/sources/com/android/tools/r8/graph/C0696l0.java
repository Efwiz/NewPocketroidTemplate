package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1048Hm;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.graph.l0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0696l0 {
    private final boolean a;
    private final boolean b;
    private final Set c;
    private final Set d;
    private final Set e;

    private C0696l0(boolean z, boolean z2, AbstractC1048Hm abstractC1048Hm, AbstractC1048Hm abstractC1048Hm2, AbstractC1048Hm abstractC1048Hm3) {
        this.a = z;
        this.b = z2;
        this.c = abstractC1048Hm;
        this.d = abstractC1048Hm2;
        this.e = abstractC1048Hm3;
    }

    public static C0689k0 a() {
        return new C0689k0();
    }

    public final Set b() {
        return this.e;
    }

    public final Set c() {
        return this.c;
    }

    public final Set d() {
        return this.d;
    }

    public final boolean e() {
        return !this.e.isEmpty();
    }

    public final boolean f() {
        return this.b;
    }

    public final boolean g() {
        return this.a;
    }

    public final boolean h() {
        return !this.c.isEmpty();
    }

    public final boolean i() {
        return !this.d.isEmpty();
    }
}
