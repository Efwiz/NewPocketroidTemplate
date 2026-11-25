package com.android.tools.r8.internal;

import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Pv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1281Pv extends AbstractC1517Xv {
    private final C2187hv h;
    private String i;

    public C1281Pv(C2187hv c2187hv) {
        this.h = c2187hv;
    }

    @Override // com.android.tools.r8.internal.AbstractC1517Xv, com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, Object obj) {
        if (str.equals("bindingName") && (obj instanceof String)) {
            this.i = (String) obj;
        } else {
            super.a(str, obj);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv
    public final String c() {
        String str = AbstractC1884e2.a;
        return "KeepBinding";
    }

    @Override // com.android.tools.r8.internal.AbstractC1517Xv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        super.a();
        AbstractC0940Dw abstractC0940DwD = d();
        if (!abstractC0940DwD.c()) {
            this.h.a(this.i, abstractC0940DwD.b());
        } else {
            throw new C2883qv("Invalid binding reference to '" + abstractC0940DwD.a() + "' in binding definition of '" + this.i + FormatHelper.QUOTE);
        }
    }
}
