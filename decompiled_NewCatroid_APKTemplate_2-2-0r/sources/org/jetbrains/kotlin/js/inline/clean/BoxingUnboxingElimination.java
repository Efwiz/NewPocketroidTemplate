package org.jetbrains.kotlin.js.inline.clean;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;

/* JADX INFO: compiled from: BoxingUnboxingElimination.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/BoxingUnboxingElimination;", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;)V", "changed", "", "apply", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BoxingUnboxingElimination {
    private boolean changed;
    private final JsBlock root;

    public BoxingUnboxingElimination(JsBlock root) {
        Intrinsics.checkNotNullParameter(root, "root");
        this.root = root;
    }

    public final boolean apply() {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.BoxingUnboxingElimination$apply$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsFunction x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsInvocation x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                super.endVisit(x, (JsContext) ctx);
                tryEliminate(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsNew x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                super.endVisit(x, (JsContext) ctx);
                tryEliminate(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsNameRef x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                super.endVisit(x, (JsContext) ctx);
                tryEliminate(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsArrayAccess x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                super.endVisit(x, (JsContext) ctx);
            }

            private final void tryEliminate(JsExpression expression, JsContext<JsNode> ctx) {
                JsExpression jsExpression = expression;
                if (MetadataProperties.isInlineClassBoxing(jsExpression) || MetadataProperties.isInlineClassUnboxing(jsExpression)) {
                    JsExpression jsExpression2 = (JsExpression) CollectionsKt.first((List) getArguments(expression));
                    JsExpression jsExpression3 = jsExpression2;
                    if ((MetadataProperties.isInlineClassBoxing(jsExpression3) || MetadataProperties.isInlineClassUnboxing(jsExpression3)) && MetadataProperties.isInlineClassBoxing(jsExpression3) != MetadataProperties.isInlineClassBoxing(jsExpression)) {
                        ctx.replaceMe((JsNode) CollectionsKt.first((List) getArguments(jsExpression2)));
                        this.this$0.changed = true;
                    }
                }
            }

            private final List<JsExpression> getArguments(JsExpression jsExpression) {
                if (!(jsExpression instanceof JsExpression.JsExpressionHasArguments)) {
                    return jsExpression instanceof JsNameRef ? CollectionsKt.listOfNotNull(((JsNameRef) jsExpression).getQualifier()) : CollectionsKt.emptyList();
                }
                List<JsExpression> arguments = ((JsExpression.JsExpressionHasArguments) jsExpression).getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                return arguments;
            }
        }.accept(this.root);
        return this.changed;
    }
}
