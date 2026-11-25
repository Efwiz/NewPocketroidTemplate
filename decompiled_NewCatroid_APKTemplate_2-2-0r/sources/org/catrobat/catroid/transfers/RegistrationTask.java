package org.catrobat.catroid.transfers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import java.lang.ref.WeakReference;
import java.util.Locale;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.utils.DeviceSettingsProvider;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.CatrobatWebClient;
import org.catrobat.catroid.web.ServerAuthenticator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/RegistrationTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RegistrationTask extends AsyncTask<Void, Void, Void> {
    private final WeakReference<Context> contextWeakReference;
    private String email;
    private String message;
    private OnRegistrationListener onRegistrationListener;
    private String password;
    private ProgressDialog progressDialog;
    private boolean userRegistered = false;
    private String username;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/RegistrationTask$OnRegistrationListener.class
      classes14.dex
     */
    public interface OnRegistrationListener {
        void onRegistrationComplete();

        void onRegistrationFailed(String msg);
    }

    public RegistrationTask(Context context, String username, String password, String email) {
        this.contextWeakReference = new WeakReference<>(context);
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setOnRegistrationListener(OnRegistrationListener listener) {
        this.onRegistrationListener = listener;
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
        final Context context = this.contextWeakReference.get();
        if (context == null) {
            return null;
        }
        if (!Utils.isNetworkAvailable(context)) {
            this.message = context.getString(R.string.error_internet_connection);
            this.userRegistered = false;
            return null;
        }
        String language = Locale.getDefault().getLanguage();
        String userCountryCode = DeviceSettingsProvider.getUserCountryCode();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        new ServerAuthenticator(this.username, this.password, defaultSharedPreferences.getString("token", Constants.NO_TOKEN), CatrobatWebClient.INSTANCE.getClient(), "https://t.me/NewCatroidDevs", defaultSharedPreferences, new ServerAuthenticator.TaskListener() { // from class: org.catrobat.catroid.transfers.RegistrationTask.1
            @Override // org.catrobat.catroid.web.ServerAuthenticator.TaskListener
            public void onError(int statusCode, String errorMessage) {
                RegistrationTask.this.message = context.getString(R.string.error_internet_connection);
                RegistrationTask.this.userRegistered = false;
            }

            @Override // org.catrobat.catroid.web.ServerAuthenticator.TaskListener
            public void onSuccess() {
                RegistrationTask.this.userRegistered = true;
            }
        }).performCatrobatRegister(this.email, language, userCountryCode);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Void any) {
        Context context = this.contextWeakReference.get();
        if (context == null) {
            return;
        }
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null && progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
        if (this.userRegistered) {
            ToastUtil.showSuccess(context, R.string.new_user_registered);
            OnRegistrationListener onRegistrationListener = this.onRegistrationListener;
            if (onRegistrationListener != null) {
                onRegistrationListener.onRegistrationComplete();
                return;
            }
            return;
        }
        if (this.message == null) {
            this.message = context.getString(R.string.register_error);
        }
        ToastUtil.showError(context, this.message);
        OnRegistrationListener onRegistrationListener2 = this.onRegistrationListener;
        if (onRegistrationListener2 != null) {
            onRegistrationListener2.onRegistrationFailed(this.message);
        }
    }
}
