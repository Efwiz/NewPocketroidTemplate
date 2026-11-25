package org.jetbrains.kotlin.com.intellij.psi.search;

import org.jetbrains.kotlin.com.intellij.openapi.application.ReadActionProcessor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.Processor;

/* JADX INFO: loaded from: classes3.dex */
public class PsiElementProcessorAdapter<T extends PsiElement> extends ReadActionProcessor<T> implements Processor<T> {
    private final PsiElementProcessor<? super T> myProcessor;

    public PsiElementProcessorAdapter(PsiElementProcessor<? super T> psiElementProcessor) {
        this.myProcessor = psiElementProcessor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.ReadActionProcessor
    public boolean processInReadAction(T t) {
        return this.myProcessor.execute(t);
    }
}
