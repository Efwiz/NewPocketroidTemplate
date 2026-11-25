package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;

/* JADX INFO: compiled from: UnderscoreUsageChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\f\u0010\u0015\u001a\u00020\u0016*\u00020\u0017H\u0002¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/UnderscoreUsageChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkCallElement", "ktElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "checkSimpleNameUsage", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "ktName", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "isUnderscoreOnlyName", "", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnderscoreUsageChecker implements CallChecker {
    public static final UnderscoreUsageChecker INSTANCE = new UnderscoreUsageChecker();

    private UnderscoreUsageChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
            return;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        ConstructorDescriptor constructorDescriptor = resultingDescriptor instanceof ConstructorDescriptor ? (ConstructorDescriptor) resultingDescriptor : null;
        ClassifierDescriptorWithTypeParameters containingDeclaration = constructorDescriptor != null ? constructorDescriptor.getContainingDeclaration() : null;
        if (containingDeclaration == null) {
            Intrinsics.checkNotNull(resultingDescriptor);
            classifierDescriptorWithTypeParameters = resultingDescriptor;
        } else {
            classifierDescriptorWithTypeParameters = containingDeclaration;
        }
        String strAsString = classifierDescriptorWithTypeParameters.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (isUnderscoreOnlyName(strAsString)) {
            KtElement callElement = resolvedCall.getCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
            checkCallElement(callElement, context);
        }
    }

    private final void checkCallElement(KtElement ktElement, CallCheckerContext context) {
        KtExpression calleeExpression;
        if (ktElement instanceof KtSimpleNameExpression) {
            checkSimpleNameUsage((KtSimpleNameExpression) ktElement, context.getTrace());
        } else {
            if (!(ktElement instanceof KtCallExpression) || (calleeExpression = ((KtCallExpression) ktElement).getCalleeExpression()) == null) {
                return;
            }
            INSTANCE.checkCallElement(calleeExpression, context);
        }
    }

    private final void checkSimpleNameUsage(KtSimpleNameExpression ktName, BindingTrace trace) {
        String text = ktName.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        if (isUnderscoreOnlyName(text)) {
            trace.report(Errors.UNDERSCORE_USAGE_WITHOUT_BACKTICKS.on(ktName));
        }
    }

    public final void checkSimpleNameUsage(DeclarationDescriptor descriptor, KtSimpleNameExpression ktName, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(ktName, "ktName");
        Intrinsics.checkNotNullParameter(trace, "trace");
        String strAsString = descriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (isUnderscoreOnlyName(strAsString)) {
            checkSimpleNameUsage(ktName, trace);
        }
    }

    private final boolean isUnderscoreOnlyName(String str) {
        String str2 = str;
        if (str2.length() <= 0) {
            return false;
        }
        for (int i = 0; i < str2.length(); i++) {
            if (str2.charAt(i) != '_') {
                return false;
            }
        }
        return true;
    }
}
