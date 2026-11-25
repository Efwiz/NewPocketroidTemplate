package com.huawei.hms.mlsdk.tts.client;

import android.content.Context;
import android.os.IInterface;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlkit.tts.common.IRemoteTtsCallback;
import com.huawei.hms.mlkit.tts.common.IRemoteTtsDelegate;
import com.huawei.hms.mlkit.tts.common.TtsCancelParcel;
import com.huawei.hms.mlkit.tts.common.TtsOptionsParcel;
import com.huawei.hms.mlkit.tts.common.TtsParcel;
import com.huawei.hms.mlkit.tts.common.TtsTextParcel;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.internal.client.adapter.AvailableAdapterManager;

/* JADX INFO: compiled from: RemoteOnDeviceTts.java */
/* JADX INFO: loaded from: classes10.dex */
public class b {
    private static MLApplication e = MLApplication.getInstance();
    private boolean a = false;
    private com.huawei.hms.mlsdk.tts.engine.offline.b b;
    private d c;
    private boolean d;

    /* JADX INFO: compiled from: RemoteOnDeviceTts.java */
    class a extends IRemoteTtsCallback.Stub {
        a() {
        }

        @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsCallback
        public void callbackFromAPK(TtsParcel ttsParcel) throws RemoteException {
            byte[] bArr = ttsParcel.pcm;
            int i = ttsParcel.rangeStart;
            int i2 = ttsParcel.rangeEnd;
            b.this.b.a(bArr, i, i2);
            SmartLog.i("Tts_SDK_RemoteOnDeviceTts", "callback from apk :rangeStart: " + i + "+ rangeStart: " + i2);
        }
    }

    public b(ModuleInfo moduleInfo) {
        this.c = new d(moduleInfo);
        this.d = moduleInfo.isLiteFlag();
    }

    public int b() {
        IInterface dynamicDelegate = this.c.getDynamicDelegate();
        if (dynamicDelegate == null) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "getModelLevel|delegate is null, return!");
            return -1;
        }
        try {
            if (dynamicDelegate instanceof IRemoteTtsDelegate) {
                return ((IRemoteTtsDelegate) dynamicDelegate).getModelLevel();
            }
            return 1;
        } catch (Exception e2) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", com.huawei.hms.mlsdk.t.a.a("destroy Exception e: ").append(e2.getMessage()).toString());
            return 1;
        }
    }

    public boolean c() {
        return this.d;
    }

    public void a(com.huawei.hms.mlsdk.tts.engine.offline.b bVar) {
        this.b = bVar;
    }

    public int a(TtsOptionsParcel ttsOptionsParcel) {
        if (this.a) {
            return 0;
        }
        try {
            a(e.getAppContext());
        } catch (IllegalStateException e2) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", com.huawei.hms.mlsdk.t.a.a("initialize must be called first, e.exception:").append(e2.getMessage()).toString());
        }
        SmartLog.i("Tts_SDK_RemoteOnDeviceTts", "initialize|[3.7.0.303]");
        IInterface dynamicDelegate = this.c.getDynamicDelegate();
        if (dynamicDelegate == null) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "initialize|delegate is null!");
            return -1;
        }
        try {
            int iInitialize = dynamicDelegate instanceof IRemoteTtsDelegate ? ((IRemoteTtsDelegate) dynamicDelegate).initialize(ObjectWrapper.wrap(this.c.getDynamicContext()), ttsOptionsParcel) : -1;
            if (iInitialize == 0) {
                this.a = true;
                ((IRemoteTtsDelegate) dynamicDelegate).addCallback(new a());
                SmartLog.i("Tts_SDK_RemoteOnDeviceTts", "initialize|success!");
                return iInitialize;
            }
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "initialize|failure " + iInitialize);
            return -1;
        } catch (Exception e3) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "initialize|has exception: " + e3);
            return -1;
        }
    }

    public void b(TtsOptionsParcel ttsOptionsParcel) {
        SmartLog.i("Tts_SDK_RemoteOnDeviceTts", "updateConfig|Enter!");
        IInterface dynamicDelegate = this.c.getDynamicDelegate();
        if (dynamicDelegate == null) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "updateConfig|delegate is null, return!");
            return;
        }
        try {
            if (dynamicDelegate instanceof IRemoteTtsDelegate) {
                ((IRemoteTtsDelegate) dynamicDelegate).updateConfig(ttsOptionsParcel);
            }
        } catch (Exception e2) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "updateConfig|has exception: " + e2);
        }
    }

    public void a(TtsCancelParcel ttsCancelParcel) {
        SmartLog.i("Tts_SDK_RemoteOnDeviceTts", "stopSynthesis|Enter!");
        IInterface dynamicDelegate = this.c.getDynamicDelegate();
        if (dynamicDelegate == null) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "stopSynthesis|delegate is null, return!");
            return;
        }
        try {
            if (dynamicDelegate instanceof IRemoteTtsDelegate) {
                ((IRemoteTtsDelegate) dynamicDelegate).stopSynthesis(ttsCancelParcel);
            }
        } catch (Exception e2) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "stopSynthesis|has exception: " + e2);
        }
    }

    public synchronized void a() {
        SmartLog.i("Tts_SDK_RemoteOnDeviceTts", "destroy|Enter!");
        IInterface dynamicDelegate = this.c.getDynamicDelegate();
        if (dynamicDelegate == null) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "destroy|delegate is null, return!");
            return;
        }
        try {
            if (dynamicDelegate instanceof IRemoteTtsDelegate) {
                ((IRemoteTtsDelegate) dynamicDelegate).destroy();
                this.a = false;
            }
        } catch (Exception e2) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "destroy|has exception: " + e2);
        }
    }

    public synchronized void a(Context context) {
        this.c.initial(context);
        AvailableAdapterManager.getInstance().notifyDownloadIfNeeded(context, this.c);
    }

    public synchronized void a(Context context, TtsTextParcel ttsTextParcel) {
        if (!AvailableAdapterManager.getInstance().isAvailable(context, this.c)) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "detectFromRemote|APK is not available, return!");
            return;
        }
        if (!this.a) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "has not initialized");
            return;
        }
        IInterface dynamicDelegate = this.c.getDynamicDelegate();
        if (dynamicDelegate == null) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "detectFromRemote|delegate is null, return!");
            return;
        }
        try {
            if (dynamicDelegate instanceof IRemoteTtsDelegate) {
                ((IRemoteTtsDelegate) dynamicDelegate).startSynthesis(ttsTextParcel);
            }
        } catch (Exception e2) {
            SmartLog.e("Tts_SDK_RemoteOnDeviceTts", "startSynthesis|has exception: " + e2);
        }
    }
}
