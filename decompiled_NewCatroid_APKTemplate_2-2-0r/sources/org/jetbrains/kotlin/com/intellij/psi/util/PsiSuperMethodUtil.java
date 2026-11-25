package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiSuperMethodUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "superMethod";
                break;
            case 2:
                objArr[0] = "superClass";
                break;
            case 3:
                objArr[0] = "superSubstitutor";
                break;
            case 4:
                objArr[0] = "derivedSubstitutor";
                break;
            case 5:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiSuperMethodUtil";
                break;
            case 6:
                objArr[0] = "aClass";
                break;
            case 8:
                objArr[0] = "psiClass";
                break;
            case 9:
                objArr[0] = "resolveScope";
                break;
            default:
                objArr[0] = "method";
                break;
        }
        if (i == 5) {
            objArr[1] = "obtainFinalSubstitutor";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiSuperMethodUtil";
        } else {
            objArr[1] = "collectOverrideEquivalents";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
                objArr[2] = "obtainFinalSubstitutor";
                break;
            case 5:
            case 7:
                break;
            case 6:
                objArr[2] = "collectOverrideEquivalents";
                break;
            case 8:
            case 9:
                objArr[2] = "correctClassByScope";
                break;
            default:
                objArr[2] = "isSuperMethod";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private PsiSuperMethodUtil() {
    }

    public static boolean isSuperMethod(PsiMethod psiMethod, PsiMethod psiMethod2) {
        if (psiMethod == null) {
            $$$reportNull$$$0(0);
        }
        if (psiMethod2 == null) {
            $$$reportNull$$$0(1);
        }
        Iterator<HierarchicalMethodSignature> it = psiMethod.getHierarchicalMethodSignature().getSuperSignatures().iterator();
        while (it.hasNext()) {
            PsiMethod method = it.next().getMethod();
            if (psiMethod2.equals(method) || isSuperMethod(method, psiMethod2)) {
                return true;
            }
        }
        return false;
    }

    public static PsiSubstitutor obtainFinalSubstitutor(PsiClass psiClass, PsiSubstitutor psiSubstitutor, PsiSubstitutor psiSubstitutor2, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiSubstitutor2 == null) {
            $$$reportNull$$$0(4);
        }
        if (z) {
            psiSubstitutor = JavaPsiFacade.getElementFactory(psiClass.getProject()).createRawSubstitutor(psiSubstitutor2, (PsiTypeParameter[]) psiSubstitutor.getSubstitutionMap().keySet().toArray(PsiTypeParameter.EMPTY_ARRAY));
        }
        HashMap map = null;
        for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(psiClass)) {
            PsiType psiTypeSubstitute = psiSubstitutor2.substitute(psiSubstitutor.substitute(psiTypeParameter));
            if (map == null) {
                map = new HashMap();
            }
            map.put(psiTypeParameter, psiTypeSubstitute);
        }
        PsiSubstitutor psiSubstitutorCreateSubstitutor = map == null ? PsiSubstitutor.EMPTY : JavaPsiFacade.getElementFactory(psiClass.getProject()).createSubstitutor(map);
        if (psiSubstitutorCreateSubstitutor == null) {
            $$$reportNull$$$0(5);
        }
        return psiSubstitutorCreateSubstitutor;
    }

    public static Map<MethodSignature, Set<PsiMethod>> collectOverrideEquivalents(PsiClass psiClass) {
        PsiClass containingClass;
        PsiClass psiClassCorrectClassByScope;
        PsiMethod psiMethodFindMethodBySignature;
        PsiSubstitutor classSubstitutor;
        if (psiClass == null) {
            $$$reportNull$$$0(6);
        }
        Map<MethodSignature, Set<PsiMethod>> mapCreateErasedMethodSignatureMap = MethodSignatureUtil.createErasedMethodSignatureMap();
        GlobalSearchScope resolveScope = psiClass.getResolveScope();
        PsiClass[] supers = psiClass.getSupers();
        for (int i = 0; i < supers.length; i++) {
            PsiClass psiClass2 = supers[i];
            boolean zIsInheritor = false;
            for (int i2 = 0; i2 < supers.length; i2++) {
                if (i2 != i) {
                    zIsInheritor |= supers[i2].isInheritor(supers[i], true);
                }
            }
            if (!zIsInheritor) {
                PsiSubstitutor superClassSubstitutor = TypeConversionUtil.getSuperClassSubstitutor(psiClass2, psiClass, PsiSubstitutor.EMPTY);
                for (HierarchicalMethodSignature hierarchicalMethodSignature : psiClass2.getVisibleSignatures()) {
                    PsiMethod method = hierarchicalMethodSignature.getMethod();
                    if (MethodSignatureUtil.findMethodBySignature(psiClass, method.getSignature(superClassSubstitutor), false) == null && (containingClass = method.mo6092getContainingClass()) != null && (psiClassCorrectClassByScope = correctClassByScope(containingClass, resolveScope)) != null && (psiMethodFindMethodBySignature = psiClassCorrectClassByScope.findMethodBySignature(method, false)) != null && (classSubstitutor = TypeConversionUtil.getClassSubstitutor(psiClassCorrectClassByScope, psiClass, PsiSubstitutor.EMPTY)) != null) {
                        mapCreateErasedMethodSignatureMap.computeIfAbsent(MethodSignatureBackedByPsiMethod.create(psiMethodFindMethodBySignature, obtainFinalSubstitutor(psiClassCorrectClassByScope, classSubstitutor, hierarchicalMethodSignature.getSubstitutor(), false), false), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiSuperMethodUtil$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return PsiSuperMethodUtil.lambda$collectOverrideEquivalents$0((MethodSignature) obj);
                            }
                        }).add(psiMethodFindMethodBySignature);
                    }
                }
            }
        }
        if (mapCreateErasedMethodSignatureMap == null) {
            $$$reportNull$$$0(7);
        }
        return mapCreateErasedMethodSignatureMap;
    }

    static /* synthetic */ Set lambda$collectOverrideEquivalents$0(MethodSignature methodSignature) {
        return new LinkedHashSet();
    }

    public static PsiClass correctClassByScope(PsiClass psiClass, GlobalSearchScope globalSearchScope) {
        PsiFile containingFile;
        VirtualFile virtualFile;
        Module moduleForFile;
        if (psiClass == null) {
            $$$reportNull$$$0(8);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(9);
        }
        String qualifiedName = psiClass.getQualifiedName();
        if (qualifiedName == null || (containingFile = psiClass.getContainingFile()) == null || !containingFile.getViewProvider().isPhysical() || (virtualFile = containingFile.getVirtualFile()) == null) {
            return psiClass;
        }
        FileIndexFacade fileIndexFacade = FileIndexFacade.getInstance(containingFile.getProject());
        if (!fileIndexFacade.isInSource(virtualFile) && !fileIndexFacade.isInLibrarySource(virtualFile) && !fileIndexFacade.isInLibraryClasses(virtualFile)) {
            return psiClass;
        }
        PsiClass psiClassFindClass = JavaPsiFacade.getInstance(psiClass.getProject()).findClass(qualifiedName, globalSearchScope);
        VirtualFile virtualFile2 = PsiUtilCore.getVirtualFile(psiClassFindClass);
        return (virtualFile2 == null || (moduleForFile = fileIndexFacade.getModuleForFile(virtualFile)) == null || moduleForFile != fileIndexFacade.getModuleForFile(virtualFile2)) ? psiClassFindClass : psiClass;
    }
}
