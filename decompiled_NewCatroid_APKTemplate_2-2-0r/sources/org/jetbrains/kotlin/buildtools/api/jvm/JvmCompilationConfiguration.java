package org.jetbrains.kotlin.buildtools.api.jvm;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.buildtools.api.ExperimentalBuildToolsApi;
import org.jetbrains.kotlin.buildtools.api.KotlinLogger;
import org.jetbrains.kotlin.buildtools.api.SourcesChanges;

/* JADX INFO: compiled from: JvmCompilationConfiguration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\b\u0010\u000b\u001a\u00020\fH&J=\u0010\r\u001a\u00020\u000e\"\b\b\u0000\u0010\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u0002H\u000f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0017H\u0016¢\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u00002\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u001bH&J\u0010\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bH&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001dÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/jvm/JvmCompilationConfiguration;", "", "kotlinScriptFilenameExtensions", "", "", "getKotlinScriptFilenameExtensions", "()Ljava/util/Set;", "logger", "Lorg/jetbrains/kotlin/buildtools/api/KotlinLogger;", "getLogger", "()Lorg/jetbrains/kotlin/buildtools/api/KotlinLogger;", "makeClasspathSnapshotBasedIncrementalCompilationConfiguration", "Lorg/jetbrains/kotlin/buildtools/api/jvm/ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration;", "useIncrementalCompilation", "", "P", "Lorg/jetbrains/kotlin/buildtools/api/jvm/IncrementalCompilationApproachParameters;", "workingDirectory", "Ljava/io/File;", "sourcesChanges", "Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges;", "approachParameters", "options", "Lorg/jetbrains/kotlin/buildtools/api/jvm/IncrementalJvmCompilationConfiguration;", "(Ljava/io/File;Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges;Lorg/jetbrains/kotlin/buildtools/api/jvm/IncrementalCompilationApproachParameters;Lorg/jetbrains/kotlin/buildtools/api/jvm/IncrementalJvmCompilationConfiguration;)V", "useKotlinScriptFilenameExtensions", "kotlinScriptExtensions", "", "useLogger", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
@ExperimentalBuildToolsApi
public interface JvmCompilationConfiguration {
    Set<String> getKotlinScriptFilenameExtensions();

    KotlinLogger getLogger();

    ClasspathSnapshotBasedIncrementalJvmCompilationConfiguration makeClasspathSnapshotBasedIncrementalCompilationConfiguration();

    JvmCompilationConfiguration useKotlinScriptFilenameExtensions(Collection<String> kotlinScriptExtensions);

    JvmCompilationConfiguration useLogger(KotlinLogger logger);

    default <P extends IncrementalCompilationApproachParameters> void useIncrementalCompilation(File workingDirectory, SourcesChanges sourcesChanges, P approachParameters, IncrementalJvmCompilationConfiguration<P> options) {
        Intrinsics.checkNotNullParameter(workingDirectory, "workingDirectory");
        Intrinsics.checkNotNullParameter(sourcesChanges, "sourcesChanges");
        Intrinsics.checkNotNullParameter(approachParameters, "approachParameters");
        Intrinsics.checkNotNullParameter(options, "options");
        throw new IllegalStateException("This version of the Build Tools API does not support incremental compilation".toString());
    }
}
