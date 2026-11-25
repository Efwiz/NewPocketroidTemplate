package com.hivemq.client.internal.util;

/* JADX INFO: loaded from: classes10.dex */
public final class StringUtil {
    public static String prepend(String str, String str2) {
        return str2.isEmpty() ? "" : str + str2;
    }

    private StringUtil() {
    }
}
