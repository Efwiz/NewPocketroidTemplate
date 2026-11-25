package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JvmArrayVariableInLoopAssignmentChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016J$\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmArrayVariableInLoopAssignmentChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AdditionalTypeChecker;", "()V", "checkType", "", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expressionTypeWithSmartCast", "c", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "isOuterForLoopRangeVariable", "", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmArrayVariableInLoopAssignmentChecker implements AdditionalTypeChecker {
    public static final JvmArrayVariableInLoopAssignmentChecker INSTANCE = new JvmArrayVariableInLoopAssignmentChecker();

    private JvmArrayVariableInLoopAssignmentChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker
    public void checkType(KtExpression expression, KotlinType expressionType, KotlinType expressionTypeWithSmartCast, ResolutionContext<?> c) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(expressionType, "expressionType");
        Intrinsics.checkNotNullParameter(expressionTypeWithSmartCast, "expressionTypeWithSmartCast");
        Intrinsics.checkNotNullParameter(c, "c");
        if (c.languageVersionSettings.supportsFeature(LanguageFeature.ProperForInArrayLoopRangeVariableAssignmentSemantic)) {
            return;
        }
        KtBinaryExpression ktBinaryExpression = expression instanceof KtBinaryExpression ? (KtBinaryExpression) expression : null;
        if (ktBinaryExpression != null && Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.EQ)) {
            KtExpression left = ktBinaryExpression.getLeft();
            KtSimpleNameExpression ktSimpleNameExpression = left instanceof KtSimpleNameExpression ? (KtSimpleNameExpression) left : null;
            if (ktSimpleNameExpression == null) {
                return;
            }
            BindingContext context = c.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktSimpleNameExpression, context);
            if (resolvedCall == null) {
                return;
            }
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            LocalVariableDescriptor localVariableDescriptor = resultingDescriptor instanceof LocalVariableDescriptor ? (LocalVariableDescriptor) resultingDescriptor : null;
            if (localVariableDescriptor == null || (localVariableDescriptor instanceof SyntheticFieldDescriptor) || localVariableDescriptor.getIsDelegated()) {
                return;
            }
            KotlinType returnType = localVariableDescriptor.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            if (KotlinBuiltIns.isArrayOrPrimitiveArray(returnType) && isOuterForLoopRangeVariable(expression, localVariableDescriptor, c) && c.dataFlowValueFactory.createDataFlowValue(ktSimpleNameExpression, returnType, c).getKind() == DataFlowValue.Kind.STABLE_VARIABLE) {
                c.trace.report(ErrorsJvm.ASSIGNMENT_TO_ARRAY_LOOP_VARIABLE.on(ktSimpleNameExpression));
            }
        }
    }

    private final boolean isOuterForLoopRangeVariable(KtExpression expression, CallableDescriptor variableDescriptor, ResolutionContext<?> c) {
        for (PsiElement psiElement : PsiUtilsKt.getParents(expression)) {
            if (psiElement instanceof KtForExpression) {
                KtExpression loopRange = ((KtForExpression) psiElement).getLoopRange();
                KtSimpleNameExpression ktSimpleNameExpression = loopRange instanceof KtSimpleNameExpression ? (KtSimpleNameExpression) loopRange : null;
                if (ktSimpleNameExpression == null) {
                    continue;
                } else {
                    BindingContext context = c.trace.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                    ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktSimpleNameExpression, context);
                    if (resolvedCall != null && Intrinsics.areEqual(resolvedCall.getResultingDescriptor(), variableDescriptor)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
