package com.hivemq.client.internal.util;

/* JADX INFO: loaded from: classes10.dex */
public final class ClassUtil {
    public static boolean isAvailable(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private ClassUtil() {
    }
}
