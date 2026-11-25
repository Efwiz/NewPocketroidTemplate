package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceBound;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariable;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class TypeEqualityConstraint implements ConstraintFormula {
    private static final Logger LOG = Logger.getInstance((Class<?>) TypeEqualityConstraint.class);
    private PsiType myS;
    private PsiType myT;

    public TypeEqualityConstraint(PsiType psiType, PsiType psiType2) {
        this.myT = psiType;
        this.myS = psiType2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public boolean reduce(InferenceSession inferenceSession, List<? super ConstraintFormula> list) {
        PsiType psiType;
        PsiType psiType2 = this.myT;
        if ((psiType2 instanceof PsiWildcardType) && (this.myS instanceof PsiWildcardType)) {
            PsiType bound = ((PsiWildcardType) psiType2).getBound();
            PsiType bound2 = ((PsiWildcardType) this.myS).getBound();
            if (bound == null && bound2 == null) {
                return true;
            }
            if (bound2 == null && ((PsiWildcardType) this.myT).isExtends()) {
                list.add(new TypeEqualityConstraint(((PsiWildcardType) this.myS).getExtendsBound(), bound));
                return true;
            }
            if (bound == null && ((PsiWildcardType) this.myS).isExtends()) {
                list.add(new TypeEqualityConstraint(((PsiWildcardType) this.myT).getExtendsBound(), bound2));
                return true;
            }
            if ((((PsiWildcardType) this.myT).isExtends() && ((PsiWildcardType) this.myS).isExtends()) || (((PsiWildcardType) this.myT).isSuper() && ((PsiWildcardType) this.myS).isSuper())) {
                Logger logger = LOG;
                logger.assertTrue(bound != null);
                logger.assertTrue(bound2 != null);
                list.add(new TypeEqualityConstraint(bound, bound2));
                return true;
            }
        }
        PsiType psiType3 = this.myT;
        if ((psiType3 instanceof PsiWildcardType) || (this.myS instanceof PsiWildcardType)) {
            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.incompatible.equality.constraint", inferenceSession.getPresentableText(psiType3), inferenceSession.getPresentableText(this.myS)));
            return false;
        }
        if (inferenceSession.isProperType(psiType3) && inferenceSession.isProperType(this.myS)) {
            boolean zEqual = Comparing.equal(this.myT, this.myS);
            if (!zEqual) {
                inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.incompatible.equality.constraint", inferenceSession.getPresentableText(this.myT), inferenceSession.getPresentableText(this.myS)));
            }
            return zEqual;
        }
        PsiType psiType4 = this.myT;
        if (psiType4 != null && psiType4 != PsiType.NULL && (psiType = this.myS) != null && psiType != PsiType.NULL) {
            if (inferenceSession.getInferenceVariable(this.myS) != null) {
                PsiType psiType5 = this.myT;
                if (!(psiType5 instanceof PsiPrimitiveType)) {
                    InferenceVariable.addBound(this.myS, psiType5, InferenceBound.EQ, inferenceSession);
                    return true;
                }
            }
            if (inferenceSession.getInferenceVariable(this.myT) != null) {
                PsiType psiType6 = this.myS;
                if (!(psiType6 instanceof PsiPrimitiveType)) {
                    InferenceVariable.addBound(this.myT, psiType6, InferenceBound.EQ, inferenceSession);
                    return true;
                }
            }
            PsiType psiType7 = this.myT;
            if ((psiType7 instanceof PsiClassType) && (this.myS instanceof PsiClassType)) {
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics = ((PsiClassType) psiType7).resolveGenerics();
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics2 = ((PsiClassType) this.myS).resolveGenerics();
                PsiClass element = classResolveResultResolveGenerics.getElement();
                if (element != null && element.getManager().areElementsEquivalent(element, classResolveResultResolveGenerics2.getElement())) {
                    PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
                    PsiSubstitutor substitutor2 = classResolveResultResolveGenerics2.getSubstitutor();
                    for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(element)) {
                        PsiType psiTypeSubstitute = substitutor.substitute(psiTypeParameter);
                        PsiType psiTypeSubstitute2 = substitutor2.substitute(psiTypeParameter);
                        if (psiTypeSubstitute != null && psiTypeSubstitute2 != null) {
                            list.add(new TypeEqualityConstraint(psiTypeSubstitute, psiTypeSubstitute2));
                        }
                        if ((psiTypeSubstitute2 == null) ^ (psiTypeSubstitute == null)) {
                            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.incompatible.equality.constraint", inferenceSession.getPresentableText(this.myT), inferenceSession.getPresentableText(this.myS)));
                            return false;
                        }
                    }
                    return true;
                }
            }
            PsiType psiType8 = this.myT;
            if ((psiType8 instanceof PsiArrayType) && (this.myS instanceof PsiArrayType)) {
                list.add(new TypeEqualityConstraint(((PsiArrayType) psiType8).getComponentType(), ((PsiArrayType) this.myS).getComponentType()));
                return true;
            }
            inferenceSession.registerIncompatibleErrorMessage(inferenceSession.getInferenceVariables(), JavaPsiBundle.message("type.conforms.to.constraint", inferenceSession.getPresentableText(this.myS), inferenceSession.getPresentableText(this.myT)));
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
        TypeEqualityConstraint typeEqualityConstraint = (TypeEqualityConstraint) obj;
        PsiType psiType = this.myS;
        if (psiType == null ? typeEqualityConstraint.myS != null : !psiType.equals(typeEqualityConstraint.myS)) {
            return false;
        }
        PsiType psiType2 = this.myT;
        PsiType psiType3 = typeEqualityConstraint.myT;
        return psiType2 == null ? psiType3 == null : psiType2.equals(psiType3);
    }

    public int hashCode() {
        PsiType psiType = this.myT;
        int iHashCode = (psiType != null ? psiType.hashCode() : 0) * 31;
        PsiType psiType2 = this.myS;
        return iHashCode + (psiType2 != null ? psiType2.hashCode() : 0);
    }

    public String toString() {
        return this.myT.getPresentableText() + " == " + this.myS.getPresentableText();
    }
}
