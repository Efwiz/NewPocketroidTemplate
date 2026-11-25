package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsWhile extends SourceInfoAwareJsNode implements JsLoop {
    protected JsStatement body;
    protected JsExpression condition;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsWhile", "deepCopy"));
    }

    public JsWhile() {
    }

    public JsWhile(JsExpression jsExpression, JsStatement jsStatement) {
        this.condition = jsExpression;
        this.body = jsStatement;
    }

    public JsStatement getBody() {
        return this.body;
    }

    public JsExpression getCondition() {
        return this.condition;
    }

    public void setBody(JsStatement jsStatement) {
        this.body = jsStatement;
    }

    public void setCondition(JsExpression jsExpression) {
        this.condition = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitWhile(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.condition);
        jsVisitor.accept(this.body);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.condition = (JsExpression) jsVisitorWithContext.accept(this.condition);
            this.body = jsVisitorWithContext.acceptStatement(this.body);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsWhile deepCopy() {
        JsWhile jsWhile = (JsWhile) new JsWhile((JsExpression) AstUtil.deepCopy(this.condition), (JsStatement) AstUtil.deepCopy(this.body)).withMetadataFrom(this);
        if (jsWhile == null) {
            $$$reportNull$$$0(0);
        }
        return jsWhile;
    }
}
