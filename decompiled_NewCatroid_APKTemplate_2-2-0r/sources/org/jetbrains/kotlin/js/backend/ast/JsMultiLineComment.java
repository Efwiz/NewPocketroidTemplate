package org.jetbrains.kotlin.js.backend.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsMultiLineComment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\r\u001a\u00020\u0000H\u0016J\u001c\u0010\u000e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000f2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsMultiLineComment;", "Lorg/jetbrains/kotlin/js/backend/ast/SourceInfoAwareJsNode;", "Lorg/jetbrains/kotlin/js/backend/ast/JsComment;", "text", "", "(Ljava/lang/String;)V", "getText", "()Ljava/lang/String;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitor;", "acceptChildren", "deepCopy", "traverse", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContext;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsMultiLineComment extends SourceInfoAwareJsNode implements JsComment {
    private final String text;

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsMultiLineComment deepCopy() {
        return this;
    }

    public JsMultiLineComment(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.text = text;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsComment
    public String getText() {
        return this.text;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitMultiLineComment(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext visitor, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        visitor.visit(this, ctx);
        visitor.endVisit(this, ctx);
    }
}
