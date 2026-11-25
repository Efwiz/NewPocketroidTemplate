package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.io.Serializable;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class ByteComparator$$ExternalSyntheticLambda0 implements ByteComparator, Serializable {
    public final /* synthetic */ ByteComparator f$0;
    public final /* synthetic */ ByteComparator f$1;

    public /* synthetic */ ByteComparator$$ExternalSyntheticLambda0(ByteComparator byteComparator, ByteComparator byteComparator2) {
        this.f$0 = byteComparator;
        this.f$1 = byteComparator2;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteComparator
    public final int compare(byte b, byte b2) {
        return ByteComparator.lambda$thenComparing$6e387fbf$1(this.f$0, this.f$1, b, b2);
    }
}
