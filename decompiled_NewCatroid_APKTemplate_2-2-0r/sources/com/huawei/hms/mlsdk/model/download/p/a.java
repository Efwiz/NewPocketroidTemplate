package com.huawei.hms.mlsdk.model.download.p;

import com.huawei.hms.ml.common.utils.SmartLog;
import java.io.IOException;

/* JADX INFO: compiled from: outline */
/* JADX INFO: loaded from: classes10.dex */
public class a {
    public static StringBuilder a(String str) {
        return new StringBuilder().append(str);
    }

    public static void a(IOException iOException, StringBuilder sb, String str) {
        SmartLog.e(str, sb.append(iOException.getMessage()).toString());
    }
}
