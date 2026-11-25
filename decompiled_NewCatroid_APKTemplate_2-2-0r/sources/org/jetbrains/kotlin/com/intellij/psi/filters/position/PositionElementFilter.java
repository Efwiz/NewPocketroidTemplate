package org.jetbrains.kotlin.com.intellij.psi.filters.position;

import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PositionElementFilter implements ElementFilter {
    private ElementFilter myFilter;

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isClassAcceptable(Class cls) {
        return true;
    }

    public void setFilter(ElementFilter elementFilter) {
        this.myFilter = elementFilter;
    }

    public ElementFilter getFilter() {
        return this.myFilter;
    }
}
