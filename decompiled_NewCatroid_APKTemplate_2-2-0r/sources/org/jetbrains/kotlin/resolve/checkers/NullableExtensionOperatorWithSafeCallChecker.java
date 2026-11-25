package org.jetbrains.kotlin.resolve.checkers;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMappingKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: NullableExtensionOperatorWithSafeCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/NullableExtensionOperatorWithSafeCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "RELEVANT_OPERATORS", "", "Lorg/jetbrains/kotlin/name/Name;", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "isNullableSafeCallReceiver", "", "receiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullableExtensionOperatorWithSafeCallChecker implements CallChecker {
    public static final NullableExtensionOperatorWithSafeCallChecker INSTANCE = new NullableExtensionOperatorWithSafeCallChecker();
    private static final Set<Name> RELEVANT_OPERATORS;

    private NullableExtensionOperatorWithSafeCallChecker() {
    }

    static {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.addAll(OperatorNameConventions.ASSIGNMENT_OPERATIONS);
        linkedHashSet.add(OperatorNameConventions.INC);
        linkedHashSet.add(OperatorNameConventions.DEC);
        linkedHashSet.add(OperatorNameConventions.GET);
        linkedHashSet.add(OperatorNameConventions.SET);
        RELEVANT_OPERATORS = linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (ArgumentMappingKt.isReallySuccess(resolvedCall)) {
            Name name2 = resolvedCall.getResultingDescriptor().getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (RELEVANT_OPERATORS.contains(name2) && isNullableSafeCallReceiver(resolvedCall.getExtensionReceiver())) {
                KtElement callElement = resolvedCall.getCall().getCallElement();
                Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
                if (callElement instanceof KtCallExpression) {
                    String identifier = name2.getIdentifier();
                    KtExpression calleeExpression = ((KtCallExpression) callElement).getCalleeExpression();
                    KtNameReferenceExpression ktNameReferenceExpression = calleeExpression instanceof KtNameReferenceExpression ? (KtNameReferenceExpression) calleeExpression : null;
                    if (Intrinsics.areEqual(identifier, ktNameReferenceExpression != null ? ktNameReferenceExpression.getReferencedName() : null)) {
                        return;
                    }
                }
                context.getTrace().report(Errors.NULLABLE_EXTENSION_OPERATOR_WITH_SAFE_CALL_RECEIVER.on(reportOn));
            }
        }
    }

    private final boolean isNullableSafeCallReceiver(ReceiverValue receiverValue) {
        if (!(receiverValue instanceof ExpressionReceiver)) {
            return false;
        }
        ExpressionReceiver expressionReceiver = (ExpressionReceiver) receiverValue;
        KotlinType type = expressionReceiver.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        if (KotlinTypeKt.isNullable(type)) {
            KotlinType type2 = expressionReceiver.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (!FlexibleTypesKt.isFlexible(type2)) {
                KtExpression expression = expressionReceiver.getExpression();
                return (expression instanceof KtSafeQualifiedExpression) && !(((KtSafeQualifiedExpression) expression).getMyParent() instanceof KtParenthesizedExpression);
            }
        }
        return false;
    }
}
