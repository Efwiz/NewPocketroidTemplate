package org.jetbrains.kotlin.js.translate.callTranslator;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.PredefinedAnnotation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;

/* JADX INFO: compiled from: FunctionCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH&J\f\u0010\u0010\u001a\u00020\f*\u00020\nH\u0004J\f\u0010\u0011\u001a\u00020\f*\u00020\nH\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/AnnotatedAsNativeXCallCase;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallCase;", "annotation", "Lorg/jetbrains/kotlin/js/PredefinedAnnotation;", "(Lorg/jetbrains/kotlin/js/PredefinedAnnotation;)V", "getAnnotation", "()Lorg/jetbrains/kotlin/js/PredefinedAnnotation;", "canApply", "", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "translateCall", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "receiver", "argumentsInfo", "Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$ArgumentsInfo;", "dispatchReceiver", "extensionReceiver", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AnnotatedAsNativeXCallCase extends FunctionCallCase {
    private final PredefinedAnnotation annotation;

    public abstract JsExpression translateCall(JsExpression receiver, CallArgumentTranslator.ArgumentsInfo argumentsInfo);

    public AnnotatedAsNativeXCallCase(PredefinedAnnotation annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        this.annotation = annotation;
    }

    public final PredefinedAnnotation getAnnotation() {
        return this.annotation;
    }

    public final boolean canApply(FunctionCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        return AnnotationsUtils.hasAnnotation(CallInfoExtensionsKt.getCallableDescriptor(callInfo), this.annotation);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public final JsExpression dispatchReceiver(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        JsExpression dispatchReceiver = functionCallInfo.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        return translateCall(dispatchReceiver, functionCallInfo.getArgumentsInfo());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public final JsExpression extensionReceiver(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        JsExpression extensionReceiver = functionCallInfo.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        return translateCall(extensionReceiver, functionCallInfo.getArgumentsInfo());
    }
}
