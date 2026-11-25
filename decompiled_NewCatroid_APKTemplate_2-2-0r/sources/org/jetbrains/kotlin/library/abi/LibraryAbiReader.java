package org.jetbrains.kotlin.library.abi;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.abi.impl.LibraryAbiReaderImpl;

/* JADX INFO: compiled from: LibraryAbiReader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\b\"\u00020\t¢\u0006\u0002\u0010\nJ\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/LibraryAbiReader;", "", "()V", "readAbiInfo", "Lorg/jetbrains/kotlin/library/abi/LibraryAbi;", "library", "Ljava/io/File;", "filters", "", "Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter;", "(Ljava/io/File;[Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter;)Lorg/jetbrains/kotlin/library/abi/LibraryAbi;", "", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class LibraryAbiReader {
    public static final LibraryAbiReader INSTANCE = new LibraryAbiReader();

    private LibraryAbiReader() {
    }

    public final LibraryAbi readAbiInfo(File library, AbiReadingFilter... filters) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(filters, "filters");
        return readAbiInfo(library, ArraysKt.asList(filters));
    }

    public final LibraryAbi readAbiInfo(File library, List<? extends AbiReadingFilter> filters) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(filters, "filters");
        return new LibraryAbiReaderImpl(library, filters).readAbi();
    }
}
