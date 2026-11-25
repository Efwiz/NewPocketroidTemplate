package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CloneAndNameBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CloneAndNameBrick extends FormulaBrick implements BrickSpinner.OnItemSelectedListener<Sprite> {
    private static final long serialVersionUID = 1;
    private Sprite objectToClone;
    private transient BrickSpinner<Sprite> spinner;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_clone_and_name;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
    }

    public CloneAndNameBrick() {
        addAllowedBrickField(Brick.BrickField.CLONE_NAME, R.id.brick_clone_name_edit_text);
    }

    public Sprite getSelectedItem() {
        return this.objectToClone;
    }

    public void resetSpinner() {
        this.spinner.setSelection(0);
        this.objectToClone = null;
    }

    public CloneAndNameBrick(String cloneName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.CLONE_NAME, new Formula(cloneName));
    }

    public CloneAndNameBrick(Formula cloneNameFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.CLONE_NAME, cloneNameFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new StringOption(context.getString(R.string.brick_clone_this)));
        arrayList.addAll(ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList());
        arrayList.remove(ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite());
        arrayList.remove(ProjectManager.getInstance().getCurrentSprite());
        BrickSpinner<Sprite> brickSpinner = new BrickSpinner<>(Integer.valueOf(R.id.brick_clone_spinner), this.view, arrayList);
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.objectToClone);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
        this.objectToClone = null;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, Sprite item) {
        this.objectToClone = item;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Sprite sprite2 = this.objectToClone;
        if (sprite2 == null) {
            sprite2 = sprite;
        }
        Formula formulaWithBrickField = getFormulaWithBrickField(Brick.BrickField.CLONE_NAME);
        sequence.addAction(sprite.getActionFactory().createCloneAndNameAction(sprite2, new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence), formulaWithBrickField));
    }
}
