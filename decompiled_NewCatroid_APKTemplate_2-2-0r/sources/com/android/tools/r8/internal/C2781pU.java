package com.android.tools.r8.internal;

import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2781pU {
    static final /* synthetic */ boolean b = true;
    private final StringBuilder a;

    private C2781pU(StringBuilder sb) {
        this.a = sb;
    }

    public static C2781pU a(StringBuilder sb) {
        return new C2781pU(sb);
    }

    public C2781pU b(String str) {
        this.a.append(str);
        return this;
    }

    public C2781pU c() {
        return b("*");
    }

    public C2781pU d() {
        return b("***");
    }

    public static C2705oU a(StringBuilder sb, Supplier supplier) {
        return new C2705oU(sb, supplier);
    }

    public C2781pU b() {
        return b("**");
    }

    public final C2781pU a(String str) {
        boolean z = b;
        if (!z && str.contains("*")) {
            throw new AssertionError();
        }
        if (z || !str.contains("(...)")) {
            return b(str);
        }
        throw new AssertionError();
    }

    public C2781pU a() {
        return b("(...)");
    }
}
