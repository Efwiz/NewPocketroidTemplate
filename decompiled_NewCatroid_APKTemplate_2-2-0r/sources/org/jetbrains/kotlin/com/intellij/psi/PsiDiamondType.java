package org.jetbrains.kotlin.com.intellij.psi;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiDiamondType extends PsiType {
    public static final RecursionGuard<PsiElement> ourDiamondGuard = RecursionManager.createGuard("diamondInference");

    public abstract JavaResolveResult getStaticFactory();

    public abstract DiamondInferenceResult resolveInferredTypes();

    public PsiDiamondType() {
        super(TypeAnnotationProvider.EMPTY);
    }

    public static class DiamondInferenceResult {
        private String myErrorMessage;
        private final List<PsiType> myInferredTypes = new ArrayList();
        private String myNewExpressionPresentableText;
        public static final DiamondInferenceResult EXPLICIT_CONSTRUCTOR_TYPE_ARGS = new DiamondInferenceResult() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/PsiDiamondType$DiamondInferenceResult$1", "getTypes"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult
            public PsiType[] getTypes() {
                PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
                if (psiTypeArr == null) {
                    $$$reportNull$$$0(0);
                }
                return psiTypeArr;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult
            public String getErrorMessage() {
                return JavaPsiBundle.message("diamond.error.explicit.type.parameters.for.constructor", new Object[0]);
            }
        };
        public static final DiamondInferenceResult NULL_RESULT = new DiamondInferenceResult() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/PsiDiamondType$DiamondInferenceResult$2", "getTypes"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult
            public PsiType[] getTypes() {
                PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
                if (psiTypeArr == null) {
                    $$$reportNull$$$0(0);
                }
                return psiTypeArr;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult
            public String getErrorMessage() {
                return JavaPsiBundle.message("diamond.error.cannot.infer.arguments", new Object[0]);
            }
        };
        public static final DiamondInferenceResult RAW_RESULT = new DiamondInferenceResult() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/PsiDiamondType$DiamondInferenceResult$3", "getTypes"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult
            public String getErrorMessage() {
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult
            public PsiType[] getTypes() {
                PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
                if (psiTypeArr == null) {
                    $$$reportNull$$$0(0);
                }
                return psiTypeArr;
            }
        };
        public static final DiamondInferenceResult UNRESOLVED_CONSTRUCTOR = new DiamondInferenceResult() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult.4
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/PsiDiamondType$DiamondInferenceResult$4", "getTypes"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult
            public PsiType[] getTypes() {
                PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
                if (psiTypeArr == null) {
                    $$$reportNull$$$0(0);
                }
                return psiTypeArr;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult
            public String getErrorMessage() {
                return JavaPsiBundle.message("diamond.error.cannot.infer.arguments.unable.to.resolve.constructor", new Object[0]);
            }
        };
        public static final DiamondInferenceResult ANONYMOUS_INNER_RESULT = new DiamondInferenceResult() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult.5
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/PsiDiamondType$DiamondInferenceResult$5", "getTypes"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult
            public PsiType[] getTypes() {
                PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
                if (psiTypeArr == null) {
                    $$$reportNull$$$0(0);
                }
                return psiTypeArr;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult
            public String getErrorMessage() {
                return JavaPsiBundle.message("diamond.error.anonymous.inner.classes", new Object[0]);
            }
        };

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/PsiDiamondType$DiamondInferenceResult", "getTypes"));
        }

        public DiamondInferenceResult() {
        }

        public DiamondInferenceResult(String str) {
            this.myNewExpressionPresentableText = str;
        }

        public PsiType[] getTypes() {
            PsiType[] psiTypeArr;
            if (this.myErrorMessage == null) {
                List<PsiType> list = this.myInferredTypes;
                psiTypeArr = (PsiType[]) list.toArray(PsiType.createArray(list.size()));
            } else {
                psiTypeArr = PsiType.EMPTY_ARRAY;
            }
            if (psiTypeArr == null) {
                $$$reportNull$$$0(0);
            }
            return psiTypeArr;
        }

        public List<PsiType> getInferredTypes() {
            return this.myInferredTypes;
        }

        public String getErrorMessage() {
            return this.myErrorMessage;
        }

        public boolean failedToInfer() {
            return this.myErrorMessage != null;
        }

        protected void addInferredType(PsiType psiType) {
            if (this.myErrorMessage != null) {
                return;
            }
            if (psiType == null) {
                this.myErrorMessage = JavaPsiBundle.message("diamond.error.cannot.infer.type.arguments", this.myNewExpressionPresentableText);
            } else {
                this.myInferredTypes.add(psiType);
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            DiamondInferenceResult diamondInferenceResult = (DiamondInferenceResult) obj;
            String str = this.myErrorMessage;
            if (str == null ? diamondInferenceResult.myErrorMessage == null : str.equals(diamondInferenceResult.myErrorMessage)) {
                return this.myInferredTypes.equals(diamondInferenceResult.myInferredTypes);
            }
            return false;
        }

        public int hashCode() {
            int iHashCode = this.myInferredTypes.hashCode() * 31;
            String str = this.myErrorMessage;
            return iHashCode + (str != null ? str.hashCode() : 0);
        }
    }

    public static boolean hasDiamond(PsiNewExpression psiNewExpression) {
        return getDiamondType(psiNewExpression) != null;
    }

    public static PsiDiamondType getDiamondType(PsiNewExpression psiNewExpression) {
        PsiJavaCodeReferenceElement classOrAnonymousClassReference;
        PsiReferenceParameterList parameterList;
        if (!PsiUtil.isLanguageLevel7OrHigher(psiNewExpression) || (classOrAnonymousClassReference = psiNewExpression.getClassOrAnonymousClassReference()) == null || (parameterList = classOrAnonymousClassReference.getParameterList()) == null) {
            return null;
        }
        PsiTypeElement[] typeParameterElements = parameterList.getTypeParameterElements();
        if (typeParameterElements.length != 1) {
            return null;
        }
        PsiType type = typeParameterElements[0].getType();
        if (type instanceof PsiDiamondType) {
            return (PsiDiamondType) type;
        }
        return null;
    }

    public static JavaResolveResult getDiamondsAwareResolveResult(PsiCall psiCall) {
        PsiDiamondType diamondType;
        if ((psiCall instanceof PsiNewExpression) && (diamondType = getDiamondType((PsiNewExpression) psiCall)) != null) {
            JavaResolveResult staticFactory = diamondType.getStaticFactory();
            return staticFactory != null ? staticFactory : JavaResolveResult.EMPTY;
        }
        if (psiCall instanceof PsiEnumConstant) {
            PsiEnumConstant psiEnumConstant = (PsiEnumConstant) psiCall;
            PsiClass containingClass = psiEnumConstant.mo6092getContainingClass();
            if (containingClass == null) {
                return JavaResolveResult.EMPTY;
            }
            JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(psiEnumConstant.getProject());
            PsiClassType psiClassTypeCreateType = javaPsiFacade.getElementFactory().createType(containingClass);
            PsiExpressionList argumentList = psiEnumConstant.getArgumentList();
            if (argumentList == null) {
                return JavaResolveResult.EMPTY;
            }
            return javaPsiFacade.getResolveHelper().resolveConstructor(psiClassTypeCreateType, argumentList, psiEnumConstant);
        }
        return psiCall.resolveMethodGenerics();
    }
}
