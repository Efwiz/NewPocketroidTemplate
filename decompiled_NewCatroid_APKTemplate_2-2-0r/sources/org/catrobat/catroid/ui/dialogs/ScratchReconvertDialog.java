package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/ScratchReconvertDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScratchReconvertDialog extends DialogFragment {
    public static final String DIALOG_FRAGMENT_TAG = "scratch_reconvert_dialog";
    private static final String TAG = "ScratchReconvertDialog";
    protected Date cachedDate;
    protected ReconvertDialogCallback callback;
    protected Context context;
    protected RadioButton downloadExistingProgramRadioButton;
    protected RadioButton reconvertProgramRadioButton;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/ScratchReconvertDialog$ReconvertDialogCallback.class
      classes14.dex
     */
    public interface ReconvertDialogCallback {
        void onDownloadExistingProgram();

        void onReconvertProgram();

        void onUserCanceledConversion();
    }

    public void setCachedDate(final Date cachedDate) {
        this.cachedDate = cachedDate;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setReconvertDialogCallback(final ReconvertDialogCallback callback) {
        this.callback = callback;
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String quantityString;
        View viewInflate = View.inflate(getActivity(), R.layout.dialog_scratch_reconvert, null);
        this.downloadExistingProgramRadioButton = (RadioButton) viewInflate.findViewById(R.id.dialog_scratch_reconvert_radio_download);
        this.reconvertProgramRadioButton = (RadioButton) viewInflate.findViewById(R.id.dialog_scratch_reconvert_radio_reconvert);
        long time = new Date().getTime() - this.cachedDate.getTime();
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(time);
        int hours = (int) TimeUnit.MILLISECONDS.toHours(time);
        int days = (int) TimeUnit.MILLISECONDS.toDays(time);
        if (days > 0) {
            quantityString = getResources().getQuantityString(R.plurals.days, days, Integer.valueOf(days));
        } else if (hours > 0) {
            quantityString = getResources().getQuantityString(R.plurals.hours, hours, Integer.valueOf(hours));
        } else {
            quantityString = getResources().getQuantityString(R.plurals.minutes, minutes, Integer.valueOf(minutes));
        }
        AlertDialog alertDialogCreate = new AlertDialog.Builder(getActivity()).setView(viewInflate).setTitle(getResources().getString(R.string.reconvert_text, quantityString)).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                ScratchReconvertDialog.this.handleOkButton();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                Log.d(ScratchReconvertDialog.TAG, "User canceled dialog by pressing Cancel-button");
                ToastUtil.showError(ScratchReconvertDialog.this.context, R.string.notification_reconvert_download_program_cancel);
                if (ScratchReconvertDialog.this.callback != null) {
                    ScratchReconvertDialog.this.callback.onUserCanceledConversion();
                }
            }
        }).create();
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog.3
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialog) {
                ((AlertDialog) dialog).getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ScratchReconvertDialog.this.handleOkButton();
                    }
                });
            }
        });
        alertDialogCreate.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog.4
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == 0 && keyCode == 66) {
                    boolean zHandleOkButton = ScratchReconvertDialog.this.handleOkButton();
                    if (zHandleOkButton) {
                        ScratchReconvertDialog.this.dismiss();
                    }
                    return zHandleOkButton;
                }
                if (keyCode != 4) {
                    return false;
                }
                Log.d(ScratchReconvertDialog.TAG, "User canceled dialog by pressing Back-button");
                ToastUtil.showError(ScratchReconvertDialog.this.context, R.string.notification_reconvert_download_program_cancel);
                if (ScratchReconvertDialog.this.callback != null) {
                    ScratchReconvertDialog.this.callback.onUserCanceledConversion();
                }
                ScratchReconvertDialog.this.dismiss();
                return true;
            }
        });
        return alertDialogCreate;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(TAG, "User canceled dialog by clicking outside of the Dialog fragment");
        ToastUtil.showError(this.context, R.string.notification_reconvert_download_program_cancel);
        ReconvertDialogCallback reconvertDialogCallback = this.callback;
        if (reconvertDialogCallback != null) {
            reconvertDialogCallback.onUserCanceledConversion();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleOkButton() {
        if (this.downloadExistingProgramRadioButton.isChecked()) {
            this.callback.onDownloadExistingProgram();
        } else if (this.reconvertProgramRadioButton.isChecked()) {
            this.callback.onReconvertProgram();
        }
        dismiss();
        return true;
    }
}
