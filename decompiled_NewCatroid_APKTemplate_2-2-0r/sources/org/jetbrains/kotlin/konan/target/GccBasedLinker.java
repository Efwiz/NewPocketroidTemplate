package org.jetbrains.kotlin.konan.target;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.exec.Command;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.NativeFileTypeKt;

/* JADX INFO: compiled from: Linker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b(\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0013\u0010]\u001a\u00020\u00072\b\u0010^\u001a\u0004\u0018\u00010\u0007H\u0096\u0001J\t\u0010_\u001a\u00020`H\u0096\u0001J\u001c\u0010a\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\u0082\u0001\u0010c\u001a\b\u0012\u0004\u0012\u00020d0\u00062\u0010\u0010e\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`f0\u00062\n\u0010g\u001a\u00060\u0007j\u0002`h2\f\u0010i\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010j\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020l2\u0006\u0010n\u001a\u00020o2\u0006\u0010p\u001a\u00020\u00072\u0006\u0010q\u001a\u00020l2\u0006\u0010r\u001a\u00020l2\b\u0010s\u001a\u0004\u0018\u00010tH\u0016J\u0017\u0010u\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010v\u001a\u00020\u0007H\u0096\u0001J\u0013\u0010w\u001a\u0004\u0018\u00010\u00072\u0006\u0010v\u001a\u00020\u0007H\u0096\u0001J\u0017\u0010x\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010v\u001a\u00020\u0007H\u0096\u0001J\u0013\u0010y\u001a\u0004\u0018\u00010\u00072\u0006\u0010v\u001a\u00020\u0007H\u0096\u0001J\u001a\u0010z\u001a\u0004\u0018\u00010\u00072\u0006\u0010{\u001a\u00020\u00072\u0006\u0010|\u001a\u00020lH\u0016J\u0017\u0010}\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010v\u001a\u00020\u0007H\u0096\u0001J\u0013\u0010~\u001a\u0004\u0018\u00010\u00072\u0006\u0010v\u001a\u00020\u0007H\u0096\u0001R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u0014\u0010\u000f\u001a\u00020\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0010\u0010\fR\u0014\u0010\u0011\u001a\u00020\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\fR\u0014\u0010\u0013\u001a\u00020\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\fR\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\tR\u000e\u0010\u0017\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0019\u0010\tR\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001b\u0010\tR\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001d\u0010\tR\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001f\u0010\tR\u001a\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b!\u0010\tR\u0014\u0010\"\u001a\u00020\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b#\u0010\fR\u0014\u0010$\u001a\u00020%8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b)\u0010\fR\u0014\u0010*\u001a\u00020%8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b+\u0010'R\u0016\u0010,\u001a\u0004\u0018\u00010\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b-\u0010\fR\u0014\u0010.\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\fR\u0016\u00100\u001a\u0004\u0018\u00010\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b1\u0010\fR\u0016\u00102\u001a\u0004\u0018\u00010\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b3\u0010\fR\u001a\u00104\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b5\u0010\tR\u001a\u00106\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b7\u0010\tR\u001a\u00108\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b9\u0010\tR\u001a\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b;\u0010\tR\u001a\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b=\u0010\tR\u001a\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b?\u0010\tR\u0016\u0010@\u001a\u0004\u0018\u00010\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bA\u0010\fR\u0016\u0010B\u001a\u0004\u0018\u00010\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bC\u0010\fR\u0016\u0010D\u001a\u0004\u0018\u00010\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bE\u0010\fR\u001a\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bG\u0010\tR\u001a\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bI\u0010\tR\u0014\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010K\u001a\u00020%8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bL\u0010'R\u0012\u0010M\u001a\u00020NX\u0096\u0005¢\u0006\u0006\u001a\u0004\bO\u0010PR\u0016\u0010Q\u001a\u0004\u0018\u00010\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bR\u0010\fR\u0016\u0010S\u001a\u0004\u0018\u00010\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bT\u0010\fR\u0016\u0010U\u001a\u0004\u0018\u00010\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bV\u0010\fR\u0016\u0010W\u001a\u0004\u0018\u00010\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bX\u0010\fR\u0014\u0010Y\u001a\u00020Z8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b[\u0010\\¨\u0006\u007f"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/GccBasedLinker;", "Lorg/jetbrains/kotlin/konan/target/LinkerFlags;", "Lorg/jetbrains/kotlin/konan/target/GccConfigurables;", "targetProperties", "(Lorg/jetbrains/kotlin/konan/target/GccConfigurables;)V", "abiSpecificLibraries", "", "", "getAbiSpecificLibraries", "()Ljava/util/List;", "absoluteGccToolchain", "getAbsoluteGccToolchain", "()Ljava/lang/String;", "absoluteLinker", "getAbsoluteLinker", "absoluteLlvmHome", "getAbsoluteLlvmHome", "absoluteTargetSysRoot", "getAbsoluteTargetSysRoot", "absoluteTargetToolchain", "getAbsoluteTargetToolchain", "additionalCacheFlags", "getAdditionalCacheFlags", "ar", "cacheableTargets", "getCacheableTargets", "clangDebugFlags", "getClangDebugFlags", "clangFlags", "getClangFlags", "clangNooptFlags", "getClangNooptFlags", "clangOptFlags", "getClangOptFlags", "crtFilesLocation", "getCrtFilesLocation", "dynamicLibraryRelocationMode", "Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "getDynamicLibraryRelocationMode", "()Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "dynamicLinker", "getDynamicLinker", "executableRelocationMode", "getExecutableRelocationMode", "gccToolchain", "getGccToolchain", "libGcc", "getLibGcc", "libffiDir", "getLibffiDir", "linker", "getLinker", "linkerDynamicFlags", "getLinkerDynamicFlags", "linkerGccFlags", "getLinkerGccFlags", "linkerHostSpecificFlags", "getLinkerHostSpecificFlags", "linkerKonanFlags", "getLinkerKonanFlags", "linkerNoDebugFlags", "getLinkerNoDebugFlags", "linkerOptimizationFlags", "getLinkerOptimizationFlags", "llvmHome", "getLlvmHome", "llvmInlineThreshold", "getLlvmInlineThreshold", "llvmVersion", "getLlvmVersion", "mimallocLinkerDependencies", "getMimallocLinkerDependencies", "runtimeDefinitions", "getRuntimeDefinitions", "specificLibs", "staticLibraryRelocationMode", "getStaticLibraryRelocationMode", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetCpu", "getTargetCpu", "targetCpuFeatures", "getTargetCpuFeatures", "targetSysRoot", "getTargetSysRoot", "targetToolchain", "getTargetToolchain", "targetTriple", "Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "getTargetTriple", "()Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "absolute", "value", "downloadDependencies", "", "filterStaticLibraries", "binaries", "finalLinkCommands", "Lorg/jetbrains/kotlin/konan/exec/Command;", "objectFiles", "Lorg/jetbrains/kotlin/konan/target/ObjectFile;", "executable", "Lorg/jetbrains/kotlin/konan/target/ExecutableFile;", "libraries", "linkerArgs", "optimize", "", "debug", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/konan/target/LinkerOutputKind;", "outputDsymBundle", "needsProfileLibrary", "mimallocEnabled", "sanitizer", "Lorg/jetbrains/kotlin/konan/target/SanitizerKind;", "hostList", ASTExpr.DEFAULT_MAP_KEY_NAME, "hostString", "hostTargetList", "hostTargetString", "provideCompilerRtLibrary", "libraryName", "isDynamic", "targetList", "targetString", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class GccBasedLinker extends LinkerFlags implements GccConfigurables {
    private final /* synthetic */ GccConfigurables $$delegate_0;
    private final String ar;
    private final String libGcc;
    private final List<String> specificLibs;

    /* JADX INFO: compiled from: Linker.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SanitizerKind.values().length];
            try {
                iArr[SanitizerKind.ADDRESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SanitizerKind.THREAD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String absolute(String value) {
        return this.$$delegate_0.absolute(value);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public void downloadDependencies() {
        this.$$delegate_0.downloadDependencies();
    }

    @Override // org.jetbrains.kotlin.konan.target.GccConfigurables
    public List<String> getAbiSpecificLibraries() {
        return this.$$delegate_0.getAbiSpecificLibraries();
    }

    @Override // org.jetbrains.kotlin.konan.target.GccConfigurables
    public String getAbsoluteGccToolchain() {
        return this.$$delegate_0.getAbsoluteGccToolchain();
    }

    @Override // org.jetbrains.kotlin.konan.target.GccConfigurables
    public String getAbsoluteLinker() {
        return this.$$delegate_0.getAbsoluteLinker();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getAbsoluteLlvmHome() {
        return this.$$delegate_0.getAbsoluteLlvmHome();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getAbsoluteTargetSysRoot() {
        return this.$$delegate_0.getAbsoluteTargetSysRoot();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getAbsoluteTargetToolchain() {
        return this.$$delegate_0.getAbsoluteTargetToolchain();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getAdditionalCacheFlags() {
        return this.$$delegate_0.getAdditionalCacheFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getCacheableTargets() {
        return this.$$delegate_0.getCacheableTargets();
    }

    @Override // org.jetbrains.kotlin.konan.target.ClangFlags
    public List<String> getClangDebugFlags() {
        return this.$$delegate_0.getClangDebugFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.ClangFlags
    public List<String> getClangFlags() {
        return this.$$delegate_0.getClangFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.ClangFlags
    public List<String> getClangNooptFlags() {
        return this.$$delegate_0.getClangNooptFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.ClangFlags
    public List<String> getClangOptFlags() {
        return this.$$delegate_0.getClangOptFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.GccConfigurables
    public String getCrtFilesLocation() {
        return this.$$delegate_0.getCrtFilesLocation();
    }

    @Override // org.jetbrains.kotlin.konan.target.RelocationModeFlags
    public RelocationModeFlags.Mode getDynamicLibraryRelocationMode() {
        return this.$$delegate_0.getDynamicLibraryRelocationMode();
    }

    @Override // org.jetbrains.kotlin.konan.target.GccConfigurables
    public String getDynamicLinker() {
        return this.$$delegate_0.getDynamicLinker();
    }

    @Override // org.jetbrains.kotlin.konan.target.RelocationModeFlags
    public RelocationModeFlags.Mode getExecutableRelocationMode() {
        return this.$$delegate_0.getExecutableRelocationMode();
    }

    @Override // org.jetbrains.kotlin.konan.target.GccConfigurables
    public String getGccToolchain() {
        return this.$$delegate_0.getGccToolchain();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getLibffiDir() {
        return this.$$delegate_0.getLibffiDir();
    }

    @Override // org.jetbrains.kotlin.konan.target.GccConfigurables
    public String getLinker() {
        return this.$$delegate_0.getLinker();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getLinkerDynamicFlags() {
        return this.$$delegate_0.getLinkerDynamicFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.GccConfigurables
    public List<String> getLinkerGccFlags() {
        return this.$$delegate_0.getLinkerGccFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.GccConfigurables
    public List<String> getLinkerHostSpecificFlags() {
        return this.$$delegate_0.getLinkerHostSpecificFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getLinkerKonanFlags() {
        return this.$$delegate_0.getLinkerKonanFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getLinkerNoDebugFlags() {
        return this.$$delegate_0.getLinkerNoDebugFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getLinkerOptimizationFlags() {
        return this.$$delegate_0.getLinkerOptimizationFlags();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getLlvmHome() {
        return this.$$delegate_0.getLlvmHome();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getLlvmInlineThreshold() {
        return this.$$delegate_0.getLlvmInlineThreshold();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getLlvmVersion() {
        return this.$$delegate_0.getLlvmVersion();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getMimallocLinkerDependencies() {
        return this.$$delegate_0.getMimallocLinkerDependencies();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getRuntimeDefinitions() {
        return this.$$delegate_0.getRuntimeDefinitions();
    }

    @Override // org.jetbrains.kotlin.konan.target.RelocationModeFlags
    public RelocationModeFlags.Mode getStaticLibraryRelocationMode() {
        return this.$$delegate_0.getStaticLibraryRelocationMode();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public KonanTarget getTarget() {
        return this.$$delegate_0.getTarget();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getTargetCpu() {
        return this.$$delegate_0.getTargetCpu();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getTargetCpuFeatures() {
        return this.$$delegate_0.getTargetCpuFeatures();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getTargetSysRoot() {
        return this.$$delegate_0.getTargetSysRoot();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getTargetToolchain() {
        return this.$$delegate_0.getTargetToolchain();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public TargetTriple getTargetTriple() {
        return this.$$delegate_0.getTargetTriple();
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public List<String> hostList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.$$delegate_0.hostList(key);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String hostString(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.$$delegate_0.hostString(key);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public List<String> hostTargetList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.$$delegate_0.hostTargetList(key);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String hostTargetString(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.$$delegate_0.hostTargetString(key);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public List<String> targetList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.$$delegate_0.targetList(key);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String targetString(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.$$delegate_0.targetString(key);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GccBasedLinker(GccConfigurables targetProperties) {
        String str;
        super(targetProperties);
        Intrinsics.checkNotNullParameter(targetProperties, "targetProperties");
        this.$$delegate_0 = targetProperties;
        if (HostManager.INSTANCE.getHostIsLinux()) {
            str = getAbsoluteTargetToolchain() + "/bin/ar";
        } else {
            str = getAbsoluteTargetToolchain() + "/bin/llvm-ar";
        }
        this.ar = str;
        this.libGcc = getAbsoluteTargetSysRoot() + '/' + super.getLibGcc();
        List<String> abiSpecificLibraries = getAbiSpecificLibraries();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(abiSpecificLibraries, 10));
        Iterator<T> it = abiSpecificLibraries.iterator();
        while (it.hasNext()) {
            arrayList.add("-L" + getAbsoluteTargetSysRoot() + '/' + ((String) it.next()));
        }
        this.specificLibs = arrayList;
    }

    @Override // org.jetbrains.kotlin.konan.target.GccConfigurables
    public String getLibGcc() {
        return this.libGcc;
    }

    @Override // org.jetbrains.kotlin.konan.target.LinkerFlags
    public String provideCompilerRtLibrary(String libraryName, boolean isDynamic) {
        Intrinsics.checkNotNullParameter(libraryName, "libraryName");
        if (isDynamic) {
            throw new IllegalArgumentException("Dynamic compiler rt librares are unsupported".toString());
        }
        if (!Intrinsics.areEqual(getTarget(), KonanTarget.LINUX_X64.INSTANCE)) {
            throw new IllegalStateException((getTarget() + " is not supported.").toString());
        }
        File file = (File) CollectionsKt.firstOrNull((List) new File(getAbsoluteLlvmHome() + "/lib/clang/").getListFiles());
        String absolutePath = file != null ? file.getAbsolutePath() : null;
        if (absolutePath != null) {
            return absolutePath + "/lib/linux/libclang_rt." + libraryName + "-x86_64.a";
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.konan.target.LinkerFlags
    public List<String> filterStaticLibraries(List<String> binaries) {
        Intrinsics.checkNotNullParameter(binaries, "binaries");
        ArrayList arrayList = new ArrayList();
        for (Object obj : binaries) {
            if (NativeFileTypeKt.isUnixStaticLib((String) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.konan.target.LinkerFlags
    public List<Command> finalLinkCommands(List<String> objectFiles, String executable, List<String> libraries, List<String> linkerArgs, boolean optimize, boolean debug, LinkerOutputKind kind, String outputDsymBundle, boolean needsProfileLibrary, boolean mimallocEnabled, SanitizerKind sanitizer) {
        StringBuilder sb;
        StringBuilder sbAppend;
        String str;
        StringBuilder sb2;
        StringBuilder sbAppend2;
        String str2;
        Intrinsics.checkNotNullParameter(objectFiles, "objectFiles");
        Intrinsics.checkNotNullParameter(executable, "executable");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(linkerArgs, "linkerArgs");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(outputDsymBundle, "outputDsymBundle");
        if (kind == LinkerOutputKind.STATIC_LIBRARY) {
            if (sanitizer == null) {
                return LinkerKt.staticGnuArCommands(this.ar, executable, objectFiles, libraries);
            }
            throw new IllegalArgumentException("Sanitizers are unsupported".toString());
        }
        boolean z = Intrinsics.areEqual(getTarget(), KonanTarget.LINUX_MIPS32.INSTANCE) || Intrinsics.areEqual(getTarget(), KonanTarget.LINUX_MIPSEL32.INSTANCE);
        boolean z2 = kind == LinkerOutputKind.DYNAMIC_LIBRARY;
        String str3 = getAbsoluteTargetSysRoot() + '/' + getCrtFilesLocation();
        Command command = new Command(getAbsoluteLinker());
        command.unaryPlus("--sysroot=" + getAbsoluteTargetSysRoot());
        command.unaryPlus("-export-dynamic");
        command.unaryPlus("-z");
        command.unaryPlus("relro");
        command.unaryPlus("--build-id");
        command.unaryPlus("--eh-frame-hdr");
        command.unaryPlus("-dynamic-linker");
        command.unaryPlus(getDynamicLinker());
        Iterator<T> it = getLinkerHostSpecificFlags().iterator();
        while (it.hasNext()) {
            command.unaryPlus((String) it.next());
        }
        command.unaryPlus("-o");
        command.unaryPlus(executable);
        if (!z2) {
            command.unaryPlus(str3 + "/crt1.o");
        }
        command.unaryPlus(str3 + "/crti.o");
        if (z2) {
            sb = new StringBuilder();
            sbAppend = sb.append(getLibGcc());
            str = "/crtbeginS.o";
        } else {
            sb = new StringBuilder();
            sbAppend = sb.append(getLibGcc());
            str = "/crtbegin.o";
        }
        command.unaryPlus(sbAppend.append(str).toString());
        command.unaryPlus("-L" + getLibGcc());
        if (!z) {
            command.unaryPlus("--hash-style=gnu");
        }
        command.unaryPlus(this.specificLibs);
        if (optimize) {
            command.unaryPlus(getLinkerOptimizationFlags());
        }
        if (!debug) {
            command.unaryPlus(getLinkerNoDebugFlags());
        }
        if (z2) {
            command.unaryPlus(getLinkerDynamicFlags());
        }
        command.unaryPlus(objectFiles);
        command.unaryPlus(libraries);
        command.unaryPlus(linkerArgs);
        if (mimallocEnabled) {
            command.unaryPlus(getMimallocLinkerDependencies());
        }
        if (needsProfileLibrary) {
            String profileLibrary = getProfileLibrary();
            Intrinsics.checkNotNull(profileLibrary);
            command.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-u__llvm_profile_runtime", profileLibrary}));
        }
        command.unaryPlus(getLinkerKonanFlags());
        command.unaryPlus(getLinkerGccFlags());
        if (z2) {
            sb2 = new StringBuilder();
            sbAppend2 = sb2.append(getLibGcc());
            str2 = "/crtendS.o";
        } else {
            sb2 = new StringBuilder();
            sbAppend2 = sb2.append(getLibGcc());
            str2 = "/crtend.o";
        }
        command.unaryPlus(sbAppend2.append(str2).toString());
        command.unaryPlus(str3 + "/crtn.o");
        int i = sanitizer == null ? -1 : WhenMappings.$EnumSwitchMapping$0[sanitizer.ordinal()];
        if (i == 1) {
            command.unaryPlus("-lrt");
            GccBasedLinker gccBasedLinker = this;
            String strProvideCompilerRtLibrary$default = LinkerFlags.provideCompilerRtLibrary$default(gccBasedLinker, "asan", false, 2, null);
            Intrinsics.checkNotNull(strProvideCompilerRtLibrary$default);
            command.unaryPlus(strProvideCompilerRtLibrary$default);
            String strProvideCompilerRtLibrary$default2 = LinkerFlags.provideCompilerRtLibrary$default(gccBasedLinker, "asan_cxx", false, 2, null);
            Intrinsics.checkNotNull(strProvideCompilerRtLibrary$default2);
            command.unaryPlus(strProvideCompilerRtLibrary$default2);
        } else if (i == 2) {
            command.unaryPlus("-lrt");
            GccBasedLinker gccBasedLinker2 = this;
            String strProvideCompilerRtLibrary$default3 = LinkerFlags.provideCompilerRtLibrary$default(gccBasedLinker2, "tsan", false, 2, null);
            Intrinsics.checkNotNull(strProvideCompilerRtLibrary$default3);
            command.unaryPlus(strProvideCompilerRtLibrary$default3);
            String strProvideCompilerRtLibrary$default4 = LinkerFlags.provideCompilerRtLibrary$default(gccBasedLinker2, "tsan_cxx", false, 2, null);
            Intrinsics.checkNotNull(strProvideCompilerRtLibrary$default4);
            command.unaryPlus(strProvideCompilerRtLibrary$default4);
        }
        return CollectionsKt.listOf(command);
    }
}
