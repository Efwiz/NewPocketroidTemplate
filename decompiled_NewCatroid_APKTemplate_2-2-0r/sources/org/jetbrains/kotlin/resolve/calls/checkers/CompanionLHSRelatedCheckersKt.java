package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: CompanionLHSRelatedCheckers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002¨\u0006\b"}, d2 = {"isReferenceToShortFormCompanion", "", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "lhs", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompanionLHSRelatedCheckersKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isReferenceToShortFormCompanion(ReceiverValue receiverValue, PsiElement psiElement, CallCheckerContext callCheckerContext) {
        KtExpression selectorExpression;
        ClassifierDescriptor this$0 = receiverValue.getType().getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null || !classDescriptor.getIsCompanionObject()) {
            return false;
        }
        KtDotQualifiedExpression ktDotQualifiedExpression = psiElement instanceof KtDotQualifiedExpression ? (KtDotQualifiedExpression) psiElement : null;
        if (ktDotQualifiedExpression != null && (selectorExpression = ktDotQualifiedExpression.getSelectorExpression()) != null) {
            psiElement = selectorExpression;
        }
        KtReferenceExpression ktReferenceExpression = psiElement instanceof KtReferenceExpression ? (KtReferenceExpression) psiElement : null;
        return (ktReferenceExpression == null || callCheckerContext.getTrace().getContext().get(BindingContext.SHORT_REFERENCE_TO_COMPANION_OBJECT, ktReferenceExpression) == null) ? false : true;
    }
}
