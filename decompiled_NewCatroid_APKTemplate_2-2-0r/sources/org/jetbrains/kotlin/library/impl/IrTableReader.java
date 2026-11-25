package org.jetbrains.kotlin.library.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrFileReaders.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B&\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\b\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000¢\u0006\u0002\u0010\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\t\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrTableReader;", "K", "", "buffer", "Lorg/jetbrains/kotlin/library/impl/ReadBuffer;", "keyReader", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/library/impl/ReadBuffer;Lkotlin/jvm/functions/Function1;)V", "indexToOffset", "", "Lkotlin/Pair;", "", "tableItemBytes", "", "id", "(Ljava/lang/Object;)[B", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class IrTableReader<K> {
    private final ReadBuffer buffer;
    private final Map<K, Pair<Integer, Integer>> indexToOffset;

    public IrTableReader(ReadBuffer buffer, Function1<? super ReadBuffer, ? extends K> keyReader) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        Intrinsics.checkNotNullParameter(keyReader, "keyReader");
        this.buffer = buffer;
        this.indexToOffset = new LinkedHashMap();
        int i = buffer.getInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.indexToOffset.put(keyReader.invoke(this.buffer), TuplesKt.to(Integer.valueOf(this.buffer.getInt()), Integer.valueOf(this.buffer.getInt())));
        }
    }

    public final byte[] tableItemBytes(K id2) {
        Pair<Integer, Integer> pair = this.indexToOffset.get(id2);
        if (pair == null) {
            throw new IllegalStateException(("No coordinates found for " + id2).toString());
        }
        int iIntValue = pair.getFirst().intValue();
        int iIntValue2 = pair.getSecond().intValue();
        byte[] bArr = new byte[iIntValue2];
        this.buffer.setPosition(iIntValue);
        this.buffer.get(bArr, 0, iIntValue2);
        return bArr;
    }
}
