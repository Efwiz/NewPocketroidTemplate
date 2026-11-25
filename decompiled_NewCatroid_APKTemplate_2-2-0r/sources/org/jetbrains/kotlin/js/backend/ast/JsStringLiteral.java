package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public final class JsStringLiteral extends JsLiteral.JsValueLiteral {
    private final String value;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsStringLiteral", "deepCopy"));
    }

    public JsStringLiteral(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitString(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        jsVisitorWithContext.visit(this, jsContext);
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLiteral.JsValueLiteral, org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsStringLiteral deepCopy() {
        JsStringLiteral jsStringLiteral = (JsStringLiteral) new JsStringLiteral(this.value).withMetadataFrom(this);
        if (jsStringLiteral == null) {
            $$$reportNull$$$0(0);
        }
        return jsStringLiteral;
    }
}
