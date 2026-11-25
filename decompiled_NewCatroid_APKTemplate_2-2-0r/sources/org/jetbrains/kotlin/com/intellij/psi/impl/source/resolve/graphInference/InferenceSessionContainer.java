package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiCall;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiReturnStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.DefaultParameterTypeInferencePolicy;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ExpressionCompatibilityConstraint;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class InferenceSessionContainer {
    private static final Logger LOG = Logger.getInstance((Class<?>) InferenceSessionContainer.class);
    private final Map<PsiElement, InferenceSession> myNestedSessions = new HashMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 12 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 12 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "partialSubstitutor";
                break;
            case 2:
            case 7:
                objArr[0] = "parent";
                break;
            case 3:
                objArr[0] = "policy";
                break;
            case 4:
                objArr[0] = "typeParameters";
                break;
            case 5:
            case 10:
                objArr[0] = "parameters";
                break;
            case 6:
            case 11:
                objArr[0] = "arguments";
                break;
            case 8:
                objArr[0] = "currentMethod";
                break;
            case 9:
                objArr[0] = "parentSession";
                break;
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/InferenceSessionContainer";
                break;
            default:
                objArr[0] = "returnType";
                break;
        }
        if (i != 12) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/InferenceSessionContainer";
        } else {
            objArr[1] = "replaceVariables";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[2] = "infer";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "inferNested";
                break;
            case 12:
                break;
            default:
                objArr[2] = "registerNestedSession";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 12) {
            throw new IllegalStateException(str2);
        }
    }

    InferenceSessionContainer() {
    }

    public void registerNestedSession(InferenceSession inferenceSession) {
        this.myNestedSessions.put(inferenceSession.getContext(), inferenceSession);
        this.myNestedSessions.putAll(inferenceSession.getInferenceSessionContainer().myNestedSessions);
    }

    public PsiSubstitutor findNestedSubstitutor(PsiElement psiElement, PsiSubstitutor psiSubstitutor) {
        InferenceSession inferenceSession = this.myNestedSessions.get(PsiTreeUtil.getParentOfType(psiElement, PsiCall.class));
        return inferenceSession == null ? psiSubstitutor : inferenceSession.getInferenceSubstitution();
    }

    void registerNestedSession(InferenceSession inferenceSession, PsiType psiType, PsiExpression psiExpression) {
        InferenceSession inferenceSessionReduceExpressionCompatibilityConstraint;
        if (psiType == null) {
            $$$reportNull$$$0(0);
        }
        if (findNestedSubstitutor(((PsiCallExpression) psiExpression).getArgumentList(), null) != null || (inferenceSessionReduceExpressionCompatibilityConstraint = ExpressionCompatibilityConstraint.reduceExpressionCompatibilityConstraint(inferenceSession, psiExpression, psiType, false)) == null || inferenceSessionReduceExpressionCompatibilityConstraint == inferenceSession) {
            return;
        }
        registerNestedSession(inferenceSessionReduceExpressionCompatibilityConstraint);
        inferenceSession.propagateVariables(inferenceSessionReduceExpressionCompatibilityConstraint);
    }

    /* JADX WARN: Type inference failed for: r10v1, types: [org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSessionContainer$1NoContainer] */
    /* JADX WARN: Type inference failed for: r9v1, types: [org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSessionContainer$1NoContainer] */
    static PsiSubstitutor infer(final PsiTypeParameter[] psiTypeParameterArr, final PsiParameter[] psiParameterArr, final PsiExpression[] psiExpressionArr, final PsiSubstitutor psiSubstitutor, final PsiElement psiElement, final ParameterTypeInferencePolicy parameterTypeInferencePolicy, final MethodCandidateInfo methodCandidateInfo) {
        final PsiCall psiCall;
        InferenceSession inferenceSessionStartTopLevelInference;
        InferenceSession inferenceSession;
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(3);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(4);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(5);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(6);
        }
        PsiExpressionList argumentList = InferenceSession.getArgumentList(psiElement);
        if ((psiElement instanceof PsiCall) && !MethodCandidateInfo.isOverloadCheck(argumentList) && (psiCall = (PsiCall) PsiResolveHelper.ourGraphGuard.doPreventingRecursion(psiElement, false, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSessionContainer$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return InferenceSessionContainer.lambda$infer$0(psiElement);
            }
        })) != null) {
            if (MethodCandidateInfo.isOverloadCheck() || !PsiDiamondType.ourDiamondGuard.currentStack().isEmpty() || !parameterTypeInferencePolicy.equals(DefaultParameterTypeInferencePolicy.INSTANCE)) {
                inferenceSessionStartTopLevelInference = startTopLevelInference(psiCall, parameterTypeInferencePolicy, methodCandidateInfo);
            } else {
                inferenceSessionStartTopLevelInference = (InferenceSession) CachedValuesManager.getCachedValue((PsiElement) psiCall, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSessionContainer$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                    public final CachedValueProvider.Result compute() {
                        return InferenceSessionContainer.lambda$infer$1(psiCall);
                    }
                });
                if (inferenceSessionStartTopLevelInference != null && (inferenceSession = inferenceSessionStartTopLevelInference.getInferenceSessionContainer().myNestedSessions.get(psiElement)) != null) {
                    for (PsiTypeParameter psiTypeParameter : psiTypeParameterArr) {
                        if (!inferenceSession.getInferenceSubstitution().getSubstitutionMap().containsKey(psiTypeParameter)) {
                            inferenceSessionStartTopLevelInference = startTopLevelInference(psiCall, parameterTypeInferencePolicy, methodCandidateInfo);
                            break;
                        }
                    }
                }
            }
            if (inferenceSessionStartTopLevelInference != null) {
                PsiSubstitutor psiSubstitutorInferNested = inferNested(psiParameterArr, psiExpressionArr, (PsiCall) psiElement, methodCandidateInfo, inferenceSessionStartTopLevelInference);
                return psiSubstitutorInferNested != null ? psiSubstitutorInferNested : new Object() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSessionContainer.1NoContainer
                    PsiSubstitutor infer(boolean z) {
                        InferenceSession inferenceSession2 = new InferenceSession(psiTypeParameterArr, psiSubstitutor, psiElement.getManager(), psiElement, parameterTypeInferencePolicy);
                        PsiParameter[] psiParameterArr2 = psiParameterArr;
                        PsiExpression[] psiExpressionArr2 = psiExpressionArr;
                        MethodCandidateInfo methodCandidateInfo2 = methodCandidateInfo;
                        PsiMethod element = methodCandidateInfo2 != null ? methodCandidateInfo2.getElement() : null;
                        MethodCandidateInfo methodCandidateInfo3 = methodCandidateInfo;
                        inferenceSession2.initExpressionConstraints(psiParameterArr2, psiExpressionArr2, element, methodCandidateInfo3 != null && methodCandidateInfo3.isVarargs());
                        return inferenceSession2.performGuardedInference(psiParameterArr, psiExpressionArr, psiElement, methodCandidateInfo, PsiSubstitutor.EMPTY, z);
                    }
                }.infer(true);
            }
            if (psiCall instanceof PsiMethodCallExpression) {
                return new InferenceSession(psiTypeParameterArr, psiSubstitutor, psiElement.getManager(), psiElement, parameterTypeInferencePolicy).prepareSubstitution();
            }
        }
        return new Object() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSessionContainer.1NoContainer
            PsiSubstitutor infer(boolean z) {
                InferenceSession inferenceSession2 = new InferenceSession(psiTypeParameterArr, psiSubstitutor, psiElement.getManager(), psiElement, parameterTypeInferencePolicy);
                PsiParameter[] psiParameterArr2 = psiParameterArr;
                PsiExpression[] psiExpressionArr2 = psiExpressionArr;
                MethodCandidateInfo methodCandidateInfo2 = methodCandidateInfo;
                PsiMethod element = methodCandidateInfo2 != null ? methodCandidateInfo2.getElement() : null;
                MethodCandidateInfo methodCandidateInfo3 = methodCandidateInfo;
                inferenceSession2.initExpressionConstraints(psiParameterArr2, psiExpressionArr2, element, methodCandidateInfo3 != null && methodCandidateInfo3.isVarargs());
                return inferenceSession2.performGuardedInference(psiParameterArr, psiExpressionArr, psiElement, methodCandidateInfo, PsiSubstitutor.EMPTY, z);
            }
        }.infer(false);
    }

    static /* synthetic */ PsiCall lambda$infer$0(PsiElement psiElement) {
        if (!(psiElement instanceof PsiExpression) || PsiPolyExpressionUtil.isPolyExpression((PsiExpression) psiElement)) {
            return LambdaUtil.treeWalkUp(psiElement);
        }
        return null;
    }

    static /* synthetic */ CachedValueProvider.Result lambda$infer$1(PsiCall psiCall) {
        return new CachedValueProvider.Result(startTopLevelInference(psiCall, DefaultParameterTypeInferencePolicy.INSTANCE, null), PsiModificationTracker.MODIFICATION_COUNT);
    }

    private static PsiSubstitutor inferNested(PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiCall psiCall, MethodCandidateInfo methodCandidateInfo, InferenceSession inferenceSession) {
        PsiCall psiCall2;
        if (psiCall == null) {
            $$$reportNull$$$0(7);
        }
        if (methodCandidateInfo == null) {
            $$$reportNull$$$0(8);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(9);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(10);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(11);
        }
        if (inferenceSession.getIncompatibleErrorMessages() != null && !MethodCandidateInfo.isOverloadCheck()) {
            return null;
        }
        CompoundInitialState compoundInitialStateCreateState = createState(inferenceSession);
        InitialInferenceState initialState = compoundInitialStateCreateState.getInitialState(psiCall);
        if (initialState != null) {
            return new InferenceSession(initialState, inferenceSession.getInferencePolicy()).collectAdditionalAndInfer(psiParameterArr, psiExpressionArr, methodCandidateInfo, compoundInitialStateCreateState.getInitialSubstitutor());
        }
        PsiElement psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiCall.getLightParent());
        while (true) {
            if (psiElementSkipParenthesizedExprUp instanceof PsiReturnStatement) {
                psiElementSkipParenthesizedExprUp = PsiTreeUtil.getParentOfType(psiElementSkipParenthesizedExprUp, (Class<PsiElement>) PsiLambdaExpression.class);
            }
            if (psiElementSkipParenthesizedExprUp instanceof PsiConditionalExpression) {
                psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiElementSkipParenthesizedExprUp.getLightParent());
            }
            if (!(psiElementSkipParenthesizedExprUp instanceof PsiLambdaExpression) || (psiCall2 = (PsiCall) PsiTreeUtil.getParentOfType(psiElementSkipParenthesizedExprUp, PsiCall.class)) == null) {
                break;
            }
            InitialInferenceState initialState2 = compoundInitialStateCreateState.getInitialState(psiCall2);
            if (initialState2 != null) {
                PsiExpressionList argumentList = psiCall2.getArgumentList();
                int lambdaIdx = argumentList != null ? LambdaUtil.getLambdaIdx(argumentList, psiElementSkipParenthesizedExprUp) : -1;
                JavaResolveResult diamondsAwareResolveResult = PsiDiamondType.getDiamondsAwareResolveResult(psiCall2);
                PsiElement element = diamondsAwareResolveResult.getElement();
                if (!(element instanceof PsiMethod) || lambdaIdx <= -1) {
                    break;
                    break;
                }
                PsiParameter[] parameters = ((PsiMethod) element).getParameterList().getParameters();
                if (parameters.length == 0) {
                    break;
                }
                InferenceSession inferenceSession2 = new InferenceSession(initialState2, inferenceSession.getInferencePolicy());
                inferenceSession2.collectAdditionalAndInfer(parameters, argumentList.getExpressions(), (MethodCandidateInfo) diamondsAwareResolveResult, compoundInitialStateCreateState.getInitialSubstitutor());
                return inferNested(psiParameterArr, psiExpressionArr, psiCall, methodCandidateInfo, inferenceSession2);
            }
            psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiCall2.getLightParent());
        }
        return null;
    }

    private static CompoundInitialState createState(InferenceSession inferenceSession) {
        final PsiSubstitutor psiSubstitutorReplaceVariables = replaceVariables(inferenceSession.getInferenceVariables());
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        InferenceSessionContainer inferenceSessionContainer = new InferenceSessionContainer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSessionContainer.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSessionContainer
            public PsiSubstitutor findNestedSubstitutor(PsiElement psiElement, PsiSubstitutor psiSubstitutor) {
                PsiSubstitutor psiSubstitutorFindNestedSubstitutor = super.findNestedSubstitutor(psiElement, null);
                if (psiSubstitutorFindNestedSubstitutor != null) {
                    return psiSubstitutorFindNestedSubstitutor;
                }
                InitialInferenceState initialInferenceState = (InitialInferenceState) linkedHashMap.get(PsiTreeUtil.getParentOfType(psiElement, PsiCall.class));
                if (initialInferenceState != null) {
                    return initialInferenceState.getInferenceSubstitutor();
                }
                return super.findNestedSubstitutor(psiElement, psiSubstitutor);
            }
        };
        Map<PsiElement, InferenceSession> map = inferenceSession.getInferenceSessionContainer().myNestedSessions;
        if (!map.isEmpty()) {
            final PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(map.keySet().iterator().next().getProject());
            List map2 = ContainerUtil.map((Collection) inferenceSession.getInferenceVariables(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSessionContainer$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return InferenceSessionContainer.lambda$createState$2(psiSubstitutorReplaceVariables, elementFactory, (InferenceVariable) obj);
                }
            });
            for (Map.Entry<PsiElement, InferenceSession> entry : map.entrySet()) {
                linkedHashMap.put(entry.getKey(), entry.getValue().createInitialState(inferenceSessionContainer, map2, psiSubstitutorReplaceVariables));
            }
        }
        HashMap map3 = new HashMap();
        for (InferenceVariable inferenceVariable : inferenceSession.getInferenceVariables()) {
            PsiType instantiation = inferenceVariable.getInstantiation();
            if (instantiation != PsiType.NULL) {
                PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(psiSubstitutorReplaceVariables.substitute(inferenceVariable));
                if (psiClassResolveClassInClassTypeOnly instanceof InferenceVariable) {
                    map3.put((PsiTypeParameter) psiClassResolveClassInClassTypeOnly, instantiation);
                }
            }
        }
        return new CompoundInitialState(PsiSubstitutor.createSubstitutor(map3), linkedHashMap);
    }

    static /* synthetic */ InitialInferenceState.VariableInfo lambda$createState$2(PsiSubstitutor psiSubstitutor, PsiElementFactory psiElementFactory, InferenceVariable inferenceVariable) {
        return new InitialInferenceState.VariableInfo(inferenceVariable, psiSubstitutor, psiElementFactory);
    }

    private static InferenceSession startTopLevelInference(final PsiCall psiCall, final ParameterTypeInferencePolicy parameterTypeInferencePolicy, final MethodCandidateInfo methodCandidateInfo) {
        final JavaResolveResult diamondsAwareResolveResult = PsiDiamondType.getDiamondsAwareResolveResult(psiCall);
        if (!(diamondsAwareResolveResult instanceof MethodCandidateInfo)) {
            return null;
        }
        final PsiMethod element = ((MethodCandidateInfo) diamondsAwareResolveResult).getElement();
        final PsiParameter[] parameters = element.getParameterList().getParameters();
        PsiExpressionList argumentList = psiCall.getArgumentList();
        LOG.assertTrue(argumentList != null, psiCall);
        final PsiExpression[] expressions = argumentList.getExpressions();
        return (InferenceSession) PsiResolveHelper.ourGraphGuard.doPreventingRecursion(psiCall, true, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSessionContainer$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return InferenceSessionContainer.lambda$startTopLevelInference$3(element, diamondsAwareResolveResult, psiCall, parameterTypeInferencePolicy, methodCandidateInfo, parameters, expressions);
            }
        });
    }

    static /* synthetic */ InferenceSession lambda$startTopLevelInference$3(PsiMethod psiMethod, JavaResolveResult javaResolveResult, PsiCall psiCall, ParameterTypeInferencePolicy parameterTypeInferencePolicy, MethodCandidateInfo methodCandidateInfo, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr) {
        MethodCandidateInfo methodCandidateInfo2 = (MethodCandidateInfo) javaResolveResult;
        InferenceSession inferenceSession = new InferenceSession(psiMethod.getTypeParameters(), methodCandidateInfo2.getSiteSubstitutor(), psiCall.getManager(), psiCall, parameterTypeInferencePolicy);
        inferenceSession.setCurrentMethod(methodCandidateInfo);
        inferenceSession.initExpressionConstraints(psiParameterArr, psiExpressionArr, psiMethod, methodCandidateInfo2.isVarargs());
        inferenceSession.performGuardedInference(psiParameterArr, psiExpressionArr, psiCall, methodCandidateInfo2, PsiSubstitutor.EMPTY, false);
        return inferenceSession;
    }

    private static PsiSubstitutor replaceVariables(Collection<InferenceVariable> collection) {
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        InferenceVariable[] inferenceVariableArr = (InferenceVariable[]) collection.toArray(new InferenceVariable[0]);
        for (InferenceVariable inferenceVariable : inferenceVariableArr) {
            InferenceVariable inferenceVariable2 = new InferenceVariable(inferenceVariable.getCallContext(), inferenceVariable.getParameter(), inferenceVariable.getName());
            map.put(inferenceVariable, JavaPsiFacade.getElementFactory(inferenceVariable.getProject()).createType(inferenceVariable2));
            arrayList.add(inferenceVariable2);
            if (inferenceVariable.isThrownBound()) {
                inferenceVariable2.setThrownBound();
            }
            inferenceVariable2.putUserData(InferenceSession.ORIGINAL_CAPTURE, (PsiCapturedWildcardType) inferenceVariable.getUserData(InferenceSession.ORIGINAL_CAPTURE));
        }
        PsiSubstitutor psiSubstitutorCreateSubstitutor = PsiSubstitutor.createSubstitutor(map);
        for (int i = 0; i < arrayList.size(); i++) {
            InferenceVariable inferenceVariable3 = (InferenceVariable) arrayList.get(i);
            for (InferenceBound inferenceBound : InferenceBound.values()) {
                Iterator<PsiType> it = inferenceVariableArr[i].getBounds(inferenceBound).iterator();
                while (it.hasNext()) {
                    inferenceVariable3.addBound(psiSubstitutorCreateSubstitutor.substitute(it.next()), inferenceBound, null);
                }
            }
        }
        if (psiSubstitutorCreateSubstitutor == null) {
            $$$reportNull$$$0(12);
        }
        return psiSubstitutorCreateSubstitutor;
    }
}
