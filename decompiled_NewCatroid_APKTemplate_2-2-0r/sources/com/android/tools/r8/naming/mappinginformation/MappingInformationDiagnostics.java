package com.android.tools.r8.naming.mappinginformation;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.position.TextPosition;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class MappingInformationDiagnostics implements Diagnostic {
    private final String a;
    private final Position b;

    private MappingInformationDiagnostics(String str, TextPosition textPosition) {
        this.a = str;
        this.b = textPosition;
    }

    static MappingInformationDiagnostics a(int i, String str) {
        return new MappingInformationDiagnostics(String.format("Could not find a handler for %s", str), new TextPosition(1L, i, -1));
    }

    static MappingInformationDiagnostics b(int i) {
        return new MappingInformationDiagnostics(String.format("Could not locate '%s' in the JSON object", "id"), new TextPosition(1L, i, -1));
    }

    static MappingInformationDiagnostics c(int i) {
        return new MappingInformationDiagnostics(String.format("The value of '%s' is not a valid string in the JSON object", "id"), new TextPosition(1L, i, -1));
    }

    public static MappingInformationDiagnostics invalidResidualSignature(String str, int i) {
        return new MappingInformationDiagnostics("The residual signature mapping '" + str + "' is invalid'", new TextPosition(1L, i, -1));
    }

    public static MappingInformationDiagnostics invalidResidualSignatureType(String str, int i) {
        return new MappingInformationDiagnostics("The residual signature mapping '" + str + "' is not of the same type as the member it describes.'", new TextPosition(1L, i, -1));
    }

    public static MappingInformationDiagnostics notAllowedCombination(e eVar, e eVar2, int i) {
        return new MappingInformationDiagnostics("The mapping '" + eVar + "' is not allowed in combination with '" + eVar2 + FormatHelper.QUOTE, new TextPosition(1L, i, -1));
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return this.a;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return Origin.unknown();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.b;
    }

    static MappingInformationDiagnostics a(int i, String str, String str2) {
        return new MappingInformationDiagnostics(String.format("Could not find '%s' for object with %s '%s'", str, "id", str2), new TextPosition(1L, i, -1));
    }

    static MappingInformationDiagnostics a(int i) {
        return new MappingInformationDiagnostics(String.format("Could not decode the information for the object with %s '%s'", PsiTreeChangeEvent.PROP_FILE_NAME, "sourceFile"), new TextPosition(1L, i, -1));
    }
}
