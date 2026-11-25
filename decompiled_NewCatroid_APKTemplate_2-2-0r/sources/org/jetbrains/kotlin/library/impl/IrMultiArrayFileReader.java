package org.jetbrains.kotlin.library.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;

/* JADX INFO: compiled from: IrFileReaders.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrMultiArrayFileReader;", "Lorg/jetbrains/kotlin/library/impl/IrMultiArrayReader;", "file", "Lorg/jetbrains/kotlin/konan/file/File;", "(Lorg/jetbrains/kotlin/konan/file/File;)V", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class IrMultiArrayFileReader extends IrMultiArrayReader {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrMultiArrayFileReader(File file) {
        super(new ReadBuffer.WeakFileBuffer(IrFileReadersKt.javaFile(file)));
        Intrinsics.checkNotNullParameter(file, "file");
    }
}
