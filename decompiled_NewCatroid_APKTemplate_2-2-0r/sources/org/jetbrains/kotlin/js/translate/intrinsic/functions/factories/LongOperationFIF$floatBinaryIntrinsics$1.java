package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: LongOperationFIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class LongOperationFIF$floatBinaryIntrinsics$1 extends FunctionReference implements Function2<JsExpression, JsExpression, JsExpression> {
    public static final LongOperationFIF$floatBinaryIntrinsics$1 INSTANCE = new LongOperationFIF$floatBinaryIntrinsics$1();

    LongOperationFIF$floatBinaryIntrinsics$1() {
        super(2);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "compareTo(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "compareTo";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(JsAstUtils.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public final JsExpression invoke(JsExpression p0, JsExpression p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return JsAstUtils.compareTo(p0, p1);
    }
}
