package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.ConstraintType;
import org.jetbrains.kotlin.com.intellij.psi.GenericsUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCall;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReturnStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.ThreadLocalTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.DefaultParameterTypeInferencePolicy;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.CheckedExceptionCompatibilityConstraint;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ExpressionCompatibilityConstraint;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.InputOutputConstraintFormula;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.StrictSubtypingConstraint;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.TypeCompatibilityConstraint;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.TypeEqualityConstraint;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.JavaClassSupers;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypesDistinctProver;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.MathUtil;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.text.UniqueNameGenerator;

/* JADX INFO: loaded from: classes3.dex */
public class InferenceSession {
    private int myConstraintIdx;
    private final List<ConstraintFormula> myConstraints;
    private final Set<ConstraintFormula> myConstraintsCopy;
    private final PsiElement myContext;
    private MethodCandidateInfo myCurrentMethod;
    private boolean myErased;
    private List<String> myErrorMessages;
    public final InferenceIncorporationPhase myIncorporationPhase;
    private final InferenceSessionContainer myInferenceSessionContainer;
    private PsiSubstitutor myInferenceSubstitution;
    protected final Set<InferenceVariable> myInferenceVariables;
    private final PsiManager myManager;
    private final ParameterTypeInferencePolicy myPolicy;
    private PsiSubstitutor myRestoreNameSubstitution;
    private PsiSubstitutor mySiteSubstitutor;
    private ThreadLocalTypes myTempTypes;
    private static final Logger LOG = Logger.getInstance((Class<?>) InferenceSession.class);
    private static final Function<Pair<PsiType, PsiType>, PsiType> UPPER_BOUND_FUNCTION = new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda7
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            Pair pair = (Pair) obj;
            return GenericsUtil.getGreatestLowerBound((PsiType) pair.first, (PsiType) pair.second);
        }
    };
    static final Key<PsiCapturedWildcardType> ORIGINAL_CAPTURE = Key.create("ORIGINAL_CAPTURE");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 21:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 4:
            case 9:
            case 11:
            case 18:
            case 20:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 21:
                i2 = 3;
                break;
            case 4:
            case 9:
            case 11:
            case 18:
            case 20:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "upperBound";
                break;
            case 2:
                objArr[0] = "place";
                break;
            case 3:
                objArr[0] = "method";
                break;
            case 4:
            case 9:
            case 11:
            case 18:
            case 20:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/InferenceSession";
                break;
            case 5:
                objArr[0] = "properties";
                break;
            case 6:
                objArr[0] = "psiSubstitutor";
                break;
            case 7:
                objArr[0] = "parameters";
                break;
            case 8:
                objArr[0] = "args";
                break;
            case 10:
            case 12:
                objArr[0] = "initialSubstitutor";
                break;
            case 13:
                objArr[0] = "parent";
                break;
            case 14:
                objArr[0] = "functionalType";
                break;
            case 15:
                objArr[0] = "typeParameters";
                break;
            case 16:
                objArr[0] = "targetType";
                break;
            case 17:
                objArr[0] = "substitutor";
                break;
            case 19:
                objArr[0] = "incompatibleBoundsMessage";
                break;
            case 21:
                objArr[0] = TypedValues.TransitionType.S_FROM;
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 21:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/InferenceSession";
                break;
            case 4:
            case 9:
                objArr[1] = "infer";
                break;
            case 11:
                objArr[1] = "performGuardedInference";
                break;
            case 18:
                objArr[1] = "resolveSubset";
                break;
            case 20:
                objArr[1] = "getOutputVariables";
                break;
            default:
                objArr[1] = "getInferencePolicy";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "createTypeParameterTypeWithUpperBound";
                break;
            case 3:
                objArr[2] = "getTypeParameterOwner";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "collectAdditionalAndInfer";
                break;
            case 10:
                objArr[2] = "performGuardedInference";
                break;
            case 12:
                objArr[2] = "doInfer";
                break;
            case 13:
                objArr[2] = "isPertinentToApplicabilityCheckOnContainingCall";
                break;
            case 14:
                objArr[2] = "processReturnExpression";
                break;
            case 15:
                objArr[2] = "initBounds";
                break;
            case 16:
                objArr[2] = "registerReturnTypeConstraints";
                break;
            case 17:
                objArr[2] = "resolveBounds";
                break;
            case 19:
                objArr[2] = "registerIncompatibleErrorMessage";
                break;
            case 21:
                objArr[2] = "propagateVariables";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 21:
                throw new IllegalArgumentException(str2);
            case 4:
            case 9:
            case 11:
            case 18:
            case 20:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public InferenceSession(InitialInferenceState initialInferenceState, ParameterTypeInferencePolicy parameterTypeInferencePolicy) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.myInferenceVariables = linkedHashSet;
        this.myConstraints = new ArrayList();
        this.myConstraintsCopy = new HashSet();
        this.myIncorporationPhase = new InferenceIncorporationPhase(this);
        this.myInferenceSubstitution = PsiSubstitutor.EMPTY;
        this.myRestoreNameSubstitution = PsiSubstitutor.EMPTY;
        PsiElement context = initialInferenceState.getContext();
        this.myContext = context;
        this.myManager = context.getManager();
        this.myInferenceSubstitution = initialInferenceState.getInferenceSubstitutor();
        linkedHashSet.addAll(initialInferenceState.getInferenceVariables());
        this.mySiteSubstitutor = initialInferenceState.getSiteSubstitutor();
        for (Pair<InferenceVariable[], PsiClassType> pair : initialInferenceState.getCaptures()) {
            this.myIncorporationPhase.addCapture(pair.first, pair.second);
        }
        this.myInferenceSessionContainer = initialInferenceState.getInferenceSessionContainer();
        this.myErased = initialInferenceState.isErased();
        this.myPolicy = parameterTypeInferencePolicy;
    }

    public InferenceSession(PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, PsiSubstitutor psiSubstitutor, PsiManager psiManager, PsiElement psiElement) {
        this.myInferenceVariables = new LinkedHashSet();
        this.myConstraints = new ArrayList();
        this.myConstraintsCopy = new HashSet();
        this.myIncorporationPhase = new InferenceIncorporationPhase(this);
        this.myInferenceSubstitution = PsiSubstitutor.EMPTY;
        this.myRestoreNameSubstitution = PsiSubstitutor.EMPTY;
        this.myManager = psiManager;
        this.mySiteSubstitutor = psiSubstitutor;
        this.myContext = psiElement;
        initBounds(psiTypeParameterArr);
        LOG.assertTrue(psiTypeArr.length == psiTypeArr2.length);
        for (int i = 0; i < psiTypeArr.length; i++) {
            PsiType psiTypeSubstitute = this.mySiteSubstitutor.substitute(psiTypeArr2[i]);
            PsiType psiTypeSubstituteWithInferenceVariables = substituteWithInferenceVariables(psiTypeArr[i]);
            PsiType psiTypeSubstituteWithInferenceVariables2 = substituteWithInferenceVariables(psiTypeSubstitute);
            if (psiTypeSubstituteWithInferenceVariables != null && psiTypeSubstituteWithInferenceVariables2 != null) {
                addConstraint(new TypeCompatibilityConstraint(psiTypeSubstituteWithInferenceVariables, psiTypeSubstituteWithInferenceVariables2));
            }
        }
        this.myPolicy = DefaultParameterTypeInferencePolicy.INSTANCE;
        this.myInferenceSessionContainer = new InferenceSessionContainer();
    }

    public InferenceSession(PsiTypeParameter[] psiTypeParameterArr, PsiSubstitutor psiSubstitutor, PsiManager psiManager, PsiElement psiElement) {
        this(psiTypeParameterArr, psiSubstitutor, psiManager, psiElement, DefaultParameterTypeInferencePolicy.INSTANCE);
    }

    public InferenceSession(PsiTypeParameter[] psiTypeParameterArr, PsiSubstitutor psiSubstitutor, PsiManager psiManager, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy) {
        this.myInferenceVariables = new LinkedHashSet();
        this.myConstraints = new ArrayList();
        this.myConstraintsCopy = new HashSet();
        this.myIncorporationPhase = new InferenceIncorporationPhase(this);
        this.myInferenceSubstitution = PsiSubstitutor.EMPTY;
        this.myRestoreNameSubstitution = PsiSubstitutor.EMPTY;
        this.myManager = psiManager;
        this.mySiteSubstitutor = psiSubstitutor;
        this.myContext = psiElement;
        this.myPolicy = parameterTypeInferencePolicy;
        initBounds(psiTypeParameterArr);
        this.myInferenceSessionContainer = new InferenceSessionContainer();
    }

    public MethodCandidateInfo getCurrentMethod(PsiExpressionList psiExpressionList) {
        MethodCandidateInfo methodCandidateInfo = this.myCurrentMethod;
        if (methodCandidateInfo == null || !methodCandidateInfo.isOnArgumentList(psiExpressionList)) {
            return null;
        }
        return this.myCurrentMethod;
    }

    public void setCurrentMethod(MethodCandidateInfo methodCandidateInfo) {
        this.myCurrentMethod = methodCandidateInfo;
    }

    public ParameterTypeInferencePolicy getInferencePolicy() {
        ParameterTypeInferencePolicy parameterTypeInferencePolicy = this.myPolicy;
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(0);
        }
        return parameterTypeInferencePolicy;
    }

    public static PsiType createTypeParameterTypeWithUpperBound(PsiType psiType, PsiElement psiElement) {
        if (psiType == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiElement.getProject());
        PsiTypeParameter psiTypeParameterCreateTypeParameterFromText = elementFactory.createTypeParameterFromText(ExifInterface.GPS_DIRECTION_TRUE, psiElement);
        TypeConversionUtil.setInferredBoundsForSynthetic(psiTypeParameterCreateTypeParameterFromText, null, psiType);
        return elementFactory.createType(psiTypeParameterCreateTypeParameterFromText);
    }

    public void initExpressionConstraints(PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiMethod psiMethod, boolean z) {
        PsiExpression psiExpression;
        if (psiMethod != null) {
            initThrowsConstraints(psiMethod);
        }
        if (psiParameterArr.length > 0) {
            for (int i = 0; i < psiExpressionArr.length; i++) {
                if ((z || i < psiParameterArr.length) && (psiExpression = psiExpressionArr[i]) != null && isPertinentToApplicability(psiExpression, psiMethod)) {
                    PsiType parameterType = getParameterType(psiParameterArr, i, this.mySiteSubstitutor, z);
                    if (psiMethod != null && PsiUtil.isRawSubstitutor(psiMethod, this.mySiteSubstitutor)) {
                        parameterType = TypeConversionUtil.erasure(parameterType);
                    }
                    LOG.assertTrue(parameterType != null);
                    if (!ignoreLambdaConstraintTree(psiExpressionArr[i])) {
                        addConstraint(new ExpressionCompatibilityConstraint(psiExpressionArr[i], substituteWithInferenceVariables(parameterType)));
                    }
                }
            }
        }
    }

    public void initThrowsConstraints(PsiMethod psiMethod) {
        for (PsiClassType psiClassType : psiMethod.getThrowsList().getReferencedTypes()) {
            InferenceVariable inferenceVariable = getInferenceVariable(substituteWithInferenceVariables(psiClassType));
            if (inferenceVariable != null) {
                inferenceVariable.setThrownBound();
            }
        }
    }

    static PsiExpressionList getArgumentList(PsiElement psiElement) {
        if (psiElement instanceof PsiCall) {
            return ((PsiCall) psiElement).getArgumentList();
        }
        if (psiElement instanceof PsiAnonymousClass) {
            return getArgumentList(psiElement.getLightParent());
        }
        return null;
    }

    public static boolean isPertinentToApplicability(PsiExpression psiExpression, PsiMethod psiMethod) {
        return isPertinentToApplicability(psiExpression, psiMethod, null);
    }

    /* JADX WARN: Code duplicated, block: B:35:0x008e  */
    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isPertinentToApplicability(PsiExpression psiExpression, PsiMethod psiMethod, PsiType psiType) {
        PsiType type;
        boolean z = psiExpression instanceof PsiLambdaExpression;
        if (((z && ((PsiLambdaExpression) psiExpression).hasFormalParameterTypes()) || ((psiExpression instanceof PsiMethodReferenceExpression) && ((PsiMethodReferenceExpression) psiExpression).isExact())) && psiMethod != null) {
            PsiElement psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiExpression.getLightParent());
            if (psiElementSkipParenthesizedExprUp instanceof PsiExpressionList) {
                PsiTypeParameterListOwner typeParameterOwner = getTypeParameterOwner(psiMethod, psiElementSkipParenthesizedExprUp.getLightParent());
                if (typeParameterOwner != null) {
                    int lambdaIdx = LambdaUtil.getLambdaIdx((PsiExpressionList) psiElementSkipParenthesizedExprUp, psiExpression);
                    PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
                    if (lambdaIdx > parameters.length - 1) {
                        type = parameters[parameters.length - 1].getType();
                        if (parameters[parameters.length - 1].isVarArgs()) {
                            type = ((PsiEllipsisType) type).getComponentType();
                        }
                    } else {
                        type = parameters[lambdaIdx].getType();
                    }
                    if (isTypeParameterType(typeParameterOwner, type)) {
                        return false;
                    }
                    psiType = type;
                } else {
                    psiType = null;
                }
            } else if (psiType == null || (!(psiElementSkipParenthesizedExprUp instanceof PsiLambdaExpression) && (!(psiElementSkipParenthesizedExprUp instanceof PsiReturnStatement) || PsiTreeUtil.getParentOfType(psiElementSkipParenthesizedExprUp, PsiLambdaExpression.class, true, (Class<? extends PsiElement>[]) new Class[]{PsiMethod.class}) == null))) {
                psiType = null;
            } else if (isTypeParameterType(psiMethod, psiType)) {
                return false;
            }
            if (z) {
                Iterator<PsiExpression> it = LambdaUtil.getReturnExpressions((PsiLambdaExpression) psiExpression).iterator();
                while (it.hasNext()) {
                    if (!isPertinentToApplicability(it.next(), psiMethod, LambdaUtil.getFunctionalInterfaceReturnType(psiType))) {
                        return false;
                    }
                }
                return true;
            }
        }
        if (z) {
            return ((PsiLambdaExpression) psiExpression).hasFormalParameterTypes();
        }
        if (psiExpression instanceof PsiMethodReferenceExpression) {
            return ((PsiMethodReferenceExpression) psiExpression).isExact();
        }
        if (psiExpression instanceof PsiParenthesizedExpression) {
            return isPertinentToApplicability(((PsiParenthesizedExpression) psiExpression).getExpression(), psiMethod);
        }
        if (psiExpression instanceof PsiConditionalExpression) {
            PsiConditionalExpression psiConditionalExpression = (PsiConditionalExpression) psiExpression;
            if (!isPertinentToApplicability(psiConditionalExpression.getThenExpression(), psiMethod) || !isPertinentToApplicability(psiConditionalExpression.getElseExpression(), psiMethod)) {
                return false;
            }
        }
        return true;
    }

    private static PsiTypeParameterListOwner getTypeParameterOwner(PsiMethod psiMethod, PsiElement psiElement) {
        PsiClass containingClass;
        if (psiMethod == null) {
            $$$reportNull$$$0(3);
        }
        if (psiMethod.getTypeParameters().length > 0 && (psiElement instanceof PsiCallExpression) && ((PsiCallExpression) psiElement).getTypeArgumentList().getTypeParameterElements().length == 0) {
            return psiMethod;
        }
        if (psiMethod.isConstructor() && (psiElement instanceof PsiNewExpression) && (containingClass = psiMethod.mo6092getContainingClass()) != null && containingClass.hasTypeParameters() && PsiDiamondType.hasDiamond((PsiNewExpression) psiElement)) {
            return containingClass;
        }
        return null;
    }

    private static boolean isTypeParameterType(PsiTypeParameterListOwner psiTypeParameterListOwner, PsiType psiType) {
        if (psiType instanceof PsiWildcardType) {
            return isTypeParameterType(psiTypeParameterListOwner, ((PsiWildcardType) psiType).getBound());
        }
        PsiClass psiClassResolveClassInType = PsiUtil.resolveClassInType(psiType);
        return (psiClassResolveClassInType instanceof PsiTypeParameter) && ((PsiTypeParameter) psiClassResolveClassInType).getOwner() == psiTypeParameterListOwner;
    }

    private static PsiType getParameterType(PsiParameter[] psiParameterArr, int i, PsiSubstitutor psiSubstitutor, boolean z) {
        if (psiSubstitutor == null) {
            return null;
        }
        if (z || i < psiParameterArr.length) {
            return psiSubstitutor.substitute(PsiTypesUtil.getParameterType(psiParameterArr, i, z));
        }
        return null;
    }

    public PsiSubstitutor infer() {
        PsiSubstitutor psiSubstitutorInfer = infer(null, null, null, null);
        if (psiSubstitutorInfer == null) {
            $$$reportNull$$$0(4);
        }
        return psiSubstitutorInfer;
    }

    PsiSubstitutor collectAdditionalAndInfer(PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, MethodCandidateInfo methodCandidateInfo, PsiSubstitutor psiSubstitutor) {
        if (methodCandidateInfo == null) {
            $$$reportNull$$$0(5);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(6);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(7);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(8);
        }
        return performGuardedInference(psiParameterArr, psiExpressionArr, this.myContext, methodCandidateInfo, psiSubstitutor, false);
    }

    public PsiSubstitutor infer(PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiElement psiElement, MethodCandidateInfo methodCandidateInfo) {
        PsiSubstitutor psiSubstitutorPerformGuardedInference = performGuardedInference(psiParameterArr, psiExpressionArr, psiElement, methodCandidateInfo, PsiSubstitutor.EMPTY, false);
        if (psiSubstitutorPerformGuardedInference == null) {
            $$$reportNull$$$0(9);
        }
        return psiSubstitutorPerformGuardedInference;
    }

    PsiSubstitutor performGuardedInference(final PsiParameter[] psiParameterArr, final PsiExpression[] psiExpressionArr, final PsiElement psiElement, final MethodCandidateInfo methodCandidateInfo, final PsiSubstitutor psiSubstitutor, boolean z) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(10);
        }
        if (!z) {
            z = (!MethodCandidateInfo.isOverloadCheck() && (psiElement instanceof PsiMethodCallExpression) && ((PsiMethodCallExpression) psiElement).getMethodExpression().multiResolve(false).length == 1) ? false : true;
        }
        PsiSubstitutor psiSubstitutor2 = (PsiSubstitutor) ThreadLocalTypes.performWithTypes(new java.util.function.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6667x6557a3d6(psiParameterArr, psiExpressionArr, psiElement, methodCandidateInfo, psiSubstitutor, (ThreadLocalTypes) obj);
            }
        }, z);
        if (psiSubstitutor2 == null) {
            $$$reportNull$$$0(11);
        }
        return psiSubstitutor2;
    }

    /* JADX INFO: renamed from: lambda$performGuardedInference$1$org-jetbrains-kotlin-com-intellij-psi-impl-source-resolve-graphInference-InferenceSession, reason: not valid java name */
    /* synthetic */ PsiSubstitutor m6667x6557a3d6(PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiElement psiElement, MethodCandidateInfo methodCandidateInfo, PsiSubstitutor psiSubstitutor, ThreadLocalTypes threadLocalTypes) {
        this.myTempTypes = threadLocalTypes;
        try {
            doInfer(psiParameterArr, psiExpressionArr, psiElement, methodCandidateInfo, psiSubstitutor);
            return prepareSubstitution();
        } finally {
            if (methodCandidateInfo != null) {
                List<String> list = this.myErrorMessages;
                if (list != null) {
                    methodCandidateInfo.setApplicabilityError(StringUtil.join((Collection<String>) list, "\n"));
                }
                if (this.myErased) {
                    methodCandidateInfo.setErased();
                }
            }
            this.myTempTypes = null;
        }
    }

    private void doInfer(PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiElement psiElement, MethodCandidateInfo methodCandidateInfo, PsiSubstitutor psiSubstitutor) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(12);
        }
        if (repeatInferencePhases()) {
            PsiExpressionList argumentList = getArgumentList(psiElement);
            if (methodCandidateInfo != null && argumentList != null && !MethodCandidateInfo.isOverloadCheck(argumentList)) {
                PsiMethod element = methodCandidateInfo.getElement();
                if ((psiElement instanceof PsiCallExpression) && PsiPolyExpressionUtil.isMethodCallPolyExpression((PsiExpression) psiElement, element)) {
                    PsiType returnType = element.getReturnType();
                    if (!PsiType.VOID.equals(returnType) && returnType != null) {
                        Ref ref = new Ref();
                        PsiType targetTypeFromParent = getTargetTypeFromParent(psiElement, ref, false);
                        if (targetTypeFromParent == null && ref.get() != null) {
                            return;
                        }
                        if (targetTypeFromParent != null && !PsiType.VOID.equals(targetTypeFromParent)) {
                            if (!PsiUtil.isRawSubstitutor(element, this.mySiteSubstitutor)) {
                                returnType = this.mySiteSubstitutor.substitute(returnType);
                            }
                            registerReturnTypeConstraints(returnType, targetTypeFromParent, this.myContext);
                        }
                    }
                }
                if (!repeatInferencePhases()) {
                    if (isPertinentToApplicabilityCheckOnContainingCall(psiElement)) {
                        return;
                    }
                } else if (psiParameterArr != null && psiParameterArr.length > 0 && psiExpressionArr != null && !isPertinentToApplicabilityCheckOnContainingCall(psiElement)) {
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    HashSet hashSet = new HashSet();
                    collectAdditionalConstraints(psiParameterArr, psiExpressionArr, element, this.mySiteSubstitutor, linkedHashSet, hashSet, methodCandidateInfo.isVarargs(), psiSubstitutor);
                    proceedWithAdditionalConstraints(linkedHashSet, hashSet);
                }
            }
            resolveBounds(this.myInferenceVariables, psiSubstitutor);
        }
    }

    private static boolean isPertinentToApplicabilityCheckOnContainingCall(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(13);
        }
        return ThreadLocalTypes.hasBindingFor(psiElement);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0061  */
    /* JADX WARN: Code duplicated, block: B:24:0x006c  */
    /* JADX WARN: Code duplicated, block: B:25:0x0073  */
    /* JADX WARN: Code duplicated, block: B:30:0x0080  */
    private void collectAdditionalConstraints(PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, Set<ConstraintFormula> set, Set<ConstraintFormula> set2, boolean z, PsiSubstitutor psiSubstitutor2) {
        PsiType psiTypeSubstitute;
        JavaResolveResult diamondsAwareResolveResult;
        PsiMethod psiMethod2;
        for (int i = 0; i < psiExpressionArr.length; i++) {
            PsiExpression psiExpressionSkipParenthesizedExprDown = PsiUtil.skipParenthesizedExprDown(psiExpressionArr[i]);
            if (psiExpressionSkipParenthesizedExprDown != null && (psiTypeSubstitute = this.myInferenceSessionContainer.findNestedSubstitutor(psiExpressionSkipParenthesizedExprDown, this.myInferenceSubstitution).substitute(getParameterType(psiParameterArr, i, psiSubstitutor, z))) != null) {
                if (!isPertinentToApplicability(psiExpressionSkipParenthesizedExprDown, psiMethod)) {
                    ExpressionCompatibilityConstraint expressionCompatibilityConstraint = new ExpressionCompatibilityConstraint(psiExpressionSkipParenthesizedExprDown, psiTypeSubstitute);
                    if (((psiExpressionSkipParenthesizedExprDown instanceof PsiFunctionalExpression) && ignoreLambdaConstraintTree(psiExpressionSkipParenthesizedExprDown)) || dependsOnIgnoredConstraint(set2, expressionCompatibilityConstraint)) {
                        set2.add(expressionCompatibilityConstraint);
                    } else {
                        set.add(expressionCompatibilityConstraint);
                        set.add(new CheckedExceptionCompatibilityConstraint(psiExpressionSkipParenthesizedExprDown, psiTypeSubstitute));
                        if (psiExpressionSkipParenthesizedExprDown instanceof PsiCall) {
                            PsiCall psiCall = (PsiCall) psiExpressionSkipParenthesizedExprDown;
                            diamondsAwareResolveResult = PsiDiamondType.getDiamondsAwareResolveResult(psiCall);
                            if (diamondsAwareResolveResult instanceof MethodCandidateInfo) {
                                psiMethod2 = (PsiMethod) diamondsAwareResolveResult.getElement();
                            } else {
                                psiMethod2 = null;
                            }
                            if (psiMethod2 == null && PsiPolyExpressionUtil.isMethodCallPolyExpression(psiExpressionSkipParenthesizedExprDown, psiMethod2)) {
                                collectAdditionalConstraints(set, set2, psiCall, psiSubstitutor2);
                            }
                        } else if (!(psiExpressionSkipParenthesizedExprDown instanceof PsiLambdaExpression) && isPertinentToApplicability(psiExpressionSkipParenthesizedExprDown, psiMethod)) {
                            PsiLambdaExpression psiLambdaExpression = (PsiLambdaExpression) psiExpressionSkipParenthesizedExprDown;
                            collectLambdaReturnExpression(set, set2, psiLambdaExpression, psiLambdaExpression.getGroundTargetType(psiTypeSubstitute), !isProperType(psiSubstitutor2.substitute(psiTypeSubstitute)), psiSubstitutor2);
                        }
                    }
                } else {
                    set.add(new CheckedExceptionCompatibilityConstraint(psiExpressionSkipParenthesizedExprDown, psiTypeSubstitute));
                    if (psiExpressionSkipParenthesizedExprDown instanceof PsiCall) {
                        PsiCall psiCall2 = (PsiCall) psiExpressionSkipParenthesizedExprDown;
                        diamondsAwareResolveResult = PsiDiamondType.getDiamondsAwareResolveResult(psiCall2);
                        if (diamondsAwareResolveResult instanceof MethodCandidateInfo) {
                            psiMethod2 = (PsiMethod) diamondsAwareResolveResult.getElement();
                        } else {
                            psiMethod2 = null;
                        }
                        if (psiMethod2 == null) {
                        }
                    } else if (!(psiExpressionSkipParenthesizedExprDown instanceof PsiLambdaExpression)) {
                    }
                }
            }
        }
    }

    private boolean dependsOnIgnoredConstraint(Set<ConstraintFormula> set, ExpressionCompatibilityConstraint expressionCompatibilityConstraint) {
        Set<InferenceVariable> inputVariables;
        if (set.isEmpty() || (inputVariables = expressionCompatibilityConstraint.getInputVariables(this)) == null) {
            return false;
        }
        for (ConstraintFormula constraintFormula : set) {
            if (constraintFormula instanceof InputOutputConstraintFormula) {
                InputOutputConstraintFormula inputOutputConstraintFormula = (InputOutputConstraintFormula) constraintFormula;
                Set<InferenceVariable> outputVariables = inputOutputConstraintFormula.getOutputVariables(inputOutputConstraintFormula.getInputVariables(this), this);
                if (outputVariables != null && ContainerUtil.intersects(outputVariables, inputVariables)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean ignoreLambdaConstraintTree(PsiExpression psiExpression) {
        Iterator<? extends PsiElement> it = MethodCandidateInfo.ourOverloadGuard.currentStack().iterator();
        while (it.hasNext()) {
            if (PsiTreeUtil.getParentOfType(it.next(), PsiFunctionalExpression.class, false) == psiExpression) {
                return true;
            }
        }
        return false;
    }

    private void collectLambdaReturnExpression(Set<ConstraintFormula> set, Set<ConstraintFormula> set2, PsiLambdaExpression psiLambdaExpression, PsiType psiType, boolean z, PsiSubstitutor psiSubstitutor) {
        PsiType functionalInterfaceReturnType = LambdaUtil.getFunctionalInterfaceReturnType(psiType);
        if (functionalInterfaceReturnType != null) {
            if (PsiType.VOID.equals(functionalInterfaceReturnType) && z) {
                return;
            }
            Iterator<PsiExpression> it = LambdaUtil.getReturnExpressions(psiLambdaExpression).iterator();
            while (it.hasNext()) {
                processReturnExpression(set, set2, it.next(), functionalInterfaceReturnType, z, psiSubstitutor);
            }
        }
    }

    private void processReturnExpression(Set<ConstraintFormula> set, Set<ConstraintFormula> set2, PsiExpression psiExpression, PsiType psiType, boolean z, PsiSubstitutor psiSubstitutor) {
        if (psiType == null) {
            $$$reportNull$$$0(14);
        }
        if (psiExpression instanceof PsiCallExpression) {
            if (z) {
                PsiCallExpression psiCallExpression = (PsiCallExpression) psiExpression;
                JavaResolveResult diamondsAwareResolveResult = PsiDiamondType.getDiamondsAwareResolveResult(psiCallExpression);
                if ((diamondsAwareResolveResult instanceof MethodCandidateInfo) && PsiPolyExpressionUtil.isMethodCallPolyExpression(psiExpression, ((MethodCandidateInfo) diamondsAwareResolveResult).getElement())) {
                    collectAdditionalConstraints(set, set2, psiCallExpression, psiSubstitutor);
                    return;
                }
                return;
            }
            getInferenceSessionContainer().registerNestedSession(this, psiType, psiExpression);
            return;
        }
        if (psiExpression instanceof PsiParenthesizedExpression) {
            processReturnExpression(set, set2, ((PsiParenthesizedExpression) psiExpression).getExpression(), psiType, z, psiSubstitutor);
            return;
        }
        if (psiExpression instanceof PsiConditionalExpression) {
            PsiConditionalExpression psiConditionalExpression = (PsiConditionalExpression) psiExpression;
            processReturnExpression(set, set2, psiConditionalExpression.getThenExpression(), psiType, z, psiSubstitutor);
            processReturnExpression(set, set2, psiConditionalExpression.getElseExpression(), psiType, z, psiSubstitutor);
        } else if (psiExpression instanceof PsiSwitchExpression) {
            Iterator<PsiExpression> it = PsiUtil.getSwitchResultExpressions((PsiSwitchExpression) psiExpression).iterator();
            while (it.hasNext()) {
                processReturnExpression(set, set2, it.next(), psiType, z, psiSubstitutor);
            }
        } else {
            if (!(psiExpression instanceof PsiLambdaExpression) || ignoreLambdaConstraintTree(psiExpression)) {
                return;
            }
            collectLambdaReturnExpression(set, set2, (PsiLambdaExpression) psiExpression, psiType, this.myErased, psiSubstitutor);
        }
    }

    private void collectAdditionalConstraints(Set<ConstraintFormula> set, Set<ConstraintFormula> set2, PsiCall psiCall, PsiSubstitutor psiSubstitutor) {
        PsiExpressionList argumentList = psiCall.getArgumentList();
        if (argumentList != null) {
            JavaResolveResult diamondsAwareResolveResult = PsiDiamondType.getDiamondsAwareResolveResult(psiCall);
            PsiMethod element = diamondsAwareResolveResult instanceof MethodCandidateInfo ? ((MethodCandidateInfo) diamondsAwareResolveResult).getElement() : null;
            if (element != null) {
                PsiExpression[] expressions = argumentList.getExpressions();
                PsiParameter[] parameters = element.getParameterList().getParameters();
                if (parameters.length > 0) {
                    collectAdditionalConstraints(parameters, expressions, element, chooseSiteSubstitutor(null, diamondsAwareResolveResult, element), set, set2, chooseVarargsMode(null, diamondsAwareResolveResult), psiSubstitutor);
                }
            }
        }
    }

    public static PsiSubstitutor chooseSiteSubstitutor(MethodCandidateInfo methodCandidateInfo, JavaResolveResult javaResolveResult, PsiMethod psiMethod) {
        if (!(javaResolveResult instanceof MethodCandidateInfo) || psiMethod == null || psiMethod.isConstructor()) {
            return methodCandidateInfo != null ? methodCandidateInfo.getSiteSubstitutor() : PsiSubstitutor.EMPTY;
        }
        return ((MethodCandidateInfo) javaResolveResult).getSiteSubstitutor();
    }

    public static boolean chooseVarargsMode(MethodCandidateInfo methodCandidateInfo, JavaResolveResult javaResolveResult) {
        return ((javaResolveResult instanceof MethodCandidateInfo) && ((MethodCandidateInfo) javaResolveResult).isVarargs()) || (methodCandidateInfo != null && methodCandidateInfo.isVarargs());
    }

    PsiSubstitutor getInstantiations(Collection<InferenceVariable> collection) {
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        for (InferenceVariable inferenceVariable : collection) {
            PsiType equalsBound = getEqualsBound(inferenceVariable, psiSubstitutorPut);
            if (equalsBound != null && !PsiType.NULL.equals(equalsBound)) {
                psiSubstitutorPut = psiSubstitutorPut.put(inferenceVariable.getParameter(), equalsBound);
            }
        }
        return psiSubstitutorPut;
    }

    protected PsiSubstitutor prepareSubstitution() {
        Iterator<List<InferenceVariable>> itResolveOrderIterator = InferenceVariablesOrder.resolveOrderIterator(this.myInferenceVariables, this);
        boolean z = false;
        while (itResolveOrderIterator.hasNext()) {
            for (InferenceVariable inferenceVariable : itResolveOrderIterator.next()) {
                PsiTypeParameter parameter = inferenceVariable.getParameter();
                if (inferenceVariable.getInstantiation() == PsiType.NULL) {
                    if (!z) {
                        z = checkBoundsConsistency(this.mySiteSubstitutor, inferenceVariable) == PsiType.NULL;
                    }
                    this.mySiteSubstitutor = this.mySiteSubstitutor.put(parameter, JavaPsiFacade.getElementFactory(this.myManager.getProject()).createType(parameter));
                }
            }
        }
        return this.mySiteSubstitutor;
    }

    InitialInferenceState createInitialState(InferenceSessionContainer inferenceSessionContainer, Collection<InitialInferenceState.VariableInfo> collection, PsiSubstitutor psiSubstitutor) {
        return new InitialInferenceState(collection, psiSubstitutor, this.myContext, this.myInferenceSubstitution, this.mySiteSubstitutor, this.myIncorporationPhase.getCaptures(), this.myErased, inferenceSessionContainer);
    }

    private void initBounds(PsiTypeParameter... psiTypeParameterArr) {
        initBounds(this.myContext, psiTypeParameterArr);
    }

    public InferenceVariable[] initBounds(PsiElement psiElement, PsiTypeParameter... psiTypeParameterArr) {
        return initBounds(psiElement, this.mySiteSubstitutor, psiTypeParameterArr);
    }

    public InferenceVariable[] initBounds(PsiElement psiElement, PsiSubstitutor psiSubstitutor, PsiTypeParameter... psiTypeParameterArr) {
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(15);
        }
        ArrayList<InferenceVariable> arrayList = new ArrayList(psiTypeParameterArr.length);
        for (PsiTypeParameter psiTypeParameter : psiTypeParameterArr) {
            String name2 = psiTypeParameter.getName();
            if (this.myContext != null) {
                name2 = name2 + MathUtil.nonNegativeAbs(this.myContext.hashCode());
            }
            InferenceVariable inferenceVariable = new InferenceVariable(psiElement, psiTypeParameter, name2);
            arrayList.add(inferenceVariable);
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(this.myManager.getProject());
            this.myInferenceSubstitution = this.myInferenceSubstitution.put(psiTypeParameter, elementFactory.createType(inferenceVariable));
            this.myRestoreNameSubstitution = this.myRestoreNameSubstitution.put(inferenceVariable, elementFactory.createType(psiTypeParameter));
            this.myInferenceVariables.add(inferenceVariable);
        }
        for (InferenceVariable inferenceVariable2 : arrayList) {
            PsiTypeParameter parameter = inferenceVariable2.getParameter();
            boolean z = false;
            for (PsiClassType psiClassType : parameter.getExtendsListTypes()) {
                PsiType psiTypeSubstituteWithInferenceVariables = substituteWithInferenceVariables(psiSubstitutor.substitute(psiClassType));
                if (isProperType(psiTypeSubstituteWithInferenceVariables)) {
                    z = true;
                }
                inferenceVariable2.addBound(psiTypeSubstituteWithInferenceVariables, InferenceBound.UPPER, null);
            }
            if (!z) {
                inferenceVariable2.addBound(PsiType.getJavaLangObject(parameter.getManager(), parameter.getResolveScope()), InferenceBound.UPPER, null);
            }
        }
        return (InferenceVariable[]) arrayList.toArray(new InferenceVariable[0]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void registerReturnTypeConstraints(PsiType psiType, PsiType psiType2, PsiElement psiElement) {
        if (psiType2 == null) {
            $$$reportNull$$$0(16);
        }
        PsiType psiTypeSubstituteWithInferenceVariables = substituteWithInferenceVariables(psiType);
        if (this.myErased) {
            addConstraint(new TypeCompatibilityConstraint(psiType2, TypeConversionUtil.erasure(resolveSubset(this.myInferenceVariables, this.mySiteSubstitutor).substitute(psiTypeSubstituteWithInferenceVariables))));
            return;
        }
        if (FunctionalInterfaceParameterizationUtil.isWildcardParameterized(psiTypeSubstituteWithInferenceVariables)) {
            PsiClass element = PsiUtil.resolveGenericsClassInType(psiTypeSubstituteWithInferenceVariables).getElement();
            if (element != 0) {
                LOG.assertTrue(psiTypeSubstituteWithInferenceVariables instanceof PsiClassType);
                PsiClassType psiClassType = (PsiClassType) PsiUtil.captureToplevelWildcards(psiTypeSubstituteWithInferenceVariables, psiElement);
                PsiTypeParameter[] typeParameters = element.getTypeParameters();
                PsiType[] parameters = psiClassType.getParameters();
                InferenceVariable[] inferenceVariableArrInitFreshVariablesForCapturedBounds = initFreshVariablesForCapturedBounds(typeParameters, parameters);
                PsiType[] psiTypeArr = new PsiType[parameters.length];
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(this.myManager.getProject());
                int i = 0;
                for (int i2 = 0; i2 < parameters.length; i2++) {
                    psiTypeArr[i2] = parameters[i2];
                    if (parameters[i2] instanceof PsiCapturedWildcardType) {
                        psiTypeArr[i2] = elementFactory.createType(inferenceVariableArrInitFreshVariablesForCapturedBounds[i]);
                        i++;
                    }
                }
                PsiClassType psiClassTypeCreateType = elementFactory.createType(element, psiTypeArr);
                this.myIncorporationPhase.addCapture(inferenceVariableArrInitFreshVariablesForCapturedBounds, (PsiClassType) psiTypeSubstituteWithInferenceVariables);
                addConstraint(new TypeCompatibilityConstraint(psiType2, psiClassTypeCreateType));
                return;
            }
            return;
        }
        InferenceVariable inferenceVariableShouldResolveAndInstantiate = shouldResolveAndInstantiate(psiTypeSubstituteWithInferenceVariables, psiType2);
        if (inferenceVariableShouldResolveAndInstantiate != null) {
            PsiType psiTypeSubstitute = resolveSubset(Collections.singletonList(inferenceVariableShouldResolveAndInstantiate), this.mySiteSubstitutor).substitute(inferenceVariableShouldResolveAndInstantiate);
            if (psiTypeSubstitute != null) {
                addConstraint(new TypeCompatibilityConstraint(psiType2, PsiUtil.captureToplevelWildcards(psiTypeSubstitute, psiElement)));
                return;
            }
            return;
        }
        addConstraint(new TypeCompatibilityConstraint(psiType2, psiTypeSubstituteWithInferenceVariables));
    }

    private InferenceVariable[] initFreshVariablesForCapturedBounds(PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr) {
        if (Registry.is("javac.fresh.variables.for.captured.wildcards.only")) {
            ArrayList arrayList = new ArrayList();
            PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
            for (int i = 0; i < psiTypeArr.length; i++) {
                PsiType psiType = psiTypeArr[i];
                if (psiType instanceof PsiCapturedWildcardType) {
                    arrayList.add(psiTypeParameterArr[i]);
                } else {
                    psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameterArr[i], psiType);
                }
            }
            InferenceVariable[] inferenceVariableArrInitBounds = initBounds(null, psiSubstitutorPut, (PsiTypeParameter[]) arrayList.toArray(PsiTypeParameter.EMPTY_ARRAY));
            int i2 = 0;
            for (PsiType psiType2 : psiTypeArr) {
                if (psiType2 instanceof PsiCapturedWildcardType) {
                    int i3 = i2 + 1;
                    InferenceVariable inferenceVariable = inferenceVariableArrInitBounds[i2];
                    PsiCapturedWildcardType psiCapturedWildcardType = (PsiCapturedWildcardType) psiType2;
                    if (isProperType(psiCapturedWildcardType.getWildcard())) {
                        inferenceVariable.putUserData(ORIGINAL_CAPTURE, psiCapturedWildcardType);
                    }
                    i2 = i3;
                }
            }
            return inferenceVariableArrInitBounds;
        }
        return initBounds(null, psiTypeParameterArr);
    }

    private InferenceVariable shouldResolveAndInstantiate(PsiType psiType, PsiType psiType2) {
        InferenceVariable inferenceVariable = getInferenceVariable(psiType);
        if (inferenceVariable == null) {
            return null;
        }
        if ((psiType2 instanceof PsiPrimitiveType) && hasPrimitiveWrapperBound(inferenceVariable)) {
            return inferenceVariable;
        }
        if (!(psiType2 instanceof PsiClassType)) {
            return null;
        }
        PsiClassType psiClassType = (PsiClassType) psiType2;
        if (hasUncheckedBounds(inferenceVariable, psiClassType, this) || hasWildcardParameterization(inferenceVariable, psiClassType)) {
            return inferenceVariable;
        }
        return null;
    }

    private static boolean hasPrimitiveWrapperBound(InferenceVariable inferenceVariable) {
        InferenceBound[] inferenceBoundArr = {InferenceBound.UPPER, InferenceBound.LOWER, InferenceBound.EQ};
        for (int i = 0; i < 3; i++) {
            Iterator<PsiType> it = inferenceVariable.getBounds(inferenceBoundArr[i]).iterator();
            while (it.hasNext()) {
                if (PsiPrimitiveType.getUnboxedType(it.next()) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasUncheckedBounds(InferenceVariable inferenceVariable, PsiClassType psiClassType, InferenceSession inferenceSession) {
        if (!psiClassType.isRaw()) {
            InferenceBound[] inferenceBoundArr = {InferenceBound.EQ, InferenceBound.LOWER};
            for (int i = 0; i < 2; i++) {
                Iterator<PsiType> it = inferenceVariable.getBounds(inferenceBoundArr[i]).iterator();
                while (it.hasNext()) {
                    if (TypeCompatibilityConstraint.isUncheckedConversion(psiClassType, it.next(), inferenceSession)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean hasWildcardParameterization(InferenceVariable inferenceVariable, PsiClassType psiClassType) {
        if (FunctionalInterfaceParameterizationUtil.isWildcardParameterized(psiClassType)) {
            return false;
        }
        List<PsiType> bounds = inferenceVariable.getBounds(InferenceBound.LOWER);
        if (findParameterizationOfTheSameGenericClass(bounds, new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return InferenceSession.lambda$hasWildcardParameterization$2((Pair) obj);
            }
        }) != null) {
            return true;
        }
        List<PsiType> bounds2 = inferenceVariable.getBounds(InferenceBound.EQ);
        ArrayList arrayList = new ArrayList(bounds);
        arrayList.addAll(bounds2);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (FunctionalInterfaceParameterizationUtil.isWildcardParameterized((PsiType) it.next())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ boolean lambda$hasWildcardParameterization$2(Pair pair) {
        return pair.first == 0 || pair.second == 0 || !TypesDistinctProver.provablyDistinct((PsiType) pair.first, (PsiType) pair.second);
    }

    public static PsiType getTargetType(PsiElement psiElement) {
        PsiType targetTypeFromParent = getTargetTypeFromParent(psiElement, new Ref(), true);
        return targetTypeFromParent instanceof PsiClassType ? ((PsiClassType) targetTypeFromParent).setLanguageLevel(PsiUtil.getLanguageLevel(psiElement)) : targetTypeFromParent;
    }

    public static PsiType getTargetTypeByParent(PsiElement psiElement) {
        PsiType targetTypeFromParent = getTargetTypeFromParent(psiElement, new Ref(), false);
        return targetTypeFromParent instanceof PsiClassType ? ((PsiClassType) targetTypeFromParent).setLanguageLevel(PsiUtil.getLanguageLevel(psiElement)) : targetTypeFromParent;
    }

    private static PsiType getTargetTypeFromParent(PsiElement psiElement, Ref<String> ref, boolean z) {
        PsiCall psiCall;
        PsiExpressionList argumentList;
        PsiType expectedTypeByParent = PsiTypesUtil.getExpectedTypeByParent(psiElement);
        if (expectedTypeByParent != null) {
            return expectedTypeByParent;
        }
        PsiElement psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiElement.getLightParent());
        if (psiElementSkipParenthesizedExprUp instanceof PsiExpressionList) {
            PsiElement lightParent = psiElementSkipParenthesizedExprUp.getLightParent();
            if (lightParent instanceof PsiAnonymousClass) {
                lightParent = lightParent.getLightParent();
            }
            if ((lightParent instanceof PsiCall) && (argumentList = (psiCall = (PsiCall) lightParent).getArgumentList()) != null) {
                if (MethodCandidateInfo.isOverloadCheck(argumentList)) {
                    return ThreadLocalTypes.getElementType(psiElement);
                }
                JavaResolveResult diamondsAwareResolveResult = PsiDiamondType.getDiamondsAwareResolveResult(psiCall);
                PsiElement element = diamondsAwareResolveResult.getElement();
                if (element == null) {
                    ref.set("Overload resolution failed");
                    return null;
                }
                if ((element instanceof PsiMethod) && (z || !((PsiMethod) element).hasTypeParameters())) {
                    return PsiTypesUtil.getTypeByMethod(psiElement, argumentList, diamondsAwareResolveResult.getElement(), (diamondsAwareResolveResult instanceof MethodCandidateInfo) && ((MethodCandidateInfo) diamondsAwareResolveResult).isVarargs(), diamondsAwareResolveResult.getSubstitutor(), z);
                }
            }
        } else {
            if (psiElementSkipParenthesizedExprUp instanceof PsiConditionalExpression) {
                return getTargetTypeFromParent(psiElementSkipParenthesizedExprUp, ref, z);
            }
            if (psiElementSkipParenthesizedExprUp instanceof PsiLambdaExpression) {
                return getTargetTypeFromParentLambda((PsiLambdaExpression) psiElementSkipParenthesizedExprUp, ref, z);
            }
            if (psiElementSkipParenthesizedExprUp instanceof PsiReturnStatement) {
                return getTargetTypeFromParentLambda((PsiLambdaExpression) PsiTreeUtil.getParentOfType(psiElementSkipParenthesizedExprUp, PsiLambdaExpression.class, true, (Class<? extends PsiElement>[]) new Class[]{PsiMethod.class}), ref, z);
            }
        }
        PsiSwitchExpression psiSwitchExpression = (PsiSwitchExpression) PsiTreeUtil.getParentOfType(psiElementSkipParenthesizedExprUp, PsiSwitchExpression.class);
        if (psiSwitchExpression == null || !PsiUtil.getSwitchResultExpressions(psiSwitchExpression).contains(psiElement)) {
            return null;
        }
        return getTargetTypeFromParent(psiSwitchExpression, ref, z);
    }

    private static PsiType getTargetTypeFromParentLambda(PsiLambdaExpression psiLambdaExpression, Ref<String> ref, boolean z) {
        if (psiLambdaExpression == null) {
            return null;
        }
        PsiType targetTypeFromParent = getTargetTypeFromParent(psiLambdaExpression, ref, z);
        if (targetTypeFromParent != null) {
            return LambdaUtil.getFunctionalInterfaceReturnType(psiLambdaExpression.getGroundTargetType(targetTypeFromParent));
        }
        PsiType elementType = ThreadLocalTypes.getElementType(psiLambdaExpression);
        if (elementType != null) {
            return LambdaUtil.getFunctionalInterfaceReturnType(psiLambdaExpression.getGroundTargetType(elementType));
        }
        if (z || !(PsiUtil.skipParenthesizedExprUp(psiLambdaExpression.getLightParent()) instanceof PsiExpressionList)) {
            return LambdaUtil.getFunctionalInterfaceReturnType(psiLambdaExpression.getFunctionalInterfaceType());
        }
        return null;
    }

    public InferenceVariable getInferenceVariable(PsiType psiType) {
        PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(psiType);
        if (psiClassResolveClassInClassTypeOnly instanceof InferenceVariable) {
            return getInferenceVariable((PsiTypeParameter) psiClassResolveClassInClassTypeOnly);
        }
        return null;
    }

    public boolean isProperType(PsiType psiType) {
        return collectDependencies(psiType, null);
    }

    public boolean collectDependencies(PsiType psiType, Set<? super InferenceVariable> set) {
        return collectDependencies(psiType, set, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.getInferenceVariable((PsiClassType) obj);
            }
        });
    }

    public static boolean collectDependencies(PsiType psiType, final Set<? super InferenceVariable> set, final Function<? super PsiClassType, ? extends InferenceVariable> function) {
        if (psiType == null) {
            return true;
        }
        Boolean bool = (Boolean) psiType.accept(new PsiTypeVisitor<Boolean>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "capturedWildcardType";
                } else if (i == 2) {
                    objArr[0] = "arrayType";
                } else if (i == 3) {
                    objArr[0] = "wildcardType";
                } else if (i != 4) {
                    objArr[0] = ModuleXmlParser.TYPE;
                } else {
                    objArr[0] = "classType";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/InferenceSession$1";
                if (i == 1) {
                    objArr[2] = "visitCapturedWildcardType";
                } else if (i == 2) {
                    objArr[2] = "visitArrayType";
                } else if (i == 3) {
                    objArr[2] = "visitWildcardType";
                } else if (i != 4) {
                    objArr[2] = "visitType";
                } else {
                    objArr[2] = "visitClassType";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitType(PsiType psiType2) {
                if (psiType2 == null) {
                    $$$reportNull$$$0(0);
                }
                return true;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitCapturedWildcardType(PsiCapturedWildcardType psiCapturedWildcardType) {
                if (psiCapturedWildcardType == null) {
                    $$$reportNull$$$0(1);
                }
                return true;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitArrayType(PsiArrayType psiArrayType) {
                if (psiArrayType == null) {
                    $$$reportNull$$$0(2);
                }
                return (Boolean) psiArrayType.getComponentType().accept(this);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitWildcardType(PsiWildcardType psiWildcardType) {
                if (psiWildcardType == null) {
                    $$$reportNull$$$0(3);
                }
                PsiType bound = psiWildcardType.getBound();
                if (bound == null) {
                    return true;
                }
                return (Boolean) bound.accept(this);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitClassType(PsiClassType psiClassType) {
                if (psiClassType == null) {
                    $$$reportNull$$$0(4);
                }
                InferenceVariable inferenceVariable = (InferenceVariable) function.fun(psiClassType);
                if (inferenceVariable != null) {
                    Set set2 = set;
                    if (set2 != null) {
                        set2.add(inferenceVariable);
                        return true;
                    }
                    return false;
                }
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
                PsiClass element = classResolveResultResolveGenerics.getElement();
                if (element != null) {
                    PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
                    Iterator<PsiTypeParameter> it = PsiUtil.typeParametersIterable(element).iterator();
                    while (it.hasNext()) {
                        PsiType psiTypeSubstitute = substitutor.substitute(it.next());
                        if (psiTypeSubstitute != null && !((Boolean) psiTypeSubstitute.accept(this)).booleanValue()) {
                            return false;
                        }
                    }
                }
                return true;
            }
        });
        if (set != null) {
            return !set.isEmpty();
        }
        return bool.booleanValue();
    }

    public boolean repeatInferencePhases() {
        while (reduceConstraints() && this.myIncorporationPhase.incorporate()) {
            if (this.myIncorporationPhase.isFullyIncorporated() && this.myConstraintIdx >= this.myConstraints.size()) {
                return true;
            }
        }
        return false;
    }

    private boolean reduceConstraints() {
        ArrayList arrayList = new ArrayList();
        for (int i = this.myConstraintIdx; i < this.myConstraints.size(); i++) {
            if (!this.myConstraints.get(i).reduce(this, arrayList)) {
                return false;
            }
        }
        this.myConstraintIdx = this.myConstraints.size();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            addConstraint((ConstraintFormula) it.next());
        }
        return true;
    }

    private boolean isThrowable(List<PsiType> list) {
        boolean z = false;
        for (PsiType psiType : list) {
            if (!psiType.equalsToText(CommonClassNames.JAVA_LANG_OBJECT) && isProperType(psiType)) {
                if (!psiType.equalsToText(CommonClassNames.JAVA_LANG_EXCEPTION) && !psiType.equalsToText(CommonClassNames.JAVA_LANG_THROWABLE)) {
                    return false;
                }
                z = true;
            }
        }
        return z;
    }

    private PsiType substituteNonProperBound(PsiType psiType, PsiSubstitutor psiSubstitutor) {
        LinkedHashSet<InferenceVariable> linkedHashSet = new LinkedHashSet();
        if (!collectDependencies(psiType, linkedHashSet)) {
            return psiType;
        }
        for (InferenceVariable inferenceVariable : linkedHashSet) {
            PsiType instantiation = inferenceVariable.getInstantiation();
            if (instantiation != PsiType.NULL) {
                psiSubstitutor = psiSubstitutor.put(inferenceVariable, instantiation);
            }
        }
        return psiSubstitutor.substitute(psiType);
    }

    private boolean hasBoundProblems(List<InferenceVariable> list, PsiSubstitutor psiSubstitutor) {
        for (InferenceVariable inferenceVariable : list) {
            if (inferenceVariable.getInstantiation() == PsiType.NULL && inferenceVariable.getUserData(ORIGINAL_CAPTURE) == null) {
                PsiType psiTypeSubstitute = psiSubstitutor.substitute(inferenceVariable);
                if (psiTypeSubstitute instanceof PsiClassType) {
                    PsiClass psiClassResolve = ((PsiClassType) psiTypeSubstitute).resolve();
                    if (!(psiClassResolve instanceof PsiTypeParameter) || !TypeConversionUtil.isFreshVariable((PsiTypeParameter) psiClassResolve)) {
                    }
                }
                if (GenericsUtil.findTypeParameterBoundError(inferenceVariable, (PsiType[]) inferenceVariable.getBounds(InferenceBound.UPPER).toArray(PsiType.EMPTY_ARRAY), psiSubstitutor, this.myContext, true) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void resolveBounds(Collection<InferenceVariable> collection, PsiSubstitutor psiSubstitutor) {
        List<InferenceVariable> list;
        PsiSubstitutor psiSubstitutor2;
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(17);
        }
        UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
        ArrayList arrayList = new ArrayList(collection);
        Map<InferenceVariable, Set<InferenceVariable>> dependencies = InferenceVariablesOrder.getDependencies(arrayList, this);
        while (!arrayList.isEmpty()) {
            List<InferenceVariable> listResolveOrder = InferenceVariablesOrder.resolveOrder(arrayList, dependencies);
            List<InferenceVariable> arrayList2 = new ArrayList<>();
            for (InferenceVariable inferenceVariable : listResolveOrder) {
                if (getEqualsBound(inferenceVariable, psiSubstitutor) == PsiType.NULL) {
                    arrayList2.add(inferenceVariable);
                }
            }
            if (!arrayList2.isEmpty() && listResolveOrder.size() > arrayList2.size()) {
                listResolveOrder.removeAll(arrayList2);
                listResolveOrder.addAll(arrayList2);
            }
            if (!this.myIncorporationPhase.hasCaptureConstraints(arrayList2)) {
                PsiSubstitutor psiSubstitutorResolveSubset = resolveSubset(listResolveOrder, psiSubstitutor);
                if (this.myErrorMessages == null && hasBoundProblems(listResolveOrder, psiSubstitutorResolveSubset)) {
                    psiSubstitutor2 = null;
                    list = listResolveOrder;
                } else {
                    list = arrayList2;
                    psiSubstitutor2 = psiSubstitutorResolveSubset;
                }
                if (psiSubstitutor2 != null) {
                    arrayList.removeAll(listResolveOrder);
                    psiSubstitutor = psiSubstitutor2;
                } else {
                    arrayList2 = list;
                }
            }
            if (!initFreshVariables(psiSubstitutor, arrayList2, uniqueNameGenerator)) {
                return;
            }
            this.myIncorporationPhase.forgetCaptures(listResolveOrder);
            if (!repeatInferencePhases()) {
                return;
            }
        }
        Map<PsiTypeParameter, PsiType> substitutionMap = psiSubstitutor.getSubstitutionMap();
        for (PsiTypeParameter parameter : substitutionMap.keySet()) {
            PsiType psiType = substitutionMap.get(parameter);
            if (parameter instanceof InferenceVariable) {
                InferenceVariable inferenceVariable2 = (InferenceVariable) parameter;
                inferenceVariable2.setInstantiation(psiType);
                if (inferenceVariable2.getCallContext() == this.myContext) {
                    parameter = inferenceVariable2.getParameter();
                }
            }
            this.mySiteSubstitutor = this.mySiteSubstitutor.put(parameter, psiType);
        }
    }

    private boolean initFreshVariables(PsiSubstitutor psiSubstitutor, List<InferenceVariable> list, UniqueNameGenerator uniqueNameGenerator) {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(getManager().getProject());
        PsiSubstitutor psiSubstitutor2 = PsiSubstitutor.EMPTY;
        int size = list.size();
        PsiTypeParameter[] psiTypeParameterArr = new PsiTypeParameter[size];
        PsiSubstitutor psiSubstitutorPut = psiSubstitutor2;
        for (int i = 0; i < list.size(); i++) {
            InferenceVariable inferenceVariable = list.get(i);
            PsiTypeParameter parameter = inferenceVariable.getParameter();
            PsiTypeParameter psiTypeParameterCreateTypeParameterFromText = elementFactory.createTypeParameterFromText(uniqueNameGenerator.generateUniqueName(parameter.getName()), parameter);
            psiTypeParameterArr[i] = psiTypeParameterCreateTypeParameterFromText;
            psiSubstitutorPut = psiSubstitutorPut.put(inferenceVariable, elementFactory.createType(psiTypeParameterCreateTypeParameterFromText));
        }
        for (int i2 = 0; i2 < size; i2++) {
            PsiTypeParameter psiTypeParameter = psiTypeParameterArr[i2];
            InferenceVariable inferenceVariable2 = list.get(i2);
            PsiType psiTypeComposeBound = composeBound(inferenceVariable2, InferenceBound.UPPER, UPPER_BOUND_FUNCTION, psiSubstitutorPut.putAll(psiSubstitutor), true);
            PsiType lowerBound = getLowerBound(inferenceVariable2, psiSubstitutor);
            if (lowerBound != PsiType.NULL) {
                for (PsiClassType psiClassType : psiTypeParameter.getExtendsListTypes()) {
                    if (!TypeConversionUtil.isAssignable(psiClassType, lowerBound)) {
                        return false;
                    }
                }
            } else {
                lowerBound = this.myPolicy.inferLowerBoundForFreshVariables() ? psiTypeComposeBound : null;
            }
            TypeConversionUtil.setInferredBoundsForSynthetic(psiTypeParameter, lowerBound, psiTypeComposeBound);
            TypeConversionUtil.markAsFreshVariable(psiTypeParameter, this.myContext);
            if (!inferenceVariable2.addBound(elementFactory.createType(psiTypeParameter), InferenceBound.EQ, this.myIncorporationPhase)) {
                return false;
            }
        }
        return true;
    }

    private PsiSubstitutor resolveSubsetOrdered(Set<InferenceVariable> set, PsiSubstitutor psiSubstitutor) {
        Iterator<List<InferenceVariable>> itResolveOrderIterator = InferenceVariablesOrder.resolveOrderIterator(set, this);
        while (itResolveOrderIterator.hasNext()) {
            psiSubstitutor = psiSubstitutor.putAll(resolveSubset(itResolveOrderIterator.next(), psiSubstitutor));
        }
        return psiSubstitutor;
    }

    protected final PsiSubstitutor resolveSubset(Collection<InferenceVariable> collection, PsiSubstitutor psiSubstitutor) {
        if (this.myErased) {
            Iterator<InferenceVariable> it = collection.iterator();
            while (it.hasNext()) {
                psiSubstitutor = psiSubstitutor.put((InferenceVariable) it.next(), null);
            }
        }
        for (InferenceVariable inferenceVariable : collection) {
            PsiType instantiation = inferenceVariable.getInstantiation();
            if (instantiation == PsiType.NULL) {
                instantiation = checkBoundsConsistency(psiSubstitutor, inferenceVariable);
            }
            if (instantiation != PsiType.NULL) {
                psiSubstitutor = psiSubstitutor.put(inferenceVariable, instantiation);
            }
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(18);
        }
        return psiSubstitutor;
    }

    private PsiType checkBoundsConsistency(final PsiSubstitutor psiSubstitutor, InferenceVariable inferenceVariable) {
        PsiCapturedWildcardType psiCapturedWildcardType;
        PsiType equalsBound = getEqualsBound(inferenceVariable, psiSubstitutor);
        if (equalsBound != PsiType.NULL && (equalsBound instanceof PsiPrimitiveType)) {
            return PsiType.NULL;
        }
        PsiType lowerBound = getLowerBound(inferenceVariable, psiSubstitutor);
        if (equalsBound == PsiType.NULL) {
            lowerBound = this.myPolicy.adjustInferredType(this.myManager, lowerBound, ConstraintType.SUBTYPE);
        }
        PsiType upperBound = getUpperBound(inferenceVariable, psiSubstitutor);
        if (equalsBound == PsiType.NULL || (!this.myErased && equalsBound == null)) {
            equalsBound = lowerBound;
        } else {
            PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(equalsBound);
            if (psiClassResolveClassInClassTypeOnly instanceof PsiTypeParameter) {
                PsiTypeParameter psiTypeParameter = (PsiTypeParameter) psiClassResolveClassInClassTypeOnly;
                if (TypeConversionUtil.isFreshVariable(psiTypeParameter) && (psiCapturedWildcardType = (PsiCapturedWildcardType) inferenceVariable.getUserData(ORIGINAL_CAPTURE)) != null && psiCapturedWildcardType.getUpperBound().equals(TypeConversionUtil.getInferredUpperBoundForSynthetic(psiTypeParameter))) {
                    equalsBound = psiCapturedWildcardType;
                }
            }
            if (isLowerBoundNotAssignable(inferenceVariable, equalsBound, true)) {
                registerIncompatibleErrorMessage(incompatibleBoundsMessage(inferenceVariable, psiSubstitutor, InferenceBound.EQ, "equality constraints", InferenceBound.LOWER, "lower bounds"));
                return PsiType.NULL;
            }
            if (isLowerBoundNotAssignable(inferenceVariable, equalsBound, false)) {
                setErased();
            }
        }
        if (equalsBound == PsiType.NULL) {
            if (inferenceVariable.isThrownBound() && this.myPolicy.inferRuntimeExceptionForThrownBoundWithNoConstraints() && isThrowable(inferenceVariable.getBounds(InferenceBound.UPPER))) {
                PsiManager psiManager = this.myManager;
                equalsBound = PsiType.getJavaLangRuntimeException(psiManager, GlobalSearchScope.allScope(psiManager.getProject()));
            } else {
                if (inferenceVariable.getBounds(InferenceBound.UPPER).size() == 1) {
                    upperBound = this.myPolicy.getInferredTypeWithNoConstraint(this.myManager, upperBound).first;
                }
                equalsBound = upperBound;
            }
            if (this.myErrorMessages == null && (equalsBound instanceof PsiIntersectionType)) {
                PsiIntersectionType psiIntersectionType = (PsiIntersectionType) equalsBound;
                String conflictingConjunctsMessage = psiIntersectionType.getConflictingConjunctsMessage();
                if (conflictingConjunctsMessage == null) {
                    if (findParameterizationOfTheSameGenericClass(inferenceVariable.getBounds(InferenceBound.UPPER), new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda10
                        @Override // org.jetbrains.kotlin.com.intellij.util.Processor
                        public final boolean process(Object obj) {
                            return this.f$0.m6662xa3cd3037(psiSubstitutor, (Pair) obj);
                        }
                    }) != null) {
                        conflictingConjunctsMessage = equalsBound.getPresentableText(false);
                    } else {
                        conflictingConjunctsMessage = getConjunctsConflict(psiIntersectionType);
                    }
                }
                if (conflictingConjunctsMessage != null) {
                    registerIncompatibleErrorMessage(JavaPsiBundle.message("error.type.parameter.has.incompatible.upper.bounds", inferenceVariable.getParameter().getName(), conflictingConjunctsMessage));
                    return PsiType.NULL;
                }
            }
        } else {
            for (PsiType psiType : inferenceVariable.getBounds(InferenceBound.UPPER)) {
                if (this.myErrorMessages == null && isProperType(psiType)) {
                    if (equalsBound != lowerBound && !TypeConversionUtil.isAssignable(psiType, equalsBound)) {
                        registerIncompatibleErrorMessage(incompatibleBoundsMessage(inferenceVariable, psiSubstitutor, InferenceBound.EQ, "equality constraints", InferenceBound.UPPER, "upper bounds"));
                        return PsiType.NULL;
                    }
                    if (equalsBound == lowerBound) {
                        for (PsiType psiType2 : inferenceVariable.getBounds(InferenceBound.LOWER)) {
                            if (isProperType(psiType2) && !TypeConversionUtil.isAssignable(psiType, psiType2)) {
                                registerIncompatibleErrorMessage(incompatibleBoundsMessage(inferenceVariable, psiSubstitutor, InferenceBound.LOWER, "lower bounds", InferenceBound.UPPER, "upper bounds"));
                                return PsiType.NULL;
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        if (equalsBound == PsiType.NULL) {
            registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.upper.bounds", StringUtil.join((Collection) inferenceVariable.getBounds(InferenceBound.UPPER), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda11
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return this.f$0.m6663x69f7b8f8(psiSubstitutor, (PsiType) obj);
                }
            }, ", ")));
        }
        return equalsBound;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$checkBoundsConsistency$3$org-jetbrains-kotlin-com-intellij-psi-impl-source-resolve-graphInference-InferenceSession, reason: not valid java name */
    /* synthetic */ boolean m6662xa3cd3037(PsiSubstitutor psiSubstitutor, Pair pair) {
        return pair.first == 0 || pair.second == 0 || Comparing.equal(substituteNonProperBound((PsiType) pair.first, psiSubstitutor), substituteNonProperBound((PsiType) pair.second, psiSubstitutor));
    }

    /* JADX INFO: renamed from: lambda$checkBoundsConsistency$4$org-jetbrains-kotlin-com-intellij-psi-impl-source-resolve-graphInference-InferenceSession, reason: not valid java name */
    /* synthetic */ String m6663x69f7b8f8(PsiSubstitutor psiSubstitutor, PsiType psiType) {
        PsiType psiTypeSubstituteNonProperBound = substituteNonProperBound(psiType, psiSubstitutor);
        if (psiTypeSubstituteNonProperBound != null) {
            psiType = psiTypeSubstituteNonProperBound;
        }
        return getPresentableText(psiType);
    }

    private boolean isLowerBoundNotAssignable(InferenceVariable inferenceVariable, final PsiType psiType, final boolean z) {
        return inferenceVariable.getBounds(InferenceBound.LOWER).stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m6666x30476319(psiType, z, (PsiType) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$isLowerBoundNotAssignable$5$org-jetbrains-kotlin-com-intellij-psi-impl-source-resolve-graphInference-InferenceSession, reason: not valid java name */
    /* synthetic */ boolean m6666x30476319(PsiType psiType, boolean z, PsiType psiType2) {
        return isProperType(psiType2) && !TypeConversionUtil.isAssignable(psiType, psiType2, z);
    }

    private static String getConjunctsConflict(PsiIntersectionType psiIntersectionType) {
        PsiType[] conjuncts = psiIntersectionType.getConjuncts();
        for (int i = 0; i < conjuncts.length; i++) {
            PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(conjuncts[i]);
            for (int i2 = 0; i2 < conjuncts.length; i2++) {
                if (i != i2) {
                    PsiClass psiClassResolveClassInClassTypeOnly2 = PsiUtil.resolveClassInClassTypeOnly(conjuncts[i2]);
                    if (psiClassResolveClassInClassTypeOnly == null || psiClassResolveClassInClassTypeOnly2 == null) {
                        PsiType psiType = conjuncts[i];
                        if (((psiType instanceof PsiArrayType) && TypesDistinctProver.proveArrayTypeDistinct((PsiArrayType) psiType, conjuncts[i2])) || ((conjuncts[i] instanceof PsiCapturedWildcardType) && psiClassResolveClassInClassTypeOnly2 != null && !psiClassResolveClassInClassTypeOnly2.isInterface() && !(psiClassResolveClassInClassTypeOnly2 instanceof PsiTypeParameter))) {
                            return JavaPsiBundle.message("conflicting.conjuncts", conjuncts[i].getPresentableText(), conjuncts[i2].getPresentableText());
                        }
                    }
                }
            }
        }
        return null;
    }

    public String getPresentableText(PsiType psiType) {
        PsiType psiTypeSubstitute = this.myRestoreNameSubstitution.substitute(psiType);
        if (psiTypeSubstitute != null) {
            return psiTypeSubstitute.getPresentableText();
        }
        return null;
    }

    public void registerIncompatibleErrorMessage(Collection<InferenceVariable> collection, String str) {
        ArrayList arrayList = new ArrayList(collection);
        arrayList.sort(new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda8
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Comparing.compare(((InferenceVariable) obj).getName(), ((InferenceVariable) obj2).getName());
            }
        });
        String strJoin = StringUtil.join((Collection) arrayList, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda9
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((InferenceVariable) obj).getParameter().getName();
            }
        }, ", ");
        if (strJoin.isEmpty()) {
            registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.no.type.variable", str));
        } else {
            registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.no.type.variable.0", strJoin, str));
        }
    }

    public void registerIncompatibleErrorMessage(String str) {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        if (this.myErrorMessages == null) {
            this.myErrorMessages = new ArrayList();
        }
        if (this.myErrorMessages.contains(str)) {
            return;
        }
        this.myErrorMessages.add(str);
    }

    private String incompatibleBoundsMessage(InferenceVariable inferenceVariable, final PsiSubstitutor psiSubstitutor, InferenceBound inferenceBound, String str, InferenceBound inferenceBound2, String str2) {
        Function function = new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6665x4f42840(psiSubstitutor, (PsiType) obj);
            }
        };
        return JavaPsiBundle.message("error.inference.variable.has.incompatible.bounds", inferenceVariable.getParameter().getName(), str, StringUtil.join((Collection) inferenceVariable.getBounds(inferenceBound), function, ", "), str2, StringUtil.join((Collection) inferenceVariable.getBounds(inferenceBound2), function, ", "));
    }

    /* JADX INFO: renamed from: lambda$incompatibleBoundsMessage$8$org-jetbrains-kotlin-com-intellij-psi-impl-source-resolve-graphInference-InferenceSession, reason: not valid java name */
    /* synthetic */ String m6665x4f42840(PsiSubstitutor psiSubstitutor, PsiType psiType) {
        PsiType psiTypeSubstituteNonProperBound = substituteNonProperBound(psiType, psiSubstitutor);
        if (psiTypeSubstituteNonProperBound != null) {
            psiType = psiTypeSubstituteNonProperBound;
        }
        return getPresentableText(psiType);
    }

    private PsiType getLowerBound(InferenceVariable inferenceVariable, PsiSubstitutor psiSubstitutor) {
        return composeBound(inferenceVariable, InferenceBound.LOWER, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6664x3195bf1b((Pair) obj);
            }
        }, psiSubstitutor);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$getLowerBound$9$org-jetbrains-kotlin-com-intellij-psi-impl-source-resolve-graphInference-InferenceSession, reason: not valid java name */
    /* synthetic */ PsiType m6664x3195bf1b(Pair pair) {
        return GenericsUtil.getLeastUpperBound((PsiType) pair.first, (PsiType) pair.second, this.myManager);
    }

    private PsiType getUpperBound(InferenceVariable inferenceVariable, PsiSubstitutor psiSubstitutor) {
        return composeBound(inferenceVariable, InferenceBound.UPPER, UPPER_BOUND_FUNCTION, psiSubstitutor);
    }

    private PsiType getEqualsBound(InferenceVariable inferenceVariable, PsiSubstitutor psiSubstitutor) {
        return composeBound(inferenceVariable, InferenceBound.EQ, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return InferenceSession.lambda$getEqualsBound$10((Pair) obj);
            }
        }, psiSubstitutor);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ PsiType lambda$getEqualsBound$10(Pair pair) {
        if (Comparing.equal((PsiType) pair.first, (PsiType) pair.second)) {
            return (PsiType) pair.first;
        }
        return null;
    }

    private PsiType composeBound(InferenceVariable inferenceVariable, InferenceBound inferenceBound, Function<? super Pair<PsiType, PsiType>, ? extends PsiType> function, PsiSubstitutor psiSubstitutor) {
        return composeBound(inferenceVariable, inferenceBound, function, psiSubstitutor, false);
    }

    private PsiType composeBound(InferenceVariable inferenceVariable, InferenceBound inferenceBound, Function<? super Pair<PsiType, PsiType>, ? extends PsiType> function, PsiSubstitutor psiSubstitutor, boolean z) {
        List<PsiType> bounds = inferenceVariable.getBounds(inferenceBound);
        PsiType psiTypeFun = PsiType.NULL;
        Iterator<PsiType> it = bounds.iterator();
        while (it.hasNext()) {
            PsiType psiTypeSubstituteNonProperBound = substituteNonProperBound(it.next(), psiSubstitutor);
            if (z || isProperType(psiTypeSubstituteNonProperBound)) {
                if (psiTypeFun == PsiType.NULL) {
                    psiTypeFun = psiTypeSubstituteNonProperBound;
                } else {
                    psiTypeFun = function.fun(Pair.create(psiTypeFun, psiTypeSubstituteNonProperBound));
                    if (psiTypeFun == null) {
                        return PsiType.NULL;
                    }
                }
            }
        }
        return psiTypeFun;
    }

    public PsiManager getManager() {
        return this.myManager;
    }

    public GlobalSearchScope getScope() {
        PsiElement psiElement = this.myContext;
        if (psiElement != null) {
            return psiElement.getResolveScope();
        }
        return GlobalSearchScope.allScope(this.myManager.getProject());
    }

    public Collection<InferenceVariable> getInferenceVariables() {
        return this.myInferenceVariables;
    }

    public void addConstraint(ConstraintFormula constraintFormula) {
        if (this.myConstraintsCopy.add(constraintFormula)) {
            if ((constraintFormula instanceof ExpressionCompatibilityConstraint) && ignoreLambdaConstraintTree(((ExpressionCompatibilityConstraint) constraintFormula).getExpression())) {
                LOG.error("Should have been stopped at lambda under overload guard");
            }
            this.myConstraints.add(constraintFormula);
        }
    }

    private boolean proceedWithAdditionalConstraints(Set<ConstraintFormula> set, Set<ConstraintFormula> set2) {
        PsiSubstitutor psiSubstitutor = PsiSubstitutor.EMPTY;
        while (!set.isEmpty()) {
            Set<ConstraintFormula> setBuildSubset = buildSubset(set, set2);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (ConstraintFormula constraintFormula : setBuildSubset) {
                if (constraintFormula instanceof InputOutputConstraintFormula) {
                    collectVarsToResolve(linkedHashSet, (InputOutputConstraintFormula) constraintFormula);
                }
            }
            PsiSubstitutor psiSubstitutorResolveSubsetOrdered = resolveSubsetOrdered(linkedHashSet, psiSubstitutor);
            Iterator<ConstraintFormula> it = setBuildSubset.iterator();
            while (it.hasNext()) {
                if (!processOneConstraint(it.next(), set, psiSubstitutorResolveSubsetOrdered, set2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void collectVarsToResolve(Set<? super InferenceVariable> set, InputOutputConstraintFormula inputOutputConstraintFormula) {
        Set<InferenceVariable> inputVariables = inputOutputConstraintFormula.getInputVariables(this);
        if (inputVariables != null) {
            Iterator<InferenceVariable> it = inputVariables.iterator();
            while (it.hasNext()) {
                set.addAll(it.next().getDependencies(this));
            }
            set.addAll(inputVariables);
        }
    }

    private boolean processOneConstraint(ConstraintFormula constraintFormula, Set<ConstraintFormula> set, PsiSubstitutor psiSubstitutor, Set<ConstraintFormula> set2) {
        constraintFormula.apply(psiSubstitutor, true);
        if (constraintFormula instanceof InputOutputConstraintFormula) {
            InputOutputConstraintFormula inputOutputConstraintFormula = (InputOutputConstraintFormula) constraintFormula;
            this.myTempTypes.forceType(inputOutputConstraintFormula.getExpression(), inputOutputConstraintFormula.getCurrentType());
        }
        addConstraint(constraintFormula);
        if (!repeatInferencePhases()) {
            return false;
        }
        if (constraintFormula instanceof ExpressionCompatibilityConstraint) {
            ExpressionCompatibilityConstraint expressionCompatibilityConstraint = (ExpressionCompatibilityConstraint) constraintFormula;
            PsiExpression expression = expressionCompatibilityConstraint.getExpression();
            if (expression instanceof PsiLambdaExpression) {
                PsiLambdaExpression psiLambdaExpression = (PsiLambdaExpression) expression;
                PsiType groundTargetType = psiLambdaExpression.getGroundTargetType(expressionCompatibilityConstraint.getCurrentType());
                collectLambdaReturnExpression(set, set2, psiLambdaExpression, groundTargetType, !isProperType(groundTargetType), psiSubstitutor);
            }
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:54:0x0068 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:57:? A[LOOP:1: B:10:0x0034->B:57:?, LOOP_END, SYNTHETIC] */
    private Set<ConstraintFormula> buildSubset(Set<ConstraintFormula> set, Set<ConstraintFormula> set2) {
        Set<InferenceVariable> outputVariables = getOutputVariables(set);
        Set<InferenceVariable> outputVariables2 = getOutputVariables(set2);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        for (ConstraintFormula constraintFormula : set) {
            if (constraintFormula instanceof InputOutputConstraintFormula) {
                Set<InferenceVariable> inputVariables = ((InputOutputConstraintFormula) constraintFormula).getInputVariables(this);
                if (inputVariables != null) {
                    boolean zDependsOnOutput = false;
                    for (InferenceVariable inferenceVariable : inputVariables) {
                        if (!zDependsOnOutput) {
                            Set<InferenceVariable> dependencies = inferenceVariable.getDependencies(this);
                            dependencies.add(inferenceVariable);
                            if (!hasCapture(inferenceVariable)) {
                                if (dependsOnOutput(outputVariables2, dependencies)) {
                                    set2.add(constraintFormula);
                                } else {
                                    zDependsOnOutput = dependsOnOutput(outputVariables, dependencies);
                                    dependencies.retainAll(outputVariables);
                                    if (!dependencies.isEmpty()) {
                                    }
                                }
                            } else {
                                dependencies.retainAll(outputVariables);
                                if (!dependencies.isEmpty()) {
                                }
                            }
                            zDependsOnOutput = true;
                            break;
                        }
                        break;
                    }
                    if (!zDependsOnOutput) {
                        linkedHashSet.add(constraintFormula);
                        if (inputVariables.isEmpty()) {
                            linkedHashSet2.add(constraintFormula);
                        }
                    }
                } else {
                    linkedHashSet.add(constraintFormula);
                    linkedHashSet2.add(constraintFormula);
                }
            } else {
                linkedHashSet.add(constraintFormula);
            }
        }
        if (linkedHashSet.isEmpty()) {
            set.removeAll(set2);
            if (!set.isEmpty()) {
                linkedHashSet.add(set.iterator().next());
            }
        }
        if (!linkedHashSet2.isEmpty()) {
            linkedHashSet = linkedHashSet2;
        }
        set.removeAll(linkedHashSet);
        return linkedHashSet;
    }

    private boolean dependsOnOutput(Set<InferenceVariable> set, Set<InferenceVariable> set2) {
        Iterator<InferenceVariable> it = set.iterator();
        while (it.hasNext()) {
            if (ContainerUtil.intersects(it.next().getDependencies(this), set2)) {
                return true;
            }
        }
        return false;
    }

    private Set<InferenceVariable> getOutputVariables(Set<ConstraintFormula> set) {
        HashSet hashSet = new HashSet();
        for (ConstraintFormula constraintFormula : set) {
            if (constraintFormula instanceof InputOutputConstraintFormula) {
                InputOutputConstraintFormula inputOutputConstraintFormula = (InputOutputConstraintFormula) constraintFormula;
                Set<InferenceVariable> outputVariables = inputOutputConstraintFormula.getOutputVariables(inputOutputConstraintFormula.getInputVariables(this), this);
                if (outputVariables != null) {
                    hashSet.addAll(outputVariables);
                }
            }
        }
        return hashSet;
    }

    /* JADX WARN: Code duplicated, block: B:60:0x01b1 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:61:0x01b2  */
    /* JADX WARN: Code duplicated, block: B:63:0x01c8  */
    /* JADX WARN: Code duplicated, block: B:65:0x01d6  */
    /* JADX WARN: Code duplicated, block: B:66:0x01d8  */
    /* JADX WARN: Code duplicated, block: B:71:0x0217 A[LOOP:1: B:69:0x020e->B:71:0x0217, LOOP_END] */
    /* JADX WARN: Multi-variable type inference failed */
    public PsiSubstitutor collectApplicabilityConstraints(PsiMethodReferenceExpression psiMethodReferenceExpression, MethodCandidateInfo methodCandidateInfo, PsiType psiType) {
        PsiSubstitutor superClassSubstitutor;
        PsiElementFactory elementFactory;
        int i;
        boolean z;
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiType);
        PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(classResolveResultResolveGenericsClassInType);
        Logger logger = LOG;
        logger.assertTrue(functionalInterfaceMethod != null, this.myContext);
        MethodSignature signature = functionalInterfaceMethod.getSignature(LambdaUtil.getSubstitutor(functionalInterfaceMethod, classResolveResultResolveGenericsClassInType));
        boolean zIsVarargs = methodCandidateInfo.isVarargs();
        PsiMethod element = methodCandidateInfo.getElement();
        PsiClass containingClass = element.mo6092getContainingClass();
        PsiMethodReferenceUtil.QualifierResolveResult qualifierResolveResult = PsiMethodReferenceUtil.getQualifierResolveResult(psiMethodReferenceExpression);
        PsiClass containingClass2 = qualifierResolveResult.getContainingClass();
        if (containingClass2 == 0) {
            return resolveSubset(this.myInferenceVariables, this.mySiteSubstitutor);
        }
        PsiParameter[] parameters = functionalInterfaceMethod.getParameterList().getParameters();
        PsiParameter[] parameters2 = element.getParameterList().getParameters();
        boolean zHasModifierProperty = element.hasModifierProperty("static");
        PsiSubstitutor substitutor = qualifierResolveResult.getSubstitutor();
        if ((parameters2.length == parameters.length && !zIsVarargs) || (zHasModifierProperty && zIsVarargs)) {
            if (element.isConstructor() && PsiUtil.isRawSubstitutor(containingClass2, substitutor)) {
                initBounds(containingClass2.getTypeParameters());
                substitutor = PsiSubstitutor.EMPTY;
            }
            if (containingClass != null && (substitutor = JavaClassSupers.getInstance().getSuperClassSubstitutor(containingClass, containingClass2, psiMethodReferenceExpression.getResolveScope(), substitutor)) == null) {
                logger.error("derived: " + containingClass2 + "; super: " + containingClass + "; reference: " + psiMethodReferenceExpression.getText() + "; containingFile: " + psiMethodReferenceExpression.getContainingFile().getName());
            }
            for (int i2 = 0; i2 < parameters.length; i2++) {
                addConstraint(new TypeCompatibilityConstraint(substituteWithInferenceVariables(getParameterType(parameters2, i2, substitutor, zIsVarargs)), PsiUtil.captureToplevelWildcards(signature.getParameterTypes()[i2], parameters[i2])));
            }
        } else if (PsiMethodReferenceUtil.isResolvedBySecondSearch(psiMethodReferenceExpression, signature, zIsVarargs, zHasModifierProperty, parameters2.length)) {
            PsiType psiType2 = signature.getParameterTypes()[0];
            if (PsiUtil.isRawSubstitutor(containingClass2, substitutor)) {
                PsiClassType subclassType = StrictSubtypingConstraint.getSubclassType(containingClass2, psiType2, true);
                PsiSubstitutor superClassSubstitutor2 = subclassType != null ? TypeConversionUtil.getSuperClassSubstitutor(containingClass2, (PsiClassType) PsiUtil.captureToplevelWildcards(subclassType, this.myContext)) : null;
                if (superClassSubstitutor2 != null) {
                    if (!element.hasTypeParameters() && (signature.getParameterTypes().length == 1 || PsiUtil.isRawSubstitutor(containingClass2, superClassSubstitutor2))) {
                        return containingClass != null ? JavaClassSupers.getInstance().getSuperClassSubstitutor(containingClass, containingClass2, psiMethodReferenceExpression.getResolveScope(), superClassSubstitutor2) : superClassSubstitutor2;
                    }
                    this.mySiteSubstitutor = this.mySiteSubstitutor.putAll(superClassSubstitutor2);
                    if (containingClass != null) {
                        PsiSubstitutor superClassSubstitutor3 = JavaClassSupers.getInstance().getSuperClassSubstitutor(containingClass, containingClass2, psiMethodReferenceExpression.getResolveScope(), superClassSubstitutor2);
                        logger.assertTrue(superClassSubstitutor3 != null, "mContainingClass: " + containingClass.getName() + "; containingClass: " + containingClass2.getName());
                        this.mySiteSubstitutor = this.mySiteSubstitutor.putAll(superClassSubstitutor3);
                    }
                    superClassSubstitutor = superClassSubstitutor2;
                }
                elementFactory = JavaPsiFacade.getElementFactory(this.myManager.getProject());
                if (PsiUtil.isArrayClass(containingClass2)) {
                    return null;
                }
                addConstraint(new TypeCompatibilityConstraint(substituteWithInferenceVariables(elementFactory.createType(containingClass2, superClassSubstitutor)), PsiUtil.captureToplevelWildcards(psiType2, psiMethodReferenceExpression)));
                if (containingClass != null) {
                    superClassSubstitutor = JavaClassSupers.getInstance().getSuperClassSubstitutor(containingClass, containingClass2, psiMethodReferenceExpression.getResolveScope(), superClassSubstitutor);
                    if (superClassSubstitutor != null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    logger.assertTrue(z, "derived: " + containingClass2 + "; super: " + containingClass + "; reference: " + psiMethodReferenceExpression.getText() + "; containingFile: " + psiMethodReferenceExpression.getContainingFile().getName());
                }
                i = 0;
                while (i < signature.getParameterTypes().length - 1) {
                    int i3 = i + 1;
                    addConstraint(new TypeCompatibilityConstraint(substituteWithInferenceVariables(getParameterType(parameters2, i, superClassSubstitutor, zIsVarargs)), PsiUtil.captureToplevelWildcards(signature.getParameterTypes()[i3], parameters[i])));
                    i = i3;
                }
            }
            superClassSubstitutor = substitutor;
            elementFactory = JavaPsiFacade.getElementFactory(this.myManager.getProject());
            if (PsiUtil.isArrayClass(containingClass2)) {
                return null;
            }
            addConstraint(new TypeCompatibilityConstraint(substituteWithInferenceVariables(elementFactory.createType(containingClass2, superClassSubstitutor)), PsiUtil.captureToplevelWildcards(psiType2, psiMethodReferenceExpression)));
            if (containingClass != null) {
                superClassSubstitutor = JavaClassSupers.getInstance().getSuperClassSubstitutor(containingClass, containingClass2, psiMethodReferenceExpression.getResolveScope(), superClassSubstitutor);
                if (superClassSubstitutor != null) {
                    z = true;
                } else {
                    z = false;
                }
                logger.assertTrue(z, "derived: " + containingClass2 + "; super: " + containingClass + "; reference: " + psiMethodReferenceExpression.getText() + "; containingFile: " + psiMethodReferenceExpression.getContainingFile().getName());
            }
            i = 0;
            while (i < signature.getParameterTypes().length - 1) {
                int i4 = i + 1;
                addConstraint(new TypeCompatibilityConstraint(substituteWithInferenceVariables(getParameterType(parameters2, i, superClassSubstitutor, zIsVarargs)), PsiUtil.captureToplevelWildcards(signature.getParameterTypes()[i4], parameters[i])));
                i = i4;
            }
        }
        return null;
    }

    public void setErased() {
        this.myErased = true;
    }

    private InferenceVariable getInferenceVariable(PsiTypeParameter psiTypeParameter) {
        if ((psiTypeParameter instanceof InferenceVariable) && this.myInferenceVariables.contains(psiTypeParameter)) {
            return (InferenceVariable) psiTypeParameter;
        }
        return null;
    }

    public static boolean isMoreSpecific(final PsiMethod psiMethod, final PsiMethod psiMethod2, final PsiSubstitutor psiSubstitutor, final PsiExpression[] psiExpressionArr, final PsiElement psiElement, final boolean z) {
        return ((Boolean) LambdaUtil.performWithSubstitutedParameterBounds(psiMethod.getTypeParameters(), psiSubstitutor, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Boolean.valueOf(InferenceSession.isMoreSpecificInternal(psiMethod, psiMethod2, psiSubstitutor, psiExpressionArr, psiElement, z));
            }
        })).booleanValue();
    }

    private static boolean isMoreSpecificInternal(PsiMethod psiMethod, PsiMethod psiMethod2, PsiSubstitutor psiSubstitutor, PsiExpression[] psiExpressionArr, PsiElement psiElement, boolean z) {
        InferenceSession inferenceSession = new InferenceSession(psiMethod2.getTypeParameters(), psiSubstitutor, psiMethod2.getManager(), psiElement);
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        PsiParameter[] parameters2 = psiMethod2.getParameterList().getParameters();
        if (!z) {
            LOG.assertTrue(parameters.length == parameters2.length);
        }
        int length = parameters.length;
        if (z) {
            length = Math.max(length, parameters2.length) - 1;
        }
        for (int i = 0; i < length; i++) {
            PsiType parameterType = getParameterType(parameters, i, psiSubstitutor, z);
            PsiType psiTypeSubstituteWithInferenceVariables = inferenceSession.substituteWithInferenceVariables(getParameterType(parameters2, i, psiSubstitutor, z));
            if (PsiUtil.isRawSubstitutor(psiMethod2, psiSubstitutor)) {
                psiTypeSubstituteWithInferenceVariables = TypeConversionUtil.erasure(psiTypeSubstituteWithInferenceVariables);
            }
            if ((parameterType instanceof PsiClassType) && (psiTypeSubstituteWithInferenceVariables instanceof PsiClassType) && LambdaUtil.isFunctionalType(parameterType) && LambdaUtil.isFunctionalType(psiTypeSubstituteWithInferenceVariables) && !relates(parameterType, psiTypeSubstituteWithInferenceVariables)) {
                if (!isFunctionalTypeMoreSpecific(parameterType, psiTypeSubstituteWithInferenceVariables, inferenceSession, psiExpressionArr[i])) {
                    return false;
                }
            } else {
                if (inferenceSession.isProperType(psiTypeSubstituteWithInferenceVariables) && !TypeConversionUtil.isAssignable(psiTypeSubstituteWithInferenceVariables, parameterType)) {
                    return false;
                }
                inferenceSession.addConstraint(new StrictSubtypingConstraint(psiTypeSubstituteWithInferenceVariables, parameterType));
            }
        }
        if (z) {
            inferenceSession.addConstraint(new StrictSubtypingConstraint(inferenceSession.substituteWithInferenceVariables(getParameterType(parameters2, length, psiSubstitutor, true)), getParameterType(parameters, length, psiSubstitutor, true)));
        }
        return inferenceSession.repeatInferencePhases();
    }

    public static boolean isFunctionalTypeMoreSpecificOnExpression(PsiType psiType, PsiType psiType2, PsiExpression psiExpression) {
        return isFunctionalTypeMoreSpecific(psiType, psiType2, null, psiExpression);
    }

    private static boolean isFunctionalTypeMoreSpecific(PsiType psiType, PsiType psiType2, InferenceSession inferenceSession, PsiExpression... psiExpressionArr) {
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiType);
        PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(classResolveResultResolveGenericsClassInType);
        Logger logger = LOG;
        logger.assertTrue(functionalInterfaceMethod != null);
        PsiSubstitutor substitutor = LambdaUtil.getSubstitutor(functionalInterfaceMethod, classResolveResultResolveGenericsClassInType);
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType2 = PsiUtil.resolveGenericsClassInType(psiType2);
        PsiMethod functionalInterfaceMethod2 = LambdaUtil.getFunctionalInterfaceMethod(classResolveResultResolveGenericsClassInType2);
        logger.assertTrue(functionalInterfaceMethod2 != null);
        PsiSubstitutor substitutor2 = LambdaUtil.getSubstitutor(functionalInterfaceMethod2, classResolveResultResolveGenericsClassInType2);
        for (PsiExpression psiExpression : psiExpressionArr) {
            if (!argConstraints(psiExpression, inferenceSession, functionalInterfaceMethod, substitutor, functionalInterfaceMethod2, substitutor2)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean argConstraints(PsiExpression psiExpression, final InferenceSession inferenceSession, final PsiMethod psiMethod, final PsiSubstitutor psiSubstitutor, final PsiMethod psiMethod2, final PsiSubstitutor psiSubstitutor2) {
        if (psiExpression instanceof PsiLambdaExpression) {
            PsiLambdaExpression psiLambdaExpression = (PsiLambdaExpression) psiExpression;
            if (psiLambdaExpression.hasFormalParameterTypes()) {
                PsiType psiTypeSubstitute = psiSubstitutor.substitute(psiMethod.getReturnType());
                PsiType psiTypeSubstitute2 = psiSubstitutor2.substitute(psiMethod2.getReturnType());
                if (PsiType.VOID.equals(psiTypeSubstitute2)) {
                    return true;
                }
                List<PsiExpression> returnExpressions = LambdaUtil.getReturnExpressions(psiLambdaExpression);
                if ((psiTypeSubstitute instanceof PsiClassType) && (psiTypeSubstitute2 instanceof PsiClassType) && LambdaUtil.isFunctionalType(psiTypeSubstitute) && LambdaUtil.isFunctionalType(psiTypeSubstitute2) && !TypeConversionUtil.isAssignable(TypeConversionUtil.erasure(psiTypeSubstitute), TypeConversionUtil.erasure(psiTypeSubstitute2)) && !TypeConversionUtil.isAssignable(TypeConversionUtil.erasure(psiTypeSubstitute2), TypeConversionUtil.erasure(psiTypeSubstitute))) {
                    if (!isFunctionalTypeMoreSpecific(psiTypeSubstitute, psiTypeSubstitute2, inferenceSession, (PsiExpression[]) returnExpressions.toArray(PsiExpression.EMPTY_ARRAY))) {
                        return false;
                    }
                } else {
                    boolean z = (psiTypeSubstitute instanceof PsiPrimitiveType) && !PsiType.VOID.equals(psiTypeSubstitute);
                    if (!(((psiTypeSubstitute2 instanceof PsiPrimitiveType) && !PsiType.VOID.equals(psiTypeSubstitute2)) ^ z)) {
                        if (inferenceSession == null) {
                            return (psiTypeSubstitute == null || psiTypeSubstitute2 == null || !TypeConversionUtil.isAssignable(psiTypeSubstitute2, psiTypeSubstitute)) ? false : true;
                        }
                        inferenceSession.addConstraint(new StrictSubtypingConstraint(psiTypeSubstitute2, psiTypeSubstitute));
                        return true;
                    }
                    for (PsiExpression psiExpression2 : returnExpressions) {
                        if (!PsiPolyExpressionUtil.isPolyExpression(psiExpression2)) {
                            PsiType type = psiExpression2.getType();
                            if (z) {
                                if (!(type instanceof PsiPrimitiveType)) {
                                    return false;
                                }
                            } else if (!(type instanceof PsiClassType)) {
                                return false;
                            }
                        } else if (z) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        if (psiExpression instanceof PsiMethodReferenceExpression) {
            PsiMethodReferenceExpression psiMethodReferenceExpression = (PsiMethodReferenceExpression) psiExpression;
            if (psiMethodReferenceExpression.isExact()) {
                PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
                PsiParameter[] parameters2 = psiMethod2.getParameterList().getParameters();
                if (parameters.length != parameters2.length) {
                    LOG.error("s: " + psiMethod.getParameterList().getText() + "; t: " + psiMethod2.getParameterList().getText());
                }
                for (int i = 0; i < parameters2.length; i++) {
                    PsiType psiTypeSubstitute3 = psiSubstitutor2.substitute(parameters2[i].getType());
                    PsiType psiTypeSubstitute4 = psiSubstitutor.substitute(parameters[i].getType());
                    if (inferenceSession != null) {
                        inferenceSession.addConstraint(new TypeEqualityConstraint(psiTypeSubstitute3, psiTypeSubstitute4));
                    } else if (!Comparing.equal(psiTypeSubstitute3, psiTypeSubstitute4)) {
                        return false;
                    }
                }
                PsiType psiTypeSubstitute5 = psiSubstitutor.substitute(psiMethod.getReturnType());
                PsiType psiTypeSubstitute6 = psiSubstitutor2.substitute(psiMethod2.getReturnType());
                if (PsiType.VOID.equals(psiTypeSubstitute6)) {
                    return true;
                }
                boolean z2 = (psiTypeSubstitute5 instanceof PsiPrimitiveType) && !PsiType.VOID.equals(psiTypeSubstitute5);
                boolean z3 = (psiTypeSubstitute6 instanceof PsiPrimitiveType) && !PsiType.VOID.equals(psiTypeSubstitute6);
                if (!(z2 ^ z3)) {
                    if (inferenceSession == null) {
                        return (psiTypeSubstitute5 == null || psiTypeSubstitute6 == null || !TypeConversionUtil.isAssignable(psiTypeSubstitute6, psiTypeSubstitute5)) ? false : true;
                    }
                    inferenceSession.addConstraint(new StrictSubtypingConstraint(psiTypeSubstitute6, psiTypeSubstitute5));
                    return true;
                }
                PsiMember potentiallyApplicableMember = psiMethodReferenceExpression.getPotentiallyApplicableMember();
                LOG.assertTrue(potentiallyApplicableMember != null, psiExpression);
                if (potentiallyApplicableMember instanceof PsiMethod) {
                    PsiType returnType = ((PsiMethod) potentiallyApplicableMember).getReturnType();
                    if ((z2 && (returnType instanceof PsiPrimitiveType) && !PsiType.VOID.equals(returnType)) || (z3 && (returnType instanceof PsiClassType))) {
                        return true;
                    }
                }
                return false;
            }
        }
        if (psiExpression instanceof PsiParenthesizedExpression) {
            return argConstraints(((PsiParenthesizedExpression) psiExpression).getExpression(), inferenceSession, psiMethod, psiSubstitutor, psiMethod2, psiSubstitutor2);
        }
        if (psiExpression instanceof PsiConditionalExpression) {
            PsiConditionalExpression psiConditionalExpression = (PsiConditionalExpression) psiExpression;
            return argConstraints(psiConditionalExpression.getThenExpression(), inferenceSession, psiMethod, psiSubstitutor, psiMethod2, psiSubstitutor2) && argConstraints(psiConditionalExpression.getElseExpression(), inferenceSession, psiMethod, psiSubstitutor, psiMethod2, psiSubstitutor2);
        }
        if (psiExpression instanceof PsiSwitchExpression) {
            return PsiUtil.getSwitchResultExpressions((PsiSwitchExpression) psiExpression).stream().allMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession$$ExternalSyntheticLambda12
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return InferenceSession.argConstraints((PsiExpression) obj, this.f$0, psiMethod, psiSubstitutor, psiMethod2, psiSubstitutor2);
                }
            });
        }
        return false;
    }

    private static boolean relates(PsiType psiType, PsiType psiType2) {
        PsiType psiTypeErasure;
        PsiType psiTypeErasure2 = TypeConversionUtil.erasure(psiType2);
        LOG.assertTrue(psiTypeErasure2 != null);
        if (psiType instanceof PsiIntersectionType) {
            boolean zIsAssignable = true;
            boolean zIsAssignable2 = false;
            for (PsiType psiType3 : ((PsiIntersectionType) psiType).getConjuncts()) {
                PsiType psiTypeErasure3 = TypeConversionUtil.erasure(psiType3);
                if (psiTypeErasure3 != null) {
                    zIsAssignable &= TypeConversionUtil.isAssignable(psiTypeErasure3, psiTypeErasure2);
                    zIsAssignable2 |= TypeConversionUtil.isAssignable(psiTypeErasure2, psiTypeErasure3);
                }
            }
            return zIsAssignable || zIsAssignable2;
        }
        if (!(psiType instanceof PsiClassType) || (psiTypeErasure = TypeConversionUtil.erasure(psiType)) == null) {
            return false;
        }
        return TypeConversionUtil.isAssignable(psiTypeErasure, psiTypeErasure2) || TypeConversionUtil.isAssignable(psiTypeErasure2, psiTypeErasure);
    }

    void collectCaptureDependencies(InferenceVariable inferenceVariable, Set<? super InferenceVariable> set) {
        this.myIncorporationPhase.collectCaptureDependencies(inferenceVariable, set);
    }

    boolean hasCapture(InferenceVariable inferenceVariable) {
        return this.myIncorporationPhase.hasCaptureConstraints(Collections.singletonList(inferenceVariable));
    }

    public PsiElement getContext() {
        return this.myContext;
    }

    public final void propagateVariables(InferenceSession inferenceSession) {
        if (inferenceSession == null) {
            $$$reportNull$$$0(21);
        }
        this.myInferenceVariables.addAll(inferenceSession.getInferenceVariables());
        this.myRestoreNameSubstitution = this.myRestoreNameSubstitution.putAll(inferenceSession.getRestoreNameSubstitution());
    }

    public PsiType substituteWithInferenceVariables(PsiType psiType) {
        return this.myInferenceSubstitution.substitute(psiType);
    }

    public PsiSubstitutor getInferenceSubstitution() {
        return this.myInferenceSubstitution;
    }

    public PsiSubstitutor getRestoreNameSubstitution() {
        return this.myRestoreNameSubstitution;
    }

    public InferenceSessionContainer getInferenceSessionContainer() {
        return this.myInferenceSessionContainer;
    }

    public PsiType startWithFreshVars(PsiType psiType) {
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        for (InferenceVariable inferenceVariable : this.myInferenceVariables) {
            psiSubstitutorPut = psiSubstitutorPut.put(inferenceVariable, JavaPsiFacade.getElementFactory(this.myManager.getProject()).createType(inferenceVariable.getParameter()));
        }
        return psiSubstitutorPut.substitute(psiType);
    }

    public static PsiClass findParameterizationOfTheSameGenericClass(List<? extends PsiType> list, Processor<? super Pair<PsiType, PsiType>> processor) {
        for (int i = 0; i < list.size(); i++) {
            PsiType psiType = list.get(i);
            PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(psiType);
            if (psiClassResolveClassInClassTypeOnly != null) {
                LinkedHashSet<PsiClass> superClasses = InheritanceUtil.getSuperClasses(psiClassResolveClassInClassTypeOnly);
                superClasses.add(psiClassResolveClassInClassTypeOnly);
                for (int i2 = i + 1; i2 < list.size(); i2++) {
                    PsiType psiType2 = list.get(i2);
                    PsiClass psiClassResolveClassInClassTypeOnly2 = PsiUtil.resolveClassInClassTypeOnly(psiType2);
                    if (psiClassResolveClassInClassTypeOnly2 != null) {
                        LinkedHashSet<PsiClass> linkedHashSet = new LinkedHashSet();
                        linkedHashSet.add(psiClassResolveClassInClassTypeOnly2);
                        linkedHashSet.addAll(InheritanceUtil.getSuperClasses(psiClassResolveClassInClassTypeOnly2));
                        linkedHashSet.retainAll(superClasses);
                        for (PsiClass psiClass : linkedHashSet) {
                            PsiSubstitutor superClassSubstitutor = TypeConversionUtil.getSuperClassSubstitutor(psiClass, (PsiClassType) psiType);
                            PsiSubstitutor superClassSubstitutor2 = TypeConversionUtil.getSuperClassSubstitutor(psiClass, (PsiClassType) psiType2);
                            boolean z = false;
                            for (PsiTypeParameter psiTypeParameter : psiClass.getTypeParameters()) {
                                if (!processor.process(Pair.create(superClassSubstitutor.substituteWithBoundsPromotion(psiTypeParameter), superClassSubstitutor2.substituteWithBoundsPromotion(psiTypeParameter)))) {
                                    z = true;
                                }
                            }
                            if (z) {
                                return psiClass;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public List<String> getIncompatibleErrorMessages() {
        return this.myErrorMessages;
    }

    public boolean isErased() {
        return this.myErased;
    }
}
