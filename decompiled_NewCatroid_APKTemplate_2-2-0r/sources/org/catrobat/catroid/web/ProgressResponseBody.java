package org.catrobat.catroid.web;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ProgressResponseBody.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ProgressResponseBody extends ResponseBody {
    public static final String TAG_ENDOFFILE = "endOfFileReached";
    public static final String TAG_PROGRESS = "currentDownloadProgress";
    private BufferedSource bufferedSource;
    private CatrobatServerCalls.DownloadProgressCallback progressCallback;
    private final ResponseBody responseBody;

    ProgressResponseBody(ResponseBody responseBody, CatrobatServerCalls.DownloadProgressCallback progressCallback) {
        this.responseBody = responseBody;
        this.progressCallback = progressCallback;
    }

    @Override // okhttp3.ResponseBody
    /* JADX INFO: renamed from: contentType */
    public MediaType get$contentType() {
        return this.responseBody.get$contentType();
    }

    @Override // okhttp3.ResponseBody
    /* JADX INFO: renamed from: contentLength */
    public long getContentLength() {
        return this.responseBody.getContentLength();
    }

    @Override // okhttp3.ResponseBody
    /* JADX INFO: renamed from: source */
    public BufferedSource getSource() {
        if (this.bufferedSource == null) {
            this.bufferedSource = Okio.buffer(source(this.responseBody.getSource()));
        }
        return this.bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) { // from class: org.catrobat.catroid.web.ProgressResponseBody.1
            long totalBytesRead = 0;
            long lastProgress = -1;

            @Override // okio.ForwardingSource, okio.Source
            public long read(Buffer sink, long byteCount) throws IOException {
                long j = super.read(sink, byteCount);
                long j2 = this.totalBytesRead + (j != -1 ? j : 0L);
                this.totalBytesRead = j2;
                long j3 = (j2 * 100) / ProgressResponseBody.this.getContentLength();
                boolean z = j == -1;
                if (j3 > this.lastProgress || z) {
                    ProgressResponseBody.this.sendUpdateIntent(j3);
                    this.lastProgress = j3;
                }
                return j;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdateIntent(long progress) {
        this.progressCallback.onProgress(progress);
    }
}
