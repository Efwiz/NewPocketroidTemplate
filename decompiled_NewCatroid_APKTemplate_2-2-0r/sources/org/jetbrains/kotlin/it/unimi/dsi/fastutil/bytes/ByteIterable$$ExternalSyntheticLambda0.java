package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.function.Consumer;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class ByteIterable$$ExternalSyntheticLambda0 implements ByteConsumer {
    public final /* synthetic */ Consumer f$0;

    public /* synthetic */ ByteIterable$$ExternalSyntheticLambda0(Consumer consumer) {
        this.f$0 = consumer;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteConsumer
    public final void accept(byte b) {
        this.f$0.accept(Byte.valueOf(b));
    }
}
