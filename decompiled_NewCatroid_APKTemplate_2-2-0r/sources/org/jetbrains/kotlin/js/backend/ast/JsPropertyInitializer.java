package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public class JsPropertyInitializer extends SourceInfoAwareJsNode {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private JsExpression labelExpr;
    private JsExpression valueExpr;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "valueExpr";
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsPropertyInitializer";
        } else {
            objArr[0] = "labelExpr";
        }
        if (i == 2) {
            objArr[1] = "getLabelExpr";
        } else if (i == 3) {
            objArr[1] = "getValueExpr";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsPropertyInitializer";
        } else {
            objArr[1] = "deepCopy";
        }
        if (i != 2 && i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public JsPropertyInitializer(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(1);
        }
        this.labelExpr = jsExpression;
        this.valueExpr = jsExpression2;
    }

    public JsExpression getLabelExpr() {
        JsExpression jsExpression = this.labelExpr;
        if (jsExpression == null) {
            $$$reportNull$$$0(2);
        }
        return jsExpression;
    }

    public JsExpression getValueExpr() {
        JsExpression jsExpression = this.valueExpr;
        if (jsExpression == null) {
            $$$reportNull$$$0(3);
        }
        return jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitPropertyInitializer(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.labelExpr);
        jsVisitor.accept(this.valueExpr);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            JsExpression jsExpression = (JsExpression) jsVisitorWithContext.accept(this.labelExpr);
            JsExpression jsExpression2 = (JsExpression) jsVisitorWithContext.accept(this.valueExpr);
            this.labelExpr = jsExpression;
            this.valueExpr = jsExpression2;
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsPropertyInitializer deepCopy() {
        JsPropertyInitializer jsPropertyInitializer = (JsPropertyInitializer) new JsPropertyInitializer(this.labelExpr.deepCopy(), this.valueExpr.deepCopy()).withMetadataFrom(this);
        if (jsPropertyInitializer == null) {
            $$$reportNull$$$0(4);
        }
        return jsPropertyInitializer;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.AbstractNode
    public String toString() {
        return this.labelExpr + ": " + this.valueExpr;
    }
}
