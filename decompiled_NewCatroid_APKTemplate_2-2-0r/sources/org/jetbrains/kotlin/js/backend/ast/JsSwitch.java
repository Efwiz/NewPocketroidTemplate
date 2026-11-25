package org.jetbrains.kotlin.js.backend.ast;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsSwitch extends SourceInfoAwareJsNode implements JsStatement {
    private final List<JsSwitchMember> cases;
    private JsExpression expression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsSwitch", "deepCopy"));
    }

    public JsSwitch() {
        this.cases = new ArrayList();
    }

    public JsSwitch(JsExpression jsExpression, List<JsSwitchMember> list) {
        this.expression = jsExpression;
        this.cases = list;
    }

    public List<JsSwitchMember> getCases() {
        return this.cases;
    }

    public JsExpression getExpression() {
        return this.expression;
    }

    public void setExpression(JsExpression jsExpression) {
        this.expression = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visit(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.expression);
        jsVisitor.acceptWithInsertRemove(this.cases);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.expression = (JsExpression) jsVisitorWithContext.accept(this.expression);
            jsVisitorWithContext.acceptList(this.cases);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsSwitch deepCopy() {
        JsSwitch jsSwitch = (JsSwitch) new JsSwitch((JsExpression) AstUtil.deepCopy(this.expression), AstUtil.deepCopy(this.cases)).withMetadataFrom(this);
        if (jsSwitch == null) {
            $$$reportNull$$$0(0);
        }
        return jsSwitch;
    }
}
