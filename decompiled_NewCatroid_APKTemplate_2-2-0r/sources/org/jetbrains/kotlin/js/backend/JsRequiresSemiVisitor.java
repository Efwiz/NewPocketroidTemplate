package org.jetbrains.kotlin.js.backend;

import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsForIn;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVisitor;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;

/* JADX INFO: loaded from: classes5.dex */
public class JsRequiresSemiVisitor extends JsVisitor {
    private boolean needsSemicolon;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "x";
        objArr[1] = "org/jetbrains/kotlin/js/backend/JsRequiresSemiVisitor";
        if (i == 1) {
            objArr[2] = "visitForIn";
        } else if (i == 2) {
            objArr[2] = "visitIf";
        } else if (i == 3) {
            objArr[2] = "visitLabel";
        } else if (i != 4) {
            objArr[2] = "visitFor";
        } else {
            objArr[2] = "visitWhile";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private JsRequiresSemiVisitor() {
    }

    public static boolean exec(JsStatement jsStatement) {
        JsRequiresSemiVisitor jsRequiresSemiVisitor = new JsRequiresSemiVisitor();
        jsRequiresSemiVisitor.accept(jsStatement);
        return jsRequiresSemiVisitor.needsSemicolon;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitFor(JsFor jsFor) {
        if (jsFor == null) {
            $$$reportNull$$$0(0);
        }
        if (jsFor.getBody() instanceof JsEmpty) {
            this.needsSemicolon = true;
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitForIn(JsForIn jsForIn) {
        if (jsForIn == null) {
            $$$reportNull$$$0(1);
        }
        if (jsForIn.getBody() instanceof JsEmpty) {
            this.needsSemicolon = true;
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitIf(JsIf jsIf) {
        if (jsIf == null) {
            $$$reportNull$$$0(2);
        }
        JsStatement thenStatement = jsIf.getThenStatement();
        JsStatement elseStatement = jsIf.getElseStatement();
        if (elseStatement != null) {
            thenStatement = elseStatement;
        }
        if (thenStatement instanceof JsEmpty) {
            this.needsSemicolon = true;
        } else {
            accept(thenStatement);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitLabel(JsLabel jsLabel) {
        if (jsLabel == null) {
            $$$reportNull$$$0(3);
        }
        if (jsLabel.getStatement() instanceof JsEmpty) {
            this.needsSemicolon = true;
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitWhile(JsWhile jsWhile) {
        if (jsWhile == null) {
            $$$reportNull$$$0(4);
        }
        if (jsWhile.getBody() instanceof JsEmpty) {
            this.needsSemicolon = true;
        }
    }
}
