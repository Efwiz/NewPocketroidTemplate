package org.jetbrains.kotlin.js.backend.ast;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsNew extends JsExpression.JsExpressionHasArguments {
    private JsExpression constructorExpression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsNew", "deepCopy"));
    }

    public JsNew(JsExpression jsExpression) {
        this(jsExpression, new SmartList());
    }

    public JsNew(JsExpression jsExpression, List<? extends JsExpression> list) {
        super(new SmartList((Collection) list));
        this.constructorExpression = jsExpression;
    }

    public JsExpression getConstructorExpression() {
        return this.constructorExpression;
    }

    public void setConstructorExpression(JsExpression jsExpression) {
        this.constructorExpression = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitNew(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.constructorExpression);
        jsVisitor.acceptList(this.arguments);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.constructorExpression = (JsExpression) jsVisitorWithContext.accept(this.constructorExpression);
            jsVisitorWithContext.acceptList(this.arguments);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression.JsExpressionHasArguments, org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsNew deepCopy() {
        JsNew jsNew = (JsNew) new JsNew((JsExpression) AstUtil.deepCopy(this.constructorExpression), AstUtil.deepCopy(this.arguments)).withMetadataFrom(this);
        if (jsNew == null) {
            $$$reportNull$$$0(0);
        }
        return jsNew;
    }
}
