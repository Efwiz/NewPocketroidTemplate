package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetGeminiKeyBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetGeminiKeyBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_gemini_key;
    }

    public SetGeminiKeyBrick() {
        addAllowedBrickField(Brick.BrickField.KEY, R.id.brick_set_gemini_key_edit_text);
    }

    public SetGeminiKeyBrick(String val1) {
        this(new Formula(val1));
    }

    public SetGeminiKeyBrick(Formula key) {
        this();
        setFormulaWithBrickField(Brick.BrickField.KEY, key);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetGeminiKey(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.KEY)));
    }
}
