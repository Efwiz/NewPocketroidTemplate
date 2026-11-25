package com.huawei.hms.core.aidl;

/* JADX INFO: loaded from: classes10.dex */
public final class CodecLookup {
    private CodecLookup() {
    }

    public static MessageCodec find(int i) {
        return i == 2 ? new a() : new MessageCodec();
    }
}
