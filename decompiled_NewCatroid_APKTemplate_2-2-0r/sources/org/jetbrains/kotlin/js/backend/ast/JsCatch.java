package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsCatch extends SourceInfoAwareJsNode implements HasCondition {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private JsBlock body;
    private JsExpression condition;
    private JsParameter param;
    protected final JsCatchScope scope;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = "ident";
        } else if (i == 3) {
            objArr[0] = "catchBody";
        } else if (i != 4) {
            objArr[0] = "name";
        } else {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsCatch";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsCatch";
        } else {
            objArr[1] = "deepCopy";
        }
        if (i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    public JsCatch(JsName jsName) {
        if (jsName == null) {
            $$$reportNull$$$0(0);
        }
        this.param = new JsParameter(jsName);
        this.scope = null;
    }

    public JsCatch(JsScope jsScope, String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        JsCatchScope jsCatchScope = new JsCatchScope(jsScope, str);
        this.scope = jsCatchScope;
        this.param = new JsParameter(jsCatchScope.findName(str));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsCatch(JsScope jsScope, String str, JsStatement jsStatement) {
        this(jsScope, str);
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (jsStatement == null) {
            $$$reportNull$$$0(3);
        }
        if (jsStatement instanceof JsBlock) {
            this.body = (JsBlock) jsStatement;
        } else {
            this.body = new JsBlock(jsStatement);
        }
    }

    public JsBlock getBody() {
        return this.body;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasCondition
    public JsExpression getCondition() {
        return this.condition;
    }

    public JsParameter getParameter() {
        return this.param;
    }

    public JsScope getScope() {
        return this.scope;
    }

    public void setBody(JsBlock jsBlock) {
        this.body = jsBlock;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasCondition
    public void setCondition(JsExpression jsExpression) {
        this.condition = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitCatch(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.param);
        JsExpression jsExpression = this.condition;
        if (jsExpression != null) {
            jsVisitor.accept(jsExpression);
        }
        jsVisitor.accept(this.body);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.param = (JsParameter) jsVisitorWithContext.accept(this.param);
            JsExpression jsExpression = this.condition;
            if (jsExpression != null) {
                this.condition = (JsExpression) jsVisitorWithContext.accept(jsExpression);
            }
            this.body = (JsBlock) jsVisitorWithContext.acceptStatement(this.body);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsCatch deepCopy() {
        JsCatchScope jsCatchScope = this.scope;
        JsCatch jsCatch = (JsCatch) new JsCatch(jsCatchScope != null ? jsCatchScope.copy() : null, (JsBlock) AstUtil.deepCopy(this.body), (JsExpression) AstUtil.deepCopy(this.condition), (JsParameter) AstUtil.deepCopy(this.param)).withMetadataFrom(this);
        if (jsCatch == null) {
            $$$reportNull$$$0(4);
        }
        return jsCatch;
    }

    private JsCatch(JsCatchScope jsCatchScope, JsBlock jsBlock, JsExpression jsExpression, JsParameter jsParameter) {
        this.scope = jsCatchScope;
        this.body = jsBlock;
        this.condition = jsExpression;
        this.param = jsParameter;
    }
}
