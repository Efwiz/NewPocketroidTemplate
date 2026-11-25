package org.jetbrains.kotlin.com.intellij.psi.scope.processor;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor;

/* JADX INFO: loaded from: classes3.dex */
public class FilterElementProcessor implements PsiElementProcessor {
    private final ElementFilter myFilter;
    private final PsiElementProcessor myProcessor;
    private final List<PsiElement> myResults;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/scope/processor/FilterElementProcessor", "execute"));
    }

    public FilterElementProcessor(ElementFilter elementFilter, PsiElementProcessor psiElementProcessor, List list) {
        this.myFilter = elementFilter;
        this.myProcessor = psiElementProcessor;
        this.myResults = list;
    }

    public FilterElementProcessor(ElementFilter elementFilter, List list) {
        this(elementFilter, null, list);
    }

    public FilterElementProcessor(ElementFilter elementFilter, PsiElementProcessor psiElementProcessor) {
        this(elementFilter, psiElementProcessor, new ArrayList());
    }

    public FilterElementProcessor(ElementFilter elementFilter) {
        this(elementFilter, null, new ArrayList());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
    public boolean execute(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (!this.myFilter.isClassAcceptable(psiElement.getClass()) || !this.myFilter.isAcceptable(psiElement, psiElement.getParent())) {
            return true;
        }
        PsiElementProcessor psiElementProcessor = this.myProcessor;
        if (psiElementProcessor != null) {
            return psiElementProcessor.execute(psiElement);
        }
        add(psiElement);
        return true;
    }

    protected void add(PsiElement psiElement) {
        this.myResults.add(psiElement);
    }

    public List<PsiElement> getResults() {
        return this.myResults;
    }

    public boolean shouldProcess(Class cls) {
        return this.myFilter.isClassAcceptable(cls);
    }
}
