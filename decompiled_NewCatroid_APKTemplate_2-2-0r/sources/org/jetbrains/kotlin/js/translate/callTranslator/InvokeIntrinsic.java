package org.jetbrains.kotlin.js.translate.callTranslator;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionInvokeDescriptor;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FunctionCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\f\u0010\u0007\u001a\u00020\b*\u00020\u0006H\u0014J\f\u0010\t\u001a\u00020\b*\u00020\u0006H\u0014¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/InvokeIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallCase;", "()V", "canApply", "", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "bothReceivers", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "dispatchReceiver", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InvokeIntrinsic extends FunctionCallCase {
    public static final InvokeIntrinsic INSTANCE = new InvokeIntrinsic();

    private InvokeIntrinsic() {
    }

    public final boolean canApply(FunctionCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        CallableDescriptor callableDescriptor = CallInfoExtensionsKt.getCallableDescriptor(callInfo);
        if (callableDescriptor instanceof FunctionInvokeDescriptor) {
            return true;
        }
        if (!Intrinsics.areEqual(callableDescriptor.getShortName(), OperatorNameConventions.INVOKE) || callableDescriptor.getExtensionReceiverParameter() == null) {
            return false;
        }
        int size = callableDescriptor.getValueParameters().size() + 1;
        DeclarationDescriptor containingDeclaration = callableDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        return Intrinsics.areEqual(containingDeclaration, DescriptorUtilsKt.getBuiltIns(callableDescriptor).getFunction(size));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression dispatchReceiver(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        JsExpression dispatchReceiver = functionCallInfo.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        return new JsInvocation(dispatchReceiver, functionCallInfo.getArgumentsInfo().getTranslateArguments());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression bothReceivers(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        JsExpression dispatchReceiver = functionCallInfo.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        CallArgumentTranslator.ArgumentsInfo argumentsInfo = functionCallInfo.getArgumentsInfo();
        JsExpression extensionReceiver = functionCallInfo.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        return new JsInvocation(dispatchReceiver, FunctionCallCasesKt.argsWithReceiver(argumentsInfo, extensionReceiver));
    }
}
