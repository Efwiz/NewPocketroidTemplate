package org.jetbrains.kotlin.js.inline.util;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;

/* JADX INFO: compiled from: functionUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\f\u0010\u0004\u001a\u0004\u0018\u00010\u0003*\u00020\u0003¨\u0006\u0005"}, d2 = {"isFunctionCreator", "", "outer", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "getInnerFunction", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionUtilsKt {
    public static final boolean isFunctionCreator(JsFunction outer) {
        Intrinsics.checkNotNullParameter(outer, "outer");
        return getInnerFunction(outer) != null;
    }

    public static final JsFunction getInnerFunction(JsFunction jsFunction) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        List<JsStatement> statements = jsFunction.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        if (statements.size() != 1) {
            return null;
        }
        JsStatement jsStatement = statements.get(0);
        JsReturn jsReturn = jsStatement instanceof JsReturn ? (JsReturn) jsStatement : null;
        JsExpression expression = jsReturn != null ? jsReturn.getExpression() : null;
        if (expression instanceof JsFunction) {
            return (JsFunction) expression;
        }
        return null;
    }
}
