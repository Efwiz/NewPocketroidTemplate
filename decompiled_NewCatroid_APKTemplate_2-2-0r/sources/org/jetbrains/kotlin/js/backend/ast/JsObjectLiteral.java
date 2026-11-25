package org.jetbrains.kotlin.js.backend.ast;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsObjectLiteral extends JsLiteral {
    private boolean multiline;
    private final List<JsPropertyInitializer> properties;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsObjectLiteral", "deepCopy"));
    }

    public JsObjectLiteral() {
        this(new SmartList());
    }

    public JsObjectLiteral(boolean z) {
        this(new SmartList(), z);
    }

    public boolean isMultiline() {
        return this.multiline;
    }

    public void setMultiline(boolean z) {
        this.multiline = z;
    }

    public JsObjectLiteral(List<JsPropertyInitializer> list) {
        this(list, false);
    }

    public JsObjectLiteral(List<JsPropertyInitializer> list, boolean z) {
        this.properties = list;
        this.multiline = z;
    }

    public List<JsPropertyInitializer> getPropertyInitializers() {
        return this.properties;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitObjectLiteral(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.acceptWithInsertRemove(this.properties);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            jsVisitorWithContext.acceptList(this.properties);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsObjectLiteral deepCopy() {
        JsObjectLiteral jsObjectLiteral = (JsObjectLiteral) new JsObjectLiteral(AstUtil.deepCopy(this.properties), this.multiline).withMetadataFrom(this);
        if (jsObjectLiteral == null) {
            $$$reportNull$$$0(0);
        }
        return jsObjectLiteral;
    }
}
