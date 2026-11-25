package org.catrobat.catroid.utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import java.lang.ref.WeakReference;
import org.catrobat.catroid.transfers.MediaDownloadService;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.web.ProgressResponseBody;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/MediaDownloader.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class MediaDownloader {
    private WeakReference<WebViewActivity> webViewActivityWeakReference;

    public MediaDownloader(WebViewActivity webViewActivity) {
        this.webViewActivityWeakReference = new WeakReference<>(webViewActivity);
    }

    public void startDownload(WebViewActivity activity, String url, String mediaName, String filePath) {
        WebViewActivity webViewActivity = this.webViewActivityWeakReference.get();
        if (mediaName == null || webViewActivity == null) {
            return;
        }
        Intent intent = new Intent(activity, (Class<?>) MediaDownloadService.class);
        intent.putExtra("receiver", new DownloadMediaReceiver(new Handler()));
        intent.putExtra("url", url);
        intent.putExtra("path", filePath);
        webViewActivity.createProgressDialog(mediaName);
        webViewActivity.setResultIntent(webViewActivity.getResultIntent().putExtra(WebViewActivity.MEDIA_FILE_PATH, filePath));
        activity.startService(intent);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/MediaDownloader$DownloadMediaReceiver.class
      classes14.dex
     */
    private class DownloadMediaReceiver extends ResultReceiver {
        DownloadMediaReceiver(Handler handler) {
            super(handler);
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            WebViewActivity webViewActivity = (WebViewActivity) MediaDownloader.this.webViewActivityWeakReference.get();
            if (webViewActivity == null) {
                return;
            }
            if (resultCode != 101) {
                if (resultCode == 105) {
                    webViewActivity.dismissProgressDialog();
                }
            } else {
                long j = resultData.getLong(ProgressResponseBody.TAG_PROGRESS);
                if (resultData.getBoolean(ProgressResponseBody.TAG_ENDOFFILE)) {
                    j = 100;
                }
                webViewActivity.updateProgressDialog(j);
            }
        }
    }
}
