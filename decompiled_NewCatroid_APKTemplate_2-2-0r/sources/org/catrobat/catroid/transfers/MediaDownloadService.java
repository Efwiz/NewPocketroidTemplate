package org.catrobat.catroid.transfers;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import java.io.IOException;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.web.CatrobatWebClient;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebConnectionException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/MediaDownloadService.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MediaDownloadService extends IntentService {
    public static final String MEDIA_FILE_PATH = "path";
    public static final String RECEIVER_TAG = "receiver";
    public static final String TAG = "MediaDownloadService";
    public static final String URL_TAG = "url";
    private Handler handler;
    public ResultReceiver receiver;

    public MediaDownloadService() {
        super("MediaDownloadService");
    }

    @Override // android.app.IntentService, android.app.Service
    public void onCreate() {
        super.onCreate();
        this.handler = new Handler();
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        boolean z;
        String stringExtra = intent.getStringExtra("url");
        String stringExtra2 = intent.getStringExtra("path");
        this.receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        int i = R.string.error_unknown_error;
        try {
            new ServerCalls(CatrobatWebClient.INSTANCE.getClient()).downloadMedia(stringExtra, stringExtra2, this.receiver);
            z = true;
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            this.receiver.send(105, null);
            z = false;
        } catch (WebConnectionException e2) {
            Log.e(TAG, Log.getStackTraceString(e2));
            this.receiver.send(105, null);
            i = R.string.error_internet_connection;
            z = false;
        }
        if (!z) {
            showToast(i, true);
        } else {
            showToast(R.string.notification_download_finished, false);
        }
    }

    private void showToast(final int messageId, boolean error) {
        if (error) {
            this.handler.post(new Runnable() { // from class: org.catrobat.catroid.transfers.MediaDownloadService.1
                @Override // java.lang.Runnable
                public void run() {
                    ToastUtil.showError(MediaDownloadService.this.getBaseContext(), messageId);
                }
            });
        } else {
            this.handler.post(new Runnable() { // from class: org.catrobat.catroid.transfers.MediaDownloadService.2
                @Override // java.lang.Runnable
                public void run() {
                    ToastUtil.showSuccess(MediaDownloadService.this.getBaseContext(), messageId);
                }
            });
        }
    }
}
