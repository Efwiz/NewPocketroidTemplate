package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;

/* JADX INFO: compiled from: FunctionCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005*\u00020\u0002H\u0016J\f\u0010\u0007\u001a\u00020\b*\u00020\u0002H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/DelegateFunctionIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/DelegateIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "()V", "getArgs", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegateFunctionIntrinsic implements DelegateIntrinsic<FunctionCallInfo> {
    public static final DelegateFunctionIntrinsic INSTANCE = new DelegateFunctionIntrinsic();

    private DelegateFunctionIntrinsic() {
    }

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.DelegateIntrinsic
    public List<JsExpression> getArgs(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        return functionCallInfo.getArgumentsInfo().getTranslateArguments();
    }

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.DelegateIntrinsic
    public CallableDescriptor getDescriptor(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        return CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo);
    }
}
