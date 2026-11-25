package org.jetbrains.kotlin.daemon;

import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.repl.ILineId;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface;
import org.jetbrains.kotlin.daemon.common.ReplStateFacade;

/* JADX INFO: compiled from: RemoteReplStateFacadeImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0004¢\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u0004H\u0016J\b\u0010\u000e\u001a\u00020\u0004H\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0004H\u0016J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u0013H\u0016J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u00132\u0006\u0010\u0015\u001a\u00020\u0010H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0015\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/daemon/RemoteReplStateFacadeServer;", "Lorg/jetbrains/kotlin/daemon/common/ReplStateFacade;", "Ljava/rmi/server/UnicastRemoteObject;", "_id", "", "state", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "port", "(ILorg/jetbrains/kotlin/cli/common/repl/IReplStageState;I)V", "get_id", "()I", "getState", "()Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "getHistorySize", "getId", "historyGet", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "index", "historyReset", "", "historyResetTo", "id", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoteReplStateFacadeServer extends UnicastRemoteObject implements ReplStateFacade {
    private final int _id;
    private final IReplStageState<?> state;

    public /* synthetic */ RemoteReplStateFacadeServer(int i, IReplStageState iReplStageState, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, iReplStageState, (i3 & 4) != 0 ? 0 : i2);
    }

    public final int get_id() {
        return this._id;
    }

    public final IReplStageState<?> getState() {
        return this.state;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RemoteReplStateFacadeServer(int i, IReplStageState<?> state, int i2) {
        super(i2, LoopbackNetworkInterface.INSTANCE.getClientLoopbackSocketFactory(), LoopbackNetworkInterface.INSTANCE.getServerLoopbackSocketFactory());
        Intrinsics.checkNotNullParameter(state, "state");
        this._id = i;
        this.state = state;
    }

    @Override // org.jetbrains.kotlin.daemon.common.ReplStateFacade
    /* JADX INFO: renamed from: getId, reason: from getter */
    public int get_id() {
        return this._id;
    }

    @Override // org.jetbrains.kotlin.daemon.common.ReplStateFacade
    public int getHistorySize() {
        return this.state.getHistory().size();
    }

    @Override // org.jetbrains.kotlin.daemon.common.ReplStateFacade
    public ILineId historyGet(int index) {
        return this.state.getHistory().get(index).getId();
    }

    @Override // org.jetbrains.kotlin.daemon.common.ReplStateFacade
    public List<ILineId> historyReset() {
        return CollectionsKt.toList(this.state.getHistory().reset());
    }

    @Override // org.jetbrains.kotlin.daemon.common.ReplStateFacade
    public List<ILineId> historyResetTo(ILineId id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        return CollectionsKt.toList(this.state.getHistory().resetTo(id2));
    }
}
