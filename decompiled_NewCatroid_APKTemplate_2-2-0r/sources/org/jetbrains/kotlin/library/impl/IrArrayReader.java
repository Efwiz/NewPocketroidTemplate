package org.jetbrains.kotlin.library.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrFileReaders.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrArrayReader;", "", "buffer", "Lorg/jetbrains/kotlin/library/impl/ReadBuffer;", "(Lorg/jetbrains/kotlin/library/impl/ReadBuffer;)V", "indexToOffset", "", "entryCount", "", "tableItemBytes", "", "id", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class IrArrayReader {
    private final ReadBuffer buffer;
    private final int[] indexToOffset;

    public IrArrayReader(ReadBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        this.buffer = buffer;
        int i = buffer.getInt();
        int i2 = i + 1;
        int[] iArr = new int[i2];
        this.indexToOffset = iArr;
        int i3 = 0;
        iArr[0] = i2 * 4;
        while (i3 < i) {
            int i4 = this.buffer.getInt();
            int[] iArr2 = this.indexToOffset;
            int i5 = i3 + 1;
            iArr2[i5] = iArr2[i3] + i4;
            i3 = i5;
        }
    }

    public final int entryCount() {
        return this.indexToOffset.length - 1;
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
}
