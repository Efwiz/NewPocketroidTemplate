package com.huawei.hms.mlsdk.t;

import com.huawei.hms.ml.common.utils.SmartLog;
import java.io.IOException;

/* JADX INFO: compiled from: outline */
/* JADX INFO: loaded from: classes10.dex */
public class a {
    public static StringBuilder a(String str) {
        return new StringBuilder().append(str);
    }

    public static StringBuilder a(StringBuilder sb, String str, char c, String str2) {
        return sb.append(str).append(c).append(str2);
    }

    public static void a(IOException iOException, StringBuilder sb, String str) {
        SmartLog.e(str, sb.append(iOException.getMessage()).toString());
    }
}
