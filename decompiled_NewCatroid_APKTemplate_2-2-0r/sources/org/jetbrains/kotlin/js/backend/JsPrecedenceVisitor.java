package org.jetbrains.kotlin.js.backend;

import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsDoubleLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsRegExp;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSuperRef;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsVisitor;

/* JADX INFO: loaded from: classes5.dex */
class JsPrecedenceVisitor extends JsVisitor {
    private int answer = -1;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 6) {
            objArr[0] = "invocation";
        } else if (i == 7) {
            objArr[0] = "nameRef";
        } else if (i != 20) {
            objArr[0] = "x";
        } else {
            objArr[0] = "node";
        }
        objArr[1] = "org/jetbrains/kotlin/js/backend/JsPrecedenceVisitor";
        switch (i) {
            case 1:
                objArr[2] = "visitArray";
                break;
            case 2:
                objArr[2] = "visitBinaryExpression";
                break;
            case 3:
                objArr[2] = "visitBoolean";
                break;
            case 4:
                objArr[2] = "visitConditional";
                break;
            case 5:
                objArr[2] = "visitFunction";
                break;
            case 6:
                objArr[2] = "visitInvocation";
                break;
            case 7:
                objArr[2] = "visitNameRef";
                break;
            case 8:
                objArr[2] = "visitNew";
                break;
            case 9:
                objArr[2] = "visitNull";
                break;
            case 10:
                objArr[2] = "visitInt";
                break;
            case 11:
                objArr[2] = "visitDouble";
                break;
            case 12:
                objArr[2] = "visitObjectLiteral";
                break;
            case 13:
                objArr[2] = "visitPostfixOperation";
                break;
            case 14:
                objArr[2] = "visitPrefixOperation";
                break;
            case 15:
                objArr[2] = "visitPropertyInitializer";
                break;
            case 16:
                objArr[2] = "visitRegExp";
                break;
            case 17:
                objArr[2] = "visitString";
                break;
            case 18:
                objArr[2] = "visitThis";
                break;
            case 19:
                objArr[2] = "visitSuper";
                break;
            case 20:
                objArr[2] = "visitElement";
                break;
            default:
                objArr[2] = "visitArrayAccess";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private JsPrecedenceVisitor() {
    }

    public static int exec(JsExpression jsExpression) {
        JsPrecedenceVisitor jsPrecedenceVisitor = new JsPrecedenceVisitor();
        jsPrecedenceVisitor.accept(jsExpression);
        int i = jsPrecedenceVisitor.answer;
        if (i >= 0) {
            return i;
        }
        throw new RuntimeException("Precedence must be >= 0!");
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitArrayAccess(JsArrayAccess jsArrayAccess) {
        if (jsArrayAccess == null) {
            $$$reportNull$$$0(0);
        }
        this.answer = 16;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitArray(JsArrayLiteral jsArrayLiteral) {
        if (jsArrayLiteral == null) {
            $$$reportNull$$$0(1);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitBinaryExpression(JsBinaryOperation jsBinaryOperation) {
        if (jsBinaryOperation == null) {
            $$$reportNull$$$0(2);
        }
        this.answer = jsBinaryOperation.getOperator().getPrecedence();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitBoolean(JsBooleanLiteral jsBooleanLiteral) {
        if (jsBooleanLiteral == null) {
            $$$reportNull$$$0(3);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitConditional(JsConditional jsConditional) {
        if (jsConditional == null) {
            $$$reportNull$$$0(4);
        }
        this.answer = 3;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitFunction(JsFunction jsFunction) {
        if (jsFunction == null) {
            $$$reportNull$$$0(5);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitInvocation(JsInvocation jsInvocation) {
        if (jsInvocation == null) {
            $$$reportNull$$$0(6);
        }
        this.answer = 16;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNameRef(JsNameRef jsNameRef) {
        if (jsNameRef == null) {
            $$$reportNull$$$0(7);
        }
        if (jsNameRef.isLeaf()) {
            this.answer = 17;
        } else {
            this.answer = 16;
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNew(JsNew jsNew) {
        if (jsNew == null) {
            $$$reportNull$$$0(8);
        }
        this.answer = 15;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNull(JsNullLiteral jsNullLiteral) {
        if (jsNullLiteral == null) {
            $$$reportNull$$$0(9);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitInt(JsIntLiteral jsIntLiteral) {
        if (jsIntLiteral == null) {
            $$$reportNull$$$0(10);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitDouble(JsDoubleLiteral jsDoubleLiteral) {
        if (jsDoubleLiteral == null) {
            $$$reportNull$$$0(11);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitObjectLiteral(JsObjectLiteral jsObjectLiteral) {
        if (jsObjectLiteral == null) {
            $$$reportNull$$$0(12);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitPostfixOperation(JsPostfixOperation jsPostfixOperation) {
        if (jsPostfixOperation == null) {
            $$$reportNull$$$0(13);
        }
        this.answer = jsPostfixOperation.getOperator().getPrecedence();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitPrefixOperation(JsPrefixOperation jsPrefixOperation) {
        if (jsPrefixOperation == null) {
            $$$reportNull$$$0(14);
        }
        this.answer = jsPrefixOperation.getOperator().getPrecedence();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitPropertyInitializer(JsPropertyInitializer jsPropertyInitializer) {
        if (jsPropertyInitializer == null) {
            $$$reportNull$$$0(15);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitRegExp(JsRegExp jsRegExp) {
        if (jsRegExp == null) {
            $$$reportNull$$$0(16);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitString(JsStringLiteral jsStringLiteral) {
        if (jsStringLiteral == null) {
            $$$reportNull$$$0(17);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitThis(JsThisRef jsThisRef) {
        if (jsThisRef == null) {
            $$$reportNull$$$0(18);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitSuper(JsSuperRef jsSuperRef) {
        if (jsSuperRef == null) {
            $$$reportNull$$$0(19);
        }
        this.answer = 17;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    protected void visitElement(JsNode jsNode) {
        if (jsNode == null) {
            $$$reportNull$$$0(20);
        }
        throw new RuntimeException("Only expressions have precedence.");
    }
}
