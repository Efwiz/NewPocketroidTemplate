package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;

/* JADX INFO: loaded from: classes3.dex */
public class SubtypingConstraint implements ConstraintFormula {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) SubtypingConstraint.class);
    private PsiType myS;
    private PsiType myT;

    public SubtypingConstraint(PsiType psiType, PsiType psiType2) {
        this.myT = psiType;
        this.myS = psiType2;
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
        SubtypingConstraint subtypingConstraint = (SubtypingConstraint) obj;
        PsiType psiType = this.myS;
        if (psiType == null ? subtypingConstraint.myS != null : !psiType.equals(subtypingConstraint.myS)) {
            return false;
        }
        PsiType psiType2 = this.myT;
        PsiType psiType3 = subtypingConstraint.myT;
        return psiType2 == null ? psiType3 == null : psiType2.equals(psiType3);
    }

    public int hashCode() {
        PsiType psiType = this.myS;
        int iHashCode = (psiType != null ? psiType.hashCode() : 0) * 31;
        PsiType psiType2 = this.myT;
        return iHashCode + (psiType2 != null ? psiType2.hashCode() : 0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public boolean reduce(InferenceSession inferenceSession, List<? super ConstraintFormula> list) {
        boolean zDoReduce = doReduce(list);
        if (!zDoReduce) {
            inferenceSession.registerIncompatibleErrorMessage(inferenceSession.getInferenceVariables(), JavaPsiBundle.message("type.can.be.converted", inferenceSession.getPresentableText(this.myS), inferenceSession.getPresentableText(this.myT)));
        }
        return zDoReduce;
    }

    private boolean doReduce(List<? super ConstraintFormula> list) {
        PsiType psiType = this.myT;
        if (psiType instanceof PsiWildcardType) {
            PsiType bound = ((PsiWildcardType) psiType).getBound();
            if (bound == null) {
                return true;
            }
            if (((PsiWildcardType) this.myT).isExtends()) {
                PsiType psiType2 = this.myS;
                if (psiType2 instanceof PsiWildcardType) {
                    PsiType bound2 = ((PsiWildcardType) psiType2).getBound();
                    if (bound2 == null) {
                        list.add(new StrictSubtypingConstraint(bound, ((PsiWildcardType) this.myS).getExtendsBound(), false));
                        return true;
                    }
                    if (((PsiWildcardType) this.myS).isExtends()) {
                        list.add(new StrictSubtypingConstraint(bound, bound2, false));
                        return true;
                    }
                    if (((PsiWildcardType) this.myS).isSuper()) {
                        list.add(new TypeEqualityConstraint(bound, PsiType.getJavaLangObject(((PsiWildcardType) this.myT).getManager(), this.myT.getResolveScope())));
                        return true;
                    }
                } else {
                    list.add(new StrictSubtypingConstraint(bound, this.myS, false));
                    return true;
                }
            } else {
                LOG.assertTrue(((PsiWildcardType) this.myT).isSuper());
                PsiType psiType3 = this.myS;
                if (psiType3 instanceof PsiWildcardType) {
                    PsiType bound3 = ((PsiWildcardType) psiType3).getBound();
                    if (bound3 != null && ((PsiWildcardType) this.myS).isSuper()) {
                        list.add(new StrictSubtypingConstraint(bound3, bound, false));
                        return true;
                    }
                } else {
                    list.add(new StrictSubtypingConstraint(this.myS, bound, false));
                    return true;
                }
            }
            return false;
        }
        if (this.myS instanceof PsiWildcardType) {
            return false;
        }
        list.add(new TypeEqualityConstraint(this.myT, this.myS));
        return true;
    }

    public String toString() {
        return this.myS.getPresentableText() + " <= " + this.myT.getPresentableText();
    }
}
