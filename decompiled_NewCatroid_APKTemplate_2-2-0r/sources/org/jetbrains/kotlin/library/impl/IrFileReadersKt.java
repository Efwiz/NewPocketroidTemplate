package org.jetbrains.kotlin.library.impl;

import java.io.File;
import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrFileReaders.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0007\u001a\u0015\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\t*\u00020\n¢\u0006\u0002\u0010\u000b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\f"}, d2 = {"buffer", "Ljava/nio/ByteBuffer;", "", "getBuffer", "([B)Ljava/nio/ByteBuffer;", "javaFile", "Ljava/io/File;", "Lorg/jetbrains/kotlin/konan/file/File;", "toArray", "", "Lorg/jetbrains/kotlin/library/impl/IrArrayReader;", "(Lorg/jetbrains/kotlin/library/impl/IrArrayReader;)[[B", "kotlin-util-klib"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class IrFileReadersKt {
    public static final ByteBuffer getBuffer(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        Intrinsics.checkNotNullExpressionValue(byteBufferWrap, "wrap(this)");
        return byteBufferWrap;
    }

    public static final File javaFile(org.jetbrains.kotlin.konan.file.File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return new File(file.getPath());
    }

    public static final byte[][] toArray(IrArrayReader irArrayReader) {
        Intrinsics.checkNotNullParameter(irArrayReader, "<this>");
        int iEntryCount = irArrayReader.entryCount();
        byte[][] bArr = new byte[iEntryCount][];
        for (int i = 0; i < iEntryCount; i++) {
            bArr[i] = irArrayReader.tableItemBytes(i);
        }
        return bArr;
    }
}
