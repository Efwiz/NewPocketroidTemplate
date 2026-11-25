package com.huawei.hms.mlsdk.langdetect.p;

import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlkit.common.ha.HianalyticsLogProvider;
import com.huawei.hms.mlsdk.common.MLApplication;
import java.util.HashMap;
import java.util.Map;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: DetectionHaAdapter.java */
/* JADX INFO: loaded from: classes10.dex */
public class b {
    private Map<String, com.huawei.hms.mlsdk.langdetect.p.a> a = new HashMap();

    /* JADX INFO: compiled from: DetectionHaAdapter.java */
    private static class a {
        private static final b a = new b();
    }

    public static b a() {
        return a.a;
    }

    public void b(String str) {
        if (TextUtils.isEmpty(str) || !this.a.containsKey(str)) {
            return;
        }
        SmartLog.i(JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "Post Event [" + str + "]");
        HianalyticsLogProvider.getInstance().postEvent(MLApplication.getInstance().getAppContext(), 1, this.a.get(str));
    }

    public void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.a.put(str, new com.huawei.hms.mlsdk.langdetect.p.a());
    }

    public void a(String str, int i, Bundle bundle) {
        if (TextUtils.isEmpty(str) || !this.a.containsKey(str)) {
            return;
        }
        com.huawei.hms.mlsdk.langdetect.p.a aVar = this.a.get(str);
        if (i == 0) {
            aVar.a(bundle.getString("language"));
            aVar.a(bundle.getInt("charNum"));
            return;
        }
        if (i == 1) {
            aVar.b(System.currentTimeMillis());
            return;
        }
        if (i == 2) {
            aVar.a(System.currentTimeMillis());
            return;
        }
        if (i == 3) {
            aVar.b((int) (System.currentTimeMillis() - aVar.a()));
        } else if (i == 4) {
            aVar.b(bundle.getBoolean("result") ? "0" : SchemaSymbols.ATTVAL_TRUE_1);
        } else {
            if (i != 5) {
                return;
            }
            aVar.c(bundle.getString("errMsg"));
        }
    }
}
