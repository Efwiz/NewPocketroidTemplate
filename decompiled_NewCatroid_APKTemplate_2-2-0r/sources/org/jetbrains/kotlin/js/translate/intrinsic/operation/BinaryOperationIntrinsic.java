package org.jetbrains.kotlin.js.translate.intrinsic.operation;

import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.psi.KtBinaryExpression;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface BinaryOperationIntrinsic {
    JsExpression invoke(KtBinaryExpression ktBinaryExpression, JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext);
}
