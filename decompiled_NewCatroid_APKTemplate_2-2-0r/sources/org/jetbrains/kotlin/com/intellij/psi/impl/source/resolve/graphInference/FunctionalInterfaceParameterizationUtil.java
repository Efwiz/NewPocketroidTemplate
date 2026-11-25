package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference;

import java.util.HashSet;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.GenericsUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.TypeEqualityConstraint;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class FunctionalInterfaceParameterizationUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) FunctionalInterfaceParameterizationUtil.class);

    public static boolean isWildcardParameterized(PsiType psiType) {
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics;
        PsiClass element;
        PsiType psiTypeNormalizeFunctionalType = LambdaUtil.normalizeFunctionalType(psiType);
        if (psiTypeNormalizeFunctionalType != null && (psiTypeNormalizeFunctionalType instanceof PsiClassType) && (element = (classResolveResultResolveGenerics = ((PsiClassType) psiTypeNormalizeFunctionalType).resolveGenerics()).getElement()) != null) {
            PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
            for (PsiTypeParameter psiTypeParameter : element.getTypeParameters()) {
                if (substitutor.substitute(psiTypeParameter) instanceof PsiWildcardType) {
                    return true;
                }
            }
        }
        return false;
    }

    public static PsiType getGroundTargetType(PsiType psiType) {
        return getGroundTargetType(psiType, null);
    }

    public static PsiType getGroundTargetType(PsiType psiType, PsiLambdaExpression psiLambdaExpression) {
        return getGroundTargetType(psiType, psiLambdaExpression, true);
    }

    public static PsiType getGroundTargetType(PsiType psiType, PsiLambdaExpression psiLambdaExpression, boolean z) {
        PsiType psiTypeNormalizeFunctionalType = LambdaUtil.normalizeFunctionalType(psiType);
        if (!isWildcardParameterized(psiTypeNormalizeFunctionalType)) {
            return psiTypeNormalizeFunctionalType;
        }
        if (psiLambdaExpression != null && psiLambdaExpression.hasFormalParameterTypes()) {
            return getFunctionalTypeExplicit(psiTypeNormalizeFunctionalType, psiLambdaExpression, z);
        }
        if (psiTypeNormalizeFunctionalType instanceof PsiClassType) {
            return getNonWildcardParameterization((PsiClassType) psiTypeNormalizeFunctionalType);
        }
        return null;
    }

    private static PsiType getFunctionalTypeExplicit(PsiType psiType, PsiLambdaExpression psiLambdaExpression, boolean z) {
        PsiMethod functionalInterfaceMethod;
        PsiClass containingClass;
        PsiParameter[] parameters = psiLambdaExpression.getParameterList().getParameters();
        LOG.assertTrue(psiType instanceof PsiClassType, "Unexpected type: " + psiType);
        PsiClassType psiClassType = (PsiClassType) psiType;
        PsiType[] parameters2 = psiClassType.getParameters();
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        if (element == null || (functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(classResolveResultResolveGenerics)) == null || (containingClass = functionalInterfaceMethod.getContainingClass()) == null) {
            return null;
        }
        PsiTypeParameter[] typeParameters = element.getTypeParameters();
        if (typeParameters.length != parameters2.length) {
            return null;
        }
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(element.getProject());
        PsiParameter[] parameters3 = functionalInterfaceMethod.getParameterList().getParameters();
        if (parameters3.length != parameters.length) {
            return null;
        }
        PsiSubstitutor superClassSubstitutor = TypeConversionUtil.getSuperClassSubstitutor(containingClass, element, PsiSubstitutor.EMPTY);
        InferenceSession inferenceSession = new InferenceSession(typeParameters, PsiSubstitutor.EMPTY, psiLambdaExpression.getManager(), psiLambdaExpression);
        for (int i = 0; i < parameters3.length; i++) {
            inferenceSession.addConstraint(new TypeEqualityConstraint(parameters[i].getType(), inferenceSession.substituteWithInferenceVariables(superClassSubstitutor.substitute(parameters3[i].getType()))));
        }
        if (!inferenceSession.repeatInferencePhases()) {
            return null;
        }
        PsiSubstitutor instantiations = inferenceSession.getInstantiations(inferenceSession.getInferenceVariables());
        PsiType[] psiTypeArr = new PsiType[parameters2.length];
        for (int i2 = 0; i2 < typeParameters.length; i2++) {
            PsiTypeParameter psiTypeParameter = typeParameters[i2];
            if (instantiations.getSubstitutionMap().containsKey(psiTypeParameter)) {
                psiTypeArr[i2] = instantiations.substitute(psiTypeParameter);
            } else {
                psiTypeArr[i2] = parameters2[i2];
            }
        }
        PsiClassType psiClassTypeCreateType = elementFactory.createType(element, psiTypeArr);
        if (isWellFormed(element, typeParameters, psiTypeArr) && (!z || psiType.isAssignableFrom(psiClassTypeCreateType))) {
            return !isWildcardParameterized(psiClassTypeCreateType) ? psiClassTypeCreateType : getNonWildcardParameterization(psiClassTypeCreateType);
        }
        return null;
    }

    private static boolean isWellFormed(PsiClass psiClass, PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr) {
        PsiSubstitutor psiSubstitutorPutAll = PsiSubstitutor.EMPTY.putAll(psiClass, psiTypeArr);
        for (int i = 0; i < psiTypeParameterArr.length; i++) {
            for (PsiClassType psiClassType : psiTypeParameterArr[i].getExtendsListTypes()) {
                if (GenericsUtil.checkNotInBounds(psiTypeArr[i], psiSubstitutorPutAll.substitute(psiClassType), false)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static PsiType getNonWildcardParameterization(PsiClassType psiClassType) {
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        if (element == null) {
            return null;
        }
        PsiTypeParameter[] typeParameters = element.getTypeParameters();
        PsiType[] psiTypeArr = new PsiType[typeParameters.length];
        PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
        HashSet hashSetNewHashSet = ContainerUtil.newHashSet(typeParameters);
        for (int i = 0; i < typeParameters.length; i++) {
            PsiType psiTypeSubstitute = substitutor.substitute(typeParameters[i]);
            if (psiTypeSubstitute instanceof PsiWildcardType) {
                PsiWildcardType psiWildcardType = (PsiWildcardType) psiTypeSubstitute;
                PsiType bound = psiWildcardType.getBound();
                PsiClassType[] extendsListTypes = typeParameters[i].getExtendsListTypes();
                int length = extendsListTypes.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        if (PsiTypesUtil.mentionsTypeParameters(extendsListTypes[i2], hashSetNewHashSet)) {
                            if (bound != null) {
                                psiTypeArr[i] = bound;
                                break;
                            }
                            return null;
                        }
                        i2++;
                    } else {
                        if (psiWildcardType.isSuper()) {
                            psiTypeArr[i] = bound;
                            break;
                        }
                        if (bound == null) {
                            bound = PsiType.getJavaLangObject(element.getManager(), psiClassType.getResolveScope());
                        }
                        psiTypeArr[i] = bound;
                        for (PsiClassType psiClassType2 : typeParameters[i].getExtendsListTypes()) {
                            psiTypeArr[i] = GenericsUtil.getGreatestLowerBound(psiTypeArr[i], psiClassType2);
                        }
                        break;
                    }
                }
            } else {
                psiTypeArr[i] = psiTypeSubstitute;
            }
        }
        if (!isWellFormed(element, typeParameters, psiTypeArr)) {
            return null;
        }
        PsiClassType psiClassTypeCreateType = JavaPsiFacade.getElementFactory(element.getProject()).createType(element, psiTypeArr);
        if (psiClassType.isAssignableFrom(psiClassTypeCreateType)) {
            return psiClassTypeCreateType;
        }
        return null;
    }
}
