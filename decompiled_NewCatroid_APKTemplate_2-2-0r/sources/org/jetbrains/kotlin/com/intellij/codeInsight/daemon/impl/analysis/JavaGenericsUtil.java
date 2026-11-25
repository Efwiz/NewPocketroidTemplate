package org.jetbrains.kotlin.com.intellij.codeInsight.daemon.impl.analysis;

import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaGenericsUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "resolveResult";
        } else if (i == 2) {
            objArr[0] = "languageLevel";
        } else if (i == 3) {
            objArr[0] = "castType";
        } else if (i == 4) {
            objArr[0] = "operandType";
        } else if (i != 6) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "scope";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/daemon/impl/analysis/JavaGenericsUtil";
        if (i == 3 || i == 4) {
            objArr[2] = "isUncheckedCast";
        } else if (i == 5 || i == 6) {
            objArr[2] = "getCollectionItemType";
        } else {
            objArr[2] = "isUncheckedWarning";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static boolean isReifiableType(PsiType psiType) {
        if (psiType instanceof PsiArrayType) {
            return isReifiableType(((PsiArrayType) psiType).getComponentType());
        }
        if (psiType instanceof PsiPrimitiveType) {
            return true;
        }
        if (PsiUtil.resolveClassInType(psiType) instanceof PsiTypeParameter) {
            return false;
        }
        if (psiType instanceof PsiClassType) {
            PsiClassType psiClassType = (PsiClassType) PsiUtil.convertAnonymousToBaseType(psiType);
            if (psiClassType.isRaw()) {
                return true;
            }
            PsiType[] parameters = psiClassType.getParameters();
            if (parameters.length > 0) {
                for (PsiType psiType2 : parameters) {
                    if (!(psiType2 instanceof PsiWildcardType) || ((PsiWildcardType) psiType2).getBound() != null) {
                        return false;
                    }
                }
                return true;
            }
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            if (element instanceof PsiTypeParameter) {
                return false;
            }
            if (element != null && !element.hasModifierProperty("static")) {
                PsiModifierListOwner enclosingStaticElement = PsiUtil.getEnclosingStaticElement(element, PsiUtil.isLocalClass(element) ? null : element.getContainingClass());
                PsiClass psiClass = (PsiClass) PsiTreeUtil.getParentOfType(element, PsiClass.class, true);
                if (psiClass != null && (enclosingStaticElement == null || PsiTreeUtil.isAncestor(enclosingStaticElement, psiClass, false))) {
                    while (psiClass instanceof PsiAnonymousClass) {
                        psiClass = (PsiClass) PsiTreeUtil.getParentOfType(psiClass, PsiClass.class, true);
                    }
                    if (psiClass != null && (enclosingStaticElement == null || PsiTreeUtil.isAncestor(enclosingStaticElement, psiClass, false))) {
                        return isReifiableType(JavaPsiFacade.getElementFactory(element.getProject()).createType(psiClass, classResolveResultResolveGenerics.getSubstitutor()));
                    }
                }
            }
            return true;
        }
        if (psiType instanceof PsiCapturedWildcardType) {
            return isReifiableType(((PsiCapturedWildcardType) psiType).getUpperBound());
        }
        return false;
    }

    public static boolean isRawToGeneric(PsiType psiType, PsiType psiType2) {
        if ((psiType instanceof PsiPrimitiveType) || (psiType2 instanceof PsiPrimitiveType) || psiType.equals(psiType2)) {
            return false;
        }
        boolean z = psiType instanceof PsiArrayType;
        if (z && (psiType2 instanceof PsiArrayType)) {
            return isRawToGeneric(((PsiArrayType) psiType).getComponentType(), ((PsiArrayType) psiType2).getComponentType());
        }
        if (!z && !(psiType2 instanceof PsiArrayType)) {
            if (psiType2 instanceof PsiIntersectionType) {
                for (PsiType psiType3 : ((PsiIntersectionType) psiType2).getConjuncts()) {
                    if (isRawToGeneric(psiType, psiType3)) {
                        return true;
                    }
                }
                return false;
            }
            if (psiType instanceof PsiIntersectionType) {
                for (PsiType psiType4 : ((PsiIntersectionType) psiType).getConjuncts()) {
                    if (isRawToGeneric(psiType4, psiType2)) {
                        return true;
                    }
                }
                return false;
            }
            if (psiType2 instanceof PsiCapturedWildcardType) {
                return isRawToGeneric(psiType, ((PsiCapturedWildcardType) psiType2).getUpperBound());
            }
            if ((psiType instanceof PsiClassType) && (psiType2 instanceof PsiClassType)) {
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics = ((PsiClassType) psiType).resolveGenerics();
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics2 = ((PsiClassType) psiType2).resolveGenerics();
                PsiClass element = classResolveResultResolveGenerics.getElement();
                PsiClass element2 = classResolveResultResolveGenerics2.getElement();
                if (element2 instanceof PsiAnonymousClass) {
                    return isRawToGeneric(psiType, ((PsiAnonymousClass) element2).getBaseClassType());
                }
                PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
                PsiSubstitutor substitutor2 = classResolveResultResolveGenerics2.getSubstitutor();
                if (element != null && element2 != null) {
                    if ((element instanceof PsiTypeParameter) && !InheritanceUtil.isInheritorOrSelf(element2, element, true)) {
                        return true;
                    }
                    if (!element.getManager().areElementsEquivalent(element, element2)) {
                        if (element.isInheritor(element2, true)) {
                            substitutor = TypeConversionUtil.getSuperClassSubstitutor(element2, element, substitutor);
                            element = element2;
                        } else {
                            if (!element2.isInheritor(element, true)) {
                                return false;
                            }
                            substitutor2 = TypeConversionUtil.getSuperClassSubstitutor(element, element2, substitutor2);
                            element2 = element;
                        }
                    }
                    Iterator<PsiTypeParameter> itTypeParametersIterator = PsiUtil.typeParametersIterator(element);
                    Iterator<PsiTypeParameter> itTypeParametersIterator2 = PsiUtil.typeParametersIterator(element2);
                    while (itTypeParametersIterator.hasNext() && itTypeParametersIterator2.hasNext()) {
                        PsiTypeParameter next = itTypeParametersIterator.next();
                        PsiTypeParameter next2 = itTypeParametersIterator2.next();
                        PsiType psiTypeSubstitute = substitutor.substitute(next);
                        PsiType psiTypeSubstituteWithBoundsPromotion = substitutor2.substituteWithBoundsPromotion(next2);
                        if (psiTypeSubstitute != null) {
                            if (psiTypeSubstituteWithBoundsPromotion == null) {
                                if (!(psiTypeSubstitute instanceof PsiWildcardType) || ((PsiWildcardType) psiTypeSubstitute).getBound() != null) {
                                    return true;
                                }
                            } else if (!TypeConversionUtil.typesAgree(psiTypeSubstitute, psiTypeSubstituteWithBoundsPromotion, true)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return false;
    }

    public static PsiType getCollectionItemType(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(5);
        }
        return getCollectionItemType(psiExpression.getType(), psiExpression.getResolveScope());
    }

    public static PsiType getCollectionItemType(PsiType psiType, GlobalSearchScope globalSearchScope) {
        PsiClass psiClass;
        PsiSubstitutor classSubstitutor;
        PsiClass psiClassFindClass;
        if (globalSearchScope == null) {
            $$$reportNull$$$0(6);
        }
        if (psiType instanceof PsiArrayType) {
            return ((PsiArrayType) psiType).getComponentType();
        }
        if (psiType instanceof PsiClassType) {
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = ((PsiClassType) psiType).resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            if (element == null) {
                return null;
            }
            PsiManager manager = element.getManager();
            String qualifiedName = element.getQualifiedName();
            PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
            JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(manager.getProject());
            if (qualifiedName != null && (psiClassFindClass = javaPsiFacade.findClass(qualifiedName, globalSearchScope)) != null && psiClassFindClass != element) {
                PsiTypeParameter iterableTypeParameter = getIterableTypeParameter(javaPsiFacade, psiClassFindClass);
                if (iterableTypeParameter == null) {
                    return null;
                }
                PsiTypeParameter iterableTypeParameter2 = getIterableTypeParameter(javaPsiFacade, element);
                if (iterableTypeParameter2 != null) {
                    substitutor = substitutor.put(iterableTypeParameter, substitutor.substitute(iterableTypeParameter2));
                }
                element = psiClassFindClass;
            }
            PsiTypeParameter iterableTypeParameter3 = getIterableTypeParameter(javaPsiFacade, element);
            if (iterableTypeParameter3 == null || (psiClass = (PsiClass) iterableTypeParameter3.getOwner()) == null || (classSubstitutor = TypeConversionUtil.getClassSubstitutor(psiClass, element, substitutor)) == null) {
                return null;
            }
            PsiType psiTypeSubstitute = classSubstitutor.substitute(iterableTypeParameter3);
            return psiTypeSubstitute == null ? PsiType.getJavaLangObject(manager, element.getResolveScope()) : psiTypeSubstitute;
        }
        if (psiType instanceof PsiIntersectionType) {
            for (PsiType psiType2 : ((PsiIntersectionType) psiType).getConjuncts()) {
                PsiType collectionItemType = getCollectionItemType(psiType2, globalSearchScope);
                if (collectionItemType != null) {
                    return collectionItemType;
                }
            }
        }
        if (psiType instanceof PsiCapturedWildcardType) {
            return getCollectionItemType(((PsiCapturedWildcardType) psiType).getUpperBound(), globalSearchScope);
        }
        return null;
    }

    private static PsiTypeParameter getIterableTypeParameter(JavaPsiFacade javaPsiFacade, PsiClass psiClass) {
        PsiClass psiClassFindClass = javaPsiFacade.findClass(CommonClassNames.JAVA_LANG_ITERABLE, psiClass.getResolveScope());
        if (psiClassFindClass == null) {
            return null;
        }
        PsiTypeParameter[] typeParameters = psiClassFindClass.getTypeParameters();
        if (typeParameters.length != 1) {
            return null;
        }
        return typeParameters[0];
    }
}
