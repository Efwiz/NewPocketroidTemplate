package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.HashSet;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class TypesDistinctProver {
    public static final Set<String> ARRAY_SUPER_CLASSES;

    static {
        HashSet hashSet = new HashSet();
        ARRAY_SUPER_CLASSES = hashSet;
        hashSet.add(CommonClassNames.JAVA_IO_SERIALIZABLE);
        hashSet.add(CommonClassNames.JAVA_LANG_CLONEABLE);
        hashSet.add(CommonClassNames.JAVA_LANG_OBJECT);
    }

    private TypesDistinctProver() {
    }

    public static boolean provablyDistinct(PsiType psiType, PsiType psiType2) {
        return provablyDistinct(psiType, psiType2, 0);
    }

    static boolean provablyDistinct(PsiType psiType, PsiType psiType2, int i) {
        if (psiType instanceof PsiWildcardType) {
            if (psiType2 instanceof PsiWildcardType) {
                return provablyDistinct((PsiWildcardType) psiType, (PsiWildcardType) psiType2, true, i);
            }
            if (i > 1) {
                return true;
            }
            if (psiType2 instanceof PsiCapturedWildcardType) {
                return provablyDistinct((PsiWildcardType) psiType, ((PsiCapturedWildcardType) psiType2).getWildcard(), false, i);
            }
            if (psiType2 instanceof PsiClassType) {
                PsiClass psiClassResolveClassInType = PsiUtil.resolveClassInType(psiType2);
                if (psiClassResolveClassInType == null) {
                    return false;
                }
                PsiWildcardType psiWildcardType = (PsiWildcardType) psiType;
                if (psiWildcardType.isExtends()) {
                    PsiType extendsBound = psiWildcardType.getExtendsBound();
                    if ((extendsBound instanceof PsiArrayType) && proveArrayTypeDistinct((PsiArrayType) extendsBound, psiType2)) {
                        return true;
                    }
                    PsiClass psiClassResolveClassInType2 = PsiUtil.resolveClassInType(extendsBound);
                    if (psiClassResolveClassInType2 == null) {
                        return false;
                    }
                    if (CommonClassNames.JAVA_LANG_OBJECT.equals(psiClassResolveClassInType.getQualifiedName()) && !(psiClassResolveClassInType2 instanceof PsiTypeParameter)) {
                        return !CommonClassNames.JAVA_LANG_OBJECT.equals(psiClassResolveClassInType2.getQualifiedName());
                    }
                    return proveExtendsBoundsDistinct(psiType, psiType2, psiClassResolveClassInType2, psiClassResolveClassInType);
                }
                if (psiWildcardType.isSuper()) {
                    PsiType superBound = psiWildcardType.getSuperBound();
                    if ((superBound instanceof PsiArrayType) && proveArrayTypeDistinct((PsiArrayType) superBound, psiType2)) {
                        return true;
                    }
                    PsiClass psiClassResolveClassInType3 = PsiUtil.resolveClassInType(superBound);
                    if (psiClassResolveClassInType3 == null) {
                        return false;
                    }
                    if (psiClassResolveClassInType3 instanceof PsiTypeParameter) {
                        PsiClassType[] extendsListTypes = psiClassResolveClassInType3.getExtendsListTypes();
                        for (PsiClassType psiClassType : extendsListTypes) {
                            PsiClass psiClassResolve = psiClassType.resolve();
                            if (InheritanceUtil.isInheritorOrSelf(psiClassResolve, psiClassResolveClassInType, true) || InheritanceUtil.isInheritorOrSelf(psiClassResolveClassInType, psiClassResolve, true)) {
                                return false;
                            }
                        }
                        return extendsListTypes.length > 0;
                    }
                    return !InheritanceUtil.isInheritorOrSelf(psiClassResolveClassInType3, psiClassResolveClassInType, true);
                }
                PsiType bound = psiWildcardType.getBound();
                return (bound == null || bound.equals(psiType2)) ? false : true;
            }
            if (psiType2 instanceof PsiArrayType) {
                return proveArrayTypeDistinct((PsiArrayType) psiType2, psiType);
            }
        } else {
            if (psiType2 instanceof PsiWildcardType) {
                return provablyDistinct(psiType2, psiType, i);
            }
            if (psiType instanceof PsiCapturedWildcardType) {
                return provablyDistinct(((PsiCapturedWildcardType) psiType).getWildcard(), psiType2, i);
            }
            if (psiType2 instanceof PsiCapturedWildcardType) {
                return provablyDistinct(psiType2, psiType, i);
            }
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiType);
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType2 = PsiUtil.resolveGenericsClassInType(psiType2);
        PsiClass element = classResolveResultResolveGenericsClassInType.getElement();
        PsiClass element2 = classResolveResultResolveGenericsClassInType2.getElement();
        boolean z = element instanceof PsiTypeParameter;
        if (z && i < 2 && !distinguishFromTypeParam((PsiTypeParameter) element, psiType, psiType2)) {
            return false;
        }
        boolean z2 = element2 instanceof PsiTypeParameter;
        if (z2 && i < 2 && !distinguishFromTypeParam((PsiTypeParameter) element2, psiType2, psiType)) {
            return false;
        }
        if (Comparing.equal(TypeConversionUtil.erasure(psiType), TypeConversionUtil.erasure(psiType2)) && !z && !z2) {
            PsiSubstitutor substitutor = classResolveResultResolveGenericsClassInType.getSubstitutor();
            PsiSubstitutor substitutor2 = classResolveResultResolveGenericsClassInType2.getSubstitutor();
            for (PsiTypeParameter psiTypeParameter : substitutor.getSubstitutionMap().keySet()) {
                PsiType psiTypeSubstitute = substitutor.substitute(psiTypeParameter);
                PsiType psiTypeSubstitute2 = substitutor2.substitute(psiTypeParameter);
                if (psiTypeSubstitute != null || psiTypeSubstitute2 != null) {
                    if (psiTypeSubstitute == null) {
                        if ((psiType2 instanceof PsiClassType) && ((PsiClassType) psiType2).hasParameters()) {
                            return true;
                        }
                    } else if (psiTypeSubstitute2 == null) {
                        if ((psiType instanceof PsiClassType) && ((PsiClassType) psiType).hasParameters()) {
                            return true;
                        }
                    } else if (provablyDistinct(psiTypeSubstitute, psiTypeSubstitute2, i + 1)) {
                        return true;
                    }
                }
            }
            if (i < 2) {
                return false;
            }
        }
        if (element == null || element2 == null) {
            return (psiType == null || psiType2 == null || psiType.equals(psiType2)) ? false : true;
        }
        if (psiType2 == null || psiType == null || psiType.equals(psiType2)) {
            return false;
        }
        if (i == 0 && element.isInterface() && element2.isInterface()) {
            return false;
        }
        return (InheritanceUtil.isInheritorOrSelf(element, element2, true) && InheritanceUtil.isInheritorOrSelf(element2, element, true)) ? false : true;
    }

    private static boolean distinguishFromTypeParam(PsiTypeParameter psiTypeParameter, PsiType psiType, final PsiType psiType2) {
        PsiClassType[] extendsListTypes = psiTypeParameter.getExtendsListTypes();
        return extendsListTypes.length == 0 ? !(psiType instanceof PsiClassType) : ContainerUtil.exists(extendsListTypes, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.TypesDistinctProver$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return TypesDistinctProver.lambda$distinguishFromTypeParam$0(psiType2, (PsiClassType) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$distinguishFromTypeParam$0(PsiType psiType, PsiClassType psiClassType) {
        return !TypeConversionUtil.isAssignable(psiClassType.rawType(), psiType);
    }

    public static boolean provablyDistinct(PsiWildcardType psiWildcardType, PsiWildcardType psiWildcardType2, boolean z, int i) {
        if (psiWildcardType.isSuper() && psiWildcardType2.isSuper()) {
            return false;
        }
        if (psiWildcardType.isExtends() && psiWildcardType2.isExtends()) {
            PsiType extendsBound = psiWildcardType.getExtendsBound();
            PsiType extendsBound2 = psiWildcardType2.getExtendsBound();
            if (((extendsBound instanceof PsiArrayType) && proveArrayTypeDistinct((PsiArrayType) extendsBound, extendsBound2)) || ((extendsBound2 instanceof PsiArrayType) && proveArrayTypeDistinct((PsiArrayType) extendsBound2, extendsBound))) {
                return true;
            }
            PsiClass psiClassResolveClassInType = PsiUtil.resolveClassInType(extendsBound);
            PsiClass psiClassResolveClassInType2 = PsiUtil.resolveClassInType(extendsBound2);
            if (psiClassResolveClassInType != null && psiClassResolveClassInType2 != null) {
                if (z && i > 0 && (extendsBound instanceof PsiClassType) && (extendsBound2 instanceof PsiClassType) && (((PsiClassType) extendsBound).isRaw() ^ ((PsiClassType) extendsBound2).isRaw())) {
                    return true;
                }
                return proveExtendsBoundsDistinct(psiWildcardType, psiWildcardType2, psiClassResolveClassInType, psiClassResolveClassInType2);
            }
            return provablyDistinct(extendsBound, extendsBound2, 1);
        }
        if (psiWildcardType2.isExtends()) {
            return provablyDistinct(psiWildcardType2, psiWildcardType, z, i);
        }
        if (psiWildcardType.isExtends() && !psiWildcardType2.isBounded() && i > 1) {
            return PsiUtil.resolveClassInType(psiWildcardType.getExtendsBound()) instanceof PsiTypeParameter;
        }
        if (psiWildcardType.isExtends() && psiWildcardType2.isSuper()) {
            PsiType extendsBound3 = psiWildcardType.getExtendsBound();
            PsiType superBound = psiWildcardType2.getSuperBound();
            if (((extendsBound3 instanceof PsiArrayType) && proveArrayTypeDistinct((PsiArrayType) extendsBound3, superBound)) || ((superBound instanceof PsiArrayType) && proveArrayTypeDistinct((PsiArrayType) superBound, extendsBound3))) {
                return true;
            }
            PsiClass psiClassResolveClassInType3 = PsiUtil.resolveClassInType(extendsBound3);
            PsiClass psiClassResolveClassInType4 = PsiUtil.resolveClassInType(superBound);
            if (psiClassResolveClassInType3 != null && psiClassResolveClassInType4 != null) {
                if (psiClassResolveClassInType3 instanceof PsiTypeParameter) {
                    return try2ProveTypeParameterDistinct(psiWildcardType2, psiClassResolveClassInType3);
                }
                if (psiClassResolveClassInType4 instanceof PsiTypeParameter) {
                    return false;
                }
                return !InheritanceUtil.isInheritorOrSelf(psiClassResolveClassInType4, psiClassResolveClassInType3, true);
            }
            return provablyDistinct(extendsBound3, superBound);
        }
        if (psiWildcardType.isBounded() && psiWildcardType2.isBounded()) {
            return !psiWildcardType.equals(psiWildcardType2);
        }
        return false;
    }

    public static boolean proveExtendsBoundsDistinct(PsiType psiType, PsiType psiType2, PsiClass psiClass, PsiClass psiClass2) {
        if (psiClass == null || psiClass2 == null) {
            return false;
        }
        if (psiClass.isInterface() && psiClass2.isInterface()) {
            return false;
        }
        if (psiClass.isInterface()) {
            return psiClass2.hasModifierProperty("final") && !InheritanceUtil.isInheritorOrSelf(psiClass2, psiClass, true);
        }
        if (psiClass2.isInterface()) {
            return psiClass.hasModifierProperty("final") && !InheritanceUtil.isInheritorOrSelf(psiClass, psiClass2, true);
        }
        if (psiClass instanceof PsiTypeParameter) {
            return try2ProveTypeParameterDistinct(psiType2, psiClass);
        }
        if (psiClass2 instanceof PsiTypeParameter) {
            return try2ProveTypeParameterDistinct(psiType, psiClass2);
        }
        return (InheritanceUtil.isInheritorOrSelf(psiClass, psiClass2, true) || InheritanceUtil.isInheritorOrSelf(psiClass2, psiClass, true)) ? false : true;
    }

    public static boolean try2ProveTypeParameterDistinct(PsiType psiType, PsiClass psiClass) {
        PsiClassType[] extendsListTypes = psiClass.getExtendsListTypes();
        if (extendsListTypes.length == 0) {
            return false;
        }
        return provablyDistinct(PsiWildcardType.createExtends(psiClass.getManager(), extendsListTypes[0]), psiType);
    }

    public static boolean proveArrayTypeDistinct(PsiArrayType psiArrayType, PsiType psiType) {
        if (psiArrayType.getArrayDimensions() == psiType.getArrayDimensions()) {
            PsiType componentType = psiArrayType.getComponentType();
            PsiType componentType2 = ((PsiArrayType) psiType).getComponentType();
            if ((componentType2 instanceof PsiClassType) && (componentType instanceof PsiClassType)) {
                return proveExtendsBoundsDistinct(componentType2, componentType, ((PsiClassType) componentType2).resolve(), ((PsiClassType) componentType).resolve());
            }
            return !psiType.equals(psiArrayType);
        }
        if (psiType.getArrayDimensions() + 1 == psiArrayType.getArrayDimensions() && (psiType.getDeepComponentType() instanceof PsiClassType)) {
            return !isSuperClassOfArrayType(((PsiClassType) psiType.getDeepComponentType()).resolve());
        }
        if (psiType.getArrayDimensions() == psiArrayType.getArrayDimensions() + 1 && (psiArrayType.getDeepComponentType() instanceof PsiClassType)) {
            return !isSuperClassOfArrayType(((PsiClassType) psiArrayType.getDeepComponentType()).resolve());
        }
        if (psiType instanceof PsiClassType) {
            return !isSuperClassOfArrayType(((PsiClassType) psiType).resolve());
        }
        if (psiType instanceof PsiWildcardType) {
            PsiWildcardType psiWildcardType = (PsiWildcardType) psiType;
            PsiType bound = psiWildcardType.getBound();
            if (bound == null || bound.equals(psiArrayType)) {
                return false;
            }
            if ((bound instanceof PsiArrayType) && !psiWildcardType.isSuper()) {
                return proveArrayTypeDistinct(psiArrayType, bound);
            }
            PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(bound);
            if (psiClassResolveClassInClassTypeOnly == null) {
                return true;
            }
            if (psiClassResolveClassInClassTypeOnly instanceof PsiTypeParameter) {
                return try2ProveTypeParameterDistinct(psiArrayType, psiClassResolveClassInClassTypeOnly);
            }
            return (psiWildcardType.isExtends() && isSuperClassOfArrayType(psiClassResolveClassInClassTypeOnly)) ? false : true;
        }
        if (psiType instanceof PsiIntersectionType) {
            for (PsiType psiType2 : ((PsiIntersectionType) psiType).getConjuncts()) {
                if (!proveArrayTypeDistinct(psiArrayType, psiType2)) {
                    return false;
                }
            }
        } else if (psiType instanceof PsiCapturedWildcardType) {
            return proveArrayTypeDistinct(psiArrayType, ((PsiCapturedWildcardType) psiType).getWildcard());
        }
        return true;
    }

    private static boolean isSuperClassOfArrayType(PsiClass psiClass) {
        String qualifiedName;
        return (psiClass == null || (qualifiedName = psiClass.getQualifiedName()) == null || !ARRAY_SUPER_CLASSES.contains(qualifiedName)) ? false : true;
    }
}
