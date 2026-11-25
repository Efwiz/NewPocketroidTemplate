package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WriteBaseBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WriteBaseBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_write_base;
    }

    public WriteBaseBrick() {
        addAllowedBrickField(Brick.BrickField.FIREBASE_ID, R.id.brick_write_base_edit_base);
        addAllowedBrickField(Brick.BrickField.FIREBASE_KEY, R.id.brick_write_base_edit_key);
        addAllowedBrickField(Brick.BrickField.FIREBASE_VALUE, R.id.brick_write_base_edit_value);
    }

    public WriteBaseBrick(String base, String key, String value) {
        this(new Formula(base), new Formula(key), new Formula(value));
    }

    public WriteBaseBrick(Formula base, Formula key, Formula value) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FIREBASE_ID, base);
        setFormulaWithBrickField(Brick.BrickField.FIREBASE_KEY, key);
        setFormulaWithBrickField(Brick.BrickField.FIREBASE_VALUE, value);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createWriteBaseAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FIREBASE_ID), getFormulaWithBrickField(Brick.BrickField.FIREBASE_KEY), getFormulaWithBrickField(Brick.BrickField.FIREBASE_VALUE)));
    }
}
