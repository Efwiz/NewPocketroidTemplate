package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsPrefixOperation extends JsUnaryOperation {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsPrefixOperation", "deepCopy"));
    }

    public JsPrefixOperation(JsUnaryOperator jsUnaryOperator, JsExpression jsExpression) {
        super(jsUnaryOperator, jsExpression);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitPrefixOperation(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsUnaryOperation, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            super.traverse(jsVisitorWithContext, jsContext);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsPrefixOperation deepCopy() {
        JsPrefixOperation jsPrefixOperation = (JsPrefixOperation) new JsPrefixOperation(getOperator(), (JsExpression) AstUtil.deepCopy(getArg())).withMetadataFrom(this);
        if (jsPrefixOperation == null) {
            $$$reportNull$$$0(0);
        }
        return jsPrefixOperation;
    }
}
