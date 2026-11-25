package org.catrobat.catroid.transfers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.CatrobatWebClient;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebConnectionException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/GoogleLogInTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GoogleLogInTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "GoogleLogInTask";
    private Context context;
    private WebConnectionException exception;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private String f603id;
    private String locale;
    private String mail;
    private String message;
    private OnGoogleServerLogInCompleteListener onGoogleServerLogInCompleteListener;
    private ProgressDialog progressDialog;
    private boolean userSignedIn;
    private String username;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/GoogleLogInTask$OnGoogleServerLogInCompleteListener.class
      classes14.dex
     */
    public interface OnGoogleServerLogInCompleteListener {
        void onGoogleServerLogInComplete();
    }

    public GoogleLogInTask(Activity activity, String mail, String username, String id2, String locale) {
        this.context = activity;
        this.mail = mail;
        this.username = username;
        this.f603id = id2;
        this.locale = locale;
    }

    public void setOnGoogleServerLogInCompleteListener(OnGoogleServerLogInCompleteListener listener) {
        this.onGoogleServerLogInCompleteListener = listener;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = this.context;
        if (context == null) {
            return;
        }
        this.progressDialog = ProgressDialog.show(this.context, context.getString(R.string.please_wait), this.context.getString(R.string.loading_google_login));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean doInBackground(Void... arg0) {
        try {
            if (!Utils.isNetworkAvailable(this.context)) {
                this.exception = new WebConnectionException(1002, "Network not available!");
                return false;
            }
            this.userSignedIn = new ServerCalls(CatrobatWebClient.INSTANCE.getClient()).googleLogin(this.mail, this.username, this.f603id, this.locale, this.context);
            return true;
        } catch (WebConnectionException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            this.message = e.getMessage();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Boolean success) {
        boolean z;
        super.onPostExecute(success);
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null && progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
        if (Utils.checkForNetworkError(this.exception)) {
            showDialog(R.string.error_internet_connection);
            return;
        }
        if ((!success.booleanValue() && this.exception != null) || !(z = this.userSignedIn)) {
            showDialog(R.string.sign_in_error);
            return;
        }
        if (z) {
            ToastUtil.showSuccess(this.context, R.string.user_logged_in);
        }
        OnGoogleServerLogInCompleteListener onGoogleServerLogInCompleteListener = this.onGoogleServerLogInCompleteListener;
        if (onGoogleServerLogInCompleteListener != null) {
            onGoogleServerLogInCompleteListener.onGoogleServerLogInComplete();
        }
    }

    private void showDialog(int messageId) {
        if (this.context == null) {
            return;
        }
        if (this.message == null) {
            new AlertDialog.Builder(this.context).setTitle(R.string.register_error).setMessage(messageId).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).show();
        } else {
            new AlertDialog.Builder(this.context).setTitle(R.string.register_error).setMessage(this.message).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).show();
        }
    }
}
