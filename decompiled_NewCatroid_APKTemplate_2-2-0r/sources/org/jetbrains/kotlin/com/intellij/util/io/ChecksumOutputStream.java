package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.OutputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Checksum;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;

/* JADX INFO: compiled from: writeAheadLog.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/ChecksumOutputStream;", "Ljava/util/zip/CheckedOutputStream;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Ljava/io/OutputStream;", "checksumGen", "Lkotlin/Function0;", "Ljava/util/zip/Checksum;", "(Ljava/io/OutputStream;Lkotlin/jvm/functions/Function0;)V", "checksum", "", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
final class ChecksumOutputStream extends CheckedOutputStream {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChecksumOutputStream(OutputStream delegate, Function0<? extends Checksum> checksumGen) {
        super(delegate, checksumGen.invoke());
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(checksumGen, "checksumGen");
    }

    public final long checksum() {
        Checksum checksum = getChecksum();
        Intrinsics.checkNotNullExpressionValue(checksum, "checksum");
        return checksum.getValue();
    }
}
