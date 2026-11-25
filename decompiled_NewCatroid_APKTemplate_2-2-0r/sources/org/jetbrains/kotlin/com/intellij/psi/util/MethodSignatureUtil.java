package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy;

/* JADX INFO: loaded from: classes3.dex */
public final class MethodSignatureUtil {
    private static final HashingStrategy<MethodSignature> METHOD_PARAMETERS_ERASURE_STRATEGY = new HashingStrategy<MethodSignature>() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureUtil.1
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public int hashCode(MethodSignature methodSignature) {
            if (methodSignature == null) {
                return 0;
            }
            return methodSignature.hashCode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public boolean equals(MethodSignature methodSignature, MethodSignature methodSignature2) {
            return methodSignature == methodSignature2 || (methodSignature != null && methodSignature2 != null && MethodSignatureUtil.areSignaturesEqualLightweight(methodSignature, methodSignature2) && MethodSignatureUtil.areErasedParametersEqual(methodSignature, methodSignature2));
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 59:
            case 61:
            case 62:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 7:
            case 8:
            case 47:
            case 48:
            case 57:
            case 58:
            case 60:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 59:
            case 61:
            case 62:
                i2 = 3;
                break;
            case 7:
            case 8:
            case 47:
            case 48:
            case 57:
            case 58:
            case 60:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
            case 4:
            case 21:
            case 23:
            case 49:
                objArr[0] = "method1";
                break;
            case 3:
            case 5:
            case 22:
            case 24:
            case 50:
                objArr[0] = "method2";
                break;
            case 6:
            case 33:
            case 36:
                objArr[0] = "signature";
                break;
            case 7:
            case 8:
            case 47:
            case 48:
            case 57:
            case 58:
            case 60:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureUtil";
                break;
            case 9:
            case 11:
            case 13:
            case 17:
                objArr[0] = "name";
                break;
            case 10:
            case 12:
            case 14:
            case 18:
                objArr[0] = "substitutor";
                break;
            case 15:
            case 19:
                objArr[0] = "parameterTypes";
                break;
            case 16:
            case 20:
                objArr[0] = "typeParameterList";
                break;
            case 25:
            case 62:
                objArr[0] = "subSignature";
                break;
            case 26:
            case 61:
                objArr[0] = "superSignature";
                break;
            case 27:
                objArr[0] = "sig1";
                break;
            case 28:
                objArr[0] = "sig2";
                break;
            case 29:
                objArr[0] = "superMethodCandidate";
                break;
            case 30:
                objArr[0] = "derivedMethod";
                break;
            case 31:
            case 37:
            case 39:
            case 41:
            case 43:
                objArr[0] = "aClass";
                break;
            case 32:
            case 34:
                objArr[0] = "superClass";
                break;
            case 35:
                objArr[0] = "superSubstitutor";
                break;
            case 38:
                objArr[0] = "pattenMethod";
                break;
            case 40:
            case 42:
            case 53:
                objArr[0] = "methodSignature";
                break;
            case 44:
            case 45:
            case 46:
                objArr[0] = "method";
                break;
            case 51:
                objArr[0] = "signature1";
                break;
            case 52:
                objArr[0] = "signature2";
                break;
            case 54:
                objArr[0] = "superMethodSignature";
                break;
            case 55:
                objArr[0] = "substitutor1";
                break;
            case 56:
                objArr[0] = "substitutor2";
                break;
            case 59:
                objArr[0] = "sameNameMethodList";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "createErasedMethodSignatureSet";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 59:
            case 61:
            case 62:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureUtil";
                break;
            case 7:
            case 8:
                objArr[1] = "calcErasedParameterTypes";
                break;
            case 47:
            case 48:
                objArr[1] = "getOverloads";
                break;
            case 57:
            case 58:
                objArr[1] = "combineSubstitutors";
                break;
            case 60:
                objArr[1] = "convertMethodSignaturesToMethods";
                break;
            default:
                objArr[1] = "createErasedMethodSignatureMap";
                break;
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "areOverrideEquivalent";
                break;
            case 4:
            case 5:
                objArr[2] = "areErasedParametersEqual";
                break;
            case 6:
                objArr[2] = "calcErasedParameterTypes";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[2] = "createMethodSignature";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[2] = "areSignaturesEqual";
                break;
            case 25:
            case 26:
                objArr[2] = "checkSignaturesEqualInner";
                break;
            case 27:
            case 28:
                objArr[2] = "areSignaturesEqualLightweight";
                break;
            case 29:
            case 30:
                objArr[2] = "isSuperMethod";
                break;
            case 31:
            case 32:
            case 33:
                objArr[2] = "findMethodInSuperClassBySignatureInDerived";
                break;
            case 34:
            case 35:
            case 36:
                objArr[2] = "doFindMethodInSuperClassBySignatureInDerived";
                break;
            case 37:
            case 38:
            case 39:
            case 40:
                objArr[2] = "findMethodBySignature";
                break;
            case 41:
            case 42:
                objArr[2] = "findMethodBySuperSignature";
                break;
            case 43:
            case 44:
                objArr[2] = "findMethodBySuperMethod";
                break;
            case 45:
                objArr[2] = "hasOverloads";
                break;
            case 46:
                objArr[2] = "getOverloads";
                break;
            case 49:
            case 50:
                objArr[2] = "areParametersErasureEqual";
                break;
            case 51:
            case 52:
                objArr[2] = "areSignaturesErasureEqual";
                break;
            case 53:
            case 54:
                objArr[2] = "getSuperMethodSignatureSubstitutor";
                break;
            case 55:
            case 56:
                objArr[2] = "combineSubstitutors";
                break;
            case 59:
                objArr[2] = "convertMethodSignaturesToMethods";
                break;
            case 61:
            case 62:
                objArr[2] = "isSubsignature";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 59:
            case 61:
            case 62:
                throw new IllegalArgumentException(str2);
            case 7:
            case 8:
            case 47:
            case 48:
            case 57:
            case 58:
            case 60:
            default:
                throw new IllegalStateException(str2);
        }
    }

