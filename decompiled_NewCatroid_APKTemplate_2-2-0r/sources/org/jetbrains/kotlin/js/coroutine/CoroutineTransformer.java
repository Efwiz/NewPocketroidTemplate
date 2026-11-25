package org.jetbrains.kotlin.js.coroutine;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;
import org.jetbrains.kotlin.js.translate.declaration.InlineCoroutineUtilKt;
import org.jetbrains.kotlin.js.translate.expression.InlineMetadata;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: CoroutineTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00052\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0016J\u001c\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\u00102\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0016J\u001c\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\u00112\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0016R\u001f\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/coroutine/CoroutineTransformer;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "()V", "functionName", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "", "getFunctionName", "()Ljava/util/Map;", "endVisit", "", "x", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "visit", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars$JsVar;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineTransformer extends JsVisitorWithContextImpl {
    private final Map<JsFunction, String> functionName = new LinkedHashMap();

    public final Map<JsFunction, String> getFunctionName() {
        return this.functionName;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsExpressionStatement x, JsContext<?> ctx) {
        JsName name2;
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsExpression expression = x.getExpression();
        Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
        Pair<JsExpression, JsExpression> pairDecomposeAssignment = JsAstUtils.decomposeAssignment(expression);
        if (pairDecomposeAssignment != null) {
            JsExpression jsExpressionComponent1 = pairDecomposeAssignment.component1();
            JsExpression jsExpressionComponent2 = pairDecomposeAssignment.component2();
            InlineMetadata.Companion companion = InlineMetadata.INSTANCE;
            Intrinsics.checkNotNull(jsExpressionComponent2);
            FunctionWithWrapper functionWithWrapperTryExtractFunction = companion.tryExtractFunction(jsExpressionComponent2);
            if (functionWithWrapperTryExtractFunction != null) {
                JsFunction function = functionWithWrapperTryExtractFunction.getFunction();
                JsNameRef jsNameRef = jsExpressionComponent1 instanceof JsNameRef ? (JsNameRef) jsExpressionComponent1 : null;
                if (jsNameRef == null || (name2 = jsNameRef.getName()) == null) {
                    name2 = function.getName();
                }
                this.functionName.put(function, name2 != null ? name2.getIdent() : null);
            }
        } else if (expression instanceof JsFunction) {
            Map<JsFunction, String> map = this.functionName;
            JsName name3 = ((JsFunction) expression).getName();
            map.put((JsFunction) expression, name3 != null ? name3.getIdent() : null);
        }
        return super.visit(x, ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsFunction x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (MetadataProperties.isInlineableCoroutineBody(x)) {
            x.setBody((JsBlock) InlineCoroutineUtilKt.transformCoroutineMetadataToSpecialFunctions(x.getBody()));
        }
        if (MetadataProperties.getCoroutineMetadata(x) != null) {
            getLastStatementLevelContext().addPrevious(new CoroutineFunctionTransformer(x, this.functionName.get(x)).transform());
            MetadataProperties.setCoroutineMetadata(x, null);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsVars.JsVar x, JsContext<?> ctx) {
        FunctionWithWrapper functionWithWrapperTryExtractFunction;
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsExpression initExpression = x.getInitExpression();
        if (initExpression != null && (functionWithWrapperTryExtractFunction = InlineMetadata.INSTANCE.tryExtractFunction(initExpression)) != null) {
            this.functionName.put(functionWithWrapperTryExtractFunction.getFunction(), x.getName().getIdent());
        }
        return super.visit(x, ctx);
    }
}
