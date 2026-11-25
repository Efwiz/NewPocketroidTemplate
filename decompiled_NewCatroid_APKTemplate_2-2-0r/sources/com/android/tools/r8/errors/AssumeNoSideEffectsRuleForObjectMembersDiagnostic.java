package com.android.tools.r8.errors;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.C3889e2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class AssumeNoSideEffectsRuleForObjectMembersDiagnostic implements Diagnostic {
    private final List a;
    private final Origin b;
    private final Position c;

    private AssumeNoSideEffectsRuleForObjectMembersDiagnostic(ArrayList arrayList, Origin origin, Position position) {
        this.a = arrayList;
        this.b = origin;
        this.c = position;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        Iterator it = this.a.iterator();
        StringBuilder sbAppend = new StringBuilder("The -assumenosideeffects rule matches the following method(s) on java.lang.Object: ").append(C3889e2.a((MethodReference) it.next(), false, false));
        while (it.hasNext()) {
            sbAppend.append(it.hasNext() ? ", " : " and ").append(C3889e2.a((MethodReference) it.next(), false, false));
        }
        return sbAppend.append(". This is most likely not intended. Consider specifying the methods more precisely.").toString();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.b;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.c;
    }
}
