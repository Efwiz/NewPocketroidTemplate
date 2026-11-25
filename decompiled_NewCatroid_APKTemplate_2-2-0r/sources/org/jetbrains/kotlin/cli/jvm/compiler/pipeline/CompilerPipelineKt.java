package org.jetbrains.kotlin.cli.jvm.compiler.pipeline;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory;
import org.jetbrains.kotlin.backend.jvm.JvmIrDeserializerImpl;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt;
import org.jetbrains.kotlin.cli.common.GroupedKtSourcesKt;
import org.jetbrains.kotlin.cli.common.SessionWithSources;
import org.jetbrains.kotlin.cli.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.config.ContentRoot;
import org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporterKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.ClasspathRootsResolver;
import org.jetbrains.kotlin.cli.jvm.compiler.CliCompilerUtilsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.CliVirtualFileFinderFactory;
import org.jetbrains.kotlin.cli.jvm.compiler.CompatKt;
import org.jetbrains.kotlin.cli.jvm.compiler.CoreEnvironmentUtilsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.FirKotlinToJvmBytecodeCompilerKt;
import org.jetbrains.kotlin.cli.jvm.compiler.JavaLanguageLevelKt;
import org.jetbrains.kotlin.cli.jvm.compiler.JvmPackagePartProvider;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.NoScopeRecordCliBindingTrace;
import org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment;
import org.jetbrains.kotlin.cli.jvm.config.JavaSourceRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootBase;
import org.jetbrains.kotlin.cli.jvm.config.JvmModulePathRoot;
import org.jetbrains.kotlin.cli.jvm.index.JavaRoot;
import org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesDynamicCompoundIndex;
import org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl;
import org.jetbrains.kotlin.cli.jvm.index.SingleJavaFileRootsIndex;
import org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder;
import org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleResolver;
import org.jetbrains.kotlin.codegen.ClassBuilderFactories;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.CodegenFactory;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.core.CoreJavaFileManager;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporterFactory;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.diagnostics.impl.PendingDiagnosticsCollectorWithSuppress;
import org.jetbrains.kotlin.fir.DependencyListForCliModule;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.jvm.FirJvmBackendClassResolver;
import org.jetbrains.kotlin.fir.backend.jvm.FirJvmBackendExtension;
import org.jetbrains.kotlin.fir.backend.jvm.JvmFir2IrExtensions;
import org.jetbrains.kotlin.fir.extensions.FirAnalysisHandlerExtension;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.pipeline.ConvertToIrKt;
import org.jetbrains.kotlin.fir.pipeline.Fir2IrActualizedResult;
import org.jetbrains.kotlin.fir.pipeline.FirResult;
import org.jetbrains.kotlin.fir.pipeline.FirUtilsKt;
import org.jetbrains.kotlin.fir.pipeline.ModuleCompilerAnalyzedOutput;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.session.IncrementalCompilationContext;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.ir.backend.jvm.serialization.JvmIrMangler;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.kotlin.MetadataFinderFactory;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.VirtualFileFinderFactory;
import org.jetbrains.kotlin.load.kotlin.incremental.IncrementalPackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.modules.Module;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.modules.TargetIdKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.CommonPlatforms;
import org.jetbrains.kotlin.platform.jvm.JvmPlatforms;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModule;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleResolver;

