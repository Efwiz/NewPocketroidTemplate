package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes6.dex */
public interface KtTypeElement extends KtElement {
    public static final KtTypeElement[] EMPTY_ARRAY = new KtTypeElement[0];
    public static final ArrayFactory<KtTypeElement> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.psi.KtTypeElement$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return KtTypeElement.lambda$static$0(i);
        }
    };

    List<KtTypeReference> getTypeArgumentsAsTypes();

    static /* synthetic */ KtTypeElement[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new KtTypeElement[i];
    }
}
