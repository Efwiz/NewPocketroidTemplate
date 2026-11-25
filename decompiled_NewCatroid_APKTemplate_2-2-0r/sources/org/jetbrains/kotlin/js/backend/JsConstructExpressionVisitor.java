package org.jetbrains.kotlin.js.backend;

import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;

/* JADX INFO: loaded from: classes5.dex */
public class JsConstructExpressionVisitor extends RecursiveJsVisitor {
    private boolean containsInvocation;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 3) {
            objArr[0] = "invocation";
        } else if (i != 4) {
            objArr[0] = "x";
        } else {
            objArr[0] = "nameRef";
        }
        objArr[1] = "org/jetbrains/kotlin/js/backend/JsConstructExpressionVisitor";
        switch (i) {
            case 1:
                objArr[2] = "visitArray";
                break;
            case 2:
                objArr[2] = "visitFunction";
                break;
            case 3:
                objArr[2] = "visitInvocation";
                break;
            case 4:
                objArr[2] = "visitNameRef";
                break;
            case 5:
                objArr[2] = "visitNew";
                break;
            case 6:
                objArr[2] = "visitObjectLiteral";
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
    public void visitFunction(JsFunction jsFunction) {
        if (jsFunction == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNew(JsNew jsNew) {
        if (jsNew == null) {
            $$$reportNull$$$0(5);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitObjectLiteral(JsObjectLiteral jsObjectLiteral) {
        if (jsObjectLiteral == null) {
            $$$reportNull$$$0(6);
        }
    }

    public static boolean exec(JsExpression jsExpression) {
        if (JsPrecedenceVisitor.exec(jsExpression) < 15) {
            return true;
        }
        JsConstructExpressionVisitor jsConstructExpressionVisitor = new JsConstructExpressionVisitor();
        jsConstructExpressionVisitor.accept(jsExpression);
        return jsConstructExpressionVisitor.containsInvocation;
    }

    private JsConstructExpressionVisitor() {
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitArrayAccess(JsArrayAccess jsArrayAccess) {
        if (jsArrayAccess == null) {
            $$$reportNull$$$0(0);
        }
        accept(jsArrayAccess.getArrayExpression());
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitInvocation(JsInvocation jsInvocation) {
        if (jsInvocation == null) {
            $$$reportNull$$$0(3);
        }
        this.containsInvocation = true;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNameRef(JsNameRef jsNameRef) {
        if (jsNameRef == null) {
            $$$reportNull$$$0(4);
        }
        if (jsNameRef.isLeaf()) {
            return;
        }
        accept(jsNameRef.getQualifier());
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public <T extends JsNode> void accept(T t) {
        if (!(t instanceof JsExpression) || JsPrecedenceVisitor.exec((JsExpression) t) >= 15) {
            super.accept(t);
        }
    }
}
