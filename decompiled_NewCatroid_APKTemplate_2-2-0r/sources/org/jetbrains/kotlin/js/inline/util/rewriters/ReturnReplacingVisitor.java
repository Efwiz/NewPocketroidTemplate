package org.jetbrains.kotlin.js.inline.util.rewriters;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.coroutine.CoroutinePassesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: ReturnReplacingVisitor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B)\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012H\u0002J\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u0012H\u0002J\u001e\u0010\u0016\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J\u001e\u0010\u0016\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00172\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/util/rewriters/ReturnReplacingVisitor;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "resultRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "breakLabel", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "isSuspend", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;Z)V", "endVisit", "", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsReturn;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "getReturnReplacement", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "returnExpression", "processCoroutineResult", "expression", "visit", "Lorg/jetbrains/kotlin/js/backend/ast/JsObjectLiteral;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReturnReplacingVisitor extends JsVisitorWithContextImpl {
    private final JsNameRef breakLabel;
    private final JsFunction function;
    private final boolean isSuspend;
    private final JsNameRef resultRef;

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsFunction x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return false;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsObjectLiteral x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return false;
    }

    public ReturnReplacingVisitor(JsNameRef jsNameRef, JsNameRef jsNameRef2, JsFunction function, boolean z) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.resultRef = jsNameRef;
        this.breakLabel = jsNameRef2;
        this.function = function;
        this.isSuspend = z;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsReturn x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (MetadataProperties.getReturnTarget(x) == null || Intrinsics.areEqual(MetadataProperties.getFunctionDescriptor(this.function), MetadataProperties.getReturnTarget(x))) {
            ctx.removeMe();
            JsExpression returnReplacement = getReturnReplacement(x.getExpression());
            if (returnReplacement != null) {
                if (returnReplacement.getSource() == null) {
                    returnReplacement.setSource(x.getSource());
                }
                ctx.addNext(new JsExpressionStatement(returnReplacement));
            }
            if (this.breakLabel != null) {
                JsBreak jsBreak = new JsBreak(this.breakLabel);
                jsBreak.setSource(x.getSource());
                ctx.addNext(jsBreak);
            }
        }
    }

    private final JsExpression getReturnReplacement(JsExpression returnExpression) {
        JsBinaryOperation jsBinaryOperationAssignment = null;
        if (returnExpression != null) {
            JsNameRef jsNameRef = this.resultRef;
            if (jsNameRef != null) {
                JsExpression jsExpressionProcessCoroutineResult = processCoroutineResult(returnExpression);
                Intrinsics.checkNotNull(jsExpressionProcessCoroutineResult);
                jsBinaryOperationAssignment = JsAstUtils.assignment(jsNameRef, jsExpressionProcessCoroutineResult);
                Intrinsics.checkNotNull(jsBinaryOperationAssignment);
                MetadataProperties.setSynthetic(jsBinaryOperationAssignment, true);
            }
            return jsBinaryOperationAssignment != null ? jsBinaryOperationAssignment : processCoroutineResult(returnExpression);
        }
        return processCoroutineResult(null);
    }

    private final JsExpression processCoroutineResult(JsExpression expression) {
        if (!this.isSuspend || CoroutinePassesKt.isStateMachineResult(expression)) {
            return expression;
        }
        JsNameRef jsNameRef = new JsNameRef("$$coroutineResult$$", JsAstUtils.stateMachineReceiver());
        MetadataProperties.setCoroutineResult(jsNameRef, true);
        JsNameRef jsNameRef2 = jsNameRef;
        if (expression == null) {
            expression = Namer.getUndefinedExpression();
            Intrinsics.checkNotNullExpressionValue(expression, "getUndefinedExpression(...)");
        }
        return JsAstUtils.assignment(jsNameRef2, expression);
    }
}
