package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsThrow extends SourceInfoAwareJsNode implements JsStatement {
    private JsExpression expression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsThrow", "deepCopy"));
    }

    public JsThrow() {
    }

    public JsThrow(JsExpression jsExpression) {
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
        jsVisitor.visitThrow(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.expression);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.expression = (JsExpression) jsVisitorWithContext.accept(this.expression);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsThrow deepCopy() {
        JsThrow jsThrow = (JsThrow) new JsThrow((JsExpression) AstUtil.deepCopy(this.expression)).withMetadataFrom(this);
        if (jsThrow == null) {
            $$$reportNull$$$0(0);
        }
        return jsThrow;
    }
}
