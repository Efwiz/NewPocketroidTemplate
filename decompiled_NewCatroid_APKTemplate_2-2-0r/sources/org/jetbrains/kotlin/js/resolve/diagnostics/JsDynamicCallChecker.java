package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtWhenConditionInRange;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: compiled from: JsDynamicCallChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\"\u0010\u000b\u001a\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001c\u0010\u000e\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000f\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\bH\u0002¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsDynamicCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkIdentifier", "name", "", "checkSpreadOperator", "reportInOperation", "reportedOn", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsDynamicCallChecker implements CallChecker {
    public static final JsDynamicCallChecker INSTANCE = new JsDynamicCallChecker();

    private JsDynamicCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNull(resultingDescriptor);
        if (!DynamicCallsKt.isDynamic(resultingDescriptor)) {
            checkSpreadOperator(resolvedCall, context);
            return;
        }
        KtElement callElement = resolvedCall.getCall().getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        if (callElement instanceof KtArrayAccessExpression) {
            if (((KtArrayAccessExpression) callElement).getIndexExpressions().size() > 1) {
                context.getTrace().report(ErrorsJs.WRONG_OPERATION_WITH_DYNAMIC.on(reportOn, "indexed access with more than one index"));
            }
        } else if (callElement instanceof KtWhenConditionInRange) {
            reportInOperation(context, reportOn);
        } else if (callElement instanceof KtBinaryExpression) {
            IElementType operationToken = ((KtBinaryExpression) callElement).getOperationToken();
            Intrinsics.checkNotNullExpressionValue(operationToken, "getOperationToken(...)");
            ImmutableSet<KtSingleValueToken> IN_OPERATIONS = OperatorConventions.IN_OPERATIONS;
            Intrinsics.checkNotNullExpressionValue(IN_OPERATIONS, "IN_OPERATIONS");
            if (CollectionsKt.contains(IN_OPERATIONS, operationToken)) {
                reportInOperation(context, reportOn);
            } else {
                if (Intrinsics.areEqual(operationToken, KtTokens.RANGE) ? true : Intrinsics.areEqual(operationToken, KtTokens.RANGE_UNTIL)) {
                    context.getTrace().report(ErrorsJs.WRONG_OPERATION_WITH_DYNAMIC.on(reportOn, "`" + ((KtSingleValueToken) operationToken).getValue() + "` operation"));
                }
            }
        } else if (callElement instanceof KtDestructuringDeclarationEntry) {
            KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry = (KtDestructuringDeclarationEntry) callElement;
            PsiElement psi = ktDestructuringDeclarationEntry.getNode().getTreeParent().getPsi();
            Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
            if (!reportedOn(context, psi)) {
                context.getTrace().report(ErrorsJs.WRONG_OPERATION_WITH_DYNAMIC.on(ktDestructuringDeclarationEntry.getLightParent(), "destructuring declaration"));
            }
        } else if (callElement instanceof KtSimpleNameExpression) {
            checkIdentifier(((KtSimpleNameExpression) callElement).getReferencedName(), callElement, context);
        } else if (callElement instanceof KtCallExpression) {
            KtExpression calleeExpression = ((KtCallExpression) callElement).getCalleeExpression();
            if (calleeExpression instanceof KtSimpleNameExpression) {
                checkIdentifier(((KtSimpleNameExpression) calleeExpression).getReferencedName(), calleeExpression, context);
            }
        }
        Iterator<? extends ValueArgument> it = resolvedCall.getCall().getValueArguments().iterator();
        while (it.hasNext()) {
            LeafPsiElement spreadElement = it.next().getSpreadElement();
            if (spreadElement != null) {
                context.getTrace().report(ErrorsJs.SPREAD_OPERATOR_IN_DYNAMIC_CALL.on(spreadElement));
            }
        }
    }

    private final void checkIdentifier(String name2, PsiElement reportOn, CallCheckerContext context) {
        if (name2 == null || context.getLanguageVersionSettings().supportsFeature(LanguageFeature.JsAllowInvalidCharsIdentifiersEscaping) || Intrinsics.areEqual(NameSuggestion.INSTANCE.sanitizeName(name2), name2)) {
            return;
        }
        context.getTrace().report(ErrorsJs.NAME_CONTAINS_ILLEGAL_CHARS.on(reportOn));
    }

    private final void checkSpreadOperator(ResolvedCall<?> resolvedCall, CallCheckerContext context) {
        KotlinType type;
        for (ValueArgument valueArgument : resolvedCall.getCall().getValueArguments()) {
            KtExpression argumentExpression = valueArgument.getArgumentExpression();
            if (argumentExpression != null && (type = context.getTrace().getContext().getType(argumentExpression)) != null && DynamicTypesKt.isDynamic(type) && valueArgument.getSpreadElement() != null) {
                context.getTrace().report(ErrorsJs.WRONG_OPERATION_WITH_DYNAMIC.on(valueArgument.getCallElement(), "spread operator"));
            }
        }
    }

    private final void reportInOperation(CallCheckerContext context, PsiElement reportOn) {
        context.getTrace().report(ErrorsJs.WRONG_OPERATION_WITH_DYNAMIC.on(reportOn, "`in` operation"));
    }

    private final boolean reportedOn(CallCheckerContext context, PsiElement element) {
        Collection<Diagnostic> collectionForElement = context.getTrace().getContext().getDiagnostics().forElement(element);
        if ((collectionForElement instanceof Collection) && collectionForElement.isEmpty()) {
            return false;
        }
        Iterator<T> it = collectionForElement.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((Diagnostic) it.next()).getFactory(), ErrorsJs.WRONG_OPERATION_WITH_DYNAMIC)) {
                return true;
            }
        }
        return false;
    }
}
