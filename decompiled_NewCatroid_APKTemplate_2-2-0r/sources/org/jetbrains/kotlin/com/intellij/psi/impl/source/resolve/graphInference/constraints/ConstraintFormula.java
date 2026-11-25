package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;

/* JADX INFO: loaded from: classes3.dex */
public interface ConstraintFormula {
    void apply(PsiSubstitutor psiSubstitutor, boolean z);

    boolean reduce(InferenceSession inferenceSession, List<? super ConstraintFormula> list);
}
