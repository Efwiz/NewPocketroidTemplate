package org.catrobat.catroid.transfers;

import android.os.AsyncTask;
import android.util.Log;
import org.catrobat.catroid.web.CatrobatServerCalls;
import org.catrobat.catroid.web.WebConnectionException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/CheckUserNameAvailableTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CheckUserNameAvailableTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "CheckUserNameAvailableTask";
    private OnCheckUserNameAvailableCompleteListener onCheckUserNameAvailableCompleteListener;
    private Boolean userNameAvailable;
    private String username;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/CheckUserNameAvailableTask$OnCheckUserNameAvailableCompleteListener.class
      classes14.dex
     */
    public interface OnCheckUserNameAvailableCompleteListener {
        void onCheckUserNameAvailableComplete(Boolean userNameAvailable, String username);
    }

    public CheckUserNameAvailableTask(String username) {
        this.username = username;
    }

    public void setOnCheckUserNameAvailableCompleteListener(OnCheckUserNameAvailableCompleteListener listener) {
        this.onCheckUserNameAvailableCompleteListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean doInBackground(Void... params) {
        try {
            this.userNameAvailable = Boolean.valueOf(new CatrobatServerCalls().isUserNameAvailable(this.username));
            return true;
        } catch (WebConnectionException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        OnCheckUserNameAvailableCompleteListener onCheckUserNameAvailableCompleteListener = this.onCheckUserNameAvailableCompleteListener;
        if (onCheckUserNameAvailableCompleteListener != null) {
            onCheckUserNameAvailableCompleteListener.onCheckUserNameAvailableComplete(this.userNameAvailable, this.username);
        }
    }
}
