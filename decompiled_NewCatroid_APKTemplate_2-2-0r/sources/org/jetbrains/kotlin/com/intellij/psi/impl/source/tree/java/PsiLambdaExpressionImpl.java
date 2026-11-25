package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.function.Supplier;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.icons.AllIcons;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpressionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReturnStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.AnalysisCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowFactory;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowOptions;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.FunctionalExpressionStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.FunctionalInterfaceParameterizationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiLambdaExpressionImpl extends JavaStubPsiElement<FunctionalExpressionStub<PsiLambdaExpression>> implements PsiLambdaExpression {
    private static final ControlFlowPolicy ourPolicy = new ControlFlowPolicy() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLambdaExpressionImpl.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "psiParameter";
            } else if (i != 2) {
                objArr[0] = "refExpr";
            } else {
                objArr[0] = "psiVariable";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiLambdaExpressionImpl$1";
            if (i == 1) {
                objArr[2] = "isParameterAccepted";
            } else if (i != 2) {
                objArr[2] = "getUsedVariable";
            } else {
                objArr[2] = "isLocalVariableAccepted";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
        public PsiVariable getUsedVariable(PsiReferenceExpression psiReferenceExpression) {
            if (psiReferenceExpression != null) {
                return null;
            }
            $$$reportNull$$$0(0);
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
        public boolean isLocalVariableAccepted(PsiLocalVariable psiLocalVariable) {
            if (psiLocalVariable != null) {
                return true;
            }
            $$$reportNull$$$0(2);
            return true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
        public boolean isParameterAccepted(PsiParameter psiParameter) {
            if (psiParameter == null) {
                $$$reportNull$$$0(1);
            }
            return true;
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 8 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 8 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "node";
                break;
            case 2:
            case 8:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiLambdaExpressionImpl";
                break;
            case 3:
                objArr[0] = "visitor";
                break;
            case 4:
                objArr[0] = "processor";
                break;
            case 5:
                objArr[0] = "state";
                break;
            case 6:
                objArr[0] = "place";
                break;
            case 7:
                objArr[0] = "paramType";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 2) {
            objArr[1] = "getParameterList";
        } else if (i == 8 || i == 9) {
            objArr[1] = "toArray";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiLambdaExpressionImpl";
        }
        switch (i) {
            case 2:
            case 8:
            case 9:
                break;
            case 3:
                objArr[2] = "accept";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "processDeclarations";
                break;
            case 7:
                objArr[2] = "toArray";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 8 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiLambdaExpressionImpl(FunctionalExpressionStub<PsiLambdaExpression> functionalExpressionStub) {
        super(functionalExpressionStub, JavaStubElementTypes.LAMBDA_EXPRESSION);
        if (functionalExpressionStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiLambdaExpressionImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiParameterList getParameterList() {
        PsiParameterList psiParameterList = (PsiParameterList) PsiTreeUtil.getRequiredChildOfType(this, PsiParameterList.class);
        if (psiParameterList == null) {
            $$$reportNull$$$0(2);
        }
        return psiParameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiElement getBody() {
        PsiElement lastChild = getLastChild();
        if ((lastChild instanceof PsiExpression) || (lastChild instanceof PsiCodeBlock)) {
            return lastChild;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression
    public PsiType getFunctionalInterfaceType() {
        return getGroundTargetType(LambdaUtil.getFunctionalInterfaceType(this, true));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression
    public boolean isVoidCompatible() {
        PsiElement body = getBody();
        if (!(body instanceof PsiCodeBlock)) {
            return true;
        }
        for (PsiReturnStatement psiReturnStatement : PsiUtil.findReturnStatements((PsiCodeBlock) body)) {
            if (psiReturnStatement.getReturnValue() != null) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression
    public boolean isValueCompatible() {
        Boolean bool = (Boolean) MethodCandidateInfo.ourOverloadGuard.doPreventingRecursion(this, false, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLambdaExpressionImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return this.f$0.m6682x86261345();
            }
        });
        return bool != null && bool.booleanValue();
    }

    /* JADX INFO: renamed from: lambda$isValueCompatible$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-java-PsiLambdaExpressionImpl, reason: not valid java name */
    /* synthetic */ Boolean m6682x86261345() {
        return Boolean.valueOf(isValueCompatibleNoCache());
    }

    private boolean isValueCompatibleNoCache() {
        PsiElement body = getBody();
        if (!(body instanceof PsiCodeBlock)) {
            return true;
        }
        try {
            ControlFlow controlFlow = ControlFlowFactory.getControlFlow(body, ourPolicy, ControlFlowOptions.NO_CONST_EVALUATE);
            int startOffset = controlFlow.getStartOffset(body);
            int endOffset = controlFlow.getEndOffset(body);
            if (startOffset != -1 && endOffset != -1 && ControlFlowUtil.canCompleteNormally(controlFlow, startOffset, endOffset)) {
                return false;
            }
        } catch (AnalysisCanceledException unused) {
        }
        for (PsiReturnStatement psiReturnStatement : PsiUtil.findReturnStatements((PsiCodeBlock) body)) {
            if (psiReturnStatement.getReturnValue() == null) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        return new PsiLambdaExpressionType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitLambdaExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(4);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(6);
        }
        return PsiImplUtil.processDeclarationsInLambda(this, psiScopeProcessor, resolveState, psiElement, psiElement2);
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiLambdaExpression";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression
    public boolean hasFormalParameterTypes() {
        for (PsiParameter psiParameter : getParameterList().getParameters()) {
            PsiTypeElement typeElement = psiParameter.getTypeElement();
            if (typeElement == null || typeElement.isInferredType()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression
    public boolean isAcceptable(PsiType psiType, PsiMethod psiMethod) {
        if (psiType instanceof PsiIntersectionType) {
            for (PsiType psiType2 : ((PsiIntersectionType) psiType).getConjuncts()) {
                if (isAcceptable(psiType2)) {
                    return true;
                }
            }
            return false;
        }
        PsiExpressionList psiExpressionList = (PsiExpressionList) PsiTreeUtil.getParentOfType(this, PsiExpressionList.class);
        if (MethodCandidateInfo.isOverloadCheck(psiExpressionList) && psiMethod != null && ((hasFormalParameterTypes() && !InferenceSession.isPertinentToApplicability(this, psiMethod)) || LambdaUtil.isPotentiallyCompatibleWithTypeParameter(this, psiExpressionList, psiMethod))) {
            return true;
        }
        PsiType groundTargetType = getGroundTargetType(psiType);
        if (!isPotentiallyCompatible(groundTargetType)) {
            return false;
        }
        if (MethodCandidateInfo.isOverloadCheck(psiExpressionList) && !hasFormalParameterTypes()) {
            return true;
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(groundTargetType);
        PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(classResolveResultResolveGenericsClassInType);
        if (functionalInterfaceMethod == null || functionalInterfaceMethod.hasTypeParameters()) {
            return false;
        }
        final PsiSubstitutor substitutor = LambdaUtil.getSubstitutor(functionalInterfaceMethod, classResolveResultResolveGenericsClassInType);
        if (hasFormalParameterTypes()) {
            PsiParameter[] parameters = getParameterList().getParameters();
            PsiType[] parameterTypes = functionalInterfaceMethod.getSignature(substitutor).getParameterTypes();
            int length = parameters.length;
            for (int i = 0; i < length; i++) {
                PsiParameter psiParameter = parameters[i];
                if (psiParameter.getTypeElement() != null && !toArray(psiParameter.getType()).equals(toArray(parameterTypes[i]))) {
                    return false;
                }
            }
        }
        final PsiType returnType = functionalInterfaceMethod.getReturnType();
        if (returnType == null || PsiType.VOID.equals(returnType)) {
            return true;
        }
        return ((Boolean) LambdaUtil.performWithTargetType(this, groundTargetType, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLambdaExpressionImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6681x56c80e71(substitutor, returnType);
            }
        })).booleanValue();
    }

    /* JADX INFO: renamed from: lambda$isAcceptable$1$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-java-PsiLambdaExpressionImpl, reason: not valid java name */
    /* synthetic */ Boolean m6681x56c80e71(PsiSubstitutor psiSubstitutor, PsiType psiType) {
        return Boolean.valueOf(LambdaUtil.checkReturnTypeCompatible(this, psiSubstitutor.substitute(psiType)) == null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression, org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression
    public boolean isPotentiallyCompatible(PsiType psiType) {
        PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(psiType);
        if (functionalInterfaceMethod == null || getParameterList().getParametersCount() != functionalInterfaceMethod.getParameterList().getParametersCount()) {
            return false;
        }
        PsiType returnType = functionalInterfaceMethod.getReturnType();
        PsiElement body = getBody();
        if (!PsiType.VOID.equals(returnType)) {
            return ((body instanceof PsiCodeBlock) && isValueCompatible()) || (body instanceof PsiExpression);
        }
        if (body instanceof PsiCodeBlock) {
            return isVoidCompatible();
        }
        return LambdaUtil.isExpressionStatementExpression(body);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression
    public PsiType getGroundTargetType(PsiType psiType) {
        return FunctionalInterfaceParameterizationUtil.getGroundTargetType(psiType, this);
    }

    private static PsiType toArray(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(7);
        }
        if (!(psiType instanceof PsiEllipsisType)) {
            if (psiType == null) {
                $$$reportNull$$$0(9);
            }
            return psiType;
        }
        PsiType arrayType = ((PsiEllipsisType) psiType).toArrayType();
        if (arrayType == null) {
            $$$reportNull$$$0(8);
        }
        return arrayType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.Iconable
    public Icon getIcon(int i) {
        return AllIcons.Nodes.Lambda;
    }
}
