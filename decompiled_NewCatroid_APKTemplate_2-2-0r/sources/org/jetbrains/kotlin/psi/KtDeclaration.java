package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.kdoc.psi.api.KDoc;

/* JADX INFO: loaded from: classes6.dex */
public interface KtDeclaration extends KtExpression, KtModifierListOwner {
    public static final KtDeclaration[] EMPTY_ARRAY = new KtDeclaration[0];
    public static final ArrayFactory<KtDeclaration> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.psi.KtDeclaration$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return KtDeclaration.lambda$static$0(i);
        }
    };

    KDoc getDocComment();

    static /* synthetic */ KtDeclaration[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new KtDeclaration[i];
    }
}
