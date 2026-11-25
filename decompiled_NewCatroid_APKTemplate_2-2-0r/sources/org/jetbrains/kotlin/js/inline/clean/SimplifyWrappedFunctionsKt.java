package org.jetbrains.kotlin.js.inline.clean;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;

/* JADX INFO: compiled from: simplifyWrappedFunctions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"simplifyWrappedFunctions", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SimplifyWrappedFunctionsKt {
    public static final void simplifyWrappedFunctions(JsNode root) {
        Intrinsics.checkNotNullParameter(root, "root");
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.SimplifyWrappedFunctionsKt$simplifyWrappedFunctions$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsInvocation x, JsContext<? super JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                FunctionWithWrapper functionWithWrapperExtractFunction = CollectUtilsKt.extractFunction(x);
                if (functionWithWrapperExtractFunction != null) {
                    JsFunction function = functionWithWrapperExtractFunction.getFunction();
                    JsBlock wrapperBody = functionWithWrapperExtractFunction.getWrapperBody();
                    if (wrapperBody == null || wrapperBody.getStatements().size() != 1) {
                        return;
                    }
                    ctx.replaceMe(function);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsVars x, JsContext<? super JsStatement> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                List<JsVars.JsVar> vars = x.getVars();
                Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
                JsVars.JsVar jsVar = (JsVars.JsVar) CollectionsKt.singleOrNull((List) vars);
                if (jsVar != null) {
                    JsExpression initExpression = jsVar.getInitExpression();
                    JsFunction jsFunction = initExpression instanceof JsFunction ? (JsFunction) initExpression : null;
                    if (jsFunction == null || jsFunction.getName() != null) {
                        return;
                    }
                    jsFunction.setName(jsVar.getName());
                    ctx.replaceMe(jsFunction.makeStmt());
                }
            }
        }.accept(root);
    }
}
