package com.huawei.hms.mlsdk.model.download.p;

import com.huawei.hms.ml.common.utils.SmartLog;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: OkHttpDownloader.java */
/* JADX INFO: loaded from: classes10.dex */
public class i {
    private static boolean i = false;
    private static int j;
    private String c;
    private String f;
    private File g;
    private f h;
    private boolean a = false;
    private boolean b = false;
    private long d = 0;
    private long e = 0;

    public i(String str, String str2, File file, f fVar) {
        this.c = str;
        this.f = str2;
        this.g = file;
        this.h = fVar;
    }

    public void a(OkHttpClient okHttpClient, int i2) {
        int i3 = 0;
        if (i2 == 1) {
            this.a = true;
            int i4 = 0;
            while (true) {
                int i5 = i4 + 1;
                if (i4 >= 3) {
                    b(new Exception("singleThreadDownloadImpl failed"));
                    break;
                }
                try {
                    Response responseExecute = okHttpClient.newCall(new Request.Builder().url(this.f).build()).execute();
                    if (responseExecute.code() != 200) {
                        b(new Exception("singleThreadDownload failed"));
                        return;
                    } else {
                        if (a(responseExecute)) {
                            break;
                        }
                        a(0L, this.e);
                        SmartLog.w("MLSDK_MODEL_OkHttpDownloader", "download failed, retry " + i5 + " times");
                        i4 = i5;
                    }
                } catch (IOException unused) {
                    b(new Exception("singleThreadDownload failed"));
                    return;
                }
            }
            this.a = false;
            return;
        }
        if (i2 != 2) {
            SmartLog.w("MLSDK_MODEL_OkHttpDownloader", "Unknown download type");
            return;
        }
        this.a = true;
        try {
            Response responseExecute2 = okHttpClient.newCall(new Request.Builder().url(this.f).get().build()).execute();
            if (responseExecute2.code() != 200) {
                SmartLog.e("MLSDK_MODEL_OkHttpDownloader", "multiThreadDownload response: " + responseExecute2.toString());
                b(new Exception("multiThreadDownload failed"));
                return;
            }
            try {
                ResponseBody responseBodyBody = responseExecute2.body();
                if (responseBodyBody.contentLength() <= 0) {
                    b(new Exception("Unknown file size"));
                    return;
                }
                RandomAccessFile randomAccessFile = new RandomAccessFile(this.g, "rwd");
                if (this.e == 0) {
                    long jContentLength = responseBodyBody.contentLength();
                    this.e = jContentLength;
                    randomAccessFile.setLength(jContentLength);
                }
                randomAccessFile.close();
                long j2 = this.e;
                long j3 = j2 / 3;
                if (j2 % 3 != 0) {
                    j3++;
                }
                while (i3 < 3 && ((long) i3) * j3 < this.e) {
                    int i6 = i3 + 1;
                    new h(okHttpClient, this, i6, j3).start();
                    i3 = i6;
                }
            } catch (IOException e) {
                b(e);
            }
        } catch (IOException e2) {
            SmartLog.e("MLSDK_MODEL_OkHttpDownloader", a.a("multiThreadDownload failed: ").append(e2.getMessage()).toString());
            b(new Exception("multiThreadDownload failed"));
        }
    }

    public File b() {
        return this.g;
    }

    public synchronized boolean c() {
        return this.b;
    }

    public boolean d() {
        return this.a;
    }

    private void b(Exception exc) {
        this.a = false;
        f fVar = this.h;
        if (fVar != null) {
            fVar.a(this.c, exc);
        }
    }

    public String a() {
        return this.f;
    }

    public synchronized void a(long j2) {
        long j3 = this.d + j2;
        this.d = j3;
        a(j3, this.e);
        if (this.d >= this.e) {
            f fVar = this.h;
            if (fVar != null) {
                fVar.a(this.c, this.g);
            }
            this.a = false;
        }
    }

    public synchronized void a(Exception exc) {
        if (!this.b) {
            this.b = true;
            b(exc);
        }
    }

    private boolean a(Response response) {
        f fVar;
        this.d = 0L;
        ResponseBody responseBodyBody = response.body();
        if (responseBodyBody == null) {
            SmartLog.e("MLSDK_MODEL_OkHttpDownloader", "singleThreadDownloadImpl responseBody is null");
            return false;
        }
        try {
            this.e = responseBodyBody.contentLength();
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.g, "rwd");
            randomAccessFile.setLength(this.e);
            randomAccessFile.seek(this.d);
            byte[] bArr = new byte[2048];
            InputStream inputStreamByteStream = responseBodyBody.byteStream();
            while (true) {
                try {
                    try {
                        try {
                            int i2 = inputStreamByteStream.read(bArr);
                            if (i2 != -1) {
                                randomAccessFile.write(bArr, 0, i2);
                                long j2 = this.d + ((long) i2);
                                this.d = j2;
                                a(j2, this.e);
                            } else {
                                try {
                                    break;
                                } catch (IOException unused) {
                                    SmartLog.e("MLSDK_MODEL_OkHttpDownloader", "singleThreadDownloadImpl close failed");
                                }
                            }
                        } catch (IOException e) {
                            SmartLog.e("MLSDK_MODEL_OkHttpDownloader", "singleThreadDownloadImpl InputStream read failed: " + e.getMessage());
                            try {
                                randomAccessFile.close();
                                inputStreamByteStream.close();
                                response.close();
                            } catch (IOException unused2) {
                                SmartLog.e("MLSDK_MODEL_OkHttpDownloader", "singleThreadDownloadImpl close failed");
                            }
                            return false;
                        }
                    } catch (Throwable th) {
                        randomAccessFile.close();
                        inputStreamByteStream.close();
                        response.close();
                        throw th;
                    }
                    randomAccessFile.close();
                    inputStreamByteStream.close();
                    response.close();
                } catch (IOException unused3) {
                    SmartLog.e("MLSDK_MODEL_OkHttpDownloader", "singleThreadDownloadImpl close failed");
                }
                throw th;
            }
            randomAccessFile.close();
            inputStreamByteStream.close();
            response.close();
            if (this.d < this.e || (fVar = this.h) == null) {
                return true;
            }
            fVar.a(this.c, this.g);
            return true;
        } catch (IOException unused4) {
            SmartLog.e("MLSDK_MODEL_OkHttpDownloader", "singleThreadDownloadImpl create RandomAccessFile failed");
            return false;
        }
    }

    private void a(long j2, long j3) {
        int i2 = (int) ((100 * j2) / j3);
        if (i2 != j) {
            if (i) {
                SmartLog.i("MLSDK_MODEL_OkHttpDownloader", a.a("model(").append(this.c).append(") already download: ").append(i2).append("%").toString());
                f fVar = this.h;
                if (fVar != null) {
                    fVar.a(j2, j3);
                }
                i = false;
            }
        } else {
            i = true;
        }
        j = i2;
    }
}
