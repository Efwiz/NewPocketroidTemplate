package com.android.tools.r8.errors;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.EnumC3891f;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class UnsupportedFeatureDiagnostic implements Diagnostic {
    private final String a;
    private final EnumC3891f b;
    private final Origin c;
    private final Position d;

    UnsupportedFeatureDiagnostic(String str, EnumC3891f enumC3891f, Origin origin, Position position) {
        this.a = str;
        this.b = enumC3891f;
        this.c = origin;
        this.d = position;
    }

    public static String makeMessage(EnumC3891f enumC3891f, String str, String str2) {
        String str3;
        if (enumC3891f == null) {
            str3 = str + " are not supported at any API level known by the compiler";
        } else {
            str3 = str + " are only supported starting with " + enumC3891f.e() + " (--min-api " + enumC3891f.d() + Constant.AFTER_QUTO;
        }
        return str2 != null ? str3 + ": " + str2 : str3;
    }

    public String getFeatureDescriptor() {
        return this.a;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.c;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.d;
    }

    public int getSupportedApiLevel() {
        EnumC3891f enumC3891f = this.b;
        if (enumC3891f == null) {
            return -1;
        }
        return enumC3891f.d();
    }
}
