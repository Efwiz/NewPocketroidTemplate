package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PlayGifBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PlayGifBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_play_gif;
    }

    public PlayGifBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_play_gif_edit);
    }

    public PlayGifBrick(String filename) {
        this(new Formula(filename));
    }

    public PlayGifBrick(Formula filenameFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, filenameFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPlayGifAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1)));
    }
}
