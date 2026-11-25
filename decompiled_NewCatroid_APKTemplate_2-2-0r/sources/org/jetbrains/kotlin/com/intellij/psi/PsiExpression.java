package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.NullableFunction;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiExpression extends PsiAnnotationMemberValue, PsiCaseLabelElement {
    public static final PsiExpression[] EMPTY_ARRAY = new PsiExpression[0];
    public static final ArrayFactory<PsiExpression> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiExpression$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiExpression.lambda$static$0(i);
        }
    };
    public static final Function<PsiExpression, PsiType> EXPRESSION_TO_TYPE = new NullableFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiExpression$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return ((PsiExpression) obj).getType();
        }
    };

    PsiType getType();

    static /* synthetic */ PsiExpression[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiExpression[i];
    }
}
