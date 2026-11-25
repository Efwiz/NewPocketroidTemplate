package com.huawei.hms.mlsdk.t;

import java.io.IOException;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: TtsResponseAnalyzer.java */
/* JADX INFO: loaded from: classes10.dex */
public class o {
    private final Response a;

    public o(Response response) throws NullPointerException {
        response.getClass();
        i0.c("TtsResponseAnalyzer", "Response is " + response);
        this.a = response;
    }

    public s a() throws IOException {
        String strString = this.a.body().string();
        s sVar = new s();
        try {
            sVar.a(new JSONObject(strString));
            return sVar;
        } catch (JSONException unused) {
            return null;
        }
    }

    public boolean b() {
        return this.a.headers("Content-Type").toString().contains("application/octet-stream");
    }

    public u c() throws IOException {
        byte[] byteArray = this.a.body().getBodySource().readByteArray(8L);
        byte b = byteArray[0];
        int i = ((byteArray[1] & 255) << 14) + ((byteArray[2] & 255) << 6);
        byte b2 = byteArray[3];
        int i2 = ((byteArray[4] & 255) << 8) + (byteArray[5] & 255);
        int i3 = ((byteArray[6] & 255) << 8) + (byteArray[7] & 255);
        i0.c("TtsResponseAnalyzer", "The start offset is " + i2 + ", and the currentLength is " + i3);
        u uVar = new u((b >> 4) & 15, b & 15, i + ((b2 & 255) >> 2), b2 & 3);
        uVar.b(i2);
        uVar.a(i3);
        return uVar;
    }

    public byte[] a(int i) throws IOException {
        i0.c("TtsResponseAnalyzer", "Reply len: " + i);
        return this.a.body().getBodySource().readByteArray(i);
    }
}
