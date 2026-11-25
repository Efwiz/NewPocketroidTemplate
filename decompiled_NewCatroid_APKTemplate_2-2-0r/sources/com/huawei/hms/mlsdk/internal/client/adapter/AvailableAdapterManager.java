package com.huawei.hms.mlsdk.internal.client.adapter;

/* JADX INFO: loaded from: classes10.dex */
public class AvailableAdapterManager extends AbstractAdapter {
    private static final int MIN_HMS_APK_VERSION = 40002300;

    @Override // com.huawei.hms.mlsdk.internal.client.adapter.AbstractAdapter
    public int getMinHmsApkVersion() {
        return MIN_HMS_APK_VERSION;
    }

    private static final class Holder {
        static final AvailableAdapterManager INSTANCE = new AvailableAdapterManager();

        private Holder() {
        }
    }

    private AvailableAdapterManager() {
    }

    public static AvailableAdapterManager getInstance() {
        return Holder.INSTANCE;
    }
}
