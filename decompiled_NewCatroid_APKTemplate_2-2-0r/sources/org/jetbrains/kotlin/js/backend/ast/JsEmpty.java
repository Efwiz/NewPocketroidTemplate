package org.jetbrains.kotlin.js.backend.ast;

import com.android.dx.rop.code.RegisterSpec;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsEmpty.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0000H\u0016J\u001c\u0010\t\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsEmpty;", "Lorg/jetbrains/kotlin/js/backend/ast/SourceInfoAwareJsNode;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "()V", "accept", "", RegisterSpec.PREFIX, "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitor;", "deepCopy", "traverse", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContext;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsEmpty extends SourceInfoAwareJsNode implements JsStatement {
    public static final JsEmpty INSTANCE = new JsEmpty();

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsEmpty deepCopy() {
        return this;
    }

    private JsEmpty() {
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor v) {
        Intrinsics.checkNotNullParameter(v, "v");
        v.visitEmpty(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext v, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        v.visit(this, ctx);
        v.endVisit(this, ctx);
    }
}
