package org.jetbrains.kotlin.js.inline;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.inline.util.CollectionUtilsKt;

/* JADX INFO: compiled from: ExpressionDecomposer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\u001a&\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00050\u0004H\u0002\u001a\u0012\u0010\u0006\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\bH\u0002\u001a \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00022\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0002¨\u0006\u000b"}, d2 = {"match", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "predicate", "Lkotlin/Function1;", "", "toStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "", "withParentsOfNodes", "nodes", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExpressionDecomposerKt {
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [org.jetbrains.kotlin.js.inline.ExpressionDecomposerKt$match$visitor$1] */
    public static final Set<JsNode> match(JsNode jsNode, final Function1<? super JsNode, Boolean> function1) {
        ?? r0 = new JsExpressionVisitor() { // from class: org.jetbrains.kotlin.js.inline.ExpressionDecomposerKt$match$visitor$1
            private final Set<JsNode> matched = CollectionUtilsKt.IdentitySet();

            public final Set<JsNode> getMatched() {
                return this.matched;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            protected <R extends JsNode> void doTraverse(R node, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(node, "node");
                super.doTraverse(node, ctx);
                if (this.matched.contains(node) || !function1.invoke(node).booleanValue()) {
                    return;
                }
                this.matched.add(node);
            }
        };
        r0.accept(jsNode);
        return r0.getMatched();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [org.jetbrains.kotlin.js.inline.ExpressionDecomposerKt$withParentsOfNodes$visitor$1] */
    public static final Set<JsNode> withParentsOfNodes(JsNode jsNode, final Set<? extends JsNode> set) {
        ?? r0 = new JsExpressionVisitor() { // from class: org.jetbrains.kotlin.js.inline.ExpressionDecomposerKt$withParentsOfNodes$visitor$1
            private final SmartList<JsNode> stack = new SmartList<>();
            private final Set<JsNode> matched = CollectionUtilsKt.IdentitySet();

            public final Set<JsNode> getMatched() {
                return this.matched;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            protected <R extends JsNode> void doTraverse(R node, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(node, "node");
                this.stack.add(node);
                super.doTraverse(node, ctx);
                if (set.contains(node)) {
                    addAllUntilMatchedOrStatement(this.stack);
                }
                SmartList<JsNode> smartList = this.stack;
                smartList.remove(CollectionsKt.getLastIndex(smartList));
            }

            public final void addAllUntilMatchedOrStatement(List<? extends JsNode> nodesOnStack) {
                Intrinsics.checkNotNullParameter(nodesOnStack, "nodesOnStack");
                for (int lastIndex = CollectionsKt.getLastIndex(nodesOnStack); -1 < lastIndex; lastIndex--) {
                    JsNode jsNode2 = nodesOnStack.get(lastIndex);
                    if (this.matched.contains(jsNode2)) {
                        return;
                    }
                    this.matched.add(jsNode2);
                    if (jsNode2 instanceof JsStatement) {
                        return;
                    }
                }
            }
        };
        r0.accept(jsNode);
        return r0.getMatched();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsStatement toStatement(List<? extends JsStatement> list) {
        int size = list.size();
        if (size == 0) {
            return JsEmpty.INSTANCE;
        }
        if (size == 1) {
            return list.get(0);
        }
        return new JsBlock((List<JsStatement>) list);
    }
}
