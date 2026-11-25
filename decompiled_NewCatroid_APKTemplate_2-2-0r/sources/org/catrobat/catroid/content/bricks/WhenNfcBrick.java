package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenNfcScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;
import org.catrobat.catroid.ui.NfcTagsActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenNfcBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenNfcBrick extends ScriptBrickBaseType implements BrickSpinner.OnItemSelectedListener<NfcTagData> {
    private static final long serialVersionUID = 1;
    private WhenNfcScript script;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_when_nfc;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    public WhenNfcBrick() {
        this(new WhenNfcScript());
    }

    public WhenNfcBrick(WhenNfcScript script) {
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        WhenNfcBrick whenNfcBrick = (WhenNfcBrick) super.clone();
        WhenNfcScript whenNfcScript = (WhenNfcScript) this.script.clone();
        whenNfcBrick.script = whenNfcScript;
        whenNfcScript.setScriptBrick(whenNfcBrick);
        return whenNfcBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewOption(context.getString(R.string.brick_when_nfc_edit_list_of_nfc_tags)));
        arrayList.add(new StringOption(context.getString(R.string.brick_when_nfc_default_all)));
        arrayList.addAll(currentSprite.getNfcTagList());
        BrickSpinner brickSpinner = new BrickSpinner(Integer.valueOf(R.id.brick_when_nfc_spinner), this.view, arrayList);
        brickSpinner.setOnItemSelectedListener(this);
        brickSpinner.setSelection(this.script.getNfcTag());
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
        Context context;
        if (this.view == null || (context = this.view.getContext()) == null) {
            return;
        }
        context.startActivity(new Intent(context, (Class<?>) NfcTagsActivity.class));
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
        this.script.setMatchAll(true);
        this.script.setNfcTag(null);
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, NfcTagData item) {
        this.script.setNfcTag(item);
        this.script.setMatchAll(false);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 16);
    }
}
