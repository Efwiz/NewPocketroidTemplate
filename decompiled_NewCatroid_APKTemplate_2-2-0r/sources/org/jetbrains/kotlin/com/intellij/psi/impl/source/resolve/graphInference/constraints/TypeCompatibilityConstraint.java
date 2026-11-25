package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceBound;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariable;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;

/* JADX INFO: loaded from: classes3.dex */
public class TypeCompatibilityConstraint implements ConstraintFormula {
    private PsiType myS;
    private PsiType myT;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "t";
        } else {
            objArr[0] = "s";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/constraints/TypeCompatibilityConstraint";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public TypeCompatibilityConstraint(PsiType psiType, PsiType psiType2) {
        if (psiType == null) {
            $$$reportNull$$$0(0);
        }
        if (psiType2 == null) {
            $$$reportNull$$$0(1);
        }
        this.myT = psiType.annotate(TypeAnnotationProvider.EMPTY);
        this.myS = psiType2.annotate(TypeAnnotationProvider.EMPTY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public boolean reduce(InferenceSession inferenceSession, List<? super ConstraintFormula> list) {
        PsiClassType boxedType;
        PsiClassType boxedType2;
        if (inferenceSession.isProperType(this.myT) && inferenceSession.isProperType(this.myS)) {
            boolean zIsAssignable = TypeConversionUtil.isAssignable(this.myT, this.myS);
            if (!zIsAssignable) {
                inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.not.convertible", inferenceSession.getPresentableText(this.myS), inferenceSession.getPresentableText(this.myT)));
            }
            return zIsAssignable;
        }
        if ((this.myS instanceof PsiPrimitiveType) && !PsiType.VOID.equals(this.myS) && (boxedType2 = ((PsiPrimitiveType) this.myS).getBoxedType(inferenceSession.getManager(), inferenceSession.getScope())) != null) {
            list.add(new TypeCompatibilityConstraint(this.myT, boxedType2));
            return true;
        }
        if ((this.myT instanceof PsiPrimitiveType) && !PsiType.VOID.equals(this.myT) && (boxedType = ((PsiPrimitiveType) this.myT).getBoxedType(inferenceSession.getManager(), inferenceSession.getScope())) != null) {
            list.add(new TypeEqualityConstraint(boxedType, this.myS));
            return true;
        }
        if (isUncheckedConversion(this.myT, this.myS, inferenceSession)) {
            inferenceSession.setErased();
            return true;
        }
        list.add(new StrictSubtypingConstraint(this.myT, this.myS));
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0045  */
    public static boolean isUncheckedConversion(PsiType psiType, PsiType psiType2, InferenceSession inferenceSession) {
        if (psiType instanceof PsiClassType) {
            PsiClassType psiClassType = (PsiClassType) psiType;
            if (!psiClassType.isRaw()) {
                PsiClass element = psiClassType.resolveGenerics().getElement();
                if ((psiType2 instanceof PsiClassType) && isUncheckedConversion(element, (PsiClassType) psiType2, inferenceSession)) {
                    return true;
                }
                if (psiType2 instanceof PsiIntersectionType) {
                    for (PsiType psiType3 : ((PsiIntersectionType) psiType2).getConjuncts()) {
                        if ((psiType3 instanceof PsiClassType) && isUncheckedConversion(element, (PsiClassType) psiType3, inferenceSession)) {
                            return true;
                        }
                    }
                }
            } else if ((psiType instanceof PsiArrayType) && psiType2 != null && psiType.getArrayDimensions() == psiType2.getArrayDimensions()) {
                return isUncheckedConversion(psiType.getDeepComponentType(), psiType2.getDeepComponentType(), inferenceSession);
            }
        } else if (psiType instanceof PsiArrayType) {
            return isUncheckedConversion(psiType.getDeepComponentType(), psiType2.getDeepComponentType(), inferenceSession);
        }
        return false;
    }

    private static boolean isUncheckedConversion(PsiClass psiClass, PsiClassType psiClassType, InferenceSession inferenceSession) {
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        if (psiClass == null || element == null || (element instanceof InferenceVariable)) {
            return false;
        }
        PsiSubstitutor classSubstitutor = TypeConversionUtil.getClassSubstitutor(psiClass, element, classResolveResultResolveGenerics.getSubstitutor());
        if (classSubstitutor != null) {
            return PsiUtil.isRawSubstitutor(psiClass, classSubstitutor);
        }
        if (!(psiClass instanceof InferenceVariable) || !psiClassType.isRaw()) {
            return false;
        }
        for (PsiType psiType : ((InferenceVariable) psiClass).getBounds(InferenceBound.UPPER)) {
            if (!inferenceSession.isProperType(psiType) && InheritanceUtil.isInheritorOrSelf(PsiUtil.resolveClassInClassTypeOnly(psiType), element, true)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public void apply(PsiSubstitutor psiSubstitutor, boolean z) {
        this.myT = psiSubstitutor.substitute(this.myT);
        this.myS = psiSubstitutor.substitute(this.myS);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TypeCompatibilityConstraint typeCompatibilityConstraint = (TypeCompatibilityConstraint) obj;
        return this.myS.equals(typeCompatibilityConstraint.myS) && this.myT.equals(typeCompatibilityConstraint.myT);
    }

    public int hashCode() {
        return (this.myT.hashCode() * 31) + this.myS.hashCode();
    }

    public String toString() {
        return this.myS.getPresentableText() + " -> " + this.myT.getPresentableText();
    }
}
