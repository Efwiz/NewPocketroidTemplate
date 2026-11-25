package org.catrobat.catroid.transfers;

import android.os.AsyncTask;
import android.util.Log;
import org.catrobat.catroid.web.CatrobatServerCalls;
import org.catrobat.catroid.web.WebConnectionException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/CheckEmailAvailableTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CheckEmailAvailableTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "CheckEmailAvailableTask";
    private String email;
    private Boolean emailAvailable;
    private OnCheckEmailAvailableCompleteListener onCheckEmailAvailableCompleteListener;
    private String provider;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/CheckEmailAvailableTask$OnCheckEmailAvailableCompleteListener.class
      classes14.dex
     */
    public interface OnCheckEmailAvailableCompleteListener {
        void onCheckEmailAvailableComplete(Boolean emailAvailable, String provider);
    }

    public CheckEmailAvailableTask(String email, String provider) {
        this.email = email;
        this.provider = provider;
    }

    public void setOnCheckEmailAvailableCompleteListener(OnCheckEmailAvailableCompleteListener listener) {
        this.onCheckEmailAvailableCompleteListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean doInBackground(String... params) {
        try {
            this.emailAvailable = Boolean.valueOf(new CatrobatServerCalls().isEMailAvailable(this.email));
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
        OnCheckEmailAvailableCompleteListener onCheckEmailAvailableCompleteListener = this.onCheckEmailAvailableCompleteListener;
        if (onCheckEmailAvailableCompleteListener != null) {
            onCheckEmailAvailableCompleteListener.onCheckEmailAvailableComplete(this.emailAvailable, this.provider);
        }
    }
}
