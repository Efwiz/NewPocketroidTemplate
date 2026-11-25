package com.huawei.hms.mlsdk.model.download.p;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.common.utils.SmartLog;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: OkHttpDownloadThread.java */
/* JADX INFO: loaded from: classes10.dex */
public class h extends Thread {
    private final i a;
    private final int b;
    private final long c;
    private final OkHttpClient d;
    private long e = 0;

    public h(OkHttpClient okHttpClient, i iVar, int i, long j) {
        this.d = okHttpClient;
        this.a = iVar;
        this.b = i;
        this.c = j;
    }

    private boolean a(Response response) {
        int i;
        this.e = 0L;
        ResponseBody responseBodyBody = response.body();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.a.b(), "rwd");
            long jA = a();
            if (jA != 0) {
                randomAccessFile.seek(jA);
            }
            InputStream inputStreamByteStream = responseBodyBody.byteStream();
            byte[] bArr = new byte[2048];
            while (!this.a.c() && (i = inputStreamByteStream.read(bArr)) != -1) {
                try {
                    try {
                        randomAccessFile.write(bArr, 0, i);
                        long j = i;
                        this.e += j;
                        this.a.a(j);
                    } catch (IOException unused) {
                        SmartLog.w("MLSDK_MODEL_OkHttpDownloadThread", "ThreadID: " + this.b + " download filed, already download: " + this.e);
                        try {
                            randomAccessFile.close();
                            inputStreamByteStream.close();
                            response.close();
                        } catch (IOException unused2) {
                            SmartLog.e("MLSDK_MODEL_OkHttpDownloadThread", "downloadFileImpl close failed");
                        }
                        return false;
                    }
                } catch (Throwable th) {
                    try {
                        randomAccessFile.close();
                        inputStreamByteStream.close();
                        response.close();
                    } catch (IOException unused3) {
                        SmartLog.e("MLSDK_MODEL_OkHttpDownloadThread", "downloadFileImpl close failed");
                    }
                    throw th;
                }
            }
            try {
                randomAccessFile.close();
                inputStreamByteStream.close();
                response.close();
            } catch (IOException unused4) {
                SmartLog.e("MLSDK_MODEL_OkHttpDownloadThread", "downloadFileImpl close failed");
            }
            SmartLog.d("MLSDK_MODEL_OkHttpDownloadThread", a.a("Thread ").append(this.b).append(" download complete ").append(this.e).append(" in ").append(this.c).toString());
            return true;
        } catch (IOException unused5) {
            SmartLog.e("MLSDK_MODEL_OkHttpDownloadThread", "downloadFileImpl create RandomAccessFile failed");
            return false;
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        long jA = a();
        long j = (this.c * ((long) this.b)) - 1;
        SmartLog.d("MLSDK_MODEL_OkHttpDownloadThread", a.a("thread ").append(this.b).append(" end position:").append(j).toString());
        String strA = this.a.a();
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (i >= 3) {
                this.a.a(new Exception("OkHttpDownloadThread failed"));
                return;
            }
            try {
                Response responseExecute = this.d.newCall(new Request.Builder().url(strA).header("RANGE", "bytes=" + jA + Parameters.DEFAULT_OPTION_PREFIXES + j).get().build()).execute();
                if (responseExecute.code() != 206) {
                    this.a.a(new Exception("OkHttpDownloadThread failed"));
                    return;
                } else {
                    if (a(responseExecute)) {
                        return;
                    }
                    this.a.a(this.e * (-1));
                    SmartLog.w("MLSDK_MODEL_OkHttpDownloadThread", "ThreadId: " + this.b + " download failed, retry " + i2 + " times");
                    i = i2;
                }
            } catch (IOException unused) {
                this.a.a(new Exception("OkHttpDownloadThread failed"));
                return;
            }
        }
    }

    private long a() {
        long j = this.c * ((long) (this.b - 1));
        SmartLog.d("MLSDK_MODEL_OkHttpDownloadThread", a.a("thread ").append(this.b).append(" start position:").append(j).toString());
        return j;
    }
}
