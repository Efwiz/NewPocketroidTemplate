package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaPsiFacade {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "project";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/JavaPsiFacade";
        if (i != 1) {
            objArr[2] = "getInstance";
        } else {
            objArr[2] = "getElementFactory";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract boolean arePackagesTheSame(PsiElement psiElement, PsiElement psiElement2);

    public abstract PsiClass findClass(String str, GlobalSearchScope globalSearchScope);

    public abstract PsiClass[] findClasses(String str, GlobalSearchScope globalSearchScope);

    public abstract PsiJavaModule findModule(String str, GlobalSearchScope globalSearchScope);

    public abstract Collection<PsiJavaModule> findModules(String str, GlobalSearchScope globalSearchScope);

    public abstract PsiPackage findPackage(String str);

    public abstract PsiConstantEvaluationHelper getConstantEvaluationHelper();

    public abstract PsiElementFactory getElementFactory();

    @Deprecated
    public abstract PsiNameHelper getNameHelper();

    public abstract PsiJavaParserFacade getParserFacade();

    public abstract Project getProject();

    public abstract PsiResolveHelper getResolveHelper();

    public abstract boolean isConstantExpression(PsiExpression psiExpression);

    public abstract boolean isInPackage(PsiElement psiElement, PsiPackage psiPackage);

    public abstract boolean isPartOfPackagePrefix(String str);

    public static JavaPsiFacade getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return (JavaPsiFacade) project.getService(JavaPsiFacade.class);
    }

    public static PsiElementFactory getElementFactory(Project project) {
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        return getInstance(project).getElementFactory();
    }
}
