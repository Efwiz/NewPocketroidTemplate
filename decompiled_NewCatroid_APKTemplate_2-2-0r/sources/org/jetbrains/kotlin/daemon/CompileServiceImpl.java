package org.jetbrains.kotlin.daemon;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.File;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import net.bytebuddy.asm.Advice;
import org.jetbrains.kotlin.cli.common.CLICompiler;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.ParseCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.cli.common.repl.ReplCheckResult;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;
import org.jetbrains.kotlin.cli.common.repl.ReplCompileResult;
import org.jetbrains.kotlin.cli.js.K2JSCompiler;
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreApplicationEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.modules.CoreJrtFileSystem;
import org.jetbrains.kotlin.cli.metadata.K2MetadataCompiler;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ZipHandler;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.jar.CoreJarFileSystem;
import org.jetbrains.kotlin.config.IncrementalCompilation;
import org.jetbrains.kotlin.config.KotlinCompilerVersion;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.daemon.common.ClientUtilsKt;
import org.jetbrains.kotlin.daemon.common.CompilationOptions;
import org.jetbrains.kotlin.daemon.common.CompilationResultCategory;
import org.jetbrains.kotlin.daemon.common.CompilationResults;
import org.jetbrains.kotlin.daemon.common.CompileService;
import org.jetbrains.kotlin.daemon.common.CompilerCallbackServicesFacade;
import org.jetbrains.kotlin.daemon.common.CompilerId;
import org.jetbrains.kotlin.daemon.common.CompilerServicesFacadeBase;
import org.jetbrains.kotlin.daemon.common.DaemonJVMOptions;
import org.jetbrains.kotlin.daemon.common.DaemonJVMOptionsMemoryComparator;
import org.jetbrains.kotlin.daemon.common.DaemonOptions;
import org.jetbrains.kotlin.daemon.common.DaemonParamsKt;
import org.jetbrains.kotlin.daemon.common.DaemonReportCategory;
import org.jetbrains.kotlin.daemon.common.DaemonWithMetadata;
import org.jetbrains.kotlin.daemon.common.DummyProfiler;
import org.jetbrains.kotlin.daemon.common.FileAgeComparator;
import org.jetbrains.kotlin.daemon.common.IncrementalCompilationOptions;
import org.jetbrains.kotlin.daemon.common.JpsCompilerServicesFacade;
import org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface;
import org.jetbrains.kotlin.daemon.common.PerfCounters;
import org.jetbrains.kotlin.daemon.common.Profiler;
import org.jetbrains.kotlin.daemon.common.ReplStateFacade;
import org.jetbrains.kotlin.daemon.common.ReportSeverity;
import org.jetbrains.kotlin.daemon.common.WallAndThreadAndMemoryTotalProfiler;
import org.jetbrains.kotlin.daemon.common.WallAndThreadTotalProfiler;
import org.jetbrains.kotlin.daemon.report.CompileServicesFacadeMessageCollector;
import org.jetbrains.kotlin.daemon.report.DaemonMessageReporter;
import org.jetbrains.kotlin.daemon.report.DaemonMessageReporterKt;
import org.jetbrains.kotlin.daemon.report.GetICReporterKt;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer;
import org.jetbrains.kotlin.incremental.parsing.ParseFileUtilsKt;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.progress.CompilationCanceledStatus;

