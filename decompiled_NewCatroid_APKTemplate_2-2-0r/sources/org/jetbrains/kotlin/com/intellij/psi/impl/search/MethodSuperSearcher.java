package org.jetbrains.kotlin.com.intellij.psi.impl.search;

import org.jetbrains.kotlin.com.intellij.openapi.application.QueryExecutorBase;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.search.searches.SuperMethodsSearch;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureUtil;
import org.jetbrains.kotlin.com.intellij.util.Processor;

/* JADX INFO: loaded from: classes3.dex */
public class MethodSuperSearcher extends QueryExecutorBase<MethodSignatureBackedByPsiMethod, SuperMethodsSearch.SearchParameters> {
    private static final Logger LOG = Logger.getInstance((Class<?>) MethodSuperSearcher.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "queryParameters";
        } else {
            objArr[0] = "consumer";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/search/MethodSuperSearcher";
        objArr[2] = "processQuery";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public MethodSuperSearcher() {
        super(true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.QueryExecutorBase
    public void processQuery(SuperMethodsSearch.SearchParameters searchParameters, Processor<? super MethodSignatureBackedByPsiMethod> processor) {
        if (searchParameters == null) {
            $$$reportNull$$$0(0);
        }
        if (processor == null) {
            $$$reportNull$$$0(1);
        }
        PsiClass psiClass = searchParameters.getPsiClass();
        PsiMethod method = searchParameters.getMethod();
        HierarchicalMethodSignature hierarchicalMethodSignature = method.getHierarchicalMethodSignature();
        boolean zIsCheckBases = searchParameters.isCheckBases();
        boolean zIsAllowStaticMethod = searchParameters.isAllowStaticMethod();
        for (HierarchicalMethodSignature hierarchicalMethodSignature2 : hierarchicalMethodSignature.getSuperSignatures()) {
            if (MethodSignatureUtil.isSubsignature(hierarchicalMethodSignature2, hierarchicalMethodSignature) && !addSuperMethods(hierarchicalMethodSignature2, method, psiClass, zIsAllowStaticMethod, zIsCheckBases, processor)) {
                return;
            }
        }
    }

    private static boolean addSuperMethods(HierarchicalMethodSignature hierarchicalMethodSignature, PsiMethod psiMethod, PsiClass psiClass, boolean z, boolean z2, Processor<? super MethodSignatureBackedByPsiMethod> processor) {
        PsiMethod method = hierarchicalMethodSignature.getMethod();
        PsiClass containingClass = method.mo6092getContainingClass();
        if ((psiClass == null || InheritanceUtil.isInheritorOrSelf(psiClass, containingClass, true)) && isAcceptable(method, psiMethod, z)) {
            if (psiClass != null && !psiClass.equals(containingClass) && !z2) {
                return true;
            }
            LOG.assertTrue(method != psiMethod, psiMethod);
            return processor.process(hierarchicalMethodSignature);
        }
        for (HierarchicalMethodSignature hierarchicalMethodSignature2 : hierarchicalMethodSignature.getSuperSignatures()) {
            if (MethodSignatureUtil.isSubsignature(hierarchicalMethodSignature2, hierarchicalMethodSignature)) {
                addSuperMethods(hierarchicalMethodSignature2, psiMethod, psiClass, z, z2, processor);
            }
        }
        return true;
    }

    private static boolean isAcceptable(PsiMethod psiMethod, PsiMethod psiMethod2, boolean z) {
        boolean zHasModifierProperty = psiMethod.hasModifierProperty("static");
        return zHasModifierProperty == psiMethod2.hasModifierProperty("static") && (z || !zHasModifierProperty) && JavaPsiFacade.getInstance(psiMethod2.getProject()).getResolveHelper().isAccessible(psiMethod, psiMethod2, null);
    }
}
