package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsPostfixOperation extends JsUnaryOperation {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsPostfixOperation", "deepCopy"));
    }

    public JsPostfixOperation(JsUnaryOperator jsUnaryOperator, JsExpression jsExpression) {
        super(jsUnaryOperator, jsExpression);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitPostfixOperation(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsUnaryOperation, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            super.traverse(jsVisitorWithContext, jsContext);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsPostfixOperation deepCopy() {
        JsPostfixOperation jsPostfixOperation = (JsPostfixOperation) new JsPostfixOperation(getOperator(), (JsExpression) AstUtil.deepCopy(getArg())).withMetadataFrom(this);
        if (jsPostfixOperation == null) {
            $$$reportNull$$$0(0);
        }
        return jsPostfixOperation;
    }
}
