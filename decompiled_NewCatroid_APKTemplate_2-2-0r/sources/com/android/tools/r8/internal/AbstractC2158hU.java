package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2158hU {
    static final /* synthetic */ boolean a = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static C2082gU a(AbstractC1249Ot abstractC1249Ot) {
        String strF = abstractC1249Ot.f();
        int iIndexOf = strF.indexOf("(");
        int iIndexOf2 = strF.indexOf(Constant.AFTER_QUTO);
        if (iIndexOf2 <= iIndexOf) {
            throw new Z00("Unexpected action: " + strF);
        }
        String strSubstring = strF.substring(0, iIndexOf);
        String strSubstring2 = strF.substring(iIndexOf + 1, iIndexOf2);
        if ("removeInnerFrames".equals(strSubstring)) {
            return C2082gU.a(strSubstring2);
        }
        if (a) {
            throw new Z00("Unexpected action: " + strSubstring);
        }
        throw new AssertionError("Unknown function " + strSubstring);
    }

    abstract C1434Ut a();

    public abstract void a(DT dt);
}
