package org.jetbrains.kotlin.cli.jvm.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import org.bouncycastle.pqc.crypto.newhope.NewHope;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporter;
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.config.ClassicFrontendSpecificJvmConfigurationKeys;
import org.jetbrains.kotlin.cli.jvm.config.JavaSourceRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.codegen.ClassBuilderFactories;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.CodegenFactory;
import org.jetbrains.kotlin.codegen.DefaultCodegenFactory;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporterFactory;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.ir.backend.jvm.JvmLibraryResolverKt;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult;
import org.jetbrains.kotlin.load.java.JavaClassesTracker;
import org.jetbrains.kotlin.load.kotlin.ModuleVisibilityManager;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.modules.Module;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade;

/* JADX INFO: compiled from: KotlinToJVMBytecodeCompiler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001)B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006J7\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\nH\u0000¢\u0006\u0002\b\u0012J$\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u00142\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0004H\u0002J\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J8\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002JP\u0010$\u001a\u00020\u001b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0017\u001a\u00020\u00042\f\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u000f2\b\u0010'\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010(\u001a\u00020\u00162\u0006\u0010 \u001a\u00020!H\u0002¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinToJVMBytecodeCompiler;", "", "()V", "analyze", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "analyzeAndGenerate", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "compileBunchOfSources", "", "compileModules", "buildFile", "Ljava/io/File;", "chunk", "", "Lorg/jetbrains/kotlin/modules/Module;", "repeat", "compileModules$cli", "convertToIr", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/codegen/CodegenFactory;", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$BackendInput;", "result", "repeatAnalysisIfNeeded", "runCodegen", "codegenInput", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$CodegenInput;", "state", "codegenFactory", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "diagnosticsReporter", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "runLowerings", "sourceFiles", "Lorg/jetbrains/kotlin/psi/KtFile;", "module", "backendInput", "DirectoriesScope", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinToJVMBytecodeCompiler {
    public static final KotlinToJVMBytecodeCompiler INSTANCE = new KotlinToJVMBytecodeCompiler();

    private KotlinToJVMBytecodeCompiler() {
    }

    public static /* synthetic */ boolean compileModules$cli$default(KotlinToJVMBytecodeCompiler kotlinToJVMBytecodeCompiler, KotlinCoreEnvironment kotlinCoreEnvironment, File file, List list, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return kotlinToJVMBytecodeCompiler.compileModules$cli(kotlinCoreEnvironment, file, list, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean compileModules$cli(final KotlinCoreEnvironment environment, File buildFile, List<? extends Module> chunk, boolean repeat) {
        Intrinsics.checkNotNullParameter(environment, "environment");
        Intrinsics.checkNotNullParameter(chunk, "chunk");
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        Integer num = (Integer) environment.getConfiguration().get(CLIConfigurationKeys.REPEAT_COMPILE_MODULES);
        int i = 1;
        if (num != null && !repeat) {
            CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) environment.getConfiguration().get(CLIConfigurationKeys.PERF_MANAGER);
            IntRange intRangeUntil = RangesKt.until(0, num.intValue());
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
            Iterator<Integer> it = intRangeUntil.iterator();
            while (it.hasNext()) {
                int iNextInt = ((IntIterator) it).nextInt();
                boolean zCompileModules$cli = INSTANCE.compileModules$cli(environment, buildFile, chunk, true);
                if (commonCompilerPerformanceManager != null) {
                    commonCompilerPerformanceManager.notifyRepeat(num.intValue(), iNextInt);
                }
                arrayList.add(Boolean.valueOf(zCompileModules$cli));
            }
            return ((Boolean) CollectionsKt.last((List) arrayList)).booleanValue();
        }
        ModuleVisibilityManager service = ModuleVisibilityManager.SERVICE.getInstance(environment.getProject());
        Iterator<? extends Module> it2 = chunk.iterator();
        while (it2.hasNext()) {
            service.addModule(it2.next());
        }
        List<String> list = environment.getConfiguration().getList(JVMConfigurationKeys.FRIEND_PATHS);
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        for (String str : list) {
            Intrinsics.checkNotNull(str);
            service.addFriendPath(str);
        }
        CompilerConfiguration configuration = environment.getConfiguration();
        if (configuration.getBoolean(CommonConfigurationKeys.USE_FIR)) {
            Project project = environment.getProject();
            VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
            Intrinsics.checkNotNullExpressionValue(fileSystem, "getFileSystem(...)");
            return FirKotlinToJvmBytecodeCompiler.INSTANCE.compileModulesUsingFrontendIR(new VfsBasedProjectEnvironment(project, fileSystem, new Function1<GlobalSearchScope, PackagePartProvider>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler$compileModules$projectEnvironment$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final PackagePartProvider invoke(GlobalSearchScope it3) {
                    Intrinsics.checkNotNullParameter(it3, "it");
                    return environment.createPackagePartProvider(it3);
                }
            }), environment.getConfiguration(), CoreEnvironmentUtilsKt.getMessageCollector(environment), environment.getSourceFiles(), buildFile, chunk);
        }
        AnalysisResult analysisResultRepeatAnalysisIfNeeded = repeatAnalysisIfNeeded(analyze(environment), environment);
        if (analysisResultRepeatAnalysisIfNeeded == null || !analysisResultRepeatAnalysisIfNeeded.getShouldGenerateCode()) {
            return false;
        }
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        analysisResultRepeatAnalysisIfNeeded.throwIfError();
        MessageCollector messageCollector = null;
        FqName fqNameFindMainClass = (chunk.size() != 1 || configuration.get(JVMConfigurationKeys.OUTPUT_JAR) == null) ? null : FindMainClassKt.findMainClass(analysisResultRepeatAnalysisIfNeeded.getBindingContext(), CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), environment.getSourceFiles());
        VirtualFileSystem fileSystem2 = VirtualFileManager.getInstance().getFileSystem("file");
        Pair<CodegenFactory, CodegenFactory.BackendInput> pairConvertToIr = convertToIr(environment, analysisResultRepeatAnalysisIfNeeded);
        CodegenFactory codegenFactoryComponent1 = pairConvertToIr.component1();
        CodegenFactory.BackendInput backendInputComponent2 = pairConvertToIr.component2();
        BaseDiagnosticsCollector baseDiagnosticsCollectorCreateReporter$default = DiagnosticReporterFactory.createReporter$default(DiagnosticReporterFactory.INSTANCE, null, false, 3, null);
        ArrayList<CodegenFactory.CodegenInput> arrayList2 = new ArrayList(chunk.size());
        for (Module module : chunk) {
            ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
            List<KtFile> sourceFiles = CliCompilerUtilsKt.getSourceFiles(module, environment.getSourceFiles(), fileSystem2, chunk.size() > i ? i : 0, buildFile);
            List<KtFile> list2 = sourceFiles;
            VirtualFileSystem virtualFileSystem = fileSystem2;
            if (!UtilsKt.checkKotlinPackageUsageForPsi$default(environment.getConfiguration(), list2, messageCollector, 4, messageCollector)) {
                return false;
            }
            CodegenFactory codegenFactory = codegenFactoryComponent1;
            arrayList2.add(runLowerings(environment, CoreEnvironmentUtilsKt.applyModuleProperties(configuration, module, buildFile), analysisResultRepeatAnalysisIfNeeded, sourceFiles, module, codegenFactory, codegenFactoryComponent1.getModuleChunkBackendInput(backendInputComponent2, list2), baseDiagnosticsCollectorCreateReporter$default));
            fqNameFindMainClass = fqNameFindMainClass;
            analysisResultRepeatAnalysisIfNeeded = analysisResultRepeatAnalysisIfNeeded;
            fileSystem2 = virtualFileSystem;
            messageCollector = messageCollector;
            arrayList2 = arrayList2;
            backendInputComponent2 = backendInputComponent2;
            codegenFactoryComponent1 = codegenFactory;
            i = 1;
        }
        CodegenFactory codegenFactory2 = codegenFactoryComponent1;
        FqName fqName = fqNameFindMainClass;
        AnalysisResult analysisResult = analysisResultRepeatAnalysisIfNeeded;
        ArrayList arrayList3 = new ArrayList(chunk.size());
        for (CodegenFactory.CodegenInput codegenInput : arrayList2) {
            Intrinsics.checkNotNull(codegenInput);
            arrayList3.add(runCodegen(codegenInput, codegenInput.getState(), codegenFactory2, analysisResult.getBindingContext(), baseDiagnosticsCollectorCreateReporter$default, environment.getConfiguration()));
        }
        return CliCompilerUtilsKt.writeOutputsIfNeeded(environment.getProject(), configuration, chunk, arrayList3, fqName);
    }

    public final boolean compileBunchOfSources(KotlinCoreEnvironment environment) {
        GenerationState generationStateAnalyzeAndGenerate;
        Intrinsics.checkNotNullParameter(environment, "environment");
        ModuleVisibilityManager service = ModuleVisibilityManager.SERVICE.getInstance(environment.getProject());
        List<String> list = environment.getConfiguration().getList(JVMConfigurationKeys.FRIEND_PATHS);
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        for (String str : list) {
            Intrinsics.checkNotNull(str);
            service.addFriendPath(str);
        }
        if (!UtilsKt.checkKotlinPackageUsageForPsi$default(environment.getConfiguration(), environment.getSourceFiles(), null, 4, null) || (generationStateAnalyzeAndGenerate = analyzeAndGenerate(environment)) == null) {
            return false;
        }
        try {
            CliCompilerUtilsKt.writeOutput(environment.getConfiguration(), generationStateAnalyzeAndGenerate.getFactory(), null);
            return true;
        } finally {
            generationStateAnalyzeAndGenerate.destroy();
        }
    }

    private final AnalysisResult repeatAnalysisIfNeeded(AnalysisResult result, KotlinCoreEnvironment environment) {
        if (!(result instanceof AnalysisResult.RetryWithAdditionalRoots)) {
            return result;
        }
        CompilerConfiguration configuration = environment.getConfiguration();
        boolean zIsReadOnly = configuration.isReadOnly();
        configuration.setReadOnly(false);
        AnalysisResult.RetryWithAdditionalRoots retryWithAdditionalRoots = (AnalysisResult.RetryWithAdditionalRoots) result;
        JvmContentRootsKt.addJavaSourceRoots$default(configuration, retryWithAdditionalRoots.getAdditionalJavaRoots(), null, 2, null);
        configuration.setReadOnly(zIsReadOnly);
        if (retryWithAdditionalRoots.getAddToEnvironment()) {
            List<File> additionalJavaRoots = retryWithAdditionalRoots.getAdditionalJavaRoots();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(additionalJavaRoots, 10));
            Iterator<T> it = additionalJavaRoots.iterator();
            while (it.hasNext()) {
                arrayList.add(new JavaSourceRoot((File) it.next(), null));
            }
            environment.updateClasspath(arrayList);
        }
        if (!retryWithAdditionalRoots.getAdditionalClassPathRoots().isEmpty()) {
            List<File> additionalClassPathRoots = retryWithAdditionalRoots.getAdditionalClassPathRoots();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(additionalClassPathRoots, 10));
            Iterator<T> it2 = additionalClassPathRoots.iterator();
            while (it2.hasNext()) {
                arrayList2.add(new JvmClasspathRoot((File) it2.next(), false));
            }
            environment.updateClasspath(arrayList2);
        }
        if (!retryWithAdditionalRoots.getAdditionalKotlinRoots().isEmpty()) {
            environment.addKotlinSourceRoots(retryWithAdditionalRoots.getAdditionalKotlinRoots());
        }
        KotlinJavaPsiFacade.getInstance(environment.getProject()).clearPackageCaches();
        JavaClassesTracker javaClassesTracker = (JavaClassesTracker) configuration.get(ClassicFrontendSpecificJvmConfigurationKeys.JAVA_CLASSES_TRACKER);
        if (javaClassesTracker != null) {
            javaClassesTracker.clear();
        }
        LookupTracker lookupTracker = (LookupTracker) configuration.get(CommonConfigurationKeys.LOOKUP_TRACKER);
        if (lookupTracker != null) {
            lookupTracker.clear();
        }
        MessageCollector messageCollector = (MessageCollector) configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        if (messageCollector != null) {
            messageCollector.clear();
        }
        return repeatAnalysisIfNeeded(analyze(environment), environment);
    }

    public final GenerationState analyzeAndGenerate(KotlinCoreEnvironment environment) {
        Intrinsics.checkNotNullParameter(environment, "environment");
        AnalysisResult analysisResultRepeatAnalysisIfNeeded = repeatAnalysisIfNeeded(analyze(environment), environment);
        if (analysisResultRepeatAnalysisIfNeeded == null || !analysisResultRepeatAnalysisIfNeeded.getShouldGenerateCode()) {
            return null;
        }
        analysisResultRepeatAnalysisIfNeeded.throwIfError();
        Pair<CodegenFactory, CodegenFactory.BackendInput> pairConvertToIr = convertToIr(environment, analysisResultRepeatAnalysisIfNeeded);
        CodegenFactory codegenFactoryComponent1 = pairConvertToIr.component1();
        CodegenFactory.BackendInput backendInputComponent2 = pairConvertToIr.component2();
        BaseDiagnosticsCollector baseDiagnosticsCollectorCreateReporter$default = DiagnosticReporterFactory.createReporter$default(DiagnosticReporterFactory.INSTANCE, null, false, 3, null);
        CodegenFactory.CodegenInput codegenInputRunLowerings = runLowerings(environment, environment.getConfiguration(), analysisResultRepeatAnalysisIfNeeded, environment.getSourceFiles(), null, codegenFactoryComponent1, backendInputComponent2, baseDiagnosticsCollectorCreateReporter$default);
        return runCodegen(codegenInputRunLowerings, codegenInputRunLowerings.getState(), codegenFactoryComponent1, analysisResultRepeatAnalysisIfNeeded.getBindingContext(), baseDiagnosticsCollectorCreateReporter$default, environment.getConfiguration());
    }

    private final Pair<CodegenFactory, CodegenFactory.BackendInput> convertToIr(KotlinCoreEnvironment environment, AnalysisResult result) {
        DefaultCodegenFactory jvmIrCodegenFactory;
        CompilerConfiguration configuration = environment.getConfiguration();
        if (configuration.getBoolean(JVMConfigurationKeys.IR)) {
            jvmIrCodegenFactory = new JvmIrCodegenFactory(configuration, (PhaseConfig) configuration.get(CLIConfigurationKeys.PHASE_CONFIG), null, null, null, null, null, 124, null);
        } else {
            jvmIrCodegenFactory = DefaultCodegenFactory.INSTANCE;
        }
        CodegenFactory.IrConversionInput irConversionInput = new CodegenFactory.IrConversionInput(environment.getProject(), environment.getSourceFiles(), configuration, result.getModuleDescriptor(), result.getBindingContext(), CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), false, false);
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) environment.getConfiguration().get(CLIConfigurationKeys.PERF_MANAGER);
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyIRTranslationStarted();
        }
        CodegenFactory.BackendInput backendInputConvertToIr = jvmIrCodegenFactory.convertToIr(irConversionInput);
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyIRTranslationFinished();
        }
        return new Pair<>(jvmIrCodegenFactory, backendInputConvertToIr);
    }

    public final AnalysisResult analyze(final KotlinCoreEnvironment environment) {
        final List listEmptyList;
        KotlinLibraryResolveResult kotlinLibraryResolveResultJvmResolveLibraries;
        Intrinsics.checkNotNullParameter(environment, "environment");
        MessageCollector messageCollector = CoreEnvironmentUtilsKt.getMessageCollector(environment);
        final List<KtFile> sourceFiles = environment.getSourceFiles();
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) environment.getConfiguration().get(CLIConfigurationKeys.PERF_MANAGER);
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyAnalysisStarted();
        }
        List list = (List) environment.getConfiguration().get(JVMConfigurationKeys.KLIB_PATHS);
        if (list == null || (kotlinLibraryResolveResultJvmResolveLibraries = JvmLibraryResolverKt.jvmResolveLibraries(list, UtilsKt.toLogger(messageCollector))) == null || (listEmptyList = KotlinLibraryResolveResult.getFullList$default(kotlinLibraryResolveResultJvmResolveLibraries, null, 1, null)) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        AnalyzerWithCompilerReport analyzerWithCompilerReport = new AnalyzerWithCompilerReport(messageCollector, CommonConfigurationKeysKt.getLanguageVersionSettings(environment.getConfiguration()), environment.getConfiguration().getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
        analyzerWithCompilerReport.analyzeAndReport(sourceFiles, new Function0<AnalysisResult>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.analyze.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final AnalysisResult invoke() {
                HashSet hashSet;
                Project project = environment.getProject();
                List list2 = (List) environment.getConfiguration().get(JVMConfigurationKeys.MODULES);
                if (list2 != null) {
                    HashSet hashSet2 = new HashSet();
                    KotlinCoreEnvironment kotlinCoreEnvironment = environment;
                    Iterator it = list2.iterator();
                    while (it.hasNext()) {
                        VirtualFile virtualFileFindLocalFile = kotlinCoreEnvironment.findLocalFile(((Module) it.next()).getOutputDir());
                        if (virtualFileFindLocalFile != null) {
                            hashSet2.add(virtualFileFindLocalFile);
                        }
                    }
                    hashSet = hashSet2;
                } else {
                    hashSet = null;
                }
                HashSet hashSetEmptySet = hashSet;
                if (hashSetEmptySet == null) {
                    hashSetEmptySet = SetsKt.emptySet();
                }
                GlobalSearchScope globalSearchScopeNewModuleSearchScope = TopDownAnalyzerFacadeForJVM.INSTANCE.newModuleSearchScope(project, sourceFiles);
                if (!hashSetEmptySet.isEmpty()) {
                    globalSearchScopeNewModuleSearchScope = globalSearchScopeNewModuleSearchScope.uniteWith(new DirectoriesScope(project, hashSetEmptySet));
                    Intrinsics.checkNotNullExpressionValue(globalSearchScopeNewModuleSearchScope, "uniteWith(...)");
                }
                return TopDownAnalyzerFacadeForJVM.analyzeFilesWithJavaIntegration$default(project, sourceFiles, new NoScopeRecordCliBindingTrace(), environment.getConfiguration(), new C02801(environment), null, globalSearchScopeNewModuleSearchScope, listEmptyList, null, null, null, NewHope.SENDA_BYTES, null);
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler$analyze$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: KotlinToJVMBytecodeCompiler.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class C02801 extends FunctionReference implements Function1<GlobalSearchScope, JvmPackagePartProvider> {
                C02801(Object obj) {
                    super(1, obj);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "createPackagePartProvider(Lcom/intellij/psi/search/GlobalSearchScope;)Lorg/jetbrains/kotlin/cli/jvm/compiler/JvmPackagePartProvider;";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "createPackagePartProvider";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(KotlinCoreEnvironment.class);
                }

                @Override // kotlin.jvm.functions.Function1
                public final JvmPackagePartProvider invoke(GlobalSearchScope p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return ((KotlinCoreEnvironment) this.receiver).createPackagePartProvider(p0);
                }
            }
        });
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyAnalysisFinished();
        }
        AnalysisResult analysisResult = analyzerWithCompilerReport.getAnalysisResult();
        if (!analyzerWithCompilerReport.hasErrors() || (analysisResult instanceof AnalysisResult.RetryWithAdditionalRoots)) {
            return analysisResult;
        }
        return null;
    }

    /* JADX INFO: compiled from: KotlinToJVMBytecodeCompiler.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0011\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0006H\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010\b\u001a\u001c\u0012\t\u0012\u00070\n¢\u0006\u0002\b\u000b0\tj\r\u0012\t\u0012\u00070\n¢\u0006\u0002\b\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinToJVMBytecodeCompiler$DirectoriesScope;", "Lorg/jetbrains/kotlin/com/intellij/psi/search/DelegatingGlobalSearchScope;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "directories", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "(Lcom/intellij/openapi/project/Project;Ljava/util/Set;)V", "fileSystems", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "Lorg/jetbrains/annotations/NotNull;", "Lkotlin/collections/HashSet;", "contains", "", "file", "toString", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DirectoriesScope extends DelegatingGlobalSearchScope {
        private final Set<VirtualFile> directories;
        private final HashSet<VirtualFileSystem> fileSystems;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public DirectoriesScope(Project project, Set<? extends VirtualFile> directories) {
            super(GlobalSearchScope.allScope(project));
            Intrinsics.checkNotNullParameter(project, "project");
            Intrinsics.checkNotNullParameter(directories, "directories");
            this.directories = directories;
            HashSet<VirtualFileSystem> hashSet = new HashSet<>();
            Iterator it = directories.iterator();
            while (it.hasNext()) {
                hashSet.add(((VirtualFile) it.next()).getFileSystem());
            }
            this.fileSystems = hashSet;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile file) {
            Intrinsics.checkNotNullParameter(file, "file");
            if (!this.fileSystems.contains(file.getFileSystem())) {
                return false;
            }
            while (!this.directories.contains(file)) {
                file = file.getParent();
                if (file == null) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope
        public String toString() {
            return "All files under: " + this.directories;
        }
    }

    private final CodegenFactory.CodegenInput runLowerings(KotlinCoreEnvironment environment, CompilerConfiguration configuration, AnalysisResult result, List<? extends KtFile> sourceFiles, Module module, CodegenFactory codegenFactory, CodegenFactory.BackendInput backendInput, BaseDiagnosticsCollector diagnosticsReporter) {
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) environment.getConfiguration().get(CLIConfigurationKeys.PERF_MANAGER);
        Project project = environment.getProject();
        ClassBuilderFactory BINARIES = ClassBuilderFactories.BINARIES;
        Intrinsics.checkNotNullExpressionValue(BINARIES, "BINARIES");
        GenerationState generationStateBuild = CliCompilerUtilsKt.withModule(new GenerationState.Builder(project, BINARIES, result.getModuleDescriptor(), result.getBindingContext(), configuration), module).onIndependentPartCompilationEnd(CliCompilerUtilsKt.createOutputFilesFlushingCallbackIfPossible(configuration)).diagnosticReporter(diagnosticsReporter).build();
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyGenerationStarted();
        }
        generationStateBuild.beforeCompile();
        generationStateBuild.oldBEInitTrace(sourceFiles);
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyIRLoweringStarted();
        }
        CodegenFactory.CodegenInput codegenInputInvokeLowerings = codegenFactory.invokeLowerings(generationStateBuild, backendInput);
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyIRLoweringFinished();
        }
        return codegenInputInvokeLowerings;
    }

    private final GenerationState runCodegen(CodegenFactory.CodegenInput codegenInput, GenerationState state, CodegenFactory codegenFactory, BindingContext bindingContext, BaseDiagnosticsCollector diagnosticsReporter, CompilerConfiguration configuration) {
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) configuration.get(CLIConfigurationKeys.PERF_MANAGER);
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyIRGenerationStarted();
        }
        codegenFactory.invokeCodegen(codegenInput);
        CodegenFactory.INSTANCE.doCheckCancelled(state);
        state.getFactory().done();
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyIRGenerationFinished();
        }
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyGenerationFinished();
        }
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        AnalyzerWithCompilerReport.Companion companion = AnalyzerWithCompilerReport.INSTANCE;
        Diagnostics collectedExtraJvmDiagnostics = state.getCollectedExtraJvmDiagnostics();
        Diagnostics diagnostics = bindingContext.getDiagnostics();
        Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
        companion.reportDiagnostics(new FilteredJvmDiagnostics(collectedExtraJvmDiagnostics, diagnostics), messageCollector, configuration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
        FirDiagnosticsCompilerResultsReporter.INSTANCE.reportToMessageCollector(diagnosticsReporter, messageCollector, configuration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        return state;
    }
}
