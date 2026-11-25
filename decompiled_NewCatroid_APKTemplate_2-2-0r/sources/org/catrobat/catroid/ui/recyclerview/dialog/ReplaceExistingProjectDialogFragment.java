package org.catrobat.catroid.ui.recyclerview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import java.io.File;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.ui.ViewUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.web.GlobalProjectDownloadQueue;
import org.catrobat.catroid.web.ProjectDownloader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/ReplaceExistingProjectDialogFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ReplaceExistingProjectDialogFragment extends DialogFragment {
    public static final String BUNDLE_KEY_DOWNLOADER = "downloader";
    public static final String BUNDLE_KEY_PROGRAM_NAME = "programName";
    public static final String TAG = "ReplaceExistingProjectDialogFragment";

    public static ReplaceExistingProjectDialogFragment newInstance(String programName, ProjectDownloader downloader) {
        ReplaceExistingProjectDialogFragment replaceExistingProjectDialogFragment = new ReplaceExistingProjectDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_PROGRAM_NAME, programName);
        bundle.putSerializable(BUNDLE_KEY_DOWNLOADER, downloader);
        replaceExistingProjectDialogFragment.setArguments(bundle);
        return replaceExistingProjectDialogFragment;
    }

    public static Boolean projectExistsInDirectory(String projectName) {
        return Boolean.valueOf(new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(projectName)).exists());
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String string = getArguments().getString(BUNDLE_KEY_PROGRAM_NAME);
        final ProjectDownloader projectDownloader = (ProjectDownloader) getArguments().getSerializable(BUNDLE_KEY_DOWNLOADER);
        View viewInflate = View.inflate(getActivity(), R.layout.dialog_overwrite_project, null);
        final TextInputLayout textInputLayout = (TextInputLayout) viewInflate.findViewById(R.id.input);
        final RadioGroup radioGroup = (RadioGroup) viewInflate.findViewById(R.id.radio_group);
        final InputWatcher.TextWatcher textWatcher = new InputWatcher.TextWatcher() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment.1
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher.TextWatcher
            protected boolean isNameUnique(String name2) {
                return (ReplaceExistingProjectDialogFragment.projectExistsInDirectory(name2).booleanValue() || GlobalProjectDownloadQueue.INSTANCE.getQueue().alreadyInQueue(name2)) ? false : true;
            }
        };
        final AlertDialog alertDialogCreate = new TextInputDialog.Builder(getContext()).setText(string).setTextWatcher(textWatcher).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                this.f$0.m5754xe5e4edbe(radioGroup, projectDownloader, dialogInterface, str);
            }
        }).setTitle(R.string.overwrite_title).setView(viewInflate).setNegativeButton(R.string.notification_download_project_cancel, (DialogInterface.OnClickListener) null).create();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment$$ExternalSyntheticLambda1
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                this.f$0.m5755x149657dd(textInputLayout, alertDialogCreate, textWatcher, radioGroup2, i);
            }
        });
        return alertDialogCreate;
    }

    /* JADX INFO: renamed from: lambda$onCreateDialog$0$org-catrobat-catroid-ui-recyclerview-dialog-ReplaceExistingProjectDialogFragment, reason: not valid java name */
    /* synthetic */ void m5754xe5e4edbe(RadioGroup radioGroup, ProjectDownloader projectDownloader, DialogInterface dialogInterface, String str) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rename /* 2131365129 */:
                projectDownloader.downloadOverwriteExistingProject(context, str);
                return;
            case R.id.replace /* 2131365130 */:
                ProjectManager.getInstance().setCurrentProject(null);
                projectDownloader.downloadOverwriteExistingProject(context, str);
                return;
            default:
                throw new IllegalStateException(TAG + ": Cannot find RadioButton.");
        }
    }

    /* JADX INFO: renamed from: lambda$onCreateDialog$1$org-catrobat-catroid-ui-recyclerview-dialog-ReplaceExistingProjectDialogFragment, reason: not valid java name */
    /* synthetic */ void m5755x149657dd(TextInputLayout textInputLayout, AlertDialog alertDialog, InputWatcher.TextWatcher textWatcher, RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rename /* 2131365129 */:
                textInputLayout.setVisibility(0);
                ViewUtils.showKeyboard(textInputLayout.getEditText());
                alertDialog.getButton(-1).setEnabled(textWatcher.validateInput(textInputLayout.getEditText().getText().toString(), getContext()) == null);
                break;
            case R.id.replace /* 2131365130 */:
                textInputLayout.setVisibility(8);
                ViewUtils.hideKeyboard(textInputLayout.getEditText());
                alertDialog.getButton(-1).setEnabled(true);
                break;
        }
    }
}
