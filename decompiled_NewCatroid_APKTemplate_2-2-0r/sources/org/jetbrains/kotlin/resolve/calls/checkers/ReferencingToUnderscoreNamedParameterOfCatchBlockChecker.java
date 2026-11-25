package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;

/* JADX INFO: compiled from: ReferencingToUnderscoreNamedParameterOfCatchBlockChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/ReferencingToUnderscoreNamedParameterOfCatchBlockChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReferencingToUnderscoreNamedParameterOfCatchBlockChecker implements CallChecker {
    public static final ReferencingToUnderscoreNamedParameterOfCatchBlockChecker INSTANCE = new ReferencingToUnderscoreNamedParameterOfCatchBlockChecker();

    private ReferencingToUnderscoreNamedParameterOfCatchBlockChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if ((resultingDescriptor instanceof LocalVariableDescriptor) && DescriptorUtilsKt.isUnderscoreNamed((VariableDescriptor) resultingDescriptor)) {
            SourceElement source = ((LocalVariableDescriptor) resultingDescriptor).getSourceElement();
            KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
            if (kotlinSourceElement == null) {
                return;
            }
            KtElement psi = kotlinSourceElement.getPsi();
            KtParameter ktParameter = psi instanceof KtParameter ? (KtParameter) psi : null;
            if (ktParameter != null && ktParameter.isCatchParameter()) {
                BindingTrace trace = context.getTrace();
                Diagnostic diagnosticOn = Errors.RESOLVED_TO_UNDERSCORE_NAMED_CATCH_PARAMETER.on(reportOn);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
            }
        }
    }
}
