package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.common.Symbol;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsLabel extends SourceInfoAwareJsNode implements HasName, JsStatement {
    private JsName label;
    private JsStatement statement;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsLabel", "deepCopy"));
    }

    public JsLabel(JsName jsName) {
        this.label = jsName;
    }

    public JsLabel(JsName jsName, JsStatement jsStatement) {
        this.label = jsName;
        this.statement = jsStatement;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasName
    public JsName getName() {
        return this.label;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasName
    public void setName(JsName jsName) {
        this.label = jsName;
    }

    @Override // org.jetbrains.kotlin.js.common.HasSymbol
    public Symbol getSymbol() {
        return this.label;
    }

    public JsStatement getStatement() {
        return this.statement;
    }

    public void setStatement(JsStatement jsStatement) {
        this.statement = jsStatement;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitLabel(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.statement);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.statement = jsVisitorWithContext.acceptStatement(this.statement);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsLabel deepCopy() {
        JsLabel jsLabel = (JsLabel) new JsLabel(this.label, (JsStatement) AstUtil.deepCopy(this.statement)).withMetadataFrom(this);
        if (jsLabel == null) {
            $$$reportNull$$$0(0);
        }
        return jsLabel;
    }
}
