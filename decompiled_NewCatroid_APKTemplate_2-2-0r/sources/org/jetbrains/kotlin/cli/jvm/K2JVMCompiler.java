package org.jetbrains.kotlin.cli.jvm;

import com.reandroid.apk.UncompressedFiles;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.jvm.JvmPhasesKt;
import org.jetbrains.kotlin.cli.common.CLICompiler;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CLITool;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.CreatePhaseConfigKt;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.extensions.ScriptEvaluationExtension;
import org.jetbrains.kotlin.cli.common.extensions.ShellExtension;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageUtil;
import org.jetbrains.kotlin.cli.common.messages.OutputMessageUtil;
import org.jetbrains.kotlin.cli.common.profiling.ProfilingCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompilerKt;
import org.jetbrains.kotlin.cli.jvm.compiler.pipeline.CompilerPipelineKt;
import org.jetbrains.kotlin.cli.jvm.config.ClassicFrontendSpecificJvmConfigurationKeys;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.codegen.CompilationException;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.ImportTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.load.java.JavaClassesTracker;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.modules.Module;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.utils.KotlinPaths;

/* JADX INFO: compiled from: K2JVMCompiler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\u0018\u0000 &2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002&'B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\u0002H\u0016J*\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J\u0018\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001aH\u0014J*\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0014J\b\u0010\u001f\u001a\u00020\u0012H\u0016J\b\u0010 \u001a\u00020!H\u0002J \u0010\"\u001a\u00020#2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001aH\u0014J\u001a\u0010$\u001a\u00020#*\b\u0012\u0004\u0012\u00020\u00120%2\u0006\u0010\u0018\u001a\u00020\u0002H\u0014R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/K2JVMCompiler;", "Lorg/jetbrains/kotlin/cli/common/CLICompiler;", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "()V", "defaultPerformanceManager", "Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "getDefaultPerformanceManager", "()Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "createArguments", "createCoreEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "rootDisposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "targetDescription", "", "createMetadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "versionArray", "", "createPerformanceManager", "arguments", "services", "Lorg/jetbrains/kotlin/config/Services;", "doExecute", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", UncompressedFiles.NAME_paths, "Lorg/jetbrains/kotlin/utils/KotlinPaths;", "executableScriptFileName", "isUseOldBackendAllowed", "", "setupPlatformSpecificArgumentsAndServices", "", "addPlatformOptions", "", "Companion", "K2JVMCompilerPerformanceManager", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2JVMCompiler extends CLICompiler<K2JVMCompilerArguments> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final CommonCompilerPerformanceManager defaultPerformanceManager = new K2JVMCompilerPerformanceManager();

    @JvmStatic
    public static final void main(String[] strArr) {
        INSTANCE.main(strArr);
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public /* bridge */ /* synthetic */ void addPlatformOptions(List list, CommonCompilerArguments commonCompilerArguments) {
        addPlatformOptions((List<String>) list, (K2JVMCompilerArguments) commonCompilerArguments);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public ExitCode doExecute(K2JVMCompilerArguments arguments, CompilerConfiguration configuration, Disposable rootDisposable, KotlinPaths paths) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(rootDisposable, "rootDisposable");
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        CompilerConfigurationKey<Integer> compilerConfigurationKey = CLIConfigurationKeys.REPEAT_COMPILE_MODULES;
        String repeatCompileModules = arguments.getRepeatCompileModules();
        Object obj = null;
        configuration.putIfNotNull(compilerConfigurationKey, repeatCompileModules != null ? StringsKt.toIntOrNull(repeatCompileModules) : null);
        K2JVMCompilerArguments k2JVMCompilerArguments = arguments;
        configuration.put(CLIConfigurationKeys.PHASE_CONFIG, CreatePhaseConfigKt.createPhaseConfig(JvmPhasesKt.getJvmPhases(), k2JVMCompilerArguments, messageCollector));
        if (!JvmArgumentsKt.configureJdkHome(configuration, arguments)) {
            return ExitCode.COMPILATION_ERROR;
        }
        configuration.put(JVMConfigurationKeys.DISABLE_STANDARD_SCRIPT_DEFINITION, Boolean.valueOf(arguments.getDisableStandardScript()));
        ExitCode exitCodeLoadPlugins = loadPlugins(paths, k2JVMCompilerArguments, configuration);
        if (exitCodeLoadPlugins != ExitCode.OK) {
            return exitCodeLoadPlugins;
        }
        String moduleName = arguments.getModuleName();
        if (moduleName == null) {
            moduleName = "main";
        }
        configuration.put(CommonConfigurationKeys.MODULE_NAME, moduleName);
        JvmArgumentsKt.configureJavaModulesContentRoots(configuration, arguments);
        JvmArgumentsKt.configureStandardLibs(configuration, paths, arguments);
        JvmArgumentsKt.configureAdvancedJvmOptions(configuration, arguments);
        JvmArgumentsKt.configureKlibPaths(configuration, arguments);
        if (arguments.getBuildFile() == null && !arguments.getVersion() && !arguments.getAllowNoSourceFiles() && (arguments.getScript() || arguments.getExpression() != null || arguments.getFreeArgs().isEmpty())) {
            JvmArgumentsKt.configureContentRootsFromClassPath(configuration, arguments);
            JvmContentRootsKt.configureJdkClasspathRoots(configuration);
            if (arguments.getScript() && arguments.getFreeArgs().isEmpty()) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Specify script source path to evaluate", null, 4, null);
                return ExitCode.COMPILATION_ERROR;
            }
            KotlinCoreEnvironment.ProjectEnvironment projectEnvironment = new KotlinCoreEnvironment.ProjectEnvironment(rootDisposable, KotlinCoreEnvironment.INSTANCE.getOrCreateApplicationEnvironmentForProduction(rootDisposable, configuration), configuration);
            projectEnvironment.registerExtensionsFromPlugins(configuration);
            if (arguments.getUseOldBackend()) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.WARNING, "-Xuse-old-backend is no longer supported. Please migrate to the new JVM IR backend", null, 4, null);
            }
            if (arguments.getScript() || arguments.getExpression() != null) {
                ScriptEvaluationExtension.Companion companion = ScriptEvaluationExtension.INSTANCE;
                MockProject project = projectEnvironment.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                for (Object obj2 : companion.getInstances(project)) {
                    if (((ScriptEvaluationExtension) obj2).isAccepted(k2JVMCompilerArguments)) {
                        obj = obj2;
                        break;
                    }
                }
                ScriptEvaluationExtension scriptEvaluationExtension = (ScriptEvaluationExtension) obj;
                if (scriptEvaluationExtension == null) {
                    MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Unable to evaluate script, no scripting plugin loaded", null, 4, null);
                    return ExitCode.COMPILATION_ERROR;
                }
                return scriptEvaluationExtension.eval(k2JVMCompilerArguments, configuration, projectEnvironment);
            }
            ShellExtension.Companion companion2 = ShellExtension.INSTANCE;
            MockProject project2 = projectEnvironment.getProject();
            Intrinsics.checkNotNullExpressionValue(project2, "getProject(...)");
            for (Object obj3 : companion2.getInstances(project2)) {
                if (((ShellExtension) obj3).isAccepted(k2JVMCompilerArguments)) {
                    obj = obj3;
                    break;
                }
            }
            ShellExtension shellExtension = (ShellExtension) obj;
            if (shellExtension == null) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Unable to run REPL, no scripting plugin loaded", null, 4, null);
                return ExitCode.COMPILATION_ERROR;
            }
            return shellExtension.run(k2JVMCompilerArguments, configuration, projectEnvironment);
        }
        if (arguments.getUseOldBackend()) {
            CompilerMessageSeverity compilerMessageSeverity = isUseOldBackendAllowed() ? CompilerMessageSeverity.WARNING : CompilerMessageSeverity.ERROR;
            MessageCollector.report$default(messageCollector, compilerMessageSeverity, "-Xuse-old-backend is no longer supported. Please migrate to the new JVM IR backend", null, 4, null);
            if (compilerMessageSeverity == CompilerMessageSeverity.ERROR) {
                return ExitCode.COMPILATION_ERROR;
            }
        }
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.LOGGING, "Configuring the compilation environment", null, 4, null);
        try {
            String buildFile = arguments.getBuildFile();
            File file = buildFile != null ? new File(buildFile) : null;
            List<Module> modules = K2JVMCompilerKt.configureModuleChunk(configuration, arguments, file).getModules();
            Intrinsics.checkNotNullExpressionValue(modules, "getModules(...)");
            KotlinToJVMBytecodeCompilerKt.configureSourceRoots(configuration, modules, file);
            JvmContentRootsKt.configureJdkClasspathRoots(configuration);
            List<Module> list = modules;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (Module module : list) {
                arrayList.add(module.getName() + '-' + module.getType());
            }
            ArrayList arrayList2 = arrayList;
            String strJoinToString$default = (String) CollectionsKt.singleOrNull((List) arrayList2);
            if (strJoinToString$default == null) {
                strJoinToString$default = CollectionsKt.joinToString$default(arrayList2, null, null, null, 0, null, null, 63, null);
            }
            String str = strJoinToString$default;
            if (configuration.getBoolean(CommonConfigurationKeys.USE_FIR) && configuration.getBoolean(CommonConfigurationKeys.USE_LIGHT_TREE)) {
                CompilerPipelineKt.compileModulesUsingFrontendIrAndLightTree(CompilerPipelineKt.createProjectEnvironment(configuration, rootDisposable, EnvironmentConfigFiles.JVM_CONFIG_FILES, messageCollector), configuration, messageCollector, file, modules, str);
            } else {
                KotlinCoreEnvironment kotlinCoreEnvironmentCreateCoreEnvironment = createCoreEnvironment(rootDisposable, configuration, messageCollector, str);
                if (kotlinCoreEnvironmentCreateCoreEnvironment == null) {
                    return ExitCode.COMPILATION_ERROR;
                }
                if (!JvmArgumentsKt.registerJavacIfNeeded(kotlinCoreEnvironmentCreateCoreEnvironment, arguments)) {
                    return ExitCode.COMPILATION_ERROR;
                }
                if (kotlinCoreEnvironmentCreateCoreEnvironment.getSourceFiles().isEmpty() && !arguments.getAllowNoSourceFiles() && file == null) {
                    if (arguments.getVersion()) {
                        return ExitCode.OK;
                    }
                    MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "No source files", null, 4, null);
                    return ExitCode.COMPILATION_ERROR;
                }
                KotlinToJVMBytecodeCompiler.compileModules$cli$default(KotlinToJVMBytecodeCompiler.INSTANCE, kotlinCoreEnvironmentCreateCoreEnvironment, file, modules, false, 8, null);
            }
            return ExitCode.OK;
        } catch (CompilationException e) {
            CompilerMessageSeverity compilerMessageSeverity2 = CompilerMessageSeverity.EXCEPTION;
            String strRenderException = OutputMessageUtil.renderException(e);
            Intrinsics.checkNotNullExpressionValue(strRenderException, "renderException(...)");
            messageCollector.report(compilerMessageSeverity2, strRenderException, MessageUtil.psiElementToMessageLocation(e.getElement()));
            return ExitCode.INTERNAL_ERROR;
        }
    }

    protected void addPlatformOptions(List<String> list, K2JVMCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        String[] scriptTemplates = arguments.getScriptTemplates();
        if (scriptTemplates != null) {
            if (!(scriptTemplates.length == 0)) {
                StringBuilder sb = new StringBuilder("plugin:kotlin.scripting:script-templates=");
                String[] scriptTemplates2 = arguments.getScriptTemplates();
                Intrinsics.checkNotNull(scriptTemplates2);
                list.add(sb.append(ArraysKt.joinToString$default(scriptTemplates2, ",", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString());
            }
        }
        String[] scriptResolverEnvironment = arguments.getScriptResolverEnvironment();
        if (scriptResolverEnvironment != null) {
            if (!(scriptResolverEnvironment.length == 0)) {
                StringBuilder sb2 = new StringBuilder("plugin:kotlin.scripting:script-resolver-environment=");
                String[] scriptResolverEnvironment2 = arguments.getScriptResolverEnvironment();
                Intrinsics.checkNotNull(scriptResolverEnvironment2);
                list.add(sb2.append(ArraysKt.joinToString$default(scriptResolverEnvironment2, ",", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString());
            }
        }
    }

    private final KotlinCoreEnvironment createCoreEnvironment(Disposable rootDisposable, CompilerConfiguration configuration, MessageCollector messageCollector, String targetDescription) {
        if (messageCollector.getHasErrors()) {
            return null;
        }
        KotlinCoreEnvironment kotlinCoreEnvironmentCreateForProduction = KotlinCoreEnvironment.INSTANCE.createForProduction(rootDisposable, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES);
        List<KtFile> sourceFiles = kotlinCoreEnvironmentCreateForProduction.getSourceFiles();
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) configuration.get(CLIConfigurationKeys.PERF_MANAGER);
        if (commonCompilerPerformanceManager != null) {
            commonCompilerPerformanceManager.notifyCompilerInitialized(sourceFiles.size(), kotlinCoreEnvironmentCreateForProduction.countLinesOfCode(sourceFiles), targetDescription);
        }
        if (messageCollector.getHasErrors()) {
            return null;
        }
        return kotlinCoreEnvironmentCreateForProduction;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public void setupPlatformSpecificArgumentsAndServices(CompilerConfiguration configuration, K2JVMCompilerArguments arguments, Services services) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(services, "services");
        if (UtilsKt.incrementalCompilationIsEnabled(arguments)) {
            configuration.putIfNotNull(CommonConfigurationKeys.LOOKUP_TRACKER, services.get(LookupTracker.class));
            configuration.putIfNotNull(CommonConfigurationKeys.EXPECT_ACTUAL_TRACKER, services.get(ExpectActualTracker.class));
            configuration.putIfNotNull(CommonConfigurationKeys.INLINE_CONST_TRACKER, services.get(InlineConstTracker.class));
            configuration.putIfNotNull(CommonConfigurationKeys.ENUM_WHEN_TRACKER, services.get(EnumWhenTracker.class));
            configuration.putIfNotNull(CommonConfigurationKeys.IMPORT_TRACKER, services.get(ImportTracker.class));
            configuration.putIfNotNull(JVMConfigurationKeys.INCREMENTAL_COMPILATION_COMPONENTS, services.get(IncrementalCompilationComponents.class));
            configuration.putIfNotNull(ClassicFrontendSpecificJvmConfigurationKeys.JAVA_CLASSES_TRACKER, services.get(JavaClassesTracker.class));
        }
        JvmArgumentsKt.setupJvmSpecificArguments(configuration, arguments);
    }

    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public K2JVMCompilerArguments createArguments() {
        K2JVMCompilerArguments k2JVMCompilerArguments = new K2JVMCompilerArguments();
        if (System.getenv("KOTLIN_REPORT_PERF") != null) {
            k2JVMCompilerArguments.setReportPerf(true);
        }
        return k2JVMCompilerArguments;
    }

    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public String executableScriptFileName() {
        return "kotlinc-jvm";
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    protected BinaryVersion createMetadataVersion(int[] versionArray) {
        Intrinsics.checkNotNullParameter(versionArray, "versionArray");
        return new JvmMetadataVersion(Arrays.copyOf(versionArray, versionArray.length));
    }

    /* JADX INFO: compiled from: K2JVMCompiler.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/K2JVMCompiler$K2JVMCompilerPerformanceManager;", "Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "()V", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class K2JVMCompilerPerformanceManager extends CommonCompilerPerformanceManager {
        public K2JVMCompilerPerformanceManager() {
            super("Kotlin to JVM Compiler");
        }
    }

    /* JADX INFO: compiled from: K2JVMCompiler.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0007¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/K2JVMCompiler$Companion;", "", "()V", "main", "", "args", "", "", "([Ljava/lang/String;)V", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void main(String[] args) {
            Intrinsics.checkNotNullParameter(args, "args");
            CLITool.INSTANCE.doMain(new K2JVMCompiler(), args);
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public CommonCompilerPerformanceManager getDefaultPerformanceManager() {
        return this.defaultPerformanceManager;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public CommonCompilerPerformanceManager createPerformanceManager(K2JVMCompilerArguments arguments, Services services) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(services, "services");
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) services.get(CommonCompilerPerformanceManager.class);
        if (commonCompilerPerformanceManager != null) {
            return commonCompilerPerformanceManager;
        }
        String profileCompilerCommand = arguments.getProfileCompilerCommand();
        return profileCompilerCommand == null ? getDefaultPerformanceManager() : ProfilingCompilerPerformanceManager.INSTANCE.create(profileCompilerCommand);
    }

    private final boolean isUseOldBackendAllowed() {
        return K2JVMCompiler.class.getClassLoader().getResource("META-INF/unsafe-allow-use-old-backend") != null;
    }
}
