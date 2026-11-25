package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetBackgroundBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetBackgroundBrick extends BrickBaseType implements BrickSpinner.OnItemSelectedListener<LookData>, NewItemInterface<LookData> {
    private static final long serialVersionUID = 1;
    protected LookData look;
    private transient BrickSpinner<LookData> spinner;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_background;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
    }

    public LookData getLook() {
        return this.look;
    }

    public void setLook(LookData look) {
        this.look = look;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        SetBackgroundBrick setBackgroundBrick = (SetBackgroundBrick) super.clone();
        setBackgroundBrick.spinner = null;
        return setBackgroundBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewOption(context.getString(R.string.new_option)));
        arrayList.addAll(ProjectManager.getInstance().getCurrentlyPlayingScene().getBackgroundSprite().getLookList());
        BrickSpinner<LookData> brickSpinner = new BrickSpinner<>(Integer.valueOf(R.id.brick_set_background_spinner), this.view, arrayList);
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.look);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetBackgroundAction(this.look, false));
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        if (activityFromView instanceof SpriteActivity) {
            SpriteActivity spriteActivity = (SpriteActivity) activityFromView;
            spriteActivity.registerOnNewLookListener(this);
            spriteActivity.handleAddBackgroundButton();
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface
    public void addItem(LookData item) {
        this.spinner.add(item);
        this.spinner.setSelection(item);
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, LookData item) {
        this.look = item;
    }
}
