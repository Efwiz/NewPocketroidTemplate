package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0911Cv extends AbstractC0939Dv {
    private String a;

    private C0911Cv() {
        super();
    }

    public final Object visitString(String str, Object obj) {
        this.a = str;
        return this;
    }
}
