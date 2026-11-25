package com.android.tools.r8.internal;

import com.android.tools.r8.utils.AbstractC3874b3;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2927rW {
    private boolean a = AbstractC3874b3.a("com.android.tools.r8.startup.instrumentation.instrument", false);
    private String b = AbstractC3874b3.a("com.android.tools.r8.startup.instrumentation.instrumentationserversyntheticcontext");
    private String c = AbstractC3874b3.a("com.android.tools.r8.startup.instrumentation.instrumentationtag");

    public final String a() {
        return this.b;
    }

    public final String b() {
        return this.c;
    }

    public final boolean c() {
        return this.b != null;
    }

    public final boolean d() {
        return this.c != null;
    }

    public final boolean e() {
        return this.a;
    }

    public C2927rW f() {
        this.a = true;
        return this;
    }

    public C2927rW a(String str) {
        this.c = str;
        return this;
    }
}
