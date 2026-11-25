package com.huawei.hms.mlsdk.tts.download.utils;

import android.text.TextUtils;
import com.huawei.hms.mlkit.tts.common.TtsOptionsParcel;
import com.huawei.hms.mlsdk.t.a;
import com.huawei.hms.mlsdk.t.i0;
import com.huawei.hms.mlsdk.tts.client.b;
import com.huawei.hms.mlsdk.tts.client.c;

/* JADX INFO: loaded from: classes10.dex */
public class ModelLevelUtils {
    public static int getModelLevel(String str) {
        b bVarB;
        int iB = -1;
        try {
            if (TextUtils.isEmpty(str) || (bVarB = c.b(str)) == null) {
                return -1;
            }
            bVarB.a((TtsOptionsParcel) null);
            iB = bVarB.b();
            i0.c("ModelLevelUtils", "get model level==" + iB);
            return iB;
        } catch (RuntimeException e) {
            i0.b("ModelLevelUtils", a.a("getRemoteOnDeviceTts exception:").append(e.getMessage()).toString());
            return iB;
        }
    }
}
