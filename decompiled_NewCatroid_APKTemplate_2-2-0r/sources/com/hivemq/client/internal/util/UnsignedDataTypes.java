package com.hivemq.client.internal.util;

import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: loaded from: classes10.dex */
public final class UnsignedDataTypes {
    public static final long UNSIGNED_INT_MAX_VALUE = 4294967295L;
    public static final int UNSIGNED_SHORT_MAX_VALUE = 65535;

    public static boolean isUnsignedInt(long j) {
        return j >= 0 && j <= 4294967295L;
    }

    public static boolean isUnsignedShort(long j) {
        return j >= 0 && j <= WebSocketProtocol.PAYLOAD_SHORT_MAX;
    }

    private UnsignedDataTypes() {
    }
}
