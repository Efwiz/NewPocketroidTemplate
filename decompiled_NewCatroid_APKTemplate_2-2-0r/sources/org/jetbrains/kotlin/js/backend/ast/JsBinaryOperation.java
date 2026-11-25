package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsBinaryOperation extends JsExpression {
    private JsExpression arg1;
    private JsExpression arg2;
    private final JsBinaryOperator op;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsBinaryOperation";
        } else {
            objArr[0] = "op";
        }
        if (i == 1) {
            objArr[1] = "getOperator";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsBinaryOperation";
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

    public JsBinaryOperation(JsBinaryOperator jsBinaryOperator, JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsBinaryOperator == null) {
            $$$reportNull$$$0(0);
        }
        this.op = jsBinaryOperator;
        this.arg1 = jsExpression;
        this.arg2 = jsExpression2;
    }

    public JsExpression getArg1() {
        return this.arg1;
    }

    public JsExpression getArg2() {
        return this.arg2;
    }

    public void setArg1(JsExpression jsExpression) {
        this.arg1 = jsExpression;
    }

    public void setArg2(JsExpression jsExpression) {
        this.arg2 = jsExpression;
    }

    public JsBinaryOperator getOperator() {
        JsBinaryOperator jsBinaryOperator = this.op;
        if (jsBinaryOperator == null) {
            $$$reportNull$$$0(1);
        }
        return jsBinaryOperator;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitBinaryExpression(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        if (this.op.isAssignment()) {
            jsVisitor.acceptLvalue(this.arg1);
        } else {
            jsVisitor.accept(this.arg1);
        }
        jsVisitor.accept(this.arg2);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            if (this.op.isAssignment()) {
                this.arg1 = jsVisitorWithContext.acceptLvalue(this.arg1);
            } else {
                this.arg1 = (JsExpression) jsVisitorWithContext.accept(this.arg1);
            }
            this.arg2 = (JsExpression) jsVisitorWithContext.accept(this.arg2);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsExpression deepCopy() {
        JsExpression jsExpression = (JsExpression) new JsBinaryOperation(this.op, (JsExpression) AstUtil.deepCopy(this.arg1), (JsExpression) AstUtil.deepCopy(this.arg2)).withMetadataFrom(this);
        if (jsExpression == null) {
            $$$reportNull$$$0(2);
        }
        return jsExpression;
    }
}
