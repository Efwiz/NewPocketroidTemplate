package org.jetbrains.kotlin.com.intellij.psi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Couple;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypesDistinctProver;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class GenericsUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) GenericsUtil.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 9 || i == 18 || i == 14 || i == 15) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 9 || i == 18 || i == 14 || i == 15) ? 3 : 2];
        if (i == 9 || i == 18) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i == 14) {
            objArr[0] = "typeParams";
        } else if (i != 15) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/GenericsUtil";
        } else {
            objArr[0] = "expectedType";
        }
        switch (i) {
            case 7:
            case 8:
                objArr[1] = "getLeastUpperClasses";
                break;
            case 9:
            case 14:
            case 15:
            case 18:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/GenericsUtil";
                break;
            case 10:
            case 11:
            case 12:
                objArr[1] = "getVariableTypeByExpressionType";
                break;
            case 13:
                objArr[1] = "getExpectedGenericType";
                break;
            case 16:
            case 17:
                objArr[1] = "getExpectedTypeArguments";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
                objArr[1] = "eliminateExtendsFinalWildcard";
                break;
            default:
                objArr[1] = "getLeastUpperBound";
                break;
        }
        if (i == 9) {
            objArr[2] = "getVariableTypeByExpressionType";
        } else if (i == 18) {
            objArr[2] = "eliminateExtendsFinalWildcard";
        } else if (i == 14 || i == 15) {
            objArr[2] = "getExpectedTypeArguments";
        }
        String str2 = String.format(str, objArr);
        if (i != 9 && i != 18 && i != 14 && i != 15) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    private GenericsUtil() {
    }

    public static PsiType getGreatestLowerBound(PsiType psiType, PsiType psiType2) {
        if (psiType == null || psiType2 == null) {
            return null;
        }
        if (psiType.equalsToText(CommonClassNames.JAVA_LANG_OBJECT)) {
            return psiType2;
        }
        return psiType2.equalsToText(CommonClassNames.JAVA_LANG_OBJECT) ? psiType : PsiIntersectionType.createIntersection(psiType, psiType2);
    }

    public static PsiType getLeastUpperBound(PsiType psiType, PsiType psiType2, PsiManager psiManager) {
        if (TypeConversionUtil.isPrimitiveAndNotNull(psiType) || TypeConversionUtil.isPrimitiveAndNotNull(psiType2)) {
            return null;
        }
        if (TypeConversionUtil.isNullType(psiType)) {
            return psiType2;
        }
        return (TypeConversionUtil.isNullType(psiType2) || Comparing.equal(psiType, psiType2)) ? psiType : getLeastUpperBound(psiType, psiType2, new LinkedHashSet(), psiManager);
    }

    private static PsiType getLeastUpperBound(PsiType psiType, PsiType psiType2, Set<Couple<PsiType>> set, PsiManager psiManager) {
        PsiSubstitutor psiSubstitutorPut;
        if (psiType instanceof PsiCapturedWildcardType) {
            return getLeastUpperBound(((PsiCapturedWildcardType) psiType).getUpperBound(), psiType2, set, psiManager);
        }
        if (psiType2 instanceof PsiCapturedWildcardType) {
            return getLeastUpperBound(psiType, ((PsiCapturedWildcardType) psiType2).getUpperBound(), set, psiManager);
        }
        if (psiType instanceof PsiWildcardType) {
            return getLeastUpperBound(((PsiWildcardType) psiType).getExtendsBound(), psiType2, set, psiManager);
        }
        if (psiType2 instanceof PsiWildcardType) {
            return getLeastUpperBound(psiType, ((PsiWildcardType) psiType2).getExtendsBound(), set, psiManager);
        }
        boolean z = psiType instanceof PsiArrayType;
        int i = 0;
        if (z && (psiType2 instanceof PsiArrayType)) {
            PsiType componentType = ((PsiArrayType) psiType).getComponentType();
            PsiType componentType2 = ((PsiArrayType) psiType2).getComponentType();
            PsiType leastUpperBound = getLeastUpperBound(componentType, componentType2, set, psiManager);
            if (((componentType instanceof PsiPrimitiveType) || (componentType2 instanceof PsiPrimitiveType)) && leastUpperBound.equalsToText(CommonClassNames.JAVA_LANG_OBJECT)) {
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiManager.getProject());
                GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(psiManager.getProject());
                PsiType psiTypeCreateIntersection = PsiIntersectionType.createIntersection(leastUpperBound, elementFactory.createTypeByFQClassName(CommonClassNames.JAVA_LANG_CLONEABLE, globalSearchScopeAllScope), elementFactory.createTypeByFQClassName(CommonClassNames.JAVA_IO_SERIALIZABLE, globalSearchScopeAllScope));
                if (psiTypeCreateIntersection == null) {
                    $$$reportNull$$$0(0);
                }
                return psiTypeCreateIntersection;
            }
            PsiArrayType psiArrayTypeCreateArrayType = leastUpperBound.createArrayType();
            if (psiArrayTypeCreateArrayType == null) {
                $$$reportNull$$$0(1);
            }
            return psiArrayTypeCreateArrayType;
        }
        if (psiType instanceof PsiIntersectionType) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            PsiType[] conjuncts = ((PsiIntersectionType) psiType).getConjuncts();
            int length = conjuncts.length;
            while (i < length) {
                linkedHashSet.add(getLeastUpperBound(conjuncts[i], psiType2, set, psiManager));
                i++;
            }
            PsiType psiTypeCreateIntersection2 = PsiIntersectionType.createIntersection((PsiType[]) linkedHashSet.toArray(PsiType.createArray(linkedHashSet.size())));
            if (psiTypeCreateIntersection2 == null) {
                $$$reportNull$$$0(2);
            }
            return psiTypeCreateIntersection2;
        }
        if (psiType2 instanceof PsiIntersectionType) {
            return getLeastUpperBound(psiType2, psiType, set, psiManager);
        }
        if ((psiType instanceof PsiClassType) && (psiType2 instanceof PsiClassType)) {
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = ((PsiClassType) psiType).resolveGenerics();
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics2 = ((PsiClassType) psiType2).resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            PsiClass element2 = classResolveResultResolveGenerics2.getElement();
            if (element == null || element2 == null) {
                PsiClassType javaLangObject = PsiType.getJavaLangObject(psiManager, GlobalSearchScope.allScope(psiManager.getProject()));
                if (javaLangObject == null) {
                    $$$reportNull$$$0(3);
                }
                return javaLangObject;
            }
            PsiClass[] leastUpperClasses = getLeastUpperClasses(element, element2);
            if (leastUpperClasses.length == 0) {
                PsiClassType javaLangObject2 = PsiType.getJavaLangObject(psiManager, element.getResolveScope());
                if (javaLangObject2 == null) {
                    $$$reportNull$$$0(4);
                }
                return javaLangObject2;
            }
            PsiElementFactory elementFactory2 = JavaPsiFacade.getElementFactory(psiManager.getProject());
            PsiClassType[] psiClassTypeArr = new PsiClassType[leastUpperClasses.length];
            HashSet hashSet = new HashSet();
            while (i < leastUpperClasses.length) {
                try {
                    PsiClass psiClass = leastUpperClasses[i];
                    PsiSubstitutor superClassSubstitutor = TypeConversionUtil.getSuperClassSubstitutor(psiClass, element, classResolveResultResolveGenerics.getSubstitutor());
                    PsiSubstitutor superClassSubstitutor2 = TypeConversionUtil.getSuperClassSubstitutor(psiClass, element2, classResolveResultResolveGenerics2.getSubstitutor());
                    PsiSubstitutor psiSubstitutor = PsiSubstitutor.EMPTY;
                    PsiClassType.ClassResolveResult classResolveResult = classResolveResultResolveGenerics;
                    Couple<PsiType> coupleOf = Couple.of(elementFactory2.createType(psiClass, superClassSubstitutor), elementFactory2.createType(psiClass, superClassSubstitutor2));
                    boolean zContains = set.contains(coupleOf);
                    for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(psiClass)) {
                        PsiClassType.ClassResolveResult classResolveResult2 = classResolveResultResolveGenerics2;
                        PsiClass psiClass2 = element;
                        PsiType psiTypeSubstitute = superClassSubstitutor.substitute(psiTypeParameter);
                        PsiClass[] psiClassArr = leastUpperClasses;
                        PsiType psiTypeSubstitute2 = superClassSubstitutor2.substitute(psiTypeParameter);
                        if (psiTypeSubstitute == null || psiTypeSubstitute2 == null) {
                            psiSubstitutorPut = psiSubstitutor.put(psiTypeParameter, null);
                        } else if (zContains) {
                            psiSubstitutorPut = psiSubstitutor.put(psiTypeParameter, PsiWildcardType.createUnbounded(psiManager));
                        } else {
                            set.add(coupleOf);
                            try {
                                psiSubstitutorPut = psiSubstitutor.put(psiTypeParameter, getLeastContainingTypeArgument(psiTypeSubstitute, psiTypeSubstitute2, set, psiManager));
                                hashSet.add(coupleOf);
                            } catch (Throwable th) {
                                hashSet.add(coupleOf);
                                throw th;
                            }
                        }
                        psiSubstitutor = psiSubstitutorPut;
                        classResolveResultResolveGenerics2 = classResolveResult2;
                        element = psiClass2;
                        leastUpperClasses = psiClassArr;
                    }
                    PsiClassType.ClassResolveResult classResolveResult3 = classResolveResultResolveGenerics2;
                    PsiClass psiClass3 = element;
                    PsiClass[] psiClassArr2 = leastUpperClasses;
                    psiClassTypeArr[i] = elementFactory2.createType(psiClass, psiSubstitutor);
                    i++;
                    classResolveResultResolveGenerics = classResolveResult;
                    classResolveResultResolveGenerics2 = classResolveResult3;
                    element = psiClass3;
                    leastUpperClasses = psiClassArr2;
                } catch (Throwable th2) {
                    set.removeAll(hashSet);
                    throw th2;
                }
            }
            set.removeAll(hashSet);
            PsiType psiTypeCreateIntersection3 = PsiIntersectionType.createIntersection(psiClassTypeArr);
            if (psiTypeCreateIntersection3 == null) {
                $$$reportNull$$$0(5);
            }
            return psiTypeCreateIntersection3;
        }
        if (psiType2 instanceof PsiArrayType) {
            return getLeastUpperBound(psiType2, psiType, set, psiManager);
        }
        if (z) {
            PsiElementFactory elementFactory3 = JavaPsiFacade.getElementFactory(psiManager.getProject());
            GlobalSearchScope globalSearchScopeAllScope2 = GlobalSearchScope.allScope(psiManager.getProject());
            return getLeastUpperBound(PsiIntersectionType.createIntersection(elementFactory3.createTypeByFQClassName(CommonClassNames.JAVA_IO_SERIALIZABLE, globalSearchScopeAllScope2), elementFactory3.createTypeByFQClassName(CommonClassNames.JAVA_LANG_CLONEABLE, globalSearchScopeAllScope2)), psiType2, set, psiManager);
        }
        PsiClassType javaLangObject3 = PsiType.getJavaLangObject(psiManager, GlobalSearchScope.allScope(psiManager.getProject()));
        if (javaLangObject3 == null) {
            $$$reportNull$$$0(6);
        }
        return javaLangObject3;
    }

    private static PsiType getLeastContainingTypeArgument(PsiType psiType, PsiType psiType2, Set<Couple<PsiType>> set, PsiManager psiManager) {
        if (psiType instanceof PsiWildcardType) {
            PsiWildcardType psiWildcardType = (PsiWildcardType) psiType;
            PsiType bound = psiWildcardType.getBound();
            if (bound == null) {
                return psiType;
            }
            if (psiType2 instanceof PsiWildcardType) {
                PsiWildcardType psiWildcardType2 = (PsiWildcardType) psiType2;
                PsiType bound2 = psiWildcardType2.getBound();
                if (bound2 == null) {
                    return psiType2;
                }
                if (psiWildcardType.isExtends() != psiWildcardType2.isExtends()) {
                    return bound.equals(bound2) ? bound : PsiWildcardType.createUnbounded(psiManager);
                }
                if (psiWildcardType.isExtends()) {
                    return PsiWildcardType.createExtends(psiManager, getLeastUpperBound(bound, bound2, set, psiManager));
                }
                return PsiWildcardType.createSuper(psiManager, getGreatestLowerBound(bound, bound2));
            }
            if (psiWildcardType.isExtends()) {
                return PsiWildcardType.createExtends(psiManager, getLeastUpperBound(bound, psiType2, set, psiManager));
            }
            return psiWildcardType.isSuper() ? PsiWildcardType.createSuper(psiManager, getGreatestLowerBound(bound, psiType2)) : psiWildcardType;
        }
        if (psiType2 instanceof PsiWildcardType) {
            return getLeastContainingTypeArgument(psiType2, psiType, set, psiManager);
        }
        return psiType.equals(psiType2) ? psiType : PsiWildcardType.createExtends(psiManager, getLeastUpperBound(psiType, psiType2, set, psiManager));
    }

    public static PsiClass[] getLeastUpperClasses(PsiClass psiClass, PsiClass psiClass2) {
        if (InheritanceUtil.isInheritorOrSelf(psiClass, psiClass2, true)) {
            return new PsiClass[]{psiClass2};
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        getLeastUpperClassesInner(psiClass, psiClass2, linkedHashSet, new HashSet());
        PsiClass[] psiClassArr = (PsiClass[]) linkedHashSet.toArray(PsiClass.EMPTY_ARRAY);
        if (psiClassArr == null) {
            $$$reportNull$$$0(8);
        }
        return psiClassArr;
    }

    private static void getLeastUpperClassesInner(PsiClass psiClass, PsiClass psiClass2, Set<PsiClass> set, Set<? super PsiClass> set2) {
        if (psiClass2.isInheritor(psiClass, true)) {
            addSuper(set, psiClass);
            return;
        }
        for (PsiClass psiClass3 : psiClass.getSupers()) {
            if (set2.add(psiClass3)) {
                getLeastUpperClassesInner(psiClass3, psiClass2, set, set2);
            }
        }
    }

    private static void addSuper(Set<PsiClass> set, PsiClass psiClass) {
        Iterator<PsiClass> it = set.iterator();
        while (it.hasNext()) {
            PsiClass next = it.next();
            if (InheritanceUtil.isInheritorOrSelf(next, psiClass, true)) {
                return;
            }
            if (psiClass.isInheritor(next, true)) {
                it.remove();
            }
        }
        set.add(psiClass);
    }

    public static boolean isTypeArgumentsApplicable(PsiTypeParameter[] psiTypeParameterArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement) {
        return isTypeArgumentsApplicable(psiTypeParameterArr, psiSubstitutor, psiElement, true);
    }

    public static boolean isTypeArgumentsApplicable(PsiTypeParameter[] psiTypeParameterArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement, boolean z) {
        return findTypeParameterWithBoundError(psiTypeParameterArr, psiSubstitutor, psiElement, z) == null;
    }

    public static Pair<PsiTypeParameter, PsiType> findTypeParameterWithBoundError(PsiTypeParameter[] psiTypeParameterArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement, boolean z) {
        for (PsiTypeParameter psiTypeParameter : psiTypeParameterArr) {
            PsiType psiTypeFindTypeParameterBoundError = findTypeParameterBoundError(psiTypeParameter, psiTypeParameter.getExtendsListTypes(), psiSubstitutor, psiElement, z);
            if (psiTypeFindTypeParameterBoundError != null) {
                return Pair.create(psiTypeParameter, psiTypeFindTypeParameterBoundError);
            }
        }
        return null;
    }

    public static PsiType findTypeParameterBoundError(PsiTypeParameter psiTypeParameter, PsiType[] psiTypeArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement, boolean z) {
        PsiType psiTypeSubstitute = psiSubstitutor.substitute(psiTypeParameter);
        if (psiTypeSubstitute == null) {
            return null;
        }
        if (psiElement != null) {
            psiTypeSubstitute = PsiUtil.captureToplevelWildcards(psiTypeSubstitute, psiElement);
        }
        if ((psiTypeSubstitute instanceof PsiWildcardType) && ((PsiWildcardType) psiTypeSubstitute).isSuper()) {
            return null;
        }
        for (PsiType psiType : psiTypeArr) {
            PsiType psiTypeSubstitute2 = psiSubstitutor.substitute(psiType);
            if (psiTypeSubstitute2 != null && !TypeConversionUtil.isAssignable(psiTypeSubstitute2, psiTypeSubstitute, z)) {
                return psiTypeSubstitute2;
            }
        }
        return null;
    }

    public static PsiType getVariableTypeByExpressionType(PsiType psiType) {
        if (psiType == null) {
            return null;
        }
        return getVariableTypeByExpressionType(psiType, true);
    }

    public static PsiType getVariableTypeByExpressionType(PsiType psiType, final boolean z) {
        if (psiType == null) {
            $$$reportNull$$$0(9);
        }
        PsiClass psiClassResolveClassInType = PsiUtil.resolveClassInType(psiType);
        if (psiClassResolveClassInType instanceof PsiAnonymousClass) {
            psiType = ((PsiAnonymousClass) psiClassResolveClassInType).getBaseClassType();
        }
        PsiType deepComponentType = psiType.getDeepComponentType();
        if (deepComponentType instanceof PsiCapturedWildcardType) {
            psiType = PsiTypesUtil.createArrayType(((PsiCapturedWildcardType) deepComponentType).getUpperBound(), psiType.getArrayDimensions());
        }
        PsiType psiType2 = (PsiType) psiType.accept(new PsiTypeVisitor<PsiType>() { // from class: org.jetbrains.kotlin.com.intellij.psi.GenericsUtil.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = ModuleXmlParser.TYPE;
                } else if (i == 2) {
                    objArr[0] = "wildcardType";
                } else if (i == 3) {
                    objArr[0] = "capturedWildcardType";
                } else if (i != 4) {
                    objArr[0] = "arrayType";
                } else {
                    objArr[0] = "classType";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/GenericsUtil$1";
                if (i == 1) {
                    objArr[2] = "visitType";
                } else if (i == 2) {
                    objArr[2] = "visitWildcardType";
                } else if (i == 3) {
                    objArr[2] = "visitCapturedWildcardType";
                } else if (i != 4) {
                    objArr[2] = "visitArrayType";
                } else {
                    objArr[2] = "visitClassType";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitType(PsiType psiType3) {
                if (psiType3 == null) {
                    $$$reportNull$$$0(1);
                }
                return psiType3;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitArrayType(PsiArrayType psiArrayType) {
                if (psiArrayType == null) {
                    $$$reportNull$$$0(0);
                }
                PsiType componentType = psiArrayType.getComponentType();
                PsiType bound = (PsiType) componentType.accept(this);
                if (bound == componentType) {
                    return psiArrayType;
                }
                if (bound instanceof PsiWildcardType) {
                    bound = ((PsiWildcardType) bound).getBound();
                }
                return bound != null ? bound.createArrayType().annotate(psiArrayType.getAnnotationProvider()) : psiArrayType;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitWildcardType(PsiWildcardType psiWildcardType) {
                if (psiWildcardType == null) {
                    $$$reportNull$$$0(2);
                }
                PsiType bound = psiWildcardType.getBound();
                PsiManager manager = psiWildcardType.getManager();
                if (bound == null) {
                    return psiWildcardType;
                }
                if (psiWildcardType.isSuper() && (bound instanceof PsiIntersectionType)) {
                    return PsiWildcardType.createUnbounded(manager);
                }
                PsiType psiType3 = (PsiType) bound.accept(this);
                if (psiType3 instanceof PsiWildcardType) {
                    return ((PsiWildcardType) psiType3).isExtends() != psiWildcardType.isExtends() ? PsiWildcardType.createUnbounded(manager) : psiType3;
                }
                if (psiWildcardType.isExtends() && psiType3.equalsToText(CommonClassNames.JAVA_LANG_OBJECT)) {
                    return PsiWildcardType.createUnbounded(manager);
                }
                if (psiType3.equals(bound)) {
                    return psiWildcardType;
                }
                if (psiWildcardType.isExtends()) {
                    return PsiWildcardType.createExtends(manager, psiType3);
                }
                return PsiWildcardType.createSuper(manager, psiType3);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitCapturedWildcardType(PsiCapturedWildcardType psiCapturedWildcardType) {
                if (psiCapturedWildcardType == null) {
                    $$$reportNull$$$0(3);
                }
                return z ? (PsiType) psiCapturedWildcardType.getWildcard().accept(this) : psiCapturedWildcardType;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public PsiType visitClassType(PsiClassType psiClassType) {
                PsiType psiTypeCreateExtends;
                if (psiClassType == null) {
                    $$$reportNull$$$0(4);
                }
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
                PsiClass element = classResolveResultResolveGenerics.getElement();
                if (element == null) {
                    return psiClassType;
                }
                PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
                boolean z2 = false;
                for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(element)) {
                    PsiType psiTypeSubstitute = classResolveResultResolveGenerics.getSubstitutor().substitute(psiTypeParameter);
                    boolean z3 = true;
                    if (psiTypeSubstitute instanceof PsiCapturedWildcardType) {
                        z2 = true;
                    }
                    if ((psiTypeSubstitute instanceof PsiWildcardType) && (((PsiWildcardType) psiTypeSubstitute).getBound() instanceof PsiIntersectionType)) {
                        z2 = true;
                    }
                    if (psiTypeSubstitute == null) {
                        psiTypeCreateExtends = null;
                    } else {
                        PsiType psiType3 = (PsiType) psiTypeSubstitute.accept(this);
                        psiTypeCreateExtends = (!(psiTypeSubstitute instanceof PsiIntersectionType) || (psiType3 instanceof PsiWildcardType)) ? psiType3 : PsiWildcardType.createExtends(psiTypeParameter.getManager(), psiType3);
                    }
                    Logger logger = GenericsUtil.LOG;
                    if (psiTypeCreateExtends != null && !psiTypeCreateExtends.isValid()) {
                        z3 = false;
                    }
                    logger.assertTrue(z3, psiTypeCreateExtends);
                    psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter, psiTypeCreateExtends);
                }
                PsiAnnotation[] applicableAnnotations = psiClassType.getApplicableAnnotations();
                if (psiSubstitutorPut == PsiSubstitutor.EMPTY && !z2 && applicableAnnotations.length == 0 && !(element instanceof PsiTypeParameter)) {
                    return psiClassType;
                }
                PsiManager manager = element.getManager();
                PsiClassType psiClassTypeAnnotate = JavaPsiFacade.getElementFactory(manager.getProject()).createType(element, psiSubstitutorPut, PsiUtil.getLanguageLevel(element)).annotate(TypeAnnotationProvider.Static.create(applicableAnnotations));
                return z2 ? PsiWildcardType.createExtends(manager, psiClassTypeAnnotate) : psiClassTypeAnnotate;
            }
        });
        PsiType deepComponentType2 = psiType2.getDeepComponentType();
        if (deepComponentType2 instanceof PsiWildcardType) {
            PsiType psiTypeCreateArrayType = PsiTypesUtil.createArrayType(((PsiWildcardType) deepComponentType2).getExtendsBound(), psiType2.getArrayDimensions());
            if (psiTypeCreateArrayType == null) {
                $$$reportNull$$$0(10);
            }
            return psiTypeCreateArrayType;
        }
        if (!(psiType2 instanceof PsiEllipsisType)) {
            if (psiType2 == null) {
                $$$reportNull$$$0(12);
            }
            return psiType2;
        }
        PsiType arrayType = ((PsiEllipsisType) psiType2).toArrayType();
        if (arrayType == null) {
            $$$reportNull$$$0(11);
        }
        return arrayType;
    }

    public static PsiSubstitutor substituteByParameterName(PsiClass psiClass, PsiSubstitutor psiSubstitutor) {
        Map<PsiTypeParameter, PsiType> substitutionMap = psiSubstitutor.getSubstitutionMap();
        ArrayList arrayList = new ArrayList(substitutionMap.size());
        for (PsiTypeParameter psiTypeParameter : psiClass.getTypeParameters()) {
            final String name2 = psiTypeParameter.getMyName();
            PsiTypeParameter psiTypeParameter2 = (PsiTypeParameter) ContainerUtil.find(substitutionMap.keySet(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.GenericsUtil$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return name2.equals(((PsiTypeParameter) obj).getMyName());
                }
            });
            if (psiTypeParameter2 != null) {
                arrayList.add(substitutionMap.get(psiTypeParameter2));
            }
        }
        return PsiSubstitutor.EMPTY.putAll(psiClass, (PsiType[]) arrayList.toArray(PsiType.createArray(arrayList.size())));
    }

    public static PsiType eliminateWildcards(PsiType psiType) {
        return eliminateWildcards(psiType, true);
    }

    public static PsiType eliminateWildcards(PsiType psiType, boolean z) {
        return eliminateWildcards(psiType, z, !z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static PsiType eliminateWildcards(PsiType psiType, boolean z, boolean z2) {
        if (z && (psiType instanceof PsiClassType)) {
            PsiClassType psiClassType = (PsiClassType) psiType;
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
            PsiClass psiClass = (PsiClass) classResolveResultResolveGenerics.getElement();
            if (psiClass == null) {
                return psiType;
            }
            PsiManager manager = psiClass.getManager();
            PsiTypeParameter[] typeParameters = psiClass.getTypeParameters();
            Map<PsiTypeParameter, PsiType> map = new HashMap<>();
            for (PsiTypeParameter psiTypeParameter : typeParameters) {
                PsiType psiTypeSubstitute = classResolveResultResolveGenerics.getSubstitutor().substitute(psiTypeParameter);
                if (psiTypeSubstitute instanceof PsiWildcardType) {
                    psiTypeSubstitute = ((PsiWildcardType) psiTypeSubstitute).getBound();
                    if (psiTypeSubstitute instanceof PsiCapturedWildcardType) {
                        psiTypeSubstitute = ((PsiCapturedWildcardType) psiTypeSubstitute).getWildcard().getBound();
                    }
                    if (psiTypeSubstitute == null) {
                        psiTypeSubstitute = TypeConversionUtil.typeParameterErasure(psiTypeParameter);
                    }
                }
                map.put(psiTypeParameter, psiTypeSubstitute);
            }
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(manager.getProject());
            return elementFactory.createType(psiClass, elementFactory.createSubstitutor(map)).annotate(psiClassType.getAnnotationProvider());
        }
        if (psiType instanceof PsiArrayType) {
            PsiType psiTypeEliminateWildcards = eliminateWildcards(((PsiArrayType) psiType).getComponentType(), false);
            return (psiType instanceof PsiEllipsisType ? new PsiEllipsisType(psiTypeEliminateWildcards) : new PsiArrayType(psiTypeEliminateWildcards)).annotate(psiType.getAnnotationProvider());
        }
        if (!(psiType instanceof PsiWildcardType)) {
            return ((psiType instanceof PsiCapturedWildcardType) && z2) ? eliminateWildcards(((PsiCapturedWildcardType) psiType).getUpperBound(), false) : psiType;
        }
        PsiWildcardType psiWildcardType = (PsiWildcardType) psiType;
        PsiType bound = psiWildcardType.getBound();
        if (bound == null) {
            bound = psiWildcardType.getExtendsBound();
        }
        return eliminateWildcards(bound, false);
    }

    public static boolean checkNotInBounds(PsiType psiType, PsiType psiType2, PsiReferenceParameterList psiReferenceParameterList) {
        return checkNotInBounds(PsiUtil.captureToplevelWildcards(psiType, psiReferenceParameterList), psiType2, PsiTreeUtil.getParentOfType(psiReferenceParameterList, PsiCallExpression.class) != null);
    }

    public static boolean checkNotInBounds(PsiType psiType, PsiType psiType2, boolean z) {
        if (psiType instanceof PsiClassType) {
            return checkNotAssignable(psiType2, psiType, z);
        }
        if (psiType instanceof PsiWildcardType) {
            PsiWildcardType psiWildcardType = (PsiWildcardType) psiType;
            if (psiWildcardType.isExtends()) {
                return checkExtendsWildcardCaptureFailure(psiWildcardType, psiType2);
            }
            if (psiWildcardType.isSuper()) {
                PsiType superBound = psiWildcardType.getSuperBound();
                return PsiUtil.resolveClassInType(superBound) instanceof PsiTypeParameter ? TypesDistinctProver.provablyDistinct(psiType, psiType2) : checkNotAssignable(psiType2, superBound, false);
            }
        } else {
            if (psiType instanceof PsiArrayType) {
                return checkNotAssignable(psiType2, psiType, true);
            }
            if (psiType instanceof PsiIntersectionType) {
                for (PsiType psiType3 : ((PsiIntersectionType) psiType).getConjuncts()) {
                    if (!checkNotInBounds(psiType3, psiType2, z)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static boolean checkExtendsWildcardCaptureFailure(PsiWildcardType psiWildcardType, PsiType psiType) {
        LOG.assertTrue(psiWildcardType.isExtends());
        PsiType extendsBound = psiWildcardType.getExtendsBound();
        if (psiType instanceof PsiWildcardType) {
            PsiWildcardType psiWildcardType2 = (PsiWildcardType) psiType;
            if (!psiWildcardType2.isBounded()) {
                return false;
            }
            if (psiWildcardType2.isSuper()) {
                psiType = psiWildcardType2.getSuperBound();
            } else {
                psiType = psiWildcardType2.getExtendsBound();
            }
        }
        PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(extendsBound);
        PsiClass psiClassResolveClassInClassTypeOnly2 = PsiUtil.resolveClassInClassTypeOnly(psiType);
        if (psiClassResolveClassInClassTypeOnly2 == null || psiClassResolveClassInClassTypeOnly == null || psiClassResolveClassInClassTypeOnly2.isInterface() || psiClassResolveClassInClassTypeOnly.isInterface()) {
            return (TypeConversionUtil.areTypesConvertible(psiType, extendsBound) || TypeConversionUtil.areTypesConvertible(extendsBound, psiType)) ? false : true;
        }
        return (InheritanceUtil.isInheritorOrSelf(psiClassResolveClassInClassTypeOnly2, psiClassResolveClassInClassTypeOnly, true) || InheritanceUtil.isInheritorOrSelf(psiClassResolveClassInClassTypeOnly, psiClassResolveClassInClassTypeOnly2, true)) ? false : true;
    }

    private static boolean checkNotAssignable(PsiType psiType, PsiType psiType2, boolean z) {
        PsiType superBound;
        if (psiType instanceof PsiWildcardType) {
            PsiWildcardType psiWildcardType = (PsiWildcardType) psiType;
            if (!psiWildcardType.isBounded()) {
                return true;
            }
            if (psiWildcardType.isExtends()) {
                superBound = psiWildcardType.getExtendsBound();
            } else {
                superBound = psiWildcardType.getSuperBound();
            }
            return !TypeConversionUtil.isAssignable(superBound, psiType2, z);
        }
        return !TypeConversionUtil.isAssignable(psiType, psiType2, z);
    }

    public static PsiClassType getExpectedGenericType(PsiElement psiElement, PsiClass psiClass, PsiClassType psiClassType) {
        PsiClassType psiClassTypeCreateType = JavaPsiFacade.getElementFactory(psiElement.getProject()).createType(psiClass, (PsiType[]) getExpectedTypeArguments(psiElement, psiClass, Arrays.asList(psiClass.getTypeParameters()), psiClassType).toArray(PsiType.EMPTY_ARRAY));
        if (psiClassTypeCreateType == null) {
            $$$reportNull$$$0(13);
        }
        return psiClassTypeCreateType;
    }

    public static List<PsiType> getExpectedTypeArguments(final PsiElement psiElement, PsiClass psiClass, Iterable<? extends PsiTypeParameter> iterable, PsiClassType psiClassType) {
        if (iterable == null) {
            $$$reportNull$$$0(14);
        }
        if (psiClassType == null) {
            $$$reportNull$$$0(15);
        }
        final PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        if (!InheritanceUtil.isInheritorOrSelf(psiClass, element, true)) {
            List<PsiType> map = ContainerUtil.map(iterable, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.GenericsUtil$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return GenericsUtil.lambda$getExpectedTypeArguments$1((PsiTypeParameter) obj);
                }
            });
            if (map == null) {
                $$$reportNull$$$0(16);
            }
            return map;
        }
        final PsiSubstitutor classSubstitutor = TypeConversionUtil.getClassSubstitutor(element, psiClass, PsiSubstitutor.EMPTY);
        List<PsiType> map2 = ContainerUtil.map(iterable, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.GenericsUtil$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return GenericsUtil.getExpectedTypeArg(psiElement, classResolveResultResolveGenerics, classSubstitutor, (PsiTypeParameter) obj);
            }
        });
        if (map2 == null) {
            $$$reportNull$$$0(17);
        }
        return map2;
    }

    static /* synthetic */ PsiType lambda$getExpectedTypeArguments$1(PsiTypeParameter psiTypeParameter) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiType getExpectedTypeArg(PsiElement psiElement, PsiClassType.ClassResolveResult classResolveResult, PsiSubstitutor psiSubstitutor, PsiTypeParameter psiTypeParameter) {
        for (PsiTypeParameter psiTypeParameter2 : PsiUtil.typeParametersIterable(classResolveResult.getElement())) {
            PsiType psiTypeSubstitute = psiSubstitutor.substitute(psiTypeParameter2);
            PsiClass psiClassResolveClassInType = PsiUtil.resolveClassInType(psiTypeSubstitute);
            if (!(psiClassResolveClassInType instanceof PsiTypeParameter) || ((PsiTypeParameter) psiClassResolveClassInType).getOwner() != psiTypeParameter.getOwner() || psiClassResolveClassInType == psiTypeParameter) {
                PsiType substitutionForTypeParameter = JavaPsiFacade.getInstance(psiElement.getProject()).getResolveHelper().getSubstitutionForTypeParameter(psiTypeParameter, psiTypeSubstitute, classResolveResult.getSubstitutor().substitute(psiTypeParameter2), true, PsiUtil.getLanguageLevel(psiElement));
                if (substitutionForTypeParameter != null && substitutionForTypeParameter != PsiType.NULL) {
                    return substitutionForTypeParameter;
                }
            }
        }
        return null;
    }

    public static boolean isGenericReference(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, PsiJavaCodeReferenceElement psiJavaCodeReferenceElement2) {
        PsiReferenceParameterList parameterList = psiJavaCodeReferenceElement2.getParameterList();
        if (parameterList != null && parameterList.getTypeParameterElements().length > 0) {
            return true;
        }
        PsiReferenceParameterList parameterList2 = psiJavaCodeReferenceElement.getParameterList();
        return parameterList2 != null && parameterList2.getTypeParameterElements().length > 0;
    }

    public static PsiType eliminateExtendsFinalWildcard(PsiType psiType) {
        PsiClass psiClassResolveClassInClassTypeOnly;
        if (psiType == null) {
            $$$reportNull$$$0(18);
        }
        if (!(psiType instanceof PsiClassType)) {
            if (psiType == null) {
                $$$reportNull$$$0(19);
            }
            return psiType;
        }
        PsiClassType psiClassType = (PsiClassType) psiType;
        PsiType[] parameters = psiClassType.getParameters();
        boolean z = false;
        for (int i = 0; i < parameters.length; i++) {
            PsiType psiType2 = parameters[i];
            if (psiType2 instanceof PsiWildcardType) {
                PsiWildcardType psiWildcardType = (PsiWildcardType) psiType2;
                PsiClassType psiClassType2 = (PsiClassType) ObjectUtils.tryCast(psiWildcardType.getBound(), PsiClassType.class);
                if (psiClassType2 != null && psiWildcardType.isExtends() && (psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(psiClassType2)) != null && psiClassResolveClassInClassTypeOnly.hasModifierProperty("final")) {
                    parameters[i] = psiClassType2;
                    z = true;
                }
            }
        }
        if (!z) {
            if (psiType == null) {
                $$$reportNull$$$0(20);
            }
            return psiType;
        }
        PsiClass psiClassResolve = psiClassType.resolve();
        if (psiClassResolve == null) {
            if (psiClassType == null) {
                $$$reportNull$$$0(21);
            }
            return psiClassType;
        }
        PsiClassType psiClassTypeAnnotate = JavaPsiFacade.getElementFactory(psiClassResolve.getProject()).createType(psiClassResolve, parameters).annotate(psiClassType.getAnnotationProvider());
        if (psiClassTypeAnnotate == null) {
            $$$reportNull$$$0(22);
        }
        return psiClassTypeAnnotate;
    }
}
