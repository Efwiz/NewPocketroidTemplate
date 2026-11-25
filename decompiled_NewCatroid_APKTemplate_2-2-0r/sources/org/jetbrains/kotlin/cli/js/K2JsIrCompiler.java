package org.jetbrains.kotlin.cli.js;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.reandroid.apk.UncompressedFiles;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.analyzer.CompilationErrorException;
import org.jetbrains.kotlin.backend.common.CompilationException;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataIncrementalSerializer;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt;
import org.jetbrains.kotlin.backend.wasm.dce.DceKt;
import org.jetbrains.kotlin.cli.common.CLICompiler;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CLITool;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.CreatePhaseConfigKt;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.GroupedKtSources;
import org.jetbrains.kotlin.cli.common.GroupedKtSourcesKt;
import org.jetbrains.kotlin.cli.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.cli.common.config.ContentRootsKt;
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageUtil;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.cli.js.klib.AnalyzedFirWithPsiOutput;
import org.jetbrains.kotlin.cli.js.klib.CompilerPipelineKt;
import org.jetbrains.kotlin.cli.js.klib.IrForKlibKt;
import org.jetbrains.kotlin.cli.js.klib.TopDownAnalyzerFacadeForJSIR;
import org.jetbrains.kotlin.cli.js.klib.TopDownAnalyzerFacadeForWasm;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.HmppCliModuleKt;
import org.jetbrains.kotlin.config.HmppCliModuleStructure;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporterFactory;
import org.jetbrains.kotlin.diagnostics.impl.PendingDiagnosticsCollectorWithSuppress;
import org.jetbrains.kotlin.fir.pipeline.Fir2IrActualizedResult;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.incremental.js.IncrementalNextRoundChecker;
import org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer;
import org.jetbrains.kotlin.ir.backend.js.CompilerKt;
import org.jetbrains.kotlin.ir.backend.js.JsIrCompilerWithIC;
import org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt;
import org.jetbrains.kotlin.ir.backend.js.KlibKt;
import org.jetbrains.kotlin.ir.backend.js.KotlinFileSerializedData;
import org.jetbrains.kotlin.ir.backend.js.LoweredIr;
import org.jetbrains.kotlin.ir.backend.js.MainModule;
import org.jetbrains.kotlin.ir.backend.js.ModulesStructure;
import org.jetbrains.kotlin.ir.backend.js.PrepareAnalyzedSourceModuleKt;
import org.jetbrains.kotlin.ir.backend.js.SourceMapsInfo;
import org.jetbrains.kotlin.ir.backend.js.WholeWorldStageController;
import org.jetbrains.kotlin.ir.backend.js.dce.DceDumpNameCache;
import org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater;
import org.jetbrains.kotlin.ir.backend.js.ic.DirtyFileState;
import org.jetbrains.kotlin.ir.backend.js.ic.IncrementalCacheGuard;
import org.jetbrains.kotlin.ir.backend.js.ic.JsExecutableProducer;
import org.jetbrains.kotlin.ir.backend.js.ic.JsIrCompilerICInterface;
import org.jetbrains.kotlin.ir.backend.js.ic.JsIrCompilerICInterfaceFactory;
import org.jetbrains.kotlin.ir.backend.js.ic.KotlinLibraryFile;
import org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFile;
import org.jetbrains.kotlin.ir.backend.js.ic.ModuleArtifact;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputsBuilt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrModuleToJsTransformer;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsCodeGenerator;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsGenerationGranularity;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.TranslationMode;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImplForJsIC;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageConfigKt;
import org.jetbrains.kotlin.js.analyzer.JsAnalysisResult;
import org.jetbrains.kotlin.js.config.EcmaVersion;
import org.jetbrains.kotlin.js.config.ErrorTolerancePolicy;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.RuntimeDiagnostic;
import org.jetbrains.kotlin.js.config.SourceMapNamesPolicy;
import org.jetbrains.kotlin.js.config.SourceMapSourceEmbedding;
import org.jetbrains.kotlin.js.config.WasmTarget;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryUtilsKt;
import org.jetbrains.kotlin.library.impl.BuiltInsPlatform;
import org.jetbrains.kotlin.library.metadata.KlibMetadataVersion;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.progress.IncrementalNextRoundException;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.serialization.js.ModuleKind;
import org.jetbrains.kotlin.utils.KotlinPaths;

