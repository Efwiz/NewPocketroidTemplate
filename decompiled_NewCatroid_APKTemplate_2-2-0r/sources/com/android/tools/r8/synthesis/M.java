package com.android.tools.r8.synthesis;

import com.android.tools.r8.internal.InterfaceC3524zk;
import java.nio.charset.StandardCharsets;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class M extends L.b {
    private final boolean c;

    public M(int i, String str, boolean z) {
        super(i, str);
        this.c = z;
    }

    @Override // com.android.tools.r8.synthesis.L.b
    public void a(InterfaceC3524zk interfaceC3524zk) {
        interfaceC3524zk.a("class", StandardCharsets.UTF_8);
        interfaceC3524zk.a(this.c);
    }

    @Override // com.android.tools.r8.synthesis.L.b
    public boolean d() {
        return this instanceof N;
    }

    @Override // com.android.tools.r8.synthesis.L.b
    public boolean e() {
        return false;
    }

    @Override // com.android.tools.r8.synthesis.L.b
    public boolean g() {
        return false;
    }

    @Override // com.android.tools.r8.synthesis.L.b
    public boolean h() {
        return this.c;
    }

    @Override // com.android.tools.r8.synthesis.L.b
    public final boolean i() {
        return false;
    }
}
