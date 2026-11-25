package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.content.bricks.brickspinner.UserVariableBrickTextInputDialogBuilder;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.ui.UiUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UserVariableBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class UserVariableBrick extends BrickBaseType implements UserVariableBrickInterface {
    private transient BrickSpinner<UserVariable> spinner;
    protected UserVariable userVariable;

    protected abstract int getSpinnerId();

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickInterface
    public UserVariable getUserVariable() {
        return this.userVariable;
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickInterface
    public void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        UserVariableBrick userVariableBrick = (UserVariableBrick) super.clone();
        userVariableBrick.spinner = null;
        return userVariableBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewOption(context.getString(R.string.new_option)));
        arrayList.addAll(currentSprite.getUserVariables());
        arrayList.addAll(ProjectManager.getInstance().getCurrentProject().getUserVariables());
        arrayList.addAll(ProjectManager.getInstance().getCurrentProject().getMultiplayerVariables());
        BrickSpinner<UserVariable> brickSpinner = new BrickSpinner<>(Integer.valueOf(getSpinnerId()), this.view, arrayList);
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.userVariable);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        if (activityFromView == null) {
            return;
        }
        new UserVariableBrickTextInputDialogBuilder(ProjectManager.getInstance().getCurrentProject(), ProjectManager.getInstance().getCurrentSprite(), this.userVariable, activityFromView, this.spinner).show();
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, UserVariable item) {
        this.userVariable = item;
    }
}
