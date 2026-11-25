package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/DeleteWebBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DeleteWebBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_delete_web;
    }

    public DeleteWebBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.delete_web_name);
    }

    public DeleteWebBrick(String name2) {
        this(new Formula(name2));
    }

    public DeleteWebBrick(Formula name2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, name2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDeleteWebAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME)));
    }
}