    private MethodSignatureUtil() {
    }

    public static <V> Map<MethodSignature, V> createErasedMethodSignatureMap() {
        Map<MethodSignature, V> mapCreateCustomHashingStrategyMap = CollectionFactory.createCustomHashingStrategyMap(METHOD_PARAMETERS_ERASURE_STRATEGY);
        if (mapCreateCustomHashingStrategyMap == null) {
            $$$reportNull$$$0(0);
        }
        return mapCreateCustomHashingStrategyMap;
    }

    public static Set<MethodSignature> createErasedMethodSignatureSet() {
        Set<MethodSignature> setCreateCustomHashingStrategySet = CollectionFactory.createCustomHashingStrategySet(METHOD_PARAMETERS_ERASURE_STRATEGY);
        if (setCreateCustomHashingStrategySet == null) {
            $$$reportNull$$$0(1);
        }
        return setCreateCustomHashingStrategySet;
    }

    public static boolean areOverrideEquivalent(PsiMethod psiMethod, PsiMethod psiMethod2) {
        if (psiMethod == null) {
            $$$reportNull$$$0(2);
        }
        if (psiMethod2 == null) {
            $$$reportNull$$$0(3);
        }
        int length = psiMethod.getTypeParameters().length;
        int length2 = psiMethod2.getTypeParameters().length;
        return (length == length2 || length == 0 || length2 == 0) && areErasedParametersEqual(psiMethod.getSignature(PsiSubstitutor.EMPTY), psiMethod2.getSignature(PsiSubstitutor.EMPTY));
    }

    public static boolean areErasedParametersEqual(MethodSignature methodSignature, MethodSignature methodSignature2) {
        if (methodSignature == null) {
            $$$reportNull$$$0(4);
        }
        if (methodSignature2 == null) {
            $$$reportNull$$$0(5);
        }
        return Arrays.equals(methodSignature instanceof MethodSignatureBase ? ((MethodSignatureBase) methodSignature).getErasedParameterTypes() : calcErasedParameterTypes(methodSignature), methodSignature2 instanceof MethodSignatureBase ? ((MethodSignatureBase) methodSignature2).getErasedParameterTypes() : calcErasedParameterTypes(methodSignature2));
    }

    public static PsiType[] calcErasedParameterTypes(MethodSignature methodSignature) {
        if (methodSignature == null) {
            $$$reportNull$$$0(6);
        }
        PsiType[] parameterTypes = methodSignature.getParameterTypes();
        if (parameterTypes.length == 0) {
            PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
            if (psiTypeArr == null) {
                $$$reportNull$$$0(7);
            }
            return psiTypeArr;
        }
        PsiSubstitutor substitutor = methodSignature.getSubstitutor();
        PsiType[] psiTypeArrCreateArray = PsiType.createArray(parameterTypes.length);
        for (int i = 0; i < parameterTypes.length; i++) {
            psiTypeArrCreateArray[i] = TypeConversionUtil.erasure(parameterTypes[i], substitutor);
        }
        if (psiTypeArrCreateArray == null) {
            $$$reportNull$$$0(8);
        }
        return psiTypeArrCreateArray;
    }

