package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;
import org.catrobat.catroid.utils.AddUserListDialog;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UserListBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class UserListBrick extends FormulaBrick implements BrickSpinner.OnItemSelectedListener<UserList> {
    private transient BrickSpinner<UserList> spinner;
    protected UserList userList;

    protected abstract int getSpinnerId();

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
    }

    public UserList getUserList() {
        return this.userList;
    }

    public void setUserList(UserList userList) {
        this.userList = userList;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        UserListBrick userListBrick = (UserListBrick) super.clone();
        userListBrick.spinner = null;
        return userListBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewOption(context.getString(R.string.new_option)));
        arrayList.addAll(currentSprite.getUserLists());
        arrayList.addAll(ProjectManager.getInstance().getCurrentProject().getUserLists());
        BrickSpinner<UserList> brickSpinner = new BrickSpinner<>(Integer.valueOf(getSpinnerId()), this.view, arrayList);
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.userList);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
        final AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        if (activityFromView == null) {
            return;
        }
        final List<UserList> userLists = ProjectManager.getInstance().getCurrentProject().getUserLists();
        final List<UserList> userLists2 = ProjectManager.getInstance().getCurrentSprite().getUserLists();
        final AddUserListDialog addUserListDialog = new AddUserListDialog(new TextInputDialog.Builder(activityFromView), this.spinner);
        addUserListDialog.show(activityFromView.getString(R.string.data_label), activityFromView.getString(R.string.ok), new AddUserListDialog.Callback() { // from class: org.catrobat.catroid.content.bricks.UserListBrick.1
            @Override // org.catrobat.catroid.utils.AddUserListDialog.Callback
            public void onPositiveButton(DialogInterface dialog, String textInput) {
                UserList userList = new UserList(textInput);
                addUserListDialog.addUserList(dialog, userList, userLists, userLists2);
                UserListBrick.this.spinner.add(userList);
                UserListBrick.this.spinner.setSelection(userList);
                ScriptFragment scriptFragment = (ScriptFragment) activityFromView.getSupportFragmentManager().findFragmentByTag(ScriptFragment.TAG);
                if (scriptFragment != null) {
                    scriptFragment.notifyDataSetChanged();
                }
            }

            @Override // org.catrobat.catroid.utils.AddUserListDialog.Callback
            public void onNegativeButton() {
                UserListBrick.this.spinner.setSelection(UserListBrick.this.userList);
            }
        });
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, UserList item) {
        this.userList = item;
    }
}
