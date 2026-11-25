package org.jetbrains.kotlin.js.dce;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;

/* JADX INFO: compiled from: Eliminator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016J\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0018\u00010\u000eR\u00020\u000fH\u0002J\u001c\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00112\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0002J\u001c\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00132\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016J\u001c\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00142\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016J\u001c\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00152\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/Eliminator;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "analysisResult", "Lorg/jetbrains/kotlin/js/dce/AnalysisResult;", "(Lorg/jetbrains/kotlin/js/dce/AnalysisResult;)V", "endVisit", "", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "isUsed", "", "node", "Lorg/jetbrains/kotlin/js/dce/Context$Node;", "Lorg/jetbrains/kotlin/js/dce/Context;", "removeIfNecessary", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "visit", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;", "Lorg/jetbrains/kotlin/js/backend/ast/JsReturn;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars$JsVar;", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Eliminator extends JsVisitorWithContextImpl {
    private final AnalysisResult analysisResult;

    public Eliminator(AnalysisResult analysisResult) {
        Intrinsics.checkNotNullParameter(analysisResult, "analysisResult");
        this.analysisResult = analysisResult;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsVars.JsVar x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return removeIfNecessary(x, ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsExpressionStatement x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return removeIfNecessary(x, ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsReturn x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return removeIfNecessary(x, ctx);
    }

    private final boolean removeIfNecessary(JsNode x, JsContext<?> ctx) {
        if (this.analysisResult.getAstNodesToEliminate().contains(x)) {
            ctx.removeMe();
            return false;
        }
        Context.Node node = this.analysisResult.getNodeMap().get(x);
        if (isUsed(node != null ? node.getOriginal() : null)) {
            return true;
        }
        ctx.removeMe();
        return false;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsVars x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (x.getVars().isEmpty()) {
            ctx.removeMe();
        }
    }

    private final boolean isUsed(Context.Node node) {
        return node == null || node.getDeclarationReachable();
    }
}
