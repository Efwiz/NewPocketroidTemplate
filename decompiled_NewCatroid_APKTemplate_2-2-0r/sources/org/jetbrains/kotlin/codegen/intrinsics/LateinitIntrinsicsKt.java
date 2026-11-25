package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: LateinitIntrinsics.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u0004\u0018\u00010\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002¨\u0006\u0006"}, d2 = {"getStackValue", "Lorg/jetbrains/kotlin/codegen/StackValue;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LateinitIntrinsicsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final StackValue getStackValue(ResolvedCall<?> resolvedCall, ExpressionCodegen expressionCodegen) {
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        ExpressionReceiver expressionReceiver = extensionReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) extensionReceiver : null;
        KtExpression expression = expressionReceiver != null ? expressionReceiver.getExpression() : null;
        KtCallableReferenceExpression ktCallableReferenceExpression = expression instanceof KtCallableReferenceExpression ? (KtCallableReferenceExpression) expression : null;
        if (ktCallableReferenceExpression == null) {
            return null;
        }
        KtSimpleNameExpression callableReference = ktCallableReferenceExpression.getCallableReference();
        Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
        BindingContext bindingContext = expressionCodegen.getBindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(callableReference, bindingContext);
        StackValue stackValueGenerateCallableReferenceReceiver = expressionCodegen.generateCallableReferenceReceiver(resolvedCallWithAssert);
        if (stackValueGenerateCallableReferenceReceiver == null) {
            stackValueGenerateCallableReferenceReceiver = StackValue.none();
        }
        CallableDescriptor resultingDescriptor = resolvedCallWithAssert.getResultingDescriptor();
        Intrinsics.checkNotNull(resultingDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
        return expressionCodegen.intermediateValueForProperty((PropertyDescriptor) resultingDescriptor, true, false, null, false, stackValueGenerateCallableReferenceReceiver, null, true);
    }
}
