package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;

/* JADX INFO: compiled from: FirErrorExpressionRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirErrorExpressionOnlyErrorRenderer;", "Lorg/jetbrains/kotlin/fir/renderer/FirErrorExpressionRenderer;", "()V", "renderErrorExpression", "", "errorExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirErrorExpressionOnlyErrorRenderer extends FirErrorExpressionRenderer {
    @Override // org.jetbrains.kotlin.fir.renderer.FirErrorExpressionRenderer
    public void renderErrorExpression(FirErrorExpression errorExpression) {
        Intrinsics.checkNotNullParameter(errorExpression, "errorExpression");
        getPrinter().print("ERROR_EXPR(" + errorExpression.getDiagnostic().getReason() + ')');
    }
}
