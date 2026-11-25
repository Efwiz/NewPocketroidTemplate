package org.jetbrains.kotlin.fir.expressions.builder;

import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.impl.FirEmptyExpressionBlock;

/* JADX INFO: compiled from: FirEmptyExpressionBlockBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0006\u0010\u0000\u001a\u00020\u0001¨\u0006\u0002"}, d2 = {"buildEmptyExpressionBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirEmptyExpressionBlockBuilderKt {
    public static final FirBlock buildEmptyExpressionBlock() {
        return new FirEmptyExpressionBlock();
    }
}
