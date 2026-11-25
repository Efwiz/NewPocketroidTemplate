package com.android.tools.r8.internal;

import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Bw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0872Bw extends AbstractC0940Dw {
    static final /* synthetic */ boolean b = true;
    private final String a;

    private C0872Bw(String str) {
        if (!b && str == null) {
            throw new AssertionError();
        }
        this.a = str;
    }

    @Override // com.android.tools.r8.internal.AbstractC0940Dw
    public final String a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C0872Bw.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C0872Bw) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "reference='" + this.a + FormatHelper.QUOTE;
    }
}
