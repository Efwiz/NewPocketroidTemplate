package org.jetbrains.kotlin.buildtools.api.jvm;

import java.io.File;
import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.buildtools.api.ExperimentalBuildToolsApi;

/* JADX INFO: compiled from: JvmCompilationConfiguration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0003\bg\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0012\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u0004H&J\u0010\u0010\t\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0004H&J\u0010\u0010\n\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0004H&J\u0010\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\rH&J\u0016\u0010\u0010\u001a\u00020\u00002\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0012H&J\u0010\u0010\u0013\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0004H&J\u0010\u0010\u0014\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0004H&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/jvm/ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration;", "Lorg/jetbrains/kotlin/buildtools/api/jvm/IncrementalJvmCompilationConfiguration;", "Lorg/jetbrains/kotlin/buildtools/api/jvm/ClasspathSnapshotBasedIncrementalCompilationApproachParameters;", "assuredNoClasspathSnapshotsChanges", "", "getAssuredNoClasspathSnapshotsChanges", "()Z", "assureNoClasspathSnapshotsChanges", "value", "forceNonIncrementalMode", "keepIncrementalCompilationCachesInMemory", "setBuildDir", "buildDir", "Ljava/io/File;", "setRootProjectDir", "rootProjectDir", "useOutputDirs", "outputDirs", "", "usePreciseCompilationResultsBackup", "usePreciseJavaTracking", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
@ExperimentalBuildToolsApi
public interface ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration extends IncrementalJvmCompilationConfiguration<ClasspathSnapshotBasedIncrementalCompilationApproachParameters> {
    ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration assureNoClasspathSnapshotsChanges(boolean value);

    @Override // org.jetbrains.kotlin.buildtools.api.jvm.IncrementalJvmCompilationConfiguration
    ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration forceNonIncrementalMode(boolean value);

    boolean getAssuredNoClasspathSnapshotsChanges();

    @Override // org.jetbrains.kotlin.buildtools.api.jvm.IncrementalJvmCompilationConfiguration
    ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration keepIncrementalCompilationCachesInMemory(boolean value);

    @Override // org.jetbrains.kotlin.buildtools.api.jvm.IncrementalJvmCompilationConfiguration
    ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration setBuildDir(File buildDir);

    @Override // org.jetbrains.kotlin.buildtools.api.jvm.IncrementalJvmCompilationConfiguration
    ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration setRootProjectDir(File rootProjectDir);

    @Override // org.jetbrains.kotlin.buildtools.api.jvm.IncrementalJvmCompilationConfiguration
    ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration useOutputDirs(Collection<? extends File> outputDirs);

    @Override // org.jetbrains.kotlin.buildtools.api.jvm.IncrementalJvmCompilationConfiguration
    ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration usePreciseCompilationResultsBackup(boolean value);

    @Override // org.jetbrains.kotlin.buildtools.api.jvm.IncrementalJvmCompilationConfiguration
    ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration usePreciseJavaTracking(boolean value);

    static /* synthetic */ ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration assureNoClasspathSnapshotsChanges$default(ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration classpathSnapshotBasedIncrementalJvmCompilationConfiguration, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: assureNoClasspathSnapshotsChanges");
        }
        if ((i & 1) != 0) {
            z = true;
        }
        return classpathSnapshotBasedIncrementalJvmCompilationConfiguration.assureNoClasspathSnapshotsChanges(z);
    }
}
