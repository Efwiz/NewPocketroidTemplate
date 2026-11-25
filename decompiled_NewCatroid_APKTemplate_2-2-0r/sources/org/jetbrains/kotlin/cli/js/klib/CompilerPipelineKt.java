package org.jetbrains.kotlin.cli.js.klib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt;
import org.jetbrains.kotlin.cli.common.GroupedKtSources;
import org.jetbrains.kotlin.cli.common.SessionWithSources;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.diagnostics.impl.PendingDiagnosticsCollectorWithSuppress;
import org.jetbrains.kotlin.fir.BinaryModuleData;
import org.jetbrains.kotlin.fir.DependencyListForCliModule;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.DescriptorSignatureComposerStub;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrExtensions;
import org.jetbrains.kotlin.fir.backend.Fir2IrResult;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.fir.backend.js.FirJsKotlinMangler;
import org.jetbrains.kotlin.fir.descriptors.FirModuleDescriptor;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.pipeline.ConvertToIrKt;
import org.jetbrains.kotlin.fir.pipeline.Fir2IrActualizedResult;
import org.jetbrains.kotlin.fir.pipeline.FirResult;
import org.jetbrains.kotlin.fir.pipeline.FirUtilsKt;
import org.jetbrains.kotlin.fir.pipeline.ModuleCompilerAnalyzedOutput;
import org.jetbrains.kotlin.fir.session.KlibIcData;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.incremental.js.IncrementalNextRoundChecker;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.KlibKt;
import org.jetbrains.kotlin.ir.backend.js.KotlinFileSerializedData;
import org.jetbrains.kotlin.ir.backend.js.ModulesStructure;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsManglerDesc;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsManglerIr;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextImpl;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.WasmTarget;
import org.jetbrains.kotlin.js.resolve.JsPlatformAnalyzerServices;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.impl.BuiltInsPlatform;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.js.JsPlatforms;
import org.jetbrains.kotlin.platform.wasm.WasmPlatforms;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.wasm.resolve.WasmPlatformAnalyzerServices;
import org.jetbrains.kotlin.wasm.resolve.WasmWasiPlatformAnalyzerServices;

