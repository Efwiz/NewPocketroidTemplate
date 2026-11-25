package org.jetbrains.kotlin.js.inline.clean;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.util.InvocationUtilsKt;

/* JADX INFO: compiled from: RedundantCallElimination.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/RedundantCallElimination;", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;)V", "changed", "", "apply", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantCallElimination {
    private boolean changed;
    private final JsBlock root;

    public RedundantCallElimination(JsBlock root) {
        Intrinsics.checkNotNullParameter(root, "root");
        this.root = root;
    }

    public final boolean apply() {
        this.root.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.RedundantCallElimination.apply.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitInvocation(JsInvocation invocation) {
                Intrinsics.checkNotNullParameter(invocation, "invocation");
                tryEliminate(invocation);
                super.visitInvocation(invocation);
            }

            private final void tryEliminate(JsInvocation invocation) {
                if (InvocationUtilsKt.isCallInvocation(invocation)) {
                    JsExpression qualifier = invocation.getQualifier();
                    JsNameRef jsNameRef = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
                    if (jsNameRef != null && MetadataProperties.isJsCall(jsNameRef)) {
                        JsExpression qualifier2 = jsNameRef.getQualifier();
                        JsNameRef jsNameRef2 = qualifier2 instanceof JsNameRef ? (JsNameRef) qualifier2 : null;
                        if (jsNameRef2 == null) {
                            return;
                        }
                        JsExpression qualifier3 = jsNameRef2.getQualifier();
                        JsNameRef jsNameRef3 = qualifier3 instanceof JsNameRef ? (JsNameRef) qualifier3 : null;
                        if (jsNameRef3 == null) {
                            return;
                        }
                        List<JsExpression> arguments = invocation.getArguments();
                        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                        Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) arguments);
                        JsNameRef jsNameRef4 = objFirstOrNull instanceof JsNameRef ? (JsNameRef) objFirstOrNull : null;
                        if (jsNameRef4 != null && jsNameRef3.getQualifier() == null && jsNameRef3.getName() != null && jsNameRef4.getQualifier() == null && Intrinsics.areEqual(jsNameRef3.getName(), jsNameRef4.getName())) {
                            invocation.getArguments().remove(0);
                            invocation.setQualifier(jsNameRef2);
                            RedundantCallElimination.this.changed = true;
                        }
                    }
                }
            }
        });
        return this.changed;
    }
}
