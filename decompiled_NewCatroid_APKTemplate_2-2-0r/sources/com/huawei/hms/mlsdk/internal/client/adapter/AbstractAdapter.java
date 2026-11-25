package com.huawei.hms.mlsdk.internal.client.adapter;

import android.content.Context;
import com.huawei.hms.mlsdk.dynamic.IInitializer;
import com.huawei.hms.mlsdk.dynamic.util.TimerAssist;

/* JADX INFO: loaded from: classes10.dex */
public abstract class AbstractAdapter implements IAdapter {
    private boolean isAvailable = false;
    private boolean isModulePopup = false;
    private HmsAdapter hmsAdapter = new HmsAdapter();
    private ModuleAdapter moduleAdapter = new ModuleAdapter();

    public abstract int getMinHmsApkVersion();

    protected AbstractAdapter() {
    }

    @Override // com.huawei.hms.mlsdk.internal.client.adapter.IAdapter
    public boolean isAvailable(Context context, IInitializer iInitializer) {
        if (!this.isAvailable && TimerAssist.getInstance().isLock()) {
            return false;
        }
        if (!iInitializer.isLocalExisted() && !this.hmsAdapter.isHmsAvailable(context, getMinHmsApkVersion())) {
            this.isAvailable = false;
        } else {
            this.isAvailable = iInitializer.isOperational();
        }
        if (!this.isAvailable && this.hmsAdapter.isHmsInstalled() && !this.isModulePopup && this.moduleAdapter.notifyDownloadModuleIfNeeded(iInitializer.getLoadExceptionBundle())) {
            this.isModulePopup = true;
        }
        return this.isAvailable;
    }

    @Override // com.huawei.hms.mlsdk.internal.client.adapter.IAdapter
    public synchronized void notifyDownloadIfNeeded(Context context, IInitializer iInitializer) {
        this.isModulePopup = false;
        this.hmsAdapter.resetStatus();
        if (iInitializer.isLocalExisted()) {
            return;
        }
        if (!this.hmsAdapter.isHmsAvailable(context, getMinHmsApkVersion())) {
            this.hmsAdapter.notifyDownloadHmsIfNeeded(context, getMinHmsApkVersion());
        } else {
            this.moduleAdapter.notifyDownloadModuleIfNeeded(iInitializer.getLoadExceptionBundle());
        }
    }

    @Override // com.huawei.hms.mlsdk.internal.client.adapter.IAdapter
    public void release(Context context) {
        this.hmsAdapter.release(context);
        this.moduleAdapter.release(context);
    }

    public HmsAdapter getHmsAdapter() {
        return this.hmsAdapter;
    }

    public ModuleAdapter getModuleAdapter() {
        return this.moduleAdapter;
    }
}
