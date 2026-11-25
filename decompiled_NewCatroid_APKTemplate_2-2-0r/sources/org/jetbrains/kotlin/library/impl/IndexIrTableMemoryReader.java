package org.jetbrains.kotlin.library.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrFileReaders.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IndexIrTableMemoryReader;", "Lorg/jetbrains/kotlin/library/impl/IrTableReader;", "", "bytes", "", "([B)V", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class IndexIrTableMemoryReader extends IrTableReader<Long> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IndexIrTableMemoryReader(byte[] bytes) {
        super(new ReadBuffer.MemoryBuffer(bytes), new Function1<ReadBuffer, Long>() { // from class: org.jetbrains.kotlin.library.impl.IndexIrTableMemoryReader.1
            @Override // kotlin.jvm.functions.Function1
            public final Long invoke(ReadBuffer readBuffer) {
                Intrinsics.checkNotNullParameter(readBuffer, "$this$null");
                return Long.valueOf(readBuffer.getLong());
            }
        });
        Intrinsics.checkNotNullParameter(bytes, "bytes");
    }
}
