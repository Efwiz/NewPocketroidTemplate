package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.codeInsight.daemon.impl.analysis.JavaGenericsUtil;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaSdkVersion;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaVersionService;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiCall;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.ThreadLocalTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiMethodCallExpressionImpl extends ExpressionPsiElement implements PsiMethodCallExpression {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiMethodCallExpressionImpl.class);
    private static final TypeEvaluator ourTypeEvaluator = new TypeEvaluator();

    /* JADX WARN: Code duplicated, block: B:11:0x0013  */
    /* JADX WARN: Code duplicated, block: B:18:0x002b  */
    /* JADX WARN: Code duplicated, block: B:6:0x0009  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 1) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    i2 = 3;
                    break;
                default:
                    i2 = 2;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i != 1) {
            switch (i) {
                case 7:
                case 9:
                    objArr[0] = "child";
                    break;
                case 8:
                    objArr[0] = "visitor";
                    break;
                case 10:
                    objArr[0] = "newElement";
                    break;
                default:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiMethodCallExpressionImpl";
                    break;
            }
        } else {
            objArr[0] = "child";
        }
        switch (i) {
            case 1:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiMethodCallExpressionImpl";
                break;
            case 2:
            case 3:
                objArr[1] = "getTypeArgumentList";
                break;
            case 4:
                objArr[1] = "getTypeArguments";
                break;
            case 5:
                objArr[1] = "getMethodExpression";
                break;
            case 6:
                objArr[1] = "getArgumentList";
                break;
            default:
                objArr[1] = "resolveMethodGenerics";
                break;
        }
        if (i != 1) {
            switch (i) {
                case 7:
                    objArr[2] = "getChildRole";
                    break;
                case 8:
                    objArr[2] = "accept";
                    break;
                case 9:
                case 10:
                    objArr[2] = "replaceChildInternal";
                    break;
            }
        } else {
            objArr[2] = "removeChild";
        }
        String str2 = String.format(str, objArr);
        if (i != 1) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    break;
                default:
                    throw new IllegalStateException(str2);
            }
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiMethodCallExpressionImpl() {
        super(JavaElementType.METHOD_CALL_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        return JavaResolveCache.getInstance(getProject()).getType(this, ourTypeEvaluator);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiMethod resolveMethod() {
        return (PsiMethod) getMethodExpression().resolve();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public JavaResolveResult resolveMethodGenerics() {
        JavaResolveResult javaResolveResultAdvancedResolve = getMethodExpression().advancedResolve(false);
        if (javaResolveResultAdvancedResolve == null) {
            $$$reportNull$$$0(0);
        }
        return javaResolveResultAdvancedResolve;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void removeChild(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        if (aSTNode == getArgumentList()) {
            LOG.error("Cannot delete argument list since it will break contract on argument list notnullity");
        }
        super.removeChild(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression
    public PsiReferenceParameterList getTypeArgumentList() {
        PsiReferenceExpression methodExpression = getMethodExpression();
        PsiReferenceParameterList parameterList = methodExpression.getParameterList();
        if (parameterList != null) {
            if (parameterList == null) {
                $$$reportNull$$$0(2);
            }
            return parameterList;
        }
        LOG.error("Invalid method call expression. Children:\n" + DebugUtil.psiTreeToString(methodExpression, true));
        if (parameterList == null) {
            $$$reportNull$$$0(3);
        }
        return parameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression
    public PsiType[] getTypeArguments() {
        PsiType[] typeParameters = getMethodExpression().getTypeParameters();
        if (typeParameters == null) {
            $$$reportNull$$$0(4);
        }
        return typeParameters;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression
    public PsiReferenceExpression getMethodExpression() {
        PsiReferenceExpression psiReferenceExpression = (PsiReferenceExpression) findChildByRoleAsPsiElement(91);
        if (psiReferenceExpression == null) {
            $$$reportNull$$$0(5);
        }
        return psiReferenceExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression, org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiExpressionList getArgumentList() {
        PsiExpressionList psiExpressionList = (PsiExpressionList) findChildByRoleAsPsiElement(73);
        if (psiExpressionList == null) {
            LOG.error("Invalid PSI for'" + getText() + ". Parent:" + DebugUtil.psiToString(getLightParent(), true));
        }
        if (psiExpressionList == null) {
            $$$reportNull$$$0(6);
        }
        return psiExpressionList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 73) {
            return findChildByType(JavaElementType.EXPRESSION_LIST);
        }
        if (i != 91) {
            return null;
        }
        return getFirstChildNode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(7);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        if (aSTNode.getElementType() == JavaElementType.EXPRESSION_LIST) {
            return 73;
        }
        return ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) ? 91 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitMethodCallExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiMethodCallExpression:" + getText();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TypeEvaluator implements Function<PsiMethodCallExpression, PsiType> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "methodExpression";
            } else if (i == 2) {
                objArr[0] = "result";
            } else if (i == 3) {
                objArr[0] = "languageLevel";
            } else if (i != 4) {
                objArr[0] = "call";
            } else {
                objArr[0] = "file";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiMethodCallExpressionImpl$TypeEvaluator";
            objArr[2] = "getResultType";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private TypeEvaluator() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public PsiType fun(PsiMethodCallExpression psiMethodCallExpression) {
            PsiReferenceExpression methodExpression = psiMethodCallExpression.getMethodExpression();
            PsiFile containingFile = psiMethodCallExpression.getContainingFile();
            JavaResolveResult[] javaResolveResultArrMultiResolveImpl = PsiImplUtil.multiResolveImpl(methodExpression, containingFile, false, PsiReferenceExpressionImpl.OurGenericsResolver.INSTANCE);
            LanguageLevel languageLevel = PsiUtil.getLanguageLevel(containingFile);
            boolean zDoWePerformGenericMethodOverloadResolutionNow = PsiMethodCallExpressionImpl.doWePerformGenericMethodOverloadResolutionNow(psiMethodCallExpression, languageLevel);
            PsiType psiType = null;
            for (int i = 0; i < javaResolveResultArrMultiResolveImpl.length; i++) {
                JavaResolveResult javaResolveResult = javaResolveResultArrMultiResolveImpl[i];
                PsiElement element = javaResolveResult.getElement();
                if (zDoWePerformGenericMethodOverloadResolutionNow && element != null && PsiPolyExpressionUtil.isMethodCallPolyExpression(psiMethodCallExpression, (PsiMethod) element)) {
                    PsiMethodCallExpressionImpl.LOG.error("poly expression evaluation during overload resolution, processing " + javaResolveResultArrMultiResolveImpl.length + " results");
                }
                PsiType resultType = getResultType(psiMethodCallExpression, methodExpression, javaResolveResult, languageLevel, containingFile);
                if (resultType == null) {
                    return null;
                }
                if (i == 0) {
                    psiType = resultType;
                } else if (!psiType.equals(resultType)) {
                    return null;
                }
            }
            return PsiClassImplUtil.correctType(psiType, containingFile.getResolveScope());
        }

        private static PsiType getResultType(PsiMethodCallExpression psiMethodCallExpression, PsiReferenceExpression psiReferenceExpression, JavaResolveResult javaResolveResult, LanguageLevel languageLevel, PsiFile psiFile) {
            if (psiMethodCallExpression == null) {
                $$$reportNull$$$0(0);
            }
            if (psiReferenceExpression == null) {
                $$$reportNull$$$0(1);
            }
            if (javaResolveResult == null) {
                $$$reportNull$$$0(2);
            }
            if (languageLevel == null) {
                $$$reportNull$$$0(3);
            }
            if (psiFile == null) {
                $$$reportNull$$$0(4);
            }
            PsiMethod psiMethod = (PsiMethod) javaResolveResult.getElement();
            if (psiMethod == null) {
                return null;
            }
            PsiUtilCore.ensureValid(psiMethod);
            boolean z = languageLevel.compareTo(LanguageLevel.JDK_1_5) >= 0;
            PsiType psiTypePatchMethodGetClassReturnType = PsiTypesUtil.patchMethodGetClassReturnType(psiMethodCallExpression, psiReferenceExpression, psiMethod, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiMethodCallExpressionImpl$TypeEvaluator$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return PsiMethodCallExpressionImpl.TypeEvaluator.lambda$getResultType$0((IElementType) obj);
                }
            }, languageLevel);
            if (psiTypePatchMethodGetClassReturnType != null) {
                return psiTypePatchMethodGetClassReturnType;
            }
            PsiType returnType = psiMethod.getReturnType();
            if (returnType == null) {
                return null;
            }
            PsiUtil.ensureValidType(returnType);
            if (returnType instanceof PsiClassType) {
                returnType = ((PsiClassType) returnType).setLanguageLevel(languageLevel);
            }
            PsiType psiType = returnType;
            if (z) {
                return PsiMethodCallExpressionImpl.captureReturnType(psiMethodCallExpression, psiMethod, psiType, javaResolveResult, languageLevel, psiFile);
            }
            return TypeConversionUtil.erasure(psiType);
        }

        static /* synthetic */ boolean lambda$getResultType$0(IElementType iElementType) {
            return (iElementType == JavaElementType.CLASS || iElementType == JavaElementType.ANONYMOUS_CLASS) ? false : true;
        }
    }

    public static boolean doWePerformGenericMethodOverloadResolutionNow(PsiCall psiCall, LanguageLevel languageLevel) {
        PsiElement psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiCall.getLightParent());
        PsiExpressionList psiExpressionList = null;
        if (languageLevel.isAtLeast(LanguageLevel.JDK_1_8) && (!(psiElementSkipParenthesizedExprUp instanceof PsiConditionalExpression) || PsiPolyExpressionUtil.isPolyExpression((PsiExpression) psiElementSkipParenthesizedExprUp))) {
            psiExpressionList = (PsiExpressionList) PsiTreeUtil.getParentOfType((PsiElement) psiCall, PsiExpressionList.class, true, (Class<? extends PsiElement>[]) new Class[]{PsiReferenceExpression.class});
        }
        return (psiExpressionList == null || !MethodCandidateInfo.isOverloadCheck(psiExpressionList) || ContainerUtil.exists(psiExpressionList.getExpressions(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiMethodCallExpressionImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PsiMethodCallExpressionImpl.lambda$doWePerformGenericMethodOverloadResolutionNow$0((PsiExpression) obj);
            }
        })) ? false : true;
    }

    static /* synthetic */ boolean lambda$doWePerformGenericMethodOverloadResolutionNow$0(PsiExpression psiExpression) {
        PsiExpression psiExpressionSkipParenthesizedExprDown = PsiUtil.skipParenthesizedExprDown(psiExpression);
        return psiExpressionSkipParenthesizedExprDown != null && ThreadLocalTypes.hasBindingFor(psiExpressionSkipParenthesizedExprDown);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiType captureReturnType(PsiMethodCallExpression psiMethodCallExpression, PsiMethod psiMethod, PsiType psiType, JavaResolveResult javaResolveResult, LanguageLevel languageLevel, PsiFile psiFile) {
        PsiSubstitutor substitutor = javaResolveResult.getSubstitutor();
        substitutor.ensureValid();
        PsiType psiTypeSubstitute = substitutor.substitute(psiType);
        if (psiTypeSubstitute == null) {
            return TypeConversionUtil.erasure(psiType);
        }
        boolean z = javaResolveResult instanceof MethodCandidateInfo;
        if (z && ((MethodCandidateInfo) javaResolveResult).isErased()) {
            return TypeConversionUtil.erasure(psiTypeSubstitute);
        }
        if (((!languageLevel.isAtLeast(LanguageLevel.JDK_1_8) || psiMethodCallExpression.getTypeArguments().length > 0) && psiMethod.hasTypeParameters()) || (!psiMethod.hasTypeParameters() && JavaVersionService.getInstance().isAtLeast(psiFile, JavaSdkVersion.JDK_1_8))) {
            PsiType psiTypeErasure = TypeConversionUtil.erasure(psiTypeSubstitute);
            if (!psiTypeSubstitute.equals(psiTypeErasure) && z) {
                MethodCandidateInfo methodCandidateInfo = (MethodCandidateInfo) javaResolveResult;
                if (methodCandidateInfo.isApplicable()) {
                    PsiType[] expressionTypes = psiMethodCallExpression.getArgumentList().getExpressionTypes();
                    PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
                    boolean z2 = methodCandidateInfo.getApplicabilityLevel() == 2;
                    for (int i = 0; i < expressionTypes.length; i++) {
                        PsiType psiTypeSubstitute2 = substitutor.substitute(PsiTypesUtil.getParameterType(parameters, i, z2));
                        PsiType psiType2 = expressionTypes[i];
                        if (psiType2 != null && psiTypeSubstitute2 != null && JavaGenericsUtil.isRawToGeneric(psiTypeSubstitute2, psiType2)) {
                            return psiTypeErasure;
                        }
                    }
                }
            }
        }
        if (PsiUtil.isRawSubstitutor(psiMethod, substitutor)) {
            PsiType psiTypeErasure2 = TypeConversionUtil.erasure(psiType);
            if (Comparing.equal(TypeConversionUtil.erasure(psiTypeSubstitute), psiTypeErasure2)) {
                return psiTypeErasure2;
            }
        }
        return PsiUtil.captureToplevelWildcards(psiTypeSubstitute, psiMethodCallExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ExpressionPsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void replaceChildInternal(ASTNode aSTNode, TreeElement treeElement) {
        if (aSTNode == null) {
            $$$reportNull$$$0(9);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(10);
        }
        if (getChildRole(aSTNode) == 91 && treeElement.getElementType() != JavaElementType.REFERENCE_EXPRESSION) {
            throw new IncorrectOperationException("ReferenceExpression expected; got: " + treeElement.getElementType());
        }
        super.replaceChildInternal(aSTNode, treeElement);
    }
}
