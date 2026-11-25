package org.catrobat.catroid.transfers;

import android.os.AsyncTask;
import android.util.Log;
import org.catrobat.catroid.web.CatrobatServerCalls;
import org.catrobat.catroid.web.WebConnectionException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/CheckTokenTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CheckTokenTask extends AsyncTask<String, Void, Boolean[]> {
    private static final String TAG = "CheckTokenTask";
    private TokenCheckListener onCheckTokenCompleteListener;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/CheckTokenTask$TokenCheckListener.class
      classes14.dex
     */
    public interface TokenCheckListener {
        void onTokenCheckComplete(boolean tokenValid, boolean connectionFailed);
    }

    public CheckTokenTask(TokenCheckListener onCheckTokenCompleteListener) {
        this.onCheckTokenCompleteListener = onCheckTokenCompleteListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean[] doInBackground(String... arg0) {
        try {
            return new Boolean[]{Boolean.valueOf(new CatrobatServerCalls().checkToken(arg0[0], arg0[1], "https://t.me/NewCatroidDevs")), false};
        } catch (WebConnectionException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return new Boolean[]{false, true};
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Boolean[] b) {
        this.onCheckTokenCompleteListener.onTokenCheckComplete(b[0].booleanValue(), b[1].booleanValue());
    }
}
