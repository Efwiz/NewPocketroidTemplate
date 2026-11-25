package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsReturn extends SourceInfoAwareJsNode implements JsStatement {
    private JsExpression expression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsReturn", "deepCopy"));
    }

    public JsReturn() {
    }

    public JsReturn(JsExpression jsExpression) {
        this.expression = jsExpression;
    }

    public JsExpression getExpression() {
        return this.expression;
    }

    public void setExpression(JsExpression jsExpression) {
        this.expression = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitReturn(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        JsExpression jsExpression = this.expression;
        if (jsExpression != null) {
            jsVisitor.accept(jsExpression);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        JsExpression jsExpression;
        if (jsVisitorWithContext.visit(this, jsContext) && (jsExpression = this.expression) != null) {
            this.expression = (JsExpression) jsVisitorWithContext.accept(jsExpression);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsReturn deepCopy() {
        JsReturn jsReturn = (JsReturn) new JsReturn((JsExpression) AstUtil.deepCopy(this.expression)).withMetadataFrom(this);
        if (jsReturn == null) {
            $$$reportNull$$$0(0);
        }
        return jsReturn;
    }
}
