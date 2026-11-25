package org.jetbrains.kotlin.psi;

import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public interface KtCallElement extends KtElement {
    KtExpression getCalleeExpression();

    List<KtLambdaArgument> getLambdaArguments();

    KtTypeArgumentList getTypeArgumentList();

    List<KtTypeProjection> getTypeArguments();

    KtValueArgumentList getValueArgumentList();

    List<? extends ValueArgument> getValueArguments();
}
