package com.android.tools.r8.internal;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.util.internal.StringUtil;

/* JADX INFO: renamed from: com.android.tools.r8.internal.n20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2584n20 {
    private final C2510m20 a;
    private final EnumC2609nP b;
    private final int c;
    private final Integer d;
    private final String e;

    public C2584n20(C2510m20 c2510m20, EnumC2609nP enumC2609nP, int i, Integer num, String str) {
        AbstractC0908Cs.a(i, FirebaseAnalytics.Param.LEVEL);
        this.a = c2510m20;
        this.b = enumC2609nP;
        this.c = i;
        this.d = num;
        this.e = str;
    }

    public final Integer a() {
        return this.d;
    }

    public final EnumC2609nP b() {
        return this.b;
    }

    public final int c() {
        return this.c;
    }

    public final String d() {
        return this.e;
    }

    public final C2510m20 e() {
        return this.a;
    }

    public final String toString() {
        return "since " + this.a + StringUtil.SPACE + AbstractC2321je.a(this.c) + (this.d != null ? " error " + this.d : "") + (this.e != null ? ": " + this.e : "");
    }
}
