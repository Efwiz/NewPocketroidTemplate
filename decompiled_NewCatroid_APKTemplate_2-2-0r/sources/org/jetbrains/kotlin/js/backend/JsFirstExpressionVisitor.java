package org.jetbrains.kotlin.js.backend;

import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsClass;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;

/* JADX INFO: loaded from: classes5.dex */
public class JsFirstExpressionVisitor extends RecursiveJsVisitor {
    private boolean needsParentheses = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 5) {
            objArr[0] = "invocation";
        } else if (i != 6) {
            objArr[0] = "x";
        } else {
            objArr[0] = "nameRef";
        }
        objArr[1] = "org/jetbrains/kotlin/js/backend/JsFirstExpressionVisitor";
        switch (i) {
            case 1:
                objArr[2] = "visitArray";
                break;
            case 2:
                objArr[2] = "visitBinaryExpression";
                break;
            case 3:
                objArr[2] = "visitConditional";
                break;
            case 4:
                objArr[2] = "visitFunction";
                break;
            case 5:
                objArr[2] = "visitInvocation";
                break;
            case 6:
                objArr[2] = "visitNameRef";
                break;
            case 7:
                objArr[2] = "visitNew";
                break;
            case 8:
                objArr[2] = "visitObjectLiteral";
                break;
            case 9:
                objArr[2] = "visitPostfixOperation";
                break;
            case 10:
                objArr[2] = "visitPrefixOperation";
                break;
            default:
                objArr[2] = "visitArrayAccess";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitArray(JsArrayLiteral jsArrayLiteral) {
        if (jsArrayLiteral == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNew(JsNew jsNew) {
        if (jsNew == null) {
            $$$reportNull$$$0(7);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitPrefixOperation(JsPrefixOperation jsPrefixOperation) {
        if (jsPrefixOperation == null) {
            $$$reportNull$$$0(10);
        }
    }

    public static boolean exec(JsExpressionStatement jsExpressionStatement) {
        JsExpression expression = jsExpressionStatement.getExpression();
        if ((expression instanceof JsFunction) || (expression instanceof JsClass)) {
            return false;
        }
        JsFirstExpressionVisitor jsFirstExpressionVisitor = new JsFirstExpressionVisitor();
        jsFirstExpressionVisitor.accept(jsExpressionStatement.getExpression());
        return jsFirstExpressionVisitor.needsParentheses;
    }

    private JsFirstExpressionVisitor() {
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitArrayAccess(JsArrayAccess jsArrayAccess) {
        if (jsArrayAccess == null) {
            $$$reportNull$$$0(0);
        }
        accept(jsArrayAccess.getArrayExpression());
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitBinaryExpression(JsBinaryOperation jsBinaryOperation) {
        if (jsBinaryOperation == null) {
            $$$reportNull$$$0(2);
        }
        accept(jsBinaryOperation.getArg1());
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitConditional(JsConditional jsConditional) {
        if (jsConditional == null) {
            $$$reportNull$$$0(3);
        }
        accept(jsConditional.getTestExpression());
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitFunction(JsFunction jsFunction) {
        if (jsFunction == null) {
            $$$reportNull$$$0(4);
        }
        this.needsParentheses = true;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitInvocation(JsInvocation jsInvocation) {
        if (jsInvocation == null) {
            $$$reportNull$$$0(5);
        }
        accept(jsInvocation.getQualifier());
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNameRef(JsNameRef jsNameRef) {
        if (jsNameRef == null) {
            $$$reportNull$$$0(6);
        }
        if (jsNameRef.isLeaf()) {
            return;
        }
        accept(jsNameRef.getQualifier());
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitObjectLiteral(JsObjectLiteral jsObjectLiteral) {
        if (jsObjectLiteral == null) {
            $$$reportNull$$$0(8);
        }
        this.needsParentheses = true;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitPostfixOperation(JsPostfixOperation jsPostfixOperation) {
        if (jsPostfixOperation == null) {
            $$$reportNull$$$0(9);
        }
        accept(jsPostfixOperation.getArg());
    }
}
