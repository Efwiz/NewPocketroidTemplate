package org.jetbrains.kotlin.daemon;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.repl.CreateReplStageStateAction;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.cli.common.repl.ReplCheckResult;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;
import org.jetbrains.kotlin.cli.common.repl.ReplCompileResult;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.daemon.common.CompileService;
import org.jetbrains.kotlin.daemon.common.CompilerId;
import org.jetbrains.kotlin.daemon.common.RemoteOperationsTracer;

/* JADX INFO: compiled from: KotlinRemoteReplService.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\fH\u0014J\u0010\u0010&\u001a\u00020$2\u0006\u0010%\u001a\u00020\fH\u0014J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0007J \u0010+\u001a\u00020,2\u0006\u0010)\u001a\u00020*2\u000e\u0010-\u001a\n\u0012\u0004\u0012\u00020*\u0018\u00010\tH\u0007J\u0010\u0010.\u001a\u00020\u00132\b\b\u0002\u0010/\u001a\u00020\u0005J2\u00100\u001a\b\u0012\u0004\u0012\u0002H201\"\u0004\b\u0000\u001022\u0006\u00103\u001a\u00020\u00052\u0016\u00104\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u000306\u0012\u0004\u0012\u0002H205R!\u0010\u0012\u001a\u00020\u00138DX\u0085\u0084\u0002¢\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u0012\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR \u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020 0\u001fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/daemon/KotlinJvmReplService;", "Lorg/jetbrains/kotlin/daemon/KotlinJvmReplServiceBase;", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "portForServers", "", "compilerId", "Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "templateClasspath", "", "Ljava/io/File;", "templateClassName", "", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "operationsTracer", "Lorg/jetbrains/kotlin/daemon/common/RemoteOperationsTracer;", "(Lcom/intellij/openapi/Disposable;ILorg/jetbrains/kotlin/daemon/common/CompilerId;Ljava/util/List;Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Lorg/jetbrains/kotlin/daemon/common/RemoteOperationsTracer;)V", "defaultStateFacade", "Lorg/jetbrains/kotlin/daemon/RemoteReplStateFacadeServer;", "getDefaultStateFacade$annotations", "()V", "getDefaultStateFacade", "()Lorg/jetbrains/kotlin/daemon/RemoteReplStateFacadeServer;", "defaultStateFacade$delegate", "Lkotlin/Lazy;", "getOperationsTracer", "()Lorg/jetbrains/kotlin/daemon/common/RemoteOperationsTracer;", "getPortForServers", "()I", "states", "Ljava/util/WeakHashMap;", "", "getStates", "()Ljava/util/WeakHashMap;", "after", "", "s", "before", "check", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult;", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "compile", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", "verifyHistory", "createRemoteState", "port", "withValidReplState", "Lorg/jetbrains/kotlin/daemon/common/CompileService$CallResult;", "R", "stateId", "body", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinJvmReplService extends KotlinJvmReplServiceBase {

    /* JADX INFO: renamed from: defaultStateFacade$delegate, reason: from kotlin metadata */
    private final Lazy defaultStateFacade;
    private final RemoteOperationsTracer operationsTracer;
    private final int portForServers;
    private final WeakHashMap<RemoteReplStateFacadeServer, Boolean> states;

    @Deprecated(message = "remove after removal state-less check/compile/eval methods")
    protected static /* synthetic */ void getDefaultStateFacade$annotations() {
    }

    public final int getPortForServers() {
        return this.portForServers;
    }

    protected final RemoteOperationsTracer getOperationsTracer() {
        return this.operationsTracer;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinJvmReplService(Disposable disposable, int i, CompilerId compilerId, List<? extends File> templateClasspath, String templateClassName, MessageCollector messageCollector, RemoteOperationsTracer remoteOperationsTracer) {
        super(disposable, compilerId, templateClasspath, templateClassName, messageCollector);
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        Intrinsics.checkNotNullParameter(compilerId, "compilerId");
        Intrinsics.checkNotNullParameter(templateClasspath, "templateClasspath");
        Intrinsics.checkNotNullParameter(templateClassName, "templateClassName");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        this.portForServers = i;
        this.operationsTracer = remoteOperationsTracer;
        this.states = new WeakHashMap<>();
        this.defaultStateFacade = LazyKt.lazy(new Function0<RemoteReplStateFacadeServer>() { // from class: org.jetbrains.kotlin.daemon.KotlinJvmReplService$defaultStateFacade$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final RemoteReplStateFacadeServer invoke() {
                return KotlinJvmReplService.createRemoteState$default(this.this$0, 0, 1, null);
            }
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.daemon.KotlinJvmReplServiceBase
    protected void before(String s) throws RemoteException {
        Intrinsics.checkNotNullParameter(s, "s");
        RemoteOperationsTracer remoteOperationsTracer = this.operationsTracer;
        if (remoteOperationsTracer != null) {
            remoteOperationsTracer.before(s);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.daemon.KotlinJvmReplServiceBase
    protected void after(String s) throws RemoteException {
        Intrinsics.checkNotNullParameter(s, "s");
        RemoteOperationsTracer remoteOperationsTracer = this.operationsTracer;
        if (remoteOperationsTracer != null) {
            remoteOperationsTracer.after(s);
        }
    }

    protected final WeakHashMap<RemoteReplStateFacadeServer, Boolean> getStates() {
        return this.states;
    }

    protected final RemoteReplStateFacadeServer getDefaultStateFacade() {
        return (RemoteReplStateFacadeServer) this.defaultStateFacade.getValue();
    }

    @Deprecated(message = "Use check(state, line) instead")
    public final ReplCheckResult check(ReplCodeLine codeLine) {
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        return check(getDefaultStateFacade().getState(), codeLine);
    }

    @Deprecated(message = "Use compile(state, line) instead")
    public final ReplCompileResult compile(ReplCodeLine codeLine, List<ReplCodeLine> verifyHistory) {
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        return compile(getDefaultStateFacade().getState(), codeLine);
    }

    public static /* synthetic */ RemoteReplStateFacadeServer createRemoteState$default(KotlinJvmReplService kotlinJvmReplService, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createRemoteState");
        }
        if ((i2 & 1) != 0) {
            i = kotlinJvmReplService.portForServers;
        }
        return kotlinJvmReplService.createRemoteState(i);
    }

    public final RemoteReplStateFacadeServer createRemoteState(int port) {
        ReentrantReadWriteLock statesLock = getStatesLock();
        ReentrantReadWriteLock.ReadLock lock = statesLock.readLock();
        int i = 0;
        int readHoldCount = statesLock.getWriteHoldCount() == 0 ? statesLock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = statesLock.writeLock();
        writeLock.lock();
        try {
            AtomicInteger stateIdCounter = getStateIdCounter();
            int iIncrementAndGet = stateIdCounter.incrementAndGet();
            int i3 = 100;
            loop1: while (true) {
                WeakHashMap<RemoteReplStateFacadeServer, Boolean> weakHashMap = this.states;
                if (weakHashMap.isEmpty()) {
                    break;
                }
                Iterator<Map.Entry<RemoteReplStateFacadeServer, Boolean>> it = weakHashMap.entrySet().iterator();
                do {
                    if (!it.hasNext()) {
                        break loop1;
                    }
                } while (it.next().getKey().get_id() != iIncrementAndGet);
                i3--;
                if (i3 <= 0) {
                    throw new IllegalStateException("Invalid state or algorithm error");
                }
                iIncrementAndGet = stateIdCounter.addAndGet(KotlinRemoteReplServiceKt.getInternalRng().nextInt());
            }
            RemoteReplStateFacadeServer remoteReplStateFacadeServer = new RemoteReplStateFacadeServer(iIncrementAndGet, CreateReplStageStateAction.createState$default(this, null, 1, null), port);
            this.states.put(remoteReplStateFacadeServer, true);
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
            return remoteReplStateFacadeServer;
        } catch (Throwable th) {
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
            throw th;
        }
    }

    public final <R> CompileService.CallResult<R> withValidReplState(int stateId, Function1<? super IReplStageState<?>, ? extends R> body) {
        Object next;
        CompileService.CallResult.Error error;
        Intrinsics.checkNotNullParameter(body, "body");
        ReentrantReadWriteLock.ReadLock lock = getStatesLock().readLock();
        lock.lock();
        try {
            Set<RemoteReplStateFacadeServer> setKeySet = this.states.keySet();
            Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
            Iterator<T> it = setKeySet.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (((RemoteReplStateFacadeServer) next).get_id() != stateId);
            RemoteReplStateFacadeServer remoteReplStateFacadeServer = (RemoteReplStateFacadeServer) next;
            if (remoteReplStateFacadeServer != null) {
                error = new CompileService.CallResult.Good(body.invoke(remoteReplStateFacadeServer.getState()));
            } else {
                error = new CompileService.CallResult.Error("No REPL state with id " + stateId + " found");
            }
            return error;
        } finally {
            lock.unlock();
        }
    }
}
