package org.jetbrains.kotlin.buildtools.api.jvm;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.buildtools.api.ExperimentalBuildToolsApi;
import org.jetbrains.kotlin.buildtools.api.jvm.IncrementalCompilationApproachParameters;

/* JADX INFO: compiled from: JvmCompilationConfiguration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u000e\n\u0002\u0010\u001e\n\u0002\b\u0003\bg\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J\u0018\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0019\u001a\u00020\tH&J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0019\u001a\u00020\tH&J\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0016\u001a\u00020\u0005H&J\u001c\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u001eH&J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0019\u001a\u00020\tH&J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0019\u001a\u00020\tH&R\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u001a\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u000bR\u0012\u0010\u0014\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000bR\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006!À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/jvm/IncrementalJvmCompilationConfiguration;", "P", "Lorg/jetbrains/kotlin/buildtools/api/jvm/IncrementalCompilationApproachParameters;", "", "buildDir", "Ljava/io/File;", "getBuildDir", "()Ljava/io/File;", "forcedNonIncrementalMode", "", "getForcedNonIncrementalMode", "()Z", "incrementalCompilationCachesKeptInMemory", "getIncrementalCompilationCachesKeptInMemory", "outputDirs", "", "getOutputDirs", "()Ljava/util/Set;", "preciseCompilationResultsBackupEnabled", "getPreciseCompilationResultsBackupEnabled", "preciseJavaTrackingEnabled", "getPreciseJavaTrackingEnabled", "rootProjectDir", "getRootProjectDir", "forceNonIncrementalMode", "value", "keepIncrementalCompilationCachesInMemory", "setBuildDir", "setRootProjectDir", "useOutputDirs", "", "usePreciseCompilationResultsBackup", "usePreciseJavaTracking", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
@ExperimentalBuildToolsApi
public interface IncrementalJvmCompilationConfiguration<P extends IncrementalCompilationApproachParameters> {
    IncrementalJvmCompilationConfiguration<P> forceNonIncrementalMode(boolean value);

    File getBuildDir();

    boolean getForcedNonIncrementalMode();

    boolean getIncrementalCompilationCachesKeptInMemory();

    Set<File> getOutputDirs();

    boolean getPreciseCompilationResultsBackupEnabled();

    boolean getPreciseJavaTrackingEnabled();

    File getRootProjectDir();

    IncrementalJvmCompilationConfiguration<P> keepIncrementalCompilationCachesInMemory(boolean value);

    IncrementalJvmCompilationConfiguration<P> setBuildDir(File buildDir);

    IncrementalJvmCompilationConfiguration<P> setRootProjectDir(File rootProjectDir);

    IncrementalJvmCompilationConfiguration<P> useOutputDirs(Collection<? extends File> outputDirs);

    IncrementalJvmCompilationConfiguration<P> usePreciseCompilationResultsBackup(boolean value);

    IncrementalJvmCompilationConfiguration<P> usePreciseJavaTracking(boolean value);

    static /* synthetic */ IncrementalJvmCompilationConfiguration forceNonIncrementalMode$default(IncrementalJvmCompilationConfiguration incrementalJvmCompilationConfiguration, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: forceNonIncrementalMode");
        }
        if ((i & 1) != 0) {
            z = true;
        }
        return incrementalJvmCompilationConfiguration.forceNonIncrementalMode(z);
    }
}
