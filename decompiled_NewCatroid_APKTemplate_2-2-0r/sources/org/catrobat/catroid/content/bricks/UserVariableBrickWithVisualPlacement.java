package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.content.bricks.brickspinner.UserVariableBrickTextInputDialogBuilder;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.utils.ShowTextUtils;
import org.catrobat.catroid.visualplacement.VisualPlacementActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UserVariableBrickWithVisualPlacement.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class UserVariableBrickWithVisualPlacement extends VisualPlacementBrick implements UserVariableBrickInterface {
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

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        UserVariableBrickWithVisualPlacement userVariableBrickWithVisualPlacement = (UserVariableBrickWithVisualPlacement) super.clone();
        userVariableBrickWithVisualPlacement.spinner = null;
        return userVariableBrickWithVisualPlacement;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
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

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Brick.BrickField getXBrickField() {
        return Brick.BrickField.X_POSITION;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Brick.BrickField getYBrickField() {
        return Brick.BrickField.Y_POSITION;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Intent generateIntentForVisualPlacement(Brick.BrickField brickFieldX, Brick.BrickField brickFieldY) {
        Intent intentGenerateIntentForVisualPlacement = super.generateIntentForVisualPlacement(brickFieldX, brickFieldY);
        Object value = 0;
        UserVariable userVariable = this.userVariable;
        if (userVariable != null) {
            value = userVariable.getValue();
        }
        String string = value.toString();
        if (ShowTextUtils.isNumberAndInteger(string)) {
            string = ShowTextUtils.getStringAsInteger(string);
        }
        intentGenerateIntentForVisualPlacement.putExtra(SpriteActivity.EXTRA_TEXT, string);
        intentGenerateIntentForVisualPlacement.putExtra(SpriteActivity.EXTRA_TEXT_COLOR, ShowTextUtils.convertColorToString(ViewCompat.MEASURED_STATE_MASK));
        intentGenerateIntentForVisualPlacement.putExtra(SpriteActivity.EXTRA_TEXT_SIZE, 1.0f);
        intentGenerateIntentForVisualPlacement.putExtra(SpriteActivity.EXTRA_TEXT_ALIGNMENT, 1);
        intentGenerateIntentForVisualPlacement.putExtra(VisualPlacementActivity.EXTRA_IS_VARIABLE, true);
        return intentGenerateIntentForVisualPlacement;
    }
}
