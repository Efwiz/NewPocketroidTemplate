package com.android.tools.r8.errors;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class UnsupportedMainDexListUsageDiagnostic implements Diagnostic {
    private final Origin a;

    public UnsupportedMainDexListUsageDiagnostic(Origin origin) {
        this.a = origin;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return "Unsupported usage of main-dex list. The usage of main-dex-list content for the compilation of non-DEX inputs is deprecated. See issue https://issuetracker.google.com/181858113 for context.";
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.a;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return Position.UNKNOWN;
    }
}
