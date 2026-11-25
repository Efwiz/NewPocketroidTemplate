package com.android.tools.r8.naming;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.StringDiagnostic;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class w0 extends StringDiagnostic {
    private w0(String str, Position position) {
        super(str, Origin.unknown(), position);
    }

    static w0 a(String str, Position position) {
        return new w0(String.format("'%s' already has a mapping", C3970u0.b(str)), position);
    }

    static w0 a(String str, String str2, Position position) {
        return new w0(String.format("'%s' in '%s' already has a mapping", str, C3970u0.b(str2)), position);
    }

    static w0 a(String str, String str2, String str3, Position position) {
        return new w0(String.format("'%s' and '%s' map to same name: '%s'", str, str2, str3), position);
    }
}