/* JADX INFO: compiled from: compilerPipeline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a¸\u0001\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0014\b\b\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u00100\u000f2\u001a\b\b\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00100\u00122\u001e\u0010\u0013\u001a\u001a\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0004\u0012\u00020\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0010H\u0086\bø\u0001\u0000\u001a\\\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0015\u001a\u00020\u0010\u001ad\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0015\u001a\u00020\u0010\u001aN\u0010\"\u001a\u00020#2\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\b2\u0006\u0010(\u001a\u00020)2\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010*\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0015\u001a\u00020\u0010\u001a$\u0010+\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010%\u001a\u00020&\u001a$\u0010,\u001a\u00020&2\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u001a\u001a\u00020-\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006."}, d2 = {"compileModuleToAnalyzedFir", "", "Lorg/jetbrains/kotlin/fir/pipeline/ModuleCompilerAnalyzedOutput;", "F", "moduleStructure", "Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;", "files", "libraries", "", "friendLibraries", "incrementalDataProvider", "Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "isCommonSource", "Lkotlin/Function1;", "", "fileBelongsToModule", "Lkotlin/Function2;", "buildResolveAndCheckFir", "Lorg/jetbrains/kotlin/fir/FirSession;", "useWasmPlatform", "compileModuleToAnalyzedFirWithPsi", "Lorg/jetbrains/kotlin/cli/js/klib/AnalyzedFirWithPsiOutput;", "ktFiles", "Lorg/jetbrains/kotlin/psi/KtFile;", "diagnosticsReporter", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "compileModulesToAnalyzedFirWithLightTree", "Lorg/jetbrains/kotlin/cli/js/klib/AnalyzedFirOutput;", "groupedSources", "Lorg/jetbrains/kotlin/cli/common/GroupedKtSources;", "ktSourceFiles", "Lorg/jetbrains/kotlin/KtSourceFile;", "serializeFirKlib", "", "firOutputs", "fir2IrActualizedResult", "Lorg/jetbrains/kotlin/fir/pipeline/Fir2IrActualizedResult;", "outputKlibPath", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", KlibKt.KLIB_PROPERTY_JS_OUTPUT_NAME, "shouldGoToNextIcRound", "transformFirToIr", "Lorg/jetbrains/kotlin/diagnostics/impl/PendingDiagnosticsCollectorWithSuppress;", "cli-js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilerPipelineKt {

    /* JADX INFO: compiled from: compilerPipeline.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 176)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WasmTarget.values().length];
            try {
                iArr[WasmTarget.JS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WasmTarget.WASI.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final <F> List<ModuleCompilerAnalyzedOutput> compileModuleToAnalyzedFir(ModulesStructure moduleStructure, List<? extends F> files, List<String> libraries, List<String> friendLibraries, IncrementalDataProvider incrementalDataProvider, LookupTracker lookupTracker, Function1<? super F, Boolean> isCommonSource, Function2<? super F, ? super String, Boolean> fileBelongsToModule, Function2<? super FirSession, ? super List<? extends F>, ModuleCompilerAnalyzedOutput> buildResolveAndCheckFir, boolean z) {
        JsPlatformAnalyzerServices jsPlatformAnalyzerServices;
        List listPrepareJsSessions;
        Intrinsics.checkNotNullParameter(moduleStructure, "moduleStructure");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(friendLibraries, "friendLibraries");
        Intrinsics.checkNotNullParameter(isCommonSource, "isCommonSource");
        Intrinsics.checkNotNullParameter(fileBelongsToModule, "fileBelongsToModule");
        Intrinsics.checkNotNullParameter(buildResolveAndCheckFir, "buildResolveAndCheckFir");
        List<FirExtensionRegistrar> instances = FirExtensionRegistrar.INSTANCE.getInstances(moduleStructure.getProject());
        Object obj = moduleStructure.getCompilerConfiguration().get(CommonConfigurationKeys.MODULE_NAME);
        Intrinsics.checkNotNull(obj);
        Name nameSpecial = Name.special("<" + ((String) obj) + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        WasmPlatforms.Default defaultJsPlatform = z ? WasmPlatforms.Default.INSTANCE : JsPlatforms.INSTANCE.getDefaultJsPlatform();
        if (z) {
            int i = WhenMappings.$EnumSwitchMapping$0[((WasmTarget) moduleStructure.getCompilerConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS)).ordinal()];
            if (i == 1) {
                jsPlatformAnalyzerServices = WasmPlatformAnalyzerServices.INSTANCE;
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                jsPlatformAnalyzerServices = WasmWasiPlatformAnalyzerServices.INSTANCE;
            }
        } else {
            jsPlatformAnalyzerServices = JsPlatformAnalyzerServices.INSTANCE;
        }
        BinaryModuleData binaryModuleDataInitialize = BinaryModuleData.INSTANCE.initialize(nameSpecial, defaultJsPlatform, jsPlatformAnalyzerServices);
        DependencyListForCliModule.Companion companion = DependencyListForCliModule.INSTANCE;
        DependencyListForCliModule.Builder builder = new DependencyListForCliModule.Builder(binaryModuleDataInitialize);
        List<String> list = libraries;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Paths.get((String) it.next(), new String[0]).toAbsolutePath());
        }
        builder.dependencies(arrayList);
        List<String> list2 = friendLibraries;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(Paths.get((String) it2.next(), new String[0]).toAbsolutePath());
        }
        builder.friendDependencies(arrayList2);
        DependencyListForCliModule dependencyListForCliModuleBuild = builder.build();
        List<KotlinLibrary> allDependencies = moduleStructure.getAllDependencies();
        if (z) {
            listPrepareJsSessions = FirSessionConstructionUtilsKt.prepareWasmSessions(files, moduleStructure.getCompilerConfiguration(), nameSpecial, allDependencies, dependencyListForCliModuleBuild, instances, isCommonSource, fileBelongsToModule, lookupTracker, incrementalDataProvider != null ? new KlibIcData(incrementalDataProvider) : null);
        } else {
            listPrepareJsSessions = FirSessionConstructionUtilsKt.prepareJsSessions(files, moduleStructure.getCompilerConfiguration(), nameSpecial, allDependencies, dependencyListForCliModuleBuild, instances, isCommonSource, fileBelongsToModule, lookupTracker, incrementalDataProvider != null ? new KlibIcData(incrementalDataProvider) : null);
        }
        List<SessionWithSources> list3 = listPrepareJsSessions;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        for (SessionWithSources sessionWithSources : list3) {
            arrayList3.add(buildResolveAndCheckFir.invoke(sessionWithSources.getSession(), sessionWithSources.getFiles()));
        }
        return arrayList3;
    }

    public static final AnalyzedFirWithPsiOutput compileModuleToAnalyzedFirWithPsi(ModulesStructure moduleStructure, List<? extends KtFile> ktFiles, List<String> libraries, List<String> friendLibraries, BaseDiagnosticsCollector diagnosticsReporter, IncrementalDataProvider incrementalDataProvider, LookupTracker lookupTracker, boolean z) {
        JsPlatformAnalyzerServices jsPlatformAnalyzerServices;
        Intrinsics.checkNotNullParameter(moduleStructure, "moduleStructure");
        Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(friendLibraries, "friendLibraries");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        Function1<KtFile, Boolean> function1IsCommonSourceForPsi = FirSessionConstructionUtilsKt.isCommonSourceForPsi();
        Function2<KtFile, String, Boolean> fileBelongsToModuleForPsi = FirSessionConstructionUtilsKt.getFileBelongsToModuleForPsi();
        List<FirExtensionRegistrar> instances = FirExtensionRegistrar.INSTANCE.getInstances(moduleStructure.getProject());
        Object obj = moduleStructure.getCompilerConfiguration().get(CommonConfigurationKeys.MODULE_NAME);
        Intrinsics.checkNotNull(obj);
        Name nameSpecial = Name.special("<" + ((String) obj) + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        WasmPlatforms.Default defaultJsPlatform = z ? WasmPlatforms.Default.INSTANCE : JsPlatforms.INSTANCE.getDefaultJsPlatform();
        if (z) {
            int i = WhenMappings.$EnumSwitchMapping$0[((WasmTarget) moduleStructure.getCompilerConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS)).ordinal()];
            if (i == 1) {
                jsPlatformAnalyzerServices = WasmPlatformAnalyzerServices.INSTANCE;
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                jsPlatformAnalyzerServices = WasmWasiPlatformAnalyzerServices.INSTANCE;
            }
        } else {
            jsPlatformAnalyzerServices = JsPlatformAnalyzerServices.INSTANCE;
        }
        BinaryModuleData binaryModuleDataInitialize = BinaryModuleData.INSTANCE.initialize(nameSpecial, defaultJsPlatform, jsPlatformAnalyzerServices);
        DependencyListForCliModule.Companion companion = DependencyListForCliModule.INSTANCE;
        DependencyListForCliModule.Builder builder = new DependencyListForCliModule.Builder(binaryModuleDataInitialize);
        List<String> list = libraries;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Paths.get((String) it.next(), new String[0]).toAbsolutePath());
        }
        builder.dependencies(arrayList);
        List<String> list2 = friendLibraries;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(Paths.get((String) it2.next(), new String[0]).toAbsolutePath());
        }
        builder.friendDependencies(arrayList2);
        DependencyListForCliModule dependencyListForCliModuleBuild = builder.build();
        List<KotlinLibrary> allDependencies = moduleStructure.getAllDependencies();
        List<SessionWithSources> listPrepareWasmSessions = z ? FirSessionConstructionUtilsKt.prepareWasmSessions(ktFiles, moduleStructure.getCompilerConfiguration(), nameSpecial, allDependencies, dependencyListForCliModuleBuild, instances, function1IsCommonSourceForPsi, fileBelongsToModuleForPsi, lookupTracker, incrementalDataProvider != null ? new KlibIcData(incrementalDataProvider) : null) : FirSessionConstructionUtilsKt.prepareJsSessions(ktFiles, moduleStructure.getCompilerConfiguration(), nameSpecial, allDependencies, dependencyListForCliModuleBuild, instances, function1IsCommonSourceForPsi, fileBelongsToModuleForPsi, lookupTracker, incrementalDataProvider != null ? new KlibIcData(incrementalDataProvider) : null);
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPrepareWasmSessions, 10));
        for (SessionWithSources sessionWithSources : listPrepareWasmSessions) {
            arrayList3.add(FirUtilsKt.buildResolveAndCheckFirFromKtFiles(sessionWithSources.getSession(), sessionWithSources.getFiles(), diagnosticsReporter));
        }
        return new AnalyzedFirWithPsiOutput(arrayList3, ktFiles);
    }

    public static final AnalyzedFirOutput compileModulesToAnalyzedFirWithLightTree(ModulesStructure moduleStructure, final GroupedKtSources groupedSources, List<? extends KtSourceFile> ktSourceFiles, List<String> libraries, List<String> friendLibraries, BaseDiagnosticsCollector diagnosticsReporter, IncrementalDataProvider incrementalDataProvider, LookupTracker lookupTracker, boolean z) {
        JsPlatformAnalyzerServices jsPlatformAnalyzerServices;
        Intrinsics.checkNotNullParameter(moduleStructure, "moduleStructure");
        Intrinsics.checkNotNullParameter(groupedSources, "groupedSources");
        Intrinsics.checkNotNullParameter(ktSourceFiles, "ktSourceFiles");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(friendLibraries, "friendLibraries");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        Function1<KtSourceFile, Boolean> function1 = new Function1<KtSourceFile, Boolean>() { // from class: org.jetbrains.kotlin.cli.js.klib.CompilerPipelineKt$compileModulesToAnalyzedFirWithLightTree$output$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtSourceFile it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FirSessionConstructionUtilsKt.isCommonSourceForLt(groupedSources).invoke(it);
            }
        };
        Function2<KtSourceFile, String, Boolean> function2 = new Function2<KtSourceFile, String, Boolean>() { // from class: org.jetbrains.kotlin.cli.js.klib.CompilerPipelineKt$compileModulesToAnalyzedFirWithLightTree$output$2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(KtSourceFile file, String it) {
                Intrinsics.checkNotNullParameter(file, "file");
                Intrinsics.checkNotNullParameter(it, "it");
                return FirSessionConstructionUtilsKt.getFileBelongsToModuleForLt(groupedSources).invoke(file, it);
            }
        };
        List<FirExtensionRegistrar> instances = FirExtensionRegistrar.INSTANCE.getInstances(moduleStructure.getProject());
        Object obj = moduleStructure.getCompilerConfiguration().get(CommonConfigurationKeys.MODULE_NAME);
        Intrinsics.checkNotNull(obj);
        Name nameSpecial = Name.special("<" + ((String) obj) + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        WasmPlatforms.Default defaultJsPlatform = z ? WasmPlatforms.Default.INSTANCE : JsPlatforms.INSTANCE.getDefaultJsPlatform();
        if (z) {
            int i = WhenMappings.$EnumSwitchMapping$0[((WasmTarget) moduleStructure.getCompilerConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS)).ordinal()];
            if (i == 1) {
                jsPlatformAnalyzerServices = WasmPlatformAnalyzerServices.INSTANCE;
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                jsPlatformAnalyzerServices = WasmWasiPlatformAnalyzerServices.INSTANCE;
            }
        } else {
            jsPlatformAnalyzerServices = JsPlatformAnalyzerServices.INSTANCE;
        }
        BinaryModuleData binaryModuleDataInitialize = BinaryModuleData.INSTANCE.initialize(nameSpecial, defaultJsPlatform, jsPlatformAnalyzerServices);
        DependencyListForCliModule.Companion companion = DependencyListForCliModule.INSTANCE;
        DependencyListForCliModule.Builder builder = new DependencyListForCliModule.Builder(binaryModuleDataInitialize);
        List<String> list = libraries;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Paths.get((String) it.next(), new String[0]).toAbsolutePath());
        }
        builder.dependencies(arrayList);
        List<String> list2 = friendLibraries;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(Paths.get((String) it2.next(), new String[0]).toAbsolutePath());
        }
        builder.friendDependencies(arrayList2);
        DependencyListForCliModule dependencyListForCliModuleBuild = builder.build();
        List<KotlinLibrary> allDependencies = moduleStructure.getAllDependencies();
        List<SessionWithSources> listPrepareWasmSessions = z ? FirSessionConstructionUtilsKt.prepareWasmSessions(ktSourceFiles, moduleStructure.getCompilerConfiguration(), nameSpecial, allDependencies, dependencyListForCliModuleBuild, instances, function1, function2, lookupTracker, incrementalDataProvider != null ? new KlibIcData(incrementalDataProvider) : null) : FirSessionConstructionUtilsKt.prepareJsSessions(ktSourceFiles, moduleStructure.getCompilerConfiguration(), nameSpecial, allDependencies, dependencyListForCliModuleBuild, instances, function1, function2, lookupTracker, incrementalDataProvider != null ? new KlibIcData(incrementalDataProvider) : null);
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPrepareWasmSessions, 10));
        for (SessionWithSources sessionWithSources : listPrepareWasmSessions) {
            arrayList3.add(FirUtilsKt.buildResolveAndCheckFirViaLightTree(sessionWithSources.getSession(), sessionWithSources.getFiles(), diagnosticsReporter, null));
        }
        return new AnalyzedFirOutput(arrayList3);
    }

    public static final Fir2IrActualizedResult transformFirToIr(ModulesStructure moduleStructure, List<ModuleCompilerAnalyzedOutput> firOutputs, PendingDiagnosticsCollectorWithSuppress diagnosticsReporter) {
        Intrinsics.checkNotNullParameter(moduleStructure, "moduleStructure");
        Intrinsics.checkNotNullParameter(firOutputs, "firOutputs");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        Fir2IrExtensions.Default r2 = Fir2IrExtensions.Default.INSTANCE;
        ArrayList arrayList = new ArrayList();
        List<KotlinLibrary> allDependencies = moduleStructure.getAllDependencies();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(allDependencies, 10));
        DefaultBuiltIns companion = null;
        for (KotlinLibrary kotlinLibrary : allDependencies) {
            ModuleDescriptorImpl moduleDescriptorImplCreateDescriptorOptionalBuiltIns = KlibKt.getJsFactories().getDefaultDeserializedDescriptorFactory().createDescriptorOptionalBuiltIns(kotlinLibrary, CommonConfigurationKeysKt.getLanguageVersionSettings(moduleStructure.getCompilerConfiguration()), new LockBasedStorageManager("ModulesStructure"), companion, null, LookupTracker.DO_NOTHING.INSTANCE);
            ArrayList arrayList3 = arrayList;
            arrayList3.add(moduleDescriptorImplCreateDescriptorOptionalBuiltIns);
            moduleDescriptorImplCreateDescriptorOptionalBuiltIns.setDependencies(new ArrayList(arrayList3));
            if (KotlinLibraryKt.getUnresolvedDependencies(kotlinLibrary).isEmpty()) {
                companion = moduleDescriptorImplCreateDescriptorOptionalBuiltIns.getBuiltIns();
            }
            arrayList2.add(moduleDescriptorImplCreateDescriptorOptionalBuiltIns);
        }
        final ArrayList arrayList4 = arrayList2;
        FirResult firResult = new FirResult(firOutputs);
        Fir2IrExtensions.Default r9 = r2;
        Object objPutIfAbsent = moduleStructure.getCompilerConfiguration().putIfAbsent(CommonConfigurationKeys.EVALUATED_CONST_TRACKER, EvaluatedConstTracker.INSTANCE.create());
        Intrinsics.checkNotNullExpressionValue(objPutIfAbsent, "putIfAbsent(...)");
        Fir2IrConfiguration fir2IrConfiguration = new Fir2IrConfiguration(CommonConfigurationKeysKt.getLanguageVersionSettings(moduleStructure.getCompilerConfiguration()), diagnosticsReporter, false, (EvaluatedConstTracker) objPutIfAbsent, null, (ExpectActualTracker) moduleStructure.getCompilerConfiguration().get(CommonConfigurationKeys.EXPECT_ACTUAL_TRACKER), false);
        List<IrGenerationExtension> instances = IrGenerationExtension.INSTANCE.getInstances(moduleStructure.getProject());
        DescriptorSignatureComposerStub descriptorSignatureComposerStub = new DescriptorSignatureComposerStub(JsManglerDesc.INSTANCE);
        JsManglerIr jsManglerIr = JsManglerIr.INSTANCE;
        FirJsKotlinMangler firJsKotlinMangler = new FirJsKotlinMangler();
        Fir2IrVisibilityConverter.Default r15 = Fir2IrVisibilityConverter.Default.INSTANCE;
        if (companion == null) {
            companion = DefaultBuiltIns.INSTANCE.getInstance();
        }
        return ConvertToIrKt.convertToIrAndActualize(firResult, r9, fir2IrConfiguration, instances, descriptorSignatureComposerStub, jsManglerIr, firJsKotlinMangler, r15, companion, C43571.INSTANCE, new Function1<Fir2IrResult, Unit>() { // from class: org.jetbrains.kotlin.cli.js.klib.CompilerPipelineKt.transformFirToIr.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Fir2IrResult fir2IrResult) {
                invoke2(fir2IrResult);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Fir2IrResult convertToIrAndActualize) {
                Intrinsics.checkNotNullParameter(convertToIrAndActualize, "$this$convertToIrAndActualize");
                ModuleDescriptor descriptor = convertToIrAndActualize.getIrModuleFragment().getDescriptor();
                FirModuleDescriptor firModuleDescriptor = descriptor instanceof FirModuleDescriptor ? (FirModuleDescriptor) descriptor : null;
                if (firModuleDescriptor != null) {
                    firModuleDescriptor.setAllDependencyModules(arrayList4);
                }
            }
        });
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.js.klib.CompilerPipelineKt$transformFirToIr$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: compilerPipeline.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C43571 extends FunctionReference implements Function1<IrBuiltIns, IrTypeSystemContextImpl> {
        public static final C43571 INSTANCE = new C43571();

        C43571() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IrTypeSystemContextImpl.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final IrTypeSystemContextImpl invoke(IrBuiltIns p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return new IrTypeSystemContextImpl(p0);
        }
    }

    public static final void serializeFirKlib(ModulesStructure moduleStructure, List<ModuleCompilerAnalyzedOutput> firOutputs, Fir2IrActualizedResult fir2IrActualizedResult, String outputKlibPath, MessageCollector messageCollector, BaseDiagnosticsCollector diagnosticsReporter, String str, boolean z) throws IOException {
        Intrinsics.checkNotNullParameter(moduleStructure, "moduleStructure");
        Intrinsics.checkNotNullParameter(firOutputs, "firOutputs");
        Intrinsics.checkNotNullParameter(fir2IrActualizedResult, "fir2IrActualizedResult");
        Intrinsics.checkNotNullParameter(outputKlibPath, "outputKlibPath");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        Fir2KlibSerializer fir2KlibSerializer = new Fir2KlibSerializer(moduleStructure, firOutputs, fir2IrActualizedResult);
        IncrementalDataProvider incrementalDataProvider = KlibKt.getIncrementalDataProvider(moduleStructure.getCompilerConfiguration());
        List<KotlinFileSerializedData> serializedData = incrementalDataProvider != null ? KlibKt.getSerializedData(incrementalDataProvider, fir2KlibSerializer.getSourceFiles()) : null;
        Object obj = moduleStructure.getCompilerConfiguration().get(CommonConfigurationKeys.MODULE_NAME);
        Intrinsics.checkNotNull(obj);
        String str2 = (String) obj;
        CompilerConfiguration compilerConfiguration = moduleStructure.getCompilerConfiguration();
        IrMessageLogger.None none = (IrMessageLogger) moduleStructure.getCompilerConfiguration().get(IrMessageLogger.INSTANCE.getIR_MESSAGE_LOGGER());
        if (none == null) {
            none = IrMessageLogger.None.INSTANCE;
        }
        List<KtSourceFile> sourceFiles = fir2KlibSerializer.getSourceFiles();
        List<KotlinLibrary> allDependencies = moduleStructure.getAllDependencies();
        IrModuleFragment irModuleFragment = fir2IrActualizedResult.getIrModuleFragment();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (serializedData == null) {
            serializedData = CollectionsKt.emptyList();
        }
        List<KotlinFileSerializedData> list = serializedData;
        boolean z2 = messageCollector.getHasErrors() || diagnosticsReporter.getHasErrors();
        KotlinAbiVersion current = KotlinAbiVersion.INSTANCE.getCURRENT();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(fir2KlibSerializer);
        BuiltInsPlatform builtInsPlatform = z ? BuiltInsPlatform.WASM : BuiltInsPlatform.JS;
        Intrinsics.checkNotNull(none);
        KlibKt.serializeModuleIntoKlib(str2, compilerConfiguration, none, sourceFiles, outputKlibPath, allDependencies, irModuleFragment, linkedHashMap, list, true, false, z2, current, str, builtInsPlatform, anonymousClass1);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.js.klib.CompilerPipelineKt$serializeFirKlib$1, reason: invalid class name */
    /* JADX INFO: compiled from: compilerPipeline.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<KtSourceFile, ProtoBuf.PackageFragment> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "serializeSingleFirFile(Lorg/jetbrains/kotlin/KtSourceFile;)Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "serializeSingleFirFile";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Fir2KlibSerializer.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final ProtoBuf.PackageFragment invoke(KtSourceFile p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return ((Fir2KlibSerializer) this.receiver).serializeSingleFirFile(p0);
        }
    }

    public static final boolean shouldGoToNextIcRound(ModulesStructure moduleStructure, List<ModuleCompilerAnalyzedOutput> firOutputs, Fir2IrActualizedResult fir2IrActualizedResult) {
        Intrinsics.checkNotNullParameter(moduleStructure, "moduleStructure");
        Intrinsics.checkNotNullParameter(firOutputs, "firOutputs");
        Intrinsics.checkNotNullParameter(fir2IrActualizedResult, "fir2IrActualizedResult");
        IncrementalNextRoundChecker incrementalNextRoundChecker = (IncrementalNextRoundChecker) moduleStructure.getCompilerConfiguration().get(JSConfigurationKeys.INCREMENTAL_NEXT_ROUND_CHECKER);
        if (incrementalNextRoundChecker == null) {
            return false;
        }
        Fir2KlibSerializer fir2KlibSerializer = new Fir2KlibSerializer(moduleStructure, firOutputs, fir2IrActualizedResult);
        for (KtSourceFile ktSourceFile : fir2KlibSerializer.getSourceFiles()) {
            ProtoBuf.PackageFragment packageFragmentSerializeSingleFirFile = fir2KlibSerializer.serializeSingleFirFile(ktSourceFile);
            String path = ktSourceFile.getPath();
            Intrinsics.checkNotNull(path);
            File file = new File(path);
            byte[] byteArray = packageFragmentSerializeSingleFirFile.toByteArray();
            Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
            incrementalNextRoundChecker.checkProtoChanges(file, byteArray);
        }
        return incrementalNextRoundChecker.shouldGoToNextRound();
    }
}
