package org.jetbrains.kotlin.cli.metadata;

import com.reandroid.apk.UncompressedFiles;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.CLICompiler;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CLITool;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2MetadataCompilerArguments;
import org.jetbrains.kotlin.cli.common.config.ContentRootsKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageUtil;
import org.jetbrains.kotlin.cli.common.messages.OutputMessageUtil;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.cli.jvm.config.K2MetadataConfigurationKeys;
import org.jetbrains.kotlin.codegen.CompilationException;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.HmppCliModuleStructure;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.utils.KotlinPaths;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: K2MetadataCompiler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\u0018\u0000 \u001e2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u001e\u001fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\u0002H\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J*\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0017H\u0016J \u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u001bH\u0014J\u001a\u0010\u001c\u001a\u00020\u0019*\b\u0012\u0004\u0012\u00020\u00170\u001d2\u0006\u0010\u000f\u001a\u00020\u0002H\u0014R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/K2MetadataCompiler;", "Lorg/jetbrains/kotlin/cli/common/CLICompiler;", "Lorg/jetbrains/kotlin/cli/common/arguments/K2MetadataCompilerArguments;", "()V", "defaultPerformanceManager", "Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "getDefaultPerformanceManager", "()Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "createArguments", "createMetadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "versionArray", "", "doExecute", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "arguments", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "rootDisposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", UncompressedFiles.NAME_paths, "Lorg/jetbrains/kotlin/utils/KotlinPaths;", "executableScriptFileName", "", "setupPlatformSpecificArgumentsAndServices", "", "services", "Lorg/jetbrains/kotlin/config/Services;", "addPlatformOptions", "", "Companion", "K2MetadataCompilerPerformanceManager", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2MetadataCompiler extends CLICompiler<K2MetadataCompilerArguments> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final CommonCompilerPerformanceManager defaultPerformanceManager = new K2MetadataCompilerPerformanceManager();

    @JvmStatic
    public static final void main(String[] strArr) {
        INSTANCE.main(strArr);
    }

    protected void addPlatformOptions(List<String> list, K2MetadataCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public void setupPlatformSpecificArgumentsAndServices(CompilerConfiguration configuration, K2MetadataCompilerArguments arguments, Services services) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(services, "services");
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public /* bridge */ /* synthetic */ void addPlatformOptions(List list, CommonCompilerArguments commonCompilerArguments) {
        addPlatformOptions((List<String>) list, (K2MetadataCompilerArguments) commonCompilerArguments);
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public CommonCompilerPerformanceManager getDefaultPerformanceManager() {
        return this.defaultPerformanceManager;
    }

    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public K2MetadataCompilerArguments createArguments() {
        return new K2MetadataCompilerArguments();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    public ExitCode doExecute(K2MetadataCompilerArguments arguments, CompilerConfiguration configuration, Disposable rootDisposable, KotlinPaths paths) {
        Set setEmptySet;
        MetadataSerializer k2MetadataKlibSerializer;
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(rootDisposable, "rootDisposable");
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        Object notNull2 = configuration.getNotNull(CLIConfigurationKeys.PERF_MANAGER);
        Intrinsics.checkNotNullExpressionValue(notNull2, "getNotNull(...)");
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) notNull2;
        ExitCode exitCodeLoadPlugins = loadPlugins(paths, arguments, configuration);
        if (exitCodeLoadPlugins != ExitCode.OK) {
            return exitCodeLoadPlugins;
        }
        String[] commonSources = arguments.getCommonSources();
        if (commonSources == null || (setEmptySet = ArraysKt.toSet(commonSources)) == null) {
            setEmptySet = SetsKt.emptySet();
        }
        if (((HmppCliModuleStructure) configuration.get(CommonConfigurationKeys.HMPP_MODULE_STRUCTURE)) != null) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "HMPP module structure should not be passed during metadata compilation. Please remove `-Xfragments` and related flags", null, 4, null);
            return ExitCode.COMPILATION_ERROR;
        }
        for (String str : arguments.getFreeArgs()) {
            ContentRootsKt.addKotlinSourceRoot(configuration, str, setEmptySet.contains(str), null);
        }
        if (arguments.getClasspath() != null) {
            String classpath = arguments.getClasspath();
            Intrinsics.checkNotNull(classpath);
            List listSplit$default = StringsKt.split$default((CharSequence) classpath, new char[]{File.pathSeparatorChar}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                arrayList.add(new File((String) it.next()));
            }
            JvmContentRootsKt.addJvmClasspathRoots(configuration, arrayList);
        }
        String moduleName = arguments.getModuleName();
        if (moduleName == null) {
            moduleName = "main";
        }
        configuration.put(CommonConfigurationKeys.MODULE_NAME, moduleName);
        configuration.put(CLIConfigurationKeys.ALLOW_KOTLIN_PACKAGE, Boolean.valueOf(arguments.getAllowKotlinPackage()));
        configuration.put(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME, Boolean.valueOf(arguments.getRenderInternalDiagnosticNames()));
        CompilerConfigurationKey<List<String>> friend_paths = K2MetadataConfigurationKeys.INSTANCE.getFRIEND_PATHS();
        String[] friendPaths = arguments.getFriendPaths();
        configuration.putIfNotNull(friend_paths, friendPaths != null ? ArraysKt.toList(friendPaths) : null);
        CompilerConfigurationKey<List<String>> refines_paths = K2MetadataConfigurationKeys.INSTANCE.getREFINES_PATHS();
        String[] refinesPaths = arguments.getRefinesPaths();
        configuration.putIfNotNull(refines_paths, refinesPaths != null ? ArraysKt.toList(refinesPaths) : null);
        String destination = arguments.getDestination();
        if (destination != null) {
            if (StringsKt.endsWith$default(destination, ".jar", false, 2, (Object) null)) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, ".jar destination is not yet supported, results will be written to the directory with the given name", null, 4, null);
            }
            configuration.put(CLIConfigurationKeys.METADATA_DESTINATION_DIRECTORY, new File(destination));
        }
        KotlinCoreEnvironment kotlinCoreEnvironmentCreateForProduction = KotlinCoreEnvironment.INSTANCE.createForProduction(rootDisposable, configuration, EnvironmentConfigFiles.METADATA_CONFIG_FILES);
        String str2 = arguments.getExpectActualLinker() ? "KLib" : "metadata";
        List<KtFile> sourceFiles = kotlinCoreEnvironmentCreateForProduction.getSourceFiles();
        commonCompilerPerformanceManager.notifyCompilerInitialized(sourceFiles.size(), kotlinCoreEnvironmentCreateForProduction.countLinesOfCode(sourceFiles), str2 + " mode for " + moduleName + " module");
        if (kotlinCoreEnvironmentCreateForProduction.getSourceFiles().isEmpty()) {
            if (arguments.getVersion()) {
                return ExitCode.OK;
            }
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "No source files", null, 4, null);
            return ExitCode.COMPILATION_ERROR;
        }
        UtilsKt.checkKotlinPackageUsageForPsi$default(kotlinCoreEnvironmentCreateForProduction.getConfiguration(), kotlinCoreEnvironmentCreateForProduction.getSourceFiles(), null, 4, null);
        try {
            if (configuration.getBoolean(CommonConfigurationKeys.USE_FIR)) {
                k2MetadataKlibSerializer = new FirMetadataSerializer(configuration, kotlinCoreEnvironmentCreateForProduction);
            } else {
                k2MetadataKlibSerializer = arguments.getExpectActualLinker() ? new K2MetadataKlibSerializer(configuration, kotlinCoreEnvironmentCreateForProduction) : new MetadataSerializer(configuration, kotlinCoreEnvironmentCreateForProduction, true, null, 8, null);
            }
            k2MetadataKlibSerializer.analyzeAndSerialize();
            return ExitCode.OK;
        } catch (CompilationException e) {
            CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.EXCEPTION;
            String strRenderException = OutputMessageUtil.renderException(e);
            Intrinsics.checkNotNullExpressionValue(strRenderException, "renderException(...)");
            messageCollector.report(compilerMessageSeverity, strRenderException, MessageUtil.psiElementToMessageLocation(e.getElement()));
            return ExitCode.INTERNAL_ERROR;
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public String executableScriptFileName() {
        return PathUtil.HOME_FOLDER_NAME;
    }

    @Override // org.jetbrains.kotlin.cli.common.CLICompiler
    protected BinaryVersion createMetadataVersion(int[] versionArray) {
        Intrinsics.checkNotNullParameter(versionArray, "versionArray");
        return new BuiltInsBinaryVersion(Arrays.copyOf(versionArray, versionArray.length));
    }

    /* JADX INFO: compiled from: K2MetadataCompiler.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0007¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/K2MetadataCompiler$Companion;", "", "()V", "main", "", "args", "", "", "([Ljava/lang/String;)V", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void main(String[] args) {
            Intrinsics.checkNotNullParameter(args, "args");
            CLITool.INSTANCE.doMain(new K2MetadataCompiler(), args);
        }
    }

    /* JADX INFO: compiled from: K2MetadataCompiler.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/K2MetadataCompiler$K2MetadataCompilerPerformanceManager;", "Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "()V", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class K2MetadataCompilerPerformanceManager extends CommonCompilerPerformanceManager {
        public K2MetadataCompilerPerformanceManager() {
            super("Kotlin to Metadata compiler");
        }
    }
}
