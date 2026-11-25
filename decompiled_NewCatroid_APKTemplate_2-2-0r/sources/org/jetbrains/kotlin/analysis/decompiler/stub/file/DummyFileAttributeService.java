package org.jetbrains.kotlin.analysis.decompiler.stub.file;

import androidx.exifinterface.media.ExifInterface;
import java.io.DataInput;
import java.io.DataOutput;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: compiled from: FileAttributeService.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J:\u0010\u0003\u001a\n\u0012\u0004\u0012\u0002H\u0005\u0018\u00010\u0004\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u0002H\u00050\u000bH\u0016JK\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u0002H\u00052\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00120\u0010H\u0016¢\u0006\u0002\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/DummyFileAttributeService;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/FileAttributeService;", "()V", "read", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/CachedAttributeData;", ExifInterface.GPS_DIRECTION_TRUE, "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "id", "", "readValueFun", "Lkotlin/Function1;", "Ljava/io/DataInput;", "write", "value", "writeValueFun", "Lkotlin/Function2;", "Ljava/io/DataOutput;", "", "(Lcom/intellij/openapi/vfs/VirtualFile;Ljava/lang/String;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/CachedAttributeData;", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DummyFileAttributeService implements FileAttributeService {
    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.file.FileAttributeService
    public <T> CachedAttributeData<T> read(VirtualFile file, String id2, Function1<? super DataInput, ? extends T> readValueFun) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(readValueFun, "readValueFun");
        return null;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.file.FileAttributeService
    public <T> CachedAttributeData<T> write(VirtualFile file, String id2, T t, Function2<? super DataOutput, ? super T, Unit> writeValueFun) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(writeValueFun, "writeValueFun");
        return new CachedAttributeData<>(t, 0L);
    }
}
