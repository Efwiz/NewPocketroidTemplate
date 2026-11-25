package com.huawei.hms.mlsdk.t;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.mlkit.common.ha.HianalyticsLogProvider;
import com.huawei.hms.mlsdk.common.MLApplication;
import java.util.HashMap;
import java.util.Map;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: HaAdapter.java */
/* JADX INFO: loaded from: classes10.dex */
public class b {
    private boolean a = false;
    private Map<String, c> b = new HashMap();

    /* JADX INFO: compiled from: HaAdapter.java */
    private static class a {
        private static final b a = new b();
    }

    public static b b() {
        return a.a;
    }

    public void a(Context context) {
        if (this.a || HianalyticsLogProvider.getInstance().sdkForbiddenHiLog(context.getApplicationContext())) {
            return;
        }
        i0.c("HaAdapter", "Start ha module");
        HianalyticsLogProvider.getInstance().initTimer("MLKitTts");
        this.a = true;
    }

    public void b(String str) {
        if (TextUtils.isEmpty(str) || !this.a || !this.b.containsKey(str) || this.b.get(str) == null || this.b.get(str).b() <= 0) {
            return;
        }
        i0.c("HaAdapter", "Post Event [" + str + "]");
        HianalyticsLogProvider.getInstance().postEvent(MLApplication.getInstance().getAppContext(), 1, this.b.get(str));
    }

    public void a() {
        if (this.a) {
            i0.c("HaAdapter", "Stop ha module");
            HianalyticsLogProvider.getInstance().reportAndCancelTimer("MLKitTts");
            this.b.clear();
            this.a = false;
        }
    }

    public void a(String str) {
        if (TextUtils.isEmpty(str) || !this.a) {
            return;
        }
        this.b.put(str, new c());
    }

    public void a(String str, int i, Bundle bundle) {
        c cVar;
        if (TextUtils.isEmpty(str) || !this.a || !this.b.containsKey(str) || (cVar = this.b.get(str)) == null) {
        }
        switch (i) {
            case 0:
                cVar.c(bundle.getString("language"));
                String string = bundle.getString("voiceName");
                if (k0.a.containsKey(string)) {
                    cVar.d(k0.a.get(string));
                } else {
                    cVar.d(string);
                }
                cVar.b(bundle.getInt("textLen"));
                break;
            case 1:
                cVar.b(System.currentTimeMillis());
                break;
            case 2:
                cVar.a(System.currentTimeMillis());
                break;
            case 3:
                cVar.c((int) (System.currentTimeMillis() - cVar.a()));
                break;
            case 4:
                cVar.a(bundle.getInt("downloadSize"));
                break;
            case 5:
                cVar.a(bundle.getBoolean("result") ? "0" : SchemaSymbols.ATTVAL_TRUE_1);
                break;
            case 6:
                cVar.b(bundle.getString("errMsg"));
                break;
        }
    }
}
