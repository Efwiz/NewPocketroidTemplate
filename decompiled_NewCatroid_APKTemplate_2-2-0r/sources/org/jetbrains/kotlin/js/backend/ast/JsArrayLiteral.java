package org.jetbrains.kotlin.js.backend.ast;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsArrayLiteral extends JsLiteral {
    private final List<JsExpression> expressions;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsArrayLiteral", "deepCopy"));
    }

    public JsArrayLiteral() {
        this.expressions = new SmartList();
    }

    public JsArrayLiteral(List<JsExpression> list) {
        this.expressions = list;
    }

    public List<JsExpression> getExpressions() {
        return this.expressions;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitArray(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.acceptWithInsertRemove(this.expressions);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            jsVisitorWithContext.acceptList(this.expressions);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsArrayLiteral deepCopy() {
        JsArrayLiteral jsArrayLiteral = (JsArrayLiteral) new JsArrayLiteral(AstUtil.deepCopy(this.expressions)).withMetadataFrom(this);
        if (jsArrayLiteral == null) {
            $$$reportNull$$$0(0);
        }
        return jsArrayLiteral;
    }
}
