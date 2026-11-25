package org.jetbrains.kotlin.daemon;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.daemon.common.CompilerCallbackServicesFacade;
import org.jetbrains.kotlin.daemon.common.DummyProfiler;
import org.jetbrains.kotlin.daemon.common.Profiler;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.modules.TargetId;

/* JADX INFO: compiled from: RemoteIncrementalCompilationComponentsClient.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/daemon/RemoteIncrementalCompilationComponentsClient;", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCompilationComponents;", "facade", "Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "profiler", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "(Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;Lorg/jetbrains/kotlin/daemon/common/Profiler;)V", "getFacade", "()Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "getProfiler", "()Lorg/jetbrains/kotlin/daemon/common/Profiler;", "getIncrementalCache", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;", "target", "Lorg/jetbrains/kotlin/modules/TargetId;", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoteIncrementalCompilationComponentsClient implements IncrementalCompilationComponents {
    private final CompilerCallbackServicesFacade facade;
    private final Profiler profiler;

    public RemoteIncrementalCompilationComponentsClient(CompilerCallbackServicesFacade facade, Profiler profiler) {
        Intrinsics.checkNotNullParameter(facade, "facade");
        Intrinsics.checkNotNullParameter(profiler, "profiler");
        this.facade = facade;
        this.profiler = profiler;
    }

    public final CompilerCallbackServicesFacade getFacade() {
        return this.facade;
    }

    public /* synthetic */ RemoteIncrementalCompilationComponentsClient(CompilerCallbackServicesFacade compilerCallbackServicesFacade, DummyProfiler dummyProfiler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilerCallbackServicesFacade, (i & 2) != 0 ? new DummyProfiler() : dummyProfiler);
    }

    public final Profiler getProfiler() {
        return this.profiler;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents
    public IncrementalCache getIncrementalCache(TargetId target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return new RemoteIncrementalCacheClient(this.facade, target, this.profiler);
    }
}
