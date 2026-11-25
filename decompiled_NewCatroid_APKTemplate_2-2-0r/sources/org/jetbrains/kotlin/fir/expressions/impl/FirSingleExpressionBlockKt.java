package org.jetbrains.kotlin.fir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirStatement;

/* JADX INFO: compiled from: FirSingleExpressionBlock.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0086\b¨\u0006\u0004"}, d2 = {"buildSingleExpressionBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "statement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirSingleExpressionBlockKt {
    public static final FirBlock buildSingleExpressionBlock(FirStatement statement) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        return new FirSingleExpressionBlock(statement);
    }
}