    public static MethodSignature createMethodSignature(String str, PsiParameterList psiParameterList, PsiTypeParameterList psiTypeParameterList, PsiSubstitutor psiSubstitutor) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(10);
        }
        return createMethodSignature(str, psiParameterList, psiTypeParameterList, psiSubstitutor, false);
    }

    public static MethodSignature createMethodSignature(String str, PsiParameterList psiParameterList, PsiTypeParameterList psiTypeParameterList, PsiSubstitutor psiSubstitutor, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(12);
        }
        return new MethodSignatureHandMade(str, psiParameterList, psiTypeParameterList, psiSubstitutor, z);
    }

    public static MethodSignature createMethodSignature(String str, PsiType[] psiTypeArr, PsiTypeParameter[] psiTypeParameterArr, PsiSubstitutor psiSubstitutor) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(14);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(15);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(16);
        }
        return createMethodSignature(str, psiTypeArr, psiTypeParameterArr, psiSubstitutor, false);
    }

    public static MethodSignature createMethodSignature(String str, PsiType[] psiTypeArr, PsiTypeParameter[] psiTypeParameterArr, PsiSubstitutor psiSubstitutor, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(18);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(19);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(20);
        }
        return new MethodSignatureHandMade(str, psiTypeArr, psiTypeParameterArr, psiSubstitutor, z);
    }

    public static boolean areSignaturesEqual(PsiMethod psiMethod, PsiMethod psiMethod2) {
        if (psiMethod == null) {
            $$$reportNull$$$0(21);
        }
        if (psiMethod2 == null) {
            $$$reportNull$$$0(22);
        }
        return areSignaturesEqual(psiMethod.getSignature(PsiSubstitutor.EMPTY), psiMethod2.getSignature(PsiSubstitutor.EMPTY));
    }

    public static boolean areSignaturesEqual(MethodSignature methodSignature, MethodSignature methodSignature2) {
        if (methodSignature == null) {
            $$$reportNull$$$0(23);
        }
        if (methodSignature2 == null) {
            $$$reportNull$$$0(24);
        }
        if (methodSignature2 == methodSignature) {
            return true;
        }
        if (areSignaturesEqualLightweight(methodSignature, methodSignature2)) {
            return checkSignaturesEqualInner(methodSignature, methodSignature2, getSuperMethodSignatureSubstitutor(methodSignature, methodSignature2)) || checkSignaturesEqualInner(methodSignature2, methodSignature, getSuperMethodSignatureSubstitutor(methodSignature2, methodSignature));
        }
        return false;
    }

    private static boolean checkSignaturesEqualInner(MethodSignature methodSignature, MethodSignature methodSignature2, PsiSubstitutor psiSubstitutor) {
        if (methodSignature == null) {
            $$$reportNull$$$0(25);
        }
        if (methodSignature2 == null) {
            $$$reportNull$$$0(26);
        }
        if (psiSubstitutor == null || !areErasedParametersEqual(methodSignature, methodSignature2)) {
            return false;
        }
        PsiType[] parameterTypes = methodSignature.getParameterTypes();
        PsiType[] parameterTypes2 = methodSignature2.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!Comparing.equal(psiSubstitutor.substitute(parameterTypes[i]), psiSubstitutor.substitute(parameterTypes2[i]))) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean areSignaturesEqualLightweight(MethodSignature methodSignature, MethodSignature methodSignature2) {
        if (methodSignature == null) {
            $$$reportNull$$$0(27);
        }
        if (methodSignature2 == null) {
            $$$reportNull$$$0(28);
        }
        boolean zIsConstructor = methodSignature.isConstructor();
        if (zIsConstructor != methodSignature2.isConstructor()) {
            return false;
        }
        if ((!zIsConstructor || (!(methodSignature instanceof HierarchicalMethodSignature) && !(methodSignature2 instanceof HierarchicalMethodSignature))) && !methodSignature.getName().equals(methodSignature2.getName())) {
            return false;
        }
        PsiType[] parameterTypes = methodSignature.getParameterTypes();
        PsiType[] parameterTypes2 = methodSignature2.getParameterTypes();
        if (parameterTypes.length != parameterTypes2.length) {
            return false;
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            PsiType psiType = parameterTypes[i];
            PsiType psiType2 = parameterTypes2[i];
            boolean z = psiType instanceof PsiPrimitiveType;
            if (z != (psiType2 instanceof PsiPrimitiveType)) {
                return false;
            }
            if (z && !psiType.equals(psiType2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSuperMethod(PsiMethod psiMethod, PsiMethod psiMethod2) {
        PsiSubstitutor maybeSuperClassSubstitutor;
        if (psiMethod == null) {
            $$$reportNull$$$0(29);
        }
        if (psiMethod2 == null) {
            $$$reportNull$$$0(30);
        }
        if (psiMethod.hasModifierProperty("private")) {
            return false;
        }
        PsiClass containingClass = psiMethod.mo6092getContainingClass();
        PsiClass containingClass2 = psiMethod2.mo6092getContainingClass();
        if (containingClass2 == null || containingClass == null || containingClass2 == containingClass) {
            return false;
        }
        if ((!psiMethod.hasModifierProperty(PsiModifier.PACKAGE_LOCAL) || JavaPsiFacade.getInstance(containingClass2.getProject()).arePackagesTheSame(containingClass, containingClass2)) && (maybeSuperClassSubstitutor = TypeConversionUtil.getMaybeSuperClassSubstitutor(containingClass, containingClass2, PsiSubstitutor.EMPTY)) != null) {
            return isSubsignature(psiMethod.getSignature(maybeSuperClassSubstitutor), psiMethod2.getSignature(PsiSubstitutor.EMPTY));
        }
        return false;
    }

    public static PsiMethod findMethodInSuperClassBySignatureInDerived(PsiClass psiClass, PsiClass psiClass2, MethodSignature methodSignature, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(31);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(32);
        }
        if (methodSignature == null) {
            $$$reportNull$$$0(33);
        }
        return doFindMethodInSuperClassBySignatureInDerived(psiClass2, TypeConversionUtil.getSuperClassSubstitutor(psiClass2, psiClass, PsiSubstitutor.EMPTY), methodSignature, z);
    }

    private static PsiMethod doFindMethodInSuperClassBySignatureInDerived(PsiClass psiClass, PsiSubstitutor psiSubstitutor, MethodSignature methodSignature, boolean z) {
        PsiClass superClass;
        if (psiClass == null) {
            $$$reportNull$$$0(34);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(35);
        }
        if (methodSignature == null) {
            $$$reportNull$$$0(36);
        }
        for (PsiMethod psiMethod : psiClass.findMethodsByName(methodSignature.getName(), false)) {
            if (isSubsignature(psiMethod.getSignature(psiSubstitutor), methodSignature)) {
                return psiMethod;
            }
        }
        if (!z || (superClass = psiClass.getSuperClass()) == null || superClass == psiClass) {
            return null;
        }
        return doFindMethodInSuperClassBySignatureInDerived(superClass, TypeConversionUtil.getSuperClassSubstitutor(superClass, psiClass, psiSubstitutor), methodSignature, true);
    }

    public static PsiMethod findMethodBySignature(PsiClass psiClass, PsiMethod psiMethod, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(37);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(38);
        }
        return findMethodBySignature(psiClass, psiMethod.getSignature(PsiSubstitutor.EMPTY), z);
    }

    public static PsiMethod findMethodBySignature(PsiClass psiClass, MethodSignature methodSignature, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(39);
        }
        if (methodSignature == null) {
            $$$reportNull$$$0(40);
        }
        for (Pair<PsiMethod, PsiSubstitutor> pair : psiClass.findMethodsAndTheirSubstitutorsByName(methodSignature.isConstructor() ? psiClass.getName() : methodSignature.getName(), z)) {
            PsiMethod psiMethod = pair.first;
            if (methodSignature.equals(psiMethod.getSignature(pair.second))) {
                return psiMethod;
            }
        }
        return null;
    }

    public static PsiMethod findMethodBySuperSignature(PsiClass psiClass, MethodSignature methodSignature, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(41);
        }
        if (methodSignature == null) {
            $$$reportNull$$$0(42);
        }
        for (Pair<PsiMethod, PsiSubstitutor> pair : psiClass.findMethodsAndTheirSubstitutorsByName(methodSignature.isConstructor() ? psiClass.getName() : methodSignature.getName(), z)) {
            PsiMethod psiMethod = pair.first;
            if (isSubsignature(methodSignature, psiMethod.getSignature(pair.second))) {
                return psiMethod;
            }
        }
        return null;
    }

    public static PsiMethod findMethodBySuperMethod(PsiClass psiClass, PsiMethod psiMethod, boolean z) {
        PsiSubstitutor classSubstitutor;
        if (psiClass == null) {
            $$$reportNull$$$0(43);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(44);
        }
        for (Pair<PsiMethod, PsiSubstitutor> pair : psiClass.findMethodsAndTheirSubstitutorsByName(psiMethod.getName(), z)) {
            PsiMethod psiMethod2 = pair.first;
            PsiSubstitutor psiSubstitutor = pair.second;
            MethodSignature signature = psiMethod2.getSignature(psiSubstitutor);
            PsiClass containingClass = psiMethod.mo6092getContainingClass();
            PsiClass containingClass2 = psiMethod2.mo6092getContainingClass();
            if (containingClass != null && containingClass2 != null && (classSubstitutor = TypeConversionUtil.getClassSubstitutor(containingClass, containingClass2, psiSubstitutor)) != null && isSubsignature(psiMethod.getSignature(classSubstitutor), signature)) {
                return psiMethod2;
            }
        }
        return null;
    }

    public static boolean hasOverloads(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(45);
        }
        return getOverloads(psiMethod).length > 1;
    }

    private static PsiMethod[] getOverloads(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(46);
        }
        PsiClass containingClass = psiMethod.mo6092getContainingClass();
        if (containingClass == null) {
            return new PsiMethod[]{psiMethod};
        }
        PsiMethod[] psiMethodArrFindMethodsByName = containingClass.findMethodsByName(psiMethod.getName(), false);
        if (psiMethodArrFindMethodsByName == null) {
            $$$reportNull$$$0(48);
        }
        return psiMethodArrFindMethodsByName;
    }

    public static boolean areParametersErasureEqual(PsiMethod psiMethod, PsiMethod psiMethod2) {
        if (psiMethod == null) {
            $$$reportNull$$$0(49);
        }
        if (psiMethod2 == null) {
            $$$reportNull$$$0(50);
        }
        if (psiMethod.getParameterList().getParametersCount() != psiMethod2.getParameterList().getParametersCount()) {
            return false;
        }
        return areSignaturesErasureEqual(psiMethod.getSignature(PsiSubstitutor.EMPTY), psiMethod2.getSignature(PsiSubstitutor.EMPTY));
    }

    public static boolean areSignaturesErasureEqual(MethodSignature methodSignature, MethodSignature methodSignature2) {
        if (methodSignature == null) {
            $$$reportNull$$$0(51);
        }
        if (methodSignature2 == null) {
            $$$reportNull$$$0(52);
        }
        return METHOD_PARAMETERS_ERASURE_STRATEGY.equals(methodSignature, methodSignature2);
    }

    public static PsiSubstitutor getSuperMethodSignatureSubstitutor(MethodSignature methodSignature, MethodSignature methodSignature2) {
        if (methodSignature == null) {
            $$$reportNull$$$0(53);
        }
        if (methodSignature2 == null) {
            $$$reportNull$$$0(54);
        }
        PsiTypeParameter[] typeParameters = methodSignature.getTypeParameters();
        PsiTypeParameter[] typeParameters2 = methodSignature2.getTypeParameters();
        if (typeParameters.length != typeParameters2.length) {
            return null;
        }
        PsiSubstitutor substitutor = methodSignature2.getSubstitutor();
        for (int i = 0; i < typeParameters.length; i++) {
            PsiTypeParameter psiTypeParameter = typeParameters[i];
            substitutor = substitutor.put(typeParameters2[i], JavaPsiFacade.getElementFactory(psiTypeParameter.getProject()).createType(psiTypeParameter));
        }
        PsiSubstitutor substitutor2 = methodSignature.getSubstitutor();
        for (int i2 = 0; i2 < typeParameters.length; i2++) {
            PsiTypeParameter psiTypeParameter2 = typeParameters[i2];
            PsiTypeParameter psiTypeParameter3 = typeParameters2[i2];
            HashSet hashSet = new HashSet();
            for (PsiClassType psiClassType : psiTypeParameter2.getSuperTypes()) {
                hashSet.add(substitutor2.substitute(psiClassType));
            }
            HashSet hashSet2 = new HashSet();
            for (PsiClassType psiClassType2 : psiTypeParameter3.getSuperTypes()) {
                hashSet2.add(substitutor2.substitute(substitutor.substitute(psiClassType2)));
            }
            hashSet.remove(PsiType.getJavaLangObject(psiTypeParameter2.getManager(), psiTypeParameter2.getResolveScope()));
            hashSet2.remove(PsiType.getJavaLangObject(psiTypeParameter3.getManager(), psiTypeParameter3.getResolveScope()));
            if (!hashSet.equals(hashSet2)) {
                return null;
            }
        }
        return substitutor;
    }

    public static PsiSubstitutor combineSubstitutors(PsiSubstitutor psiSubstitutor, PsiSubstitutor psiSubstitutor2) {
        PsiType psiTypeSubstitute;
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(55);
        }
        if (psiSubstitutor2 == null) {
            $$$reportNull$$$0(56);
        }
        if (psiSubstitutor == PsiSubstitutor.EMPTY) {
            if (psiSubstitutor2 == null) {
                $$$reportNull$$$0(57);
            }
            return psiSubstitutor2;
        }
        for (PsiTypeParameter psiTypeParameter : (PsiTypeParameter[]) psiSubstitutor.getSubstitutionMap().keySet().toArray(PsiTypeParameter.EMPTY_ARRAY)) {
            PsiType psiTypeSubstitute2 = psiSubstitutor.substitute(psiTypeParameter);
            if (psiTypeSubstitute2 instanceof PsiClassType) {
                PsiClass psiClassResolve = ((PsiClassType) psiTypeSubstitute2).resolve();
                psiTypeSubstitute = psiClassResolve instanceof PsiTypeParameter ? psiSubstitutor2.substitute((PsiTypeParameter) psiClassResolve) : psiSubstitutor2.substitute(psiTypeSubstitute2);
            } else {
                psiTypeSubstitute = psiSubstitutor2.substitute(psiTypeSubstitute2);
            }
            psiSubstitutor = psiSubstitutor.put(psiTypeParameter, psiTypeSubstitute);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(58);
        }
        return psiSubstitutor;
    }

    public static PsiMethod[] convertMethodSignaturesToMethods(List<? extends MethodSignatureBackedByPsiMethod> list) {
        if (list == null) {
            $$$reportNull$$$0(59);
        }
        PsiMethod[] psiMethodArr = new PsiMethod[list.size()];
        for (int i = 0; i < list.size(); i++) {
            psiMethodArr[i] = list.get(i).getMethod();
        }
        return psiMethodArr;
    }

    public static boolean isSubsignature(MethodSignature methodSignature, MethodSignature methodSignature2) {
        if (methodSignature == null) {
            $$$reportNull$$$0(61);
        }
        if (methodSignature2 == null) {
            $$$reportNull$$$0(62);
        }
        if (methodSignature2 == methodSignature) {
            return true;
        }
        if (!areSignaturesEqualLightweight(methodSignature, methodSignature2)) {
            return false;
        }
        if (checkSignaturesEqualInner(methodSignature, methodSignature2, getSuperMethodSignatureSubstitutor(methodSignature2, methodSignature))) {
            return true;
        }
        if (methodSignature2.getTypeParameters().length > 0) {
            return false;
        }
        PsiType[] parameterTypes = methodSignature2.getParameterTypes();
        PsiType[] parameterTypes2 = methodSignature.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!Comparing.equal(parameterTypes[i], TypeConversionUtil.erasure(parameterTypes2[i], methodSignature.getSubstitutor()))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isReturnTypeSubstitutable(MethodSignature methodSignature, MethodSignature methodSignature2, PsiType psiType, PsiType psiType2) {
        if (PsiType.VOID.equals(psiType)) {
            return PsiType.VOID.equals(psiType2);
        }
        if (psiType instanceof PsiPrimitiveType) {
            return psiType.equals(psiType2);
        }
        if (((psiType instanceof PsiClassType) || (psiType instanceof PsiArrayType)) && psiType2 != null) {
            PsiSubstitutor superMethodSignatureSubstitutor = getSuperMethodSignatureSubstitutor(methodSignature2, methodSignature);
            if (superMethodSignatureSubstitutor != null && psiType2.isAssignableFrom(superMethodSignatureSubstitutor.substitute(psiType))) {
                return true;
            }
            if (!areSignaturesEqual(methodSignature, methodSignature2)) {
                return psiType.equals(TypeConversionUtil.erasure(psiType2));
            }
        }
        return Comparing.equal(psiType, psiType2);
    }
}
