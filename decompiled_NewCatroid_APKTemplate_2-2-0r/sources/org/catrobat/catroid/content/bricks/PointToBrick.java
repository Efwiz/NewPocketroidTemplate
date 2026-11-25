package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PointToBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PointToBrick extends BrickBaseType implements BrickSpinner.OnItemSelectedListener<Sprite>, NewItemInterface<Sprite> {
    private static final long serialVersionUID = 1;
    private Sprite pointedObject;
    private transient BrickSpinner<Sprite> spinner;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_point_to;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
    }

    public PointToBrick() {
    }

    public PointToBrick(Sprite pointedSprite) {
        this.pointedObject = pointedSprite;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        PointToBrick pointToBrick = (PointToBrick) super.clone();
        pointToBrick.spinner = null;
        return pointToBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewOption(context.getString(R.string.new_option)));
        arrayList.addAll(ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList());
        arrayList.remove(ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite());
        arrayList.remove(ProjectManager.getInstance().getCurrentSprite());
        BrickSpinner<Sprite> brickSpinner = new BrickSpinner<>(Integer.valueOf(R.id.brick_point_to_spinner), this.view, arrayList);
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.pointedObject);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        if (activityFromView instanceof SpriteActivity) {
            SpriteActivity spriteActivity = (SpriteActivity) activityFromView;
            spriteActivity.registerOnNewSpriteListener(this);
            spriteActivity.handleAddSpriteButton();
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface
    public void addItem(Sprite item) {
        this.spinner.add(item);
        this.spinner.setSelection(item);
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, Sprite item) {
        this.pointedObject = item;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPointToAction(sprite, this.pointedObject));
    }
}
