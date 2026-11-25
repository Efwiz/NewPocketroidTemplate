package org.jetbrains.kotlin.fir.builder;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirLazyExpressionBuilder;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: PsiRawFirBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class PsiRawFirBuilder$Visitor$buildOrLazyExpression$1 extends Lambda implements Function0<FirExpression> {
    final /* synthetic */ KtSourceElement $sourceElement;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiRawFirBuilder$Visitor$buildOrLazyExpression$1(KtSourceElement ktSourceElement) {
        super(0);
        this.$sourceElement = ktSourceElement;
    }

    @Override // kotlin.jvm.functions.Function0
    public final FirExpression invoke() {
        KtSourceElement ktSourceElement = this.$sourceElement;
        FirLazyExpressionBuilder firLazyExpressionBuilder = new FirLazyExpressionBuilder();
        firLazyExpressionBuilder.setSource(ktSourceElement);
        return firLazyExpressionBuilder.build();
    }
}
