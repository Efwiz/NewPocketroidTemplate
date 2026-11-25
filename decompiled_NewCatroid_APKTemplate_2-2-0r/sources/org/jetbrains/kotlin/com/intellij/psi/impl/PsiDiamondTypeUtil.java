package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCall;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReturnStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiDiamondTypeUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiDiamondTypeUtil.class);

    private PsiDiamondTypeUtil() {
    }

    public static boolean canCollapseToDiamond(PsiNewExpression psiNewExpression, PsiNewExpression psiNewExpression2, PsiType psiType) {
        return canCollapseToDiamond(psiNewExpression, psiNewExpression2, psiType, false);
    }

    public static boolean canChangeContextForDiamond(PsiNewExpression psiNewExpression, PsiType psiType) {
        PsiNewExpression psiNewExpression2 = (PsiNewExpression) psiNewExpression.copy();
        return canCollapseToDiamond(psiNewExpression2, psiNewExpression2, psiType, true);
    }

    private static boolean canCollapseToDiamond(PsiNewExpression psiNewExpression, PsiNewExpression psiNewExpression2, PsiType psiType, boolean z) {
        PsiJavaCodeReferenceElement classOrAnonymousClassReference;
        PsiReferenceParameterList parameterList;
        PsiAnonymousClass anonymousClass;
        if (PsiUtil.getLanguageLevel(psiNewExpression2).isAtLeast(LanguageLevel.JDK_1_7) && (classOrAnonymousClassReference = psiNewExpression.getClassOrAnonymousClassReference()) != null && (parameterList = classOrAnonymousClassReference.getParameterList()) != null) {
            PsiTypeElement[] typeParameterElements = parameterList.getTypeParameterElements();
            if (typeParameterElements.length <= 0 || (!z && typeParameterElements.length == 1 && (typeParameterElements[0].getType() instanceof PsiDiamondType))) {
                return false;
            }
            PsiDiamondType.DiamondInferenceResult diamondInferenceResultResolveInferredTypes = PsiDiamondTypeImpl.resolveInferredTypes(psiNewExpression, psiNewExpression2);
            if (diamondInferenceResultResolveInferredTypes.getErrorMessage() != null || ((anonymousClass = psiNewExpression.getAnonymousClass()) != null && ContainerUtil.exists(anonymousClass.getMethods(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDiamondTypeUtil$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return PsiDiamondTypeUtil.lambda$canCollapseToDiamond$0((PsiMethod) obj);
                }
            }))) {
                return false;
            }
            List<PsiType> inferredTypes = diamondInferenceResultResolveInferredTypes.getInferredTypes();
            PsiType[] parameters = psiType instanceof PsiClassType ? ((PsiClassType) psiType).getParameters() : null;
            if (parameters == null) {
                parameters = parameterList.getTypeArguments();
            }
            if (inferredTypes.size() == parameters.length) {
                PsiMethod psiMethodResolveMethod = psiNewExpression.resolveMethod();
                PsiElement psiElementResolve = classOrAnonymousClassReference.resolve();
                if (psiElementResolve instanceof PsiClass) {
                    return areTypeArgumentsRedundant(parameters, psiNewExpression2, true, psiMethodResolveMethod, ((PsiClass) psiElementResolve).getTypeParameters());
                }
            }
        }
        return false;
    }

    static /* synthetic */ boolean lambda$canCollapseToDiamond$0(PsiMethod psiMethod) {
        return !psiMethod.hasModifierProperty("private") && psiMethod.findSuperMethods().length == 0;
    }

    public static PsiElement createExplicitReplacement(PsiElement psiElement) {
        if (!(psiElement instanceof PsiReferenceParameterList)) {
            return null;
        }
        PsiJavaCodeReferenceElement classReference = ((PsiNewExpression) JavaPsiFacade.getElementFactory(psiElement.getProject()).createExpressionFromText("new a<>()", psiElement)).getClassReference();
        Logger logger = LOG;
        logger.assertTrue(classReference != null);
        PsiReferenceParameterList parameterList = classReference.getParameterList();
        logger.assertTrue(parameterList != null);
        return parameterList;
    }

    public static PsiElement replaceDiamondWithExplicitTypes(PsiElement psiElement) {
        PsiElement lightParent = psiElement.getLightParent();
        if (!(lightParent instanceof PsiJavaCodeReferenceElement)) {
            return lightParent;
        }
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = (PsiJavaCodeReferenceElement) lightParent;
        PsiReferenceParameterList parameterList = psiJavaCodeReferenceElement.getParameterList();
        if (parameterList == null) {
            return psiJavaCodeReferenceElement;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(psiJavaCodeReferenceElement.getQualifiedName());
        sb.append('<');
        PsiNewExpression psiNewExpression = (PsiNewExpression) PsiTreeUtil.getParentOfType(psiElement, PsiNewExpression.class);
        sb.append(StringUtil.join((Collection) PsiDiamondTypeImpl.resolveInferredTypesNoCheck(psiNewExpression, psiNewExpression).getInferredTypes(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDiamondTypeUtil$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiType) obj).getCanonicalText();
            }
        }, ","));
        sb.append('>');
        PsiReferenceParameterList parameterList2 = JavaPsiFacade.getElementFactory(psiElement.getProject()).createReferenceFromText(sb.toString(), psiElement).getParameterList();
        LOG.assertTrue(parameterList2 != null);
        CodeStyleManager.getInstance(psiJavaCodeReferenceElement.getProject()).reformat(parameterList.replace(parameterList2));
        return psiJavaCodeReferenceElement;
    }

    public static PsiExpression expandTopLevelDiamondsInside(PsiExpression psiExpression) {
        PsiJavaCodeReferenceElement classReference;
        PsiReferenceParameterList parameterList;
        if (!(psiExpression instanceof PsiNewExpression) || (classReference = ((PsiNewExpression) psiExpression).getClassReference()) == null || (parameterList = classReference.getParameterList()) == null) {
            return psiExpression;
        }
        PsiTypeElement[] typeParameterElements = parameterList.getTypeParameterElements();
        return (typeParameterElements.length == 1 && (typeParameterElements[0].getType() instanceof PsiDiamondType)) ? (PsiExpression) replaceDiamondWithExplicitTypes(parameterList).getLightParent() : psiExpression;
    }

    public static String getCollapsedType(PsiType psiType, PsiElement psiElement) {
        int iIndexOf;
        String canonicalText = psiType.getCanonicalText();
        return (!PsiUtil.isLanguageLevel7OrHigher(psiElement) || (iIndexOf = canonicalText.indexOf(60)) < 0) ? canonicalText : canonicalText.substring(0, iIndexOf) + "<>";
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0034  */
    private static boolean isAugmented(PsiExpression psiExpression) {
        PsiTypeElement returnTypeElement;
        PsiElement psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiExpression.getLightParent());
        if (psiElementSkipParenthesizedExprUp instanceof PsiVariable) {
            returnTypeElement = ((PsiVariable) psiElementSkipParenthesizedExprUp).getTypeElement();
        } else if (psiElementSkipParenthesizedExprUp instanceof PsiReturnStatement) {
            PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElementSkipParenthesizedExprUp, (Class<? extends PsiElement>[]) new Class[]{PsiMethod.class, PsiLambdaExpression.class});
            if (parentOfType instanceof PsiMethod) {
                returnTypeElement = ((PsiMethod) parentOfType).getReturnTypeElement();
            } else {
                returnTypeElement = null;
            }
        } else {
            returnTypeElement = null;
        }
        return (returnTypeElement == null || PsiAugmentProvider.getInferredType(returnTypeElement) == null) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:102:0x0158  */
    /* JADX WARN: Code duplicated, block: B:106:0x015f  */
    /* JADX WARN: Code duplicated, block: B:47:0x00a3 A[Catch: all -> 0x0146, IncorrectOperationException -> 0x0149, TryCatch #6 {IncorrectOperationException -> 0x0149, all -> 0x0146, blocks: (B:45:0x0096, B:47:0x00a3, B:49:0x00ad, B:78:0x00f8, B:79:0x0101, B:82:0x010d, B:84:0x0123, B:55:0x00bb, B:57:0x00c5, B:59:0x00c9, B:64:0x00d8, B:68:0x00df), top: B:114:0x0096 }] */
    /* JADX WARN: Code duplicated, block: B:52:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:54:0x00b9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:55:0x00bb A[Catch: all -> 0x0146, IncorrectOperationException -> 0x0149, TRY_ENTER, TryCatch #6 {IncorrectOperationException -> 0x0149, all -> 0x0146, blocks: (B:45:0x0096, B:47:0x00a3, B:49:0x00ad, B:78:0x00f8, B:79:0x0101, B:82:0x010d, B:84:0x0123, B:55:0x00bb, B:57:0x00c5, B:59:0x00c9, B:64:0x00d8, B:68:0x00df), top: B:114:0x0096 }] */
    /* JADX WARN: Code duplicated, block: B:57:0x00c5 A[Catch: all -> 0x0146, IncorrectOperationException -> 0x0149, TryCatch #6 {IncorrectOperationException -> 0x0149, all -> 0x0146, blocks: (B:45:0x0096, B:47:0x00a3, B:49:0x00ad, B:78:0x00f8, B:79:0x0101, B:82:0x010d, B:84:0x0123, B:55:0x00bb, B:57:0x00c5, B:59:0x00c9, B:64:0x00d8, B:68:0x00df), top: B:114:0x0096 }] */
    /* JADX WARN: Code duplicated, block: B:62:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:64:0x00d8 A[Catch: all -> 0x0146, IncorrectOperationException -> 0x0149, TRY_ENTER, TryCatch #6 {IncorrectOperationException -> 0x0149, all -> 0x0146, blocks: (B:45:0x0096, B:47:0x00a3, B:49:0x00ad, B:78:0x00f8, B:79:0x0101, B:82:0x010d, B:84:0x0123, B:55:0x00bb, B:57:0x00c5, B:59:0x00c9, B:64:0x00d8, B:68:0x00df), top: B:114:0x0096 }] */
    /* JADX WARN: Code duplicated, block: B:66:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:67:0x00de  */
    /* JADX WARN: Code duplicated, block: B:70:0x00e8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:71:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:74:0x00f0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:75:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:77:0x00f6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:78:0x00f8 A[Catch: all -> 0x0146, IncorrectOperationException -> 0x0149, TRY_ENTER, TryCatch #6 {IncorrectOperationException -> 0x0149, all -> 0x0146, blocks: (B:45:0x0096, B:47:0x00a3, B:49:0x00ad, B:78:0x00f8, B:79:0x0101, B:82:0x010d, B:84:0x0123, B:55:0x00bb, B:57:0x00c5, B:59:0x00c9, B:64:0x00d8, B:68:0x00df), top: B:114:0x0096 }] */
    /* JADX WARN: Code duplicated, block: B:87:0x013c  */
    /* JADX WARN: Code duplicated, block: B:90:0x0142  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiExpression] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4, types: [org.jetbrains.kotlin.com.intellij.psi.PsiElement] */
    /* JADX WARN: Type inference failed for: r10v5, types: [org.jetbrains.kotlin.com.intellij.psi.PsiElement] */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [org.jetbrains.kotlin.com.intellij.psi.PsiElement] */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7, types: [org.jetbrains.kotlin.com.intellij.psi.PsiElement] */
    /* JADX WARN: Type inference failed for: r4v8 */
    public static boolean areTypeArgumentsRedundant(PsiType[] psiTypeArr, PsiExpression psiExpression, boolean z, PsiMethod psiMethod, PsiTypeParameter[] psiTypeParameterArr) throws Throwable {
        ?? r4;
        PsiElement psiElementCopyWithExpectedType;
        PsiCallExpression psiCallExpression;
        PsiElementFactory elementFactory;
        boolean z2;
        PsiCallExpression psiCallExpression2;
        PsiCallExpression psiCallExpression3;
        JavaResolveResult javaResolveResultResolveMethodGenerics;
        JavaResolveResult javaResolveResultResolveMethodGenerics2;
        PsiMethodReferenceExpression psiMethodReferenceExpression;
        try {
            PsiType expectedTypeByParent = PsiTypesUtil.getExpectedTypeByParent(psiExpression);
            if (expectedTypeByParent != null && PsiTypesUtil.isDenotableType(expectedTypeByParent, psiExpression)) {
                if (isAugmented(psiExpression)) {
                    return false;
                }
                try {
                    try {
                        RecaptureTypeMapper.encode(psiExpression);
                        psiElementCopyWithExpectedType = LambdaUtil.copyWithExpectedType(psiExpression, expectedTypeByParent);
                        r4 = psiExpression;
                        psiCallExpression = (PsiCallExpression) PsiTreeUtil.getParentOfType(psiElementCopyWithExpectedType, PsiCallExpression.class, false);
                        if (psiExpression instanceof PsiMethodReferenceExpression) {
                            psiMethodReferenceExpression = (PsiMethodReferenceExpression) PsiTreeUtil.getParentOfType(psiElementCopyWithExpectedType, PsiMethodReferenceExpression.class, false);
                            if (psiMethodReferenceExpression != null) {
                                if (r4 != 0) {
                                    RecaptureTypeMapper.clean(r4);
                                }
                                return false;
                            }
                        } else if (psiCallExpression != null) {
                            elementFactory = JavaPsiFacade.getElementFactory(psiCallExpression.getProject());
                            if (!z) {
                                if (psiCallExpression instanceof PsiNewExpression) {
                                }
                                if (r4 != 0) {
                                    RecaptureTypeMapper.clean(r4);
                                }
                                return false;
                            }
                            Logger logger = LOG;
                            if (psiMethod != null) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            logger.assertTrue(z2);
                            if (!isInferenceEquivalent(psiTypeArr, elementFactory, psiCallExpression, psiMethod, psiTypeParameterArr)) {
                                if (r4 != 0) {
                                    RecaptureTypeMapper.clean(r4);
                                }
                                return false;
                            }
                        }
                        if (expectedTypeByParent != null) {
                            if (r4 != 0) {
                                RecaptureTypeMapper.clean(r4);
                            }
                            return true;
                        }
                        if (psiCallExpression != null) {
                        }
                        psiCallExpression3 = (PsiCallExpression) PsiTreeUtil.getParentOfType((PsiElement) psiExpression, PsiCallExpression.class);
                        if (psiCallExpression2 != null) {
                            javaResolveResultResolveMethodGenerics = psiCallExpression2.resolveMethodGenerics();
                            javaResolveResultResolveMethodGenerics2 = psiCallExpression3.resolveMethodGenerics();
                            if (Objects.equals(javaResolveResultResolveMethodGenerics.getElement(), javaResolveResultResolveMethodGenerics2.getElement())) {
                            }
                            if (r4 != 0) {
                                RecaptureTypeMapper.clean(r4);
                            }
                            return false;
                        }
                        if (r4 != 0) {
                            RecaptureTypeMapper.clean(r4);
                        }
                        return true;
                    } catch (IncorrectOperationException e) {
                        e = e;
                        LOG.info(e);
                        if (psiExpression != 0) {
                            RecaptureTypeMapper.clean(psiExpression);
                        }
                        return false;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (psiExpression != 0) {
                        RecaptureTypeMapper.clean(psiExpression);
                    }
                    throw th;
                }
            }
            PsiExpressionList argumentList = psiExpression instanceof PsiCallExpression ? ((PsiCallExpression) psiExpression).getArgumentList() : null;
            Object obj = new Object();
            PsiTreeUtil.mark(argumentList != null ? argumentList : psiExpression, obj);
            PsiCall psiCallTreeWalkUp = LambdaUtil.treeWalkUp(psiExpression);
            if (psiCallTreeWalkUp != null) {
                try {
                    RecaptureTypeMapper.encode(psiCallTreeWalkUp);
                    PsiCall psiCallCopyTopLevelCall = LambdaUtil.copyTopLevelCall(psiCallTreeWalkUp);
                    PsiElement psiElementReleaseMark = psiCallCopyTopLevelCall != null ? PsiTreeUtil.releaseMark(psiCallCopyTopLevelCall, obj) : null;
                    r4 = psiCallTreeWalkUp;
                    psiElementCopyWithExpectedType = psiElementReleaseMark;
                } catch (IncorrectOperationException e2) {
                    e = e2;
                    psiExpression = psiCallTreeWalkUp;
                    LOG.info(e);
                    if (psiExpression != 0) {
                        RecaptureTypeMapper.clean(psiExpression);
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    psiExpression = psiCallTreeWalkUp;
                    if (psiExpression != 0) {
                        RecaptureTypeMapper.clean(psiExpression);
                    }
                    throw th;
                }
            } else {
                if (InjectedLanguageManager.getInstance(psiExpression.getProject()).getInjectionHost(psiExpression) != null) {
                    return false;
                }
                PsiFile containingFile = psiExpression.getContainingFile();
                PsiFile psiFile = (PsiFile) containingFile.copy();
                PsiElement psiElementReleaseMark2 = PsiTreeUtil.releaseMark(psiFile, obj);
                if (psiMethod != null && psiMethod.getContainingFile() == containingFile && (psiMethod = (PsiMethod) PsiTreeUtil.getParentOfType(psiFile.findElementAt(psiMethod.getTextOffset()), PsiMethod.class)) == null) {
                    return false;
                }
                psiElementCopyWithExpectedType = psiElementReleaseMark2;
                r4 = 0;
            }
            try {
                psiCallExpression = (PsiCallExpression) PsiTreeUtil.getParentOfType(psiElementCopyWithExpectedType, PsiCallExpression.class, false);
                if (psiExpression instanceof PsiMethodReferenceExpression) {
                    psiMethodReferenceExpression = (PsiMethodReferenceExpression) PsiTreeUtil.getParentOfType(psiElementCopyWithExpectedType, PsiMethodReferenceExpression.class, false);
                    if (psiMethodReferenceExpression != null && !isInferenceEquivalent(psiTypeArr, psiTypeParameterArr, psiMethod, psiMethodReferenceExpression)) {
                        if (r4 != 0) {
                            RecaptureTypeMapper.clean(r4);
                        }
                        return false;
                    }
                } else if (psiCallExpression != null) {
                    elementFactory = JavaPsiFacade.getElementFactory(psiCallExpression.getProject());
                    if (!z) {
                        Logger logger2 = LOG;
                        if (psiMethod != null) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        logger2.assertTrue(z2);
                        if (!isInferenceEquivalent(psiTypeArr, elementFactory, psiCallExpression, psiMethod, psiTypeParameterArr)) {
                            if (r4 != 0) {
                                RecaptureTypeMapper.clean(r4);
                            }
                            return false;
                        }
                    } else if ((psiCallExpression instanceof PsiNewExpression) || !isInferenceEquivalent(psiTypeArr, elementFactory, (PsiNewExpression) psiCallExpression)) {
                        if (r4 != 0) {
                            RecaptureTypeMapper.clean(r4);
                        }
                        return false;
                    }
                }
                if (expectedTypeByParent != null) {
                    if (r4 != 0) {
                        RecaptureTypeMapper.clean(r4);
                    }
                    return true;
                }
                psiCallExpression2 = psiCallExpression != null ? (PsiCallExpression) PsiTreeUtil.getParentOfType(psiCallExpression, PsiCallExpression.class) : null;
                psiCallExpression3 = (PsiCallExpression) PsiTreeUtil.getParentOfType((PsiElement) psiExpression, PsiCallExpression.class);
                if (psiCallExpression2 != null && psiCallExpression3 != null) {
                    javaResolveResultResolveMethodGenerics = psiCallExpression2.resolveMethodGenerics();
                    javaResolveResultResolveMethodGenerics2 = psiCallExpression3.resolveMethodGenerics();
                    if (Objects.equals(javaResolveResultResolveMethodGenerics.getElement(), javaResolveResultResolveMethodGenerics2.getElement()) || !new RecaptureTypeMapper().recapture(javaResolveResultResolveMethodGenerics.getSubstitutor()).equals(javaResolveResultResolveMethodGenerics2.getSubstitutor())) {
                        if (r4 != 0) {
                            RecaptureTypeMapper.clean(r4);
                        }
                        return false;
                    }
                }
                if (r4 != 0) {
                    RecaptureTypeMapper.clean(r4);
                }
                return true;
            } catch (IncorrectOperationException e3) {
                e = e3;
                psiExpression = r4;
                LOG.info(e);
                if (psiExpression != 0) {
                    RecaptureTypeMapper.clean(psiExpression);
                }
                return false;
            } catch (Throwable th3) {
                th = th3;
                psiExpression = r4;
                if (psiExpression != 0) {
                    RecaptureTypeMapper.clean(psiExpression);
                }
                throw th;
            }
        } catch (IncorrectOperationException e4) {
            e = e4;
            psiExpression = 0;
        } catch (Throwable th4) {
            th = th4;
            psiExpression = 0;
        }
    }

    private static boolean isInferenceEquivalent(PsiType[] psiTypeArr, PsiTypeParameter[] psiTypeParameterArr, PsiMethod psiMethod, PsiMethodReferenceExpression psiMethodReferenceExpression) {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiMethodReferenceExpression.getProject());
        PsiTypeElement qualifierType = psiMethodReferenceExpression.getQualifierType();
        if (qualifierType != null) {
            qualifierType.replace(elementFactory.createTypeElement(((PsiClassType) qualifierType.getType()).rawType()));
        } else {
            PsiReferenceParameterList parameterList = psiMethodReferenceExpression.getParameterList();
            if (parameterList != null) {
                parameterList.delete();
            }
        }
        JavaResolveResult javaResolveResultAdvancedResolve = psiMethodReferenceExpression.advancedResolve(false);
        if (psiMethod != null && javaResolveResultAdvancedResolve.getElement() != psiMethod) {
            return false;
        }
        PsiSubstitutor substitutor = javaResolveResultAdvancedResolve.getSubstitutor();
        for (int i = 0; i < psiTypeParameterArr.length; i++) {
            if (!psiTypeArr[i].equals(substitutor.getSubstitutionMap().get(psiTypeParameterArr[i]))) {
                return false;
            }
        }
        return checkParentApplicability(psiMethodReferenceExpression);
    }

    private static boolean isInferenceEquivalent(PsiType[] psiTypeArr, PsiElementFactory psiElementFactory, PsiCallExpression psiCallExpression, PsiMethod psiMethod, PsiTypeParameter[] psiTypeParameterArr) throws IncorrectOperationException {
        psiCallExpression.getTypeArgumentList().replace(((PsiCallExpression) psiElementFactory.createExpressionFromText("foo()", (PsiElement) null)).getTypeArgumentList());
        JavaResolveResult javaResolveResultResolveMethodGenerics = psiCallExpression.resolveMethodGenerics();
        if (psiMethod != javaResolveResultResolveMethodGenerics.getElement()) {
            return false;
        }
        PsiSubstitutor substitutor = javaResolveResultResolveMethodGenerics.getSubstitutor();
        int length = psiTypeParameterArr.length;
        for (int i = 0; i < length; i++) {
            PsiTypeParameter psiTypeParameter = psiTypeParameterArr[i];
            PsiType psiType = substitutor.getSubstitutionMap().get(psiTypeParameter);
            if (!psiTypeArr[i].equals(psiType)) {
                return false;
            }
            if (PsiUtil.resolveClassInType(psiMethod.getReturnType()) == psiTypeParameter && PsiPrimitiveType.getUnboxedType(psiType) != null) {
                return false;
            }
        }
        return checkParentApplicability(psiCallExpression);
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0074  */
    /* JADX WARN: Code duplicated, block: B:42:0x007c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x007d A[SYNTHETIC] */
    private static boolean isInferenceEquivalent(PsiType[] psiTypeArr, PsiElementFactory psiElementFactory, PsiNewExpression psiNewExpression) throws IncorrectOperationException {
        PsiWildcardType psiWildcardType;
        PsiType bound;
        PsiJavaCodeReferenceElement classOrAnonymousClassReference = ((PsiNewExpression) psiElementFactory.createExpressionFromText("new A<>()", (PsiElement) null)).getClassOrAnonymousClassReference();
        Logger logger = LOG;
        logger.assertTrue(classOrAnonymousClassReference != null);
        PsiReferenceParameterList parameterList = classOrAnonymousClassReference.getParameterList();
        logger.assertTrue(parameterList != null);
        PsiJavaCodeReferenceElement classOrAnonymousClassReference2 = psiNewExpression.getClassOrAnonymousClassReference();
        logger.assertTrue(classOrAnonymousClassReference2 != null);
        PsiReferenceParameterList parameterList2 = classOrAnonymousClassReference2.getParameterList();
        logger.assertTrue(parameterList2 != null);
        parameterList2.replace(parameterList);
        PsiType[] typeArguments = classOrAnonymousClassReference2.getParameterList().getTypeArguments();
        if (psiTypeArr.length != typeArguments.length) {
            return false;
        }
        for (int i = 0; i < psiTypeArr.length; i++) {
            PsiType psiType = psiTypeArr[i];
            PsiType psiType2 = typeArguments[i];
            if ((psiType2 instanceof PsiWildcardType) && (bound = (psiWildcardType = (PsiWildcardType) psiType2).getBound()) != null) {
                if (psiWildcardType.isExtends()) {
                    if (bound.isAssignableFrom(psiType)) {
                        continue;
                    } else if (!psiType.equals(typeArguments[i])) {
                        return false;
                    }
                } else if (psiType.isAssignableFrom(bound)) {
                    continue;
                } else if (!psiType.equals(typeArguments[i])) {
                    return false;
                }
            } else if (!psiType.equals(typeArguments[i])) {
                return false;
            }
        }
        return checkParentApplicability(psiNewExpression);
    }

    private static boolean checkParentApplicability(PsiExpression psiExpression) {
        while (psiExpression != null) {
            JavaResolveResult diamondsAwareResolveResult = psiExpression instanceof PsiCallExpression ? PsiDiamondType.getDiamondsAwareResolveResult((PsiCall) psiExpression) : null;
            if ((diamondsAwareResolveResult instanceof MethodCandidateInfo) && !((MethodCandidateInfo) diamondsAwareResolveResult).isApplicable()) {
                return false;
            }
            psiExpression = (PsiExpression) PsiTreeUtil.getParentOfType(psiExpression, PsiCallExpression.class, true);
        }
        return true;
    }
}
