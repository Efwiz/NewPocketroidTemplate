package com.android.tools.r8.internal;

import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2705oU extends C2781pU {
    final Supplier c;
    final StringBuilder d;

    private C2705oU(StringBuilder sb, Supplier supplier) {
        super(sb);
        this.d = new StringBuilder();
        this.c = supplier;
    }

    @Override // com.android.tools.r8.internal.C2781pU
    public final C2781pU a() {
        return b("(...)");
    }

    @Override // com.android.tools.r8.internal.C2781pU
    public final C2781pU b(String str) {
        this.d.append(str);
        ((C2781pU) this).a.append(str);
        return this;
    }

    @Override // com.android.tools.r8.internal.C2781pU
    public final C2781pU c() {
        this.d.append('<').append(this.c.get()).append('>');
        ((C2781pU) this).a.append("*");
        return this;
    }

    @Override // com.android.tools.r8.internal.C2781pU
    public final C2781pU d() {
        this.d.append('<').append(this.c.get()).append('>');
        ((C2781pU) this).a.append("***");
        return this;
    }

    @Override // com.android.tools.r8.internal.C2781pU
    public final C2781pU b() {
        this.d.append('<').append(this.c.get()).append('>');
        ((C2781pU) this).a.append("**");
        return this;
    }
}
