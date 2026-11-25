package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;

/* JADX INFO: compiled from: coroutineCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/CoroutineSuspendCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineSuspendCallChecker implements CallChecker {
    public static final CoroutineSuspendCallChecker INSTANCE = new CoroutineSuspendCallChecker();

    private CoroutineSuspendCallChecker() {
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0084  */
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        boolean z = callableDescriptor instanceof FunctionDescriptor;
        if (z) {
            if (!((FunctionDescriptor) callableDescriptor).isSuspend()) {
                return;
            }
        } else {
            if (!(callableDescriptor instanceof PropertyDescriptor)) {
                return;
            }
            Intrinsics.checkNotNull(callableDescriptor);
            if (!Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(callableDescriptor), CoroutineCallCheckerKt.getCOROUTINE_CONTEXT_FQ_NAME())) {
                return;
            }
        }
        KtElement callElement = resolvedCall.getCall().getCallElement();
        Intrinsics.checkNotNull(callElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtExpression");
        KtExpression ktExpression = (KtExpression) callElement;
        FunctionDescriptor functionDescriptorFindEnclosingSuspendFunction = CoroutineCallCheckerKt.findEnclosingSuspendFunction(context);
        if (functionDescriptorFindEnclosingSuspendFunction != null) {
            FunctionDescriptor functionDescriptor = functionDescriptorFindEnclosingSuspendFunction;
            if (!InlineUtil.checkNonLocalReturnUsage(functionDescriptor, ktExpression, context.getResolutionContext())) {
                PsiFile containingFile = ktExpression.getContainingFile();
                if (containingFile instanceof KtCodeFragment) {
                    PsiElement context2 = ((KtCodeFragment) containingFile).getContext();
                    KtExpression ktExpression2 = context2 != null ? (KtExpression) PsiTreeUtil.getParentOfType(context2, KtExpression.class, false) : null;
                    if (ktExpression2 == null || !InlineUtil.checkNonLocalReturnUsage(functionDescriptor, ktExpression2, context.getResolutionContext())) {
                        context.getTrace().report(Errors.NON_LOCAL_SUSPENSION_POINT.on(reportOn));
                    }
                } else {
                    context.getTrace().report(Errors.NON_LOCAL_SUSPENSION_POINT.on(reportOn));
                }
            } else {
                Iterator<HierarchicalScope> it = ScopeUtilsKt.getParentsWithSelf(context.getScope()).iterator();
                while (it.hasNext()) {
                    if (CoroutineCallCheckerKt.isScopeForDefaultParameterValuesOf(it.next(), functionDescriptorFindEnclosingSuspendFunction)) {
                        context.getTrace().report(Errors.UNSUPPORTED.on(reportOn, "suspend function calls in a context of default parameter value"));
                        break;
                    }
                }
            }
            context.getTrace().record(BindingContext.ENCLOSING_SUSPEND_FUNCTION_FOR_SUSPEND_FUNCTION_CALL, resolvedCall.getCall(), functionDescriptorFindEnclosingSuspendFunction);
            CoroutineCallCheckerKt.checkRestrictsSuspension(functionDescriptorFindEnclosingSuspendFunction, resolvedCall, reportOn, context);
            return;
        }
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        if (CallUtilKt.isCallableReference(call)) {
            return;
        }
        Intrinsics.checkNotNull(callableDescriptor);
        if (z) {
            context.getTrace().report(Errors.ILLEGAL_SUSPEND_FUNCTION_CALL.on(reportOn, resolvedCall.get$suspendPropertyDescriptor()));
        } else if (callableDescriptor instanceof PropertyDescriptor) {
            context.getTrace().report(Errors.ILLEGAL_SUSPEND_PROPERTY_ACCESS.on(reportOn, resolvedCall.get$suspendPropertyDescriptor()));
        }
    }
}
