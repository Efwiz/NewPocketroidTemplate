package org.jetbrains.kotlin.cli.js;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.backend.common.output.OutputFileCollection;
import org.jetbrains.kotlin.cli.common.CLICompiler;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArgumentsKt;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.cli.common.config.ContentRootsKt;
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageUtil;
import org.jetbrains.kotlin.cli.common.output.OutputUtilsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer;
import org.jetbrains.kotlin.incremental.js.TranslationResultValue;
import org.jetbrains.kotlin.js.analyze.TopDownAnalyzerFacadeForJS;
import org.jetbrains.kotlin.js.analyzer.JsAnalysisResult;
import org.jetbrains.kotlin.js.config.EcmaVersion;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.config.SourceMapSourceEmbedding;
import org.jetbrains.kotlin.js.facade.K2JSTranslator;
import org.jetbrains.kotlin.js.facade.MainCallParameters;
import org.jetbrains.kotlin.js.facade.TranslationResult;
import org.jetbrains.kotlin.js.facade.TranslationUnit;
import org.jetbrains.kotlin.js.facade.exceptions.TranslationException;
import org.jetbrains.kotlin.js.sourceMap.SourceFilePathResolver;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.CompilerEnvironment;
import org.jetbrains.kotlin.serialization.js.ModuleKind;
import org.jetbrains.kotlin.utils.ExceptionUtilsKt;
import org.jetbrains.kotlin.utils.JsMetadataVersion;
import org.jetbrains.kotlin.utils.KotlinPaths;
import org.jetbrains.kotlin.utils.PathUtil;
import org.jetbrains.kotlin.utils.StringsKt;

