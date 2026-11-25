package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsCase extends JsSwitchMember {
    private JsExpression caseExpression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsCase", "deepCopy"));
    }

    public JsExpression getCaseExpression() {
        return this.caseExpression;
    }

    public void setCaseExpression(JsExpression jsExpression) {
        this.caseExpression = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitCase(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsSwitchMember, org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.caseExpression);
        super.acceptChildren(jsVisitor);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.caseExpression = (JsExpression) jsVisitorWithContext.accept(this.caseExpression);
            jsVisitorWithContext.acceptStatementList(this.statements);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsSwitchMember, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsCase deepCopy() {
        JsCase jsCase = new JsCase();
        jsCase.caseExpression = (JsExpression) AstUtil.deepCopy(this.caseExpression);
        jsCase.statements.addAll(AstUtil.deepCopy(this.statements));
        JsCase jsCase2 = (JsCase) jsCase.withMetadataFrom(this);
        if (jsCase2 == null) {
            $$$reportNull$$$0(0);
        }
        return jsCase2;
    }
}
