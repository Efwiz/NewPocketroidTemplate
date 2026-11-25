package org.jetbrains.kotlin.com.intellij.lang.folding;

import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.PossiblyDumbAware;

/* JADX INFO: loaded from: classes3.dex */
public class CompositeFoldingBuilder extends FoldingBuilderEx implements PossiblyDumbAware {
    private final List<? extends FoldingBuilder> myBuilders;

    CompositeFoldingBuilder(List<? extends FoldingBuilder> list) {
        this.myBuilders = list;
    }

    public String toString() {
        return getClass().getSimpleName() + this.myBuilders;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.PossiblyDumbAware
    public boolean isDumbAware() {
        Iterator<? extends FoldingBuilder> it = this.myBuilders.iterator();
        while (it.hasNext()) {
            if (DumbService.isDumbAware(it.next())) {
                return true;
            }
        }
        return false;
    }
}
