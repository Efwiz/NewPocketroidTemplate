package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenBackgroundChangesScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenBackgroundChangesBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenBackgroundChangesBrick extends BrickBaseType implements ScriptBrick, BrickSpinner.OnItemSelectedListener<LookData>, NewItemInterface<LookData> {
    private static final long serialVersionUID = 1;
    private WhenBackgroundChangesScript script;
    private transient BrickSpinner<LookData> spinner;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public int getPositionInDragAndDropTargetList() {
        return -1;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public int getPositionInScript() {
        return -1;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_when_background_changes_to;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
    }

    public WhenBackgroundChangesBrick() {
        this(new WhenBackgroundChangesScript());
    }

    public WhenBackgroundChangesBrick(WhenBackgroundChangesScript script) {
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
    }

    public LookData getLook() {
        return this.script.getLook();
    }

    public void setLook(LookData look) {
        this.script.setLook(look);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        WhenBackgroundChangesBrick whenBackgroundChangesBrick = (WhenBackgroundChangesBrick) super.clone();
        WhenBackgroundChangesScript whenBackgroundChangesScript = (WhenBackgroundChangesScript) this.script.clone();
        whenBackgroundChangesBrick.script = whenBackgroundChangesScript;
        whenBackgroundChangesScript.setScriptBrick(whenBackgroundChangesBrick);
        whenBackgroundChangesBrick.spinner = null;
        return whenBackgroundChangesBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(final Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewOption(context.getString(R.string.new_option)));
        arrayList.addAll(ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite().getLookList());
        BrickSpinner<LookData> brickSpinner = new BrickSpinner<>(Integer.valueOf(R.id.brick_when_background_spinner), this.view, arrayList);
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(getLook());
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        if (activityFromView instanceof SpriteActivity) {
            SpriteActivity spriteActivity = (SpriteActivity) activityFromView;
            spriteActivity.setCurrentSprite(ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite());
            spriteActivity.registerOnNewLookListener(this);
            spriteActivity.handleAddBackgroundButton();
        }
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, LookData item) {
        setLook(item);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        getScript().setCommentedOut(commentedOut);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetLookAction(sprite, getLook()));
    }

    @Override // org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface
    public void addItem(LookData item) {
        this.spinner.add(item);
        this.spinner.setSelection(item);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        return getScript().getBrickList();
    }
}
