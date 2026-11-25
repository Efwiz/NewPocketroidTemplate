package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsConditional extends JsExpression {
    private JsExpression elseExpression;
    private JsExpression testExpression;
    private JsExpression thenExpression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsConditional", "deepCopy"));
    }

    public JsConditional() {
    }

    public JsConditional(JsExpression jsExpression, JsExpression jsExpression2, JsExpression jsExpression3) {
        this.testExpression = jsExpression;
        this.thenExpression = jsExpression2;
        this.elseExpression = jsExpression3;
    }

    public JsExpression getElseExpression() {
        return this.elseExpression;
    }

    public JsExpression getTestExpression() {
        return this.testExpression;
    }

    public JsExpression getThenExpression() {
        return this.thenExpression;
    }

    public void setElseExpression(JsExpression jsExpression) {
        this.elseExpression = jsExpression;
    }

    public void setTestExpression(JsExpression jsExpression) {
        this.testExpression = jsExpression;
    }

    public void setThenExpression(JsExpression jsExpression) {
        this.thenExpression = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitConditional(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.testExpression);
        jsVisitor.accept(this.thenExpression);
        jsVisitor.accept(this.elseExpression);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.testExpression = (JsExpression) jsVisitorWithContext.accept(this.testExpression);
            this.thenExpression = (JsExpression) jsVisitorWithContext.accept(this.thenExpression);
            this.elseExpression = (JsExpression) jsVisitorWithContext.accept(this.elseExpression);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsConditional deepCopy() {
        JsConditional jsConditional = (JsConditional) new JsConditional((JsExpression) AstUtil.deepCopy(this.testExpression), (JsExpression) AstUtil.deepCopy(this.thenExpression), (JsExpression) AstUtil.deepCopy(this.elseExpression)).withMetadataFrom(this);
        if (jsConditional == null) {
            $$$reportNull$$$0(0);
        }
        return jsConditional;
    }
}
