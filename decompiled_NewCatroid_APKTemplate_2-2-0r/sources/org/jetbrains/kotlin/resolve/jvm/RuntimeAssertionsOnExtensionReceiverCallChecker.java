package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: RuntimeAssertions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J$\u0010\u000b\u001a\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionsOnExtensionReceiverCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkReceiver", "receiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "receiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RuntimeAssertionsOnExtensionReceiverCallChecker implements CallChecker {
    public static final RuntimeAssertionsOnExtensionReceiverCallChecker INSTANCE = new RuntimeAssertionsOnExtensionReceiverCallChecker();

    private RuntimeAssertionsOnExtensionReceiverCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        if (CallUtilKt.isSafeCall(call)) {
            return;
        }
        checkReceiver(resolvedCall.getResultingDescriptor().getExtensionReceiverParameter(), resolvedCall.getExtensionReceiver(), context);
    }

    private final void checkReceiver(ReceiverParameterDescriptor receiverParameter, ReceiverValue receiverValue, CallCheckerContext context) {
        if (receiverParameter == null || receiverValue == null) {
            return;
        }
        ExpressionReceiver expressionReceiver = receiverValue instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValue : null;
        if (expressionReceiver == null) {
            return;
        }
        KtExpression expression = expressionReceiver.getExpression();
        ResolutionContext<?> resolutionContext = context.getResolutionContext();
        RuntimeAssertionInfo.Companion companion = RuntimeAssertionInfo.INSTANCE;
        KotlinType type = receiverParameter.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        ExpressionReceiver expressionReceiver2 = (ExpressionReceiver) receiverValue;
        KotlinType type2 = expressionReceiver2.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        KotlinType type3 = expressionReceiver2.getType();
        Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
        RuntimeAssertionInfo runtimeAssertionInfoCreate = companion.create(type, type2, new RuntimeAssertionsDataFlowExtras(resolutionContext, type3, expression));
        if (runtimeAssertionInfoCreate != null) {
            resolutionContext.trace.record(JvmBindingContextSlices.RECEIVER_RUNTIME_ASSERTION_INFO, expressionReceiver, runtimeAssertionInfoCreate);
        }
    }
}
