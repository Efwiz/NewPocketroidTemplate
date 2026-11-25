package org.jetbrains.kotlin.incremental.storage;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RelocatableFileToPathConverter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/FileLocations;", "", "rootProjectDir", "Ljava/io/File;", "buildDir", "(Ljava/io/File;Ljava/io/File;)V", "getBuildDir", "()Ljava/io/File;", "getRootProjectDir", "getRelocatablePathConverterForOutputFiles", "Lorg/jetbrains/kotlin/incremental/storage/RelocatableFileToPathConverter;", "getRelocatablePathConverterForSourceFiles", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileLocations {
    private final File buildDir;
    private final File rootProjectDir;

    public FileLocations(File rootProjectDir, File buildDir) {
        Intrinsics.checkNotNullParameter(rootProjectDir, "rootProjectDir");
        Intrinsics.checkNotNullParameter(buildDir, "buildDir");
        this.rootProjectDir = rootProjectDir;
        this.buildDir = buildDir;
    }

    public final File getRootProjectDir() {
        return this.rootProjectDir;
    }

    public final File getBuildDir() {
        return this.buildDir;
    }

    public final RelocatableFileToPathConverter getRelocatablePathConverterForSourceFiles() {
        return new RelocatableFileToPathConverter(this.rootProjectDir);
    }

    public final RelocatableFileToPathConverter getRelocatablePathConverterForOutputFiles() {
        return new RelocatableFileToPathConverter(this.buildDir);
    }
}
