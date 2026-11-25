package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsContinue extends SourceInfoAwareJsNode implements JsStatement {
    protected JsNameRef label;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsContinue", "deepCopy"));
    }

    public JsContinue() {
        this(null);
    }

    public JsContinue(JsNameRef jsNameRef) {
        this.label = jsNameRef;
    }

    public JsNameRef getLabel() {
        return this.label;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitContinue(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        JsNameRef jsNameRef = this.label;
        if (jsNameRef != null) {
            jsVisitor.accept(jsNameRef);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        JsNameRef jsNameRef;
        if (jsVisitorWithContext.visit(this, jsContext) && (jsNameRef = this.label) != null) {
            this.label = (JsNameRef) jsVisitorWithContext.accept(jsNameRef);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsContinue deepCopy() {
        JsNameRef jsNameRef = this.label;
        if (jsNameRef == null) {
            return new JsContinue();
        }
        JsContinue jsContinue = (JsContinue) new JsContinue((JsNameRef) AstUtil.deepCopy(jsNameRef)).withMetadataFrom(this);
        if (jsContinue == null) {
            $$$reportNull$$$0(0);
        }
        return jsContinue;
    }
}
