package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public final class JsBooleanLiteral extends JsLiteral.JsValueLiteral {
    private final boolean value;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i != 2) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsBooleanLiteral";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsBooleanLiteral";
        } else {
            objArr[1] = "deepCopy";
        }
        if (i == 1) {
            objArr[2] = "isFalse";
        } else if (i != 2) {
            objArr[2] = "isTrue";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public JsBooleanLiteral(boolean z) {
        this.value = z;
    }

    public static boolean isTrue(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(0);
        }
        return (jsExpression instanceof JsBooleanLiteral) && ((JsBooleanLiteral) jsExpression).getValue();
    }

    public static boolean isFalse(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(1);
        }
        return (jsExpression instanceof JsBooleanLiteral) && !((JsBooleanLiteral) jsExpression).getValue();
    }

    public boolean getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitBoolean(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        jsVisitorWithContext.visit(this, jsContext);
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLiteral.JsValueLiteral, org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsBooleanLiteral deepCopy() {
        JsBooleanLiteral jsBooleanLiteral = (JsBooleanLiteral) new JsBooleanLiteral(this.value).withMetadataFrom(this);
        if (jsBooleanLiteral == null) {
            $$$reportNull$$$0(2);
        }
        return jsBooleanLiteral;
    }
}
