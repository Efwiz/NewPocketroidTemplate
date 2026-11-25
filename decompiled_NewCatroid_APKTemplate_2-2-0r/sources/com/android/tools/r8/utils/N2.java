package com.android.tools.r8.utils;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.DiagnosticsLevel;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class N2 {
    private final DiagnosticsLevel a;
    private final DiagnosticsLevel b;
    private final String c;

    public N2(DiagnosticsLevel diagnosticsLevel, DiagnosticsLevel diagnosticsLevel2, String str) {
        this.a = diagnosticsLevel;
        this.b = diagnosticsLevel2;
        this.c = str;
    }

    public final DiagnosticsLevel a(DiagnosticsLevel diagnosticsLevel, Diagnostic diagnostic) {
        if (diagnosticsLevel != this.a) {
            return diagnosticsLevel;
        }
        if (this.c.length() == 0 || this.c.equals(diagnostic.getClass().getSimpleName()) || this.c.equals(diagnostic.getClass().getTypeName())) {
            return this.b;
        }
        for (Class<?> cls : diagnostic.getClass().getInterfaces()) {
            if (this.c.equals(cls.getSimpleName()) || this.c.equals(cls.getTypeName())) {
                return this.b;
            }
        }
        return diagnosticsLevel;
    }
}
