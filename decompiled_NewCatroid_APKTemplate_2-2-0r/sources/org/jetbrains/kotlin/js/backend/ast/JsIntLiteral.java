package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public final class JsIntLiteral extends JsNumberLiteral {
    public final int value;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsIntLiteral", "deepCopy"));
    }

    public JsIntLiteral(int i) {
        this.value = i;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitInt(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.AbstractNode
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        jsVisitorWithContext.visit(this, jsContext);
        jsVisitorWithContext.endVisit((JsNumberLiteral) this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLiteral.JsValueLiteral, org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsExpression deepCopy() {
        JsExpression jsExpression = (JsExpression) new JsIntLiteral(this.value).withMetadataFrom(this);
        if (jsExpression == null) {
            $$$reportNull$$$0(0);
        }
        return jsExpression;
    }
}
