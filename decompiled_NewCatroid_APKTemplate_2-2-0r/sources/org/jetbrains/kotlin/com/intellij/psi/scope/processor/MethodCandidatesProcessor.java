package org.jetbrains.kotlin.com.intellij.psi.scope.processor;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveUtil;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.DuplicateConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.util.ImportsUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class MethodCandidatesProcessor extends MethodsProcessor {
    boolean myHasAccessibleStaticCorrectCandidate;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 13 || i == 16 || i == 17) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 13 || i == 16 || i == 17) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "container";
                break;
            case 2:
                objArr[0] = "resolvers";
                break;
            case 3:
            default:
                objArr[0] = "place";
                break;
            case 4:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 5:
            case 7:
            case 11:
                objArr[0] = "substitutor";
                break;
            case 6:
            case 8:
            case 9:
            case 10:
                objArr[0] = "method";
                break;
            case 12:
                objArr[0] = "argumentList";
                break;
            case 13:
            case 16:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/MethodCandidatesProcessor";
                break;
            case 14:
            case 15:
                objArr[0] = "candidate";
                break;
        }
        if (i == 13) {
            objArr[1] = "getExpressionTypes";
        } else if (i == 16 || i == 17) {
            objArr[1] = "getCandidates";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/MethodCandidatesProcessor";
        }
        switch (i) {
            case 4:
            case 5:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 6:
            case 7:
                objArr[2] = "addMethod";
                break;
            case 8:
                objArr[2] = "isInterfaceStaticMethodAccessibleThroughInheritance";
                break;
            case 9:
                objArr[2] = "getContainingClass";
                break;
            case 10:
            case 11:
                objArr[2] = "createCandidateInfo";
                break;
            case 12:
                objArr[2] = "getExpressionTypes";
                break;
            case 13:
            case 16:
            case 17:
                break;
            case 14:
                objArr[2] = "isAccepted";
                break;
            case 15:
                objArr[2] = "isShadowed";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13 && i != 16 && i != 17) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected boolean acceptVarargs() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected MethodCandidatesProcessor(PsiElement psiElement, PsiFile psiFile, PsiConflictResolver[] psiConflictResolverArr, List<CandidateInfo> list) {
        super(psiConflictResolverArr, list, psiElement, psiFile);
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        if (psiConflictResolverArr == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MethodCandidatesProcessor(PsiElement psiElement, PsiFile psiFile) {
        super(new PsiConflictResolver[]{DuplicateConflictResolver.INSTANCE}, new SmartList(), psiElement, psiFile);
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.ConflictFilterProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor
    public void add(PsiElement psiElement, PsiSubstitutor psiSubstitutor) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) psiElement;
            addMethod(psiMethod, psiSubstitutor, isInStaticScope() && !psiMethod.hasModifierProperty("static"));
        }
    }

    public void addMethod(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, boolean z) {
        boolean z2;
        PsiClass containingClass;
        if (psiMethod == null) {
            $$$reportNull$$$0(6);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(7);
        }
        boolean z3 = false;
        boolean z4 = JavaResolveUtil.isAccessible(psiMethod, getContainingClass(psiMethod), psiMethod.mo6095getModifierList(), this.myPlace, this.myAccessClass, this.myCurrentFileContext, this.myPlaceFile) && !isShadowed(psiMethod);
        if (isAccepted(psiMethod)) {
            if (isInterfaceStaticMethodAccessibleThroughInheritance(psiMethod) && ImportsUtil.hasStaticImportOn(this.myPlace, psiMethod, true)) {
                return;
            }
            if (z || !psiMethod.hasModifierProperty("static") || (containingClass = psiMethod.mo6092getContainingClass()) == null || !containingClass.isInterface() || (this.myAccessClass instanceof PsiTypeParameter) || containingClass.getManager().areElementsEquivalent(this.myAccessClass, containingClass)) {
                z2 = false;
            } else if (this.myAccessClass != null) {
                z2 = false;
                z = true;
            } else {
                z2 = true;
            }
            add(createCandidateInfo(psiMethod, psiSubstitutor, z, z4, false));
            if (acceptVarargs() && psiMethod.isVarArgs() && PsiUtil.isLanguageLevel8OrHigher(this.myPlace)) {
                add(createCandidateInfo(psiMethod, psiSubstitutor, z, z4, true));
            }
            boolean z5 = this.myHasAccessibleStaticCorrectCandidate;
            if (z4 && !z2) {
                z3 = true;
            }
            this.myHasAccessibleStaticCorrectCandidate = z5 | z3;
        }
    }

    private boolean isInterfaceStaticMethodAccessibleThroughInheritance(PsiMethod psiMethod) {
        PsiClass containingClass;
        if (psiMethod == null) {
            $$$reportNull$$$0(8);
        }
        return psiMethod.hasModifierProperty("static") && !(this.myCurrentFileContext instanceof PsiImportStaticStatement) && (this.myPlace instanceof PsiMethodCallExpression) && ((PsiMethodCallExpression) this.myPlace).getMethodExpression().getQualifierExpression() == null && (containingClass = psiMethod.mo6092getContainingClass()) != null && containingClass.isInterface();
    }

    protected PsiClass getContainingClass(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(9);
        }
        return psiMethod.mo6092getContainingClass();
    }

    protected MethodCandidateInfo createCandidateInfo(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, boolean z, boolean z2, boolean z3) {
        if (psiMethod == null) {
            $$$reportNull$$$0(10);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(11);
        }
        return new VarargsAwareMethodCandidateInfo(psiMethod, psiSubstitutor, z2, z, getArgumentList(), this.myCurrentFileContext, getTypeArguments(), getLanguageLevel(), z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiType[] getExpressionTypes(PsiExpressionList psiExpressionList) {
        if (psiExpressionList == null) {
            $$$reportNull$$$0(12);
        }
        PsiType[] expressionTypes = psiExpressionList.getExpressionTypes();
        if (expressionTypes == null) {
            $$$reportNull$$$0(13);
        }
        return expressionTypes;
    }

    protected boolean isAccepted(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(14);
        }
        if (!isConstructor()) {
            return !psiMethod.isConstructor() && psiMethod.getMyName().equals(getName(ResolveState.initial()));
        }
        if (!psiMethod.isConstructor()) {
            return false;
        }
        if (this.myAccessClass == null) {
            return true;
        }
        if (this.myAccessClass instanceof PsiAnonymousClass) {
            PsiClass containingClass = getContainingClass(psiMethod);
            return containingClass != null && containingClass.equals(this.myAccessClass.getSuperClass());
        }
        return this.myAccessClass.isEquivalentTo(getContainingClass(psiMethod));
    }

    private boolean isShadowed(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(15);
        }
        if (!(this.myCurrentFileContext instanceof PsiImportStaticStatement)) {
            return false;
        }
        for (CandidateInfo candidateInfo : getResults()) {
            if (candidateInfo.getElement() != psiMethod && candidateInfo.isAccessible() && !(candidateInfo.getCurrentFileResolveScope() instanceof PsiImportStaticStatement)) {
                return true;
            }
        }
        return false;
    }

    public CandidateInfo[] getCandidates() {
        JavaResolveResult[] result = getResult();
        if (result.length == 0) {
            CandidateInfo[] candidateInfoArr = CandidateInfo.EMPTY_ARRAY;
            if (candidateInfoArr == null) {
                $$$reportNull$$$0(16);
            }
            return candidateInfoArr;
        }
        CandidateInfo[] candidateInfoArr2 = (CandidateInfo[]) Arrays.copyOf(result, result.length, CandidateInfo[].class);
        if (candidateInfoArr2 == null) {
            $$$reportNull$$$0(17);
        }
        return candidateInfoArr2;
    }

    private static class VarargsAwareMethodCandidateInfo extends MethodCandidateInfo {
        private final PsiExpressionList myArgumentList;
        private PsiType[] myExpressionTypes;
        private final boolean myVarargs;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "substitutor";
            } else if (i != 2) {
                objArr[0] = "method";
            } else {
                objArr[0] = FirebaseAnalytics.Param.LEVEL;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/MethodCandidatesProcessor$VarargsAwareMethodCandidateInfo";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        VarargsAwareMethodCandidateInfo(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, boolean z, boolean z2, PsiExpressionList psiExpressionList, PsiElement psiElement, PsiType[] psiTypeArr, LanguageLevel languageLevel, boolean z3) {
            super(psiMethod, psiSubstitutor, !z, z2, psiExpressionList, psiElement, null, psiTypeArr, languageLevel);
            if (psiMethod == null) {
                $$$reportNull$$$0(0);
            }
            if (psiSubstitutor == null) {
                $$$reportNull$$$0(1);
            }
            if (languageLevel == null) {
                $$$reportNull$$$0(2);
            }
            this.myArgumentList = psiExpressionList;
            this.myVarargs = z3;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo
        public PsiType[] getArgumentTypes() {
            PsiExpressionList psiExpressionList;
            if (this.myExpressionTypes == null && (psiExpressionList = this.myArgumentList) != null) {
                PsiType[] expressionTypes = MethodCandidatesProcessor.getExpressionTypes(psiExpressionList);
                if (isOverloadCheck()) {
                    return expressionTypes;
                }
                this.myExpressionTypes = expressionTypes;
            }
            return this.myExpressionTypes;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo
        public boolean isVarargs() {
            return this.myVarargs;
        }
    }
}
