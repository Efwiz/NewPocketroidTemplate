package org.jetbrains.kotlin.library.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrFileReaders.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0007H\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0007J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrMultiArrayReader;", "", "buffer", "Lorg/jetbrains/kotlin/library/impl/ReadBuffer;", "(Lorg/jetbrains/kotlin/library/impl/ReadBuffer;)V", "indexIndexToOffset", "", "", "", "indexToOffset", "readOffsets", "position", "tableItemBytes", "", "id", "row", "column", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class IrMultiArrayReader {
    private final ReadBuffer buffer;
    private final Map<Integer, int[]> indexIndexToOffset;
    private final int[] indexToOffset;

    public IrMultiArrayReader(ReadBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        this.buffer = buffer;
        this.indexIndexToOffset = new LinkedHashMap();
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

    public final byte[] tableItemBytes(int id2) {
        int[] iArr = this.indexToOffset;
        int i = iArr[id2];
        int i2 = iArr[id2 + 1] - i;
        byte[] bArr = new byte[i2];
        this.buffer.setPosition(i);
        this.buffer.get(bArr, 0, i2);
        return bArr;
    }

    public final byte[] tableItemBytes(int row, int column) {
        int i = this.indexToOffset[row];
        Map<Integer, int[]> map = this.indexIndexToOffset;
        Integer numValueOf = Integer.valueOf(row);
        int[] offsets = map.get(numValueOf);
        if (offsets == null) {
            offsets = readOffsets(i);
            map.put(numValueOf, offsets);
        }
        int[] iArr = offsets;
        int i2 = iArr[column];
        int i3 = iArr[column + 1] - i2;
        byte[] bArr = new byte[i3];
        this.buffer.setPosition(i + i2);
        this.buffer.get(bArr, 0, i3);
        return bArr;
    }
}
