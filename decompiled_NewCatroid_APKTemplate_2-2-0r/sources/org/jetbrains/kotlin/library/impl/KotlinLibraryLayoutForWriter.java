package org.jetbrains.kotlin.library.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.IrKotlinLibraryLayout;
import org.jetbrains.kotlin.library.KotlinLibraryLayout;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.library.MetadataKotlinLibraryLayout;

/* JADX INFO: compiled from: KotlinLibraryWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutForWriter;", "Lorg/jetbrains/kotlin/library/KotlinLibraryLayout;", "Lorg/jetbrains/kotlin/library/MetadataKotlinLibraryLayout;", "Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;", "libFile", "Lorg/jetbrains/kotlin/konan/file/File;", "unzippedDir", "component", "", "(Lorg/jetbrains/kotlin/konan/file/File;Lorg/jetbrains/kotlin/konan/file/File;Ljava/lang/String;)V", "getComponent", "()Ljava/lang/String;", "componentDir", "getComponentDir", "()Lorg/jetbrains/kotlin/konan/file/File;", "getLibFile", "pre_1_4_manifest", "getPre_1_4_manifest", "getUnzippedDir", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class KotlinLibraryLayoutForWriter implements IrKotlinLibraryLayout, KotlinLibraryLayout, MetadataKotlinLibraryLayout {
    private final String component;
    private final File libFile;
    private final File unzippedDir;

    public KotlinLibraryLayoutForWriter(File libFile, File unzippedDir, String component) {
        Intrinsics.checkNotNullParameter(libFile, "libFile");
        Intrinsics.checkNotNullParameter(unzippedDir, "unzippedDir");
        Intrinsics.checkNotNullParameter(component, "component");
        this.libFile = libFile;
        this.unzippedDir = unzippedDir;
        this.component = component;
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public File getLibFile() {
        return this.libFile;
    }

    public final File getUnzippedDir() {
        return this.unzippedDir;
    }

    public /* synthetic */ KotlinLibraryLayoutForWriter(File file, File file2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, file2, (i & 4) != 0 ? "default" : str);
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public String getComponent() {
        return this.component;
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public File getComponentDir() {
        return new File(this.unzippedDir, getComponent());
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public File getPre_1_4_manifest() {
        return new File(this.unzippedDir, KotlinLibraryLayoutKt.KLIB_MANIFEST_FILE_NAME);
    }
}
