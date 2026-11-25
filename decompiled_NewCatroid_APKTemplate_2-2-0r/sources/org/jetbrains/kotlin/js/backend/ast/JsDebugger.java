package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public class JsDebugger extends SourceInfoAwareJsNode implements JsStatement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsDebugger", "deepCopy"));
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsDebugger deepCopy() {
        return this;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitDebugger(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        jsVisitorWithContext.visit(this, jsContext);
        jsVisitorWithContext.endVisit(this, jsContext);
    }
}
