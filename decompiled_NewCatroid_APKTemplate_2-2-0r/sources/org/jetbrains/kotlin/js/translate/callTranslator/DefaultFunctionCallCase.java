package org.jetbrains.kotlin.js.translate.callTranslator;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TemporaryConstVariable;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: FunctionCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bJ.\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bJ\u0018\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J\f\u0010\u0013\u001a\u00020\u0004*\u00020\u0014H\u0014J\f\u0010\u0007\u001a\u00020\u0004*\u00020\u0014H\u0014J\f\u0010\u0015\u001a\u00020\u0004*\u00020\u0014H\u0014J\f\u0010\u0016\u001a\u00020\u0004*\u00020\u0014H\u0014¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/DefaultFunctionCallCase;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallCase;", "()V", "buildDefaultCallWithDispatchReceiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "argumentsInfo", "Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$ArgumentsInfo;", "dispatchReceiver", "functionName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "isNative", "", "hasSpreadOperator", "buildDefaultCallWithoutReceiver", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "callableDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "nativeSpreadFunWithDispatchOrExtensionReceiver", "bothReceivers", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "extensionReceiver", "noReceivers", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultFunctionCallCase extends FunctionCallCase {
    public static final DefaultFunctionCallCase INSTANCE = new DefaultFunctionCallCase();

    private DefaultFunctionCallCase() {
    }

    private final JsExpression nativeSpreadFunWithDispatchOrExtensionReceiver(CallArgumentTranslator.ArgumentsInfo argumentsInfo, JsName functionName) {
        TemporaryConstVariable cachedReceiver = argumentsInfo.getCachedReceiver();
        Intrinsics.checkNotNull(cachedReceiver);
        JsNameRef functionApplyRef = Namer.getFunctionApplyRef(new JsNameRef(functionName, cachedReceiver.assignmentExpression()));
        Intrinsics.checkNotNullExpressionValue(functionApplyRef, "getFunctionApplyRef(...)");
        return new JsInvocation(functionApplyRef, argumentsInfo.getTranslateArguments());
    }

    public final JsExpression buildDefaultCallWithDispatchReceiver(CallArgumentTranslator.ArgumentsInfo argumentsInfo, JsExpression dispatchReceiver, JsName functionName, boolean isNative, boolean hasSpreadOperator) {
        Intrinsics.checkNotNullParameter(argumentsInfo, "argumentsInfo");
        Intrinsics.checkNotNullParameter(dispatchReceiver, "dispatchReceiver");
        Intrinsics.checkNotNullParameter(functionName, "functionName");
        if (isNative && hasSpreadOperator) {
            return nativeSpreadFunWithDispatchOrExtensionReceiver(argumentsInfo, functionName);
        }
        return new JsInvocation(JsAstUtils.pureFqn(functionName, dispatchReceiver), argumentsInfo.getTranslateArguments());
    }

    public final JsExpression buildDefaultCallWithoutReceiver(TranslationContext context, CallArgumentTranslator.ArgumentsInfo argumentsInfo, CallableDescriptor callableDescriptor, boolean isNative, boolean hasSpreadOperator) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(argumentsInfo, "argumentsInfo");
        Intrinsics.checkNotNullParameter(callableDescriptor, "callableDescriptor");
        JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(callableDescriptor, context);
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
        if (isNative && hasSpreadOperator) {
            JsNameRef functionApplyRef = Namer.getFunctionApplyRef(jsExpressionTranslateAsValueReference);
            Intrinsics.checkNotNullExpressionValue(functionApplyRef, "getFunctionApplyRef(...)");
            return new JsInvocation(functionApplyRef, argumentsInfo.getTranslateArguments());
        }
        return new JsInvocation(jsExpressionTranslateAsValueReference, argumentsInfo.getTranslateArguments());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression noReceivers(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        FunctionCallInfo functionCallInfo2 = functionCallInfo;
        return buildDefaultCallWithoutReceiver(functionCallInfo.getContext(), functionCallInfo.getArgumentsInfo(), CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2), CallInfoExtensionsKt.isNative(functionCallInfo2), CallInfoExtensionsKt.hasSpreadOperator(functionCallInfo));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression dispatchReceiver(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        CallArgumentTranslator.ArgumentsInfo argumentsInfo = functionCallInfo.getArgumentsInfo();
        JsExpression dispatchReceiver = functionCallInfo.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        return buildDefaultCallWithDispatchReceiver(argumentsInfo, dispatchReceiver, CallInfoExtensionsKt.getFunctionName(functionCallInfo), CallInfoExtensionsKt.isNative(functionCallInfo), CallInfoExtensionsKt.hasSpreadOperator(functionCallInfo));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression extensionReceiver(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        FunctionCallInfo functionCallInfo2 = functionCallInfo;
        if (CallInfoExtensionsKt.isNative(functionCallInfo2) && CallInfoExtensionsKt.hasSpreadOperator(functionCallInfo)) {
            return nativeSpreadFunWithDispatchOrExtensionReceiver(functionCallInfo.getArgumentsInfo(), CallInfoExtensionsKt.getFunctionName(functionCallInfo));
        }
        if (CallInfoExtensionsKt.isNative(functionCallInfo2)) {
            return new JsInvocation(new JsNameRef(CallInfoExtensionsKt.getFunctionName(functionCallInfo), functionCallInfo.getExtensionReceiver()), functionCallInfo.getArgumentsInfo().getTranslateArguments());
        }
        JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2), functionCallInfo.getContext());
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
        CallArgumentTranslator.ArgumentsInfo argumentsInfo = functionCallInfo.getArgumentsInfo();
        JsExpression extensionReceiver = functionCallInfo.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        return new JsInvocation(jsExpressionTranslateAsValueReference, FunctionCallCasesKt.argsWithReceiver(argumentsInfo, extensionReceiver));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression bothReceivers(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        JsName functionName = CallInfoExtensionsKt.getFunctionName(functionCallInfo);
        JsExpression dispatchReceiver = functionCallInfo.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(functionName, dispatchReceiver);
        Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "pureFqn(...)");
        CallArgumentTranslator.ArgumentsInfo argumentsInfo = functionCallInfo.getArgumentsInfo();
        JsExpression extensionReceiver = functionCallInfo.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        return new JsInvocation(jsNameRefPureFqn, FunctionCallCasesKt.argsWithReceiver(argumentsInfo, extensionReceiver));
    }
}
