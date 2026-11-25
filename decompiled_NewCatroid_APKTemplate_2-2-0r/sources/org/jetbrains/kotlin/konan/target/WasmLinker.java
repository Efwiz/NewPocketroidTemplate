package org.jetbrains.kotlin.konan.target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.exec.Command;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.NativeFileTypeKt;

/* JADX INFO: compiled from: Linker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0013\u0010M\u001a\u00020\u00062\b\u0010N\u001a\u0004\u0018\u00010\u0006H\u0096\u0001J\t\u0010O\u001a\u00020PH\u0096\u0001J\u001c\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\f\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00060\u000eH\u0016J\u0082\u0001\u0010S\u001a\b\u0012\u0004\u0012\u00020T0\u000e2\u0010\u0010U\u001a\f\u0012\b\u0012\u00060\u0006j\u0002`V0\u000e2\n\u0010W\u001a\u00060\u0006j\u0002`X2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0006\u0010[\u001a\u00020J2\u0006\u0010\\\u001a\u00020J2\u0006\u0010]\u001a\u00020^2\u0006\u0010_\u001a\u00020\u00062\u0006\u0010`\u001a\u00020J2\u0006\u0010a\u001a\u00020J2\b\u0010b\u001a\u0004\u0018\u00010cH\u0016J\u0017\u0010d\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0006\u0010e\u001a\u00020\u0006H\u0096\u0001J\u0013\u0010f\u001a\u0004\u0018\u00010\u00062\u0006\u0010e\u001a\u00020\u0006H\u0096\u0001J\u0017\u0010g\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0006\u0010e\u001a\u00020\u0006H\u0096\u0001J\u0013\u0010h\u001a\u0004\u0018\u00010\u00062\u0006\u0010e\u001a\u00020\u0006H\u0096\u0001J\u0017\u0010i\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0006\u0010e\u001a\u00020\u0006H\u0096\u0001J\u0013\u0010j\u001a\u0004\u0018\u00010\u00062\u0006\u0010e\u001a\u00020\u0006H\u0096\u0001R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0010R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0010R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0010R\u0014\u0010\u001b\u001a\u00020\u001c8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\u001c8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b \u0010\u001eR\u0016\u0010!\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\"\u0010\bR\u001a\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b$\u0010\u0010R\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b&\u0010\u0010R\u001a\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b(\u0010\u0010R\u001a\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b*\u0010\u0010R\u001a\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b,\u0010\u0010R\u0016\u0010-\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b.\u0010\bR\u0016\u0010/\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b0\u0010\bR\u0016\u00101\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b2\u0010\bR\u001a\u00103\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b4\u0010\u0010R\u001a\u00105\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b6\u0010\u0010R\u0014\u00107\u001a\u00020\u001c8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b8\u0010\u001eR\u0012\u00109\u001a\u00020:X\u0096\u0005¢\u0006\u0006\u001a\u0004\b;\u0010<R\u0016\u0010=\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b>\u0010\bR\u0016\u0010?\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b@\u0010\bR\u0016\u0010A\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bB\u0010\bR\u0016\u0010C\u001a\u0004\u0018\u00010\u00068VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bD\u0010\bR\u0014\u0010E\u001a\u00020F8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bG\u0010HR\u0014\u0010I\u001a\u00020J8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010L¨\u0006k"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/WasmLinker;", "Lorg/jetbrains/kotlin/konan/target/LinkerFlags;", "Lorg/jetbrains/kotlin/konan/target/WasmConfigurables;", "targetProperties", "(Lorg/jetbrains/kotlin/konan/target/WasmConfigurables;)V", "absoluteLlvmHome", "", "getAbsoluteLlvmHome", "()Ljava/lang/String;", "absoluteTargetSysRoot", "getAbsoluteTargetSysRoot", "absoluteTargetToolchain", "getAbsoluteTargetToolchain", "additionalCacheFlags", "", "getAdditionalCacheFlags", "()Ljava/util/List;", "cacheableTargets", "getCacheableTargets", "clangDebugFlags", "getClangDebugFlags", "clangFlags", "getClangFlags", "clangNooptFlags", "getClangNooptFlags", "clangOptFlags", "getClangOptFlags", "dynamicLibraryRelocationMode", "Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "getDynamicLibraryRelocationMode", "()Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags$Mode;", "executableRelocationMode", "getExecutableRelocationMode", "libffiDir", "getLibffiDir", "linkerDynamicFlags", "getLinkerDynamicFlags", "linkerKonanFlags", "getLinkerKonanFlags", "linkerNoDebugFlags", "getLinkerNoDebugFlags", "linkerOptimizationFlags", "getLinkerOptimizationFlags", "lldFlags", "getLldFlags", "llvmHome", "getLlvmHome", "llvmInlineThreshold", "getLlvmInlineThreshold", "llvmVersion", "getLlvmVersion", "mimallocLinkerDependencies", "getMimallocLinkerDependencies", "runtimeDefinitions", "getRuntimeDefinitions", "staticLibraryRelocationMode", "getStaticLibraryRelocationMode", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetCpu", "getTargetCpu", "targetCpuFeatures", "getTargetCpuFeatures", "targetSysRoot", "getTargetSysRoot", "targetToolchain", "getTargetToolchain", "targetTriple", "Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "getTargetTriple", "()Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "useCompilerDriverAsLinker", "", "getUseCompilerDriverAsLinker", "()Z", "absolute", "value", "downloadDependencies", "", "filterStaticLibraries", "binaries", "finalLinkCommands", "Lorg/jetbrains/kotlin/konan/exec/Command;", "objectFiles", "Lorg/jetbrains/kotlin/konan/target/ObjectFile;", "executable", "Lorg/jetbrains/kotlin/konan/target/ExecutableFile;", "libraries", "linkerArgs", "optimize", "debug", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/konan/target/LinkerOutputKind;", "outputDsymBundle", "needsProfileLibrary", "mimallocEnabled", "sanitizer", "Lorg/jetbrains/kotlin/konan/target/SanitizerKind;", "hostList", ASTExpr.DEFAULT_MAP_KEY_NAME, "hostString", "hostTargetList", "hostTargetString", "targetList", "targetString", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class WasmLinker extends LinkerFlags implements WasmConfigurables {
    private final /* synthetic */ WasmConfigurables $$delegate_0;

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

    @Override // org.jetbrains.kotlin.konan.target.LldFlags
    public List<String> getLldFlags() {
        return this.$$delegate_0.getLldFlags();
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
        return false;
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
    public WasmLinker(WasmConfigurables targetProperties) {
        super(targetProperties);
        Intrinsics.checkNotNullParameter(targetProperties, "targetProperties");
        this.$$delegate_0 = targetProperties;
    }

    @Override // org.jetbrains.kotlin.konan.target.LinkerFlags
    public List<String> filterStaticLibraries(List<String> binaries) {
        Intrinsics.checkNotNullParameter(binaries, "binaries");
        ArrayList arrayList = new ArrayList();
        for (Object obj : binaries) {
            if (NativeFileTypeKt.isJavaScript((String) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.konan.target.LinkerFlags
    public List<Command> finalLinkCommands(List<String> objectFiles, final String executable, final List<String> libraries, List<String> linkerArgs, boolean optimize, boolean debug, LinkerOutputKind kind, String outputDsymBundle, boolean needsProfileLibrary, boolean mimallocEnabled, SanitizerKind sanitizer) {
        Intrinsics.checkNotNullParameter(objectFiles, "objectFiles");
        Intrinsics.checkNotNullParameter(executable, "executable");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(linkerArgs, "linkerArgs");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(outputDsymBundle, "outputDsymBundle");
        if (kind != LinkerOutputKind.EXECUTABLE) {
            throw new Error("Unsupported linker output kind");
        }
        if (sanitizer != null) {
            throw new IllegalArgumentException("Sanitizers are unsupported".toString());
        }
        Command command = new Command(getLlvmBin() + "/wasm-ld");
        command.unaryPlus(objectFiles);
        command.unaryPlus(CollectionsKt.listOf((Object[]) new String[]{"-o", executable}));
        command.unaryPlus(getLldFlags());
        final String[] strArr = new String[0];
        return CollectionsKt.listOf((Object[]) new Command[]{command, new Command(strArr) { // from class: org.jetbrains.kotlin.konan.target.WasmLinker$finalLinkCommands$jsBindingsGeneration$1
            @Override // org.jetbrains.kotlin.konan.exec.Command
            public void execute() throws IOException {
                javaScriptLink(libraries, executable);
            }

            private final String javaScriptLink(List<String> jsFiles, String executable2) throws IOException {
                File file = new File(executable2 + ".js");
                file.writeText("var konan = { libraries: [] };\n");
                Iterator<T> it = jsFiles.iterator();
                while (it.hasNext()) {
                    byte[] bytes = new File((String) it.next()).readBytes();
                    Intrinsics.checkNotNullExpressionValue(bytes, "File(it).readBytes()");
                    file.appendBytes(bytes);
                }
                byte[] bytes2 = "if (isBrowser()) {\n   konan.moduleEntry([]);\n} else {\n   konan.moduleEntry(arguments);\n}".getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
                file.appendBytes(bytes2);
                return file.getName();
            }
        }});
    }
}
