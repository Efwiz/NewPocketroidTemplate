package org.jetbrains.kotlin.js.translate.context;

import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;

/* JADX INFO: loaded from: classes5.dex */
public final class TemporaryConstVariable extends TemporaryVariable {
    private boolean initialized;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "assignmentExpression";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/context/TemporaryConstVariable";
        } else {
            objArr[0] = "variableName";
        }
        if (i == 2 || i == 3) {
            objArr[1] = "value";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/translate/context/TemporaryConstVariable";
        }
        if (i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemporaryConstVariable(JsName jsName, JsExpression jsExpression) {
        super(jsName, jsExpression, null);
        if (jsName == null) {
            $$$reportNull$$$0(0);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(1);
        }
        this.initialized = false;
    }

    public JsExpression value() {
        if (this.initialized) {
            JsNameRef jsNameRefReference = reference();
            if (jsNameRefReference == null) {
                $$$reportNull$$$0(2);
            }
            return jsNameRefReference;
        }
        this.initialized = true;
        JsExpression jsExpressionAssignmentExpression = assignmentExpression();
        if (jsExpressionAssignmentExpression == null) {
            $$$reportNull$$$0(3);
        }
        return jsExpressionAssignmentExpression;
    }
}
