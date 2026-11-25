package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public final class JsDoubleLiteral extends JsNumberLiteral {
    public final double value;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsDoubleLiteral", "deepCopy"));
    }

    public JsDoubleLiteral(double d) {
        this.value = d;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitDouble(this);
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
    public JsDoubleLiteral deepCopy() {
        JsDoubleLiteral jsDoubleLiteral = (JsDoubleLiteral) new JsDoubleLiteral(this.value).withMetadataFrom(this);
        if (jsDoubleLiteral == null) {
            $$$reportNull$$$0(0);
        }
        return jsDoubleLiteral;
    }
}
