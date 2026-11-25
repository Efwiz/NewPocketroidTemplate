package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenBounceOffScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenBounceOffBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenBounceOffBrick extends ScriptBrickBaseType implements BrickSpinner.OnItemSelectedListener<Sprite> {
    private static final String ANYTHING_ESCAPE_CHAR = "\u0000";
    private static final long serialVersionUID = 1;
    private WhenBounceOffScript script;
    private transient BrickSpinner<Sprite> spinner;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_when_bounce_off;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
    }

    public WhenBounceOffBrick() {
        this(new WhenBounceOffScript());
    }

    public WhenBounceOffBrick(WhenBounceOffScript script) {
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public BrickBaseType clone() throws CloneNotSupportedException {
        WhenBounceOffBrick whenBounceOffBrick = (WhenBounceOffBrick) super.clone();
        WhenBounceOffScript whenBounceOffScript = (WhenBounceOffScript) this.script.clone();
        whenBounceOffBrick.script = whenBounceOffScript;
        whenBounceOffScript.setScriptBrick(whenBounceOffBrick);
        whenBounceOffBrick.spinner = null;
        return whenBounceOffBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new StringOption("\u0000" + context.getString(R.string.collision_with_anything) + "\u0000"));
        arrayList.addAll(ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList());
        BrickSpinner<Sprite> brickSpinner = new BrickSpinner<>(Integer.valueOf(R.id.brick_when_bounce_off_spinner), this.view, arrayList);
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.script.getSpriteToBounceOffName());
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
        this.script.setSpriteToBounceOffName(null);
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, Sprite item) {
        this.script.setSpriteToBounceOffName(item != null ? item.getName() : null);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 3);
    }
}
