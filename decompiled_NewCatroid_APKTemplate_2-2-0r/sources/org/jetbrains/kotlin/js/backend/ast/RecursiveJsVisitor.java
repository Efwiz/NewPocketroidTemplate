package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public abstract class RecursiveJsVisitor extends JsVisitor {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "node", "org/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor", "visitElement"));
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    protected void visitElement(JsNode jsNode) {
        if (jsNode == null) {
            $$$reportNull$$$0(0);
        }
        jsNode.acceptChildren(this);
    }
}
