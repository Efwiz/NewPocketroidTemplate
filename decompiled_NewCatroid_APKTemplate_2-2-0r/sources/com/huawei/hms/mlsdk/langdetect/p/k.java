package com.huawei.hms.mlsdk.langdetect.p;

import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.ml.language.common.utils.ExceptionUtils;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: RestClientManager.java */
/* JADX INFO: loaded from: classes10.dex */
public class k {
    private static k a;
    private static Map<String, i> b = new HashMap();

    private k() {
    }

    public static k a() {
        k kVar;
        synchronized (k.class) {
            if (a == null) {
                a = new k();
            }
            kVar = a;
        }
        return kVar;
    }

    public i a(String str) throws MLException {
        m mVar = new m(MLApplication.getInstance(), null);
        String str2 = m.class.getName() + "." + str;
        if (b.containsKey(str2)) {
            return b.get(str2);
        }
        try {
            i iVarA = mVar.a(str);
            a(str2, iVarA);
            return iVarA;
        } catch (MLException e) {
            SmartLog.e("com.huawei.hms.mlsdk.langdetect.p.k", "Failure to get rest client");
            throw ExceptionUtils.getMlException(e);
        }
    }

    private void a(String str, i iVar) {
        synchronized (b) {
            b.remove(str);
            b.put(str, iVar);
        }
    }
}
