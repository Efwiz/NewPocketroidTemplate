package org.jetbrains.kotlin.konan.target;

import com.google.android.gms.cast.CredentialsData;
import com.reandroid.arsc.value.ResConfigBase;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.text.StringsKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.exec.Command;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.FileKt;
import org.jetbrains.kotlin.konan.file.NativeFileTypeKt;

/* JADX INFO: compiled from: Linker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0013\u0010^\u001a\u00020\u00062\b\u0010_\u001a\u0004\u0018\u00010\u0006H\u0096\u0001J\t\u0010`\u001a\u00020aH\u0096\u0001J\u001a\u0010b\u001a\u00020c2\n\u0010d\u001a\u00060\u0006j\u0002`e2\u0006\u0010f\u001a\u00020\u0006J \u0010g\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\n\u0010d\u001a\u00060\u0006j\u0002`e2\u0006\u0010f\u001a\u00020\u0006J\u0018\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\n\u0010d\u001a\u00060\u0006j\u0002`eJ\u0016\u0010i\u001a\u00020j2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020\u00060\u0010H\u0002J\u001c\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\f\u0010m\u001a\b\u0012\u0004\u0012\u00020\u00060\u0010H\u0016J\u0082\u0001\u0010n\u001a\b\u0012\u0004\u0012\u00020c0\u00102\u0010\u0010o\u001a\f\u0012\b\u0012\u00060\u0006j\u0002`p0\u00102\n\u0010d\u001a\u00060\u0006j\u0002`e2\f\u0010q\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\f\u0010r\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010s\u001a\u00020t2\u0006\u0010u\u001a\u00020t2\u0006\u0010v\u001a\u00020w2\u0006\u0010f\u001a\u00020\u00062\u0006\u0010x\u001a\u00020t2\u0006\u0010y\u001a\u00020t2\b\u0010z\u001a\u0004\u0018\u00010{H\u0016J\u0017\u0010|\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010}\u001a\u00020\u0006H\u0096\u0001J\u0013\u0010~\u001a\u0004\u0018\u00010\u00062\u0006\u0010}\u001a\u00020\u0006H\u0096\u0001J\u0017\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010}\u001a\u00020\u0006H\u0096\u0001J\u0014\u0010\u0080\u0001\u001a\u0004\u0018\u00010\u00062\u0006\u0010}\u001a\u00020\u0006H\u0096\u0001J\u000f\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u0010H\u0002J.\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020c0\u00102\u0010\u0010o\u001a\f\u0012\b\u0012\u00060\u0006j\u0002`p0\u00102\u000b\u0010\u0083\u0001\u001a\u00060\u0006j\u0002`pH\u0016J\u001d\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u00062\u0007\u0010\u0085\u0001\u001a\u00020\u00062\u0007\u0010\u0086\u0001\u001a\u00020tH\u0016J\"\u0010\u0087\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\u0007\u0010\u0088\u0001\u001a\u00020t2\b\u0010z\u001a\u0004\u0018\u00010{H\u0002J\u0018\u0010\u0089\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010}\u001a\u00020\u0006H\u0096\u0001J\u0014\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u00062\u0006\u0010}\u001a\u00020\u0006H\u0096\u0001R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\bR\u0014\u0010\u0015\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\bR\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0012R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0012R\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0012R\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0012R\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b \u0010\u0012R\u001d\u0010!\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b\"\u0010\bR\u001d\u0010%\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b'\u0010$\u001a\u0004\b&\u0010\bR\u000e\u0010(\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010)\u001a\u00020*8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020*8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b.\u0010,R\u0016\u0010/\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b0\u0010\bR\u000e\u00101\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00103\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b4\u0010\u0012R\u001a\u00105\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b6\u0010\u0012R\u001a\u00107\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b8\u0010\u0012R\u001a\u00109\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b:\u0010\u0012R\u0016\u0010;\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b<\u0010\bR\u0016\u0010=\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b>\u0010\bR\u0016\u0010?\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b@\u0010\bR\u001a\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bB\u0010\u0012R\u0014\u0010C\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bD\u0010\bR\u001a\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bF\u0010\u0012R\u0014\u0010G\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bH\u0010\bR\u0014\u0010I\u001a\u00020*8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bJ\u0010,R\u000e\u0010K\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bM\u0010\u0012R\u0012\u0010N\u001a\u00020OX\u0096\u0005¢\u0006\u0006\u001a\u0004\bP\u0010QR\u0016\u0010R\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bS\u0010\bR\u0016\u0010T\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bU\u0010\bR\u0016\u0010V\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bW\u0010\bR\u0016\u0010X\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bY\u0010\bR\u0014\u0010Z\u001a\u00020[8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\\\u0010]¨\u0006\u008b\u0001"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/MacOSBasedLinker;", "Lorg/jetbrains/kotlin/konan/target/LinkerFlags;", "Lorg/jetbrains/kotlin/konan/target/AppleConfigurables;", "targetProperties", "(Lorg/jetbrains/kotlin/konan/target/AppleConfigurables;)V", "absoluteAdditionalToolsDir", "", "getAbsoluteAdditionalToolsDir", "()Ljava/lang/String;", "absoluteLlvmHome", "getAbsoluteLlvmHome", "absoluteTargetSysRoot", "getAbsoluteTargetSysRoot", "absoluteTargetToolchain", "getAbsoluteTargetToolchain", "additionalCacheFlags", "", "getAdditionalCacheFlags", "()Ljava/util/List;", "additionalToolsDir", "getAdditionalToolsDir", "arch", "getArch", "cacheableTargets", "getCacheableTargets", "clangDebugFlags", "getClangDebugFlags", "clangFlags", "getClangFlags", "clangNooptFlags", "getClangNooptFlags", "clangOptFlags", "getClangOptFlags", "compilerRtDir", "getCompilerRtDir", "compilerRtDir$delegate", "Lkotlin/Lazy;", "compilerRtLibrary", "getCompilerRtLibrary", "compilerRtLibrary$delegate", "dsymutil", "dynamicLibraryRelocationMode", "Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "getDynamicLibraryRelocationMode", "()Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "executableRelocationMode", "getExecutableRelocationMode", "libffiDir", "getLibffiDir", "libtool", "linker", "linkerDynamicFlags", "getLinkerDynamicFlags", "linkerKonanFlags", "getLinkerKonanFlags", "linkerNoDebugFlags", "getLinkerNoDebugFlags", "linkerOptimizationFlags", "getLinkerOptimizationFlags", "llvmHome", "getLlvmHome", "llvmInlineThreshold", "getLlvmInlineThreshold", "llvmVersion", "getLlvmVersion", "mimallocLinkerDependencies", "getMimallocLinkerDependencies", "osVersionMin", "getOsVersionMin", "runtimeDefinitions", "getRuntimeDefinitions", ResConfigBase.NAME_sdkVersion, "getSdkVersion", "staticLibraryRelocationMode", "getStaticLibraryRelocationMode", "strip", "stripFlags", "getStripFlags", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetCpu", "getTargetCpu", "targetCpuFeatures", "getTargetCpuFeatures", "targetSysRoot", "getTargetSysRoot", "targetToolchain", "getTargetToolchain", "targetTriple", "Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "getTargetTriple", "()Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "absolute", "value", "downloadDependencies", "", "dsymUtilCommand", "Lorg/jetbrains/kotlin/konan/exec/Command;", "executable", "Lorg/jetbrains/kotlin/konan/target/ExecutableFile;", "outputDsymBundle", "dsymutilCommand", "dsymutilDryRunVerboseCommand", "executeCommandWithFilter", "", "command", "filterStaticLibraries", "binaries", "finalLinkCommands", "objectFiles", "Lorg/jetbrains/kotlin/konan/target/ObjectFile;", "libraries", "linkerArgs", "optimize", "", "debug", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/konan/target/LinkerOutputKind;", "needsProfileLibrary", "mimallocEnabled", "sanitizer", "Lorg/jetbrains/kotlin/konan/target/SanitizerKind;", "hostList", ASTExpr.DEFAULT_MAP_KEY_NAME, "hostString", "hostTargetList", "hostTargetString", "platformVersionFlags", "preLinkCommands", "output", "provideCompilerRtLibrary", "libraryName", "isDynamic", "rpath", "dynamic", "targetList", "targetString", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class MacOSBasedLinker extends LinkerFlags implements AppleConfigurables {
    private final /* synthetic */ AppleConfigurables $$delegate_0;

    /* JADX INFO: renamed from: compilerRtDir$delegate, reason: from kotlin metadata */
    private final Lazy compilerRtDir;

    /* JADX INFO: renamed from: compilerRtLibrary$delegate, reason: from kotlin metadata */
    private final Lazy compilerRtLibrary;
    private final String dsymutil;
    private final String libtool;
    private final String linker;
    private final String strip;

    /* JADX INFO: compiled from: Linker.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Family.values().length];
            try {
                iArr[Family.IOS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Family.WATCHOS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Family.TVOS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Family.OSX.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[SanitizerKind.values().length];
            try {
                iArr2[SanitizerKind.ADDRESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[SanitizerKind.THREAD.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
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

    @Override // org.jetbrains.kotlin.konan.target.AppleConfigurables
    public String getAbsoluteAdditionalToolsDir() {
        return this.$$delegate_0.getAbsoluteAdditionalToolsDir();
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

    @Override // org.jetbrains.kotlin.konan.target.AppleConfigurables
    public String getAdditionalToolsDir() {
        return this.$$delegate_0.getAdditionalToolsDir();
    }

    @Override // org.jetbrains.kotlin.konan.target.AppleConfigurables
    public String getArch() {
        return this.$$delegate_0.getArch();
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

    @Override // org.jetbrains.kotlin.konan.target.AppleConfigurables
    public String getOsVersionMin() {
        return this.$$delegate_0.getOsVersionMin();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public List<String> getRuntimeDefinitions() {
        return this.$$delegate_0.getRuntimeDefinitions();
    }

    @Override // org.jetbrains.kotlin.konan.target.AppleConfigurables
    public String getSdkVersion() {
        return this.$$delegate_0.getSdkVersion();
    }

    @Override // org.jetbrains.kotlin.konan.target.RelocationModeFlags
    public RelocationModeFlags.Mode getStaticLibraryRelocationMode() {
        return this.$$delegate_0.getStaticLibraryRelocationMode();
    }

    @Override // org.jetbrains.kotlin.konan.target.AppleConfigurables
    public List<String> getStripFlags() {
        return this.$$delegate_0.getStripFlags();
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
    public MacOSBasedLinker(AppleConfigurables targetProperties) {
        super(targetProperties);
        Intrinsics.checkNotNullParameter(targetProperties, "targetProperties");
        this.$$delegate_0 = targetProperties;
        this.libtool = getAbsoluteTargetToolchain() + "/usr/bin/libtool";
        this.linker = getAbsoluteTargetToolchain() + "/usr/bin/ld";
        this.strip = getAbsoluteTargetToolchain() + "/usr/bin/strip";
        this.dsymutil = getAbsoluteTargetToolchain() + "/usr/bin/dsymutil";
        this.compilerRtDir = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.MacOSBasedLinker$compilerRtDir$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                File file = (File) CollectionsKt.firstOrNull((List) new File(this.this$0.getAbsoluteTargetToolchain() + "/usr/lib/clang/").getListFiles());
                String absolutePath = file != null ? file.getAbsolutePath() : null;
                if (absolutePath != null) {
                    return absolutePath + "/lib/darwin/";
                }
                return null;
            }
        });
        this.compilerRtLibrary = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.MacOSBasedLinker$compilerRtLibrary$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return LinkerFlags.provideCompilerRtLibrary$default(this.this$0, "", false, 2, null);
            }
        });
    }

    private final String getCompilerRtDir() {
        return (String) this.compilerRtDir.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.target.LinkerFlags
    public String provideCompilerRtLibrary(String libraryName, boolean isDynamic) {
        String str;
        Intrinsics.checkNotNullParameter(libraryName, "libraryName");
        int i = WhenMappings.$EnumSwitchMapping$0[getTarget().getFamily().ordinal()];
        if (i == 1) {
            str = CredentialsData.CREDENTIALS_TYPE_IOS;
        } else if (i == 2) {
            str = "watchos";
        } else if (i == 3) {
            str = "tvos";
        } else if (i == 4) {
            str = "osx";
        } else {
            throw new IllegalStateException(("Target " + getTarget() + " is unsupported").toString());
        }
        String str2 = TargetTripleKt.isSimulator(getTargetTriple()) ? "sim" : "";
        String compilerRtDir = getCompilerRtDir();
        String str3 = libraryName.length() != 0 ? libraryName + '_' : "";
        String str4 = isDynamic ? "_dynamic.dylib" : ".a";
        if (compilerRtDir != null) {
            return compilerRtDir + "/libclang_rt." + str3 + str + str2 + str4;
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
    public List<Command> preLinkCommands(List<String> objectFiles, String output) {
        Intrinsics.checkNotNullParameter(objectFiles, "objectFiles");
        Intrinsics.checkNotNullParameter(output, "output");
        Command command = new Command(this.linker);
        command.unaryPlus("-r");
        command.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-arch", getArch()}));
        command.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-syslibroot", getAbsoluteTargetSysRoot()}));
        command.unaryPlus(objectFiles);
        command.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-o", output}));
        return CollectionsKt.listOf(command);
    }

    private final List<String> platformVersionFlags() {
        String str;
        ArrayList arrayList = new ArrayList();
        arrayList.add("-platform_version");
        int i = WhenMappings.$EnumSwitchMapping$0[getTarget().getFamily().ordinal()];
        if (i == 1) {
            str = CredentialsData.CREDENTIALS_TYPE_IOS;
        } else if (i == 2) {
            str = "watchos";
        } else if (i == 3) {
            str = "tvos";
        } else if (i == 4) {
            str = "macos";
        } else {
            throw new IllegalStateException(("Unexpected Apple target family: " + getTarget().getFamily()).toString());
        }
        arrayList.add(str.concat(TargetTripleKt.isSimulator(getTargetTriple()) ? "-simulator" : ""));
        arrayList.add(getOsVersionMin() + ".0");
        arrayList.add(getSdkVersion());
        return CollectionsKt.toList(arrayList);
    }

    @Override // org.jetbrains.kotlin.konan.target.LinkerFlags
    public List<Command> finalLinkCommands(List<String> objectFiles, String executable, List<String> libraries, List<String> linkerArgs, boolean optimize, boolean debug, LinkerOutputKind kind, String outputDsymBundle, boolean needsProfileLibrary, boolean mimallocEnabled, SanitizerKind sanitizer) {
        Intrinsics.checkNotNullParameter(objectFiles, "objectFiles");
        Intrinsics.checkNotNullParameter(executable, "executable");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(linkerArgs, "linkerArgs");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(outputDsymBundle, "outputDsymBundle");
        if (kind == LinkerOutputKind.STATIC_LIBRARY) {
            if (sanitizer != null) {
                throw new IllegalArgumentException("Sanitizers are unsupported".toString());
            }
            Command command = new Command(this.libtool);
            command.unaryPlus("-static");
            command.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-o", executable}));
            command.unaryPlus(objectFiles);
            command.unaryPlus(libraries);
            return CollectionsKt.listOf(command);
        }
        boolean z = kind == LinkerOutputKind.DYNAMIC_LIBRARY;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = arrayList;
        Command command2 = new Command(this.linker);
        command2.unaryPlus("-demangle");
        command2.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-dynamic", "-arch", getArch()}));
        command2.unaryPlus(platformVersionFlags());
        command2.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-syslibroot", getAbsoluteTargetSysRoot(), "-o", executable}));
        command2.unaryPlus(objectFiles);
        if (optimize) {
            command2.unaryPlus(getLinkerOptimizationFlags());
        }
        if (!debug) {
            command2.unaryPlus(getLinkerNoDebugFlags());
        }
        if (z) {
            command2.unaryPlus(getLinkerDynamicFlags());
        }
        command2.unaryPlus(getLinkerKonanFlags());
        if (mimallocEnabled) {
            command2.unaryPlus(getMimallocLinkerDependencies());
        }
        if (getCompilerRtLibrary() != null) {
            String compilerRtLibrary = getCompilerRtLibrary();
            Intrinsics.checkNotNull(compilerRtLibrary);
            command2.unaryPlus(compilerRtLibrary);
        }
        if (needsProfileLibrary) {
            String profileLibrary = getProfileLibrary();
            Intrinsics.checkNotNull(profileLibrary);
            command2.unaryPlus(profileLibrary);
        }
        command2.unaryPlus(libraries);
        command2.unaryPlus(linkerArgs);
        command2.unaryPlus(rpath(z, sanitizer));
        int i = sanitizer == null ? -1 : WhenMappings.$EnumSwitchMapping$1[sanitizer.ordinal()];
        if (i == 1) {
            String strProvideCompilerRtLibrary = provideCompilerRtLibrary("asan", true);
            Intrinsics.checkNotNull(strProvideCompilerRtLibrary);
            command2.unaryPlus(strProvideCompilerRtLibrary);
        } else if (i == 2) {
            String strProvideCompilerRtLibrary2 = provideCompilerRtLibrary("tsan", true);
            Intrinsics.checkNotNull(strProvideCompilerRtLibrary2);
            command2.unaryPlus(strProvideCompilerRtLibrary2);
        }
        arrayList2.add(command2);
        if (debug) {
            arrayList2.add(dsymUtilCommand(executable, outputDsymBundle));
            if (optimize) {
                SpreadBuilder spreadBuilder = new SpreadBuilder(3);
                spreadBuilder.add(this.strip);
                spreadBuilder.addSpread(getStripFlags().toArray(new String[0]));
                spreadBuilder.add(executable);
                arrayList2.add(new Command((String[]) spreadBuilder.toArray(new String[spreadBuilder.size()])));
            }
        }
        return arrayList;
    }

    private final String getCompilerRtLibrary() {
        return (String) this.compilerRtLibrary.getValue();
    }

    private final List<String> rpath(boolean dynamic, SanitizerKind sanitizer) {
        String str;
        String[] strArr = new String[3];
        int i = WhenMappings.$EnumSwitchMapping$0[getTarget().getFamily().ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            str = "@executable_path/Frameworks";
        } else if (i == 4) {
            str = "@executable_path/../Frameworks";
        } else {
            throw new IllegalStateException(getTarget().toString());
        }
        strArr[0] = str;
        strArr[1] = dynamic ? "@loader_path/Frameworks" : null;
        strArr[2] = sanitizer != null ? getCompilerRtDir() : null;
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) strArr);
        ArrayList arrayList = new ArrayList();
        Iterator it = listListOfNotNull.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, CollectionsKt.listOf((Object[]) new String[]{"-rpath", (String) it.next()}));
        }
        return arrayList;
    }

    public final Command dsymUtilCommand(String executable, String outputDsymBundle) {
        Intrinsics.checkNotNullParameter(executable, "executable");
        Intrinsics.checkNotNullParameter(outputDsymBundle, "outputDsymBundle");
        return new Command(dsymutilCommand(executable, outputDsymBundle)) { // from class: org.jetbrains.kotlin.konan.target.MacOSBasedLinker.dsymUtilCommand.1
            @Override // org.jetbrains.kotlin.konan.exec.Command
            public int runProcess() {
                return MacOSBasedLinker.this.executeCommandWithFilter(getCommand());
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int executeCommandWithFilter(List<String> command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        boolean zAreEqual = Intrinsics.areEqual(command.get(0), this.dsymutil);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
        if (!zAreEqual) {
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        }
        Process processStart = processBuilder.start();
        if (zAreEqual) {
            BufferedReader bufferedReader = FileKt.bufferedReader(processStart.getErrorStream());
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (!StringsKt.contains$default((CharSequence) line, (CharSequence) "warning: could not find object file symbol for symbol _main", false, 2, (Object) null)) {
                    System.err.println(line);
                }
            }
            bufferedReader.close();
        }
        return processStart.waitFor();
    }

    public final List<String> dsymutilCommand(String executable, String outputDsymBundle) {
        Intrinsics.checkNotNullParameter(executable, "executable");
        Intrinsics.checkNotNullParameter(outputDsymBundle, "outputDsymBundle");
        return CollectionsKt.listOf((Object[]) new String[]{this.dsymutil, executable, "-o", outputDsymBundle});
    }

    public final List<String> dsymutilDryRunVerboseCommand(String executable) {
        Intrinsics.checkNotNullParameter(executable, "executable");
        return CollectionsKt.listOf((Object[]) new String[]{this.dsymutil, "-dump-debug-map", executable});
    }
}
