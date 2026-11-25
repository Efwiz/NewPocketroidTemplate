package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsBreak extends JsContinue {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsBreak", "deepCopy"));
    }

    public JsBreak() {
        super(null);
    }

    public JsBreak(JsNameRef jsNameRef) {
        super(jsNameRef);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsContinue, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitBreak(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsContinue, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext) && this.label != null) {
            this.label = (JsNameRef) jsVisitorWithContext.accept(this.label);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsContinue, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsBreak deepCopy() {
        JsBreak jsBreak = (JsBreak) new JsBreak((JsNameRef) AstUtil.deepCopy(this.label)).withMetadataFrom(this);
        if (jsBreak == null) {
            $$$reportNull$$$0(0);
        }
        return jsBreak;
    }
}
