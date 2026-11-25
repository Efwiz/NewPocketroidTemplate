package org.jetbrains.kotlin.js.inline.clean;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsClass;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsSuperRef;

/* JADX INFO: compiled from: TrivialConstructorRemoval.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\f\u0010\u0007\u001a\u00020\u0006*\u00020\bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/TrivialConstructorRemoval;", "", "klass", "Lorg/jetbrains/kotlin/js/backend/ast/JsClass;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsClass;)V", "apply", "", "isTrivial", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TrivialConstructorRemoval {
    private final JsClass klass;

    public TrivialConstructorRemoval(JsClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        this.klass = klass;
    }

    public final boolean apply() {
        JsFunction constructor = this.klass.getConstructor();
        if (constructor == null || !isTrivial(constructor)) {
            return false;
        }
        this.klass.setConstructor(null);
        return true;
    }

    private final boolean isTrivial(JsFunction jsFunction) {
        List<JsStatement> statements = jsFunction.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        List<JsStatement> list = statements;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        for (JsStatement jsStatement : list) {
            JsExpressionStatement jsExpressionStatement = jsStatement instanceof JsExpressionStatement ? (JsExpressionStatement) jsStatement : null;
            JsExpression expression = jsExpressionStatement != null ? jsExpressionStatement.getExpression() : null;
            JsInvocation jsInvocation = expression instanceof JsInvocation ? (JsInvocation) expression : null;
            if (((jsInvocation != null ? jsInvocation.getQualifier() : null) instanceof JsSuperRef) && jsInvocation.getArguments().size() == jsFunction.getParameters().size()) {
                List<JsExpression> arguments = jsInvocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(arguments);
                if (!(iterableWithIndex instanceof Collection) || !((Collection) iterableWithIndex).isEmpty()) {
                    for (IndexedValue indexedValue : iterableWithIndex) {
                        int index = indexedValue.getIndex();
                        JsExpression jsExpression = (JsExpression) indexedValue.component2();
                        if (!(jsExpression instanceof JsNameRef) || ((JsNameRef) jsExpression).getName() != jsFunction.getParameters().get(index).getName()) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }
}
