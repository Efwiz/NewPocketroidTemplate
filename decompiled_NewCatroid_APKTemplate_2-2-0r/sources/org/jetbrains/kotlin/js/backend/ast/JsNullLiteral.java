package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public final class JsNullLiteral extends JsLiteral.JsValueLiteral {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsNullLiteral", "deepCopy"));
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitNull(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        jsVisitorWithContext.visit(this, jsContext);
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLiteral.JsValueLiteral, org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsNullLiteral deepCopy() {
        JsNullLiteral jsNullLiteral = (JsNullLiteral) new JsNullLiteral().withMetadataFrom(this);
        if (jsNullLiteral == null) {
            $$$reportNull$$$0(0);
        }
        return jsNullLiteral;
    }
}