/* JADX INFO: compiled from: K2JsIrCompiler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0004\u0018\u0000 /2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003/01B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\u0002H\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J*\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0017H\u0016JV\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00170\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00170\u001f2\u0006\u0010!\u001a\u00020\u00112\u000e\u0010\"\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u001fH\u0002J<\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00170\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00170\u001f2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010'\u001a\u00020\u0017H\u0002J<\u0010(\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00170\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00170\u001f2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010'\u001a\u00020\u0017H\u0002J \u0010)\u001a\u00020*2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010+\u001a\u00020,H\u0014J\u001a\u0010-\u001a\u00020**\b\u0012\u0004\u0012\u00020\u00170.2\u0006\u0010\u000f\u001a\u00020\u0002H\u0014R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/K2JsIrCompiler;", "Lorg/jetbrains/kotlin/cli/common/CLICompiler;", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;", "()V", "defaultPerformanceManager", "Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "getDefaultPerformanceManager", "()Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "createArguments", "createMetadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "versionArray", "", "doExecute", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "arguments", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "rootDisposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", UncompressedFiles.NAME_paths, "Lorg/jetbrains/kotlin/utils/KotlinPaths;", "executableScriptFileName", "", "prepareIcCaches", "Lorg/jetbrains/kotlin/cli/js/K2JsIrCompiler$IcCachesArtifacts;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", ModuleXmlParser.OUTPUT_DIR, "Ljava/io/File;", "libraries", "", "friendLibraries", "configurationJs", "mainCallArguments", "processSourceModule", "Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;", "environmentForJS", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "outputKlibPath", "processSourceModuleWithK2", "setupPlatformSpecificArgumentsAndServices", "", "services", "Lorg/jetbrains/kotlin/config/Services;", "addPlatformOptions", "", "Companion", "IcCachesArtifacts", "Ir2JsTransformer", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2JsIrCompiler extends CLICompiler<K2JSCompilerArguments> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Map<String, ModuleKind> moduleKindMap = MapsKt.mapOf(TuplesKt.to(K2JsArgumentConstants.MODULE_PLAIN, ModuleKind.PLAIN), TuplesKt.to(K2JsArgumentConstants.MODULE_COMMONJS, ModuleKind.COMMON_JS), TuplesKt.to(K2JsArgumentConstants.MODULE_AMD, ModuleKind.AMD), TuplesKt.to(K2JsArgumentConstants.MODULE_UMD, ModuleKind.UMD), TuplesKt.to("es", ModuleKind.ES));
    private static final Map<String, SourceMapSourceEmbedding> sourceMapContentEmbeddingMap = MapsKt.mapOf(TuplesKt.to("always", SourceMapSourceEmbedding.ALWAYS), TuplesKt.to("never", SourceMapSourceEmbedding.NEVER), TuplesKt.to(K2JsArgumentConstants.SOURCE_MAP_SOURCE_CONTENT_INLINING, SourceMapSourceEmbedding.INLINING));
    private static final Map<String, SourceMapNamesPolicy> sourceMapNamesPolicyMap = MapsKt.mapOf(TuplesKt.to("no", SourceMapNamesPolicy.NO), TuplesKt.to(K2JsArgumentConstants.SOURCE_MAP_NAMES_POLICY_SIMPLE_NAMES, SourceMapNamesPolicy.SIMPLE_NAMES), TuplesKt.to(K2JsArgumentConstants.SOURCE_MAP_NAMES_POLICY_FQ_NAMES, SourceMapNamesPolicy.FULLY_QUALIFIED_NAMES));
    private final CommonCompilerPerformanceManager defaultPerformanceManager = new CommonCompilerPerformanceManager() { // from class: org.jetbrains.kotlin.cli.js.K2JsIrCompiler$defaultPerformanceManager$1
    };

    @JvmStatic
    public static final void main(String[] strArr) {
        INSTANCE.main(strArr);
    }

    protected void addPlatformOptions(List<String> list, K2JSCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public /* bridge */ /* synthetic */ void addPlatformOptions(List list, CommonCompilerArguments commonCompilerArguments) {
        addPlatformOptions((List<String>) list, (K2JSCompilerArguments) commonCompilerArguments);
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public CommonCompilerPerformanceManager getDefaultPerformanceManager() {
        return this.defaultPerformanceManager;
    }

    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public K2JSCompilerArguments createArguments() {
        return new K2JSCompilerArguments();
    }

    /* JADX INFO: compiled from: K2JsIrCompiler.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b¢\u0006\u0002\u0010\rJ\u0006\u0010\u0018\u001a\u00020\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/K2JsIrCompiler$Ir2JsTransformer;", "", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;", "module", "Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "mainCallArguments", "", "", "(Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Ljava/util/List;)V", "getArguments", "()Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;", "getMainCallArguments", "()Ljava/util/List;", "getMessageCollector", "()Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "getModule", "()Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;", "getPhaseConfig", "()Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "compileAndTransformIrNew", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuilt;", "lowerIr", "Lorg/jetbrains/kotlin/ir/backend/js/LoweredIr;", "makeJsCodeGenerator", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsCodeGenerator;", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Ir2JsTransformer {
        private final K2JSCompilerArguments arguments;
        private final List<String> mainCallArguments;
        private final MessageCollector messageCollector;
        private final ModulesStructure module;
        private final PhaseConfig phaseConfig;

        public Ir2JsTransformer(K2JSCompilerArguments arguments, ModulesStructure module, PhaseConfig phaseConfig, MessageCollector messageCollector, List<String> list) {
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
            Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
            this.arguments = arguments;
            this.module = module;
            this.phaseConfig = phaseConfig;
            this.messageCollector = messageCollector;
            this.mainCallArguments = list;
        }

        public final K2JSCompilerArguments getArguments() {
            return this.arguments;
        }

        public final ModulesStructure getModule() {
            return this.module;
        }

        public final PhaseConfig getPhaseConfig() {
            return this.phaseConfig;
        }

        public final MessageCollector getMessageCollector() {
            return this.messageCollector;
        }

        public final List<String> getMainCallArguments() {
            return this.mainCallArguments;
        }

        /* JADX WARN: Code duplicated, block: B:16:0x0061  */
        private final LoweredIr lowerIr() {
            Set setEmptySet;
            List listSplit$default;
            ModulesStructure modulesStructure = this.module;
            PhaseConfig phaseConfig = this.phaseConfig;
            IrFactoryImplForJsIC irFactoryImplForJsIC = new IrFactoryImplForJsIC(new WholeWorldStageController());
            String irKeep = this.arguments.getIrKeep();
            if (irKeep == null || (listSplit$default = StringsKt.split$default((CharSequence) irKeep, new String[]{","}, false, 0, 6, (Object) null)) == null) {
                setEmptySet = SetsKt.emptySet();
            } else {
                ArrayList arrayList = new ArrayList();
                for (Object obj : listSplit$default) {
                    if (((String) obj).length() != 0) {
                        arrayList.add(obj);
                    }
                }
                setEmptySet = CollectionsKt.toSet(arrayList);
                if (setEmptySet == null) {
                    setEmptySet = SetsKt.emptySet();
                }
            }
            return CompilerKt.compile(modulesStructure, phaseConfig, irFactoryImplForJsIC, (584 & 8) != 0 ? SetsKt.emptySet() : null, (584 & 16) != 0 ? SetsKt.emptySet() : setEmptySet, (584 & 32) != 0 ? null : K2JsIrCompilerKt.resolve(RuntimeDiagnostic.INSTANCE, this.arguments.getIrDceRuntimeDiagnostic(), this.messageCollector), (584 & 64) != 0, (584 & 128) != 0 ? false : this.arguments.getIrSafeExternalBoolean(), (584 & 256) != 0 ? null : K2JsIrCompilerKt.resolve(RuntimeDiagnostic.INSTANCE, this.arguments.getIrSafeExternalBooleanDiagnostic(), this.messageCollector), (584 & 512) != 0 ? null : null, (584 & 1024) != 0 ? JsGenerationGranularity.WHOLE_PROGRAM : K2JsIrCompilerKt.getGranularity(this.arguments));
        }

        private final JsCodeGenerator makeJsCodeGenerator() {
            LoweredIr loweredIrLowerIr = lowerIr();
            return new IrModuleToJsTransformer(loweredIrLowerIr.getContext(), this.mainCallArguments, loweredIrLowerIr.getModuleFragmentToUniqueName(), false, 8, null).makeJsCodeGenerator(loweredIrLowerIr.getAllModules(), TranslationMode.INSTANCE.fromFlags(this.arguments.getIrDce(), K2JsIrCompilerKt.getGranularity(this.arguments), this.arguments.getIrMinimizedMemberNames()));
        }

        public final CompilationOutputsBuilt compileAndTransformIrNew() {
            return makeJsCodeGenerator().generateJsCode(true, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public ExitCode doExecute(K2JSCompilerArguments arguments, CompilerConfiguration configuration, Disposable rootDisposable, KotlinPaths paths) {
        Set setEmptySet;
        File file;
        ModulesStructure modulesStructureProcessSourceModule;
        ModulesStructure modulesStructure;
        Object next;
        File file2;
        String absolutePath;
        String includes;
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(rootDisposable, "rootDisposable");
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        K2JSCompilerArguments k2JSCompilerArguments = arguments;
        ExitCode exitCodeLoadPlugins = loadPlugins(paths, k2JSCompilerArguments, configuration);
        if (exitCodeLoadPlugins != ExitCode.OK) {
            return exitCodeLoadPlugins;
        }
        if (arguments.getScript()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "K/JS does not support Kotlin script (*.kts) files", null, 4, null);
            return ExitCode.COMPILATION_ERROR;
        }
        if (arguments.getFreeArgs().isEmpty() && !UtilsKt.incrementalCompilationIsEnabledForJs(k2JSCompilerArguments)) {
            if (arguments.getVersion()) {
                return ExitCode.OK;
            }
            String includes2 = arguments.getIncludes();
            if (includes2 == null || includes2.length() == 0) {
                messageCollector.report(CompilerMessageSeverity.ERROR, "Specify at least one source file or directory", null);
                return ExitCode.COMPILATION_ERROR;
            }
        }
        Companion companion = INSTANCE;
        List<String> listPlus = CollectionsKt.plus((Collection) companion.configureLibraries(arguments.getLibraries()), (Iterable) CollectionsKt.listOfNotNull(arguments.getIncludes()));
        List<String> listConfigureLibraries = companion.configureLibraries(arguments.getFriendModules());
        configuration.put(JSConfigurationKeys.LIBRARIES, listPlus);
        configuration.put(JSConfigurationKeys.TRANSITIVE_LIBRARIES, listPlus);
        configuration.put(JSConfigurationKeys.WASM_ENABLE_ARRAY_RANGE_CHECKS, Boolean.valueOf(arguments.getWasmEnableArrayRangeChecks()));
        configuration.put(JSConfigurationKeys.WASM_ENABLE_ASSERTS, Boolean.valueOf(arguments.getWasmEnableAsserts()));
        configuration.put(JSConfigurationKeys.WASM_GENERATE_WAT, Boolean.valueOf(arguments.getWasmGenerateWat()));
        configuration.put(JSConfigurationKeys.WASM_USE_TRAPS_INSTEAD_OF_EXCEPTIONS, Boolean.valueOf(arguments.getWasmUseTrapsInsteadOfExceptions()));
        CompilerConfigurationKey<WasmTarget> compilerConfigurationKey = JSConfigurationKeys.WASM_TARGET;
        String wasmTarget = arguments.getWasmTarget();
        configuration.putIfNotNull(compilerConfigurationKey, wasmTarget != null ? WasmTarget.INSTANCE.fromName(wasmTarget) : null);
        configuration.put(JSConfigurationKeys.USE_ES6_CLASSES, Boolean.valueOf(arguments.getUseEsClasses()));
        configuration.put(JSConfigurationKeys.OPTIMIZE_GENERATED_JS, Boolean.valueOf(arguments.getOptimizeGeneratedJs()));
        String[] commonSources = arguments.getCommonSources();
        if (commonSources == null || (setEmptySet = ArraysKt.toSet(commonSources)) == null) {
            setEmptySet = SetsKt.emptySet();
        }
        HmppCliModuleStructure hmppCliModuleStructure = (HmppCliModuleStructure) configuration.get(CommonConfigurationKeys.HMPP_MODULE_STRUCTURE);
        for (String str : arguments.getFreeArgs()) {
            ContentRootsKt.addKotlinSourceRoot(configuration, str, setEmptySet.contains(str), hmppCliModuleStructure != null ? HmppCliModuleKt.getModuleNameForSource(hmppCliModuleStructure, str) : null);
        }
        String[] relativePathBases = arguments.getRelativePathBases();
        if (relativePathBases != null) {
            configuration.put(CommonConfigurationKeys.KLIB_RELATIVE_PATH_BASES, ArraysKt.toList(relativePathBases));
        }
        configuration.put(CommonConfigurationKeys.KLIB_NORMALIZE_ABSOLUTE_PATH, Boolean.valueOf(arguments.getNormalizeAbsolutePath()));
        configuration.put(CommonConfigurationKeys.PRODUCE_KLIB_SIGNATURES_CLASH_CHECKS, Boolean.valueOf(arguments.getEnableSignatureClashChecks()));
        KotlinCoreEnvironment kotlinCoreEnvironmentCreateForProduction = KotlinCoreEnvironment.INSTANCE.createForProduction(rootDisposable, configuration, EnvironmentConfigFiles.JS_CONFIG_FILES);
        Project project = kotlinCoreEnvironmentCreateForProduction.getProject();
        CompilerConfiguration configuration2 = kotlinCoreEnvironmentCreateForProduction.getConfiguration();
        List<KtFile> sourceFiles = kotlinCoreEnvironmentCreateForProduction.getSourceFiles();
        configuration2.put(CLIConfigurationKeys.ALLOW_KOTLIN_PACKAGE, Boolean.valueOf(arguments.getAllowKotlinPackage()));
        configuration2.put(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME, Boolean.valueOf(arguments.getRenderInternalDiagnosticNames()));
        configuration2.put(JSConfigurationKeys.PROPERTY_LAZY_INITIALIZATION, Boolean.valueOf(arguments.getIrPropertyLazyInitialization()));
        configuration2.put(JSConfigurationKeys.GENERATE_POLYFILLS, Boolean.valueOf(arguments.getGeneratePolyfills()));
        configuration2.put(JSConfigurationKeys.GENERATE_DTS, Boolean.valueOf(arguments.getGenerateDts()));
        configuration2.put(JSConfigurationKeys.GENERATE_INLINE_ANONYMOUS_FUNCTIONS, Boolean.valueOf(arguments.getIrGenerateInlineAnonymousFunctions()));
        DisposableZipFileSystemAccessor disposableZipFileSystemAccessor = new DisposableZipFileSystemAccessor(64);
        Disposer.register(rootDisposable, disposableZipFileSystemAccessor);
        configuration2.put(JSConfigurationKeys.ZIP_FILE_SYSTEM_ACCESSOR, disposableZipFileSystemAccessor);
        List<KtFile> list = sourceFiles;
        if (!HelpersKt.checkKotlinPackageUsageForPsi(kotlinCoreEnvironmentCreateForProduction.getConfiguration(), list)) {
            return ExitCode.COMPILATION_ERROR;
        }
        String outputDir = arguments.getOutputDir();
        String moduleName = arguments.getModuleName();
        if (outputDir == null) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "IR: Specify output dir via -ir-output-dir", null);
            return ExitCode.COMPILATION_ERROR;
        }
        if (moduleName == null) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "IR: Specify output name via -ir-output-name", null);
            return ExitCode.COMPILATION_ERROR;
        }
        if (messageCollector.getHasErrors()) {
            return ExitCode.COMPILATION_ERROR;
        }
        if (sourceFiles.isEmpty() && !UtilsKt.incrementalCompilationIsEnabledForJs(k2JSCompilerArguments) && ((includes = arguments.getIncludes()) == null || includes.length() == 0)) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "No source files", null);
            return ExitCode.COMPILATION_ERROR;
        }
        if (arguments.getVerbose()) {
            INSTANCE.reportCompiledSourcesList(messageCollector, sourceFiles);
        }
        String irModuleName = arguments.getIrModuleName();
        String str2 = irModuleName == null ? moduleName : irModuleName;
        configuration2.put(CommonConfigurationKeys.MODULE_NAME, str2);
        File file3 = new File(outputDir);
        try {
            configuration2.put(JSConfigurationKeys.OUTPUT_DIR, file3.getCanonicalFile());
            List<String> listEmptyList = Intrinsics.areEqual(K2JsArgumentConstants.NO_CALL, arguments.getMain()) ? null : CollectionsKt.emptyList();
            String str3 = str2;
            IcCachesArtifacts icCachesArtifactsPrepareIcCaches = prepareIcCaches(arguments, messageCollector, file3, listPlus, listConfigureLibraries, configuration2, listEmptyList);
            String includes3 = arguments.getIncludes();
            if (includes3 == null) {
                if (arguments.getIrProduceKlibFile()) {
                    file2 = file3;
                    absolutePath = FilesKt.normalize(FilesKt.resolve(file2, moduleName + KotlinLibraryUtilsKt.KLIB_FILE_EXTENSION_WITH_DOT)).getAbsolutePath();
                } else {
                    file2 = file3;
                    absolutePath = outputDir;
                }
                if (Intrinsics.areEqual(configuration.get(CommonConfigurationKeys.USE_FIR), (Object) true)) {
                    Intrinsics.checkNotNull(absolutePath);
                    file = file2;
                    modulesStructureProcessSourceModule = processSourceModuleWithK2(kotlinCoreEnvironmentCreateForProduction, listPlus, listConfigureLibraries, arguments, absolutePath);
                } else {
                    Intrinsics.checkNotNull(absolutePath);
                    file = file2;
                    modulesStructureProcessSourceModule = processSourceModule(kotlinCoreEnvironmentCreateForProduction, listPlus, listConfigureLibraries, arguments, absolutePath);
                    if (!modulesStructureProcessSourceModule.getJsFrontEndResult().getJsAnalysisResult().getShouldGenerateCode()) {
                        return ExitCode.OK;
                    }
                }
            } else {
                file = file3;
                modulesStructureProcessSourceModule = null;
            }
            if (arguments.getIrProduceJs()) {
                ModuleKind moduleKind = (ModuleKind) configuration2.get(JSConfigurationKeys.MODULE_KIND);
                if (moduleKind == null) {
                    throw new IllegalStateException("cannot get 'module kind' from configuration".toString());
                }
                String str4 = "Produce executable: " + outputDir;
                File file4 = file;
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, str4, null, 4, null);
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "Cache directory: " + arguments.getCacheDirectory(), null, 4, null);
                if (icCachesArtifactsPrepareIcCaches != null) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    icCachesArtifactsPrepareIcCaches.getCacheGuard().tryAcquire();
                    JsExecutableProducer jsExecutableProducer = new JsExecutableProducer(str3, moduleKind, SourceMapsInfo.INSTANCE.from(configuration2), icCachesArtifactsPrepareIcCaches.getArtifacts(), true);
                    JsExecutableProducer.BuildResult buildResultBuildExecutable = jsExecutableProducer.buildExecutable(K2JsIrCompilerKt.getGranularity(arguments), false);
                    CompilationOutputs compilationOut = buildResultBuildExecutable.getCompilationOut();
                    List<String> listComponent2 = buildResultBuildExecutable.component2();
                    compilationOut.writeAll(file4, moduleName, arguments.getGenerateDts(), str3, moduleKind);
                    icCachesArtifactsPrepareIcCaches.getCacheGuard().release();
                    MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "Executable production duration (IC): " + (System.currentTimeMillis() - jCurrentTimeMillis) + LanguageCodeUtil.MS, null, 4, null);
                    for (Map.Entry<String, Long> entry : jsExecutableProducer.getStopwatchLaps().entrySet()) {
                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "  " + entry.getKey() + ": " + ((int) (entry.getValue().longValue() / 1000000.0d)) + LanguageCodeUtil.MS, null, 4, null);
                    }
                    Iterator<String> it = listComponent2.iterator();
                    while (it.hasNext()) {
                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "IC module builder rebuilt JS for module [" + new File(it.next()).getName() + ']', null, 4, null);
                    }
                    return ExitCode.OK;
                }
                PhaseConfig phaseConfigCreatePhaseConfig = CreatePhaseConfigKt.createPhaseConfig(JsLoweringPhasesKt.getJsPhases(), k2JSCompilerArguments, messageCollector);
                if (includes3 != null) {
                    if (!list.isEmpty()) {
                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Source files are not supported when -Xinclude is present", null, 4, null);
                    }
                    String canonicalPath = new File(includes3).getCanonicalPath();
                    Iterator<T> it2 = listPlus.iterator();
                    do {
                        if (!it2.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it2.next();
                    } while (!Intrinsics.areEqual(new File((String) next).getCanonicalPath(), canonicalPath));
                    String str5 = (String) next;
                    if (str5 == null) {
                        throw new IllegalStateException(("No library with name " + includes3 + " (" + canonicalPath + ") found").toString());
                    }
                    modulesStructure = new ModulesStructure(project, new MainModule.Klib(str5), configuration2, listPlus, listConfigureLibraries);
                } else {
                    Intrinsics.checkNotNull(modulesStructureProcessSourceModule);
                    modulesStructure = modulesStructureProcessSourceModule;
                }
                if (arguments.getWasm()) {
                    Pair<List<IrModuleFragment>, WasmBackendContext> pairCompileToLoweredIr = org.jetbrains.kotlin.backend.wasm.CompilerKt.compileToLoweredIr(modulesStructure, CreatePhaseConfigKt.createPhaseConfig(WasmLoweringPhasesKt.getWasmPhases(), k2JSCompilerArguments, messageCollector), IrFactoryImpl.INSTANCE, SetsKt.setOf(new FqName("main")), arguments.getIrPropertyLazyInitialization());
                    List<IrModuleFragment> listComponent1 = pairCompileToLoweredIr.component1();
                    WasmBackendContext wasmBackendContextComponent2 = pairCompileToLoweredIr.component2();
                    DceDumpNameCache dceDumpNameCache = new DceDumpNameCache();
                    if (arguments.getIrDce()) {
                        DceKt.eliminateDeadDeclarations(listComponent1, wasmBackendContextComponent2, dceDumpNameCache);
                    }
                    org.jetbrains.kotlin.ir.backend.js.dce.UtilsKt.dumpDeclarationIrSizesIfNeed(arguments.getIrDceDumpDeclarationIrSizesToFile(), listComponent1, dceDumpNameCache);
                    boolean z = configuration.getBoolean(JSConfigurationKeys.SOURCE_MAP);
                    boolean wasmDebug = arguments.getWasmDebug();
                    boolean irDce = arguments.getIrDce();
                    Object obj = configuration.get(JSConfigurationKeys.WASM_GENERATE_WAT, false);
                    Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
                    org.jetbrains.kotlin.backend.wasm.CompilerKt.writeCompilationResult(org.jetbrains.kotlin.backend.wasm.CompilerKt.compileWasm(listComponent1, wasmBackendContextComponent2, moduleName, wasmDebug, irDce, ((Boolean) obj).booleanValue(), z), file4, moduleName);
                    return ExitCode.OK;
                }
                if (arguments.getIrDceDumpReachabilityInfoToFile() != null) {
                    MessageCollector.report$default(r3, CompilerMessageSeverity.STRONG_WARNING, "Dumping the reachability info to file is supported only for Kotlin/Wasm.", null, 4, null);
                }
                if (arguments.getIrDceDumpDeclarationIrSizesToFile() != null) {
                    MessageCollector.report$default(r3, CompilerMessageSeverity.STRONG_WARNING, "Dumping the size of declarations to file is supported only for Kotlin/Wasm.", null, 4, null);
                }
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                try {
                    CompilationOutputsBuilt compilationOutputsBuiltCompileAndTransformIrNew = new Ir2JsTransformer(arguments, modulesStructure, phaseConfigCreatePhaseConfig, r3, listEmptyList).compileAndTransformIrNew();
                    MessageCollector.report$default(r3, CompilerMessageSeverity.INFO, "Executable production duration: " + (System.currentTimeMillis() - jCurrentTimeMillis2) + LanguageCodeUtil.MS, null, 4, null);
                    compilationOutputsBuiltCompileAndTransformIrNew.writeAll(file4, moduleName, arguments.getGenerateDts(), str3, moduleKind);
                } catch (CompilationException e) {
                    r3.report(CompilerMessageSeverity.ERROR, ExceptionsKt.stackTraceToString(e), CompilerMessageLocation.INSTANCE.create(e.getPath(), e.getLine(), e.getColumn(), e.getContent()));
                    return ExitCode.INTERNAL_ERROR;
                }
            }
            return ExitCode.OK;
        } catch (IOException unused) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "Could not resolve output directory", null);
            return ExitCode.COMPILATION_ERROR;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [T, org.jetbrains.kotlin.ir.backend.js.ModulesStructure] */
    private final ModulesStructure processSourceModule(KotlinCoreEnvironment environmentForJS, List<String> libraries, List<String> friendLibraries, K2JSCompilerArguments arguments, String outputKlibPath) throws IOException {
        TopDownAnalyzerFacadeForJSIR topDownAnalyzerFacadeForJSIRFacadeFor;
        ModulesStructure modulesStructure;
        boolean z;
        ModulesStructure modulesStructure2;
        ModulesStructure modulesStructure3;
        List<KotlinFileSerializedData> listEmptyList;
        ModulesStructure modulesStructure4;
        ModulesStructure modulesStructure5;
        ModulesStructure modulesStructure6;
        ModulesStructure modulesStructure7;
        ModulesStructure modulesStructure8;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        do {
            if (arguments.getWasm()) {
                topDownAnalyzerFacadeForJSIRFacadeFor = TopDownAnalyzerFacadeForWasm.INSTANCE.facadeFor((WasmTarget) environmentForJS.getConfiguration().get(JSConfigurationKeys.WASM_TARGET));
            } else {
                topDownAnalyzerFacadeForJSIRFacadeFor = TopDownAnalyzerFacadeForJSIR.INSTANCE;
            }
            objectRef.element = PrepareAnalyzedSourceModuleKt.prepareAnalyzedSourceModule$default(environmentForJS.getProject(), environmentForJS.getSourceFiles(), environmentForJS.getConfiguration(), libraries, friendLibraries, new AnalyzerWithCompilerReport(environmentForJS.getConfiguration()), null, topDownAnalyzerFacadeForJSIRFacadeFor, 64, null);
            if (objectRef.element == 0) {
                Intrinsics.throwUninitializedPropertyAccessException("sourceModule");
                modulesStructure = null;
            } else {
                modulesStructure = (ModulesStructure) objectRef.element;
            }
            AnalysisResult jsAnalysisResult = modulesStructure.getJsFrontEndResult().getJsAnalysisResult();
            z = jsAnalysisResult instanceof JsAnalysisResult.RetryWithAdditionalRoots;
            if (z) {
                environmentForJS.addKotlinSourceRoots(((JsAnalysisResult.RetryWithAdditionalRoots) jsAnalysisResult).getAdditionalKotlinRoots());
            }
        } while (z);
        if (objectRef.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("sourceModule");
            modulesStructure2 = null;
        } else {
            modulesStructure2 = (ModulesStructure) objectRef.element;
        }
        if (modulesStructure2.getJsFrontEndResult().getJsAnalysisResult().getShouldGenerateCode() && (arguments.getIrProduceKlibDir() || arguments.getIrProduceKlibFile())) {
            if (objectRef.element == 0) {
                Intrinsics.throwUninitializedPropertyAccessException("sourceModule");
                modulesStructure3 = null;
            } else {
                modulesStructure3 = (ModulesStructure) objectRef.element;
            }
            MainModule mainModule = modulesStructure3.getMainModule();
            Intrinsics.checkNotNull(mainModule, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.backend.js.MainModule.SourceFiles");
            List<KtFile> files = ((MainModule.SourceFiles) mainModule).getFiles();
            IncrementalDataProvider incrementalDataProvider = KlibKt.getIncrementalDataProvider(environmentForJS.getConfiguration());
            if (incrementalDataProvider == null || (listEmptyList = KlibKt.getSerializedDataByPsiFiles(incrementalDataProvider, files)) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            Project project = environmentForJS.getProject();
            CompilerConfiguration configuration = environmentForJS.getConfiguration();
            if (objectRef.element == 0) {
                Intrinsics.throwUninitializedPropertyAccessException("sourceModule");
                modulesStructure4 = null;
            } else {
                modulesStructure4 = (ModulesStructure) objectRef.element;
            }
            AnalysisResult jsAnalysisResult2 = modulesStructure4.getJsFrontEndResult().getJsAnalysisResult();
            if (objectRef.element == 0) {
                Intrinsics.throwUninitializedPropertyAccessException("sourceModule");
                modulesStructure5 = null;
            } else {
                modulesStructure5 = (ModulesStructure) objectRef.element;
            }
            final IrModuleFragment irModuleFragmentComponent1 = IrForKlibKt.generateIrForKlibSerialization(project, files, configuration, jsAnalysisResult2, modulesStructure5.getAllDependencies(), listEmptyList, linkedHashMap, IrFactoryImpl.INSTANCE, true, new Function1<KotlinLibrary, ModuleDescriptor>() { // from class: org.jetbrains.kotlin.cli.js.K2JsIrCompiler.processSourceModule.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ModuleDescriptor invoke(KotlinLibrary it) {
                    ModulesStructure modulesStructure9;
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (objectRef.element == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("sourceModule");
                        modulesStructure9 = null;
                    } else {
                        modulesStructure9 = objectRef.element;
                    }
                    return modulesStructure9.getModuleDescriptor(it);
                }
            }).component1();
            CompilerConfiguration configuration2 = environmentForJS.getConfiguration();
            if (objectRef.element == 0) {
                Intrinsics.throwUninitializedPropertyAccessException("sourceModule");
                modulesStructure6 = null;
            } else {
                modulesStructure6 = (ModulesStructure) objectRef.element;
            }
            Project project2 = modulesStructure6.getProject();
            if (objectRef.element == 0) {
                Intrinsics.throwUninitializedPropertyAccessException("sourceModule");
                modulesStructure7 = null;
            } else {
                modulesStructure7 = (ModulesStructure) objectRef.element;
            }
            final KlibMetadataIncrementalSerializer KlibMetadataIncrementalSerializer = KlibKt.KlibMetadataIncrementalSerializer(configuration2, project2, modulesStructure7.getJsFrontEndResult().getHasErrors());
            if (objectRef.element == 0) {
                Intrinsics.throwUninitializedPropertyAccessException("sourceModule");
                modulesStructure8 = null;
            } else {
                modulesStructure8 = (ModulesStructure) objectRef.element;
            }
            KlibKt.generateKLib(modulesStructure8, outputKlibPath, arguments.getIrProduceKlibDir(), (8 & 8) != 0 ? KotlinAbiVersion.INSTANCE.getCURRENT() : null, arguments.getIrPerModuleOutputName(), listEmptyList, linkedHashMap, irModuleFragmentComponent1, (8 & 256) != 0 ? BuiltInsPlatform.JS : arguments.getWasm() ? BuiltInsPlatform.WASM : BuiltInsPlatform.JS, new Function1<KtSourceFile, ProtoBuf.PackageFragment>() { // from class: org.jetbrains.kotlin.cli.js.K2JsIrCompiler.processSourceModule.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ProtoBuf.PackageFragment invoke(KtSourceFile file) {
                    ModulesStructure modulesStructure9;
                    Intrinsics.checkNotNullParameter(file, "file");
                    KlibMetadataIncrementalSerializer klibMetadataIncrementalSerializer = KlibMetadataIncrementalSerializer;
                    if (objectRef.element == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("sourceModule");
                        modulesStructure9 = null;
                    } else {
                        modulesStructure9 = objectRef.element;
                    }
                    return KlibKt.serializeScope(klibMetadataIncrementalSerializer, file, modulesStructure9.getJsFrontEndResult().getBindingContext(), irModuleFragmentComponent1.getDescriptor());
                }
            });
        }
        if (objectRef.element != 0) {
            return (ModulesStructure) objectRef.element;
        }
        Intrinsics.throwUninitializedPropertyAccessException("sourceModule");
        return null;
    }

    private final ModulesStructure processSourceModuleWithK2(KotlinCoreEnvironment environmentForJS, List<String> libraries, List<String> friendLibraries, K2JSCompilerArguments arguments, String outputKlibPath) throws IOException {
        AnalyzedFirWithPsiOutput analyzedFirWithPsiOutputCompileModuleToAnalyzedFirWithPsi;
        CompilerConfiguration configuration = environmentForJS.getConfiguration();
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default = DiagnosticReporterFactory.createPendingReporter$default(DiagnosticReporterFactory.INSTANCE, null, 1, null);
        ModulesStructure modulesStructure = new ModulesStructure(environmentForJS.getProject(), new MainModule.SourceFiles(environmentForJS.getSourceFiles()), configuration, libraries, friendLibraries);
        LookupTracker.DO_NOTHING do_nothing = (LookupTracker) configuration.get(CommonConfigurationKeys.LOOKUP_TRACKER);
        if (do_nothing == null) {
            do_nothing = LookupTracker.DO_NOTHING.INSTANCE;
        }
        Intrinsics.checkNotNull(do_nothing);
        if (configuration.getBoolean(CommonConfigurationKeys.USE_FIR) && configuration.getBoolean(CommonConfigurationKeys.USE_LIGHT_TREE)) {
            GroupedKtSources groupedKtSourcesCollectSources = GroupedKtSourcesKt.collectSources(configuration, environmentForJS.getProject(), messageCollector);
            analyzedFirWithPsiOutputCompileModuleToAnalyzedFirWithPsi = CompilerPipelineKt.compileModulesToAnalyzedFirWithLightTree(modulesStructure, groupedKtSourcesCollectSources, CollectionsKt.plus((Collection) groupedKtSourcesCollectSources.getCommonSources(), (Iterable) groupedKtSourcesCollectSources.getPlatformSources()), libraries, friendLibraries, pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default, (IncrementalDataProvider) configuration.get(JSConfigurationKeys.INCREMENTAL_DATA_PROVIDER), do_nothing, arguments.getWasm());
        } else {
            analyzedFirWithPsiOutputCompileModuleToAnalyzedFirWithPsi = CompilerPipelineKt.compileModuleToAnalyzedFirWithPsi(modulesStructure, environmentForJS.getSourceFiles(), libraries, friendLibraries, pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default, (IncrementalDataProvider) configuration.get(JSConfigurationKeys.INCREMENTAL_DATA_PROVIDER), do_nothing, arguments.getWasm());
        }
        PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppress = pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default;
        if (analyzedFirWithPsiOutputCompileModuleToAnalyzedFirWithPsi.reportCompilationErrors(modulesStructure, pendingDiagnosticsCollectorWithSuppress, messageCollector)) {
            throw new CompilationErrorException();
        }
        Fir2IrActualizedResult fir2IrActualizedResultTransformFirToIr = CompilerPipelineKt.transformFirToIr(modulesStructure, analyzedFirWithPsiOutputCompileModuleToAnalyzedFirWithPsi.getOutput(), pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default);
        if (configuration.getBoolean(CommonConfigurationKeys.INCREMENTAL_COMPILATION) && CompilerPipelineKt.shouldGoToNextIcRound(modulesStructure, analyzedFirWithPsiOutputCompileModuleToAnalyzedFirWithPsi.getOutput(), fir2IrActualizedResultTransformFirToIr)) {
            throw new IncrementalNextRoundException();
        }
        if (!arguments.getIrProduceKlibDir() && !arguments.getIrProduceKlibFile()) {
            return modulesStructure;
        }
        CompilerPipelineKt.serializeFirKlib(modulesStructure, analyzedFirWithPsiOutputCompileModuleToAnalyzedFirWithPsi.getOutput(), fir2IrActualizedResultTransformFirToIr, outputKlibPath, messageCollector, pendingDiagnosticsCollectorWithSuppress, arguments.getIrPerModuleOutputName(), arguments.getWasm());
        return modulesStructure;
    }

    /* JADX INFO: compiled from: K2JsIrCompiler.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/K2JsIrCompiler$IcCachesArtifacts;", "", "artifacts", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "cacheGuard", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheGuard;", "(Ljava/util/List;Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheGuard;)V", "getArtifacts", "()Ljava/util/List;", "getCacheGuard", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheGuard;", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IcCachesArtifacts {
        private final List<ModuleArtifact> artifacts;
        private final IncrementalCacheGuard cacheGuard;

        public IcCachesArtifacts(List<ModuleArtifact> artifacts, IncrementalCacheGuard cacheGuard) {
            Intrinsics.checkNotNullParameter(artifacts, "artifacts");
            Intrinsics.checkNotNullParameter(cacheGuard, "cacheGuard");
            this.artifacts = artifacts;
            this.cacheGuard = cacheGuard;
        }

        public final List<ModuleArtifact> getArtifacts() {
            return this.artifacts;
        }

        public final IncrementalCacheGuard getCacheGuard() {
            return this.cacheGuard;
        }
    }

    private final IcCachesArtifacts prepareIcCaches(final K2JSCompilerArguments arguments, MessageCollector messageCollector, File outputDir, List<String> libraries, List<String> friendLibraries, CompilerConfiguration configurationJs, List<String> mainCallArguments) {
        DirtyFileState dirtyFileState;
        Pair pair;
        String strJoinToString$default;
        String cacheDirectory = arguments.getCacheDirectory();
        if (arguments.getWasm() || cacheDirectory == null) {
            return null;
        }
        IncrementalCacheGuard incrementalCacheGuard = new IncrementalCacheGuard(cacheDirectory);
        if (incrementalCacheGuard.acquire() == IncrementalCacheGuard.AcquireStatus.CACHE_CLEARED) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "Cache guard file detected, cache directory '" + cacheDirectory + "' cleared", null, 4, null);
        }
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "", null, 4, null);
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "Building cache:", null, 4, null);
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "to: " + outputDir, null, 4, null);
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "cache directory: " + cacheDirectory, null, 4, null);
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, libraries.toString(), null, 4, null);
        long jCurrentTimeMillis = System.currentTimeMillis();
        String includes = arguments.getIncludes();
        Intrinsics.checkNotNull(includes);
        CacheUpdater cacheUpdater = new CacheUpdater(includes, libraries, friendLibraries, cacheDirectory, configurationJs, new Function0<IrFactory>() { // from class: org.jetbrains.kotlin.cli.js.K2JsIrCompiler$prepareIcCaches$cacheUpdater$1
            @Override // kotlin.jvm.functions.Function0
            public final IrFactory invoke() {
                return new IrFactoryImplForJsIC(new WholeWorldStageController());
            }
        }, mainCallArguments, new JsIrCompilerICInterfaceFactory() { // from class: org.jetbrains.kotlin.cli.js.K2JsIrCompiler$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsIrCompilerICInterfaceFactory
            public final JsIrCompilerICInterface createCompilerForIC(IrModuleFragment irModuleFragment, CompilerConfiguration compilerConfiguration) {
                return K2JsIrCompiler.prepareIcCaches$lambda$3(arguments, irModuleFragment, compilerConfiguration);
            }
        });
        List<ModuleArtifact> listActualizeCaches = cacheUpdater.actualizeCaches();
        incrementalCacheGuard.release();
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "IC rebuilt overall time: " + (System.currentTimeMillis() - jCurrentTimeMillis) + LanguageCodeUtil.MS, null, 4, null);
        for (Pair<String, Long> pair2 : cacheUpdater.getStopwatchLastLaps()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "  " + pair2.component1() + ": " + ((int) (pair2.component2().longValue() / 1000000.0d)) + LanguageCodeUtil.MS, null, 4, null);
        }
        Iterator it = cacheUpdater.getDirtyFileLastStats().entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String strM7157unboximpl = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
            Map map = (Map) entry.getValue();
            EnumSet enumSet = (EnumSet) CollectionsKt.firstOrNull(map.values());
            if (enumSet != null && (dirtyFileState = (DirtyFileState) CollectionsKt.singleOrNull(enumSet)) != null) {
                Collection collectionValues = map.values();
                if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
                    Iterator it2 = collectionValues.iterator();
                    while (it2.hasNext()) {
                        if (CollectionsKt.singleOrNull((EnumSet) it2.next()) != dirtyFileState) {
                            dirtyFileState = null;
                            break;
                        }
                    }
                }
            } else {
                dirtyFileState = null;
                break;
            }
            if (dirtyFileState != DirtyFileState.NON_MODIFIED_IR) {
                if (dirtyFileState == DirtyFileState.REMOVED_FILE) {
                    pair = TuplesKt.to("removed", MapsKt.emptyMap());
                } else if (dirtyFileState != DirtyFileState.ADDED_FILE) {
                    Collection collectionValues2 = map.values();
                    if ((collectionValues2 instanceof Collection) && collectionValues2.isEmpty()) {
                        pair = TuplesKt.to("fully rebuilt", map);
                        break;
                    }
                    Iterator it3 = collectionValues2.iterator();
                    while (true) {
                        if (it3.hasNext()) {
                            if (CollectionsKt.singleOrNull((EnumSet) it3.next()) == DirtyFileState.NON_MODIFIED_IR) {
                                pair = TuplesKt.to("partially rebuilt", map);
                                break;
                            }
                        } else {
                            pair = TuplesKt.to("fully rebuilt", map);
                            break;
                        }
                    }
                } else {
                    pair = TuplesKt.to("built clean", MapsKt.emptyMap());
                }
                String str = (String) pair.component1();
                Map map2 = (Map) pair.component2();
                i++;
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, i + ") module [" + new File(strM7157unboximpl).getName() + "] was " + str, null, 4, null);
                int i2 = 0;
                for (Map.Entry entry2 : map2.entrySet()) {
                    String strM7171unboximpl = ((KotlinSourceFile) entry2.getKey()).m7171unboximpl();
                    EnumSet enumSet2 = (EnumSet) entry2.getValue();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : enumSet2) {
                        if (((DirtyFileState) obj) != DirtyFileState.NON_MODIFIED_IR) {
                            arrayList.add(obj);
                        }
                    }
                    ArrayList arrayList2 = arrayList;
                    if (arrayList2.isEmpty()) {
                        arrayList2 = null;
                    }
                    if (arrayList2 != null && (strJoinToString$default = CollectionsKt.joinToString$default(arrayList2, null, null, null, 0, null, new Function1<DirtyFileState, CharSequence>() { // from class: org.jetbrains.kotlin.cli.js.K2JsIrCompiler$prepareIcCaches$statStr$2
                        @Override // kotlin.jvm.functions.Function1
                        public final CharSequence invoke(DirtyFileState dirtyFileState2) {
                            return dirtyFileState2.getStr();
                        }
                    }, 31, null)) != null) {
                        i2++;
                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, "  " + i + '.' + i2 + ") file [" + new File(strM7171unboximpl).getName() + "]: (" + strJoinToString$default + ')', null, 4, null);
                    }
                }
            }
        }
        return new IcCachesArtifacts(listActualizeCaches, incrementalCacheGuard);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsIrCompilerICInterface prepareIcCaches$lambda$3(K2JSCompilerArguments arguments, IrModuleFragment mainModule, CompilerConfiguration cfg) {
        Intrinsics.checkNotNullParameter(arguments, "$arguments");
        Intrinsics.checkNotNullParameter(mainModule, "mainModule");
        Intrinsics.checkNotNullParameter(cfg, "cfg");
        return new JsIrCompilerWithIC(mainModule, cfg, K2JsIrCompilerKt.getGranularity(arguments), new PhaseConfig(JsLoweringPhasesKt.getJsPhases(), null, null, null, null, null, null, null, null, null, false, false, false, 8190, null), null, 16, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public void setupPlatformSpecificArgumentsAndServices(CompilerConfiguration configuration, K2JSCompilerArguments arguments, Services services) {
        SourceMapSourceEmbedding sourceMapSourceEmbedding;
        SourceMapNamesPolicy sourceMapNamesPolicy;
        List listEmptyList;
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(services, "services");
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        final MessageCollector messageCollector = (MessageCollector) notNull;
        if (arguments.getTarget() != null) {
            Intrinsics.areEqual("v5", arguments.getTarget());
        }
        configuration.put(JSConfigurationKeys.TARGET, EcmaVersion.defaultVersion());
        if (arguments.getSourceMap()) {
            configuration.put(JSConfigurationKeys.SOURCE_MAP, true);
            if (arguments.getSourceMapPrefix() != null) {
                CompilerConfigurationKey<String> compilerConfigurationKey = JSConfigurationKeys.SOURCE_MAP_PREFIX;
                String sourceMapPrefix = arguments.getSourceMapPrefix();
                Intrinsics.checkNotNull(sourceMapPrefix);
                configuration.put(compilerConfigurationKey, sourceMapPrefix);
            }
            String sourceMapBaseDirs = arguments.getSourceMapBaseDirs();
            if (sourceMapBaseDirs == null && StringUtil.isNotEmpty(arguments.getSourceMapPrefix())) {
                sourceMapBaseDirs = K2JSCompiler.calculateSourceMapSourceRoot(messageCollector, arguments);
            }
            if (sourceMapBaseDirs != null) {
                List<String> listSplit = StringUtil.split(sourceMapBaseDirs, File.pathSeparator);
                Intrinsics.checkNotNullExpressionValue(listSplit, "split(...)");
                configuration.put(JSConfigurationKeys.SOURCE_MAP_SOURCE_ROOTS, listSplit);
            }
        } else {
            if (arguments.getSourceMapPrefix() != null) {
                messageCollector.report(CompilerMessageSeverity.WARNING, "source-map-prefix argument has no effect without source map", null);
            }
            if (arguments.getSourceMapBaseDirs() != null) {
                messageCollector.report(CompilerMessageSeverity.WARNING, "source-map-source-root argument has no effect without source map", null);
            }
        }
        if (arguments.getMetaInfo()) {
            configuration.put(JSConfigurationKeys.META_INFO, true);
        }
        configuration.put(JSConfigurationKeys.TYPED_ARRAYS_ENABLED, Boolean.valueOf(arguments.getTypedArrays()));
        configuration.put(JSConfigurationKeys.FRIEND_PATHS_DISABLED, Boolean.valueOf(arguments.getFriendModulesDisabled()));
        configuration.put(JSConfigurationKeys.GENERATE_STRICT_IMPLICIT_EXPORT, Boolean.valueOf(arguments.getStrictImplicitExportType()));
        String friendModules = arguments.getFriendModules();
        if (!arguments.getFriendModulesDisabled() && friendModules != null) {
            String pathSeparator = File.pathSeparator;
            Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
            List<String> listSplit2 = new Regex(pathSeparator).split(friendModules, 0);
            if (!listSplit2.isEmpty()) {
                ListIterator<String> listIterator = listSplit2.listIterator(listSplit2.size());
                while (true) {
                    if (listIterator.hasPrevious()) {
                        if (listIterator.previous().length() != 0) {
                            listEmptyList = CollectionsKt.take(listSplit2, listIterator.nextIndex() + 1);
                            break;
                        }
                    } else {
                        listEmptyList = CollectionsKt.emptyList();
                        break;
                    }
                }
            } else {
                listEmptyList = CollectionsKt.emptyList();
                break;
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj : listEmptyList) {
                if (((String) obj).length() != 0) {
                    arrayList.add(obj);
                }
            }
            configuration.put(JSConfigurationKeys.FRIEND_PATHS, arrayList);
        }
        String moduleKind = arguments.getModuleKind();
        ModuleKind moduleKind2 = moduleKind != null ? moduleKindMap.get(moduleKind) : ModuleKind.PLAIN;
        if (moduleKind2 == null) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "Unknown module kind: " + moduleKind + ". Valid values are: plain, amd, commonjs, umd, es", null);
            moduleKind2 = ModuleKind.PLAIN;
        }
        if (arguments.getWasm()) {
            moduleKind2 = ModuleKind.ES;
        }
        configuration.put(JSConfigurationKeys.MODULE_KIND, moduleKind2);
        configuration.putIfNotNull(JSConfigurationKeys.INCREMENTAL_DATA_PROVIDER, services.get(IncrementalDataProvider.class));
        configuration.putIfNotNull(JSConfigurationKeys.INCREMENTAL_RESULTS_CONSUMER, services.get(IncrementalResultsConsumer.class));
        configuration.putIfNotNull(JSConfigurationKeys.INCREMENTAL_NEXT_ROUND_CHECKER, services.get(IncrementalNextRoundChecker.class));
        configuration.putIfNotNull(CommonConfigurationKeys.LOOKUP_TRACKER, services.get(LookupTracker.class));
        configuration.putIfNotNull(CommonConfigurationKeys.EXPECT_ACTUAL_TRACKER, services.get(ExpectActualTracker.class));
        String errorTolerancePolicy = arguments.getErrorTolerancePolicy();
        ErrorTolerancePolicy errorTolerancePolicyResolvePolicy = errorTolerancePolicy != null ? ErrorTolerancePolicy.INSTANCE.resolvePolicy(errorTolerancePolicy) : null;
        configuration.putIfNotNull(JSConfigurationKeys.ERROR_TOLERANCE_POLICY, errorTolerancePolicyResolvePolicy);
        if (errorTolerancePolicyResolvePolicy != null && errorTolerancePolicyResolvePolicy.getAllowErrors()) {
            configuration.put(JSConfigurationKeys.DEVELOPER_MODE, true);
        }
        String sourceMapEmbedSources = arguments.getSourceMapEmbedSources();
        if (sourceMapEmbedSources != null) {
            sourceMapSourceEmbedding = sourceMapContentEmbeddingMap.get(sourceMapEmbedSources);
        } else {
            sourceMapSourceEmbedding = SourceMapSourceEmbedding.INLINING;
        }
        if (sourceMapSourceEmbedding == null) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Unknown source map source embedding mode: " + sourceMapEmbedSources + ". Valid values are: " + CollectionsKt.joinToString$default(sourceMapContentEmbeddingMap.keySet(), null, null, null, 0, null, null, 63, null), null, 4, null);
            sourceMapSourceEmbedding = SourceMapSourceEmbedding.INLINING;
        }
        configuration.put(JSConfigurationKeys.SOURCE_MAP_EMBED_SOURCES, sourceMapSourceEmbedding);
        if (!arguments.getSourceMap() && sourceMapEmbedSources != null) {
            messageCollector.report(CompilerMessageSeverity.WARNING, "source-map-embed-sources argument has no effect without source map", null);
        }
        String sourceMapNamesPolicy2 = arguments.getSourceMapNamesPolicy();
        if (sourceMapNamesPolicy2 != null) {
            sourceMapNamesPolicy = sourceMapNamesPolicyMap.get(sourceMapNamesPolicy2);
        } else {
            sourceMapNamesPolicy = SourceMapNamesPolicy.SIMPLE_NAMES;
        }
        if (sourceMapNamesPolicy == null) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Unknown source map names policy: " + sourceMapNamesPolicy2 + ". Valid values are: " + CollectionsKt.joinToString$default(sourceMapNamesPolicyMap.keySet(), null, null, null, 0, null, null, 63, null), null, 4, null);
            sourceMapNamesPolicy = SourceMapNamesPolicy.SIMPLE_NAMES;
        }
        configuration.put(JSConfigurationKeys.SOURCEMAP_NAMES_POLICY, sourceMapNamesPolicy);
        configuration.put(JSConfigurationKeys.PRINT_REACHABILITY_INFO, Boolean.valueOf(arguments.getIrDcePrintReachabilityInfo()));
        configuration.put(JSConfigurationKeys.FAKE_OVERRIDE_VALIDATOR, Boolean.valueOf(arguments.getFakeOverrideValidator()));
        configuration.putIfNotNull(JSConfigurationKeys.DUMP_REACHABILITY_INFO_TO_FILE, arguments.getIrDceDumpReachabilityInfoToFile());
        PartialLinkageConfigKt.setupPartialLinkageConfig(configuration, arguments.getPartialLinkageMode(), arguments.getPartialLinkageLogLevel(), (arguments.getWasm() || arguments.getIncludes() == null) ? false : true, new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.cli.js.K2JsIrCompiler.setupPlatformSpecificArgumentsAndServices.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.WARNING, it, null, 4, null);
            }
        }, new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.cli.js.K2JsIrCompiler.setupPlatformSpecificArgumentsAndServices.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, it, null, 4, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public String executableScriptFileName() {
        throw new NotImplementedError("An operation is not implemented: Provide a proper way to run the compiler with IR BE");
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    protected BinaryVersion createMetadataVersion(int[] versionArray) {
        Intrinsics.checkNotNullParameter(versionArray, "versionArray");
        return new KlibMetadataVersion(Arrays.copyOf(versionArray, versionArray.length));
    }

    /* JADX INFO: compiled from: K2JsIrCompiler.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0005H\u0002J\u001b\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011H\u0007¢\u0006\u0002\u0010\u0012J\u001e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\fH\u0002J\u0012\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\f*\u00020\u0005H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/K2JsIrCompiler$Companion;", "", "()V", "moduleKindMap", "", "", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "sourceMapContentEmbeddingMap", "Lorg/jetbrains/kotlin/js/config/SourceMapSourceEmbedding;", "sourceMapNamesPolicyMap", "Lorg/jetbrains/kotlin/js/config/SourceMapNamesPolicy;", "configureLibraries", "", "libraryString", "main", "", "args", "", "([Ljava/lang/String;)V", "reportCompiledSourcesList", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "sourceFiles", "Lorg/jetbrains/kotlin/psi/KtFile;", "splitByPathSeparator", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void main(String[] args) {
            Intrinsics.checkNotNullParameter(args, "args");
            CLITool.INSTANCE.doMain(new K2JsIrCompiler(), args);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void reportCompiledSourcesList(MessageCollector messageCollector, List<? extends KtFile> sourceFiles) {
            String strVirtualFileToPath;
            List<? extends KtFile> list = sourceFiles;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (KtFile ktFile : list) {
                VirtualFile virtualFile = ktFile.getVirtualFile();
                if (virtualFile != null) {
                    strVirtualFileToPath = MessageUtil.virtualFileToPath(virtualFile);
                } else {
                    strVirtualFileToPath = ktFile.get$name() + " (no virtual file)";
                }
                arrayList.add(strVirtualFileToPath);
            }
            messageCollector.report(CompilerMessageSeverity.LOGGING, "Compiling source files: " + org.jetbrains.kotlin.utils.StringsKt.join(arrayList, ", "), null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<String> configureLibraries(String libraryString) {
            List<String> listSplitByPathSeparator;
            return (libraryString == null || (listSplitByPathSeparator = splitByPathSeparator(libraryString)) == null) ? CollectionsKt.emptyList() : listSplitByPathSeparator;
        }

        private final List<String> splitByPathSeparator(String str) {
            List listEmptyList;
            String pathSeparator = File.pathSeparator;
            Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
            Regex regex = new Regex(pathSeparator);
            List<String> listSplit = regex.split(str, 0);
            if (!listSplit.isEmpty()) {
                ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
                while (true) {
                    if (listIterator.hasPrevious()) {
                        if (listIterator.previous().length() != 0) {
                            listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                            break;
                        }
                    } else {
                        listEmptyList = CollectionsKt.emptyList();
                        break;
                    }
                }
            } else {
                listEmptyList = CollectionsKt.emptyList();
                break;
            }
            Object[] array = listEmptyList.toArray(new String[0]);
            ArrayList arrayList = new ArrayList();
            for (Object obj : array) {
                if (((String) obj).length() != 0) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }
    }
}
