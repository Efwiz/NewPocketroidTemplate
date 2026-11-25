package com.android.tools.r8.internal;

import com.android.tools.r8.utils.EnumC3891f;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Yk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1533Yk {
    private final EnumC3891f a;
    private final String b;
    private final String c;
    private final String d;
    private final boolean e;
    private final List f;

    C1533Yk(EnumC3891f enumC3891f, String str, String str2, String str3, boolean z, List list) {
        this.a = enumC3891f;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = z;
        this.f = list;
    }

    public static C1506Xk a() {
        return new C1506Xk();
    }

    public static C1533Yk h() {
        return new C1533Yk(EnumC3891f.O, "unused", "testing", null, true, AbstractC1639am.i());
    }

    public List<String> b() {
        return this.f;
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
