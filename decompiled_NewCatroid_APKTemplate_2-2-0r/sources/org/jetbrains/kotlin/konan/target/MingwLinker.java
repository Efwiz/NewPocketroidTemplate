package org.jetbrains.kotlin.konan.target;

import java.util.ArrayList;
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
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0013\u0010]\u001a\u00020\u00062\b\u0010^\u001a\u0004\u0018\u00010\u0006H\u0096\u0001J\t\u0010_\u001a\u00020`H\u0096\u0001J\u001c\u0010a\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\f\u0010b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0010H\u0016J\u0082\u0001\u0010c\u001a\b\u0012\u0004\u0012\u00020d0\u00102\u0010\u0010e\u001a\f\u0012\b\u0012\u00060\u0006j\u0002`f0\u00102\n\u0010g\u001a\u00060\u0006j\u0002`h2\f\u0010i\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\f\u0010j\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010k\u001a\u00020T2\u0006\u0010l\u001a\u00020T2\u0006\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020\u00062\u0006\u0010p\u001a\u00020T2\u0006\u0010q\u001a\u00020T2\b\u0010r\u001a\u0004\u0018\u00010sH\u0016J\u0017\u0010t\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010u\u001a\u00020\u0006H\u0096\u0001J\u0013\u0010v\u001a\u0004\u0018\u00010\u00062\u0006\u0010u\u001a\u00020\u0006H\u0096\u0001J\u0017\u0010w\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010u\u001a\u00020\u0006H\u0096\u0001J\u0013\u0010x\u001a\u0004\u0018\u00010\u00062\u0006\u0010u\u001a\u00020\u0006H\u0096\u0001J\u001a\u0010y\u001a\u0004\u0018\u00010\u00062\u0006\u0010z\u001a\u00020\u00062\u0006\u0010{\u001a\u00020TH\u0016J\u0017\u0010|\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010u\u001a\u00020\u0006H\u0096\u0001J\u0013\u0010}\u001a\u0004\u0018\u00010\u00062\u0006\u0010u\u001a\u00020\u0006H\u0096\u0001R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0012R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0012R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0012R\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0012R\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0012R\u0014\u0010\u001f\u001a\u00020 8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020 8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b$\u0010\"R\u0016\u0010%\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b&\u0010\bR\u0014\u0010'\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b(\u0010\bR\u001a\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b*\u0010\u0012R\u001a\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b,\u0010\u0012R\u001a\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b.\u0010\u0012R\u001a\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b0\u0010\u0012R\u0016\u00101\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b2\u0010\bR\u0016\u00103\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b4\u0010\bR\u0016\u00105\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b6\u0010\bR\u001a\u00107\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b8\u0010\u0012R\u0012\u00109\u001a\u00020:X\u0096\u0005¢\u0006\u0006\u001a\u0004\b;\u0010<R\u0014\u0010=\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b>\u0010\bR\u001a\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b@\u0010\u0012R\u0014\u0010A\u001a\u00020 8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bB\u0010\"R\u0012\u0010C\u001a\u00020DX\u0096\u0005¢\u0006\u0006\u001a\u0004\bE\u0010FR\u0016\u0010G\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bH\u0010\bR\u0016\u0010I\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bJ\u0010\bR\u0016\u0010K\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bL\u0010\bR\u0016\u0010M\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bN\u0010\bR\u0014\u0010O\u001a\u00020P8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bQ\u0010RR\u0014\u0010S\u001a\u00020T8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bU\u0010VR\u0012\u0010W\u001a\u00020XX\u0096\u0005¢\u0006\u0006\u001a\u0004\bY\u0010ZR\u0014\u0010[\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\\\u0010\b¨\u0006~"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/MingwLinker;", "Lorg/jetbrains/kotlin/konan/target/LinkerFlags;", "Lorg/jetbrains/kotlin/konan/target/MingwConfigurables;", "targetProperties", "(Lorg/jetbrains/kotlin/konan/target/MingwConfigurables;)V", "absoluteLinker", "", "getAbsoluteLinker", "()Ljava/lang/String;", "absoluteLlvmHome", "getAbsoluteLlvmHome", "absoluteTargetSysRoot", "getAbsoluteTargetSysRoot", "absoluteTargetToolchain", "getAbsoluteTargetToolchain", "additionalCacheFlags", "", "getAdditionalCacheFlags", "()Ljava/util/List;", "ar", "cacheableTargets", "getCacheableTargets", "clang", "clangDebugFlags", "getClangDebugFlags", "clangFlags", "getClangFlags", "clangNooptFlags", "getClangNooptFlags", "clangOptFlags", "getClangOptFlags", "dynamicLibraryRelocationMode", "Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "getDynamicLibraryRelocationMode", "()Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "executableRelocationMode", "getExecutableRelocationMode", "libffiDir", "getLibffiDir", "linker", "getLinker", "linkerDynamicFlags", "getLinkerDynamicFlags", "linkerKonanFlags", "getLinkerKonanFlags", "linkerNoDebugFlags", "getLinkerNoDebugFlags", "linkerOptimizationFlags", "getLinkerOptimizationFlags", "llvmHome", "getLlvmHome", "llvmInlineThreshold", "getLlvmInlineThreshold", "llvmVersion", "getLlvmVersion", "mimallocLinkerDependencies", "getMimallocLinkerDependencies", "msvc", "Lorg/jetbrains/kotlin/konan/target/Msvc;", "getMsvc", "()Lorg/jetbrains/kotlin/konan/target/Msvc;", "msvcParts", "getMsvcParts", "runtimeDefinitions", "getRuntimeDefinitions", "staticLibraryRelocationMode", "getStaticLibraryRelocationMode", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetCpu", "getTargetCpu", "targetCpuFeatures", "getTargetCpuFeatures", "targetSysRoot", "getTargetSysRoot", "targetToolchain", "getTargetToolchain", "targetTriple", "Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "getTargetTriple", "()Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "useCompilerDriverAsLinker", "", "getUseCompilerDriverAsLinker", "()Z", "windowsKit", "Lorg/jetbrains/kotlin/konan/target/WindowsKit;", "getWindowsKit", "()Lorg/jetbrains/kotlin/konan/target/WindowsKit;", "windowsKitParts", "getWindowsKitParts", "absolute", "value", "downloadDependencies", "", "filterStaticLibraries", "binaries", "finalLinkCommands", "Lorg/jetbrains/kotlin/konan/exec/Command;", "objectFiles", "Lorg/jetbrains/kotlin/konan/target/ObjectFile;", "executable", "Lorg/jetbrains/kotlin/konan/target/ExecutableFile;", "libraries", "linkerArgs", "optimize", "debug", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/konan/target/LinkerOutputKind;", "outputDsymBundle", "needsProfileLibrary", "mimallocEnabled", "sanitizer", "Lorg/jetbrains/kotlin/konan/target/SanitizerKind;", "hostList", ASTExpr.DEFAULT_MAP_KEY_NAME, "hostString", "hostTargetList", "hostTargetString", "provideCompilerRtLibrary", "libraryName", "isDynamic", "targetList", "targetString", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class MingwLinker extends LinkerFlags implements MingwConfigurables {
    private final /* synthetic */ MingwConfigurables $$delegate_0;
    private final String ar;
    private final String clang;

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String absolute(String value) {
        return this.$$delegate_0.absolute(value);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public void downloadDependencies() {
        this.$$delegate_0.downloadDependencies();
    }

    @Override // org.jetbrains.kotlin.konan.target.MingwConfigurables
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

    @Override // org.jetbrains.kotlin.konan.target.RelocationModeFlags
    public RelocationModeFlags.Mode getDynamicLibraryRelocationMode() {
        return this.$$delegate_0.getDynamicLibraryRelocationMode();
    }

    @Override // org.jetbrains.kotlin.konan.target.RelocationModeFlags
    public RelocationModeFlags.Mode getExecutableRelocationMode() {
        return this.$$delegate_0.getExecutableRelocationMode();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getLibffiDir() {
        return this.$$delegate_0.getLibffiDir();
    }

    @Override // org.jetbrains.kotlin.konan.target.MingwConfigurables
    public String getLinker() {
        return this.$$delegate_0.getLinker();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getLinkerDynamicFlags() {
        return this.$$delegate_0.getLinkerDynamicFlags();
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

    @Override // org.jetbrains.kotlin.konan.target.MingwConfigurables
    public Msvc getMsvc() {
        return this.$$delegate_0.getMsvc();
    }

    @Override // org.jetbrains.kotlin.konan.target.MingwConfigurables
    public String getMsvcParts() {
        return this.$$delegate_0.getMsvcParts();
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

    @Override // org.jetbrains.kotlin.konan.target.LinkerFlags
    public boolean getUseCompilerDriverAsLinker() {
        return true;
    }

    @Override // org.jetbrains.kotlin.konan.target.MingwConfigurables
    public WindowsKit getWindowsKit() {
        return this.$$delegate_0.getWindowsKit();
    }

    @Override // org.jetbrains.kotlin.konan.target.MingwConfigurables
    public String getWindowsKitParts() {
        return this.$$delegate_0.getWindowsKitParts();
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
    public MingwLinker(MingwConfigurables targetProperties) {
        String str;
        super(targetProperties);
        Intrinsics.checkNotNullParameter(targetProperties, "targetProperties");
        this.$$delegate_0 = targetProperties;
        if (HostManager.INSTANCE.getHostIsMingw()) {
            str = getAbsoluteTargetToolchain() + "/bin/ar";
        } else {
            str = getAbsoluteLlvmHome() + "/bin/llvm-ar";
        }
        this.ar = str;
        this.clang = getAbsoluteLlvmHome() + "/bin/clang++";
    }

    @Override // org.jetbrains.kotlin.konan.target.LinkerFlags
    public List<String> filterStaticLibraries(List<String> binaries) {
        Intrinsics.checkNotNullParameter(binaries, "binaries");
        ArrayList arrayList = new ArrayList();
        for (Object obj : binaries) {
            String str = (String) obj;
            if (NativeFileTypeKt.isWindowsStaticLib(str) || NativeFileTypeKt.isUnixStaticLib(str)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.konan.target.LinkerFlags
    public String provideCompilerRtLibrary(String libraryName, boolean isDynamic) {
        Intrinsics.checkNotNullParameter(libraryName, "libraryName");
        if (isDynamic) {
            throw new IllegalArgumentException("Dynamic compiler rt librares are unsupported".toString());
        }
        if (!Intrinsics.areEqual(getTarget(), KonanTarget.MINGW_X64.INSTANCE)) {
            throw new IllegalStateException((getTarget() + " is not supported.").toString());
        }
        File file = (File) CollectionsKt.firstOrNull((List) new File(getAbsoluteLlvmHome() + "/lib/clang/").getListFiles());
        String absolutePath = file != null ? file.getAbsolutePath() : null;
        if (absolutePath != null) {
            return absolutePath + "/lib/windows/libclang_rt." + libraryName + "-x86_64.a";
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.konan.target.LinkerFlags
    public List<Command> finalLinkCommands(List<String> objectFiles, String executable, List<String> libraries, List<String> linkerArgs, boolean optimize, boolean debug, LinkerOutputKind kind, String outputDsymBundle, boolean needsProfileLibrary, boolean mimallocEnabled, SanitizerKind sanitizer) {
        Intrinsics.checkNotNullParameter(objectFiles, "objectFiles");
        Intrinsics.checkNotNullParameter(executable, "executable");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(linkerArgs, "linkerArgs");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(outputDsymBundle, "outputDsymBundle");
        if (sanitizer != null) {
            throw new IllegalArgumentException("Sanitizers are unsupported".toString());
        }
        if (kind == LinkerOutputKind.STATIC_LIBRARY) {
            return LinkerKt.staticGnuArCommands(this.ar, executable, objectFiles, libraries);
        }
        return CollectionsKt.listOf(finalLinkCommands$constructLinkerArguments$default(new Command(this.clang), this, executable, objectFiles, optimize, needsProfileLibrary, kind == LinkerOutputKind.DYNAMIC_LIBRARY, debug, libraries, linkerArgs, mimallocEnabled, CollectionsKt.listOf("-fuse-ld=" + getAbsoluteLinker()), null, 2048, null));
    }

    static /* synthetic */ Command finalLinkCommands$constructLinkerArguments$default(Command command, MingwLinker mingwLinker, String str, List list, boolean z, boolean z2, boolean z3, boolean z4, List list2, List list3, boolean z5, List list4, List list5, int i, Object obj) {
        return finalLinkCommands$constructLinkerArguments(command, mingwLinker, str, list, z, z2, z3, z4, list2, list3, z5, (i & 1024) != 0 ? CollectionsKt.emptyList() : list4, (i & 2048) != 0 ? CollectionsKt.emptyList() : list5);
    }

    private static final Command finalLinkCommands$constructLinkerArguments(Command command, MingwLinker mingwLinker, String str, List<String> list, boolean z, boolean z2, boolean z3, boolean z4, List<String> list2, List<String> list3, boolean z5, List<String> list4, List<String> list5) {
        command.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"--sysroot", mingwLinker.getAbsoluteTargetSysRoot()}));
        command.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-target", mingwLinker.getTargetTriple().toString()}));
        command.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-o", str}));
        command.unaryPlus(list);
        if (z && !z2 && !z3) {
            command.unaryPlus(mingwLinker.getLinkerOptimizationFlags());
        }
        if (!z4) {
            command.unaryPlus(mingwLinker.getLinkerNoDebugFlags());
        }
        if (z3) {
            command.unaryPlus(mingwLinker.getLinkerDynamicFlags());
        }
        command.unaryPlus(list2);
        if (z2) {
            String profileLibrary = mingwLinker.getProfileLibrary();
            Intrinsics.checkNotNull(profileLibrary);
            command.unaryPlus(profileLibrary);
        }
        command.unaryPlus(list3);
        List<String> linkerKonanFlags = mingwLinker.getLinkerKonanFlags();
        ArrayList arrayList = new ArrayList();
        for (Object obj : linkerKonanFlags) {
            if (!list5.contains((String) obj)) {
                arrayList.add(obj);
            }
        }
        command.unaryPlus(arrayList);
        if (z5) {
            command.unaryPlus(mingwLinker.getMimallocLinkerDependencies());
        }
        command.unaryPlus(list4);
        return command;
    }
}
