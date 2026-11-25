package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsFor extends SourceInfoAwareJsNode implements JsLoop {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private JsStatement body;
    private JsExpression condition;
    private JsExpression incrementExpression;
    private JsExpression initExpression;
    private JsVars initVars;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsFor", "deepCopy"));
    }

    public JsFor(JsVars jsVars, JsExpression jsExpression, JsExpression jsExpression2) {
        this(jsVars, jsExpression, jsExpression2, (JsStatement) null);
    }

    public JsFor(JsVars jsVars, JsExpression jsExpression, JsExpression jsExpression2, JsStatement jsStatement) {
        this.initVars = jsVars;
        this.incrementExpression = jsExpression2;
        this.condition = jsExpression;
        this.body = jsStatement;
        this.initExpression = null;
    }

    public JsFor(JsExpression jsExpression, JsExpression jsExpression2, JsExpression jsExpression3) {
        this(jsExpression, jsExpression2, jsExpression3, (JsStatement) null);
    }

    public JsFor(JsExpression jsExpression, JsExpression jsExpression2, JsExpression jsExpression3, JsStatement jsStatement) {
        this.initExpression = jsExpression;
        this.incrementExpression = jsExpression3;
        this.condition = jsExpression2;
        this.body = jsStatement;
        this.initVars = null;
    }

    public JsStatement getBody() {
        return this.body;
    }

    public JsExpression getCondition() {
        return this.condition;
    }

    public JsExpression getIncrementExpression() {
        return this.incrementExpression;
    }

    public JsExpression getInitExpression() {
        return this.initExpression;
    }

    public JsVars getInitVars() {
        return this.initVars;
    }

    public void setBody(JsStatement jsStatement) {
        this.body = jsStatement;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitFor(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        JsExpression jsExpression = this.initExpression;
        if (jsExpression != null) {
            jsVisitor.accept(jsExpression);
        } else {
            JsVars jsVars = this.initVars;
            if (jsVars != null) {
                jsVisitor.accept(jsVars);
            }
        }
        JsExpression jsExpression2 = this.condition;
        if (jsExpression2 != null) {
            jsVisitor.accept(jsExpression2);
        }
        JsExpression jsExpression3 = this.incrementExpression;
        if (jsExpression3 != null) {
            jsVisitor.accept(jsExpression3);
        }
        jsVisitor.accept(this.body);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            JsExpression jsExpression = this.initExpression;
            if (jsExpression != null) {
                this.initExpression = (JsExpression) jsVisitorWithContext.accept(jsExpression);
            } else {
                JsVars jsVars = this.initVars;
                if (jsVars != null) {
                    JsStatement jsStatementAcceptStatement = jsVisitorWithContext.acceptStatement(jsVars);
                    if (jsStatementAcceptStatement instanceof JsVars) {
                        this.initVars = (JsVars) jsStatementAcceptStatement;
                    } else {
                        this.initVars = null;
                        if (jsStatementAcceptStatement instanceof JsExpressionStatement) {
                            this.initExpression = ((JsExpressionStatement) jsStatementAcceptStatement).getExpression();
                        } else if (jsStatementAcceptStatement != null) {
                            jsContext.addPrevious(jsStatementAcceptStatement);
                        }
                    }
                }
            }
            JsExpression jsExpression2 = this.condition;
            if (jsExpression2 != null) {
                this.condition = (JsExpression) jsVisitorWithContext.accept(jsExpression2);
            }
            JsExpression jsExpression3 = this.incrementExpression;
            if (jsExpression3 != null) {
                this.incrementExpression = (JsExpression) jsVisitorWithContext.accept(jsExpression3);
            }
            this.body = jsVisitorWithContext.acceptStatement(this.body);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsFor deepCopy() {
        JsFor jsFor;
        JsStatement jsStatement = (JsStatement) AstUtil.deepCopy(this.body);
        JsExpression jsExpression = (JsExpression) AstUtil.deepCopy(this.condition);
        JsExpression jsExpression2 = (JsExpression) AstUtil.deepCopy(this.incrementExpression);
        JsVars jsVars = this.initVars;
        if (jsVars != null) {
            jsFor = new JsFor(jsVars.deepCopy(), jsExpression, jsExpression2, jsStatement);
        } else {
            JsExpression jsExpression3 = this.initExpression;
            jsFor = new JsFor(jsExpression3 != null ? jsExpression3.deepCopy() : null, jsExpression, jsExpression2, jsStatement);
        }
        JsFor jsFor2 = (JsFor) jsFor.withMetadataFrom(this);
        if (jsFor2 == null) {
            $$$reportNull$$$0(0);
        }
        return jsFor2;
    }
}
