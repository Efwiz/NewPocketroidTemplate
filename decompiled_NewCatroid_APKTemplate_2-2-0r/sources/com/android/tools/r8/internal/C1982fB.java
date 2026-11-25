package com.android.tools.r8.internal;

import com.android.tools.r8.utils.EnumC3891f;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1982fB {
    private final EnumC3891f a;
    private final String b;
    private final String c;
    private final String d;
    private final boolean e;
    private final List f;

    C1982fB(EnumC3891f enumC3891f, String str, String str2, String str3, boolean z, List list) {
        this.a = enumC3891f;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = z;
        this.f = list;
    }

    public static C1895eB a() {
        return new C1895eB();
    }

    public static C1982fB h() {
        return new C1982fB(EnumC3891f.O, "j$/", "testing", null, true, AbstractC1639am.i());
    }

    public final List b() {
        return this.f;
    }

    public final String c() {
        return this.c;
    }

    public final String d() {
        return this.d;
    }

    public final EnumC3891f e() {
        return this.a;
    }

    public final String f() {
        return this.b;
    }

    public final boolean g() {
        return this.e;
    }
}
