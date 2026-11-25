package org.jetbrains.kotlin.daemon;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.build.SourcesUtilsKt;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.build.report.RemoteBuildReporter;
import org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporterKt;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.cli.common.CLICompiler;
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArgumentsKt;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.cli.common.arguments.ParseCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.js.K2JSCompiler;
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler;
import org.jetbrains.kotlin.cli.metadata.K2MetadataCompiler;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.config.IncrementalCompilation;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.daemon.common.ClientUtilsKt;
import org.jetbrains.kotlin.daemon.common.CompilationOptions;
import org.jetbrains.kotlin.daemon.common.CompilationResultCategory;
import org.jetbrains.kotlin.daemon.common.CompilationResults;
import org.jetbrains.kotlin.daemon.common.CompileService;
import org.jetbrains.kotlin.daemon.common.CompilerId;
import org.jetbrains.kotlin.daemon.common.CompilerMode;
import org.jetbrains.kotlin.daemon.common.DaemonOptions;
import org.jetbrains.kotlin.daemon.common.DaemonParamsKt;
import org.jetbrains.kotlin.daemon.common.DummyProfiler;
import org.jetbrains.kotlin.daemon.common.IncrementalCompilationOptions;
import org.jetbrains.kotlin.daemon.common.MultiModuleICSettings;
import org.jetbrains.kotlin.daemon.common.PerfCounters;
import org.jetbrains.kotlin.daemon.common.Profiler;
import org.jetbrains.kotlin.daemon.common.RemoteOperationsTracer;
import org.jetbrains.kotlin.daemon.common.ReportSeverity;
import org.jetbrains.kotlin.daemon.common.WallAndThreadAndMemoryTotalProfiler;
import org.jetbrains.kotlin.daemon.common.WallAndThreadTotalProfiler;
import org.jetbrains.kotlin.daemon.report.DaemonMessageReporter;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.incremental.ChangedFiles;
import org.jetbrains.kotlin.incremental.ClasspathChanges;
import org.jetbrains.kotlin.incremental.CompileScopeExpansionMode;
import org.jetbrains.kotlin.incremental.IncrementalCompilerRunner;
import org.jetbrains.kotlin.incremental.IncrementalJsCompilerRunner;
import org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner;
import org.jetbrains.kotlin.incremental.IncrementalModuleInfo;
import org.jetbrains.kotlin.incremental.multiproject.EmptyModulesApiHistory;
import org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory;
import org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistoryAndroid;
import org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistoryJs;
import org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistoryJvm;
import org.jetbrains.kotlin.incremental.storage.FileLocations;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: CompileServiceImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000ú\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u00002\u00020\u0001:\u0004|}~\u007fB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ5\u00104\u001a\u0002H5\"\u0004\b\u0000\u001052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u0002092\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H50;H\u0084\bø\u0001\u0000¢\u0006\u0002\u0010<Jô\u0001\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00070>\"\u0004\b\u0000\u0010?\"\u0004\b\u0001\u0010@\"\u0004\b\u0002\u0010A2\u0006\u0010B\u001a\u00020\u00072\u000e\u0010C\u001a\n\u0012\u0006\b\u0001\u0012\u00020E0D2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u0002H?2\u0006\u0010I\u001a\u0002HA2\u0017\u0010J\u001a\u0013\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u00020L0K¢\u0006\u0002\bM2\u0018\u0010N\u001a\u0014\u0012\u0004\u0012\u0002H?\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020P0O2\u0018\u0010Q\u001a\u0014\u0012\u0004\u0012\u0002H?\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u0002070O2\u001e\u0010R\u001a\u001a\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u00020T\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020U0S2,\u0010V\u001a(\u0012\u0004\u0012\u0002H?\u0012\u0006\u0012\u0004\u0018\u0001HA\u0012\u0004\u0012\u00020W\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020Y\u0012\u0004\u0012\u00020Z0X0SH\u0084\bø\u0001\u0000¢\u0006\u0002\u0010[J\u0006\u0010\\\u001a\u00020]JF\u0010^\u001a\b\u0012\u0004\u0012\u00020\u00070>2\u0006\u0010B\u001a\u00020\u00072\u0006\u00106\u001a\u0002072\b\u0010_\u001a\u0004\u0018\u00010`2\u0018\u0010:\u001a\u0014\u0012\u0004\u0012\u00020T\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020a0OH\u0084\bø\u0001\u0000J\u001a\u0010b\u001a\u00020]2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020]0;H\u0084\bø\u0001\u0000J4\u0010c\u001a\u00020a2\u0006\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020W2\u0006\u0010g\u001a\u00020P2\u0012\u0010h\u001a\u000e\u0012\u0004\u0012\u00020Y\u0012\u0004\u0012\u00020Z0XH\u0004J4\u0010i\u001a\u00020a2\u0006\u0010j\u001a\u00020k2\u0006\u0010f\u001a\u00020W2\u0006\u0010g\u001a\u00020P2\u0012\u0010h\u001a\u000e\u0012\u0004\u0012\u00020Y\u0012\u0004\u0012\u00020Z0XH\u0004J6\u0010l\u001a\b\u0012\u0004\u0012\u0002H50>\"\u0004\b\u0000\u001052\b\b\u0002\u0010m\u001a\u00020n2\u0012\u0010:\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50>0;H\u0084\bø\u0001\u0000J\b\u0010o\u001a\u00020]H$J\b\u0010p\u001a\u00020]H$J\b\u0010q\u001a\u00020]H$J\u000e\u0010r\u001a\b\u0012\u0004\u0012\u00020s0>H\u0004J\u0006\u0010t\u001a\u00020]JB\u0010u\u001a\b\u0012\u0004\u0012\u0002H50>\"\u0004\b\u0000\u001052\u0006\u0010B\u001a\u00020\u00072 \u0010:\u001a\u001c\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010v\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50>0KH\u0084\bø\u0001\u0000JE\u0010w\u001a\b\u0012\u0004\u0012\u0002H50>\"\u0004\b\u0000\u00105\"\u0004\b\u0001\u0010x2\u0006\u0010B\u001a\u00020\u00072\u001d\u0010:\u001a\u0019\u0012\u0004\u0012\u0002Hx\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50>0K¢\u0006\u0002\bMH\u0084\bø\u0001\u0000J\n\u0010y\u001a\u00020\f*\u00020\fJ\n\u0010z\u001a\u00020\f*\u00020\fJ\f\u0010{\u001a\u00020E*\u00020\u0007H\u0004R\u001a\u0010\u000b\u001a\u00020\fX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0012X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u0016X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001d\u001a\u00020\fX¤\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u000eR#\u0010\u001f\u001a\n !*\u0004\u0018\u00010 0 8DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b\"\u0010#R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u001a\u0010(\u001a\u00020)X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u0014\u0010.\u001a\u00020/X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b2\u00103\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0080\u0001"}, d2 = {"Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase;", "", "daemonOptions", "Lorg/jetbrains/kotlin/daemon/common/DaemonOptions;", "compilerId", "Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "port", "", "timer", "Ljava/util/Timer;", "(Lorg/jetbrains/kotlin/daemon/common/DaemonOptions;Lorg/jetbrains/kotlin/daemon/common/CompilerId;ILjava/util/Timer;)V", "_lastUsedSeconds", "", "get_lastUsedSeconds", "()J", "set_lastUsedSeconds", "(J)V", "classpathWatcher", "Lorg/jetbrains/kotlin/daemon/LazyClasspathWatcher;", "getClasspathWatcher", "()Lorg/jetbrains/kotlin/daemon/LazyClasspathWatcher;", "compilationsCounter", "Ljava/util/concurrent/atomic/AtomicInteger;", "getCompilationsCounter", "()Ljava/util/concurrent/atomic/AtomicInteger;", "getCompilerId", "()Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "getDaemonOptions", "()Lorg/jetbrains/kotlin/daemon/common/DaemonOptions;", "lastUsedSeconds", "getLastUsedSeconds", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Ljava/util/logging/Logger;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getLog", "()Ljava/util/logging/Logger;", "log$delegate", "Lkotlin/Lazy;", "getPort", "()I", "runFile", "Ljava/io/File;", "getRunFile", "()Ljava/io/File;", "setRunFile", "(Ljava/io/File;)V", "state", "Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$CompileServiceState;", "getState", "()Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$CompileServiceState;", "getTimer", "()Ljava/util/Timer;", "checkedCompile", "R", "daemonMessageReporter", "Lorg/jetbrains/kotlin/daemon/report/DaemonMessageReporter;", "rpcProfiler", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "body", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/daemon/report/DaemonMessageReporter;Lorg/jetbrains/kotlin/daemon/common/Profiler;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "compileImpl", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "ServicesFacadeT", "JpsServicesFacadeT", "CompilationResultsT", "sessionId", "compilerArguments", "", "", "compilationOptions", "Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;", "servicesFacade", "compilationResults", "hasIncrementalCaches", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "createMessageCollector", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "createReporter", "createServices", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/daemon/EventManager;", "Lorg/jetbrains/kotlin/config/Services;", "getICReporter", "Lorg/jetbrains/kotlin/daemon/common/IncrementalCompilationOptions;", "Lorg/jetbrains/kotlin/build/report/RemoteBuildReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "(I[Ljava/lang/String;Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function3;)Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "configurePeriodicActivities", "", "doCompile", "tracer", "Lorg/jetbrains/kotlin/daemon/common/RemoteOperationsTracer;", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "exceptionLoggingTimerThread", "execIncrementalCompiler", "k2jvmArgs", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "incrementalCompilationOptions", "compilerMessageCollector", "reporter", "execJsIncrementalCompiler", "args", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;", "ifAliveChecksImpl", "minAliveness", "Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$Aliveness;", "initiateElections", "periodicAndAfterSessionCheck", "periodicSeldomCheck", "postReleaseCompileSession", "", "startDaemonElections", "withValidClientOrSessionProxy", "Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$ClientOrSessionProxy;", "withValidReplImpl", "KotlinJvmReplServiceT", "kb", LanguageCodeUtil.MS, "toAlivenessName", "Aliveness", "ClientOrSessionProxy", "CompileServiceState", "SessionsContainer", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CompileServiceImplBase {
    private volatile long _lastUsedSeconds;
    private final LazyClasspathWatcher classpathWatcher;
    private final AtomicInteger compilationsCounter;
    private final CompilerId compilerId;
    private final DaemonOptions daemonOptions;

    /* JADX INFO: renamed from: log$delegate, reason: from kotlin metadata */
    private final Lazy log;
    private final int port;
    private File runFile;
    private final CompileServiceState state;
    private final Timer timer;

    /* JADX INFO: compiled from: CompileServiceImpl.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$Aliveness;", "", "(Ljava/lang/String;I)V", "Dying", "LastSession", "Alive", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Aliveness {
        Dying,
        LastSession,
        Alive;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Aliveness> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: CompileServiceImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 176)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

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
            int[] iArr2 = new int[CompilerMode.values().length];
            try {
                iArr2[CompilerMode.JPS_COMPILER.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[CompilerMode.NON_INCREMENTAL_COMPILER.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[CompilerMode.INCREMENTAL_COMPILER.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    protected abstract long getLastUsedSeconds();

    protected abstract void initiateElections();

    protected abstract void periodicAndAfterSessionCheck();

    protected abstract void periodicSeldomCheck();

    public CompileServiceImplBase(DaemonOptions daemonOptions, CompilerId compilerId, int i, Timer timer) {
        Intrinsics.checkNotNullParameter(daemonOptions, "daemonOptions");
        Intrinsics.checkNotNullParameter(compilerId, "compilerId");
        Intrinsics.checkNotNullParameter(timer, "timer");
        this.daemonOptions = daemonOptions;
        this.compilerId = compilerId;
        this.port = i;
        this.timer = timer;
        this.log = LazyKt.lazy(new Function0<Logger>() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImplBase$log$2
            @Override // kotlin.jvm.functions.Function0
            public final Logger invoke() {
                return Logger.getLogger("compiler");
            }
        });
        CompilerSystemProperties.KOTLIN_COMPILER_ENVIRONMENT_KEEPALIVE_PROPERTY.setValue("true");
        this.compilationsCounter = new AtomicInteger(0);
        this.classpathWatcher = new LazyClasspathWatcher(compilerId.getCompilerClasspath(), 0L, 0L, 6, null);
        this.state = new CompileServiceState();
        this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
        File file = new File(DaemonParamsKt.getRunFilesPathOrDefault(daemonOptions));
        file.mkdirs();
        String str = String.format("%tFT%<tH-%<tM-%<tS.%<tLZ", Arrays.copyOf(new Object[]{Calendar.getInstance(TimeZone.getTimeZone("Z"))}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        File file2 = new File(file, ClientUtilsKt.makeRunFilenameString$default(str, compilerId.digest(), String.valueOf(i), null, 8, null));
        this.runFile = file2;
        try {
            if (!file2.createNewFile()) {
                throw new Exception("createNewFile returned false");
            }
            this.runFile.deleteOnExit();
        } catch (Throwable th) {
            throw new IllegalStateException("Unable to create run file '" + this.runFile.getAbsolutePath() + '\'', th);
        }
    }

    public final DaemonOptions getDaemonOptions() {
        return this.daemonOptions;
    }

    public final CompilerId getCompilerId() {
        return this.compilerId;
    }

    public final int getPort() {
        return this.port;
    }

    public final Timer getTimer() {
        return this.timer;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final Logger getLog() {
        return (Logger) this.log.getValue();
    }

    /* JADX INFO: compiled from: CompileServiceImpl.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0004\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0002B'\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00018\u0000\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010\u0011\u001a\u00020\u0012R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0015\u0010\u0005\u001a\u0004\u0018\u00018\u0000¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$ClientOrSessionProxy;", ExifInterface.GPS_DIRECTION_TRUE, "", "aliveFlagPath", "", "data", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "(Ljava/lang/String;Ljava/lang/Object;Lcom/intellij/openapi/Disposable;)V", "getAliveFlagPath", "()Ljava/lang/String;", "getData", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "isAlive", "", "()Z", "dispose", "", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class ClientOrSessionProxy<T> {
        private final String aliveFlagPath;
        private final T data;
        private Disposable disposable;

        public ClientOrSessionProxy(String str, T t, Disposable disposable) {
            this.aliveFlagPath = str;
            this.data = t;
            this.disposable = disposable;
        }

        public /* synthetic */ ClientOrSessionProxy(String str, Object obj, Disposable disposable, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : obj, (i & 4) != 0 ? null : disposable);
        }

        public final String getAliveFlagPath() {
            return this.aliveFlagPath;
        }

        public final T getData() {
            return this.data;
        }

        public final boolean isAlive() {
            String str = this.aliveFlagPath;
            if (str != null) {
                return new File(str).exists();
            }
            return true;
        }

        public final void dispose() {
            Disposable disposable = this.disposable;
            if (disposable != null) {
                Disposer.dispose(disposable);
                this.disposable = null;
            }
        }
    }

    protected final AtomicInteger getCompilationsCounter() {
        return this.compilationsCounter;
    }

    protected final LazyClasspathWatcher getClasspathWatcher() {
        return this.classpathWatcher;
    }

    /* JADX INFO: compiled from: CompileServiceImpl.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0019\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000b2\u0006\u0010\u0011\u001a\u00020\u0004H\u0086\u0002J\u0006\u0010\u0012\u001a\u00020\u000fJ\u001e\u0010\u0013\u001a\u00020\u0004\"\b\b\u0000\u0010\u0014*\u00020\u00012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u000bJ\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0004R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$SessionsContainer;", "", "()V", "lastSessionId", "", "getLastSessionId", "()I", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "sessions", "", "Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$ClientOrSessionProxy;", "sessionsIdCounter", "Ljava/util/concurrent/atomic/AtomicInteger;", "cleanDead", "", "get", "sessionId", "isEmpty", "leaseSession", ExifInterface.GPS_DIRECTION_TRUE, "session", ProductAction.ACTION_REMOVE, "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class SessionsContainer {
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private final Map<Integer, ClientOrSessionProxy<Object>> sessions = new HashMap();
        private final AtomicInteger sessionsIdCounter = new AtomicInteger(0);

        public final int getLastSessionId() {
            return this.sessionsIdCounter.get();
        }

        public final <T> int leaseSession(ClientOrSessionProxy<? extends T> session) {
            Intrinsics.checkNotNullParameter(session, "session");
            ReentrantReadWriteLock reentrantReadWriteLock = this.lock;
            ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
            int i = 0;
            int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
            for (int i2 = 0; i2 < readHoldCount; i2++) {
                lock.unlock();
            }
            ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
            writeLock.lock();
            try {
                AtomicInteger atomicInteger = this.sessionsIdCounter;
                int iIncrementAndGet = atomicInteger.incrementAndGet();
                int i3 = 100;
                while (true) {
                    if (iIncrementAndGet != CompileService.INSTANCE.getNO_SESSION() && !this.sessions.containsKey(Integer.valueOf(iIncrementAndGet))) {
                        this.sessions.put(Integer.valueOf(iIncrementAndGet), session);
                        while (i < readHoldCount) {
                            lock.lock();
                            i++;
                        }
                        writeLock.unlock();
                        return iIncrementAndGet;
                    }
                    i3--;
                    if (i3 <= 0) {
                        throw new IllegalStateException("Invalid state or algorithm error");
                    }
                    iIncrementAndGet = atomicInteger.addAndGet(KotlinRemoteReplServiceKt.getInternalRng().nextInt());
                }
            } catch (Throwable th) {
                while (i < readHoldCount) {
                    lock.lock();
                    i++;
                }
                writeLock.unlock();
                throw th;
            }
        }

        public final boolean isEmpty() {
            ReentrantReadWriteLock.ReadLock lock = this.lock.readLock();
            lock.lock();
            try {
                return this.sessions.isEmpty();
            } finally {
                lock.unlock();
            }
        }

        public final ClientOrSessionProxy<Object> get(int sessionId) {
            ReentrantReadWriteLock.ReadLock lock = this.lock.readLock();
            lock.lock();
            try {
                return this.sessions.get(Integer.valueOf(sessionId));
            } finally {
                lock.unlock();
            }
        }

        public final boolean remove(int sessionId) {
            ReentrantReadWriteLock reentrantReadWriteLock = this.lock;
            ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
            int i = 0;
            int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
            for (int i2 = 0; i2 < readHoldCount; i2++) {
                lock.unlock();
            }
            ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
            writeLock.lock();
            try {
                ClientOrSessionProxy<Object> clientOrSessionProxyRemove = this.sessions.remove(Integer.valueOf(sessionId));
                if (clientOrSessionProxyRemove != null) {
                    clientOrSessionProxyRemove.dispose();
                } else {
                    clientOrSessionProxyRemove = null;
                }
                boolean z = clientOrSessionProxyRemove != null;
                while (i < readHoldCount) {
                    lock.lock();
                    i++;
                }
                return z;
            } finally {
                while (i < readHoldCount) {
                    lock.lock();
                    i++;
                }
                writeLock.unlock();
            }
        }

        public final boolean cleanDead() {
            ReentrantReadWriteLock.ReadLock lock = this.lock.readLock();
            lock.lock();
            try {
                Map<Integer, ClientOrSessionProxy<Object>> map = this.sessions;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry<Integer, ClientOrSessionProxy<Object>> entry : map.entrySet()) {
                    if (!entry.getValue().isAlive()) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                LinkedHashMap linkedHashMap2 = linkedHashMap;
                boolean z = false;
                if (!linkedHashMap2.isEmpty()) {
                    ReentrantReadWriteLock reentrantReadWriteLock = this.lock;
                    ReentrantReadWriteLock.ReadLock lock2 = reentrantReadWriteLock.readLock();
                    int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
                    for (int i = 0; i < readHoldCount; i++) {
                        lock2.unlock();
                    }
                    ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
                    writeLock.lock();
                    try {
                        Iterator it = linkedHashMap2.entrySet().iterator();
                        while (it.hasNext()) {
                            ClientOrSessionProxy<Object> clientOrSessionProxyRemove = this.sessions.remove(((Map.Entry) it.next()).getKey());
                            if (clientOrSessionProxyRemove != null) {
                                clientOrSessionProxyRemove.dispose();
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                        for (int i2 = 0; i2 < readHoldCount; i2++) {
                            lock2.lock();
                        }
                        writeLock.unlock();
                        z = true;
                    } catch (Throwable th) {
                        for (int i3 = 0; i3 < readHoldCount; i3++) {
                            lock2.lock();
                        }
                        writeLock.unlock();
                        throw th;
                    }
                }
                Unit unit2 = Unit.INSTANCE;
                lock.unlock();
                return z;
            } catch (Throwable th2) {
                lock.unlock();
                throw th2;
            }
        }
    }

    /* JADX INFO: compiled from: CompileServiceImpl.kt */
    @Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 J\u0006\u0010!\u001a\u00020\"J\f\u0010#\u001a\b\u0012\u0004\u0012\u00020 0$JM\u0010%\u001a\u00020\"\"\u0004\b\u0000\u0010&*\b\u0012\u0004\u0012\u0002H&0'2\u0006\u0010(\u001a\u00020\u00142\u0014\b\u0004\u0010)\u001a\u000e\u0012\u0004\u0012\u0002H&\u0012\u0004\u0012\u00020\"0*2\u0014\b\u0004\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H&\u0012\u0004\u0012\u00020\u001e0*H\u0082\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\rR\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$CompileServiceState;", "", "()V", "_clientsCounter", "Ljava/util/concurrent/atomic/AtomicInteger;", "alive", "getAlive", "()Ljava/util/concurrent/atomic/AtomicInteger;", "setAlive", "(Ljava/util/concurrent/atomic/AtomicInteger;)V", "aliveClientsCount", "", "getAliveClientsCount", "()I", "clientProxies", "", "Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$ClientOrSessionProxy;", "clientsCounter", "getClientsCounter", "clientsLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "delayedShutdownQueued", "Ljava/util/concurrent/atomic/AtomicBoolean;", "getDelayedShutdownQueued", "()Ljava/util/concurrent/atomic/AtomicBoolean;", "sessions", "Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$SessionsContainer;", "getSessions", "()Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$SessionsContainer;", "addClient", "", "aliveFlagPath", "", "cleanDeadClients", "", "getClientsFlagPaths", "", "cleanMatching", ExifInterface.GPS_DIRECTION_TRUE, "", "lock", "pred", "Lkotlin/Function1;", Constants.ATTR_FILTER_TYPE_CLEAN, "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class CompileServiceState {
        private final ReentrantReadWriteLock clientsLock = new ReentrantReadWriteLock();
        private final Set<ClientOrSessionProxy<Object>> clientProxies = new HashSet();
        private final SessionsContainer sessions = new SessionsContainer();
        private final AtomicBoolean delayedShutdownQueued = new AtomicBoolean(false);
        private AtomicInteger alive = new AtomicInteger(Aliveness.Alive.ordinal());
        private final AtomicInteger _clientsCounter = new AtomicInteger(0);

        public final SessionsContainer getSessions() {
            return this.sessions;
        }

        public final AtomicBoolean getDelayedShutdownQueued() {
            return this.delayedShutdownQueued;
        }

        public final AtomicInteger getAlive() {
            return this.alive;
        }

        public final void setAlive(AtomicInteger atomicInteger) {
            Intrinsics.checkNotNullParameter(atomicInteger, "<set-?>");
            this.alive = atomicInteger;
        }

        public final int getAliveClientsCount() {
            return this.clientProxies.size();
        }

        public final int getClientsCounter() {
            return this._clientsCounter.get();
        }

        public final void addClient(String aliveFlagPath) {
            ReentrantReadWriteLock reentrantReadWriteLock = this.clientsLock;
            ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
            int i = 0;
            int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
            for (int i2 = 0; i2 < readHoldCount; i2++) {
                lock.unlock();
            }
            ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
            writeLock.lock();
            try {
                this._clientsCounter.incrementAndGet();
                this.clientProxies.add(new ClientOrSessionProxy<>(aliveFlagPath, null, null, 6, null));
                while (i < readHoldCount) {
                    lock.lock();
                    i++;
                }
            } finally {
                while (i < readHoldCount) {
                    lock.lock();
                    i++;
                }
                writeLock.unlock();
            }
        }

        public final List<String> getClientsFlagPaths() {
            ReentrantReadWriteLock.ReadLock lock = this.clientsLock.readLock();
            lock.lock();
            try {
                Set<ClientOrSessionProxy<Object>> set = this.clientProxies;
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = set.iterator();
                while (it.hasNext()) {
                    String aliveFlagPath = ((ClientOrSessionProxy) it.next()).getAliveFlagPath();
                    if (aliveFlagPath != null) {
                        arrayList.add(aliveFlagPath);
                    }
                }
                return arrayList;
            } finally {
                lock.unlock();
            }
        }

        private final <T> boolean cleanMatching(Iterable<? extends T> iterable, ReentrantReadWriteLock reentrantReadWriteLock, Function1<? super T, Boolean> function1, Function1<? super T, Unit> function2) {
            ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
            lock.lock();
            try {
                ArrayList arrayList = new ArrayList();
                for (T t : iterable) {
                    if (function1.invoke(t).booleanValue()) {
                        arrayList.add(t);
                    }
                }
                ArrayList arrayList2 = arrayList;
                boolean z = false;
                if (!arrayList2.isEmpty()) {
                    ReentrantReadWriteLock.ReadLock lock2 = reentrantReadWriteLock.readLock();
                    int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
                    for (int i = 0; i < readHoldCount; i++) {
                        lock2.unlock();
                    }
                    ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
                    writeLock.lock();
                    try {
                        Iterator<T> it = arrayList2.iterator();
                        while (it.hasNext()) {
                            function2.invoke(it.next());
                        }
                        Unit unit = Unit.INSTANCE;
                        InlineMarker.finallyStart(1);
                        for (int i2 = 0; i2 < readHoldCount; i2++) {
                            lock2.lock();
                        }
                        writeLock.unlock();
                        InlineMarker.finallyEnd(1);
                        z = true;
                    } finally {
                        InlineMarker.finallyStart(1);
                        for (int i3 = 0; i3 < readHoldCount; i3++) {
                            lock2.lock();
                        }
                        writeLock.unlock();
                        InlineMarker.finallyEnd(1);
                    }
                }
                Unit unit2 = Unit.INSTANCE;
                InlineMarker.finallyStart(1);
                lock.unlock();
                return z;
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                lock.unlock();
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }

        public final boolean cleanDeadClients() {
            Set<ClientOrSessionProxy<Object>> set = this.clientProxies;
            ReentrantReadWriteLock reentrantReadWriteLock = this.clientsLock;
            ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
            lock.lock();
            try {
                ArrayList arrayList = new ArrayList();
                for (Object obj : set) {
                    if (!((ClientOrSessionProxy) obj).isAlive()) {
                        arrayList.add(obj);
                    }
                }
                ArrayList<ClientOrSessionProxy> arrayList2 = arrayList;
                boolean z = false;
                if (!arrayList2.isEmpty()) {
                    ReentrantReadWriteLock.ReadLock lock2 = reentrantReadWriteLock.readLock();
                    int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
                    for (int i = 0; i < readHoldCount; i++) {
                        lock2.unlock();
                    }
                    ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
                    writeLock.lock();
                    try {
                        for (ClientOrSessionProxy clientOrSessionProxy : arrayList2) {
                            if (this.clientProxies.remove(clientOrSessionProxy)) {
                                clientOrSessionProxy.dispose();
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                        for (int i2 = 0; i2 < readHoldCount; i2++) {
                            lock2.lock();
                        }
                        writeLock.unlock();
                        z = true;
                    } catch (Throwable th) {
                        for (int i3 = 0; i3 < readHoldCount; i3++) {
                            lock2.lock();
                        }
                        writeLock.unlock();
                        throw th;
                    }
                }
                Unit unit2 = Unit.INSTANCE;
                lock.unlock();
                return z;
            } catch (Throwable th2) {
                lock.unlock();
                throw th2;
            }
        }
    }

    protected final CompileServiceState getState() {
        return this.state;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String toAlivenessName(int i) {
        try {
            return Aliveness.values()[i].name();
        } catch (Throwable unused) {
            return "invalid(" + i + ')';
        }
    }

    protected final long get_lastUsedSeconds() {
        return this._lastUsedSeconds;
    }

    protected final void set_lastUsedSeconds(long j) {
        this._lastUsedSeconds = j;
    }

    protected final File getRunFile() {
        return this.runFile;
    }

    protected final void setRunFile(File file) {
        Intrinsics.checkNotNullParameter(file, "<set-?>");
        this.runFile = file;
    }

    protected final CompileService.CallResult postReleaseCompileSession() {
        this.state.getSessions().isEmpty();
        this.timer.schedule(new TimerTask() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImplBase$postReleaseCompileSession$$inlined$schedule$1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                this.this$0.periodicAndAfterSessionCheck();
            }
        }, 0L);
        return new CompileService.CallResult.Ok();
    }

    protected final void exceptionLoggingTimerThread(Function0<Unit> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        try {
            body.invoke();
        } catch (Throwable th) {
            System.err.println("Exception in timer thread: " + th.getMessage());
            th.printStackTrace(System.err);
            getLog().log(Level.SEVERE, "Exception in timer thread", th);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r54v2 */
    protected final <ServicesFacadeT, JpsServicesFacadeT, CompilationResultsT> CompileService.CallResult<Integer> compileImpl(int sessionId, String[] compilerArguments, CompilationOptions compilationOptions, ServicesFacadeT servicesFacade, CompilationResultsT compilationResults, Function1<? super JpsServicesFacadeT, Boolean> hasIncrementalCaches, Function2<? super ServicesFacadeT, ? super CompilationOptions, ? extends MessageCollector> createMessageCollector, Function2<? super ServicesFacadeT, ? super CompilationOptions, ? extends DaemonMessageReporter> createReporter, Function3<? super JpsServicesFacadeT, ? super EventManager, ? super Profiler, Services> createServices, Function3<? super ServicesFacadeT, ? super CompilationResultsT, ? super IncrementalCompilationOptions, RemoteBuildReporter<GradleBuildTime, GradleBuildPerformanceMetric>> getICReporter) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        K2JVMCompiler k2JVMCompiler;
        CompileService.CallResult.Error error;
        int i;
        Throwable th;
        WallAndThreadAndMemoryTotalProfiler wallAndThreadAndMemoryTotalProfiler;
        int i2;
        int i3;
        Intrinsics.checkNotNullParameter(compilerArguments, "compilerArguments");
        Intrinsics.checkNotNullParameter(compilationOptions, "compilationOptions");
        Intrinsics.checkNotNullParameter(hasIncrementalCaches, "hasIncrementalCaches");
        Intrinsics.checkNotNullParameter(createMessageCollector, "createMessageCollector");
        Intrinsics.checkNotNullParameter(createReporter, "createReporter");
        Intrinsics.checkNotNullParameter(createServices, "createServices");
        Intrinsics.checkNotNullParameter(getICReporter, "getICReporter");
        MessageCollector messageCollectorInvoke = createMessageCollector.invoke(servicesFacade, compilationOptions);
        DaemonMessageReporter daemonMessageReporterInvoke = createReporter.invoke(servicesFacade, compilationOptions);
        CompileService.TargetPlatform targetPlatform = compilationOptions.getTargetPlatform();
        getLog().info("Starting compilation with args: " + ArraysKt.joinToString$default(compilerArguments, " ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
        int i4 = WhenMappings.$EnumSwitchMapping$0[targetPlatform.ordinal()];
        if (i4 == 1) {
            k2JVMCompiler = new K2JVMCompiler();
        } else if (i4 == 2) {
            k2JVMCompiler = new K2JSCompiler();
        } else {
            if (i4 != 3) {
                throw new NoWhenBranchMatchedException();
            }
            k2JVMCompiler = new K2MetadataCompiler();
        }
        CommonCompilerArguments commonCompilerArguments = (CommonCompilerArguments) k2JVMCompiler.createArguments();
        CLICompiler cLICompiler = k2JVMCompiler;
        ParseCommandLineArgumentsKt.parseCommandLineArguments$default(ArraysKt.asList(compilerArguments), commonCompilerArguments, false, 4, null);
        String strValidateArguments = ParseCommandLineArgumentsKt.validateArguments(commonCompilerArguments.getErrors());
        if (strValidateArguments != null) {
            MessageCollector.report$default(messageCollectorInvoke, CompilerMessageSeverity.ERROR, strValidateArguments, null, 4, null);
            return new CompileService.CallResult.Good(Integer.valueOf(ExitCode.COMPILATION_ERROR.getCode()));
        }
        int i5 = WhenMappings.$EnumSwitchMapping$1[compilationOptions.getCompilerMode().ordinal()];
        long jFreeMemory = 0;
        String string = "";
        if (i5 == 1) {
            boolean zBooleanValue = hasIncrementalCaches.invoke(servicesFacade).booleanValue();
            boolean zIsEnabledForJvm = IncrementalCompilation.isEnabledForJvm();
            IncrementalCompilation.setIsEnabledForJvm(zBooleanValue);
            try {
                if (commonCompilerArguments.getIncrementalCompilation() == null) {
                    commonCompilerArguments.setIncrementalCompilation(Boolean.valueOf(zBooleanValue));
                }
                CompileServiceImplBase compileServiceImplBase = this;
                getLog().fine("alive!");
                if (sessionId != CompileService.INSTANCE.getNO_SESSION() && this.state.getSessions().get(sessionId) == null) {
                    error = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
                    i = 1;
                } else {
                    try {
                        this.compilationsCounter.incrementAndGet();
                        Profiler wallAndThreadTotalProfiler = getDaemonOptions().getReportPerf() ? new WallAndThreadTotalProfiler() : new DummyProfiler();
                        EventManagerImpl eventManagerImpl = new EventManagerImpl();
                        try {
                            getLog().fine("trying get exitCode");
                            try {
                                DummyProfiler wallAndThreadAndMemoryTotalProfiler2 = getDaemonOptions().getReportPerf() ? new WallAndThreadAndMemoryTotalProfiler(false) : new DummyProfiler();
                                List<Long> listBeginMeasure = wallAndThreadAndMemoryTotalProfiler2.beginMeasure(null);
                                Integer numValueOf = Integer.valueOf(cLICompiler.exec(messageCollectorInvoke, createServices.invoke(servicesFacade, eventManagerImpl, wallAndThreadTotalProfiler), commonCompilerArguments).getCode());
                                wallAndThreadAndMemoryTotalProfiler2.endMeasure(null, listBeginMeasure);
                                if (getDaemonOptions().getReportPerf()) {
                                    Runtime runtime = Runtime.getRuntime();
                                    jFreeMemory = runtime.totalMemory() - runtime.freeMemory();
                                }
                                long j = jFreeMemory;
                                getLog().info("Done with result " + numValueOf);
                                if (getDaemonOptions().getReportPerf()) {
                                    PerfCounters totalCounters = wallAndThreadAndMemoryTotalProfiler2.getTotalCounters();
                                    PerfCounters totalCounters2 = wallAndThreadTotalProfiler.getTotalCounters();
                                    StringBuilder sbAppend = new StringBuilder("PERF: Compile on daemon: ").append(ms(totalCounters.getTime())).append(" ms; thread: user ").append(ms(totalCounters.getThreadUserTime())).append(" ms, sys ").append(ms(totalCounters.getThreadTime() - totalCounters.getThreadUserTime())).append(" ms; rpc: ").append(totalCounters2.getCount()).append(" calls, ").append(ms(totalCounters2.getTime())).append(" ms, thread ").append(ms(totalCounters2.getThreadTime())).append(" ms; memory: ").append(kb(j)).append(" kb (");
                                    String str = String.format("%+d", Arrays.copyOf(new Object[]{Long.valueOf(kb(totalCounters.getMemory()))}, 1));
                                    Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                                    String string2 = sbAppend.append(str).append(" kb)").toString();
                                    daemonMessageReporterInvoke.report(ReportSeverity.INFO, string2);
                                    getLog().info(string2);
                                    Unit unit = Unit.INSTANCE;
                                    for (Map.Entry<Object, PerfCounters> entry : wallAndThreadTotalProfiler.getCounters().entrySet()) {
                                        Object key = entry.getKey();
                                        PerfCounters value = entry.getValue();
                                        String str2 = "PERF: rpc by " + key + ": " + value.getCount() + " calls, " + ms(value.getTime()) + " ms, thread " + ms(value.getThreadTime()) + " ms";
                                        daemonMessageReporterInvoke.report(ReportSeverity.INFO, str2);
                                        getLog().info(str2);
                                        Unit unit2 = Unit.INSTANCE;
                                    }
                                }
                                CompileService.CallResult.Good good = new CompileService.CallResult.Good(Integer.valueOf(numValueOf.intValue()));
                                i = 1;
                                InlineMarker.finallyStart(1);
                                eventManagerImpl.fireCompilationFinished();
                                InlineMarker.finallyEnd(1);
                                CompileService.CallResult.Good good2 = good;
                                InlineMarker.finallyStart(1);
                                this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                InlineMarker.finallyEnd(1);
                                error = good2;
                            } catch (Throwable th2) {
                                Logger log = getLog();
                                Level level = Level.SEVERE;
                                StringBuilder sbAppend2 = new StringBuilder().append("Exception: ").append(th2).append("\n  ");
                                StackTraceElement[] stackTrace = th2.getStackTrace();
                                Intrinsics.checkNotNullExpressionValue(stackTrace, "getStackTrace(...)");
                                StringBuilder sbAppend3 = sbAppend2.append(ArraysKt.joinToString$default(stackTrace, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                if (th2.getCause() != null && !Intrinsics.areEqual(th2.getCause(), th2)) {
                                    StringBuilder sbAppend4 = new StringBuilder().append("\nCaused by: ").append(th2.getCause()).append("\n  ");
                                    Throwable cause = th2.getCause();
                                    Intrinsics.checkNotNull(cause);
                                    StackTraceElement[] stackTrace2 = cause.getStackTrace();
                                    Intrinsics.checkNotNullExpressionValue(stackTrace2, "getStackTrace(...)");
                                    string = sbAppend4.append(ArraysKt.joinToString$default(stackTrace2, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
                                }
                                log.log(level, sbAppend3.append(string).toString());
                                throw th2;
                            }
                        } catch (Throwable th3) {
                            InlineMarker.finallyStart(1);
                            eventManagerImpl.fireCompilationFinished();
                            InlineMarker.finallyEnd(1);
                            throw th3;
                        }
                    } catch (Throwable th4) {
                        InlineMarker.finallyStart(1);
                        this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                        InlineMarker.finallyEnd(1);
                        throw th4;
                    }
                }
                InlineMarker.finallyStart(i);
                IncrementalCompilation.setIsEnabledForJvm(zIsEnabledForJvm);
                InlineMarker.finallyEnd(i);
            } catch (Throwable th5) {
                InlineMarker.finallyStart(1);
                IncrementalCompilation.setIsEnabledForJvm(zIsEnabledForJvm);
                InlineMarker.finallyEnd(1);
                throw th5;
            }
        } else if (i5 == 2) {
            getLog().fine("alive!");
            if (sessionId != CompileService.INSTANCE.getNO_SESSION() && this.state.getSessions().get(sessionId) == null) {
                error = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
            } else {
                try {
                    this.compilationsCounter.incrementAndGet();
                    Profiler wallAndThreadTotalProfiler2 = getDaemonOptions().getReportPerf() ? new WallAndThreadTotalProfiler() : new DummyProfiler();
                    EventManagerImpl eventManagerImpl2 = new EventManagerImpl();
                    try {
                        try {
                            getLog().fine("trying get exitCode");
                            try {
                                if (getDaemonOptions().getReportPerf()) {
                                    try {
                                        wallAndThreadAndMemoryTotalProfiler = new WallAndThreadAndMemoryTotalProfiler(false);
                                    } catch (Throwable th6) {
                                        th = th6;
                                        Logger log2 = getLog();
                                        Level level2 = Level.SEVERE;
                                        StringBuilder sbAppend5 = new StringBuilder().append("Exception: ").append(th).append("\n  ");
                                        StackTraceElement[] stackTrace3 = th.getStackTrace();
                                        Intrinsics.checkNotNullExpressionValue(stackTrace3, "getStackTrace(...)");
                                        StringBuilder sbAppend6 = sbAppend5.append(ArraysKt.joinToString$default(stackTrace3, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                        if (th.getCause() != null && !Intrinsics.areEqual(th.getCause(), th)) {
                                            StringBuilder sbAppend7 = new StringBuilder().append("\nCaused by: ").append(th.getCause()).append("\n  ");
                                            Throwable cause2 = th.getCause();
                                            Intrinsics.checkNotNull(cause2);
                                            StackTraceElement[] stackTrace4 = cause2.getStackTrace();
                                            Intrinsics.checkNotNullExpressionValue(stackTrace4, "getStackTrace(...)");
                                            string = sbAppend7.append(ArraysKt.joinToString$default(stackTrace4, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
                                        }
                                        log2.log(level2, sbAppend6.append(string).toString());
                                        throw th;
                                    }
                                } else {
                                    wallAndThreadAndMemoryTotalProfiler = new DummyProfiler();
                                }
                                List<Long> listBeginMeasure2 = wallAndThreadAndMemoryTotalProfiler.beginMeasure(null);
                                try {
                                    ExitCode exitCodeExec = cLICompiler.exec(messageCollectorInvoke, Services.EMPTY, commonCompilerArguments);
                                    String strRenderCompilerPerformance = cLICompiler.getDefaultPerformanceManager().renderCompilerPerformance();
                                    if (compilationResults != null) {
                                        ((CompilationResults) compilationResults).add(CompilationResultCategory.BUILD_REPORT_LINES.getCode(), CollectionsKt.arrayListOf(strRenderCompilerPerformance));
                                    }
                                    Integer numValueOf2 = Integer.valueOf(exitCodeExec.getCode());
                                    wallAndThreadAndMemoryTotalProfiler.endMeasure(null, listBeginMeasure2);
                                    if (getDaemonOptions().getReportPerf()) {
                                        Runtime runtime2 = Runtime.getRuntime();
                                        jFreeMemory = runtime2.totalMemory() - runtime2.freeMemory();
                                    }
                                    long j2 = jFreeMemory;
                                    getLog().info("Done with result " + numValueOf2);
                                    if (getDaemonOptions().getReportPerf()) {
                                        PerfCounters totalCounters3 = wallAndThreadAndMemoryTotalProfiler.getTotalCounters();
                                        PerfCounters totalCounters4 = wallAndThreadTotalProfiler2.getTotalCounters();
                                        StringBuilder sbAppend8 = new StringBuilder("PERF: Compile on daemon: ").append(ms(totalCounters3.getTime())).append(" ms; thread: user ").append(ms(totalCounters3.getThreadUserTime())).append(" ms, sys ").append(ms(totalCounters3.getThreadTime() - totalCounters3.getThreadUserTime())).append(" ms; rpc: ").append(totalCounters4.getCount()).append(" calls, ").append(ms(totalCounters4.getTime())).append(" ms, thread ").append(ms(totalCounters4.getThreadTime())).append(" ms; memory: ").append(kb(j2)).append(" kb (");
                                        String str3 = String.format("%+d", Arrays.copyOf(new Object[]{Long.valueOf(kb(totalCounters3.getMemory()))}, 1));
                                        Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
                                        String string3 = sbAppend8.append(str3).append(" kb)").toString();
                                        daemonMessageReporterInvoke.report(ReportSeverity.INFO, string3);
                                        getLog().info(string3);
                                        Unit unit3 = Unit.INSTANCE;
                                        for (Map.Entry<Object, PerfCounters> entry2 : wallAndThreadTotalProfiler2.getCounters().entrySet()) {
                                            Object key2 = entry2.getKey();
                                            PerfCounters value2 = entry2.getValue();
                                            String str4 = "PERF: rpc by " + key2 + ": " + value2.getCount() + " calls, " + ms(value2.getTime()) + " ms, thread " + ms(value2.getThreadTime()) + " ms";
                                            daemonMessageReporterInvoke.report(ReportSeverity.INFO, str4);
                                            getLog().info(str4);
                                            Unit unit4 = Unit.INSTANCE;
                                        }
                                    }
                                    CompileService.CallResult.Good good3 = new CompileService.CallResult.Good(Integer.valueOf(numValueOf2.intValue()));
                                    InlineMarker.finallyStart(1);
                                    eventManagerImpl2.fireCompilationFinished();
                                    InlineMarker.finallyEnd(1);
                                    error = good3;
                                    InlineMarker.finallyStart(1);
                                    this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                    InlineMarker.finallyEnd(1);
                                } catch (Throwable th7) {
                                    th = th7;
                                    th = th;
                                    Logger log3 = getLog();
                                    Level level3 = Level.SEVERE;
                                    StringBuilder sbAppend9 = new StringBuilder().append("Exception: ").append(th).append("\n  ");
                                    StackTraceElement[] stackTrace5 = th.getStackTrace();
                                    Intrinsics.checkNotNullExpressionValue(stackTrace5, "getStackTrace(...)");
                                    StringBuilder sbAppend10 = sbAppend9.append(ArraysKt.joinToString$default(stackTrace5, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                    if (th.getCause() != null) {
                                        StringBuilder sbAppend11 = new StringBuilder().append("\nCaused by: ").append(th.getCause()).append("\n  ");
                                        Throwable cause3 = th.getCause();
                                        Intrinsics.checkNotNull(cause3);
                                        StackTraceElement[] stackTrace6 = cause3.getStackTrace();
                                        Intrinsics.checkNotNullExpressionValue(stackTrace6, "getStackTrace(...)");
                                        string = sbAppend11.append(ArraysKt.joinToString$default(stackTrace6, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
                                    }
                                    log3.log(level3, sbAppend10.append(string).toString());
                                    throw th;
                                }
                            } catch (Throwable th8) {
                                th = th8;
                            }
                        } catch (Throwable th9) {
                            th = th9;
                            sessionId = eventManagerImpl2;
                            Throwable th10 = th;
                            InlineMarker.finallyStart(1);
                            sessionId.fireCompilationFinished();
                            InlineMarker.finallyEnd(1);
                            throw th10;
                        }
                    } catch (Throwable th11) {
                        th = th11;
                        Throwable th12 = th;
                        InlineMarker.finallyStart(1);
                        sessionId.fireCompilationFinished();
                        InlineMarker.finallyEnd(1);
                        throw th12;
                    }
                } catch (Throwable th13) {
                    InlineMarker.finallyStart(1);
                    this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                    InlineMarker.finallyEnd(1);
                    throw th13;
                }
            }
        } else if (i5 == 3) {
            IncrementalCompilationOptions incrementalCompilationOptions = (IncrementalCompilationOptions) compilationOptions;
            int i6 = WhenMappings.$EnumSwitchMapping$0[targetPlatform.ordinal()];
            if (i6 == 1) {
                boolean zIsEnabledForJvm2 = IncrementalCompilation.isEnabledForJvm();
                IncrementalCompilation.setIsEnabledForJvm(true);
                try {
                    if (commonCompilerArguments.getIncrementalCompilation() == null) {
                        commonCompilerArguments.setIncrementalCompilation(true);
                    }
                    CompileServiceImplBase compileServiceImplBase2 = this;
                    getLog().fine("alive!");
                    if (sessionId != CompileService.INSTANCE.getNO_SESSION() && this.state.getSessions().get(sessionId) == null) {
                        error = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
                        i2 = 1;
                    } else {
                        try {
                            this.compilationsCounter.incrementAndGet();
                            Profiler wallAndThreadTotalProfiler3 = getDaemonOptions().getReportPerf() ? new WallAndThreadTotalProfiler() : new DummyProfiler();
                            EventManagerImpl eventManagerImpl3 = new EventManagerImpl();
                            try {
                                getLog().fine("trying get exitCode");
                                try {
                                    Profiler wallAndThreadAndMemoryTotalProfiler3 = getDaemonOptions().getReportPerf() ? new WallAndThreadAndMemoryTotalProfiler(false) : new DummyProfiler();
                                    List<Long> listBeginMeasure3 = wallAndThreadAndMemoryTotalProfiler3.beginMeasure(null);
                                    Intrinsics.checkNotNull(commonCompilerArguments, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments");
                                    Intrinsics.checkNotNull(compilationResults);
                                    Integer numValueOf3 = Integer.valueOf(execIncrementalCompiler((K2JVMCompilerArguments) commonCompilerArguments, incrementalCompilationOptions, messageCollectorInvoke, getICReporter.invoke(servicesFacade, compilationResults, incrementalCompilationOptions)).getCode());
                                    wallAndThreadAndMemoryTotalProfiler3.endMeasure(null, listBeginMeasure3);
                                    if (getDaemonOptions().getReportPerf()) {
                                        Runtime runtime3 = Runtime.getRuntime();
                                        jFreeMemory = runtime3.totalMemory() - runtime3.freeMemory();
                                    }
                                    long j3 = jFreeMemory;
                                    getLog().info("Done with result " + numValueOf3);
                                    if (getDaemonOptions().getReportPerf()) {
                                        PerfCounters totalCounters5 = wallAndThreadAndMemoryTotalProfiler3.getTotalCounters();
                                        PerfCounters totalCounters6 = wallAndThreadTotalProfiler3.getTotalCounters();
                                        StringBuilder sbAppend12 = new StringBuilder("PERF: Compile on daemon: ").append(ms(totalCounters5.getTime())).append(" ms; thread: user ").append(ms(totalCounters5.getThreadUserTime())).append(" ms, sys ").append(ms(totalCounters5.getThreadTime() - totalCounters5.getThreadUserTime())).append(" ms; rpc: ").append(totalCounters6.getCount()).append(" calls, ").append(ms(totalCounters6.getTime())).append(" ms, thread ").append(ms(totalCounters6.getThreadTime())).append(" ms; memory: ").append(kb(j3)).append(" kb (");
                                        String str5 = String.format("%+d", Arrays.copyOf(new Object[]{Long.valueOf(kb(totalCounters5.getMemory()))}, 1));
                                        Intrinsics.checkNotNullExpressionValue(str5, "format(...)");
                                        String string4 = sbAppend12.append(str5).append(" kb)").toString();
                                        daemonMessageReporterInvoke.report(ReportSeverity.INFO, string4);
                                        getLog().info(string4);
                                        Unit unit5 = Unit.INSTANCE;
                                        for (Map.Entry<Object, PerfCounters> entry3 : wallAndThreadTotalProfiler3.getCounters().entrySet()) {
                                            Object key3 = entry3.getKey();
                                            PerfCounters value3 = entry3.getValue();
                                            String str6 = "PERF: rpc by " + key3 + ": " + value3.getCount() + " calls, " + ms(value3.getTime()) + " ms, thread " + ms(value3.getThreadTime()) + " ms";
                                            daemonMessageReporterInvoke.report(ReportSeverity.INFO, str6);
                                            getLog().info(str6);
                                            Unit unit6 = Unit.INSTANCE;
                                        }
                                    }
                                    CompileService.CallResult.Good good4 = new CompileService.CallResult.Good(Integer.valueOf(numValueOf3.intValue()));
                                    i2 = 1;
                                    InlineMarker.finallyStart(1);
                                    eventManagerImpl3.fireCompilationFinished();
                                    InlineMarker.finallyEnd(1);
                                    error = good4;
                                    InlineMarker.finallyStart(1);
                                    this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                    InlineMarker.finallyEnd(1);
                                } catch (Throwable th14) {
                                    Logger log4 = getLog();
                                    Level level4 = Level.SEVERE;
                                    StringBuilder sbAppend13 = new StringBuilder().append("Exception: ").append(th14).append("\n  ");
                                    StackTraceElement[] stackTrace7 = th14.getStackTrace();
                                    Intrinsics.checkNotNullExpressionValue(stackTrace7, "getStackTrace(...)");
                                    StringBuilder sbAppend14 = sbAppend13.append(ArraysKt.joinToString$default(stackTrace7, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                    if (th14.getCause() != null && !Intrinsics.areEqual(th14.getCause(), th14)) {
                                        StringBuilder sbAppend15 = new StringBuilder().append("\nCaused by: ").append(th14.getCause()).append("\n  ");
                                        Throwable cause4 = th14.getCause();
                                        Intrinsics.checkNotNull(cause4);
                                        StackTraceElement[] stackTrace8 = cause4.getStackTrace();
                                        Intrinsics.checkNotNullExpressionValue(stackTrace8, "getStackTrace(...)");
                                        string = sbAppend15.append(ArraysKt.joinToString$default(stackTrace8, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
                                    }
                                    log4.log(level4, sbAppend14.append(string).toString());
                                    throw th14;
                                }
                            } catch (Throwable th15) {
                                InlineMarker.finallyStart(1);
                                eventManagerImpl3.fireCompilationFinished();
                                InlineMarker.finallyEnd(1);
                                throw th15;
                            }
                        } catch (Throwable th16) {
                            InlineMarker.finallyStart(1);
                            this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                            InlineMarker.finallyEnd(1);
                            throw th16;
                        }
                    }
                    InlineMarker.finallyStart(i2);
                    IncrementalCompilation.setIsEnabledForJvm(zIsEnabledForJvm2);
                    InlineMarker.finallyEnd(i2);
                } catch (Throwable th17) {
                    InlineMarker.finallyStart(1);
                    IncrementalCompilation.setIsEnabledForJvm(zIsEnabledForJvm2);
                    InlineMarker.finallyEnd(1);
                    throw th17;
                }
            } else {
                if (i6 != 2) {
                    throw new IllegalStateException("Incremental compilation is not supported for target platform: " + targetPlatform);
                }
                boolean zIsEnabledForJs = IncrementalCompilation.isEnabledForJs();
                IncrementalCompilation.setIsEnabledForJs(true);
                try {
                    if (commonCompilerArguments.getIncrementalCompilation() == null) {
                        commonCompilerArguments.setIncrementalCompilation(true);
                    }
                    CompileServiceImplBase compileServiceImplBase3 = this;
                    getLog().fine("alive!");
                    if (sessionId != CompileService.INSTANCE.getNO_SESSION() && this.state.getSessions().get(sessionId) == null) {
                        error = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
                        i3 = 1;
                    } else {
                        try {
                            this.compilationsCounter.incrementAndGet();
                            Profiler wallAndThreadTotalProfiler4 = getDaemonOptions().getReportPerf() ? new WallAndThreadTotalProfiler() : new DummyProfiler();
                            EventManagerImpl eventManagerImpl4 = new EventManagerImpl();
                            try {
                                getLog().fine("trying get exitCode");
                                try {
                                    Profiler wallAndThreadAndMemoryTotalProfiler4 = getDaemonOptions().getReportPerf() ? new WallAndThreadAndMemoryTotalProfiler(false) : new DummyProfiler();
                                    List<Long> listBeginMeasure4 = wallAndThreadAndMemoryTotalProfiler4.beginMeasure(null);
                                    Intrinsics.checkNotNull(commonCompilerArguments, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArguments");
                                    Intrinsics.checkNotNull(compilationResults);
                                    Integer numValueOf4 = Integer.valueOf(execJsIncrementalCompiler((K2JSCompilerArguments) commonCompilerArguments, incrementalCompilationOptions, messageCollectorInvoke, getICReporter.invoke(servicesFacade, compilationResults, incrementalCompilationOptions)).getCode());
                                    wallAndThreadAndMemoryTotalProfiler4.endMeasure(null, listBeginMeasure4);
                                    if (getDaemonOptions().getReportPerf()) {
                                        Runtime runtime4 = Runtime.getRuntime();
                                        jFreeMemory = runtime4.totalMemory() - runtime4.freeMemory();
                                    }
                                    long j4 = jFreeMemory;
                                    getLog().info("Done with result " + numValueOf4);
                                    if (getDaemonOptions().getReportPerf()) {
                                        PerfCounters totalCounters7 = wallAndThreadAndMemoryTotalProfiler4.getTotalCounters();
                                        PerfCounters totalCounters8 = wallAndThreadTotalProfiler4.getTotalCounters();
                                        StringBuilder sbAppend16 = new StringBuilder("PERF: Compile on daemon: ").append(ms(totalCounters7.getTime())).append(" ms; thread: user ").append(ms(totalCounters7.getThreadUserTime())).append(" ms, sys ").append(ms(totalCounters7.getThreadTime() - totalCounters7.getThreadUserTime())).append(" ms; rpc: ").append(totalCounters8.getCount()).append(" calls, ").append(ms(totalCounters8.getTime())).append(" ms, thread ").append(ms(totalCounters8.getThreadTime())).append(" ms; memory: ").append(kb(j4)).append(" kb (");
                                        String str7 = String.format("%+d", Arrays.copyOf(new Object[]{Long.valueOf(kb(totalCounters7.getMemory()))}, 1));
                                        Intrinsics.checkNotNullExpressionValue(str7, "format(...)");
                                        String string5 = sbAppend16.append(str7).append(" kb)").toString();
                                        daemonMessageReporterInvoke.report(ReportSeverity.INFO, string5);
                                        getLog().info(string5);
                                        Unit unit7 = Unit.INSTANCE;
                                        for (Map.Entry<Object, PerfCounters> entry4 : wallAndThreadTotalProfiler4.getCounters().entrySet()) {
                                            Object key4 = entry4.getKey();
                                            PerfCounters value4 = entry4.getValue();
                                            String str8 = "PERF: rpc by " + key4 + ": " + value4.getCount() + " calls, " + ms(value4.getTime()) + " ms, thread " + ms(value4.getThreadTime()) + " ms";
                                            daemonMessageReporterInvoke.report(ReportSeverity.INFO, str8);
                                            getLog().info(str8);
                                            Unit unit8 = Unit.INSTANCE;
                                        }
                                    }
                                    CompileService.CallResult.Good good5 = new CompileService.CallResult.Good(Integer.valueOf(numValueOf4.intValue()));
                                    i3 = 1;
                                    InlineMarker.finallyStart(1);
                                    eventManagerImpl4.fireCompilationFinished();
                                    InlineMarker.finallyEnd(1);
                                    error = good5;
                                    InlineMarker.finallyStart(1);
                                    this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                    InlineMarker.finallyEnd(1);
                                } catch (Throwable th18) {
                                    Logger log5 = getLog();
                                    Level level5 = Level.SEVERE;
                                    StringBuilder sbAppend17 = new StringBuilder().append("Exception: ").append(th18).append("\n  ");
                                    StackTraceElement[] stackTrace9 = th18.getStackTrace();
                                    Intrinsics.checkNotNullExpressionValue(stackTrace9, "getStackTrace(...)");
                                    StringBuilder sbAppend18 = sbAppend17.append(ArraysKt.joinToString$default(stackTrace9, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                    if (th18.getCause() != null && !Intrinsics.areEqual(th18.getCause(), th18)) {
                                        StringBuilder sbAppend19 = new StringBuilder().append("\nCaused by: ").append(th18.getCause()).append("\n  ");
                                        Throwable cause5 = th18.getCause();
                                        Intrinsics.checkNotNull(cause5);
                                        StackTraceElement[] stackTrace10 = cause5.getStackTrace();
                                        Intrinsics.checkNotNullExpressionValue(stackTrace10, "getStackTrace(...)");
                                        string = sbAppend19.append(ArraysKt.joinToString$default(stackTrace10, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
                                    }
                                    log5.log(level5, sbAppend18.append(string).toString());
                                    throw th18;
                                }
                            } catch (Throwable th19) {
                                InlineMarker.finallyStart(1);
                                eventManagerImpl4.fireCompilationFinished();
                                InlineMarker.finallyEnd(1);
                                throw th19;
                            }
                        } catch (Throwable th20) {
                            InlineMarker.finallyStart(1);
                            this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                            InlineMarker.finallyEnd(1);
                            throw th20;
                        }
                    }
                    InlineMarker.finallyStart(i3);
                    IncrementalCompilation.setIsEnabledForJs(zIsEnabledForJs);
                    InlineMarker.finallyEnd(i3);
                } catch (Throwable th21) {
                    InlineMarker.finallyStart(1);
                    IncrementalCompilation.setIsEnabledForJs(zIsEnabledForJs);
                    InlineMarker.finallyEnd(1);
                    throw th21;
                }
            }
        } else {
            throw new IllegalStateException("Unknown compilation mode " + compilationOptions.getCompilerMode());
        }
        return error;
    }

    /* JADX WARN: Code duplicated, block: B:67:0x0312  */
    /* JADX WARN: Code duplicated, block: B:90:0x032e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    protected final CompileService.CallResult<Integer> doCompile(int sessionId, DaemonMessageReporter daemonMessageReporter, RemoteOperationsTracer tracer, Function2<? super EventManager, ? super Profiler, ? extends ExitCode> body) throws Throwable {
        int i;
        String str;
        String str2;
        String string;
        long jFreeMemory;
        String str3 = "PERF: Compile on daemon: ";
        Intrinsics.checkNotNullParameter(daemonMessageReporter, "daemonMessageReporter");
        Intrinsics.checkNotNullParameter(body, "body");
        getLog().fine("alive!");
        if (sessionId != CompileService.INSTANCE.getNO_SESSION() && this.state.getSessions().get(sessionId) == null) {
            return new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
        }
        try {
            this.compilationsCounter.incrementAndGet();
            if (tracer != null) {
                tracer.before("compile");
            }
            Profiler wallAndThreadTotalProfiler = getDaemonOptions().getReportPerf() ? new WallAndThreadTotalProfiler() : new DummyProfiler();
            EventManagerImpl eventManagerImpl = new EventManagerImpl();
            try {
                try {
                    getLog().fine("trying get exitCode");
                    try {
                        Profiler wallAndThreadAndMemoryTotalProfiler = getDaemonOptions().getReportPerf() ? new WallAndThreadAndMemoryTotalProfiler(false) : new DummyProfiler();
                        List<Long> listBeginMeasure = wallAndThreadAndMemoryTotalProfiler.beginMeasure(null);
                        Integer numValueOf = Integer.valueOf(body.invoke(eventManagerImpl, wallAndThreadTotalProfiler).getCode());
                        wallAndThreadAndMemoryTotalProfiler.endMeasure(null, listBeginMeasure);
                        if (getDaemonOptions().getReportPerf()) {
                            Runtime runtime = Runtime.getRuntime();
                            jFreeMemory = runtime.totalMemory() - runtime.freeMemory();
                        } else {
                            jFreeMemory = 0;
                        }
                        str2 = "\n  ";
                        long j = jFreeMemory;
                        try {
                            str = "getStackTrace(...)";
                            try {
                                getLog().info("Done with result " + numValueOf);
                                if (getDaemonOptions().getReportPerf()) {
                                    PerfCounters totalCounters = wallAndThreadAndMemoryTotalProfiler.getTotalCounters();
                                    PerfCounters totalCounters2 = wallAndThreadTotalProfiler.getTotalCounters();
                                    try {
                                        StringBuilder sbAppend = new StringBuilder("PERF: Compile on daemon: ").append(ms(totalCounters.getTime())).append(" ms; thread: user ").append(ms(totalCounters.getThreadUserTime())).append(" ms, sys ").append(ms(totalCounters.getThreadTime() - totalCounters.getThreadUserTime())).append(" ms; rpc: ").append(totalCounters2.getCount()).append(" calls, ").append(ms(totalCounters2.getTime())).append(" ms, thread ").append(ms(totalCounters2.getThreadTime())).append(" ms; memory: ").append(kb(j)).append(" kb (");
                                        String str4 = String.format("%+d", Arrays.copyOf(new Object[]{Long.valueOf(kb(totalCounters.getMemory()))}, 1));
                                        Intrinsics.checkNotNullExpressionValue(str4, "format(...)");
                                        String string2 = sbAppend.append(str4).append(" kb)").toString();
                                        daemonMessageReporter.report(ReportSeverity.INFO, string2);
                                        getLog().info(string2);
                                        for (Map.Entry<Object, PerfCounters> entry : wallAndThreadTotalProfiler.getCounters().entrySet()) {
                                            Object key = entry.getKey();
                                            PerfCounters value = entry.getValue();
                                            String str5 = "PERF: rpc by " + key + ": " + value.getCount() + " calls, " + ms(value.getTime()) + " ms, thread " + ms(value.getThreadTime()) + " ms";
                                            daemonMessageReporter.report(ReportSeverity.INFO, str5);
                                            getLog().info(str5);
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        Logger log = getLog();
                                        Level level = Level.SEVERE;
                                        String str6 = str2;
                                        StringBuilder sbAppend2 = new StringBuilder().append("Exception: ").append(th).append(str6);
                                        StackTraceElement[] stackTrace = th.getStackTrace();
                                        String str7 = str;
                                        Intrinsics.checkNotNullExpressionValue(stackTrace, str7);
                                        StringBuilder sbAppend3 = sbAppend2.append(ArraysKt.joinToString$default(stackTrace, str6, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                        if (th.getCause() == null && !Intrinsics.areEqual(th.getCause(), th)) {
                                            StringBuilder sbAppend4 = new StringBuilder().append("\nCaused by: ").append(th.getCause()).append(str6);
                                            Throwable cause = th.getCause();
                                            Intrinsics.checkNotNull(cause);
                                            StackTraceElement[] stackTrace2 = cause.getStackTrace();
                                            Intrinsics.checkNotNullExpressionValue(stackTrace2, str7);
                                            string = sbAppend4.append(ArraysKt.joinToString$default(stackTrace2, str6, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
                                        } else {
                                            string = "";
                                        }
                                        log.log(level, sbAppend3.append(string).toString());
                                        throw th;
                                    }
                                }
                                CompileService.CallResult.Good good = new CompileService.CallResult.Good(Integer.valueOf(numValueOf.intValue()));
                                InlineMarker.finallyStart(1);
                                eventManagerImpl.fireCompilationFinished();
                                if (tracer != null) {
                                    try {
                                        tracer.after("compile");
                                    } catch (Throwable th2) {
                                        th = th2;
                                        i = 1;
                                        InlineMarker.finallyStart(i);
                                        this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                        InlineMarker.finallyEnd(i);
                                        throw th;
                                    }
                                }
                                InlineMarker.finallyEnd(1);
                                CompileService.CallResult.Good good2 = good;
                                InlineMarker.finallyStart(1);
                                this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                InlineMarker.finallyEnd(1);
                                return good2;
                            } catch (Throwable th3) {
                                th = th3;
                                Logger log2 = getLog();
                                Level level2 = Level.SEVERE;
                                String str8 = str2;
                                StringBuilder sbAppend5 = new StringBuilder().append("Exception: ").append(th).append(str8);
                                StackTraceElement[] stackTrace3 = th.getStackTrace();
                                String str9 = str;
                                Intrinsics.checkNotNullExpressionValue(stackTrace3, str9);
                                StringBuilder sbAppend6 = sbAppend5.append(ArraysKt.joinToString$default(stackTrace3, str8, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                if (th.getCause() == null) {
                                    string = "";
                                } else {
                                    string = "";
                                }
                                log2.log(level2, sbAppend6.append(string).toString());
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            str = "getStackTrace(...)";
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        str = "getStackTrace(...)";
                        str2 = "\n  ";
                    }
                } catch (Throwable th6) {
                    th = th6;
                    i = 1;
                    InlineMarker.finallyStart(1);
                    eventManagerImpl.fireCompilationFinished();
                    if (tracer != null) {
                        try {
                            tracer.after(str3);
                        } catch (Throwable th7) {
                            th = th7;
                            InlineMarker.finallyStart(i);
                            this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                            InlineMarker.finallyEnd(i);
                            throw th;
                        }
                    }
                    InlineMarker.finallyEnd(1);
                    throw th;
                }
            } catch (Throwable th8) {
                th = th8;
                str3 = "compile";
                i = 1;
                InlineMarker.finallyStart(1);
                eventManagerImpl.fireCompilationFinished();
                if (tracer != null) {
                    tracer.after(str3);
                }
                InlineMarker.finallyEnd(1);
                throw th;
            }
        } catch (Throwable th9) {
            th = th9;
            i = 1;
        }
    }

    public final long ms(long j) {
        return TimeUnit.NANOSECONDS.toMillis(j);
    }

    public final long kb(long j) {
        return j / ((long) 1024);
    }

    protected final <R> R checkedCompile(DaemonMessageReporter daemonMessageReporter, Profiler rpcProfiler, Function0<? extends R> body) {
        String string;
        long jFreeMemory;
        Intrinsics.checkNotNullParameter(daemonMessageReporter, "daemonMessageReporter");
        Intrinsics.checkNotNullParameter(rpcProfiler, "rpcProfiler");
        Intrinsics.checkNotNullParameter(body, "body");
        try {
            Profiler wallAndThreadAndMemoryTotalProfiler = getDaemonOptions().getReportPerf() ? new WallAndThreadAndMemoryTotalProfiler(false) : new DummyProfiler();
            List<Long> listBeginMeasure = wallAndThreadAndMemoryTotalProfiler.beginMeasure(null);
            R rInvoke = body.invoke();
            wallAndThreadAndMemoryTotalProfiler.endMeasure(null, listBeginMeasure);
            if (getDaemonOptions().getReportPerf()) {
                Runtime runtime = Runtime.getRuntime();
                jFreeMemory = runtime.totalMemory() - runtime.freeMemory();
            } else {
                jFreeMemory = 0;
            }
            getLog().info("Done with result " + rInvoke);
            if (getDaemonOptions().getReportPerf()) {
                PerfCounters totalCounters = wallAndThreadAndMemoryTotalProfiler.getTotalCounters();
                PerfCounters totalCounters2 = rpcProfiler.getTotalCounters();
                StringBuilder sbAppend = new StringBuilder("PERF: Compile on daemon: ").append(ms(totalCounters.getTime())).append(" ms; thread: user ").append(ms(totalCounters.getThreadUserTime())).append(" ms, sys ").append(ms(totalCounters.getThreadTime() - totalCounters.getThreadUserTime())).append(" ms; rpc: ").append(totalCounters2.getCount()).append(" calls, ").append(ms(totalCounters2.getTime())).append(" ms, thread ").append(ms(totalCounters2.getThreadTime())).append(" ms; memory: ").append(kb(jFreeMemory)).append(" kb (");
                String str = String.format("%+d", Arrays.copyOf(new Object[]{Long.valueOf(kb(totalCounters.getMemory()))}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                String string2 = sbAppend.append(str).append(" kb)").toString();
                daemonMessageReporter.report(ReportSeverity.INFO, string2);
                getLog().info(string2);
                for (Map.Entry<Object, PerfCounters> entry : rpcProfiler.getCounters().entrySet()) {
                    Object key = entry.getKey();
                    PerfCounters value = entry.getValue();
                    String str2 = "PERF: rpc by " + key + ": " + value.getCount() + " calls, " + ms(value.getTime()) + " ms, thread " + ms(value.getThreadTime()) + " ms";
                    daemonMessageReporter.report(ReportSeverity.INFO, str2);
                    getLog().info(str2);
                }
            }
            return rInvoke;
        } catch (Throwable th) {
            Logger log = getLog();
            Level level = Level.SEVERE;
            StringBuilder sbAppend2 = new StringBuilder("Exception: ").append(th).append("\n  ");
            StackTraceElement[] stackTrace = th.getStackTrace();
            Intrinsics.checkNotNullExpressionValue(stackTrace, "getStackTrace(...)");
            StringBuilder sbAppend3 = sbAppend2.append(ArraysKt.joinToString$default(stackTrace, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
            if (th.getCause() != null && !Intrinsics.areEqual(th.getCause(), th)) {
                StringBuilder sbAppend4 = new StringBuilder("\nCaused by: ").append(th.getCause()).append("\n  ");
                Throwable cause = th.getCause();
                Intrinsics.checkNotNull(cause);
                StackTraceElement[] stackTrace2 = cause.getStackTrace();
                Intrinsics.checkNotNullExpressionValue(stackTrace2, "getStackTrace(...)");
                string = sbAppend4.append(ArraysKt.joinToString$default(stackTrace2, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
            } else {
                string = "";
            }
            log.log(level, sbAppend3.append(string).toString());
            throw th;
        }
    }

    public final void startDaemonElections() {
        this.timer.schedule(new TimerTask() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImplBase$startDaemonElections$$inlined$schedule$1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                CompileServiceImplBase compileServiceImplBase = this.this$0;
                try {
                    compileServiceImplBase.initiateElections();
                } catch (Throwable th) {
                    System.err.println("Exception in timer thread: " + th.getMessage());
                    th.printStackTrace(System.err);
                    compileServiceImplBase.getLog().log(Level.SEVERE, "Exception in timer thread", th);
                }
            }
        }, 10L);
    }

    public final void configurePeriodicActivities() {
        this.timer.schedule(new TimerTask() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImplBase$configurePeriodicActivities$$inlined$schedule$1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                CompileServiceImplBase compileServiceImplBase = this.this$0;
                try {
                    compileServiceImplBase.periodicAndAfterSessionCheck();
                } catch (Throwable th) {
                    System.err.println("Exception in timer thread: " + th.getMessage());
                    th.printStackTrace(System.err);
                    compileServiceImplBase.getLog().log(Level.SEVERE, "Exception in timer thread", th);
                }
            }
        }, KotlinCompileDaemonKt.getDAEMON_PERIODIC_CHECK_INTERVAL_MS(), KotlinCompileDaemonKt.getDAEMON_PERIODIC_CHECK_INTERVAL_MS());
        this.timer.schedule(new TimerTask() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImplBase$configurePeriodicActivities$$inlined$schedule$2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                CompileServiceImplBase compileServiceImplBase = this.this$0;
                try {
                    compileServiceImplBase.periodicSeldomCheck();
                } catch (Throwable th) {
                    System.err.println("Exception in timer thread: " + th.getMessage());
                    th.printStackTrace(System.err);
                    compileServiceImplBase.getLog().log(Level.SEVERE, "Exception in timer thread", th);
                }
            }
        }, KotlinCompileDaemonKt.getDAEMON_PERIODIC_SELDOM_CHECK_INTERVAL_MS() + ((long) 100), KotlinCompileDaemonKt.getDAEMON_PERIODIC_SELDOM_CHECK_INTERVAL_MS());
    }

    public static /* synthetic */ CompileService.CallResult ifAliveChecksImpl$default(CompileServiceImplBase compileServiceImplBase, Aliveness minAliveness, Function0 body, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                minAliveness = Aliveness.LastSession;
            }
            Intrinsics.checkNotNullParameter(minAliveness, "minAliveness");
            Intrinsics.checkNotNullParameter(body, "body");
            int i2 = compileServiceImplBase.state.getAlive().get();
            if (i2 < minAliveness.ordinal()) {
                compileServiceImplBase.getLog().info("Cannot perform operation, requested state: " + minAliveness.name() + " > actual: " + compileServiceImplBase.toAlivenessName(i2));
                return new CompileService.CallResult.Dying();
            }
            try {
                return (CompileService.CallResult) body.invoke();
            } catch (Throwable th) {
                compileServiceImplBase.getLog().log(Level.SEVERE, "Exception", th);
                return new CompileService.CallResult.Error(th);
            }
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: ifAliveChecksImpl");
    }

    protected final <R> CompileService.CallResult<R> ifAliveChecksImpl(Aliveness minAliveness, Function0<? extends CompileService.CallResult<? extends R>> body) {
        Intrinsics.checkNotNullParameter(minAliveness, "minAliveness");
        Intrinsics.checkNotNullParameter(body, "body");
        int i = this.state.getAlive().get();
        if (i < minAliveness.ordinal()) {
            getLog().info("Cannot perform operation, requested state: " + minAliveness.name() + " > actual: " + toAlivenessName(i));
            return new CompileService.CallResult.Dying();
        }
        try {
            return body.invoke();
        } catch (Throwable th) {
            getLog().log(Level.SEVERE, "Exception", th);
            return new CompileService.CallResult.Error(th);
        }
    }

    protected final <R> CompileService.CallResult<R> withValidClientOrSessionProxy(int sessionId, Function1<? super ClientOrSessionProxy<? extends Object>, ? extends CompileService.CallResult<? extends R>> body) {
        ClientOrSessionProxy<Object> clientOrSessionProxy;
        Intrinsics.checkNotNullParameter(body, "body");
        if (sessionId == CompileService.INSTANCE.getNO_SESSION()) {
            clientOrSessionProxy = null;
        } else {
            ClientOrSessionProxy<Object> clientOrSessionProxy2 = this.state.getSessions().get(sessionId);
            if (clientOrSessionProxy2 == null) {
                return new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
            }
            clientOrSessionProxy = clientOrSessionProxy2;
        }
        try {
            this.compilationsCounter.incrementAndGet();
            return body.invoke(clientOrSessionProxy);
        } finally {
            InlineMarker.finallyStart(1);
            this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
            InlineMarker.finallyEnd(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ExitCode execJsIncrementalCompiler(K2JSCompilerArguments args, IncrementalCompilationOptions incrementalCompilationOptions, MessageCollector compilerMessageCollector, RemoteBuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter) {
        ChangedFiles.Unknown unknown;
        ModulesApiHistory modulesApiHistoryJs;
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(incrementalCompilationOptions, "incrementalCompilationOptions");
        Intrinsics.checkNotNullParameter(compilerMessageCollector, "compilerMessageCollector");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        RemoteBuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> remoteBuildReporter = reporter;
        BuildMetricsReporterKt.startMeasureGc(remoteBuildReporter);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it = args.getFreeArgs().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str = (String) it.next();
            if (StringsKt.endsWith$default(str, KotlinFileType.DOT_DEFAULT_EXTENSION, false, 2, (Object) null) && new File(str).exists()) {
                arrayList.add(new File(str));
            } else {
                arrayList2.add(str);
            }
        }
        args.setFreeArgs(arrayList2);
        if (incrementalCompilationOptions.getAreFileChangesKnown()) {
            List<File> modifiedFiles = incrementalCompilationOptions.getModifiedFiles();
            Intrinsics.checkNotNull(modifiedFiles);
            List<File> deletedFiles = incrementalCompilationOptions.getDeletedFiles();
            Intrinsics.checkNotNull(deletedFiles);
            unknown = new ChangedFiles.Known(modifiedFiles, deletedFiles, false, 4, null);
        } else {
            unknown = new ChangedFiles.Unknown();
        }
        ChangedFiles changedFiles = unknown;
        File workingDir = incrementalCompilationOptions.getWorkingDir();
        if (incrementalCompilationOptions.getMultiModuleICSettings() != null) {
            IncrementalModuleInfo modulesInfo = incrementalCompilationOptions.getModulesInfo();
            if (modulesInfo == null) {
                throw new IllegalStateException("The build is configured to use the history-file based IC approach, but doesn't provide the modulesInfo".toString());
            }
            File rootProjectDir = incrementalCompilationOptions.getRootProjectDir();
            if (rootProjectDir == null) {
                throw new IllegalStateException("rootProjectDir is expected to be non null when the history-file based IC approach is used".toString());
            }
            modulesApiHistoryJs = new ModulesApiHistoryJs(rootProjectDir, modulesInfo);
        } else {
            modulesApiHistoryJs = EmptyModulesApiHistory.INSTANCE;
        }
        MultiModuleICSettings multiModuleICSettings = incrementalCompilationOptions.getMultiModuleICSettings();
        try {
            return IncrementalCompilerRunner.compile$default(new IncrementalJsCompilerRunner(workingDir, reporter, multiModuleICSettings != null ? multiModuleICSettings.getBuildHistoryFile() : null, modulesApiHistoryJs, K2JSCompilerArgumentsKt.isIrBackendEnabled(args) ? CompileScopeExpansionMode.ALWAYS : CompileScopeExpansionMode.NEVER, incrementalCompilationOptions.getWithAbiSnapshot(), incrementalCompilationOptions.getPreciseCompilationResultsBackup(), incrementalCompilationOptions.getKeepIncrementalCompilationCachesInMemory()), arrayList, args, compilerMessageCollector, changedFiles, null, 16, null);
        } finally {
            BuildMetricsReporterKt.endMeasureGc(remoteBuildReporter);
            reporter.flush();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code duplicated, block: B:26:0x00d2  */
    public final ExitCode execIncrementalCompiler(K2JVMCompilerArguments k2jvmArgs, IncrementalCompilationOptions incrementalCompilationOptions, MessageCollector compilerMessageCollector, RemoteBuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter) {
        ChangedFiles.Unknown unknown;
        final MultiModuleICSettings multiModuleICSettings;
        EmptyModulesApiHistory emptyModulesApiHistory;
        ModulesApiHistoryAndroid modulesApiHistoryAndroid;
        LanguageVersion languageVersionFromVersionString;
        Intrinsics.checkNotNullParameter(k2jvmArgs, "k2jvmArgs");
        Intrinsics.checkNotNullParameter(incrementalCompilationOptions, "incrementalCompilationOptions");
        Intrinsics.checkNotNullParameter(compilerMessageCollector, "compilerMessageCollector");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        RemoteBuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> remoteBuildReporter = reporter;
        BuildMetricsReporterKt.startMeasureGc(remoteBuildReporter);
        List<String> default_kotlin_source_files_extensions = SourcesUtilsKt.getDEFAULT_KOTLIN_SOURCE_FILES_EXTENSIONS();
        String[] kotlinScriptExtensions = incrementalCompilationOptions.getKotlinScriptExtensions();
        if (kotlinScriptExtensions == null) {
            kotlinScriptExtensions = new String[0];
        }
        List listDistinct = CollectionsKt.distinct(CollectionsKt.plus((Collection) default_kotlin_source_files_extensions, (Object[]) kotlinScriptExtensions));
        List list = listDistinct;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add("." + ((String) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (String str : k2jvmArgs.getFreeArgs()) {
            File file = new File(str);
            if (!file.isFile()) {
                arrayList3.add(str);
                break;
                break;
            }
            ArrayList arrayList5 = arrayList2;
            if ((arrayList5 instanceof Collection) && arrayList5.isEmpty()) {
                arrayList3.add(str);
                break;
                break;
            }
            Iterator it2 = arrayList5.iterator();
            while (true) {
                if (it2.hasNext()) {
                    String str2 = (String) it2.next();
                    String path = file.getPath();
                    Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
                    if (StringsKt.endsWith(path, str2, true)) {
                        arrayList4.add(file);
                        break;
                    }
                } else {
                    arrayList3.add(str);
                    break;
                }
            }
        }
        k2jvmArgs.setFreeArgs(arrayList3);
        if (incrementalCompilationOptions.getAreFileChangesKnown()) {
            List<File> modifiedFiles = incrementalCompilationOptions.getModifiedFiles();
            Intrinsics.checkNotNull(modifiedFiles);
            List<File> deletedFiles = incrementalCompilationOptions.getDeletedFiles();
            Intrinsics.checkNotNull(deletedFiles);
            unknown = new ChangedFiles.Known(modifiedFiles, deletedFiles, false, 4, null);
        } else {
            unknown = new ChangedFiles.Unknown();
        }
        ChangedFiles changedFiles = unknown;
        File workingDir = incrementalCompilationOptions.getWorkingDir();
        File rootProjectDir = incrementalCompilationOptions.getRootProjectDir();
        File buildDir = incrementalCompilationOptions.getBuildDir();
        if (!(incrementalCompilationOptions.getClasspathChanges() instanceof ClasspathChanges.ClasspathSnapshotEnabled) && (multiModuleICSettings = incrementalCompilationOptions.getMultiModuleICSettings()) != null) {
            ICReporterKt.info(reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImplBase$execIncrementalCompiler$modulesApiHistory$1$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return "Use module detection: " + multiModuleICSettings.getUseModuleDetection();
                }
            });
            IncrementalModuleInfo modulesInfo = incrementalCompilationOptions.getModulesInfo();
            if (modulesInfo == null) {
                throw new IllegalStateException("The build is configured to use the history-file based IC approach, but doesn't provide the modulesInfo".toString());
            }
            if (rootProjectDir == null) {
                throw new IllegalStateException("rootProjectDir is expected to be non null when the history-file based IC approach is used".toString());
            }
            if (!multiModuleICSettings.getUseModuleDetection()) {
                modulesApiHistoryAndroid = new ModulesApiHistoryJvm(rootProjectDir, modulesInfo);
            } else {
                modulesApiHistoryAndroid = new ModulesApiHistoryAndroid(rootProjectDir, modulesInfo);
            }
            emptyModulesApiHistory = modulesApiHistoryAndroid;
        } else {
            emptyModulesApiHistory = EmptyModulesApiHistory.INSTANCE;
        }
        boolean usePreciseJavaTracking = (k2jvmArgs.getUseK2() || ((languageVersionFromVersionString = LanguageVersion.INSTANCE.fromVersionString(k2jvmArgs.getLanguageVersion())) != null && languageVersionFromVersionString.getUsesK2())) ? false : incrementalCompilationOptions.getUsePreciseJavaTracking();
        MultiModuleICSettings multiModuleICSettings2 = incrementalCompilationOptions.getMultiModuleICSettings();
        try {
            return new IncrementalJvmCompilerRunner(workingDir, reporter, usePreciseJavaTracking, multiModuleICSettings2 != null ? multiModuleICSettings2.getBuildHistoryFile() : null, incrementalCompilationOptions.getOutputFiles(), emptyModulesApiHistory, listDistinct, incrementalCompilationOptions.getClasspathChanges(), incrementalCompilationOptions.getWithAbiSnapshot(), incrementalCompilationOptions.getPreciseCompilationResultsBackup(), incrementalCompilationOptions.getKeepIncrementalCompilationCachesInMemory()).compile(arrayList4, k2jvmArgs, compilerMessageCollector, changedFiles, (rootProjectDir == null || buildDir == null) ? null : new FileLocations(rootProjectDir, buildDir));
        } finally {
            BuildMetricsReporterKt.endMeasureGc(remoteBuildReporter);
            reporter.flush();
        }
    }

    protected final <R, KotlinJvmReplServiceT> CompileService.CallResult<R> withValidReplImpl(int sessionId, Function1<? super KotlinJvmReplServiceT, ? extends CompileService.CallResult<? extends R>> body) {
        ClientOrSessionProxy<Object> clientOrSessionProxy;
        CompileService.CallResult.Error error;
        Intrinsics.checkNotNullParameter(body, "body");
        Object obj = null;
        if (sessionId == CompileService.INSTANCE.getNO_SESSION()) {
            clientOrSessionProxy = null;
        } else {
            clientOrSessionProxy = this.state.getSessions().get(sessionId);
            if (clientOrSessionProxy == null) {
                return new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
            }
        }
        try {
            this.compilationsCounter.incrementAndGet();
            Object data = clientOrSessionProxy != null ? clientOrSessionProxy.getData() : null;
            if (data != null) {
                obj = data;
            }
            if (obj == null || (error = body.invoke(obj)) == null) {
                error = new CompileService.CallResult.Error("Not a REPL session " + sessionId);
            }
            return error;
        } finally {
            InlineMarker.finallyStart(1);
            this._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
            InlineMarker.finallyEnd(1);
        }
    }
}
