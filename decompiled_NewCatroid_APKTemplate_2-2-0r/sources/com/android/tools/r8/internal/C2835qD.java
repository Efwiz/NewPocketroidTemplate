package com.android.tools.r8.internal;

import com.android.tools.r8.utils.EnumC3891f;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2835qD {
    private final EnumC3891f a;
    private final String b;
    private final String c;
    private final String d;
    private final boolean e;
    private final List f;

    public C2835qD(EnumC3891f enumC3891f, String str, String str2, String str3, boolean z, List list) {
        this.a = enumC3891f;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = z;
        this.f = list;
    }

    public List<String> a() {
        return this.f;
    }

    public final String b() {
        return String.join("\n", this.f);
    }

    public String c() {
        return this.c;
    }

    public final String d() {
        return this.d;
    }

    public EnumC3891f e() {
        return this.a;
    }

    public String f() {
        return this.b;
    }

    public final boolean g() {
        return this.e;
    }
}
