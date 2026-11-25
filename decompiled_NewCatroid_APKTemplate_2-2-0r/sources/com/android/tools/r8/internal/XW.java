package com.android.tools.r8.internal;

import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class XW extends AbstractC1619aX implements Consumer {
    Object c;
    final /* synthetic */ ZW d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    XW(Spliterator spliterator, long j, ZW zw) {
        super(spliterator, j);
        this.d = zw;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.c = obj;
    }

    @Override // java.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        if (!this.a.tryAdvance(this)) {
            return false;
        }
        try {
            ZW zw = this.d;
            Object obj = this.c;
            long j = this.b;
            this.b = 1 + j;
            consumer.accept(zw.a(obj, j));
            return true;
        } finally {
            this.c = null;
        }
    }
}
