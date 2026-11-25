package com.android.tools.r8.naming;

import com.android.tools.r8.utils.EnumC3891f;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class x0 {
    private String a;
    private boolean b;
    private EnumC3891f c;
    private MapVersion d;
    private J0 e;

    public final x0 a(String str) {
        this.a = str;
        return this;
    }

    public final x0 a(EnumC3891f enumC3891f) {
        this.c = enumC3891f;
        return this;
    }

    public final x0 a(boolean z) {
        this.b = z;
        return this;
    }

    public final x0 a(MapVersion mapVersion) {
        this.d = mapVersion;
        return this;
    }

    public final x0 a(J0 j0) {
        this.e = j0;
        return this;
    }

    public final y0 a() {
        return new y0(this.a, this.b, this.c, this.d, this.e);
    }
}
