package org.jetbrains.kotlin.com.intellij.psi.filters;

import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public class AndFilter implements ElementFilter {
    private final ElementFilter[] myFilters;

    public AndFilter(ElementFilter elementFilter, ElementFilter elementFilter2) {
        this.myFilters = new ElementFilter[]{elementFilter, elementFilter2};
    }

    public AndFilter(ElementFilter... elementFilterArr) {
        this.myFilters = elementFilterArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isAcceptable(final Object obj, final PsiElement psiElement) {
        return Stream.of((Object[]) this.myFilters).allMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.filters.AndFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj2) {
                return ((ElementFilter) obj2).isAcceptable(obj, psiElement);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isClassAcceptable(final Class cls) {
        return Stream.of((Object[]) this.myFilters).allMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.filters.AndFilter$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((ElementFilter) obj).isClassAcceptable(cls);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public String toString() {
        return "(" + StringUtil.join(this.myFilters, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.filters.AndFilter$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((ElementFilter) obj).toString();
            }
        }, " & ") + ')';
    }
}
