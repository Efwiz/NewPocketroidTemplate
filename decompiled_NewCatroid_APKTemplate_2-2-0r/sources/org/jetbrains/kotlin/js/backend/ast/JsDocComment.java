package org.jetbrains.kotlin.js.backend.ast;

import java.util.Collections;
import java.util.Map;

/* JADX INFO: loaded from: classes5.dex */
public class JsDocComment extends JsExpression {
    private final Map<String, Object> tags;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsDocComment", "deepCopy"));
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
    }

    public JsDocComment(Map<String, Object> map) {
        this.tags = map;
    }

    public Map<String, Object> getTags() {
        return this.tags;
    }

    public JsDocComment(String str, JsNameRef jsNameRef) {
        this.tags = Collections.singletonMap(str, jsNameRef);
    }

    public JsDocComment(String str, String str2) {
        this.tags = Collections.singletonMap(str, str2);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitDocComment(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsDocComment deepCopy() {
        JsDocComment jsDocComment = (JsDocComment) new JsDocComment(this.tags).withMetadataFrom(this);
        if (jsDocComment == null) {
            $$$reportNull$$$0(0);
        }
        return jsDocComment;
    }
}
