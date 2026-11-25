package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public abstract class JsUnaryOperation extends JsExpression {
    private JsExpression arg;
    private final JsUnaryOperator op;

    public JsUnaryOperation(JsUnaryOperator jsUnaryOperator, JsExpression jsExpression) {
        this.op = jsUnaryOperator;
        this.arg = jsExpression;
    }

    public JsExpression getArg() {
        return this.arg;
    }

    public JsUnaryOperator getOperator() {
        return this.op;
    }

    public void setArg(JsExpression jsExpression) {
        this.arg = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        if (this.op.isModifying()) {
            jsVisitor.acceptLvalue(this.arg);
        } else {
            jsVisitor.accept(this.arg);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (this.op.isModifying()) {
            this.arg = jsVisitorWithContext.acceptLvalue(this.arg);
        } else {
            this.arg = (JsExpression) jsVisitorWithContext.accept(this.arg);
        }
    }
}
