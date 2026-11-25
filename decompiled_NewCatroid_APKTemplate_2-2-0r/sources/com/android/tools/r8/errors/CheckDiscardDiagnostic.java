package com.android.tools.r8.errors;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class CheckDiscardDiagnostic implements Diagnostic {
    private final List a;

    private CheckDiscardDiagnostic(AbstractC1639am abstractC1639am) {
        this.a = abstractC1639am;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, String str) {
        sb.append(System.lineSeparator());
        sb.append(str);
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        final StringBuilder sb = new StringBuilder("Discard checks failed.");
        if (this.a.size() > 0) {
            sb.append(System.lineSeparator());
            sb.append("The following items were not discarded");
            this.a.forEach(new Consumer() { // from class: com.android.tools.r8.errors.CheckDiscardDiagnostic$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CheckDiscardDiagnostic.a(sb, (String) obj);
                }
            });
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
