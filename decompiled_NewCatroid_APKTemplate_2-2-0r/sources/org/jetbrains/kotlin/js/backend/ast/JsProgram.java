package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public final class JsProgram extends SourceInfoAwareJsNode {
    private final JsCompositeBlock globalBlock = new JsCompositeBlock();
    private final JsRootScope rootScope;
    private final JsObjectScope topScope;

    public JsProgram() {
        JsRootScope jsRootScope = new JsRootScope(this);
        this.rootScope = jsRootScope;
        this.topScope = new JsObjectScope(jsRootScope, "Global");
    }

    public JsCompositeBlock getGlobalBlock() {
        return this.globalBlock;
    }

    public JsRootScope getRootScope() {
        return this.rootScope;
    }

    public JsObjectScope getScope() {
        return this.topScope;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitProgram(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.globalBlock);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            jsVisitorWithContext.accept(this.globalBlock);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsProgram deepCopy() {
        throw new UnsupportedOperationException();
    }
}
