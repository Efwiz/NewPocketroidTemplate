package org.jetbrains.kotlin.js.inline.clean;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.inline.util.InvocationUtilsKt;

/* JADX INFO: compiled from: removeUnusedLocalFunctionDeclarations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"isFunctionReference", "", "expr", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "removeUnusedLocalFunctionDeclarations", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RemoveUnusedLocalFunctionDeclarationsKt {
    public static final void removeUnusedLocalFunctionDeclarations(JsNode root) {
        Intrinsics.checkNotNullParameter(root, "root");
        UnusedInstanceCollector unusedInstanceCollector = new UnusedInstanceCollector();
        unusedInstanceCollector.accept(root);
        final List<JsStatement> removableDeclarations = unusedInstanceCollector.getRemovableDeclarations();
        new NodeRemover(JsStatement.class, new Function1<JsStatement, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.RemoveUnusedLocalFunctionDeclarationsKt.removeUnusedLocalFunctionDeclarations.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(JsStatement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(removableDeclarations.contains(it));
            }
        }).accept(root);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isFunctionReference(JsExpression jsExpression) {
        if (jsExpression instanceof JsInvocation) {
            jsExpression = ((JsInvocation) jsExpression).getQualifier();
        } else if (!(jsExpression instanceof JsNameRef)) {
            jsExpression = null;
        }
        return (jsExpression != null ? InvocationUtilsKt.getTransitiveStaticRef(jsExpression) : null) instanceof JsFunction;
    }
}
