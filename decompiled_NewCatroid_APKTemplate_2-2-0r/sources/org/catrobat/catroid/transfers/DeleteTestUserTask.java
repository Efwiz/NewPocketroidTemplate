package org.catrobat.catroid.transfers;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.CatrobatServerCalls;
import org.catrobat.catroid.web.WebConnectionException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/DeleteTestUserTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DeleteTestUserTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "DeleteTestUserTask";
    private Context context;
    private WebConnectionException exception;
    private OnDeleteTestUserCompleteListener onDeleteTestUserCompleteListener;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/DeleteTestUserTask$OnDeleteTestUserCompleteListener.class
      classes14.dex
     */
    public interface OnDeleteTestUserCompleteListener {
        void onDeleteTestUserComplete(Boolean deleted);
    }

    public DeleteTestUserTask(Context context) {
        this.context = context;
    }

    public void setOnDeleteTestUserCompleteListener(OnDeleteTestUserCompleteListener listener) {
        this.onDeleteTestUserCompleteListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean doInBackground(Void... params) {
        try {
            if (!Utils.isNetworkAvailable(this.context)) {
                this.exception = new WebConnectionException(1002, "Network not available!");
                return false;
            }
            return Boolean.valueOf(new CatrobatServerCalls().deleteTestUserAccountsOnServer());
        } catch (WebConnectionException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            this.exception = e;
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Boolean deleted) {
        super.onPostExecute(deleted);
        if (Utils.checkForNetworkError(this.exception)) {
            showDialog(R.string.error_internet_connection);
            return;
        }
        OnDeleteTestUserCompleteListener onDeleteTestUserCompleteListener = this.onDeleteTestUserCompleteListener;
        if (onDeleteTestUserCompleteListener != null) {
            onDeleteTestUserCompleteListener.onDeleteTestUserComplete(deleted);
        }
    }

    private void showDialog(int messageId) {
        if (this.context == null) {
            return;
        }
        if (this.exception.getMessage() == null) {
            new AlertDialog.Builder(this.context).setMessage(messageId).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).show();
        } else {
            new AlertDialog.Builder(this.context).setMessage(this.exception.getMessage()).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).show();
        }
    }
}
