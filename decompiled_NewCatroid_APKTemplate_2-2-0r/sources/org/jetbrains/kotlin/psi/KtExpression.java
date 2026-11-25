package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes6.dex */
public interface KtExpression extends KtElement {
    public static final KtExpression[] EMPTY_ARRAY = new KtExpression[0];
    public static final ArrayFactory<KtExpression> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.psi.KtExpression$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return KtExpression.lambda$static$0(i);
        }
    };

    <R, D> R accept(KtVisitor<R, D> ktVisitor, D d);

    static /* synthetic */ KtExpression[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new KtExpression[i];
    }
}
