package org.jetbrains.kotlin.daemon;

import androidx.exifinterface.media.ExifInterface;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.cli.common.CLICompiler;
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.cli.common.environment.UtilKt;
import org.jetbrains.kotlin.cli.js.K2JSCompiler;
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler;
import org.jetbrains.kotlin.cli.jvm.compiler.CompatKt;
import org.jetbrains.kotlin.cli.metadata.K2MetadataCompiler;
import org.jetbrains.kotlin.daemon.common.CompileService;
import org.jetbrains.kotlin.daemon.common.CompilerId;
import org.jetbrains.kotlin.daemon.common.DaemonJVMOptions;
import org.jetbrains.kotlin.daemon.common.DaemonOptions;
import org.jetbrains.kotlin.daemon.common.DaemonParamsKt;
import org.jetbrains.kotlin.daemon.common.NetworkUtilsKt;
import org.jetbrains.kotlin.daemon.common.OptionsGroup;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: KotlinCompileDaemon.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0014J<\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH$J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J\u001b\u0010\u001d\u001a\u00020\u000b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001fH\u0004¢\u0006\u0002\u0010 J\u0012\u0010!\u001a\u0004\u0018\u00010\u00012\u0006\u0010\"\u001a\u00020\u000fH\u0014J!\u0010#\u001a\u0002H$\"\u0004\b\u0000\u0010$2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H$0&H\u0014¢\u0006\u0002\u0010'R#\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/daemon/KotlinCompileDaemonBase;", "", "()V", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Ljava/util/logging/Logger;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getLog", "()Ljava/util/logging/Logger;", "log$delegate", "Lkotlin/Lazy;", "awaitServerRun", "", "serverRun", "getCompileServiceAndPort", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase;", "", "compilerSelector", "Lorg/jetbrains/kotlin/daemon/CompilerSelector;", "compilerId", "Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "daemonOptions", "Lorg/jetbrains/kotlin/daemon/common/DaemonOptions;", "daemonJVMOptions", "Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "timer", "Ljava/util/Timer;", "loadVersionFromResource", "", "mainImpl", "args", "", "([Ljava/lang/String;)V", "runCompileService", "compileService", "runSynchronized", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinCompileDaemonBase {

    /* JADX INFO: renamed from: log$delegate, reason: from kotlin metadata */
    private final Lazy log;

    protected void awaitServerRun(Object serverRun) {
    }

    protected abstract Pair<CompileServiceImplBase, Integer> getCompileServiceAndPort(CompilerSelector compilerSelector, CompilerId compilerId, DaemonOptions daemonOptions, DaemonJVMOptions daemonJVMOptions, Timer timer);

    protected Object runCompileService(CompileServiceImplBase compileService) {
        Intrinsics.checkNotNullParameter(compileService, "compileService");
        return null;
    }

    public KotlinCompileDaemonBase() throws IOException {
        String strTrimQuotes;
        String str = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss-SSS").format(new Date());
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        String value = CompilerSystemProperties.COMPILE_DAEMON_LOG_PATH_PROPERTY.getValue();
        Pair pair = (value == null || (strTrimQuotes = DaemonParamsKt.trimQuotes(value)) == null) ? new Pair("%t", false) : new Pair(strTrimQuotes, Boolean.valueOf(new File(strTrimQuotes).isFile()));
        String str2 = (String) pair.component1();
        boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
        String str3 = "handlers = java.util.logging.FileHandler\njava.util.logging.FileHandler.level     = ALL\njava.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter\njava.util.logging.FileHandler.encoding  = UTF-8\njava.util.logging.FileHandler.limit     = " + (zBooleanValue ? 0 : 1048576) + "\njava.util.logging.FileHandler.count     = " + (zBooleanValue ? 1 : 3) + "\njava.util.logging.FileHandler.append    = " + zBooleanValue + "\njava.util.logging.FileHandler.pattern   = " + (zBooleanValue ? str2 : str2 + File.separator + "kotlin-daemon." + str + ".%u%g.log") + "\njava.util.logging.SimpleFormatter.format = %1$tF %1$tT.%1$tL [%3$s] %4$s: %5$s%n\n";
        LogManager logManager = LogManager.getLogManager();
        byte[] bytes = str3.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        logManager.readConfiguration(new ByteArrayInputStream(bytes));
        this.log = LazyKt.lazy(new Function0<Logger>() { // from class: org.jetbrains.kotlin.daemon.KotlinCompileDaemonBase$log$2
            @Override // kotlin.jvm.functions.Function0
            public final Logger invoke() {
                return Logger.getLogger("daemon");
            }
        });
    }

    public final Logger getLog() {
        return (Logger) this.log.getValue();
    }

    private final String loadVersionFromResource() {
        URL urlFindResource;
        ClassLoader classLoader = KotlinCompileDaemonBase.class.getClassLoader();
        URLClassLoader uRLClassLoader = classLoader instanceof URLClassLoader ? (URLClassLoader) classLoader : null;
        if (uRLClassLoader == null || (urlFindResource = uRLClassLoader.findResource("META-INF/MANIFEST.MF")) == null) {
            return null;
        }
        try {
            String value = new Manifest(urlFindResource.openStream()).getMainAttributes().getValue("Implementation-Version");
            if (value == null) {
                return null;
            }
            return value;
        } catch (IOException unused) {
            return null;
        }
    }

    protected <T> T runSynchronized(Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return block.invoke();
    }

    protected final void mainImpl(final String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        NetworkUtilsKt.ensureServerHostnameIsSetUp();
        List inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        Logger log = getLog();
        StringBuilder sb = new StringBuilder("Kotlin compiler daemon version ");
        String strLoadVersionFromResource = loadVersionFromResource();
        if (strLoadVersionFromResource == null) {
            strLoadVersionFromResource = "<unknown>";
        }
        log.info(sb.append(strLoadVersionFromResource).toString());
        Logger log2 = getLog();
        StringBuilder sb2 = new StringBuilder("daemon JVM args: ");
        Intrinsics.checkNotNull(inputArguments);
        log2.info(sb2.append(CollectionsKt.joinToString$default(inputArguments, " ", null, null, 0, null, null, 62, null)).toString());
        getLog().info("daemon args: " + ArraysKt.joinToString$default(args, " ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
        UtilKt.setIdeaIoUseFallback();
        CompatKt.setupIdeaStandaloneExecution();
        final CompilerId compilerId = new CompilerId(null, null, 3, null);
        final DaemonOptions daemonOptions = new DaemonOptions(null, 0L, 0, 0, 0L, 0L, false, false, 255, null);
        runSynchronized(new Function0<Unit>() { // from class: org.jetbrains.kotlin.daemon.KotlinCompileDaemonBase.mainImpl.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() throws Exception {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() throws Exception {
                try {
                    DaemonJVMOptions daemonJVMOptionsConfigureDaemonJVMOptions = DaemonParamsKt.configureDaemonJVMOptions(new String[0], true, true, true);
                    Iterable<String> iterableFilterExtractProps = DaemonParamsKt.filterExtractProps(ArraysKt.asIterable(args), new OptionsGroup[]{compilerId, daemonOptions}, DaemonParamsKt.COMPILE_DAEMON_CMDLINE_OPTIONS_PREFIX);
                    if (CollectionsKt.any(iterableFilterExtractProps)) {
                        this.getLog().info("usage: <daemon> <compilerId options> <daemon options>");
                        System.out.println((Object) "usage: <daemon> <compilerId options> <daemon options>");
                        throw new IllegalArgumentException("Unknown arguments: " + CollectionsKt.joinToString$default(iterableFilterExtractProps, " ", null, null, 0, null, null, 62, null));
                    }
                    this.getLog().info("starting daemon");
                    Pair<CompileServiceImplBase, Integer> compileServiceAndPort = this.getCompileServiceAndPort(new CompilerSelector() { // from class: org.jetbrains.kotlin.daemon.KotlinCompileDaemonBase$mainImpl$1$compilerSelector$1

                        /* JADX INFO: renamed from: jvm$delegate, reason: from kotlin metadata */
                        private final Lazy jvm = LazyKt.lazy(new Function0<K2JVMCompiler>() { // from class: org.jetbrains.kotlin.daemon.KotlinCompileDaemonBase$mainImpl$1$compilerSelector$1$jvm$2
                            @Override // kotlin.jvm.functions.Function0
                            public final K2JVMCompiler invoke() {
                                return new K2JVMCompiler();
                            }
                        });

                        /* JADX INFO: renamed from: js$delegate, reason: from kotlin metadata */
                        private final Lazy js = LazyKt.lazy(new Function0<K2JSCompiler>() { // from class: org.jetbrains.kotlin.daemon.KotlinCompileDaemonBase$mainImpl$1$compilerSelector$1$js$2
                            @Override // kotlin.jvm.functions.Function0
                            public final K2JSCompiler invoke() {
                                return new K2JSCompiler();
                            }
                        });

                        /* JADX INFO: renamed from: metadata$delegate, reason: from kotlin metadata */
                        private final Lazy metadata = LazyKt.lazy(new Function0<K2MetadataCompiler>() { // from class: org.jetbrains.kotlin.daemon.KotlinCompileDaemonBase$mainImpl$1$compilerSelector$1$metadata$2
                            @Override // kotlin.jvm.functions.Function0
                            public final K2MetadataCompiler invoke() {
                                return new K2MetadataCompiler();
                            }
                        });

                        /* JADX INFO: compiled from: KotlinCompileDaemon.kt */
                        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                        public /* synthetic */ class WhenMappings {
                            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                            static {
                                int[] iArr = new int[CompileService.TargetPlatform.values().length];
                                try {
                                    iArr[CompileService.TargetPlatform.JVM.ordinal()] = 1;
                                } catch (NoSuchFieldError unused) {
                                }
                                try {
                                    iArr[CompileService.TargetPlatform.JS.ordinal()] = 2;
                                } catch (NoSuchFieldError unused2) {
                                }
                                try {
                                    iArr[CompileService.TargetPlatform.METADATA.ordinal()] = 3;
                                } catch (NoSuchFieldError unused3) {
                                }
                                $EnumSwitchMapping$0 = iArr;
                            }
                        }

                        private final K2JVMCompiler getJvm() {
                            return (K2JVMCompiler) this.jvm.getValue();
                        }

                        private final K2JSCompiler getJs() {
                            return (K2JSCompiler) this.js.getValue();
                        }

                        private final K2MetadataCompiler getMetadata() {
                            return (K2MetadataCompiler) this.metadata.getValue();
                        }

                        @Override // org.jetbrains.kotlin.daemon.CompilerSelector
                        public CLICompiler<?> get(CompileService.TargetPlatform targetPlatform) {
                            Intrinsics.checkNotNullParameter(targetPlatform, "targetPlatform");
                            int i = WhenMappings.$EnumSwitchMapping$0[targetPlatform.ordinal()];
                            if (i == 1) {
                                return getJvm();
                            }
                            if (i == 2) {
                                return getJs();
                            }
                            if (i == 3) {
                                return getMetadata();
                            }
                            throw new NoWhenBranchMatchedException();
                        }
                    }, compilerId, daemonOptions, daemonJVMOptionsConfigureDaemonJVMOptions, new Timer(true));
                    CompileServiceImplBase compileServiceImplBaseComponent1 = compileServiceAndPort.component1();
                    int iIntValue = compileServiceAndPort.component2().intValue();
                    compileServiceImplBaseComponent1.startDaemonElections();
                    compileServiceImplBaseComponent1.configurePeriodicActivities();
                    Object objRunCompileService = this.runCompileService(compileServiceImplBaseComponent1);
                    System.out.println((Object) DaemonParamsKt.COMPILE_DAEMON_IS_READY_MESSAGE);
                    this.getLog().info("daemon is listening on port: " + iIntValue);
                    System.out.close();
                    System.err.close();
                    System.setErr(new PrintStream(new LogStream("stderr")));
                    System.setOut(new PrintStream(new LogStream("stdout")));
                    this.awaitServerRun(objRunCompileService);
                } catch (Exception e) {
                    System.err.println("Exception: " + e.getMessage());
                    e.printStackTrace(System.err);
                    this.getLog().log(Level.INFO, "Exception: ", (Throwable) e);
                    throw e;
                }
            }
        });
    }
}
