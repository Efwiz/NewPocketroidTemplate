package org.catrobat.catroid.transfers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.CatrobatWebClient;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebConnectionException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/GoogleExchangeCodeTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GoogleExchangeCodeTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "GoogleExchangeCodeTask";
    private String code;
    private Context context;
    private WebConnectionException exception;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private String f602id;
    private final String idToken;
    private String locale;
    private String mail;
    private String message;
    private OnGoogleExchangeCodeCompleteListener onGoogleExchangeCodeCompleteListener;
    private ProgressDialog progressDialog;
    private boolean tokenExchanged;
    private String username;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/GoogleExchangeCodeTask$OnGoogleExchangeCodeCompleteListener.class
      classes14.dex
     */
    public interface OnGoogleExchangeCodeCompleteListener {
        void onGoogleExchangeCodeComplete();
    }

    public GoogleExchangeCodeTask(Activity activity, String code, String mail, String username, String id2, String locale, String idToken) {
        this.code = code;
        this.context = activity;
        this.mail = mail;
        this.username = username;
        this.f602id = id2;
        this.locale = locale;
        this.idToken = idToken;
    }

    public void setOnGoogleExchangeCodeCompleteListener(OnGoogleExchangeCodeCompleteListener listener) {
        this.onGoogleExchangeCodeCompleteListener = listener;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = this.context;
        if (context == null) {
            return;
        }
        this.progressDialog = ProgressDialog.show(this.context, context.getString(R.string.please_wait), this.context.getString(R.string.loading_google_exchange_code));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean doInBackground(Void... arg0) {
        try {
            if (!Utils.isNetworkAvailable(this.context)) {
                this.exception = new WebConnectionException(1002, "Network not available!");
                return false;
            }
            this.tokenExchanged = new ServerCalls(CatrobatWebClient.INSTANCE.getClient()).googleExchangeCode(this.code, this.f602id, this.username, this.mail, this.locale, this.idToken);
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
        super.onPostExecute(success);
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null && progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
        if (Utils.checkForNetworkError(this.exception)) {
            showDialog(R.string.error_internet_connection);
            return;
        }
        if ((!success.booleanValue() && this.exception != null) || !this.tokenExchanged) {
            showDialog(R.string.sign_in_error);
            return;
        }
        OnGoogleExchangeCodeCompleteListener onGoogleExchangeCodeCompleteListener = this.onGoogleExchangeCodeCompleteListener;
        if (onGoogleExchangeCodeCompleteListener != null) {
            onGoogleExchangeCodeCompleteListener.onGoogleExchangeCodeComplete();
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
