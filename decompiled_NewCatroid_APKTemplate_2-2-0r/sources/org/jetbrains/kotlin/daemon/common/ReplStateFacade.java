package org.jetbrains.kotlin.daemon.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.repl.ILineId;

/* JADX INFO: compiled from: ReplStateFacade.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003H&J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\tH&J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u0006\u0010\u000b\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/ReplStateFacade;", "Ljava/rmi/Remote;", "getHistorySize", "", "getId", "historyGet", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "index", "historyReset", "", "historyResetTo", "id", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReplStateFacade extends Remote {
    int getHistorySize() throws RemoteException;

    int getId() throws RemoteException;

    ILineId historyGet(int index) throws RemoteException;

    List<ILineId> historyReset() throws RemoteException;

    List<ILineId> historyResetTo(ILineId id2) throws RemoteException;
}
