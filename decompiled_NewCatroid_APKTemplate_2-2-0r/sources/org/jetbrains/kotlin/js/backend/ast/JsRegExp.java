package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public final class JsRegExp extends JsLiteral.JsValueLiteral {
    private String flags;
    private String pattern;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsRegExp", "deepCopy"));
    }

    public String getFlags() {
        return this.flags;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setFlags(String str) {
        this.flags = str;
    }

    public void setPattern(String str) {
        this.pattern = str;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitRegExp(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        jsVisitorWithContext.visit(this, jsContext);
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLiteral.JsValueLiteral, org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsRegExp deepCopy() {
        JsRegExp jsRegExp = (JsRegExp) new JsRegExp().withMetadataFrom(this);
        jsRegExp.setFlags(this.flags);
        jsRegExp.setPattern(this.pattern);
        if (jsRegExp == null) {
            $$$reportNull$$$0(0);
        }
        return jsRegExp;
    }
}
