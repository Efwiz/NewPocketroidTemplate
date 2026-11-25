package org.jetbrains.kotlin.konan.target;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: Platform.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0013\u0010P\u001a\u00020\u00052\b\u0010Q\u001a\u0004\u0018\u00010\u0005H\u0096\u0001J\t\u0010R\u001a\u00020SH\u0096\u0001J\u0017\u0010T\u001a\b\u0012\u0004\u0012\u00020\u00050\r2\u0006\u0010U\u001a\u00020\u0005H\u0096\u0001J\u0013\u0010V\u001a\u0004\u0018\u00010\u00052\u0006\u0010U\u001a\u00020\u0005H\u0096\u0001J\u0017\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00050\r2\u0006\u0010U\u001a\u00020\u0005H\u0096\u0001J\u0013\u0010X\u001a\u0004\u0018\u00010\u00052\u0006\u0010U\u001a\u00020\u0005H\u0096\u0001J\u0017\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00050\r2\u0006\u0010U\u001a\u00020\u0005H\u0096\u0001J\u0013\u0010Z\u001a\u0004\u0018\u00010\u00052\u0006\u0010U\u001a\u00020\u0005H\u0096\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u00058VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\r8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\r8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000fR\u001b\u0010\u0012\u001a\u00020\u00138FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0018\u001a\u00020\u00198FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u0017\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020 8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020 8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b$\u0010\"R\u0016\u0010%\u001a\u0004\u0018\u00010\u00058VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b&\u0010\u0007R\u001b\u0010'\u001a\u00020(8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\u0017\u001a\u0004\b)\u0010*R\u001a\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\r8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b-\u0010\u000fR\u001a\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00050\r8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b/\u0010\u000fR\u001a\u00100\u001a\b\u0012\u0004\u0012\u00020\u00050\r8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b1\u0010\u000fR\u001a\u00102\u001a\b\u0012\u0004\u0012\u00020\u00050\r8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b3\u0010\u000fR\u0016\u00104\u001a\u0004\u0018\u00010\u00058VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b5\u0010\u0007R\u0016\u00106\u001a\u0004\u0018\u00010\u00058VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b7\u0010\u0007R\u0016\u00108\u001a\u0004\u0018\u00010\u00058VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b9\u0010\u0007R\u001a\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00050\r8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b;\u0010\u000fR\u001a\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00050\r8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b=\u0010\u000fR\u0014\u0010>\u001a\u00020 8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b?\u0010\"R\u0012\u0010@\u001a\u00020AX\u0096\u0005¢\u0006\u0006\u001a\u0004\bB\u0010CR\u0016\u0010D\u001a\u0004\u0018\u00010\u00058VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bE\u0010\u0007R\u0016\u0010F\u001a\u0004\u0018\u00010\u00058VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bG\u0010\u0007R\u0016\u0010H\u001a\u0004\u0018\u00010\u00058VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bI\u0010\u0007R\u0016\u0010J\u001a\u0004\u0018\u00010\u00058VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bK\u0010\u0007R\u0014\u0010L\u001a\u00020M8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bN\u0010O¨\u0006["}, d2 = {"Lorg/jetbrains/kotlin/konan/target/Platform;", "Lorg/jetbrains/kotlin/konan/target/Configurables;", "configurables", "(Lorg/jetbrains/kotlin/konan/target/Configurables;)V", "absoluteLlvmHome", "", "getAbsoluteLlvmHome", "()Ljava/lang/String;", "absoluteTargetSysRoot", "getAbsoluteTargetSysRoot", "absoluteTargetToolchain", "getAbsoluteTargetToolchain", "additionalCacheFlags", "", "getAdditionalCacheFlags", "()Ljava/util/List;", "cacheableTargets", "getCacheableTargets", "clang", "Lorg/jetbrains/kotlin/konan/target/ClangArgs$Native;", "getClang", "()Lorg/jetbrains/kotlin/konan/target/ClangArgs$Native;", "clang$delegate", "Lkotlin/Lazy;", "clangForJni", "Lorg/jetbrains/kotlin/konan/target/ClangArgs$Jni;", "getClangForJni", "()Lorg/jetbrains/kotlin/konan/target/ClangArgs$Jni;", "clangForJni$delegate", "getConfigurables", "()Lorg/jetbrains/kotlin/konan/target/Configurables;", "dynamicLibraryRelocationMode", "Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "getDynamicLibraryRelocationMode", "()Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "executableRelocationMode", "getExecutableRelocationMode", "libffiDir", "getLibffiDir", "linker", "Lorg/jetbrains/kotlin/konan/target/LinkerFlags;", "getLinker", "()Lorg/jetbrains/kotlin/konan/target/LinkerFlags;", "linker$delegate", "linkerDynamicFlags", "getLinkerDynamicFlags", "linkerKonanFlags", "getLinkerKonanFlags", "linkerNoDebugFlags", "getLinkerNoDebugFlags", "linkerOptimizationFlags", "getLinkerOptimizationFlags", "llvmHome", "getLlvmHome", "llvmInlineThreshold", "getLlvmInlineThreshold", "llvmVersion", "getLlvmVersion", "mimallocLinkerDependencies", "getMimallocLinkerDependencies", "runtimeDefinitions", "getRuntimeDefinitions", "staticLibraryRelocationMode", "getStaticLibraryRelocationMode", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetCpu", "getTargetCpu", "targetCpuFeatures", "getTargetCpuFeatures", "targetSysRoot", "getTargetSysRoot", "targetToolchain", "getTargetToolchain", "targetTriple", "Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "getTargetTriple", "()Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "absolute", "value", "downloadDependencies", "", "hostList", ASTExpr.DEFAULT_MAP_KEY_NAME, "hostString", "hostTargetList", "hostTargetString", "targetList", "targetString", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class Platform implements Configurables {

    /* JADX INFO: renamed from: clang$delegate, reason: from kotlin metadata */
    private final Lazy clang;

    /* JADX INFO: renamed from: clangForJni$delegate, reason: from kotlin metadata */
    private final Lazy clangForJni;
    private final Configurables configurables;

    /* JADX INFO: renamed from: linker$delegate, reason: from kotlin metadata */
    private final Lazy linker;

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String absolute(String value) {
        return this.configurables.absolute(value);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public void downloadDependencies() {
        this.configurables.downloadDependencies();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getAbsoluteLlvmHome() {
        return this.configurables.getAbsoluteLlvmHome();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getAbsoluteTargetSysRoot() {
        return this.configurables.getAbsoluteTargetSysRoot();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getAbsoluteTargetToolchain() {
        return this.configurables.getAbsoluteTargetToolchain();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getAdditionalCacheFlags() {
        return this.configurables.getAdditionalCacheFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getCacheableTargets() {
        return this.configurables.getCacheableTargets();
    }

    @Override // org.jetbrains.kotlin.konan.target.RelocationModeFlags
    public RelocationModeFlags.Mode getDynamicLibraryRelocationMode() {
        return this.configurables.getDynamicLibraryRelocationMode();
    }

    @Override // org.jetbrains.kotlin.konan.target.RelocationModeFlags
    public RelocationModeFlags.Mode getExecutableRelocationMode() {
        return this.configurables.getExecutableRelocationMode();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getLibffiDir() {
        return this.configurables.getLibffiDir();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getLinkerDynamicFlags() {
        return this.configurables.getLinkerDynamicFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getLinkerKonanFlags() {
        return this.configurables.getLinkerKonanFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getLinkerNoDebugFlags() {
        return this.configurables.getLinkerNoDebugFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getLinkerOptimizationFlags() {
        return this.configurables.getLinkerOptimizationFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getLlvmHome() {
        return this.configurables.getLlvmHome();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getLlvmInlineThreshold() {
        return this.configurables.getLlvmInlineThreshold();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getLlvmVersion() {
        return this.configurables.getLlvmVersion();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getMimallocLinkerDependencies() {
        return this.configurables.getMimallocLinkerDependencies();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getRuntimeDefinitions() {
        return this.configurables.getRuntimeDefinitions();
    }

    @Override // org.jetbrains.kotlin.konan.target.RelocationModeFlags
    public RelocationModeFlags.Mode getStaticLibraryRelocationMode() {
        return this.configurables.getStaticLibraryRelocationMode();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public KonanTarget getTarget() {
        return this.configurables.getTarget();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getTargetCpu() {
        return this.configurables.getTargetCpu();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getTargetCpuFeatures() {
        return this.configurables.getTargetCpuFeatures();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getTargetSysRoot() {
        return this.configurables.getTargetSysRoot();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getTargetToolchain() {
        return this.configurables.getTargetToolchain();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public TargetTriple getTargetTriple() {
        return this.configurables.getTargetTriple();
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public List<String> hostList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.configurables.hostList(key);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String hostString(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.configurables.hostString(key);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public List<String> hostTargetList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.configurables.hostTargetList(key);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String hostTargetString(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.configurables.hostTargetString(key);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public List<String> targetList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.configurables.targetList(key);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String targetString(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.configurables.targetString(key);
    }

    public Platform(Configurables configurables) {
        Intrinsics.checkNotNullParameter(configurables, "configurables");
        this.configurables = configurables;
        this.clang = LazyKt.lazy(new Function0<ClangArgs.Native>() { // from class: org.jetbrains.kotlin.konan.target.Platform$clang$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClangArgs.Native invoke() {
                return new ClangArgs.Native(this.this$0.getConfigurables());
            }
        });
        this.clangForJni = LazyKt.lazy(new Function0<ClangArgs.Jni>() { // from class: org.jetbrains.kotlin.konan.target.Platform$clangForJni$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClangArgs.Jni invoke() {
                return new ClangArgs.Jni(this.this$0.getConfigurables());
            }
        });
        this.linker = LazyKt.lazy(new Function0<LinkerFlags>() { // from class: org.jetbrains.kotlin.konan.target.Platform$linker$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LinkerFlags invoke() {
                return LinkerKt.linker(this.this$0.getConfigurables());
            }
        });
    }

    public final Configurables getConfigurables() {
        return this.configurables;
    }

    public final ClangArgs.Native getClang() {
        return (ClangArgs.Native) this.clang.getValue();
    }

    public final ClangArgs.Jni getClangForJni() {
        return (ClangArgs.Jni) this.clangForJni.getValue();
    }

    public final LinkerFlags getLinker() {
        return (LinkerFlags) this.linker.getValue();
    }
}
