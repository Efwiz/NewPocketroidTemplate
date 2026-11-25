package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: InfixCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/InfixCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InfixCallChecker implements CallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        ResolvedCall<VariableDescriptor> variableCall;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        FunctionDescriptor functionDescriptor = resultingDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) resultingDescriptor : null;
        if (functionDescriptor == null || functionDescriptor.isInfix()) {
            return;
        }
        FunctionDescriptor functionDescriptor2 = functionDescriptor;
        if (DynamicCallsKt.isDynamic(functionDescriptor2) || ErrorUtils.isError(functionDescriptor2)) {
            return;
        }
        VariableAsFunctionResolvedCall variableAsFunctionResolvedCall = resolvedCall instanceof VariableAsFunctionResolvedCall ? (VariableAsFunctionResolvedCall) resolvedCall : null;
        if (variableAsFunctionResolvedCall != null && (variableCall = variableAsFunctionResolvedCall.getVariableCall()) != null) {
            resolvedCall = variableCall;
        }
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        if (CallResolverUtilKt.isInfixCall(call)) {
            DeclarationDescriptor declarationDescriptor = functionDescriptor.getContainingModule();
            Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "getContainingDeclaration(...)");
            String strAsString = DescriptorUtilsKt.getFqNameUnsafe(declarationDescriptor).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            context.getTrace().report(Errors.INFIX_MODIFIER_REQUIRED.on(reportOn, functionDescriptor, strAsString));
        }
    }
}
