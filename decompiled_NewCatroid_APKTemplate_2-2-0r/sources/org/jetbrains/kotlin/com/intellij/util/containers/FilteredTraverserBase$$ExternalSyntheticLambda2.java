package org.jetbrains.kotlin.com.intellij.util.containers;

import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class FilteredTraverserBase$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ FilteredTraverserBase.Meta f$0;

    public /* synthetic */ FilteredTraverserBase$$ExternalSyntheticLambda2(FilteredTraverserBase.Meta meta) {
        this.f$0 = meta;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.Function
    public final Object fun(Object obj) {
        return this.f$0.children(obj);
    }
}
