package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public interface ContainerHolder extends Result, Releasable {

    /* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
    public interface ContainerAvailableListener {
        void onContainerAvailable(ContainerHolder containerHolder, String str);
    }

    Container getContainer();

    void refresh();

    void setContainerAvailableListener(ContainerAvailableListener containerAvailableListener);
}
