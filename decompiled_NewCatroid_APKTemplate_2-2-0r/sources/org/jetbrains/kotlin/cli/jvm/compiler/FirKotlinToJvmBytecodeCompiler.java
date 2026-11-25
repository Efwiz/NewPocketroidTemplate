package org.jetbrains.kotlin.cli.jvm.compiler;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.actualizer.IrActualizedResult;
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensions;
import org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory;
import org.jetbrains.kotlin.backend.jvm.JvmIrDeserializerImpl;
import org.jetbrains.kotlin.cli.common.CLICompiler;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt;
import org.jetbrains.kotlin.cli.common.SessionWithSources;
import org.jetbrains.kotlin.cli.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporter;
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.codegen.ClassBuilderFactories;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.CodegenFactory;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar;
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar;
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
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrPluginContext;
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
import org.jetbrains.kotlin.fir.session.IncrementalCompilationContext;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.ir.backend.jvm.serialization.JvmIrMangler;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.modules.Module;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.modules.TargetIdKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.resolve.multiplatform.IsCommonSourceKt;

/* JADX INFO: compiled from: FirKotlinToJvmBytecodeCompiler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001)B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JD\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\fJG\u0010\u0012\u001a\u00020\u0013\"\b\b\u0000\u0010\u0014*\u00020\u0001*\n\u0012\u0004\u0012\u0002H\u0014\u0018\u00010\f2\u000e\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u00162\u0017\u0010\u0018\u001a\u0013\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u00040\u0019¢\u0006\u0002\b\u001aH\u0002J\u001a\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u001c*\u00020\u001fH\u0002J2\u0010 \u001a\u00020\u001e*\u00020\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0002J$\u0010(\u001a\u0004\u0018\u00010\u001d*\u00020\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010&\u001a\u00020'H\u0002¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/FirKotlinToJvmBytecodeCompiler;", "", "()V", "compileModulesUsingFrontendIR", "", "projectEnvironment", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "projectConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "allSources", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "buildFile", "Ljava/io/File;", "chunk", "Lorg/jetbrains/kotlin/modules/Module;", "collectIncompatiblePluginNamesTo", "", ExifInterface.GPS_DIRECTION_TRUE, FirebaseAnalytics.Param.DESTINATION, "", "", "supportsK2", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "compileModule", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/pipeline/FirResult;", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "Lorg/jetbrains/kotlin/cli/jvm/compiler/FirKotlinToJvmBytecodeCompiler$CompilationContext;", "runBackend", "ktFiles", "fir2IrActualizedResult", "Lorg/jetbrains/kotlin/fir/pipeline/Fir2IrActualizedResult;", "extensions", "Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;", "diagnosticsReporter", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "runFrontend", "CompilationContext", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirKotlinToJvmBytecodeCompiler {
    public static final FirKotlinToJvmBytecodeCompiler INSTANCE = new FirKotlinToJvmBytecodeCompiler();

    private FirKotlinToJvmBytecodeCompiler() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v22, types: [org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment] */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24, types: [org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem] */
    /* JADX WARN: Type inference failed for: r0v55 */
    /* JADX WARN: Type inference failed for: r0v56 */
    public final boolean compileModulesUsingFrontendIR(AbstractProjectEnvironment projectEnvironment, CompilerConfiguration projectConfiguration, MessageCollector messageCollector, List<? extends KtFile> allSources, File buildFile, List<? extends Module> chunk) {
        ArrayList arrayList;
        ?? r0;
        ?? localFileSystem;
        List<FirExtensionRegistrar> listEmptyList;
        List<IrGenerationExtension> listEmptyList2;
        VfsBasedProjectEnvironment vfsBasedProjectEnvironment;
        Boolean boolAnalyze;
        AbstractProjectEnvironment projectEnvironment2 = projectEnvironment;
        List<? extends KtFile> allSources2 = allSources;
        Intrinsics.checkNotNullParameter(projectEnvironment2, "projectEnvironment");
        Intrinsics.checkNotNullParameter(projectConfiguration, "projectConfiguration");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(allSources2, "allSources");
        Intrinsics.checkNotNullParameter(chunk, "chunk");
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) projectConfiguration.get(CLIConfigurationKeys.PERF_MANAGER);
        ArrayList arrayList2 = new ArrayList();
        FirKotlinToJvmBytecodeCompiler firKotlinToJvmBytecodeCompiler = INSTANCE;
        firKotlinToJvmBytecodeCompiler.collectIncompatiblePluginNamesTo((List) projectConfiguration.get(ComponentRegistrar.INSTANCE.getPLUGIN_COMPONENT_REGISTRARS()), arrayList2, new PropertyReference1() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.FirKotlinToJvmBytecodeCompiler$compileModulesUsingFrontendIR$notSupportedPlugins$1$1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getSupportsK2()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((ComponentRegistrar) obj).getSupportsK2());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "supportsK2";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(ComponentRegistrar.class);
            }
        });
        firKotlinToJvmBytecodeCompiler.collectIncompatiblePluginNamesTo((List) projectConfiguration.get(CompilerPluginRegistrar.INSTANCE.getCOMPILER_PLUGIN_REGISTRARS()), arrayList2, new PropertyReference1() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.FirKotlinToJvmBytecodeCompiler$compileModulesUsingFrontendIR$notSupportedPlugins$1$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getSupportsK2()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((CompilerPluginRegistrar) obj).getSupportsK2());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "supportsK2";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(CompilerPluginRegistrar.class);
            }
        });
        int i = 0;
        int i2 = 1;
        FqName fqName = null;
        if (!arrayList2.isEmpty()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, StringsKt.trimMargin$default("\n                    |There are some plugins incompatible with language version 2.0:\n                    |" + CollectionsKt.joinToString$default(arrayList2, "\n|", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.FirKotlinToJvmBytecodeCompiler.compileModulesUsingFrontendIR.1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(String str) {
                    return "  " + str;
                }
            }, 30, null) + "\n                    |Please use language version 1.9 or below\n                ", null, 1, null), null, 4, null);
            return false;
        }
        ArrayList arrayList3 = new ArrayList(chunk.size());
        List list = (List) projectConfiguration.get(JVMConfigurationKeys.MODULES);
        char c = '\n';
        if (list != null) {
            List list2 = list;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList4.add(TargetIdKt.TargetId((Module) it.next()));
            }
            arrayList = arrayList4;
        } else {
            arrayList = null;
        }
        IncrementalCompilationComponents incrementalCompilationComponents = (IncrementalCompilationComponents) projectConfiguration.get(JVMConfigurationKeys.INCREMENTAL_COMPILATION_COMPONENTS);
        boolean z = chunk.size() > 1;
        boolean z2 = projectEnvironment2 instanceof VfsBasedProjectEnvironment;
        VfsBasedProjectEnvironment vfsBasedProjectEnvironment2 = z2 ? (VfsBasedProjectEnvironment) projectEnvironment2 : null;
        Project project = vfsBasedProjectEnvironment2 != null ? vfsBasedProjectEnvironment2.getProject() : null;
        if (project != null && (boolAnalyze = FirAnalysisHandlerExtension.INSTANCE.analyze(project, projectConfiguration)) != null) {
            return boolAnalyze.booleanValue();
        }
        for (Module module : chunk) {
            CompilerConfiguration compilerConfigurationApplyModuleProperties = CoreEnvironmentUtilsKt.applyModuleProperties(projectConfiguration, module, buildFile);
            if (z2) {
                vfsBasedProjectEnvironment = (VfsBasedProjectEnvironment) projectEnvironment2;
            } else {
                r0 = fqName;
            }
            if (r0 != 0) {
                r0 = vfsBasedProjectEnvironment;
                localFileSystem = r0.getLocalFileSystem();
            } else {
                r0 = vfsBasedProjectEnvironment;
                localFileSystem = fqName;
            }
            List<KtFile> sourceFiles = CliCompilerUtilsKt.getSourceFiles(module, allSources2, localFileSystem, z, buildFile);
            boolean z3 = compilerConfigurationApplyModuleProperties.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME);
            if (project == null || (listEmptyList = FirExtensionRegistrar.INSTANCE.getInstances(project)) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            List<FirExtensionRegistrar> list3 = listEmptyList;
            if (project == null || (listEmptyList2 = IrGenerationExtension.INSTANCE.getInstances(project)) == null) {
                listEmptyList2 = CollectionsKt.emptyList();
            }
            Project project2 = project;
            boolean z4 = z2;
            boolean z5 = z;
            ArrayList arrayList5 = arrayList3;
            FqName fqName2 = fqName;
            int i3 = i2;
            Pair<FirResult, GenerationState> pairCompileModule = compileModule(new CompilationContext(module, sourceFiles, projectEnvironment, messageCollector, z3, compilerConfigurationApplyModuleProperties, commonCompilerPerformanceManager, arrayList, incrementalCompilationComponents, list3, listEmptyList2));
            if (pairCompileModule == null) {
                return false;
            }
            arrayList5.add(pairCompileModule);
            project = project2;
            i2 = i3;
            z2 = z4;
            arrayList3 = arrayList5;
            fqName = fqName2;
            z = z5;
            c = '\n';
            projectEnvironment2 = projectEnvironment;
            allSources2 = allSources;
        }
        Project project3 = project;
        ArrayList arrayList6 = arrayList3;
        FqName fqName3 = fqName;
        int i4 = i2;
        if (chunk.size() == i4 && projectConfiguration.get(JVMConfigurationKeys.OUTPUT_JAR) != null) {
            i = i4;
        }
        FqName fqNameFindMainClass = i != 0 ? FirKotlinToJvmBytecodeCompilerKt.findMainClass(((ModuleCompilerAnalyzedOutput) CollectionsKt.last((List) ((FirResult) ((Pair) CollectionsKt.single((List) arrayList6)).getFirst()).getOutputs())).getFir()) : fqName3;
        ArrayList arrayList7 = arrayList6;
        ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList7, 10));
        Iterator it2 = arrayList7.iterator();
        while (it2.hasNext()) {
            arrayList8.add((GenerationState) ((Pair) it2.next()).getSecond());
        }
        return CliCompilerUtilsKt.writeOutputsIfNeeded(project3, projectConfiguration, chunk, arrayList8, fqNameFindMainClass);
    }

    private final <T> void collectIncompatiblePluginNamesTo(List<? extends T> list, List<String> list2, Function1<? super T, Boolean> function1) {
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            for (T t : list) {
                if (!function1.invoke(t).booleanValue() && !Intrinsics.areEqual(t.getClass().getCanonicalName(), CLICompiler.SCRIPT_PLUGIN_REGISTRAR_NAME)) {
                    arrayList.add(t);
                }
            }
            Iterator<T> it = arrayList.iterator();
            while (it.hasNext()) {
                list2.add(Reflection.getOrCreateKotlinClass(it.next().getClass()).getQualifiedName());
            }
        }
    }

    private final Pair<FirResult, GenerationState> compileModule(CompilationContext compilationContext) {
        CommonCompilerPerformanceManager performanceManager = compilationContext.getPerformanceManager();
        if (performanceManager != null) {
            performanceManager.notifyAnalysisStarted();
        }
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        if (!UtilsKt.checkKotlinPackageUsageForPsi$default(compilationContext.getModuleConfiguration(), compilationContext.getAllSources(), null, 4, null)) {
            return null;
        }
        boolean z = compilationContext.getModuleConfiguration().getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME);
        PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default = DiagnosticReporterFactory.createPendingReporter$default(DiagnosticReporterFactory.INSTANCE, null, 1, null);
        PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppress = pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default;
        FirResult firResultRunFrontend = runFrontend(compilationContext, compilationContext.getAllSources(), pendingDiagnosticsCollectorWithSuppress);
        CommonCompilerPerformanceManager performanceManager2 = compilationContext.getPerformanceManager();
        if (performanceManager2 != null) {
            performanceManager2.notifyAnalysisFinished();
        }
        if (firResultRunFrontend == null) {
            FirDiagnosticsCompilerResultsReporter.INSTANCE.reportToMessageCollector(pendingDiagnosticsCollectorWithSuppress, compilationContext.getMessageCollector(), z);
            return null;
        }
        CommonCompilerPerformanceManager performanceManager3 = compilationContext.getPerformanceManager();
        if (performanceManager3 != null) {
            performanceManager3.notifyGenerationStarted();
        }
        CommonCompilerPerformanceManager performanceManager4 = compilationContext.getPerformanceManager();
        if (performanceManager4 != null) {
            performanceManager4.notifyIRTranslationStarted();
        }
        JvmFir2IrExtensions jvmFir2IrExtensions = new JvmFir2IrExtensions(compilationContext.getModuleConfiguration(), new JvmIrDeserializerImpl(), JvmIrMangler.INSTANCE);
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(compilationContext.getModuleConfiguration());
        PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppress2 = pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default;
        boolean z2 = compilationContext.getModuleConfiguration().getBoolean(JVMConfigurationKeys.LINK_VIA_SIGNATURES);
        Object objPutIfAbsent = compilationContext.getModuleConfiguration().putIfAbsent(CommonConfigurationKeys.EVALUATED_CONST_TRACKER, EvaluatedConstTracker.INSTANCE.create());
        Intrinsics.checkNotNullExpressionValue(objPutIfAbsent, "putIfAbsent(...)");
        Fir2IrActualizedResult fir2IrActualizedResultConvertToIrAndActualizeForJvm = ConvertToIrKt.convertToIrAndActualizeForJvm(firResultRunFrontend, jvmFir2IrExtensions, new Fir2IrConfiguration(languageVersionSettings, pendingDiagnosticsCollectorWithSuppress2, z2, (EvaluatedConstTracker) objPutIfAbsent, (InlineConstTracker) compilationContext.getModuleConfiguration().get(CommonConfigurationKeys.INLINE_CONST_TRACKER), (ExpectActualTracker) compilationContext.getModuleConfiguration().get(CommonConfigurationKeys.EXPECT_ACTUAL_TRACKER), false), compilationContext.getIrGenerationExtensions());
        CommonCompilerPerformanceManager performanceManager5 = compilationContext.getPerformanceManager();
        if (performanceManager5 != null) {
            performanceManager5.notifyIRTranslationFinished();
        }
        GenerationState generationStateRunBackend = runBackend(compilationContext, compilationContext.getAllSources(), fir2IrActualizedResultConvertToIrAndActualizeForJvm, jvmFir2IrExtensions, pendingDiagnosticsCollectorWithSuppress);
        FirDiagnosticsCompilerResultsReporter.INSTANCE.reportToMessageCollector(pendingDiagnosticsCollectorWithSuppress, compilationContext.getMessageCollector(), z);
        CommonCompilerPerformanceManager performanceManager6 = compilationContext.getPerformanceManager();
        if (performanceManager6 != null) {
            performanceManager6.notifyIRGenerationFinished();
        }
        CommonCompilerPerformanceManager performanceManager7 = compilationContext.getPerformanceManager();
        if (performanceManager7 != null) {
            performanceManager7.notifyGenerationFinished();
        }
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        return TuplesKt.to(firResultRunFrontend, generationStateRunBackend);
    }

    private final FirResult runFrontend(CompilationContext compilationContext, List<? extends KtFile> list, BaseDiagnosticsCollector baseDiagnosticsCollector) {
        AbstractProjectFileSearchScope precompiledBinariesFileScope;
        List<? extends KtFile> list2 = list;
        Iterator<T> it = list2.iterator();
        boolean isHasErrors = false;
        while (it.hasNext()) {
            isHasErrors |= AnalyzerWithCompilerReport.INSTANCE.reportSyntaxErrors((KtFile) it.next(), compilationContext.getMessageCollector()).getIsHasErrors();
        }
        AbstractProjectEnvironment projectEnvironment = compilationContext.getProjectEnvironment();
        Intrinsics.checkNotNull(projectEnvironment, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment");
        AbstractProjectFileSearchScope abstractProjectFileSearchScopePlus = VfsBasedProjectEnvironment.getSearchScopeByPsiFiles$default((VfsBasedProjectEnvironment) projectEnvironment, list2, false, 2, null).plus(compilationContext.getProjectEnvironment().getSearchScopeForProjectJavaSources());
        AbstractProjectFileSearchScope searchScopeForProjectLibraries = compilationContext.getProjectEnvironment().getSearchScopeForProjectLibraries();
        final IncrementalCompilationContext incrementalCompilationContextCreateContextForIncrementalCompilation = CliCompilerUtilsKt.createContextForIncrementalCompilation(compilationContext.getProjectEnvironment(), compilationContext.getIncrementalComponents(), compilationContext.getModuleConfiguration(), compilationContext.getTargetIds(), abstractProjectFileSearchScopePlus);
        if (incrementalCompilationContextCreateContextForIncrementalCompilation != null && (precompiledBinariesFileScope = incrementalCompilationContextCreateContextForIncrementalCompilation.getPrecompiledBinariesFileScope()) != null) {
            searchScopeForProjectLibraries = searchScopeForProjectLibraries.minus(precompiledBinariesFileScope);
        }
        AbstractProjectFileSearchScope abstractProjectFileSearchScope = searchScopeForProjectLibraries;
        String name2 = compilationContext.getModule().getName();
        DependencyListForCliModule dependencyListForCliModuleCreateLibraryListForJvm = CliCompilerUtilsKt.createLibraryListForJvm(name2, compilationContext.getModuleConfiguration(), compilationContext.getModule().getFriendPaths());
        CompilerConfiguration moduleConfiguration = compilationContext.getModuleConfiguration();
        AbstractProjectEnvironment projectEnvironment2 = compilationContext.getProjectEnvironment();
        Name nameSpecial = Name.special("<" + name2 + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        List<SessionWithSources> listPrepareJvmSessions = FirSessionConstructionUtilsKt.prepareJvmSessions(list, moduleConfiguration, projectEnvironment2, nameSpecial, compilationContext.getExtensionRegistrars(), abstractProjectFileSearchScope, dependencyListForCliModuleCreateLibraryListForJvm, new Function1<KtFile, Boolean>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.FirKotlinToJvmBytecodeCompiler$runFrontend$sessionsWithSources$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtFile it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(Intrinsics.areEqual((Object) IsCommonSourceKt.isCommonSource(it2), (Object) true));
            }
        }, new Function2<KtFile, String, Boolean>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.FirKotlinToJvmBytecodeCompiler$runFrontend$sessionsWithSources$2
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(KtFile file, String moduleName) {
                Intrinsics.checkNotNullParameter(file, "file");
                Intrinsics.checkNotNullParameter(moduleName, "moduleName");
                return Boolean.valueOf(Intrinsics.areEqual(IsCommonSourceKt.getHmppModuleName(file), moduleName));
            }
        }, new Function1<List<? extends KtFile>, IncrementalCompilationContext>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.FirKotlinToJvmBytecodeCompiler$runFrontend$sessionsWithSources$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IncrementalCompilationContext invoke(List<? extends KtFile> it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return incrementalCompilationContextCreateContextForIncrementalCompilation;
            }
        });
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPrepareJvmSessions, 10));
        for (SessionWithSources sessionWithSources : listPrepareJvmSessions) {
            arrayList.add(FirUtilsKt.buildResolveAndCheckFirFromKtFiles(sessionWithSources.getSession(), sessionWithSources.component2(), baseDiagnosticsCollector));
        }
        ArrayList arrayList2 = arrayList;
        if (isHasErrors || baseDiagnosticsCollector.getHasErrors()) {
            return null;
        }
        return new FirResult(arrayList2);
    }

    private final GenerationState runBackend(final CompilationContext compilationContext, List<? extends KtFile> list, Fir2IrActualizedResult fir2IrActualizedResult, JvmGeneratorExtensions jvmGeneratorExtensions, BaseDiagnosticsCollector baseDiagnosticsCollector) {
        IrModuleFragment irModuleFragment = fir2IrActualizedResult.getIrModuleFragment();
        Fir2IrComponents components = fir2IrActualizedResult.getComponents();
        Fir2IrPluginContext pluginContext = fir2IrActualizedResult.getPluginContext();
        IrActualizedResult irActualizedResult = fir2IrActualizedResult.getIrActualizedResult();
        BindingContext bindingContext = new NoScopeRecordCliBindingTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        JvmIrCodegenFactory jvmIrCodegenFactory = new JvmIrCodegenFactory(compilationContext.getModuleConfiguration(), (PhaseConfig) compilationContext.getModuleConfiguration().get(CLIConfigurationKeys.PHASE_CONFIG), null, null, null, null, null, 124, null);
        AbstractProjectEnvironment projectEnvironment = compilationContext.getProjectEnvironment();
        Intrinsics.checkNotNull(projectEnvironment, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment");
        Project project = ((VfsBasedProjectEnvironment) projectEnvironment).getProject();
        ClassBuilderFactory BINARIES = ClassBuilderFactories.BINARIES;
        Intrinsics.checkNotNullExpressionValue(BINARIES, "BINARIES");
        GenerationState generationStateBuild = CliCompilerUtilsKt.withModule(new GenerationState.Builder(project, BINARIES, irModuleFragment.getDescriptor(), bindingContext, compilationContext.getModuleConfiguration()), compilationContext.getModule()).onIndependentPartCompilationEnd(CliCompilerUtilsKt.createOutputFilesFlushingCallbackIfPossible(compilationContext.getModuleConfiguration())).isIrBackend(true).jvmBackendClassResolver(new FirJvmBackendClassResolver(components)).diagnosticReporter(baseDiagnosticsCollector).build();
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        CommonCompilerPerformanceManager performanceManager = compilationContext.getPerformanceManager();
        if (performanceManager != null) {
            performanceManager.notifyIRLoweringStarted();
        }
        generationStateBuild.beforeCompile();
        generationStateBuild.oldBEInitTrace(list);
        jvmIrCodegenFactory.generateModuleInFrontendIRMode(generationStateBuild, irModuleFragment, components.getSymbolTable(), components.getIrProviders(), jvmGeneratorExtensions, new FirJvmBackendExtension(components, irActualizedResult), pluginContext, new Function0<Unit>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.FirKotlinToJvmBytecodeCompiler.runBackend.1
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
                CommonCompilerPerformanceManager performanceManager2 = compilationContext.getPerformanceManager();
                if (performanceManager2 != null) {
                    performanceManager2.notifyIRLoweringFinished();
                }
                CommonCompilerPerformanceManager performanceManager3 = compilationContext.getPerformanceManager();
                if (performanceManager3 != null) {
                    performanceManager3.notifyIRGenerationStarted();
                }
            }
        });
        CodegenFactory.INSTANCE.doCheckCancelled(generationStateBuild);
        generationStateBuild.getFactory().done();
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        AnalyzerWithCompilerReport.Companion companion = AnalyzerWithCompilerReport.INSTANCE;
        Diagnostics collectedExtraJvmDiagnostics = generationStateBuild.getCollectedExtraJvmDiagnostics();
        Diagnostics diagnostics = bindingContext.getDiagnostics();
        Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
        companion.reportDiagnostics(new FilteredJvmDiagnostics(collectedExtraJvmDiagnostics, diagnostics), compilationContext.getMessageCollector(), compilationContext.getRenderDiagnosticName());
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        return generationStateBuild;
    }

    /* JADX INFO: compiled from: FirKotlinToJvmBytecodeCompiler.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0016\b\u0002\u0018\u00002\u00020\u0001B{\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\u000e\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0005\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0005\u0012\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018¢\u0006\u0002\u0010\u001aR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001cR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0019\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001c¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/FirKotlinToJvmBytecodeCompiler$CompilationContext;", "", "module", "Lorg/jetbrains/kotlin/modules/Module;", "allSources", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "projectEnvironment", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "renderDiagnosticName", "", "moduleConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "performanceManager", "Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "targetIds", "Lorg/jetbrains/kotlin/modules/TargetId;", "incrementalComponents", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCompilationComponents;", "extensionRegistrars", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "irGenerationExtensions", "", "Lorg/jetbrains/kotlin/backend/common/extensions/IrGenerationExtension;", "(Lorg/jetbrains/kotlin/modules/Module;Ljava/util/List;Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;ZLorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;Ljava/util/List;Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCompilationComponents;Ljava/util/List;Ljava/util/Collection;)V", "getAllSources", "()Ljava/util/List;", "getExtensionRegistrars", "getIncrementalComponents", "()Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCompilationComponents;", "getIrGenerationExtensions", "()Ljava/util/Collection;", "getMessageCollector", "()Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "getModule", "()Lorg/jetbrains/kotlin/modules/Module;", "getModuleConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getPerformanceManager", "()Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "getProjectEnvironment", "()Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "getRenderDiagnosticName", "()Z", "getTargetIds", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CompilationContext {
        private final List<KtFile> allSources;
        private final List<FirExtensionRegistrar> extensionRegistrars;
        private final IncrementalCompilationComponents incrementalComponents;
        private final Collection<IrGenerationExtension> irGenerationExtensions;
        private final MessageCollector messageCollector;
        private final Module module;
        private final CompilerConfiguration moduleConfiguration;
        private final CommonCompilerPerformanceManager performanceManager;
        private final AbstractProjectEnvironment projectEnvironment;
        private final boolean renderDiagnosticName;
        private final List<TargetId> targetIds;

        /* JADX WARN: Multi-variable type inference failed */
        public CompilationContext(Module module, List<? extends KtFile> allSources, AbstractProjectEnvironment projectEnvironment, MessageCollector messageCollector, boolean z, CompilerConfiguration moduleConfiguration, CommonCompilerPerformanceManager commonCompilerPerformanceManager, List<TargetId> list, IncrementalCompilationComponents incrementalCompilationComponents, List<? extends FirExtensionRegistrar> extensionRegistrars, Collection<? extends IrGenerationExtension> irGenerationExtensions) {
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(allSources, "allSources");
            Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
            Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
            Intrinsics.checkNotNullParameter(moduleConfiguration, "moduleConfiguration");
            Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
            Intrinsics.checkNotNullParameter(irGenerationExtensions, "irGenerationExtensions");
            this.module = module;
            this.allSources = allSources;
            this.projectEnvironment = projectEnvironment;
            this.messageCollector = messageCollector;
            this.renderDiagnosticName = z;
            this.moduleConfiguration = moduleConfiguration;
            this.performanceManager = commonCompilerPerformanceManager;
            this.targetIds = list;
            this.incrementalComponents = incrementalCompilationComponents;
            this.extensionRegistrars = extensionRegistrars;
            this.irGenerationExtensions = irGenerationExtensions;
        }

        public final Module getModule() {
            return this.module;
        }

        public final List<KtFile> getAllSources() {
            return this.allSources;
        }

        public final AbstractProjectEnvironment getProjectEnvironment() {
            return this.projectEnvironment;
        }

        public final MessageCollector getMessageCollector() {
            return this.messageCollector;
        }

        public final boolean getRenderDiagnosticName() {
            return this.renderDiagnosticName;
        }

        public final CompilerConfiguration getModuleConfiguration() {
            return this.moduleConfiguration;
        }

        public final CommonCompilerPerformanceManager getPerformanceManager() {
            return this.performanceManager;
        }

        public final List<TargetId> getTargetIds() {
            return this.targetIds;
        }

        public final IncrementalCompilationComponents getIncrementalComponents() {
            return this.incrementalComponents;
        }

        public final List<FirExtensionRegistrar> getExtensionRegistrars() {
            return this.extensionRegistrars;
        }

        public final Collection<IrGenerationExtension> getIrGenerationExtensions() {
            return this.irGenerationExtensions;
        }
    }
}
