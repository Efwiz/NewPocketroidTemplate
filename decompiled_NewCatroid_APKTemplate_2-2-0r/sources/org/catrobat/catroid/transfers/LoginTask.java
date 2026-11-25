package org.catrobat.catroid.transfers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import java.lang.ref.WeakReference;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.CatrobatWebClient;
import org.catrobat.catroid.web.ServerAuthenticator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/LoginTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LoginTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "LoginTask";
    private WeakReference<Context> contextWeakReference;
    private String message;
    private OnLoginListener onLoginListener;
    private String password;
    private ProgressDialog progressDialog;
    private boolean userLoggedIn = false;
    private String username;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/LoginTask$OnLoginListener.class
      classes14.dex
     */
    public interface OnLoginListener {
        void onLoginComplete();

        void onLoginFailed(String msg);
    }

    public LoginTask(Context activity, String username, String password) {
        this.contextWeakReference = new WeakReference<>(activity);
        this.username = username;
        this.password = password;
    }

    public void setOnLoginListener(OnLoginListener listener) {
        this.onLoginListener = listener;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        Context context = this.contextWeakReference.get();
        if (context == null) {
            return;
        }
        this.progressDialog = ProgressDialog.show(context, context.getString(R.string.please_wait), context.getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Void doInBackground(Void... arg0) {
        Context context = this.contextWeakReference.get();
        if (context == null) {
            return null;
        }
        if (!Utils.checkIsNetworkAvailableAndShowErrorMessage(context)) {
            this.userLoggedIn = false;
            return null;
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String string = defaultSharedPreferences.getString("token", Constants.NO_TOKEN);
        Log.d(TAG, string);
        new ServerAuthenticator(this.username, this.password, string, CatrobatWebClient.INSTANCE.getClient(), "https://t.me/NewCatroidDevs", defaultSharedPreferences, new ServerAuthenticator.TaskListener() { // from class: org.catrobat.catroid.transfers.LoginTask.1
            @Override // org.catrobat.catroid.web.ServerAuthenticator.TaskListener
            public void onError(int statusCode, String errorMessage) {
                Log.e(LoginTask.TAG, "StatusCode: " + statusCode + errorMessage);
                LoginTask.this.message = errorMessage;
                LoginTask.this.userLoggedIn = false;
            }

            @Override // org.catrobat.catroid.web.ServerAuthenticator.TaskListener
            public void onSuccess() {
                LoginTask.this.userLoggedIn = true;
            }
        }).performCatrobatLogin();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Void arg) {
        Context context = this.contextWeakReference.get();
        if (context == null) {
            return;
        }
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null && progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
        if (this.userLoggedIn) {
            ToastUtil.showSuccess(context, R.string.user_logged_in);
            OnLoginListener onLoginListener = this.onLoginListener;
            if (onLoginListener != null) {
                onLoginListener.onLoginComplete();
                return;
            }
            return;
        }
        if (this.message == null) {
            this.message = context.getString(R.string.sign_in_error);
        }
        this.onLoginListener.onLoginFailed(this.message);
        Log.e(TAG, this.message);
    }
}
