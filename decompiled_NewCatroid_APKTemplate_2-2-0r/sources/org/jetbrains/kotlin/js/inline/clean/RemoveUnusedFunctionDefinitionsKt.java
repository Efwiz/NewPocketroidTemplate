package org.jetbrains.kotlin.js.inline.clean;

import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;

/* JADX INFO: compiled from: removeUnusedFunctionDefinitions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¨\u0006\b"}, d2 = {"removeUnusedFunctionDefinitions", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "functions", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RemoveUnusedFunctionDefinitionsKt {
    public static final void removeUnusedFunctionDefinitions(JsNode root, Map<JsName, JsFunction> functions) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(functions, "functions");
        UnusedLocalFunctionsCollector unusedLocalFunctionsCollector = new UnusedLocalFunctionsCollector(functions);
        unusedLocalFunctionsCollector.process();
        unusedLocalFunctionsCollector.accept(root);
        final Set set = CollectionsKt.toSet(unusedLocalFunctionsCollector.getRemovableFunctions());
        new NodeRemover(JsStatement.class, new Function1<JsStatement, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.RemoveUnusedFunctionDefinitionsKt$removeUnusedFunctionDefinitions$remover$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Code duplicated, block: B:10:0x0032  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(JsStatement statement) {
                JsExpression initExpression;
                Intrinsics.checkNotNullParameter(statement, "statement");
                boolean z = false;
                if (statement instanceof JsExpressionStatement) {
                    initExpression = ((JsExpressionStatement) statement).getExpression();
                } else if (statement instanceof JsVars) {
                    JsVars jsVars = (JsVars) statement;
                    if (jsVars.getVars().size() == 1) {
                        initExpression = jsVars.getVars().get(0).getInitExpression();
                    } else {
                        initExpression = null;
                    }
                } else {
                    initExpression = null;
                }
                if ((initExpression instanceof JsFunction) && set.contains(initExpression)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }).accept(root);
    }
}
