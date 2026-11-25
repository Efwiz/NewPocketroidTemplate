package org.jetbrains.kotlin.daemon;

import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.util.List;
import java.util.logging.Logger;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.daemon.common.CompilerCallbackServicesFacade;
import org.jetbrains.kotlin.daemon.common.DummyProfiler;
import org.jetbrains.kotlin.daemon.common.Profiler;
import org.jetbrains.kotlin.daemon.common.RmiFriendlyCompilationCanceledException;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.progress.CompilationCanceledException;
import org.jetbrains.kotlin.progress.CompilationCanceledStatus;

/* JADX INFO: compiled from: RemoteCompilationCanceledStatusClient.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR#\u0010\u000f\u001a\n \u0011*\u0004\u0018\u00010\u00100\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/daemon/RemoteCompilationCanceledStatusClient;", "Lorg/jetbrains/kotlin/progress/CompilationCanceledStatus;", "facade", "Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "profiler", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "(Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;Lorg/jetbrains/kotlin/daemon/common/Profiler;)V", "getFacade", "()Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "lastChecked", "", "getLastChecked", "()J", "setLastChecked", "(J)V", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Ljava/util/logging/Logger;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getLog", "()Ljava/util/logging/Logger;", "log$delegate", "Lkotlin/Lazy;", "getProfiler", "()Lorg/jetbrains/kotlin/daemon/common/Profiler;", "checkCanceled", "", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoteCompilationCanceledStatusClient implements CompilationCanceledStatus {
    private final CompilerCallbackServicesFacade facade;
    private volatile long lastChecked;

    /* JADX INFO: renamed from: log$delegate, reason: from kotlin metadata */
    private final Lazy log;
    private final Profiler profiler;

    public RemoteCompilationCanceledStatusClient(CompilerCallbackServicesFacade facade, Profiler profiler) {
        Intrinsics.checkNotNullParameter(facade, "facade");
        Intrinsics.checkNotNullParameter(profiler, "profiler");
        this.facade = facade;
        this.profiler = profiler;
        this.log = LazyKt.lazy(new Function0<Logger>() { // from class: org.jetbrains.kotlin.daemon.RemoteCompilationCanceledStatusClient$log$2
            @Override // kotlin.jvm.functions.Function0
            public final Logger invoke() {
                return Logger.getLogger("compiler");
            }
        });
        this.lastChecked = System.nanoTime();
    }

    public final CompilerCallbackServicesFacade getFacade() {
        return this.facade;
    }

    public /* synthetic */ RemoteCompilationCanceledStatusClient(CompilerCallbackServicesFacade compilerCallbackServicesFacade, DummyProfiler dummyProfiler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilerCallbackServicesFacade, (i & 2) != 0 ? new DummyProfiler() : dummyProfiler);
    }

    public final Profiler getProfiler() {
        return this.profiler;
    }

    private final Logger getLog() {
        return (Logger) this.log.getValue();
    }

    public final long getLastChecked() {
        return this.lastChecked;
    }

    public final void setLastChecked(long j) {
        this.lastChecked = j;
    }

    private static final void checkCanceled$cancelOnError(RemoteCompilationCanceledStatusClient remoteCompilationCanceledStatusClient, Exception exc) {
        remoteCompilationCanceledStatusClient.getLog().warning("error communicating with host, assuming compilation canceled (" + exc.getMessage() + ')');
        throw new CompilationCanceledException();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.progress.CompilationCanceledStatus
    public void checkCanceled() throws RemoteException {
        long jNanoTime = System.nanoTime();
        if (jNanoTime - this.lastChecked > RemoteCompilationCanceledStatusClientKt.getCANCELED_STATUS_CHECK_THRESHOLD_NS()) {
            Profiler profiler = this.profiler;
            List<Long> listBeginMeasure = profiler.beginMeasure(this);
            try {
                this.facade.compilationCanceledStatus_checkCanceled();
            } catch (ConnectException e) {
                checkCanceled$cancelOnError(this, e);
                Unit unit = Unit.INSTANCE;
            } catch (RmiFriendlyCompilationCanceledException unused) {
                throw new CompilationCanceledException();
            } catch (UnmarshalException e2) {
                checkCanceled$cancelOnError(this, e2);
                Unit unit2 = Unit.INSTANCE;
            } catch (NoSuchObjectException e3) {
                checkCanceled$cancelOnError(this, e3);
                Unit unit3 = Unit.INSTANCE;
            } catch (ConnectIOException e4) {
                checkCanceled$cancelOnError(this, e4);
                Unit unit4 = Unit.INSTANCE;
            }
            profiler.endMeasure(this, listBeginMeasure);
            this.lastChecked = jNanoTime;
        }
    }
}
