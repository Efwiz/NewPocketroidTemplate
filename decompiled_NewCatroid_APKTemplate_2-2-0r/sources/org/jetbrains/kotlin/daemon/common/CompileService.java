package org.jetbrains.kotlin.daemon.common;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.cli.common.repl.ReplCheckResult;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;
import org.jetbrains.kotlin.cli.common.repl.ReplCompileResult;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.js.JavaScript;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CompileService.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\bf\u0018\u0000 <2\u00020\u0001:\u0004;<=>J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J*\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00072\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\bH&J\b\u0010\u000e\u001a\u00020\u000fH&JE\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00072\u0006\u0010\n\u001a\u00020\u000b2\u000e\u0010\u0011\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H&¢\u0006\u0002\u0010\u0019J\u0014\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u001b0\u0007H&J\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u0007H&J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0007H&J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0007H&J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020\t0\u0007H&J\u0018\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u00072\b\b\u0002\u0010$\u001a\u00020\u0003H&J\u0018\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00072\b\u0010&\u001a\u0004\u0018\u00010\tH&JS\u0010'\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00072\b\u0010&\u001a\u0004\u0018\u00010\t2\u000e\u0010\u0011\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\r0\u001b2\u0006\u0010)\u001a\u00020\tH&¢\u0006\u0002\u0010*J\u0018\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\u00072\b\u0010&\u001a\u0004\u0018\u00010\tH&J\u0016\u0010-\u001a\b\u0012\u0004\u0012\u00020,0\u00072\u0006\u0010\n\u001a\u00020\u000bH&J\u0016\u0010.\u001a\b\u0012\u0004\u0012\u00020,0\u00072\u0006\u0010\n\u001a\u00020\u000bH&J&\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u000b2\u0006\u00102\u001a\u000203H&J&\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u000b2\u0006\u00102\u001a\u000203H&J\u0016\u00106\u001a\b\u0012\u0004\u0012\u0002070\u00072\u0006\u0010\n\u001a\u00020\u000bH&J\u0016\u00108\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u0006\u00109\u001a\u00020\u0003H&J\u000e\u0010:\u001a\b\u0012\u0004\u0012\u00020,0\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006?À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompileService;", "Ljava/rmi/Remote;", "checkCompilerId", "", "expectedCompilerId", "Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "classesFqNamesByFiles", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "", "", "sessionId", "", "sourceFiles", "Ljava/io/File;", "clearJarCache", "", "compile", "compilerArguments", "", "compilationOptions", "Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;", "servicesFacade", "Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;", "compilationResults", "Lorg/jetbrains/kotlin/daemon/common/CompilationResults;", "(I[Ljava/lang/String;Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;Lorg/jetbrains/kotlin/daemon/common/CompilationResults;)Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "getClients", "", "getDaemonInfo", "getDaemonJVMOptions", "Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "getDaemonOptions", "Lorg/jetbrains/kotlin/daemon/common/DaemonOptions;", "getKotlinVersion", "getUsedMemory", "", "withGC", "leaseCompileSession", "aliveFlagPath", "leaseReplSession", "templateClasspath", "templateClassName", "(Ljava/lang/String;[Ljava/lang/String;Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;Ljava/util/List;Ljava/lang/String;)Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "registerClient", "", "releaseCompileSession", "releaseReplSession", "replCheck", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult;", "replStateId", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "replCompile", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", "replCreateState", "Lorg/jetbrains/kotlin/daemon/common/ReplStateFacade;", "scheduleShutdown", "graceful", "shutdown", "CallResult", "Companion", "OutputFormat", "TargetPlatform", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CompileService extends Remote {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: CompileService.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompileService$OutputFormat;", "", "Ljava/io/Serializable;", "(Ljava/lang/String;I)V", "PLAIN", "XML", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum OutputFormat implements Serializable {
        PLAIN,
        XML;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<OutputFormat> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: CompileService.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompileService$TargetPlatform;", "", "Ljava/io/Serializable;", "(Ljava/lang/String;I)V", "JVM", JavaScript.NAME, "METADATA", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum TargetPlatform implements Serializable {
        JVM,
        JS,
        METADATA;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<TargetPlatform> getEntries() {
            return $ENTRIES;
        }
    }

    boolean checkCompilerId(CompilerId expectedCompilerId) throws RemoteException;

    CallResult<Set<String>> classesFqNamesByFiles(int sessionId, Set<? extends File> sourceFiles) throws RemoteException;

    void clearJarCache() throws RemoteException;

    CallResult<Integer> compile(int sessionId, String[] compilerArguments, CompilationOptions compilationOptions, CompilerServicesFacadeBase servicesFacade, CompilationResults compilationResults) throws RemoteException;

    CallResult<List<String>> getClients() throws RemoteException;

    CallResult<String> getDaemonInfo() throws RemoteException;

    CallResult<DaemonJVMOptions> getDaemonJVMOptions() throws RemoteException;

    CallResult<DaemonOptions> getDaemonOptions() throws RemoteException;

    CallResult<String> getKotlinVersion() throws RemoteException;

    CallResult<Long> getUsedMemory(boolean withGC) throws RemoteException;

    CallResult<Integer> leaseCompileSession(String aliveFlagPath) throws RemoteException;

    CallResult<Integer> leaseReplSession(String aliveFlagPath, String[] compilerArguments, CompilationOptions compilationOptions, CompilerServicesFacadeBase servicesFacade, List<? extends File> templateClasspath, String templateClassName) throws RemoteException;

    CallResult registerClient(String aliveFlagPath) throws RemoteException;

    CallResult releaseCompileSession(int sessionId) throws RemoteException;

    CallResult releaseReplSession(int sessionId) throws RemoteException;

    CallResult<ReplCheckResult> replCheck(int sessionId, int replStateId, ReplCodeLine codeLine) throws RemoteException;

    CallResult<ReplCompileResult> replCompile(int sessionId, int replStateId, ReplCodeLine codeLine) throws RemoteException;

    CallResult<ReplStateFacade> replCreateState(int sessionId) throws RemoteException;

    CallResult<Boolean> scheduleShutdown(boolean graceful) throws RemoteException;

    CallResult shutdown() throws RemoteException;

    /* JADX INFO: compiled from: CompileService.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompileService$Companion;", "", "()V", "NO_SESSION", "", "getNO_SESSION", "()I", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final int NO_SESSION = 0;

        private Companion() {
        }

        public final int getNO_SESSION() {
            return NO_SESSION;
        }
    }

    /* JADX INFO: compiled from: CompileService.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0004\t\n\u000b\fB\u0007\b\u0004¢\u0006\u0002\u0010\u0003J\r\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006\u0082\u0001\u0004\r\u000e\u000f\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "R", "Ljava/io/Serializable;", "()V", "isGood", "", "()Z", "get", "()Ljava/lang/Object;", "Dying", "Error", "Good", "Ok", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult$Dying;", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult$Error;", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult$Good;", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult$Ok;", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class CallResult<R> implements Serializable {
        public /* synthetic */ CallResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract R get();

        private CallResult() {
        }

        /* JADX INFO: compiled from: CompileService.kt */
        @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004J\u0013\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0096\u0002J\r\u0010\f\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0006J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult$Good;", "R", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "result", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getResult", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, Namer.EQUALS_METHOD_NAME, "", "other", "", "get", "hashCode", "", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Good<R> extends CallResult<R> {
            private final R result;

            public Good(R r) {
                super(null);
                this.result = r;
            }

            public final R getResult() {
                return this.result;
            }

            @Override // org.jetbrains.kotlin.daemon.common.CompileService.CallResult
            public R get() {
                return this.result;
            }

            public boolean equals(Object other) {
                return (other instanceof Good) && Intrinsics.areEqual(this.result, ((Good) other).result);
            }

            public int hashCode() {
                int iHashCode = getClass().hashCode();
                R r = this.result;
                return iHashCode + (r != null ? r.hashCode() : 1);
            }
        }

        /* JADX INFO: compiled from: CompileService.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0013\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0096\u0002J\b\u0010\b\u001a\u00020\u0002H\u0016J\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult$Ok;", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "get", "hashCode", "", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Ok extends CallResult {
            public Ok() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.daemon.common.CompileService.CallResult
            public Void get() {
                throw new IllegalStateException("Get is inapplicable to Ok call result");
            }

            public boolean equals(Object other) {
                return other instanceof Ok;
            }

            public int hashCode() {
                return getClass().hashCode() + 1;
            }
        }

        /* JADX INFO: compiled from: CompileService.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0013\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0096\u0002J\b\u0010\b\u001a\u00020\u0002H\u0016J\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult$Dying;", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "get", "hashCode", "", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Dying extends CallResult {
            public Dying() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.daemon.common.CompileService.CallResult
            public Void get() {
                throw new IllegalStateException("Service is dying");
            }

            public boolean equals(Object other) {
                return other instanceof Dying;
            }

            public int hashCode() {
                return getClass().hashCode() + 1;
            }
        }

        /* JADX INFO: compiled from: CompileService.kt */
        @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u000f\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u0019\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\tJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0002H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult$Error;", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "", "cause", "", "(Ljava/lang/Throwable;)V", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "getCause", "()Ljava/lang/Throwable;", "getMessage", "()Ljava/lang/String;", Namer.EQUALS_METHOD_NAME, "", "other", "", "get", "hashCode", "", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Error extends CallResult {
            private final Throwable cause;
            private final String message;

            public Error(String str, Throwable th) {
                super(null);
                this.message = str;
                this.cause = th;
            }

            public final Throwable getCause() {
                return this.cause;
            }

            public final String getMessage() {
                return this.message;
            }

            /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
            public Error(Throwable cause) {
                this(null, cause);
                Intrinsics.checkNotNullParameter(cause, "cause");
            }

            /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
            public Error(String message) {
                this(message, null);
                Intrinsics.checkNotNullParameter(message, "message");
            }

            @Override // org.jetbrains.kotlin.daemon.common.CompileService.CallResult
            public Void get() throws Exception {
                throw new Exception(this.message, this.cause);
            }

            public boolean equals(Object other) {
                if (other instanceof Error) {
                    Error error = (Error) other;
                    if (Intrinsics.areEqual(this.message, error.message) && Intrinsics.areEqual(this.cause, error.cause)) {
                        return true;
                    }
                }
                return false;
            }

            public int hashCode() {
                int iHashCode = getClass().hashCode();
                Throwable th = this.cause;
                int iHashCode2 = iHashCode + (th != null ? th.hashCode() : 1);
                String str = this.message;
                return iHashCode2 + (str != null ? str.hashCode() : 2);
            }
        }

        public final boolean isGood() {
            return this instanceof Good;
        }
    }

    static /* synthetic */ CallResult getUsedMemory$default(CompileService compileService, boolean z, int i, Object obj) throws RemoteException {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getUsedMemory");
        }
        if ((i & 1) != 0) {
            z = true;
        }
        return compileService.getUsedMemory(z);
    }
}
