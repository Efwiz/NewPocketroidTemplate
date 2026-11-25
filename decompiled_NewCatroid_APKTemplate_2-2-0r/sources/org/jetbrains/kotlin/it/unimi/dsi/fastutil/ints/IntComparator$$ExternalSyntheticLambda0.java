package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.Serializable;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class IntComparator$$ExternalSyntheticLambda0 implements IntComparator, Serializable {
    public final /* synthetic */ IntComparator f$0;
    public final /* synthetic */ IntComparator f$1;

    public /* synthetic */ IntComparator$$ExternalSyntheticLambda0(IntComparator intComparator, IntComparator intComparator2) {
        this.f$0 = intComparator;
        this.f$1 = intComparator2;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntComparator
    public final int compare(int i, int i2) {
        return IntComparator.lambda$thenComparing$931d6fed$1(this.f$0, this.f$1, i, i2);
    }
}
