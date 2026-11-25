package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LunoScriptBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LunoScriptBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_lunoscript;
    }

    public LunoScriptBrick() {
        addAllowedBrickField(Brick.BrickField.CODE, R.id.brick_lunoscript_edit_text);
    }

    public LunoScriptBrick(String value) {
        this(new Formula(value));
    }

    public LunoScriptBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.CODE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLunoScriptAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.CODE)));
    }
}
