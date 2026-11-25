package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsIf extends SourceInfoAwareJsNode implements JsStatement {
    private JsStatement elseStatement;
    private JsExpression ifExpression;
    private JsStatement thenStatement;

    /* JADX WARN: Code duplicated, block: B:23:0x0032  */
    /* JADX WARN: Code duplicated, block: B:24:0x0035  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 8) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "thenStatement";
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsIf";
        } else if (i == 7) {
            objArr[0] = "thenStatement";
        } else if (i != 8) {
            objArr[0] = "ifExpression";
        } else {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsIf";
        }
        if (i == 4) {
            objArr[1] = "getIfExpression";
        } else if (i == 5) {
            objArr[1] = "getThenStatement";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsIf";
        } else {
            objArr[1] = "deepCopy";
        }
        switch (i) {
            case 4:
            case 5:
            case 8:
                break;
            case 6:
                objArr[2] = "setIfExpression";
                break;
            case 7:
                objArr[2] = "setThenStatement";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public JsIf(JsExpression jsExpression, JsStatement jsStatement, JsStatement jsStatement2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (jsStatement == null) {
            $$$reportNull$$$0(1);
        }
        this.ifExpression = jsExpression;
        this.thenStatement = jsStatement;
        this.elseStatement = jsStatement2;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsIf(JsExpression jsExpression, JsStatement jsStatement) {
        this(jsExpression, jsStatement, null);
        if (jsExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (jsStatement == null) {
            $$$reportNull$$$0(3);
        }
    }

    public JsStatement getElseStatement() {
        return this.elseStatement;
    }

    public JsExpression getIfExpression() {
        JsExpression jsExpression = this.ifExpression;
        if (jsExpression == null) {
            $$$reportNull$$$0(4);
        }
        return jsExpression;
    }

    public JsStatement getThenStatement() {
        JsStatement jsStatement = this.thenStatement;
        if (jsStatement == null) {
            $$$reportNull$$$0(5);
        }
        return jsStatement;
    }

    public void setElseStatement(JsStatement jsStatement) {
        this.elseStatement = jsStatement;
    }

    public void setIfExpression(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(6);
        }
        this.ifExpression = jsExpression;
    }

    public void setThenStatement(JsStatement jsStatement) {
        if (jsStatement == null) {
            $$$reportNull$$$0(7);
        }
        this.thenStatement = jsStatement;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitIf(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.ifExpression);
        jsVisitor.accept(this.thenStatement);
        JsStatement jsStatement = this.elseStatement;
        if (jsStatement != null) {
            jsVisitor.accept(jsStatement);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.ifExpression = (JsExpression) jsVisitorWithContext.accept(this.ifExpression);
            this.thenStatement = jsVisitorWithContext.acceptStatement(this.thenStatement);
            JsStatement jsStatement = this.elseStatement;
            if (jsStatement != null) {
                this.elseStatement = jsVisitorWithContext.acceptStatement(jsStatement);
            }
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsIf deepCopy() {
        JsIf jsIf = (JsIf) new JsIf((JsExpression) AstUtil.deepCopy(this.ifExpression), (JsStatement) AstUtil.deepCopy(this.thenStatement), (JsStatement) AstUtil.deepCopy(this.elseStatement)).withMetadataFrom(this);
        if (jsIf == null) {
            $$$reportNull$$$0(8);
        }
        return jsIf;
    }
}
