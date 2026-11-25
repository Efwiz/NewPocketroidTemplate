package org.jetbrains.kotlin.com.intellij.psi.infos;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaSdkVersion;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaVersionService;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.GenericsUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpressionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.ThreadLocalTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.DefaultParameterTypeInferencePolicy;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;

/* JADX INFO: loaded from: classes3.dex */
public class MethodCandidateInfo extends CandidateInfo {
    public static final RecursionGuard<PsiElement> ourOverloadGuard = RecursionManager.createGuard("overload.guard");
    private volatile boolean myApplicabilityError;
    private volatile int myApplicabilityLevel;
    private final PsiElement myArgumentList;
    private final PsiType[] myArgumentTypes;
    private PsiSubstitutor myCalcedSubstitutor;
    private volatile boolean myErased;
    private volatile String myInferenceError;
    private final LanguageLevel myLanguageLevel;
    private volatile int myPertinentApplicabilityLevel;
    private final PsiType[] myTypeArguments;

    public static final class ApplicabilityLevel {
        public static final int FIXED_ARITY = 3;
        public static final int NOT_APPLICABLE = 1;
        public static final int VARARGS = 2;
    }

    public @interface ApplicabilityLevelConstant {
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0012  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 13 && i != 17 && i != 18) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 13 && i != 17 && i != 18) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "substitutor";
                break;
            case 2:
            default:
                objArr[0] = "candidate";
                break;
            case 4:
                objArr[0] = "languageLevel";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 17:
            case 18:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/infos/MethodCandidateInfo";
                break;
            case 12:
            case 14:
            case 15:
                objArr[0] = "policy";
                break;
            case 16:
                objArr[0] = "arguments";
                break;
            case 19:
                objArr[0] = "applicabilityError";
                break;
        }
        if (i != 13 && i != 17 && i != 18) {
            switch (i) {
                case 5:
                    objArr[1] = "getSiteSubstitutor";
                    break;
                case 6:
                    objArr[1] = "getSubstitutorFromQualifier";
                    break;
                case 7:
                case 8:
                case 9:
                case 10:
                    objArr[1] = "getSubstitutor";
                    break;
                case 11:
                    objArr[1] = "getElement";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/infos/MethodCandidateInfo";
                    break;
            }
        } else {
            objArr[1] = "inferTypeArguments";
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 17:
            case 18:
                break;
            case 12:
            case 15:
            case 16:
                objArr[2] = "inferTypeArguments";
                break;
            case 14:
                objArr[2] = "inferSubstitutorFromArgs";
                break;
            case 19:
                objArr[2] = "setApplicabilityError";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13 && i != 17 && i != 18) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ PsiSubstitutor lambda$getPertinentApplicabilityLevelInner$4(PsiSubstitutor psiSubstitutor) {
        return psiSubstitutor;
    }

    public boolean isVarargs() {
        return false;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MethodCandidateInfo(PsiElement psiElement, PsiSubstitutor psiSubstitutor, boolean z, boolean z2, PsiElement psiElement2, PsiElement psiElement3, PsiType[] psiTypeArr, PsiType[] psiTypeArr2) {
        this(psiElement, psiSubstitutor, z, z2, psiElement2, psiElement3, psiTypeArr, psiTypeArr2, PsiUtil.getLanguageLevel(psiElement2));
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MethodCandidateInfo(PsiElement psiElement, PsiSubstitutor psiSubstitutor, boolean z, boolean z2, PsiElement psiElement2, PsiElement psiElement3, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, LanguageLevel languageLevel) {
        super(psiElement, psiSubstitutor, z, z2, psiElement3);
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(3);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(4);
        }
        this.myArgumentList = psiElement2;
        this.myArgumentTypes = psiTypeArr;
        this.myTypeArguments = psiTypeArr2;
        this.myLanguageLevel = languageLevel;
    }

    public boolean isApplicable() {
        return getPertinentApplicabilityLevel() != 1;
    }

    private int getApplicabilityLevelInner() {
        PsiType[] argumentTypes = getArgumentTypes();
        if (argumentTypes == null) {
            return 1;
        }
        int applicabilityLevel = PsiUtil.getApplicabilityLevel(getElement(), getSubstitutor(), argumentTypes, this.myLanguageLevel);
        if (applicabilityLevel <= 1 || isTypeArgumentsApplicable()) {
            return applicabilityLevel;
        }
        return 1;
    }

    public int getApplicabilityLevel() {
        int i = this.myApplicabilityLevel;
        if (i != 0) {
            return i;
        }
        int applicabilityLevelInner = getApplicabilityLevelInner();
        this.myApplicabilityLevel = applicabilityLevelInner;
        return applicabilityLevelInner;
    }

    public int getPertinentApplicabilityLevel() {
        return getPertinentApplicabilityLevel(null);
    }

    public int getPertinentApplicabilityLevel(final Map<MethodCandidateInfo, PsiSubstitutor> map) {
        int i = this.myPertinentApplicabilityLevel;
        if (i != 0) {
            return i;
        }
        int pertinentApplicabilityLevelInner = getPertinentApplicabilityLevelInner(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6689x172ecb40(map);
            }
        });
        this.myPertinentApplicabilityLevel = pertinentApplicabilityLevelInner;
        return pertinentApplicabilityLevelInner;
    }

    /* JADX INFO: renamed from: lambda$getPertinentApplicabilityLevel$0$org-jetbrains-kotlin-com-intellij-psi-infos-MethodCandidateInfo, reason: not valid java name */
    /* synthetic */ PsiSubstitutor m6689x172ecb40(Map map) {
        return map != null ? (PsiSubstitutor) map.get(this) : getSubstitutor(false);
    }

    private int getPertinentApplicabilityLevelInner(Supplier<PsiSubstitutor> supplier) {
        if (this.myArgumentList == null || !this.myLanguageLevel.isAtLeast(LanguageLevel.JDK_1_8)) {
            return getApplicabilityLevel();
        }
        final PsiMethod element = getElement();
        if (isToInferApplicability()) {
            supplier.get();
            if (this.myApplicabilityError || isPotentiallyCompatible() != ThreeState.YES) {
                return 1;
            }
            return isVarargs() ? 2 : 3;
        }
        final PsiSubstitutor psiSubstitutor = supplier.get();
        int iIntValue = ((Integer) Objects.requireNonNull((Integer) ourOverloadGuard.doPreventingRecursion(this.myArgumentList, false, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return this.f$0.m6691xcbe82eaf(element, psiSubstitutor);
            }
        }))).intValue();
        if (iIntValue <= 1 || isTypeArgumentsApplicable(new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return MethodCandidateInfo.lambda$getPertinentApplicabilityLevelInner$4(psiSubstitutor);
            }
        })) {
            return iIntValue;
        }
        return 1;
    }

    /* JADX INFO: renamed from: lambda$getPertinentApplicabilityLevelInner$3$org-jetbrains-kotlin-com-intellij-psi-infos-MethodCandidateInfo, reason: not valid java name */
    /* synthetic */ Integer m6691xcbe82eaf(final PsiMethod psiMethod, final PsiSubstitutor psiSubstitutor) {
        return (Integer) computeWithKnownTargetType(new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return this.f$0.m6690x7e28b6ae(psiMethod, psiSubstitutor);
            }
        }, psiSubstitutor);
    }

    /* JADX INFO: renamed from: lambda$getPertinentApplicabilityLevelInner$2$org-jetbrains-kotlin-com-intellij-psi-infos-MethodCandidateInfo, reason: not valid java name */
    /* synthetic */ Integer m6690x7e28b6ae(final PsiMethod psiMethod, PsiSubstitutor psiSubstitutor) {
        PsiType[] argumentTypes = getArgumentTypes();
        if (argumentTypes == null) {
            return 1;
        }
        int applicabilityLevel = PsiUtil.getApplicabilityLevel(psiMethod, psiSubstitutor, argumentTypes, this.myLanguageLevel, true, true, new PsiUtil.ApplicabilityChecker() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil.ApplicabilityChecker
            public final boolean isApplicable(PsiType psiType, PsiType psiType2, boolean z, int i) {
                return MethodCandidateInfo.checkFunctionalInterfaceAcceptance(psiMethod, psiType, psiType2, z);
            }
        });
        if (!isVarargs() && applicabilityLevel < 3) {
            return 1;
        }
        return Integer.valueOf(applicabilityLevel);
    }

    private <T> T computeWithKnownTargetType(final Computable<T> computable, final PsiSubstitutor psiSubstitutor) {
        PsiElement psiElement = this.myArgumentList;
        if (psiElement instanceof PsiExpressionList) {
            final PsiExpressionList psiExpressionList = (PsiExpressionList) psiElement;
            final PsiElement parent = psiExpressionList.getThis$0();
            boolean zBooleanValue = ((Boolean) CachedValuesManager.getCachedValue(parent, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda8
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return MethodCandidateInfo.lambda$computeWithKnownTargetType$5(parent);
                }
            })).booleanValue();
            final PsiExpression[] psiExpressionArr = (PsiExpression[]) Arrays.stream(psiExpressionList.getExpressions()).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PsiUtil.skipParenthesizedExprDown((PsiExpression) obj);
                }
            }).filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda10
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return MethodCandidateInfo.lambda$computeWithKnownTargetType$7((PsiExpression) obj);
                }
            }).toArray(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda11
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return MethodCandidateInfo.lambda$computeWithKnownTargetType$8(i);
                }
            });
            return (T) ThreadLocalTypes.performWithTypes(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda12
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m6688x93b0442(psiExpressionArr, psiExpressionList, psiSubstitutor, computable, (ThreadLocalTypes) obj);
                }
            }, zBooleanValue);
        }
        return computable.compute();
    }

    static /* synthetic */ CachedValueProvider.Result lambda$computeWithKnownTargetType$5(PsiElement psiElement) {
        return new CachedValueProvider.Result(Boolean.valueOf(!(psiElement instanceof PsiCallExpression) || JavaPsiFacade.getInstance(psiElement.getProject()).getResolveHelper().hasOverloads((PsiCallExpression) psiElement)), PsiModificationTracker.MODIFICATION_COUNT);
    }

    static /* synthetic */ boolean lambda$computeWithKnownTargetType$7(PsiExpression psiExpression) {
        return (psiExpression == null || (psiExpression instanceof PsiFunctionalExpression) || !PsiPolyExpressionUtil.isPolyExpression(psiExpression)) ? false : true;
    }

    static /* synthetic */ PsiExpression[] lambda$computeWithKnownTargetType$8(int i) {
        return new PsiExpression[i];
    }

    /* JADX INFO: renamed from: lambda$computeWithKnownTargetType$9$org-jetbrains-kotlin-com-intellij-psi-infos-MethodCandidateInfo, reason: not valid java name */
    /* synthetic */ Object m6688x93b0442(PsiExpression[] psiExpressionArr, PsiExpressionList psiExpressionList, PsiSubstitutor psiSubstitutor, Computable computable, ThreadLocalTypes threadLocalTypes) {
        PsiMethod element = getElement();
        boolean zIsVarargs = isVarargs();
        for (PsiExpression psiExpression : psiExpressionArr) {
            threadLocalTypes.forceType(psiExpression, PsiTypesUtil.getTypeByMethod(psiExpression, psiExpressionList, element, zIsVarargs, psiSubstitutor, false));
        }
        return computable.compute();
    }

    public boolean isOnArgumentList(PsiExpressionList psiExpressionList) {
        return this.myArgumentList == psiExpressionList;
    }

    public void setErased() {
        this.myErased = true;
    }

    public boolean isErased() {
        return this.myErased;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkFunctionalInterfaceAcceptance(PsiMethod psiMethod, PsiType psiType, PsiType psiType2, boolean z) {
        PsiFunctionalExpression expression;
        if (psiType2 instanceof PsiLambdaExpressionType) {
            expression = ((PsiLambdaExpressionType) psiType2).getExpression();
        } else {
            expression = psiType2 instanceof PsiMethodReferenceType ? ((PsiMethodReferenceType) psiType2).getExpression() : null;
        }
        if (expression != null) {
            return !(psiType instanceof PsiArrayType) && expression.isAcceptable(psiType, psiMethod);
        }
        return TypeConversionUtil.isAssignable(psiType, psiType2, z);
    }

    public boolean isToInferApplicability() {
        return this.myTypeArguments == null && getElement().hasTypeParameters() && !isRawSubstitution();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ThreeState isPotentiallyCompatible() {
        if (this.myArgumentList instanceof PsiExpressionList) {
            PsiMethod element = getElement();
            PsiParameter[] parameters = element.getParameterList().getParameters();
            PsiExpression[] expressions = ((PsiExpressionList) this.myArgumentList).getExpressions();
            if (!isVarargs() && this.myLanguageLevel.isAtLeast(LanguageLevel.JDK_1_8)) {
                if (expressions.length != parameters.length) {
                    return ThreeState.NO;
                }
            } else {
                if (expressions.length < parameters.length - 1) {
                    return ThreeState.NO;
                }
                if (parameters.length == 0 && expressions.length != parameters.length) {
                    return ThreeState.NO;
                }
            }
            int i = 0;
            boolean z = false;
            while (i < expressions.length) {
                PsiExpression psiExpression = expressions[i];
                PsiType type = (i < parameters.length ? parameters[i] : parameters[parameters.length - 1]).getType();
                if ((type instanceof PsiEllipsisType) && isVarargs()) {
                    type = ((PsiEllipsisType) type).getComponentType();
                }
                ThreeState threeStateIsPotentialCompatible = isPotentialCompatible(psiExpression, getSiteSubstitutor().substitute(type), element);
                if (threeStateIsPotentialCompatible == ThreeState.NO) {
                    return ThreeState.NO;
                }
                if (threeStateIsPotentialCompatible == ThreeState.UNSURE) {
                    z = true;
                }
                i++;
            }
            if (z) {
                return ThreeState.UNSURE;
            }
            if (element.hasTypeParameters() && this.myTypeArguments != null) {
                return ThreeState.fromBoolean(element.getTypeParameters().length == this.myTypeArguments.length);
            }
        }
        return ThreeState.YES;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ThreeState isPotentialCompatible(PsiExpression psiExpression, final PsiType psiType, final PsiMethod psiMethod) {
        if (psiExpression instanceof PsiFunctionalExpression) {
            PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(psiType);
            if ((psiClassResolveClassInClassTypeOnly instanceof PsiTypeParameter) && psiMethod.equals(((PsiTypeParameter) psiClassResolveClassInClassTypeOnly).getOwner())) {
                return ThreeState.YES;
            }
            PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(psiType);
            if (functionalInterfaceMethod == null) {
                return ThreeState.NO;
            }
            if ((psiExpression instanceof PsiLambdaExpression) && ((PsiLambdaExpression) psiExpression).getParameterList().getParametersCount() != functionalInterfaceMethod.getParameterList().getParametersCount()) {
                return ThreeState.NO;
            }
            if (!((PsiFunctionalExpression) psiExpression).isPotentiallyCompatible(psiType)) {
                return ThreeState.UNSURE;
            }
        } else {
            if (psiExpression instanceof PsiParenthesizedExpression) {
                return isPotentialCompatible(((PsiParenthesizedExpression) psiExpression).getExpression(), psiType, psiMethod);
            }
            if (psiExpression instanceof PsiConditionalExpression) {
                PsiConditionalExpression psiConditionalExpression = (PsiConditionalExpression) psiExpression;
                ThreeState threeStateIsPotentialCompatible = isPotentialCompatible(psiConditionalExpression.getThenExpression(), psiType, psiMethod);
                ThreeState threeStateIsPotentialCompatible2 = isPotentialCompatible(psiConditionalExpression.getElseExpression(), psiType, psiMethod);
                if (threeStateIsPotentialCompatible == ThreeState.NO || threeStateIsPotentialCompatible2 == ThreeState.NO) {
                    return ThreeState.NO;
                }
                if (threeStateIsPotentialCompatible == ThreeState.UNSURE || threeStateIsPotentialCompatible2 == ThreeState.UNSURE) {
                    return ThreeState.UNSURE;
                }
            } else if (psiExpression instanceof PsiSwitchExpression) {
                Set set = (Set) PsiUtil.getSwitchResultExpressions((PsiSwitchExpression) psiExpression).stream().map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda7
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MethodCandidateInfo.isPotentialCompatible((PsiExpression) obj, psiType, psiMethod);
                    }
                }).collect(Collectors.toSet());
                if (set.contains(ThreeState.NO)) {
                    return ThreeState.NO;
                }
                if (set.contains(ThreeState.UNSURE)) {
                    return ThreeState.UNSURE;
                }
            }
        }
        return ThreeState.YES;
    }

    public PsiSubstitutor getSiteSubstitutor() {
        PsiSubstitutor substitutor = super.getSubstitutor();
        if (this.myTypeArguments != null) {
            PsiTypeParameter[] typeParameters = getElement().getTypeParameters();
            int i = 0;
            while (true) {
                PsiType[] psiTypeArr = this.myTypeArguments;
                if (i >= psiTypeArr.length || i >= typeParameters.length) {
                    break;
                }
                substitutor = substitutor.put(typeParameters[i], psiTypeArr[i]);
                i++;
            }
        }
        if (substitutor == null) {
            $$$reportNull$$$0(5);
        }
        return substitutor;
    }

    public PsiSubstitutor getSubstitutorFromQualifier() {
        PsiSubstitutor substitutor = super.getSubstitutor();
        if (substitutor == null) {
            $$$reportNull$$$0(6);
        }
        return substitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo, org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
    public PsiSubstitutor getSubstitutor() {
        PsiSubstitutor substitutor = getSubstitutor(true);
        if (substitutor == null) {
            $$$reportNull$$$0(7);
        }
        return substitutor;
    }

    public PsiSubstitutor getSubstitutor(boolean z) {
        PsiSubstitutor substitutor = this.myCalcedSubstitutor;
        if (substitutor == null || ((!z && this.myLanguageLevel.isAtLeast(LanguageLevel.JDK_1_8)) || isOverloadCheck())) {
            substitutor = super.getSubstitutor();
            PsiMethod element = getElement();
            if (this.myTypeArguments == null) {
                RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
                PsiSubstitutor psiSubstitutorInferTypeArguments = inferTypeArguments(DefaultParameterTypeInferencePolicy.INSTANCE, z);
                if (isOverloadCheck() || ((!z && this.myLanguageLevel.isAtLeast(LanguageLevel.JDK_1_8)) || ((this.myArgumentList != null && PsiResolveHelper.ourGraphGuard.currentStack().contains(this.myArgumentList.getThis$0())) || !stackStampMarkStack.mayCacheNow()))) {
                    if (psiSubstitutorInferTypeArguments == null) {
                        $$$reportNull$$$0(8);
                    }
                    return psiSubstitutorInferTypeArguments;
                }
                this.myCalcedSubstitutor = psiSubstitutorInferTypeArguments;
                substitutor = psiSubstitutorInferTypeArguments;
            } else {
                PsiTypeParameter[] typeParameters = element.getTypeParameters();
                if (isRawSubstitution()) {
                    PsiSubstitutor psiSubstitutorCreateRawSubstitutor = JavaPsiFacade.getElementFactory(element.getProject()).createRawSubstitutor(this.mySubstitutor, typeParameters);
                    if (psiSubstitutorCreateRawSubstitutor == null) {
                        $$$reportNull$$$0(9);
                    }
                    return psiSubstitutorCreateRawSubstitutor;
                }
                int i = 0;
                while (true) {
                    PsiType[] psiTypeArr = this.myTypeArguments;
                    if (i >= psiTypeArr.length || i >= typeParameters.length) {
                        break;
                    }
                    substitutor = substitutor.put(typeParameters[i], psiTypeArr[i]);
                    i++;
                }
                this.myCalcedSubstitutor = substitutor;
            }
        }
        if (substitutor == null) {
            $$$reportNull$$$0(10);
        }
        return substitutor;
    }

    public static boolean isOverloadCheck() {
        return !ourOverloadGuard.currentStack().isEmpty();
    }

    public static boolean isOverloadCheck(PsiElement psiElement) {
        return ourOverloadGuard.currentStack().contains(psiElement);
    }

    public boolean isTypeArgumentsApplicable() {
        return isTypeArgumentsApplicable(new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return this.f$0.m6693x12f0336f();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$isTypeArgumentsApplicable$11$org-jetbrains-kotlin-com-intellij-psi-infos-MethodCandidateInfo, reason: not valid java name */
    /* synthetic */ PsiSubstitutor m6693x12f0336f() {
        return getSubstitutor(false);
    }

    private boolean isTypeArgumentsApplicable(Computable<? extends PsiSubstitutor> computable) {
        PsiMethod element = getElement();
        PsiTypeParameter[] typeParameters = element.getTypeParameters();
        PsiType[] psiTypeArr = this.myTypeArguments;
        if (psiTypeArr == null || typeParameters.length == psiTypeArr.length || PsiUtil.isLanguageLevel7OrHigher(element)) {
            return GenericsUtil.isTypeArgumentsApplicable(typeParameters, computable.compute(), getParent());
        }
        return typeParameters.length == 0 && JavaVersionService.getInstance().isAtLeast(element, JavaSdkVersion.JDK_1_7);
    }

    protected PsiElement getParent() {
        PsiElement psiElement = this.myArgumentList;
        if (psiElement != null) {
            return psiElement.getThis$0();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo, org.jetbrains.kotlin.com.intellij.psi.ResolveResult
    public boolean isValidResult() {
        return super.isValidResult() && isApplicable();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo, org.jetbrains.kotlin.com.intellij.psi.ResolveResult
    public PsiMethod getElement() {
        PsiMethod psiMethod = (PsiMethod) super.getElement();
        if (psiMethod == null) {
            $$$reportNull$$$0(11);
        }
        return psiMethod;
    }

    public PsiSubstitutor inferTypeArguments(ParameterTypeInferencePolicy parameterTypeInferencePolicy, boolean z) {
        PsiExpression[] expressions;
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(12);
        }
        PsiElement psiElement = this.myArgumentList;
        if (psiElement instanceof PsiExpressionList) {
            expressions = ((PsiExpressionList) psiElement).getExpressions();
        } else {
            expressions = PsiExpression.EMPTY_ARRAY;
        }
        PsiSubstitutor psiSubstitutorInferTypeArguments = inferTypeArguments(parameterTypeInferencePolicy, expressions, z);
        if (psiSubstitutorInferTypeArguments == null) {
            $$$reportNull$$$0(13);
        }
        return psiSubstitutorInferTypeArguments;
    }

    public PsiSubstitutor inferSubstitutorFromArgs(ParameterTypeInferencePolicy parameterTypeInferencePolicy, PsiExpression[] psiExpressionArr) {
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(14);
        }
        if (this.myTypeArguments == null) {
            return inferTypeArguments(parameterTypeInferencePolicy, psiExpressionArr, true);
        }
        return getSiteSubstitutor();
    }

    public PsiSubstitutor inferTypeArguments(final ParameterTypeInferencePolicy parameterTypeInferencePolicy, final PsiExpression[] psiExpressionArr, boolean z) {
        PsiSubstitutor psiSubstitutor;
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(15);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(16);
        }
        Computable computable = new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return this.f$0.m6692xd30f6f59(psiExpressionArr, parameterTypeInferencePolicy);
            }
        };
        if (!z) {
            PsiElement psiElement = this.myArgumentList;
            if (psiElement == null) {
                psiSubstitutor = PsiSubstitutor.EMPTY;
            } else {
                psiSubstitutor = (PsiSubstitutor) Objects.requireNonNull((PsiSubstitutor) ourOverloadGuard.doPreventingRecursion(psiElement, false, computable));
            }
            if (psiSubstitutor == null) {
                $$$reportNull$$$0(17);
            }
            return psiSubstitutor;
        }
        PsiSubstitutor psiSubstitutor2 = (PsiSubstitutor) computable.compute();
        if (psiSubstitutor2 == null) {
            $$$reportNull$$$0(18);
        }
        return psiSubstitutor2;
    }

    /* JADX INFO: renamed from: lambda$inferTypeArguments$12$org-jetbrains-kotlin-com-intellij-psi-infos-MethodCandidateInfo, reason: not valid java name */
    /* synthetic */ PsiSubstitutor m6692xd30f6f59(PsiExpression[] psiExpressionArr, ParameterTypeInferencePolicy parameterTypeInferencePolicy) {
        PsiMethod element = getElement();
        PsiTypeParameter[] typeParameters = element.getTypeParameters();
        if (isRawSubstitution()) {
            return JavaPsiFacade.getElementFactory(element.getProject()).createRawSubstitutor(this.mySubstitutor, typeParameters);
        }
        PsiElement parent = getParent();
        if (parent == null) {
            return PsiSubstitutor.EMPTY;
        }
        return JavaPsiFacade.getInstance(element.getProject()).getResolveHelper().inferTypeArguments(typeParameters, element.getParameterList().getParameters(), psiExpressionArr, this, parent, parameterTypeInferencePolicy, this.myLanguageLevel);
    }

    public boolean isRawSubstitution() {
        PsiClass containingClass;
        PsiMethod element = getElement();
        return (element.hasModifierProperty("static") || (containingClass = element.mo6092getContainingClass()) == null || !PsiUtil.isRawSubstitutor(containingClass, this.mySubstitutor)) ? false : true;
    }

    public boolean isInferencePossible() {
        PsiElement psiElement = this.myArgumentList;
        return psiElement != null && psiElement.isValid();
    }

    public PsiType[] getArgumentTypes() {
        return this.myArgumentTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo
    public boolean equals(Object obj) {
        return super.equals(obj) && isVarargs() == ((MethodCandidateInfo) obj).isVarargs();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo
    public int hashCode() {
        return (super.hashCode() * 31) + (isVarargs() ? 1 : 0);
    }

    public void setApplicabilityError(String str) {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        boolean zIsOverloadCheck = isOverloadCheck();
        if (!zIsOverloadCheck) {
            this.myInferenceError = str;
        }
        PsiElement psiElement = this.myArgumentList;
        if (psiElement == null) {
            if (!zIsOverloadCheck) {
                return;
            }
        } else if (!isOverloadCheck(psiElement)) {
            return;
        }
        markNotApplicable();
    }

    public void markNotApplicable() {
        this.myApplicabilityError = true;
    }

    public String getInferenceErrorMessage() {
        getSubstitutor();
        return this.myInferenceError;
    }

    public String getInferenceErrorMessageAssumeAlreadyComputed() {
        return this.myInferenceError;
    }
}