/* JADX INFO: loaded from: classes3.dex */
public class K2JSCompiler extends CLICompiler<K2JSCompilerArguments> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Map<String, ModuleKind> moduleKindMap;
    private static final Map<String, SourceMapSourceEmbedding> sourceMapContentEmbeddingMap;
    private K2JsIrCompiler irCompiler = null;
    private final K2JSCompilerPerformanceManager performanceManager = new K2JSCompilerPerformanceManager();

    /* JADX WARN: Code duplicated, block: B:10:0x0018 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:19:0x002c A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:32:0x0058  */
    /* JADX WARN: Code duplicated, block: B:33:0x005d  */
    /* JADX WARN: Code duplicated, block: B:40:0x0080  */
    /* JADX WARN: Code duplicated, block: B:41:0x0085  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 42 && i != 43 && i != 45) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 31:
                                case 32:
                                case 33:
                                case 34:
                                case 35:
                                case 36:
                                case 37:
                                case 38:
                                case 39:
                                case 40:
                                    break;
                                default:
                                    str = "@NotNull method %s.%s must not return null";
                                    break;
                            }
                        case 10:
                        case 11:
                        case 12:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 42 && i != 43 && i != 45) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 31:
                                case 32:
                                case 33:
                                case 34:
                                case 35:
                                case 36:
                                case 37:
                                case 38:
                                case 39:
                                case 40:
                                    break;
                                default:
                                    i2 = 2;
                                    break;
                            }
                        case 10:
                        case 11:
                        case 12:
                            i2 = 3;
                            break;
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 42) {
            objArr[0] = "messageCollector";
        } else if (i == 43) {
            objArr[0] = "arguments";
        } else if (i != 45) {
            switch (i) {
                case 1:
                    objArr[0] = "$self";
                    break;
                case 2:
                    objArr[0] = "arguments";
                    break;
                case 3:
                    objArr[0] = "reporter";
                    break;
                case 4:
                    objArr[0] = "allKotlinFiles";
                    break;
                case 5:
                    objArr[0] = "jsAnalysisResult";
                    break;
                case 6:
                    objArr[0] = "mainCallParameters";
                    break;
                case 7:
                    objArr[0] = "config";
                    break;
                default:
                    switch (i) {
                        case 10:
                            objArr[0] = "arguments";
                            break;
                        case 11:
                            objArr[0] = "configuration";
                            break;
                        case 12:
                            objArr[0] = "rootDisposable";
                            break;
                        default:
                            switch (i) {
                                case 31:
                                    objArr[0] = K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG;
                                    break;
                                case 32:
                                case 35:
                                    objArr[0] = "sourceFiles";
                                    break;
                                case 33:
                                    objArr[0] = "config";
                                    break;
                                case 34:
                                case 40:
                                    objArr[0] = "messageCollector";
                                    break;
                                case 36:
                                    objArr[0] = "configuration";
                                    break;
                                case 37:
                                case 39:
                                    objArr[0] = "arguments";
                                    break;
                                case 38:
                                    objArr[0] = "services";
                                    break;
                                default:
                                    objArr[0] = "org/jetbrains/kotlin/cli/js/K2JSCompiler";
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[0] = "versionArray";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 12:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 42:
            case 43:
            case 45:
                objArr[1] = "org/jetbrains/kotlin/cli/js/K2JSCompiler";
                break;
            case 8:
            case 9:
                objArr[1] = "translate";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                objArr[1] = "doExecute";
                break;
            case 41:
                objArr[1] = "configureLibraries";
                break;
            case 44:
                objArr[1] = "getDefaultPerformanceManager";
                break;
            default:
                objArr[1] = "getIrCompiler";
                break;
        }
        if (i == 42 || i == 43) {
            objArr[2] = "calculateSourceMapSourceRoot";
        } else if (i != 45) {
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "addPlatformOptions";
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    objArr[2] = "translate";
                    break;
                default:
                    switch (i) {
                        case 10:
                        case 11:
                        case 12:
                            objArr[2] = "doExecute";
                            break;
                        default:
                            switch (i) {
                                case 31:
                                case 32:
                                case 33:
                                    objArr[2] = "checkDuplicateSourceFileNames";
                                    break;
                                case 34:
                                case 35:
                                    objArr[2] = "reportCompiledSourcesList";
                                    break;
                                case 36:
                                case 37:
                                case 38:
                                    objArr[2] = "setupPlatformSpecificArgumentsAndServices";
                                    break;
                                case 39:
                                case 40:
                                    objArr[2] = "configureLibraries";
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[2] = "createMetadataVersion";
        }
        String str2 = String.format(str, objArr);
        if (i != 42 && i != 43 && i != 45) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    break;
                default:
                    switch (i) {
                        case 10:
                        case 11:
                        case 12:
                            break;
                        default:
                            switch (i) {
                                case 31:
                                case 32:
                                case 33:
                                case 34:
                                case 35:
                                case 36:
                                case 37:
                                case 38:
                                case 39:
                                case 40:
                                    break;
                                default:
                                    throw new IllegalStateException(str2);
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalArgumentException(str2);
    }

    protected void addPlatformOptions(List<String> list, K2JSCompilerArguments k2JSCompilerArguments) {
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        if (k2JSCompilerArguments == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    protected /* bridge */ /* synthetic */ void addPlatformOptions(List list, CommonCompilerArguments commonCompilerArguments) {
        addPlatformOptions((List<String>) list, (K2JSCompilerArguments) commonCompilerArguments);
    }

    static {
        HashMap map = new HashMap();
        moduleKindMap = map;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        sourceMapContentEmbeddingMap = linkedHashMap;
        map.put(K2JsArgumentConstants.MODULE_PLAIN, ModuleKind.PLAIN);
        map.put(K2JsArgumentConstants.MODULE_COMMONJS, ModuleKind.COMMON_JS);
        map.put(K2JsArgumentConstants.MODULE_AMD, ModuleKind.AMD);
        map.put(K2JsArgumentConstants.MODULE_UMD, ModuleKind.UMD);
        map.put("es", ModuleKind.ES);
        linkedHashMap.put("always", SourceMapSourceEmbedding.ALWAYS);
        linkedHashMap.put("never", SourceMapSourceEmbedding.NEVER);
        linkedHashMap.put(K2JsArgumentConstants.SOURCE_MAP_SOURCE_CONTENT_INLINING, SourceMapSourceEmbedding.INLINING);
    }

    private K2JsIrCompiler getIrCompiler() {
        if (this.irCompiler == null) {
            this.irCompiler = new K2JsIrCompiler();
        }
        K2JsIrCompiler k2JsIrCompiler = this.irCompiler;
        if (k2JsIrCompiler == null) {
            $$$reportNull$$$0(0);
        }
        return k2JsIrCompiler;
    }

    public static void main(String... strArr) {
        doMain(new K2JSCompiler(), strArr);
    }

    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public K2JSCompilerArguments createArguments() {
        return new K2JSCompilerArguments();
    }

    private static TranslationResult translate(JsConfig.Reporter reporter, List<KtFile> list, JsAnalysisResult jsAnalysisResult, MainCallParameters mainCallParameters, JsConfig jsConfig) throws TranslationException {
        if (reporter == null) {
            $$$reportNull$$$0(3);
        }
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        if (jsAnalysisResult == null) {
            $$$reportNull$$$0(5);
        }
        if (mainCallParameters == null) {
            $$$reportNull$$$0(6);
        }
        if (jsConfig == null) {
            $$$reportNull$$$0(7);
        }
        K2JSTranslator k2JSTranslator = new K2JSTranslator(jsConfig);
        IncrementalDataProvider incrementalDataProvider = (IncrementalDataProvider) jsConfig.getConfiguration().get(JSConfigurationKeys.INCREMENTAL_DATA_PROVIDER);
        if (incrementalDataProvider != null) {
            HashMap map = new HashMap(list.size());
            for (KtFile ktFile : list) {
                map.put(VfsUtilCore.virtualToIoFile(ktFile.getVirtualFile()), ktFile);
            }
            Map<File, TranslationResultValue> compiledPackageParts = incrementalDataProvider.getCompiledPackageParts();
            int size = compiledPackageParts.size() + list.size();
            File[] fileArr = new File[size];
            Iterator<File> it = compiledPackageParts.keySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                fileArr[i] = it.next();
                i++;
            }
            Iterator it2 = map.keySet().iterator();
            while (it2.hasNext()) {
                fileArr[i] = (File) it2.next();
                i++;
            }
            Arrays.sort(fileArr);
            Set set = (Set) map.values().stream().map(new Function() { // from class: org.jetbrains.kotlin.cli.js.K2JSCompiler$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((KtFile) obj).getPackageFqName();
                }
            }).collect(Collectors.toSet());
            HashMap map2 = new HashMap();
            for (Map.Entry<String, byte[]> entry : incrementalDataProvider.getPackageMetadata().entrySet()) {
                FqName fqName = new FqName(entry.getKey());
                if (!set.contains(fqName)) {
                    map2.put(fqName, entry.getValue());
                }
            }
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < size; i2++) {
                KtFile ktFile2 = (KtFile) map.get(fileArr[i2]);
                if (ktFile2 != null) {
                    arrayList.add(new TranslationUnit.SourceFile(ktFile2));
                } else {
                    TranslationResultValue translationResultValue = compiledPackageParts.get(fileArr[i2]);
                    arrayList.add(new TranslationUnit.BinaryAst(translationResultValue.getBinaryAst(), translationResultValue.getInlineData()));
                }
            }
            TranslationResult translationResultTranslateUnits = k2JSTranslator.translateUnits(reporter, arrayList, mainCallParameters, jsAnalysisResult, map2);
            if (translationResultTranslateUnits == null) {
                $$$reportNull$$$0(8);
            }
            return translationResultTranslateUnits;
        }
        CollectionsKt.sortBy(list, new Function1() { // from class: org.jetbrains.kotlin.cli.js.K2JSCompiler$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VfsUtilCore.virtualToIoFile(((KtFile) obj).getVirtualFile());
            }
        });
        TranslationResult translationResultTranslate = k2JSTranslator.translate(reporter, list, mainCallParameters, jsAnalysisResult);
        if (translationResultTranslate == null) {
            $$$reportNull$$$0(9);
        }
        return translationResultTranslate;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public ExitCode doExecute(K2JSCompilerArguments k2JSCompilerArguments, CompilerConfiguration compilerConfiguration, Disposable disposable, KotlinPaths kotlinPaths) {
        AnalysisResult analysisResult;
        boolean z;
        File file;
        File file2;
        if (k2JSCompilerArguments == null) {
            $$$reportNull$$$0(10);
        }
        if (compilerConfiguration == null) {
            $$$reportNull$$$0(11);
        }
        if (disposable == null) {
            $$$reportNull$$$0(12);
        }
        final MessageCollector messageCollector = (MessageCollector) compilerConfiguration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        ExitCode exitCodeDoExecute = ExitCode.OK;
        boolean zEquals = Boolean.TRUE.equals(compilerConfiguration.get(CommonConfigurationKeys.USE_FIR));
        if (K2JSCompilerArgumentsKt.isIrBackendEnabled(k2JSCompilerArguments) || zEquals) {
            exitCodeDoExecute = getIrCompiler().doExecute(k2JSCompilerArguments, compilerConfiguration.copy(), disposable, kotlinPaths);
        }
        if (K2JSCompilerArgumentsKt.isPreIrBackendDisabled(k2JSCompilerArguments) || zEquals) {
            if (exitCodeDoExecute == null) {
                $$$reportNull$$$0(13);
            }
            return exitCodeDoExecute;
        }
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(compilerConfiguration);
        if (!k2JSCompilerArguments.getForceDeprecatedLegacyCompilerUsage() && languageVersionSettings.getLanguageVersion().compareTo(LanguageVersion.KOTLIN_1_9) >= 0) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "Old Kotlin/JS compiler is no longer supported. Please migrate to the new JS IR backend", null);
            ExitCode exitCode = ExitCode.COMPILATION_ERROR;
            if (exitCode == null) {
                $$$reportNull$$$0(14);
            }
            return exitCode;
        }
        if (k2JSCompilerArguments.getFreeArgs().isEmpty() && !UtilsKt.incrementalCompilationIsEnabledForJs(k2JSCompilerArguments)) {
            if (k2JSCompilerArguments.getVersion()) {
                ExitCode exitCode2 = ExitCode.OK;
                if (exitCode2 == null) {
                    $$$reportNull$$$0(15);
                }
                return exitCode2;
            }
            messageCollector.report(CompilerMessageSeverity.ERROR, "Specify at least one source file or directory", null);
            ExitCode exitCode3 = ExitCode.COMPILATION_ERROR;
            if (exitCode3 == null) {
                $$$reportNull$$$0(16);
            }
            return exitCode3;
        }
        ExitCode exitCodeLoadPlugins = loadPlugins(kotlinPaths, k2JSCompilerArguments, compilerConfiguration);
        if (exitCodeLoadPlugins != ExitCode.OK) {
            if (exitCodeLoadPlugins == null) {
                $$$reportNull$$$0(17);
            }
            return exitCodeLoadPlugins;
        }
        compilerConfiguration.put(JSConfigurationKeys.LIBRARIES, configureLibraries(k2JSCompilerArguments, kotlinPaths, messageCollector));
        String[] commonSources = k2JSCompilerArguments.getCommonSources();
        Set setEmptySet = commonSources == null ? Collections.emptySet() : SetsKt.setOf((Object[]) commonSources);
        for (String str : k2JSCompilerArguments.getFreeArgs()) {
            ContentRootsKt.addKotlinSourceRoot(compilerConfiguration, str, setEmptySet.contains(str));
        }
        KotlinCoreEnvironment kotlinCoreEnvironmentCreateForProduction = KotlinCoreEnvironment.createForProduction(disposable, compilerConfiguration, EnvironmentConfigFiles.JS_CONFIG_FILES);
        Project project = kotlinCoreEnvironmentCreateForProduction.getProject();
        List<KtFile> sourceFiles = kotlinCoreEnvironmentCreateForProduction.getSourceFiles();
        kotlinCoreEnvironmentCreateForProduction.getConfiguration().put(CLIConfigurationKeys.ALLOW_KOTLIN_PACKAGE, Boolean.valueOf(k2JSCompilerArguments.getAllowKotlinPackage()));
        kotlinCoreEnvironmentCreateForProduction.getConfiguration().put(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME, Boolean.valueOf(k2JSCompilerArguments.getRenderInternalDiagnosticNames()));
        if (!HelpersKt.checkKotlinPackageUsageForPsi(kotlinCoreEnvironmentCreateForProduction.getConfiguration(), sourceFiles)) {
            ExitCode exitCode4 = ExitCode.COMPILATION_ERROR;
            if (exitCode4 == null) {
                $$$reportNull$$$0(18);
            }
            return exitCode4;
        }
        if (k2JSCompilerArguments.getOutputFile() == null) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "Specify output file via -output", null);
            ExitCode exitCode5 = ExitCode.COMPILATION_ERROR;
            if (exitCode5 == null) {
                $$$reportNull$$$0(19);
            }
            return exitCode5;
        }
        if (messageCollector.getHasErrors()) {
            ExitCode exitCode6 = ExitCode.COMPILATION_ERROR;
            if (exitCode6 == null) {
                $$$reportNull$$$0(20);
            }
            return exitCode6;
        }
        if (sourceFiles.isEmpty() && !UtilsKt.incrementalCompilationIsEnabledForJs(k2JSCompilerArguments)) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "No source files", null);
            ExitCode exitCode7 = ExitCode.COMPILATION_ERROR;
            if (exitCode7 == null) {
                $$$reportNull$$$0(21);
            }
            return exitCode7;
        }
        if (k2JSCompilerArguments.getVerbose()) {
            reportCompiledSourcesList(messageCollector, sourceFiles);
        }
        File file3 = new File(k2JSCompilerArguments.getOutputFile());
        compilerConfiguration.put(CommonConfigurationKeys.MODULE_NAME, FileUtil.getNameWithoutExtension(file3));
        final JsConfig jsConfig = new JsConfig(project, compilerConfiguration, CompilerEnvironment.INSTANCE);
        JsConfig.Reporter reporter = new JsConfig.Reporter() { // from class: org.jetbrains.kotlin.cli.js.K2JSCompiler.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                objArr[0] = CrashHianalyticsData.MESSAGE;
                objArr[1] = "org/jetbrains/kotlin/cli/js/K2JSCompiler$1";
                if (i != 1) {
                    objArr[2] = "error";
                } else {
                    objArr[2] = CompilerOptions.WARNING;
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.js.config.JsConfig.Reporter
            public void error(String str2) {
                if (str2 == null) {
                    $$$reportNull$$$0(0);
                }
                messageCollector.report(CompilerMessageSeverity.ERROR, str2, null);
            }

            @Override // org.jetbrains.kotlin.js.config.JsConfig.Reporter
            public void warning(String str2) {
                if (str2 == null) {
                    $$$reportNull$$$0(1);
                }
                messageCollector.report(CompilerMessageSeverity.STRONG_WARNING, str2, null);
            }
        };
        if (jsConfig.checkLibFilesAndReportErrors(reporter)) {
            ExitCode exitCode8 = ExitCode.COMPILATION_ERROR;
            if (exitCode8 == null) {
                $$$reportNull$$$0(22);
            }
            return exitCode8;
        }
        do {
            AnalyzerWithCompilerReport analyzerWithCompilerReport = new AnalyzerWithCompilerReport(messageCollector, CommonConfigurationKeysKt.getLanguageVersionSettings(compilerConfiguration), compilerConfiguration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
            final List<KtFile> sourceFiles2 = kotlinCoreEnvironmentCreateForProduction.getSourceFiles();
            analyzerWithCompilerReport.analyzeAndReport(sourceFiles, new Function0() { // from class: org.jetbrains.kotlin.cli.js.K2JSCompiler$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return TopDownAnalyzerFacadeForJS.analyzeFiles(sourceFiles2, jsConfig);
                }
            });
            if (analyzerWithCompilerReport.hasErrors()) {
                ExitCode exitCode9 = ExitCode.COMPILATION_ERROR;
                if (exitCode9 == null) {
                    $$$reportNull$$$0(23);
                }
                return exitCode9;
            }
            ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
            analysisResult = analyzerWithCompilerReport.getAnalysisResult();
            z = analysisResult instanceof JsAnalysisResult.RetryWithAdditionalRoots;
            if (z) {
                kotlinCoreEnvironmentCreateForProduction.addKotlinSourceRoots(((JsAnalysisResult.RetryWithAdditionalRoots) analysisResult).getAdditionalKotlinRoots());
            }
        } while (z);
        if (!analysisResult.getShouldGenerateCode()) {
            ExitCode exitCode10 = ExitCode.OK;
            if (exitCode10 == null) {
                $$$reportNull$$$0(24);
            }
            return exitCode10;
        }
        JsAnalysisResult jsAnalysisResult = (JsAnalysisResult) analysisResult;
        if (k2JSCompilerArguments.getOutputPrefix() != null) {
            file = new File(k2JSCompilerArguments.getOutputPrefix());
            if (!file.exists()) {
                messageCollector.report(CompilerMessageSeverity.ERROR, "Output prefix file '" + k2JSCompilerArguments.getOutputPrefix() + "' not found", null);
                ExitCode exitCode11 = ExitCode.COMPILATION_ERROR;
                if (exitCode11 == null) {
                    $$$reportNull$$$0(25);
                }
                return exitCode11;
            }
        } else {
            file = null;
        }
        if (k2JSCompilerArguments.getOutputPostfix() != null) {
            file2 = new File(k2JSCompilerArguments.getOutputPostfix());
            if (!file2.exists()) {
                messageCollector.report(CompilerMessageSeverity.ERROR, "Output postfix file '" + k2JSCompilerArguments.getOutputPostfix() + "' not found", null);
                ExitCode exitCode12 = ExitCode.COMPILATION_ERROR;
                if (exitCode12 == null) {
                    $$$reportNull$$$0(26);
                }
                return exitCode12;
            }
        } else {
            file2 = null;
        }
        File parentFile = file3.getParentFile();
        if (parentFile == null) {
            parentFile = file3.getAbsoluteFile().getParentFile();
        }
        try {
            jsConfig.getConfiguration().put(JSConfigurationKeys.OUTPUT_DIR, parentFile.getCanonicalFile());
            if (jsConfig.getConfiguration().getBoolean(JSConfigurationKeys.SOURCE_MAP)) {
                checkDuplicateSourceFileNames(messageCollector, sourceFiles, jsConfig);
            }
            try {
                TranslationResult translationResultTranslate = translate(reporter, sourceFiles, jsAnalysisResult, createMainCallParameters(k2JSCompilerArguments.getMain()), jsConfig);
                ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
                AnalyzerWithCompilerReport.INSTANCE.reportDiagnostics(translationResultTranslate.getDiagnostics(), messageCollector, compilerConfiguration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
                if (translationResultTranslate instanceof TranslationResult.Fail) {
                    ExitCode exitCode13 = ExitCode.COMPILATION_ERROR;
                    if (exitCode13 == null) {
                        $$$reportNull$$$0(28);
                    }
                    return exitCode13;
                }
                OutputFileCollection outputFiles = ((TranslationResult.SuccessBase) translationResultTranslate).getOutputFiles(file3, file, file2);
                if (file3.isDirectory()) {
                    messageCollector.report(CompilerMessageSeverity.ERROR, "Cannot open output file '" + file3.getPath() + "': is a directory", null);
                    ExitCode exitCode14 = ExitCode.COMPILATION_ERROR;
                    if (exitCode14 == null) {
                        $$$reportNull$$$0(29);
                    }
                    return exitCode14;
                }
                ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
                OutputUtilsKt.writeAll(outputFiles, parentFile, messageCollector, compilerConfiguration.getBoolean(CommonConfigurationKeys.REPORT_OUTPUT_FILES));
                ExitCode exitCode15 = ExitCode.OK;
                if (exitCode15 == null) {
                    $$$reportNull$$$0(30);
                }
                return exitCode15;
            } catch (Exception e) {
                throw ExceptionUtilsKt.rethrow(e);
            }
        } catch (IOException unused) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "Could not resolve output directory", null);
            ExitCode exitCode16 = ExitCode.COMPILATION_ERROR;
            if (exitCode16 == null) {
                $$$reportNull$$$0(27);
            }
            return exitCode16;
        }
    }

    private static void checkDuplicateSourceFileNames(MessageCollector messageCollector, List<KtFile> list, JsConfig jsConfig) {
        if (messageCollector == null) {
            $$$reportNull$$$0(31);
        }
        if (list == null) {
            $$$reportNull$$$0(32);
        }
        if (jsConfig == null) {
            $$$reportNull$$$0(33);
        }
        if (jsConfig.getSourceMapRoots().isEmpty()) {
            return;
        }
        SourceFilePathResolver sourceFilePathResolverCreate = SourceFilePathResolver.create(jsConfig);
        HashMap map = new HashMap();
        HashSet hashSet = new HashSet();
        try {
            for (KtFile ktFile : list) {
                String path = ktFile.getVirtualFile().getPath();
                String pathRelativeToSourceRoots = sourceFilePathResolverCreate.getPathRelativeToSourceRoots(new File(ktFile.getVirtualFile().getPath()));
                String str = (String) map.get(pathRelativeToSourceRoots);
                if (str != null) {
                    if (hashSet.add(pathRelativeToSourceRoots)) {
                        messageCollector.report(CompilerMessageSeverity.WARNING, "There are files with same path '" + pathRelativeToSourceRoots + "', relative to source roots: '" + path + "' and '" + str + "'. This will likely cause problems with debugger", null);
                    }
                } else {
                    map.put(pathRelativeToSourceRoots, path);
                }
            }
        } catch (IOException e) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "IO error occurred validating source path:\n" + ExceptionUtil.getThrowableText(e), null);
        }
    }

    private static void reportCompiledSourcesList(MessageCollector messageCollector, List<KtFile> list) {
        if (messageCollector == null) {
            $$$reportNull$$$0(34);
        }
        if (list == null) {
            $$$reportNull$$$0(35);
        }
        messageCollector.report(CompilerMessageSeverity.LOGGING, "Compiling source files: " + StringsKt.join(CollectionsKt.map(list, new Function1() { // from class: org.jetbrains.kotlin.cli.js.K2JSCompiler$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return K2JSCompiler.lambda$reportCompiledSourcesList$2((KtFile) obj);
            }
        }), ", "), null);
    }

    static /* synthetic */ String lambda$reportCompiledSourcesList$2(KtFile ktFile) {
        VirtualFile virtualFile = ktFile.getVirtualFile();
        if (virtualFile != null) {
            return MessageUtil.virtualFileToPath(virtualFile);
        }
        return ktFile.get$name() + " (no virtual file)";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public void setupPlatformSpecificArgumentsAndServices(CompilerConfiguration compilerConfiguration, K2JSCompilerArguments k2JSCompilerArguments, Services services) {
        if (compilerConfiguration == null) {
            $$$reportNull$$$0(36);
        }
        if (k2JSCompilerArguments == null) {
            $$$reportNull$$$0(37);
        }
        if (services == null) {
            $$$reportNull$$$0(38);
        }
        if (K2JSCompilerArgumentsKt.isIrBackendEnabled(k2JSCompilerArguments)) {
            getIrCompiler().setupPlatformSpecificArgumentsAndServices(compilerConfiguration, k2JSCompilerArguments, services);
        }
        if (K2JSCompilerArgumentsKt.isPreIrBackendDisabled(k2JSCompilerArguments)) {
            return;
        }
        MessageCollector messageCollector = (MessageCollector) compilerConfiguration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        k2JSCompilerArguments.getTarget();
        compilerConfiguration.put(JSConfigurationKeys.TARGET, EcmaVersion.defaultVersion());
        if (k2JSCompilerArguments.getSourceMap()) {
            compilerConfiguration.put(JSConfigurationKeys.SOURCE_MAP, true);
            if (k2JSCompilerArguments.getSourceMapPrefix() != null) {
                compilerConfiguration.put(JSConfigurationKeys.SOURCE_MAP_PREFIX, k2JSCompilerArguments.getSourceMapPrefix());
            }
            String sourceMapBaseDirs = k2JSCompilerArguments.getSourceMapBaseDirs();
            if (sourceMapBaseDirs == null && StringUtil.isNotEmpty(k2JSCompilerArguments.getSourceMapPrefix())) {
                sourceMapBaseDirs = calculateSourceMapSourceRoot(messageCollector, k2JSCompilerArguments);
            }
            if (sourceMapBaseDirs != null) {
                compilerConfiguration.put(JSConfigurationKeys.SOURCE_MAP_SOURCE_ROOTS, StringUtil.split(sourceMapBaseDirs, File.pathSeparator));
            }
        } else {
            if (k2JSCompilerArguments.getSourceMapPrefix() != null) {
                messageCollector.report(CompilerMessageSeverity.WARNING, "source-map-prefix argument has no effect without source map", null);
            }
            if (k2JSCompilerArguments.getSourceMapBaseDirs() != null) {
                messageCollector.report(CompilerMessageSeverity.WARNING, "source-map-source-root argument has no effect without source map", null);
            }
        }
        if (k2JSCompilerArguments.getMetaInfo()) {
            compilerConfiguration.put(JSConfigurationKeys.META_INFO, true);
        }
        compilerConfiguration.put(JSConfigurationKeys.TYPED_ARRAYS_ENABLED, Boolean.valueOf(k2JSCompilerArguments.getTypedArrays()));
        compilerConfiguration.put(JSConfigurationKeys.FRIEND_PATHS_DISABLED, Boolean.valueOf(k2JSCompilerArguments.getFriendModulesDisabled()));
        if (!k2JSCompilerArguments.getFriendModulesDisabled() && k2JSCompilerArguments.getFriendModules() != null) {
            compilerConfiguration.put(JSConfigurationKeys.FRIEND_PATHS, ArraysKt.filterNot(k2JSCompilerArguments.getFriendModules().split(File.pathSeparator), new K2JSCompiler$$ExternalSyntheticLambda3()));
        }
        compilerConfiguration.put(JSConfigurationKeys.METADATA_ONLY, Boolean.valueOf(k2JSCompilerArguments.getMetadataOnly()));
        String moduleKind = k2JSCompilerArguments.getModuleKind();
        ModuleKind moduleKind2 = moduleKind != null ? moduleKindMap.get(moduleKind) : ModuleKind.PLAIN;
        if (moduleKind2 == null) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "Unknown module kind: " + moduleKind + ". Valid values are: plain, amd, commonjs, umd", null);
            moduleKind2 = ModuleKind.PLAIN;
        }
        compilerConfiguration.put(JSConfigurationKeys.MODULE_KIND, moduleKind2);
        IncrementalDataProvider incrementalDataProvider = (IncrementalDataProvider) services.get(IncrementalDataProvider.class);
        if (incrementalDataProvider != null) {
            compilerConfiguration.put(JSConfigurationKeys.INCREMENTAL_DATA_PROVIDER, incrementalDataProvider);
        }
        IncrementalResultsConsumer incrementalResultsConsumer = (IncrementalResultsConsumer) services.get(IncrementalResultsConsumer.class);
        if (incrementalResultsConsumer != null) {
            compilerConfiguration.put(JSConfigurationKeys.INCREMENTAL_RESULTS_CONSUMER, incrementalResultsConsumer);
        }
        LookupTracker lookupTracker = (LookupTracker) services.get(LookupTracker.class);
        if (lookupTracker != null) {
            compilerConfiguration.put(CommonConfigurationKeys.LOOKUP_TRACKER, lookupTracker);
        }
        ExpectActualTracker expectActualTracker = (ExpectActualTracker) services.get(ExpectActualTracker.class);
        if (expectActualTracker != null) {
            compilerConfiguration.put(CommonConfigurationKeys.EXPECT_ACTUAL_TRACKER, expectActualTracker);
        }
        String sourceMapEmbedSources = k2JSCompilerArguments.getSourceMapEmbedSources();
        SourceMapSourceEmbedding sourceMapSourceEmbedding = sourceMapEmbedSources != null ? sourceMapContentEmbeddingMap.get(sourceMapEmbedSources) : SourceMapSourceEmbedding.INLINING;
        if (sourceMapSourceEmbedding == null) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "Unknown source map source embedding mode: " + sourceMapEmbedSources + ". Valid values are: " + StringUtil.join((Collection<String>) sourceMapContentEmbeddingMap.keySet(), ", "), null);
            sourceMapSourceEmbedding = SourceMapSourceEmbedding.INLINING;
        }
        compilerConfiguration.put(JSConfigurationKeys.SOURCE_MAP_EMBED_SOURCES, sourceMapSourceEmbedding);
        if (k2JSCompilerArguments.getSourceMap() || sourceMapEmbedSources == null) {
            return;
        }
        messageCollector.report(CompilerMessageSeverity.WARNING, "source-map-embed-sources argument has no effect without source map", null);
    }

    private static List<String> configureLibraries(K2JSCompilerArguments k2JSCompilerArguments, KotlinPaths kotlinPaths, MessageCollector messageCollector) {
        File libraryFromHome;
        if (k2JSCompilerArguments == null) {
            $$$reportNull$$$0(39);
        }
        if (messageCollector == null) {
            $$$reportNull$$$0(40);
        }
        SmartList smartList = new SmartList();
        if (!k2JSCompilerArguments.getNoStdlib() && (libraryFromHome = UtilsKt.getLibraryFromHome(kotlinPaths, new Function1() { // from class: org.jetbrains.kotlin.cli.js.K2JSCompiler$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((KotlinPaths) obj).getJsStdLibJarPath();
            }
        }, PathUtil.JS_LIB_JAR_NAME, messageCollector, "'-no-stdlib'")) != null) {
            smartList.add(libraryFromHome.getAbsolutePath());
        }
        if (k2JSCompilerArguments.getLibraries() != null) {
            smartList.addAll(ArraysKt.filterNot(k2JSCompilerArguments.getLibraries().split(File.pathSeparator), new K2JSCompiler$$ExternalSyntheticLambda3()));
        }
        return smartList;
    }

    static String calculateSourceMapSourceRoot(MessageCollector messageCollector, K2JSCompilerArguments k2JSCompilerArguments) {
        if (messageCollector == null) {
            $$$reportNull$$$0(42);
        }
        if (k2JSCompilerArguments == null) {
            $$$reportNull$$$0(43);
        }
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        try {
            Iterator<String> it = k2JSCompilerArguments.getFreeArgs().iterator();
            File file = null;
            while (it.hasNext()) {
                File canonicalFile = new File(it.next()).getCanonicalFile();
                if (file != null) {
                    while (canonicalFile != null) {
                        Integer num = (Integer) map.get(canonicalFile);
                        if (num != null) {
                            arrayList.subList(Integer.valueOf(Math.min(num.intValue(), arrayList.size() - 1)).intValue() + 1, arrayList.size()).clear();
                            file = (File) arrayList.get(arrayList.size() - 1);
                            break;
                        }
                        canonicalFile = canonicalFile.getParentFile();
                    }
                    if (canonicalFile == null) {
                        break;
                    }
                } else {
                    for (File parentFile = canonicalFile; parentFile != null; parentFile = parentFile.getParentFile()) {
                        arrayList.add(parentFile);
                    }
                    Collections.reverse(arrayList);
                    for (int i = 0; i < arrayList.size(); i++) {
                        map.put(arrayList.get(i), Integer.valueOf(i));
                    }
                    file = canonicalFile;
                }
            }
            return file != null ? file.getPath() : ".";
        } catch (IOException e) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "IO error occurred calculating source root:\n" + ExceptionUtil.getThrowableText(e), null);
            return ".";
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public CommonCompilerPerformanceManager getDefaultPerformanceManager() {
        K2JSCompilerPerformanceManager k2JSCompilerPerformanceManager = this.performanceManager;
        if (k2JSCompilerPerformanceManager == null) {
            $$$reportNull$$$0(44);
        }
        return k2JSCompilerPerformanceManager;
    }

    private static MainCallParameters createMainCallParameters(String str) {
        if (K2JsArgumentConstants.NO_CALL.equals(str)) {
            return MainCallParameters.noCall();
        }
        return MainCallParameters.mainWithoutArguments();
    }

    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public String executableScriptFileName() {
        return "kotlinc-js";
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    protected BinaryVersion createMetadataVersion(int[] iArr) {
        if (iArr == null) {
            $$$reportNull$$$0(45);
        }
        return new JsMetadataVersion(iArr);
    }

    private static final class K2JSCompilerPerformanceManager extends CommonCompilerPerformanceManager {
        public K2JSCompilerPerformanceManager() {
            super("Kotlin to JS Compiler");
        }
    }
}
