package org.jetbrains.kotlin.library.impl;

import java.nio.file.FileSystem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.MetadataKotlinLibraryLayout;

/* JADX INFO: compiled from: KotlinLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/FromZipMetadataLibraryImpl;", "Lorg/jetbrains/kotlin/library/impl/FromZipBaseLibraryImpl;", "Lorg/jetbrains/kotlin/library/MetadataKotlinLibraryLayout;", "zipped", "Lorg/jetbrains/kotlin/library/impl/MetadataLibraryLayoutImpl;", "zipFileSystem", "Ljava/nio/file/FileSystem;", "(Lorg/jetbrains/kotlin/library/impl/MetadataLibraryLayoutImpl;Ljava/nio/file/FileSystem;)V", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class FromZipMetadataLibraryImpl extends FromZipBaseLibraryImpl implements MetadataKotlinLibraryLayout {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromZipMetadataLibraryImpl(MetadataLibraryLayoutImpl zipped, FileSystem zipFileSystem) {
        super(zipped, zipFileSystem);
        Intrinsics.checkNotNullParameter(zipped, "zipped");
        Intrinsics.checkNotNullParameter(zipFileSystem, "zipFileSystem");
    }
}
