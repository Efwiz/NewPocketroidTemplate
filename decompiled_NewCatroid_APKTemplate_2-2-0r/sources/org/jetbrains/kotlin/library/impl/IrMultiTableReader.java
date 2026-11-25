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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0005\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B&\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\b\u0007¢\u0006\u0002\u0010\bJ(\u0010\u0010\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\r0\f2\u0006\u0010\u0011\u001a\u00020\u000bH\u0002J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000bH\u0002J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ\u001b\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00028\u0000¢\u0006\u0002\u0010\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R2\u0010\t\u001a&\u0012\u0004\u0012\u00020\u000b\u0012\u001c\u0012\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\r0\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001f\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\b\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrMultiTableReader;", "K", "", "buffer", "Lorg/jetbrains/kotlin/library/impl/ReadBuffer;", "keyReader", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/library/impl/ReadBuffer;Lkotlin/jvm/functions/Function1;)V", "indexToIndexMap", "", "", "", "Lkotlin/Pair;", "indexToOffset", "", "readIndexMap", "position", "readOffsets", "tableItemBytes", "", "idx", "row", "id", "(ILjava/lang/Object;)[B", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class IrMultiTableReader<K> {
    private final ReadBuffer buffer;
    private final Map<Integer, Map<K, Pair<Integer, Integer>>> indexToIndexMap;
    private final int[] indexToOffset;
    private final Function1<ReadBuffer, K> keyReader;

    /* JADX WARN: Multi-variable type inference failed */
    public IrMultiTableReader(ReadBuffer buffer, Function1<? super ReadBuffer, ? extends K> keyReader) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        Intrinsics.checkNotNullParameter(keyReader, "keyReader");
        this.buffer = buffer;
        this.keyReader = keyReader;
        this.indexToIndexMap = new LinkedHashMap();
        this.indexToOffset = readOffsets(0);
    }

    private final int[] readOffsets(int position) {
        this.buffer.setPosition(position);
        int i = this.buffer.getInt();
        int i2 = i + 1;
        int[] iArr = new int[i2];
        int i3 = 0;
        iArr[0] = i2 * 4;
        while (i3 < i) {
            int i4 = i3 + 1;
            iArr[i4] = iArr[i3] + this.buffer.getInt();
            i3 = i4;
        }
        return iArr;
    }

    private final Map<K, Pair<Integer, Integer>> readIndexMap(int position) {
        this.buffer.setPosition(position);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int i = this.buffer.getInt();
        for (int i2 = 0; i2 < i; i2++) {
            linkedHashMap.put(this.keyReader.invoke(this.buffer), TuplesKt.to(Integer.valueOf(this.buffer.getInt()), Integer.valueOf(this.buffer.getInt())));
        }
        return linkedHashMap;
    }

    public final byte[] tableItemBytes(int idx) {
        int[] iArr = this.indexToOffset;
        int i = iArr[idx];
        int i2 = iArr[idx + 1] - i;
        byte[] bArr = new byte[i2];
        this.buffer.setPosition(i);
        this.buffer.get(bArr, 0, i2);
        return bArr;
    }

    public final byte[] tableItemBytes(int row, K id2) {
        int i = this.indexToOffset[row];
        Map<Integer, Map<K, Pair<Integer, Integer>>> map = this.indexToIndexMap;
        Integer numValueOf = Integer.valueOf(row);
        Map<K, Pair<Integer, Integer>> indexMap = map.get(numValueOf);
        if (indexMap == null) {
            indexMap = readIndexMap(i);
            map.put(numValueOf, indexMap);
        }
        Pair<Integer, Integer> pair = indexMap.get(id2);
        if (pair == null) {
            throw new IllegalStateException(("No coordinates found for " + id2).toString());
        }
        int iIntValue = pair.getFirst().intValue();
        int iIntValue2 = pair.getSecond().intValue();
        byte[] bArr = new byte[iIntValue2];
        this.buffer.setPosition(i + iIntValue);
        this.buffer.get(bArr, 0, iIntValue2);
        return bArr;
    }
}
