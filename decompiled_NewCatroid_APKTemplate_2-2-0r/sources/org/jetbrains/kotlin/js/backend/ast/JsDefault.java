package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsDefault extends JsSwitchMember {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsDefault", "deepCopy"));
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitDefault(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            jsVisitorWithContext.acceptStatementList(this.statements);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsSwitchMember, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsDefault deepCopy() {
        JsDefault jsDefault = new JsDefault();
        jsDefault.statements.addAll(AstUtil.deepCopy(this.statements));
        JsDefault jsDefault2 = (JsDefault) jsDefault.withMetadataFrom(this);
        if (jsDefault2 == null) {
            $$$reportNull$$$0(0);
        }
        return jsDefault2;
    }
}
