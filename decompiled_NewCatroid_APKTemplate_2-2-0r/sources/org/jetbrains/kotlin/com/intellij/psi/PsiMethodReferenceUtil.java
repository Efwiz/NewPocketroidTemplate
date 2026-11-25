package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiMethodReferenceUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiMethodReferenceUtil.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 2 || i == 3) {
            objArr[0] = "methodReferenceExpression";
        } else if (i != 4) {
            objArr[0] = "methodRef";
        } else {
            objArr[0] = "context";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiMethodReferenceUtil";
        if (i == 2) {
            objArr[2] = "getQualifierResolveResult";
        } else if (i == 3) {
            objArr[2] = "isStaticallyReferenced";
        } else if (i != 4) {
            objArr[2] = "isResolvedBySecondSearch";
        } else {
            objArr[2] = "replaceArrayType";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static boolean isSecondSearchPossible(PsiType[] psiTypeArr, QualifierResolveResult qualifierResolveResult, PsiMethodReferenceExpression psiMethodReferenceExpression) {
        return psiTypeArr.length > 0 && !(psiTypeArr[0] instanceof PsiPrimitiveType) && !psiMethodReferenceExpression.isConstructor() && isStaticallyReferenced(psiMethodReferenceExpression) && isReceiverType(psiTypeArr[0], qualifierResolveResult.getContainingClass(), qualifierResolveResult.getSubstitutor());
    }

    public static boolean isResolvedBySecondSearch(PsiMethodReferenceExpression psiMethodReferenceExpression) {
        if (psiMethodReferenceExpression == null) {
            $$$reportNull$$$0(0);
        }
        PsiElement psiElementResolve = psiMethodReferenceExpression.resolve();
        if (!(psiElementResolve instanceof PsiMethod)) {
            return false;
        }
        PsiMethod psiMethod = (PsiMethod) psiElementResolve;
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiMethodReferenceExpression.getFunctionalInterfaceType());
        PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(classResolveResultResolveGenericsClassInType);
        return functionalInterfaceMethod != null && isResolvedBySecondSearch(psiMethodReferenceExpression, functionalInterfaceMethod.getSignature(LambdaUtil.getSubstitutor(functionalInterfaceMethod, classResolveResultResolveGenericsClassInType)), psiMethod.isVarArgs(), psiMethod.hasModifierProperty("static"), psiMethod.getParameterList().getParametersCount());
    }

    public static boolean isResolvedBySecondSearch(PsiMethodReferenceExpression psiMethodReferenceExpression, MethodSignature methodSignature, boolean z, boolean z2, int i) {
        if (psiMethodReferenceExpression == null) {
            $$$reportNull$$$0(1);
        }
        if (methodSignature == null) {
            return false;
        }
        QualifierResolveResult qualifierResolveResult = getQualifierResolveResult(psiMethodReferenceExpression);
        PsiType[] parameterTypes = methodSignature.getParameterTypes();
        return ((i + 1 == parameterTypes.length && !z) || (z && parameterTypes.length > 0 && !z2)) && isSecondSearchPossible(parameterTypes, qualifierResolveResult, psiMethodReferenceExpression);
    }

    public static PsiType getQualifierType(PsiMethodReferenceExpression psiMethodReferenceExpression) {
        PsiTypeElement qualifierType = psiMethodReferenceExpression.getQualifierType();
        if (qualifierType != null) {
            return qualifierType.getType();
        }
        PsiElement qualifier = psiMethodReferenceExpression.getQualifier();
        PsiType type = qualifier instanceof PsiExpression ? ((PsiExpression) qualifier).getType() : null;
        return (type == null && (qualifier instanceof PsiReferenceExpression)) ? JavaPsiFacade.getElementFactory(psiMethodReferenceExpression.getProject()).createType((PsiReferenceExpression) qualifier) : type;
    }

    public static boolean isReturnTypeCompatible(PsiMethodReferenceExpression psiMethodReferenceExpression, JavaResolveResult javaResolveResult, PsiType psiType) {
        return isReturnTypeCompatible(psiMethodReferenceExpression, javaResolveResult, psiType, null);
    }

    public static PsiType getMethodReferenceReturnType(PsiMethodReferenceExpression psiMethodReferenceExpression) {
        return getMethodReferenceReturnType(psiMethodReferenceExpression, psiMethodReferenceExpression.advancedResolve(false));
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0050  */
    /* JADX WARN: Multi-variable type inference failed */
    private static PsiType getMethodReferenceReturnType(PsiMethodReferenceExpression psiMethodReferenceExpression, JavaResolveResult javaResolveResult) {
        PsiClass containingClass;
        PsiType psiTypeCreateArrayType;
        PsiSubstitutor substitutor = javaResolveResult.getSubstitutor();
        PsiElement element = javaResolveResult.getElement();
        if (element instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) element;
            containingClass = psiMethod.getContainingClass();
            psiTypeCreateArrayType = PsiTypesUtil.patchMethodGetClassReturnType(psiMethodReferenceExpression, psiMethod);
            if (psiTypeCreateArrayType == null) {
                PsiType returnType = psiMethod.getReturnType();
                if (PsiType.VOID.equals(returnType)) {
                    return returnType;
                }
                psiTypeCreateArrayType = substitutor.substitute(returnType);
            }
        } else if (element instanceof PsiClass) {
            if (PsiUtil.isArrayClass(element)) {
                PsiTypeParameter[] typeParameters = ((PsiClass) element).getTypeParameters();
                if (typeParameters.length == 1) {
                    PsiType psiTypeSubstitute = substitutor.substitute(typeParameters[0]);
                    if (psiTypeSubstitute == null) {
                        return null;
                    }
                    psiTypeCreateArrayType = psiTypeSubstitute.createArrayType();
                } else {
                    psiTypeCreateArrayType = null;
                }
            } else {
                psiTypeCreateArrayType = null;
            }
            containingClass = (PsiClass) element;
        } else {
            containingClass = null;
            psiTypeCreateArrayType = null;
        }
        if (psiTypeCreateArrayType == null) {
            if (containingClass == null) {
                return null;
            }
            psiTypeCreateArrayType = JavaPsiFacade.getElementFactory(psiMethodReferenceExpression.getProject()).createType(containingClass, substitutor);
        }
        return PsiUtil.captureToplevelWildcards(psiTypeCreateArrayType, psiMethodReferenceExpression);
    }

    private static boolean isReturnTypeCompatible(PsiMethodReferenceExpression psiMethodReferenceExpression, JavaResolveResult javaResolveResult, PsiType psiType, Ref<String> ref) {
        if (LambdaUtil.getFunctionalInterfaceMethod(PsiUtil.resolveGenericsClassInType(psiType)) != null) {
            PsiType functionalInterfaceReturnType = LambdaUtil.getFunctionalInterfaceReturnType(psiType);
            if (PsiType.VOID.equals(functionalInterfaceReturnType) || functionalInterfaceReturnType == null) {
                return true;
            }
            PsiType methodReferenceReturnType = getMethodReferenceReturnType(psiMethodReferenceExpression, javaResolveResult);
            if (methodReferenceReturnType == null) {
                return false;
            }
            if (TypeConversionUtil.isAssignable(functionalInterfaceReturnType, methodReferenceReturnType)) {
                return true;
            }
            if (ref != null) {
                ref.set(JavaPsiBundle.message("bad.return.type.in.method.reference", methodReferenceReturnType.getCanonicalText(), functionalInterfaceReturnType.getCanonicalText()));
            }
        }
        return false;
    }

    public static class QualifierResolveResult {
        private final PsiClass myContainingClass;
        private final boolean myReferenceTypeQualified;
        private final PsiSubstitutor mySubstitutor;

        public QualifierResolveResult(PsiClass psiClass, PsiSubstitutor psiSubstitutor, boolean z) {
            this.myContainingClass = psiClass;
            this.mySubstitutor = psiSubstitutor;
            this.myReferenceTypeQualified = z;
        }

        public PsiClass getContainingClass() {
            return this.myContainingClass;
        }

        public PsiSubstitutor getSubstitutor() {
            return this.mySubstitutor;
        }

        public boolean isReferenceTypeQualified() {
            return this.myReferenceTypeQualified;
        }
    }

    public static boolean isValidQualifier(PsiMethodReferenceExpression psiMethodReferenceExpression) {
        if (!(psiMethodReferenceExpression.getReferenceNameElement() instanceof PsiKeyword)) {
            return false;
        }
        PsiElement qualifier = psiMethodReferenceExpression.getQualifier();
        if (qualifier instanceof PsiTypeElement) {
            return true;
        }
        return (qualifier instanceof PsiReferenceExpression) && (((PsiReferenceExpression) qualifier).resolve() instanceof PsiClass);
    }

    public static QualifierResolveResult getQualifierResolveResult(PsiMethodReferenceExpression psiMethodReferenceExpression) {
        PsiClass element;
        PsiType psiTypeReplaceArrayType;
        if (psiMethodReferenceExpression == null) {
            $$$reportNull$$$0(2);
        }
        PsiSubstitutor substitutor = PsiSubstitutor.EMPTY;
        PsiExpression qualifierExpression = psiMethodReferenceExpression.getQualifierExpression();
        if (qualifierExpression != null) {
            PsiType type = qualifierExpression.getType();
            if (type instanceof PsiCapturedWildcardType) {
                psiTypeReplaceArrayType = ((PsiCapturedWildcardType) type).getUpperBound();
            } else {
                psiTypeReplaceArrayType = replaceArrayType(type, qualifierExpression);
            }
            PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiTypeReplaceArrayType);
            element = classResolveResultResolveGenericsClassInType.getElement();
            if (element != null) {
                substitutor = classResolveResultResolveGenericsClassInType.getSubstitutor();
            }
            if (element == null && (qualifierExpression instanceof PsiReferenceExpression)) {
                JavaResolveResult javaResolveResultAdvancedResolve = ((PsiReferenceExpression) qualifierExpression).advancedResolve(false);
                PsiElement element2 = javaResolveResultAdvancedResolve.getElement();
                if (element2 instanceof PsiClass) {
                    return new QualifierResolveResult((PsiClass) element2, javaResolveResultAdvancedResolve.getSubstitutor(), true);
                }
            }
        } else {
            PsiTypeElement qualifierType = psiMethodReferenceExpression.getQualifierType();
            if (qualifierType != null) {
                PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType2 = PsiUtil.resolveGenericsClassInType(replaceArrayType(qualifierType.getType(), qualifierType));
                element = classResolveResultResolveGenericsClassInType2.getElement();
                if (element != null) {
                    return new QualifierResolveResult(element, classResolveResultResolveGenericsClassInType2.getSubstitutor(), true);
                }
            } else {
                element = null;
            }
        }
        return new QualifierResolveResult(element, substitutor, false);
    }

    public static boolean isStaticallyReferenced(PsiMethodReferenceExpression psiMethodReferenceExpression) {
        if (psiMethodReferenceExpression == null) {
            $$$reportNull$$$0(3);
        }
        PsiExpression qualifierExpression = psiMethodReferenceExpression.getQualifierExpression();
        if (qualifierExpression != null) {
            return (qualifierExpression instanceof PsiReferenceExpression) && (((PsiReferenceExpression) qualifierExpression).resolve() instanceof PsiClass);
        }
        return true;
    }

    public static boolean isReceiverType(PsiType psiType, PsiClass psiClass, PsiSubstitutor psiSubstitutor) {
        if (psiType == null) {
            return false;
        }
        return TypeConversionUtil.isAssignable(JavaPsiFacade.getElementFactory(psiClass.getProject()).createType(psiClass, psiSubstitutor), replaceArrayType(psiType, psiClass));
    }

    public static PsiType getFirstParameterType(PsiType psiType, PsiElement psiElement) {
        PsiType psiTypeSubstitute;
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiType);
        MethodSignature function = LambdaUtil.getFunction(classResolveResultResolveGenericsClassInType.getElement());
        if (function == null || function.getParameterTypes().length <= 0 || (psiTypeSubstitute = classResolveResultResolveGenericsClassInType.getSubstitutor().substitute(function.getParameterTypes()[0])) == null) {
            return null;
        }
        return PsiUtil.captureToplevelWildcards(psiTypeSubstitute, psiElement);
    }

    private static PsiType replaceArrayType(PsiType psiType, PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        return psiType instanceof PsiArrayType ? JavaPsiFacade.getElementFactory(psiElement.getProject()).getArrayClassType(((PsiArrayType) psiType).getComponentType(), PsiUtil.getLanguageLevel(psiElement)) : psiType;
    }

    public static String checkTypeArguments(PsiTypeElement psiTypeElement, PsiType psiType) {
        PsiJavaCodeReferenceElement innermostComponentReferenceElement;
        if (!(psiType instanceof PsiClassType) || (innermostComponentReferenceElement = psiTypeElement.getInnermostComponentReferenceElement()) == null) {
            return null;
        }
        for (PsiType psiType2 : innermostComponentReferenceElement.getTypeParameters()) {
            if (psiType2 instanceof PsiWildcardType) {
                return JavaPsiBundle.message("error.message.wildcard.not.expected", new Object[0]);
            }
        }
        return null;
    }

    public static String checkReturnType(PsiMethodReferenceExpression psiMethodReferenceExpression, JavaResolveResult javaResolveResult, PsiType psiType) {
        Ref refCreate = Ref.create();
        if (isReturnTypeCompatible(psiMethodReferenceExpression, javaResolveResult, psiType, refCreate)) {
            return null;
        }
        return (String) refCreate.get();
    }
}
