package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Reader;

/* JADX INFO: renamed from: com.android.tools.r8.internal.g9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C2052g9 extends AbstractC2211i9 {
    protected final CharSequence a;

    static {
        OV.a();
    }

    protected C2052g9(CharSequence charSequence) {
        this.a = (CharSequence) AbstractC3123uM.a(charSequence);
    }

    @Override // com.android.tools.r8.internal.AbstractC2211i9
    public Reader a() {
        return new C1980f9(this.a);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x001a A[PHI: r0
      0x001a: PHI (r0v1 java.lang.CharSequence) = (r0v12 java.lang.CharSequence), (r0v13 java.lang.CharSequence) binds: [B:3:0x000d, B:5:0x0017] A[DONT_GENERATE, DONT_INLINE]] */
    public final String toString() {
        String string;
        String string2;
        CharSequence charSequence = this.a;
        int i = AbstractC3123uM.a;
        charSequence.getClass();
        int length = charSequence.length();
        CharSequence charSequence2 = charSequence;
        if (length <= 30) {
            string2 = charSequence.toString();
            if (string2.length() > 30) {
                string = string2;
                charSequence2 = string2;
                string = new StringBuilder(30).append(charSequence2, 0, 27).append("...").toString();
            }
        } else {
            string = string2;
            charSequence2 = string2;
            string = new StringBuilder(30).append(charSequence2, 0, 27).append("...").toString();
        }
        string = string2;
        charSequence2 = string2;
        return new StringBuilder(String.valueOf(string).length() + 17).append("CharSource.wrap(").append(string).append(Constant.AFTER_QUTO).toString();
    }
}
