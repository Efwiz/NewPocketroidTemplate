package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaSdkVersion;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaVersionService;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.ConstraintType;
import org.jetbrains.kotlin.com.intellij.psi.GenericsUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiOldInferenceHelper implements PsiInferenceHelper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final PsiManager myManager;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiOldInferenceHelper.class);
    public static final Pair<PsiType, ConstraintType> RAW_INFERENCE = new Pair<>(null, ConstraintType.EQUALS);
    private static final Pair<PsiType, ConstraintType> FAILED_INFERENCE = new Pair<>(PsiType.NULL, ConstraintType.EQUALS);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 23 || i == 24 || i == 29 || i == 35) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 23 || i == 24 || i == 29 || i == 35) ? 2 : 3];
        switch (i) {
            case 1:
            case 6:
            case 12:
            case 16:
            case 30:
                objArr[0] = "partialSubstitutor";
                break;
            case 2:
            case 7:
            case 13:
            case 18:
            case 39:
            case 44:
                objArr[0] = "policy";
                break;
            case 3:
            case 14:
            case 21:
                objArr[0] = "parameters";
                break;
            case 4:
            case 15:
            case 22:
                objArr[0] = "arguments";
                break;
            case 5:
            case 10:
            case 11:
            case 36:
            case 42:
            default:
                objArr[0] = "typeParameter";
                break;
            case 8:
                objArr[0] = "paramTypes";
                break;
            case 9:
                objArr[0] = "argTypes";
                break;
            case 17:
            case 38:
            case 40:
                objArr[0] = "parent";
                break;
            case 19:
            case 25:
            case 31:
                objArr[0] = "languageLevel";
                break;
            case 20:
            case 26:
            case 32:
                objArr[0] = "typeParameters";
                break;
            case 23:
            case 24:
            case 29:
            case 35:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/PsiOldInferenceHelper";
                break;
            case 27:
            case 33:
                objArr[0] = "leftTypes";
                break;
            case 28:
            case 34:
                objArr[0] = "rightTypes";
                break;
            case 37:
            case 43:
                objArr[0] = "substitutor";
                break;
            case 41:
                objArr[0] = "methodCall";
                break;
        }
        if (i == 23 || i == 24 || i == 29 || i == 35) {
            objArr[1] = "inferTypeArguments";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/PsiOldInferenceHelper";
        }
        switch (i) {
            case 10:
                objArr[2] = "getFailedInferenceConstraint";
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[2] = "inferTypeForMethodTypeParameter";
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 25:
            case 26:
            case 27:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
                objArr[2] = "inferTypeArguments";
                break;
            case 23:
            case 24:
            case 29:
            case 35:
                break;
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                objArr[2] = "inferMethodTypeParameterFromParent";
                break;
            default:
                objArr[2] = "inferTypeForMethodTypeParameterInner";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 23 && i != 24 && i != 29 && i != 35) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiOldInferenceHelper(PsiManager psiManager) {
        this.myManager = psiManager;
    }

    private Pair<PsiType, ConstraintType> inferTypeForMethodTypeParameterInner(PsiTypeParameter psiTypeParameter, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(0);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(2);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(3);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(4);
        }
        PsiType[] psiTypeArrCreateArray = PsiType.createArray(psiExpressionArr.length);
        PsiType[] psiTypeArrCreateArray2 = PsiType.createArray(psiExpressionArr.length);
        if (psiParameterArr.length > 0) {
            for (int i = 0; i < psiTypeArrCreateArray2.length; i++) {
                PsiExpression psiExpression = psiExpressionArr[i];
                if (psiExpression != null && (!(psiExpression instanceof PsiMethodCallExpression) || !PsiResolveHelper.ourGuard.currentStack().contains(psiExpression))) {
                    psiTypeArrCreateArray2[i] = psiExpression.getType();
                    PsiParameter psiParameter = psiParameterArr[Math.min(i, psiParameterArr.length - 1)];
                    if (i >= psiParameterArr.length && !psiParameter.getMyVarArgs()) {
                        break;
                    }
                    PsiType type = psiParameter.getType();
                    psiTypeArrCreateArray[i] = type;
                    if (type instanceof PsiEllipsisType) {
                        psiTypeArrCreateArray[i] = ((PsiEllipsisType) type).getComponentType();
                        if (psiExpressionArr.length == psiParameterArr.length) {
                            PsiType psiType = psiTypeArrCreateArray2[i];
                            if ((psiType instanceof PsiArrayType) && !(((PsiArrayType) psiType).getComponentType() instanceof PsiPrimitiveType)) {
                                psiTypeArrCreateArray2[i] = ((PsiArrayType) psiTypeArrCreateArray2[i]).getComponentType();
                            }
                        }
                    }
                }
            }
        }
        return inferTypeForMethodTypeParameterInner(psiTypeParameter, psiTypeArrCreateArray, psiTypeArrCreateArray2, psiSubstitutor, psiElement, parameterTypeInferencePolicy);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private Pair<PsiType, ConstraintType> inferTypeForMethodTypeParameterInner(PsiTypeParameter psiTypeParameter, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy) {
        PsiWildcardType psiWildcardType;
        Pair<PsiType, ConstraintType> pair;
        Pair<PsiType, ConstraintType> pairInferMethodTypeParameterFromParent;
        PsiType componentType;
        int i;
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(5);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(6);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(7);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(8);
        }
        if (psiTypeArr2 == null) {
            $$$reportNull$$$0(9);
        }
        PsiType leastUpperBound = PsiType.NULL;
        PsiType psiType = PsiType.NULL;
        if (psiTypeArr.length > 0) {
            PsiType psiType2 = leastUpperBound;
            PsiType psiType3 = psiType;
            int i2 = 0;
            PsiWildcardType psiWildcardType2 = null;
            pair = null;
            while (i2 < psiTypeArr2.length) {
                PsiType componentType2 = psiTypeArr2[i2];
                if (componentType2 != null) {
                    if (i2 >= psiTypeArr.length || (componentType = psiTypeArr[i2]) == null) {
                        break;
                    }
                    if (componentType instanceof PsiEllipsisType) {
                        componentType = ((PsiEllipsisType) componentType).getComponentType();
                        if (psiTypeArr2.length == psiTypeArr.length && (componentType2 instanceof PsiArrayType)) {
                            PsiArrayType psiArrayType = (PsiArrayType) componentType2;
                            if (!(psiArrayType.getComponentType() instanceof PsiPrimitiveType)) {
                                componentType2 = psiArrayType.getComponentType();
                            }
                        }
                    }
                    i = i2;
                    Pair<PsiType, ConstraintType> substitutionForTypeParameterConstraint = getSubstitutionForTypeParameterConstraint(psiTypeParameter, componentType, componentType2, true, PsiUtil.getLanguageLevel(psiTypeParameter));
                    if (substitutionForTypeParameterConstraint == null) {
                        continue;
                    } else {
                        if (substitutionForTypeParameterConstraint == FAILED_INFERENCE) {
                            return getFailedInferenceConstraint(psiTypeParameter);
                        }
                        ConstraintType second = substitutionForTypeParameterConstraint.getSecond();
                        PsiType first = substitutionForTypeParameterConstraint.getFirst();
                        if (first == null) {
                            pair = RAW_INFERENCE;
                        } else {
                            int i3 = AnonymousClass1.$SwitchMap$com$intellij$psi$ConstraintType[second.ordinal()];
                            if (i3 != 1) {
                                if (i3 == 2) {
                                    if (PsiType.NULL.equals(psiType2)) {
                                        psiType2 = first;
                                    } else if (psiType2.equals(first)) {
                                        continue;
                                    } else {
                                        PsiType leastUpperBound2 = GenericsUtil.getLeastUpperBound(psiType2, first, this.myManager);
                                        if (leastUpperBound2 == null) {
                                            return getFailedInferenceConstraint(psiTypeParameter);
                                        }
                                        psiType2 = leastUpperBound2;
                                    }
                                } else if (i3 == 3 && (PsiType.NULL.equals(psiType3) || TypeConversionUtil.isAssignable(psiType3, first))) {
                                    psiType3 = first;
                                }
                            } else {
                                if (!(first instanceof PsiWildcardType)) {
                                    return substitutionForTypeParameterConstraint;
                                }
                                if (psiWildcardType2 != null) {
                                    return getFailedInferenceConstraint(psiTypeParameter);
                                }
                                psiWildcardType2 = (PsiWildcardType) first;
                            }
                        }
                    }
                } else {
                    i = i2;
                }
                i2 = i + 1;
            }
            leastUpperBound = psiType2;
            psiType = psiType3;
            psiWildcardType = psiWildcardType2;
        } else {
            psiWildcardType = null;
            pair = null;
        }
        if (psiWildcardType != null) {
            if (leastUpperBound == PsiType.NULL) {
                return (psiType == PsiType.NULL || psiType.isAssignableFrom(psiWildcardType)) ? new Pair<>(psiWildcardType, ConstraintType.EQUALS) : getFailedInferenceConstraint(psiTypeParameter);
            }
            if (!psiWildcardType.isAssignableFrom(leastUpperBound)) {
                return getFailedInferenceConstraint(psiTypeParameter);
            }
            if (psiWildcardType.isSuper()) {
                return new Pair<>(psiWildcardType, ConstraintType.SUPERTYPE);
            }
            leastUpperBound = GenericsUtil.getLeastUpperBound(leastUpperBound, psiWildcardType, this.myManager);
        }
        if (pair != null) {
            return pair;
        }
        if (leastUpperBound != PsiType.NULL) {
            return Pair.create(leastUpperBound, ConstraintType.EQUALS);
        }
        if (psiElement != null && (pairInferMethodTypeParameterFromParent = inferMethodTypeParameterFromParent(psiTypeParameter, psiSubstitutor, psiElement, parameterTypeInferencePolicy)) != null) {
            return (pairInferMethodTypeParameterFromParent.getSecond() == ConstraintType.SUBTYPE && psiType != PsiType.NULL) ? Pair.create(psiType, ConstraintType.SUBTYPE) : pairInferMethodTypeParameterFromParent;
        }
        if (psiType != PsiType.NULL) {
            return Pair.create(psiType, ConstraintType.SUBTYPE);
        }
        return null;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.PsiOldInferenceHelper$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$ConstraintType;

        static {
            int[] iArr = new int[ConstraintType.values().length];
            $SwitchMap$com$intellij$psi$ConstraintType = iArr;
            try {
                iArr[ConstraintType.EQUALS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$ConstraintType[ConstraintType.SUPERTYPE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$ConstraintType[ConstraintType.SUBTYPE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static Pair<PsiType, ConstraintType> getFailedInferenceConstraint(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(10);
        }
        return new Pair<>(JavaPsiFacade.getElementFactory(psiTypeParameter.getProject()).createType(psiTypeParameter), ConstraintType.EQUALS);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper
    public PsiType inferTypeForMethodTypeParameter(PsiTypeParameter psiTypeParameter, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(11);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(12);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(13);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(14);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(15);
        }
        Pair<PsiType, ConstraintType> pairInferTypeForMethodTypeParameterInner = inferTypeForMethodTypeParameterInner(psiTypeParameter, psiParameterArr, psiExpressionArr, psiSubstitutor, psiElement, parameterTypeInferencePolicy);
        if (pairInferTypeForMethodTypeParameterInner == null) {
            return PsiType.NULL;
        }
        return pairInferTypeForMethodTypeParameterInner.getFirst();
    }

    /* JADX WARN: Code duplicated, block: B:64:0x0111  */
    /* JADX WARN: Code duplicated, block: B:67:0x0120  */
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper
    public PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, MethodCandidateInfo methodCandidateInfo, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy, LanguageLevel languageLevel) {
        Pair<PsiType, ConstraintType> pairInferMethodTypeParameterFromParent;
        int i;
        int i2;
        int i3;
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(16);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(17);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(18);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(19);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(20);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(21);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(22);
        }
        PsiType[] psiTypeArrCreateArray = PsiType.createArray(psiTypeParameterArr.length);
        Pair<PsiType, ConstraintType>[] pairArr = new Pair[psiTypeParameterArr.length];
        PsiSubstitutor psiSubstitutorPut = psiSubstitutor;
        int i4 = 0;
        while (i4 < psiTypeParameterArr.length) {
            if (psiTypeArrCreateArray[i4] != null) {
                i2 = i4;
            } else {
                i2 = i4;
                Pair<PsiType, ConstraintType> pairInferTypeForMethodTypeParameterInner = inferTypeForMethodTypeParameterInner(psiTypeParameterArr[i4], psiParameterArr, psiExpressionArr, psiSubstitutorPut, (PsiElement) null, parameterTypeInferencePolicy);
                pairArr[i2] = pairInferTypeForMethodTypeParameterInner;
                if (pairInferTypeForMethodTypeParameterInner != null && pairInferTypeForMethodTypeParameterInner.getSecond() != ConstraintType.SUBTYPE) {
                    PsiType first = pairInferTypeForMethodTypeParameterInner.getFirst();
                    psiTypeArrCreateArray[i2] = first;
                    if (first != null && languageLevel.isAtLeast(LanguageLevel.JDK_1_8)) {
                        i3 = -1;
                        psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameterArr[i2], psiTypeArrCreateArray[i2]);
                    }
                }
                i4 = i3 + 1;
            }
            i3 = i2;
            i4 = i3 + 1;
        }
        for (int i5 = 0; i5 < psiTypeParameterArr.length; i5++) {
            PsiTypeParameter psiTypeParameter = psiTypeParameterArr[i5];
            if (psiTypeArrCreateArray[i5] == null) {
                PsiType psiType = PsiType.NULL;
                int i6 = 0;
                while (i6 < psiTypeParameterArr.length) {
                    if (i5 != i6) {
                        PsiTypeParameter psiTypeParameter2 = psiTypeParameterArr[i6];
                        PsiType psiType2 = psiTypeArrCreateArray[i6];
                        if (psiType2 == null) {
                            i = i6;
                        } else {
                            PsiClassType[] extendsListTypes = psiTypeParameter2.getExtendsListTypes();
                            int length = extendsListTypes.length;
                            PsiType leastUpperBound = psiType;
                            int i7 = 0;
                            while (i7 < length) {
                                int i8 = i7;
                                PsiType psiType3 = leastUpperBound;
                                int i9 = length;
                                PsiClassType[] psiClassTypeArr = extendsListTypes;
                                int i10 = i6;
                                Pair<PsiType, ConstraintType> substitutionForTypeParameterConstraint = getSubstitutionForTypeParameterConstraint(psiTypeParameter, psiSubstitutorPut.substitute(extendsListTypes[i7]), psiType2, true, languageLevel);
                                if (substitutionForTypeParameterConstraint == null) {
                                    leastUpperBound = psiType3;
                                } else {
                                    PsiType first2 = substitutionForTypeParameterConstraint.getFirst();
                                    ConstraintType second = substitutionForTypeParameterConstraint.getSecond();
                                    if (second == ConstraintType.EQUALS) {
                                        if (first2 == null) {
                                            pairArr[i5] = FAILED_INFERENCE;
                                        }
                                        psiType = first2;
                                        break;
                                    }
                                    if (second != ConstraintType.SUPERTYPE || JavaVersionService.getInstance().isAtLeast(psiElement, JavaSdkVersion.JDK_1_7)) {
                                        leastUpperBound = psiType3;
                                    } else {
                                        leastUpperBound = PsiType.NULL.equals(psiType3) ? first2 : GenericsUtil.getLeastUpperBound(psiType3, first2, this.myManager);
                                    }
                                }
                                i7 = i8 + 1;
                                length = i9;
                                i6 = i10;
                                extendsListTypes = psiClassTypeArr;
                            }
                            i = i6;
                            psiType = leastUpperBound;
                        }
                    } else {
                        i = i6;
                    }
                    i6 = i + 1;
                }
                if (psiType != PsiType.NULL) {
                    psiTypeArrCreateArray[i5] = psiType;
                }
            }
        }
        for (int i11 = 0; i11 < psiTypeParameterArr.length; i11++) {
            PsiTypeParameter psiTypeParameter3 = psiTypeParameterArr[i11];
            PsiType psiType4 = psiTypeArrCreateArray[i11];
            if (psiType4 != PsiType.NULL) {
                psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter3, psiType4);
            }
        }
        for (int i12 = 0; i12 < psiTypeParameterArr.length; i12++) {
            PsiTypeParameter psiTypeParameter4 = psiTypeParameterArr[i12];
            PsiType first3 = psiTypeArrCreateArray[i12];
            if (first3 == null) {
                Pair<PsiType, ConstraintType> pairInferMethodTypeParameterFromParent2 = pairArr[i12];
                if (pairInferMethodTypeParameterFromParent2 == null) {
                    pairInferMethodTypeParameterFromParent2 = inferMethodTypeParameterFromParent(psiTypeParameter4, psiSubstitutorPut, psiElement, parameterTypeInferencePolicy);
                } else if (pairInferMethodTypeParameterFromParent2.getSecond() == ConstraintType.SUBTYPE && (pairInferMethodTypeParameterFromParent = inferMethodTypeParameterFromParent(psiTypeParameter4, psiSubstitutorPut, psiElement, parameterTypeInferencePolicy)) != null && (pairInferMethodTypeParameterFromParent.getSecond() == ConstraintType.EQUALS || pairInferMethodTypeParameterFromParent.getSecond() == ConstraintType.SUPERTYPE || compareSubtypes(pairInferMethodTypeParameterFromParent2.getFirst(), pairInferMethodTypeParameterFromParent.getFirst()))) {
                    pairInferMethodTypeParameterFromParent2 = pairInferMethodTypeParameterFromParent;
                }
                if (pairInferMethodTypeParameterFromParent2 != null) {
                    first3 = pairInferMethodTypeParameterFromParent2.getFirst();
                }
                if (first3 == null) {
                    PsiSubstitutor psiSubstitutorCreateRawSubstitutor = JavaPsiFacade.getElementFactory(this.myManager.getProject()).createRawSubstitutor(psiSubstitutorPut, psiTypeParameterArr);
                    if (psiSubstitutorCreateRawSubstitutor == null) {
                        $$$reportNull$$$0(23);
                    }
                    return psiSubstitutorCreateRawSubstitutor;
                }
                if (first3 != PsiType.NULL) {
                    psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter4, first3);
                }
            }
        }
        if (psiSubstitutorPut == null) {
            $$$reportNull$$$0(24);
        }
        return psiSubstitutorPut;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper
    public PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(25);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(26);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(27);
        }
        if (psiTypeArr2 == null) {
            $$$reportNull$$$0(28);
        }
        PsiSubstitutor psiSubstitutorInferTypeArguments = inferTypeArguments(psiTypeParameterArr, psiTypeArr, psiTypeArr2, PsiSubstitutor.EMPTY, languageLevel);
        if (psiSubstitutorInferTypeArguments == null) {
            $$$reportNull$$$0(29);
        }
        return psiSubstitutorInferTypeArguments;
    }

    private static boolean compareSubtypes(PsiType psiType, PsiType psiType2) {
        return (psiType == null || psiType2 == null || !TypeConversionUtil.isAssignable(psiType, psiType2)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper
    public PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, PsiSubstitutor psiSubstitutor, LanguageLevel languageLevel) {
        PsiTypeParameter psiTypeParameter;
        PsiType psiTypeSubstitute;
        PsiType psiType;
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(30);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(31);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(32);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(33);
        }
        if (psiTypeArr2 == null) {
            $$$reportNull$$$0(34);
        }
        if (psiTypeArr.length != psiTypeArr2.length) {
            throw new IllegalArgumentException("Types must be of the same length");
        }
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        for (PsiTypeParameter psiTypeParameter2 : psiTypeParameterArr) {
            PsiType psiType2 = PsiType.NULL;
            PsiType psiType3 = PsiType.NULL;
            int i = 0;
            while (true) {
                if (i >= psiTypeArr.length) {
                    psiType = psiType3;
                    break;
                }
                int i2 = i;
                psiType = psiType3;
                Pair<PsiType, ConstraintType> substitutionForTypeParameterConstraint = getSubstitutionForTypeParameterConstraint(psiTypeParameter2, psiTypeArr[i], psiTypeArr2[i], true, languageLevel);
                if (substitutionForTypeParameterConstraint != null) {
                    ConstraintType second = substitutionForTypeParameterConstraint.getSecond();
                    PsiType first = substitutionForTypeParameterConstraint.getFirst();
                    if (second == ConstraintType.EQUALS) {
                        psiType2 = first;
                        break;
                    }
                    if (second == ConstraintType.SUBTYPE) {
                        if (!PsiType.NULL.equals(psiType2)) {
                            first = GenericsUtil.getLeastUpperBound(psiType2, first, this.myManager);
                        }
                        psiType2 = first;
                        psiType3 = psiType;
                    } else {
                        if (!PsiType.NULL.equals(psiType)) {
                            first = GenericsUtil.getLeastUpperBound(psiType, first, this.myManager);
                        }
                        psiType3 = first;
                    }
                } else {
                    psiType3 = psiType;
                }
                i = i2 + 1;
            }
            PsiType psiType4 = PsiType.NULL.equals(psiType2) ? psiType : psiType2;
            if (psiType4 != PsiType.NULL) {
                psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter2, psiType4);
            }
        }
        for (int i3 = 0; i3 < psiTypeParameterArr.length; i3++) {
            PsiTypeParameter psiTypeParameter3 = psiTypeParameterArr[i3];
            if (!psiSubstitutorPut.getSubstitutionMap().containsKey(psiTypeParameter3)) {
                PsiType psiType5 = PsiType.NULL;
                for (int i4 = 0; i4 < psiTypeParameterArr.length; i4++) {
                    if (i3 != i4 && (psiTypeSubstitute = psiSubstitutorPut.substitute((psiTypeParameter = psiTypeParameterArr[i4]))) != null) {
                        PsiClassType[] extendsListTypes = psiTypeParameter.getExtendsListTypes();
                        int length = extendsListTypes.length;
                        PsiType leastUpperBound = psiType5;
                        int i5 = 0;
                        while (i5 < length) {
                            int i6 = i5;
                            Pair<PsiType, ConstraintType> substitutionForTypeParameterConstraint2 = getSubstitutionForTypeParameterConstraint(psiTypeParameter3, psiSubstitutorPut.substitute(extendsListTypes[i5]), psiTypeSubstitute, true, languageLevel);
                            if (substitutionForTypeParameterConstraint2 != null) {
                                PsiType first2 = substitutionForTypeParameterConstraint2.getFirst();
                                ConstraintType second2 = substitutionForTypeParameterConstraint2.getSecond();
                                if (second2 == ConstraintType.EQUALS) {
                                    psiType5 = first2;
                                    break;
                                }
                                if (second2 == ConstraintType.SUPERTYPE) {
                                    leastUpperBound = PsiType.NULL.equals(leastUpperBound) ? first2 : GenericsUtil.getLeastUpperBound(leastUpperBound, first2, this.myManager);
                                }
                            }
                            i5 = i6 + 1;
                        }
                        psiType5 = leastUpperBound;
                    }
                }
                if (psiType5 != PsiType.NULL) {
                    psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter3, psiType5);
                }
            }
        }
        if (psiSubstitutorPut == null) {
            $$$reportNull$$$0(35);
        }
        return psiSubstitutorPut;
    }

    private static Pair<PsiType, ConstraintType> processArgType(PsiType psiType, ConstraintType constraintType, boolean z) {
        if ((psiType instanceof PsiWildcardType) && !z) {
            return FAILED_INFERENCE;
        }
        if (psiType != PsiType.NULL) {
            return Pair.create(psiType, constraintType);
        }
        return null;
    }

    private Pair<PsiType, ConstraintType> inferMethodTypeParameterFromParent(PsiTypeParameter psiTypeParameter, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(36);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(37);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(38);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(39);
        }
        if (!(psiTypeParameter.getOwner() instanceof PsiMethod) || !(psiElement instanceof PsiCallExpression)) {
            return null;
        }
        PsiCallExpression psiCallExpression = (PsiCallExpression) psiElement;
        return inferMethodTypeParameterFromParent(PsiUtil.skipParenthesizedExprUp(psiCallExpression.getLightParent()), psiCallExpression, psiTypeParameter, psiSubstitutor, parameterTypeInferencePolicy);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper
    public PsiType getSubstitutionForTypeParameter(PsiTypeParameter psiTypeParameter, PsiType psiType, PsiType psiType2, boolean z, LanguageLevel languageLevel) {
        Pair<PsiType, ConstraintType> substitutionForTypeParameterConstraint = getSubstitutionForTypeParameterConstraint(psiTypeParameter, psiType, psiType2, z, languageLevel);
        return substitutionForTypeParameterConstraint == null ? PsiType.NULL : substitutionForTypeParameterConstraint.getFirst();
    }

    public Pair<PsiType, ConstraintType> getSubstitutionForTypeParameterConstraint(PsiTypeParameter psiTypeParameter, PsiType psiType, PsiType psiType2, boolean z, LanguageLevel languageLevel) {
        PsiType psiTypeCreateType = psiType;
        PsiType psiTypeCreateType2 = psiType2;
        if ((psiTypeCreateType instanceof PsiArrayType) && (psiTypeCreateType2 instanceof PsiArrayType)) {
            return getSubstitutionForTypeParameterConstraint(psiTypeParameter, ((PsiArrayType) psiTypeCreateType).getComponentType(), ((PsiArrayType) psiTypeCreateType2).getComponentType(), z, languageLevel);
        }
        if (!(psiTypeCreateType instanceof PsiClassType)) {
            return null;
        }
        if ((psiTypeCreateType2 instanceof PsiPrimitiveType) && ((!JavaVersionService.getInstance().isAtLeast(psiTypeParameter, JavaSdkVersion.JDK_1_7) && !z) || (psiTypeCreateType2 = ((PsiPrimitiveType) psiTypeCreateType2).getBoxedType(psiTypeParameter)) == null)) {
            return null;
        }
        if (psiTypeCreateType2 instanceof PsiCapturedWildcardType) {
            psiTypeCreateType2 = ((PsiCapturedWildcardType) psiTypeCreateType2).getUpperBound();
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = ((PsiClassType) psiTypeCreateType).resolveGenerics();
        PsiClass psiClass = (PsiClass) classResolveResultResolveGenerics.getElement();
        if (psiTypeParameter == psiClass) {
            PsiClass psiClassResolveClassInType = PsiUtil.resolveClassInType(psiTypeCreateType2);
            if (psiTypeCreateType2 == null || (psiTypeCreateType2.getDeepComponentType() instanceof PsiPrimitiveType) || (psiTypeCreateType2 instanceof PsiIntersectionType) || (psiClassResolveClassInType != null && (z || !CommonClassNames.JAVA_LANG_OBJECT.equals(psiClassResolveClassInType.getQualifiedName()) || (psiTypeCreateType2 instanceof PsiArrayType)))) {
                return Pair.create(intersectAllExtends(psiTypeParameter, psiTypeCreateType2), ConstraintType.SUPERTYPE);
            }
            if (psiClassResolveClassInType == null && (psiTypeCreateType2 instanceof PsiClassType)) {
                return Pair.create(psiTypeCreateType2, ConstraintType.EQUALS);
            }
            return null;
        }
        if (psiClass == null || !(psiTypeCreateType2 instanceof PsiClassType)) {
            return null;
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics2 = ((PsiClassType) psiTypeCreateType2).resolveGenerics();
        PsiClass psiClass2 = (PsiClass) classResolveResultResolveGenerics2.getElement();
        if (psiClass2 == null) {
            return null;
        }
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(this.myManager.getProject());
        PsiClassType psiClassTypeCreateType = elementFactory.createType(psiTypeParameter);
        if (z) {
            PsiSubstitutor classSubstitutor = TypeConversionUtil.getClassSubstitutor(psiClass, psiClass2, classResolveResultResolveGenerics2.getSubstitutor());
            if (classSubstitutor == null) {
                return null;
            }
            psiTypeCreateType2 = elementFactory.createType(psiClass, classSubstitutor, languageLevel);
        } else {
            PsiSubstitutor classSubstitutor2 = TypeConversionUtil.getClassSubstitutor(psiClass2, psiClass, classResolveResultResolveGenerics.getSubstitutor());
            if (classSubstitutor2 == null) {
                return null;
            }
            psiTypeCreateType = elementFactory.createType(psiClass2, classSubstitutor2, languageLevel);
        }
        return getSubstitutionForTypeParameterInner(psiTypeCreateType, psiTypeCreateType2, psiClassTypeCreateType, ConstraintType.SUPERTYPE, 0);
    }

    private static PsiType intersectAllExtends(PsiTypeParameter psiTypeParameter, PsiType psiType) {
        if (psiType == null) {
            return null;
        }
        PsiClassType[] superTypes = psiTypeParameter.getSuperTypes();
        PsiType[] psiTypeArrCreateArray = PsiType.createArray(superTypes.length);
        for (int i = 0; i < superTypes.length; i++) {
            psiTypeArrCreateArray[i] = TypeConversionUtil.erasure(superTypes[i]);
        }
        return PsiIntersectionType.createIntersection((PsiType[]) ArrayUtil.append(psiTypeArrCreateArray, psiType, (Class<PsiType>) PsiType.class));
    }

    private Pair<PsiType, ConstraintType> getSubstitutionForTypeParameterInner(PsiType psiType, PsiType psiType2, PsiType psiType3, ConstraintType constraintType, int i) {
        char c;
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics;
        PsiClass element;
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics2;
        PsiClass element2;
        Pair<PsiType, ConstraintType> substitutionForTypeParameterInner;
        Pair<PsiType, ConstraintType> substitutionForTypeParameterInner2;
        Pair<PsiType, ConstraintType> substitutionForTypeParameterInner3;
        PsiType wildcard = psiType2;
        if (psiType3.equals(psiType)) {
            return processArgType(wildcard, constraintType, i < 2);
        }
        if ((wildcard instanceof PsiCapturedWildcardType) && (i < 2 || constraintType != ConstraintType.EQUALS || (psiType instanceof PsiWildcardType))) {
            wildcard = ((PsiCapturedWildcardType) wildcard).getWildcard();
        }
        PsiType psiType4 = wildcard;
        if (psiType instanceof PsiWildcardType) {
            PsiWildcardType psiWildcardType = (PsiWildcardType) psiType;
            PsiType bound = psiWildcardType.getBound();
            if (bound == null) {
                return null;
            }
            ConstraintType constraintType2 = psiWildcardType.isExtends() ? ConstraintType.SUPERTYPE : ConstraintType.SUBTYPE;
            if (psiType4 instanceof PsiWildcardType) {
                PsiWildcardType psiWildcardType2 = (PsiWildcardType) psiType4;
                if (psiWildcardType2.isExtends() == psiWildcardType.isExtends() && psiWildcardType2.isBounded() == psiWildcardType.isBounded() && (substitutionForTypeParameterInner3 = getSubstitutionForTypeParameterInner(bound, psiWildcardType2.getBound(), psiType3, constraintType2, i)) != null) {
                    return substitutionForTypeParameterInner3;
                }
            } else if (psiType3.equals(bound)) {
                Pair<PsiType, ConstraintType> substitutionForTypeParameterInner4 = getSubstitutionForTypeParameterInner(bound, psiType4, psiType3, constraintType2, i);
                if (substitutionForTypeParameterInner4 != null) {
                    return substitutionForTypeParameterInner4;
                }
            } else if ((bound instanceof PsiArrayType) && (psiType4 instanceof PsiArrayType)) {
                Pair<PsiType, ConstraintType> substitutionForTypeParameterInner5 = getSubstitutionForTypeParameterInner(((PsiArrayType) bound).getComponentType(), ((PsiArrayType) psiType4).getComponentType(), psiType3, constraintType2, i);
                if (substitutionForTypeParameterInner5 != null) {
                    return substitutionForTypeParameterInner5;
                }
            } else if ((bound instanceof PsiClassType) && (psiType4 instanceof PsiClassType) && (element = (classResolveResultResolveGenerics = ((PsiClassType) bound).resolveGenerics()).getElement()) != null && (element2 = (classResolveResultResolveGenerics2 = ((PsiClassType) psiType4).resolveGenerics()).getElement()) != null) {
                if (psiWildcardType.isExtends()) {
                    PsiSubstitutor classSubstitutor = TypeConversionUtil.getClassSubstitutor(element, element2, classResolveResultResolveGenerics2.getSubstitutor());
                    if (classSubstitutor != null) {
                        for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(element)) {
                            PsiType psiTypeSubstitute = classSubstitutor.substitute(psiTypeParameter);
                            if (psiTypeSubstitute != null && (substitutionForTypeParameterInner2 = getSubstitutionForTypeParameterInner(classResolveResultResolveGenerics.getSubstitutor().substitute(psiTypeParameter), psiTypeSubstitute, psiType3, ConstraintType.EQUALS, i + 1)) != null) {
                                return substitutionForTypeParameterInner2;
                            }
                            classSubstitutor = classSubstitutor;
                        }
                    }
                } else {
                    PsiSubstitutor classSubstitutor2 = TypeConversionUtil.getClassSubstitutor(element2, element, classResolveResultResolveGenerics.getSubstitutor());
                    if (classSubstitutor2 != null) {
                        for (PsiTypeParameter psiTypeParameter2 : PsiUtil.typeParametersIterable(element2)) {
                            PsiType psiTypeSubstitute2 = classResolveResultResolveGenerics2.getSubstitutor().substitute(psiTypeParameter2);
                            if (psiTypeSubstitute2 != null && (substitutionForTypeParameterInner = getSubstitutionForTypeParameterInner(classSubstitutor2.substitute(psiTypeParameter2), psiTypeSubstitute2, psiType3, ConstraintType.EQUALS, i + 1)) != null && substitutionForTypeParameterInner != FAILED_INFERENCE) {
                                return substitutionForTypeParameterInner;
                            }
                        }
                    }
                }
            }
        }
        if ((psiType instanceof PsiArrayType) && (psiType4 instanceof PsiArrayType)) {
            return getSubstitutionForTypeParameterInner(((PsiArrayType) psiType).getComponentType(), ((PsiArrayType) psiType4).getComponentType(), psiType3, constraintType, i);
        }
        if (!(psiType instanceof PsiClassType) || !(psiType4 instanceof PsiClassType)) {
            return null;
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics3 = ((PsiClassType) psiType).resolveGenerics();
        PsiClass element3 = classResolveResultResolveGenerics3.getElement();
        if (element3 == null) {
            return null;
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics4 = ((PsiClassType) psiType4).resolveGenerics();
        if (!element3.isEquivalentTo(classResolveResultResolveGenerics4.getElement())) {
            return null;
        }
        PsiPrimitiveType psiPrimitiveType = PsiType.NULL;
        PsiPrimitiveType psiPrimitiveType2 = PsiType.NULL;
        PsiType psiType5 = psiPrimitiveType2;
        Pair<PsiType, ConstraintType> pair = null;
        PsiType psiType6 = psiPrimitiveType;
        for (PsiTypeParameter psiTypeParameter3 : PsiUtil.typeParametersIterable(element3)) {
            PsiType psiTypeSubstitute3 = classResolveResultResolveGenerics3.getSubstitutor().substitute(psiTypeParameter3);
            PsiType psiTypeSubstituteWithBoundsPromotion = classResolveResultResolveGenerics4.getSubstitutor().substituteWithBoundsPromotion(psiTypeParameter3);
            if (pair != null) {
                boolean z = false;
                for (PsiTypeParameter psiTypeParameter4 : PsiUtil.typeParametersIterable(element3)) {
                    if (psiTypeParameter4 != psiTypeParameter3 && psiTypeSubstitute3 != null && classResolveResultResolveGenerics4.getSubstitutor().substituteWithBoundsPromotion(psiTypeParameter4) == psiTypeSubstituteWithBoundsPromotion && psiTypeSubstitute3.equals(classResolveResultResolveGenerics3.getSubstitutor().substitute(psiTypeParameter4))) {
                        z = true;
                    }
                }
                if (z) {
                }
            }
            PsiType psiType7 = psiType5;
            Pair<PsiType, ConstraintType> substitutionForTypeParameterInner6 = getSubstitutionForTypeParameterInner(psiTypeSubstitute3, psiTypeSubstituteWithBoundsPromotion, psiType3, ConstraintType.EQUALS, i + 1);
            if (substitutionForTypeParameterInner6 != null) {
                PsiType first = substitutionForTypeParameterInner6.getFirst();
                int i2 = AnonymousClass1.$SwitchMap$com$intellij$psi$ConstraintType[substitutionForTypeParameterInner6.getSecond().ordinal()];
                if (i2 != 1) {
                    c = 2;
                    if (i2 == 2) {
                        if (!PsiType.NULL.equals(psiType6)) {
                            if (!psiType6.equals(first)) {
                                first = GenericsUtil.getLeastUpperBound(psiType6, first, this.myManager);
                                if (first == null) {
                                    return FAILED_INFERENCE;
                                }
                            }
                        }
                        pair = substitutionForTypeParameterInner6;
                        psiType6 = first;
                        psiType5 = psiType7;
                    } else if (i2 == 3) {
                        if (PsiType.NULL.equals(psiType7) || TypeConversionUtil.isAssignable(psiType7, first)) {
                            pair = substitutionForTypeParameterInner6;
                            psiType5 = first;
                        }
                    }
                } else {
                    c = 2;
                    if (!(first instanceof PsiWildcardType)) {
                        return substitutionForTypeParameterInner6;
                    }
                    if (pair != null) {
                        return FAILED_INFERENCE;
                    }
                }
                pair = substitutionForTypeParameterInner6;
                psiType5 = psiType7;
            } else {
                c = 2;
            }
            psiType5 = psiType7;
        }
        PsiType psiType8 = psiType5;
        if (psiType6 != PsiType.NULL) {
            return Pair.create(psiType6, ConstraintType.SUPERTYPE);
        }
        return psiType8 != PsiType.NULL ? Pair.create(psiType8, ConstraintType.SUBTYPE) : pair;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0056  */
    private Pair<PsiType, ConstraintType> inferMethodTypeParameterFromParent(PsiElement psiElement, PsiExpression psiExpression, PsiTypeParameter psiTypeParameter, PsiSubstitutor psiSubstitutor, ParameterTypeInferencePolicy parameterTypeInferencePolicy) {
        Pair<PsiType, ConstraintType> substitutionForTypeParameterConstraint;
        PsiType psiType;
        PsiType first;
        if (psiElement == null) {
            $$$reportNull$$$0(40);
        }
        if (psiExpression == null) {
            $$$reportNull$$$0(41);
        }
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(42);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(43);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(44);
        }
        PsiType expectedTypeByParent = PsiTypesUtil.getExpectedTypeByParent(psiExpression);
        if (expectedTypeByParent == null && (psiElement instanceof PsiExpressionList)) {
            PsiElement lightParent = psiElement.getLightParent();
            if (lightParent instanceof PsiCallExpression) {
                PsiCallExpression psiCallExpression = (PsiCallExpression) lightParent;
                if (psiElement.equals(psiCallExpression.getArgumentList())) {
                    substitutionForTypeParameterConstraint = parameterTypeInferencePolicy.inferTypeConstraintFromCallContext(psiExpression, (PsiExpressionList) psiElement, psiCallExpression, psiTypeParameter);
                } else {
                    substitutionForTypeParameterConstraint = null;
                }
            } else {
                substitutionForTypeParameterConstraint = null;
            }
        } else {
            substitutionForTypeParameterConstraint = null;
        }
        GlobalSearchScope resolveScope = psiElement.getResolveScope();
        if (substitutionForTypeParameterConstraint == null) {
            if (expectedTypeByParent == null) {
                expectedTypeByParent = psiExpression instanceof PsiCallExpression ? parameterTypeInferencePolicy.getDefaultExpectedType((PsiCallExpression) psiExpression) : null;
            }
            if (parameterTypeInferencePolicy.requestForBoxingExplicitly() && TypeConversionUtil.isPrimitiveAndNotNull(expectedTypeByParent)) {
                expectedTypeByParent = ((PsiPrimitiveType) expectedTypeByParent).getBoxedType(psiTypeParameter);
            }
            PsiType psiType2 = expectedTypeByParent;
            PsiType returnType = ((PsiMethod) psiTypeParameter.getOwner()).getReturnType();
            substitutionForTypeParameterConstraint = getSubstitutionForTypeParameterConstraint(psiTypeParameter, returnType, psiType2, false, PsiUtil.getLanguageLevel(psiElement));
            if (substitutionForTypeParameterConstraint != null && (first = substitutionForTypeParameterConstraint.getFirst()) != null && !first.equals(PsiType.NULL) && substitutionForTypeParameterConstraint.getSecond() == ConstraintType.SUPERTYPE && (first instanceof PsiIntersectionType) && !JavaVersionService.getInstance().isAtLeast(psiElement, JavaSdkVersion.JDK_1_7)) {
                for (PsiType psiType3 : ((PsiIntersectionType) first).getConjuncts()) {
                    if (!psiType3.isAssignableFrom(psiType2)) {
                        return FAILED_INFERENCE;
                    }
                }
            }
            psiType = returnType;
        } else {
            psiType = null;
        }
        PsiClassType[] superTypes = psiTypeParameter.getSuperTypes();
        PsiType[] psiTypeArrCreateArray = PsiType.createArray(superTypes.length);
        for (int i = 0; i < superTypes.length; i++) {
            PsiType psiTypeSubstitute = psiSubstitutor.substitute(superTypes[i]);
            if ((psiTypeSubstitute instanceof PsiClassType) && ((PsiClassType) psiTypeSubstitute).isRaw()) {
                psiTypeSubstitute = TypeConversionUtil.erasure(psiTypeSubstitute);
            }
            if (psiTypeSubstitute == null) {
                psiTypeSubstitute = PsiType.getJavaLangObject(this.myManager, resolveScope);
            }
            psiTypeArrCreateArray[i] = psiTypeSubstitute;
        }
        if (substitutionForTypeParameterConstraint == null) {
            if (!(psiExpression instanceof PsiCallExpression) || psiTypeArrCreateArray.length == 0) {
                return null;
            }
            return parameterTypeInferencePolicy.getInferredTypeWithNoConstraint(this.myManager, PsiIntersectionType.createIntersection(psiTypeArrCreateArray));
        }
        PsiType first2 = substitutionForTypeParameterConstraint.getFirst();
        if (first2 != null && psiTypeArrCreateArray.length > 0) {
            first2 = GenericsUtil.getGreatestLowerBound(first2, PsiIntersectionType.createIntersection(psiTypeArrCreateArray));
        }
        PsiType psiTypeAdjustInferredType = parameterTypeInferencePolicy.adjustInferredType(this.myManager, first2, substitutionForTypeParameterConstraint.getSecond());
        if ((psiType instanceof PsiClassType) && psiTypeParameter.equals(((PsiClassType) psiType).resolve())) {
            PsiClassType[] extendsListTypes = psiTypeParameter.getExtendsListTypes();
            PsiSubstitutor psiSubstitutorPut = psiSubstitutor.put(psiTypeParameter, psiTypeAdjustInferredType);
            for (PsiClassType psiClassType : extendsListTypes) {
                PsiType psiTypeSubstitute2 = psiSubstitutorPut.substitute(psiClassType);
                if (psiTypeAdjustInferredType != null && psiTypeSubstitute2 != null && !psiTypeSubstitute2.isAssignableFrom(psiTypeAdjustInferredType)) {
                    if (!psiTypeAdjustInferredType.isAssignableFrom(psiTypeSubstitute2)) {
                        break;
                    }
                    psiSubstitutorPut = psiSubstitutor.put(psiTypeParameter, psiTypeSubstitute2);
                    psiTypeAdjustInferredType = psiTypeSubstitute2;
                }
            }
        }
        return Pair.create(psiTypeAdjustInferredType, substitutionForTypeParameterConstraint.getSecond());
    }
}
