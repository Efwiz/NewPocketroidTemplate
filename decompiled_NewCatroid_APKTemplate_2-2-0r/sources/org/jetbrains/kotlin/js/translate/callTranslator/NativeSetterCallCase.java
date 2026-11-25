package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.PredefinedAnnotation;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: FunctionCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/NativeSetterCallCase;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/AnnotatedAsNativeXCallCase;", "()V", "translateCall", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "receiver", "argumentsInfo", "Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$ArgumentsInfo;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeSetterCallCase extends AnnotatedAsNativeXCallCase {
    public static final NativeSetterCallCase INSTANCE = new NativeSetterCallCase();

    private NativeSetterCallCase() {
        super(PredefinedAnnotation.NATIVE_SETTER);
    }

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.AnnotatedAsNativeXCallCase
    public JsExpression translateCall(JsExpression receiver, CallArgumentTranslator.ArgumentsInfo argumentsInfo) {
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(argumentsInfo, "argumentsInfo");
        List<JsExpression> translateArguments = argumentsInfo.getTranslateArguments();
        JsBinaryOperation jsBinaryOperationAssignment = JsAstUtils.assignment(new JsArrayAccess(receiver, translateArguments.get(0)), translateArguments.get(1));
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAssignment, "assignment(...)");
        return jsBinaryOperationAssignment;
    }
}
