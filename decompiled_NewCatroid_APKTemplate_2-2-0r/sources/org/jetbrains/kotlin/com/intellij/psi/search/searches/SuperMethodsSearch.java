package org.jetbrains.kotlin.com.intellij.psi.search.searches;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.util.Query;
import org.jetbrains.kotlin.com.intellij.util.QueryExecutor;
import org.jetbrains.kotlin.com.intellij.util.QueryParameters;
import org.jetbrains.kotlin.com.intellij.util.UniqueResultsQuery;
import org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy;

/* JADX INFO: loaded from: classes3.dex */
public final class SuperMethodsSearch extends ExtensibleQueryFactory<MethodSignatureBackedByPsiMethod, SearchParameters> {
    public static final ExtensionPointName<QueryExecutor> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.superMethodsSearch");
    private static final SuperMethodsSearch SUPER_METHODS_SEARCH_INSTANCE = new SuperMethodsSearch();
    private static final HashingStrategy<MethodSignatureBackedByPsiMethod> METHOD_BASED_HASHING_STRATEGY = new HashingStrategy<MethodSignatureBackedByPsiMethod>() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.searches.SuperMethodsSearch.1
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public int hashCode(MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethod) {
            if (methodSignatureBackedByPsiMethod == null) {
                return 0;
            }
            return methodSignatureBackedByPsiMethod.getMethod().hashCode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public boolean equals(MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethod, MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethod2) {
            return methodSignatureBackedByPsiMethod == methodSignatureBackedByPsiMethod2 || !(methodSignatureBackedByPsiMethod == null || methodSignatureBackedByPsiMethod2 == null || !methodSignatureBackedByPsiMethod.getMethod().equals(methodSignatureBackedByPsiMethod2.getMethod()));
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "derivedMethod";
        } else {
            objArr[0] = "parameters";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/searches/SuperMethodsSearch";
        objArr[2] = FirebaseAnalytics.Event.SEARCH;
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static class SearchParameters implements QueryParameters {
        private final boolean myAllowStaticMethod;
        private final boolean myCheckBases;
        private final PsiClass myClass;
        private final boolean myJlsOnly;
        private final PsiMethod myMethod;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i != 2) {
                objArr[0] = "method";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/searches/SuperMethodsSearch$SearchParameters";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/searches/SuperMethodsSearch$SearchParameters";
            } else {
                objArr[1] = "getMethod";
            }
            if (i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public SearchParameters(PsiMethod psiMethod, PsiClass psiClass, boolean z, boolean z2) {
            this(psiMethod, psiClass, z, z2, false);
            if (psiMethod == null) {
                $$$reportNull$$$0(0);
            }
        }

        public SearchParameters(PsiMethod psiMethod, PsiClass psiClass, boolean z, boolean z2, boolean z3) {
            if (psiMethod == null) {
                $$$reportNull$$$0(1);
            }
            this.myCheckBases = z;
            this.myClass = psiClass;
            this.myMethod = psiMethod;
            this.myAllowStaticMethod = z2;
            this.myJlsOnly = z3;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.QueryParameters
        public Project getProject() {
            return this.myMethod.getProject();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.QueryParameters
        public boolean isQueryValid() {
            PsiClass psiClass;
            return this.myMethod.isValid() && ((psiClass = this.myClass) == null || psiClass.isValid());
        }

        public final boolean isCheckBases() {
            return this.myCheckBases;
        }

        public final PsiMethod getMethod() {
            PsiMethod psiMethod = this.myMethod;
            if (psiMethod == null) {
                $$$reportNull$$$0(2);
            }
            return psiMethod;
        }

        public final PsiClass getPsiClass() {
            return this.myClass;
        }

        public final boolean isAllowStaticMethod() {
            return this.myAllowStaticMethod;
        }

        public boolean isJlsOnly() {
            return this.myJlsOnly;
        }
    }

    private SuperMethodsSearch() {
    }

    public static Query<MethodSignatureBackedByPsiMethod> search(PsiMethod psiMethod, PsiClass psiClass, boolean z, boolean z2) {
        if (psiMethod == null) {
            $$$reportNull$$$0(0);
        }
        return search(new SearchParameters(psiMethod, psiClass, z, z2));
    }

    public static Query<MethodSignatureBackedByPsiMethod> search(SearchParameters searchParameters) {
        if (searchParameters == null) {
            $$$reportNull$$$0(1);
        }
        return new UniqueResultsQuery(SUPER_METHODS_SEARCH_INSTANCE.createQuery(searchParameters), METHOD_BASED_HASHING_STRATEGY);
    }
}
