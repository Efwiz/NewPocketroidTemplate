package org.jetbrains.kotlin.js.translate.reference;

import org.jetbrains.kotlin.js.backend.ast.JsExpression;

/* JADX INFO: loaded from: classes5.dex */
public interface AccessTranslator {
    AccessTranslator getCached();

    JsExpression translateAsGet();

    JsExpression translateAsSet(JsExpression jsExpression);
}
