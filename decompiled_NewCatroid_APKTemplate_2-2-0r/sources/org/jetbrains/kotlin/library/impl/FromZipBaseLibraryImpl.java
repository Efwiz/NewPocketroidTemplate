package org.jetbrains.kotlin.library.impl;

import java.nio.file.FileSystem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.ZipUtilKt;
import org.jetbrains.kotlin.library.KotlinLibraryLayout;

/* JADX INFO: compiled from: KotlinLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/FromZipBaseLibraryImpl;", "Lorg/jetbrains/kotlin/library/KotlinLibraryLayout;", "zipped", "Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutImpl;", "zipFileSystem", "Ljava/nio/file/FileSystem;", "(Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutImpl;Ljava/nio/file/FileSystem;)V", "component", "", "getComponent", "()Ljava/lang/String;", "libFile", "Lorg/jetbrains/kotlin/konan/file/File;", "getLibFile", "()Lorg/jetbrains/kotlin/konan/file/File;", "libraryName", "getLibraryName", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class FromZipBaseLibraryImpl implements KotlinLibraryLayout {
    private final String component;
    private final File libFile;
    private final String libraryName;

    public FromZipBaseLibraryImpl(KotlinLibraryLayoutImpl zipped, FileSystem zipFileSystem) {
        Intrinsics.checkNotNullParameter(zipped, "zipped");
        Intrinsics.checkNotNullParameter(zipFileSystem, "zipFileSystem");
        this.libraryName = zipped.getLibraryName();
        this.libFile = ZipUtilKt.file(zipFileSystem, zipped.getLibFile());
        this.component = zipped.getComponent();
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public String getLibraryName() {
        return this.libraryName;
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public File getLibFile() {
        return this.libFile;
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public String getComponent() {
        return this.component;
    }
}
