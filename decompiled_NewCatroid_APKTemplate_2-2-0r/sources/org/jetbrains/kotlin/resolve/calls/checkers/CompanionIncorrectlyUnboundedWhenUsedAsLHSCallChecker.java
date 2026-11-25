package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.resolve.calls.context.CallPosition;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassQualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: CompanionLHSRelatedCheckers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/CompanionIncorrectlyUnboundedWhenUsedAsLHSCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompanionIncorrectlyUnboundedWhenUsedAsLHSCallChecker implements CallChecker {
    public static final CompanionIncorrectlyUnboundedWhenUsedAsLHSCallChecker INSTANCE = new CompanionIncorrectlyUnboundedWhenUsedAsLHSCallChecker();

    private CompanionIncorrectlyUnboundedWhenUsedAsLHSCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        ReceiverValue dispatchReceiver;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        PsiElement parent = resolvedCall.getCall().getCallElement().getMyParent();
        KtCallableReferenceExpression ktCallableReferenceExpression = parent instanceof KtCallableReferenceExpression ? (KtCallableReferenceExpression) parent : null;
        if (ktCallableReferenceExpression == null) {
            return;
        }
        Receiver explicitReceiver = resolvedCall.getCall().getExplicitReceiver();
        ClassQualifier classQualifier = explicitReceiver instanceof ClassQualifier ? (ClassQualifier) explicitReceiver : null;
        if (classQualifier != null && (dispatchReceiver = resolvedCall.getDispatchReceiver()) != null && Intrinsics.areEqual(dispatchReceiver, classQualifier.getClassValueReceiver()) && (context.getResolutionContext().callPosition instanceof CallPosition.CallableReferenceRhs)) {
            ClassifierDescriptor this$0 = dispatchReceiver.getType().getConstructor().getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            if (classDescriptor != null && classDescriptor.getIsCompanionObject()) {
                context.getTrace().report(Errors.INCORRECT_CALLABLE_REFERENCE_RESOLUTION_FOR_COMPANION_LHS.on(ktCallableReferenceExpression));
            }
        }
    }
}
