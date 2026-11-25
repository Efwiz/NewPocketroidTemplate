package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsForIn extends SourceInfoAwareJsNode implements JsLoop {
    private JsStatement body;
    private JsExpression iterExpression;
    private final JsName iterVarName;
    private JsExpression objectExpression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsForIn", "deepCopy"));
    }

    public JsForIn() {
        this(null);
    }

    public JsForIn(JsName jsName) {
        this.iterVarName = jsName;
    }

    public JsForIn(JsName jsName, JsExpression jsExpression, JsExpression jsExpression2, JsStatement jsStatement) {
        this.iterVarName = jsName;
        this.iterExpression = jsExpression;
        this.objectExpression = jsExpression2;
        this.body = jsStatement;
    }

    public JsStatement getBody() {
        return this.body;
    }

    public JsExpression getIterExpression() {
        return this.iterExpression;
    }

    public JsName getIterVarName() {
        return this.iterVarName;
    }

    public JsExpression getObjectExpression() {
        return this.objectExpression;
    }

    public void setBody(JsStatement jsStatement) {
        this.body = jsStatement;
    }

    public void setIterExpression(JsExpression jsExpression) {
        this.iterExpression = jsExpression;
    }

    public void setObjectExpression(JsExpression jsExpression) {
        this.objectExpression = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitForIn(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        JsExpression jsExpression = this.iterExpression;
        if (jsExpression != null) {
            jsVisitor.acceptLvalue(jsExpression);
        }
        jsVisitor.accept(this.objectExpression);
        jsVisitor.accept(this.body);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            JsExpression jsExpression = this.iterExpression;
            if (jsExpression != null) {
                this.iterExpression = jsVisitorWithContext.acceptLvalue(jsExpression);
            }
            this.objectExpression = (JsExpression) jsVisitorWithContext.accept(this.objectExpression);
            this.body = jsVisitorWithContext.acceptStatement(this.body);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsForIn deepCopy() {
        JsStatement jsStatement = (JsStatement) AstUtil.deepCopy(this.body);
        JsForIn jsForIn = (JsForIn) new JsForIn(this.iterVarName, (JsExpression) AstUtil.deepCopy(this.iterExpression), (JsExpression) AstUtil.deepCopy(this.objectExpression), jsStatement).withMetadataFrom(this);
        if (jsForIn == null) {
            $$$reportNull$$$0(0);
        }
        return jsForIn;
    }
}
