package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.io.Serializable;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class LongComparator$$ExternalSyntheticLambda0 implements LongComparator, Serializable {
    public final /* synthetic */ LongComparator f$0;
    public final /* synthetic */ LongComparator f$1;

    public /* synthetic */ LongComparator$$ExternalSyntheticLambda0(LongComparator longComparator, LongComparator longComparator2) {
        this.f$0 = longComparator;
        this.f$1 = longComparator2;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongComparator
    public final int compare(long j, long j2) {
        return LongComparator.lambda$thenComparing$3d6e68ef$1(this.f$0, this.f$1, j, j2);
    }
}
