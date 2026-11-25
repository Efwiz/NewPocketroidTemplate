package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.checkers.CoroutineCallCheckerKt;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: SuspensionPointInsideMutexLockChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J<\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0002J$\u0010\u0011\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/SuspensionPointInsideMutexLockChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkCall", "", "parent", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "child", "insideLambda", "reportProblem", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuspensionPointInsideMutexLockChecker implements CallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        FunctionDescriptor functionDescriptorFindEnclosingSuspendFunction;
        SourceElement source;
        PsiElement psi;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        if (!(callableDescriptor instanceof FunctionDescriptor) || !((FunctionDescriptor) callableDescriptor).isSuspend() || (functionDescriptorFindEnclosingSuspendFunction = CoroutineCallCheckerKt.findEnclosingSuspendFunction(context)) == null || (source = functionDescriptorFindEnclosingSuspendFunction.getSourceElement()) == null || (psi = PsiSourceElementKt.getPsi(source)) == null) {
            return;
        }
        boolean z = false;
        PsiElement myParent = reportOn;
        PsiElement psiElement = myParent;
        while (!Intrinsics.areEqual(myParent, psi)) {
            if ((myParent instanceof KtCallExpression) && checkCall(context, (KtCallExpression) myParent, psiElement, z, reportOn, resolvedCall)) {
                return;
            }
            if (myParent instanceof KtLambdaExpression) {
                z = true;
            }
            if (!(myParent instanceof KtValueArgumentList)) {
                psiElement = myParent;
            }
            myParent = myParent.getMyParent();
            if (myParent == null) {
                return;
            }
        }
    }

    private final boolean checkCall(CallCheckerContext context, KtCallExpression parent, PsiElement child, boolean insideLambda, PsiElement reportOn, ResolvedCall<?> resolvedCall) {
        ResolvedCall resolvedCall2;
        Call call = (Call) context.getTrace().get(BindingContext.CALL, parent.getCalleeExpression());
        if (call == null || (resolvedCall2 = (ResolvedCall) context.getTrace().get(BindingContext.RESOLVED_CALL, call)) == null) {
            return false;
        }
        CallableDescriptor resultingDescriptor = resolvedCall2.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        if (DescriptorUtilKt.isTopLevelInPackage(resultingDescriptor, "synchronized", IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME)) {
            List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall2.getValueArgumentsByIndex();
            ResolvedValueArgument resolvedValueArgument = valueArgumentsByIndex != null ? valueArgumentsByIndex.get(1) : null;
            ExpressionValueArgument expressionValueArgument = resolvedValueArgument instanceof ExpressionValueArgument ? (ExpressionValueArgument) resolvedValueArgument : null;
            boolean zAreEqual = Intrinsics.areEqual(expressionValueArgument != null ? expressionValueArgument.getValueArgument() : null, child);
            if (insideLambda && zAreEqual) {
                reportProblem(context, reportOn, resolvedCall);
            }
            return true;
        }
        CallableDescriptor resultingDescriptor2 = resolvedCall2.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor2, "getResultingDescriptor(...)");
        if (!DescriptorUtilKt.isTopLevelInPackage(resultingDescriptor2, "withLock", "kotlin.concurrent")) {
            return false;
        }
        reportProblem(context, reportOn, resolvedCall);
        return true;
    }

    private final void reportProblem(CallCheckerContext context, PsiElement reportOn, ResolvedCall<?> resolvedCall) {
        context.getTrace().report(ErrorsJvm.SUSPENSION_POINT_INSIDE_CRITICAL_SECTION.on(reportOn, resolvedCall.getResultingDescriptor()));
    }
}
