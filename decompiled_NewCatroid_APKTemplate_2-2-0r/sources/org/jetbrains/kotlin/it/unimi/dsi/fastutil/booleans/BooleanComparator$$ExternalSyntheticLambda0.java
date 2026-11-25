package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.io.Serializable;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class BooleanComparator$$ExternalSyntheticLambda0 implements BooleanComparator, Serializable {
    public final /* synthetic */ BooleanComparator f$0;
    public final /* synthetic */ BooleanComparator f$1;

    public /* synthetic */ BooleanComparator$$ExternalSyntheticLambda0(BooleanComparator booleanComparator, BooleanComparator booleanComparator2) {
        this.f$0 = booleanComparator;
        this.f$1 = booleanComparator2;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanComparator
    public final int compare(boolean z, boolean z2) {
        return BooleanComparator.lambda$thenComparing$e8be742d$1(this.f$0, this.f$1, z, z2);
    }
}
