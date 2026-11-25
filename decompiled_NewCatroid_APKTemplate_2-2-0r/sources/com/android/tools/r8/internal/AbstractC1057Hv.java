package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Hv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1057Hv extends AbstractC2509m2 {
    AbstractC1057Hv() {
        super(C2422kw.h);
    }

    private String b() {
        return " @" + c() + ": ";
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public void a(String str, Object obj) {
        throw new C2883qv("Unexpected value in" + b() + str + " = " + obj);
    }

    public abstract String c();

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public AbstractC2509m2 a(String str, String str2) {
        throw new C2883qv("Unexpected annotation in" + b() + str);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public void a(String str, String str2, String str3) {
        throw new C2883qv("Unexpected enum in" + b() + str);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public AbstractC2509m2 a(String str) {
        throw new C2883qv("Unexpected array in" + b() + str);
    }
}
