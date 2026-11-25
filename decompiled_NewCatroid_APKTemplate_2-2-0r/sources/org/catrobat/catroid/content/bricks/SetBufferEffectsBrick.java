package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetBufferEffectsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetBufferEffectsBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_buffer_effects;
    }

    public SetBufferEffectsBrick() {
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_buffer_fx_name);
        addAllowedBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, R.id.brick_buffer_fx_vfx);
        addAllowedBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, R.id.brick_buffer_fx_mip);
    }

    public SetBufferEffectsBrick(String name2, float vfx, float mip) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, new Formula(name2));
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, new Formula(Float.valueOf(vfx)));
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, new Formula(Float.valueOf(mip)));
    }

    public SetBufferEffectsBrick(Formula name2, Formula vfx, Formula mip) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, name2);
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, vfx);
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, mip);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetBufferEffectsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.IF_CONDITION), getFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE), getFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE)));
    }
}
