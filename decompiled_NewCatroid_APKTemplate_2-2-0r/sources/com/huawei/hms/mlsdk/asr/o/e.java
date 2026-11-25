package com.huawei.hms.mlsdk.asr.o;

import okhttp3.Response;
import okio.ByteString;

/* JADX INFO: compiled from: IWsMessageListener.java */
/* JADX INFO: loaded from: classes10.dex */
public interface e {
    void a();

    void a(int i, String str);

    void a(String str);

    void a(Throwable th, Response response);

    void a(Response response);

    void a(ByteString byteString);

    void b(int i, String str);
}
