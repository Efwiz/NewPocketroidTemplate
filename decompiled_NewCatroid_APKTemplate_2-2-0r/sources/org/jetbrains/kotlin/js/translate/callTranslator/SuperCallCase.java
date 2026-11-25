package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;

/* JADX INFO: compiled from: FunctionCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\f\u0010\u0007\u001a\u00020\b*\u00020\u0006H\u0014¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/SuperCallCase;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallCase;", "()V", "canApply", "", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "dispatchReceiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuperCallCase extends FunctionCallCase {
    public static final SuperCallCase INSTANCE = new SuperCallCase();

    private SuperCallCase() {
    }

    public final boolean canApply(FunctionCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        return CallInfoExtensionsKt.isSuperInvocation(callInfo);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression dispatchReceiver(FunctionCallInfo functionCallInfo) {
        JsNameRef functionCallRef;
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        FunctionCallInfo functionCallInfo2 = functionCallInfo;
        JsExpression jsExpressionPrototypeOf = JsAstUtils.prototypeOf(CallInfoExtensionsKt.getCalleeOwner(functionCallInfo2));
        Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf, "prototypeOf(...)");
        List mutableList = CollectionsKt.toMutableList((Collection) functionCallInfo.getArgumentsInfo().getTranslateArguments());
        CallableDescriptor original = CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2).getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        if ((original instanceof FunctionDescriptor) && TranslationUtils.isOverridableFunctionWithDefaultParameters((FunctionDescriptor) original)) {
            int size = original.getValueParameters().size() - functionCallInfo.getArgumentsInfo().getValueArguments().size();
            for (int i = 0; i < size; i++) {
                mutableList.add(Namer.getUndefinedExpression());
            }
            JsName jsNameDeclareName = functionCallInfo.getContext().getScopeForDescriptor(original.getContainingDeclaration()).declareName(CallInfoExtensionsKt.getFunctionName(functionCallInfo).getIdent() + "$default");
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
            JsExpression dispatchReceiver = functionCallInfo.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver);
            JsInvocation jsInvocationInvokeBind = JsAstUtils.invokeBind(dispatchReceiver, new JsNameRef(jsNameDeclareName, jsExpressionPrototypeOf));
            Intrinsics.checkNotNullExpressionValue(jsInvocationInvokeBind, "invokeBind(...)");
            mutableList.add(jsInvocationInvokeBind);
            functionCallRef = JsAstUtils.pureFqn(CallInfoExtensionsKt.getFunctionName(functionCallInfo), functionCallInfo.getDispatchReceiver());
        } else {
            JsExpression dispatchReceiver2 = functionCallInfo.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver2);
            mutableList.add(0, dispatchReceiver2);
            functionCallRef = Namer.getFunctionCallRef(new JsNameRef(CallInfoExtensionsKt.getFunctionName(functionCallInfo), jsExpressionPrototypeOf));
        }
        Intrinsics.checkNotNull(functionCallRef);
        return new JsInvocation(functionCallRef, (List<? extends JsExpression>) mutableList);
    }
}
