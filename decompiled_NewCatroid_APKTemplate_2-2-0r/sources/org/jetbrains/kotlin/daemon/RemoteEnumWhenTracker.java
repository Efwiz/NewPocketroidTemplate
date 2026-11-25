package org.jetbrains.kotlin.daemon;

import java.rmi.RemoteException;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.daemon.common.CompilerCallbackServicesFacade;
import org.jetbrains.kotlin.daemon.common.DummyProfiler;
import org.jetbrains.kotlin.daemon.common.Profiler;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;

/* JADX INFO: compiled from: RemoteEnumWhenTracker.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/daemon/RemoteEnumWhenTracker;", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "facade", "Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "profiler", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "(Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;Lorg/jetbrains/kotlin/daemon/common/Profiler;)V", "getFacade", "()Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "getProfiler", "()Lorg/jetbrains/kotlin/daemon/common/Profiler;", "report", "", "whenExpressionFilePath", "", "enumClassFqName", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoteEnumWhenTracker implements EnumWhenTracker {
    private final CompilerCallbackServicesFacade facade;
    private final Profiler profiler;

    public RemoteEnumWhenTracker(CompilerCallbackServicesFacade facade, Profiler profiler) {
        Intrinsics.checkNotNullParameter(facade, "facade");
        Intrinsics.checkNotNullParameter(profiler, "profiler");
        this.facade = facade;
        this.profiler = profiler;
    }

    public final CompilerCallbackServicesFacade getFacade() {
        return this.facade;
    }

    public /* synthetic */ RemoteEnumWhenTracker(CompilerCallbackServicesFacade compilerCallbackServicesFacade, DummyProfiler dummyProfiler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilerCallbackServicesFacade, (i & 2) != 0 ? new DummyProfiler() : dummyProfiler);
    }

    public final Profiler getProfiler() {
        return this.profiler;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.incremental.components.EnumWhenTracker
    public void report(String whenExpressionFilePath, String enumClassFqName) throws RemoteException {
        Intrinsics.checkNotNullParameter(whenExpressionFilePath, "whenExpressionFilePath");
        Intrinsics.checkNotNullParameter(enumClassFqName, "enumClassFqName");
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        this.facade.enumWhenTracker_report(whenExpressionFilePath, enumClassFqName);
        Unit unit = Unit.INSTANCE;
        profiler.endMeasure(this, listBeginMeasure);
    }
}
