package com.huawei.hms.mlsdk.langdetect.p;

import android.content.Context;
import android.os.Bundle;
import android.os.IInterface;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectDelegate;
import com.huawei.hms.ml.language.common.langdetect.LangDetectFrameParcel;
import com.huawei.hms.ml.language.common.langdetect.LangDetectOptionsParcel;
import com.huawei.hms.ml.language.common.langdetect.LangDetectParcel;
import com.huawei.hms.mlsdk.internal.client.adapter.AvailableAdapterManager;
import java.util.List;

/* JADX INFO: compiled from: RemoteLanguageDecoder.java */
/* JADX INFO: loaded from: classes10.dex */
public class f {
    private boolean a = false;

    /* JADX INFO: compiled from: RemoteLanguageDecoder.java */
    private static final class b {
        static final f a = new f(null);
    }

    /* synthetic */ f(a aVar) {
    }

    public static f b() {
        return b.a;
    }

    public synchronized int a() {
        try {
            IInterface dynamicDelegate = e.a().getDynamicDelegate();
            if (dynamicDelegate == null) {
                return -1;
            }
            try {
                return ((IRemoteLangDetectDelegate) dynamicDelegate).destroy();
            } catch (Exception e) {
                SmartLog.d("RemoteLanguageDecoder", "destroy Exception e: " + e);
                return -1;
            } catch (Throwable th) {
                SmartLog.d("RemoteLanguageDecoder", "destroy Throwable e: " + th);
                return -1;
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public synchronized void b(Context context) {
        if (this.a) {
            a();
            this.a = false;
        }
        AvailableAdapterManager.getInstance().release(context);
        e.a().release();
    }

    public synchronized int a(LangDetectOptionsParcel langDetectOptionsParcel) {
        while (true) {
            try {
                e eVarA = e.a();
                IInterface dynamicDelegate = eVarA.getDynamicDelegate();
                if (dynamicDelegate == null) {
                    return -1;
                }
                try {
                    int iInitial = ((IRemoteLangDetectDelegate) dynamicDelegate).initial(ObjectWrapper.wrap(eVarA.getDynamicContext()), langDetectOptionsParcel);
                    if (iInitial >= 0) {
                        this.a = true;
                        return iInitial;
                    }
                } catch (Exception e) {
                    SmartLog.e("RemoteLanguageDecoder", "initial Exception e: " + e);
                } catch (Throwable th) {
                    SmartLog.e("RemoteLanguageDecoder", "initial Throwable e: " + th);
                }
                boolean z = !z && eVarA.switchDynamicContext();
                return -1;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public synchronized void a(Context context) {
        e.a().initial(context);
        AvailableAdapterManager.getInstance().notifyDownloadIfNeeded(context, e.a());
    }

    public synchronized List<LangDetectParcel> a(Context context, Bundle bundle, LangDetectFrameParcel langDetectFrameParcel, LangDetectOptionsParcel langDetectOptionsParcel) {
        if (!AvailableAdapterManager.getInstance().isAvailable(context, e.a())) {
            return null;
        }
        if (!this.a && a(langDetectOptionsParcel) >= 0) {
            this.a = true;
        }
        if (!this.a) {
            return null;
        }
        IInterface dynamicDelegate = e.a().getDynamicDelegate();
        if (dynamicDelegate == null) {
            return null;
        }
        try {
            return ((IRemoteLangDetectDelegate) dynamicDelegate).detect(bundle, langDetectFrameParcel, langDetectOptionsParcel);
        } catch (Exception e) {
            SmartLog.d("RemoteLanguageDecoder", "detect Exception  e: " + e);
            return null;
        }
    }
}
