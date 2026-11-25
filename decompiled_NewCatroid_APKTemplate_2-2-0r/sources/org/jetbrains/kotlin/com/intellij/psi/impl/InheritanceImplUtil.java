package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.JavaClassSupers;

/* JADX INFO: loaded from: classes3.dex */
public final class InheritanceImplUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) InheritanceImplUtil.class);

    /* JADX WARN: Code duplicated, block: B:14:0x002e  */
    /* JADX WARN: Code duplicated, block: B:15:0x0033  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "baseClass";
        } else if (i == 3) {
            objArr[0] = "manager";
        } else if (i != 20) {
            switch (i) {
                case 5:
                case 10:
                case 12:
                case 17:
                    objArr[0] = "baseClass";
                    break;
                case 6:
                    objArr[0] = "baseQName";
                    break;
                case 7:
                    objArr[0] = "facade";
                    break;
                case 8:
                case 11:
                case 15:
                    objArr[0] = "manager";
                    break;
                case 9:
                case 16:
                    objArr[0] = "aClass";
                    break;
                case 13:
                case 18:
                    objArr[0] = "checkedClasses";
                    break;
                case 14:
                    objArr[0] = "supers";
                    break;
                default:
                    objArr[0] = "candidateClass";
                    break;
            }
        } else {
            objArr[0] = "baseClass";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/InheritanceImplUtil";
        if (i != 2) {
            switch (i) {
                case 6:
                case 7:
                    objArr[2] = "checkReferenceListWithQualifiedNamesInClsClass";
                    break;
                case 8:
                case 9:
                case 10:
                    objArr[2] = "isInheritorWithoutCaching";
                    break;
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                    objArr[2] = "checkInheritor";
                    break;
                case 19:
                case 20:
                    objArr[2] = "isInheritorDeep";
                    break;
                default:
                    objArr[2] = "isInheritor";
                    break;
            }
        } else {
            objArr[2] = "hasObjectQualifiedName";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static boolean isInheritor(PsiClass psiClass, PsiClass psiClass2, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(1);
        }
        if ((psiClass2 instanceof PsiAnonymousClass) || psiClass2.getManager().areElementsEquivalent(psiClass2, psiClass)) {
            return false;
        }
        if (z) {
            return JavaClassSupers.getInstance().getSuperClassSubstitutor(psiClass2, psiClass, psiClass.getResolveScope(), PsiSubstitutor.EMPTY) != null;
        }
        return isInheritor(psiClass.getManager(), psiClass, psiClass2, false, null);
    }

    static boolean hasObjectQualifiedName(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        if (!CommonClassNames.JAVA_LANG_OBJECT_SHORT.equals(psiClass.getMyName())) {
            return false;
        }
        PsiElement parent = psiClass.getMyParent();
        return (parent instanceof PsiJavaFile) && CommonClassNames.DEFAULT_PACKAGE.equals(((PsiJavaFile) parent).getPackageName());
    }

    private static boolean isInheritor(PsiManager psiManager, PsiClass psiClass, PsiClass psiClass2, boolean z, Set<PsiClass> set) {
        if (psiManager == null) {
            $$$reportNull$$$0(3);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(4);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(5);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            PsiClass psiClassResolve = ((PsiAnonymousClass) psiClass).getBaseClassType().resolve();
            if (psiClassResolve == null) {
                return false;
            }
            if (!z) {
                return psiManager.areElementsEquivalent(psiClassResolve, psiClass2);
            }
            return InheritanceUtil.isInheritorOrSelf(psiClassResolve, psiClass2, true);
        }
        if (z) {
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("Using uncached version for " + psiClass.getQualifiedName() + " and " + psiClass2);
            }
        }
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(psiManager.getProject());
        if (hasObjectQualifiedName(psiClass2)) {
            PsiElement psiElementFindClass = javaPsiFacade.findClass(CommonClassNames.JAVA_LANG_OBJECT, psiClass.getResolveScope());
            if (psiManager.areElementsEquivalent(psiClass2, psiElementFindClass)) {
                if (psiManager.areElementsEquivalent(psiClass, psiElementFindClass)) {
                    return false;
                }
                if (z || psiClass.isInterface()) {
                    return true;
                }
                return psiManager.areElementsEquivalent(psiClass.getSuperClass(), psiElementFindClass);
            }
        }
        if (!z) {
            if (psiClass instanceof PsiCompiledElement) {
                String qualifiedName = psiClass2.getQualifiedName();
                if (qualifiedName == null) {
                    return false;
                }
                if (CommonClassNames.JAVA_LANG_ENUM.equals(qualifiedName) && psiClass.isEnum()) {
                    return javaPsiFacade.findClass(qualifiedName, psiClass.getResolveScope()) != null;
                }
                if (CommonClassNames.JAVA_LANG_ANNOTATION_ANNOTATION.equals(qualifiedName) && psiClass.isAnnotationType()) {
                    return javaPsiFacade.findClass(qualifiedName, psiClass.getResolveScope()) != null;
                }
                boolean zIsInterface = psiClass.isInterface();
                boolean zIsInterface2 = psiClass2.isInterface();
                if (zIsInterface == zIsInterface2 && checkReferenceListWithQualifiedNamesInClsClass(qualifiedName, psiClass.getExtendsList(), javaPsiFacade)) {
                    return true;
                }
                return zIsInterface2 && !zIsInterface && checkReferenceListWithQualifiedNamesInClsClass(qualifiedName, psiClass.getImplementsList(), javaPsiFacade);
            }
            boolean zIsInterface3 = psiClass.isInterface();
            boolean zIsInterface4 = psiClass2.isInterface();
            String name2 = psiClass2.getMyName();
            if (zIsInterface3 == zIsInterface4) {
                return PsiClassImplUtil.isInExtendsList(psiClass, psiClass2, name2, psiManager);
            }
            if (zIsInterface3) {
                return false;
            }
            return PsiClassImplUtil.isInReferenceList(psiClass.getImplementsList(), psiClass2, name2, psiManager);
        }
        return isInheritorWithoutCaching(psiManager, psiClass, psiClass2, set);
    }

    private static boolean checkReferenceListWithQualifiedNamesInClsClass(String str, PsiReferenceList psiReferenceList, JavaPsiFacade javaPsiFacade) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (javaPsiFacade == null) {
            $$$reportNull$$$0(7);
        }
        if (psiReferenceList != null) {
            PsiJavaCodeReferenceElement[] referenceElements = psiReferenceList.getReferenceElements();
            if (referenceElements.length != 0) {
                GlobalSearchScope resolveScope = psiReferenceList.getResolveScope();
                for (PsiJavaCodeReferenceElement psiJavaCodeReferenceElement : referenceElements) {
                    if (Objects.equals(PsiNameHelper.getQualifiedClassName(psiJavaCodeReferenceElement.getQualifiedName(), false), str) && javaPsiFacade.findClass(str, resolveScope) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isInheritorWithoutCaching(PsiManager psiManager, PsiClass psiClass, PsiClass psiClass2, Set<PsiClass> set) {
        if (psiManager == null) {
            $$$reportNull$$$0(8);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(9);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(10);
        }
        if (psiManager.areElementsEquivalent(psiClass, psiClass2)) {
            return false;
        }
        if (psiClass.isInterface() && !psiClass2.isInterface()) {
            return false;
        }
        if (set == null) {
            set = new HashSet<>();
        }
        set.add(psiClass);
        return checkInheritor(psiManager, psiClass.getExtendsListTypes(), psiClass2, set) || checkInheritor(psiManager, psiClass.getImplementsListTypes(), psiClass2, set);
    }

    private static boolean checkInheritor(PsiManager psiManager, PsiClassType[] psiClassTypeArr, PsiClass psiClass, Set<PsiClass> set) {
        if (psiManager == null) {
            $$$reportNull$$$0(11);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(12);
        }
        if (set == null) {
            $$$reportNull$$$0(13);
        }
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(14);
        }
        for (PsiClassType psiClassType : psiClassTypeArr) {
            PsiClass psiClassResolve = psiClassType.resolve();
            if (psiClassResolve != null && checkInheritor(psiManager, psiClassResolve, psiClass, set)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkInheritor(PsiManager psiManager, PsiClass psiClass, PsiClass psiClass2, Set<PsiClass> set) {
        if (psiManager == null) {
            $$$reportNull$$$0(15);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(16);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(17);
        }
        if (set == null) {
            $$$reportNull$$$0(18);
        }
        ProgressIndicatorProvider.checkCanceled();
        if (psiManager.areElementsEquivalent(psiClass2, psiClass)) {
            return true;
        }
        if (set.contains(psiClass)) {
            return false;
        }
        return isInheritor(psiManager, psiClass, psiClass2, true, set);
    }

    public static boolean isInheritorDeep(PsiClass psiClass, PsiClass psiClass2, PsiClass psiClass3) {
        HashSet hashSet;
        if (psiClass == null) {
            $$$reportNull$$$0(19);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(20);
        }
        if (psiClass2 instanceof PsiAnonymousClass) {
            return false;
        }
        if (psiClass3 != null) {
            hashSet = new HashSet();
            hashSet.add(psiClass3);
        } else {
            hashSet = null;
        }
        return isInheritor(psiClass.getManager(), psiClass, psiClass2, true, hashSet);
    }
}
