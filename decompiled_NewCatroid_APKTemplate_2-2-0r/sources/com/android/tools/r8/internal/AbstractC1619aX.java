package com.android.tools.r8.internal;

import java.util.Spliterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1619aX implements Spliterator {
    final Spliterator a;
    long b;

    AbstractC1619aX(Spliterator spliterator, long j) {
        this.a = spliterator;
        this.b = j;
    }

    @Override // java.util.Spliterator
    public final int characteristics() {
        return this.a.characteristics() & 16464;
    }

    @Override // java.util.Spliterator
    public final long estimateSize() {
        return this.a.estimateSize();
    }

    @Override // java.util.Spliterator
    public final Spliterator trySplit() {
        Spliterator spliteratorTrySplit = this.a.trySplit();
        if (spliteratorTrySplit == null) {
            return null;
        }
        long j = this.b;
        XW xw = new XW(spliteratorTrySplit, j, ((XW) this).d);
        this.b = spliteratorTrySplit.getExactSizeIfKnown() + j;
        return xw;
    }
}
