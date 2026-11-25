package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsDoWhile extends JsWhile {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsDoWhile", "deepCopy"));
    }

    public JsDoWhile() {
    }

    public JsDoWhile(JsExpression jsExpression, JsStatement jsStatement) {
        super(jsExpression, jsStatement);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsWhile, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitDoWhile(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsWhile, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.body = jsVisitorWithContext.acceptStatement(this.body);
            this.condition = (JsExpression) jsVisitorWithContext.accept(this.condition);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsWhile, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsDoWhile deepCopy() {
        JsDoWhile jsDoWhile = (JsDoWhile) new JsDoWhile((JsExpression) AstUtil.deepCopy(this.condition), (JsStatement) AstUtil.deepCopy(this.body)).withMetadataFrom(this);
        if (jsDoWhile == null) {
            $$$reportNull$$$0(0);
        }
        return jsDoWhile;
    }
}
