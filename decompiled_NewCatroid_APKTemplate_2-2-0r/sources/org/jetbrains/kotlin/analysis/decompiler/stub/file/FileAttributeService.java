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
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J:\u0010\u0002\u001a\n\u0012\u0004\u0012\u0002H\u0004\u0018\u00010\u0003\"\u0004\b\u0000\u0010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u0002H\u00040\nH&J \u0010\f\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J>\u0010\u000e\u001a\n\u0012\u0004\u0012\u0002H\u0004\u0018\u00010\u0003\"\u000e\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0011H\u0016J\"\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\rH\u0016JK\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0018\u001a\u0002H\u00042\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00130\u001aH&¢\u0006\u0002\u0010\u001cJ&\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\rH\u0016J;\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u000e\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u0002H\u0004H\u0016¢\u0006\u0002\u0010\u001fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006 À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/FileAttributeService;", "", "read", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/CachedAttributeData;", ExifInterface.GPS_DIRECTION_TRUE, "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "id", "", "readValueFun", "Lkotlin/Function1;", "Ljava/io/DataInput;", "readBooleanAttribute", "", "readEnumAttribute", "", "klass", "Ljava/lang/Class;", "register", "", "version", "", "fixedSize", "write", "value", "writeValueFun", "Lkotlin/Function2;", "Ljava/io/DataOutput;", "(Lcom/intellij/openapi/vfs/VirtualFile;Ljava/lang/String;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/CachedAttributeData;", "writeBooleanAttribute", "writeEnumAttribute", "(Ljava/lang/String;Lcom/intellij/openapi/vfs/VirtualFile;Ljava/lang/Enum;)Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/CachedAttributeData;", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FileAttributeService {
    <T> CachedAttributeData<T> read(VirtualFile file, String id2, Function1<? super DataInput, ? extends T> readValueFun);

    default CachedAttributeData<Boolean> readBooleanAttribute(String id2, VirtualFile file) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(file, "file");
        return null;
    }

    default <T extends Enum<T>> CachedAttributeData<T> readEnumAttribute(String id2, VirtualFile file, Class<T> klass) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(klass, "klass");
        return null;
    }

    default void register(String id2, int version, boolean fixedSize) {
        Intrinsics.checkNotNullParameter(id2, "id");
    }

    <T> CachedAttributeData<T> write(VirtualFile virtualFile, String str, T t, Function2<? super DataOutput, ? super T, Unit> function2);

    static /* synthetic */ void register$default(FileAttributeService fileAttributeService, String str, int i, boolean z, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: register");
        }
        if ((i2 & 4) != 0) {
            z = true;
        }
        fileAttributeService.register(str, i, z);
    }

    default <T extends Enum<T>> CachedAttributeData<T> writeEnumAttribute(String id2, VirtualFile file, T value) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(value, "value");
        return new CachedAttributeData<>(value, file.getTimeStamp());
    }

    default CachedAttributeData<Boolean> writeBooleanAttribute(String id2, VirtualFile file, boolean value) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(file, "file");
        return new CachedAttributeData<>(Boolean.valueOf(value), file.getTimeStamp());
    }
}
