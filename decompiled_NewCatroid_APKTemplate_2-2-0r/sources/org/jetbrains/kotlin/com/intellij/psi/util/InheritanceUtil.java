package org.jetbrains.kotlin.com.intellij.psi.util;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.util.Processor;

/* JADX INFO: loaded from: classes3.dex */
public final class InheritanceUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 7:
            case 9:
            case 14:
            case 15:
            case 16:
            case 19:
            case 21:
                objArr[0] = "aClass";
                break;
            case 2:
            default:
                objArr[0] = "superProcessor";
                break;
            case 3:
            case 11:
                objArr[0] = "visited";
                break;
            case 4:
            case 5:
            case 6:
                objArr[0] = "baseClassName";
                break;
            case 8:
            case 10:
                objArr[0] = "results";
                break;
            case 12:
                objArr[0] = "manager";
                break;
            case 13:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 17:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 18:
                objArr[0] = "processor";
                break;
            case 20:
                objArr[0] = "usedClasses";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/InheritanceUtil";
        switch (i) {
            case 4:
            case 5:
            case 6:
                objArr[2] = "isInheritor";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "getSuperClasses";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[2] = "getSuperClassesOfList";
                break;
            case 14:
            case 15:
                objArr[2] = "hasEnclosingInstanceInScope";
                break;
            case 16:
                objArr[2] = "findEnclosingInstanceInScope";
                break;
            case 17:
            case 18:
                objArr[2] = "processSuperTypes";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "getCircularClass";
                break;
            default:
                objArr[2] = "processSupers";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static /* synthetic */ boolean lambda$hasEnclosingInstanceInScope$0(boolean z, PsiClass psiClass) {
        return z;
    }

    private InheritanceUtil() {
    }

    public static boolean isInheritorOrSelf(PsiClass psiClass, PsiClass psiClass2, boolean z) {
        if (psiClass == null || psiClass2 == null) {
            return false;
        }
        return psiClass.getManager().areElementsEquivalent(psiClass2, psiClass) || psiClass.isInheritor(psiClass2, z);
    }

    public static boolean processSupers(PsiClass psiClass, boolean z, Processor<? super PsiClass> processor) {
        if (processor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiClass == null) {
            return true;
        }
        if (!z || processor.process(psiClass)) {
            return processSupers(psiClass, processor, new HashSet());
        }
        return false;
    }

    private static boolean processSupers(PsiClass psiClass, Processor<? super PsiClass> processor, Set<? super PsiClass> set) {
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        if (processor == null) {
            $$$reportNull$$$0(2);
        }
        if (set == null) {
            $$$reportNull$$$0(3);
        }
        if (!set.add(psiClass)) {
            return true;
        }
        for (PsiClass psiClass2 : psiClass.getInterfaces()) {
            if (!processor.process(psiClass2) || !processSupers(psiClass2, processor, set)) {
                return false;
            }
        }
        PsiClass superClass = psiClass.getSuperClass();
        return superClass == null || (processor.process(superClass) && processSupers(superClass, processor, set));
    }

    public static boolean isInheritor(PsiType psiType, String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (psiType instanceof PsiClassType) {
            PsiUtil.ensureValidType(psiType);
            return isInheritor(((PsiClassType) psiType).resolve(), str);
        }
        if (psiType instanceof PsiIntersectionType) {
            for (PsiType psiType2 : ((PsiIntersectionType) psiType).getConjuncts()) {
                if (isInheritor(psiType2, str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInheritor(PsiClass psiClass, String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return isInheritor(psiClass, false, str);
    }

    public static boolean isInheritor(PsiClass psiClass, boolean z, String str) {
        PsiClass psiClassFindClass;
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (psiClass == null || (psiClassFindClass = JavaPsiFacade.getInstance(psiClass.getProject()).findClass(str, psiClass.getResolveScope())) == null) {
            return false;
        }
        return z ? psiClass.isInheritor(psiClassFindClass, true) : isInheritorOrSelf(psiClass, psiClassFindClass, true);
    }

    public static void getSuperClasses(PsiClass psiClass, Set<? super PsiClass> set, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(7);
        }
        if (set == null) {
            $$$reportNull$$$0(8);
        }
        getSuperClassesOfList(psiClass.getSuperTypes(), set, z, new HashSet(), psiClass.getManager());
    }

    public static LinkedHashSet<PsiClass> getSuperClasses(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(9);
        }
        LinkedHashSet<PsiClass> linkedHashSet = new LinkedHashSet<>();
        getSuperClasses(psiClass, linkedHashSet, true);
        return linkedHashSet;
    }

    private static void getSuperClassesOfList(PsiClassType[] psiClassTypeArr, Set<? super PsiClass> set, boolean z, Set<? super PsiClass> set2, PsiManager psiManager) {
        if (set == null) {
            $$$reportNull$$$0(10);
        }
        if (set2 == null) {
            $$$reportNull$$$0(11);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(12);
        }
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(13);
        }
        for (PsiClassType psiClassType : psiClassTypeArr) {
            PsiClass psiClassResolve = psiClassType.resolve();
            if (psiClassResolve != null && set2.add(psiClassResolve)) {
                if (z || psiManager.isInProject(psiClassResolve)) {
                    set.add(psiClassResolve);
                }
                getSuperClassesOfList(psiClassResolve.getSuperTypes(), set, z, set2, psiManager);
            }
        }
    }

    public static boolean hasEnclosingInstanceInScope(PsiClass psiClass, PsiElement psiElement, final boolean z, boolean z2) {
        if (psiClass == null) {
            $$$reportNull$$$0(14);
        }
        return hasEnclosingInstanceInScope(psiClass, psiElement, (Condition<? super PsiClass>) new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return InheritanceUtil.lambda$hasEnclosingInstanceInScope$0(z, (PsiClass) obj);
            }
        }, z2);
    }

    public static boolean hasEnclosingInstanceInScope(PsiClass psiClass, PsiElement psiElement, Condition<? super PsiClass> condition, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(15);
        }
        return findEnclosingInstanceInScope(psiClass, psiElement, condition, z) != null;
    }

    public static PsiClass findEnclosingInstanceInScope(PsiClass psiClass, PsiElement psiElement, Condition<? super PsiClass> condition, boolean z) {
        PsiModifierList psiModifierListMo6095getModifierList;
        if (psiClass == null) {
            $$$reportNull$$$0(16);
        }
        PsiManager manager = psiClass.getManager();
        while (psiElement != null && !(psiElement instanceof PsiFile)) {
            if (psiElement instanceof PsiClass) {
                PsiClass psiClass2 = (PsiClass) psiElement;
                if (condition.value(psiClass2)) {
                    if (isInheritorOrSelf(psiClass2, psiClass, true)) {
                        return psiClass2;
                    }
                } else if (manager.areElementsEquivalent(psiElement, psiClass)) {
                    return psiClass;
                }
                if (z && (psiElement instanceof PsiTypeParameter)) {
                    return psiClass2;
                }
            }
            if ((psiElement instanceof PsiModifierListOwner) && (psiModifierListMo6095getModifierList = ((PsiModifierListOwner) psiElement).mo6095getModifierList()) != null && psiModifierListMo6095getModifierList.hasModifierProperty("static")) {
                return null;
            }
            psiElement = psiElement.getLightParent();
        }
        return null;
    }

    public static boolean processSuperTypes(PsiType psiType, boolean z, Processor<? super PsiType> processor) {
        if (psiType == null) {
            $$$reportNull$$$0(17);
        }
        if (processor == null) {
            $$$reportNull$$$0(18);
        }
        if (!z || processor.process(psiType)) {
            return processSuperTypes(psiType, processor, new HashSet());
        }
        return false;
    }

    private static boolean processSuperTypes(PsiType psiType, Processor<? super PsiType> processor, Set<? super PsiType> set) {
        if (!set.add(psiType)) {
            return true;
        }
        for (PsiType psiType2 : psiType.getSuperTypes()) {
            if (!processor.process(psiType2)) {
                return false;
            }
            processSuperTypes(psiType2, processor, set);
        }
        return true;
    }

    private static PsiClass getCircularClass(PsiClass psiClass, Collection<? super PsiClass> collection) {
        PsiClass circularClass;
        if (psiClass == null) {
            $$$reportNull$$$0(19);
        }
        if (collection == null) {
            $$$reportNull$$$0(20);
        }
        if (collection.contains(psiClass)) {
            return psiClass;
        }
        try {
            collection.add(psiClass);
            PsiElement[] supers = psiClass.getSupers();
            int length = supers.length;
            for (int i = 0; i < length; i++) {
                for (PsiElement lightParent = supers[i]; lightParent instanceof PsiClass; lightParent = lightParent.getLightParent()) {
                    if (!CommonClassNames.JAVA_LANG_OBJECT.equals(((PsiClass) lightParent).getQualifiedName()) && (circularClass = getCircularClass((PsiClass) lightParent, collection)) != null) {
                        return circularClass;
                    }
                }
            }
            return null;
        } finally {
            collection.remove(psiClass);
        }
    }

    public static PsiClass getCircularClass(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(21);
        }
        return getCircularClass(psiClass, new HashSet());
    }
}
