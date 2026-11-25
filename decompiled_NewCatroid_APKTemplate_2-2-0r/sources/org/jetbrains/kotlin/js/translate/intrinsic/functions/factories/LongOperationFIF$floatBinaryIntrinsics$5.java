package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: LongOperationFIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class LongOperationFIF$floatBinaryIntrinsics$5 extends FunctionReference implements Function2<JsExpression, JsExpression, JsBinaryOperation> {
    public static final LongOperationFIF$floatBinaryIntrinsics$5 INSTANCE = new LongOperationFIF$floatBinaryIntrinsics$5();

    LongOperationFIF$floatBinaryIntrinsics$5() {
        super(2);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "div(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperation;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "div";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(JsAstUtils.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public final JsBinaryOperation invoke(JsExpression p0, JsExpression p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return JsAstUtils.div(p0, p1);
    }
}
