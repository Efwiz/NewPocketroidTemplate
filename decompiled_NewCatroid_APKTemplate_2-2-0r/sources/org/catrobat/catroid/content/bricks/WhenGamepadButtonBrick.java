package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenGamepadButtonScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenGamepadButtonBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenGamepadButtonBrick extends ScriptBrickBaseType implements BrickSpinner.OnItemSelectedListener<StringOption> {
    private static final long serialVersionUID = 1;
    private WhenGamepadButtonScript script;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_when_gamepad_button;
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

    public WhenGamepadButtonBrick() {
        this.script = new WhenGamepadButtonScript();
    }

    public WhenGamepadButtonBrick(WhenGamepadButtonScript script) {
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        WhenGamepadButtonBrick whenGamepadButtonBrick = (WhenGamepadButtonBrick) super.clone();
        WhenGamepadButtonScript whenGamepadButtonScript = (WhenGamepadButtonScript) this.script.clone();
        whenGamepadButtonBrick.script = whenGamepadButtonScript;
        whenGamepadButtonScript.setScriptBrick(whenGamepadButtonBrick);
        return whenGamepadButtonBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new StringOption(context.getString(R.string.cast_gamepad_A)));
        arrayList.add(new StringOption(context.getString(R.string.cast_gamepad_B)));
        arrayList.add(new StringOption(context.getString(R.string.cast_gamepad_up)));
        arrayList.add(new StringOption(context.getString(R.string.cast_gamepad_down)));
        arrayList.add(new StringOption(context.getString(R.string.cast_gamepad_left)));
        arrayList.add(new StringOption(context.getString(R.string.cast_gamepad_right)));
        BrickSpinner brickSpinner = new BrickSpinner(Integer.valueOf(R.id.brick_when_gamepad_button_spinner), this.view, arrayList);
        brickSpinner.setOnItemSelectedListener(this);
        brickSpinner.setSelection(this.script.getAction());
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
        this.script.setAction(string);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 22);
    }
}
