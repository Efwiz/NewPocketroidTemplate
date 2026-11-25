package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.PsiCall;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;

/* JADX INFO: loaded from: classes3.dex */
class CompoundInitialState {
    private final Map<PsiElement, InitialInferenceState> myInitialStates;
    private final PsiSubstitutor myInitialSubstitutor;

    CompoundInitialState(PsiSubstitutor psiSubstitutor, Map<PsiElement, InitialInferenceState> map) {
        this.myInitialSubstitutor = psiSubstitutor;
        this.myInitialStates = map;
    }

    PsiSubstitutor getInitialSubstitutor() {
        return this.myInitialSubstitutor;
    }

    InitialInferenceState getInitialState(PsiCall psiCall) {
        return this.myInitialStates.get(psiCall);
    }
}