/* JADX INFO: compiled from: CompileServiceImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000æ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002BK\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012¢\u0006\u0002\u0010\u0014J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\bH\u0016J*\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0(0'2\u0006\u0010*\u001a\u00020\u000e2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020,0(H\u0016J\b\u0010-\u001a\u00020\u0013H\u0016JE\u0010.\u001a\b\u0012\u0004\u0012\u00020\u000e0'2\u0006\u0010*\u001a\u00020\u000e2\u000e\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020)002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\b\u00105\u001a\u0004\u0018\u000106H\u0016¢\u0006\u0002\u00107J \u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0002J\u0014\u0010@\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0A0'H\u0016J\u000e\u0010B\u001a\b\u0012\u0004\u0012\u00020)0'H\u0016J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\f0'H\u0016J\u000e\u0010C\u001a\b\u0012\u0004\u0012\u00020\n0'H\u0016J\u000e\u0010D\u001a\b\u0012\u0004\u0012\u00020)0'H\u0016J\u0016\u0010E\u001a\b\u0012\u0004\u0012\u00020\u001a0'2\u0006\u0010F\u001a\u00020$H\u0016J\u0010\u0010G\u001a\u00020$2\u0006\u0010H\u001a\u00020$H\u0002J3\u0010I\u001a\b\u0012\u0004\u0012\u0002HJ0'\"\u0004\b\u0000\u0010J2\b\b\u0002\u0010K\u001a\u00020L2\u0012\u0010M\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002HJ0'0\u0012H\u0082\bJ3\u0010N\u001a\b\u0012\u0004\u0012\u0002HJ0'\"\u0004\b\u0000\u0010J2\b\b\u0002\u0010K\u001a\u00020L2\u0012\u0010M\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002HJ0'0\u0012H\u0082\bJ!\u0010O\u001a\u00020\u00132\b\b\u0002\u0010K\u001a\u00020L2\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0082\bJ!\u0010P\u001a\u00020\u00132\b\b\u0002\u0010K\u001a\u00020L2\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0082\bJ\b\u0010Q\u001a\u00020\u0013H\u0014J\u0018\u0010R\u001a\b\u0012\u0004\u0012\u00020\u000e0'2\b\u0010S\u001a\u0004\u0018\u00010)H\u0016JS\u0010T\u001a\b\u0012\u0004\u0012\u00020\u000e0'2\b\u0010S\u001a\u0004\u0018\u00010)2\u000e\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020)002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\f\u0010U\u001a\b\u0012\u0004\u0012\u00020,0A2\u0006\u0010V\u001a\u00020)H\u0016¢\u0006\u0002\u0010WJ\b\u0010X\u001a\u00020\u0013H\u0014J\b\u0010Y\u001a\u00020\u0013H\u0014J\u0018\u0010Z\u001a\b\u0012\u0004\u0012\u00020[0'2\b\u0010S\u001a\u0004\u0018\u00010)H\u0016J\u0016\u0010\\\u001a\b\u0012\u0004\u0012\u00020[0'2\u0006\u0010*\u001a\u00020\u000eH\u0016J\u0016\u0010]\u001a\b\u0012\u0004\u0012\u00020[0'2\u0006\u0010*\u001a\u00020\u000eH\u0016J&\u0010^\u001a\b\u0012\u0004\u0012\u00020_0'2\u0006\u0010*\u001a\u00020\u000e2\u0006\u0010`\u001a\u00020\u000e2\u0006\u0010a\u001a\u00020bH\u0016J&\u0010c\u001a\b\u0012\u0004\u0012\u00020d0'2\u0006\u0010*\u001a\u00020\u000e2\u0006\u0010`\u001a\u00020\u000e2\u0006\u0010a\u001a\u00020bH\u0016J\u0016\u0010e\u001a\b\u0012\u0004\u0012\u00020f0'2\u0006\u0010*\u001a\u00020\u000eH\u0016J\u0016\u0010g\u001a\b\u0012\u0004\u0012\u00020$0'2\u0006\u0010h\u001a\u00020$H\u0016J\u000e\u0010i\u001a\b\u0012\u0004\u0012\u00020[0'H\u0016J\b\u0010j\u001a\u00020\u0013H\u0002J\b\u0010k\u001a\u00020\u0013H\u0002J<\u0010l\u001a\b\u0012\u0004\u0012\u0002HJ0'\"\u0004\b\u0000\u0010J2\u0006\u0010*\u001a\u00020\u000e2\u001d\u0010M\u001a\u0019\u0012\u0004\u0012\u00020n\u0012\n\u0012\b\u0012\u0004\u0012\u0002HJ0'0m¢\u0006\u0002\boH\u0082\bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001a8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006p"}, d2 = {"Lorg/jetbrains/kotlin/daemon/CompileServiceImpl;", "Lorg/jetbrains/kotlin/daemon/common/CompileService;", "Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase;", "registry", "Ljava/rmi/registry/Registry;", "compiler", "Lorg/jetbrains/kotlin/daemon/CompilerSelector;", "compilerId", "Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "daemonOptions", "Lorg/jetbrains/kotlin/daemon/common/DaemonOptions;", "daemonJVMOptions", "Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "port", "", "timer", "Ljava/util/Timer;", "onShutdown", "Lkotlin/Function0;", "", "(Ljava/rmi/registry/Registry;Lorg/jetbrains/kotlin/daemon/CompilerSelector;Lorg/jetbrains/kotlin/daemon/common/CompilerId;Lorg/jetbrains/kotlin/daemon/common/DaemonOptions;Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;ILjava/util/Timer;Lkotlin/jvm/functions/Function0;)V", "getCompiler", "()Lorg/jetbrains/kotlin/daemon/CompilerSelector;", "getDaemonJVMOptions", "()Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "lastUsedSeconds", "", "getLastUsedSeconds", "()J", "getOnShutdown", "()Lkotlin/jvm/functions/Function0;", "getRegistry", "()Ljava/rmi/registry/Registry;", "rwlock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "checkCompilerId", "", "expectedCompilerId", "classesFqNamesByFiles", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "", "", "sessionId", "sourceFiles", "Ljava/io/File;", "clearJarCache", "compile", "compilerArguments", "", "compilationOptions", "Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;", "servicesFacade", "Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;", "compilationResults", "Lorg/jetbrains/kotlin/daemon/common/CompilationResults;", "(I[Ljava/lang/String;Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;Lorg/jetbrains/kotlin/daemon/common/CompilationResults;)Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "createCompileServices", "Lorg/jetbrains/kotlin/config/Services;", "facade", "Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "eventManager", "Lorg/jetbrains/kotlin/daemon/EventManager;", "rpcProfiler", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "getClients", "", "getDaemonInfo", "getDaemonOptions", "getKotlinVersion", "getUsedMemory", "withGC", "gracefulShutdown", "onAnotherThread", "ifAlive", "R", "minAliveness", "Lorg/jetbrains/kotlin/daemon/CompileServiceImplBase$Aliveness;", "body", "ifAliveExclusive", "ifAliveExclusiveUnit", "ifAliveUnit", "initiateElections", "leaseCompileSession", "aliveFlagPath", "leaseReplSession", "templateClasspath", "templateClassName", "(Ljava/lang/String;[Ljava/lang/String;Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;Ljava/util/List;Ljava/lang/String;)Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "periodicAndAfterSessionCheck", "periodicSeldomCheck", "registerClient", "", "releaseCompileSession", "releaseReplSession", "replCheck", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult;", "replStateId", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "replCompile", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", "replCreateState", "Lorg/jetbrains/kotlin/daemon/common/ReplStateFacade;", "scheduleShutdown", "graceful", "shutdown", "shutdownNow", "shutdownWithDelay", "withValidRepl", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/daemon/KotlinJvmReplService;", "Lkotlin/ExtensionFunctionType;", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompileServiceImpl extends CompileServiceImplBase implements CompileService {
    private final CompilerSelector compiler;
    private final DaemonJVMOptions daemonJVMOptions;
    private final Function0<Unit> onShutdown;
    private final Registry registry;
    private final ReentrantReadWriteLock rwlock;

    public final Registry getRegistry() {
        return this.registry;
    }

    public final CompilerSelector getCompiler() {
        return this.compiler;
    }

    public final DaemonJVMOptions getDaemonJVMOptions() {
        return this.daemonJVMOptions;
    }

    public final Function0<Unit> getOnShutdown() {
        return this.onShutdown;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CompileServiceImpl(Registry registry, CompilerSelector compiler, CompilerId compilerId, DaemonOptions daemonOptions, DaemonJVMOptions daemonJVMOptions, int i, Timer timer, Function0<Unit> onShutdown) {
        super(daemonOptions, compilerId, i, timer);
        Intrinsics.checkNotNullParameter(registry, "registry");
        Intrinsics.checkNotNullParameter(compiler, "compiler");
        Intrinsics.checkNotNullParameter(compilerId, "compilerId");
        Intrinsics.checkNotNullParameter(daemonOptions, "daemonOptions");
        Intrinsics.checkNotNullParameter(daemonJVMOptions, "daemonJVMOptions");
        Intrinsics.checkNotNullParameter(timer, "timer");
        Intrinsics.checkNotNullParameter(onShutdown, "onShutdown");
        this.registry = registry;
        this.compiler = compiler;
        this.daemonJVMOptions = daemonJVMOptions;
        this.onShutdown = onShutdown;
        this.rwlock = new ReentrantReadWriteLock();
        try {
            UnicastRemoteObject.unexportObject(this, false);
        } catch (NoSuchObjectException unused) {
        }
        Remote remoteExportObject = UnicastRemoteObject.exportObject(this, i, LoopbackNetworkInterface.INSTANCE.getClientLoopbackSocketFactory(), LoopbackNetworkInterface.INSTANCE.getServerLoopbackSocketFactory());
        Intrinsics.checkNotNull(remoteExportObject, "null cannot be cast to non-null type org.jetbrains.kotlin.daemon.common.CompileService");
        this.registry.rebind(DaemonParamsKt.COMPILER_SERVICE_RMI_NAME, (CompileService) remoteExportObject);
    }

    private final <R> CompileService.CallResult<R> withValidRepl(int sessionId, Function1<? super KotlinJvmReplService, ? extends CompileService.CallResult<? extends R>> body) {
        CompileServiceImplBase.ClientOrSessionProxy<Object> clientOrSessionProxy;
        CompileService.CallResult.Error error;
        CompileServiceImpl compileServiceImpl = this;
        Object obj = null;
        if (sessionId == CompileService.INSTANCE.getNO_SESSION()) {
            clientOrSessionProxy = null;
        } else {
            clientOrSessionProxy = ((CompileServiceImplBase) compileServiceImpl).state.getSessions().get(sessionId);
            if (clientOrSessionProxy == null) {
                return new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
            }
        }
        try {
            ((CompileServiceImplBase) compileServiceImpl).compilationsCounter.incrementAndGet();
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
            ((CompileServiceImplBase) compileServiceImpl)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
            InlineMarker.finallyEnd(1);
        }
    }

    @Override // org.jetbrains.kotlin.daemon.CompileServiceImplBase
    protected long getLastUsedSeconds() {
        return (this.rwlock.isWriteLocked() || this.rwlock.getReadLockCount() - this.rwlock.getReadHoldCount() > 0) ? CompileServiceImplKt.nowSeconds() : get_lastUsedSeconds();
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<String> getDaemonInfo() {
        CompileService.CallResult.Good error;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.Dying;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    error = new CompileService.CallResult.Good("Kotlin daemon on port " + getPort());
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            lock.unlock();
            return error;
        } catch (Throwable th2) {
            lock.unlock();
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult registerClient(String aliveFlagPath) {
        CompileService.CallResult.Ok error;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.Alive;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    getState().addClient(aliveFlagPath);
                    getLog().info("Registered a client alive file: " + aliveFlagPath);
                    error = new CompileService.CallResult.Ok();
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            return error;
        } finally {
            lock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<Integer> leaseCompileSession(String aliveFlagPath) {
        CompileService.CallResult.Good error;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.Alive;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    Integer numValueOf = Integer.valueOf(getState().getSessions().leaseSession(new CompileServiceImplBase.ClientOrSessionProxy(aliveFlagPath, null, null, 6, null)));
                    getLog().info("leased a new session " + numValueOf.intValue() + ", session alive file: " + aliveFlagPath);
                    error = new CompileService.CallResult.Good(numValueOf);
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            lock.unlock();
            return error;
        } catch (Throwable th2) {
            lock.unlock();
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult releaseCompileSession(int sessionId) {
        CompileService.CallResult.Dying error;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    getState().getSessions().remove(sessionId);
                    getLog().info("cleaning after session " + sessionId);
                    ReentrantReadWriteLock reentrantReadWriteLock = this.rwlock;
                    ReentrantReadWriteLock.ReadLock lock2 = reentrantReadWriteLock.readLock();
                    int i2 = 0;
                    int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
                    for (int i3 = 0; i3 < readHoldCount; i3++) {
                        lock2.unlock();
                    }
                    ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
                    writeLock.lock();
                    try {
                        clearJarCache();
                        Unit unit = Unit.INSTANCE;
                        while (i2 < readHoldCount) {
                            lock2.lock();
                            i2++;
                        }
                        writeLock.unlock();
                        error = postReleaseCompileSession();
                    } catch (Throwable th) {
                        while (i2 < readHoldCount) {
                            lock2.lock();
                            i2++;
                        }
                        writeLock.unlock();
                        throw th;
                    }
                } catch (Throwable th2) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th2);
                    error = new CompileService.CallResult.Error(th2);
                }
            }
            lock.unlock();
            return error;
        } catch (Throwable th3) {
            lock.unlock();
            throw th3;
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0065 A[RETURN, SYNTHETIC] */
    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public boolean checkCompilerId(CompilerId expectedCompilerId) {
        Intrinsics.checkNotNullParameter(expectedCompilerId, "expectedCompilerId");
        if (getCompilerId().getCompilerVersion().length() == 0 || Intrinsics.areEqual(getCompilerId().getCompilerVersion(), expectedCompilerId.getCompilerVersion())) {
            List<String> compilerClasspath = getCompilerId().getCompilerClasspath();
            if ((compilerClasspath instanceof Collection) && compilerClasspath.isEmpty()) {
                if (!getClasspathWatcher().isChanged()) {
                    return true;
                }
            } else {
                Iterator<T> it = compilerClasspath.iterator();
                while (it.hasNext()) {
                    if (!expectedCompilerId.getCompilerClasspath().contains((String) it.next())) {
                    }
                }
                if (!getClasspathWatcher().isChanged()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult shutdown() {
        CompileService.CallResult.Ok error;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock reentrantReadWriteLock = this.rwlock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i3 = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i3 < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i3));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    shutdownWithDelay();
                    error = new CompileService.CallResult.Ok();
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
            return error;
        } catch (Throwable th2) {
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<Boolean> scheduleShutdown(boolean graceful) {
        CompileService.CallResult.Good error;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                boolean zGracefulShutdown = true;
                try {
                    if (graceful) {
                        zGracefulShutdown = gracefulShutdown(true);
                    } else {
                        shutdownWithDelay();
                    }
                    error = new CompileService.CallResult.Good(Boolean.valueOf(zGracefulShutdown));
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            lock.unlock();
            return error;
        } catch (Throwable th2) {
            lock.unlock();
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult releaseReplSession(int sessionId) {
        return releaseCompileSession(sessionId);
    }

    private final Services createCompileServices(CompilerCallbackServicesFacade facade, EventManager eventManager, Profiler rpcProfiler) {
        Services.Builder builder = new Services.Builder();
        if (facade.hasIncrementalCaches()) {
            builder.register(IncrementalCompilationComponents.class, new RemoteIncrementalCompilationComponentsClient(facade, rpcProfiler));
        }
        if (facade.hasLookupTracker()) {
            builder.register(LookupTracker.class, new RemoteLookupTrackerClient(facade, eventManager, rpcProfiler));
        }
        if (facade.hasCompilationCanceledStatus()) {
            builder.register(CompilationCanceledStatus.class, new RemoteCompilationCanceledStatusClient(facade, rpcProfiler));
        }
        if (facade.hasExpectActualTracker()) {
            builder.register(ExpectActualTracker.class, new RemoteExpectActualTracker(facade, rpcProfiler));
        }
        if (facade.hasInlineConstTracker()) {
            builder.register(InlineConstTracker.class, new RemoteInlineConstTracker(facade, rpcProfiler));
        }
        if (facade.hasEnumWhenTracker()) {
            builder.register(EnumWhenTracker.class, new RemoteEnumWhenTracker(facade, rpcProfiler));
        }
        if (facade.hasIncrementalResultsConsumer()) {
            builder.register(IncrementalResultsConsumer.class, new RemoteIncrementalResultsConsumer(facade, eventManager, rpcProfiler));
        }
        if (facade.hasIncrementalDataProvider()) {
            builder.register(IncrementalDataProvider.class, new RemoteIncrementalDataProvider(facade, rpcProfiler));
        }
        return builder.build();
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<Integer> leaseReplSession(String aliveFlagPath, String[] compilerArguments, CompilationOptions compilationOptions, CompilerServicesFacadeBase servicesFacade, List<? extends File> templateClasspath, String templateClassName) {
        CompileService.CallResult.Good error;
        Intrinsics.checkNotNullParameter(compilerArguments, "compilerArguments");
        Intrinsics.checkNotNullParameter(compilationOptions, "compilationOptions");
        Intrinsics.checkNotNullParameter(servicesFacade, "servicesFacade");
        Intrinsics.checkNotNullParameter(templateClasspath, "templateClasspath");
        Intrinsics.checkNotNullParameter(templateClassName, "templateClassName");
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.Alive;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    if (compilationOptions.getTargetPlatform() != CompileService.TargetPlatform.JVM) {
                        error = new CompileService.CallResult.Error("Sorry, only JVM target platform is supported now");
                    } else {
                        Disposable disposableNewDisposable = Disposer.newDisposable();
                        Intrinsics.checkNotNullExpressionValue(disposableNewDisposable, "newDisposable(...)");
                        error = new CompileService.CallResult.Good(Integer.valueOf(getState().getSessions().leaseSession(new CompileServiceImplBase.ClientOrSessionProxy(aliveFlagPath, new KotlinJvmReplService(disposableNewDisposable, getPort(), getCompilerId(), templateClasspath, templateClassName, new CompileServicesFacadeMessageCollector(servicesFacade, compilationOptions), null), disposableNewDisposable))));
                    }
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            return error;
        } finally {
            lock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<ReplStateFacade> replCreateState(int sessionId) {
        CompileService.CallResult.Error error;
        CompileService.CallResult.Dying dying;
        CompileServiceImplBase.ClientOrSessionProxy<Object> clientOrSessionProxy;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.Alive;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                dying = new CompileService.CallResult.Dying();
            } else {
                try {
                    CompileServiceImpl compileServiceImpl2 = this;
                    Object obj = null;
                    try {
                        if (sessionId == CompileService.INSTANCE.getNO_SESSION()) {
                            clientOrSessionProxy = null;
                        } else {
                            clientOrSessionProxy = ((CompileServiceImplBase) compileServiceImpl2).state.getSessions().get(sessionId);
                            if (clientOrSessionProxy == null) {
                                error = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
                            }
                            dying = error;
                        }
                        ((CompileServiceImplBase) compileServiceImpl2).compilationsCounter.incrementAndGet();
                        Object data = clientOrSessionProxy != null ? clientOrSessionProxy.getData() : null;
                        if (data != null) {
                            obj = data;
                        }
                        if (obj != null) {
                            error = new CompileService.CallResult.Good(((KotlinJvmReplService) obj).createRemoteState(getPort()));
                        } else {
                            error = new CompileService.CallResult.Error("Not a REPL session " + sessionId);
                        }
                        ((CompileServiceImplBase) compileServiceImpl2)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                    } catch (Throwable th) {
                        ((CompileServiceImplBase) compileServiceImpl2)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                        throw th;
                    }
                } catch (Throwable th2) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th2);
                    error = new CompileService.CallResult.Error(th2);
                }
                dying = error;
            }
            lock.unlock();
            return dying;
        } catch (Throwable th3) {
            lock.unlock();
            throw th3;
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<ReplCheckResult> replCheck(int sessionId, int replStateId, final ReplCodeLine codeLine) {
        CompileService.CallResult.Error error;
        CompileService.CallResult.Dying dying;
        CompileServiceImplBase.ClientOrSessionProxy<Object> clientOrSessionProxy;
        final KotlinJvmReplService kotlinJvmReplService;
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.Alive;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                dying = new CompileService.CallResult.Dying();
            } else {
                try {
                    CompileServiceImpl compileServiceImpl2 = this;
                    Object obj = null;
                    try {
                        if (sessionId == CompileService.INSTANCE.getNO_SESSION()) {
                            clientOrSessionProxy = null;
                        } else {
                            clientOrSessionProxy = ((CompileServiceImplBase) compileServiceImpl2).state.getSessions().get(sessionId);
                            if (clientOrSessionProxy == null) {
                                error = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
                            }
                            dying = error;
                        }
                        ((CompileServiceImplBase) compileServiceImpl2).compilationsCounter.incrementAndGet();
                        Object data = clientOrSessionProxy != null ? clientOrSessionProxy.getData() : null;
                        if (data != null) {
                            obj = data;
                        }
                        if (obj == null || (error = (kotlinJvmReplService = (KotlinJvmReplService) obj).withValidReplState(replStateId, new Function1<IReplStageState<?>, ReplCheckResult>() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImpl$replCheck$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final ReplCheckResult invoke(IReplStageState<?> state) {
                                Intrinsics.checkNotNullParameter(state, "state");
                                return kotlinJvmReplService.check(state, codeLine);
                            }
                        })) == null) {
                            error = new CompileService.CallResult.Error("Not a REPL session " + sessionId);
                        }
                        ((CompileServiceImplBase) compileServiceImpl2)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                    } catch (Throwable th) {
                        ((CompileServiceImplBase) compileServiceImpl2)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                        throw th;
                    }
                } catch (Throwable th2) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th2);
                    error = new CompileService.CallResult.Error(th2);
                }
                dying = error;
            }
            lock.unlock();
            return dying;
        } catch (Throwable th3) {
            lock.unlock();
            throw th3;
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<ReplCompileResult> replCompile(int sessionId, int replStateId, final ReplCodeLine codeLine) {
        CompileService.CallResult.Error error;
        CompileService.CallResult.Dying dying;
        CompileServiceImplBase.ClientOrSessionProxy<Object> clientOrSessionProxy;
        final KotlinJvmReplService kotlinJvmReplService;
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.Alive;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                dying = new CompileService.CallResult.Dying();
            } else {
                try {
                    CompileServiceImpl compileServiceImpl2 = this;
                    Object obj = null;
                    try {
                        if (sessionId == CompileService.INSTANCE.getNO_SESSION()) {
                            clientOrSessionProxy = null;
                        } else {
                            clientOrSessionProxy = ((CompileServiceImplBase) compileServiceImpl2).state.getSessions().get(sessionId);
                            if (clientOrSessionProxy == null) {
                                error = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
                            }
                            dying = error;
                        }
                        ((CompileServiceImplBase) compileServiceImpl2).compilationsCounter.incrementAndGet();
                        Object data = clientOrSessionProxy != null ? clientOrSessionProxy.getData() : null;
                        if (data != null) {
                            obj = data;
                        }
                        if (obj == null || (error = (kotlinJvmReplService = (KotlinJvmReplService) obj).withValidReplState(replStateId, new Function1<IReplStageState<?>, ReplCompileResult>() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImpl$replCompile$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final ReplCompileResult invoke(IReplStageState<?> state) {
                                Intrinsics.checkNotNullParameter(state, "state");
                                return kotlinJvmReplService.compile(state, codeLine);
                            }
                        })) == null) {
                            error = new CompileService.CallResult.Error("Not a REPL session " + sessionId);
                        }
                        ((CompileServiceImplBase) compileServiceImpl2)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                    } catch (Throwable th) {
                        ((CompileServiceImplBase) compileServiceImpl2)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                        throw th;
                    }
                } catch (Throwable th2) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th2);
                    error = new CompileService.CallResult.Error(th2);
                }
                dying = error;
            }
            lock.unlock();
            return dying;
        } catch (Throwable th3) {
            lock.unlock();
            throw th3;
        }
    }

    /* JADX WARN: Type inference failed for: r0v12, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    /* JADX WARN: Type inference failed for: r0v4, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    /* JADX WARN: Type inference failed for: r0v6, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    /* JADX WARN: Type inference failed for: r1v4, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    /* JADX WARN: Type inference failed for: r4v19, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    /* JADX WARN: Type inference failed for: r4v23, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    @Override // org.jetbrains.kotlin.daemon.CompileServiceImplBase
    protected void periodicAndAfterSessionCheck() {
        if (getState().getDelayedShutdownQueued().get()) {
            return;
        }
        boolean z = getState().getSessions().cleanDead() || getState().cleanDeadClients();
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                new CompileService.CallResult.Dying();
            } else {
                try {
                    if (getState().getAlive().get() == CompileServiceImplBase.Aliveness.LastSession.ordinal() && getState().getSessions().isEmpty()) {
                        getLog().info("All sessions finished");
                        shutdownWithDelay();
                        lock.unlock();
                        return;
                    } else if (getState().getAliveClientsCount() == 0) {
                        getLog().info("No more clients left");
                        shutdownWithDelay();
                        lock.unlock();
                        return;
                    } else {
                        if (!getRunFile().exists()) {
                            getLog().info("Run file removed");
                            shutdownWithDelay();
                            lock.unlock();
                            return;
                        }
                        new CompileService.CallResult.Ok();
                    }
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    new CompileService.CallResult.Error(th);
                }
            }
            Unit unit = Unit.INSTANCE;
            lock.unlock();
            CompileServiceImplBase.Aliveness aliveness2 = CompileServiceImplBase.Aliveness.Alive;
            ReentrantReadWriteLock.ReadLock lock2 = this.rwlock.readLock();
            lock2.lock();
            try {
                CompileServiceImpl compileServiceImpl2 = this;
                int i2 = ((CompileServiceImplBase) compileServiceImpl2).state.getAlive().get();
                if (i2 < aliveness2.ordinal()) {
                    compileServiceImpl2.getLog().info("Cannot perform operation, requested state: " + aliveness2.name() + " > actual: " + compileServiceImpl2.toAlivenessName(i2));
                    new CompileService.CallResult.Dying();
                } else {
                    try {
                        if (getDaemonOptions().getAutoshutdownUnusedSeconds() != 0 && getCompilationsCounter().get() == 0 && CompileServiceImplKt.nowSeconds() - getLastUsedSeconds() > getDaemonOptions().getAutoshutdownUnusedSeconds()) {
                            getLog().info("Unused timeout exceeded " + getDaemonOptions().getAutoshutdownUnusedSeconds() + Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL);
                            gracefulShutdown(false);
                        } else if (getDaemonOptions().getAutoshutdownIdleSeconds() != 0 && CompileServiceImplKt.nowSeconds() - getLastUsedSeconds() > getDaemonOptions().getAutoshutdownIdleSeconds()) {
                            getLog().info("Idle timeout exceeded " + getDaemonOptions().getAutoshutdownIdleSeconds() + Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL);
                            gracefulShutdown(false);
                        } else if (z) {
                            clearJarCache();
                        }
                        new CompileService.CallResult.Ok();
                    } catch (Throwable th2) {
                        compileServiceImpl2.getLog().log(Level.SEVERE, "Exception", th2);
                        new CompileService.CallResult.Error(th2);
                    }
                }
                Unit unit2 = Unit.INSTANCE;
                lock2.unlock();
            } catch (Throwable th3) {
                lock2.unlock();
                throw th3;
            }
        } catch (Throwable th4) {
            lock.unlock();
            throw th4;
        }
    }

    @Override // org.jetbrains.kotlin.daemon.CompileServiceImplBase
    protected void periodicSeldomCheck() {
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.Alive;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                new CompileService.CallResult.Dying();
            } else {
                try {
                    if (getClasspathWatcher().isChanged()) {
                        getLog().info("Compiler changed.");
                        gracefulShutdown(false);
                    }
                    new CompileService.CallResult.Ok();
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    new CompileService.CallResult.Error(th);
                }
            }
            Unit unit = Unit.INSTANCE;
            lock.unlock();
        } catch (Throwable th2) {
            lock.unlock();
            throw th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void shutdownNow() {
        getLog().info("Shutdown started");
        Runtime runtime = Runtime.getRuntime();
        getLog().info("Memory stats: total: " + shutdownNow$mb(runtime.totalMemory()) + "mb, free: " + shutdownNow$mb(runtime.freeMemory()) + "mb, max: " + shutdownNow$mb(runtime.maxMemory()) + "mb");
        getState().getAlive().set(CompileServiceImplBase.Aliveness.Dying.ordinal());
        UnicastRemoteObject.unexportObject(this, true);
        getLog().info("Shutdown complete");
        this.onShutdown.invoke();
        Handler[] handlers = getLog().getHandlers();
        Intrinsics.checkNotNullExpressionValue(handlers, "getHandlers(...)");
        for (Handler handler : handlers) {
            handler.flush();
        }
    }

    private static final long shutdownNow$mb(long j) {
        return j / ((long) 1048576);
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    /* JADX WARN: Type inference failed for: r5v1, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    private final void shutdownWithDelay() {
        getState().getDelayedShutdownQueued().set(true);
        final int clientsCounter = getState().getClientsCounter();
        final int lastSessionId = getState().getSessions().getLastSessionId();
        final int i = getCompilationsCounter().get();
        getLog().info("Delayed shutdown in " + getDaemonOptions().getShutdownDelayMilliseconds() + LanguageCodeUtil.MS);
        getTimer().schedule(new TimerTask() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImpl$shutdownWithDelay$$inlined$schedule$1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                int i2 = 0;
                this.this$0.getState().getDelayedShutdownQueued().set(false);
                if (clientsCounter == this.this$0.getState().getClientsCounter() && i == this.this$0.getCompilationsCounter().get() && lastSessionId == this.this$0.getState().getSessions().getLastSessionId()) {
                    CompileServiceImpl compileServiceImpl = this.this$0;
                    CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
                    ReentrantReadWriteLock reentrantReadWriteLock = compileServiceImpl.rwlock;
                    ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
                    int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
                    for (int i3 = 0; i3 < readHoldCount; i3++) {
                        lock.unlock();
                    }
                    ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
                    writeLock.lock();
                    try {
                        CompileServiceImpl compileServiceImpl2 = compileServiceImpl;
                        int i4 = ((CompileServiceImplBase) compileServiceImpl2).state.getAlive().get();
                        if (i4 < aliveness.ordinal()) {
                            compileServiceImpl2.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl2.toAlivenessName(i4));
                            new CompileService.CallResult.Dying();
                        } else {
                            try {
                                this.this$0.getLog().fine("Execute delayed shutdown");
                                this.this$0.shutdownNow();
                                new CompileService.CallResult.Ok();
                            } catch (Throwable th) {
                                compileServiceImpl2.getLog().log(Level.SEVERE, "Exception", th);
                                new CompileService.CallResult.Error(th);
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                        while (i2 < readHoldCount) {
                            lock.lock();
                            i2++;
                        }
                        writeLock.unlock();
                        return;
                    } catch (Throwable th2) {
                        while (i2 < readHoldCount) {
                            lock.lock();
                            i2++;
                        }
                        writeLock.unlock();
                        throw th2;
                    }
                }
                this.this$0.getLog().info("Cancel delayed shutdown due to a new activity");
            }
        }, getDaemonOptions().getShutdownDelayMilliseconds());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v3, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    /* JADX WARN: Type inference failed for: r0v4, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    /* JADX WARN: Type inference failed for: r1v3, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    /* JADX WARN: Type inference failed for: r2v0, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    /* JADX WARN: Type inference failed for: r2v3, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    /* JADX WARN: Type inference failed for: r4v1, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    public static final void gracefulShutdown$shutdownIfIdle(CompileServiceImpl compileServiceImpl) {
        if (compileServiceImpl.getState().getSessions().isEmpty()) {
            compileServiceImpl.shutdownWithDelay();
            return;
        }
        compileServiceImpl.getDaemonOptions().setAutoshutdownIdleSeconds((int) TimeUnit.MILLISECONDS.toSeconds(compileServiceImpl.getDaemonOptions().getForceShutdownTimeoutMilliseconds()));
        compileServiceImpl.getDaemonOptions().setAutoshutdownUnusedSeconds(compileServiceImpl.getDaemonOptions().getAutoshutdownIdleSeconds());
        compileServiceImpl.getLog().info("Some sessions are active, waiting for them to finish");
        compileServiceImpl.getLog().info("Unused/idle timeouts are set to " + compileServiceImpl.getDaemonOptions().getAutoshutdownUnusedSeconds() + '/' + compileServiceImpl.getDaemonOptions().getAutoshutdownIdleSeconds() + Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL);
    }

    private final boolean gracefulShutdown(boolean onAnotherThread) {
        if (!getState().getAlive().compareAndSet(CompileServiceImplBase.Aliveness.Alive.ordinal(), CompileServiceImplBase.Aliveness.LastSession.ordinal())) {
            getLog().info("Invalid state for graceful shutdown: " + toAlivenessName(getState().getAlive().get()));
            return false;
        }
        getLog().info("Graceful shutdown signalled");
        if (!onAnotherThread) {
            gracefulShutdown$shutdownIfIdle(this);
            return true;
        }
        getTimer().schedule(new TimerTask() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImpl$gracefulShutdown$$inlined$schedule$1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                CompileServiceImpl compileServiceImpl = this.this$0;
                CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
                ReentrantReadWriteLock reentrantReadWriteLock = compileServiceImpl.rwlock;
                ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
                int i = 0;
                int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
                for (int i2 = 0; i2 < readHoldCount; i2++) {
                    lock.unlock();
                }
                ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
                writeLock.lock();
                try {
                    CompileServiceImpl compileServiceImpl2 = compileServiceImpl;
                    int i3 = ((CompileServiceImplBase) compileServiceImpl2).state.getAlive().get();
                    if (i3 < aliveness.ordinal()) {
                        compileServiceImpl2.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl2.toAlivenessName(i3));
                        new CompileService.CallResult.Dying();
                    } else {
                        try {
                            CompileServiceImpl.gracefulShutdown$shutdownIfIdle(this.this$0);
                            new CompileService.CallResult.Ok();
                        } catch (Throwable th) {
                            compileServiceImpl2.getLog().log(Level.SEVERE, "Exception", th);
                            new CompileService.CallResult.Error(th);
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    while (i < readHoldCount) {
                        lock.lock();
                        i++;
                    }
                    writeLock.unlock();
                } catch (Throwable th2) {
                    while (i < readHoldCount) {
                        lock.lock();
                        i++;
                    }
                    writeLock.unlock();
                    throw th2;
                }
            }
        }, 1L);
        return true;
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public void clearJarCache() {
        ZipHandler.clearFileAccessorCache();
        KotlinCoreApplicationEnvironment applicationEnvironment = KotlinCoreEnvironment.INSTANCE.getApplicationEnvironment();
        if (applicationEnvironment != null) {
            VirtualFileSystem jarFileSystem = applicationEnvironment.getJarFileSystem();
            CoreJarFileSystem coreJarFileSystem = jarFileSystem instanceof CoreJarFileSystem ? (CoreJarFileSystem) jarFileSystem : null;
            if (coreJarFileSystem != null) {
                coreJarFileSystem.clearHandlersCache();
            }
            VirtualFileSystem jrtFileSystem = applicationEnvironment.getJrtFileSystem();
            CoreJrtFileSystem coreJrtFileSystem = jrtFileSystem instanceof CoreJrtFileSystem ? (CoreJrtFileSystem) jrtFileSystem : null;
            if (coreJrtFileSystem != null) {
                coreJrtFileSystem.clearRoots();
            }
            applicationEnvironment.idleCleanup();
        }
    }

    static /* synthetic */ CompileService.CallResult ifAlive$default(CompileServiceImpl compileServiceImpl, CompileServiceImplBase.Aliveness aliveness, Function0 function0, int i, Object obj) {
        CompileService.CallResult.Error error;
        if ((i & 1) != 0) {
            aliveness = CompileServiceImplBase.Aliveness.LastSession;
        }
        ReentrantReadWriteLock.ReadLock lock = compileServiceImpl.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl2 = compileServiceImpl;
            int i2 = ((CompileServiceImplBase) compileServiceImpl2).state.getAlive().get();
            if (i2 < aliveness.ordinal()) {
                compileServiceImpl2.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl2.toAlivenessName(i2));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    error = (CompileService.CallResult) function0.invoke();
                } catch (Throwable th) {
                    compileServiceImpl2.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            InlineMarker.finallyStart(1);
            lock.unlock();
            InlineMarker.finallyEnd(1);
            return error;
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            lock.unlock();
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    private final <R> CompileService.CallResult<R> ifAlive(CompileServiceImplBase.Aliveness minAliveness, Function0<? extends CompileService.CallResult<? extends R>> body) {
        CompileService.CallResult<? extends R> error;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < minAliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + minAliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    error = body.invoke();
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            InlineMarker.finallyStart(1);
            lock.unlock();
            InlineMarker.finallyEnd(1);
            return error;
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            lock.unlock();
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    private final void ifAliveUnit(CompileServiceImplBase.Aliveness minAliveness, Function0<Unit> body) {
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < minAliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + minAliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                new CompileService.CallResult.Dying();
            } else {
                try {
                    body.invoke();
                    new CompileService.CallResult.Ok();
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    new CompileService.CallResult.Error(th);
                }
            }
            Unit unit = Unit.INSTANCE;
            InlineMarker.finallyStart(1);
            lock.unlock();
            InlineMarker.finallyEnd(1);
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            lock.unlock();
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    static /* synthetic */ void ifAliveUnit$default(CompileServiceImpl compileServiceImpl, CompileServiceImplBase.Aliveness aliveness, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            aliveness = CompileServiceImplBase.Aliveness.LastSession;
        }
        ReentrantReadWriteLock.ReadLock lock = compileServiceImpl.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl2 = compileServiceImpl;
            int i2 = ((CompileServiceImplBase) compileServiceImpl2).state.getAlive().get();
            if (i2 < aliveness.ordinal()) {
                compileServiceImpl2.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl2.toAlivenessName(i2));
                new CompileService.CallResult.Dying();
            } else {
                try {
                    function0.invoke();
                    new CompileService.CallResult.Ok();
                } catch (Throwable th) {
                    compileServiceImpl2.getLog().log(Level.SEVERE, "Exception", th);
                    new CompileService.CallResult.Error(th);
                }
            }
            Unit unit = Unit.INSTANCE;
            InlineMarker.finallyStart(1);
            lock.unlock();
            InlineMarker.finallyEnd(1);
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            lock.unlock();
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    static /* synthetic */ CompileService.CallResult ifAliveExclusive$default(CompileServiceImpl compileServiceImpl, CompileServiceImplBase.Aliveness aliveness, Function0 function0, int i, Object obj) {
        CompileService.CallResult.Error error;
        if ((i & 1) != 0) {
            aliveness = CompileServiceImplBase.Aliveness.LastSession;
        }
        ReentrantReadWriteLock reentrantReadWriteLock = compileServiceImpl.rwlock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i2 = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i3 = 0; i3 < readHoldCount; i3++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            CompileServiceImpl compileServiceImpl2 = compileServiceImpl;
            int i4 = ((CompileServiceImplBase) compileServiceImpl2).state.getAlive().get();
            if (i4 < aliveness.ordinal()) {
                compileServiceImpl2.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl2.toAlivenessName(i4));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    error = (CompileService.CallResult) function0.invoke();
                } catch (Throwable th) {
                    compileServiceImpl2.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            InlineMarker.finallyStart(1);
            while (i2 < readHoldCount) {
                lock.lock();
                i2++;
            }
            writeLock.unlock();
            InlineMarker.finallyEnd(1);
            return error;
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            while (i2 < readHoldCount) {
                lock.lock();
                i2++;
            }
            writeLock.unlock();
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    private final <R> CompileService.CallResult<R> ifAliveExclusive(CompileServiceImplBase.Aliveness minAliveness, Function0<? extends CompileService.CallResult<? extends R>> body) {
        CompileService.CallResult<? extends R> error;
        ReentrantReadWriteLock reentrantReadWriteLock = this.rwlock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i3 = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i3 < minAliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + minAliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i3));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    error = body.invoke();
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            InlineMarker.finallyStart(1);
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
            InlineMarker.finallyEnd(1);
            return error;
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    private final void ifAliveExclusiveUnit(CompileServiceImplBase.Aliveness minAliveness, Function0<Unit> body) {
        ReentrantReadWriteLock reentrantReadWriteLock = this.rwlock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i3 = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i3 < minAliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + minAliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i3));
                new CompileService.CallResult.Dying();
            } else {
                try {
                    body.invoke();
                    new CompileService.CallResult.Ok();
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    new CompileService.CallResult.Error(th);
                }
            }
            Unit unit = Unit.INSTANCE;
            InlineMarker.finallyStart(1);
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
            InlineMarker.finallyEnd(1);
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    static /* synthetic */ void ifAliveExclusiveUnit$default(CompileServiceImpl compileServiceImpl, CompileServiceImplBase.Aliveness aliveness, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            aliveness = CompileServiceImplBase.Aliveness.LastSession;
        }
        ReentrantReadWriteLock reentrantReadWriteLock = compileServiceImpl.rwlock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i2 = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i3 = 0; i3 < readHoldCount; i3++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            CompileServiceImpl compileServiceImpl2 = compileServiceImpl;
            int i4 = ((CompileServiceImplBase) compileServiceImpl2).state.getAlive().get();
            if (i4 < aliveness.ordinal()) {
                compileServiceImpl2.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl2.toAlivenessName(i4));
                new CompileService.CallResult.Dying();
            } else {
                try {
                    function0.invoke();
                    new CompileService.CallResult.Ok();
                } catch (Throwable th) {
                    compileServiceImpl2.getLog().log(Level.SEVERE, "Exception", th);
                    new CompileService.CallResult.Error(th);
                }
            }
            Unit unit = Unit.INSTANCE;
            InlineMarker.finallyStart(1);
            while (i2 < readHoldCount) {
                lock.lock();
                i2++;
            }
            writeLock.unlock();
            InlineMarker.finallyEnd(1);
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            while (i2 < readHoldCount) {
                lock.lock();
                i2++;
            }
            writeLock.unlock();
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<String> getKotlinVersion() {
        CompileService.CallResult.Error error;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    try {
                        error = new CompileService.CallResult.Good(KotlinCompilerVersion.VERSION);
                    } catch (Exception unused) {
                        error = new CompileService.CallResult.Error("Unknown Kotlin version");
                    }
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            lock.unlock();
            return error;
        } catch (Throwable th2) {
            lock.unlock();
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<DaemonOptions> getDaemonOptions() {
        CompileService.CallResult.Good error;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    error = new CompileService.CallResult.Good(getDaemonOptions());
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            lock.unlock();
            return error;
        } catch (Throwable th2) {
            lock.unlock();
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<DaemonJVMOptions> getDaemonJVMOptions() {
        CompileService.CallResult.Good error;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    getLog().info("getDaemonJVMOptions: " + this.daemonJVMOptions);
                    error = new CompileService.CallResult.Good(this.daemonJVMOptions);
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            return error;
        } finally {
            lock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<List<String>> getClients() {
        CompileService.CallResult.Good error;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    error = new CompileService.CallResult.Good(getState().getClientsFlagPaths());
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
            }
            return error;
        } finally {
            lock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<Long> getUsedMemory(boolean withGC) {
        CompileService.CallResult.Error error;
        CompileService.CallResult.Dying dying;
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                dying = new CompileService.CallResult.Dying();
            } else {
                try {
                    if (withGC) {
                        System.gc();
                    }
                    Runtime runtime = Runtime.getRuntime();
                    error = new CompileService.CallResult.Good(Long.valueOf(runtime.totalMemory() - runtime.freeMemory()));
                } catch (Throwable th) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th);
                    error = new CompileService.CallResult.Error(th);
                }
                dying = error;
            }
            lock.unlock();
            return dying;
        } catch (Throwable th2) {
            lock.unlock();
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<Set<String>> classesFqNamesByFiles(int sessionId, Set<? extends File> sourceFiles) {
        CompileService.CallResult.Error error;
        CompileService.CallResult.Dying dying;
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                dying = new CompileService.CallResult.Dying();
            } else {
                try {
                    CompileServiceImpl compileServiceImpl2 = this;
                    if (sessionId != CompileService.INSTANCE.getNO_SESSION() && ((CompileServiceImplBase) compileServiceImpl2).state.getSessions().get(sessionId) == null) {
                        error = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
                    } else {
                        try {
                            ((CompileServiceImplBase) compileServiceImpl2).compilationsCounter.incrementAndGet();
                            error = new CompileService.CallResult.Good(ParseFileUtilsKt.classesFqNames(sourceFiles));
                            ((CompileServiceImplBase) compileServiceImpl2)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                        } catch (Throwable th) {
                            ((CompileServiceImplBase) compileServiceImpl2)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th2);
                    error = new CompileService.CallResult.Error(th2);
                }
                dying = error;
            }
            lock.unlock();
            return dying;
        } catch (Throwable th3) {
            lock.unlock();
            throw th3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r54v1 */
    /* JADX WARN: Type inference failed for: r54v6 */
    @Override // org.jetbrains.kotlin.daemon.common.CompileService
    public CompileService.CallResult<Integer> compile(int sessionId, String[] compilerArguments, CompilationOptions compilationOptions, CompilerServicesFacadeBase servicesFacade, CompilationResults compilationResults) throws Throwable {
        CompileServiceImpl compileServiceImpl;
        CompileService.CallResult.Error error;
        K2JVMCompiler k2JVMCompiler;
        CompileService.CallResult.Error error2;
        Throwable th;
        WallAndThreadAndMemoryTotalProfiler wallAndThreadAndMemoryTotalProfiler;
        Throwable th2;
        WallAndThreadAndMemoryTotalProfiler wallAndThreadAndMemoryTotalProfiler2;
        boolean z;
        Intrinsics.checkNotNullParameter(compilerArguments, "compilerArguments");
        Intrinsics.checkNotNullParameter(compilationOptions, "compilationOptions");
        Intrinsics.checkNotNullParameter(servicesFacade, "servicesFacade");
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl2 = this;
            int i = ((CompileServiceImplBase) compileServiceImpl2).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl2.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl2.toAlivenessName(i));
                error = new CompileService.CallResult.Dying();
            } else {
                try {
                    CompileServiceImpl compileServiceImpl3 = this;
                    CompileServicesFacadeMessageCollector compileServicesFacadeMessageCollector = new CompileServicesFacadeMessageCollector(servicesFacade, compilationOptions);
                    DaemonMessageReporter DaemonMessageReporter = DaemonMessageReporterKt.DaemonMessageReporter(servicesFacade, compilationOptions);
                    CompileService.TargetPlatform targetPlatform = compilationOptions.getTargetPlatform();
                    compileServiceImpl = compileServiceImpl2;
                    try {
                        compileServiceImpl3.getLog().info("Starting compilation with args: " + ArraysKt.joinToString$default(compilerArguments, " ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                        int i2 = CompileServiceImplBase.WhenMappings.$EnumSwitchMapping$0[targetPlatform.ordinal()];
                        if (i2 == 1) {
                            k2JVMCompiler = new K2JVMCompiler();
                        } else if (i2 == 2) {
                            k2JVMCompiler = new K2JSCompiler();
                        } else {
                            if (i2 != 3) {
                                throw new NoWhenBranchMatchedException();
                            }
                            k2JVMCompiler = new K2MetadataCompiler();
                        }
                        CommonCompilerArguments commonCompilerArguments = (CommonCompilerArguments) k2JVMCompiler.createArguments();
                        CLICompiler cLICompiler = k2JVMCompiler;
                        try {
                            ParseCommandLineArgumentsKt.parseCommandLineArguments$default(ArraysKt.asList(compilerArguments), commonCompilerArguments, false, 4, null);
                            String strValidateArguments = ParseCommandLineArgumentsKt.validateArguments(commonCompilerArguments.getErrors());
                            if (strValidateArguments != null) {
                                MessageCollector.report$default(compileServicesFacadeMessageCollector, CompilerMessageSeverity.ERROR, strValidateArguments, null, 4, null);
                                error2 = new CompileService.CallResult.Good(Integer.valueOf(ExitCode.COMPILATION_ERROR.getCode()));
                            } else {
                                int i3 = CompileServiceImplBase.WhenMappings.$EnumSwitchMapping$1[compilationOptions.getCompilerMode().ordinal()];
                                long jFreeMemory = 0;
                                String string = "";
                                if (i3 == 1) {
                                    try {
                                        CompilerServicesFacadeBase compilerServicesFacadeBase = servicesFacade;
                                        boolean zHasIncrementalCaches = ((JpsCompilerServicesFacade) servicesFacade).hasIncrementalCaches();
                                        boolean zIsEnabledForJvm = IncrementalCompilation.isEnabledForJvm();
                                        IncrementalCompilation.setIsEnabledForJvm(zHasIncrementalCaches);
                                        try {
                                            if (commonCompilerArguments.getIncrementalCompilation() == null) {
                                                commonCompilerArguments.setIncrementalCompilation(Boolean.valueOf(zHasIncrementalCaches));
                                            }
                                            compileServiceImpl3.getLog().fine("alive!");
                                            if (sessionId != CompileService.INSTANCE.getNO_SESSION() && ((CompileServiceImplBase) compileServiceImpl3).state.getSessions().get(sessionId) == null) {
                                                error2 = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
                                            } else {
                                                try {
                                                    try {
                                                        ((CompileServiceImplBase) compileServiceImpl3).compilationsCounter.incrementAndGet();
                                                        Profiler wallAndThreadTotalProfiler = compileServiceImpl3.getDaemonOptions().getReportPerf() ? new WallAndThreadTotalProfiler() : new DummyProfiler();
                                                        try {
                                                            EventManagerImpl eventManagerImpl = new EventManagerImpl();
                                                            try {
                                                                try {
                                                                    compileServiceImpl3.getLog().fine("trying get exitCode");
                                                                    try {
                                                                        if (compileServiceImpl3.getDaemonOptions().getReportPerf()) {
                                                                            try {
                                                                                wallAndThreadAndMemoryTotalProfiler = new WallAndThreadAndMemoryTotalProfiler(false);
                                                                            } catch (Throwable th3) {
                                                                                th = th3;
                                                                                Logger log = compileServiceImpl3.getLog();
                                                                                Level level = Level.SEVERE;
                                                                                StringBuilder sbAppend = new StringBuilder().append("Exception: ").append(th).append("\n  ");
                                                                                StackTraceElement[] stackTrace = th.getStackTrace();
                                                                                Intrinsics.checkNotNullExpressionValue(stackTrace, "getStackTrace(...)");
                                                                                StringBuilder sbAppend2 = sbAppend.append(ArraysKt.joinToString$default(stackTrace, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                                                                if (th.getCause() != null && !Intrinsics.areEqual(th.getCause(), th)) {
                                                                                    StringBuilder sbAppend3 = new StringBuilder().append("\nCaused by: ").append(th.getCause()).append("\n  ");
                                                                                    Throwable cause = th.getCause();
                                                                                    Intrinsics.checkNotNull(cause);
                                                                                    StackTraceElement[] stackTrace2 = cause.getStackTrace();
                                                                                    Intrinsics.checkNotNullExpressionValue(stackTrace2, "getStackTrace(...)");
                                                                                    string = sbAppend3.append(ArraysKt.joinToString$default(stackTrace2, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
                                                                                }
                                                                                log.log(level, sbAppend2.append(string).toString());
                                                                                throw th;
                                                                            }
                                                                        } else {
                                                                            wallAndThreadAndMemoryTotalProfiler = new DummyProfiler();
                                                                        }
                                                                        List<Long> listBeginMeasure = wallAndThreadAndMemoryTotalProfiler.beginMeasure(null);
                                                                        try {
                                                                            Integer numValueOf = Integer.valueOf(cLICompiler.exec(compileServicesFacadeMessageCollector, createCompileServices((CompilerCallbackServicesFacade) servicesFacade, eventManagerImpl, wallAndThreadTotalProfiler), commonCompilerArguments).getCode());
                                                                            wallAndThreadAndMemoryTotalProfiler.endMeasure(null, listBeginMeasure);
                                                                            if (compileServiceImpl3.getDaemonOptions().getReportPerf()) {
                                                                                Runtime runtime = Runtime.getRuntime();
                                                                                jFreeMemory = runtime.totalMemory() - runtime.freeMemory();
                                                                            }
                                                                            long j = jFreeMemory;
                                                                            compileServiceImpl3.getLog().info("Done with result " + numValueOf);
                                                                            if (compileServiceImpl3.getDaemonOptions().getReportPerf()) {
                                                                                PerfCounters totalCounters = wallAndThreadAndMemoryTotalProfiler.getTotalCounters();
                                                                                PerfCounters totalCounters2 = wallAndThreadTotalProfiler.getTotalCounters();
                                                                                StringBuilder sbAppend4 = new StringBuilder("PERF: Compile on daemon: ").append(compileServiceImpl3.ms(totalCounters.getTime())).append(" ms; thread: user ").append(compileServiceImpl3.ms(totalCounters.getThreadUserTime())).append(" ms, sys ").append(compileServiceImpl3.ms(totalCounters.getThreadTime() - totalCounters.getThreadUserTime())).append(" ms; rpc: ").append(totalCounters2.getCount()).append(" calls, ").append(compileServiceImpl3.ms(totalCounters2.getTime())).append(" ms, thread ").append(compileServiceImpl3.ms(totalCounters2.getThreadTime())).append(" ms; memory: ").append(compileServiceImpl3.kb(j)).append(" kb (");
                                                                                String str = String.format("%+d", Arrays.copyOf(new Object[]{Long.valueOf(compileServiceImpl3.kb(totalCounters.getMemory()))}, 1));
                                                                                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                                                                                String string2 = sbAppend4.append(str).append(" kb)").toString();
                                                                                DaemonMessageReporter.report(ReportSeverity.INFO, string2);
                                                                                compileServiceImpl3.getLog().info(string2);
                                                                                Unit unit = Unit.INSTANCE;
                                                                                for (Map.Entry<Object, PerfCounters> entry : wallAndThreadTotalProfiler.getCounters().entrySet()) {
                                                                                    Object key = entry.getKey();
                                                                                    PerfCounters value = entry.getValue();
                                                                                    String str2 = "PERF: rpc by " + key + ": " + value.getCount() + " calls, " + compileServiceImpl3.ms(value.getTime()) + " ms, thread " + compileServiceImpl3.ms(value.getThreadTime()) + " ms";
                                                                                    DaemonMessageReporter.report(ReportSeverity.INFO, str2);
                                                                                    compileServiceImpl3.getLog().info(str2);
                                                                                    Unit unit2 = Unit.INSTANCE;
                                                                                }
                                                                            }
                                                                            CompileService.CallResult.Good good = new CompileService.CallResult.Good(Integer.valueOf(numValueOf.intValue()));
                                                                            eventManagerImpl.fireCompilationFinished();
                                                                            CompileService.CallResult.Good good2 = good;
                                                                            ((CompileServiceImplBase) compileServiceImpl3)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                                                            error2 = good2;
                                                                        } catch (Throwable th4) {
                                                                            th = th4;
                                                                            th = th;
                                                                            Logger log2 = compileServiceImpl3.getLog();
                                                                            Level level2 = Level.SEVERE;
                                                                            StringBuilder sbAppend5 = new StringBuilder().append("Exception: ").append(th).append("\n  ");
                                                                            StackTraceElement[] stackTrace3 = th.getStackTrace();
                                                                            Intrinsics.checkNotNullExpressionValue(stackTrace3, "getStackTrace(...)");
                                                                            StringBuilder sbAppend6 = sbAppend5.append(ArraysKt.joinToString$default(stackTrace3, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                                                            if (th.getCause() != null) {
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
                                                                    } catch (Throwable th5) {
                                                                        th = th5;
                                                                    }
                                                                } catch (Throwable th6) {
                                                                    th = th6;
                                                                    Throwable th7 = th;
                                                                    sessionId.fireCompilationFinished();
                                                                    throw th7;
                                                                }
                                                            } catch (Throwable th8) {
                                                                th = th8;
                                                                sessionId = eventManagerImpl;
                                                                Throwable th9 = th;
                                                                sessionId.fireCompilationFinished();
                                                                throw th9;
                                                            }
                                                        } catch (Throwable th10) {
                                                            th = th10;
                                                            Throwable th11 = th;
                                                            ((CompileServiceImplBase) compileServiceImpl3)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                                            throw th11;
                                                        }
                                                    } catch (Throwable th12) {
                                                        th = th12;
                                                    }
                                                } catch (Throwable th13) {
                                                    th = th13;
                                                    Throwable th14 = th;
                                                    IncrementalCompilation.setIsEnabledForJvm(zIsEnabledForJvm);
                                                    throw th14;
                                                }
                                            }
                                            IncrementalCompilation.setIsEnabledForJvm(zIsEnabledForJvm);
                                        } catch (Throwable th15) {
                                            th = th15;
                                        }
                                    } catch (Throwable th16) {
                                        th = th16;
                                        Throwable th17 = th;
                                        try {
                                            compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th17);
                                            error = new CompileService.CallResult.Error(th17);
                                        } catch (Throwable th18) {
                                            th = th18;
                                            Throwable th19 = th;
                                            lock.unlock();
                                            throw th19;
                                        }
                                    }
                                } else if (i3 == 2) {
                                    compileServiceImpl3.getLog().fine("alive!");
                                    if (sessionId != CompileService.INSTANCE.getNO_SESSION() && ((CompileServiceImplBase) compileServiceImpl3).state.getSessions().get(sessionId) == null) {
                                        error2 = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
                                    } else {
                                        try {
                                            ((CompileServiceImplBase) compileServiceImpl3).compilationsCounter.incrementAndGet();
                                            Profiler wallAndThreadTotalProfiler2 = compileServiceImpl3.getDaemonOptions().getReportPerf() ? new WallAndThreadTotalProfiler() : new DummyProfiler();
                                            EventManagerImpl eventManagerImpl2 = new EventManagerImpl();
                                            try {
                                                try {
                                                    compileServiceImpl3.getLog().fine("trying get exitCode");
                                                    try {
                                                        if (compileServiceImpl3.getDaemonOptions().getReportPerf()) {
                                                            try {
                                                                wallAndThreadAndMemoryTotalProfiler2 = new WallAndThreadAndMemoryTotalProfiler(false);
                                                            } catch (Throwable th20) {
                                                                th2 = th20;
                                                                Logger log3 = compileServiceImpl3.getLog();
                                                                Level level3 = Level.SEVERE;
                                                                StringBuilder sbAppend8 = new StringBuilder().append("Exception: ").append(th2).append("\n  ");
                                                                StackTraceElement[] stackTrace5 = th2.getStackTrace();
                                                                Intrinsics.checkNotNullExpressionValue(stackTrace5, "getStackTrace(...)");
                                                                StringBuilder sbAppend9 = sbAppend8.append(ArraysKt.joinToString$default(stackTrace5, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                                                if (th2.getCause() != null && !Intrinsics.areEqual(th2.getCause(), th2)) {
                                                                    StringBuilder sbAppend10 = new StringBuilder().append("\nCaused by: ").append(th2.getCause()).append("\n  ");
                                                                    Throwable cause3 = th2.getCause();
                                                                    Intrinsics.checkNotNull(cause3);
                                                                    StackTraceElement[] stackTrace6 = cause3.getStackTrace();
                                                                    Intrinsics.checkNotNullExpressionValue(stackTrace6, "getStackTrace(...)");
                                                                    string = sbAppend10.append(ArraysKt.joinToString$default(stackTrace6, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
                                                                }
                                                                log3.log(level3, sbAppend9.append(string).toString());
                                                                throw th2;
                                                            }
                                                        } else {
                                                            wallAndThreadAndMemoryTotalProfiler2 = new DummyProfiler();
                                                        }
                                                        List<Long> listBeginMeasure2 = wallAndThreadAndMemoryTotalProfiler2.beginMeasure(null);
                                                        try {
                                                            ExitCode exitCodeExec = cLICompiler.exec(compileServicesFacadeMessageCollector, Services.EMPTY, commonCompilerArguments);
                                                            String strRenderCompilerPerformance = cLICompiler.getDefaultPerformanceManager().renderCompilerPerformance();
                                                            if (compilationResults != null) {
                                                                Intrinsics.checkNotNull(compilationResults, "null cannot be cast to non-null type org.jetbrains.kotlin.daemon.common.CompilationResults");
                                                                compilationResults.add(CompilationResultCategory.BUILD_REPORT_LINES.getCode(), CollectionsKt.arrayListOf(strRenderCompilerPerformance));
                                                            }
                                                            Integer numValueOf2 = Integer.valueOf(exitCodeExec.getCode());
                                                            wallAndThreadAndMemoryTotalProfiler2.endMeasure(null, listBeginMeasure2);
                                                            if (compileServiceImpl3.getDaemonOptions().getReportPerf()) {
                                                                Runtime runtime2 = Runtime.getRuntime();
                                                                jFreeMemory = runtime2.totalMemory() - runtime2.freeMemory();
                                                            }
                                                            long j2 = jFreeMemory;
                                                            compileServiceImpl3.getLog().info("Done with result " + numValueOf2);
                                                            if (compileServiceImpl3.getDaemonOptions().getReportPerf()) {
                                                                PerfCounters totalCounters3 = wallAndThreadAndMemoryTotalProfiler2.getTotalCounters();
                                                                PerfCounters totalCounters4 = wallAndThreadTotalProfiler2.getTotalCounters();
                                                                StringBuilder sbAppend11 = new StringBuilder("PERF: Compile on daemon: ").append(compileServiceImpl3.ms(totalCounters3.getTime())).append(" ms; thread: user ").append(compileServiceImpl3.ms(totalCounters3.getThreadUserTime())).append(" ms, sys ").append(compileServiceImpl3.ms(totalCounters3.getThreadTime() - totalCounters3.getThreadUserTime())).append(" ms; rpc: ").append(totalCounters4.getCount()).append(" calls, ").append(compileServiceImpl3.ms(totalCounters4.getTime())).append(" ms, thread ").append(compileServiceImpl3.ms(totalCounters4.getThreadTime())).append(" ms; memory: ").append(compileServiceImpl3.kb(j2)).append(" kb (");
                                                                String str3 = String.format("%+d", Arrays.copyOf(new Object[]{Long.valueOf(compileServiceImpl3.kb(totalCounters3.getMemory()))}, 1));
                                                                Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
                                                                String string3 = sbAppend11.append(str3).append(" kb)").toString();
                                                                DaemonMessageReporter.report(ReportSeverity.INFO, string3);
                                                                compileServiceImpl3.getLog().info(string3);
                                                                Unit unit3 = Unit.INSTANCE;
                                                                for (Map.Entry<Object, PerfCounters> entry2 : wallAndThreadTotalProfiler2.getCounters().entrySet()) {
                                                                    Object key2 = entry2.getKey();
                                                                    PerfCounters value2 = entry2.getValue();
                                                                    String str4 = "PERF: rpc by " + key2 + ": " + value2.getCount() + " calls, " + compileServiceImpl3.ms(value2.getTime()) + " ms, thread " + compileServiceImpl3.ms(value2.getThreadTime()) + " ms";
                                                                    DaemonMessageReporter.report(ReportSeverity.INFO, str4);
                                                                    compileServiceImpl3.getLog().info(str4);
                                                                    Unit unit4 = Unit.INSTANCE;
                                                                }
                                                            }
                                                            CompileService.CallResult.Good good3 = new CompileService.CallResult.Good(Integer.valueOf(numValueOf2.intValue()));
                                                            eventManagerImpl2.fireCompilationFinished();
                                                            CompileService.CallResult.Good good4 = good3;
                                                            ((CompileServiceImplBase) compileServiceImpl3)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                                            error2 = good4;
                                                        } catch (Throwable th21) {
                                                            th = th21;
                                                            th2 = th;
                                                            Logger log4 = compileServiceImpl3.getLog();
                                                            Level level4 = Level.SEVERE;
                                                            StringBuilder sbAppend12 = new StringBuilder().append("Exception: ").append(th2).append("\n  ");
                                                            StackTraceElement[] stackTrace7 = th2.getStackTrace();
                                                            Intrinsics.checkNotNullExpressionValue(stackTrace7, "getStackTrace(...)");
                                                            StringBuilder sbAppend13 = sbAppend12.append(ArraysKt.joinToString$default(stackTrace7, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                                            if (th2.getCause() != null) {
                                                                StringBuilder sbAppend14 = new StringBuilder().append("\nCaused by: ").append(th2.getCause()).append("\n  ");
                                                                Throwable cause4 = th2.getCause();
                                                                Intrinsics.checkNotNull(cause4);
                                                                StackTraceElement[] stackTrace8 = cause4.getStackTrace();
                                                                Intrinsics.checkNotNullExpressionValue(stackTrace8, "getStackTrace(...)");
                                                                string = sbAppend14.append(ArraysKt.joinToString$default(stackTrace8, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
                                                            }
                                                            log4.log(level4, sbAppend13.append(string).toString());
                                                            throw th2;
                                                        }
                                                    } catch (Throwable th22) {
                                                        th = th22;
                                                    }
                                                } catch (Throwable th23) {
                                                    th = th23;
                                                    Throwable th24 = th;
                                                    sessionId.fireCompilationFinished();
                                                    throw th24;
                                                }
                                            } catch (Throwable th25) {
                                                th = th25;
                                                sessionId = eventManagerImpl2;
                                                Throwable th26 = th;
                                                sessionId.fireCompilationFinished();
                                                throw th26;
                                            }
                                        } catch (Throwable th27) {
                                            ((CompileServiceImplBase) compileServiceImpl3)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                            throw th27;
                                        }
                                    }
                                } else {
                                    if (i3 != 3) {
                                        throw new IllegalStateException("Unknown compilation mode " + compilationOptions.getCompilerMode());
                                    }
                                    IncrementalCompilationOptions incrementalCompilationOptions = (IncrementalCompilationOptions) compilationOptions;
                                    int i4 = CompileServiceImplBase.WhenMappings.$EnumSwitchMapping$0[targetPlatform.ordinal()];
                                    if (i4 == 1) {
                                        boolean zIsEnabledForJvm2 = IncrementalCompilation.isEnabledForJvm();
                                        IncrementalCompilation.setIsEnabledForJvm(true);
                                        try {
                                            if (commonCompilerArguments.getIncrementalCompilation() == null) {
                                                commonCompilerArguments.setIncrementalCompilation(true);
                                            }
                                            compileServiceImpl3.getLog().fine("alive!");
                                            if (sessionId != CompileService.INSTANCE.getNO_SESSION() && ((CompileServiceImplBase) compileServiceImpl3).state.getSessions().get(sessionId) == null) {
                                                error2 = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
                                            } else {
                                                try {
                                                    ((CompileServiceImplBase) compileServiceImpl3).compilationsCounter.incrementAndGet();
                                                    Profiler wallAndThreadTotalProfiler3 = compileServiceImpl3.getDaemonOptions().getReportPerf() ? new WallAndThreadTotalProfiler() : new DummyProfiler();
                                                    EventManagerImpl eventManagerImpl3 = new EventManagerImpl();
                                                    try {
                                                        compileServiceImpl3.getLog().fine("trying get exitCode");
                                                        try {
                                                            Profiler wallAndThreadAndMemoryTotalProfiler3 = compileServiceImpl3.getDaemonOptions().getReportPerf() ? new WallAndThreadAndMemoryTotalProfiler(false) : new DummyProfiler();
                                                            List<Long> listBeginMeasure3 = wallAndThreadAndMemoryTotalProfiler3.beginMeasure(null);
                                                            Intrinsics.checkNotNull(commonCompilerArguments, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments");
                                                            Intrinsics.checkNotNull(compilationResults);
                                                            Intrinsics.checkNotNull(compilationResults);
                                                            Integer numValueOf3 = Integer.valueOf(compileServiceImpl3.execIncrementalCompiler((K2JVMCompilerArguments) commonCompilerArguments, incrementalCompilationOptions, compileServicesFacadeMessageCollector, GetICReporterKt.getBuildReporter(servicesFacade, compilationResults, incrementalCompilationOptions)).getCode());
                                                            wallAndThreadAndMemoryTotalProfiler3.endMeasure(null, listBeginMeasure3);
                                                            if (compileServiceImpl3.getDaemonOptions().getReportPerf()) {
                                                                Runtime runtime3 = Runtime.getRuntime();
                                                                jFreeMemory = runtime3.totalMemory() - runtime3.freeMemory();
                                                            }
                                                            long j3 = jFreeMemory;
                                                            compileServiceImpl3.getLog().info("Done with result " + numValueOf3);
                                                            if (compileServiceImpl3.getDaemonOptions().getReportPerf()) {
                                                                PerfCounters totalCounters5 = wallAndThreadAndMemoryTotalProfiler3.getTotalCounters();
                                                                PerfCounters totalCounters6 = wallAndThreadTotalProfiler3.getTotalCounters();
                                                                StringBuilder sbAppend15 = new StringBuilder("PERF: Compile on daemon: ").append(compileServiceImpl3.ms(totalCounters5.getTime())).append(" ms; thread: user ").append(compileServiceImpl3.ms(totalCounters5.getThreadUserTime())).append(" ms, sys ").append(compileServiceImpl3.ms(totalCounters5.getThreadTime() - totalCounters5.getThreadUserTime())).append(" ms; rpc: ").append(totalCounters6.getCount()).append(" calls, ").append(compileServiceImpl3.ms(totalCounters6.getTime())).append(" ms, thread ").append(compileServiceImpl3.ms(totalCounters6.getThreadTime())).append(" ms; memory: ").append(compileServiceImpl3.kb(j3)).append(" kb (");
                                                                String str5 = String.format("%+d", Arrays.copyOf(new Object[]{Long.valueOf(compileServiceImpl3.kb(totalCounters5.getMemory()))}, 1));
                                                                Intrinsics.checkNotNullExpressionValue(str5, "format(...)");
                                                                String string4 = sbAppend15.append(str5).append(" kb)").toString();
                                                                DaemonMessageReporter.report(ReportSeverity.INFO, string4);
                                                                compileServiceImpl3.getLog().info(string4);
                                                                Unit unit5 = Unit.INSTANCE;
                                                                for (Map.Entry<Object, PerfCounters> entry3 : wallAndThreadTotalProfiler3.getCounters().entrySet()) {
                                                                    Object key3 = entry3.getKey();
                                                                    PerfCounters value3 = entry3.getValue();
                                                                    String str6 = "PERF: rpc by " + key3 + ": " + value3.getCount() + " calls, " + compileServiceImpl3.ms(value3.getTime()) + " ms, thread " + compileServiceImpl3.ms(value3.getThreadTime()) + " ms";
                                                                    DaemonMessageReporter.report(ReportSeverity.INFO, str6);
                                                                    compileServiceImpl3.getLog().info(str6);
                                                                    Unit unit6 = Unit.INSTANCE;
                                                                }
                                                            }
                                                            CompileService.CallResult.Good good5 = new CompileService.CallResult.Good(Integer.valueOf(numValueOf3.intValue()));
                                                            eventManagerImpl3.fireCompilationFinished();
                                                            CompileService.CallResult.Good good6 = good5;
                                                            ((CompileServiceImplBase) compileServiceImpl3)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                                            error2 = good6;
                                                        } catch (Throwable th28) {
                                                            Logger log5 = compileServiceImpl3.getLog();
                                                            Level level5 = Level.SEVERE;
                                                            StringBuilder sbAppend16 = new StringBuilder().append("Exception: ").append(th28).append("\n  ");
                                                            StackTraceElement[] stackTrace9 = th28.getStackTrace();
                                                            Intrinsics.checkNotNullExpressionValue(stackTrace9, "getStackTrace(...)");
                                                            StringBuilder sbAppend17 = sbAppend16.append(ArraysKt.joinToString$default(stackTrace9, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                                            if (th28.getCause() != null && !Intrinsics.areEqual(th28.getCause(), th28)) {
                                                                StringBuilder sbAppend18 = new StringBuilder().append("\nCaused by: ").append(th28.getCause()).append("\n  ");
                                                                Throwable cause5 = th28.getCause();
                                                                Intrinsics.checkNotNull(cause5);
                                                                StackTraceElement[] stackTrace10 = cause5.getStackTrace();
                                                                Intrinsics.checkNotNullExpressionValue(stackTrace10, "getStackTrace(...)");
                                                                string = sbAppend18.append(ArraysKt.joinToString$default(stackTrace10, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
                                                            }
                                                            log5.log(level5, sbAppend17.append(string).toString());
                                                            throw th28;
                                                        }
                                                    } catch (Throwable th29) {
                                                        eventManagerImpl3.fireCompilationFinished();
                                                        throw th29;
                                                    }
                                                } catch (Throwable th30) {
                                                    ((CompileServiceImplBase) compileServiceImpl3)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                                    throw th30;
                                                }
                                            }
                                            IncrementalCompilation.setIsEnabledForJvm(zIsEnabledForJvm2);
                                        } catch (Throwable th31) {
                                            IncrementalCompilation.setIsEnabledForJvm(zIsEnabledForJvm2);
                                            throw th31;
                                        }
                                    } else {
                                        if (i4 != 2) {
                                            throw new IllegalStateException("Incremental compilation is not supported for target platform: " + targetPlatform);
                                        }
                                        boolean zIsEnabledForJs = IncrementalCompilation.isEnabledForJs();
                                        IncrementalCompilation.setIsEnabledForJs(true);
                                        try {
                                            if (commonCompilerArguments.getIncrementalCompilation() == null) {
                                                z = zIsEnabledForJs;
                                                try {
                                                    commonCompilerArguments.setIncrementalCompilation(true);
                                                } catch (Throwable th32) {
                                                    th = th32;
                                                    Throwable th33 = th;
                                                    IncrementalCompilation.setIsEnabledForJs(z);
                                                    throw th33;
                                                }
                                            } else {
                                                z = zIsEnabledForJs;
                                            }
                                            compileServiceImpl3.getLog().fine("alive!");
                                            if (sessionId != CompileService.INSTANCE.getNO_SESSION() && ((CompileServiceImplBase) compileServiceImpl3).state.getSessions().get(sessionId) == null) {
                                                error2 = new CompileService.CallResult.Error("Unknown or invalid session " + sessionId);
                                            } else {
                                                try {
                                                    ((CompileServiceImplBase) compileServiceImpl3).compilationsCounter.incrementAndGet();
                                                    Profiler wallAndThreadTotalProfiler4 = compileServiceImpl3.getDaemonOptions().getReportPerf() ? new WallAndThreadTotalProfiler() : new DummyProfiler();
                                                    EventManagerImpl eventManagerImpl4 = new EventManagerImpl();
                                                    try {
                                                        compileServiceImpl3.getLog().fine("trying get exitCode");
                                                        try {
                                                            Profiler wallAndThreadAndMemoryTotalProfiler4 = compileServiceImpl3.getDaemonOptions().getReportPerf() ? new WallAndThreadAndMemoryTotalProfiler(false) : new DummyProfiler();
                                                            List<Long> listBeginMeasure4 = wallAndThreadAndMemoryTotalProfiler4.beginMeasure(null);
                                                            Intrinsics.checkNotNull(commonCompilerArguments, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArguments");
                                                            Intrinsics.checkNotNull(compilationResults);
                                                            Intrinsics.checkNotNull(compilationResults);
                                                            Integer numValueOf4 = Integer.valueOf(compileServiceImpl3.execJsIncrementalCompiler((K2JSCompilerArguments) commonCompilerArguments, incrementalCompilationOptions, compileServicesFacadeMessageCollector, GetICReporterKt.getBuildReporter(servicesFacade, compilationResults, incrementalCompilationOptions)).getCode());
                                                            wallAndThreadAndMemoryTotalProfiler4.endMeasure(null, listBeginMeasure4);
                                                            if (compileServiceImpl3.getDaemonOptions().getReportPerf()) {
                                                                Runtime runtime4 = Runtime.getRuntime();
                                                                jFreeMemory = runtime4.totalMemory() - runtime4.freeMemory();
                                                            }
                                                            long j4 = jFreeMemory;
                                                            compileServiceImpl3.getLog().info("Done with result " + numValueOf4);
                                                            if (compileServiceImpl3.getDaemonOptions().getReportPerf()) {
                                                                PerfCounters totalCounters7 = wallAndThreadAndMemoryTotalProfiler4.getTotalCounters();
                                                                PerfCounters totalCounters8 = wallAndThreadTotalProfiler4.getTotalCounters();
                                                                StringBuilder sbAppend19 = new StringBuilder("PERF: Compile on daemon: ").append(compileServiceImpl3.ms(totalCounters7.getTime())).append(" ms; thread: user ").append(compileServiceImpl3.ms(totalCounters7.getThreadUserTime())).append(" ms, sys ").append(compileServiceImpl3.ms(totalCounters7.getThreadTime() - totalCounters7.getThreadUserTime())).append(" ms; rpc: ").append(totalCounters8.getCount()).append(" calls, ").append(compileServiceImpl3.ms(totalCounters8.getTime())).append(" ms, thread ").append(compileServiceImpl3.ms(totalCounters8.getThreadTime())).append(" ms; memory: ").append(compileServiceImpl3.kb(j4)).append(" kb (");
                                                                String str7 = String.format("%+d", Arrays.copyOf(new Object[]{Long.valueOf(compileServiceImpl3.kb(totalCounters7.getMemory()))}, 1));
                                                                Intrinsics.checkNotNullExpressionValue(str7, "format(...)");
                                                                String string5 = sbAppend19.append(str7).append(" kb)").toString();
                                                                DaemonMessageReporter.report(ReportSeverity.INFO, string5);
                                                                compileServiceImpl3.getLog().info(string5);
                                                                Unit unit7 = Unit.INSTANCE;
                                                                for (Map.Entry<Object, PerfCounters> entry4 : wallAndThreadTotalProfiler4.getCounters().entrySet()) {
                                                                    Object key4 = entry4.getKey();
                                                                    PerfCounters value4 = entry4.getValue();
                                                                    String str8 = "PERF: rpc by " + key4 + ": " + value4.getCount() + " calls, " + compileServiceImpl3.ms(value4.getTime()) + " ms, thread " + compileServiceImpl3.ms(value4.getThreadTime()) + " ms";
                                                                    DaemonMessageReporter.report(ReportSeverity.INFO, str8);
                                                                    compileServiceImpl3.getLog().info(str8);
                                                                    Unit unit8 = Unit.INSTANCE;
                                                                }
                                                            }
                                                            CompileService.CallResult.Good good7 = new CompileService.CallResult.Good(Integer.valueOf(numValueOf4.intValue()));
                                                            eventManagerImpl4.fireCompilationFinished();
                                                            CompileService.CallResult.Good good8 = good7;
                                                            ((CompileServiceImplBase) compileServiceImpl3)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                                            error2 = good8;
                                                        } catch (Throwable th34) {
                                                            Logger log6 = compileServiceImpl3.getLog();
                                                            Level level6 = Level.SEVERE;
                                                            StringBuilder sbAppend20 = new StringBuilder().append("Exception: ").append(th34).append("\n  ");
                                                            StackTraceElement[] stackTrace11 = th34.getStackTrace();
                                                            Intrinsics.checkNotNullExpressionValue(stackTrace11, "getStackTrace(...)");
                                                            StringBuilder sbAppend21 = sbAppend20.append(ArraysKt.joinToString$default(stackTrace11, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                                            if (th34.getCause() != null && !Intrinsics.areEqual(th34.getCause(), th34)) {
                                                                StringBuilder sbAppend22 = new StringBuilder().append("\nCaused by: ").append(th34.getCause()).append("\n  ");
                                                                Throwable cause6 = th34.getCause();
                                                                Intrinsics.checkNotNull(cause6);
                                                                StackTraceElement[] stackTrace12 = cause6.getStackTrace();
                                                                Intrinsics.checkNotNullExpressionValue(stackTrace12, "getStackTrace(...)");
                                                                string = sbAppend22.append(ArraysKt.joinToString$default(stackTrace12, "\n  ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).toString();
                                                            }
                                                            log6.log(level6, sbAppend21.append(string).toString());
                                                            throw th34;
                                                        }
                                                    } catch (Throwable th35) {
                                                        eventManagerImpl4.fireCompilationFinished();
                                                        throw th35;
                                                    }
                                                } catch (Throwable th36) {
                                                    ((CompileServiceImplBase) compileServiceImpl3)._lastUsedSeconds = CompileServiceImplKt.nowSeconds();
                                                    throw th36;
                                                }
                                            }
                                            IncrementalCompilation.setIsEnabledForJs(z);
                                        } catch (Throwable th37) {
                                            th = th37;
                                            z = zIsEnabledForJs;
                                        }
                                    }
                                }
                                error = error2;
                            }
                            error = error2;
                        } catch (Throwable th38) {
                            th = th38;
                            Throwable th110 = th;
                            compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th110);
                            error = new CompileService.CallResult.Error(th110);
                        }
                    } catch (Throwable th39) {
                        th = th39;
                    }
                } catch (Throwable th40) {
                    th = th40;
                    compileServiceImpl = compileServiceImpl2;
                }
            }
            lock.unlock();
            return error;
        } catch (Throwable th41) {
            th = th41;
        }
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [org.jetbrains.kotlin.daemon.common.DaemonOptions] */
    @Override // org.jetbrains.kotlin.daemon.CompileServiceImplBase
    protected void initiateElections() {
        CompileServiceImplBase.Aliveness aliveness = CompileServiceImplBase.Aliveness.LastSession;
        ReentrantReadWriteLock.ReadLock lock = this.rwlock.readLock();
        lock.lock();
        try {
            CompileServiceImpl compileServiceImpl = this;
            int i = ((CompileServiceImplBase) compileServiceImpl).state.getAlive().get();
            if (i < aliveness.ordinal()) {
                compileServiceImpl.getLog().info("Cannot perform operation, requested state: " + aliveness.name() + " > actual: " + compileServiceImpl.toAlivenessName(i));
                new CompileService.CallResult.Dying();
            } else {
                try {
                    getLog().info("initiate elections");
                    List<DaemonWithMetadata> list = SequencesKt.toList(ClientUtilsKt.walkDaemons(new File(DaemonParamsKt.getRunFilesPathOrDefault(getDaemonOptions())), getCompilerId(), getRunFile(), new Function2<File, Integer, Boolean>() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImpl$initiateElections$1$aliveWithOpts$1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Boolean invoke(File file, Integer num) {
                            return invoke(file, num.intValue());
                        }

                        public final Boolean invoke(File file, int i2) {
                            Intrinsics.checkNotNullParameter(file, "<anonymous parameter 0>");
                            return Boolean.valueOf(i2 != this.this$0.getPort());
                        }
                    }, new Function2<DaemonReportCategory, String, Unit>() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImpl$initiateElections$1$aliveWithOpts$2
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(DaemonReportCategory daemonReportCategory, String str) {
                            invoke2(daemonReportCategory, str);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(DaemonReportCategory daemonReportCategory, String msg) {
                            Intrinsics.checkNotNullParameter(daemonReportCategory, "<anonymous parameter 0>");
                            Intrinsics.checkNotNullParameter(msg, "msg");
                            this.this$0.getLog().info(msg);
                        }
                    }));
                    final DaemonJVMOptionsMemoryComparator daemonJVMOptionsMemoryComparator = new DaemonJVMOptionsMemoryComparator();
                    final Comparator comparator = new Comparator() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImpl$initiateElections$lambda$41$$inlined$compareByDescending$1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            return daemonJVMOptionsMemoryComparator.compare(((DaemonWithMetadata) t2).getJvmOptions(), ((DaemonWithMetadata) t).getJvmOptions());
                        }
                    };
                    final FileAgeComparator fileAgeComparator = new FileAgeComparator();
                    DaemonWithMetadata daemonWithMetadata = (DaemonWithMetadata) CollectionsKt.maxWithOrNull(list, new Comparator() { // from class: org.jetbrains.kotlin.daemon.CompileServiceImpl$initiateElections$lambda$41$$inlined$thenBy$1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            int iCompare = comparator.compare(t, t2);
                            return iCompare != 0 ? iCompare : fileAgeComparator.compare(((DaemonWithMetadata) t).getRunFile(), ((DaemonWithMetadata) t2).getRunFile());
                        }
                    });
                    if (daemonWithMetadata != null) {
                        DaemonJVMOptions jvmOptions = daemonWithMetadata.getJvmOptions();
                        if (DaemonParamsKt.memorywiseFitsInto(jvmOptions, this.daemonJVMOptions) && new FileAgeComparator().compare(daemonWithMetadata.getRunFile(), getRunFile()) < 0) {
                            getLog().info("Assuming other daemons have lower prio, taking clients from them and schedule them to shutdown: my runfile: " + getRunFile().getName() + " (" + getRunFile().lastModified() + ") vs best other runfile: " + daemonWithMetadata.getRunFile().getName() + " (" + daemonWithMetadata.getRunFile().lastModified() + ')');
                            for (DaemonWithMetadata daemonWithMetadata2 : list) {
                                CompileService daemon = daemonWithMetadata2.getDaemon();
                                File runFile = daemonWithMetadata2.getRunFile();
                                try {
                                    CompileService.CallResult<List<String>> clients = daemon.getClients();
                                    if (!clients.isGood()) {
                                        clients = null;
                                    }
                                    if (clients != null) {
                                        Iterator<T> it = clients.get().iterator();
                                        while (it.hasNext()) {
                                            registerClient((String) it.next());
                                        }
                                    }
                                    daemon.scheduleShutdown(true);
                                } catch (Throwable th) {
                                    getLog().info("Cannot connect to a daemon, assuming dying ('" + FilesKt.normalize(runFile).getAbsolutePath() + "'): " + th.getMessage());
                                }
                            }
                        } else if (DaemonParamsKt.memorywiseFitsInto(this.daemonJVMOptions, jvmOptions) && new FileAgeComparator().compare(daemonWithMetadata.getRunFile(), getRunFile()) > 0) {
                            getLog().info("Assuming other daemons have higher prio, handover clients to it and schedule shutdown: my runfile: " + getRunFile().getName() + " (" + getRunFile().lastModified() + ") vs best other runfile: " + daemonWithMetadata.getRunFile().getName() + " (" + daemonWithMetadata.getRunFile().lastModified() + ')');
                            CompileService.CallResult<List<String>> clients2 = getClients();
                            CompileService.CallResult<List<String>> callResult = clients2.isGood() ? clients2 : null;
                            if (callResult != null) {
                                Iterator<T> it2 = callResult.get().iterator();
                                while (it2.hasNext()) {
                                    daemonWithMetadata.getDaemon().registerClient((String) it2.next());
                                }
                            }
                            scheduleShutdown(true);
                        } else {
                            getLog().info("Assuming other daemons have equal prio, continue: " + getRunFile().getName() + " (" + getRunFile().lastModified() + ") vs best other runfile: " + daemonWithMetadata.getRunFile().getName() + " (" + daemonWithMetadata.getRunFile().lastModified() + ')');
                        }
                    }
                    new CompileService.CallResult.Ok();
                } catch (Throwable th2) {
                    compileServiceImpl.getLog().log(Level.SEVERE, "Exception", th2);
                    new CompileService.CallResult.Error(th2);
                    Unit unit = Unit.INSTANCE;
                    lock.unlock();
                }
            }
            Unit unit2 = Unit.INSTANCE;
            lock.unlock();
        } catch (Throwable th3) {
            lock.unlock();
            throw th3;
        }
    }
}
