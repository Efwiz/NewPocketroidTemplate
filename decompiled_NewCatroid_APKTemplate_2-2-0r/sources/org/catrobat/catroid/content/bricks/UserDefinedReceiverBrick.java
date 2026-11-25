package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.UserDefinedScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UserDefinedReceiverBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UserDefinedReceiverBrick extends ScriptBrickBaseType implements BrickSpinner.OnItemSelectedListener<StringOption> {
    private static final long serialVersionUID = 1;
    public int spinnerSelection;
    private LinearLayout userBrickSpace;
    private Brick userDefinedBrick;
    private UserDefinedScript userDefinedScript;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_user_defined_script;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, StringOption item) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
    }

    public UserDefinedReceiverBrick(UserDefinedScript userDefinedScript) {
        this.spinnerSelection = !userDefinedScript.getScreenRefresh().booleanValue() ? 1 : 0;
        this.userDefinedScript = userDefinedScript;
    }

    public UserDefinedReceiverBrick(UserDefinedBrick userDefinedBrick) {
        UserDefinedScript userDefinedScript = new UserDefinedScript(userDefinedBrick.getUserDefinedBrickID());
        this.userDefinedScript = userDefinedScript;
        userDefinedScript.setScriptBrick(this);
        this.userDefinedBrick = userDefinedBrick;
    }

    public UserDefinedReceiverBrick() {
        this.userDefinedScript = new UserDefinedScript();
    }

    public UserDefinedBrick getUserDefinedBrick() {
        return (UserDefinedBrick) this.userDefinedBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.userDefinedScript;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        UserDefinedReceiverBrick userDefinedReceiverBrick = (UserDefinedReceiverBrick) super.clone();
        UserDefinedScript userDefinedScript = (UserDefinedScript) this.userDefinedScript.clone();
        userDefinedReceiverBrick.userDefinedScript = userDefinedScript;
        userDefinedScript.setScriptBrick(userDefinedReceiverBrick);
        Brick brick = this.userDefinedBrick;
        if (brick != null) {
            userDefinedReceiverBrick.userDefinedBrick = brick.clone();
        }
        return userDefinedReceiverBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        this.userBrickSpace = (LinearLayout) this.view.findViewById(R.id.user_brick_space);
        if (this.userDefinedBrick == null) {
            this.userDefinedBrick = ProjectManager.getInstance().getCurrentSprite().getUserDefinedBrickByID(this.userDefinedScript.getUserDefinedBrickID());
        }
        Brick brick = this.userDefinedBrick;
        if (brick != null) {
            this.userBrickSpace.addView(brick.getView(context));
        }
        setUpSpinner(context);
        return this.view;
    }

    private void setUpSpinner(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new StringOption(context.getString(R.string.brick_user_defined_with_screen_refreshing)));
        arrayList.add(new StringOption(context.getString(R.string.brick_user_defined_without_screen_refreshing)));
        BrickSpinner brickSpinner = new BrickSpinner(Integer.valueOf(R.id.brick_set_screen_refresh_spinner), this.view, R.layout.userdefined_brick_spinner_item, arrayList);
        brickSpinner.setSpinnerFontColor(context, R.color.dark_blue);
        brickSpinner.setOnItemSelectedListener(this);
        if (this.spinnerSelection == 0) {
            brickSpinner.setSelection(0);
        }
        if (this.spinnerSelection == 1) {
            brickSpinner.setSelection(1);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
        Context context = this.view.getContext();
        if (string.equals(context.getString(R.string.brick_user_defined_with_screen_refreshing))) {
            this.spinnerSelection = 0;
        }
        if (string.equals(context.getString(R.string.brick_user_defined_without_screen_refreshing))) {
            this.spinnerSelection = 1;
        }
        this.userDefinedScript.setScreenRefresh(Boolean.valueOf(this.spinnerSelection == 0));
    }
}
