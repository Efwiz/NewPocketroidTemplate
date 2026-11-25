package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0402a;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2004fU {
    /* JADX INFO: Access modifiers changed from: private */
    public static C2238iU a(AbstractC1249Ot abstractC1249Ot) {
        String strF = abstractC1249Ot.f();
        int iIndexOf = strF.indexOf(40);
        if (iIndexOf < 1 || !strF.endsWith(Constant.AFTER_QUTO)) {
            throw new C1367Tb("Invalid formatted condition: " + strF);
        }
        String strSubstring = strF.substring(0, iIndexOf);
        String strA = AbstractC0402a.a(strF, 1, iIndexOf + 1);
        if (PsiKeyword.THROWS.equals(strSubstring)) {
            return C2238iU.a(strA);
        }
        throw new C1367Tb("Unexpected condition: " + strF);
    }

    protected abstract C1434Ut a();

    public abstract boolean a(CT ct);
}
