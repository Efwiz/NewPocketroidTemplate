package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import com.google.android.gms.cast.CastStatusCodes;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptDependency;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.impl.CompilationInternalsKt;
import kotlin.script.experimental.jvm.CompiledJvmScriptsCache;
import kotlin.script.experimental.jvm.JvmDependency;
import kotlin.script.experimental.jvm.JvmDependencyFromClassLoader;
import kotlin.script.experimental.jvm.JvmScriptCachingKt;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.KtPsiSourceFile;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt;
import org.jetbrains.kotlin.cli.common.GroupedKtSources;
import org.jetbrains.kotlin.cli.common.SessionWithSources;
import org.jetbrains.kotlin.cli.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.config.ContentRoot;
import org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporter;
import org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporterKt;
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.cli.jvm.compiler.CliCompilerUtilsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.JvmPackagePartProvider;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.NoScopeRecordCliBindingTrace;
import org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM;
import org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironmentKt;
import org.jetbrains.kotlin.cli.jvm.compiler.pipeline.CompilerPipelineKt;
import org.jetbrains.kotlin.cli.jvm.compiler.pipeline.ModuleCompilerEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.pipeline.ModuleCompilerInput;
import org.jetbrains.kotlin.cli.jvm.compiler.pipeline.ModuleCompilerOutput;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmModulePathRoot;
import org.jetbrains.kotlin.codegen.ClassBuilderFactories;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.DefaultCodegenFactory;
import org.jetbrains.kotlin.codegen.KotlinCodegenFacade;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporterFactory;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.diagnostics.impl.PendingDiagnosticsCollectorWithSuppress;
import org.jetbrains.kotlin.fir.DependencyListForCliModule;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.pipeline.AnalyseKt;
import org.jetbrains.kotlin.fir.pipeline.FirResult;
import org.jetbrains.kotlin.fir.pipeline.FirUtilsKt;
import org.jetbrains.kotlin.fir.pipeline.ModuleCompilerAnalyzedOutput;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.session.IncrementalCompilationContext;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.CommonPlatforms;
import org.jetbrains.kotlin.platform.jvm.JvmPlatforms;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.extensions.PackageFragmentProviderExtension;
import org.jetbrains.kotlin.scripting.compiler.plugin.dependencies.ScriptsCompilationDependencies;
import org.jetbrains.kotlin.scripting.compiler.plugin.services.FirScriptDefinitionProviderService;
import org.jetbrains.kotlin.scripting.compiler.plugin.services.FirScriptDefinitionProviderServiceKt;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.resolve.RefineCompilationConfigurationKt;
import org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper;
import org.jetbrains.kotlin.scripting.resolve.VirtualFileScriptSource;
import org.jetbrains.kotlin.utils.PathUtil;
import org.jetbrains.kotlin.utils.SortUtilsKt;

