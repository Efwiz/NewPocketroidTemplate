package org.jetbrains.kotlin.com.intellij.psi.filters.position;

import org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition;
import org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public class FilterPattern extends ObjectPattern<Object, FilterPattern> {
    private final ElementFilter myFilter;

    public FilterPattern(final ElementFilter elementFilter) {
        super(new InitialPatternCondition<Object>(Object.class) { // from class: org.jetbrains.kotlin.com.intellij.psi.filters.position.FilterPattern.1
            @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
            public boolean accepts(Object obj, ProcessingContext processingContext) {
                ElementFilter elementFilter2 = elementFilter;
                if (elementFilter2 != null) {
                    if (obj != null && elementFilter2.isClassAcceptable(obj.getClass())) {
                        if (elementFilter.isAcceptable(obj, obj instanceof PsiElement ? (PsiElement) obj : null)) {
                        }
                    }
                    return false;
                }
                return true;
            }
        });
        this.myFilter = elementFilter;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FilterPattern)) {
            return false;
        }
        ElementFilter elementFilter = this.myFilter;
        ElementFilter elementFilter2 = ((FilterPattern) obj).myFilter;
        return elementFilter == null ? elementFilter2 == null : elementFilter.equals(elementFilter2);
    }

    public int hashCode() {
        ElementFilter elementFilter = this.myFilter;
        if (elementFilter != null) {
            return elementFilter.hashCode();
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern
    public String toString() {
        return super.toString() + " & " + this.myFilter;
    }
}
