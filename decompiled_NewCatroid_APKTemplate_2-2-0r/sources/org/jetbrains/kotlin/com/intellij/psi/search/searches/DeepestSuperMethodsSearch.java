package org.jetbrains.kotlin.com.intellij.psi.search.searches;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.util.Query;
import org.jetbrains.kotlin.com.intellij.util.QueryExecutor;

/* JADX INFO: loaded from: classes3.dex */
public final class DeepestSuperMethodsSearch extends ExtensibleQueryFactory<PsiMethod, PsiMethod> {
    public static final ExtensionPointName<QueryExecutor<PsiMethod, PsiMethod>> EP_NAME = new ExtensionPointName<>("org.jetbrains.kotlin.com.intellij.deepestSuperMethodsSearch");
    private static final DeepestSuperMethodsSearch DEEPEST_SUPER_METHODS_SEARCH_INSTANCE = new DeepestSuperMethodsSearch();

    private DeepestSuperMethodsSearch() {
        super(EP_NAME);
    }

    public static Query<PsiMethod> search(PsiMethod psiMethod) {
        return DEEPEST_SUPER_METHODS_SEARCH_INSTANCE.createQuery(psiMethod);
    }
}
