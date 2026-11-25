package org.jetbrains.kotlin.buildtools.api.jvm;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.buildtools.api.ExperimentalBuildToolsApi;

/* JADX INFO: compiled from: JvmCompilationConfiguration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/jvm/ClasspathSnapshotBasedIncrementalCompilationApproachParameters;", "Lorg/jetbrains/kotlin/buildtools/api/jvm/IncrementalCompilationApproachParameters;", "newClasspathSnapshotFiles", "", "Ljava/io/File;", "shrunkClasspathSnapshot", "(Ljava/util/List;Ljava/io/File;)V", "getNewClasspathSnapshotFiles", "()Ljava/util/List;", "getShrunkClasspathSnapshot", "()Ljava/io/File;", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
@ExperimentalBuildToolsApi
public final class ClasspathSnapshotBasedIncrementalCompilationApproachParameters implements IncrementalCompilationApproachParameters {
    private final List<File> newClasspathSnapshotFiles;
    private final File shrunkClasspathSnapshot;

    /* JADX WARN: Multi-variable type inference failed */
    public ClasspathSnapshotBasedIncrementalCompilationApproachParameters(List<? extends File> newClasspathSnapshotFiles, File shrunkClasspathSnapshot) {
        Intrinsics.checkNotNullParameter(newClasspathSnapshotFiles, "newClasspathSnapshotFiles");
        Intrinsics.checkNotNullParameter(shrunkClasspathSnapshot, "shrunkClasspathSnapshot");
        this.newClasspathSnapshotFiles = newClasspathSnapshotFiles;
        this.shrunkClasspathSnapshot = shrunkClasspathSnapshot;
    }

    public final List<File> getNewClasspathSnapshotFiles() {
        return this.newClasspathSnapshotFiles;
    }

    public final File getShrunkClasspathSnapshot() {
        return this.shrunkClasspathSnapshot;
    }
}
