package org.jetbrains.kotlin.com.intellij.psi.infos;

import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaSdkVersion;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaVersionService;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;

/* JADX INFO: loaded from: classes3.dex */
public class CandidateInfo implements JavaResolveResult {
    public static final CandidateInfo[] EMPTY_ARRAY = new CandidateInfo[0];
    public static final JavaResolveResult[] RESOLVE_RESULT_FOR_PACKAGE_PREFIX_PACKAGE = {new CandidateInfo(PsiUtilCore.NULL_PSI_ELEMENT, PsiSubstitutor.EMPTY, Boolean.TRUE, false, null, null, null, true)};
    private final PsiClass myAccessClass;
    private Boolean myAccessible;
    private final PsiElement myCandidate;
    private final PsiElement myCurrentFileResolveContext;
    private final boolean myPackagePrefixPackageReference;
    private final PsiElement myPlace;
    private final boolean myStaticsProblem;
    protected final PsiSubstitutor mySubstitutor;

    /* JADX WARN: Code duplicated, block: B:26:0x003f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 14 || i == 15) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 14 || i == 15) ? 2 : 3];
        if (i != 1 && i != 3 && i != 5 && i != 7 && i != 9) {
            switch (i) {
                case 11:
                    objArr[0] = "substitutor";
                    break;
                case 12:
                    objArr[0] = "ci";
                    break;
                case 13:
                    objArr[0] = "newSubstitutor";
                    break;
                case 14:
                case 15:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/infos/CandidateInfo";
                    break;
                default:
                    objArr[0] = "candidate";
                    break;
            }
        } else {
            objArr[0] = "substitutor";
        }
        if (i == 14) {
            objArr[1] = "getElement";
        } else if (i != 15) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/infos/CandidateInfo";
        } else {
            objArr[1] = "getSubstitutor";
        }
        if (i != 14 && i != 15) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 14 && i != 15) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private CandidateInfo(PsiElement psiElement, PsiSubstitutor psiSubstitutor, Boolean bool, boolean z, PsiElement psiElement2, PsiElement psiElement3, PsiClass psiClass, boolean z2) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
        this.myCandidate = psiElement;
        this.myAccessible = bool;
        this.myStaticsProblem = z;
        this.mySubstitutor = psiSubstitutor;
        this.myCurrentFileResolveContext = psiElement2;
        this.myAccessClass = psiClass;
        this.myPlace = psiElement3;
        this.myPackagePrefixPackageReference = z2;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CandidateInfo(PsiElement psiElement, PsiSubstitutor psiSubstitutor, boolean z, boolean z2, PsiElement psiElement2) {
        this(psiElement, psiSubstitutor, Boolean.valueOf(!z), z2, psiElement2, null, null, false);
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(3);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CandidateInfo(PsiElement psiElement, PsiSubstitutor psiSubstitutor, boolean z, boolean z2) {
        this(psiElement, psiSubstitutor, z, z2, null);
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(5);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CandidateInfo(PsiElement psiElement, PsiSubstitutor psiSubstitutor, PsiElement psiElement2, PsiClass psiClass, boolean z, PsiElement psiElement3) {
        this(psiElement, psiSubstitutor, null, z, psiElement3, psiElement2, psiClass, false);
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(7);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CandidateInfo(PsiElement psiElement, PsiSubstitutor psiSubstitutor, PsiElement psiElement2, boolean z) {
        this(psiElement, psiSubstitutor, psiElement2, null, z, null);
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(9);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CandidateInfo(PsiElement psiElement, PsiSubstitutor psiSubstitutor) {
        this(psiElement, psiSubstitutor, null, null, false, null);
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(11);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CandidateInfo(CandidateInfo candidateInfo, PsiSubstitutor psiSubstitutor) {
        this(candidateInfo.myCandidate, psiSubstitutor, candidateInfo.myAccessible, candidateInfo.myStaticsProblem, candidateInfo.myCurrentFileResolveContext, candidateInfo.myPlace, null, false);
        if (candidateInfo == null) {
            $$$reportNull$$$0(12);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(13);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
    public boolean isValidResult() {
        return isAccessible() && isStaticsScopeCorrect();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
    public boolean isPackagePrefixPackageReference() {
        return this.myPackagePrefixPackageReference;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
    public PsiElement getElement() {
        PsiElement psiElement = this.myCandidate;
        if (psiElement == null) {
            $$$reportNull$$$0(14);
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
    public PsiSubstitutor getSubstitutor() {
        PsiSubstitutor psiSubstitutor = this.mySubstitutor;
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(15);
        }
        return psiSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
    public boolean isAccessible() {
        Boolean boolValueOf = this.myAccessible;
        if (boolValueOf == null) {
            Boolean boolValueOf2 = true;
            PsiElement psiElement = this.myPlace;
            if (psiElement != null) {
                PsiResolveHelper resolveHelper = JavaPsiFacade.getInstance(psiElement.getProject()).getResolveHelper();
                PsiElement psiElement2 = this.myCandidate;
                if (psiElement2 instanceof PsiMember) {
                    PsiMember psiMember = (PsiMember) psiElement2;
                    boolValueOf2 = Boolean.valueOf(resolveHelper.isAccessible(psiMember, psiMember.mo6095getModifierList(), this.myPlace, this.myAccessClass, this.myCurrentFileResolveContext));
                    if (boolValueOf2.booleanValue() && psiMember.hasModifierProperty("private") && (this.myPlace instanceof PsiReferenceExpression) && JavaVersionService.getInstance().isAtLeast(this.myPlace, JavaSdkVersion.JDK_1_7)) {
                        boolValueOf2 = Boolean.valueOf(true ^ isAccessedThroughTypeParameterBound());
                    }
                } else if (psiElement2 instanceof PsiPackage) {
                    boolValueOf = Boolean.valueOf(resolveHelper.isAccessible((PsiPackage) psiElement2, this.myPlace));
                }
                boolValueOf = boolValueOf2;
            } else {
                boolValueOf = boolValueOf2;
            }
            this.myAccessible = boolValueOf;
        }
        return boolValueOf.booleanValue();
    }

    private boolean isAccessedThroughTypeParameterBound() {
        PsiExpression qualifierExpression = ((PsiReferenceExpression) this.myPlace).getQualifierExpression();
        if (!(qualifierExpression instanceof PsiMethodCallExpression)) {
            return false;
        }
        JavaResolveResult javaResolveResultResolveMethodGenerics = ((PsiMethodCallExpression) qualifierExpression).resolveMethodGenerics();
        PsiElement element = javaResolveResultResolveMethodGenerics.getElement();
        if (!(element instanceof PsiMethod)) {
            return false;
        }
        PsiType psiTypeSubstitute = javaResolveResultResolveMethodGenerics.getSubstitutor().substitute(((PsiMethod) element).getReturnType());
        return (psiTypeSubstitute instanceof PsiCapturedWildcardType) || (psiTypeSubstitute instanceof PsiWildcardType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
    public boolean isStaticsScopeCorrect() {
        return !this.myStaticsProblem;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
    public PsiElement getCurrentFileResolveScope() {
        return this.myCurrentFileResolveContext;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CandidateInfo candidateInfo = (CandidateInfo) obj;
        if (this.myPackagePrefixPackageReference == candidateInfo.myPackagePrefixPackageReference && this.myStaticsProblem == candidateInfo.myStaticsProblem && Objects.equals(this.myAccessClass, candidateInfo.myAccessClass) && isAccessible() == candidateInfo.isAccessible() && this.myCandidate.equals(candidateInfo.myCandidate) && Objects.equals(this.myCurrentFileResolveContext, candidateInfo.myCurrentFileResolveContext) && Objects.equals(this.myPlace, candidateInfo.myPlace)) {
            return this.mySubstitutor.equals(candidateInfo.mySubstitutor);
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((Objects.hashCode(this.myPlace) * 31) + Objects.hashCode(this.myAccessClass)) * 31) + this.myCandidate.hashCode()) * 31) + (isAccessible() ? 1 : 0)) * 31) + (this.myStaticsProblem ? 1 : 0)) * 31) + this.mySubstitutor.hashCode()) * 31) + Objects.hashCode(this.myCurrentFileResolveContext)) * 31) + (this.myPackagePrefixPackageReference ? 1 : 0);
    }

    public String toString() {
        return getClass().getSimpleName() + "{element=" + this.myCandidate + ", substitutor=" + this.mySubstitutor + "}";
    }
}
