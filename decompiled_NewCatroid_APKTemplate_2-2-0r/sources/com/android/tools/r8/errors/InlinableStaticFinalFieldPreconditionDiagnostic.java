package com.android.tools.r8.errors;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.shaking.C3712e3;
import com.android.tools.r8.utils.V0;
import com.android.tools.r8.utils.V1;
import com.android.tools.r8.utils.Y2;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class InlinableStaticFinalFieldPreconditionDiagnostic implements Diagnostic {
    private final C3712e3 a;
    private final List b;

    public InlinableStaticFinalFieldPreconditionDiagnostic(C3712e3 c3712e3, List<Y0> list) {
        this.a = c3712e3;
        this.b = V1.a((Collection) list, new Function() { // from class: com.android.tools.r8.errors.InlinableStaticFinalFieldPreconditionDiagnostic$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Y0) obj).E0();
            }
        });
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return Y2.b("Rule precondition matches static final fields javac has inlined.", "Such rules are unsound as the shrinker cannot infer the inlining precisely.", "Consider adding !static to the rule.", "Matched fields are: ") + Y2.a((Collection) V1.a((Collection) this.b, new Function() { // from class: com.android.tools.r8.errors.InlinableStaticFinalFieldPreconditionDiagnostic$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return V0.a((FieldReference) obj);
            }
        }));
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.a.l();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.a.m();
    }
}
