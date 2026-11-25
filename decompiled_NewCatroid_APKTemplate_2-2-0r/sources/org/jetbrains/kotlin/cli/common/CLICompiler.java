package org.jetbrains.kotlin.cli.common;

import com.reandroid.apk.UncompressedFiles;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.analyzer.CompilationErrorException;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.environment.UtilKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector;
import org.jetbrains.kotlin.cli.common.messages.IrMessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageCollectorUtil;
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer;
import org.jetbrains.kotlin.cli.jvm.plugins.PluginCliParser;
import org.jetbrains.kotlin.cli.plugins.PluginClasspathAndOptions;
import org.jetbrains.kotlin.cli.plugins.PluginsOptionsParserKt;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor;
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar;
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.progress.CompilationCanceledException;
import org.jetbrains.kotlin.progress.CompilationCanceledStatus;
import org.jetbrains.kotlin.progress.IncrementalNextRoundException;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.utils.KotlinPaths;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: CLICompiler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 =*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001=B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH$J\u001d\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00020\u0010H\u0014¢\u0006\u0002\u0010\u0011J/\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H$¢\u0006\u0002\u0010\u001aJ/\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u001e\u001a\n\u0012\u0006\b\u0001\u0012\u00020 0\u001f\"\u00020 ¢\u0006\u0002\u0010!J!\u0010\"\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f¢\u0006\u0002\u0010#J%\u0010$\u001a\u00020\u00132\u0006\u0010%\u001a\u00020&2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010'J'\u0010(\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00020\u0015H\u0004¢\u0006\u0002\u0010)J\u001e\u0010*\u001a\u00020+2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020 0-2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001d\u0010.\u001a\u00020+2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000e\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010/J%\u00100\u001a\u00020+2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00020\u0010H$¢\u0006\u0002\u00101J&\u00102\u001a\u0002032\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010,\u001a\b\u0012\u0004\u0012\u00020 0-2\u0006\u00104\u001a\u000203H\u0002J\u001f\u00105\u001a\u00020+*\b\u0012\u0004\u0012\u00020 062\u0006\u0010\u000e\u001a\u00028\u0000H$¢\u0006\u0002\u00107J\f\u00108\u001a\u000203*\u000209H\u0002J\u0014\u0010:\u001a\u00020+*\u00020&2\u0006\u0010;\u001a\u00020<H\u0002R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006>"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/CLICompiler;", "A", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "Lorg/jetbrains/kotlin/cli/common/CLITool;", "()V", "defaultPerformanceManager", "Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "getDefaultPerformanceManager", "()Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "createMetadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "versionArray", "", "createPerformanceManager", "arguments", "services", "Lorg/jetbrains/kotlin/config/Services;", "(Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/config/Services;)Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "doExecute", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "rootDisposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", UncompressedFiles.NAME_paths, "Lorg/jetbrains/kotlin/utils/KotlinPaths;", "(Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lcom/intellij/openapi/Disposable;Lorg/jetbrains/kotlin/utils/KotlinPaths;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "execAndOutputXml", "errStream", "Ljava/io/PrintStream;", "args", "", "", "(Ljava/io/PrintStream;Lorg/jetbrains/kotlin/config/Services;[Ljava/lang/String;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "execFullPathsInMessages", "(Ljava/io/PrintStream;[Ljava/lang/String;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "execImpl", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Lorg/jetbrains/kotlin/config/Services;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "loadPlugins", "(Lorg/jetbrains/kotlin/utils/KotlinPaths;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "processScriptPluginCliOptions", "", "pluginOptions", "", "setupCommonArguments", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;)V", "setupPlatformSpecificArgumentsAndServices", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/config/Services;)V", "tryLoadScriptingPluginFromCurrentClassLoader", "", "useK2", "addPlatformOptions", "", "(Ljava/util/List;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;)V", "hasOOMCause", "", "reportCompilationCancelled", "e", "Lorg/jetbrains/kotlin/progress/CompilationCanceledException;", "Companion", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CLICompiler<A extends CommonCompilerArguments> extends CLITool<A> {
    public static final String SCRIPT_PLUGIN_COMMANDLINE_PROCESSOR_NAME = "org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingCommandLineProcessor";
    public static final String SCRIPT_PLUGIN_K2_REGISTRAR_NAME = "org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingK2CompilerPluginRegistrar";
    public static final String SCRIPT_PLUGIN_REGISTRAR_NAME = "org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingCompilerConfigurationComponentRegistrar";

    protected abstract void addPlatformOptions(List<String> list, A a);

    protected abstract BinaryVersion createMetadataVersion(int[] versionArray);

    protected abstract ExitCode doExecute(A a, CompilerConfiguration compilerConfiguration, Disposable disposable, KotlinPaths kotlinPaths);

    public abstract CommonCompilerPerformanceManager getDefaultPerformanceManager();

    protected abstract void setupPlatformSpecificArgumentsAndServices(CompilerConfiguration configuration, A arguments, Services services);

    protected CommonCompilerPerformanceManager createPerformanceManager(A arguments, Services services) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(services, "services");
        return getDefaultPerformanceManager();
    }

    public final ExitCode execAndOutputXml(PrintStream errStream, Services services, String... args) {
        Intrinsics.checkNotNullParameter(errStream, "errStream");
        Intrinsics.checkNotNullParameter(services, "services");
        Intrinsics.checkNotNullParameter(args, "args");
        MessageRenderer XML = MessageRenderer.XML;
        Intrinsics.checkNotNullExpressionValue(XML, "XML");
        return exec(errStream, services, XML, args);
    }

    public final ExitCode execFullPathsInMessages(PrintStream errStream, String[] args) {
        Intrinsics.checkNotNullParameter(errStream, "errStream");
        Intrinsics.checkNotNullParameter(args, "args");
        Services services = Services.EMPTY;
        MessageRenderer PLAIN_FULL_PATHS = MessageRenderer.PLAIN_FULL_PATHS;
        Intrinsics.checkNotNullExpressionValue(PLAIN_FULL_PATHS, "PLAIN_FULL_PATHS");
        return exec(errStream, services, PLAIN_FULL_PATHS, args);
    }

    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public ExitCode execImpl(MessageCollector messageCollector, Services services, A arguments) {
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(services, "services");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        CommonCompilerPerformanceManager commonCompilerPerformanceManagerCreatePerformanceManager = createPerformanceManager(arguments, services);
        if (arguments.getReportPerf() || arguments.getDumpPerf() != null) {
            commonCompilerPerformanceManagerCreatePerformanceManager.enableCollectingPerformanceStatistics();
        }
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.put(CLIConfigurationKeys.ORIGINAL_MESSAGE_COLLECTOR_KEY, messageCollector);
        GroupingMessageCollector groupingMessageCollector = new GroupingMessageCollector(messageCollector, arguments.getAllWarningsAsErrors());
        compilerConfiguration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, groupingMessageCollector);
        compilerConfiguration.put(IrMessageLogger.INSTANCE.getIR_MESSAGE_LOGGER(), new IrMessageCollector(groupingMessageCollector));
        compilerConfiguration.put(CLIConfigurationKeys.PERF_MANAGER, commonCompilerPerformanceManagerCreatePerformanceManager);
        try {
            try {
                setupCommonArguments(compilerConfiguration, arguments);
                setupPlatformSpecificArgumentsAndServices(compilerConfiguration, arguments, services);
                KotlinPaths kotlinPathsComputeKotlinPaths = ArgumentsKt.computeKotlinPaths(groupingMessageCollector, arguments);
                if (groupingMessageCollector.hasErrors()) {
                    ExitCode exitCode = ExitCode.COMPILATION_ERROR;
                    groupingMessageCollector.flush();
                    return exitCode;
                }
                ProgressIndicatorAndCompilationCanceledStatus.setCompilationCanceledStatus((CompilationCanceledStatus) services.get(CompilationCanceledStatus.class));
                Disposable disposableNewDisposable = Disposer.newDisposable();
                Intrinsics.checkNotNullExpressionValue(disposableNewDisposable, "newDisposable(...)");
                try {
                    try {
                        UtilKt.setIdeaIoUseFallback();
                        ExitCode exitCodeDoExecute = doExecute(arguments, compilerConfiguration, disposableNewDisposable, kotlinPathsComputeKotlinPaths);
                        commonCompilerPerformanceManagerCreatePerformanceManager.notifyCompilationFinished();
                        if (arguments.getReportPerf()) {
                            MessageCollector.report$default(groupingMessageCollector, CompilerMessageSeverity.LOGGING, "PERF: " + commonCompilerPerformanceManagerCreatePerformanceManager.getTargetInfo(), null, 4, null);
                            Iterator<PerformanceMeasurement> it = commonCompilerPerformanceManagerCreatePerformanceManager.getMeasurementResults().iterator();
                            while (it.hasNext()) {
                                groupingMessageCollector.report(CompilerMessageSeverity.LOGGING, "PERF: " + it.next().getCounterReport(), null);
                            }
                        }
                        if (arguments.getDumpPerf() != null) {
                            String dumpPerf = arguments.getDumpPerf();
                            Intrinsics.checkNotNull(dumpPerf);
                            commonCompilerPerformanceManagerCreatePerformanceManager.dumpPerformanceReport(new File(dumpPerf));
                        }
                        if (groupingMessageCollector.hasErrors()) {
                            exitCodeDoExecute = ExitCode.COMPILATION_ERROR;
                        }
                        Disposer.dispose(disposableNewDisposable);
                        groupingMessageCollector.flush();
                        return exitCodeDoExecute;
                    } catch (Throwable th) {
                        Disposer.dispose(disposableNewDisposable);
                        throw th;
                    }
                } catch (CompilationCanceledException e) {
                    reportCompilationCancelled(groupingMessageCollector, e);
                    ExitCode exitCode2 = ExitCode.OK;
                    Disposer.dispose(disposableNewDisposable);
                    groupingMessageCollector.flush();
                    return exitCode2;
                } catch (RuntimeException e2) {
                    Throwable cause = e2.getCause();
                    if (!(cause instanceof CompilationCanceledException)) {
                        throw e2;
                    }
                    reportCompilationCancelled(groupingMessageCollector, (CompilationCanceledException) cause);
                    ExitCode exitCode3 = ExitCode.OK;
                    Disposer.dispose(disposableNewDisposable);
                    groupingMessageCollector.flush();
                    return exitCode3;
                }
            } catch (Throwable th2) {
                groupingMessageCollector.flush();
                throw th2;
            }
        } catch (CompilationErrorException unused) {
            ExitCode exitCode4 = ExitCode.COMPILATION_ERROR;
            groupingMessageCollector.flush();
            return exitCode4;
        } catch (Throwable th3) {
            MessageCollectorUtil.reportException(groupingMessageCollector, th3);
            ExitCode exitCode5 = ((th3 instanceof OutOfMemoryError) || hasOOMCause(th3)) ? ExitCode.OOM_ERROR : ExitCode.INTERNAL_ERROR;
            groupingMessageCollector.flush();
            return exitCode5;
        }
    }

    private final boolean hasOOMCause(Throwable th) {
        if (th.getCause() instanceof OutOfMemoryError) {
            return true;
        }
        Throwable cause = th.getCause();
        if (cause != null) {
            return hasOOMCause(cause);
        }
        return false;
    }

    private final void reportCompilationCancelled(MessageCollector messageCollector, CompilationCanceledException compilationCanceledException) {
        if (compilationCanceledException instanceof IncrementalNextRoundException) {
            return;
        }
        messageCollector.report(CompilerMessageSeverity.INFO, "Compilation was canceled", null);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.common.CLICompiler$setupCommonArguments$1, reason: invalid class name */
    /* JADX INFO: compiled from: CLICompiler.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<int[], BinaryVersion> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "createMetadataVersion([I)Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "createMetadataVersion";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(CLICompiler.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final BinaryVersion invoke(int[] p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return ((CLICompiler) this.receiver).createMetadataVersion(p0);
        }
    }

    private final void setupCommonArguments(CompilerConfiguration configuration, A arguments) {
        ArgumentsKt.setupCommonArguments(configuration, arguments, new AnonymousClass1(this));
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:42:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:48:0x0119 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:49:0x011b  */
    /* JADX WARN: Code duplicated, block: B:50:0x0120  */
    /* JADX WARN: Code duplicated, block: B:53:0x012c  */
    /* JADX WARN: Code duplicated, block: B:57:0x0135  */
    /* JADX WARN: Code duplicated, block: B:60:0x014e A[LOOP:0: B:59:0x014c->B:60:0x014e, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:64:0x0173  */
    /* JADX WARN: Code duplicated, block: B:70:0x019f  */
    /* JADX WARN: Code duplicated, block: B:73:0x01b8 A[LOOP:2: B:71:0x01b2->B:73:0x01b8, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:75:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:84:0x0184 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:86:0x0180 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:91:0x0110 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:92:0x0116 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:93:? A[LOOP:3: B:40:0x00ef->B:93:?, LOOP_END, SYNTHETIC] */
    protected final ExitCode loadPlugins(KotlinPaths paths, A arguments, CompilerConfiguration configuration) {
        ArrayList arrayList;
        List list;
        Iterator it;
        String name2;
        KotlinPaths kotlinPathsForCompiler;
        File libPath;
        ArrayList arrayList2;
        int i;
        ArrayList arrayList3;
        ArrayList arrayList4;
        List list2;
        List list3;
        ArrayList arrayList5;
        Iterator it2;
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        String[] pluginClasspaths = arguments.getPluginClasspaths();
        if (pluginClasspaths == null) {
            pluginClasspaths = new String[0];
        }
        List mutableList = ArraysKt.toMutableList(pluginClasspaths);
        String[] pluginOptions = arguments.getPluginOptions();
        if (pluginOptions == null) {
            pluginOptions = new String[0];
        }
        List<String> mutableList2 = ArraysKt.toMutableList(pluginOptions);
        String[] pluginConfigurations = arguments.getPluginConfigurations();
        if (pluginConfigurations == null) {
            pluginConfigurations = new String[0];
        }
        List mutableList3 = ArraysKt.toMutableList(pluginConfigurations);
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        boolean zAreEqual = Intrinsics.areEqual(configuration.get(CommonConfigurationKeys.USE_FIR), (Object) true);
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        if (arguments.getDisableDefaultScriptingPlugin()) {
            arrayList = arrayList7;
            arrayList.add("plugin:kotlin.scripting:disable=true");
        } else {
            addPlatformOptions(arrayList7, arguments);
            List<PluginClasspathAndOptions> listExtractPluginClasspathAndOptions = PluginsOptionsParserKt.extractPluginClasspathAndOptions(mutableList3);
            File file = null;
            if ((listExtractPluginClasspathAndOptions instanceof Collection) && listExtractPluginClasspathAndOptions.isEmpty()) {
                list = mutableList;
                if (list instanceof Collection) {
                    it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            name2 = new File((String) it.next()).getName();
                            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                            if (StringsKt.startsWith$default(name2, PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME, false, 2, (Object) null)) {
                            }
                        } else if (!tryLoadScriptingPluginFromCurrentClassLoader(configuration, mutableList2, zAreEqual)) {
                            if (paths == null) {
                                kotlinPathsForCompiler = PathUtil.getKotlinPathsForCompiler();
                            } else {
                                kotlinPathsForCompiler = paths;
                            }
                            libPath = kotlinPathsForCompiler.getLibPath();
                            if (libPath.exists()) {
                                file = libPath;
                            }
                            if (file == null) {
                                file = new File(".");
                            }
                            String[] kotlin_scripting_plugin_classpath_jars = PathUtil.INSTANCE.getKOTLIN_SCRIPTING_PLUGIN_CLASSPATH_JARS();
                            arrayList2 = new ArrayList(kotlin_scripting_plugin_classpath_jars.length);
                            for (String str : kotlin_scripting_plugin_classpath_jars) {
                                arrayList2.add(new File(file, str));
                            }
                            arrayList3 = new ArrayList();
                            arrayList4 = new ArrayList();
                            for (Object obj : arrayList2) {
                                if (((File) obj).exists()) {
                                    arrayList3.add(obj);
                                } else {
                                    arrayList4.add(obj);
                                }
                            }
                            Pair pair = new Pair(arrayList3, arrayList4);
                            list2 = (List) pair.component1();
                            list3 = (List) pair.component2();
                            if (list3.isEmpty()) {
                                List list4 = list2;
                                arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
                                it2 = list4.iterator();
                                while (it2.hasNext()) {
                                    arrayList5.add(((File) it2.next()).getCanonicalPath());
                                }
                                arrayList6.addAll(0, arrayList5);
                            } else {
                                String str2 = "Scripting plugin will not be loaded: not all required jars are present in the classpath (missing files: " + list3 + ')';
                                arrayList = arrayList7;
                                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.LOGGING, str2, null, 4, null);
                            }
                        }
                    }
                } else {
                    it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            name2 = new File((String) it.next()).getName();
                            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                            if (StringsKt.startsWith$default(name2, PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME, false, 2, (Object) null)) {
                            }
                        } else if (!tryLoadScriptingPluginFromCurrentClassLoader(configuration, mutableList2, zAreEqual)) {
                            if (paths == null) {
                                kotlinPathsForCompiler = PathUtil.getKotlinPathsForCompiler();
                            } else {
                                kotlinPathsForCompiler = paths;
                            }
                            libPath = kotlinPathsForCompiler.getLibPath();
                            if (libPath.exists()) {
                                file = libPath;
                            }
                            if (file == null) {
                                file = new File(".");
                            }
                            String[] kotlin_scripting_plugin_classpath_jars2 = PathUtil.INSTANCE.getKOTLIN_SCRIPTING_PLUGIN_CLASSPATH_JARS();
                            arrayList2 = new ArrayList(kotlin_scripting_plugin_classpath_jars2.length);
                            while (i < r9) {
                                arrayList2.add(new File(file, str));
                            }
                            arrayList3 = new ArrayList();
                            arrayList4 = new ArrayList();
                            while (r4.hasNext()) {
                                if (((File) obj).exists()) {
                                    arrayList3.add(obj);
                                } else {
                                    arrayList4.add(obj);
                                }
                            }
                            Pair pair2 = new Pair(arrayList3, arrayList4);
                            list2 = (List) pair2.component1();
                            list3 = (List) pair2.component2();
                            if (list3.isEmpty()) {
                                List list5 = list2;
                                arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
                                it2 = list5.iterator();
                                while (it2.hasNext()) {
                                    arrayList5.add(((File) it2.next()).getCanonicalPath());
                                }
                                arrayList6.addAll(0, arrayList5);
                            } else {
                                String str3 = "Scripting plugin will not be loaded: not all required jars are present in the classpath (missing files: " + list3 + ')';
                                arrayList = arrayList7;
                                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.LOGGING, str3, null, 4, null);
                            }
                        }
                    }
                }
                arrayList = arrayList7;
            } else {
                Iterator it3 = listExtractPluginClasspathAndOptions.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        List<String> listComponent2 = ((PluginClasspathAndOptions) it3.next()).component2();
                        if (!(listComponent2 instanceof Collection) || !listComponent2.isEmpty()) {
                            Iterator<T> it4 = listComponent2.iterator();
                            while (true) {
                                if (it4.hasNext()) {
                                    String name3 = new File((String) it4.next()).getName();
                                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                                    Iterator it5 = it3;
                                    if (!StringsKt.startsWith$default(name3, PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME, false, 2, (Object) null)) {
                                        it3 = it5;
                                    }
                                }
                            }
                        }
                        it3 = it3;
                    } else {
                        list = mutableList;
                        if ((list instanceof Collection) || !list.isEmpty()) {
                            it = list.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    name2 = new File((String) it.next()).getName();
                                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                                    if (StringsKt.startsWith$default(name2, PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME, false, 2, (Object) null)) {
                                    }
                                } else if (!tryLoadScriptingPluginFromCurrentClassLoader(configuration, mutableList2, zAreEqual)) {
                                    if (paths == null) {
                                        kotlinPathsForCompiler = PathUtil.getKotlinPathsForCompiler();
                                    } else {
                                        kotlinPathsForCompiler = paths;
                                    }
                                    libPath = kotlinPathsForCompiler.getLibPath();
                                    if (libPath.exists() && libPath.isDirectory()) {
                                        file = libPath;
                                    }
                                    if (file == null) {
                                        file = new File(".");
                                    }
                                    String[] kotlin_scripting_plugin_classpath_jars3 = PathUtil.INSTANCE.getKOTLIN_SCRIPTING_PLUGIN_CLASSPATH_JARS();
                                    arrayList2 = new ArrayList(kotlin_scripting_plugin_classpath_jars3.length);
                                    while (i < r9) {
                                        arrayList2.add(new File(file, str));
                                    }
                                    arrayList3 = new ArrayList();
                                    arrayList4 = new ArrayList();
                                    while (r4.hasNext()) {
                                        if (((File) obj).exists()) {
                                            arrayList3.add(obj);
                                        } else {
                                            arrayList4.add(obj);
                                        }
                                    }
                                    Pair pair3 = new Pair(arrayList3, arrayList4);
                                    list2 = (List) pair3.component1();
                                    list3 = (List) pair3.component2();
                                    if (list3.isEmpty()) {
                                        List list6 = list2;
                                        arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list6, 10));
                                        it2 = list6.iterator();
                                        while (it2.hasNext()) {
                                            arrayList5.add(((File) it2.next()).getCanonicalPath());
                                        }
                                        arrayList6.addAll(0, arrayList5);
                                    } else {
                                        String str4 = "Scripting plugin will not be loaded: not all required jars are present in the classpath (missing files: " + list3 + ')';
                                        arrayList = arrayList7;
                                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.LOGGING, str4, null, 4, null);
                                    }
                                }
                            }
                        } else if (!tryLoadScriptingPluginFromCurrentClassLoader(configuration, mutableList2, zAreEqual)) {
                            if (paths == null) {
                                kotlinPathsForCompiler = PathUtil.getKotlinPathsForCompiler();
                            } else {
                                kotlinPathsForCompiler = paths;
                            }
                            libPath = kotlinPathsForCompiler.getLibPath();
                            if (libPath.exists()) {
                                file = libPath;
                            }
                            if (file == null) {
                                file = new File(".");
                            }
                            String[] kotlin_scripting_plugin_classpath_jars4 = PathUtil.INSTANCE.getKOTLIN_SCRIPTING_PLUGIN_CLASSPATH_JARS();
                            arrayList2 = new ArrayList(kotlin_scripting_plugin_classpath_jars4.length);
                            while (i < r9) {
                                arrayList2.add(new File(file, str));
                            }
                            arrayList3 = new ArrayList();
                            arrayList4 = new ArrayList();
                            while (r4.hasNext()) {
                                if (((File) obj).exists()) {
                                    arrayList3.add(obj);
                                } else {
                                    arrayList4.add(obj);
                                }
                            }
                            Pair pair4 = new Pair(arrayList3, arrayList4);
                            list2 = (List) pair4.component1();
                            list3 = (List) pair4.component2();
                            if (list3.isEmpty()) {
                                List list7 = list2;
                                arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list7, 10));
                                it2 = list7.iterator();
                                while (it2.hasNext()) {
                                    arrayList5.add(((File) it2.next()).getCanonicalPath());
                                }
                                arrayList6.addAll(0, arrayList5);
                            } else {
                                String str5 = "Scripting plugin will not be loaded: not all required jars are present in the classpath (missing files: " + list3 + ')';
                                arrayList = arrayList7;
                                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.LOGGING, str5, null, 4, null);
                            }
                        }
                    }
                    arrayList = arrayList7;
                }
            }
        }
        mutableList.addAll(arrayList6);
        mutableList2.addAll(arrayList);
        return !CLICompilerKt.checkPluginsArguments(messageCollector, zAreEqual, mutableList, mutableList2, mutableList3) ? ExitCode.INTERNAL_ERROR : PluginCliParser.loadPluginsSafe(mutableList, mutableList2, mutableList3, configuration);
    }

    private final boolean tryLoadScriptingPluginFromCurrentClassLoader(CompilerConfiguration configuration, List<String> pluginOptions, boolean useK2) {
        try {
            Object objNewInstance = PluginCliParser.class.getClassLoader().loadClass(SCRIPT_PLUGIN_REGISTRAR_NAME).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            CompilerPluginRegistrar compilerPluginRegistrar = null;
            ComponentRegistrar componentRegistrar = objNewInstance instanceof ComponentRegistrar ? (ComponentRegistrar) objNewInstance : null;
            if (componentRegistrar != null) {
                configuration.add(ComponentRegistrar.INSTANCE.getPLUGIN_COMPONENT_REGISTRARS(), componentRegistrar);
            } else {
                componentRegistrar = null;
            }
            if (useK2) {
                Object objNewInstance2 = PluginCliParser.class.getClassLoader().loadClass(SCRIPT_PLUGIN_K2_REGISTRAR_NAME).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                CompilerPluginRegistrar compilerPluginRegistrar2 = objNewInstance2 instanceof CompilerPluginRegistrar ? (CompilerPluginRegistrar) objNewInstance2 : null;
                if (compilerPluginRegistrar2 != null) {
                    configuration.add(CompilerPluginRegistrar.INSTANCE.getCOMPILER_PLUGIN_REGISTRARS(), compilerPluginRegistrar2);
                    compilerPluginRegistrar = compilerPluginRegistrar2;
                }
            }
            if (componentRegistrar == null && compilerPluginRegistrar == null) {
                return false;
            }
            processScriptPluginCliOptions(pluginOptions, configuration);
            return true;
        } catch (Throwable th) {
            Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
            Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
            MessageCollector.report$default((MessageCollector) notNull, CompilerMessageSeverity.LOGGING, "Exception on loading scripting plugin: " + th, null, 4, null);
            return false;
        }
    }

    private final void processScriptPluginCliOptions(List<String> pluginOptions, CompilerConfiguration configuration) throws ClassNotFoundException {
        Class<?> clsLoadClass;
        Constructor<?> declaredConstructor;
        if (pluginOptions.isEmpty()) {
            clsLoadClass = null;
        } else {
            clsLoadClass = PluginCliParser.class.getClassLoader().loadClass(SCRIPT_PLUGIN_COMMANDLINE_PROCESSOR_NAME);
            Intrinsics.checkNotNull(clsLoadClass);
        }
        Object objNewInstance = (clsLoadClass == null || (declaredConstructor = clsLoadClass.getDeclaredConstructor(new Class[0])) == null) ? null : declaredConstructor.newInstance(new Object[0]);
        CommandLineProcessor commandLineProcessor = objNewInstance instanceof CommandLineProcessor ? (CommandLineProcessor) objNewInstance : null;
        if (commandLineProcessor != null) {
            PluginsOptionsParserKt.processCompilerPluginsOptions(configuration, pluginOptions, CollectionsKt.listOf(commandLineProcessor));
        }
    }
}
