package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceBound;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariable;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;

/* JADX INFO: loaded from: classes3.dex */
public class StrictSubtypingConstraint implements ConstraintFormula {
    private final boolean myCapture;
    private PsiType myS;
    private PsiType myT;

    public StrictSubtypingConstraint(PsiType psiType, PsiType psiType2) {
        this(psiType, psiType2, true);
    }

    public StrictSubtypingConstraint(PsiType psiType, PsiType psiType2, boolean z) {
        this.myT = psiType;
        this.myS = psiType2;
        this.myCapture = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public void apply(PsiSubstitutor psiSubstitutor, boolean z) {
        this.myT = psiSubstitutor.substitute(this.myT);
        this.myS = psiSubstitutor.substitute(this.myS);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public boolean reduce(InferenceSession inferenceSession, List<? super ConstraintFormula> list) {
        HashSet hashSet = new HashSet();
        boolean zDoReduce = doReduce(inferenceSession, hashSet, list);
        if (!zDoReduce) {
            inferenceSession.registerIncompatibleErrorMessage(hashSet, JavaPsiBundle.message("type.conforms.to.constraint", inferenceSession.getPresentableText(this.myS), inferenceSession.getPresentableText(this.myT)));
        }
        return zDoReduce;
    }

    private boolean doReduce(InferenceSession inferenceSession, Set<? super InferenceVariable> set, List<? super ConstraintFormula> list) {
        PsiType lowerBound;
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics;
        PsiClass element;
        if (!inferenceSession.collectDependencies(this.myS, set) && !inferenceSession.collectDependencies(this.myT, set)) {
            PsiType psiType = this.myT;
            if (psiType == null) {
                PsiType psiType2 = this.myS;
                return psiType2 == null || psiType2.equalsToText(CommonClassNames.JAVA_LANG_OBJECT);
            }
            PsiType psiType3 = this.myS;
            if (psiType3 == null) {
                return true;
            }
            return TypeConversionUtil.isAssignable(psiType, psiType3);
        }
        if (PsiType.NULL.equals(this.myT) || this.myT == null) {
            return false;
        }
        if (!PsiType.NULL.equals(this.myS) && this.myS != null && !this.myT.equalsToText(CommonClassNames.JAVA_LANG_OBJECT)) {
            if (PsiType.VOID.equals(this.myS) ^ PsiType.VOID.equals(this.myT)) {
                return false;
            }
            if (inferenceSession.getInferenceVariable(this.myS) != null) {
                InferenceVariable.addBound(this.myS, this.myT, InferenceBound.UPPER, inferenceSession);
                return true;
            }
            if (inferenceSession.getInferenceVariable(this.myT) != null) {
                InferenceVariable.addBound(this.myT, this.myS, InferenceBound.LOWER, inferenceSession);
                return true;
            }
            PsiType psiType4 = this.myT;
            if (psiType4 instanceof PsiArrayType) {
                PsiType psiType5 = this.myS;
                if (psiType5 instanceof PsiCapturedWildcardType) {
                    PsiType upperBound = ((PsiCapturedWildcardType) psiType5).getUpperBound();
                    if (upperBound instanceof PsiArrayType) {
                        psiType5 = upperBound;
                    }
                }
                if (!(psiType5 instanceof PsiArrayType)) {
                    return false;
                }
                PsiType componentType = ((PsiArrayType) this.myT).getComponentType();
                PsiType componentType2 = ((PsiArrayType) psiType5).getComponentType();
                if ((componentType instanceof PsiPrimitiveType) || (componentType2 instanceof PsiPrimitiveType)) {
                    return (componentType2 instanceof PsiPrimitiveType) && componentType2.equals(componentType);
                }
                list.add(new StrictSubtypingConstraint(componentType, componentType2, this.myCapture));
                return true;
            }
            if (psiType4 instanceof PsiClassType) {
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics2 = ((PsiClassType) psiType4).resolveGenerics();
                PsiClass element2 = classResolveResultResolveGenerics2.getElement();
                if (element2 != null) {
                    if (element2 instanceof PsiTypeParameter) {
                        PsiType psiType6 = this.myS;
                        if (psiType6 instanceof PsiIntersectionType) {
                            for (PsiType psiType7 : ((PsiIntersectionType) psiType6).getConjuncts()) {
                                if (this.myT.equals(psiType7)) {
                                    return true;
                                }
                            }
                        }
                        PsiType inferredLowerBoundForSynthetic = TypeConversionUtil.getInferredLowerBoundForSynthetic((PsiTypeParameter) element2);
                        if (inferredLowerBoundForSynthetic == null) {
                            return false;
                        }
                        list.add(new StrictSubtypingConstraint(inferredLowerBoundForSynthetic, this.myS, this.myCapture));
                        return true;
                    }
                    PsiType psiType8 = this.myS;
                    if (psiType8 instanceof PsiArrayType) {
                        return this.myT.isAssignableFrom(psiType8);
                    }
                    PsiClassType subclassType = getSubclassType(element2, psiType8, this.myCapture);
                    if (subclassType == null || (element = (classResolveResultResolveGenerics = subclassType.resolveGenerics()).getElement()) == null) {
                        return false;
                    }
                    if (((PsiClassType) this.myT).isRaw() || (this.myCapture && subclassType.isRaw())) {
                        return InheritanceUtil.isInheritorOrSelf(element, element2, true);
                    }
                    PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
                    HashMap map = new HashMap();
                    for (PsiTypeParameter psiTypeParameter : element.getTypeParameters()) {
                        map.put(psiTypeParameter, substitutor.substituteWithBoundsPromotion(psiTypeParameter));
                    }
                    PsiSubstitutor psiSubstitutorPutAll = substitutor.putAll(map);
                    PsiSubstitutor substitutor2 = classResolveResultResolveGenerics2.getSubstitutor();
                    PsiSubstitutor classSubstitutor = TypeConversionUtil.getClassSubstitutor(element2, element, psiSubstitutorPutAll);
                    if (classSubstitutor != null) {
                        for (PsiTypeParameter psiTypeParameter2 : PsiUtil.typeParametersIterable(element2)) {
                            PsiType psiTypeSubstitute = substitutor2.substitute(psiTypeParameter2);
                            PsiType psiTypeSubstitute2 = classSubstitutor.substitute(psiTypeParameter2);
                            if ((psiTypeSubstitute == null) ^ (psiTypeSubstitute2 == null)) {
                                return false;
                            }
                            list.add(new SubtypingConstraint(psiTypeSubstitute, psiTypeSubstitute2));
                        }
                        return true;
                    }
                }
                return false;
            }
            if (psiType4 instanceof PsiIntersectionType) {
                for (PsiType psiType9 : ((PsiIntersectionType) psiType4).getConjuncts()) {
                    list.add(new StrictSubtypingConstraint(psiType9, this.myS, this.myCapture));
                }
                return true;
            }
            if ((psiType4 instanceof PsiCapturedWildcardType) && (lowerBound = ((PsiCapturedWildcardType) psiType4).getLowerBound()) != PsiType.NULL) {
                list.add(new StrictSubtypingConstraint(lowerBound, this.myS, this.myCapture));
            }
        }
        return true;
    }

    public static PsiClassType getSubclassType(PsiClass psiClass, PsiType psiType, boolean z) {
        if (psiType instanceof PsiIntersectionType) {
            for (PsiType psiType2 : ((PsiIntersectionType) psiType).getConjuncts()) {
                if (psiType2 instanceof PsiClassType) {
                    PsiClassType psiClassType = (PsiClassType) psiType2;
                    if (InheritanceUtil.isInheritorOrSelf(psiClassType.resolveGenerics().getElement(), psiClass, true)) {
                        return psiClassType;
                    }
                }
            }
            return null;
        }
        if (psiType instanceof PsiClassType) {
            return (PsiClassType) psiType;
        }
        if (!(psiType instanceof PsiCapturedWildcardType)) {
            return null;
        }
        PsiType upperBound = ((PsiCapturedWildcardType) psiType).getUpperBound(z);
        if (upperBound instanceof PsiClassType) {
            return (PsiClassType) upperBound;
        }
        if (upperBound instanceof PsiIntersectionType) {
            return getSubclassType(psiClass, upperBound, z);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StrictSubtypingConstraint strictSubtypingConstraint = (StrictSubtypingConstraint) obj;
        PsiType psiType = this.myS;
        if (psiType == null ? strictSubtypingConstraint.myS != null : !psiType.equals(strictSubtypingConstraint.myS)) {
            return false;
        }
        PsiType psiType2 = this.myT;
        PsiType psiType3 = strictSubtypingConstraint.myT;
        return psiType2 == null ? psiType3 == null : psiType2.equals(psiType3);
    }

    public int hashCode() {
        PsiType psiType = this.myS;
        int iHashCode = (psiType != null ? psiType.hashCode() : 0) * 31;
        PsiType psiType2 = this.myT;
        return iHashCode + (psiType2 != null ? psiType2.hashCode() : 0);
    }

    public String toString() {
        return this.myT.getPresentableText() + " < " + this.myS.getPresentableText();
    }
}
