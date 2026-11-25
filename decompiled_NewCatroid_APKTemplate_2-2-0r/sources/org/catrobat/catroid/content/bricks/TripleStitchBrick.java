package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/TripleStitchBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TripleStitchBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_triple_stitch;
    }

    public TripleStitchBrick() {
        addAllowedBrickField(Brick.BrickField.EMBROIDERY_LENGTH, R.id.brick_triple_stitch_edit_text_steps);
    }

    public TripleStitchBrick(Formula steps) {
        this();
        setFormulaWithBrickField(Brick.BrickField.EMBROIDERY_LENGTH, steps);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.EMBROIDERY_LENGTH;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createTripleStitchAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.EMBROIDERY_LENGTH)));
    }
}
