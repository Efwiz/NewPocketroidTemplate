package org.jetbrains.kotlin.buildtools.api;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.buildtools.api.jvm.ClassSnapshotGranularity;
import org.jetbrains.kotlin.buildtools.api.jvm.ClasspathEntrySnapshot;
import org.jetbrains.kotlin.buildtools.api.jvm.JvmCompilationConfiguration;

/* JADX INFO: compiled from: CompilationService.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bg\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J<\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0011H&J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u000bH&J\b\u0010\u0016\u001a\u00020\rH&J\b\u0010\u0017\u001a\u00020\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0019À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/CompilationService;", "", "calculateClasspathSnapshot", "Lorg/jetbrains/kotlin/buildtools/api/jvm/ClasspathEntrySnapshot;", "classpathEntry", "Ljava/io/File;", "granularity", "Lorg/jetbrains/kotlin/buildtools/api/jvm/ClassSnapshotGranularity;", "compileJvm", "Lorg/jetbrains/kotlin/buildtools/api/CompilationResult;", Constants.EXTRA_PROJECT_ID, "Lorg/jetbrains/kotlin/buildtools/api/ProjectId;", "strategyConfig", "Lorg/jetbrains/kotlin/buildtools/api/CompilerExecutionStrategyConfiguration;", "compilationConfig", "Lorg/jetbrains/kotlin/buildtools/api/jvm/JvmCompilationConfiguration;", "sources", "", "arguments", "", "finishProjectCompilation", "", "makeCompilerExecutionStrategyConfiguration", "makeJvmCompilationConfiguration", "Companion", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
@ExperimentalBuildToolsApi
public interface CompilationService {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    @JvmStatic
    static CompilationService loadImplementation(ClassLoader classLoader) {
        return INSTANCE.loadImplementation(classLoader);
    }

    ClasspathEntrySnapshot calculateClasspathSnapshot(File classpathEntry, ClassSnapshotGranularity granularity);

    CompilationResult compileJvm(ProjectId projectId, CompilerExecutionStrategyConfiguration strategyConfig, JvmCompilationConfiguration compilationConfig, List<? extends File> sources, List<String> arguments);

    void finishProjectCompilation(ProjectId projectId);

    CompilerExecutionStrategyConfiguration makeCompilerExecutionStrategyConfiguration();

    JvmCompilationConfiguration makeJvmCompilationConfiguration();

    /* JADX INFO: compiled from: CompilationService.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0087\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/CompilationService$Companion;", "", "()V", "loadImplementation", "Lorg/jetbrains/kotlin/buildtools/api/CompilationService;", "classLoader", "Ljava/lang/ClassLoader;", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
    @ExperimentalBuildToolsApi
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        @JvmStatic
        public final CompilationService loadImplementation(ClassLoader classLoader) {
            Intrinsics.checkNotNullParameter(classLoader, "classLoader");
            return (CompilationService) SharedApiClassesClassLoader.loadImplementation(Reflection.getOrCreateKotlinClass(CompilationService.class), classLoader);
        }
    }
}
