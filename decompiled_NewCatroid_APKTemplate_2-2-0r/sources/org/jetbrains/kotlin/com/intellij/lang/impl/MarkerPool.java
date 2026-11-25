package org.jetbrains.kotlin.com.intellij.lang.impl;

import org.jetbrains.kotlin.com.intellij.util.containers.IntStack;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectArrayList;

/* JADX INFO: loaded from: classes3.dex */
final class MarkerPool extends ObjectArrayList<PsiBuilderImpl.ProductionMarker> {
    private final PsiBuilderImpl myBuilder;
    private final IntStack myFreeStartMarkers = new IntStack();
    private final IntStack myFreeErrorItems = new IntStack();

    MarkerPool(PsiBuilderImpl psiBuilderImpl) {
        this.myBuilder = psiBuilderImpl;
        add(null);
    }

    PsiBuilderImpl.StartMarker allocateStartMarker() {
        if (this.myFreeStartMarkers.size() > 0) {
            return (PsiBuilderImpl.StartMarker) get(this.myFreeStartMarkers.pop());
        }
        PsiBuilderImpl.StartMarker startMarker = new PsiBuilderImpl.StartMarker(size(), this.myBuilder);
        add(startMarker);
        return startMarker;
    }

    PsiBuilderImpl.ErrorItem allocateErrorItem() {
        if (this.myFreeErrorItems.size() > 0) {
            return (PsiBuilderImpl.ErrorItem) get(this.myFreeErrorItems.pop());
        }
        PsiBuilderImpl.ErrorItem errorItem = new PsiBuilderImpl.ErrorItem(size(), this.myBuilder);
        add(errorItem);
        return errorItem;
    }

    void freeMarker(PsiBuilderImpl.ProductionMarker productionMarker) {
        productionMarker.clean();
        (productionMarker instanceof PsiBuilderImpl.StartMarker ? this.myFreeStartMarkers : this.myFreeErrorItems).push(productionMarker.markerId);
    }
}
