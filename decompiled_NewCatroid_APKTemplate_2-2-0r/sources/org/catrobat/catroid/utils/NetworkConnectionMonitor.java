package org.catrobat.catroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;
import androidx.lifecycle.LiveData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NetworkConnectionMonitor.class
  classes14.dex
 */
/* JADX INFO: compiled from: NetworkConnectionMonitor.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u0000 \u00142\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0014\u0015B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0002H\u0007J\u0006\u0010\u0013\u001a\u00020\u0010R\u001e\u0010\u0006\u001a\u00060\u0007R\u00020\u0000X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/utils/NetworkConnectionMonitor;", "Landroidx/lifecycle/LiveData;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "callback", "Lorg/catrobat/catroid/utils/NetworkConnectionMonitor$ConnectionNetworkCallback;", "getCallback", "()Lorg/catrobat/catroid/utils/NetworkConnectionMonitor$ConnectionNetworkCallback;", "setCallback", "(Lorg/catrobat/catroid/utils/NetworkConnectionMonitor$ConnectionNetworkCallback;)V", "checkConnection", "connectivityManager", "Landroid/net/ConnectivityManager;", "registerDefaultNetworkCallback", "", "setValueTo", "value", "unregisterDefaultNetworkCallback", "Companion", "ConnectionNetworkCallback", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NetworkConnectionMonitor extends LiveData<Boolean> {
    private static final String TAG = Reflection.getOrCreateKotlinClass(NetworkConnectionMonitor.class).getSimpleName();
    public ConnectionNetworkCallback callback;
    private final Context context;

    public NetworkConnectionMonitor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final ConnectionNetworkCallback getCallback() {
        ConnectionNetworkCallback connectionNetworkCallback = this.callback;
        if (connectionNetworkCallback != null) {
            return connectionNetworkCallback;
        }
        Intrinsics.throwUninitializedPropertyAccessException("callback");
        return null;
    }

    public final void setCallback(ConnectionNetworkCallback connectionNetworkCallback) {
        Intrinsics.checkNotNullParameter(connectionNetworkCallback, "<set-?>");
        this.callback = connectionNetworkCallback;
    }

    public final void unregisterDefaultNetworkCallback() {
        Object systemService = this.context.getSystemService("connectivity");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
        ((ConnectivityManager) systemService).unregisterNetworkCallback(getCallback());
    }

    public final void registerDefaultNetworkCallback() {
        Object systemService = this.context.getSystemService("connectivity");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
        ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
        postValue(Boolean.valueOf(checkConnection(connectivityManager)));
        setCallback(new ConnectionNetworkCallback());
        connectivityManager.registerDefaultNetworkCallback(getCallback());
    }

    private final boolean checkConnection(ConnectivityManager connectivityManager) {
        NetworkCapabilities networkCapabilities;
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
            return false;
        }
        return networkCapabilities.hasTransport(1) || networkCapabilities.hasTransport(0) || networkCapabilities.hasTransport(3) || networkCapabilities.hasTransport(2);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NetworkConnectionMonitor$ConnectionNetworkCallback.class
      classes14.dex
     */
    /* JADX INFO: compiled from: NetworkConnectionMonitor.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/utils/NetworkConnectionMonitor$ConnectionNetworkCallback;", "Landroid/net/ConnectivityManager$NetworkCallback;", "(Lorg/catrobat/catroid/utils/NetworkConnectionMonitor;)V", "onAvailable", "", "network", "Landroid/net/Network;", "onLost", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class ConnectionNetworkCallback extends ConnectivityManager.NetworkCallback {
        public ConnectionNetworkCallback() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            Intrinsics.checkNotNullParameter(network, "network");
            super.onAvailable(network);
            NetworkConnectionMonitor.this.postValue(true);
            Log.d(NetworkConnectionMonitor.TAG, "onAvailable");
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            Intrinsics.checkNotNullParameter(network, "network");
            super.onLost(network);
            NetworkConnectionMonitor.this.postValue(false);
            Log.d(NetworkConnectionMonitor.TAG, "onLost");
        }
    }

    public final void setValueTo(boolean value) {
        postValue(Boolean.valueOf(value));
    }
}
