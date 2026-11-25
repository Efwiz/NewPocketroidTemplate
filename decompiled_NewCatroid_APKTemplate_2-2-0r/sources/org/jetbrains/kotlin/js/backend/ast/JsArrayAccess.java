package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsArrayAccess extends JsExpression {
    private JsExpression arrayExpression;
    private JsExpression indexExpression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsArrayAccess", "deepCopy"));
    }

    public JsArrayAccess() {
    }

    public JsArrayAccess(JsExpression jsExpression, JsExpression jsExpression2) {
        this.arrayExpression = jsExpression;
        this.indexExpression = jsExpression2;
    }

    public JsExpression getArrayExpression() {
        return this.arrayExpression;
    }

    public JsExpression getIndexExpression() {
        return this.indexExpression;
    }

    public void setArrayExpression(JsExpression jsExpression) {
        this.arrayExpression = jsExpression;
    }

    public void setIndexExpression(JsExpression jsExpression) {
        this.indexExpression = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitArrayAccess(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.arrayExpression);
        jsVisitor.accept(this.indexExpression);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.arrayExpression = (JsExpression) jsVisitorWithContext.accept(this.arrayExpression);
            this.indexExpression = (JsExpression) jsVisitorWithContext.accept(this.indexExpression);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsArrayAccess deepCopy() {
        JsArrayAccess jsArrayAccess = (JsArrayAccess) new JsArrayAccess((JsExpression) AstUtil.deepCopy(this.arrayExpression), (JsExpression) AstUtil.deepCopy(this.indexExpression)).withMetadataFrom(this);
        if (jsArrayAccess == null) {
            $$$reportNull$$$0(0);
        }
        return jsArrayAccess;
    }
}
