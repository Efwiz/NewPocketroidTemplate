package com.android.tools.r8.errors;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class CheckEnumUnboxedDiagnostic implements Diagnostic {
    private final List a;

    CheckEnumUnboxedDiagnostic(AbstractC1639am abstractC1639am) {
        this.a = abstractC1639am;
    }

    public static d builder() {
        return new d();
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        StringBuilder sb = new StringBuilder("Enum unboxing checks failed.");
        for (String str : this.a) {
            sb.append(System.lineSeparator());
            sb.append(str);
        }
        return sb.toString();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return Origin.unknown();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return Position.UNKNOWN;
    }
}