/* JADX INFO: compiled from: ScriptJvmCompilerImpls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a.\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001aV\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00142\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u0018H\u0002\u001aV\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00130\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00142\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u0018H\u0002\u001a.\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00142\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0010\u001a\u00020\u001fH\u0002\u001a\u0018\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a:\u0010#\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0%H\u0002¨\u0006&"}, d2 = {"analyze", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "sourceFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "compileImpl", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/CompiledScript;", "script", "Lkotlin/script/experimental/api/SourceCode;", "context", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/SharedScriptCompilationContext;", "initialConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "messageCollector", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ScriptDiagnosticsMessageCollector;", "doCompile", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledScript;", "", "sourceDependencies", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/dependencies/ScriptsCompilationDependencies$SourceDependencies;", "getScriptConfiguration", "Lkotlin/Function1;", "doCompileWithK2", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "analysisResult", "kotlinCompilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "registerPackageFragmentProvidersIfNeeded", "", "scriptCompilationConfiguration", "withScriptCompilationCache", "body", "Lkotlin/Function0;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptJvmCompilerImplsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final ResultWithDiagnostics<CompiledScript> withScriptCompilationCache(SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector, Function0<? extends ResultWithDiagnostics<? extends CompiledScript>> function0) {
        ResultWithDiagnostics.Success successAsSuccess;
        ScriptingHostConfiguration scriptingHostConfiguration = (ScriptingHostConfiguration) scriptCompilationConfiguration.get(ScriptCompilationKt.getHostConfiguration(ScriptCompilationConfiguration.INSTANCE));
        CompiledJvmScriptsCache compiledJvmScriptsCache = scriptingHostConfiguration != null ? (CompiledJvmScriptsCache) scriptingHostConfiguration.get(JvmScriptCachingKt.getCompilationCache(JvmScriptingHostConfigurationKt.getJvm(ScriptingHostConfiguration.INSTANCE))) : null;
        CompiledScript compiledScript = compiledJvmScriptsCache != null ? compiledJvmScriptsCache.get(sourceCode, scriptCompilationConfiguration) : null;
        if (compiledScript != null && (successAsSuccess = ErrorHandlingKt.asSuccess(compiledScript, scriptDiagnosticsMessageCollector.getDiagnostics())) != null) {
            return successAsSuccess;
        }
        ResultWithDiagnostics<CompiledScript> resultWithDiagnostics = (ResultWithDiagnostics) function0.invoke();
        if (compiledJvmScriptsCache == null || !(resultWithDiagnostics instanceof ResultWithDiagnostics.Success)) {
            return resultWithDiagnostics;
        }
        compiledJvmScriptsCache.store((CompiledScript) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue(), sourceCode, scriptCompilationConfiguration);
        return resultWithDiagnostics;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ResultWithDiagnostics<CompiledScript> compileImpl(SourceCode sourceCode, final SharedScriptCompilationContext sharedScriptCompilationContext, ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        ResultWithDiagnostics<KtFile> scriptKtFile = JvmCompilationUtilKt.getScriptKtFile(sourceCode, sharedScriptCompilationContext.getBaseScriptCompilationConfiguration(), sharedScriptCompilationContext.getEnvironment().getProject(), scriptDiagnosticsMessageCollector);
        if (scriptKtFile instanceof ResultWithDiagnostics.Success) {
            KtFile ktFile = (KtFile) ((ResultWithDiagnostics.Success) scriptKtFile).getValue();
            if (scriptDiagnosticsMessageCollector.getHasErrors()) {
                return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, new ScriptDiagnostic[0]);
            }
            Pair<List<KtFile>, List<ScriptsCompilationDependencies.SourceDependencies>> pairCollectRefinedSourcesAndUpdateEnvironment = CompilationContextKt.collectRefinedSourcesAndUpdateEnvironment(sharedScriptCompilationContext, ktFile, scriptCompilationConfiguration, scriptDiagnosticsMessageCollector);
            List<KtFile> listComponent1 = pairCollectRefinedSourcesAndUpdateEnvironment.component1();
            List<ScriptsCompilationDependencies.SourceDependencies> listComponent2 = pairCollectRefinedSourcesAndUpdateEnvironment.component2();
            UtilsKt.checkKotlinPackageUsageForPsi(sharedScriptCompilationContext.getEnvironment().getConfiguration(), listComponent1, scriptDiagnosticsMessageCollector);
            if (!scriptDiagnosticsMessageCollector.getHasErrors()) {
                List<ScriptsCompilationDependencies.SourceDependencies> list = listComponent2;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        if (((ScriptsCompilationDependencies.SourceDependencies) it.next()).getSourceDependencies() instanceof ResultWithDiagnostics.Failure) {
                        }
                    }
                }
                final ScriptDependenciesProvider companion = ScriptDependenciesProvider.INSTANCE.getInstance(sharedScriptCompilationContext.getEnvironment().getProject());
                Function1<KtFile, ScriptCompilationConfiguration> function1 = new Function1<KtFile, ScriptCompilationConfiguration>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptJvmCompilerImplsKt$compileImpl$getScriptConfiguration$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final ScriptCompilationConfiguration invoke(KtFile ktFile2) {
                        ScriptCompilationConfiguration baseScriptCompilationConfiguration;
                        ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> scriptConfigurationResult;
                        ScriptCompilationConfigurationWrapper scriptCompilationConfigurationWrapper;
                        Intrinsics.checkNotNullParameter(ktFile2, "ktFile");
                        ScriptDependenciesProvider scriptDependenciesProvider = companion;
                        if (scriptDependenciesProvider == null || (scriptConfigurationResult = scriptDependenciesProvider.getScriptConfigurationResult(ktFile2, sharedScriptCompilationContext.getBaseScriptCompilationConfiguration())) == null || (scriptCompilationConfigurationWrapper = (ScriptCompilationConfigurationWrapper) ErrorHandlingKt.valueOrNull(scriptConfigurationResult)) == null || (baseScriptCompilationConfiguration = scriptCompilationConfigurationWrapper.getConfiguration()) == null) {
                            baseScriptCompilationConfiguration = sharedScriptCompilationContext.getBaseScriptCompilationConfiguration();
                        }
                        final SharedScriptCompilationContext sharedScriptCompilationContext2 = sharedScriptCompilationContext;
                        return ScriptCompilationKt.with(baseScriptCompilationConfiguration, new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptJvmCompilerImplsKt$compileImpl$getScriptConfiguration$1.1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                                invoke2(builder);
                                return Unit.INSTANCE;
                            }

                            /* JADX WARN: Code duplicated, block: B:26:0x00a9  */
                            /* JADX WARN: Code duplicated, block: B:28:0x00ad  */
                            /* JADX WARN: Code duplicated, block: B:29:0x00b4  */
                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(ScriptCompilationConfiguration.Builder with) {
                                HashSet hashSet;
                                File file;
                                List<File> listEmptyList;
                                Intrinsics.checkNotNullParameter(with, "$this$with");
                                ScriptCompilationConfiguration.Builder builder = with;
                                with.invoke(CompilationInternalsKt.get_languageVersion(builder), CommonConfigurationKeysKt.getLanguageVersionSettings(sharedScriptCompilationContext2.getEnvironment().getConfiguration()).getLanguageVersion().getVersionString());
                                List<ScriptDependency> list2 = (List) with.get(ScriptCompilationKt.getDependencies(builder));
                                if (list2 != null) {
                                    HashSet hashSet2 = new HashSet();
                                    for (ScriptDependency scriptDependency : list2) {
                                        JvmDependency jvmDependency = scriptDependency instanceof JvmDependency ? (JvmDependency) scriptDependency : null;
                                        if (jvmDependency == null || (listEmptyList = jvmDependency.getClasspath()) == null) {
                                            listEmptyList = CollectionsKt.emptyList();
                                        }
                                        CollectionsKt.addAll(hashSet2, listEmptyList);
                                    }
                                    hashSet = hashSet2;
                                } else {
                                    hashSet = null;
                                }
                                List<ContentRoot> list3 = sharedScriptCompilationContext2.getEnvironment().getConfiguration().getList(CLIConfigurationKeys.CONTENT_ROOTS);
                                Intrinsics.checkNotNullExpressionValue(list3, "getList(...)");
                                ArrayList arrayList = new ArrayList();
                                for (ContentRoot contentRoot : list3) {
                                    if (contentRoot instanceof JvmClasspathRoot) {
                                        JvmClasspathRoot jvmClasspathRoot = (JvmClasspathRoot) contentRoot;
                                        if (!jvmClasspathRoot.isSdkRoot()) {
                                            file = jvmClasspathRoot.getFile();
                                        } else if (contentRoot instanceof JvmModulePathRoot) {
                                            file = ((JvmModulePathRoot) contentRoot).getFile();
                                        } else {
                                            file = null;
                                        }
                                    } else if (contentRoot instanceof JvmModulePathRoot) {
                                        file = ((JvmModulePathRoot) contentRoot).getFile();
                                    } else {
                                        file = null;
                                    }
                                    if (file != null) {
                                        arrayList.add(file);
                                    }
                                }
                                ArrayList arrayList2 = arrayList;
                                HashSet hashSet3 = hashSet;
                                if (hashSet3 == null || hashSet3.isEmpty()) {
                                    with.append(ScriptCompilationKt.getDependencies(builder), new JvmDependency(arrayList2));
                                    return;
                                }
                                ArrayList arrayList3 = new ArrayList();
                                for (Object obj : arrayList2) {
                                    if (!hashSet.contains((File) obj)) {
                                        arrayList3.add(obj);
                                    }
                                }
                                ArrayList arrayList4 = arrayList3;
                                if (arrayList4.isEmpty()) {
                                    return;
                                }
                                with.append(ScriptCompilationKt.getDependencies(builder), new JvmDependency(arrayList4));
                            }
                        });
                    }
                };
                if (sharedScriptCompilationContext.getEnvironment().getConfiguration().getBoolean(CommonConfigurationKeys.USE_FIR)) {
                    return doCompileWithK2(sharedScriptCompilationContext, sourceCode, listComponent1, listComponent2, scriptDiagnosticsMessageCollector, function1);
                }
                return doCompile(sharedScriptCompilationContext, sourceCode, listComponent1, listComponent2, scriptDiagnosticsMessageCollector, function1);
            }
            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, new ScriptDiagnostic[0]);
        }
        if (scriptKtFile instanceof ResultWithDiagnostics.Failure) {
            return (ResultWithDiagnostics.Failure) scriptKtFile;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final void registerPackageFragmentProvidersIfNeeded(ScriptCompilationConfiguration scriptCompilationConfiguration, KotlinCoreEnvironment environment) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        Intrinsics.checkNotNullParameter(environment, "environment");
        List list = (List) scriptCompilationConfiguration.get(ScriptCompilationKt.getDependencies(ScriptCompilationConfiguration.INSTANCE));
        if (list == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof JvmDependencyFromClassLoader) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            arrayList2 = null;
        }
        if (arrayList2 == null) {
            return;
        }
        Object[] extensions = environment.getProject().getExtensionArea().getExtensionPoint(PackageFragmentProviderExtension.INSTANCE.getExtensionPointName()).getExtensions();
        Intrinsics.checkNotNullExpressionValue(extensions, "getExtensions(...)");
        for (Object obj2 : extensions) {
            PackageFragmentProviderExtension packageFragmentProviderExtension = (PackageFragmentProviderExtension) obj2;
            if ((packageFragmentProviderExtension instanceof PackageFragmentFromClassLoaderProviderExtension) && Intrinsics.areEqual(((PackageFragmentFromClassLoaderProviderExtension) packageFragmentProviderExtension).getScriptCompilationConfiguration().get(ScriptCompilationKt.getDependencies(ScriptCompilationConfiguration.INSTANCE)), list)) {
                return;
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            PackageFragmentProviderExtension.INSTANCE.registerExtension(environment.getProject(), new PackageFragmentFromClassLoaderProviderExtension(((JvmDependencyFromClassLoader) it.next()).getClassLoaderGetter(), scriptCompilationConfiguration, environment.getConfiguration()));
        }
    }

    private static final ResultWithDiagnostics<KJvmCompiledScript> doCompile(SharedScriptCompilationContext sharedScriptCompilationContext, SourceCode sourceCode, List<? extends KtFile> list, List<ScriptsCompilationDependencies.SourceDependencies> list2, ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector, Function1<? super KtFile, ? extends ScriptCompilationConfiguration> function1) {
        registerPackageFragmentProvidersIfNeeded(function1.invoke(CollectionsKt.first((List) list)), sharedScriptCompilationContext.getEnvironment());
        AnalysisResult analysisResultAnalyze = analyze(list, sharedScriptCompilationContext.getEnvironment());
        if (!analysisResultAnalyze.getShouldGenerateCode()) {
            return ErrorReportingKt.failure(sourceCode, scriptDiagnosticsMessageCollector, "no code to generate");
        }
        if (analysisResultAnalyze.isError() || scriptDiagnosticsMessageCollector.getHasErrors()) {
            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, new ScriptDiagnostic[0]);
        }
        GenerationState generationStateGenerate = generate(analysisResultAnalyze, list, sharedScriptCompilationContext.getEnvironment().getConfiguration(), scriptDiagnosticsMessageCollector);
        if (scriptDiagnosticsMessageCollector.getHasErrors()) {
            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, new ScriptDiagnostic[0]);
        }
        ResultWithDiagnostics<KJvmCompiledScript> resultWithDiagnosticsMakeCompiledScript = JvmCompilationUtilKt.makeCompiledScript(generationStateGenerate, sourceCode, (KtFile) CollectionsKt.first((List) list), list2, function1);
        if (resultWithDiagnosticsMakeCompiledScript instanceof ResultWithDiagnostics.Success) {
            return ErrorHandlingKt.plus(resultWithDiagnosticsMakeCompiledScript.getReports(), new ResultWithDiagnostics.Success((KJvmCompiledScript) ((ResultWithDiagnostics.Success) resultWithDiagnosticsMakeCompiledScript).getValue(), scriptDiagnosticsMessageCollector.getDiagnostics()));
        }
        if (resultWithDiagnosticsMakeCompiledScript instanceof ResultWithDiagnostics.Failure) {
            return resultWithDiagnosticsMakeCompiledScript;
        }
        throw new NoWhenBranchMatchedException();
    }

    private static final AnalysisResult analyze(final Collection<? extends KtFile> collection, final KotlinCoreEnvironment kotlinCoreEnvironment) {
        Object obj = kotlinCoreEnvironment.getConfiguration().get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNull(obj);
        AnalyzerWithCompilerReport analyzerWithCompilerReport = new AnalyzerWithCompilerReport((MessageCollector) obj, CommonConfigurationKeysKt.getLanguageVersionSettings(kotlinCoreEnvironment.getConfiguration()), kotlinCoreEnvironment.getConfiguration().getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
        analyzerWithCompilerReport.analyzeAndReport(collection, new Function0<AnalysisResult>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptJvmCompilerImplsKt.analyze.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final AnalysisResult invoke() {
                return TopDownAnalyzerFacadeForJVM.analyzeFilesWithJavaIntegration$default(kotlinCoreEnvironment.getProject(), collection, new NoScopeRecordCliBindingTrace(), kotlinCoreEnvironment.getConfiguration(), new C03351(kotlinCoreEnvironment), null, null, null, null, null, null, CastStatusCodes.DEVICE_CONNECTION_SUSPENDED, null);
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptJvmCompilerImplsKt$analyze$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: ScriptJvmCompilerImpls.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class C03351 extends FunctionReference implements Function1<GlobalSearchScope, JvmPackagePartProvider> {
                C03351(Object obj) {
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
        return analyzerWithCompilerReport.getAnalysisResult();
    }

    private static final GenerationState generate(AnalysisResult analysisResult, List<? extends KtFile> list, CompilerConfiguration compilerConfiguration, MessageCollector messageCollector) {
        DefaultCodegenFactory jvmIrCodegenFactory;
        BaseDiagnosticsCollector baseDiagnosticsCollectorCreateReporter$default = DiagnosticReporterFactory.createReporter$default(DiagnosticReporterFactory.INSTANCE, null, false, 3, null);
        Project project = ((KtFile) CollectionsKt.first((List) list)).getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        ClassBuilderFactory BINARIES = ClassBuilderFactories.BINARIES;
        Intrinsics.checkNotNullExpressionValue(BINARIES, "BINARIES");
        GenerationState generationStateBuild = new GenerationState.Builder(project, BINARIES, analysisResult.getModuleDescriptor(), analysisResult.getBindingContext(), compilerConfiguration).diagnosticReporter(baseDiagnosticsCollectorCreateReporter$default).build();
        List<? extends KtFile> list2 = list;
        if (compilerConfiguration.getBoolean(JVMConfigurationKeys.IR)) {
            jvmIrCodegenFactory = new JvmIrCodegenFactory(compilerConfiguration, (PhaseConfig) compilerConfiguration.get(CLIConfigurationKeys.PHASE_CONFIG), null, null, null, null, null, 124, null);
        } else {
            jvmIrCodegenFactory = DefaultCodegenFactory.INSTANCE;
        }
        KotlinCodegenFacade.compileCorrectFiles(list2, generationStateBuild, jvmIrCodegenFactory);
        FirDiagnosticsCompilerResultsReporter.INSTANCE.reportToMessageCollector(baseDiagnosticsCollectorCreateReporter$default, messageCollector, compilerConfiguration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
        return generationStateBuild;
    }

    /* JADX WARN: Code duplicated, block: B:97:0x02a1  */
    private static final ResultWithDiagnostics<KJvmCompiledScript> doCompileWithK2(SharedScriptCompilationContext sharedScriptCompilationContext, SourceCode sourceCode, List<? extends KtFile> list, List<ScriptsCompilationDependencies.SourceDependencies> list2, ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector, Function1<? super KtFile, ? extends ScriptCompilationConfiguration> function1) {
        AbstractProjectFileSearchScope abstractProjectFileSearchScopeMinus;
        FirScriptDefinitionProviderService firScriptDefinitionProviderService;
        List<FirFile> list3;
        Iterator it;
        List listEmptyList;
        List<SourceCode> list4;
        List<FirFile> list5;
        Iterator it2;
        FirFile firFile;
        VirtualFile virtualFile;
        Object next;
        ScriptCompilationConfigurationWrapper scriptConfiguration;
        List<? extends KtFile> list6 = list;
        Iterator<T> it3 = list6.iterator();
        boolean isHasErrors = false;
        while (it3.hasNext()) {
            isHasErrors |= AnalyzerWithCompilerReport.INSTANCE.reportSyntaxErrors((KtFile) it3.next(), scriptDiagnosticsMessageCollector).getIsHasErrors();
        }
        if (isHasErrors) {
            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, new ScriptDiagnostic[0]);
        }
        registerPackageFragmentProvidersIfNeeded(function1.invoke(CollectionsKt.first((List) list)), sharedScriptCompilationContext.getEnvironment());
        CompilerConfiguration configuration = sharedScriptCompilationContext.getEnvironment().getConfiguration();
        String str = (String) configuration.get(CommonConfigurationKeys.MODULE_NAME);
        if (str == null) {
            str = "main";
        }
        TargetId targetId = new TargetId(str, ModuleXmlParser.TYPE_PRODUCTION);
        boolean z = configuration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME);
        PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default = DiagnosticReporterFactory.createPendingReporter$default(DiagnosticReporterFactory.INSTANCE, null, 1, null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list6, 10));
        Iterator<T> it4 = list6.iterator();
        while (it4.hasNext()) {
            arrayList.add(new KtPsiSourceFile((KtFile) it4.next()));
        }
        final ModuleCompilerInput moduleCompilerInput = new ModuleCompilerInput(targetId, new GroupedKtSources(arrayList, SetsKt.emptySet(), MapsKt.emptyMap()), CommonPlatforms.INSTANCE.getDefaultCommonPlatform(), JvmPlatforms.INSTANCE.getUnspecifiedJvmPlatform(), configuration, null, 32, null);
        final AbstractProjectEnvironment abstractProjectEnvironment = VfsBasedProjectEnvironmentKt.toAbstractProjectEnvironment(sharedScriptCompilationContext.getEnvironment());
        PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppress = pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default;
        final ModuleCompilerEnvironment moduleCompilerEnvironment = new ModuleCompilerEnvironment(abstractProjectEnvironment, pendingDiagnosticsCollectorWithSuppress);
        AbstractProjectFileSearchScope searchScopeForProjectLibraries = abstractProjectEnvironment.getSearchScopeForProjectLibraries();
        final AbstractProjectFileSearchScope abstractProjectFileSearchScopeCreateIncrementalCompilationScope = CompilerPipelineKt.createIncrementalCompilationScope(moduleCompilerInput.getConfiguration(), abstractProjectEnvironment, null);
        if (abstractProjectFileSearchScopeCreateIncrementalCompilationScope != null) {
            abstractProjectFileSearchScopeMinus = searchScopeForProjectLibraries.minus(abstractProjectFileSearchScopeCreateIncrementalCompilationScope);
        } else {
            abstractProjectFileSearchScopeMinus = searchScopeForProjectLibraries;
            abstractProjectFileSearchScopeCreateIncrementalCompilationScope = null;
        }
        VfsBasedProjectEnvironment vfsBasedProjectEnvironment = abstractProjectEnvironment instanceof VfsBasedProjectEnvironment ? (VfsBasedProjectEnvironment) abstractProjectEnvironment : null;
        List<FirExtensionRegistrar> instances = vfsBasedProjectEnvironment != null ? FirExtensionRegistrar.INSTANCE.getInstances(vfsBasedProjectEnvironment.getProject()) : null;
        if (instances == null) {
            instances = CollectionsKt.emptyList();
        }
        List<FirExtensionRegistrar> list7 = instances;
        String name2 = targetId.getName();
        DependencyListForCliModule dependencyListForCliModuleCreateLibraryListForJvm = CliCompilerUtilsKt.createLibraryListForJvm(name2, configuration, CollectionsKt.emptyList());
        Name nameSpecial = Name.special("<" + name2 + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        FirSession session = ((SessionWithSources) CollectionsKt.single(FirSessionConstructionUtilsKt.prepareJvmSessions(list, configuration, abstractProjectEnvironment, nameSpecial, list7, abstractProjectFileSearchScopeMinus, dependencyListForCliModuleCreateLibraryListForJvm, FirSessionConstructionUtilsKt.isCommonSourceForPsi(), FirSessionConstructionUtilsKt.getFileBelongsToModuleForPsi(), new Function1<List<? extends KtFile>, IncrementalCompilationContext>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptJvmCompilerImplsKt$doCompileWithK2$session$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IncrementalCompilationContext invoke(List<? extends KtFile> files) {
                Intrinsics.checkNotNullParameter(files, "files");
                CompilerConfiguration configuration2 = moduleCompilerInput.getConfiguration();
                AbstractProjectEnvironment abstractProjectEnvironment2 = abstractProjectEnvironment;
                AbstractProjectEnvironment projectEnvironment = moduleCompilerEnvironment.getProjectEnvironment();
                List<? extends KtFile> list8 = files;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list8, 10));
                Iterator<T> it5 = list8.iterator();
                while (it5.hasNext()) {
                    arrayList2.add(new KtPsiSourceFile((KtFile) it5.next()));
                }
                return CompilerPipelineKt.createContextForIncrementalCompilation(configuration2, abstractProjectEnvironment2, AbstractProjectEnvironment.getSearchScopeBySourceFiles$default(projectEnvironment, arrayList2, false, 2, null), CollectionsKt.emptyList(), abstractProjectFileSearchScopeCreateIncrementalCompilationScope);
            }
        }))).getSession();
        FirScriptDefinitionProviderService scriptDefinitionProviderService = FirScriptDefinitionProviderServiceKt.getScriptDefinitionProviderService(session);
        if (scriptDefinitionProviderService != null) {
            scriptDefinitionProviderService.setDefinitionProvider(ScriptDefinitionProvider.INSTANCE.getInstance(sharedScriptCompilationContext.getEnvironment().getProject()));
            scriptDefinitionProviderService.setConfigurationProvider(ScriptDependenciesProvider.INSTANCE.getInstance(sharedScriptCompilationContext.getEnvironment().getProject()));
        }
        List<FirFile> listBuildFirFromKtFiles = FirUtilsKt.buildFirFromKtFiles(session, list);
        if (scriptDefinitionProviderService != null) {
            List<FirFile> list8 = listBuildFirFromKtFiles;
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list8, 10)), 16));
            Iterator it5 = list8.iterator();
            while (it5.hasNext()) {
                Object next2 = it5.next();
                LinkedHashMap linkedHashMap2 = linkedHashMap;
                KtSourceFile sourceFile = ((FirFile) next2).getSourceFile();
                KtPsiSourceFile ktPsiSourceFile = sourceFile instanceof KtPsiSourceFile ? (KtPsiSourceFile) sourceFile : null;
                PsiFile psiFile = ktPsiSourceFile != null ? ktPsiSourceFile.getPsiFile() : null;
                KtFile ktFile = psiFile instanceof KtFile ? (KtFile) psiFile : null;
                if (ktFile == null) {
                    firScriptDefinitionProviderService = scriptDefinitionProviderService;
                    list3 = list8;
                    it = it5;
                    listEmptyList = null;
                } else {
                    ScriptDependenciesProvider configurationProvider = scriptDefinitionProviderService.getConfigurationProvider();
                    ScriptCompilationConfiguration configuration2 = (configurationProvider == null || (scriptConfiguration = configurationProvider.getScriptConfiguration(ktFile)) == null) ? null : scriptConfiguration.getConfiguration();
                    if (configuration2 == null || (list4 = (List) configuration2.get(RefineCompilationConfigurationKt.getResolvedImportScripts(ScriptCompilationConfiguration.INSTANCE))) == null) {
                        firScriptDefinitionProviderService = scriptDefinitionProviderService;
                        list3 = list8;
                        it = it5;
                        listEmptyList = null;
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        for (SourceCode sourceCode2 : list4) {
                            FirScriptDefinitionProviderService firScriptDefinitionProviderService2 = scriptDefinitionProviderService;
                            VirtualFileScriptSource virtualFileScriptSource = sourceCode2 instanceof VirtualFileScriptSource ? (VirtualFileScriptSource) sourceCode2 : null;
                            if (virtualFileScriptSource == null || (virtualFile = virtualFileScriptSource.getVirtualFile()) == null) {
                                list5 = list8;
                                it2 = it5;
                                firFile = null;
                            } else {
                                Iterator<T> it6 = list8.iterator();
                                while (true) {
                                    if (!it6.hasNext()) {
                                        list5 = list8;
                                        it2 = it5;
                                        next = null;
                                        break;
                                    }
                                    next = it6.next();
                                    list5 = list8;
                                    KtSourceFile sourceFile2 = ((FirFile) next).getSourceFile();
                                    it2 = it5;
                                    KtPsiSourceFile ktPsiSourceFile2 = sourceFile2 instanceof KtPsiSourceFile ? (KtPsiSourceFile) sourceFile2 : null;
                                    PsiFile psiFile2 = ktPsiSourceFile2 != null ? ktPsiSourceFile2.getPsiFile() : null;
                                    KtFile ktFile2 = psiFile2 instanceof KtFile ? (KtFile) psiFile2 : null;
                                    if (Intrinsics.areEqual(ktFile2 != null ? ktFile2.getVirtualFile() : null, virtualFile)) {
                                        break;
                                    }
                                    list8 = list5;
                                    it5 = it2;
                                }
                                firFile = (FirFile) next;
                            }
                            if (firFile != null) {
                                arrayList2.add(firFile);
                            }
                            list8 = list5;
                            scriptDefinitionProviderService = firScriptDefinitionProviderService2;
                            it5 = it2;
                        }
                        firScriptDefinitionProviderService = scriptDefinitionProviderService;
                        list3 = list8;
                        it = it5;
                        listEmptyList = arrayList2;
                    }
                }
                if (listEmptyList == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                linkedHashMap2.put(next2, listEmptyList);
                list8 = list3;
                scriptDefinitionProviderService = firScriptDefinitionProviderService;
                it5 = it;
            }
            final LinkedHashMap linkedHashMap3 = linkedHashMap;
            try {
                listBuildFirFromKtFiles = CollectionsKt.reversed(SortUtilsKt.topologicalSort(listBuildFirFromKtFiles, new Function1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptJvmCompilerImplsKt$doCompileWithK2$orderedRawFir$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Void invoke(FirFile it7) throws ScriptJvmCompilerImplsKt$doCompileWithK2$orderedRawFir$CycleDetected {
                        Intrinsics.checkNotNullParameter(it7, "it");
                        throw new ScriptJvmCompilerImplsKt$doCompileWithK2$orderedRawFir$CycleDetected(it7);
                    }
                }, new Function1<FirFile, Iterable<? extends FirFile>>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptJvmCompilerImplsKt$doCompileWithK2$orderedRawFir$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Iterable<FirFile> invoke(FirFile topologicalSort) {
                        Intrinsics.checkNotNullParameter(topologicalSort, "$this$topologicalSort");
                        List<FirFile> listEmptyList2 = linkedHashMap3.get(topologicalSort);
                        if (listEmptyList2 == null) {
                            listEmptyList2 = CollectionsKt.emptyList();
                        }
                        return listEmptyList2;
                    }
                }));
            } catch (ScriptJvmCompilerImplsKt$doCompileWithK2$orderedRawFir$CycleDetected e) {
                ScriptDiagnostic[] scriptDiagnosticArr = new ScriptDiagnostic[1];
                String str2 = "Unable to handle recursive script dependencies, cycle detected on file " + e.getNode().getName();
                KtSourceFile sourceFile3 = e.getNode().getSourceFile();
                scriptDiagnosticArr[0] = new ScriptDiagnostic(-1, str2, null, sourceFile3 != null ? sourceFile3.getPath() : null, null, null, 52, null);
                return new ResultWithDiagnostics.Failure(scriptDiagnosticArr);
            }
        }
        Pair<ScopeSession, List<FirFile>> pairRunResolution = AnalyseKt.runResolution(session, listBuildFirFromKtFiles);
        ScopeSession scopeSessionComponent1 = pairRunResolution.component1();
        List<FirFile> listComponent2 = pairRunResolution.component2();
        AnalyseKt.runCheckers(session, scopeSessionComponent1, listComponent2, pendingDiagnosticsCollectorWithSuppress);
        FirResult firResult = new FirResult(CollectionsKt.listOf(new ModuleCompilerAnalyzedOutput(session, scopeSessionComponent1, listComponent2)));
        if (pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default.getHasErrors()) {
            FirDiagnosticsCompilerResultsReporterKt.reportToMessageCollector(pendingDiagnosticsCollectorWithSuppress, scriptDiagnosticsMessageCollector, z);
            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, new ScriptDiagnostic[0]);
        }
        ModuleCompilerOutput moduleCompilerOutputGenerateCodeFromIr = CompilerPipelineKt.generateCodeFromIr(CompilerPipelineKt.convertAnalyzedFirToIr(moduleCompilerInput, firResult, moduleCompilerEnvironment), moduleCompilerEnvironment, null);
        FirDiagnosticsCompilerResultsReporterKt.reportToMessageCollector(pendingDiagnosticsCollectorWithSuppress, scriptDiagnosticsMessageCollector, z);
        if (pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default.getHasErrors()) {
            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, new ScriptDiagnostic[0]);
        }
        ResultWithDiagnostics<KJvmCompiledScript> resultWithDiagnosticsMakeCompiledScript = JvmCompilationUtilKt.makeCompiledScript(moduleCompilerOutputGenerateCodeFromIr.getGenerationState(), sourceCode, (KtFile) CollectionsKt.first((List) list), list2, function1);
        if (resultWithDiagnosticsMakeCompiledScript instanceof ResultWithDiagnostics.Success) {
            return ErrorHandlingKt.plus(resultWithDiagnosticsMakeCompiledScript.getReports(), new ResultWithDiagnostics.Success((KJvmCompiledScript) ((ResultWithDiagnostics.Success) resultWithDiagnosticsMakeCompiledScript).getValue(), scriptDiagnosticsMessageCollector.getDiagnostics()));
        }
        if (resultWithDiagnosticsMakeCompiledScript instanceof ResultWithDiagnostics.Failure) {
            return resultWithDiagnosticsMakeCompiledScript;
        }
        throw new NoWhenBranchMatchedException();
    }
}
