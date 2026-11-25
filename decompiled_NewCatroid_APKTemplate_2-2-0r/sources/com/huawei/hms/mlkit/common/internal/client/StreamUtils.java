package com.huawei.hms.mlkit.common.internal.client;

import java.io.Closeable;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public final class StreamUtils {
    private StreamUtils() {
    }

    public static void closeStreams(Closeable... closeableArr) {
        if (closeableArr == null) {
            return;
        }
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException unused) {
                    com.huawei.hms.ml.common.base.SmartLog.w("StreamUtils", "IOException closeStreams");
                } catch (RuntimeException unused2) {
                    com.huawei.hms.ml.common.base.SmartLog.w("StreamUtils", "RuntimeException closeStreams");
                }
            }
        }
    }
}
