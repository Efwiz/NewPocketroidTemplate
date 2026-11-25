package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/GoToBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GoToBrick extends BrickBaseType implements BrickSpinner.OnItemSelectedListener<Sprite> {
    private static final long serialVersionUID = 1;
    private Sprite destinationSprite;
    private int spinnerSelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_go_to;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
    }

    public GoToBrick() {
    }

    public GoToBrick(Sprite destinationSprite) {
        this.destinationSprite = destinationSprite;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new StringOption(context.getString(R.string.brick_go_to_touch_position)));
        arrayList.add(new StringOption(context.getString(R.string.brick_go_to_random_position)));
        arrayList.addAll(ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList());
        arrayList.remove(ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite());
        arrayList.remove(ProjectManager.getInstance().getCurrentSprite());
        BrickSpinner brickSpinner = new BrickSpinner(Integer.valueOf(R.id.brick_go_to_spinner), this.view, arrayList);
        brickSpinner.setOnItemSelectedListener(this);
        if (this.spinnerSelection == 80) {
            brickSpinner.setSelection(0);
        }
        if (this.spinnerSelection == 81) {
            brickSpinner.setSelection(1);
        }
        if (this.spinnerSelection == 82) {
            brickSpinner.setSelection(this.destinationSprite);
        }
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
        Context context = this.view.getContext();
        if (string.equals(context.getString(R.string.brick_go_to_touch_position))) {
            this.spinnerSelection = 80;
        }
        if (string.equals(context.getString(R.string.brick_go_to_random_position))) {
            this.spinnerSelection = 81;
        }
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, Sprite item) {
        this.spinnerSelection = 82;
        this.destinationSprite = item;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createGoToAction(sprite, this.destinationSprite, this.spinnerSelection));
    }
}
