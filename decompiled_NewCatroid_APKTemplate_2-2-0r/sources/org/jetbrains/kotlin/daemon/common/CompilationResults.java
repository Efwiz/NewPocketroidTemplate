package org.jetbrains.kotlin.daemon.common;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import kotlin.Metadata;

/* JADX INFO: compiled from: CompilationResults.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompilationResults;", "Ljava/rmi/Remote;", ProductAction.ACTION_ADD, "", "compilationResultCategory", "", "value", "Ljava/io/Serializable;", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CompilationResults extends Remote {
    void add(int compilationResultCategory, Serializable value) throws RemoteException;
}
