package org.jetbrains.kotlin.js.backend.ast;

import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata;

/* JADX INFO: loaded from: classes5.dex */
public final class JsExpressionStatement extends AbstractNode implements JsStatement {
    private JsExpression expression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsExpressionStatement";
        } else {
            objArr[0] = "expression";
        }
        if (i == 1) {
            objArr[1] = "getExpression";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsExpressionStatement";
        } else {
            objArr[1] = "deepCopy";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public Object getSource() {
        return null;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ void copyMetadataFrom(HasMetadata hasMetadata) {
        super.copyMetadataFrom(hasMetadata);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public /* bridge */ /* synthetic */ List getCommentsAfterNode() {
        return super.getCommentsAfterNode();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public /* bridge */ /* synthetic */ List getCommentsBeforeNode() {
        return super.getCommentsBeforeNode();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ Object getData(String str) {
        return super.getData(str);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ Map getRawMetadata() {
        return super.getRawMetadata();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ boolean hasData(String str) {
        return super.hasData(str);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ void removeData(String str) {
        super.removeData(str);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public /* bridge */ /* synthetic */ void setCommentsAfterNode(List list) {
        super.setCommentsAfterNode(list);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public /* bridge */ /* synthetic */ void setCommentsBeforeNode(List list) {
        super.setCommentsBeforeNode(list);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ void setData(String str, Object obj) {
        super.setData(str, obj);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode
    public /* bridge */ /* synthetic */ HasMetadata withMetadataFrom(HasMetadata hasMetadata) {
        return super.withMetadataFrom(hasMetadata);
    }

    public JsExpressionStatement(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(0);
        }
        this.expression = jsExpression;
    }

    public JsExpression getExpression() {
        JsExpression jsExpression = this.expression;
        if (jsExpression == null) {
            $$$reportNull$$$0(1);
        }
        return jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitExpressionStatement(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.expression);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void setSource(Object obj) {
        throw new IllegalStateException("You must not set source info for JsExpressionStatement, set for expression");
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsNode source(Object obj) {
        throw new IllegalStateException("You must not set source info for JsExpressionStatement, set for expression");
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.expression = (JsExpression) jsVisitorWithContext.accept(this.expression);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsExpressionStatement deepCopy() {
        JsExpressionStatement jsExpressionStatement = (JsExpressionStatement) new JsExpressionStatement(this.expression.deepCopy()).withMetadataFrom(this);
        if (jsExpressionStatement == null) {
            $$$reportNull$$$0(2);
        }
        return jsExpressionStatement;
    }
}
