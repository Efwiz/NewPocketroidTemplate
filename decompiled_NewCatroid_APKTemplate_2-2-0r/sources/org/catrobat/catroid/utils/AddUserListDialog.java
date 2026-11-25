package org.catrobat.catroid.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.RadioButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/AddUserListDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AddUserListDialog {
    private TextInputDialog.Builder builder;
    private List<UserList> existingUserLists;
    private BrickSpinner<UserList> spinner;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/AddUserListDialog$Callback.class
      classes14.dex
     */
    public interface Callback {
        void onNegativeButton();

        void onPositiveButton(DialogInterface dialog, String textInput);
    }

    public AddUserListDialog(TextInputDialog.Builder builder, BrickSpinner<UserList> spinner) {
        this.builder = builder;
        this.spinner = spinner;
        this.existingUserLists = spinner.getItems();
    }

    public AddUserListDialog(TextInputDialog.Builder builder) {
        this.builder = builder;
        this.existingUserLists = new ArrayList();
    }

    public void show(String hint, String ok, final Callback callback) {
        TextInputDialog.Builder text = this.builder.setHint(hint).setTextWatcher(new DuplicateInputTextWatcher(this.existingUserLists)).setText(this.builder.createUniqueNameProvider(R.string.default_list_name).getUniqueName(this.builder.getContext().getString(R.string.default_list_name), null));
        Objects.requireNonNull(callback);
        text.setPositiveButton(ok, new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.utils.AddUserListDialog$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                callback.onPositiveButton(dialogInterface, str);
            }
        }).setTitle(R.string.formula_editor_list_dialog_title).setView(R.layout.dialog_new_user_data).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.AddUserListDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                callback.onNegativeButton();
            }
        });
        if (this.spinner != null) {
            this.builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.catrobat.catroid.utils.AddUserListDialog$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    callback.onNegativeButton();
                }
            });
        }
        this.builder.show();
    }

    public void addUserList(DialogInterface dialog, UserList userList, List<UserList> projectUserList, List<UserList> spriteUserList) {
        if (((RadioButton) ((Dialog) dialog).findViewById(R.id.global)).isChecked()) {
            projectUserList.add(userList);
        } else {
            spriteUserList.add(userList);
        }
    }
}
