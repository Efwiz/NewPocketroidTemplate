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
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0013\u0010O\u001a\u00020\u00062\b\u0010P\u001a\u0004\u0018\u00010\u0006H\u0096\u0001J\t\u0010Q\u001a\u00020RH\u0096\u0001J\u001c\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\f\u0010T\u001a\b\u0012\u0004\u0012\u00020\u00060\u000eH\u0016J\u0082\u0001\u0010U\u001a\b\u0012\u0004\u0012\u00020V0\u000e2\u0010\u0010W\u001a\f\u0012\b\u0012\u00060\u0006j\u0002`X0\u000e2\n\u0010Y\u001a\u00060\u0006j\u0002`Z2\f\u0010[\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\f\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0006\u0010]\u001a\u00020L2\u0006\u0010^\u001a\u00020L2\u0006\u0010_\u001a\u00020`2\u0006\u0010a\u001a\u00020\u00062\u0006\u0010b\u001a\u00020L2\u0006\u0010c\u001a\u00020L2\b\u0010d\u001a\u0004\u0018\u00010eH\u0016J\u0017\u0010f\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0006\u0010g\u001a\u00020\u0006H\u0096\u0001J\u0013\u0010h\u001a\u0004\u0018\u00010\u00062\u0006\u0010g\u001a\u00020\u0006H\u0096\u0001J\u0017\u0010i\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0006\u0010g\u001a\u00020\u0006H\u0096\u0001J\u0013\u0010j\u001a\u0004\u0018\u00010\u00062\u0006\u0010g\u001a\u00020\u0006H\u0096\u0001J\u0017\u0010k\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0006\u0010g\u001a\u00020\u0006H\u0096\u0001J\u0013\u0010l\u001a\u0004\u0018\u00010\u00062\u0006\u0010g\u001a\u00020\u0006H\u0096\u0001R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0010R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0010R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0010R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0010R\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0010R\u000e\u0010\u001d\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020\u001f8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b#\u0010!R\u0016\u0010$\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b%\u0010\bR\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b'\u0010\u0010R\u001a\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b)\u0010\u0010R\u001a\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b+\u0010\u0010R\u001a\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b-\u0010\u0010R\u0016\u0010.\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b/\u0010\bR\u0016\u00100\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b1\u0010\bR\u0016\u00102\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b3\u0010\bR\u001a\u00104\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b5\u0010\u0010R\u000e\u00106\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00107\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b8\u0010\u0010R\u0014\u00109\u001a\u00020\u001f8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b:\u0010!R\u0012\u0010;\u001a\u00020<X\u0096\u0005¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0016\u0010?\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b@\u0010\bR\u0016\u0010A\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bB\u0010\bR\u0016\u0010C\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bD\u0010\bR\u0016\u0010E\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bF\u0010\bR\u0014\u0010G\u001a\u00020H8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0014\u0010K\u001a\u00020L8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010N¨\u0006m"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/AndroidLinker;", "Lorg/jetbrains/kotlin/konan/target/LinkerFlags;", "Lorg/jetbrains/kotlin/konan/target/AndroidConfigurables;", "targetProperties", "(Lorg/jetbrains/kotlin/konan/target/AndroidConfigurables;)V", "absoluteLlvmHome", "", "getAbsoluteLlvmHome", "()Ljava/lang/String;", "absoluteTargetSysRoot", "getAbsoluteTargetSysRoot", "absoluteTargetToolchain", "getAbsoluteTargetToolchain", "additionalCacheFlags", "", "getAdditionalCacheFlags", "()Ljava/util/List;", "ar", "cacheableTargets", "getCacheableTargets", "clang", "clangDebugFlags", "getClangDebugFlags", "clangFlags", "getClangFlags", "clangNooptFlags", "getClangNooptFlags", "clangOptFlags", "getClangOptFlags", "clangTarget", "dynamicLibraryRelocationMode", "Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "getDynamicLibraryRelocationMode", "()Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "executableRelocationMode", "getExecutableRelocationMode", "libffiDir", "getLibffiDir", "linkerDynamicFlags", "getLinkerDynamicFlags", "linkerKonanFlags", "getLinkerKonanFlags", "linkerNoDebugFlags", "getLinkerNoDebugFlags", "linkerOptimizationFlags", "getLinkerOptimizationFlags", "llvmHome", "getLlvmHome", "llvmInlineThreshold", "getLlvmInlineThreshold", "llvmVersion", "getLlvmVersion", "mimallocLinkerDependencies", "getMimallocLinkerDependencies", "prefix", "runtimeDefinitions", "getRuntimeDefinitions", "staticLibraryRelocationMode", "getStaticLibraryRelocationMode", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetCpu", "getTargetCpu", "targetCpuFeatures", "getTargetCpuFeatures", "targetSysRoot", "getTargetSysRoot", "targetToolchain", "getTargetToolchain", "targetTriple", "Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "getTargetTriple", "()Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "useCompilerDriverAsLinker", "", "getUseCompilerDriverAsLinker", "()Z", "absolute", "value", "downloadDependencies", "", "filterStaticLibraries", "binaries", "finalLinkCommands", "Lorg/jetbrains/kotlin/konan/exec/Command;", "objectFiles", "Lorg/jetbrains/kotlin/konan/target/ObjectFile;", "executable", "Lorg/jetbrains/kotlin/konan/target/ExecutableFile;", "libraries", "linkerArgs", "optimize", "debug", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/konan/target/LinkerOutputKind;", "outputDsymBundle", "needsProfileLibrary", "mimallocEnabled", "sanitizer", "Lorg/jetbrains/kotlin/konan/target/SanitizerKind;", "hostList", ASTExpr.DEFAULT_MAP_KEY_NAME, "hostString", "hostTargetList", "hostTargetString", "targetList", "targetString", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class AndroidLinker extends LinkerFlags implements AndroidConfigurables {
    private final /* synthetic */ AndroidConfigurables $$delegate_0;
    private final String ar;
    private final String clang;
    private final String clangTarget;
    private final String prefix;

    /* JADX INFO: compiled from: Linker.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LinkerOutputKind.values().length];
            try {
                iArr[LinkerOutputKind.EXECUTABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LinkerOutputKind.DYNAMIC_LIBRARY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[LinkerOutputKind.STATIC_LIBRARY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
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
    public AndroidLinker(AndroidConfigurables targetProperties) {
        StringBuilder sbAppend;
        String str;
        super(targetProperties);
        Intrinsics.checkNotNullParameter(targetProperties, "targetProperties");
        this.$$delegate_0 = targetProperties;
        String strWithoutVendor = Intrinsics.areEqual(targetProperties.getTargetTriple().toString(), "arm-unknown-linux-androideabi") ? "armv7a-linux-androideabi" : TargetTripleKt.withoutVendor(targetProperties.getTargetTriple());
        this.clangTarget = strWithoutVendor;
        String str2 = getAbsoluteTargetToolchain() + "/bin/" + strWithoutVendor + Android.API;
        this.prefix = str2;
        if (HostManager.INSTANCE.getHostIsMingw()) {
            sbAppend = new StringBuilder().append(str2);
            str = "-clang.cmd";
        } else {
            sbAppend = new StringBuilder().append(str2);
            str = "-clang";
        }
        this.clang = sbAppend.append(str).toString();
        this.ar = getAbsoluteTargetToolchain() + '/' + TargetTripleKt.withoutVendor(targetProperties.getTargetTriple()) + "/bin/ar";
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
        StringBuilder sbAppend;
        String str;
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
        boolean z = kind == LinkerOutputKind.DYNAMIC_LIBRARY;
        String str2 = getAbsoluteTargetToolchain() + "/sysroot";
        String str3 = getAbsoluteTargetSysRoot() + '/' + Android.INSTANCE.architectureDirForTarget(getTarget());
        String strWithoutVendor = TargetTripleKt.withoutVendor(getTargetTriple());
        String[] strArr = new String[4];
        strArr[0] = "--sysroot=" + str3;
        if (Intrinsics.areEqual(getTarget(), KonanTarget.ANDROID_X64.INSTANCE)) {
            sbAppend = new StringBuilder("-L").append(str3);
            str = "/usr/lib64";
        } else {
            sbAppend = new StringBuilder("-L").append(str3);
            str = "/usr/lib";
        }
        strArr[1] = sbAppend.append(str).toString();
        strArr[2] = "-L" + str2 + "/usr/lib/" + strWithoutVendor + "/21";
        strArr[3] = "-L" + str2 + "/usr/lib/" + strWithoutVendor;
        List<String> listListOf = CollectionsKt.listOf((Object[]) strArr);
        Command command = new Command(this.clang);
        command.unaryPlus("-o");
        command.unaryPlus(executable);
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        if (i == 1) {
            command.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-fPIE", "-pie"}));
        } else if (i == 2) {
            command.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-fPIC", "-shared"}));
        }
        command.unaryPlus("-target");
        command.unaryPlus(strWithoutVendor);
        command.unaryPlus(listListOf);
        command.unaryPlus(objectFiles);
        if (optimize) {
            command.unaryPlus(getLinkerOptimizationFlags());
        }
        if (!debug) {
            command.unaryPlus(getLinkerNoDebugFlags());
        }
        if (z) {
            command.unaryPlus(getLinkerDynamicFlags());
        }
        if (z) {
            command.unaryPlus("-Wl,-soname," + new File(executable).getName());
        }
        command.unaryPlus(getLinkerKonanFlags());
        if (mimallocEnabled) {
            command.unaryPlus(getMimallocLinkerDependencies());
        }
        command.unaryPlus(libraries);
        command.unaryPlus(linkerArgs);
        return CollectionsKt.listOf(command);
    }
}
