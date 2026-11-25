package org.catrobat.catroid.transfers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.CatrobatServerCalls;
import org.catrobat.catroid.web.WebConnectionException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/CheckOAuthTokenTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CheckOAuthTokenTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "CheckOAuthTokenTask";
    private Activity activity;
    private WebConnectionException exception;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private String f601id;
    private OnCheckOAuthTokenCompleteListener onCheckOAuthTokenCompleteListener;
    private ProgressDialog progressDialog;
    private String provider;
    private Boolean tokenAvailable;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/CheckOAuthTokenTask$OnCheckOAuthTokenCompleteListener.class
      classes14.dex
     */
    public interface OnCheckOAuthTokenCompleteListener {
        void onCheckOAuthTokenComplete(Boolean tokenAvailable, String provider);
    }

    public CheckOAuthTokenTask(Activity activity, String id2, String provider) {
        this.activity = activity;
        this.f601id = id2;
        this.provider = provider;
    }

    public void setOnCheckOAuthTokenCompleteListener(OnCheckOAuthTokenCompleteListener listener) {
        this.onCheckOAuthTokenCompleteListener = listener;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        super.onPreExecute();
        Activity activity = this.activity;
        if (activity == null) {
            return;
        }
        this.progressDialog = ProgressDialog.show(this.activity, activity.getString(R.string.please_wait), this.activity.getString(R.string.loading_check_oauth_token));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean doInBackground(String... params) {
        try {
            if (!Utils.isNetworkAvailable(this.activity)) {
                this.exception = new WebConnectionException(1002, "Network not available!");
                return false;
            }
            this.tokenAvailable = new CatrobatServerCalls().checkOAuthToken(this.f601id, this.provider, this.activity);
            return true;
        } catch (WebConnectionException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            this.exception = e;
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null && progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
        if (Utils.checkForNetworkError(this.exception)) {
            showDialog(R.string.error_internet_connection);
            return;
        }
        if (!success.booleanValue() && this.exception != null) {
            showDialog(R.string.sign_in_error);
            return;
        }
        OnCheckOAuthTokenCompleteListener onCheckOAuthTokenCompleteListener = this.onCheckOAuthTokenCompleteListener;
        if (onCheckOAuthTokenCompleteListener != null) {
            onCheckOAuthTokenCompleteListener.onCheckOAuthTokenComplete(this.tokenAvailable, this.provider);
        }
    }

    private void showDialog(int messageId) {
        if (this.activity == null) {
            return;
        }
        if (this.exception.getMessage() == null) {
            new AlertDialog.Builder(this.activity).setMessage(messageId).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).show();
        } else {
            new AlertDialog.Builder(this.activity).setMessage(this.exception.getMessage()).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).show();
        }
    }
}
