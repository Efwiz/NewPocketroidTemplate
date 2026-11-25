package org.jetbrains.kotlin.com.intellij.psi.filters;

import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class OrFilter implements ElementFilter {
    private final List<ElementFilter> myFilters;

    @Deprecated
    public OrFilter() {
        this.myFilters = new SmartList();
    }

    public OrFilter(ElementFilter... elementFilterArr) {
        this.myFilters = new SmartList((Object[]) elementFilterArr);
    }

    @Deprecated
    public void addFilter(ElementFilter elementFilter) {
        this.myFilters.add(elementFilter);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isAcceptable(final Object obj, final PsiElement psiElement) {
        return this.myFilters.isEmpty() || this.myFilters.stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.filters.OrFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj2) {
                return ((ElementFilter) obj2).isAcceptable(obj, psiElement);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isClassAcceptable(final Class cls) {
        return this.myFilters.isEmpty() || this.myFilters.stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.filters.OrFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((ElementFilter) obj).isClassAcceptable(cls);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public String toString() {
        return "(" + StringUtil.join((Iterable<?>) this.myFilters, " | ") + ')';
    }
}
