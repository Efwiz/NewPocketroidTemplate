package org.jetbrains.kotlin.daemon;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.daemon.common.DummyProfiler;
import org.jetbrains.kotlin.daemon.common.Profiler;
import org.jetbrains.kotlin.daemon.common.RemoteOutputStream;

/* JADX INFO: compiled from: RemoteOutputStreamClient.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0010H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/daemon/RemoteOutputStreamClient;", "Ljava/io/OutputStream;", "remote", "Lorg/jetbrains/kotlin/daemon/common/RemoteOutputStream;", "profiler", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "(Lorg/jetbrains/kotlin/daemon/common/RemoteOutputStream;Lorg/jetbrains/kotlin/daemon/common/Profiler;)V", "getProfiler", "()Lorg/jetbrains/kotlin/daemon/common/Profiler;", "getRemote", "()Lorg/jetbrains/kotlin/daemon/common/RemoteOutputStream;", "write", "", "data", "", TypedValues.CycleType.S_WAVE_OFFSET, "", "length", "byte", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoteOutputStreamClient extends OutputStream {
    private final Profiler profiler;
    private final RemoteOutputStream remote;

    public RemoteOutputStreamClient(RemoteOutputStream remote, Profiler profiler) {
        Intrinsics.checkNotNullParameter(remote, "remote");
        Intrinsics.checkNotNullParameter(profiler, "profiler");
        this.remote = remote;
        this.profiler = profiler;
    }

    public /* synthetic */ RemoteOutputStreamClient(RemoteOutputStream remoteOutputStream, DummyProfiler dummyProfiler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(remoteOutputStream, (i & 2) != 0 ? new DummyProfiler() : dummyProfiler);
    }

    public final Profiler getProfiler() {
        return this.profiler;
    }

    public final RemoteOutputStream getRemote() {
        return this.remote;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // java.io.OutputStream
    public void write(byte[] data) throws RemoteException {
        Intrinsics.checkNotNullParameter(data, "data");
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        this.remote.write(data, 0, data.length);
        Unit unit = Unit.INSTANCE;
        profiler.endMeasure(this, listBeginMeasure);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // java.io.OutputStream
    public void write(byte[] data, int offset, int length) throws RemoteException {
        Intrinsics.checkNotNullParameter(data, "data");
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        this.remote.write(data, offset, length);
        Unit unit = Unit.INSTANCE;
        profiler.endMeasure(this, listBeginMeasure);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // java.io.OutputStream
    public void write(int i) throws RemoteException {
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        this.remote.write(i);
        Unit unit = Unit.INSTANCE;
        profiler.endMeasure(this, listBeginMeasure);
    }
}
