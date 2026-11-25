package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
class InitialInferenceState {
    private final ArrayList<Pair<InferenceVariable[], PsiClassType>> myCaptures;
    private final PsiElement myContext;
    private final boolean myErased;
    private final InferenceSessionContainer myInferenceSessionContainer;
    private final PsiSubstitutor myInferenceSubstitutor;
    private final List<InferenceVariable> myInferenceVariables;
    private final PsiSubstitutor mySiteSubstitutor;

    InitialInferenceState(Collection<VariableInfo> collection, PsiSubstitutor psiSubstitutor, PsiElement psiElement, PsiSubstitutor psiSubstitutor2, PsiSubstitutor psiSubstitutor3, List<Pair<InferenceVariable[], PsiClassType>> list, boolean z, InferenceSessionContainer inferenceSessionContainer) {
        this.myErased = z;
        this.myInferenceVariables = new ArrayList(collection.size());
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        PsiSubstitutor psiSubstitutorPut2 = PsiSubstitutor.EMPTY;
        for (VariableInfo variableInfo : collection) {
            if (variableInfo.myResolved instanceof InferenceVariable) {
                this.myInferenceVariables.add((InferenceVariable) variableInfo.myResolved);
                if (psiSubstitutor2.getSubstitutionMap().containsValue(variableInfo.myType)) {
                    psiSubstitutorPut = psiSubstitutorPut.put(variableInfo.myVariable.getParameter(), variableInfo.mySubstituted);
                    psiSubstitutorPut2 = psiSubstitutorPut2.put(variableInfo.myVariable, variableInfo.mySubstituted);
                }
            }
        }
        this.myInferenceSubstitutor = psiSubstitutorPut;
        this.myContext = psiElement;
        this.mySiteSubstitutor = psiSubstitutor3;
        this.myCaptures = new ArrayList<>();
        for (Pair<InferenceVariable[], PsiClassType> pair : list) {
            InferenceVariable[] inferenceVariableArr = new InferenceVariable[pair.first.length];
            InferenceVariable[] inferenceVariableArr2 = pair.first;
            for (int i = 0; i < inferenceVariableArr2.length; i++) {
                inferenceVariableArr[i] = (InferenceVariable) PsiUtil.resolveClassInClassTypeOnly(psiSubstitutor.substitute(inferenceVariableArr2[i]));
            }
            this.myCaptures.add(Pair.create(inferenceVariableArr, (PsiClassType) psiSubstitutor.substitute(psiSubstitutorPut2.substitute(pair.second))));
        }
        this.myInferenceSessionContainer = inferenceSessionContainer;
    }

    InferenceSessionContainer getInferenceSessionContainer() {
        return this.myInferenceSessionContainer;
    }

    List<InferenceVariable> getInferenceVariables() {
        return this.myInferenceVariables;
    }

    public PsiElement getContext() {
        return this.myContext;
    }

    PsiSubstitutor getInferenceSubstitutor() {
        return this.myInferenceSubstitutor;
    }

    PsiSubstitutor getSiteSubstitutor() {
        return this.mySiteSubstitutor;
    }

    public ArrayList<Pair<InferenceVariable[], PsiClassType>> getCaptures() {
        return this.myCaptures;
    }

    public boolean isErased() {
        return this.myErased;
    }

    static class VariableInfo {
        final PsiClass myResolved;
        final PsiType mySubstituted;
        final PsiClassType myType;
        final InferenceVariable myVariable;

        VariableInfo(InferenceVariable inferenceVariable, PsiSubstitutor psiSubstitutor, PsiElementFactory psiElementFactory) {
            this.myVariable = inferenceVariable;
            PsiType psiTypeSubstitute = psiSubstitutor.substitute(inferenceVariable);
            this.mySubstituted = psiTypeSubstitute;
            this.myResolved = PsiUtil.resolveClassInClassTypeOnly(psiTypeSubstitute);
            this.myType = psiElementFactory.createType(inferenceVariable);
        }
    }
}
