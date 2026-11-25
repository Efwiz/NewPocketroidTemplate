package org.jetbrains.kotlin.js.translate.utils.mutator;

import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: loaded from: classes5.dex */
public final class AssignToExpressionMutator implements Mutator {
    private final JsExpression toAssign;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/utils/mutator/AssignToExpressionMutator";
        } else {
            objArr[0] = "toAssign";
        }
        if (i == 2 || i == 3) {
            objArr[1] = "mutate";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/translate/utils/mutator/AssignToExpressionMutator";
        }
        if (i == 1) {
            objArr[2] = "mutate";
        } else if (i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public AssignToExpressionMutator(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(0);
        }
        this.toAssign = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.translate.utils.mutator.Mutator
    public JsNode mutate(JsNode jsNode) {
        if (jsNode == null) {
            $$$reportNull$$$0(1);
        }
        if (!(jsNode instanceof JsExpression)) {
            if (jsNode == null) {
                $$$reportNull$$$0(2);
            }
            return jsNode;
        }
        JsBinaryOperation jsBinaryOperationAssignment = JsAstUtils.assignment(this.toAssign, (JsExpression) jsNode);
        jsBinaryOperationAssignment.setSource(jsNode.getSource());
        if (jsBinaryOperationAssignment == null) {
            $$$reportNull$$$0(3);
        }
        return jsBinaryOperationAssignment;
    }
}
