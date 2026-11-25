package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1130Kh extends AbstractC3097u {
    public String g;
    public String h;
    public String i;

    public C1130Kh(int i, String str, String str2, String str3) {
        super(i);
        this.g = str;
        this.h = str2;
        this.i = str3;
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final void a(AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(this.a, this.g, this.h, this.i);
        b(abstractC1899eF);
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final int c() {
        return 4;
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final AbstractC3097u a(Map map) {
        return new C1130Kh(this.a, this.g, this.h, this.i).a(this);
    }
}