/* JADX INFO: compiled from: compilerPipeline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000°\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a@\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u001a>\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00072\u0006\u0010\u001b\u001a\u00020\u001c\u001a*\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\u001a\u001e\u0010$\u001a\u00020%2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005\u001a8\u0010'\u001a\u0004\u0018\u00010(2\u0006\u0010)\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\n2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\u0010+\u001a\u0004\u0018\u00010\n\u001a\"\u0010,\u001a\u0004\u0018\u00010\n2\u0006\u0010)\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\t\u001a\u0004\u0018\u00010\n\u001a&\u0010-\u001a\u00020.2\u0006\u0010)\u001a\u00020\u00142\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u0010\u0015\u001a\u00020\u0016\u001a \u00103\u001a\u0002042\u0006\u0010\u0002\u001a\u00020%2\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u001a\u0010\u00105\u001a\u00020\u00102\u0006\u0010)\u001a\u00020\u0014H\u0002\u001a.\u00106\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010)\u001a\u00020\u00142\f\u00107\u001a\b\u0012\u0004\u0012\u000209082\b\u0010:\u001a\u0004\u0018\u00010;\u001a&\u0010<\u001a\u0004\u0018\u00010\u001e*\u00020\"2\u0006\u0010\u001f\u001a\u00020=2\u0006\u0010>\u001a\u00020\u001c2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\u001a\u0016\u0010?\u001a\u0004\u0018\u00010\u001e*\u00020\"2\u0006\u0010@\u001a\u00020\u0018H\u0002¨\u0006A"}, d2 = {"compileModuleToAnalyzedFir", "Lorg/jetbrains/kotlin/fir/pipeline/FirResult;", "input", "Lorg/jetbrains/kotlin/cli/jvm/compiler/pipeline/ModuleCompilerInput;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/pipeline/ModuleCompilerEnvironment;", "previousStepsSymbolProviders", "", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "incrementalExcludesScope", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "diagnosticsReporter", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "performanceManager", "Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "compileModulesUsingFrontendIrAndLightTree", "", "projectEnvironment", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "buildFile", "Ljava/io/File;", "chunk", "Lorg/jetbrains/kotlin/modules/Module;", "targetDescription", "", "contentRootToVirtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "root", "Lorg/jetbrains/kotlin/cli/jvm/config/JvmContentRootBase;", "localFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "jarFileSystem", "convertAnalyzedFirToIr", "Lorg/jetbrains/kotlin/cli/jvm/compiler/pipeline/ModuleCompilerIrBackendInput;", "analysisResults", "createContextForIncrementalCompilation", "Lorg/jetbrains/kotlin/fir/session/IncrementalCompilationContext;", "configuration", "sourceScope", "incrementalCompilationScope", "createIncrementalCompilationScope", "createProjectEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/VfsBasedProjectEnvironment;", "parentDisposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "configFiles", "Lorg/jetbrains/kotlin/cli/jvm/compiler/EnvironmentConfigFiles;", "generateCodeFromIr", "Lorg/jetbrains/kotlin/cli/jvm/compiler/pipeline/ModuleCompilerOutput;", "needCreateIncrementalCompilationScope", "writeOutputs", "outputs", "", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "mainClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "findExistingRoot", "Lorg/jetbrains/kotlin/cli/jvm/config/JvmContentRoot;", "rootDescription", "findJarRoot", "file", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilerPipelineKt {
    public static final boolean compileModulesUsingFrontendIrAndLightTree(AbstractProjectEnvironment projectEnvironment, CompilerConfiguration compilerConfiguration, final MessageCollector messageCollector, File file, List<? extends Module> chunk, String targetDescription) {
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(chunk, "chunk");
        Intrinsics.checkNotNullParameter(targetDescription, "targetDescription");
        if (!(projectEnvironment instanceof VfsBasedProjectEnvironment)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) compilerConfiguration.get(CLIConfigurationKeys.PERF_MANAGER);
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyCompilerInitialized(0, 0, targetDescription);
        }
        VfsBasedProjectEnvironment vfsBasedProjectEnvironment = (VfsBasedProjectEnvironment) projectEnvironment;
        Boolean boolAnalyze = FirAnalysisHandlerExtension.INSTANCE.analyze(vfsBasedProjectEnvironment.getProject(), compilerConfiguration);
        if (boolAnalyze != null) {
            return boolAnalyze.booleanValue();
        }
        ArrayList arrayList = new ArrayList();
        FqName fqNameFindMainClass = null;
        for (Module module : chunk) {
            CompilerConfiguration compilerConfigurationCopy = compilerConfiguration.copy();
            Intrinsics.checkNotNullExpressionValue(compilerConfigurationCopy, "copy(...)");
            CompilerConfiguration compilerConfigurationApplyModuleProperties = CoreEnvironmentUtilsKt.applyModuleProperties(compilerConfigurationCopy, module, file);
            compilerConfigurationApplyModuleProperties.put(JVMConfigurationKeys.FRIEND_PATHS, module.getFriendPaths());
            ModuleCompilerInput moduleCompilerInput = new ModuleCompilerInput(TargetIdKt.TargetId(module), GroupedKtSourcesKt.collectSources(compilerConfiguration, vfsBasedProjectEnvironment, messageCollector), CommonPlatforms.INSTANCE.getDefaultCommonPlatform(), JvmPlatforms.INSTANCE.getUnspecifiedJvmPlatform(), compilerConfigurationApplyModuleProperties, null, 32, null);
            boolean z = compilerConfigurationApplyModuleProperties.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME);
            PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppressCreatePendingReporter = DiagnosticReporterFactory.INSTANCE.createPendingReporter(new Function2<Boolean, String, Unit>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.pipeline.CompilerPipelineKt$compileModulesUsingFrontendIrAndLightTree$diagnosticsReporter$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool, String str) {
                    invoke(bool.booleanValue(), str);
                    return Unit.INSTANCE;
                }

                public final void invoke(boolean z2, String message) {
                    Intrinsics.checkNotNullParameter(message, "message");
                    MessageCollector.report$default(messageCollector, z2 ? CompilerMessageSeverity.ERROR : CompilerMessageSeverity.WARNING, message, null, 4, null);
                }
            });
            PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppress = pendingDiagnosticsCollectorWithSuppressCreatePendingReporter;
            ModuleCompilerEnvironment moduleCompilerEnvironment = new ModuleCompilerEnvironment(projectEnvironment, pendingDiagnosticsCollectorWithSuppress);
            if (commonCompilerPerformanceManager != null) {
                commonCompilerPerformanceManager.notifyAnalysisStarted();
            }
            FirResult firResultCompileModuleToAnalyzedFir = compileModuleToAnalyzedFir(moduleCompilerInput, moduleCompilerEnvironment, CollectionsKt.emptyList(), null, pendingDiagnosticsCollectorWithSuppress, commonCompilerPerformanceManager);
            List<ModuleCompilerAnalyzedOutput> outputs = firResultCompileModuleToAnalyzedFir.getOutputs();
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it = outputs.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList2, ((ModuleCompilerAnalyzedOutput) it.next()).getFir());
            }
            if (!UtilsKt.checkKotlinPackageUsageForLightTree$default(compilerConfigurationApplyModuleProperties, arrayList2, null, 4, null)) {
                return false;
            }
            if (commonCompilerPerformanceManager != null) {
                commonCompilerPerformanceManager.notifyAnalysisFinished();
            }
            if (fqNameFindMainClass == null && compilerConfigurationApplyModuleProperties.get(JVMConfigurationKeys.OUTPUT_JAR) != null) {
                fqNameFindMainClass = FirKotlinToJvmBytecodeCompilerKt.findMainClass(((ModuleCompilerAnalyzedOutput) CollectionsKt.last((List) firResultCompileModuleToAnalyzedFir.getOutputs())).getFir());
            }
            if (pendingDiagnosticsCollectorWithSuppressCreatePendingReporter.getHasErrors()) {
                FirDiagnosticsCompilerResultsReporterKt.reportToMessageCollector(pendingDiagnosticsCollectorWithSuppress, messageCollector, z);
            } else {
                if (commonCompilerPerformanceManager != null) {
                    commonCompilerPerformanceManager.notifyGenerationStarted();
                }
                if (commonCompilerPerformanceManager != null) {
                    commonCompilerPerformanceManager.notifyIRTranslationStarted();
                }
                ModuleCompilerIrBackendInput moduleCompilerIrBackendInputConvertAnalyzedFirToIr = convertAnalyzedFirToIr(moduleCompilerInput, firResultCompileModuleToAnalyzedFir, moduleCompilerEnvironment);
                if (commonCompilerPerformanceManager != null) {
                    commonCompilerPerformanceManager.notifyIRTranslationFinished();
                }
                ModuleCompilerOutput moduleCompilerOutputGenerateCodeFromIr = generateCodeFromIr(moduleCompilerIrBackendInputConvertAnalyzedFirToIr, moduleCompilerEnvironment, commonCompilerPerformanceManager);
                FirDiagnosticsCompilerResultsReporterKt.reportToMessageCollector(pendingDiagnosticsCollectorWithSuppress, messageCollector, compilerConfigurationApplyModuleProperties.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
                if (commonCompilerPerformanceManager != null) {
                    commonCompilerPerformanceManager.notifyIRGenerationFinished();
                }
                if (commonCompilerPerformanceManager != null) {
                    commonCompilerPerformanceManager.notifyGenerationFinished();
                }
                if (!pendingDiagnosticsCollectorWithSuppressCreatePendingReporter.getHasErrors()) {
                    arrayList.add(moduleCompilerOutputGenerateCodeFromIr.getGenerationState());
                }
            }
        }
        return writeOutputs(projectEnvironment, compilerConfiguration, arrayList, fqNameFindMainClass);
    }

    public static final ModuleCompilerIrBackendInput convertAnalyzedFirToIr(ModuleCompilerInput input, FirResult analysisResults, ModuleCompilerEnvironment environment) {
        List<IrGenerationExtension> listEmptyList;
        Project project;
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(analysisResults, "analysisResults");
        Intrinsics.checkNotNullParameter(environment, "environment");
        JvmFir2IrExtensions jvmFir2IrExtensions = new JvmFir2IrExtensions(input.getConfiguration(), new JvmIrDeserializerImpl(), JvmIrMangler.INSTANCE);
        AbstractProjectEnvironment projectEnvironment = environment.getProjectEnvironment();
        VfsBasedProjectEnvironment vfsBasedProjectEnvironment = projectEnvironment instanceof VfsBasedProjectEnvironment ? (VfsBasedProjectEnvironment) projectEnvironment : null;
        if (vfsBasedProjectEnvironment == null || (project = vfsBasedProjectEnvironment.getProject()) == null || (listEmptyList = IrGenerationExtension.INSTANCE.getInstances(project)) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(input.getConfiguration());
        BaseDiagnosticsCollector diagnosticsReporter = environment.getDiagnosticsReporter();
        boolean z = input.getConfiguration().getBoolean(JVMConfigurationKeys.LINK_VIA_SIGNATURES);
        Object objPutIfAbsent = input.getConfiguration().putIfAbsent(CommonConfigurationKeys.EVALUATED_CONST_TRACKER, EvaluatedConstTracker.INSTANCE.create());
        Intrinsics.checkNotNullExpressionValue(objPutIfAbsent, "putIfAbsent(...)");
        Fir2IrActualizedResult fir2IrActualizedResultConvertToIrAndActualizeForJvm = ConvertToIrKt.convertToIrAndActualizeForJvm(analysisResults, jvmFir2IrExtensions, new Fir2IrConfiguration(languageVersionSettings, diagnosticsReporter, z, (EvaluatedConstTracker) objPutIfAbsent, (InlineConstTracker) input.getConfiguration().get(CommonConfigurationKeys.INLINE_CONST_TRACKER), (ExpectActualTracker) input.getConfiguration().get(CommonConfigurationKeys.EXPECT_ACTUAL_TRACKER), false), listEmptyList);
        return new ModuleCompilerIrBackendInput(input.getTargetId(), input.getConfiguration(), jvmFir2IrExtensions, fir2IrActualizedResultConvertToIrAndActualizeForJvm.getIrModuleFragment(), fir2IrActualizedResultConvertToIrAndActualizeForJvm.getComponents(), fir2IrActualizedResultConvertToIrAndActualizeForJvm.getPluginContext(), fir2IrActualizedResultConvertToIrAndActualizeForJvm.getIrActualizedResult());
    }

    public static final ModuleCompilerOutput generateCodeFromIr(ModuleCompilerIrBackendInput input, ModuleCompilerEnvironment environment, final CommonCompilerPerformanceManager commonCompilerPerformanceManager) {
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(environment, "environment");
        JvmIrCodegenFactory jvmIrCodegenFactory = new JvmIrCodegenFactory(input.getConfiguration(), (PhaseConfig) input.getConfiguration().get(CLIConfigurationKeys.PHASE_CONFIG), null, null, null, null, null, 124, null);
        BindingContext bindingContext = new NoScopeRecordCliBindingTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        AbstractProjectEnvironment projectEnvironment = environment.getProjectEnvironment();
        Intrinsics.checkNotNull(projectEnvironment, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment");
        Project project = ((VfsBasedProjectEnvironment) projectEnvironment).getProject();
        ClassBuilderFactory BINARIES = ClassBuilderFactories.BINARIES;
        Intrinsics.checkNotNullExpressionValue(BINARIES, "BINARIES");
        GenerationState generationStateBuild = new GenerationState.Builder(project, BINARIES, input.getIrModuleFragment().getDescriptor(), bindingContext, input.getConfiguration()).targetId(input.getTargetId()).moduleName(input.getTargetId().getName()).outDirectory((File) input.getConfiguration().get(JVMConfigurationKeys.OUTPUT_DIRECTORY)).onIndependentPartCompilationEnd(CliCompilerUtilsKt.createOutputFilesFlushingCallbackIfPossible(input.getConfiguration())).isIrBackend(true).jvmBackendClassResolver(new FirJvmBackendClassResolver(input.getComponents())).diagnosticReporter(environment.getDiagnosticsReporter()).build();
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyIRLoweringStarted();
        }
        generationStateBuild.beforeCompile();
        jvmIrCodegenFactory.generateModuleInFrontendIRMode(generationStateBuild, input.getIrModuleFragment(), input.getComponents().getSymbolTable(), input.getComponents().getIrProviders(), input.getExtensions(), new FirJvmBackendExtension(input.getComponents(), input.getIrActualizedResult()), input.getPluginContext(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.pipeline.CompilerPipelineKt.generateCodeFromIr.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                CommonCompilerPerformanceManager commonCompilerPerformanceManager2 = commonCompilerPerformanceManager;
                if (commonCompilerPerformanceManager2 != null) {
                    commonCompilerPerformanceManager2.notifyIRLoweringFinished();
                }
                CommonCompilerPerformanceManager commonCompilerPerformanceManager3 = commonCompilerPerformanceManager;
                if (commonCompilerPerformanceManager3 != null) {
                    commonCompilerPerformanceManager3.notifyIRGenerationStarted();
                }
            }
        });
        CodegenFactory.INSTANCE.doCheckCancelled(generationStateBuild);
        generationStateBuild.getFactory().done();
        return new ModuleCompilerOutput(generationStateBuild);
    }

    public static final FirResult compileModuleToAnalyzedFir(ModuleCompilerInput input, ModuleCompilerEnvironment environment, final List<? extends FirSymbolProvider> previousStepsSymbolProviders, AbstractProjectFileSearchScope abstractProjectFileSearchScope, BaseDiagnosticsCollector diagnosticsReporter, CommonCompilerPerformanceManager commonCompilerPerformanceManager) {
        AbstractProjectFileSearchScope abstractProjectFileSearchScopeMinus;
        List<FirExtensionRegistrar> listEmptyList;
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(environment, "environment");
        Intrinsics.checkNotNullParameter(previousStepsSymbolProviders, "previousStepsSymbolProviders");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        final AbstractProjectEnvironment projectEnvironment = environment.getProjectEnvironment();
        final CompilerConfiguration configuration = input.getConfiguration();
        AbstractProjectFileSearchScope searchScopeForProjectLibraries = projectEnvironment.getSearchScopeForProjectLibraries();
        String name2 = input.getTargetId().getName();
        final AbstractProjectFileSearchScope abstractProjectFileSearchScopeCreateIncrementalCompilationScope = createIncrementalCompilationScope(configuration, projectEnvironment, abstractProjectFileSearchScope);
        if (abstractProjectFileSearchScopeCreateIncrementalCompilationScope != null) {
            abstractProjectFileSearchScopeMinus = searchScopeForProjectLibraries.minus(abstractProjectFileSearchScopeCreateIncrementalCompilationScope);
        } else {
            abstractProjectFileSearchScopeMinus = searchScopeForProjectLibraries;
            abstractProjectFileSearchScopeCreateIncrementalCompilationScope = null;
        }
        VfsBasedProjectEnvironment vfsBasedProjectEnvironment = projectEnvironment instanceof VfsBasedProjectEnvironment ? (VfsBasedProjectEnvironment) projectEnvironment : null;
        if (vfsBasedProjectEnvironment == null || (listEmptyList = FirExtensionRegistrar.INSTANCE.getInstances(vfsBasedProjectEnvironment.getProject())) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(input.getGroupedSources().getCommonSources());
        arrayList.addAll(input.getGroupedSources().getPlatformSources());
        DependencyListForCliModule dependencyListForCliModuleCreateLibraryListForJvm = CliCompilerUtilsKt.createLibraryListForJvm(name2, configuration, CollectionsKt.emptyList());
        Name nameSpecial = Name.special("<" + name2 + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        List listPrepareJvmSessions = FirSessionConstructionUtilsKt.prepareJvmSessions(arrayList, configuration, projectEnvironment, nameSpecial, listEmptyList, abstractProjectFileSearchScopeMinus, dependencyListForCliModuleCreateLibraryListForJvm, FirSessionConstructionUtilsKt.isCommonSourceForLt(input.getGroupedSources()), FirSessionConstructionUtilsKt.getFileBelongsToModuleForLt(input.getGroupedSources()), new Function1<List<? extends KtSourceFile>, IncrementalCompilationContext>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.pipeline.CompilerPipelineKt$compileModuleToAnalyzedFir$sessionWithSources$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IncrementalCompilationContext invoke(List<? extends KtSourceFile> files) {
                Intrinsics.checkNotNullParameter(files, "files");
                return CompilerPipelineKt.createContextForIncrementalCompilation(configuration, projectEnvironment, AbstractProjectEnvironment.getSearchScopeBySourceFiles$default(projectEnvironment, files, false, 2, null), previousStepsSymbolProviders, abstractProjectFileSearchScopeCreateIncrementalCompilationScope);
            }
        });
        CompilerPipelineKt$compileModuleToAnalyzedFir$countFilesAndLines$1 compilerPipelineKt$compileModuleToAnalyzedFir$countFilesAndLines$1 = commonCompilerPerformanceManager != null ? new CompilerPipelineKt$compileModuleToAnalyzedFir$countFilesAndLines$1(commonCompilerPerformanceManager) : null;
        List<SessionWithSources> list = listPrepareJvmSessions;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (SessionWithSources sessionWithSources : list) {
            arrayList2.add(FirUtilsKt.buildResolveAndCheckFirViaLightTree(sessionWithSources.getSession(), sessionWithSources.component2(), diagnosticsReporter, compilerPipelineKt$compileModuleToAnalyzedFir$countFilesAndLines$1));
        }
        return new FirResult(arrayList2);
    }

    public static final boolean writeOutputs(AbstractProjectEnvironment projectEnvironment, CompilerConfiguration configuration, Collection<GenerationState> outputs, FqName fqName) {
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(outputs, "outputs");
        try {
            for (GenerationState generationState : outputs) {
                ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
                CliCompilerUtilsKt.writeOutput(generationState.getConfiguration(), generationState.getFactory(), fqName);
            }
            Collection<GenerationState> collection = outputs;
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                ((GenerationState) it.next()).destroy();
            }
            if (!configuration.getBoolean(JVMConfigurationKeys.COMPILE_JAVA)) {
                return true;
            }
            GenerationState generationState2 = (GenerationState) CollectionsKt.singleOrNull(collection);
            if (generationState2 == null) {
                Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
                Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
                MessageCollector.report$default((MessageCollector) notNull, CompilerMessageSeverity.WARNING, "A chunk contains multiple modules (" + CollectionsKt.joinToString$default(collection, null, null, null, 0, null, new Function1<GenerationState, CharSequence>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.pipeline.CompilerPipelineKt.writeOutputs.3
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(GenerationState it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return it2.getModuleName();
                    }
                }, 31, null) + "). -Xuse-javac option couldn't be used to compile java files", null, 4, null);
                return true;
            }
            JavacWrapper companion = JavacWrapper.INSTANCE.getInstance(((VfsBasedProjectEnvironment) projectEnvironment).getProject());
            try {
                boolean zCompile = companion.compile(generationState2.getOutDirectory());
                CloseableKt.closeFinally(companion, null);
                return zCompile;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(companion, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            Iterator<T> it2 = outputs.iterator();
            while (it2.hasNext()) {
                ((GenerationState) it2.next()).destroy();
            }
            throw th3;
        }
    }

    public static final AbstractProjectFileSearchScope createIncrementalCompilationScope(CompilerConfiguration configuration, AbstractProjectEnvironment projectEnvironment, AbstractProjectFileSearchScope abstractProjectFileSearchScope) {
        File file;
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        if (!needCreateIncrementalCompilationScope(configuration) || (file = (File) configuration.get(JVMConfigurationKeys.OUTPUT_DIRECTORY)) == null) {
            return null;
        }
        AbstractProjectFileSearchScope searchScopeByDirectories = projectEnvironment.getSearchScopeByDirectories(SetsKt.setOf(file));
        return (abstractProjectFileSearchScope == null || abstractProjectFileSearchScope.isEmpty()) ? searchScopeByDirectories : searchScopeByDirectories.minus(abstractProjectFileSearchScope);
    }

    private static final boolean needCreateIncrementalCompilationScope(CompilerConfiguration compilerConfiguration) {
        return (compilerConfiguration.get(JVMConfigurationKeys.MODULES) == null || compilerConfiguration.get(JVMConfigurationKeys.INCREMENTAL_COMPILATION_COMPONENTS) == null) ? false : true;
    }

    public static final IncrementalCompilationContext createContextForIncrementalCompilation(CompilerConfiguration configuration, AbstractProjectEnvironment projectEnvironment, AbstractProjectFileSearchScope sourceScope, List<? extends FirSymbolProvider> previousStepsSymbolProviders, AbstractProjectFileSearchScope abstractProjectFileSearchScope) {
        List list;
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(sourceScope, "sourceScope");
        Intrinsics.checkNotNullParameter(previousStepsSymbolProviders, "previousStepsSymbolProviders");
        if ((abstractProjectFileSearchScope == null && previousStepsSymbolProviders.isEmpty()) || (list = (List) configuration.get(JVMConfigurationKeys.MODULES)) == null) {
            return null;
        }
        List list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(TargetIdKt.TargetId((Module) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        IncrementalCompilationComponents incrementalCompilationComponents = (IncrementalCompilationComponents) configuration.get(JVMConfigurationKeys.INCREMENTAL_COMPILATION_COMPONENTS);
        if (incrementalCompilationComponents == null) {
            return null;
        }
        List<? extends FirSymbolProvider> list3 = previousStepsSymbolProviders;
        PackagePartProvider packagePartProvider = projectEnvironment.getPackagePartProvider(sourceScope);
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            arrayList4.add(incrementalCompilationComponents.getIncrementalCache((TargetId) it2.next()));
        }
        return new IncrementalCompilationContext(list3, new IncrementalPackagePartProvider(packagePartProvider, arrayList4), abstractProjectFileSearchScope);
    }

    public static final VfsBasedProjectEnvironment createProjectEnvironment(final CompilerConfiguration configuration, Disposable parentDisposable, EnvironmentConfigFiles configFiles, final MessageCollector messageCollector) throws IllegalAccessException, NoSuchFieldException {
        String absolutePath;
        Module module;
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(parentDisposable, "parentDisposable");
        Intrinsics.checkNotNullParameter(configFiles, "configFiles");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        CompatKt.setupIdeaStandaloneExecution();
        final KotlinCoreEnvironment.ProjectEnvironment projectEnvironment = new KotlinCoreEnvironment.ProjectEnvironment(parentDisposable, KotlinCoreEnvironment.INSTANCE.getOrCreateApplicationEnvironmentForProduction(parentDisposable, configuration), configuration);
        KotlinCoreEnvironment.INSTANCE.configureProjectEnvironment(projectEnvironment, configuration, configFiles);
        MockProject project = projectEnvironment.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        final VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
        Object service = project.getService(CoreJavaFileManager.class);
        Intrinsics.checkNotNull(service, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl");
        KotlinCliJavaFileManagerImpl kotlinCliJavaFileManagerImpl = (KotlinCliJavaFileManagerImpl) service;
        Integer num = (Integer) configuration.get(JVMConfigurationKeys.JDK_RELEASE);
        KotlinCliJavaFileManagerImpl kotlinCliJavaFileManagerImpl2 = kotlinCliJavaFileManagerImpl;
        MockProject mockProject = project;
        CliJavaModuleFinder cliJavaModuleFinder = new CliJavaModuleFinder((File) configuration.get(JVMConfigurationKeys.JDK_HOME), messageCollector, kotlinCliJavaFileManagerImpl2, mockProject, num);
        List list = (List) configuration.get(JVMConfigurationKeys.MODULES);
        if (list == null || (module = (Module) CollectionsKt.singleOrNull(list)) == null || (absolutePath = module.getOutputDir()) == null) {
            File file = (File) configuration.get(JVMConfigurationKeys.OUTPUT_DIRECTORY);
            absolutePath = file != null ? file.getAbsolutePath() : null;
        }
        PsiManager psiManager = PsiManager.getInstance(mockProject);
        Intrinsics.checkNotNullExpressionValue(psiManager, "getInstance(...)");
        List list2 = configuration.getList(JVMConfigurationKeys.ADDITIONAL_JAVA_MODULES);
        Intrinsics.checkNotNullExpressionValue(list2, "getList(...)");
        ClasspathRootsResolver classpathRootsResolver = new ClasspathRootsResolver(psiManager, messageCollector, list2, new Function1<JvmContentRootBase, VirtualFile>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.pipeline.CompilerPipelineKt$createProjectEnvironment$classpathRootsResolver$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final VirtualFile invoke(JvmContentRootBase it) {
                Intrinsics.checkNotNullParameter(it, "it");
                VirtualFileSystem localFileSystem = fileSystem;
                Intrinsics.checkNotNullExpressionValue(localFileSystem, "$localFileSystem");
                return CompilerPipelineKt.contentRootToVirtualFile(it, localFileSystem, projectEnvironment.getJarFileSystem(), messageCollector);
            }
        }, cliJavaModuleFinder, !configuration.getBoolean(CLIConfigurationKeys.ALLOW_KOTLIN_PACKAGE), absolutePath != null ? fileSystem.findFileByPath(absolutePath) : null, kotlinCliJavaFileManagerImpl2, num);
        List<? extends ContentRoot> list3 = configuration.getList(CLIConfigurationKeys.CONTENT_ROOTS);
        Intrinsics.checkNotNullExpressionValue(list3, "getList(...)");
        ClasspathRootsResolver.RootsAndModules rootsAndModulesConvertClasspathRoots = classpathRootsResolver.convertClasspathRoots(list3);
        List<JavaRoot> listComponent1 = rootsAndModulesConvertClasspathRoots.component1();
        List<JavaModule> listComponent2 = rootsAndModulesConvertClasspathRoots.component2();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : listComponent1) {
            VirtualFile file2 = ((JavaRoot) obj).getFile();
            if (file2.isDirectory() || !Intrinsics.areEqual(file2.getExtension(), SuffixConstants.EXTENSION_java)) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list4 = (List) pair.component1();
        List list5 = (List) pair.component2();
        JvmDependenciesDynamicCompoundIndex jvmDependenciesDynamicCompoundIndex = new JvmDependenciesDynamicCompoundIndex();
        jvmDependenciesDynamicCompoundIndex.addIndex(new JvmDependenciesIndexImpl(list4));
        Iterator<JavaRoot> it = jvmDependenciesDynamicCompoundIndex.getIndexedRoots().iterator();
        while (it.hasNext()) {
            projectEnvironment.addSourcesToClasspath(it.next().getFile());
        }
        project.registerService((Class<CliJavaModuleResolver>) JavaModuleResolver.class, new CliJavaModuleResolver(classpathRootsResolver.getJavaModuleGraph(), listComponent2, SequencesKt.toList(cliJavaModuleFinder.getSystemModules()), mockProject));
        JvmDependenciesDynamicCompoundIndex jvmDependenciesDynamicCompoundIndex2 = jvmDependenciesDynamicCompoundIndex;
        CliVirtualFileFinderFactory cliVirtualFileFinderFactory = new CliVirtualFileFinderFactory(jvmDependenciesDynamicCompoundIndex2, num != null);
        project.registerService((Class<CliVirtualFileFinderFactory>) MetadataFinderFactory.class, cliVirtualFileFinderFactory);
        project.registerService((Class<CliVirtualFileFinderFactory>) VirtualFileFinderFactory.class, cliVirtualFileFinderFactory);
        JavaLanguageLevelKt.setupHighestLanguageLevel(mockProject);
        Intrinsics.checkNotNull(fileSystem);
        ProjectEnvironmentWithCoreEnvironmentEmulation projectEnvironmentWithCoreEnvironmentEmulation = new ProjectEnvironmentWithCoreEnvironmentEmulation(mockProject, fileSystem, new Function1<GlobalSearchScope, PackagePartProvider>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.pipeline.CompilerPipelineKt.createProjectEnvironment.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PackagePartProvider invoke(GlobalSearchScope it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return new JvmPackagePartProvider(CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), it2);
            }
        }, listComponent1, configuration);
        kotlinCliJavaFileManagerImpl.initialize(jvmDependenciesDynamicCompoundIndex2, projectEnvironmentWithCoreEnvironmentEmulation.getPackagePartProviders(), new SingleJavaFileRootsIndex(list5), configuration.getBoolean(JVMConfigurationKeys.USE_PSI_CLASS_FILES_READING));
        return projectEnvironmentWithCoreEnvironmentEmulation;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final VirtualFile contentRootToVirtualFile(JvmContentRootBase jvmContentRootBase, VirtualFileSystem virtualFileSystem, VirtualFileSystem virtualFileSystem2, MessageCollector messageCollector) {
        if (jvmContentRootBase instanceof JvmClasspathRoot) {
            JvmClasspathRoot jvmClasspathRoot = (JvmClasspathRoot) jvmContentRootBase;
            return jvmClasspathRoot.getFile().isFile() ? findJarRoot(virtualFileSystem2, jvmClasspathRoot.getFile()) : findExistingRoot(virtualFileSystem, (JvmContentRoot) jvmContentRootBase, "Classpath entry", messageCollector);
        }
        if (jvmContentRootBase instanceof JvmModulePathRoot) {
            JvmModulePathRoot jvmModulePathRoot = (JvmModulePathRoot) jvmContentRootBase;
            return jvmModulePathRoot.getFile().isFile() ? findJarRoot(virtualFileSystem2, jvmModulePathRoot.getFile()) : findExistingRoot(virtualFileSystem, (JvmContentRoot) jvmContentRootBase, "Java module root", messageCollector);
        }
        if (jvmContentRootBase instanceof JavaSourceRoot) {
            return findExistingRoot(virtualFileSystem, (JvmContentRoot) jvmContentRootBase, "Java source root", messageCollector);
        }
        throw new IllegalStateException("Unexpected root: " + jvmContentRootBase);
    }

    private static final VirtualFile findJarRoot(VirtualFileSystem virtualFileSystem, File file) {
        return virtualFileSystem.findFileByPath(file + "!/");
    }

    private static final VirtualFile findExistingRoot(VirtualFileSystem virtualFileSystem, JvmContentRoot jvmContentRoot, String str, MessageCollector messageCollector) {
        VirtualFile virtualFileFindFileByPath = virtualFileSystem.findFileByPath(jvmContentRoot.getFile().getAbsolutePath());
        if (virtualFileFindFileByPath == null) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, str + " points to a non-existent location: " + jvmContentRoot.getFile(), null, 4, null);
        }
        return virtualFileFindFileByPath;
    }
}
